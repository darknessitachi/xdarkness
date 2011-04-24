<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>
<%
	String catalogID = request.getParameter("RelaCatalogID");
	String value = "0";
	String text = "文档库";
	if (StringUtil.isNotEmpty(catalogID)&&!catalogID.equalsIgnoreCase("null")&&!catalogID.equals("0")) {
		value = catalogID;
		text = CatalogUtil.getName(catalogID);
	}
%>

<script>
Page.onLoad(function(){
	Selector.setValueEx("RelaCatalogID",'<%=value%>','<%=text%>');
});
</script>
</head>
<body class="dialogBody">
<sky:init method="com.xdarkness.cms.dataservice.MessageBoard.initDialog">
	<form id="form2">
	<table width="100%" cellpadding="2" cellspacing="0" align="center">
    	<tr><td colspan="2" height="20"></td></tr>
		<tr>
			<td width="25%" height="25" align="right" >留言板名：</td>
			<td width="75%" height="25">
            <input name="Name" type="text" value="${Name}" class="inputText" id="Name" size="45" verify="留言板名|NotNull"/>
            <input type="hidden" id="ID" name="ID" value="${ID}"/>
            </td>
		</tr>
        <tr>
			<td height="25" align="right" >留言板描述：</td>
			<td height="25"><textarea id="Description" name="Description" style="width:250px; height:100px;" verify="留言板描述|NotNull">${Description}</textarea></td>
		</tr>
        <tr>
			<td height="25" align="right" >关联栏目：</td>
			<td height="25"><sky:select id="RelaCatalogID" listWidth="200" listHeight="300" listURL="Site/CatalogSelectList.jsp"></sky:select></td>
		</tr>
        <tr>
			<td height="25" align="right" >留言需审核：</td>
			<td height="25">${RadioIsOpen}</td>
		</tr>
	</table>
	</form>
</sky:init>
</body>
</html>
