 package com.zving.bbs;
 
 import com.zving.framework.Ajax;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataListAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.schema.ZCThemeSchema;
 import java.util.Date;
 
 public class MySubject extends Ajax
 {
   public static void getList(DataListAction dla)
   {
     String addTime = dla.getParams().getString("addtime");
     String orderBy = dla.getParams().getString("orderby");
     String ascdesc = dla.getParams().getString("ascdesc");
 
     QueryBuilder qb = new QueryBuilder(
       "select t.*, f.Name, p.ID PID from ZCTheme t, ZCForum f, ZCPost p  where p.ThemeID=t.ID and p.first='Y' and t.status='Y' and t.ForumID=f.ID and t.AddUser=?", 
       User.getUserName());
 
     if ((!StringUtil.isEmpty(addTime)) && (!"0".equals(addTime))) {
       Date date = new Date(new Date().getTime() - Long.parseLong(addTime));
       qb.append(" and t.addTime >?", date);
     }
 
     if (!StringUtil.isEmpty(orderBy))
       qb.append(" order by " + orderBy);
     else {
       qb.append(" order by OrderTime");
     }
 
     if ((!StringUtil.isEmpty(ascdesc)) && 
       ("DESC".equals(ascdesc))) {
       qb.append(" desc ");
     }
 
     DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
     dt.insertColumn("AuditStatus");
     dt.insertColumn("Operation");
     for (int i = 0; i < dt.getRowCount(); ++i) {
       if (dt.get(i, "VerifyFlag").equals("Y")) {
         dt.set(i, "AuditStatus", "正常");
         if (dt.get(i, "ApplyDel") == null)
           dt.set(i, "Operation", "<a href='javascript:applyDel(" + dt.get(i, "PID") + ")'>申请删除</a>");
         else
           dt.set(i, "Operation", "已申请删除");
       }
       else {
         dt.set(i, "AuditStatus", "待审核");
         dt.set(i, "Operation", "<cite><a href='javascript:editTheme(" + dt.get(i, "PID") + "," + 
           dt.get(i, "ForumID") + "," + dt.get(i, "ID") + 
           ")'>修改</a></cite> <em><a href='javascript:del(" + dt.get(i, "ID") + ")' >删除</a></em>");
       }
     }
     dla.setTotal(qb);
     dla.bindData(dt);
   }
 
   public static Mapx init(Mapx params) {
     params.put("AddUser", User.getUserName());
     params.put("BBSName", ForumUtil.getBBSName(params.getString("SiteID")));
     return params;
   }
 
   public void del() {
     Transaction trans = new Transaction();
     ZCThemeSchema theme = new ZCThemeSchema();
     theme.setID($V("ID"));
     theme.fill();
     trans.add(theme, 5);
     trans.add(new QueryBuilder("delete from zcpost where themeID=?", theme.getID()));
 
     if (trans.commit())
       this.Response.setLogInfo(1, "删除成功");
     else
       this.Response.setLogInfo(1, "操作失败");
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.bbs.MySubject
 * JD-Core Version:    0.5.4
 */