 package com.zving.platform;
 
 import com.zving.framework.Page;
 import com.zving.framework.RequestImpl;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.data.DataColumn;
 import com.zving.framework.data.DataRow;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.Filter;
 import com.zving.framework.utility.HtmlUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.schema.ZDPrivilegeSchema;
 import java.util.Map;
 
 public class RoleTabCatalog extends Page
 {
   public static final Mapx PrivTypeMap = new Mapx();
   public static final Mapx CatalogTypeMap;
 
   static
   {
     PrivTypeMap.put("article", "文章栏目");
     PrivTypeMap.put("image", "图片栏目");
     PrivTypeMap.put("video", "视频栏目");
     PrivTypeMap.put("audio", "音频栏目");
     PrivTypeMap.put("attach", "附件栏目");
 
     CatalogTypeMap = new Mapx();
 
     CatalogTypeMap.put("article", 1);
     CatalogTypeMap.put("image", 4);
     CatalogTypeMap.put("video", 5);
     CatalogTypeMap.put("audio", 6);
     CatalogTypeMap.put("attach", 7);
   }
 
   public static Mapx init(Mapx params) {
     String roleCode = params.getString("RoleCode");
     DataTable dt = new QueryBuilder("select name,id from zcsite order by orderflag ,id").executeDataTable();
     dt = dt.filter(new Filter(roleCode) {
       public boolean filter(Object obj) {
         DataRow dr = (DataRow)obj;
         return RolePriv.getRolePriv(new String[] { (String)this.Param }, "site", dr.getString("ID"), 
           "site_browse");
       }
     });
     String SiteID = params.getString("SiteID");
     if (StringUtil.isEmpty(SiteID)) {
       SiteID = params.getString("OldSiteID");
       if (StringUtil.isEmpty(SiteID)) {
         SiteID = Application.getCurrentSiteID();
       }
     }
     params.put("SiteID", HtmlUtil.dataTableToOptions(dt, SiteID));
 
     String PrivType = params.getString("PrivType");
     if (StringUtil.isEmpty(PrivType)) {
       PrivType = params.getString("OldPrivType");
       if (StringUtil.isEmpty(PrivType)) {
         PrivType = "article";
       }
     }
     params.put("PrivType", HtmlUtil.mapxToOptions(PrivTypeMap, PrivType));
     return params;
   }
 
   public static void dg1DataBind(DataGridAction dga) {
     String roleCode = dga.getParam("RoleCode");
     String siteID = dga.getParam("SiteID");
     if (StringUtil.isEmpty(siteID)) {
       siteID = dga.getParam("OldSiteID");
       if (StringUtil.isEmpty(siteID)) {
         siteID = Application.getCurrentSiteID();
       }
       if (StringUtil.isNotEmpty(siteID)) {
         if (!RolePriv.getRolePriv(new String[] { roleCode }, "site", siteID, "site_browse")) {
           siteID = "";
         }
       }
     }
     if (StringUtil.isEmpty(siteID)) {
       DataTable dt = new QueryBuilder("select name,id from zcsite order by orderflag ,id").executeDataTable();
       dt = dt.filter(new Filter(roleCode) {
         public boolean filter(Object obj) {
           DataRow dr = (DataRow)obj;
           return RolePriv.getRolePriv(new String[] { (String)this.Param }, "site", dr.getString("ID"), 
             "site_browse");
         }
       });
       if (dt.getRowCount() > 0) {
         siteID = dt.getString(0, "ID");
       } else {
         dga.bindData(new DataTable());
         return;
       }
     }
 
     String PrivType = dga.getParam("PrivType");
     if (StringUtil.isEmpty(PrivType)) {
       PrivType = dga.getParam("OldPrivType");
       if (StringUtil.isEmpty(PrivType)) {
         PrivType = "article";
       }
     }
     StringBuffer sb = new StringBuffer();
     sb.append(",'" + roleCode + "' as RoleCode");
     Object[] ks = ((Mapx)Priv.PRIV_MAP.get(PrivType)).keyArray();
     for (int i = 0; i < ((Mapx)Priv.PRIV_MAP.get(PrivType)).size(); ++i) {
       sb.append(",'' as " + ks[i]);
     }
 
     String sql = "select ID,Name,0 as TreeLevel ,'site' as PrivType" + sb.toString() + 
       ",'' as ParentInnerCode from ZCSite a where a.ID = ?";
     DataTable siteDT = new QueryBuilder(sql, siteID).executeDataTable();
 
     Map PrivTypeMap = RolePriv.getPrivTypeMap(roleCode, "site");
     for (int i = 0; i < siteDT.getRowCount(); ++i) {
       DataRow dr = siteDT.getDataRow(i);
       Map mapRow = (Map)PrivTypeMap.get(siteDT.getString(i, "ID"));
       if (mapRow != null) {
         for (int j = 0; j < dr.getColumnCount(); ++j) {
           if (dr.getDataColumn(j).getColumnName().toLowerCase().indexOf("_") > 0) {
             Object o = mapRow.get(dr.getDataColumn(j).getColumnName().toLowerCase());
             dr.set(j, ("1".equals(o)) ? "√" : "");
           }
         }
       }
     }
     String catalogType = CatalogTypeMap.getString(PrivType);
     QueryBuilder qb = new QueryBuilder(
       "select InnerCode as ID,Name,TreeLevel ,'" + 
       PrivType + 
       "' as PrivType" + 
       sb.toString() + 
       ", (select b.InnerCode from ZCCatalog b where a.parentid=b.id) as ParentInnerCode from ZCCatalog a where Type = " + 
       catalogType + " and a.SiteID = " + siteID + " order by orderflag,innercode ");
     DataTable catalogDT = qb.executeDataTable();
     catalogDT = DataGridAction.sortTreeDataTable(catalogDT, "ID", "ParentInnerCode");
     PrivTypeMap = RolePriv.getPrivTypeMap(roleCode, PrivType);
     for (int i = 0; i < catalogDT.getRowCount(); ++i) {
       DataRow dr = catalogDT.getDataRow(i);
       Map mapRow = (Map)PrivTypeMap.get(dr.getString("ID"));
       if (mapRow != null) {
         for (int j = 0; j < dr.getColumnCount(); ++j) {
           if (dr.getDataColumn(j).getColumnName().toLowerCase().indexOf("_") > 0) {
             Object o = mapRow.get(dr.getDataColumn(j).getColumnName().toLowerCase());
             dr.set(j, ("1".equals(o)) ? "√" : "");
           }
         }
       }
     }
     catalogDT.insertRow(siteDT.getDataRow(0), 0);
     catalogDT = DataGridAction.sortTreeDataTable(catalogDT, "ID", "ParentInnerCode");
     dga.bindData(catalogDT);
   }
 
   public void dg1Edit() {
     DataTable dt = (DataTable)this.Request.get("DT");
     String RoleCode = $V("RoleCode");
     String PrivType = $V("PrivType");
     String SiteID = $V("SiteID");
     Transaction trans = new Transaction();
 
     ZDPrivilegeSchema p = new ZDPrivilegeSchema();
     QueryBuilder qb1 = new QueryBuilder("where OwnerType='R' and Owner=? and PrivType=? and exists (select '1' from zccatalog where innercode=ZDPrivilege.id and siteid=?)", 
       RoleCode, 
       PrivType);
     qb1.add(SiteID);
     trans.add(p.query(qb1), 5);
     QueryBuilder qb2 = new QueryBuilder("where OwnerType='R' and Owner=? and PrivType='site' and ID=?", 
       RoleCode, SiteID);
     qb2.append(" and code like ?", PrivType + "_%");
     trans.add(p.query(qb2), 5);
 
     for (int i = 0; i < dt.getRowCount(); ++i) {
       DataRow dr = dt.getDataRow(i);
       for (int j = 0; j < dr.getColumnCount(); ++j) {
         if (dr.getDataColumn(j).getColumnName().toLowerCase().indexOf("_") > 0) {
           ZDPrivilegeSchema priv = new ZDPrivilegeSchema();
           priv.setOwnerType("R");
           priv.setOwner(dr.getString("RoleCode"));
           priv.setID(dr.getString("ID"));
           priv.setPrivType(dr.getString("PrivType"));
           priv.setCode(dr.getDataColumn(j).getColumnName().toLowerCase());
           priv.setValue(("".equals(dr.getString(j))) ? "0" : "1");
           trans.add(priv, 1);
         }
       }
     }
     if (trans.commit()) {
       RolePriv.updateAllPriv(RoleCode);
       this.Response.setLogInfo(1, "修改成功!");
     } else {
       this.Response.setLogInfo(0, "修改失败!");
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.platform.RoleTabCatalog
 * JD-Core Version:    0.5.4
 */