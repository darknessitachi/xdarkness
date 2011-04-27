 package com.xdarkness.cms.stat.impl;
 
 import com.xdarkness.cms.stat.AbstractStat;
import com.xdarkness.cms.stat.Visit;
import com.xdarkness.cms.stat.VisitCount;
 
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

          
/*    com.xdarkness.cms.stat.impl.DistrictStat
 * JD-Core Version:    0.6.0
 */