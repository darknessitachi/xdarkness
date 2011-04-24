<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>角色</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<body>
<table width="490" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
				<sky:datagrid id="dg1" method="com.xdarkness.workflow.WorkflowPage.roleDataBind" page="false">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
						<tr xtype="head" class="dataTableHead">
							<td width="7%" xtype="RowNo"></td>
							<td width="9%" xtype="selector" field="RoleCode">&nbsp;</td>
							<td width="41%"><b>名称</b></td>
							<td width="43%"><strong>代码</strong></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>${RoleName}</td>
							<td>${RoleCode}</td>
						</tr>
					</table>
				</sky:datagrid>
		</td>
	</tr>
</table>
</body>
</html>
