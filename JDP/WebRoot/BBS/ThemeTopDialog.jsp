<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
</head>
<body>
<sky:init method="com.xdarkness.bbs.Theme.initTopThemeDialog">
<form id="form1">
	<table width="100%" border="0" cellspacing="6" cellpadding="0" >
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" >
				<tr>
					<td align="left">选择：</td>
				</tr>
				<tr>
					<td align="left">${TopTheme}</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</form>
</sky:init>
</body>
</html>