 package com.zving.cms.stat.report;
 
 import com.zving.framework.Page;
 import com.zving.framework.controls.DataListAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 
 public class LastVisitReport extends Page
 {
   public static void dg1DataBind(DataListAction dga)
   {
     QueryBuilder qb = new QueryBuilder("select * from ZCVisitLog where SiteID=? order by AddTime desc", 
       Application.getCurrentSiteID());
     DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
     dt.insertColumn("RowNo");
     for (int i = 0; i < dt.getRowCount(); ++i) {
       if (StringUtil.isEmpty(dt.getString(i, "District"))) {
         dt.set(i, "District", "未知地区");
       }
       if (StringUtil.isEmpty(dt.getString(i, "Language"))) {
         dt.set(i, "Language", "未知");
       }
       if (StringUtil.isEmpty(dt.getString(i, "FlashVersion"))) {
         dt.set(i, "Language", "未知");
       }
       if (StringUtil.isEmpty(dt.getString(i, "Referer"))) {
         dt.set(i, "Referer", "无(直接进入)");
       }
       dt.set(i, "RowNo", dga.getPageSize() * dga.getPageIndex() + i + 1);
     }
     dga.setTotal(qb);
     dga.bindData(dt);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.stat.report.LastVisitReport
 * JD-Core Version:    0.5.4
 */