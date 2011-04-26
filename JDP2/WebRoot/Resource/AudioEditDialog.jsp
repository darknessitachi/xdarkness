<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
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

function onReturnBack(returnID){
	onCustomReturnBack(returnID);
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


</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
	<body class="dialogBody">
	<sky:init method="com.xdarkness.cms.resource.Audio.initEditDialog">
	<form id="form2">
	<table width="100%" cellpadding="2" cellspacing="0">
		<tr>
			<td width="24%" height="10"></td>
			<td width="76%"></td>
		</tr>
		<tr>
			<td align="right">音频名称：</td>
			<td>
            <input name="Name" type="text" class="input1" id="Name" style="width:180px" value="${Name}" verify="附件名称|NotNull" />
            <input type="hidden" id="ID" name="ID" value="${ID}"/>
            <input type="hidden" id="CatalogID" name="CatalogID" value="${CatalogID}"/>            </td>
		</tr>
		<tr>
			<td align="right">标签：</td>
			<td><input name="Tag" type="text" class="input1" id="Tag" style="width:180px" value="${Tag}" /></td>
		</tr>
        <tr>
			<td height="22" align="right">大小：</td>
			<td>${FileSize}</td>
		</tr>
        <tr>
          <td height="22" align="right">格式：</td>
          <td>${Suffix}</td>
        </tr>
        <tr>
			<td height="22" align="right">上传时间：</td>
			<td>${AddTime}</td>
		</tr>
        <tr>
			<td height="22" align="right">上传者：</td>
			<td>${AddUser}</td>
		</tr>
        ${CustomColumn}
	</table>
	</form>
	</sky:init>
	</body>
</html>