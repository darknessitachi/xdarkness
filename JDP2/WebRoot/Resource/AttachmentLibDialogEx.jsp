<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function onReturnBack(returnPaths){
	if(returnPaths){
		var dc = new DataCollection();
		dc.add("Time",$V("Time"));
		dc.add("Path",returnPaths);
		Server.sendRequest("com.xdarkness.cms.resource.Image.setTempPath",dc,function(response){
			window.close();
		});
	}
}
</script>
</head>
<body>
<sky:init>
<input type="hidden" id="Time" name="Time" value="${t}"/>
	<sky:tab>
		<sky:childtab id="AttachmentUpload" src="AttachmentUploadEx.jsp?CatalogID=${CatalogID}" selected="true">
			<img src="../Icons/icon003a8.gif" />
			<b>上传附件</b>
		</sky:childtab>
		<sky:childtab id="AttachmentBrowse" src="AttachmentBrowseEx.jsp?CatalogID=${CatalogID}&Search=${Search}">
			<img src="../Icons/icon003a8.gif" />
			<b>附件浏览</b>
		</sky:childtab>
	</sky:tab>
</sky:init>
</body>
</html>
