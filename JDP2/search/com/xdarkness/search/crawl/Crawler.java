package com.xdarkness.search.crawl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.lang.ArrayUtils;

import com.xdarkness.cms.api.ArticleAPI;
import com.xdarkness.cms.datachannel.Deploy;
import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.framework.Config;
import com.xdarkness.framework.extend.ExtendManager;
import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.messages.LongTimeTask;
import com.xdarkness.framework.script.EvalException;
import com.xdarkness.framework.script.ScriptEngine;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.DateUtil;
import com.xdarkness.framework.util.Filter;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.NumberUtil;
import com.xdarkness.framework.util.RegexParser;
import com.xdarkness.framework.util.ServletUtil;
import com.xdarkness.framework.util.XString;
import com.xdarkness.platform.pub.ImageUtilEx;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.schema.ZCArticleSchema;
import com.xdarkness.schema.ZCArticleSet;
import com.xdarkness.schema.ZCImageSchema;
import com.xdarkness.schema.ZCImageSet;
import com.xdarkness.search.DocumentList;
import com.xdarkness.search.SearchUtil;
import com.xdarkness.search.WebDocument;

/**
 * 
 * @author Darkness Create on Nov 19, 2010 1:59:27 PM
 * @version 1.0
 */
public class Crawler {
	private CrawlConfig config;
	private DocumentList list;
	private int startLevel;
	private FileDownloader fileDownloader = new FileDownloader();

	private UrlExtracter extracter = new UrlExtracter();
	private int currentLevel;
	private int currentLevelCount;
	private int currentLevelDownloadedCount;
	private Filter retryFilter;
	public LongTimeTask task;
	Pattern framePattern = Pattern.compile("<iframe.*?<\\/iframe>",
			Pattern.CASE_INSENSITIVE);

	Pattern stylePattern = Pattern.compile("<style.*?><\\/style>",
			Pattern.CASE_INSENSITIVE);

	Pattern scriptPattern = Pattern.compile("<script.*?<\\/script>",
			Pattern.CASE_INSENSITIVE);

	Pattern linkPattern = Pattern.compile("<a .*?>(.*?)<\\/a>",
			Pattern.CASE_INSENSITIVE);

	Pattern tagPattern = Pattern.compile("<.*?>", Pattern.CASE_INSENSITIVE);
	private ScriptEngine se;
	private double total = 0.0D;

	public LongTimeTask getTask() {
		return this.task;
	}

	public Crawler() {
		this(null);
	}

	public Crawler(LongTimeTask ltt) {
		this.task = ltt;
		if (ltt == null)
			this.task = LongTimeTask.createEmptyInstance();
	}

	/**
	 * 获取当前抓取目录
	 * 
	 * @return
	 */
	private String getCurrentWebGatherDir() {
		String path = Config.getContextRealPath()
				+ CrawlConfig.getWebGatherDir();
		if ((!(path.endsWith("/"))) && (!(path.endsWith("\\")))) {
			path = path + "/";
		}
		return path + this.config.getID() + "/";
	}

	/**
	 * 初始化文档列表
	 */
	private void initDocumentList() {
		if (this.list == null) {
			String path = getCurrentWebGatherDir();
			File f = new File(path);
			if (!(f.exists())) {
				f.mkdirs();
			}
			this.list = new DocumentList(path);
		}
		this.list.setCrawler(this);
	}

	/**
	 * 配置文件下载器
	 */
	private void configFileDownloader() {
		this.fileDownloader
				.setDenyExtension(".gif.jpg.jpeg.swf.bmp.png.js.wmv.css.ico.avi.mpg.mpeg.mp3.mp4.wma.rm.rmvb.exe.tar.gz.zip.rar");
		this.fileDownloader.setThreadCount(this.config.getThreadCount());
		this.fileDownloader.setTimeout(this.config.getTimeout() * 1000);

		if (this.config.isProxyFlag()) {
			this.fileDownloader.setProxyFlag(this.config.isProxyFlag());
			this.fileDownloader.setProxyHost(this.config.getProxyHost());
			this.fileDownloader
					.setProxyPassword(this.config.getProxyPassword());
			this.fileDownloader
					.setProxyUserName(this.config.getProxyUserName());
			this.fileDownloader.setProxyPort(this.config.getProxyPort());
		} else if ("Y".equalsIgnoreCase(Config.getValue("Proxy.IsUseProxy"))) {
			this.fileDownloader.setProxyFlag(true);
			this.fileDownloader.setProxyHost(Config.getValue("Proxy.Host"));
			this.fileDownloader.setProxyPassword(Config
					.getValue("Proxy.Password"));
			this.fileDownloader.setProxyUserName(Config
					.getValue("Proxy.UserName"));
			this.fileDownloader.setProxyPort(Integer.parseInt(Config
					.getValue("Proxy.Port")));
		}
	}

