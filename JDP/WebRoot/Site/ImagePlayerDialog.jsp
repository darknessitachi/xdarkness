<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>图片播放器</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>

var ImagePlayerID = "<%=request.getParameter("ImagePlayerID")%>";
var imagesource = "<%=request.getParameter("ImageSource")%>";
var catalogInnerCode = "<%=request.getParameter("RelaCatalog")%>";
Page.onLoad(function(){
	if (imagesource == "1") {
		tabClick("ImagePlayerPreview");
		$("ImagePlayerRela").hide();
	} else {
		tabClick("ImagePlayerRela");
	}
});

function tabClick(tabid){
	Tab.onChildTabClick(tabid);
	var url = tabid+".jsp?ImagePlayerID="+ImagePlayerID;
	if (imagesource) {
		url += "&ImageSource=" + imagesource;
	}
	
	if (catalogInnerCode) {
		url += "&RelaCatalog=" + catalogInnerCode;
	}
	Tab.getCurrentTab().src = url;
	return;
}

</script>
</head>
<body>
<sky:init>
	<sky:tab>
		<sky:childtab src="ImagePlayerBasic.jsp?ImagePlayerID=${ImagePlayerID}&ImageSource=${ImageSource}&RelaCatalog=${RelaCatalog}" id="ImagePlayerBasic" selected="true">
			<img src="../Icons/icon039a1.gif" />
			<b>基本信息</b>
		</sky:childtab>
		<sky:childtab id="ImagePlayerRela" src="" afterClick="tabClick('ImagePlayerRela')">
			<img src="../Icons/icon039a13.gif" />
			<b>关联图片</b>
		</sky:childtab>
		<sky:childtab id="ImagePlayerPreview" src="" afterClick="tabClick('ImagePlayerPreview')">
			<img src="../Icons/icon039a12.gif" />
			<b> 预&nbsp;&nbsp;&nbsp;&nbsp;览 </b>
		</sky:childtab>
	</sky:tab>
</sky:init>
</body>
</html>
