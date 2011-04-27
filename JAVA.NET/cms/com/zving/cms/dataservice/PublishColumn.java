 package com.zving.cms.dataservice;
 
 import com.zving.framework.Page;
 import com.zving.framework.utility.DateUtil;
 import com.zving.framework.utility.Mapx;
 import java.util.Date;
 
 public class PublishColumn extends Page
 {
   public static Mapx initSearch(Mapx params)
   {
     String dateStr = DateUtil.toString(new Date(), "yyyy-MM-dd");
     Mapx map = new Mapx();
     map.put("today", dateStr);
     return map;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.dataservice.PublishColumn
 * JD-Core Version:    0.5.4
 */