	/**
	 * 
	 */
	private void dealCopyImage() {
		if (this.config.isCopyImageFlag()) {
			int maxPage = this.config.getMaxPageCount();
			this.config.setMaxPageCount(-1);
			this.fileDownloader
					.setDenyExtension(".html.htm.jsp.php.asp.shtml.swf.js.css.ico.avi.mpg.mpeg.mp3.mp4.wma.wmv.rm.rmvb.exe.tar.gz.zip.rar");
			this.currentLevel += 1;
			this.task.setCurrentInfo("正在处理第" + (this.currentLevel + 1)
					+ "层级URL");
			String[] urls = this.config.getUrlLevels();
			urls = (String[]) ArrayUtils
					.add(
							urls,
							"${A}.gif\n${A}.jpg\n${A}.jpeg\n${A}.png\n${A}.bmp\n${A}.GIF\n${A}.JPG\n${A}.JPEG\n${A}.PNG\n${A}.BMP");
			this.config.setUrlLevels(urls);
			dealOneLevel();
			this.config.setMaxPageCount(maxPage);
			this.fileDownloader
					.setDenyExtension(".gif.jpg.jpeg.swf.bmp.png.js.wmv.css.ico.avi.mpg.mpeg.mp3.mp4.wma.rm.rmvb.exe.tar.gz.zip.rar");
		}
	}

	/**
	 * 开始抓取
	 * 
	 * @return
	 */
	public DocumentList crawl() {

		initDocumentList();

		try {
			configFileDownloader();

			prepareScript();

			dealAllLevel();

			if (this.task.checkStop()) {
				writeArticle();
			} else {
				dealCopyImage();
				retryWithFilter();
				writeArticle();
			}
		} catch (Exception e1) {
			LogUtil.info("抓取过程中发生重大错误，文章未生成！");
			e1.printStackTrace();
			return null;
		} finally {
			this.list.save();
			this.list.close();
		}
		return this.list;
	}

