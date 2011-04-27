 package com.zving.search.crawl;
 
 import com.zving.framework.messages.LongTimeTask;
 import com.zving.framework.utility.LogUtil;
 import com.zving.search.DocumentList;
 import com.zving.search.SearchUtil;
 import com.zving.search.WebDocument;
 import org.apache.commons.logging.Log;
 
 public class UrlExtracterThread extends Thread
 {
   private DocumentList list;
   private int level;
   private boolean isBusy;
   private UrlExtracter extracter;
   private int threadIndex;
   private WebDocument doc;
 
   public void run()
   {
     this.doc = this.list.next();
     int maxPageCount = this.list.getCrawler().getConfig().getMaxPageCount();
     int maxListCount = this.list.getCrawler().getConfig().getMaxListCount();
     int maxUrlLevel = this.list.getCrawler().getConfig().getUrlLevels().length;
     while (this.doc != null) {
       if (!this.list.getCrawler().task.checkStop()) {
         if (!this.isBusy) {
           synchronized (this.extracter) {
             this.extracter.busyThreadCount += 1;
           }
         }
         this.isBusy = true;
         if ((this.doc.getLevel() == this.level - 1) && (this.doc.isTextContent())) {
           String[] urls = SearchUtil.getUrlFromHtml(this.doc.getContentText());
           for (int k = 0; k < urls.length; ++k) {
             if ((maxPageCount > 0) && (maxUrlLevel == this.doc.getLevel() + 2) && 
               (this.extracter.extractOutCount >= maxPageCount)) {
               break;
             }
             if ((maxListCount > 0) && (maxUrlLevel == this.doc.getLevel() + 3) && 
               (this.extracter.extractOutCount >= maxListCount)) {
               break;
             }
             String url2 = urls[k];
             url2 = SearchUtil.normalizeUrl(url2, this.doc.getUrl());
             url2 = SearchUtil.escapeUrl(url2);
             if (this.extracter.isMatchedUrl(url2, this.doc.getUrl())) {
               if (!this.list.containsKey(url2)) {
                 WebDocument doc2 = new WebDocument();
                 doc2.setUrl(url2);
                 doc2.setLevel(this.level);
                 doc2.setRefUrl(this.doc.getUrl());
                 this.list.put(doc2);
                 CrawlScriptUtil util = new CrawlScriptUtil();
                 util.doc = doc2;
                 util.list = this.list;
                 this.list.getCrawler().executeScript("before", util);
                 this.extracter.extractOutCount += 1;
               }
               else if (this.list.get(url2).getContent() != null) {
                 this.extracter.extractOutCount += 1;
               }
             }
           }
 
           this.extracter.extractedCount += 1;
           long percent = Math.round(this.extracter.extractedCount * 10000.0D / this.extracter.size);
           long p = percent % 100L;
           String pStr = p;
           if (p < 10L) {
             pStr = "0" + p;
           }
           LogUtil.getLogger().info(
             "Extracting,Thread " + this.threadIndex + "\tL" + this.doc.getLevel() + "\t" + percent / 100L + 
             "." + pStr + "%");
         } else if ((this.doc.getLevel() == this.level) && 
           (!this.extracter.isMatchedUrl(this.doc.getUrl(), this.doc.getRefUrl()))) {
           this.list.remove(this.doc.getUrl());
         }
 
         this.doc = this.list.next();
       } else {
         if (this.isBusy) {
           synchronized (this.extracter) {
             this.extracter.busyThreadCount -= 1;
           }
         }
         this.doc = null;
       }
     }
     synchronized (this.extracter) {
       this.isBusy = false;
       this.extracter.busyThreadCount -= 1;
       this.extracter.aliveThreadCount -= 1;
     }
   }
 
   public int getLevel() {
     return this.level;
   }
 
   public void setLevel(int level) {
     this.level = level;
   }
 
   public DocumentList getList() {
     return this.list;
   }
 
   public void setList(DocumentList list) {
     this.list = list;
   }
 
   public UrlExtracter getExtracter() {
     return this.extracter;
   }
 
   public void setExtracter(UrlExtracter extracter) {
     this.extracter = extracter;
   }
 
   public int getThreadIndex() {
     return this.threadIndex;
   }
 
   public void setThreadIndex(int threadIndex) {
     this.threadIndex = threadIndex;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.search.crawl.UrlExtracterThread
 * JD-Core Version:    0.5.4
 */