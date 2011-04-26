<%@page import="com.xdarkness.platform.Application"%>
<%@page import="com.xdarkness.framework.User"%>
<%@page import="com.xdarkness.framework.data.QueryBuilder"%>
<%@page import="com.xdarkness.framework.utility.StringUtil"%>
<%@page import="com.xdarkness.cms.pub.SiteUtil"%>
<%
//判断是否登录
String cSiteID = request.getParameter("SiteID");
if(StringUtil.isEmpty(cSiteID)){
	out.println("document.write('未传入SiteID')");
	return;
}
if(User.isManager()||!User.isLogin()){
	StringBuffer sb = new StringBuffer();
	sb.append("document.write(\"<form id='formLoginVerify' name='formLoginVerify'>\");\n");
	sb.append("document.write(\"用户名：<input type='text' name='name' id='name' size='10'/>\");\n");
	sb.append("document.write(\"密码：<input type='password' name='code' id='code' size='10'/>&nbsp;\");\n");
	sb.append("document.write(\"<input type='button' class='btn' value='登录' onClick='doTopLogin()'/>&nbsp;\");\n");
	sb.append("document.write(\"<input type='button' value='注册' onClick='doTopRegister()' class='btn' />&nbsp;\");\n");
	//sb.append("document.write(\"[<a href='javascript:void(0);' onClick='goShopCart();'>购物车</a>]&nbsp;\");\n");
	sb.append("document.write(\"</form>\");\n");
	out.println(sb.toString());
} else {
	StringBuffer sb = new StringBuffer();
	sb.append("document.write(\"<div id='loginMenu2'>\");\n");
	sb.append("document.write(\"<b>欢迎 " + User.getRealName() + "</b>&nbsp;\");\n");
	sb.append("document.write(\"[<a href='javascript:void(0);' onClick='goMemberCenter();'>会员中心</a>]&nbsp;\");\n");
	/*if(SiteUtil.isShopEnable(cSiteID)){
		sb.append("document.write(\"[<a href='javascript:void(0);' onClick='goShopCart();'>购物车</a>]&nbsp;\");\n");
		sb.append("document.write(\"[<a href='javascript:void(0);' onClick='goFavorite();'>收藏夹</a>]&nbsp;</form>\");\n");
	}*/
	sb.append("document.write(\"[<a href='javascript:void(0);' onClick='doTopLogout();'>退出</a>]\");\n");
	sb.append("document.write(\"</div>\");\n");
	out.println(sb.toString());
}
%>