 package com.zving.search.index;
 
 import com.zving.framework.Config;
 import com.zving.framework.utility.DateUtil;
 import com.zving.framework.utility.FileUtil;
 import com.zving.framework.utility.LogUtil;
 import com.zving.framework.utility.StringUtil;
 import com.zving.search.ZvingAnalyzer;
 import com.zving.search.ZvingSimilarity;
 import java.io.File;
 import java.util.Date;
 import org.apache.commons.logging.Log;
 import org.apache.lucene.document.Document;
 import org.apache.lucene.index.IndexWriter;
 import org.apache.lucene.index.IndexWriter.MaxFieldLength;
 import org.apache.lucene.store.FSDirectory;
 
 public abstract class Indexer
 {
   protected String indexPath;
   protected IndexWriter writer;
   protected Date lastDate;
   protected Date nextLastDate;
   protected int threadCount = 8;
   protected int aliveThreadCount;
   private boolean updateFlag;
   private boolean singleThreadMode = true;
 
   private static Object mutex = new Object();
 
   public void setPath(String path) {
     this.indexPath = path;
     try {
       if (!new File(path).exists()) {
         this.nextLastDate = (this.lastDate = DateUtil.parse("1970-01-01"));
         this.updateFlag = false;
       }
       else if (!new File(path + "/time.lock").exists()) {
         FileUtil.deleteFromDir(path);
         this.nextLastDate = (this.lastDate = new Date());
         this.updateFlag = false;
       }
       else {
         String lastDateStr = FileUtil.readText(path + "/time.lock").trim();
         if (StringUtil.isEmpty(lastDateStr))
           this.nextLastDate = (this.lastDate = new Date());
         else {
           this.nextLastDate = (this.lastDate = DateUtil.parseDateTime(lastDateStr));
         }
         this.updateFlag = true;
       }
     }
     catch (Exception e) {
       throw new RuntimeException(e);
     }
   }
 
   public void start()
   {
     synchronized (mutex) {
       if (StringUtil.isEmpty(this.indexPath)) {
         throw new RuntimeException("没有指定索引存放的目录!");
       }
       LogUtil.getLogger().info("开始建立索引" + this.indexPath + "......");
       long startTime = System.currentTimeMillis();
       try
       {
         try {
           if (new File(this.indexPath + "/write.lock").exists())
             FileUtil.delete(this.indexPath + "/write.lock");
         }
         catch (Exception localException1) {
         }
         if (this.updateFlag)
           this.writer = 
             new IndexWriter(FSDirectory.open(new File(this.indexPath)), new ZvingAnalyzer(), false, 
             IndexWriter.MaxFieldLength.UNLIMITED);
         else {
           this.writer = 
             new IndexWriter(FSDirectory.open(new File(this.indexPath)), new ZvingAnalyzer(), true, 
             IndexWriter.MaxFieldLength.UNLIMITED);
         }
         this.writer.setSimilarity(new ZvingSimilarity());
 
         if ("true".equals(Config.getValue("App.MinimalMemory")))
           this.writer.setMaxBufferedDocs(10);
         else {
           this.writer.setMaxBufferedDocs(50);
         }
 
         if (this.singleThreadMode) {
           this.threadCount = 1;
         }
         this.aliveThreadCount = this.threadCount;
         for (int i = 0; i < this.threadCount; ++i) {
           IndexerThread t = new IndexerThread();
           t.setIndexer(this);
           t.start();
         }
         try {
           while (this.aliveThreadCount != 0)
             Thread.sleep(10L);
         }
         catch (InterruptedException e) {
           e.printStackTrace();
         }
         if ((this.nextLastDate == null) || (this.lastDate == this.nextLastDate)) {
           this.nextLastDate = new Date();
         }
         FileUtil.writeText(this.indexPath + "/time.lock", DateUtil.toDateTimeString(this.nextLastDate));
         LogUtil.getLogger().info("建立索引共耗时 " + (System.currentTimeMillis() - startTime) + " 毫秒.");
       } catch (Throwable e) {
         e.printStackTrace();
         LogUtil.getLogger().warn("建立索引失败," + this.indexPath);
       } finally {
         try {
           if (this.writer != null) {
             if (new File(this.indexPath + "/optimize.lock").exists()) {
               String lastDateStr = FileUtil.readText(this.indexPath + "/optimize.lock").trim();
               if (StringUtil.isNotEmpty(lastDateStr)) {
                 Date date = DateUtil.parseDateTime(lastDateStr);
                 if (date.getTime() - System.currentTimeMillis() >= 1440000L)
                   this.writer.optimize(true);
               }
               else {
                 this.writer.optimize(true);
               }
             } else {
               this.writer.optimize(true);
             }
             this.writer.close();
           }
         } catch (Exception e) {
           e.printStackTrace();
         }
       }
     }
   }
 
   public abstract void create();
 
   public abstract void update();
 
   protected void addDocument(Document doc) throws Exception {
     this.writer.addDocument(doc);
   }
 
   public int getThreadCount() {
     return this.threadCount;
   }
 
   public void setThreadCount(int threadCount) {
     this.threadCount = threadCount;
   }
 
   public Date getLastDate() {
     return this.lastDate;
   }
 
   public boolean isUpdateFlag() {
     return this.updateFlag;
   }
 
   public void setUpdateFlag(boolean updateFlag) {
     this.updateFlag = updateFlag;
   }
 
   public boolean isSingleThreadMode() {
     return this.singleThreadMode;
   }
 
   public void setSingleThreadMode(boolean singleThreadMode) {
     this.singleThreadMode = singleThreadMode;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.search.index.Indexer
 * JD-Core Version:    0.5.4
 */