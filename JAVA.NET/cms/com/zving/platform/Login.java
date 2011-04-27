 package com.zving.platform;
 
 import com.zving.framework.Ajax;
 import com.zving.framework.Config;
 import com.zving.framework.CookieImpl;
 import com.zving.framework.RequestImpl;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.data.DataCollection;
 import com.zving.framework.data.DataRow;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.utility.Filter;
 import com.zving.framework.utility.LogUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.schema.ZDUserSchema;
 import com.zving.schema.ZDUserSet;
 import java.io.IOException;
 import java.util.ArrayList;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 
 public class Login extends Ajax
 {
   private static ArrayList wrongList = new ArrayList();
 
   public static void ssoLogin(HttpServletRequest request, HttpServletResponse response, String username) {
     if (username == null) {
       return;
     }
     ZDUserSchema user = new ZDUserSchema();
     user.setUserName(username);
     ZDUserSet userSet = user.query();
 
     if ((!Config.isAllowLogin) && (!username.equalsIgnoreCase("admin"))) {
       UserLog.log("Log", "Login", "临时禁止登录.用户名：" + username, request.getRemoteAddr(), username);
       return;
     }
 
     if ((userSet == null) || (userSet.size() < 1)) {
       UserLog.log("Log", "Login", "SSO登陆失败.用户名：" + username, request.getRemoteAddr(), username);
     }
     else {
       user = userSet.get(0);
       User.setUserName(user.getUserName());
       User.setRealName(user.getRealName());
       User.setBranchInnerCode(user.getBranchInnerCode());
       User.setType(user.getType());
       User.setValue("Prop1", user.getProp1());
       User.setValue("Prop2", user.getProp2());
       User.setValue("Prop3", user.getProp3());
       User.setValue("Prop4", user.getProp4());
       User.setManager(true);
       User.setLogin(true);
 
       UserLog.log("Log", "Login", "登录成功", request.getRemoteAddr());
 
       DataTable dt = new QueryBuilder("select name,id from zcsite order by BranchInnerCode ,orderflag ,id")
         .executeDataTable();
       dt = dt.filter(new Filter() {
         public boolean filter(Object obj) {
           DataRow dr = (DataRow)obj;
           return Priv.getPriv(User.getUserName(), "site", dr.getString("ID"), "site_browse");
         }
       });
       if (dt.getRowCount() > 0)
         Application.setCurrentSiteID(dt.getString(0, 1));
       else {
         Application.setCurrentSiteID("");
       }
       try
       {
         String path = request.getParameter("Referer");
         LogUtil.info("SSOLogin,Referer:" + path);
         if (StringUtil.isNotEmpty(path)) {
           if ((StringUtil.isNotEmpty(request.getParameter("t"))) && 
             (!"null".equalsIgnoreCase(request.getParameter("t"))))
             response.sendRedirect(path + "?t=" + request.getParameter("t"));
           else
             response.sendRedirect(path);
         }
         else
           response.sendRedirect("Application.jsp");
       }
       catch (IOException e) {
         e.printStackTrace();
       }
     }
   }
 
   public void submit() {
     String userName = $V("UserName").toLowerCase();
     if (wrongList.contains(userName)) {
       Object authCode = User.getValue("_ZVING_AUTHKEY");
       if ((authCode == null) || (!authCode.equals($V("VerifyCode")))) {
         this.Response.setStatus(0);
         this.Response.setMessage("验证码输入错误");
         return;
       }
     }
     String Password = StringUtil.md5Hex($V("Password"));
     ZDUserSchema user = new ZDUserSchema();
     user.setUserName(userName);
     ZDUserSet userSet = user.query();
 
     if ((!Config.isAllowLogin) && (!user.getUserName().equalsIgnoreCase("admin"))) {
       UserLog.log("Log", "Login", "临时禁止登录.用户名" + $V("UserName"), this.Request.getClientIP(), 
         $V("UserName"));
       this.Response.setStatus(0);
       this.Response.setMessage("临时禁止登录，请与系统管理员联系!");
       return;
     }
 
     if ((userSet == null) || (userSet.size() < 1))
     {
       UserLog.log("Log", "Login", "登陆失败.用户名：" + $V("UserName"), this.Request.getClientIP(), 
         $V("UserName"));
       this.Response.setStatus(0);
       this.Response.setMessage("用户名或密码输入错误");
     }
     else {
       user = userSet.get(0);
       if (!user.getPassword().equalsIgnoreCase(Password)) {
         this.Response.setStatus(0);
         this.Response.setMessage("用户名或密码输入错误");
         if (!wrongList.contains(userName)) {
           synchronized (wrongList) {
             if (wrongList.size() > 20000) {
               wrongList.clear();
             }
             wrongList.add(userName);
           }
         }
         return;
       }
       if ((!"admin".equalsIgnoreCase(user.getUserName())) && 
         ("S".equals(user.getStatus()))) {
         UserLog.log("Log", "Login", "登陆失败.用户名：" + $V("UserName") + "已停用", this.Request.getClientIP(), 
           $V("UserName"));
 
         this.Response.setStatus(0);
         this.Response.setMessage("该用户处于停用状态，请联系管理员！");
         return;
       }
       User.setUserName(user.getUserName());
       User.setRealName(user.getRealName());
       User.setBranchInnerCode(user.getBranchInnerCode());
       User.setType(user.getType());
       User.setValue("Prop1", user.getProp1());
       User.setValue("Prop2", user.getProp2());
       User.setValue("Prop3", user.getProp3());
       User.setValue("Prop4", user.getProp4());
       User.setManager(true);
       User.setLogin(true);
 
       UserLog.log("Log", "Login", "登陆成功", this.Request.getClientIP());
 
       DataTable dt = new QueryBuilder("select name,id from zcsite order by orderflag").executeDataTable();
       dt = dt.filter(new Filter() {
         public boolean filter(Object obj) {
           DataRow dr = (DataRow)obj;
           return Priv.getPriv(User.getUserName(), "site", dr.getString("ID"), "site_browse");
         }
       });
       String siteID = getCookie().getCookie("SiteID");
       if ((StringUtil.isNotEmpty(siteID)) && (Priv.getPriv(User.getUserName(), "site", siteID, "site_browse"))) {
         Application.setCurrentSiteID(siteID);
       }
       else if (dt.getRowCount() > 0)
         Application.setCurrentSiteID(dt.getString(0, 1));
       else {
         Application.setCurrentSiteID("");
       }
 
       this.Response.setStatus(1);
       synchronized (wrongList) {
         wrongList.remove(userName);
       }
       redirect("Application.jsp");
     }
   }
 
   public void getAllPriv() {
     getAllPriv(this.Response);
   }
 
   public static DataCollection getAllPriv(DataCollection Response) {
     if ("admin".equalsIgnoreCase(User.getUserName())) {
       Response.put("isBranchAdmin", "Y");
     } else {
       Response.put("isBranchAdmin", "N");
       StringBuffer privTypes = new StringBuffer();
       Object[] ks = Priv.PRIV_MAP.keyArray();
       for (int i = 0; i < Priv.PRIV_MAP.size(); ++i) {
         if ("menu".equals(ks[i])) {
           continue;
         }
         privTypes.append(ks[i].toString());
         privTypes.append(",");
       }
       privTypes.deleteCharAt(privTypes.length() - 1);
       Response.put("privTypes", privTypes.toString());
 
       Response.put("siteDT", Priv.getSitePrivDT(User.getUserName(), Application.getCurrentSiteID(), 
         "site"));
 
       for (int i = 0; i < Priv.PRIV_MAP.size(); ++i) {
         if ("menu".equals(ks[i]))
           continue;
         if ("site".equals(ks[i])) {
           continue;
         }
         Response.put(ks[i] + "DT", Priv.getCatalogPrivDT(User.getUserName(), Application.getCurrentSiteID(), 
           (String)ks[i]));
       }
     }
 
     return Response;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.platform.Login
 * JD-Core Version:    0.5.4
 */