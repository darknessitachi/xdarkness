 package com.xdarkness.datachannel;
 
 import com.xdarkness.schema.ZCInnerDeploySchema;
import com.xdarkness.schema.ZCInnerDeploySet;
import com.xdarkness.schema.ZCInnerGatherSchema;
import com.xdarkness.schema.ZCInnerGatherSet;
import com.xdarkness.framework.util.LogUtil;
 
 public class InnerSyncTask extends GeneralTask
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
               InnerSyncTask.this.isRunning = true;
               User.UserData ud = new User.UserData();
               ud.setUserName("SYSTEM");
               ud.setLogin(true);
               ud.setManager(true);
               User.setCurrent(new User.UserData());
               long t = System.currentTimeMillis();
               try {
                 ZCInnerGatherSet gatherSet = new ZCInnerGatherSchema().query();
                 for (int i = 0; i < gatherSet.size(); i++) {
                   if ("N".equals(gatherSet.get(i).getStatus())) {
                     continue;
                   }
                   InnerSyncService.executeGather(gatherSet.get(i), null);
                 }
                 ZCInnerDeploySet deploySet = new ZCInnerDeploySchema().query();
                 for (int i = 0; i < deploySet.size(); i++) {
                   if ("N".equals(deploySet.get(i).getStatus())) {
                     continue;
                   }
                   InnerSyncService.executeDeploy(deploySet.get(i), null);
                 }
               } finally {
                 InnerSyncTask.this.isRunning = false;
               }
               LogUtil.info("网站群任务耗时：" + (System.currentTimeMillis() - t) + "毫秒");
             }
           }
           .start();
       }
   }
 
   public long getID()
   {
     return 201005212237L;
   }
 
   public String getName()
   {
     return "网站群任务";
   }
 }

          
/*    com.xdarkness.datachannel.InnerSyncTask
 * JD-Core Version:    0.6.0
 */