 package com.zving.cms.datachannel;
 
 import com.zving.framework.Config;
 import com.zving.framework.orm.SchemaSet;
 import com.zving.framework.utility.StringUtil;
 import com.zving.schema.ZCArticleSet;
 
 public class PublishMonitor
 {
   public static ZCArticleSet set = new ZCArticleSet();
 
   public static PublishThread thread = null;
 
   public static long interval = 1000L;
 
   private static Object mutex = new Object();
 
   public void init()
   {
     String publishInterval = Config.getValue("PublishInterval");
     if (StringUtil.isDigit(publishInterval)) {
       interval = Long.parseLong(publishInterval);
     }
     synchronized (mutex) {
       if (thread == null)
         synchronized (mutex) {
           thread = new PublishThread();
           thread.start();
         }
     }
   }
 
   public void addJob(SchemaSet newSet)
   {
     init();
 
     synchronized (set) {
       set.add(newSet);
     }
   }
 
   public void clean()
   {
     synchronized (set) {
       set.clear();
     }
   }
 
   public void execute(ZCArticleSet newSet)
   {
     Publisher p = new Publisher();
     p.publishArticle(newSet);
   }
 
   class PublishThread extends Thread {
     PublishThread() {
     }
 
     public void run() {
       while (true) {
         try {
           sleep(PublishMonitor.interval);
         } catch (InterruptedException e) {
           e.printStackTrace();
         }
 
         ZCArticleSet newSet = new ZCArticleSet();
         synchronized (PublishMonitor.set) {
           newSet.add(PublishMonitor.set);
           PublishMonitor.set.clear();
         }
 
         PublishMonitor.this.execute(newSet);
       }
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.datachannel.PublishMonitor
 * JD-Core Version:    0.5.4
 */