	/**
	 * 处理所有层级URL
	 */
	private void dealAllLevel() {

		for (int i = 0; (i < this.config.getUrlLevels().length)
				&& (i <= this.config.getMaxLevel()); i++) {
			try {
				if ((i >= this.startLevel) || (this.task.checkStop())) {
					this.task.setCurrentInfo("正在处理第" + (i + 1) + "层级URL");
					this.currentLevel = i;
					dealOneLevel();
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 文档采集的目的栏目是否存在
	 * 
	 * @return
	 */
	private boolean isAimCatalogExist() {
		if (XString.isEmpty(CatalogUtil
				.getSiteID(this.config.getCatalogID()))) {
			LogUtil.getLogger().warn(
					"文档采集的目的栏目不存在：ID=" + this.config.getCatalogID());
			return false;
		}
		return true;
	}

	/**
	 * 获取默认图片的字典id
	 * 
	 * @return
	 */
	private String getImageCatelogId() {

		QueryBuilder imageQB = new QueryBuilder(
				"select id from ZCCatalog where type=4 and siteid=?",
				CatalogUtil.getSiteID(this.config.getCatalogID()));

		return imageQB.executeString();
	}
	
	/**
	 * 处理文章中的内容
	 * @param content
	 * @return
	 */
	private String dealArticleContent(String content) {
		RegexParser[] filters = this.config.getFilterBlocks();
		content = content.trim();
//		while (rp.match()) {
//			String html = rp.getMapx().getString(
//					"Content");
//			content = content + html;
//		}
		if (this.config.isCleanLinkFlag()) {
			content = this.framePattern
					.matcher(content).replaceAll("");
			content = this.stylePattern
					.matcher(content).replaceAll("");
			content = this.scriptPattern.matcher(
					content).replaceAll("");
			content = this.linkPattern.matcher(content)
					.replaceAll("$1");
		}
		if (filters != null) {
			for (int k = 0; k < filters.length; ++k) {
				content = filters[k].replace(content, "");
			}
		}
		
		return content;
	}

	/**
	 * 生成文章
	 */
	public void writeArticle() {
		LogUtil.getLogger().info("开始生成文章");
		initDocumentList();
		
		if (this.config.getType() == 1) {
			
			if(!isAimCatalogExist()){
				return;
			}
			
			String imageCatalogID = getImageCatelogId();
			String sitePath = SiteUtil.getAbsolutePath(CatalogUtil
					.getSiteID(this.config.getCatalogID()));
			String imagePath = "upload/Image/"
					+ CatalogUtil.getAlias(imageCatalogID) + "/";

			RegexParser rp = this.config.getTemplate("Ref1");
			RegexParser[] filters = this.config.getFilterBlocks();
			this.list.moveFirst();
			WebDocument doc = null;
			int cSuccess = 0;
			int cFailure = 0;
			int cLost = 0;

			boolean publishDateFlag = false;
			ZCArticleSet set = new ZCArticleSet();
			while ((doc = this.list.next()) != null) {
				if (this.task.checkStop()) {
					return;
				}
				if (doc.getLevel() != this.config.getUrlLevels().length - 1) {
					continue;
				}

				// TODO 特殊网站处理
//				List<String> urlList = new ArrayList<String>();
//				String url = doc.getUrl();
//				//if(url.indexOf("viewthread.php?tid=") == -1){// 不是文章
//					//continue;
//		        //}
//
//				//if(url.indexOf("page%3D1") == -1 && url.indexOf("page=1") == -1) {// 非第一页，为回复，不处理
//	              //  continue;
//	            //}
//				
//				Pattern patt = Pattern.compile("tid=(\\d*)", Pattern.CASE_INSENSITIVE);
//				Matcher m = patt.matcher(url);
//				int lastEndIndex = 0;
//				if ( m.find(lastEndIndex)) {
//					 lastEndIndex = m.end();
//				}
//				String tid = "";
//				try {
//					tid = m.group(1);
//				} catch (Exception e1) {
//					e1.printStackTrace();
//					LogUtil.debug("url:"+url+"非匹配");
//					continue;
//				}
//				synchronized (this) {
//					if(urlList.contains(tid))
//						continue;
//					urlList.add(tid);	
//				}
				
				int percent = (100 - this.task.getPercent())
						* (cSuccess + cFailure + cLost) / this.list.size();
				this.task.setPercent(this.task.getPercent() + percent);
				
				if ((doc.isTextContent()) && (doc.getContent() != null)) {
					String text = doc.getContentText();
//					rp.setText(text);
					text = text.replaceAll("\r", "").replace("\n", "");
					Mapx map = rp.getMatchedMap(text);
					
					if (map != null) {
						String title = map.getString("Title");
						String content = map.getString("Content");
						String author = map.getString("Author");
						String source = map.getString("Source");
						String strDate = map.getString("PublishDate");
						Date publishDate = doc.getLastmodifiedDate();
						if ((XString.isNotEmpty(strDate))
								&& (XString.isNotEmpty(this.config
										.getPublishDateFormat()))) {
							try {
								strDate = DateUtil
										.convertChineseNumber(strDate);
								publishDate = DateUtil.parse(strDate,
										this.config.getPublishDateFormat());
							} catch (Exception e) {
								this.task.addError("日期" + strDate + "不符合指定格式"
										+ doc.getUrl());
							}
							publishDateFlag = true;
						}
						if (publishDate.getTime() > System.currentTimeMillis()) {
							publishDate = new Date();
						}
						ArticleAPI api = new ArticleAPI();
						try {
							ZCArticleSchema article = new ZCArticleSchema();
							
							// 标题或内容为空，为未匹配
							if (XString.isEmpty(title) || XString.isEmpty(content)) {
								++cLost;
								logCurrentInfo(cSuccess, cFailure, cLost);
								continue;
							}
							
							title = this.tagPattern.matcher(title).replaceAll("").trim();
							article.setTitle(title);
							content = dealArticleContent(content);
							
							while (rp.match()) {
								String html = rp.getMapx().getString(
										"Content");
								content = content + html;
							}
							if (this.config.isCleanLinkFlag()) {
								content = this.framePattern
										.matcher(content).replaceAll("");
								content = this.stylePattern
										.matcher(content).replaceAll("");
								content = this.scriptPattern.matcher(
										content).replaceAll("");
								content = this.linkPattern.matcher(content)
										.replaceAll("$1");
							}
							if (filters != null) {
								for (int k = 0; k < filters.length; k++) {
									content = filters[k].replace(content,
											"");
								}
							}

							String str = dealImage(content, doc.getUrl(),
									sitePath, imagePath, imageCatalogID);
							article.setContent(str);
								
							if (XString.isNotEmpty(author)) {
								article.setAuthor(author);
							}
							if (XString.isNotEmpty(source)) {
								article.setReferName(source);
							}
							article.setReferURL(doc.getUrl());
							article.setPublishDate(publishDate);
							article.setCatalogID(this.config.getCatalogID());
							article.setBranchInnerCode("0001");
							article.setProp2("FromWeb");

							if (ExtendManager.hasAction("FromWeb.BeforeSave")) {
								ExtendManager.executeAll("FromWeb.BeforeSave",
										new Object[] { article });
							}

							Date date = (Date) new QueryBuilder(
									"select PublishDate from ZCArticle where ReferURL=? and CatalogID=?",
									doc.getUrl(), this.config.getCatalogID())
									.executeOneValue();
							if (date != null) {
								if (date.getTime() < doc.getLastDownloadTime()) {
									QueryBuilder qb = new QueryBuilder(
											"update ZCArticle set Title=?,Content=? where CatalogID=? and ReferURL=?");
									qb.add(article.getTitle());
									qb.add(article.getContent());
									qb.add(this.config.getCatalogID());
									qb.add(doc.getUrl());
									qb.executeNoQuery();
								}
								cSuccess++;
								//logCurrentInfo(cSuccess, cFailure, cLost);
							}else {
				                api.setSchema(article);
				                set.add(article);
				                if (api.insert() > 0L)
				                  ++cSuccess;
				                else
				                  ++cFailure;
							}
						} catch (Exception e) {
							cFailure++;
							e.printStackTrace();
						}
					} else {
						LogUtil.getLogger().info("未能匹配" + doc.getUrl());
						this.task.addError("未能匹配" + doc.getUrl());
						cLost++;
					}
//					this.task.setCurrentInfo("正在转换文档, <font class='green'>"
//							+ cSuccess + "</font> 个成功, <font class='red'>"
//							+ cFailure + "</font> 个失败, <font class='green'>"
//							+ cLost + "</font> 个未匹配");
				}else {
					++cLost;
				}
			}
			logCurrentInfo(cSuccess, cFailure, cLost);
			if (!publishDateFlag) {
				String[] lastURLs = this.config.getUrlLevels()[(this.config
						.getUrlLevels().length - 1)].split("\\\n", -1);
				if (lastURLs.length != 1) {
					return;
				}
				RegexParser rpUrl = new RegexParser(lastURLs[0]);
				boolean numberFlag = true;
				for (int i = 0; i < set.size(); i++) {
					String url = set.get(i).getReferURL();
					rpUrl.setText(url);
					if (rpUrl.match()) {
						String v = rpUrl.getMapx().getString("SortID");
						set.get(i).setProp2(v);
						if (!NumberUtil.isLong(v)) {
							numberFlag = false;
						}
					}
				}
				set.sort("Prop2", "asc", numberFlag);
				for (int i = set.size() - 1; i >= 0; i--) {
					set.get(i).setOrderFlag(OrderUtil.getDefaultOrder());
					set.get(i).setProp2(null);
				}
				set.update();
			}
		}
		LogUtil.getLogger().info("生成文章结束");
	}
	/**
	 * 记录当前任务转换文档日志
	 * @param cSuccess
	 * @param cFailure
	 * @param cLost
	 */
	private void logCurrentInfo(int cSuccess, int cFailure, int cLost) {
		this.task.setCurrentInfo("正在转换文档, <font color='green'>"
				+ cSuccess
				+ "</font> 个成功, <font color='red'>"
				+ cFailure
				+ "</font> 个失败, <font color='green'>"
				+ cLost + "</font> 个未匹配");
	}
	
	/**
	 * 处理文档内容中的图片
	 * @param content
	 * @param baseUrl
	 * @param sitePath
	 * @param imagePath
	 * @param imageCatalogID
	 * @return
	 */
	public String dealImage(String content, String baseUrl, String sitePath,
			String imagePath, String imageCatalogID) {
		Matcher m = SearchUtil.resourcePattern1.matcher(content);
		int lastEndIndex = 0;
		StringBuffer sb = new StringBuffer();
		while (m.find(lastEndIndex)) {
			String url = m.group(2);
			String ext = ServletUtil.getUrlExtension(url);
			if ((SearchUtil.isRightUrl(url)) && (XString.isNotEmpty(ext))
					&& (".gif.jpg.jpeg.bmp.png".indexOf(ext) >= 0)) {
				String fullUrl = SearchUtil.normalizeUrl(url, baseUrl);
				WebDocument tdoc = this.list.get(fullUrl);
				if ((tdoc != null) && (!tdoc.isTextContent())) {
					byte[] data = tdoc.getContent();
					sb.append(content.substring(lastEndIndex, m.start()));
					
					String imageFilePath = content.substring(lastEndIndex, m.end());

					try {
						if(data != null) {
							imageFilePath = saveImage(data, sitePath, imagePath,
								imageCatalogID, ext, fullUrl);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}	
					
					sb.append(XString.replaceEx(m.group(0), url,
							imageFilePath));

//					sb.append(content.substring(lastEndIndex, m.start()));

				} else {
					sb.append(content.substring(lastEndIndex, m.end()));
				}
			} else {
				sb.append(content.substring(lastEndIndex, m.end()));
			}
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));
		content = sb.toString();

		sb = new StringBuffer();
		m = SearchUtil.resourcePattern2.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String url = m.group(3);
			String ext = ServletUtil.getUrlExtension(url);
			if ((SearchUtil.isRightUrl2(url)) && (XString.isNotEmpty(ext))
					&& (".gif.jpg.jpeg.bmp.png".indexOf(ext) >= 0)) {
				String fullUrl = SearchUtil.normalizeUrl(url, baseUrl);
				WebDocument tdoc = this.list.get(fullUrl);
				if ((tdoc != null) && (!tdoc.isTextContent())) {
					byte[] data = tdoc.getContent();
					sb.append(content.substring(lastEndIndex, m.start()));
					String imageFilePath = saveImage(data, sitePath, imagePath,
							imageCatalogID, ext, fullUrl);
					sb.append(XString.replaceEx(m.group(0), url,
							imageFilePath));
				} else {
					sb.append(content.substring(lastEndIndex, m.end()));
				}
			} else {
				sb.append(content.substring(lastEndIndex, m.end()));
			}
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));
		return sb.toString();
	}
	/**
	 * 保存图片至本地磁盘
	 * @param data
	 * @param path1
	 * @param path2
	 * @param catalogID
	 * @param ext
	 * @param imageURL
	 * @return
	 */
	public static String saveImage(byte[] data, String path1, String path2,
			String catalogID, String ext, String imageURL) {
		ZCImageSchema image = new ZCImageSchema();
		image.setSourceURL(imageURL);
		boolean flag = true;
		ZCImageSet set = image.query();
		
		path2 += DateUtil.getCurrentDateTime("yyyy_MM_dd_HH") + "/";
		String timeDirString = path1 + path2;
		
		File timeDir = new File(timeDirString);
		if(!timeDir.exists()) {
		    timeDir.mkdir();
		}
		
		if (set.size() > 0) {
			image = set.get(0);
//			File f = new File(path1 + path2 + image.getSrcFileName());
//			if (f.exists()) {
//				if (f.length() == data.length) {
//					flag = false;
//				}
//				FileUtil.writeByte(f, data);
//			}
			File f = new File(path1 + image.getPath() + image.getSrcFileName());
			if (f.exists()) {// if the file is exist,ingore
//				if (f.length() == data.length) {
					flag = false;
//				}
//				FileUtil.writeByte(f, data);
			} else {
				FileUtil.writeByte(f, data);
			}
		} else {
			long imageID = NoUtil.getMaxID("DocID");
			int random = NumberUtil.getRandomInt(10000);

//			String srcFileName =  imageID + "_" +  random + ext;
//			FileUtil.writeByte(timeDirString + srcFileName, data);
			String srcFileName = imageID + random + ext;
			FileUtil.writeByte(path1 + path2 + srcFileName, data);
			image.setID(imageID);
			image.setCatalogID(catalogID);
			image.setCatalogInnerCode(CatalogUtil.getInnerCode(catalogID));
			image.setName((imageID + random) + "");
			image.setOldName((imageID + random) + "");
			image.setSiteID(CatalogUtil.getSiteID(catalogID));
			image.setPath(path2);
			image
					.setFileName(imageID + NumberUtil.getRandomInt(10000)
							+ ".jpg");
			image.setSrcFileName(srcFileName);
			image.setSuffix(ext);
			image.setCount(1L);
			image.setWidth(0L);
			image.setHeight(0L);
			try {
				BufferedImage img = ImageIO.read(new File(path1 + path2
						+ srcFileName));
				image.setWidth(img.getWidth());
				image.setHeight(img.getHeight());
			} catch (IOException e) {
				e.printStackTrace();
			}
			image.setHitCount(0L);
			image.setStickTime(0L);
			image.setAuthor("Crawler");
			image.setSystem("CMS");
			image.setOrderFlag(OrderUtil.getDefaultOrder());
			image.setAddTime(new Date());
			image.setAddUser("SYS");
			image.setSiteID(CatalogUtil.getSiteID(image.getCatalogID()));
			image.insert();
		}
		if (flag) {
			ArrayList imageList = null;
			try {
				imageList = ImageUtilEx.afterUploadImage(image, path1 + path2);
			} catch (Throwable e) {
				e.printStackTrace();
				return "";
			}
			Deploy d = new Deploy();
			d.addJobs(image.getSiteID(), imageList);

			return (Config.getContextPath() + Config.getValue("UploadDir")
					+ "/" + SiteUtil.getAlias(CatalogUtil.getSiteID(catalogID))
					+ "/" + image.getPath() + "1_" + image.getFileName())
					.replaceAll("//", "/");
		}
		return (Config.getContextPath() + Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(CatalogUtil.getSiteID(catalogID)) + "/"
				+ image.getPath() + image.getSrcFileName()).replaceAll("//",
				"/");
	}

	public void retryWithFilter() {
		if (this.retryFilter != null) {
			LogUtil.getLogger().info("Retry Downloading URL ........");
			WebDocument doc = this.list.next();
			while (doc != null) {
				if (this.retryFilter.filter(doc)) {
					FileDownloader.downloadOne(doc);
					if (this.list.hasContent(doc.getUrl())) {
						this.list.put(doc);
					}
				}
				doc = this.list.next();
			}
		}
	}

	private void prepareScript() throws EvalException {
		StringBuffer sb = new StringBuffer();
		if (XString.isNotEmpty(this.config.getScript())) {
			this.se = new ScriptEngine(this.config.getLanguage());
			this.se.importPackage("com.xdarkness.framework.data");
			this.se.importPackage("com.xdarkness.framework.utility");
			if (this.config.getLanguage() == 1) {
				sb.append("StringBuffer _Result = new StringBuffer();\n");
				sb.append("void write(String str){_Result.append(str);}\n");
				sb
						.append("void writeln(String str){_Result.append(str+\"\\n\");}\n");
				sb.append(this.config.getScript());
				sb.append("\nreturn _Result.toString();\n");
			} else {
				sb.append("var _Result = [];\n");
				sb.append("function write(str){_Result.push(str);}\n");
				sb
						.append("function writeln(str){_Result.push(str+\"\\n\");}\n");
				sb.append(this.config.getScript());
				sb.append("\nreturn _Result.join('');\n");
			}
			this.se.compileFunction("_EvalScript", sb.toString());
		}
	}

	public void executeScript(String when, CrawlScriptUtil util) {
		//this.currentLevelCount = this.list.size();
		this.currentLevelDownloadedCount += 1;
		if (when.equalsIgnoreCase("after")) {
			this.task.setCurrentInfo("正在抓取" + util.getCurrentUrl());
		}
		if (this.total == 0.0D) {
			for (int i = 0; i < this.config.getUrlLevels().length + 1; i++) {
				this.total += (i + 1) * (i + 1) * 400;
			}
		}
		double t = (this.currentLevel + 1) * (this.currentLevel + 1) * 400;
		t = t
				/ this.total
				+ (this.currentLevel + 1)
				* (this.currentLevel + 2)
				/ this.total
				* (this.currentLevelDownloadedCount * 1.0D / this.currentLevelCount);
		int percent = new Double(t * 100.0D).intValue();
		this.task.setPercent(percent);
		if (XString.isNotEmpty(this.config.getScript())) {
			this.se.setVar("Util", util);
			this.se.setVar("When", when);
			this.se.setVar("Level", new Integer(this.currentLevel));
			try {
				this.se.executeFunction("_EvalScript");
			} catch (EvalException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 处理当前层级URL
	 */
	private void dealOneLevel() {
		String[] arr = this.config.getUrlLevels()[this.currentLevel].trim()
				.split("\n");
		this.task.setCurrentInfo("正在生成第" + (this.currentLevel + 1) + "层级URL");
		this.currentLevelCount = 0;
//		if (this.currentLevel != 0)
//			this.extracter.extract(this.list, this.currentLevel,
//					this.fileDownloader);
//		else {
//			for (int i = 0; i < arr.length; i++) {
//				String url = arr[i];
//				if (StringUtil.isEmpty(url)) {
//					continue;
//				}
//				if (!this.list.containsKey(url)) {
//					WebDocument doc = new WebDocument();
//					doc.setUrl(url);
//					doc.setLevel(this.currentLevel);
//					this.list.put(doc);
//				}
//			}
//		}
//		this.currentLevelCount = this.list.size();
		if (this.currentLevel == 0) {// 是第一层级URL，直接加入文档列表
			for (int i = 0; i < arr.length; ++i) {
				String url = arr[i];
				if (XString.isEmpty(url)) {
					continue;
				}
				if (!(this.list.containsKey(url))) {
					WebDocument doc = new WebDocument();
					doc.setUrl(url);
					doc.setLevel(0);//doc.setLevel(this.currentLevel);
					this.list.put(doc);
				}
			}
		} else { // 从文档列表当前层级的上层级文档内容中提取出当前文档URL
			
			this.extracter.extract(this.list, this.currentLevel, this.fileDownloader.getAllowExtension(), this.fileDownloader.getDenyExtension());
		}
		this.currentLevelCount = getCurrentLevelCountFromDocumentList();
		this.fileDownloader.downloadList(this.list, this.currentLevel);
	}
	/**
	 * 获取当前层级文档总数
	 * @return
	 */
	private int getCurrentLevelCountFromDocumentList() {
//		int total = 0;
//		for(int i=0;i<this.list.size();i++){
//			
//		}
		// TODO 该方法有问题，需修改
		return this.list.size();
	}
	public long getTaskID() {
		return this.config.getID();
	}

	public int getStartLevel() {
		return this.startLevel;
	}

	public void setStartLevel(int startLevel) {
		this.startLevel = startLevel;
	}

	public Filter getRetryFilter() {
		return this.retryFilter;
	}

	public void setRetryFilter(Filter retryFilter) {
		this.retryFilter = retryFilter;
	}

	public DocumentList getList() {
		return this.list;
	}

	public void setList(DocumentList list) {
		this.list = list;
	}

	public FileDownloader getFileDownloader() {
		return this.fileDownloader;
	}

	public CrawlConfig getConfig() {
		return this.config;
	}

	public void setConfig(CrawlConfig config) {
		this.config = config;
	}
}