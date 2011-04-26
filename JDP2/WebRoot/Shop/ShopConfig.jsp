<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分类</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td><sky:tab>
			<sky:childtab id="SendType" src="SendType.jsp" selected="true">
				<img src="../Icons/icon043a1.gif" />
				<b>配送方式</b>
			</sky:childtab>
			<sky:childtab id="Payment" src="Payment.jsp">
				<img src="../Icons/icon009a1.gif" />
				<b>付款方式</b>
			</sky:childtab>
		</sky:tab></td>
	</tr>
</table>
</body>
</html>
