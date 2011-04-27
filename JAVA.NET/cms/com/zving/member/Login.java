 package com.zving.member;
 
 import com.zving.cms.pub.SiteUtil;
 import com.zving.framework.Ajax;
 import com.zving.framework.Config;
 import com.zving.framework.CookieImpl;
 import com.zving.framework.RequestImpl;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.extend.ExtendManager;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.ServletUtil;
 import com.zving.framework.utility.StringUtil;
 import java.util.ArrayList;
 import java.util.Date;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 
 public class Login extends Ajax
 {
   private static ArrayList wrongList = new ArrayList();
 
   public static Mapx initSiteLinks(Mapx params) {
     String SiteID = params.getString("SiteID");
     String UserName = User.getUserName();
     String SiteLinks = "";
     if (StringUtil.isEmpty(SiteID)) {
       if (StringUtil.isEmpty(UserName)) {
         params.put("SiteLinks", "获取网站链接失败");
         return params;
       }
       Member member = new Member(UserName);
       member.fill();
       SiteID = member.getSiteID();
     }
 
     if ((User.isLogin()) && (User.isMember())) {
       params.put("display", "none");
     }
     String SiteAlias = SiteUtil.getAlias(SiteID);
     String Path = Config.getContextPath() + Config.getValue("UploadDir") + "/" + SiteAlias + "/";
     DataTable dt = new QueryBuilder("select * from zccatalog where Type = 1 and ParentID = 0 and SiteID = ?", 
       SiteID).executePagedDataTable(10, 0);
     SiteLinks = SiteLinks + "<a href='" + Path + "'>首页</a>&nbsp;&nbsp;&nbsp;";
 
     if ((dt != null) && (dt.getRowCount() > 0)) {
       for (int i = 0; i < dt.getRowCount(); ++i) {
         SiteLinks = SiteLinks + "<a href='" + Path + dt.getString(i, "URL") + "'>" + dt.getString(i, "Name") + 
           "</a>&nbsp;&nbsp;&nbsp;";
       }
     }
     params.put("SiteID", SiteID);
     params.put("SiteLinks", SiteLinks);
     return params;
   }
 
   public void doLogin() {
     Object authCode = User.getValue("_ZVING_AUTHKEY");
     String userName = $V("UserName");
     if ((wrongList.contains(userName)) && ((
       (authCode == null) || (!authCode.equals($V("VerifyCode")))))) {
       this.Response.setStatus(0);
       this.Response.setMessage("验证码输入错误");
       return;
     }
 
     String passWord = $V("PassWord");
 
     int cookieTime = 0;
     if (StringUtil.isNotEmpty($V("CookieTime")))
       cookieTime = Integer.parseInt($V("CookieTime"));
     else {
       cookieTime = 1800;
     }
 
     Member member = new Member(userName);
     if (member.isExists()) {
       if (!member.fill()) {
         this.Response.setLogInfo(0, "用户名或密码错误，请重新输入");
       }
       if (member.checkPassWord(passWord)) {
         String VerifyCookie = StringUtil.md5Hex(member.getPassword().substring(0, 10) + 
           System.currentTimeMillis());
         this.Cookie.setCookie("VerifyCookie", VerifyCookie, cookieTime);
         this.Cookie.setCookie("UserName", userName, cookieTime);
         User.setManager(false);
         User.setLogin(true);
         User.setUserName(userName);
         User.setRealName(member.getName());
         User.setType(member.getType());
         User.setMember(true);
         User.setValue("SiteID", member.getSiteID());
         User.setValue("UserName", member.getUserName());
         User.setValue("Type", member.getType());
         ExtendManager.executeAll("Member.Login", new Object[] { member });
         if (StringUtil.isNotEmpty(member.getName()))
           User.setValue("Name", member.getName());
         else {
           User.setValue("Name", member.getUserName());
         }
         member.setLoginMD5(VerifyCookie);
         member.setMemberLevel(
           new QueryBuilder(new StringBuffer("select ID from ZDMemberLevel where Score <= ")
           .append(member.getScore()).append(" order by Score desc").toString()).executeOneValue());
 
         member.setLastLoginIP(this.Request.getClientIP());
         member.setLastLoginTime(new Date());
         member.update();
         this.Response.setLogInfo(1, "登录成功，欢迎您 " + userName);
         this.Response.put("UserName", userName);
         this.Response.put("Name", member.getName());
         this.Response.put("Type", member.getType());
         synchronized (wrongList) {
           wrongList.remove(userName);
         }
       } else {
         this.Response.setLogInfo(0, "用户名或密码错误，请重新输入");
         if (!wrongList.contains(userName))
           synchronized (wrongList) {
             if (wrongList.size() > 20000) {
               wrongList.clear();
             }
             wrongList.add(userName);
           }
       }
     }
     else {
       this.Response.setLogInfo(0, "用户名不存在，请重新输入");
     }
   }
 
   public static void checkAndLogin(HttpServletRequest request) {
     String VerifyCookie = ServletUtil.getCookieValue(request, "VerifyCookie");
     String UserName = ServletUtil.getCookieValue(request, "UserName");
     if ((!User.isManager()) && (!User.isLogin()) && (StringUtil.isNotEmpty(UserName))) {
       Member member = new Member(UserName);
       if ((!member.fill()) || 
         (!StringUtil.isNotEmpty(member.getLoginMD5())) || (!member.getLoginMD5().equalsIgnoreCase(VerifyCookie))) return;
       User.setManager(false);
       User.setLogin(true);
       User.setUserName(member.getUserName());
       User.setRealName(member.getName());
       User.setType(member.getType());
       User.setMember(true);
       User.setValue("SiteID", member.getSiteID());
       User.setValue("UserName", member.getUserName());
       User.setValue("Type", member.getType());
 
       ExtendManager.executeAll("Member.Login", new Object[] { member });
 
       if (StringUtil.isNotEmpty(member.getName()))
         User.setValue("Name", member.getName());
       else {
         User.setValue("Name", member.getUserName());
       }
       member.setLoginMD5(VerifyCookie);
       member.setLastLoginIP(request.getRemoteAddr());
       member.setLastLoginTime(new Date());
       member.update();
     }
   }
 
   public void loginComment(HttpServletRequest request, HttpServletResponse response, String userName, String passWord)
   {
     Member member = new Member(userName);
     if (member.isExists()) {
       member.fill();
       if (member.checkPassWord(passWord)) {
         String VerifyCookie = StringUtil.md5Hex(member.getPassword().substring(0, 10) + 
           System.currentTimeMillis());
         this.Cookie = new CookieImpl(request);
         this.Cookie.setCookie("VerifyCookie", VerifyCookie, 0);
         this.Cookie.setCookie("UserName", userName, 0);
         this.Cookie.writeToResponse(request, response);
         User.setManager(false);
         User.setLogin(true);
         User.setUserName(userName);
         User.setRealName(member.getName());
         User.setType(member.getType());
         User.setMember(true);
         User.setValue("SiteID", member.getSiteID());
         User.setValue("UserName", member.getUserName());
         User.setValue("Type", member.getType());
 
         ExtendManager.executeAll("Member.Login", new Object[] { member });
 
         if (StringUtil.isNotEmpty(member.getName()))
           User.setValue("Name", member.getName());
         else {
           User.setValue("Name", member.getUserName());
         }
         member.setLoginMD5(VerifyCookie);
         member.setMemberLevel(
           new QueryBuilder(new StringBuffer("select ID from ZDMemberLevel where Score <= ")
           .append(member.getScore()).append(" order by Score desc").toString()).executeOneValue());
 
         member.setLastLoginIP(request.getRemoteAddr());
         member.setLastLoginTime(new Date());
         member.update();
       }
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.member.Login
 * JD-Core Version:    0.5.4
 */