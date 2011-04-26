<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>代码</title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<sky:init method="com.xdarkness.platform.Code.initList">
<script language="javascript">

</script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img
						src="../../Icons/icon018a1.gif" /> ${CodeType}: <b>${CodeName}</b> 的代码列表</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;"><sky:button onClick="add()"><img src="../../Icons/icon018a2.gif" />新建</sky:button> 
					<sky:button onClick="save()"><img src="../../Icons/icon018a4.gif" />保存</sky:button> 
					<sky:button onClick="del()"><img src="../../Icons/icon018a3.gif" />删除</sky:button>
					</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<sky:datagrid id="dg1"
						method="com.xdarkness.platform.Code.dg1BindCodeList">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" afterdrag="afterRowDragEnd">
							<tr xtype="head" class="dataTableHead">
								<td  width="5%" xtype="RowNo" drag="true"><img src="../../Framework/Images/icon_drag.gif" width="16" height="16"></td>
								<td width="5%" xtype="selector" field="id">&nbsp;</td>
								<td width="15%"><strong>代码值</strong></td>
								<td width="15%"><b>代码名称</b></td>
								<td width="20%"><strong>所属代码类别</strong></td>
								<td width="30%">备注</td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td >&nbsp;</td>
								<td>&nbsp;</td>
								<td>${CodeValue}</td>
								<td>${CodeName}</td>
								<td>${CodeType}</td>
								<td>${Memo}</td>
							</tr>
							<tr xtype="edit" bgcolor="#E1F3FF">
								<td  bgcolor="#E1F3FF">&nbsp;</td>
								<td>&nbsp;</td>
								<td><input name="CodeValue" type="text" id="CodeValue"
									value="${CodeValue}" size="10"></td>
								<td><input name="CodeName" type="text" id="CodeName"
									value="${CodeName}" size="10"></td>
								<td>${CodeType}</td>
								<td><input name="Memo" type="text" id="Memo"
									value="${Memo}" size="30"></td>
							</tr>
						</table></sky:datagrid></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</body>
</sky:init>
</html>
