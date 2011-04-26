<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function doSearch(){
	var name = $V("UserName").trim();
	var startDate=$V("startDate").trim();
	var endDate=$V("endDate").trim();
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","UserName",name);
	DataGrid.setParam("dg1","startDate",startDate);
	DataGrid.setParam("dg1","endDate",endDate);
	DataGrid.setParam("dg1","ArticleID",$V("ArticleID"));
	DataGrid.loadData("dg1");
}
</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<input type="hidden" name="AritcleID" id="ArticleID" value="<%=request.getParameter("ArticleID")%>">
<div style="padding:5px;white-space: nowrap;">
起始时间：<input xtype="date" type="text" size="15" value="" id="startDate" />&nbsp;&nbsp;
结束时间：<input xtype="date" type="text" size="15" value="" id="endDate" />&nbsp;&nbsp;
操作人员：<input name="UserName" type="text" id="UserName" value=""  style="width:100px">
<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="doSearch()">
</div>
<div style="padding:5px;"><sky:datagrid id="dg1" method="com.xdarkness.cms.document.ArticleNote.logDataBind">
	<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
		<tr xtype="head" class="dataTableHead">
			<td width="6%" xtype="RowNo"><strong>序号</strong></td>
			<td width="55%""><b>操作明细</b></td>
			<td width="19%"><strong>操作人</strong></td>
			<td width="22%"><strong>操作时间</strong></td>
		</tr>
		<tr style1="background-color:#FFFFFF"
			style2="background-color:#F9FBFC">
			<td>&nbsp;</td>
			<td>${ActionDetail}</td>
			<td>${addUser}</td>
			<td>${addTime}</td>
		</tr>
	</table>
</sky:datagrid></div>
</body>
</html>
