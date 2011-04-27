package com.xdarkness.cms.resource.uploader;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import com.xdarkness.cms.datachannel.Deploy;
import com.xdarkness.cms.datachannel.Publisher;
import com.xdarkness.cms.dataservice.ColumnUtil;
import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.cms.pub.PubFun;
import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.cms.template.HtmlNameParser;
import com.xdarkness.cms.template.HtmlNameRule;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.ImageUtilEx;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.schema.ZCCatalogSchema;
import com.xdarkness.schema.ZCImageSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.orm.data.DataCollection;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.NumberUtil;
import com.xdarkness.framework.util.ZipUtil;

public class SimpleUploaderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String IMAGE = "Image";
	private static String baseDir;
	private static boolean enabled = false;

	public void init() throws ServletException {
		baseDir = Config.getValue("UploadDir");
		if (baseDir == null) {
			baseDir = "";
		}
		enabled = new Boolean(getInitParameter("enabled")).booleanValue();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (XString.isEmpty(baseDir)) {
			baseDir = Config.getValue("UploadDir");
		}
		response.setContentType("text/html; charset=" + Constant.GlobalCharset);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

		String returnIDs = "";
		String returnPaths = "";
		String returnValue = "0";
		String errorMessage = "";
		String taskID = "";
		String filePath = "";
		String fromObjec = null;

		if (!enabled) {
			returnValue = "1";
			errorMessage = "This file uploader is disabled. Please check the WEB-INF/web.xml file";
		} else {
			FileItemFactory fileFactory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(fileFactory);
			upload.setHeaderEncoding(Constant.GlobalCharset);
			upload.setSizeMax(2097152000L);
			try {
				List items = upload.parseRequest(request);
				Mapx fields = new Mapx();
				Mapx files = new Mapx();
				Iterator iter = items.iterator();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					if (item.isFormField()) {
						fields.put(item.getFieldName(), item
								.getString(Constant.GlobalCharset));
					} else {
						String OldFileName = item.getName();
						long size = item.getSize();
						if (((OldFileName == null) || (OldFileName.equals("")))
								&& (size == 0L)) {
							continue;
						}
						LogUtil.info("-----UploadFileName:-----" + OldFileName);
						files.put(item.getFieldName(), item);
					}

				}

				String repeat = (String) fields.get("Repeat");

				fromObjec = (String) fields.get("FromObject");

				String[] arr = (String[]) null;
				if ("1".equals(repeat))
					arr = repeatUpload(files, fields);
				else {
					arr = upload(files, fields);
				}

				returnIDs = arr[0];
				returnValue = arr[1];
				errorMessage = arr[2];
				taskID = arr[3];
				if (arr.length > 4) {
					filePath = arr[4];
					returnPaths = arr[5];
				}
			} catch (FileUploadBase.SizeLimitExceededException ex) {
				ex.printStackTrace();
				returnValue = "-1";
				errorMessage = "文件过大：";
			} catch (Throwable ex) {
				ex.printStackTrace();
				returnValue = "-1";
				errorMessage = "上传文件失败:" + ex.getMessage();
			}
		}
		if ("activex".equalsIgnoreCase(fromObjec)) {
			out.print(filePath);
			out.close();
		} else {
			out.println("taskid:" + taskID);
			out.println("<script type=\"text/javascript\">");
			out.println("window.parent.onUploadCompleted(" + returnValue + ",'"
					+ returnIDs.toString() + "','" + errorMessage + "','"
					+ returnPaths + "');");
			out.println("</script>");
			out.flush();
			out.close();
		}
	}

	private String[] repeatUpload(Mapx files, Mapx fields) throws Throwable {
		StringBuffer returnIDs = new StringBuffer();
		String returnValue = "0";
		String errorMessage = "";
		String taskID = "";
		if ("Image".equalsIgnoreCase((String) fields.get("FileType"))) {
			ZCImageSchema image = new ZCImageSchema();
			image.setID(Long.parseLong(fields.get("RepeatID").toString()));
			if (!image.fill()) {
				returnValue = "";
				errorMessage = "没有查找到原图片";
			}
			String SiteAlias = SiteUtil.getAlias(image.getSiteID());
			Iterator it = files.keySet().iterator();
			while (it.hasNext()) {
				FileItem uplFile = (FileItem) files.get(it.next());
				String AbsolutePath = (getServletContext().getRealPath(
						new StringBuffer(String.valueOf(baseDir)).append("/")
								.append(SiteAlias).append("/").append(
										image.getPath()).toString()) + "/")
						.replaceAll("//", "/");
				uplFile.write(new File(AbsolutePath + image.getSrcFileName()));
				ImageUtilEx.afterUploadImage(image, AbsolutePath, fields);
			}
			image.setModifyTime(new Date());
			image.setModifyUser(User.getUserName());
			image.update();
		}
		return new String[] { returnIDs.toString(), returnValue, errorMessage,
				taskID };
	}

	private String[] upload(Mapx files, Mapx fields) throws Exception {
		StringBuffer returnIDs = new StringBuffer();
		String returnPaths = "";
		String returnValue = "0";
		String errorMessage = "";
		String taskID = "";
		String filePath = "";
		String catalogID = (String) fields.get("CatalogID");
		String absolutePath = (String) fields.get("AbsolutePath");
		String fileType = (String) fields.get("FileType");

		ZCCatalogSchema catalog = new ZCCatalogSchema();
		String innerCode = "";
		String siteAlias = "";
		String path = "";
		long siteID = 0L;

		if ((catalogID == null) && ("Image".equals(fileType))) {
			catalogID = new QueryBuilder(
					"select ID from ZCCatalog  where type='4' and Name ='默认图片'  and siteid=?",
					ApplicationPage.getCurrentSiteID()).executeString();
			if (XString.isEmpty(catalogID)) {
				catalogID = new QueryBuilder(
						"select ID from ZCCatalog  where type='4' and siteid=?",
						ApplicationPage.getCurrentSiteID()).executeString();
			}
		}

		if (XString.isEmpty(catalogID)) {
			LogUtil.warn("没有找到选择分类库");
			return new String[] { returnIDs.toString(), "1", "没有找到选择分类库",
					taskID, filePath, returnPaths };
		}
		catalog.setID(Long.parseLong(catalogID));
		if (!catalog.fill()) {
			LogUtil.warn("没有找到上传分类库:" + catalogID);
			return new String[] { returnIDs.toString(), "1",
					"没有找到上传分类库:" + catalogID, taskID, filePath, returnPaths };
		}
		siteID = catalog.getSiteID();
		siteAlias = SiteUtil.getAlias(siteID);
		innerCode = catalog.getInnerCode();
		HtmlNameParser nameParser = new HtmlNameParser(null, catalog
				.toDataRow(), null, catalog.getListNameRule());
		HtmlNameRule h = nameParser.getNameRule();
		path = "upload/" + fileType + "/" + h.getDirPath() + "/";
		absolutePath = (getServletContext().getRealPath(
				new StringBuffer(String.valueOf(baseDir)).append("/").append(
						siteAlias).append("/").append(path).toString()) + "/")
				.replaceAll("//", "/");
		LogUtil.info("文件上传AbsolutePath：" + absolutePath);

		if ((!"".equals(absolutePath)) && (absolutePath != null)) {
			File dir = new File(absolutePath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
		}
		fields.put("AbsolutePath", absolutePath);

		ArrayList fileList = new ArrayList();
		Transaction trans = new Transaction();
		DataCollection dc = new DataCollection();
		dc.putAll(fields);

		if ("Image".equalsIgnoreCase(fileType)) {
			String rootPath = Config.getContextPath()
					+ Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/";
			rootPath = rootPath.replaceAll("///", "/");
			rootPath = rootPath.replaceAll("//", "/");

			Object[] vs = files.valueArray();
			for (int m = files.size() - 1; m >= 0; m--) {
				FileItem uplFile = (FileItem) vs[m];
				String fileNameLong = uplFile.getName();
				fileNameLong = fileNameLong.replaceAll("\\\\", "/");
				String oldName = fileNameLong.substring(fileNameLong
						.lastIndexOf("/") + 1);
				String ext = getExtension(oldName);

				if (PubFun.isAllowExt(ext, fileType)) {
					if ("zip".equalsIgnoreCase(ext)) {
						String tempPath = (getServletContext().getRealPath(
								new StringBuffer(String.valueOf(baseDir))
										.append("/").append(siteAlias)
										.toString()) + "/Temp/").replaceAll(
								"//", "/");
						new File(tempPath).mkdirs();
						File oldZipFile = new File(tempPath, oldName);
						uplFile.write(oldZipFile);
						String unZipDir = tempPath
								+ oldName
										.substring(0, oldName.lastIndexOf("."));
						FileUtil.deleteFromDir(unZipDir);
						ZipUtil.unzip(tempPath + oldName, unZipDir, false);
						File[] imageFiles = new File(unZipDir).listFiles();
						Arrays.sort(imageFiles, new Comparator() {
							public int compare(Object o1, Object o2) {
								File file1 = (File) o1;
								File file2 = (File) o2;
								return -file1.getName().compareTo(
										file2.getName());
							}
						});
						for (int i = 0; i < imageFiles.length; i++) {
							oldName = imageFiles[i].getName();
							ext = getExtension(oldName);
							oldName = oldName
									.substring(0, oldName.indexOf("."));
							if (!PubFun.isAllowExt(ext, fileType))
								continue;
							if ("Image".equalsIgnoreCase(fileType)) {
								long imageID = NoUtil.getMaxID("DocID");
								int random = NumberUtil.getRandomInt(10000);
								String srcFileName = imageID + random + "."
										+ ext;
								FileUtil.copy(imageFiles[i], absolutePath
										+ srcFileName);

								ZCImageSchema image = new ZCImageSchema();
								image.setID(imageID);
								returnIDs.append(imageID + ",");
								String fileinfo = fields.get(
										uplFile.getFieldName() + "Info")
										.toString();
								image.setName(oldName);
								if (fileinfo.equals(""))
									image.setInfo(oldName);
								else {
									image.setInfo(fileinfo);
								}
								image.setOldName(oldName);
								image.setSiteID(siteID);
								image.setCatalogID(Long.parseLong(catalogID));
								image.setCatalogInnerCode(innerCode);
								image.setBranchInnerCode(User
										.getBranchInnerCode());
								image.setPath(path);
								if (("tif".equalsIgnoreCase(ext))
										|| ("tiff".equalsIgnoreCase(ext)))
									image.setFileName(imageID
											+ NumberUtil.getRandomInt(10000)
											+ ".jpg");
								else {
									image.setFileName(imageID
											+ NumberUtil.getRandomInt(10000)
											+ "." + ext);
								}
								image.setSrcFileName(srcFileName);
								image.setSuffix(ext);
								image.setWidth(0L);
								image.setHeight(0L);
								image.setOrderFlag(OrderUtil.getDefaultOrder());
								image.setFileSize(FileUtils
										.byteCountToDisplaySize(imageFiles[i]
												.length()));
								image.setHitCount(0L);
								image.setStickTime(0L);
								String ProduceTime = (String) fields
										.get("ProduceTime");
								if (XString.isNotEmpty(ProduceTime)) {
									image.setProduceTime(DateUtil
											.parse(ProduceTime));
								}
								image.setAuthor((String) fields.get("Author"));
								image.setSystem("CMS");
								image.setAddTime(new Date());
								image.setAddUser(User.getUserName());
								image.setModifyTime(new Date());
								image.setModifyUser(User.getUserName());
								image.setStatus(20L);
								trans.add(ColumnUtil.getValueFromRequest(Long
										.parseLong(catalogID), image.getID(),
										fields), OperateType.INSERT);
								trans.add(image, OperateType.INSERT);
								ArrayList imageList = null;
								try {
									imageList = ImageUtilEx.afterUploadImage(
											image, absolutePath, fields);
								} catch (Throwable e) {
									e.printStackTrace();
									if ("Unsupported Image Type".equals(e
											.getMessage()))
										errorMessage = oldName
												+ "该图片格式不支持，此图片的色彩模式可能为CMYK，请把图片的色彩模式调整为RGB模式后再上传！";
									else {
										errorMessage = e.getMessage();
									}
									LogUtil
											.warn("-------------读取图片文件"
													+ srcFileName
													+ " 失败！-------------");
									return new String[] { returnIDs.toString(),
											"1", errorMessage, taskID,
											filePath, returnPaths };
								}

								if (XString.isNotEmpty((String) fields
										.get("OtherIDs"))) {
									String[] OtherIDs = XString.splitEx(
											(String) fields.get("OtherIDs"),
											",");
									for (int j = 0; j < OtherIDs.length; j++) {
										ZCImageSchema imageClone = (ZCImageSchema) image
												.clone();
										imageClone.setID(NoUtil
												.getMaxID("DocID"));
										imageClone.setCatalogID(OtherIDs[j]);
										imageClone
												.setCatalogInnerCode(CatalogUtil
														.getInnerCode(OtherIDs[j]));
										trans.add(imageClone,
												OperateType.INSERT);
									}

								}

								fileList.add(absolutePath + srcFileName);
								fileList.addAll(imageList);

								returnValue = "0";
								errorMessage = "";
							}
						}

						oldZipFile.delete();
						FileUtil.delete(unZipDir);
					} else {
						long imageID = NoUtil.getMaxID("DocID");
						int random = NumberUtil.getRandomInt(10000);
						String srcFileName = imageID + random + "." + ext;
						uplFile.write(new File(absolutePath, srcFileName));

						ZCImageSchema image = new ZCImageSchema();
						image.setID(imageID);
						returnIDs.append(imageID + ",");
						Object obj = fields
								.get(uplFile.getFieldName() + "Name");
						String fileName = obj == null ? "无标题" : obj.toString();
						image.setName(fileName);
						image.setOldName(oldName.substring(0, oldName
								.lastIndexOf(".")));
						image.setSiteID(siteID);
						image.setCatalogID(Long.parseLong(catalogID));
						image.setCatalogInnerCode(innerCode);
						image.setBranchInnerCode(User.getBranchInnerCode());
						image.setPath(path);
						image.setStatus(20L);
						if (("tif".equalsIgnoreCase(ext))
								|| ("tiff".equalsIgnoreCase(ext)))
							image.setFileName(imageID
									+ NumberUtil.getRandomInt(10000) + ".jpg");
						else {
							image.setFileName(imageID
									+ NumberUtil.getRandomInt(10000) + "."
									+ ext);
						}

						image.setSrcFileName(srcFileName);
						image.setSuffix(ext);
						image.setWidth(0L);
						image.setHeight(0L);
						image.setOrderFlag(OrderUtil.getDefaultOrder());
						image.setFileSize(FileUtils
								.byteCountToDisplaySize(uplFile.getSize()));
						image.setHitCount(0L);
						image.setStickTime(0L);
						image.setInfo((String) fields.get(uplFile
								.getFieldName()
								+ "Info"));
						String ProduceTime = (String) fields.get("ProduceTime");
						if (XString.isNotEmpty(ProduceTime)) {
							image.setProduceTime(DateUtil.parse(ProduceTime));
						}
						image.setAuthor((String) fields.get("Author"));
						image.setSystem("CMS");
						image.setAddTime(new Date());
						image.setAddUser(User.getUserName());
						image.setModifyTime(new Date());
						image.setModifyUser(User.getUserName());
						trans.add(ColumnUtil.getValueFromRequest(Long
								.parseLong(catalogID), image.getID(), fields),
								OperateType.INSERT);
						trans.add(image, OperateType.INSERT);
						ArrayList imageList = null;
						try {
							imageList = ImageUtilEx.afterUploadImage(image,
									absolutePath, fields);
						} catch (Throwable e) {
							e.printStackTrace();
							if ("Unsupported Image Type".equals(e.getMessage()))
								errorMessage = oldName
										+ "该图片格式不支持，此图片的色彩模式可能为CMYK，请把图片的色彩模式调整为RGB模式后再上传！";
							else {
								errorMessage = e.getMessage();
							}
							LogUtil.warn("-------------读取图片文件" + srcFileName
									+ " 失败！-------------");
							return new String[] { returnIDs.toString(), "1",
									errorMessage, taskID, filePath, returnPaths };
						}

						if (XString.isNotEmpty((String) fields
								.get("OtherIDs"))) {
							String[] OtherIDs = XString.splitEx(
									(String) fields.get("OtherIDs"), ",");
							for (int j = 0; j < OtherIDs.length; j++) {
								ZCImageSchema imageClone = (ZCImageSchema) image
										.clone();
								imageClone.setID(NoUtil.getMaxID("DocID"));
								imageClone.setCatalogID(OtherIDs[j]);
								imageClone.setCatalogInnerCode(CatalogUtil
										.getInnerCode(OtherIDs[j]));
								trans.add(imageClone, OperateType.INSERT);
							}

						}

						fileList.add(absolutePath + srcFileName);
						fileList.addAll(imageList);

						returnValue = "0";
						errorMessage = "";

						if (XString.isEmpty(returnPaths))
							returnPaths = returnPaths + rootPath + path + "1_"
									+ image.getFileName();
						else {
							returnPaths = returnPaths + "," + rootPath + path
									+ "1_" + image.getFileName();
						}

						if ((m == 0) || (files.size() == 1))
							filePath = rootPath + path + "1_"
									+ image.getFileName();
						else {
							filePath = rootPath + path + "1_"
									+ image.getFileName() + "|";
						}
					}
				}
			}
			trans
					.add(new QueryBuilder(
							"update zccatalog set isdirty = 1,total = (select count(*) from zcimage where catalogID =?) where ID =?",
							catalog.getID(), catalog.getID()));
		}

		try {
			if ((XString.isNotEmpty(catalog.getIndexTemplate()))
					|| (XString.isNotEmpty(catalog.getListTemplate()))
					|| (XString.isNotEmpty(catalog.getDetailTemplate()))) {
				Publisher p = new Publisher();
				p.publishCatalog(catalog.getID(), false, true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Deploy d = new Deploy();
		trans.add(d.getJobs(siteID, fileList, "copy"), OperateType.INSERT);

		trans.commit();
		int t = returnIDs.lastIndexOf(",");
		if (t != -1) {
			returnIDs.deleteCharAt(t);
		}
		return new String[] { returnIDs.toString(), returnValue, errorMessage,
				taskID, filePath, returnPaths };
	}

	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
}

/*
 * com.xdarkness.cms.resource.uploader.SimpleUploaderServlet JD-Core Version: 0.6.0
 */