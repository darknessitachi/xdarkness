<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=utf-8"%> 
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="com.xdarkness.framework.*"%>
<%@page import="com.xdarkness.framework.utility.*"%>
<%@page import="com.xdarkness.framework.data.*"%>
<%@page import="com.xdarkness.framework.cache.*"%>
<%@page import="com.xdarkness.framework.orm.*"%>
<%@page import="com.xdarkness.framework.jaf.controls.*"%>
<%@page import="com.xdarkness.schema.*"%>
<%@page import="com.xdarkness.platform.*"%>
<%@page import="com.xdarkness.cms.site.*"%>
<%@page import="com.xdarkness.cms.document.*"%>
<%@page import="com.xdarkness.cms.dataservice.*"%>
<%@page import="com.xdarkness.cms.pub.*"%>
<%@page import="com.xdarkness.platform.pub.*"%>
<%
long id = Long.parseLong(request.getParameter("ArticleID"));
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
DataRow article  = dt.getDataRow(0);
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%=article.get("Title")%></title>
<style>
div{ line-height:1.5; font-size: 14px;}
div div{ padding:5px; background-color:#f9f9f9;border:#e6e6e6 1px solid;}
div div div{ padding:5px 10px; background-color:#f5f5f5; border:none;}
</style>
</head>
<body style="text-align:center; background:#fff">
<div style="margin:20px auto; width:70%; text-align:left;">
<div style="margin-bottom:10px;">当前位置：<%=PubFun.getCurrentPage(article.getLong("CatalogID"),0,article.getString("Name")," > ","")%>
</div>
<div>
<div style=" float:right; font-size:12px;">作者：<%=article.get("Author")%></div>
<div style="text-align:center; margin:0;"><b><%=article.get("Title")%></b></div>
<% if("4".equals(article.getString("Type"))){%>
<div style="padding:20px; margin:10px 0 0 0;">跳转链接：<a href="<%=article.get("RedirectURL")%>"><%=article.get("RedirectURL")%></a></div>
<% }else{%>
<div style="padding:20px; margin:10px 0 0 0;"><%=article.get("Content")%></div>
<% }%>
</div>
</div>
</body>
</html>

