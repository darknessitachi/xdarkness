<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="controls" prefix="sky"%>
<%@page import="com.xdarkness.framework.Config"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="<%=Config.getContextPath()%>Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="<%=Config.getContextPath()%>Framework/Main.js"></script>
<script type="text/javascript">
Page.onLoad(function(){
	
});
</script>
</head>
<body class="dialogBody">
<form id="form2">
<input name="GoodsID" type="hidden" id="GoodsID" value="<%=request.getParameter("GoodsID") %>" />
<input name="MemberIP" type="hidden" id="MemberIP" value="<%=request.getRemoteAddr() %>" />
<table width="100%" border="0" cellpadding="2" cellspacing="3" style="margin-top:15px;">
	<tr>
		<td align="right">评价标题:</td>
		<td>
			<input name="Title" id="Title" type="text" class="input1" value="" size="50" />
		</td>
	</tr>
	<tr>
		<td align="right">评价内容:</td>
		<td>
			<textarea id="Content" name= "Content" verify="评价内容|NotNull" style="width:300px;height:130px"></textarea>
		</td>
	</tr>
</table>
</form>
</body>
</html>
