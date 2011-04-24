<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<body>
<form id="form2"><sky:init
	method="com.xdarkness.platform.UserLog.initDialog">
	
			<table width="450" align="center" cellpadding="2" cellspacing="0">
            	<tr>
					<td width="114"  align="right"></td>
					<td width="466" ></td>
				</tr>
				<tr>
					<td width="114"  align="right" >用户名：</td>
					<td width="466" >${UserName}</td>
				</tr>
				<tr>
					<td  align="right" >姓名：</td>
					<td >${RealName}</td>
				</tr>
				<tr>
					<td  align="right" >时间：</td>
					<td >${AddTime}</td>
				</tr>
				<tr>
					<td  align="right" >消息类型：</td>
					<td >${LogType}</td>
				</tr>
				<tr>
					<td  align="right" >IP：</td>
					<td >${IP}</td>
				</tr>
				<tr>
					<td  align="right" >消息：</td>
					<td >${LogMessage}</td>
				</tr>
			</table>
</sky:init></form>
</body>
</html>
