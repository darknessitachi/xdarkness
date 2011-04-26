<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<script src="BranchDialog.jsp.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<sky:init method="com.xdarkness.platform.page.BranchPage.initDialog">
<form id="form2">
	<table width="500" align="center" cellpadding="2" cellspacing="0">
		<tr>
			<td width="117" height="10"></td>
			<td width="373"></td>
		</tr>
		<tr>
			<td align="right">上级机构：</td>
			<td height="30"><sky:select id="ParentInnerCode"> ${ParentInnerCode} </sky:select></td>
		</tr>
		<tr>
			<td align="right">机构名称：</td>
			<td height="30"><input id="Name" name="Name" type="text"
				value="${Name}" class="input1" verify="机构名称|NotNull" size=36 /></td>
		</tr>
		<tr>
			<td align="right">机构编码：</td>
			<td height="30"><input id="BranchCode" name="BranchCode" type="text"
				value="${BranchCode}" class="input1" size=36/></td>
		</tr>
		<tr style="display:none">
			<td align="right">机构主管：</td>
			<td height="30"><input type="hidden" name="Manager"id="Manager">
      		<textarea name="ManagerName" style="height:30px; width:200px;"
					id="ManagerName" readonly/>${ManagerName}</textarea>
			<a href="javascript:void(0);" onClick="selectUser('Manager');">添加</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        	<a href="javascript:void(0);" onClick="clearUser('Manager');">清空</a></td>
		</tr>
		<tr style="display:none">
			<td align="right">上级主管领导：</td>
			<td height="30"><input type="hidden" name="Leader1"id="Leader1">
      		<textarea name="Leader1Name" style="height:30px; width:200px;"
					id="Leader1Name" readonly/>${Leader1Name}</textarea>
			<a href="javascript:void(0);" onClick="selectUser('Leader1');">添加</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        	<a href="javascript:void(0);" onClick="clearUser('Leader1');">清空</a></td>
		</tr>
		<tr style="display:none">
			<td align="right">上级分管领导：</td>
			<td height="30"><input type="hidden" name="Leader2"id="Leader2">
      		<textarea name="Leader2Name" style="height:30px; width:200px;"
					id="Leader2Name" readonly/>${Leader2Name}</textarea>
			<a href="javascript:void(0);" onClick="selectUser('Leader2');">添加</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        	<a href="javascript:void(0);" onClick="clearUser('Leader2');">清空</a></td>
		</tr>
		<tr>
			<td align="right">电 话：</td>
			<td height="30"><input id="Phone" name="Phone" type="text"
				value="${Phone}" class="input1" size=36 /></td>
		</tr>
		<tr>
			<td align="right">传 真：</td>
			<td height="30"><input id="Fax" name="Fax" type="text"
				value="${Fax}" class="input1" size=36 /></td>
		</tr>
	</table>
	</form>
</sky:init>
</body>
</html>
