<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<%
int type = Integer.parseInt(request.getParameter("Type"));
long siteID = Application.getCurrentSiteID();
String path = "";
path = com.xdarkness.framework.Config.getContextPath()+com.xdarkness.framework.Config.getValue("Statical.TargetDir")
		+"/"+SiteUtil.getAlias(siteID)+"/";
path = path.replaceAll("///","/");
path = path.replaceAll("//","/");
if(type==1){
	long catalogID = Long.parseLong(request.getParameter("CatalogID"));
	String link = CatalogUtil.getLink(catalogID,"");
	if(link.startsWith("http://") || link.startsWith("https://")){
	  path = link;
	}else{
		path += link;
	}
}else if(type==2){
	path += request.getParameter("File");
}else{
	path += "index.shtml";
}
response.sendRedirect(path+"?"+System.currentTimeMillis());
%>
