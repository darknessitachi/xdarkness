<%
String Services = Config.getValue("ServicesContext");
String SiteID = request.getParameter("SiteID");
String ADID   = request.getParameter("ADID");
String URL   = request.getParameter("URL");
com.xdarkness.cms.stat.impl.LeafStat.dealADClick(SiteID,ADID);
%>
<%@page import="com.xdarkness.framework.Config"%>
<script>
window.location = "<%=URL%>";
</script>