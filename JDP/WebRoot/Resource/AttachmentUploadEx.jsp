<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="controls" prefix="sky"%>

<%@page import="com.xdarkness.framework.data.QueryBuilder"%>
<%@page import="com.xdarkness.framework.data.DataTable"%>
<%@page import="com.xdarkness.platform.Application"%>
<%@page import="com.xdarkness.framework.Config"%><html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<%
	String CatalogID = request.getParameter("CatalogID");
	QueryBuilder qb = null;
	if(CatalogID!=null&&!"".equals(CatalogID)&&!"null".equals(CatalogID)){
		qb = new QueryBuilder("select id,name from zccatalog where type=7 and id =?",CatalogID);
	}else{
		qb = new QueryBuilder("select id,name from zccatalog where type=7 and siteid=? order by id",Application.getCurrentSiteID());
	}
	DataTable dt = qb.executePagedDataTable(1,0);
	String value = dt.getString(0,0);
	String text = dt.getString(0,1);
%>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
Page.onLoad(function(){
	Selector.setValueEx("SelectCatalogID",'<%=value%>','<%=text%>');
});

function setCatalogID(){
	$S("CatalogID",$V("SelectCatalogID"));
}

var extsStr = '<%=Config.getValue("AllowAttachExt")%>';
var exts = extsStr.split(",");
function upload(){
	var sid = $V("SelectCatalogID");
	var flag = false;var vidName = $V("File1");
	var ext = vidName.substring(vidName.lastIndexOf(".")+1).toLowerCase();
	if(vidName==""){
			flag = false;
	}else{
		for(var j=0; j<exts.length; j++) {
			if(ext.trim()==exts[j].toLowerCase().trim()) {
				flag = true;
			}
		}
		if(!flag) {
			Dialog.alert("附件上传不支持"+ext+"文件，请重新选择！");
			return;
		}
	}
	if(flag){
		if(sid==null){
			Dialog.alert("您没有选择 附件 分类，请选择。");
			return;
		}
		setCatalogID();
		msg();
		$S('CatalogID',$V('SelectCatalogID'));
		$F("form2").submit();
	}else{
		Dialog.alert("请先浏览选择文件!");
		return;
	}
}

var counter=1;
function msg(){
		  var txt = "正在上传处理中，请稍后...耗时";
			setInterval(function(){$("divMsg").innerHTML="<font color=red>"+txt+counter+"秒</font>";counter++}, 1000);
	}

function getExtname(filename){
	var pos = filename.lastIndexOf("\\");
	return (filename.substr(pos+1));
}

function setNameInfo(ele){
	var name = getExtname(ele.value);
	name = name.substring(0,name.lastIndexOf("."));
	var id =ele.id;
	$S(id+"Name",name);
	//$S(id+"Info",name);
}

function onUploadCompleted(returnValue,returnID,errorMessage,returnPaths){
	switch ( returnValue )
	{
		case 0 :	// No errors
			//alert( 'Your file has been successfully uploaded' ) ;
			break ;
		//case 1 :	// Custom error
		//	alert( customMsg ) ;
		//	return ;
		//case 101 :	// Custom warning
		//	alert( customMsg ) ;
		//	break ;
		//case 201 :
		//	alert( 'A file with the same name is already available. The uploaded file has been renamed to "' + fileName + '"' ) ;
		//	break ;
		case 202 :
			Dialog.alert( '无效的文件类型' ) ;
			return ;
		case 203 :
			Dialog.alert( "Security error. You probably don't have enough permissions to upload. Please check your server." ) ;
			return ;
		default :
			Dialog.alert( 'Error on file upload. Error number: ' + returnValue ) ;
			return ;
	}
	window.parent.onReturnBack(returnPaths);
	try {
	  window.parent.Dialog.close();
  }catch(ex){}
}

function browse(){
	var diag = new Dialog("Diag2");
	diag.Width = 300;
	diag.Height = 400;
	diag.Title = "附件分类列表";
	diag.URL = "Resource/AttachmentLibListDialog.jsp";
	diag.OKEvent = browseSave;
	diag.show();
}


function browseSave(){
	var arr = $DW.$NV("ID");
	if(!arr||arr.length==0){
		Dialog.alert("请先选中附件分类！");
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

function deleteOtherID(ele){
	ele.parentElement.parentElement.parentElement.deleteRow(ele.parentElement.parentElement.rowIndex);
	return false;
}


function imageBrowse(){
	var diag = new Dialog("Diag4");
	diag.Width = 780;
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
	Server.sendRequest("com.xdarkness.cms.resource.AttachmentLib.getPicSrc",dc,function(response){
		$("Pic").src = response.get("picSrc");
		$("ImagePath").value = response.get("ImagePath");
	})
}

function showImage(){
  $("tr").show();
  $("link1").hide();
  $("link2").show();
}

function hideImage(){
 $("tr").hide();
 $("link1").show();
 $("link2").hide();
}




</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<div style="display:none"><iframe name="formTarget"
	src="javascript:void(0)"></iframe></div>
<form enctype="multipart/form-data" id="form2"
	action="../Editor/filemanager/upload/simpleuploader.jsp" method="post"
	target="formTarget">
<input type="hidden" id="FileType" name="FileType" value="Attach">
<input type="hidden" id="CatalogID" name="CatalogID" value="">
<table width="100%" align="center" cellpadding="2" cellspacing="0">
	<tr>
			<td  valign="top">
			<fieldset><legend> <strong>附件:(支持<%=Config.getValue("AllowAttachExt")%>等文件的上传)</strong></legend>
			<table id="Videotable0" style="display:" width="100%" cellpadding="2"
				cellspacing="0">
				<tr>
					<td width="16%"><label></label></td>
					<td width="84%" colspan="2"><label></label></td>
				</tr>
				<tr>
					<td><label>附件浏览：</label></td>
					<td colspan="2"><input name='File1' id='File1' type='file' value=''
						size='26' onChange="setNameInfo(this);">
						</td>
				</tr>
				<tr>
					<td><label>附件名称：</label></td>
					<td colspan="2"><input id="File1Name" name="File1Name" type="text"
						class="input1" value="" /></td>
				</tr>
			</table>
			</fieldset>
			</td>
		</tr>
		<tr >
			<td  valign="top">
			<fieldset><legend> <label><strong>参数设置</strong></label></legend>
			<table width="100%" cellpadding="2" cellspacing="0">
				<tr>
					<td width="58%" align="left">
					<table width="100%" cellpadding="2" cellspacing="0">
						<tr>
							<td width="21%">所属主分类:</td>
							<td colspan="2"><sky:select id="SelectCatalogID"
								listWidth="200" listHeight="300"
								listURL="Resource/AttachmentLibSelectList.jsp"> </sky:select></td>
                                <td><input type="button" value=" 开始上传 " onClick="upload()" /></td>
						</tr>
					</table>
					<div id="divMsg" align="center"></div>
					</td>
				</tr>
			</table>
			</fieldset>
			</td>
		</tr>
</table>
</form>
<div id="divMsg" align="center"></div>
</body>
</html>
