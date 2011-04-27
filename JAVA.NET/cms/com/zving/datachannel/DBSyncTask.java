 package com.zving.datachannel;
 
 import com.zving.framework.User;
 import com.zving.framework.User.UserData;
 import com.zving.framework.schedule.GeneralTask;
 import com.zving.framework.utility.LogUtil;
 import com.zving.schema.ZCDBGatherSchema;
 import com.zving.schema.ZCDBGatherSet;
 
 public class DBSyncTask extends GeneralTask
 {
   private boolean isRunning = false;
   private static Object mutex = new Object();
 
   public void execute()
   {
     if (!this.isRunning)
       synchronized (mutex) {
         if (!this.isRunning)
           new Thread() {
             public void run() {
               DBSyncTask.this.isRunning = true;
               User.UserData ud = new User.UserData();
               ud.setUserName("SYSTEM");
               ud.setLogin(true);
               ud.setManager(true);
               User.setCurrent(new User.UserData());
               long t = System.currentTimeMillis();
               try {
                 ZCDBGatherSet gatherSet = new ZCDBGatherSchema().query();
                 for (int i = 0; i < gatherSet.size(); ++i) {
                   if ("N".equals(gatherSet.get(i).getStatus())) {
                     continue;
                   }
                   FromDB.executeGather(gatherSet.get(i), false, null);
                 }
               } finally {
                 DBSyncTask.this.isRunning = false;
               }
               LogUtil.info("数据库采集任务耗时：" + (System.currentTimeMillis() - t) + "毫秒");
             }
           }
           .start();
       }
   }
 
   public long getID()
   {
     return 201005281237L;
   }
 
   public String getName()
   {
     return "数据库采集任务";
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.datachannel.DBSyncTask
 * JD-Core Version:    0.5.4
 */