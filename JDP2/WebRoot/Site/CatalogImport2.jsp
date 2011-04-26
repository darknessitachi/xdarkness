<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script>
function browse(ele){
	var diag  = new Dialog("Diag3");
	diag.Width = 700;
	diag.Height = 440;
	diag.Title ="浏览列表页模板";
	diag.URL = "Site/TemplateExplorer.jsp";
	goBack = function(params){
		$S(ele,params);
	}
	diag.OKEvent = afterSelect;
	diag.show();
}

function afterSelect(){
	$DW.onOK();
}

function returnBack(){
	window.location="CatalogImport.jsp?ParentID="+<%=request.getParameter("ParentID")%>;
}

function save(){
	var dc = new DataCollection();
	dc.add("ParentID",$V("ParentID"));
	dc.add("ListTemplate",$V("ListTemplate"));
	dc.add("DetailTemplate",$V("DetailTemplate"));
	dc.add("BatchColumn",$V("BatchColumn"));
	dc.add("Type",$V("Type"));
	
	Server.sendRequest("com.xdarkness.cms.site.Catalog.importCatalog",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("导入成功",function(){
					Parent.reloadTree();
					Dialog.close();
		  });
		}
	});
}

</script>
</head>
<body class="dialogBody">
<form id="form2">
<input type="hidden" id="ParentID"
	value="<%=request.getParameter("ParentID")%>">
<input type="hidden" id="Type" value="<%=request.getParameter("Type")%>">
<textarea style="display:none" id="BatchColumn" name="BatchColumn"><%=request.getParameter("BatchColumn")%></textarea>
<table width="100%" height="100%" align="center" cellpadding="2" cellspacing="0">
	<tr>
		<td colspan="2" align="left">&nbsp;栏目预览</td>
	</tr>
	<tr>
		<td colspan="2" align="center">
		<div style="text-align:left;">
		<sky:tree id="tree1" style="height:210px"
			method="com.xdarkness.cms.site.Catalog.importTreeDataBind">
			<p cid='${ID}'>${Name}</p>
		</sky:tree></div>
		</td>
	</tr>
	<tr>
		<td width="22%" align="center">列表页模板：</td>
		<td width="78%" align="center"><input name="ListTemplate"
			type="text" class="input1" id="ListTemplate"
			value="/template/list.html" size="30"> <input type="button"
			class="input2" onClick="browse('ListTemplate');" value="浏览..." /></td>
	</tr>
	<tr>
		<td align="center">详细页模板：</td>
		<td align="center"><input name="DetailTemplate" type="text"
			class="input1" id="DetailTemplate" value="/template/detail.html"
			size="30" /> <input type="button" class="input2"
			onClick="browse('DetailTemplate');" value="浏览..." /></td>
	</tr>
	<tr height="35" class="dialogButtonBg">
		<td height="50" colspan="2" align="center"><input name="button"
			type="Button" class="inputButton" id="button" onClick="returnBack()"
			value=" 上一步 " /> &nbsp; <input name="button" type="Button"
			class="inputButton" id="button" onClick="save()" value=" 完 成 " />
	  &nbsp; <input name="button2" type="button" class="inputButton"
			onClick="Dialog.close();" value=" 取 消 " /></td>
	</tr>
</table>
</form>
</body>
</html>
