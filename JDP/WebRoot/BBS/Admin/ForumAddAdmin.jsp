<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../../Framework/Main.js"></script>
<script src="../../Framework/Spell.js"></script>
</head>
<sky:init method="com.xdarkness.bbs.admin.ForumAdmin.initAddDialog">
<body>
<form id="form1">
	<table width="100%" border="0" cellspacing="6" cellpadding="0" >
		<tr valign="top">
			<td height="184">
			<table width="400" border="0" align="center" cellpadding="6" cellspacing="0" >
				<tr>
				  <td height="10" colspan="2" align="left"></td>
			  </tr>
				<tr>
					<td width="74" align="left">板块名称:</td>
					<td width="302" align="left"><input type="text" name="Name" id="Name" verify="NotNull" style="width: 100"/></td>
				</tr>
				<tr>
					<td align="left">上级分区:</td>
					<td><sky:select name="ParentID" id="ParentID"
						style="width:100px;"><span
						value="0"></span> ${ParentForum}</sky:select>					</td>
				</tr>
				<tr>
					<td align="left">板块介绍:</td>
					<td align="left"><textarea name="Info" id="Info" style="width:300px;height:100px"/></textarea></td>
				</tr>
			</table>
		  </td>
		</tr>
	</table>
</form>
</body>
</sky:init>
</html>