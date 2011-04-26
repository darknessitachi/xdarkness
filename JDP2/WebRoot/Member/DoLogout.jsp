<%@page import="com.xdarkness.framework.User"%>
<%
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	String SiteID = User.getValue("SiteID") + "";
	User.setUserName("");
	User.setLogin(false);
	User.setMember(false);
	User.destory();
	String flag = "Y";
	
	StringBuffer sb = new StringBuffer();
	sb.append("<form name='form'>");
	sb.append("用户名：<input type='text' id='name' size='10'/>");
	sb.append("密码：<input type='password' id='code' size='10'/>&nbsp;");
	sb.append("<input type='button' class='btn' value='登录' onClick='doLogin()'/>&nbsp;");
	sb.append("<input type='button' value='注册' onClick='doRegister()' class='btn' />&nbsp;");
	//sb.append("[<a href='javascript:void(0); onClick='goShopCart();'>购物车</a>]&nbsp;");
	
	response.getWriter().write(flag + "&" + sb.toString());
%>