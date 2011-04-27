package com.xdarkness.cms.resource.uploader;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.cms.template.HtmlNameParser;
import com.xdarkness.cms.template.HtmlNameRule;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.schema.ZCCatalogSchema;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class ZUploaderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String IMAGE = "Image";
	public static final String VIDEO = "Video";
	public static final String AUDIO = "Audio";
	public static final String ATTACH = "Attach";
	private static String baseDir;
	private static boolean debug = false;

	public void init() throws ServletException {
		debug = new Boolean(getInitParameter("debug")).booleanValue();
		baseDir = Config.getValue("UploadDir");
		if (baseDir == null)
			baseDir = "";
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (XString.isEmpty(baseDir)) {
			baseDir = Config.getValue("UploadDir");
		}
		System.out.println("--- BEGIN DOPOST ---");
		response.setContentType("text/html; charset=" + Constant.GlobalCharset);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

		FileItemFactory fileFactory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fileFactory);
		upload.setHeaderEncoding("UTF-8");
		upload.setSizeMax(2097152000L);
		try {
			List items = upload.parseRequest(request);
			Mapx fields = new Mapx();
			Mapx files = new Mapx();
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField()) {
					fields.put(item.getFieldName(), item.getString("UTF-8"));
				} else {
					String OldFileName = item.getName();
					long size = item.getSize();
					if (((OldFileName == null) || (OldFileName.equals("")))
							&& (size == 0L)) {
						continue;
					}
					System.out.println("-----UploadFileName:-----"
							+ OldFileName);
					files.put(item.getFieldName(), item);
				}

			}

			String CatalogID = fields.getString("CatalogID");
			String AbsolutePath = fields.getString("AbsolutePath");
			String FileType = fields.getString("FileType");

			ZCCatalogSchema catalog = new ZCCatalogSchema();
			String SiteAlias = "";
			String PathData = "";
			long SiteID = 0L;

			if (CatalogID == null) {
				if ("Image".equals(FileType))
					CatalogID = new QueryBuilder(
							"select ID from ZCCatalog  where type='4' and Name ='默认图片'  and siteid=?",
							ApplicationPage.getCurrentSiteID()).executeString();
				else if ("Video".equals(FileType))
					CatalogID = new QueryBuilder(
							"select ID from ZCCatalog  where type='5' and Name ='默认视频' and siteid=?",
							ApplicationPage.getCurrentSiteID()).executeString();
				else if ("Attach".equals(FileType))
					CatalogID = new QueryBuilder(
							"select ID from ZCCatalog  where type='7' and Name ='默认附件' and siteid=?",
							ApplicationPage.getCurrentSiteID()).executeString();
				else if ("Audio".equals(FileType)) {
					CatalogID = new QueryBuilder(
							"select ID from ZCCatalog  where type='6' and Name ='默认音频' and siteid=?",
							ApplicationPage.getCurrentSiteID()).executeString();
				}
			}
			catalog.setID(CatalogID);
			if (!catalog.fill()) {
				System.out.println("没有找到上传库");
			}
			SiteID = catalog.getSiteID();
			SiteAlias = SiteUtil.getAlias(SiteID);
			HtmlNameParser nameParser = new HtmlNameParser(null, catalog
					.toDataRow(), null, catalog.getListNameRule());
			HtmlNameRule h = nameParser.getNameRule();
			PathData = "upload/" + FileType + "/" + h.getDirPath() + "/";
			AbsolutePath = (getServletContext().getRealPath(
					new StringBuffer(String.valueOf(baseDir)).append("/")
							.append(SiteAlias).append("/").append(PathData)
							.toString()) + "/").replaceAll("//", "/");
			System.out.println("文件上传path：" + PathData);
			System.out.println("文件上传AbsolutePath：" + AbsolutePath);

			if ((!"".equals(AbsolutePath)) && (AbsolutePath != null)) {
				File dir = new File(AbsolutePath);
				if (!dir.exists()) {
					dir.mkdirs();
				}
			}
			fields.put("CatalogID", CatalogID);
			fields.put("SiteID", SiteID);
			fields.put("SiteAlias", SiteAlias);
			fields.put("PathData", PathData);
			fields.put("AbsolutePath", AbsolutePath);
			fields.put("tempPath", (getServletContext().getRealPath(
					new StringBuffer(String.valueOf(baseDir)).append("/")
							.append(SiteAlias).toString()) + "/Temp/")
					.replaceAll("//", "/"));
			String repeat = (String) fields.get("Repeat");
			if (!"Image".equals(FileType)) {
				if ("Video".equals(FileType))
					UploadVideo.upload(files, fields);
				else if ("Attach".equals(FileType)) {
					if ("1".equals(repeat))
						UploadAttachment.repeatUpload(files, fields);
					else
						UploadAttachment.upload(files, fields);
				} else if ("Audio".equals(FileType))
					if ("1".equals(repeat))
						UploadAudio.repeatUpload(files, fields);
					else
						UploadAudio.upload(files, fields);
			}
		} catch (Exception ex) {
			if (debug) {
				ex.printStackTrace();
			}
		}
		out.flush();
		out.close();

		if (debug)
			System.out.println("--- END DOPOST ---");
	}

	public static String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
}

/*
 * com.xdarkness.cms.resource.uploader.ZUploaderServlet JD-Core Version: 0.6.0
 */