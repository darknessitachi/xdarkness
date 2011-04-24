<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="controls" prefix="sky"%>
<%@page import="com.xdarkness.framework.Config"%>
<html>
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>配送单详细</title>
<link href="<%=Config.getContextPath()%>Include/Default.css" rel="stylesheet" type="text/css" />
<script src="<%=Config.getContextPath()%>Framework/Main.js"></script>
</head>
<body>
<sky:init method="com.xdarkness.shop.web.Sending.init">
<form id="orderform">
<input type="hidden" id="OrderID" value="${OrderID}"/>
	<table width="100%" border="0" cellspacing="0" cellpadding="6">
		<tr>
			<td><div style="text-align:right">【<a href="javascript:window.print();">打印</a>】</div></td>
		</tr>
	</table>
	<table width="100%" border="0" cellspacing="0" cellpadding="6">
		<tr>
			<td style="padding:0px 10px;">
			<table width="100%" cellpadding="2" cellspacing="0">
				<tr>
					<td align="right" colspan="4" height="10px"></td>
				</tr>
				<tr>
					<td width="15%" align="right">收&nbsp;货&nbsp;&nbsp;人：</td>
					<td width="35%" align="left">${Name}</td>
					<td width="15%" align="right">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;编：</td>
					<td width="35%" align="left">${ZipCode}</td>
				</tr>
				<tr>
					<td width="15%" align="right">送货地址：</td>
					<td colspan="3" align="left">${Province}${City}${District}${Address}</td>
				</tr>
				<tr>
					<td align="right" colspan="4" height="10px"></td>
				</tr>
				<tr>
					<td width="15%" align="right">商品金额：</td>
					<td width="35%" align="left">${Amount}</td>
					<td width="15%" align="right">配送金额：</td>
					<td width="35%" align="left">${SendFee}</td>
				</tr>
				<tr>
					<td width="15%" align="right">订单金额：</td>
					<td width="35%">${OrderAmount}</td>
					<td width="15%" align="right">固定电话：</td>
					<td width="35%" align="left">${Tel}</td>
				</tr>
				<tr>
					<td width="15%" align="right">移动电话：</td>
					<td width="35%">${Mobile}</td>
					<td width="15%" align="right">配送方式：</td>
					<td width="35%" align="left">${SendType}</td>
				</tr>
				<tr>
					<td width="15%" align="right">支付方式：</td>
					<td width="35%" align="left">${PaymentType}</td>
					<td width="15%" align="right">发票信息：</td>
					<td width="35%" align="left">${InvoiceTitle}</td>
				</tr>
				<tr>
					<td align="right" colspan="4" height="10px"></td>
				</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
			<sky:datagrid id="dg1" method="com.xdarkness.shop.web.Sending.dg1PrintDataBind">
				<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
					<tr>
						<td width="20%" align="center"><b>商品名称</b></td>
						<td width="40%"><b>厂家</b></td>
						<td width="10%"><b>价格</b></td>
						<td width="10%"><b>数量</b></td>
						<td width="10%"><b>小计</b></td>
						<td width="10%"><b>积分</b></td>
					</tr>
					<tr>
						<td align="center">${Name}</td>
						<td>${Factory}</td>
						<td>${Price}</td>
						<td>${Count}</td>
						<td>${Amount}</td>
						<td>${Score}</td>
					</tr>
				</table>
			</sky:datagrid></td>
		</tr>
	</table>
	</form>
</sky:init>
</body>
</html>