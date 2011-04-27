 package com.zving.bbs;
 
 import com.zving.framework.Ajax;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataListAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 
 public class ThemeSearch extends Ajax
 {
   public static Mapx init(Mapx params)
   {
     String SiteID = params.getString("SiteID");
     if (StringUtil.isEmpty(User.getUserName())) {
       params.put("redirect", "<script>window.location='Index.jsp?SiteID=" + SiteID + "'</script>");
       return params;
     }
     ForumPriv priv = new ForumPriv(SiteID);
     if (!priv.hasPriv("AllowSearch")) {
       params.put("redirect", "<script>window.location='Index.jsp?SiteID=" + SiteID + "'</script>");
       return params;
     }
 
     params.put("AddUser", User.getUserName());
     params.put("Priv", ForumUtil.initPriv(params.getString("SiteID")));
     params.put("BBSName", ForumUtil.getBBSName(params.getString("SiteID")));
     return params;
   }
 
   public static void dg1DataBind(DataListAction dla) {
     String SiteID = dla.getParam("SiteID");
     ForumPriv priv = new ForumPriv(SiteID);
     if (!priv.hasPriv("AllowSearch")) {
       return;
     }
     String searchAddUser = dla.getParams().getString("SearchAddUser").replace('*', '%');
     String searchSubject = dla.getParams().getString("SearchSubject");
 
     QueryBuilder qb = new QueryBuilder("select * from ZCTheme where SiteID=? and Status='Y' ", SiteID);
     if (StringUtil.isNotEmpty(searchAddUser)) {
       qb.append(" and AddUser like ?", searchAddUser);
     }
     if (StringUtil.isNotEmpty(searchSubject)) {
       qb.append(" and Subject like ?", "%" + searchSubject + "%");
     }
     qb.append(" order by ID desc");
     DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
     dla.setTotal(qb);
     dla.bindData(dt);
   }
 
   public static Mapx initAddDialog(Mapx params) {
     return params;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.bbs.ThemeSearch
 * JD-Core Version:    0.5.4
 */