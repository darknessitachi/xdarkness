 package com.zving.cms.document;
 
 import com.zving.framework.Page;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.DateUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.pub.NoUtil;
 import com.zving.schema.ZCArticleLogSchema;
 import com.zving.schema.ZCArticleLogSet;
 import java.util.Date;
 
 public class ArticleNote extends Page
 {
   public static Mapx init(Mapx params)
   {
     if ((StringUtil.isNotEmpty(params.getString("ID"))) && (StringUtil.isDigit(params.getString("ID")))) {
       ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
       articleLog.setID(params.getString("ID"));
       articleLog.fill();
       params.putAll(articleLog.toCaseIgnoreMapx());
     }
     return params;
   }
 
   public static void noteDataBind(DataGridAction dga) {
     String articleID = dga.getParam("ArticleID");
     QueryBuilder qb = new QueryBuilder(
       "select * from ZCArticlelog where action='NOTE' and articleID=? order by ID desc", articleID);
     dga.bindData(qb);
   }
 
   public static void logDataBind(DataGridAction dga) {
     String articleID = (String)dga.getParams().get("ArticleID");
     String userName = (String)dga.getParams().get("UserName");
     String startDate = (String)dga.getParams().get("startDate");
     String endDate = (String)dga.getParams().get("endDate");
     QueryBuilder qb = new QueryBuilder("select * from ZCArticlelog where action<>'NOTE' and articleID=? order by addtime", 
       articleID);
     if (StringUtil.isNotEmpty(userName)) {
       qb.append(" and AddUser=?", userName);
     }
     if (StringUtil.isNotEmpty(startDate)) {
       qb.append(" and AddTime>=?", startDate);
     }
     if (StringUtil.isNotEmpty(endDate)) {
       qb.append(" and AddTime<?", DateUtil.addDay(DateUtil.parse(endDate), 1));
     }
     dga.bindData(qb);
   }
 
   public void del() {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       this.Response.setStatus(0);
       this.Response.setMessage("操作数据库时发生错误!");
       return;
     }
     Transaction trans = new Transaction();
     ZCArticleLogSchema log = new ZCArticleLogSchema();
     ZCArticleLogSet set = log.query(new QueryBuilder("where id in (" + ids + ")"));
     trans.add(set, 5);
 
     if (trans.commit()) {
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("操作数据库时发生错误!");
     }
   }
 
   public void add() {
     Transaction trans = new Transaction();
     if ((StringUtil.isNotEmpty($V("Content"))) && ($V("Content").length() > 400)) {
       this.Response.setStatus(0);
       this.Response.setMessage("批注内容不能超过400个字!");
       return;
     }
 
     add(trans, $V("ArticleID"), $V("Content"));
     if (trans.commit()) {
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("操作数据库时发生错误!");
     }
   }
 
   public static void add(Transaction trans, String articleID, String content) {
     long id = Long.parseLong(articleID);
 
     ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
     articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
     articleLog.setArticleID(id);
     articleLog.setAction("NOTE");
     articleLog.setActionDetail(content);
     articleLog.setAddUser(User.getUserName());
     articleLog.setAddTime(new Date());
     trans.add(articleLog, 1);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.document.ArticleNote
 * JD-Core Version:    0.5.4
 */