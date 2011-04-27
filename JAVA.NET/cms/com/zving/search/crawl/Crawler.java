 package com.zving.search.crawl;
 
 import com.zving.cms.api.ArticleAPI;
 import com.zving.cms.datachannel.Deploy;
 import com.zving.cms.pub.CatalogUtil;
 import com.zving.cms.pub.SiteUtil;
 import com.zving.framework.Config;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.extend.ExtendManager;
 import com.zving.framework.messages.LongTimeTask;
 import com.zving.framework.script.EvalException;
 import com.zving.framework.script.ScriptEngine;
 import com.zving.framework.utility.DateUtil;
 import com.zving.framework.utility.FileUtil;
 import com.zving.framework.utility.Filter;
 import com.zving.framework.utility.LogUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.NumberUtil;
 import com.zving.framework.utility.RegexParser;
 import com.zving.framework.utility.ServletUtil;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.pub.ImageUtilEx;
 import com.zving.platform.pub.NoUtil;
 import com.zving.platform.pub.OrderUtil;
 import com.zving.schema.ZCArticleSchema;
 import com.zving.schema.ZCArticleSet;
 import com.zving.schema.ZCImageSchema;
 import com.zving.schema.ZCImageSet;
 import com.zving.search.DocumentList;
 import com.zving.search.SearchUtil;
 import com.zving.search.WebDocument;
 import java.awt.image.BufferedImage;
 import java.io.File;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 import javax.imageio.ImageIO;
 import org.apache.commons.lang.ArrayUtils;
 import org.apache.commons.logging.Log;
 
 public class Crawler
 {
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
   Pattern framePattern = Pattern.compile("<iframe.*?<\\/iframe>", 34);
 
   Pattern stylePattern = Pattern.compile("<style.*?><\\/style>", 34);
 
   Pattern scriptPattern = Pattern.compile("<script.*?<\\/script>", 34);
 
   Pattern linkPattern = Pattern.compile("<a .*?>(.*?)<\\/a>", 34);
 
   Pattern tagPattern = Pattern.compile("<.*?>", 34);
   private ScriptEngine se;
   private double total = 0.0D;
 
   public LongTimeTask getTask()
   {
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
 
   private void prepareList()
   {
     if (this.list == null) {
       String path = Config.getContextRealPath() + CrawlConfig.getWebGatherDir();
       if ((!path.endsWith("/")) && (!path.endsWith("\\"))) {
         path = path + "/";
       }
       path = path + this.config.getID() + "/";
       File f = new File(path);
       if (!f.exists()) {
         f.mkdirs();
       }
       this.list = new DocumentList(path);
     }
   }
 
   public DocumentList crawl() {
     prepareList();
     try {
       this.list.setCrawler(this);
       this.fileDownloader
         .setDenyExtension(".gif.jpg.jpeg.swf.bmp.png.js.wmv.css.ico.avi.mpg.mpeg.mp3.mp4.wma.rm.rmvb.exe.tar.gz.zip.rar");
       this.fileDownloader.setThreadCount(this.config.getThreadCount());
       this.fileDownloader.setTimeout(this.config.getTimeout() * 1000);
 
       if (this.config.isProxyFlag()) {
         this.fileDownloader.setProxyFlag(this.config.isProxyFlag());
         this.fileDownloader.setProxyHost(this.config.getProxyHost());
         this.fileDownloader.setProxyPassword(this.config.getProxyPassword());
         this.fileDownloader.setProxyUserName(this.config.getProxyUserName());
         this.fileDownloader.setProxyPort(this.config.getProxyPort());
       } else if ("Y".equalsIgnoreCase(Config.getValue("Proxy.IsUseProxy"))) {
         this.fileDownloader.setProxyFlag(true);
         this.fileDownloader.setProxyHost(Config.getValue("Proxy.Host"));
         this.fileDownloader.setProxyPassword(Config.getValue("Proxy.Password"));
         this.fileDownloader.setProxyUserName(Config.getValue("Proxy.UserName"));
         this.fileDownloader.setProxyPort(Integer.parseInt(Config.getValue("Proxy.Port")));
       }
 
       prepareScript();
       for (int i = 0; (i < this.config.getUrlLevels().length) && (i <= this.config.getMaxLevel()); ++i) {
         try {
           if ((i >= this.startLevel) || (this.task.checkStop()))
           {
             this.task.setCurrentInfo("正在处理第" + (i + 1) + "层级URL");
             this.currentLevel = i;
             dealOneLevel();
           }
         } catch (Throwable e) {
           e.printStackTrace();
         }
       }
       if (this.task.checkStop())
         break label457;
       if (this.config.isCopyImageFlag()) {
         int maxPage = this.config.getMaxPageCount();
         this.config.setMaxPageCount(-1);
         this.fileDownloader
           .setDenyExtension(".html.htm.jsp.php.asp.shtml.swf.js.css.ico.avi.mpg.mpeg.mp3.mp4.wma.wmv.rm.rmvb.exe.tar.gz.zip.rar");
         this.currentLevel += 1;
         this.task.setCurrentInfo("正在处理第" + (this.currentLevel + 1) + "层级URL");
         String[] urls = this.config.getUrlLevels();
         urls = (String[])
           ArrayUtils.add(urls, 
           "${A}.gif\n${A}.jpg\n${A}.jpeg\n${A}.png\n${A}.bmp\n${A}.GIF\n${A}.JPG\n${A}.JPEG\n${A}.PNG\n${A}.BMP");
         this.config.setUrlLevels(urls);
         dealOneLevel();
         this.config.setMaxPageCount(maxPage);
         this.fileDownloader
           .setDenyExtension(".gif.jpg.jpeg.swf.bmp.png.js.wmv.css.ico.avi.mpg.mpeg.mp3.mp4.wma.rm.rmvb.exe.tar.gz.zip.rar");
       }
       retryWithFilter();
       label457: writeArticle();
     }
     catch (Exception e1) {
       e1.printStackTrace();
       return null;
     } finally {
       this.list.save();
       this.list.close();
     }
     return this.list;
   }
 
   public void writeArticle()
   {
     prepareList();
     if (this.config.getType() == 1) {
       QueryBuilder imageQB = new QueryBuilder("select id from zccatalog where type=4 and siteid=?", 
         CatalogUtil.getSiteID(this.config.getCatalogID()));
       String imageCatalogID = imageQB.executeString();
       if (StringUtil.isEmpty(CatalogUtil.getSiteID(this.config.getCatalogID()))) {
         LogUtil.getLogger().warn("文档采集的目的栏目不存在：ID=" + this.config.getCatalogID());
         return;
       }
       String sitePath = SiteUtil.getAbsolutePath(CatalogUtil.getSiteID(this.config.getCatalogID()));
       String imagePath = "upload/Image/" + CatalogUtil.getAlias(imageCatalogID) + "/";
 
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
         int percent = (100 - this.task.getPercent()) * (cSuccess + cFailure + cLost) / this.list.size();
         this.task.setPercent(this.task.getPercent() + percent);
         if ((doc.isTextContent()) && (doc.getContent() != null)) {
           String text = doc.getContentText();
           rp.setText(text);
           if (rp.match()) {
             Mapx map = rp.getMapx();
             Object[] ks = map.keyArray();
             Object[] vs = map.valueArray();
             for (int i = 0; i < map.size(); ++i) {
               String key = ks[i].toString();
               String value = vs[i].toString();
               if (!key.equalsIgnoreCase("Content")) {
                 value = this.tagPattern.matcher(value).replaceAll("");
               }
               value = StringUtil.htmlDecode(value);
               value = value.trim();
               map.put(key, value);
             }
             String title = map.getString("Title");
             String content = map.getString("Content");
             String author = map.getString("Author");
             String source = map.getString("Source");
             String strDate = map.getString("PublishDate");
             Date publishDate = doc.getLastmodifiedDate();
             if ((StringUtil.isNotEmpty(strDate)) && (StringUtil.isNotEmpty(this.config.getPublishDateFormat())))
             {
               try {
                 strDate = DateUtil.convertChineseNumber(strDate);
                 publishDate = DateUtil.parse(strDate, this.config.getPublishDateFormat());
               } catch (Exception e) {
                 this.task.addError("日期" + strDate + "不符合指定格式" + doc.getUrl());
               }
               publishDateFlag = true;
             }
             if (publishDate.getTime() > System.currentTimeMillis()) {
               publishDate = new Date();
             }
             ArticleAPI api = new ArticleAPI();
             try {
               ZCArticleSchema article = new ZCArticleSchema();
               if (StringUtil.isNotEmpty(title)) {
                 article.setTitle(title);
               } else {
                 ++cLost;
                 break label1209:
               }
               if (StringUtil.isNotEmpty(content)) {
                 content = content.trim();
                 while (rp.match()) {
                   String html = rp.getMapx().getString("Content");
                   content = content + html;
                 }
                 if (this.config.isCleanLinkFlag()) {
                   content = this.framePattern.matcher(content).replaceAll("");
                   content = this.stylePattern.matcher(content).replaceAll("");
                   content = this.scriptPattern.matcher(content).replaceAll("");
                   content = this.linkPattern.matcher(content).replaceAll("$1");
                 }
                 if (filters != null) {
                   for (int k = 0; k < filters.length; ++k) {
                     content = filters[k].replace(content, "");
                   }
                 }
 
                 String str = dealImage(content, doc.getUrl(), sitePath, imagePath, imageCatalogID);
                 article.setContent(str);
               } else {
                 ++cLost;
                 break label1209:
               }
               if (StringUtil.isNotEmpty(author)) {
                 article.setAuthor(author);
               }
               if (StringUtil.isNotEmpty(source)) {
                 article.setReferName(source);
               }
               article.setReferURL(doc.getUrl());
               article.setPublishDate(publishDate);
               article.setCatalogID(this.config.getCatalogID());
               article.setBranchInnerCode("0001");
               article.setProp2("FromWeb");
 
               if (ExtendManager.hasAction("FromWeb.BeforeSave")) {
                 ExtendManager.executeAll("FromWeb.BeforeSave", new Object[] { article });
               }
 
               Date date = (Date)new QueryBuilder(
                 "select PublishDate from ZCArticle where ReferURL=? and CatalogID=?", doc.getUrl(), 
                 this.config.getCatalogID()).executeOneValue();
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
                 ++cSuccess;
               } else {
                 api.setSchema(article);
                 set.add(article);
                 if (api.insert() > 0L)
                   ++cSuccess;
                 else
                   ++cFailure;
               }
             }
             catch (Exception e) {
               ++cFailure;
               e.printStackTrace();
             }
           } else {
             LogUtil.getLogger().info("未能匹配" + doc.getUrl());
             this.task.addError("未能匹配" + doc.getUrl());
             ++cLost;
           }
           label1209: this.task.setCurrentInfo("正在转换文档, <font class='green'>" + cSuccess + "</font> 个成功, <font class='red'>" + 
             cFailure + "</font> 个失败, <font class='green'>" + cLost + "</font> 个未匹配");
         }
       }
 
       if (!publishDateFlag) {
         String[] lastURLs = this.config.getUrlLevels()[(this.config.getUrlLevels().length - 1)].split("\\\n", -1);
         if (lastURLs.length != 1) {
           return;
         }
         RegexParser rpUrl = new RegexParser(lastURLs[0]);
         boolean numberFlag = true;
         for (int i = 0; i < set.size(); ++i) {
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
         for (int i = set.size() - 1; i >= 0; --i) {
           set.get(i).setOrderFlag(OrderUtil.getDefaultOrder());
           set.get(i).setProp2(null);
         }
         set.update();
       }
     }
   }
 
   public String dealImage(String content, String baseUrl, String sitePath, String imagePath, String imageCatalogID) {
     Matcher m = SearchUtil.resourcePattern1.matcher(content);
     int lastEndIndex = 0;
     StringBuffer sb = new StringBuffer();
     while (m.find(lastEndIndex)) {
       String url = m.group(2);
       String ext = ServletUtil.getUrlExtension(url);
       if ((SearchUtil.isRightUrl(url)) && (StringUtil.isNotEmpty(ext)) && (".gif.jpg.jpeg.bmp.png".indexOf(ext) >= 0)) {
         String fullUrl = SearchUtil.normalizeUrl(url, baseUrl);
         WebDocument tdoc = this.list.get(fullUrl);
         if ((tdoc != null) && (!tdoc.isTextContent())) {
           byte[] data = tdoc.getContent();
           sb.append(content.substring(lastEndIndex, m.start()));
           String imageFilePath = saveImage(data, sitePath, imagePath, imageCatalogID, ext, fullUrl);
           sb.append(StringUtil.replaceEx(m.group(0), url, imageFilePath));
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
       if ((SearchUtil.isRightUrl2(url)) && (StringUtil.isNotEmpty(ext)) && (".gif.jpg.jpeg.bmp.png".indexOf(ext) >= 0)) {
         String fullUrl = SearchUtil.normalizeUrl(url, baseUrl);
         WebDocument tdoc = this.list.get(fullUrl);
         if ((tdoc != null) && (!tdoc.isTextContent())) {
           byte[] data = tdoc.getContent();
           sb.append(content.substring(lastEndIndex, m.start()));
           String imageFilePath = saveImage(data, sitePath, imagePath, imageCatalogID, ext, fullUrl);
           sb.append(StringUtil.replaceEx(m.group(0), url, imageFilePath));
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
 
   public static String saveImage(byte[] data, String path1, String path2, String catalogID, String ext, String imageURL)
   {
     ZCImageSchema image = new ZCImageSchema();
     image.setSourceURL(imageURL);
     boolean flag = true;
     ZCImageSet set = image.query();
     if (set.size() > 0) {
       image = set.get(0);
       File f = new File(path1 + path2 + image.getSrcFileName());
       if (f.exists()) {
         if (f.length() == data.length) {
           flag = false;
         }
         FileUtil.writeByte(f, data);
       }
     } else {
       long imageID = NoUtil.getMaxID("DocID");
       int random = NumberUtil.getRandomInt(10000);
       String srcFileName = imageID + random + ext;
       FileUtil.writeByte(path1 + path2 + srcFileName, data);
       image.setID(imageID);
       image.setCatalogID(catalogID);
       image.setCatalogInnerCode(CatalogUtil.getInnerCode(catalogID));
       image.setName(imageID + random);
       image.setOldName(imageID + random);
       image.setSiteID(CatalogUtil.getSiteID(catalogID));
       image.setPath(path2);
       image.setFileName(imageID + NumberUtil.getRandomInt(10000) + ".jpg");
       image.setSrcFileName(srcFileName);
       image.setSuffix(ext);
       image.setCount(1L);
       image.setWidth(0L);
       image.setHeight(0L);
       try {
         BufferedImage img = ImageIO.read(new File(path1 + path2 + srcFileName));
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
 
       return 
         (Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
         SiteUtil.getAlias(CatalogUtil.getSiteID(catalogID)) + "/" + image.getPath() + "1_" + image
         .getFileName()).replaceAll("//", "/");
     }
     return 
       (Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
       SiteUtil.getAlias(CatalogUtil.getSiteID(catalogID)) + "/" + image.getPath() + image.getSrcFileName())
       .replaceAll("//", "/");
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
 
   private void prepareScript()
     throws EvalException
   {
     StringBuffer sb = new StringBuffer();
     if (StringUtil.isNotEmpty(this.config.getScript())) {
       this.se = new ScriptEngine(this.config.getLanguage());
       this.se.importPackage("com.zving.framework.data");
       this.se.importPackage("com.zving.framework.utility");
       if (this.config.getLanguage() == 1) {
         sb.append("StringBuffer _Result = new StringBuffer();\n");
         sb.append("void write(String str){_Result.append(str);}\n");
         sb.append("void writeln(String str){_Result.append(str+\"\\n\");}\n");
         sb.append(this.config.getScript());
         sb.append("\nreturn _Result.toString();\n");
       } else {
         sb.append("var _Result = [];\n");
         sb.append("function write(str){_Result.push(str);}\n");
         sb.append("function writeln(str){_Result.push(str+\"\\n\");}\n");
         sb.append(this.config.getScript());
         sb.append("\nreturn _Result.join('');\n");
       }
       this.se.compileFunction("_EvalScript", sb.toString());
     }
   }
 
   public void executeScript(String when, CrawlScriptUtil util)
   {
     this.currentLevelDownloadedCount += 1;
     if (when.equalsIgnoreCase("after")) {
       this.task.setCurrentInfo("正在抓取" + util.getCurrentUrl());
     }
     if (this.total == 0.0D) {
       for (int i = 0; i < this.config.getUrlLevels().length + 1; ++i) {
         this.total += (i + 1) * (i + 1) * 400;
       }
     }
     double t = (this.currentLevel + 1) * (this.currentLevel + 1) * 400;
     t = t / this.total + (this.currentLevel + 1) * (this.currentLevel + 2) / this.total * (
       this.currentLevelDownloadedCount * 1.0D / this.currentLevelCount);
     int percent = new Double(t * 100.0D).intValue();
     this.task.setPercent(percent);
     if (StringUtil.isNotEmpty(this.config.getScript())) {
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
 
   private void dealOneLevel() {
     String[] arr = this.config.getUrlLevels()[this.currentLevel].trim().split("\n");
     this.task.setCurrentInfo("正在生成第" + (this.currentLevel + 1) + "层级URL");
     this.currentLevelCount = 0;
     if (this.currentLevel != 0)
       this.extracter.extract(this.list, this.currentLevel, this.fileDownloader);
     else {
       for (int i = 0; i < arr.length; ++i) {
         String url = arr[i];
         if (StringUtil.isEmpty(url)) {
           continue;
         }
         if (!this.list.containsKey(url)) {
           WebDocument doc = new WebDocument();
           doc.setUrl(url);
           doc.setLevel(this.currentLevel);
           this.list.put(doc);
         }
       }
     }
     this.currentLevelCount = this.list.size();
     this.fileDownloader.downloadList(this.list, this.currentLevel);
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

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.search.crawl.Crawler
 * JD-Core Version:    0.5.4
 */