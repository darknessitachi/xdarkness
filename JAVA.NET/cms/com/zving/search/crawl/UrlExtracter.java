 package com.zving.search.crawl;
 
 import com.zving.framework.utility.RegexParser;
 import com.zving.framework.utility.ServletUtil;
 import com.zving.framework.utility.StringUtil;
 import com.zving.search.DocumentList;
 import com.zving.search.SearchUtil;
 import java.io.PrintStream;
 import java.util.ArrayList;
 
 public class UrlExtracter extends Thread
 {
   private int threadCount = 1;
 
   protected int aliveThreadCount = 0;
 
   protected int busyThreadCount = 0;
   protected FileDownloader fileDownloader;
   protected int size;
   protected int extractOutCount;
   protected int extractedCount;
   ArrayList urlArr = new ArrayList();
 
   ArrayList rpArr = new ArrayList();
 
   ArrayList rpFilterArr = new ArrayList();
   CrawlConfig cc;
 
   protected void init(DocumentList list, int level, FileDownloader fileDownloader)
   {
     this.fileDownloader = fileDownloader;
     this.extractedCount = 0;
     this.cc = list.getCrawler().getConfig();
     String[] arr = this.cc.getUrlLevels()[level].trim().split("\n");
     this.urlArr.clear();
     this.rpArr.clear();
     for (int i = 0; i < arr.length; ++i) {
       String url = arr[i].trim();
       if (StringUtil.isEmpty(url)) {
         continue;
       }
       url = url.trim();
       url = SearchUtil.escapeUrl(url);
       RegexParser rp = new RegexParser(url);
       this.urlArr.add(url);
       this.rpArr.add(rp);
     }
     if (this.cc.isFilterFlag()) {
       arr = this.cc.getFilterExpr().trim().split("\n");
       for (int i = 0; i < arr.length; ++i) {
         String url = arr[i].trim();
         if (StringUtil.isEmpty(url)) {
           continue;
         }
         url = url.trim();
         RegexParser rp = new RegexParser(url);
         this.rpFilterArr.add(rp);
       }
     }
   }
 
   public void extract(DocumentList list, int level, FileDownloader fileDownloader) {
     init(list, level, fileDownloader);
     this.aliveThreadCount = this.threadCount;
     this.size = list.size();
     list.moveFirst();
     for (int i = 0; i < this.threadCount; ++i) {
       UrlExtracterThread edt = new UrlExtracterThread();
       edt.setExtracter(this);
       edt.setList(list);
       edt.setLevel(level);
       edt.setThreadIndex(i);
       edt.start();
     }
     try {
       while (this.aliveThreadCount != 0)
         Thread.sleep(1000L);
     }
     catch (InterruptedException e) {
       e.printStackTrace();
     }
   }
 
   public boolean isMatchedUrl(String url2, String refUrl) {
     if (url2.startsWith("http://mso.allyes.com")) {
       System.out.println(1);
     }
 
     String ext = ServletUtil.getUrlExtension(url2);
     if ((StringUtil.isNotEmpty(ext)) && 
       (((StringUtil.isEmpty(this.fileDownloader.getAllowExtension())) || 
       (this.fileDownloader.getAllowExtension().indexOf(ext) < 0))) && 
       (this.fileDownloader.getDenyExtension().indexOf(ext) >= 0)) {
       return false;
     }
 
     boolean matchedFlag = false;
     for (int i = 0; i < this.rpFilterArr.size(); ++i) {
       RegexParser rp = (RegexParser)this.rpFilterArr.get(i);
 
       synchronized (rp) {
         rp.setText(url2);
         if (rp.match()) {
           return false;
         }
       }
     }
     for (int i = 0; i < this.rpArr.size(); ++i) {
       String url = (String)this.urlArr.get(i);
       RegexParser rp = (RegexParser)this.rpArr.get(i);
 
       if (url.indexOf('/', 8) > 0) {
         String prefix = url.substring(0, url.indexOf('/', 8));
         if ((prefix.indexOf('$') < 0) && 
           (!url2.startsWith(prefix)))
         {
           continue;
         }
 
       }
 
       synchronized (rp) {
         rp.setText(url2);
         if (rp.match()) {
           matchedFlag = true;
         }
       }
     }
     return matchedFlag;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.search.crawl.UrlExtracter
 * JD-Core Version:    0.5.4
 */