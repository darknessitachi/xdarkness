<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<sky:init method="com.xdarkness.cms.site.CatalogColumn.initDialog">
	<form id="form2">
	<table width="100%" height="70" border="0" align="center"
		cellpadding="4" cellspacing="" bordercolor="#DEDEDC"
		style="border-collapse:collapse;">
		<tr>
			<td width="28%" height="10"></td>
			<td><input name="ColumnID" id="ColumnID" type="hidden"
				value="${ID}" /></td>
		</tr>
		<tr>
			<td align="right" valign="top">字段沿用</td>
			<td valign="top"><sky:select id="Extend" style="width:150px;">
				<span value="1">仅删除本栏目</span>
				<span value="2">删除所有子栏目沿用</span>
				<span value="3">删除所有栏目沿用</span>
			</sky:select></td>
		</tr>
	</table>
	</form>
</sky:init>
</body>
</html>
