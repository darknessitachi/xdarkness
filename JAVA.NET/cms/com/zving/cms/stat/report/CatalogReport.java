 package com.zving.cms.stat.report;
 
 import com.zving.cms.pub.CatalogUtil;
 import com.zving.framework.Config;
 import com.zving.framework.Page;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.utility.DateUtil;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import java.io.PrintStream;
 import java.util.Date;
 
 public class CatalogReport extends Page
 {
   public void getChartData()
   {
     String code = $V("Code");
     if ((!StringUtil.verify(code, "Number")) || (StringUtil.isEmpty(code)) || (code.equals("null"))) {
       code = "";
     }
     Date start = DateUtil.parse($V("StartDate"));
     Date end = DateUtil.parse($V("EndDate"));
     DataTable dt = getCatalogHitData(Application.getCurrentSiteID(), start, end, code);
     dt.deleteColumn("StickTime");
     dt.deleteColumn("Item");
     Object[] vs = dt.getColumnValues("PV");
     dt.deleteColumn("PV");
     dt.insertColumn("PV", vs);
     String xml = ChartUtil.getPie3DChart(dt, 8);
     $S("Data", xml);
   }
 
   public static void dg1DataBind(DataGridAction dga) {
     String code = dga.getParam("Code");
     if ((!StringUtil.verify(code, "Number")) || (StringUtil.isEmpty(code)) || (code.equals("null"))) {
       code = "";
     }
     String startDate = dga.getParam("StartDate");
     String endDate = dga.getParam("EndDate");
     Date start = null;
     Date end = null;
     if (StringUtil.isEmpty(startDate)) {
       start = new Date(System.currentTimeMillis() - 2592000000L);
       end = new Date();
     } else {
       start = DateUtil.parse(startDate);
       end = DateUtil.parse(endDate);
     }
     DataTable dt = getCatalogHitData(Application.getCurrentSiteID(), start, end, code);
     dt.sort("PV");
 
     StringBuffer sb = new StringBuffer("''");
     for (int i = 0; i < dt.getRowCount(); ++i) {
       sb.append(",");
       sb.append(dt.getString(i, "Item"));
     }
     DataTable dt2 = new QueryBuilder("select InnerCode from ZCCatalog where isLeaf=0 and InnerCode in (" + sb + ")")
       .executeDataTable();
     for (int i = 0; i < dt.getRowCount(); ++i) {
       for (int j = 0; j < dt2.getRowCount(); ++j) {
         if (dt.getString(i, "Item").equals(dt2.getString(j, 0))) {
           dt.set(i, "ItemName", "<a href='Catalog.jsp?Code=" + dt.getString(i, "Item") + "'>" + 
             dt.getString(i, "ItemName") + "</a>");
         }
       }
     }
     ReportUtil.computeRate(dt, "PV", "Rate");
     ReportUtil.addSuffix(dt, "Rate", "%");
     ReportUtil.addTrend(dt, "Catalog", "PV");
     dga.bindData(dt);
   }
 
   public static DataTable getCatalogHitData(long siteID, Date start, Date end, String catalogInnerCode)
   {
     String period1 = DateUtil.toString(start, "yyyyMM");
     String period2 = DateUtil.toString(end, "yyyyMM");
     QueryBuilder qb = new QueryBuilder(
       "select * from ZCStatItem where SiteID=? and Type=? and SubType in ('PV','StickTime') and Period>=? and Period<=? and Item like '" + 
       catalogInnerCode + "%'");
     if (Config.isSQLServer())
       qb.append(" and len(Item)=?");
     else {
       qb.append(" and length(Item)=?");
     }
     qb.add(siteID);
     qb.add("Catalog");
     qb.add(period1);
     qb.add(period2);
     qb.add(catalogInnerCode.length() + 6);
     DataTable dt = qb.executeDataTable();
     dt = ReportUtil.toItemTable(dt, start, end);
     dt.sort("PV");
     dt.insertColumn("ItemName");
     for (int i = dt.getRowCount() - 1; i >= 0; --i) {
       String name = CatalogUtil.getNameByInnerCode(dt.getString(i, "Item"));
       if (StringUtil.isEmpty(name)) {
         QueryBuilder qb2 = new QueryBuilder(
           "select Name from BZCCatalog where InnerCode=? order by BackupNo desc", dt.getString(i, "Item"));
         name = qb2.executeString();
       }
       dt.set(i, "ItemName", name);
       if (dt.getInt(i, "PV") == 0) {
         dt.deleteRow(i);
       }
     }
     return dt;
   }
 
   public static void main(String[] args) {
     Date start = null;
     Date end = null;
     start = new Date(System.currentTimeMillis() - 2592000000L);
     end = new Date();
     System.out.println(getCatalogHitData(206L, start, end, ""));
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.stat.report.CatalogReport
 * JD-Core Version:    0.5.4
 */