<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>图片库</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function doSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","Search","Search");
	DataGrid.setParam("dg1","CatalogID",$V("CatalogID"));
	DataGrid.setParam("dg1","Name",$V("Name"));
	DataGrid.setParam("dg1","Info",$V("Info"));
	DataGrid.loadData("dg1");
}

function onFileReturnBack(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要返回的附件！");
		return;
	}
	var dc = new DataCollection();
	dc.add("AttachID",arr[0]);
	Server.sendRequest("com.xdarkness.cms.resource.Attachment.getAttachSrc",dc,function(response){
		path = response.get("AttachPath");
		if(window.parent.onReturnBack){
			window.parent.onReturnBack(path);
		}
		window.parent.Dialog.close();
	});
}
</script>
</head>
<body oncontextmenu="return false;">
<div style="height:100%;overflow:auto;">
<table width="100%" cellpadding="2" cellspacing="0">
	<tr>
		<td>
		<table width="100%" cellpadding="2" cellspacing="0" id="tbSearch">
			<tr>
				<td width="18%" align="left"><label>所属分类:<sky:select
					id="CatalogID" style="width:80px" listWidth="200" listHeight="300"
					listURL="Resource/AttachmentLibSelectList.jsp"> </sky:select></label></td>
				<td width="7%">名称：</td>
				<td width="17%"><input name="Name" id="Name" type="text"
					value=""></td>
				<td width="8%">描述：</td>
				<td width="19%"><input name="Info" id="Info" type="text"
					value=""></td>
				<td width="20%">
                <sky:button id="searchButton" onClick="doSearch()"><img src="../Icons/icon003a8.gif" />查询</sky:button>
                <sky:button id="returnButton" onClick="onFileReturnBack()"><img src="../Icons/icon003a8.gif" />确定</sky:button>
                </td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td style="padding:0px 5px;"><sky:datagrid id="dg1"
			method="com.xdarkness.cms.resource.AttachmentLib.dg1DataBindBrowse"
			size="10">
			<table class="dataTable" cellspacing="0" cellpadding="2" width="100%">
				<tr xtype="head" class="dataTableHead">
					<td width="4%" xtype="RowNo"></td>
					<td width="5%" xtype="selector" field="ID">&nbsp;</td>
					<td width="20%"><b>附件名称</b></td>
					<td width="30%"><b>附件描述</b></td>
					<td width="7%"><b>附件大小</b></td>
					<td width="8%"><b>附件格式</b></td>
					<td width="9%"><b>上传用户</b></td>
					<td width="17%"><b>上传时间</b></td>
				</tr>
				<tr style1="background-color:#FFFFFF; cursor:default;"
					style2="background-color:#F9FBFC; cursor:default;">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td><font color="#0099FF">${Name}</font></td>
					<td>${Info}</td>
					<td>${FileSize}</td>
					<td>${SuffixImage}</td>
					<td>${AddUser}</td>
					<td>${AddTime}</td>
				</tr>
				<tr xtype="pagebar">
					<td colspan="8">${PageBar}</td>
				</tr>
			</table>
		</sky:datagrid></td>
	</tr>
</table>
</div>
</body>
</html>
