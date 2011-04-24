<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<sky:init method="com.xdarkness.cms.site.KeywordType.initEditDialog">
<body class="dialogBody">
<form id="form2">
<table width="100%" cellpadding="3" cellspacing="0">
	<tr>
		<td width="12" colspan="3" height="10"></td>
	</tr>
	<tr>
		<td width="12"></td>
		<td align="right">类别名称：</td>
		<td>
			<input name="TypeName" type="text" value="${TypeName}" style="width:100px" class="input1" id="TypeName" verify="类别名称|NotNull" size=15 />
			<input type="hidden" id="cid" value="${ID}" />
		</td>
	</tr>
</table>
</form>
</body>
</sky:init>
</html>
