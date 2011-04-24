<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<%
out.println(com.xdarkness.cms.stat.VisitCount.getInstance().toString().replaceAll("\\n","<br>"));
%>
