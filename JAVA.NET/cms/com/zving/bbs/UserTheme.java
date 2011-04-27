 package com.zving.bbs;
 
 import com.zving.framework.Ajax;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataListAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.utility.LogUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import java.util.Date;
 
 public class UserTheme extends Ajax
 {
   public static void getList(DataListAction dla)
   {
     String addTime = dla.getParams().getString("addtime");
     String orderBy = dla.getParams().getString("orderby");
     String ascdesc = dla.getParams().getString("ascdesc");
 
     QueryBuilder qb = new QueryBuilder("select * from ZCTheme  where Status='Y' and VerifyFlag='Y' and AddUser=?", 
       dla.getParam("LastPoster"));
 
     if ((!StringUtil.isEmpty(addTime)) && (!"0".equals(addTime))) {
       Date date = new Date(new Date().getTime() - Long.parseLong(addTime));
       qb.append(" and addTime >?", date);
     }
 
     if (!StringUtil.isEmpty(orderBy)) {
       if (!StringUtil.checkID(orderBy)) {
         LogUtil.warn("可能的SQL注入,UserTheme.getList:" + orderBy);
         return;
       }
       qb.append(" order by " + orderBy);
       if ((!StringUtil.isEmpty(ascdesc)) && 
         ("DESC".equals(ascdesc)))
         qb.append(" desc ");
     }
     else {
       qb.append(" order by OrderTime desc");
     }
 
     DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
     dla.setTotal(qb);
     dla.bindData(dt);
   }
 
   public static Mapx init(Mapx params) {
     params.put("AddUser", User.getUserName());
     return params;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.bbs.UserTheme
 * JD-Core Version:    0.5.4
 */