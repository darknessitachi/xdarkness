 package com.zving.shop;
 
 import com.zving.framework.Page;
 import com.zving.framework.RequestImpl;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.cache.CacheManager;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.HtmlUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.schema.ZCCommentSchema;
 import com.zving.schema.ZCCommentSet;
 import java.util.Date;
 
 public class Evaluation extends Page
 {
   public static Mapx init(Mapx params)
   {
     params.put("VerifyStatusOptions", HtmlUtil.codeToOptions("Comment.Status", false));
     return params;
   }
 
   public static Mapx initDetail(Mapx params) {
     String id = params.getString("ID");
     if (StringUtil.isNotEmpty(id)) {
       ZCCommentSchema comment = new ZCCommentSchema();
       comment.setID(id);
       if (comment.fill()) {
         params.putAll(comment.toMapx());
         params.put("VerifyFlag", CacheManager.getMapx("Code", "Comment.Status").get(params.get("VerifyFlag")));
         String addTimeStr = params.getString("AddTime");
         params.put("AddTime", addTimeStr.substring(0, addTimeStr.lastIndexOf(".")));
       }
     }
     return params;
   }
 
   public static void dg1DataBind(DataGridAction dga) {
     String VerifyStatus = dga.getParam("VerifyStatus");
     String CatalogID = dga.getParam("CatalogID");
 
     QueryBuilder qb = new QueryBuilder(
       "select ZCComment.*,(select Name from zccatalog where zccatalog.ID = ZCComment.CatalogID) as CatalogName, (select Name from ZSGoods where ZSGoods.ID = ZCComment.RelaID) as GoodsName from ZCComment where SiteID = ? and CatalogType = '9' ");
 
     qb.add(Application.getCurrentSiteID());
     if ((StringUtil.isNotEmpty(VerifyStatus)) && (!"All".equals(VerifyStatus))) {
       qb.append(" and VerifyFlag = ?", VerifyStatus);
     }
     if (StringUtil.isNotEmpty(CatalogID)) {
       qb.append(" and CatalogID = ? ", CatalogID);
     }
     dga.setTotal(qb);
     qb.append(" Order by VerifyFlag asc, ID desc");
 
     DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
     dt.insertColumn("PreLink");
     for (int i = 0; i < dt.getRowCount(); ++i) {
       if (dt.getString(i, "CatalogType").equals("9"))
         dt.set(i, "PreLink", "../Document/Preview.jsp?GoodsID=" + dt.getString(i, "RelaID"));
       else {
         dt.set(i, "PreLink", "#;");
       }
     }
     dt.decodeColumn("VerifyFlag", 
       new QueryBuilder("select CodeName,CodeValue from ZDCode where CodeType = 'Comment.Status'").executeDataTable().toMapx(1, 
       0));
     dga.bindData(dt);
   }
 
   public void Verify() {
     String ID = $V("ID");
     String Type = $V("Type");
     String IDs = $V("IDs");
     if ((StringUtil.isNotEmpty(ID)) && (StringUtil.isEmpty(IDs))) {
       ZCCommentSchema comment = new ZCCommentSchema();
       comment.setID(ID);
       comment.fill();
       if (Type.equals("Pass"))
         comment.setVerifyFlag("Y");
       else if (Type.equals("NoPass")) {
         comment.setVerifyFlag("N");
       }
       comment.setVerifyUser(User.getUserName());
       comment.setVerifyTime(new Date());
       if (comment.update())
         this.Response.setLogInfo(1, "审核成功");
       else
         this.Response.setLogInfo(0, "审核失败");
     }
     else if ((StringUtil.isNotEmpty(IDs)) && (StringUtil.isEmpty(ID))) {
       ZCCommentSchema comment = new ZCCommentSchema();
       if (!StringUtil.checkID(IDs)) {
         return;
       }
       ZCCommentSet set = comment.query(new QueryBuilder("where ID in (" + IDs + ")"));
       Transaction trans = new Transaction();
       for (int i = 0; i < set.size(); ++i) {
         comment = set.get(i);
         if (Type.equals("Pass"))
           comment.setVerifyFlag("Y");
         else if (Type.equals("NoPass")) {
           comment.setVerifyFlag("N");
         }
         comment.setVerifyUser(User.getUserName());
         comment.setVerifyTime(new Date());
         trans.add(comment, 2);
       }
       if (trans.commit())
         this.Response.setLogInfo(1, "审核成功");
       else
         this.Response.setLogInfo(0, "审核失败");
     }
   }
 
   public void del()
   {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       this.Response.setLogInfo(0, "传入ID时发生错误");
       return;
     }
     Transaction trans = new Transaction();
     ZCCommentSchema task = new ZCCommentSchema();
     ZCCommentSet set = task.query(new QueryBuilder("where id in (" + ids + ")"));
     trans.add(set, 5);
     if (trans.commit())
       this.Response.setLogInfo(1, "删除评论成功");
     else
       this.Response.setLogInfo(0, "删除评论失败");
   }
 
   public void addSupporterCount()
   {
     String ip = this.Request.getClientIP();
     String id = $V("ID");
 
     Transaction trans = new Transaction();
     ZCCommentSchema task = new ZCCommentSchema();
 
     task.setID(id);
     task.fill();
     String supportAntiIP = task.getSupportAntiIP();
     if ((StringUtil.isNotEmpty(supportAntiIP)) && (supportAntiIP.indexOf(ip) >= 0)) {
       this.Response.setMessage("您已经评论过，谢谢支持！");
       this.Response.put("count", task.getSupporterCount());
       return;
     }
 
     long count = task.getSupporterCount();
 
     task.setSupporterCount(count + 1L);
     task.setSupportAntiIP(((StringUtil.isEmpty(supportAntiIP)) ? "" : supportAntiIP) + ip);
     trans.add(task, 2);
     if (trans.commit()) {
       this.Response.setStatus(1);
       this.Response.setMessage("您的评论提交成功！");
       this.Response.put("count", count + 1L);
     } else {
       this.Response.setLogInfo(0, "审核失败");
     }
   }
 
   public void addAntiCount() {
     String ip = this.Request.getClientIP();
     String id = $V("ID");
 
     Transaction trans = new Transaction();
     ZCCommentSchema task = new ZCCommentSchema();
 
     task.setID(id);
     task.fill();
     String supportAntiIP = task.getSupportAntiIP();
     if ((StringUtil.isNotEmpty(supportAntiIP)) && (supportAntiIP.indexOf(ip) >= 0)) {
       this.Response.setMessage("您已经评论过，谢谢支持！");
       this.Response.put("count", task.getAntiCount());
       return;
     }
     long count = task.getAntiCount();
 
     task.setAntiCount(count + 1L);
     task.setSupportAntiIP(((StringUtil.isEmpty(supportAntiIP)) ? "" : supportAntiIP) + ip);
     trans.add(task, 2);
     if (trans.commit()) {
       this.Response.setStatus(1);
       this.Response.setMessage("您的评论提交成功！");
       this.Response.put("count", count + 1L);
     } else {
       this.Response.setLogInfo(0, "审核失败");
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.shop.Evaluation
 * JD-Core Version:    0.5.4
 */