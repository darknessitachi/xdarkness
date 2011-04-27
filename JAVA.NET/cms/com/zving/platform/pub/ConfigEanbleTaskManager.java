 package com.zving.platform.pub;
 
 import com.zving.framework.schedule.AbstractTaskManager;
 import com.zving.framework.utility.Mapx;
 import com.zving.schema.ZDScheduleSchema;
 import com.zving.schema.ZDScheduleSet;
 
 public abstract class ConfigEanbleTaskManager extends AbstractTaskManager
 {
   protected static ZDScheduleSet set;
   private static Object mutex = new Object();
   protected static long LastTime;
 
   public Mapx getUsableTasks()
   {
     synchronized (mutex) {
       if ((set == null) || (System.currentTimeMillis() - LastTime > 60000L)) {
         set = new ZDScheduleSchema().query();
         LastTime = System.currentTimeMillis();
       }
     }
     Mapx map = new Mapx();
     for (int i = 0; i < set.size(); ++i) {
       ZDScheduleSchema s = set.get(i);
       if ((s.getTypeCode().equals(getCode())) && ("Y".equals(s.getIsUsing()))) {
         map.put(new Long(s.getSourceID()), "");
       }
     }
     return map;
   }
 
   public String getTaskCronExpression(long id) {
     synchronized (mutex) {
       if ((set == null) || (System.currentTimeMillis() - LastTime > 60000L)) {
         set = new ZDScheduleSchema().query();
         LastTime = System.currentTimeMillis();
       }
     }
     for (int i = 0; i < set.size(); ++i) {
       ZDScheduleSchema s = set.get(i);
       if ((s.getTypeCode().equals(getCode())) && (s.getSourceID() == id)) {
         return s.getCronExpression();
       }
     }
     return null;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.platform.pub.ConfigEanbleTaskManager
 * JD-Core Version:    0.5.4
 */