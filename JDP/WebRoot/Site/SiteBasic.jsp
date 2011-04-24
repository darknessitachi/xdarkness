<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<%
	//如果有DocList.LastCatalog则重定向到CatalogBasic.jsp
	CatalogSite.onRequestBegin(request, response);
%>
<sky:init method="com.xdarkness.cms.site.CatalogSite.init">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>栏目</title>
<script src="../Framework/Main.js"></script>
<script> 
//鼠标点击当前页面时，隐藏右键菜单
var iFrame =parent.parent;

Page.onLoad(function(){
		Application.setAllPriv("site",Application.CurrentSite);
});

Page.onClick(function(){
	var div = iFrame.$("_DivContextMenu")
	if(div){
			$E.hide(div);
	}
});

var topFrame = window.parent;
function add(){
	topFrame.add();	
}

function publish(){
	topFrame.publish();	
}

function publishIndex(){
	if(!$V("IndexTemplate")){
		Dialog.alert("首页模板不能为空");
		return;
	}
	topFrame.publishIndex();	
}

function del(){
	topFrame.del();
}

function batchAdd(){
	topFrame.batchAdd();
}

function batchDelete(){
	topFrame.batchDelete();
}

function addMagazine(){
}

function openEditor(path){
	topFrame.openEditor(path);
}

function preview(){
	topFrame.preview();
}

function browse(ele){
	var diag  = new Dialog("Diag3");
	diag.Width = 700;
	diag.Height = 440;
	diag.Title ="选择首页模板";
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

function changeIndexTemplate(){
	var indexTemplate=$V('IndexTemplate');
	if(!indexTemplate){
		Dialog.alert("请选择模板!");
		return;
	}
	var dc=new DataCollection();
	dc.add("IndexTemplate",indexTemplate);
	Server.sendRequest("com.xdarkness.cms.site.CatalogSite.changeTemplate",dc,function(response){
		Dialog.alert(response.Message);
		if(response.Status==1){
			$('editLink').style.display="block";
			$('editLink').onclick=function(){openEditor(indexTemplate);}
		}
	});
}

function preview(){
	topFrame.preview();
}

function exportStructure(){
	var diag = new Dialog("Diag4");
	diag.Width = 500;
	diag.Height = 350;
	diag.Title ="导出栏目结构";
	diag.URL = "Site/CatalogExportStructure.jsp?Type=${Type}&ID=";
	diag.ShowMessageRow = true;
	diag.Message = "导出的文本可复制后再使用“批量导入”功能导入的其他站点或栏目下。";
	diag.show();
	diag.OKButton.hide();
}
</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	<div style="padding:2px">
	<table width="100%" cellpadding='4' cellspacing='0'>
		<tr>
			<td><sky:button id="BtnAdd" priv="site_manage"
				onClick="add();">
				<img src="../Icons/icon002a2.gif" width="20" height="20" />新建栏目</sky:button> <sky:button
				id="BtnBatchAdd" priv="site_manage" onClick="batchAdd();">
				<img src="../Icons/icon002a9.gif" width="20" height="20" />批量新建</sky:button>
				<sky:button id="BtnBatchAdd" priv="site_manage" onClick="batchDelete();">
					<img src="../Icons/icon002a3.gif" width="20" height="20" />批量删除</sky:button> 
				<sky:button id="BtnExportStructure" priv="article_manage" onClick="exportStructure();">
					<img src="../Icons/icon002a11.gif" width="20" height="20" />导出栏目结构</sky:button>
				<sky:button
				id="BtnPublish" priv="site_manage" onClick="publish();">
				<img src="../Icons/icon002a1.gif" />发布全站</sky:button> <sky:button
				id="BtnPublishIndex" priv="site_manage" onClick="publishIndex();">
				<img src="../Icons/icon002a1.gif" />发布首页</sky:button> <sky:button id="BtnPublish"
				priv="site_browse" onClick="preview();">
				<img src="../Icons/icon403a3.gif" />预览</sky:button></td>
		</tr>
	</table>
	<form id="form2">
	<table width="600" border="1" cellpadding="3" cellspacing="0"
		bordercolor="#eeeeee">
		<tr>
		  <td>站点ID：</td>
		  <td>${ID}</td>
	    </tr>
		<tr>
			<td>站点名称：</td>
		  <td>${Name} &nbsp;
		    <input
				type="hidden" id="ID" value="${ID}"> <input type="hidden"
				id="Name" value="${Name}">
	      <input name="BtnPreview" type="button" id="BtnPreview" value="预览" onclick="preview();"></td>
		</tr>
		<tr>
			<td>站点目录名：</td>
			<td>${Alias}</td>
		</tr>
		<tr>
			<td>站点URL：</td>
			<td>${URL}</td>
		</tr>
		<!--tr>
	  <td>&nbsp;</td>
      <td  align="right" >站点Logo：</td>
      <td><img src="./${LogoFileName}" align="absmiddle"/>&nbsp;</td>
    </tr-->
		<tr>
			<td>首页模板：</td>
			<td><input name="IndexTemplate" type="text" class="input1"
				id="IndexTemplate" value="${IndexTemplate}" size="30" /> <input
				type="button" name="Submit" value="选择模板"
				onClick="browse('IndexTemplate')"> <input type="button"
				name="Submit" value="更新" onClick="changeIndexTemplate()">&nbsp;&nbsp;${edit}			</td>
		</tr>
		<tr>
			<td>编辑器样式：</td>
			<td>${EditorCss}&nbsp;</td>
		</tr>
		<tr>
			<td>描述：</td>
			<td>${Info}&nbsp;</td>
		</tr>
		<!--tr>
	  <td>&nbsp;</td>
      <td  align="right" >频道数：</td>
      <td>${ChannelCount}</td>
    </tr>
    <tr>
	  <td>&nbsp;</td>
      <td  align="right" >期刊数：</td>
      <td>${MagzineCount}</td>
    </tr>
    <tr>
	  <td>&nbsp;</td>
      <td  align="right" >专题数：</td>
      <td>${SpecialCount}</td>
    </tr>
    <tr!-->
	</table>
	</form>
	</div>
</body>
</html>
</sky:init>
