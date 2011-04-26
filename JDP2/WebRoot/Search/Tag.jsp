<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8" 
	import="com.xdarkness.search.*"
	import="com.xdarkness.cms.api.*" 
	import="com.xdarkness.framework.*"
	import="com.xdarkness.framework.utility.*"
	import="com.xdarkness.framework.data.*"
	import="java.util.*"
%>
<%@ taglib uri="controls" prefix="sky"%>
<%
	request.setCharacterEncoding(Constant.GlobalCharset);
    response.setContentType("text/html;charset=utf-8"); 
	
	String keyword = SearchAPI.getParameter(request,"keyword");
	String query = SearchAPI.getParameter(request,"query");
	if (StringUtil.isEmpty(query)) {
		query = keyword;//和旧版本保持一致
	}
	query = StringUtil.isEmpty(query)?"":query;

	String strpage = request.getParameter("page");
	strpage = StringUtil.isEmpty(strpage)?"1":strpage;
	
	String order = request.getParameter("order");
	order = StringUtil.isEmpty(order)?"rela":order;

	String time = request.getParameter("time");
	time = StringUtil.isEmpty(time)?"all":time;
	
	String site = request.getParameter("site");
	site = StringUtil.isEmpty(site)?"":site;

	String id = request.getParameter("id");
	id  = StringUtil.isEmpty(id)?"":id; 

	String size = request.getParameter("size");
	size  = StringUtil.isEmpty(size)?"10":size; 
	
	int pageSize = Integer.parseInt(size);
	int pageIndex = Integer.parseInt(strpage) - 1;
	
	Mapx map = ServletUtil.getParameterMap(request,false);
	map.put("query",query);
	map.put("page",pageIndex);
	map.put("size",pageSize);
	SearchResult r = ArticleSearcher.tagSearch(map);
	if(r==null){
		out.println("<br><br><br><b><font color='red'>全文检索索引未建立。</font></b>");
		out.println("<br><br>请在“站点管理”-“站点列表”中选择相应站点，开启“自动建立索引”选项。");
		return;
	}
	DataTable dt = r.Data;
	int total = r.Total;
	if(total<=0){
		out.println("<br><br><br><b><font color='red'>对不起，没有查询到匹配的结果，请设置其他关键字查询。</font></b>");
		return;
	}
	double usedTime = r.UsedTime;
