<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>版位管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 450;
	diag.Height = 260;
	diag.Title = "新建版位";
	diag.URL = "DataService/AdvertiseLayoutDialog.jsp?RelaCatalogID="+$V("RelaCatalogID");
	diag.onLoad = function(){
		$DW.$("PositionName").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}
function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.xdarkness.cms.dataservice.AdvertiseLayout.add",dc,function(response){
		if(response.Status==1){
			Dialog.alert("新增版位成功",function(){
				$D.close();
				if(currentTreeItem){
					Tree.loadData("tree1",function(){Tree.select("tree1","cid",currentTreeItem.getAttribute("cid"))});
				}else{
					Tree.loadData("tree1");
				}
				DataGrid.loadData("dg1");	
			});
		}
		else{
			Dialog.alert(response.Message);
		}
	});
}
function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的版位！");
		return;
	}
	
	Dialog.confirm("确定要删除版位吗？",function(){
		var dc = new DataCollection();
			dc.add("IDs",arr.join());
			Server.sendRequest("com.xdarkness.cms.dataservice.AdvertiseLayout.del",dc,function(response){
				if(response.Status==1){
					Dialog.alert(response.Message,function(){
					   if(currentTreeItem){
						   Tree.loadData("tree1",function(){Tree.select("tree1","cid",currentTreeItem.getAttribute("cid"))});
					   }
					   DataGrid.loadData("dg1");
					});
				}
				else{
					Dialog.alert(response.Message);
				}
			});
	})
}

function copy(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要复制的版位！");
		return;
	}
	if(arr.length>1){
		Dialog.alert("复制操作只能针对一个版位！");
		return;
	}
	var dc = new DataCollection();
	dc.add("ID",arr[0]);
	Server.sendRequest("com.xdarkness.cms.dataservice.AdvertiseLayout.copy",dc,function(response){
		if(response.Status==1){
			Dialog.alert("复制版位成功",function(){
				DataGrid.loadData("dg1");
			});
		}else{
			Dialog.alert(response.Message);
		}
	});
}

function editDialog(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("请先选择要编辑的版位！");
		return;
	}
	dr = drs[0];
	var diag = new Dialog("Diag1");
	diag.Width = 450;
	diag.Height = 260;
	diag.Title = "编辑版位基本信息";
	diag.URL = "DataService/AdvertiseLayoutDialog.jsp?ID="+dr.get("ID")+ "&RelaCatalogID=" + dr.get("RelaCatalogID");
	diag.onLoad = function(){
    	$DW.$S("ID",dr.get("ID"));
		$DW.$("PositionName").focus();
	};
	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.xdarkness.cms.dataservice.AdvertiseLayout.add",dc,function(response){
		if(response.Status==1){
			Dialog.alert("修改版位成功",function(){
				$D.close();
				if(currentTreeItem){
					Tree.loadData("tree1",function(){Tree.select("tree1","cid",currentTreeItem.getAttribute("cid"))});
				}
				DataGrid.loadData("dg1");
			});
		}
		else{
			Dialog.alert(response.Message);
		}
	});
}

function preview(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要预览的广告版位！");
		return;
	}
	window.open("AdvertisePreview.jsp?ID="+arr[0])
}

function doSearch(){
	var name = "";
	if($V("SearchContent").trim()!= "请输入要查询的版位名称"){
		name = $V("SearchContent");
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","SearchContent",name);
	DataGrid.loadData("dg1");
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if(ele.id == 'SearchContent'||ele.id == 'Submitbutton'){
			doSearch();
		}
	}
}

function delKeyWord() {
	var keyWord = $V("SearchContent");
	if (keyWord == "请输入要查询的版位名称") {
		$S("SearchContent","");
	}
}

function editAdContent(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择广告版位！");
		return;
	}
	var diag = new Dialog("diag1");
	diag.Width = 520;
	diag.Height = 460;
	diag.Title = "编辑广告内容";
	diag.URL = "DataService/AdvertiseDialog.jsp?PosID="+arr[0]+"&addType=layout";
	diag.OKEvent = editAdSave;
	diag.show();
}

function editAdSave(){
	if($DW.Verify.hasError()){
		return;
	}
	var dc = Form.getData($DW.$F("adform"));
	if($DW.$V("AdType")=="image"){
		if($DW.checkImgPath()){
			dc.add("imgADLinkUrl",$DW.$NV("imgADLinkUrl").join("^"));
			dc.add("imgADAlt",$DW.$NV("imgADAlt").join("^"));
			dc.add("ImgPath",$DW.$NV("ImgPath").join("^"));
		}else{
			Dialog.alert("请为图片广告选择图片");
			return;
		}
	}else if($DW.$V("AdType")=="flash"){
		dc.add("SwfFilePath",$DW.$NV("SwfFilePath").join("^"));
	}else if($DW.$V("AdType")=="text"){
		dc.add("textContent",$DW.$NV("textContent").join("^"));
		dc.add("textLinkUrl",$DW.$NV("textLinkUrl").join("^"));
		dc.add("TextColor",$DW.$NV("TextColor").join("^"));
	}
	Server.sendRequest("com.xdarkness.cms.dataservice.Advertise.add",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){$D.close();DataGrid.loadData("dg1");});
		}else{
			Dialog.alert(response.Message);
			return;
		}
	});
}

