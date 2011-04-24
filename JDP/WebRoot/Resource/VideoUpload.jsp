<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<%
String CatalogID = request.getParameter("CatalogID");
QueryBuilder qb = null;
if(CatalogID!=null&&!"".equals(CatalogID)&&!"null".equals(CatalogID)){
	qb = new QueryBuilder("select id,name from zccatalog where type=" + Catalog.VIDEOLIB + " and id =?",CatalogID);
}else{
	qb = new QueryBuilder("select id,name from zccatalog where type=" + Catalog.VIDEOLIB + " and siteid=?",Application.getCurrentSiteID());
}
DataTable dt = qb.executePagedDataTable(1,0);

String value = "";
String text = "";
String customColumn = "";
if (dt != null && dt.getRowCount() > 0) {
	value = dt.getString(0,0);
	text = dt.getString(0,1);
	customColumn = ColumnUtil.getHtml(ColumnUtil.RELA_TYPE_CATALOG_COLUMN, dt.getString(0,"ID"));
}
%>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
Page.onLoad(function(){
	Selector.setValueEx("SelectCatalogID",'<%=value%>','<%=text%>');
});

function setCatalogID(){
	$S("CatalogID",$V("SelectCatalogID"));
	$S('OtherIDs',$NV('OtherID'));
}

function upload(){
	var sid = $V("SelectCatalogID");
	var Integral = $V("Integral");
	if(sid==null||sid=="0"){
		Dialog.alert("您没有选择 视频 分类，请选择。");
		return;
	}
	if(isNaN(Integral)){
		Dialog.alert("积分只能是数字，请重新填写。");
		return;
	}
	if(getUploader("VideoFile").hasFile()&&!getUploader("VideoFile").hasError()){
		setCatalogID();
		var dc = Form.getData($F("form2"));
		getUploader("VideoFile").addPostParam(dc.toQueryString());
		getUploader("VideoFile").doUpload();
		$E.disable(window.parent.Parent.$D.CancelButton);
		$E.disable(window.parent.Parent.$D.OKButton);
	}else{
		Dialog.alert("未选择文件或文件选择有误");	
	}
}

/** 可选参数FileIDs,FileTypes,FilePaths,FileStatus */
function onUploadCompleted(FileIDs){
	if(customPicName==null||customPicName==""){
		window.parent.Parent.onUploadVideoCompleted(FileIDs);
	}else{
		onCustomReturnBack(FileIDs)
	}
}

function browse(){
	var diag = new Dialog("Diag2");
	diag.Width = 300;
	diag.Height = 400;
	diag.Title = "视频分类列表";
	diag.URL = "Resource/VideoLibListDialog.jsp";
	diag.OKEvent = browseSave;
	diag.show();
}

function browseSave(){
	var arr = $DW.$NV("ID");
	if(!arr||arr.length==0){
		Dialog.alert("请先选中视频分类！");
		return;
	}
	var OtherLibTable = $("OtherLibTable");
	var sb = [];
	var catalogid=$V("SelectCatalogID");
	for(var i=0;i<arr.length;i++){
		if(arr[i]==catalogid){
			continue;
		}
		sb.push("<tr><td><input type='hidden' name='OtherID' value='"+arr[i]+"'>"+$DW.$("span_"+arr[i]).innerHTML+"</td><td><img src='../Framework/Images/icon_cancel.gif' title='删除' onclick='deleteOtherID(this);' /></td></tr>");	
	}
	OtherLibTable.outerHTML="<table width='100%' border='1' cellspacing='0' id='OtherLibTable' bordercolor='#eeeeee'>"+sb.join('')+"</table>";
	$D.close();
}

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

function deleteOtherID(ele){
	ele.parentElement.parentElement.parentElement.deleteRow(ele.parentElement.parentElement.rowIndex);
	return false;
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<sky:init method="com.xdarkness.cms.resource.Video.initUploadDialog">
	<body>
	<form id="form2">
	<input type="hidden" id="FileType" name="FileType" value="Video">
	<input type="hidden" id="CatalogID" name="CatalogID" value="${CatalogID}">
	<input type="hidden" id="OtherIDs" name="OtherIDs" value="">
	<table width="100%" cellpadding="2" cellspacing="0">
		<tr>
			<td height="240px">
			<fieldset><legend> <strong>视频上传:(支持<%=Config.getValue("AllowVideoExt")%>等文件的上传)</strong></legend>
			<table id="Videotable0" style="display:" width="100%" cellpadding="2"
				cellspacing="0">
				<tr>
					<td width="16%"><label></label></td>
					<td width="84%"><label></label></td>
				</tr>
				<tr>
					<td width='16%'><label>视频浏览：</label></td>
					<td><sky:uploader id="VideoFile" uploadtype="Video" action="../Editor/filemanager/upload/zuploader.jsp" onComplete="onUploadCompleted"/></td>
				</tr>
				<tr>
					<td width="16%"><label>视频名称：</label></td>
					<td width="84%"><input id="FileName" name="FileName" type="text"
						class="input1" value="" /></td>
				</tr>
				<tr>
					<td width="16%">视频标签：</td>
					<td width="84%"><input id="Tag" name="Tag" type="text" value="" class="input1" /></td>
				</tr>
				<tr>
					<td width="16%">积分：</td>
					<td width="84%"><input id="Integral" name="Integral" type="text" value="" class="input1" /></td>
				</tr>
				<tr>
					<td width="16%"><label>是否原创：</label></td>
					<td width="84%">${IsOriginal}</td>
				</tr>
				<tr>
					<td width="16%"><label> 视频描述：</label></td>
					<td width="84%"><textarea id="Info" name="Info" cols="100"
						rows="1" class="input3" style="height:45px "></textarea></td>
				</tr>
			</table>
			</fieldset>
			</td>
		</tr>
		<tr>
			<td style="height:150px">
			<fieldset><legend> <label><strong>参数设置</strong></label></legend>
			<table width="100%" cellpadding="2" cellspacing="0">
				<tr>
					<td width="58%" align="left">
					<table width="100%" cellpadding="2" cellspacing="0">
						<tr>
							<td width="16%" align="right">所属主分类：</td>
							<td colspan="2"><sky:select id="SelectCatalogID"
								listWidth="200" listHeight="300"
								listURL="Resource/VideoLibSelectList.jsp"> </sky:select></td>
						</tr>
						<tr>
							<td valign="top" align="right">所属其他分类：</td>
							<td width="18%">
							<div style="overflow:auto;height:70px;width:122px;">
							<table width="100%" border="1" cellspacing="0" id="OtherLibTable" bordercolor="#eeeeee">
								<tr>
									<td width="55%">&nbsp;</td>
									<td width="45%">&nbsp;</td>
								</tr>
								<tr>
									<td width="55%">&nbsp;</td>
									<td width="45%">&nbsp;</td>
								</tr>
								<tr>
									<td width="55%">&nbsp;</td>
									<td width="45%">&nbsp;</td>
								</tr>
							</table>
							</div>
							</td>
							<td width="66%" valign="top"><input type="button"
								class="input2" onClick="browse();" value="选择" /></td>
                        </tr>
					</table>
                    <table width="100%" cellpadding="2" cellspacing="0">
					<%=customColumn %>
                    </table>
					</td>
				</tr>
			</table>
			</fieldset>
			</td>
		</tr>
	</table>
	</form>
	</body>
</sky:init>
</html>
