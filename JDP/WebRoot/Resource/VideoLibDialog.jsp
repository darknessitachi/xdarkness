<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<body>
<sky:init>
	<sky:tab>
		<sky:childtab id="VideoUpload"
			src="VideoUpload.jsp?CatalogID=${CatalogID}" selected="true">
			<img src="../Icons/icon012a13.gif" />
			<b>视频上传</b>
		</sky:childtab>
		<sky:childtab id="VideoBrowse" src="VideoBrowse.jsp?CatalogID=${CatalogID}&Search=${Search}">
			<img src="../Icons/icon012a11.gif" />
			<b>视频浏览</b>
		</sky:childtab>
	</sky:tab>
</sky:init>
</body>
</html>
