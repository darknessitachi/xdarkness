<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>图片</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<body>
<sky:init>
	<sky:tab>
		<sky:childtab id="ImageBrowse" selected="true"
			src="ImageBrowse.jsp?_CatalogID=${CatalogID}&Search=${Search}&SelectType=${SelectType}">
			<img src="../Icons/icon030a11.gif" />
			<b>图片浏览</b>
		</sky:childtab>
		<sky:childtab id="ImageUpload" src="ImageUploadCover.jsp">
			<img src="../Icons/icon030a13.gif" />
			<b>上传专辑图片</b>
		</sky:childtab>
	</sky:tab>
</sky:init>
</body>
</html>
