<%@page import="com.xdarkness.framework.*"%>
<% 
	session.invalidate();
	response.sendRedirect(Config.getContextPath() + Config.getLoginPage());
%>