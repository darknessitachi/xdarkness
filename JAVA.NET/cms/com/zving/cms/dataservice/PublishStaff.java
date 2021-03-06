 package com.zving.cms.dataservice;
 
 import com.zving.cms.document.Article;
 import com.zving.framework.Page;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.utility.DateUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.schema.ZCCatalogSchema;
 import com.zving.schema.ZDUserSchema;
 import java.util.Date;
 
 public class PublishStaff extends Page
 {
   public static Mapx initStaff(Mapx params)
   {
     String dateStr = DateUtil.toString(new Date(), "yyyy-MM-dd");
     Mapx map = new Mapx();
     map.put("today", dateStr);
     return map;
   }
 
   public static Mapx initInputor(Mapx params) {
     String dateStr = DateUtil.toString(new Date(), "yyyy-MM-dd");
     Mapx map = new Mapx();
     map.put("today", dateStr);
     String username = params.getString("Username");
     ZDUserSchema user = new ZDUserSchema();
     user.setUserName(username);
     if (user.fill()) {
       map.putAll(user.toMapx());
     }
     map.put("Username", username);
     return map;
   }
 
   public static Mapx initInputorColumn(Mapx params) {
     String dateStr = DateUtil.toString(new Date(), "yyyy-MM-dd");
     Mapx map = new Mapx();
     map.put("today", dateStr);
     String username = params.getString("ColumnInputor");
     String catalogID = params.getString("CatalogID");
     String catalogInnerCode = params.getString("CatalogInnerCode");
     map.put("CatalogID", catalogID);
     map.put("CatalogInnerCode", catalogInnerCode);
     ZDUserSchema user = new ZDUserSchema();
     user.setUserName(username);
     if (user.fill()) {
       map.putAll(user.toMapx());
     }
     map.put("ColumnInputor", username);
 
     ZCCatalogSchema catalog = new ZCCatalogSchema();
     catalog.setID(catalogID);
     if (catalog.fill()) {
       map.put("CatalogName", catalog.getName());
     }
 
     return map;
   }
 
   public static void dg1DataBind(DataGridAction dga)
   {
     String startDate = dga.getParam("startDate");
     String endDate = dga.getParam("endDate");
 
     QueryBuilder qb = new QueryBuilder(
       "select b.UserName,b.RealName as Inputor,b.BranchInnerCode, (select name from zdbranch d where d.branchinnercode=b.branchinnercode) as branchname, sum(case when a.id is null then 0 else 1 end) as ArticleCount, sum(case when a.Status =0 then 1 else 0 end) as DraftCount, sum(case when a.Status =10 then 1 else 0 end) as WorkflowCount, sum(case when a.Status =20 then 1 else 0 end) as ToPublishCount, sum(case when a.Status =30 then 1 else 0 end) as PublishCount, sum(case when a.Status =40 then 1 else 0 end) as OfflineCount, sum(case when a.Status =50 then 1 else 0 end) as ArchiveCount from zduser b left outer join zcarticle a on a.AddUser=b.UserName   and a.SiteID=? ", 
       Application.getCurrentSiteID());
     if (StringUtil.isNotEmpty(startDate)) {
       qb.append(" and a.AddTime>?", DateUtil.parse(startDate, "yyyy-MM-dd"));
     }
     if (StringUtil.isNotEmpty(endDate)) {
       qb.append(" and a.AddTime<?", DateUtil.addDay(DateUtil.parse(endDate, "yyyy-MM-dd"), 1));
     }
     qb.append(" group by b.UserName,b.realname,b.branchinnercode order by b.BranchInnerCode");
 
     DataTable dt = qb.executeDataTable();
     dga.bindData(dt);
   }
 
   public static void dg11DataBind(DataGridAction dga)
   {
     String startDate = dga.getParam("startDate");
     String endDate = dga.getParam("endDate");
     String username = dga.getParam("Username");
 
     QueryBuilder qb = new QueryBuilder(
       "select a.ID,a.Name as CatalogName,a.InnerCode,a.TreeLevel,  (select orderflag from zccatalog where zccatalog.id=a.id) as orderflag, sum(case when b.id is null then 0 else 1 end) as ArticleCount, sum(case when b.Status =0 then 1 else 0 end) as DraftCount, sum(case when b.Status =10 then 1 else 0 end) as WorkflowCount, sum(case when b.Status =20 then 1 else 0 end) as ToPublishCount, sum(case when b.Status =30 then 1 else 0 end) as PublishCount, sum(case when b.Status =40 then 1 else 0 end) as OfflineCount, sum(case when b.Status =50 then 1 else 0 end) as ArchiveCount from zccatalog a left join zcarticle b on a.id=b.catalogid ");
 
     if (StringUtil.isNotEmpty(username)) {
       qb.append(" and b.adduser=?", username);
     }
     if (StringUtil.isNotEmpty(startDate)) {
       qb.append(" and b.AddTime>?", DateUtil.parse(startDate, "yyyy-MM-dd"));
     }
     if (StringUtil.isNotEmpty(endDate)) {
       qb.append(" and b.AddTime<?", DateUtil.addDay(DateUtil.parse(endDate, "yyyy-MM-dd"), 1));
     }
 
     qb.append(" where a.SiteID=? and a.Type='1' group by a.ID,a.Name,a.InnerCode,a.TreeLevel order by orderflag", Application.getCurrentSiteID());
     DataTable dt = qb.executeDataTable();
     dga.bindData(dt);
   }
 
   public static void dg12DataBind(DataGridAction dga)
   {
     String startDate = dga.getParam("startDate");
     String endDate = dga.getParam("endDate");
     String username = dga.getParam("ColumnInputor");
     String catalogInnerCode = dga.getParam("CatalogInnerCode");
 
     QueryBuilder qb = new QueryBuilder("select zcarticle.*,(select name from zccatalog where zccatalog.id=zcarticle.catalogid ) as CatalogName from zcarticle where SiteID=? ", 
       Application.getCurrentSiteID());
     if (StringUtil.isNotEmpty(username)) {
       qb.append(" and AddUser=?", username);
     }
     if (StringUtil.isNotEmpty(catalogInnerCode)) {
       qb.append(" and CatalogInnerCode like ?", catalogInnerCode + "%");
     }
 
     if (StringUtil.isNotEmpty(startDate)) {
       qb.append(" and AddTime>?", DateUtil.parse(startDate, "yyyy-MM-dd"));
     }
 
     if (StringUtil.isNotEmpty(endDate)) {
       qb.append(" and AddTime<?", DateUtil.addDay(DateUtil.parse(endDate, "yyyy-MM-dd"), 1));
     }
 
     qb.append(" order by CatalogInnerCode,OrderFlag desc");
     DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
     dt.decodeColumn("Status", Article.STATUS_MAP);
     dt.insertColumn("LastModifyTime");
     for (int i = 0; i < dt.getRowCount(); ++i) {
       if (StringUtil.isEmpty(dt.getString(i, "ModifyTime")))
         dt.set(i, "LastModifyTime", dt.get(i, "AddTime"));
       else {
         dt.set(i, "LastModifyTime", dt.get(i, "ModifyTime"));
       }
     }
     dga.setTotal(qb);
     dga.bindData(dt);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.dataservice.PublishStaff
 * JD-Core Version:    0.5.4
 */