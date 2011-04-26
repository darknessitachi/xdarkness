<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Editor/fckeditor.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<sky:init method="com.xdarkness.cms.site.ReferConfig.init">
	<form id="form2">
	<table width="100%" border="0" align="center" cellpadding="4" cellspacing="" bordercolor="#DEDEDC" style="border-collapse:collapse;">
    	<tr>
			<td width="21%" align="right">&nbsp;</td>
			<td width="79%"><input type="hidden" id="New" name="New" value="${New}"/><input type="hidden" id="hCodeName" name="hCodeName" value="${CodeName}"/>&nbsp;</td>
		</tr>
		<tr>
			<td align="right">来源名称：</td>
			<td><input name="CodeName" id="CodeName" type="text" verify="NotNull" value="${CodeName}" style="width:180px;"/></td>
		</tr>
	</table>
	</form>
    </sky:init>
</body>
</html>
