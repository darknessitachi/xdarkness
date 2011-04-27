 package com.zving.cms.dataservice;
 
 import com.zving.cms.api.SearchAPI;
 import com.zving.framework.User;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.utility.Mapx;
 import com.zving.platform.Application;
 import com.zving.platform.pub.ConfigEanbleTaskManager;
 
 public class FullTextTaskManager extends ConfigEanbleTaskManager
 {
   public static final String CODE = "IndexMaintenance";
   Mapx runningMap = new Mapx();
 
   public boolean isRunning(long id) {
     int r = this.runningMap.getInt(new Long(id));
     return r != 0;
   }
 
   public void execute(long id) {
     this.runningMap.put(new Long(id), 1);
     new Thread(id) {
       private final long val$id;
 
       public void run() { try { SearchAPI.update(this.val$id);
         } catch (Exception e) {
           e.printStackTrace();
         } finally {
           FullTextTaskManager.this.runningMap.remove(new Long(this.val$id));
         } }
     }
     .start();
   }
 
   public Mapx getConfigEnableTasks() {
     DataTable dt = null;
     if (User.getCurrent() != null)
       dt = new QueryBuilder("select id,name from ZCFullText where siteid=?", Application.getCurrentSiteID())
         .executeDataTable();
     else {
       dt = new QueryBuilder("select id,name from ZCFullText").executeDataTable();
     }
     return dt.toMapx(0, 1);
   }
 
   public String getCode() {
     return "IndexMaintenance";
   }
 
   public String getName() {
     return "索引维护任务";
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.dataservice.FullTextTaskManager
 * JD-Core Version:    0.5.4
 */