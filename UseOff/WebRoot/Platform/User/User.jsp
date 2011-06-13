<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户</title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<script src="User.jsp.js"></script>
</head>
<body>
<sky:grid title="用户列表" image="../../Icons/icon021a1.gif">
	<sky:toolbar>
		<sky:button onClick="add()" image="../../Icons/icon021a2.gif" text="新建"/>
		<sky:button onClick="edit()" image="../../Icons/icon021a4.gif" text="编辑"/>
		<sky:button onClick="stopUser()" image="../../Icons/icon021a8.gif" text="停用"/>
		<sky:button onClick="del()" image="../../Icons/icon021a3.gif" text="删除"/>
		<sky:button onClick="setPriv()" image="../../Icons/icon021a9.gif" text="修改权限"/>			
		<div style="float: right; white-space: nowrap;">
		  <input name="SearchUserName" type="text" id="SearchUserName" value="请输入用户名或真实姓名" onFocus="delKeyWord();" style="width:150px">
          <input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="doSearch()">
        </div>
	</sky:toolbar>
	<sky:datagrid id="dg1" method="com.xdarkness.platform.page.UserListPage.dg1DataBind" size="15">
	     <table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
			<tr xtype="head" class="dataTableHead">
	   	        <td width="4%" xtype="RowNo">序号</td>
		        <td width="4%" xtype="selector" field="UserName">&nbsp;</td>
	            <td width="12%">用户名</td>
				<td width="10%">真实姓名</td>
				<td width="8%">用户状态</td>
				<td width="18%">所属机构</td>
                <td width="42%">所属角色</td>
		    </tr>
		    <tr onDblClick="edit(this)" style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
	          <td >&nbsp;</td>
		      <td>&nbsp;</td>
			  <td title="${UserName}">${UserName}</td>
			  <td title="${RealName}">${RealName}</td>
			  <td title="${StatusName}">${StatusName}</td>
			  <td title="${BranchInnercodeName}">${BranchInnercodeName}</td>
              <td title="${RoleNames}">${RoleNames}</td>
		    </tr>
			<tr xtype="pagebar">
			  <td colspan="10" align="center">${PageBar}</td>
			</tr>
		</table>
	</sky:datagrid>
</sky:grid>
</body>
</html>
