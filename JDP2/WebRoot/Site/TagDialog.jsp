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
<body class="dialogBody">
<sky:init method="com.xdarkness.cms.site.Tag.init">
<form id="form2">
<table width="100%" height="123" cellpadding="3" cellspacing="0">
	<tr>
		<td colspan="3" height="10"></td>
	</tr>
	<tr>
		<td width="12"></td>
		<td width="299" align="right">Tag名称：</td>
		<td width="664"><input name="Tag" type="text" value="" class="input1"
			id="Tag" verify="NotNull" size=25 /> <input name="ID"
			type="hidden" id="ID" value="${ID}" /></td>
	</tr>
	<tr>
		<td></td>
		<td align="right">Tag链接：</td>
		<td><input name="LinkURL" type="text"
			class="input1" id="LinkURL" value="" size="25" /></td>
	</tr>
	<tr>
		<td></td>
		<td height="26" align="right">Tag描述：</td>
		<td height="26"><input name="TagText" type="text"
			class="input1" id="TagText" value="" size="25" /></td>
	</tr>

	<tr>
		<td height="5" align="right"></td>
		<td height="5"></td>
	</tr>
</table>
</form>
</sky:init>
</body>
</html>
