<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
Page.onLoad(function(){

});

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
	Server.sendRequest("com.xdarkness.shop.Brand.getPicSrc",dc,function(response){
		$("ImagePath").src = response.get("picSrc");
		$("ImageID").value = returnID;
	})
}

function onUploadCompleted(returnID){
	onReturnBack(returnID);
}
</script>
</head>
<sky:init method="com.xdarkness.shop.Brand.initDialog">
<body class="dialogBody">
<form id="form2">
<input name="ID" type="hidden" id="ID" value="${ID}" />
<input name="PicSrc1" type="hidden" id="PicSrc1" value="${PicSrc1}" />
<table width="100%" border=0 cellpadding=2 cellspacing=3>
	<tr>
		<td align=right>品牌名称:</td>
		<td>
			<input name="Name" id="Name" type="text" class="input1" value="${Name}" size="20" verify="商品品牌名称|NotNull" />
		</td>
	</tr>
	<tr>
		<td align=right>品牌英文名:</td>
		<td>
			<input name="Alias" id="Alias" type="text" class="input1" value="${Alias}" size="20" verify="品牌英文名|NotNull"/>
		</td>
	</tr>
	<tr>
		<td align=right>品牌URL:</td>
		<td>
			<input name="URL" id="URL" type="text" class="input1" value="${URL}" size="20" />
		</td>
	</tr>
	<tr>
		<td align=right>品牌缩略图:</td>
		<td>
			<input name="ImageID" type="hidden" id="ImageID" size=8 />
			<img src="${PicSrc}" name="ImagePath" width="100" height="75" id="ImagePath"> 
			<input type="button" name="Submit" value="浏览..." onClick="imageBrowse()">(建议长宽比4:3)
		</td>
	</tr>
	<tr>
		<td align=right>所属机构:</td>
		<td>
			<sky:select id="BranchInnerCode"> ${BranchInnerCodeOptions} </sky:select>
		</td>
	</tr>
	<tr>
		<td align=right>品牌描述:</td>
		<td>
			<textarea id="Info" name= "Info" style="width:300px;height:50px">${Info}</textarea>
		</td>
	</tr>
</table>
</form>
</body>
</sky:init>
</html>
