 package com.zving.cms.stat;
 
 import com.zving.cms.stat.impl.GlobalStat;
 import com.zving.framework.schedule.GeneralTask;
 import com.zving.framework.utility.DateUtil;
 import java.util.Date;
 
 public class StatUpdateTask extends GeneralTask
 {
   private long LastUpdateTime = System.currentTimeMillis();
 
   public void execute() {
     long current = System.currentTimeMillis();
     VisitHandler.init(current);
     GlobalStat.dealVisitHistory(current);
     if (!DateUtil.toString(new Date(current)).equals(DateUtil.toString(new Date(this.LastUpdateTime))))
       VisitHandler.changePeriod(1, current);
     else {
       VisitHandler.update(System.currentTimeMillis(), false, false);
     }
     this.LastUpdateTime = current;
   }
 
   public long getID() {
     return 200812191853L;
   }
 
   public String getName() {
     return "定时更新CMS统计信息";
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.stat.StatUpdateTask
 * JD-Core Version:    0.5.4
 */