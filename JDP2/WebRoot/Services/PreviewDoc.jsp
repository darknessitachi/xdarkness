<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@page import="com.xdarkness.cms.template.*"%>
<%@ taglib uri="controls" prefix="sky"%>
<%
long id = Long.parseLong(request.getParameter("ArticleID"));
String cpage = request.getParameter("currentPage");
int currentPage = 1;
if(StringUtil.isNotEmpty(cpage)&&StringUtil.isDigit(cpage)){
	currentPage = Integer.parseInt(cpage);
	currentPage = currentPage>0 ? currentPage:1;
}
String type = request.getParameter("Type");
DataTable dt = null;
if("B".equals(type)){
	dt = new QueryBuilder("select * from BZCArticle where ID=? order by BackupNo desc",id).executeDataTable();
}else{
	dt = new QueryBuilder("select * from ZCArticle where ID=?",id).executeDataTable();
}
if(dt.getRowCount()==0){
	out.println("没有找到id为"+id+"的文章");
	return;
}

PageGenerator p = new PageGenerator();
String html = p.previewDoc("article", dt.getDataRow(0), currentPage);
out.print(html);
%>

