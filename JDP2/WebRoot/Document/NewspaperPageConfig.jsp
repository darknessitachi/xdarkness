<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html style="height:100%;">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<link href="../Framework/Controls/Paper.css" rel="stylesheet" type="text/css">
<style>
body {background-color:#ffffff;color: #444;}
a { color: #08c; text-decoration: none; border: 0; background-color: transparent; }
a:hover { color: #fff; text-decoration: underline; background-color:#09d;}
a:active{ color: #f70; outline:0;}
body,div,q,iframe,form,
ul,li,dl,dt,dd,h1,h2,h3,h4,h5,h6,p{ margin: 0; padding: 0;}
body,td,textarea{word-break: break-all; line-height:1.5;}
body,input,textarea,select,button{margin: 0; font-size: 12px;font-family: Tahoma, SimSun, sans-serif;}
div,p,table,th,td{ font-size:1em; font-family:inherit; line-height:inherit;}

.rect{ border:solid #6699cc 1px;}
.rectHover{ border:solid #f60 1px;}
</style><script type="text/javascript" src="../Framework/Main.js"></script>
<script type="text/javascript" src="../Framework/Resize.js"></script>
<script type="text/javascript" src="../Framework/Controls/Paper.js"></script>
<script>
var hotareaData=[{"id":"_rect0","css":"padding: 0pt; position: absolute; z-index: 10; left: 241px; top: 420px; height: 115px; width: 135px;","hashData":"{title:\"泽元软件\",url:\"http:\/\/www.sky.com\"}"}, {"id":"_rect1","css":"padding: 0pt; position: absolute; z-index: 10; left: 19px; top: 461px; height: 75px; width: 203px;","hashData":"{title:\"泽元软件\",url:\"http:\/\/www.sky.com\"}"}];
function setProperties(hashDataElem,evt){
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 300;
	diag.Title = "设置属性";
	diag.URL = "Document/DocListDialog.jsp&CatalogID=";
	diag.onLoad = function(){
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	hashDataElem.value=JSON.toString({title:"新闻标题",url:"http://www.sky.com"});
}

Page.onLoad(function(){
	new Paper('drawPaper',setProperties,$id('textarea1').value,true)
});

</script>
</head>
<body>
	
<div id='rect'
	style='border:1px solid #0000FF;position:absolute;background-color: #99CCFF;filter:alpha(opacity=50);opacity:0.5;'>
</div>
<input type="hidden" id="CatalogID">
<input type="hidden" id="ListType" value='${ListType}'>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="blockTable">
			<tr>
			</tr>
			<tr>
				<td style="padding:4px 8px;"><img src="../Icons/icon018a1.gif" />图片</td>
				<td style="padding:8px 10px;" class="blockTd"><img
					src="../Icons/icon018a1.gif" />文档列表</td>
			</tr>
			<tr>
				<td width="65%" valign="top"
					style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;"
					align="center">

				<div
					style="overflow:auto; height:100% border:#666 1px solid; background-color:#999999; text-align:center; padding:10px"
					id="PaperImage"><img id="drawPaper"
					src="Images/2008070301_brief.jpg" ></div>
				</td>
				<td width="35%" valign="top"
					style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<sky:datagrid id="dg1"
					method="com.xdarkness.cms.document.ArticleList.dg1DataBind" size="15"
					page="false">
					<table width="100%" height="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr xtype="head" class="dataTableHead">
							<td width="13%" height="30" xtype="RowNo" drag="true"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="35%" sortfield="title"><b>标题</b></td>
							<td width="27%"><strong>坐标</strong></td>
							<td width="15%"><strong>预览</strong></td>
						</tr>
						<tr style1="background-color:#FFFFFF"
							style2="background-color:#F9FBFC">
							<td>&nbsp;</td>
							<td>${Title}</td>
							<td>${Author}</td>
							<td><a href="./Preview.jsp?ArticleID=${ID}" target="_blank">预览</a></td>
						</tr>
					</table>
				</sky:datagrid></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
 <textarea id="textarea1" cols="60" rows="8" style="display:none">[{"id":"_rect0","css":"padding: 0pt; position: absolute; z-index: 10; left: 241px; top: 420px; height: 115px; width: 135px;","hashData":{"title":"我院参加缅甸区块钻井研讨会","url":"http://www.sky.com"}},{"id":"_rect1","css":"padding: 0pt; position: absolute; z-index: 10; left: 19px; top: 461px; height: 75px; width: 203px;","hashData":{"title":"院领导到实验室检查工作","url":"http://www.sky.com"}}]</textarea>
</body>
</html>
