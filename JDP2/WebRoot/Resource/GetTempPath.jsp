<%@page import="com.xdarkness.cms.resource.Image"%>
<%
String Time = request.getParameter("t");
String Path = Image.pathMap.getString(Time);
out.write("ReturnPath('"+Path+"')");
%>