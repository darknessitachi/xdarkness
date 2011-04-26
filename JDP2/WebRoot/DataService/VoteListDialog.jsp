<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>调查</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" class="cellspacing" cellpadding="0">
  <tr>
    <td>
<sky:datagrid id="dg1" method="com.xdarkness.cms.dataservice.Vote.dg1DataBind" size="15" >
	<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
		<tr xtype="head" class="dataTableHead">
			<td width="5%" xtype="RowNo">序号</td>
			<td width="6%" align="center" xtype="selector" field="id">&nbsp;</td>
			<td width="6%"><b>ID</b></td>
			<td width="32%"><b>调查主题</b></td>
			<td width="12%"><b>限制IP</b></td>
			<td width="19%"><b>开始时间</b></td>
			<td width="20%"><b>截止时间</b></td>
		</tr>
		<tr style1="background-color:#FFFFFF"
			style2="background-color:#F9FBFC">
			<td align="center">&nbsp;</td>
			<td align="center">&nbsp;</td>
			<td>${ID}</td>
			<td>${Title}</td>
			<td>${IPLimitName}</td>
			<td>${StartTime}</td>
			<td>${EndTime}</td>
		</tr>
		<tr xtype="pagebar">
			<td colspan="9" align="center">${PageBar}</td>
		</tr>
  	</table>
  </sky:datagrid>		
</td>
  </tr>
</table>					
	</body>
</html>
