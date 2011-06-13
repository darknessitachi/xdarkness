<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>菜单</title>
	<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
	<script src="../../Framework/Main.js"></script>
	<script src="Menu.jsp.js"></script>
</head>
<body>
<sky:grid title="菜单列表" image="../../Icons/icon022a1.gif">
	<sky:toolbar>
		<sky:button onClick="MenuManager.add()" image="../../Icons/icon022a2.gif">新建</sky:button>
		<sky:button onClick="DataGrid.edit(event,'dg1')" image="../../Icons/icon022a4.gif">编辑</sky:button>
		<sky:button onClick="save()" image="../../Icons/icon022a16.gif">保存</sky:button>
		<sky:button onClick="discard()" image="../../Icons/icon400a8.gif">放弃</sky:button>
		<sky:button onClick="del()" image="../../Icons/icon022a3.gif">删除</sky:button>
	</sky:toolbar>
	<sky:datagrid id="dg1" method="com.xdarkness.platform.page.MenuPage.dg1DataBind" page="false">
		<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" afterdrag="sortMenu">
			<tr xtype="head" class="dataTableHead">
				<td width="3%" xtype="RowNo" drag="true"><img src="../../Framework/Images/icon_drag.gif" width="16" height="16"></td>
				<td width="3%" xtype="selector" field="id">&nbsp;</td>
				<td width="18%" xtype="tree" level="treelevel" >菜单名称</td>
				<td width="7%" xtype="checkbox" checkedvalue="Y" field="visiable">是否显示</td>
				<td width="7%" xtype="checkbox" checkedvalue="Y" field="expand">是否展开</td>
				<td width="28%">URL</td>
				<td width="17%">添加时间</td>
				<td width="17%">备注</td>
			</tr>
			<tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td><img src="../../${Icon}" align="absmiddle"/>&nbsp;${Name}</td>
				<td></td>
				<td>${Expand}</td>
				<td>${URL} </td>
				<td>${AddTime}</td>
				<td>${Memo}</td>
			</tr>
			<tr xtype="edit" bgcolor="#E1F3FF">
				<td>&nbsp;</td>
				<td bgcolor="#E1F3FF">&nbsp;</td>
				<td bgcolor="#E1F3FF"><img id="iconFile" src="../../${Icon}" align="absmiddle" border="1" onClick="selectIcon()"/>&nbsp;<input name="text" type="text" class="input1" id="Name" value="${Name}" size="16"></td>
				<td bgcolor="#E1F3FF"></td>
				<td bgcolor="#E1F3FF">${Expand}</td>
				<td><input name="text2" type="text" class="input1" id="URL" value="${URL}" size="40"></td>
				<td>${AddTime}</td>
				<td><input name="text2" type="text" class="input1" id="Memo" value="${Memo}" size="10"></td>
			</tr>
		</table>
	</sky:datagrid>
</sky:grid>

</body>
</html>
