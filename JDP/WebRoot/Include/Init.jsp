<%@ page contentType="text/html; charset=utf-8"%>
<%@page import="com.xdarkness.framework.Config"%>
<%@page import="com.xdarkness.framework.User"%>
<%@page import="com.xdarkness.platform.Priv"%>


<%
if(!User.isLogin() || !User.isManager()){
	out.println("<script>alert('用户会话己失效，请重新登陆!');window.parent.location=\""+Config.getContextPath()+Config.getValue("App.LoginPage")+"\";</script>");
	return;
}else if(!Priv.isValidURL(request.getServletPath())){
	out.println("<script>alert('您没有访问此页面的权限!请联系管理员.');window.parent.location=\""+Config.getContextPath()+Config.getValue("App.LoginPage")+"\";</script>");
	return;
}
response.setHeader("Pragma","No-Cache");
response.setHeader("Cache-Control","No-Cache");
response.setDateHeader("Expires", 0);
%>
