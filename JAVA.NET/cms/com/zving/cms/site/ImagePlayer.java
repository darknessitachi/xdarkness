 package com.zving.cms.site;
 
 import com.zving.cms.pub.CatalogUtil;
 import com.zving.framework.Page;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.controls.TreeAction;
 import com.zving.framework.controls.TreeItem;
 import com.zving.framework.data.DataRow;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.Filter;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.platform.Priv;
 import com.zving.schema.ZCImagePlayerSchema;
 import com.zving.schema.ZCImagePlayerSet;
 import com.zving.schema.ZCImageRelaSchema;
 import com.zving.schema.ZCImageRelaSet;
 import java.util.List;
 
 public class ImagePlayer extends Page
 {
   public static void dg1DataBind(DataGridAction dga)
   {
     String InnerCode = dga.getParam("CatalogInnerCode");
     if ((StringUtil.isEmpty(InnerCode)) || (InnerCode == null) || (InnerCode.equalsIgnoreCase("null"))) {
       InnerCode = "0";
     }
     QueryBuilder qb = new QueryBuilder(
       "select ZCImagePlayer.*,(SELECT Name from ZCCatalog where ZCCatalog.InnerCode = ZCImagePlayer.RelaCatalogInnerCode) as CatalogName from ZCImagePlayer where SiteID=? ", 
       Application.getCurrentSiteID());
     if (!InnerCode.equals("0")) {
       qb.append(" and RelaCatalogInnerCode = ? ", InnerCode);
     }
     qb.append(" Order by RelaCatalogInnerCode asc ");
     DataTable dt = qb.executeDataTable();
     dt = dt.filter(new Filter() {
       public boolean filter(Object obj) {
         if (Priv.getPriv(User.getUserName(), "site", Application.getCurrentSiteID(), "site_manage")) {
           return true;
         }
         DataRow dr = (DataRow)obj;
         String RelaCatalogInnerCode = dr.getString("RelaCatalogInnerCode");
         if ("0".equals(RelaCatalogInnerCode)) {
           return Priv.getPriv(User.getUserName(), "site", Application.getCurrentSiteID(), 
             "article_manage");
         }
         return Priv.getPriv(User.getUserName(), "article", RelaCatalogInnerCode, "article_modify");
       }
     });
     DataTable newdt = new DataTable(dt.getDataColumns(), null);
     for (int i = dga.getPageIndex() * dga.getPageSize(); (i < dt.getRowCount()) && 
       (i < (dga.getPageIndex() + 1) * dga.getPageSize()); )
     {
       newdt.insertRow(dt.getDataRow(i));
 
       ++i;
     }
 
     for (int i = 0; i < newdt.getRowCount(); ++i) {
       if (StringUtil.isEmpty(newdt.getString(i, "CatalogName"))) {
         newdt.set(i, "CatalogName", "文档库");
       }
     }
     dga.bindData(newdt);
   }
 
   public static void treeDataBind(TreeAction ta) {
     Object obj = ta.getParams().get("SiteID");
     String siteID = Application.getCurrentSiteID();
     Object typeObj = ta.getParams().get("CatalogType");
     int catalogType = (typeObj != null) ? Integer.parseInt(typeObj.toString()) : 1;
     String parentTreeLevel = ta.getParams().getString("ParentLevel");
     String parentID = ta.getParams().getString("ParentID");
     DataTable dt = null;
     if (ta.isLazyLoad()) {
       QueryBuilder qb = new QueryBuilder(
         "select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel>? and innerCode like ? and  exists (select 1 from zcimageplayer where RelaCatalogInnerCode=ZCCatalog.innercode) order by orderflag,innercode");
 
       qb.add(catalogType);
       qb.add(siteID);
       qb.add(parentTreeLevel);
       qb.add(CatalogUtil.getInnerCode(parentID) + "%");
       dt = qb.executeDataTable();
     } else {
       QueryBuilder qb = new QueryBuilder(
         "select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel-1 <=? and  exists (select 1 from zcimageplayer where RelaCatalogInnerCode=ZCCatalog.innercode) order by orderflag,innercode");
 
       qb.add(catalogType);
       qb.add(siteID);
       qb.add(ta.getLevel());
       dt = qb.executeDataTable();
     }
 
     String siteName = "文档库";
     dt = dt.filter(new Filter() {
       public boolean filter(Object obj) {
         DataRow dr = (DataRow)obj;
         return Priv.getPriv(User.getUserName(), "article", dr.getString("InnerCode"), "article_manage");
       }
     });
     ta.setRootText(siteName);
     ta.bindData(dt);
     List items = ta.getItemList();
     for (int i = 1; i < items.size(); ++i) {
       TreeItem item = (TreeItem)items.get(i);
       if ("Y".equals(item.getData().getString("SingleFlag")))
         item.setIcon("Icons/treeicon11.gif");
     }
   }
 
   public void del()
   {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       this.Response.setStatus(0);
       this.Response.setMessage("传入ID时发生错误!");
       return;
     }
     Transaction trans = new Transaction();
     ZCImagePlayerSchema ImagePlayer = new ZCImagePlayerSchema();
     ZCImagePlayerSet set = ImagePlayer.query(new QueryBuilder("where id in (" + ids + ")"));
     trans.add(set, 5);
 
     ZCImageRelaSchema imageRela = new ZCImageRelaSchema();
     ZCImageRelaSet imageSet = imageRela.query(
       new QueryBuilder(" where relaid in (" + ids + ") and RelaType = ?", 
       "ImagePlayerImage"));
     trans.add(imageSet, 5);
 
     if (trans.commit()) {
       this.Response.setStatus(1);
       this.Response.setMessage("删除成功!");
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("操作数据库时发生错误!");
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.site.ImagePlayer
 * JD-Core Version:    0.5.4
 */