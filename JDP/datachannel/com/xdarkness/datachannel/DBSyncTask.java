 package com.xdarkness.datachannel;
 
 import com.xdarkness.schema.ZCDBGatherSchema;
import com.xdarkness.schema.ZCDBGatherSet;
import com.xdarkness.framework.util.LogUtil;
 
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
                 for (int i = 0; i < gatherSet.size(); i++) {
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

          
/*    com.xdarkness.datachannel.DBSyncTask
 * JD-Core Version:    0.6.0
 */