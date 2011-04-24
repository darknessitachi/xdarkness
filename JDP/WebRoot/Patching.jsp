<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=utf-8"%>
<%@page import="com.xdarkness.framework.*"%>
<%@page import="com.xdarkness.framework.utility.*"%>
<%@page import="com.xdarkness.platform.*"%>
<%@page import="com.xdarkness.framework.license.*"%>
<%
	com.xdarkness.platform.pub.Patch.checkUpdate();
	if (!Config.isNeedCheckPatch && !Config.isPatching) {
			RequestDispatcher rd = request.getRequestDispatcher("/");
			rd.forward(request, response);
	}
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=Config.getAppCode()%><%=Config.getAppName()%></title>
<link href="Include/Default.css" rel="stylesheet" type="text/css">
<style>
.input1 {
	border: 1px solid #84A1BD;
	width: 100px;
	height: 20px;
	line-height: 23px;
}

.input2 {
	border: 1px solid #84A1BD;
	width: 68px;
	height: 20px;
	line-height: 23px;
}

.button1 {
	border: none;
	width: 70px;
	height: 27px;
	line-height: 23px;
	color: #525252;
	font-size: 12px;
	font-weight: bold;
	background-image: url(images/bt001.jpg);
	background-repeat: no-repeat;
	background-position: 0px 0px;
}

.button2 {
	border: none;
	width: 70px;
	height: 27px;
	line-height: 23px;
	color: #525252;
	font-size: 12px;
	font-weight: bold;
	background-image: url(images/bt002.jpg);
	background-repeat: no-repeat;
	background-position: 0px 0px;
}

.STYLE7 {
	font-size: 20px;
	color: #FF6600;
}
</style>
<script src="Framework/Main.js"></script>
<script>
setTimeout(function(){
	window.location.reload();
},5000);
</script>
</head>
<body>
<form id="form1" method="post" style="display: block; height: 100%;">
<table width="100%" height="100%">
	<tr>
		<td align="center" valign="middle">
		<table
			style="height: 283px; width: 620px; background: url(Platform/Images/loginbg.jpg) no-repeat 0px 0px;">
			<tr>
				<td>
				<div style="height: 213px; width: 620px;"></div>
				<div style="height: 70px; width: 620px; margin: 0px auto 0px auto;">
				<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top: 8px;">
					<tr>
						<td height="36" align="center" valign="bottom"><span
							class="STYLE7">正在运行补丁升级程序，请稍等!</span></td>
					</tr>
				</table>
				</div>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>
