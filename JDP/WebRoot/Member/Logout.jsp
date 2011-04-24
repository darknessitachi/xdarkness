<%@page import="com.xdarkness.framework.*"%>
<% 
	String SiteID = request.getParameter("SiteID");
	session.invalidate();
	response.sendRedirect(Config.getContextPath() + "Member/Login.jsp?SiteID="+SiteID);
%>