function showAdList(ID,PositionName){
	var arr;
	if(ID==""||ID==null){
		arr = DataGrid.getSelectedValue("dg1");
		if(!arr||arr.length==0){
			Dialog.alert("请先选择广告版位！");
			return;
		}
		ID = arr[0];
	}
	var diag = new Dialog("Diag1");
	diag.Width = 720;
	diag.Height = 380;
	diag.Title = "广告列表";
	diag.URL = "DataService/AdvertiseListDialog.jsp?PosID="+ID+"&PosName="+PositionName;
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}

function loadAdData(){
	DataGrid.loadData("dg1");	
}

var currentTreeItem;
function onTreeClick(ele){
	currentTreeItem = ele;
	var cid =  ele.getAttribute("cid");
	var code = ele.getAttribute("innercode");
	$S("RelaCatalogID",cid);
	DataGrid.setParam("dg1","CatalogID",cid);
	DataGrid.setParam("dg1","CatalogInnerCode",code);
	DataGrid.loadData("dg1");
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
<tr valign="top">
	<td>
    <table width="180" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td style="padding:6px;" class="blockTd"><sky:tree id="tree1"
					style="height:450px;width:160px;"
					method="com.xdarkness.cms.dataservice.AdvertiseLayout.treeDataBind" level="2"
					lazy="true">
					<p cid='${ID}' innercode='${InnerCode}' parentid='${ParentID}' onClick="onTreeClick(this);" oncontextmenu="showMenu(event,this);">${Name}</p>
				</sky:tree></td>
			</tr>
		</table>
    </td>
    <td>
	<table width="100%" border="0" cellpadding="4" cellspacing="0" class="blockTable">
      <tr>
        <td valign="middle" class="blockTd"><img src="../Icons/icon010a1.gif"/> 广告版位列表
        <input type="hidden" id="RelaCatalogID" name="RelaCatalogID" value="0"/></td>
      </tr>
				<tr>
					<td style="padding:0 8px 4px;"><sky:button onClick="add();"><img src="../Icons/icon010a2.gif" />新建</sky:button> 
        <sky:button onClick="editDialog();"><img src="../Icons/icon010a16.gif" />修改</sky:button>
        <sky:button onClick="del();"><img src="../Icons/icon010a3.gif" />删除</sky:button> 
        <sky:button onClick="copy();"><img src="../Icons/icon010a12.gif" />复制</sky:button>
        <sky:button onClick="showAdList();"><img src="../Icons/icon010a12.gif" />广告列表</sky:button>
        <sky:button onClick="editAdContent();"><img src="../Icons/icon010a4.gif" />修改广告内容</sky:button>
        <sky:button onClick="preview();"><img src="../Icons/icon010a15.gif" />预览</sky:button>
		<div style="display:none;float: right; white-space: nowrap;">
		<input name="SearchContent" type="text" id="SearchContent" value="请输入要查询的版位名称" onFocus="delKeyWord();" style="width:150px">
		<input type="button" name="Submitbutton" id="Submitbutton" value="搜索" onClick="doSearch()">
		</div>
        </td>
      </tr>
      <tr>
        <td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
		<sky:datagrid id="dg1" method="com.xdarkness.cms.dataservice.AdvertiseLayout.dg1DataBind"	size="15">
		<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
			<tr xtype="head" class="dataTableHead">
				<td width="5%" xtype="RowNo">序号</td>
				<td width="3%" xtype="selector" field="id">&nbsp;</td>
				<td width="17%"><b>版位名称</b></td>
				<td width="15%"><b>JS路径</b></td>
                <td width="10%"><b>所属栏目</b></td>
                <td width="10%"><b>版位类型</b></td>
                <td width="10%"><b>广告类型</b></td>
				<td width="10%"><b>尺寸</b></td>
                <td width="19%"><b>版位描述</b></td>
			</tr>
			<tr onDblClick="showAdList(${ID},'${PositionName}')" style1="background-color:#FFFFFF"
				style2="background-color:#F9FBFC">
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>${PositionName}</td>
				<td>${JSName}</td>
                <td>${CatalogName}</td>
                <td>${PositionTypeName}</td>
                <td>${ADTypeName}</td>
				<td>${PositionSizeWidth}*${PositionSizeHeight}</td>
                <td title="${Description}">${Description}</td>
			</tr>
			<tr xtype="pagebar">
				<td colspan="9" align="center">${PageBar}</td>
			</tr>
		</table>		
		</sky:datagrid>
    </td>
  </tr>
</table>
</td>
</tr>
</table>
</body>
</html>