<%@page import="com.xdarkness.framework.Config"%>
<%@page import="com.xdarkness.framework.User"%>
<%@page import="com.xdarkness.framework.data.QueryBuilder"%>
<%@page import="com.xdarkness.framework.utility.StringUtil"%>
<%
//判断是否登录
if(!User.isMember()||!User.isLogin()){
	String cSiteID = request.getParameter("SiteID");
	if(StringUtil.isEmpty(cSiteID)){
		cSiteID = new QueryBuilder("select ID from ZCSite order by AddTime desc").executeOneValue()+"";
	}
	out.println("<script>alert('您未登录，请登陆!');window.parent.location='"+Config.getContextPath()+"/Member/Login.jsp?SiteID="+cSiteID+"';</script>");
	return;
}
%>