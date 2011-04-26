<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="com.xdarkness.zas.*"%>
<%@page import="com.xdarkness.zas.client.*"%>
<%@page import="com.xdarkness.framework.*"%>

<%
if(session.getAttribute(com.xdarkness.zas.Constant.UserSessionAttrName)!=null){
	UserData user = (UserData) session.getAttribute(com.xdarkness.zas.Constant.UserSessionAttrName);
	//PGTUtil.removePGT(user.getUserName());
	session.invalidate();
	String renew = ClientConfig.isNeedNewLogin() ? "&renew=" + ClientConfig.isNeedNewLogin() : "";
	response.sendRedirect(ClientConfig.getServerURL() + com.xdarkness.zas.Constant.LogoutPage + "?service=" + ZASFilter.getReferer(request) + renew);
}else{
	session.invalidate();
	response.sendRedirect(Config.getContextPath() + Config.getValue("App.LoginPage"));
}
%>
