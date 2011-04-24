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
<sky:grid title="组织机构树">
	<sky:toolbar>
	</sky:toolbar>
	<sky:datagrid>
	</sky:datagrid>
</sky:grid>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<tr>
				<td valign="middle" class="blockTd"><img
					src="../../Icons/icon042a1.gif" width="20" height="20" />组织机构树</td>
			</tr>
			<tr>
				<td style="padding:0 8px 4px;">
					<sky:button onClick="addBranch()" image="../../Icons/icon042a2.gif">新建机构</sky:button>
					<sky:button onClick="edit()" image="../../Icons/icon042a4.gif">编辑</sky:button>
					<sky:button onClick="del()" image="../../Icons/icon042a3.gif">删除</sky:button>
				</td>
			</tr>
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<sky:datagrid id="dg1" method="com.xdarkness.platform.page.BranchPage.dg1DataBind" page="false">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable" afterdrag="sortBranch">
						<tr xtype="head" class="dataTableHead">
							<td width="5%" xtype="RowNo" drag="true"><img
								src="../../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="4%" xtype="selector" field="BranchInnerCode">&nbsp;</td>
							<td width="25%" xtype="tree" level="TreeLevel"><b>名称</b></td>
							<td width="10%"><b>编码</b></td>
							<td width="20%"><strong>机构主管</strong></td>
							<td width="18%"><strong>电话</strong></td>
							<td width="18%"><strong>传真</strong></td>
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
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
