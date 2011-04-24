<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>


<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<script src="../../Framework/Spell.js"></script>


</head>

<body>
<sky:init method="com.xdarkness.bbs.admin.ForumUserGroupOption.init">
	<sky:tab>
		<sky:childtab id="ForumUserBasic" src="ForumUserBasic.jsp?ID=${ID}" selected="true"><img src="../../Icons/icon002a1.gif" /><b>基本设置</b></sky:childtab>
		<sky:childtab id="ForumUserPostOption" src="ForumUserPostOption.jsp?ID=${ID}"><img src="../../Icons/icon002a1.gif" /><b>帖子相关</b></sky:childtab>
		<sky:childtab id="ForumManageBasic" src="ForumManageGroupBasic.jsp?ID=${ID}" ><img src="../../Icons/icon002a1.gif" /><b>管理权限</b></sky:childtab>
		<sky:childtab id="ForumUserThemeOption" src="ForumUserThemeOption.jsp?ID=${ID}"><img src="../../Icons/icon002a1.gif" /><b>主题相关</b></sky:childtab>
	</sky:tab>
</sky:init>
</body>
</html>
