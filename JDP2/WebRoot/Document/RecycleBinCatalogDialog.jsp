<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>选择己删除的栏目</title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script>
function selectAll(){
	var catalogs = $N("CatalogID");
	for(var i=0;i<catalogs.length;i++){
		catalogs[i].checked = $("_site").checked;
	}
}

function onCheck(ele){
	ele = $(ele);
	var checked = ele.checked;
	var p = ele.getParent("P");
	var code = p.$A("cinnercode");
	var level = p.$A("level");
	var arr = $("tree1").$T("P");
	var flag = true;
	for(var i=0;i<arr.length;i++){
		var c = arr[i];
		if(c.$A("cid")){
			if(code.indexOf(c.$A("cinnercode"))>=0&&checked){
				$("Catalog_"+c.$A("cid")).checked = true;
			}
			if(c!=p&&flag){
				continue;
			}
			if(c==p){
				flag = false;
				continue;
			}
			if(c.$A("level")>level){
				$("Catalog_"+c.$A("cid")).checked = checked;
			}else{
				break;
			}
		}
	}
}
</script>
</head>
<body>
<form id="form2">
<table width="100%" cellpadding="0" cellspacing="6" class="cellspacing">
	<tr>
		<td colspan="2">
		<sky:tree id="tree1" style="height:310px" method="com.xdarkness.cms.document.RecycleBin.treeCatalogDataBind" expand="true">
			<p cid='${ID}' cinnercode='${InnerCode}' level="${Level}"> 
			<input type="checkbox" name="CatalogID" id='Catalog_${ID}' value='${ID}' ondblclick="stopEvent(event);" onClick="onCheck(this);">
			<label for="Catalog_${ID}"><span id="span_${ID}">${Name}</span></label></p>
		</sky:tree>	
		</tr>
</table>
</form>
</body>
</html>
