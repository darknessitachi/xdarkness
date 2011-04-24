<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<%
	response.setHeader("Pragma","No-Cache");
	response.setHeader("Cache-Control","No-Cache");
	response.setDateHeader("Expires", 0);
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<script>
function imageBrowse(){
	var diag = new Dialog("Diag4");
	diag.Width = 780;
	diag.Height = 460;
	diag.Title ="浏览图片库";
	diag.URL = "Resource/ImageLibDialog.jsp?Search=Search&SelectType=radio";
	diag.OKEvent = ok;
	diag.show();
}

function ok(){
	if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageUpload")){
 		$DW.Tab.getCurrentTab().contentWindow.upload();
	}
	else if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageBrowse")){
	 	$DW.Tab.getCurrentTab().contentWindow.onReturnBack();
	}
}

function onReturnBack(returnID){
	var dc = new DataCollection();
	dc.add("PicID",returnID);
	Server.sendRequest("com.xdarkness.shop.PaymentService.getPicSrc",dc,function(response){
		$("ImagePath").src = response.get("picSrc");
		$("ImageID").value = returnID;
	})
}

function onUploadCompleted(returnID){
	onReturnBack(returnID);
}

function loadPayment() {
	window.location = <%=Config.getContextPath()%> + $V("PaymentSelect") + "?PaymentSelect=" + $V("PaymentSelect");
}

function save() {
	var dc = Form.getData($F("form2"));
	if(Verify.hasError()){
	  return;
     }
    dc.add("PaymentPath", $V("PaymentSelect")); 
	Server.sendRequest("com.xdarkness.shop.PaymentService.save",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				window.parent.location.reload();
				window.close();
			}
		});
	});
}

</script>
</head>
<body class="dialogBody">
<sky:init method="com.xdarkness.shop.PaymentService.init">
	<input type="hidden" value="${ID}" id="ID" name="ID">
	<form id="form2">
	<input name="PicSrc1" type="hidden" id="PicSrc1" value="${PicSrc1}" />
	<table width="100%" cellpadding="2" cellspacing="0">
		<tr>
			<td width="160" class="tdgrey2">&nbsp;</td>
			<td class="tdgrey2">&nbsp;</td>
		</tr>
		<tr>
			<td align="right" class="tdgrey1">支付方式名称：</td>
			<td class="tdgrey2">
				<sky:select id="PaymentSelect" style="width:100"
					onChange="loadPayment();">${PaymentOptions}</sky:select>
		</tr>
		<tr>
			<td align=right>支付方式图片：</td>
			<td>
				<input name="ImageID" type="hidden" id="ImageID" size=8 />
				<img src="${Prop1}" alt="" name="ImagePath" width="150" height="40" id="ImagePath" style="margin-bottom:-7px" /> 
				<input type="button" name="Submit" value="浏览..." onClick="imageBrowse()" />
				<br/>(建议图片尺寸：150*40)
			</td>
		</tr>
		<tr>
			<td align="right" class="tdgrey1">支持交易货币：</td>
			<td class="tdgrey2">
				<input id="Pmt_MoneyType" name="Pmt_MoneyType" value="${MoneyType}" />
		</tr>
		<tr>
			<td align="right" class="tdgrey1">客户号：</td>
			<td class="tdgrey2">
				<input id="Pmt_MerchantAcctId" name="Pmt_MerchantAcctId" value="${MerchantAcctId}" />
		</tr>
		<tr>
			<td align="right" class="tdgrey1">私钥：</td>
			<td class="tdgrey2">
				<input id="Pmt_Key" name="Pmt_Key" value="${Key}" />
		</tr>
		<tr>
			<td align="right" class="tdgrey1">支付费率：</td>
			<td class="tdgrey2">
				<input id="Pmt_PaymentProp" name="Pmt_PaymentProp" value="${PaymentProp}" />%
				说明：顾客将承担的订单支付手续费比率
		</tr>
		<tr>
			<td align="right" class="tdgrey1">支付方式说明：</td>
			<td class="tdgrey2"><textarea name="Info" cols="100"
				class="inputText" id="Info"
				style="height:300px;"">${Info}</textarea></td>
		</tr>
		<tr style="background-color:#E1F3FF">
			<td></td>
			<td align="right" style="padding-right:100px"><input type="button" value="保存" onClick="save();"/></td>
		</tr>
	</table>
	</form>
</sky:init>
</body>
</html>
