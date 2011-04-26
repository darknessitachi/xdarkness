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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
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
	Server.sendRequest("com.xdarkness.shop.Payment.getPicSrc",dc,function(response){
		$("ImagePath").src = response.get("picSrc");
		$("ImageID").value = returnID;
	})
}

function onUploadCompleted(returnID){
	onReturnBack(returnID);
}

function loadPayment() {
	window.location = $V("PaymentSelect");
}

</script>
</head>
<body class="dialogBody">
<sky:init method="com.xdarkness.shop.Payment.initDialog">
	<input type="hidden" value="${ID}" id="ID" name="ID">
	<form id="form2">
	<input name="PicSrc1" type="hidden" id="PicSrc1" value="${PicSrc1}" />
	<table width="100%" cellpadding="2" cellspacing="0">
		<tr>
			<td class="tdgrey2"></td>
			<td class="tdgrey2"></td>
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
				<img src="${ImagePath}" alt="" name="ImagePath" width="150" height="40" id="ImagePath" style="margin-bottom:-7px" /> 
				<input type="button" name="Submit" value="浏览..." onClick="imageBrowse()" />
				<br/>(建议图片尺寸：150*40)
			</td>
		</tr>
		<tr>
			<td align="right" class="tdgrey1">支付方式说明：</td>
			<td class="tdgrey2"><textarea name="Info" cols="100"
				class="inputText" id="Info" verify="支付方式说明|NotNull"
				style="height:300px;"">${Info}</textarea></td>
		</tr>
	</table>
	</form>
</sky:init>
</body>
</html>
