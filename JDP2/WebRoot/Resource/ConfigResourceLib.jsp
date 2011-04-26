<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>媒体库配置</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<body>
<table width="100%" border="0" cellspacing="0" cellpadding="6">
	<tr>
		<td height="26"><sky:tab>
			<sky:childtab id="ImageLib" src="ConfigImageLib.jsp" selected="true">
				<img src='../Icons/icon014a7.gif' />
				<b>图片水印及缩略图</b>
			</sky:childtab>
			<sky:childtab id="Separate" src="ConfigResourceSeparate.jsp">
				<img src='../Icons/icon014a8.gif' />
				<b>分离部署设置</b>
			</sky:childtab>
		</sky:tab></td>
	</tr>
</table>
</body>
</html>
