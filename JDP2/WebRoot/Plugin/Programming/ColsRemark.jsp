<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>字段信息</title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<script>
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td valign="middle" class="blockTd"><img
					src="../../Icons/icon042a1.gif" width="20" height="20" />字段信息</td>
			</tr>
			<tr>
				<td style="padding:0 8px 4px;"><sky:button onClick="addBranch()">
						<img src="../../Icons/icon042a2.gif" width="20" height="20"/>新建</sky:button>
					<sky:button onClick="edit()">
						<img src="../../Icons/icon042a4.gif" width="20" height="20"/>编辑</sky:button>
					<sky:button onClick="del()">
						<img src="../../Icons/icon042a3.gif" width="20" height="20"/>删除</sky:button></td>
			</tr>
			<tr>
				<td
					style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<sky:datagrid id="dg1" method="admin.ColsRemark.dg1DataBind"
					page="false">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable" afterdrag="sortBranch">
						<tr xtype="head" class="dataTableHead">
							<td width="5%" xtype="RowNo" drag="true"><img
								src="../../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="4%" xtype="selector" field="BranchInnerCode">&nbsp;</td>
							<td width="6%"><b>别名</b></td>
							<td width="5%"><b>下拉选框</b></td>
							<td width="5%"><strong>多选框</strong></td>
							<td width="5%"><strong>最小值</strong></td>
							<td width="5%"><strong>最大值</strong></td>
							<td width="5%"><strong>动态绑定</strong></td>
							<td width="5%"><strong>多行</strong></td>
							<td width="5%"><strong>精度</strong></td>
							<td width="5%"><strong>时间格式</strong></td>
							<td width="10%"><strong>字段名</strong></td>
							<td width="5%"><strong>业务数据</strong></td>
							<td width="5%"><strong>必填项</strong></td>
							<td width="10%"><strong>校验表达式</strong></td>
							<td width="10%"><strong>开发者备注</strong></td>
							<td width="5%"><strong>启用日志跟踪</strong></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>${alias}</td>
							<td>${categoryid}</td>
							<td>${multiselected}</td>
							<td>${minvalue}</td>
							<td>${maxvalue}</td>
							<td>${dynamicbind}</td>
							<td>${multilines}</td>
							<td>${rigor}</td>
							<td>${fmttypetime}</td>
							<td>${colname}</td>
							<td>${openout}</td>
							<td>${required}</td>
							<td>${validexp}</td>
							<td>${devinfo}</td>
							<td>${tracelog}</td>
						</tr>
						<tr xtype="edit" bgcolor="#E1F3FF">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td><input type="text" value="${alias}"></td>
							<td>${categoryid}</td>
							<td>${multiselected}</td>
							<td>${minvalue}</td>
							<td>${maxvalue}</td>
							<td>${dynamicbind}</td>
							<td>${multilines}</td>
							<td>${rigor}</td>
							<td>${fmttypetime}</td>
							<td>${colname}</td>
							<td>${openout}</td>
							<td>${required}</td>
							<td>${validexp}</td>
							<td>${devinfo}</td>
							<td>${tracelog}</td>
						</tr>
					</table>
				</sky:datagrid></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>
