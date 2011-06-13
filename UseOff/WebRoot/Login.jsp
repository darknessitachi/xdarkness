<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html; charset=utf-8"%>
<%@page import="com.abigdreamer.java.net.jaf.WebConfig"%>
<%@page import="com.abigdreamer.java.net.util.DateUtil"%>
<%@page import="com.abigdreamer.java.net.license.LicenseInfo"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=WebConfig.getAppCode()%><%=WebConfig.getAppName()%></title>
<link href="Include/Default.css" rel="stylesheet" type="text/css">
<style>
.input1{ border:1px solid #84A1BD; width:100px; height:20px; line-height:23px;}
.input2{ border:1px solid #84A1BD; width:68px; height:20px; line-height:23px;}
.button1{
	border:none;
	width:70px;
	height:27px;
	line-height:23px;
	color:#525252;
	font-size:12px;
	font-weight:bold;
	background-image: url(images/bt001.jpg);
	background-repeat: no-repeat;
	background-position: 0px 0px;
}
.button2{
	border:none;
	width:70px;
	height:27px;
	line-height:23px;
	color:#525252;
	font-size:12px;
	font-weight:bold;
	background-image: url(images/bt002.jpg);
	background-repeat: no-repeat;
	background-position: 0px 0px;
}
.STYLE3 {
	color: #FF0000;
	font-weight: bold;
}
</style>
<script id="jsFramework" src="Framework/Main.js" charset="UTF-8"></script>
<script src="Login.jsp.js"></script>
</head>
<body>
<form id="form1" method="post" style=" display:block;height:100%;">
<table width="100%" height="100%">
	<tr>
		<td align="center" valign="middle">
		<table
			style=" height:283px; width:620px; background:url(Platform/Images/loginbg.jpg) no-repeat 0px 0px;">
			<tr>
				<td>
				<div style="height:233px; width:620px;"></div>
				<div style="height:70px; width:620px;margin:0px auto 0px auto;">
				<table width="95%" border="0" align="center" cellpadding="0"
					cellspacing="0" style="margin-top:8px;">
					<tr>
						<td align="center">用户名：
					    <input name="UserName" type="text" style="width:120px"
							id="UserName" class="inputText" onfocus="this.select();"/>
					    &nbsp;密码：
					    <input name="Password" type="password" style="width:120px"
							id="Password" class="inputText" onfocus="this.select();"/>
					    <span id='spanVerifyCode'></span>	
						&nbsp;<img src="Platform/Images/loginbutton.jpg" name="LoginImg" align="absmiddle" id="LoginImg" style="cursor:pointer"
							onClick="login();" /></td>
					</tr>
					<tr>
						<td height="40" align="center" valign="bottom">Copyright
						&copy; 2007-2010 xds.com Inc. All rights reserved. XDS软件 版权所有</td>
					</tr>
				</table>
				</div>
				</td>
			</tr>
		</table>
		<br>
		<%
			if(LicenseInfo.needWarning()){
		%> 请注意，当前许可证将于 <span class="STYLE3"><%=DateUtil.toString(LicenseInfo.getEndDate())%></span>
		失效！ <a href="LicenseRequest.jsp">点击此处获得新的许可证。</a> 
		<%}%>
		</td>
	</tr>
</table>
</form>
</body>
</html>
