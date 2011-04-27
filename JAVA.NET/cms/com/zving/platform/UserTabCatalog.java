 package com.zving.platform;
 
 import com.zving.cms.pub.PubFun;
 import com.zving.framework.Page;
 import com.zving.framework.RequestImpl;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.data.BlockingTransaction;
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
 import com.zving.schema.ZDPrivilegeSet;
 import java.io.PrintStream;
 import java.util.List;
 
 public class UserTabCatalog extends Page
 {
   public static Mapx init(Mapx params)
   {
     String userName = params.getString("UserName");
     DataTable dt = new QueryBuilder("select name,id from zcsite order by orderflag ,id").executeDataTable();
     dt = dt.filter(new Filter(userName) {
       public boolean filter(Object obj) {
         DataRow dr = (DataRow)obj;
         return Priv.getPriv((String)this.Param, "site", dr.getString("ID"), "site_browse");
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
     params.put("PrivType", HtmlUtil.mapxToOptions(RoleTabCatalog.PrivTypeMap, PrivType));
     return params;
   }
 
   public static void dg1DataBind(DataGridAction dga) {
     String userName = dga.getParam("UserName");
     String siteID = dga.getParam("SiteID");
     if (StringUtil.isEmpty(siteID)) {
       siteID = dga.getParam("OldSiteID");
       if (StringUtil.isEmpty(siteID)) {
         siteID = Application.getCurrentSiteID();
       }
       if ((StringUtil.isNotEmpty(siteID)) && 
         (!Priv.getPriv(userName, "site", siteID, "site_browse"))) {
         siteID = "";
       }
     }
 
     if (StringUtil.isEmpty(siteID)) {
       DataTable dt = new QueryBuilder("select name,id from zcsite order by orderflag ,id").executeDataTable();
       dt = dt.filter(new Filter(userName) {
         public boolean filter(Object obj) {
           DataRow dr = (DataRow)obj;
           return Priv.getPriv((String)this.Param, "site", dr.getString("ID"), "site_browse");
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
 
     DataTable catalogDT = Priv.getCatalogPrivDT(userName, siteID, PrivType, true);
     catalogDT = DataGridAction.sortTreeDataTable(catalogDT, "ID", "ParentInnerCode");
     dga.bindData(catalogDT);
   }
 
   public void dg1Edit() {
     DataTable resultDT = (DataTable)this.Request.get("DT");
     Transaction trans = new BlockingTransaction();
     String UserName = $V("UserName");
     String PrivType = $V("PrivType");
     String SiteID = $V("SiteID");
     ZDPrivilegeSchema p = new ZDPrivilegeSchema();
     QueryBuilder qb1 = new QueryBuilder("where OwnerType='U' and Owner=? and PrivType=? and exists (select '1' from zccatalog where innercode=ZDPrivilege.id and siteid=?)", 
       UserName, 
       PrivType);
     qb1.add(SiteID);
     trans.add(p.query(qb1), 5);
     QueryBuilder qb2 = new QueryBuilder("where OwnerType='U' and Owner=? and PrivType='site' and ID=?", 
       UserName, SiteID);
     qb2.append(" and code like ?", PrivType + "_%");
     trans.add(p.query(qb2), 5);
     for (int i = 0; i < resultDT.getRowCount(); ++i) {
       for (int j = 0; j < resultDT.getColCount(); ++j) {
         if (resultDT.getDataColumn(j).getColumnName().toLowerCase().indexOf("_") > 0) {
           if ("√".equals(resultDT.getString(i, j)))
             resultDT.set(i, j, "1");
           else {
             resultDT.set(i, j, "0");
           }
         }
       }
     }
 
     String[] RoleCodes = new String[0];
     List roleCodeList = PubFun.getRoleCodesByUserName(UserName);
     if ((roleCodeList != null) && (roleCodeList.size() != 0)) {
       RoleCodes = (String[])roleCodeList.toArray(new String[roleCodeList.size()]);
     }
     DataColumn[] types = resultDT.getDataColumns();
     DataColumn[] copyTypes = new DataColumn[types.length];
     System.arraycopy(types, 0, copyTypes, 0, types.length);
     Object[][] copyValues = new Object[resultDT.getRowCount()][types.length];
     for (int i = 0; i < copyValues.length; ++i) {
       System.arraycopy(resultDT.getDataRow(i).getDataValues(), 0, copyValues[i], 0, types.length);
     }
     DataTable rolePrivDT = new DataTable(copyTypes, copyValues);
     for (int i = 0; (i < rolePrivDT.getRowCount()) && (i < 1); ++i) {
       for (int j = 0; j < rolePrivDT.getColCount(); ++j) {
         if (rolePrivDT.getDataColumn(j).getColumnName().toLowerCase().indexOf("_") > 0) {
           rolePrivDT.set(i, j, (RolePriv.getRolePriv(RoleCodes, "site", rolePrivDT.getString(i, "ID"), 
             rolePrivDT.getDataColumn(j).getColumnName().toLowerCase())) ? 
             "1" : "0");
         }
       }
     }
     for (int i = 1; i < rolePrivDT.getRowCount(); ++i) {
       for (int j = 0; j < rolePrivDT.getColCount(); ++j) {
         if (rolePrivDT.getDataColumn(j).getColumnName().toLowerCase().indexOf("_") > 0) {
           rolePrivDT.set(i, j, (RolePriv.getRolePriv(RoleCodes, PrivType, rolePrivDT.getString(i, "ID"), 
             rolePrivDT.getDataColumn(j).getColumnName().toLowerCase())) ? 
             "1" : "0");
         }
       }
 
     }
 
     String v1 = null;
     String v2 = null;
     for (int i = 0; i < rolePrivDT.getRowCount(); ++i) {
       for (int j = 0; j < rolePrivDT.getColCount(); ++j) {
         if (rolePrivDT.getDataColumn(j).getColumnName().toLowerCase().indexOf("_") > 0) {
           v1 = rolePrivDT.getString(i, j);
           v2 = resultDT.getString(i, j);
           if (v1.equals(v2)) {
             resultDT.set(i, j, "0");
           } else if (("0".equals(v1)) || (StringUtil.isEmpty(v1))) {
             if ("1".equals(v2))
               resultDT.set(i, j, "1");
           } else {
             if ((!"1".equals(v1)) || 
               (!"0".equals(v2))) continue;
             resultDT.set(i, j, "-1");
           }
         }
       }
     }
 
     ZDPrivilegeSet set = new ZDPrivilegeSet();
     for (int i = 0; i < resultDT.getRowCount(); ++i) {
       DataRow dr = resultDT.getDataRow(i);
       for (int j = 0; j < dr.getColumnCount(); ++j) {
         if (dr.getDataColumn(j).getColumnName().toLowerCase().indexOf("_") > 0) {
           if ("0".equals(dr.getString(j))) {
             continue;
           }
           ZDPrivilegeSchema priv = new ZDPrivilegeSchema();
           priv.setOwnerType("U");
           priv.setOwner(UserName);
           priv.setID(dr.getString("ID"));
           priv.setPrivType(dr.getString("PrivType"));
           priv.setCode(dr.getDataColumn(j).getColumnName().toLowerCase());
           priv.setValue(dr.getString(j));
           set.add(priv);
         }
       }
     }
     qb1 = new QueryBuilder("where OwnerType='U' and Owner=?", UserName);
     System.out.println(p.query(qb1));
     trans.add(set, 1);
     if (trans.commit()) {
       Priv.updateAllPriv(UserName);
       this.Response.setLogInfo(1, "修改成功!");
     } else {
       this.Response.setLogInfo(0, "修改失败!");
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.platform.UserTabCatalog
 * JD-Core Version:    0.5.4
 */