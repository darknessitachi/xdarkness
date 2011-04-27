 package com.zving.cms.stat.impl;
 
 import com.zving.cms.stat.AbstractStat;
 import com.zving.cms.stat.Visit;
 import com.zving.cms.stat.VisitCount;
 
 public class DistrictStat extends AbstractStat
 {
   private static final String Type = "District";
 
   public String getStatType()
   {
     return "District";
   }
 
   public void deal(Visit v)
   {
     VisitCount.getInstance().add(v.SiteID, "District", "PV", v.District);
     if (v.IPFlag) {
       VisitCount.getInstance().add(v.SiteID, "District", "IP", v.District);
     }
     if (v.UVFlag)
       VisitCount.getInstance().add(v.SiteID, "District", "UV", v.District);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.stat.impl.DistrictStat
 * JD-Core Version:    0.5.4
 */