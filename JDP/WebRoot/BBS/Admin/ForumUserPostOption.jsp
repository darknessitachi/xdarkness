<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../../Framework/Main.js"></script>
<script type="text/javascript">

</script>
</head>
<sky:init method="com.xdarkness.bbs.admin.ForumUserGroupOption.initPostOption">
<body>
<form id="form2" >
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				
				<tr>
					<td align="right">允许发帖:</td>
					<td>${AllowTheme}</td>
				</tr>
				<tr>
					<td align="right">允许回复:</td>
					<td>${AllowReply}</td>
				</tr>

				<tr>
					<td width="100" align="right">不审核发帖:</td>
					<td>${Verify}</td>
				</tr>

				
			</table>
			</td>
		</tr>
	</table>
</form>
</body>
</sky:init>
</html>