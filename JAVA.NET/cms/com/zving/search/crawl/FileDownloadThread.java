 package com.zving.search.crawl;
 
 import com.zving.framework.messages.LongTimeTask;
 import com.zving.framework.utility.LogUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.ServletUtil;
 import com.zving.framework.utility.StringUtil;
 import com.zving.search.DocumentList;
 import com.zving.search.SearchUtil;
 import com.zving.search.WebDocument;
 import com.zving.search.index.IndexUtil;
 import java.util.Arrays;
 import java.util.Date;
 import org.apache.commons.httpclient.Header;
 import org.apache.commons.httpclient.HttpClient;
 import org.apache.commons.httpclient.methods.HeadMethod;
 import org.apache.commons.lang.ArrayUtils;
 import org.apache.commons.logging.Log;
 
 public class FileDownloadThread extends Thread
 {
   private HttpClient httpClient;
   private int threadIndex;
   private WebDocument doc;
   private DocumentList list;
   private long startTime;
   private FileDownloader downloader;
   private boolean isCheckEmpty;
   private boolean isBusy;
   private CrawlConfig config;
   private int maxListCount;
   private int maxPageCount;
 
   public FileDownloader getDownloader()
   {
     return this.downloader;
   }
 
   public void setDownloader(FileDownloader downloader) {
     this.downloader = downloader;
   }
 
   public FileDownloadThread() {
     setName("FileDownloadThread " + this.threadIndex);
   }
 
   public int getThreadIndex() {
     return this.threadIndex;
   }
 
   public void setThreadIndex(int threadIndex) {
     this.threadIndex = threadIndex;
   }
 
   private void waitOtherThread() {
     if (this.isBusy) {
       synchronized (this.downloader) {
         this.downloader.busyThreadCount -= 1;
       }
     }
     this.isBusy = false;
     try {
       Thread.sleep(500L);
       for (int i = 0; i < 100; ++i) {
         if ((this.doc == null) && (this.downloader.busyThreadCount > 0)) {
           Thread.sleep(500L);
           this.doc = ((this.isCheckEmpty) ? this.list.nextEmpty() : this.list.next());
         }
       }
     }
     catch (InterruptedException e)
     {
       e.printStackTrace();
     }
   }
 
   public void run() {
     this.config = this.list.getCrawler().getConfig();
     this.maxListCount = this.config.getMaxListCount();
     this.maxPageCount = this.config.getMaxPageCount();
 
     this.startTime = (System.currentTimeMillis() - 100L);
     this.doc = ((this.isCheckEmpty) ? this.list.nextEmpty() : this.list.next());
     if (this.doc == null) {
       waitOtherThread();
     }
     while (this.doc != null) {
       if (!this.list.getCrawler().task.checkStop()) {
         if (!this.isBusy) {
           synchronized (this.downloader) {
             this.downloader.busyThreadCount += 1;
           }
         }
         if ((this.maxPageCount > 0) && (this.config.getUrlLevels().length == this.doc.getLevel() + 1)) {
           if (this.downloader.levelDownloadCount >= this.maxPageCount) break; if (this.threadIndex > this.maxPageCount) {
             break;
           }
         }
         if ((this.maxListCount > 0) && (this.config.getUrlLevels().length == this.downloader.getCurrentLevel() + 2)) {
           if (this.downloader.levelDownloadCount >= this.maxListCount) break; if (this.threadIndex > this.maxListCount) {
             break;
           }
         }
         this.isBusy = true;
         if (this.doc.getLevel() == this.downloader.getCurrentLevel()) {
           String statusCode = "200";
           if ((this.doc.getLastDownloadTime() < this.startTime) && (this.doc.getLevel() == this.downloader.getCurrentLevel())) {
             if (isNeedDownload())
               statusCode = dealOneUrl();
             else {
               statusCode = "---";
             }
           }
           long percent = Math.round(10000.0D * this.downloader.downloadCount / this.list.size());
           long p = percent % 100L;
           String pStr = p;
           if (p < 10L) {
             pStr = "0" + p;
           }
           LogUtil.getLogger().info(
             ((this.threadIndex < 0) ? "" : new StringBuffer(String.valueOf(this.threadIndex)).append("\t").toString()) + statusCode + "\tL" + this.doc.getLevel() + "\t" + 
             percent / 100L + "." + pStr + "% \t" + this.doc.getUrl());
         }
         this.doc = ((this.isCheckEmpty) ? this.list.nextEmpty() : this.list.next());
         if (this.doc == null)
           waitOtherThread();
       }
       else {
         if (this.isBusy) {
           synchronized (this.downloader) {
             this.downloader.busyThreadCount -= 1;
           }
         }
         this.doc = null;
         this.isBusy = false;
       }
     }
     synchronized (this.downloader) {
       this.downloader.aliveThreadCount -= 1;
     }
   }
 
   public int dealOneUrl() {
     String url = this.doc.getUrl();
     setName("FileDownloadThread " + url);
     int level = this.doc.getLevel();
     String ref = this.doc.getRefUrl();
     int statusCode = FileDownloader.downloadOne(this.httpClient, this.doc, this.threadIndex);
     if (this.doc.getContent() == null) {
       return statusCode;
     }
 
     do
     {
       String redirectURL = SearchUtil.getRedirectURL(this.doc);
       if (!StringUtil.isNotEmpty(redirectURL)) break;
       redirectURL = SearchUtil.normalizeUrl(redirectURL, this.doc.getUrl());
       WebDocument rdoc = new WebDocument();
       rdoc.setLevel(this.doc.getLevel());
       rdoc.setRefUrl(this.doc.getUrl());
       rdoc.setUrl(redirectURL);
       FileDownloader.downloadOne(this.httpClient, rdoc, this.threadIndex);
       this.doc.setContent(rdoc.getContent());
       this.doc.setCharset(rdoc.getCharset());
     }
     while (this.doc.getContent().length < 1024);
 
     this.doc.setRefUrl(ref);
     this.list.put(this.doc);
     this.downloader.levelAddedCount += 1;
 
     CrawlScriptUtil util = new CrawlScriptUtil();
     util.doc = this.doc;
     util.list = this.list;
     this.list.getCrawler().executeScript("after", util);
 
     synchronized (this.downloader) {
       this.downloader.downloadCount += 1;
       this.downloader.levelDownloadCount += 1;
     }
 
     if ((this.doc.isTextContent()) && (!isStopUrl(this.doc))) {
       String[] urls = SearchUtil.getPageURL(this.doc);
 
       for (int i = 0; i < urls.length; ++i) {
         if (urls[i].startsWith("?")) {
           String fileName = ServletUtil.getFileName(url);
           urls[i] = (fileName + urls[i]);
         }
         urls[i] = SearchUtil.normalizeUrl(urls[i], url);
       }
       Arrays.sort(urls);
       for (int i = urls.length - 1; i > 0; --i) {
         if (urls[i].equals(urls[(i - 1)])) {
           urls[i] = null;
         }
       }
       for (int i = 0; i < urls.length; ++i) {
         if (StringUtil.isEmpty(urls[i])) {
           continue;
         }
         String url2 = urls[i];
 
         Mapx form = null;
         if (url2.toLowerCase().indexOf("javascript:") > 0) {
           form = SearchUtil.getDotNetForm(this.doc.getContentText(), url2);
           StringBuffer sb = new StringBuffer();
           Object[] vs = form.valueArray();
           for (int j = 0; j < form.size(); ++j) {
             sb.append(",");
             sb.append(vs[j]);
           }
           if (url.indexOf("?") < 0) {
             url2 = url + "?__MD5=" + StringUtil.md5Hex(sb.toString());
           }
           else if (url.indexOf("__MD5") > 0)
             url2 = url.substring(0, url.indexOf("__MD5")) + "__MD5=" + StringUtil.md5Hex(sb.toString());
           else {
             url2 = url + "&__MD5=" + StringUtil.md5Hex(sb.toString());
           }
         }
 
         if ((this.doc.getLevel() != 0) && (!this.downloader.isMatchedUrl(url2, url))) {
           continue;
         }
         if (this.list.containsKey(url2)) {
           continue;
         }
         WebDocument doc2 = new WebDocument();
         doc2 = new WebDocument();
         doc2.setUrl(url2);
         doc2.setLevel(level);
         doc2.setRefUrl(url);
         doc2.setForm(form);
         doc2.setPageUrl(true);
 
         if (this.config.getUrlLevels().length == this.doc.getLevel() + 1) {
           FileDownloader.downloadOne(this.httpClient, doc2, this.threadIndex);
           if (IndexUtil.getTextFromHtml(this.doc.getContentText()).indexOf(
             IndexUtil.getTextFromHtml(doc2.getContentText())) >= 0) {
             continue;
           }
           byte[] bs = doc2.getContent();
           if (bs != null) {
             this.doc.setContent(ArrayUtils.addAll(this.doc.getContent(), bs));
             this.list.put(this.doc);
           }
         } else {
           synchronized (this.downloader) {
             if ((this.maxListCount > 0) && (this.config.getUrlLevels().length == this.downloader.getCurrentLevel() + 2) && 
               (this.downloader.levelAddedCount >= this.maxListCount)) {
               break label1015;
             }
             this.list.put(doc2);
             this.downloader.levelAddedCount += 1;
           }
         }
       }
     }
     label1015: return statusCode;
   }
 
   public boolean isStopUrl(WebDocument doc)
   {
     if (doc.isPageUrl()) {
       WebDocument refDoc = doc.getList().get(doc.getRefUrl());
       if (refDoc.isPageUrl()) {
         String str1 = doc.getContentText();
         String str2 = refDoc.getContentText();
         if (Math.abs(str1.length() - str2.length()) < 5) {
           int is = 0; int ie = 0;
           for (int i = 0; (i < str1.length()) && (i < str2.length()); ++i) {
             if (str1.charAt(i) != str2.charAt(i)) {
               is = i;
               break;
             }
           }
           for (int i = 1; (i <= str1.length()) && (i <= str2.length()); ++i) {
             if (str1.charAt(str1.length() - i) != str2.charAt(str2.length() - i)) {
               ie = i;
               break;
             }
           }
           if (ie < is) {
             return false;
           }
           String str = str2.substring(is, ie);
           if (str.length() < 5000) {
             if (str.indexOf('>') < 0) {
               return false;
             }
             str = str.substring(str.indexOf('>'));
             if (str.indexOf('<') < 0) {
               return false;
             }
             str = str.substring(0, str.lastIndexOf('<'));
             str = str.replaceAll("<.*?>", "");
             if (str.length() < 20) {
               return true;
             }
           }
         }
       }
     }
     return false;
   }
 
   public boolean isNeedDownload() {
     String url = this.doc.getUrl();
     String ext = ServletUtil.getUrlExtension(url);
     if ((!ext.equals(".gif")) && (!ext.equals(".jpg")) && (!ext.equals(".png")) && (!ext.equals(".jpeg"))) {
       if (this.doc.getLastmodifiedDate() == null)
         return true;
       if (!this.list.hasContent(url)) {
         return true;
       }
     }
     Date date = this.doc.getLastmodifiedDate();
     HeadMethod hm = new HeadMethod(url);
     String lastModified = null;
     try {
       this.httpClient.executeMethod(hm);
       Header h = hm.getRequestHeader("Content-Length");
       if (h != null) { int len = Integer.parseInt(h.getValue().trim());
         if ((this.list.getCrawler().getConfig().getType() == 1) && 
           (((ext.equals(".gif")) || (ext.equals(".jpg")) || (ext.equals(".png")) || (ext.equals(".jpeg")))) && 
           (len < 20000));
         int len;
         do return false;
 
         while (len == this.doc.getContent().length); }
 
 
       Header h = hm.getResponseHeader("Last-Modified");
       if (h == null) {
         h = hm.getResponseHeader("Date");
       }
       if (h == null)
         return true;
       Header h;
       lastModified = h.getValue();
     } catch (Exception localException) {
     }
     finally {
       hm.releaseConnection();
     }
     try {
       if ((lastModified == null) || (!date.equals(SearchUtil.parseLastModifedDate(lastModified)))) break label318;
       return false;
     }
     catch (Exception localException1) {
     }
     label318: return true;
   }
 
   public HttpClient getHttpClient() {
     return this.httpClient;
   }
 
   public void setHttpClient(HttpClient httpClient) {
     this.httpClient = httpClient;
   }
 
   public DocumentList getList() {
     return this.list;
   }
 
   public void setList(DocumentList list) {
     this.list = list;
   }
 
   public boolean isCheckEmpty() {
     return this.isCheckEmpty;
   }
 
   public void setCheckEmpty(boolean isCheckEmpty) {
     this.isCheckEmpty = isCheckEmpty;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.search.crawl.FileDownloadThread
 * JD-Core Version:    0.5.4
 */