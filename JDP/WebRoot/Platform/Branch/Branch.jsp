<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>组织机构</title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<script src="Branch.jsp.js"></script>
</head>
<body>
<sky:grid title="组织机构树" image="../../Icons/icon042a1.gif">
	<sky:toolbar>
		<sky:button onClick="branchManager.onAdd()" image="../../Icons/icon042a2.gif" text="新建机构" />
		<sky:button onClick="branchManager.onEdit()" image="../../Icons/icon042a4.gif" text="编辑" />
		<sky:button onClick="branchManager.onDelete()" image="../../Icons/icon042a3.gif" text="删除" />
	</sky:toolbar>
	<sky:datagrid id="branch_DataGrid" method="com.xdarkness.platform.page.BranchPage.dataGridBind" page="false">
		<table width="100%" cellpadding="2" cellspacing="0"
			class="dataTable" afterdrag="sortBranch">
			<tr xtype="head" class="dataTableHead">
				<td width="5%" xtype="RowNo" drag="true"><img
					src="../../Framework/Images/icon_drag.gif" width="16" height="16"></td>
				<td width="4%" xtype="selector" field="BranchInnerCode">&nbsp;</td>
				<td width="25%" xtype="tree" level="TreeLevel">名称</td>
				<td width="10%">编码</td>
				<td width="20%">机构主管</td>
				<td width="18%">电话</td>
				<td width="18%">传真</td>
			</tr>
			<tr onDblClick="edit();">
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>${Name}</td>
				<td>${BranchCode}</td>
				<td>${ManagerName}</td>
				<td>${Phone}</td>
				<td>${Fax}</td>
			</tr>
		</table>
	</sky:datagrid>
</sky:grid>
</body>
</html>
