<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
<script src="../Editor/fckeditor.js"></script>
<script src="../Framework/Controls/StyleToolbar.js"></script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

function afterSelectIcon(){
	$("Logo").src = $DW.Icon;
	$D.close();
}

function goBack(params){
	alert(params)
}

function browse(ele){
	var diag  = new Dialog("Diag3");
	diag.Width = 700;	diag.Height = 450;
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

function setAlias(){
	if($V("Alias") == ""){
	  $S("Alias",getSpell($V("Name"),true));
  }
}

function imageBrowse(){
	var diag = new Dialog("Diag4");
	diag.Width = 800;	
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
	var dc = new DataCollection();
	dc.add("PicID",returnID);
	Server.sendRequest("com.xdarkness.cms.site.MagazineIssue.getPicSrc",dc,function(response){
		$("Pic").src = response.get("picSrc");
		$S("CoverImage", response.get("CoverImage"));
	})
}

function onUploadCompleted(returnID){
	onReturnBack(returnID);
}

var editor;
function getContent(){
	editor = FCKeditorAPI.GetInstance('Content');
    return editor.GetXHTML(false);	
}

</script>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<sky:init method="com.xdarkness.cms.site.MagazineIssue.initDialog">
	<body class="dialogBody">
	<form id="form2"><input type="hidden" id="ID">

	<table width="100%" border=0 cellPadding=2 cellSpacing=3>
		<TBODY>
			<tr>
				<td align=right height="5"></td>
				<td></td>
			</tr>
			<tr>
				<td align=right>年号:</td>
				<td><input name="Year" id="Year" type="text" class="input1"
					size=20 verify="年号|NotNull&&Int" /></td>
			</tr>
			<tr>
				<td align=right>期号:</td>
				<td><input name="PeriodNum" type="text" id="PeriodNum"
					class="input1" size=20 verify="期号|NotNull&&Int" /></td>
			</tr>
			<tr>
				<td align=right>出版日期:</td>
				<td><input name="PublishDate" type="text" id="PublishDate"
					class="input1" xtype="Date" /></td>
			</tr>
			<tr>
				<td align=right>封面图片:</td>
				<td align=left><input name="CoverImage" value="${CoverImage}"
					type="hidden" id="CoverImage" /> <img src="${PicSrc}" name="Pic"
					id="Pic" height="90" width="120"> <input type="button" name="Submit" value="浏览..."
					onClick="imageBrowse()"></td>
			</tr>
			<tr>
				<td align=right>封面页模板:</td>
				<td><input name="CoverTemplate" type="text" class="input1"
					id="CoverTemplate" size="30"> <input type="button"
					class="input2" onClick="browse('CoverTemplate');" value="浏览..." /></td>
			</tr>
			<tr>
				<td align=right>本期简介:</td>
				<td>
            <div  id="Toolbar" style="height:26px; width:95%"></div>
            <textarea id="_Content" name="_Content" style=" display:none;">${Memo}</textarea>
            <script type="text/javascript">
				var sBasePath = <%=Config.getContextPath()%>+"Editor/" ;
				var oFCKeditor = new FCKeditor( 'Content' ) ;
				oFCKeditor.BasePath	= sBasePath ;
				oFCKeditor.ToolbarSet="Basic"
				oFCKeditor.Width  = '95%';
				oFCKeditor.Height = 90;
				oFCKeditor.Config['EditorAreaCSS'] = '${CssPath}';
				oFCKeditor.Config[ 'ToolbarLocation' ] = 'Out:Toolbar' ;
				oFCKeditor.Value = $V("_Content");
				oFCKeditor.Create();
			</script>            
            </td>
			</tr>
			<tr>
				<td align=right>上期栏目:</td>
				<td>${LastCatalog}</td>
			</tr>
			<tr>
			</tr>
		</TBODY>
	</table>
	</form>
	</body>
</sky:init>
</html>
