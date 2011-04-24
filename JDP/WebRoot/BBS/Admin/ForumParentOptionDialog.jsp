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
function userList(){
	var diag = new Dialog("Diag2");
	diag.Width = 750;
	diag.Height =400;
	diag.Title = "选择用户";
	diag.URL = "BBS/Admin/ForumUserInfo.jsp";
	diag.OKEvent = editSave;
	diag.show();
}
function editSave(){
	var arr = $DW.DataGrid.getSelectedValue("dg1");
	$("ForumAdmin").innerHTML = arr.join();
	$D.close();
}
</script>
</head>
<sky:init method="com.xdarkness.bbs.admin.ForumOption.basicInit">
<body>
<form id="form1" >
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<input type="hidden" value="${ID}" id="ID" >
				<tr>
					<td width="100" align="right">板块名称:</td>
					<td><input type="text" id="Name" verify="NotNull" value="${Name}"></td>
				</tr>
				<tr>
					<td align="right">设定版主:</td>
					<td><img onclick="userList()" src="../../Icons/icon022a16.gif">&nbsp;&nbsp;<span id="ForumAdmin">${ForumAdmin}</span></td>
				</tr>
			</table>
			</td>
			
		</tr>
	</table>
</form>
</body>
</sky:init>
</html>