%>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=utf-8">
<title>搜索结果_<%=query%></title>
<style>
<!--
body {font-size: 12px; font-family: "宋体";	background: #FFFFFF; margin: 0px; padding: 0px;	color: #444;}
a:link {color: #0066CC;	text-decoration: underline;}
a:visited {color: #0066CC; text-decoration: underline;}
a:hover {color: #CC0000; text-decoration: none;}
td {font-size: 12px; line-height: 18px;	color: #444;}
.f14 {font-size: 14px; text-align:left}
.f10 {font-size: 10.5pt}
.f16 {font-size: 16px; font-family: Arial}
.c {color: #7777CC;}
.p1 {line-height: 120%;	margin-left: -12pt}
.p2 {width: 100%; line-height: 120%;	margin-left: -12pt}
.i {font-size: 16px}
.t {color: #0000cc;text-decoration: none}
a.t:hover {text-decoration: underline}
.p {padding-left: 18px;	font-size: 14px; word-spacing: 4px;}
.f {line-height: 120%; font-size: 100%;	width: 32em; padding-left: 15px; word-break: break-all;	word-wrap: break-word;}
.h {margin-left: 8px; width: 100%}
.s {width: 8%; padding-left: 10px; height: 25px;}
.m,a.m:link {color: #666666; font-size: 100%;}
a.m:visited {color: #660066;}
.g {color: #008000;	font-size: 12px;}
.r {word-break: break-all; cursor: hand; width: 225px;}
.bi {margin-bottom: 12px}
.pl {padding-left: 3px;	height: 8px; padding-right: 2px; font-size: 14px;}
.Tit {height: 21px;	font-size: 14px;}
.fB {font-weight: bold;}
.mo,a.mo:link,a.mo:visited {color: #666666;	font-size: 100%; line-height: 10px;}
.htb {margin-bottom: 5px;}
#ft {clear: both; background: #E6E6E6; text-align: center; color: #777;	font-size: 12px; font-family: Arial; line-height: 24px;	width: 100%;}
#ft span {color: #666}
.g1 {margin-top: 1em; margin-bottom: 0em; text-align:left}
.l {font-size: 16px; font-family: Verdana, Geneva, sans-serif;}
.j {line-height: 22px}
#mleft {float: left;}
.STYLE13 {color: #336600;}
-->
</style>
<script>
var query = "<%=query%>";
var order = "<%=order%>";
var time = "<%=time%>";
var site = "<%=site%>";
var id = "<%=id%>";
function searchInResult(){
	if(document.getElementById("query").value!=query){
		document.getElementById("query").value  = query+" "+document.getElementById("query").value;
	}
	document.getElementById("search").submit();
}
function searchInResult2(){
	if(document.getElementById("query2").value!=query){
		document.getElementById("query2").value  = query+" "+document.getElementById("query2").value;
	}
	document.getElementById("search2").submit();
}
</script>
<%@ include file="../Include/Head.jsp" %>
</head>
<body link="#261CDC">
<%@ include file="../Include/Top.jsp" %>
<div class="bodyWidth">
  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="bi">
	<tr>
		<td width="81%" valign="bottom" nowrap>&nbsp;
		<div id=div1
			style="background-color: #eee; border-top: 1px solid #ddd; border-bottom: 1px solid #ddd; CLEAR: right; Z-INDEX: 20; FLOAT: left; WIDTH: 100%; HEIGHT: 24px; FONT-SIZE: 13px;">
		<div style="padding: 0 1em; border-top: 1px solid #fff;">含有Tag &nbsp;<font color=red><%=query%></font>&nbsp;的文章：</div>
		</div>
		</td>
	</tr>
</table>
  <div id="mleft" style="width: 680px; padding: 20px;padding-top:0px; text-align:left">
<div>
 <%
	for (int i = 0; i < dt.getRowCount(); i++) {
		DataRow dr = dt.getDataRow(i);
%>
	<p class=g1><a class=l href="<%=dr.getString("URL")%>" target=_blank><%=dr.getString("Title")%></a>&nbsp;&nbsp;(Tag：<font color="red"><%=dr.getString("Tag")%></font>)
	<table cellpadding=0 cellspacing=0 border=0>
		<tr>
			<td class=j><span style="font-size: 13px;"><%=dr.getString("Content")%>...<br>
			<span style="color: #875"><%=dr.getString("URL")%> - <%=dr.getString("PublishDate")%></span></span></td>
		</tr>
	</table>
<%
	}
%>
</div>
</div>
<br clear=all>
<table style="margin-left: 20px;">
	<tr>
		<td nowrap><span style="font-size: 13px;">页码:&nbsp;</span></td>
		<%
			int pageCount = new Double(Math.ceil(total * 1.0 / pageSize)).intValue();
			int start = pageIndex - 9 < 1 ? 1 : pageIndex - 9;
			int end = pageIndex + 9 > pageCount ? pageCount : pageIndex + 9;
			if (pageIndex > 1) {
				out.println(" <td nowrap><a href='"+ SearchAPI.getPageURL(map, pageIndex)+ "'>上一页</a></td>");
			}
			for (int i = start; i <= end; i++) {
				if (i == pageIndex + 1) {
					out.println("<td nowrap><span class=i>" + i	+ "</span></td>");
				} else {
					out.println("<td nowrap><a href='"+ SearchAPI.getPageURL(map, i) + "'>[" + i+ "]</a></td>");
				}
			}
			if (pageIndex < pageCount - 1) {
				out.println(" <td nowrap><a href='"+ SearchAPI.getPageURL(map, pageIndex + 2)+ "'>下一页</a></td>");
			}
		%>
	</tr>
</table>
</div>
<%@ include file="../Include/Bottom.jsp" %>
<script src="<%=Config.getContextPath()%>Services/Stat.js" type="text/javascript"></script>
<script type="text/javascript">
_zcms_stat("SiteID=<%=site%>&Type=Article&Dest=<%=Config.getContextPath()%>Services/Stat.jsp");
</script>
</body>
</html>
