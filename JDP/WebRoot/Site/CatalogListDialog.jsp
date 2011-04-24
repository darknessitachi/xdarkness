<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<%
String inputType = request.getParameter("Type");
String boxType = null;
if("0".equals(inputType)||"3".equals(inputType)){
	boxType = "Radio";
}else{
	boxType = "CheckBox";
}

String ids = request.getParameter("IDs");
if(ids == null){
    ids = "";
}
String codes = request.getParameter("Codes");
if(codes == null){
    codes = "";
}
%>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script>
var IDs = "<%=ids%>";
var Codes = "<%=codes%>";
function onSiteChange(){
	Tree.setParam("tree1","SiteID",$V("_SiteSelector"));
	Tree.loadData("tree1");
}

Page.onLoad(function(){
	//type3 站点前面加单选框
	if($V("Type")==3){
		$E.disable($("_SiteSelector"));
	}
	var selectedID = IDs.split(",");
	for(var i=0;i<selectedID.length;i++){
	  $("catalog_"+selectedID[i]).checked = true;
	}
});

function selectAll(){
	var catalogs = $N("CatalogID");
	for(var i=0;i<catalogs.length;i++){
		catalogs[i].checked = $("_site").checked;
	}
}

function onCheck(ele){
	ele = $(ele);
	if(ele.type=="checkbox"){
		var checked = ele.checked;
		var p = ele.getParent("P");
		var level = p.$A("level");
		var arr = $("tree1").$T("P");
		var flag = true;
		for(var i=0;i<arr.length;i++){
			var c = arr[i];
			if(c.$A("cid")){
				if(c!=p&&flag){
					continue;
				}
				if(c==p){
					flag = false;
					continue;
				}
				if(c.$A("level")>level){
					$("catalog_"+c.$A("cid")).checked = checked;
				}else{
					break;
				}
			}
		}
	}
}
</script>
</head>
<body>
<input type="hidden" id="Type" value='<%=inputType%>'>
<form id="form2">
<table width="100%" cellpadding="0" cellspacing="6" class="cellspacing">
	<tr style="display:none">
		<td colspan="2" align="center" height=30>
		<div style="text-align:left;">&nbsp; 当前站点： <sky:init
			method="com.xdarkness.platform.Application.init">
			<sky:select id="_SiteSelector" style="width:180px;"
				onChange="onSiteChange();">
				${Sites}          
			</sky:select>
		</sky:init></div>
		</td>
	</tr>
	<tr>
		<td colspan="2"><sky:tree id="tree1" style="height:360px;width:480px;"
			method="com.xdarkness.cms.site.Catalog.treeDiagDataBind" level="2"
			lazy="true" expand="true">
			<p cid='${ID}' cinnercode='${InnerCode}' level="${Level}"><input
				type="<%=boxType%>" name="CatalogID" id='catalog_${ID}'
				value='${ID}' onClick="onCheck(this);"> <input type="hidden"
				name="cInnerCode_${ID}" id='cInnerCode_${ID}' value='${InnerCode}'>
			<label for="catalog_${ID}"><span id="span_${ID}">${Name}</span></label></p>
		</sky:tree>
	</tr>
	<% if("copy".equals(request.getParameter("Action"))){%>
	<tr><td height=30><input type="radio" name="ReferType" value="1" checked><b>完全复制</b>，复制所有字段 <input type="radio" name="ReferType" value="2"><b>引用复制</b>，只复制标题并链接到原文</td></tr>
  <%}%>
</table>
</form>
</body>
</html>
