<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">

function goBack(params){
	alert(params)
}

function imageBrowse(){
	var diag = new Dialog("Diag4");
	diag.Width = 780;
	diag.Height = 460;
	diag.Title ="浏览图片库";
	diag.URL = "Resource/ImageLibDialogCover.jsp?Search=Search&SelectType=radio";
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
	if(customPicName==null||customPicName==""){
		var dc = new DataCollection();
		dc.add("PicID",returnID);
		Server.sendRequest("com.xdarkness.cms.resource.AttachmentLib.getPicSrc",dc,function(response){
			$("Pic").src = response.get("picSrc");
			$("ImagePath").value = response.get("ImagePath");
		})
	}else{
		onCustomReturnBack(returnID);
	}
}

/***图片上传**/
var customPicName; //自定义图片库上传
function custom_img_upload(colsName){
	customPicName = colsName;
	var CatalogID = $V("CatalogID");
	var diag = new Dialog("Diag_custom");
	diag.Width = 800;
	diag.Height = 460;
	diag.Title = "图片浏览/上传";
	diag.URL = "Resource/ImageLibDialogCover.jsp?Search=Search&SelectType=radio";
	diag.OKEvent = OK;
	diag.show();
}

function OK(){
	var currentTab = $DW.Tab.getCurrentTab();
	if(currentTab==$DW.Tab.getChildTab("ImageUpload")){
 		currentTab.contentWindow.upload();
	}else if(currentTab==$DW.Tab.getChildTab("ImageBrowse")){
	 	currentTab.contentWindow.onReturnBack();
	}
}

function onCustomReturnBack(returnID){
	var dc = new DataCollection();
	dc.add("PicID",returnID);
	dc.add("Custom","1");
	dc.add("CatalogID", $V("CatalogID"));
	Server.sendRequest("com.xdarkness.cms.document.Article.getPicSrc",dc,function(response){
		$S(customPicName,response.get("s_picSrc"));
		$("Img"+customPicName).src = response.get("s_picSrc");
		$(customPicName).focus();
		customPicName = "";
	});
}

function onUploadCompleted(FileIDs){
	onCustomReturnBack(FileIDs);
}

</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
	<body class="dialogBody">
	<sky:init method="com.xdarkness.cms.resource.Attachment.initDialog">
	<form id="form2">
	<table width="100%" cellpadding="2" cellspacing="0">
		<tr>
			<td width="31%" height="10"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">附件名称：</td>
			<td>
            <input name="Name" type="text" class="input1" id="Name" value="${Name}" verify="附件名称|NotNull" />
            <input type="hidden" id="ID" name="ID" value="${ID}"/>
            <input type="hidden" id="CatalogID" name="CatalogID" value="${CatalogID}"/>
            </td>
		</tr>
		<tr>
			<td align="right">附件描述：</td>
			<td><input name="Info" type="text" class="input1" id="Info" value="${Info}" /></td>
		</tr>
        <tr>
			<td align="right">大小：</td>
			<td>${FileSize}<span style="padding-left:20px;">格式：${Suffix}</span></td>
		</tr>
        <tr>
			<td align="right">上传时间：</td>
			<td>${AddTime}</td>
		</tr>
        <tr>
			<td align="right">上传者：</td>
			<td>${AddUser}</td>
		</tr>
        ${CustomColumn}
		<tr id="IndexImg">
			<td align="right">附件索引图片：</td>
			<td align="left" width="69%">
            <img src="${PicSrc}" name="Pic" width="120" height="90" id="Pic">
            <input name="button" type="button" onClick="imageBrowse();" value="浏览..."/>
            <input name="ImagePath" value="${ImagePath}" type="hidden" id="ImagePath"/>
            </td>
		</tr>
        <tr>
        	<td>&nbsp;</td>
            <td><a href="${AttachmentLink}" target="_blank" title="下载次数:${Prop1}">下载此附件</a></td>
        </tr>
		<!--
		<tr>
			<td align="right" width="31%">列表页模板:</td>
			<td colspan="2"><input name="ListTemplate" type="text"
				class="input1" id="ListTemplate" value="${ListTemplate}" size="30">
			<input type="button" class="input2" onClick="browse('ListTemplate');"
				value="浏览..." /></td>
		</tr>
		<tr>
			<td align="right">图片详细页模板:</td>
			<td colspan="2"><input name="DetailTemplate" type="text"
				class="input1" id="DetailTemplate" value="${DetailTemplate}"
				size="30" /> <input type="button" class="input2"
				onClick="browse('DetailTemplate')" value="浏览..." /></td>
		</tr>
		  -->
	</table>
	</form>
	</sky:init>
	</body>
</html>