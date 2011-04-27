 package com.zving.cms.dataservice;
 
 import com.zving.cms.pub.CMSCache;
 import com.zving.cms.site.BadWord;
 import com.zving.framework.Ajax;
 import com.zving.framework.Config;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataListAction;
 import com.zving.framework.data.DataRow;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.member.Login;
 import com.zving.platform.pub.NoUtil;
 import com.zving.schema.ZCCatalogConfigSchema;
 import com.zving.schema.ZCCommentSchema;
 import java.io.PrintWriter;
 import java.util.Date;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 
 public class CommentService extends Ajax
 {
   public static Mapx init(Mapx params)
   {
     params.put("ServicesContext", Config.getValue("ServicesContext"));
     params.put("CommentActionURL", Config.getValue("CommentActionURL"));
     params.put("CommentCountJS", Config.getValue("CommentCountJS"));
     params.put("CommentListJS", Config.getValue("CommentListJS"));
     params.put("CommentListPageJS", Config.getValue("CommentListPageJS"));
     return params;
   }
 
   public static void dg1DataBind(DataListAction dla) {
     String relaID = dla.getParam("RelaID");
     if (dla.getTotal() == 0) {
       dla
         .setTotal(new QueryBuilder("select count(*) from ZCComment where verifyflag='Y' and relaID = ?", 
         relaID));
     }
     DataTable dt = new QueryBuilder("select * from ZCComment where verifyflag='Y' and relaID = ? order by ID desc", 
       relaID).executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
     for (int i = 0; i < dt.getRowCount(); ++i) {
       if (dt.get(i, "AntiCount") == null) {
         dt.set(i, "AntiCount", 0);
       }
       if (dt.get(i, "SupporterCount") == null) {
         dt.set(i, "SupporterCount", 0);
       }
     }
     dla.bindData(dt);
   }
 
   public static DataTable listDataBind(Mapx params, DataRow parentDR) {
     String relaID = params.getString("RelaID");
     String count = params.getString("Count");
     if (StringUtil.isEmpty(count)) {
       count = "5";
     }
     DataTable dt = new QueryBuilder("select * from ZCComment where verifyflag='Y' and relaID = ? order by ID desc", 
       relaID).executePagedDataTable(Integer.parseInt(count), 0);
 
     return dt;
   }
 
   public static void dealSubmit(HttpServletRequest request, HttpServletResponse response) throws Exception {
     String relaID = request.getParameter("RelaID");
     String title = request.getParameter("Title");
     String content = request.getParameter("CmntContent");
     PrintWriter out = response.getWriter();
 
     if (StringUtil.lengthEx(content) > 400) {
       out.print("<script type='text/javascript'>alert('评论内容不能超过200个字！');window.history.go(-1);</script>");
       return;
     }
     if (StringUtil.isNotEmpty(content)) {
       String catalogID = request.getParameter("CatalogID");
       String catalogType = request.getParameter("CatalogType");
       String siteID = request.getParameter("SiteID");
       String ip = request.getRemoteAddr();
       String anonymous = request.getParameter("CmntCheckbox");
       String parentID = request.getParameter("ParentID");
       String user = request.getParameter("CmntUserName");
       String password = request.getParameter("CmntPwd");
       if (User.isLogin()) {
         if ("on".equals(anonymous))
           user = "匿名网友";
         else
           user = User.getUserName();
       }
       else {
         QueryBuilder qb = null;
         qb = new QueryBuilder("select count(*) from ZDMember where UserName=? and Password=?");
         qb.add(user);
         qb.add(StringUtil.md5Hex(password).trim());
         if ("on".equals(anonymous)) {
           user = "匿名网友"; } else {
           if ((StringUtil.isEmpty(user)) || (StringUtil.isEmpty(password))) {
             out.print("<script type='text/javascript'>alert('请输入用户名和密码！');window.history.go(-1);</script>");
             return;
           }if (qb.executeString().equals("0")) {
             out.print("<script type='text/javascript'>alert('用户名或密码输入不正确！');window.history.go(-1);</script>");
             return;
           }if (qb.executeInt() > 0) {
             Login login = new Login();
             login.loginComment(request, response, user, password);
           }
         }
       }
       if (StringUtil.isEmpty(title)) {
         title = "无";
       }
 
       ZCCatalogConfigSchema catalogConfig = CMSCache.getCatalogConfig(catalogID);
       boolean verifyFlag = false;
       if ((catalogConfig != null) && 
         ("Y".equals(catalogConfig.getCommentVerify()))) {
         verifyFlag = true;
       }
 
       DataTable dt = new QueryBuilder("select * from ZCComment where ID = ?", parentID).executeDataTable();
       String CommentAddUser = "";
       String CommentAddTime = "";
       String CommentAddUserIp = "";
       String CommentContent = "";
       if (dt.getRowCount() > 0) {
         CommentAddUser = dt.getString(0, "AddUser");
         CommentAddTime = dt.getString(0, "AddTime");
         CommentAddUserIp = dt.getString(0, "AddUserIP");
         CommentContent = dt.getString(0, "Content");
       }
 
       ZCCommentSchema comment = new ZCCommentSchema();
       comment.setID(NoUtil.getMaxID("CommentID"));
       comment.setCatalogID(catalogID);
       comment.setCatalogType(catalogType);
       comment.setRelaID(relaID);
       comment.setSiteID(siteID);
       comment.setTitle(StringUtil.htmlEncode(BadWord.filterBadWord(StringUtil.subStringEx(title, 90))));
       if ((parentID != "") && (parentID != null))
       {
         comment.setContent("<div class=\"huifu\">" + CommentAddUser + " " + CommentAddTime + " IP:" + 
           CommentAddUserIp + "<br>" + CommentContent + "</div><br>" + StringUtil.htmlEncode(StringUtil.subStringEx(content, 900)));
       }
       else {
         comment.setContent(StringUtil.htmlEncode(StringUtil.subStringEx(content, 900)));
       }
       comment.setAddUser(user);
       comment.setAddTime(new Date());
       comment.setAddUserIP(ip);
       if (verifyFlag) {
         comment.setVerifyFlag("X");
       } else {
         boolean hasBadWord = false;
         if (StringUtil.isNotEmpty(BadWord.checkBadWord(content, "1"))) {
           hasBadWord = true;
         }
         if ((!hasBadWord) && 
           (StringUtil.isNotEmpty(BadWord.checkBadWord(content, "2")))) {
           hasBadWord = true;
         }
 
         if ((!hasBadWord) && 
           (StringUtil.isNotEmpty(BadWord.checkBadWord(content, "3")))) {
           hasBadWord = true;
         }
 
         if (hasBadWord)
           comment.setVerifyFlag("X");
         else {
           comment.setVerifyFlag("Y");
         }
       }
       if (comment.insert()) {
         if (verifyFlag)
           out.println("<script type='text/javascript'>alert('您的评论已经提交,请等待管理员审核');window.location='" + 
             request.getHeader("REFERER") + "';</script>");
         else {
           out.println("<script type='text/javascript'>alert('发表评论成功');window.location='" + 
             request.getHeader("REFERER") + "';</script>");
         }
       }
       else {
         out.println("<script type='text/javascript'>alert('发表评论失败');window.location='" + 
           request.getHeader("REFERER") + "';</script>");
       }
 
     }
     else
     {
       out.print("<script type='text/javascript'>alert('提交的内容不能空');window.location='" + 
         request.getHeader("REFERER") + "';</script>");
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.dataservice.CommentService
 * JD-Core Version:    0.5.4
 */