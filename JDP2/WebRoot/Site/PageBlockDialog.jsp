<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<script src="../Framework/Controls/Tabpage.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<sky:init>
	<sky:tab>
		<sky:childtab id="Basic" src="PageBlockBasic.jsp?ID=${ID}"
			selected="true">
			<img src="../Icons/icon002a1.gif" width="20" height="20" />
			<b>基础信息</b>
		</sky:childtab>
		<sky:childtab id="Content" src="PageBlockContent.jsp?ID=${ID}">
			<img src="../Icons/icon002a7.gif" width="20" height="20" />
			<b>区块内容</b>
		</sky:childtab>
		<sky:childtab id="List" src="PageBlockList.jsp?ID=${ID}">
			<img src="../Icons/icon002a5.gif" width="20" height="20" />
			<b>自选列表</b>
		</sky:childtab>
	</sky:tab>
</sky:init>
</body>
</html>
