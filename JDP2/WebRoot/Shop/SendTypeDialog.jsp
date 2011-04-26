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
</script>
</head>
<body class="dialogBody">
<sky:init method="com.xdarkness.shop.SendType.initEditDialog">
	<input type="hidden" value="${ID}" id="ID" name="ID">
	<form id="form2">
	<input name="PicSrc1" type="hidden" id="PicSrc1" value="${PicSrc1}" />
	<table width="100%" cellpadding="2" cellspacing="0">
		<tr>
			<td width="492" height="10" class="tdgrey2"></td>
			<td width="612" class="tdgrey2"></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">配送名称：</td>
			<td class="tdgrey2"><input type="text" value="${Name}" id="Name"
				name="Name" class="inputText" verify="配送名称|NotNull"></td>
		</tr>
		<tr>
			<td align=right>展示图片:</td>
			<td>
				<input name="ImageID" type="hidden" id="ImageID" size=8 />
				<img src="${Prop1}" alt="" name="ImagePath" width="150" height="40" id="ImagePath" style="margin-bottom:-7px" /> 
				<input type="button" name="Submit" value="浏览..." onClick="imageBrowse()" />
				<br/>(建议图片尺寸：150*40)
			</td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">配送费用：</td>
			<td class="tdgrey2"><input type="text" value="${Price}"
				id="Price" name="Price" class="inputText"
				verify="配送费用|NotNull&&number"></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">送货说明：</td>
			<td class="tdgrey2"><textarea value="" cols="40" id="SendInfo"
				name="SendInfo" class="inputText" verify="送货说明|NotNull">${SendInfo}</textarea></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">到货说明：</td>
			<td class="tdgrey2"><textarea value="" type="text" cols="40"
				id="ArriveInfo" name="ArriveInfo" class="inputText"
				verify="到货说明|NotNull">${ArriveInfo}</textarea></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">配送说明：</td>
			<td class="tdgrey2"><textarea type="text" value="" id="Info"
				cols="40" name="Info" class="inputText" verify="配送说明|NotNull">${Info}</textarea></td>
		</tr>
	</table>
	</form>
</sky:init>
</body>
</html>
