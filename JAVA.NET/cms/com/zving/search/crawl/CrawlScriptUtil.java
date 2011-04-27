 package com.zving.search.crawl;
 
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.RegexParser;
 import com.zving.search.DocumentList;
 import com.zving.search.WebDocument;
 
 public class CrawlScriptUtil
 {
   protected DocumentList list;
   protected WebDocument doc;
   protected Mapx map;
 
   public String getField(String content, String regex, String key)
   {
     RegexParser rp = new RegexParser(regex);
     rp.setText(content);
     if (rp.match()) {
       return this.map.getString(key);
     }
     return null;
   }
 
   public String getCurrentUrlField(String field) {
     return this.map.getString(field);
   }
 
   public String getCurrentUrl() {
     return this.doc.getUrl();
   }
 
   public int getCurrentLevel() {
     return this.doc.getLevel();
   }
 
   public void removeUrl(String url) {
     this.list.remove(url);
   }
 
   public void removeCurrentUrl() {
     this.list.remove(this.doc.getUrl());
   }
 
   public void addUrl(String url) {
     addUrl(url, this.doc.getLevel());
   }
 
   public String getCurrentContent() {
     return this.doc.getContentText();
   }
 
   public String getContent(String url) {
     WebDocument wd = this.list.get(url);
     if (wd != null) {
       return wd.getContentText();
     }
     return null;
   }
 
   public void addUrl(String url, int level) {
     WebDocument wd = new WebDocument();
     wd.setUrl(url);
     wd.setLevel(level);
     wd.setRefUrl(this.doc.getUrl());
     this.list.put(wd);
   }
 
   public void addDocument(String url, String content)
   {
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.search.crawl.CrawlScriptUtil
 * JD-Core Version:    0.5.4
 */