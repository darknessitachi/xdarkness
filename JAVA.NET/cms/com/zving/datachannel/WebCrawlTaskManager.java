 package com.zving.datachannel;
 
 import com.zving.framework.User;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.utility.Mapx;
 import com.zving.platform.Application;
 import com.zving.platform.pub.ConfigEanbleTaskManager;
 import com.zving.schema.ZCWebGatherSchema;
 import com.zving.search.crawl.CrawlConfig;
 import com.zving.search.crawl.Crawler;
 import java.io.PrintStream;
 
 public class WebCrawlTaskManager extends ConfigEanbleTaskManager
 {
   public static final String CODE = "WebCrawl";
   Mapx runningMap = new Mapx();
 
   public boolean isRunning(long id) {
     int r = this.runningMap.getInt(new Long(id));
     return r != 0;
   }
 
   public Mapx getConfigEnableTasks() {
     DataTable dt = null;
     if (User.getCurrent() != null)
       dt = 
         new QueryBuilder("select id,name from ZCWebGather where siteid=?", Application.getCurrentSiteID()).executeDataTable();
     else {
       dt = new QueryBuilder("select id,name from ZCWebGather").executeDataTable();
     }
     return dt.toMapx(0, 1);
   }
 
   public void execute(long id) {
     this.runningMap.put(new Long(id), 1);
     ZCWebGatherSchema wg = new ZCWebGatherSchema();
     wg.setID(id);
     if (wg.fill()) {
       if ("N".equals(wg.getStatus())) {
         return;
       }
       Thread thread = new Thread(wg) {
         private final ZCWebGatherSchema val$wg;
 
         public void run() { try { CrawlConfig cc = new CrawlConfig();
             cc.parse(this.val$wg);
             Crawler crawler = new Crawler();
             crawler.setConfig(cc);
             crawler.crawl();
           } finally {
             WebCrawlTaskManager.this.runningMap.remove(new Long(this.val$wg.getID()));
           } }
 
       };
       thread.start();
     } else {
       System.out.println("没有找到对应的抓取任务.任务ID:" + id);
     }
   }
 
   public String getCode() {
     return "WebCrawl";
   }
 
   public String getName() {
     return "Web采集任务";
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.datachannel.WebCrawlTaskManager
 * JD-Core Version:    0.5.4
 */