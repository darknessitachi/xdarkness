<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<%
	response.setHeader("Pragma","No-Cache");
	response.setHeader("Cache-Control","No-Cache");
	response.setDateHeader("Expires", 0);
%>
<html>
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/District.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
function checkBeginDate(){
	var date=$V("SendBeginDate");
	var valid=/^\d{4}-((0{0,1}[1-9]{1})|(1[0-2]{1}))-((0{0,1}[1-9]{1})|([1-2]{1}[0-9]{1})|(3[0-1]{1}))$/;
	if(valid.test(date)){
		return true;
	}else{
		return false;
	}
}

function checkEndDate(){
	var enddate=$V("SendEndDate");
	var begindate=$V("SendBeginDate");
	var valid=/^\d{4}-((0{0,1}[1-9]{1})|(1[0-2]{1}))-((0{0,1}[1-9]{1})|([1-2]{1}[0-9]{1})|(3[0-1]{1}))$/;
	if(!valid.test(enddate)){
		return true;
	}
	if(begindate>enddate){
		return false;
	}
	return true;
}
</script>
</head>
<sky:init method="com.xdarkness.shop.Order.initAddDialog">
<input type="hidden" id="ID" value="${ID}" />
<body class="dialogBody">
<form id="form2">
	<table width="100%" cellpadding="2" cellspacing="0">
	  <tr>
		<td align="center">
		<fieldset style="width:500px"><legend> 订单基本信息 </legend>
		<table width=100%>
			<tr>
				<td height="25" align="right" class="tdgrey1">会员名：</td>
				<td class="tdgrey2" align="left">
					<input type="text" value="${UserName}"
						id="UserName" name="UserName" class="inputText"
						verify="会员名|NotNull"></td>
				<td height="25" align="right" class="tdgrey1">订单状态：</td>
				<td height="25" class="tdgrey2" align="left">
					<sky:select id="Status">${Status}</sky:select>
				</td>
			</tr>
			<tr>
				<td height="25" align="right">是否开发票：</td>
				<td align="left">${HasInvoice}</td>
				<td height="25" align="right">是否有效：</td>
				<td align="left">${IsValid}</td>
			</tr>
			<tr id="tr_hidden" style="display:none">
				<td height="25" align="right" class="tdgrey1">积分：</td>
				<td class="tdgrey2" align="left">
					<input value="0" type="text" id="Score"
						name="Score" class="inputText" verify="积分|NotNull&&Int"></td>
				<td height="25" align="right" class="tdgrey1">金额：</td>
				<td class="tdgrey2" align="left">
					<input value="0" type="text" id="Amount"
						name="Amount" class="inputText" verify="金额为数字|NotNull&&Number"></td>
			</tr>
			<tr>
				<td height="25" align="right" class="tdgrey1">配送方式：</td>
				<td height="25" class="tdgrey2" align="left">
					<sky:select id="SendType"> ${SendType} </sky:select>
				</td>

				<td height="25" align="right" class="tdgrey1">支付方式：</td>
				<td height="25" class="tdgrey2" align="left">
					<sky:select id="PaymentType"> ${PaymentType} </sky:select>
				</td>
			</tr>
			<tr>
				<td height="25" align="right" class="tdgrey1">发票抬头：</td>
				<td class="tdgrey2" colspan="3" align="left"><input name="InvoiceTitle"
					type="text" class="inputText" id="InvoiceTitle" value="" size="65"></td>
			</tr>
		</table>
		</fieldset>
		</td>
	</tr>
	<tr>
		<td align="center">
		<fieldset style="width:500px"><legend> 地址信息 </legend>
		<table width="100%">
			<tr>
				<td height="25" align="right" class="tdgrey1">省份：</td>
				<td height="25" class="tdgrey2" align="left">
				<sky:select id="Province" value="${Province}" onChange="changeProvince();" listHeight="300"> </sky:select>
				</td>

				<td height="25" align="right" class="tdgrey1">城市：</td>
				<td height="25" class="tdgrey2" align="left">
				<sky:select id="City" value="${City}" onChange="changeCity();" listHeight="300"> </sky:select>
				</td>
			</tr>
			<tr>
				<td height="25" align="right" class="tdgrey1">区县：</td>
				<td height="25" class="tdgrey2" align="left">
				<sky:select id="District" value="${District}" listHeight="300"> </sky:select>
				</td>
				<td height="25" align="right" class="tdgrey1">邮编：</td>
				<td class="tdgrey2" align="left">
					<input value="" type="text" id="ZipCode"
						name="ZipCode" class="inputText" verify="邮编|NotNull&&Number"></td>
			</tr>
			<tr>
				<td height="25" align="right" class="tdgrey1">固定电话：</td>
				<td class="tdgrey2" align="left">
					<input value="" type="text" id="Tel"
						name="Tel" class="inputText" verify="固定电话|NotNull"></td>
				<td height="25" align="right" class="tdgrey1">移动电话：</td>
				<td class="tdgrey2" align="left">
					<input value="" type="text" id="Mobile"
						name="Mobile" class="inputText" verify="移动电话|NotNull"></td>
			</tr>
		</table>
		</fieldset>
		</td>
	  </tr>
	  <tr>
		<td align="center">
		<fieldset style="width:500px"><legend> 送货明细 </legend>
		<table width="100%">
			<tr>
				<td height="25" align="right" class="tdgrey1">收货人的姓名：</td>
				<td class="tdgrey2" align="left">
					<input value="" type="text" id="Name"
						name="Name" class="inputText" verify="收货人的姓名|NotNull"></td>
				<td height="25" align="right" class="tdgrey1">送货时间段：</td>
				<td height="25" class="tdgrey2" align="left">
					<sky:select id="SendTimeSlice">${SendTimeSlice}</sky:select>
				</td>
			</tr>
			<tr>
				<td height="25" align="right" class="tdgrey1">送货开始时间：</td>
				<td class="tdgrey2" align="left">
					<input value="" type="text"
						id="SendBeginDate" name="SendBeginDate" xtype="Date"
						class="inputText" verify="日期格式为yyyy-MM-dd|Script=checkBeginDate()"></td>
				<td height="25" align="right" class="tdgrey1">送货结束时间：</td>
				<td class="tdgrey2" align="left">
					<input value="" type="text"
						id="SendEndDate" name="SendEndDate" xtype="Date" class="inputText"
						verify="日期格式为yyyy-MM-dd且晚于开始时间,|Script=checkEndDate()"></td>
			</tr>
			<tr>
				<td height="25" align="right" class="tdgrey1">送货地址：</td>
				<td class="tdgrey2" colspan="3" align="left"><input value="" type="text"
					size=65 id="Address" name="Address" class="inputText"
					verify="地址|NotNull"></td>
			</tr>
			<tr>
				<td height="25" align="right" class="tdgrey1">送货特殊说明：</td>
				<td class="tdgrey2" colspan="3" align="left"><input size=65 value=""
					type="text" id="SendInfo" name="SendInfo" class="inputText"></td>
			</tr>
		</table>
		</fieldset>
		</td>
	  </tr>
	</table>
</form>
</body>
</sky:init>
</html>
