<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>代码管理</title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<script src="Code.jsp.js" language="javascript"></script>
</head>
<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img
						src="../../Icons/icon023a6.gif" /> 代码列表</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;"><sky:button onClick="add()">
						<img src="../../Icons/icon018a2.gif" />新建</sky:button> <sky:button
						onClick="save()">
						<img src="../../Icons/icon018a4.gif" />保存</sky:button> <sky:button onClick="del()">
						<img src="../../Icons/icon018a3.gif" />删除</sky:button>
						<div style="float: right; white-space: nowrap;"><input type="text" name="SearchCodeType"
						id="SearchCodeType" value="请输入代码类别或名称" onFocus="delKeyWord();" style="width:150px"> 
						<input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="doSearch()"></div></td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<sky:datagrid id="dg1" method="com.xdarkness.platform.page.CodePage.dg1BindCode"
						size="15">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr xtype="head" class="dataTableHead">
								<td width="4%" xtype="RowNo">序号</td>
								<td width="3%" xtype="selector" field="id">&nbsp;</td>
								<td width="18%"><b>代码类别</b></td>
								<td width="24%"><b>代码名称</b></td>
								<td width="28%">备注</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td><span class="fc_bl3"><a href="javascript:void(0);" onClick="codeList('${CodeType}','${CodeName}');">${CodeType}</a></span></td>
								<td>${CodeName}</td>
								<td>${Memo}</td>
							</tr>
							<tr xtype="edit" bgcolor="#E1F3FF">
								<td bgcolor="#E1F3FF">&nbsp;</td>
								<td>&nbsp;</td>
								<td><input name="CodeType" type="CodeType" id="CodeType"
									value="${CodeType}" size="20"></td>
								<td><input name="CodeName" type="text" id="CodeName"
									value="${CodeName}" size="20"></td>
								<td><input name="Memo" type="text" id="Memo"
									value="${Memo}" size="20"></td>
							</tr>
							<tr xtype="pagebar">
								<td colspan="5">${PageBar}</td>
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
