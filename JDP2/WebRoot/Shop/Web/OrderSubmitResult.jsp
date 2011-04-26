<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.xdarkness.framework.Config"%>
<%@ taglib uri="controls" prefix="sky" %>
<%@page import="com.xdarkness.framework.utility.Mapx"%>
<%@page import="com.xdarkness.framework.data.DataTable"%>
<%@page import="com.xdarkness.schema.ZSPaymentSchema"%>
<%@page import="com.xdarkness.schema.ZSOrderSchema"%>
<%@page import="com.xdarkness.framework.utility.NumberUtil"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>订单信息</title>
<link href="<%=Config.getContextPath()%>Include/Default.css" rel="stylesheet" type="text/css">
<link href="<%=Config.getContextPath()%>Include/front-end.css" rel="stylesheet" type="text/css" />
<script src="<%=Config.getContextPath()%>Framework/Main.js" type="text/javascript"></script>
<script src="<%=Config.getContextPath()%>Framework/District.js"></script>
<%@ include file="../../Include/Head.jsp" %>
</head>
<body>
<%@ include file="../../Include/Top.jsp" %>
<div class="wrap">
<%@ include file="../../Member/Verify.jsp"%>
<div id="nav"><a href="#">首页</a>  &raquo; 订单信息</div>
<br>
<sky:init method="com.xdarkness.shop.web.OrderByCenter.initOrderResult">
	<table width="100%" border="0" cellspacing="0" cellpadding="6">
		<tr>
			<td><div style="text-align:right">【<a href="javascript:window.print();">打印</a>】</div></td>
		</tr>
	</table>
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
			<td width="15%" align="right">送货时间：</td>
			<td colspan="3" align="left">${SendTimeSlice}</td>
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
	<sky:datagrid id="dg1" method="com.xdarkness.shop.web.OrderByCenter.dg1PrintDataBind">
		<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
			<tr xtype="head" class="dataTableHead">
				<td width="20%" align="center"><b>商品名称</b></td>
				<td width="40%"><b>厂家</b></td>
				<td width="10%"><b>价格</b></td>
				<td width="10%"><b>数量</b></td>
				<td width="10%"><b>小计</b></td>
				<td width="10%"><b>积分</b></td>
			</tr>
			<tr style1="background-color:#FFFFFF"
						style2="background-color:#F9FBFC">
				<td align="center">${Name}</td>
				<td>${Factory}</td>
				<td>${Price}</td>
				<td>${Count}</td>
				<td>${Amount}</td>
				<td>${Score}</td>
			</tr>
		</table>
	</sky:datagrid>
	<!-- 更具付款方式去调用支付接口 -->
	<%
	ZSOrderSchema order = new ZSOrderSchema();
	order.setID(request.getParameter("OrderID"));
	order.fill();
	String paymentType = order.getPaymentType();
	long l = System.currentTimeMillis();
	%>
	<%if(l%2==0){ %>
		<%@ include file="../Alipay/index.jsp" %>
	<%}else if(l%2==1){  %>
		<%@ include file="../Chinapay/Chinapay.jsp" %>
	<%} %>
	<!--<table width="100%" border="0" cellspacing="0" cellpadding="6">
		<tr>
			<td><input type="button" name="submit" value="立刻付款" onclick="pay()"/> </td>
		</tr>
	</table>
--></sky:init>
</div>
<%@ include file="../../Include/Bottom.jsp" %>
</body>
</html>