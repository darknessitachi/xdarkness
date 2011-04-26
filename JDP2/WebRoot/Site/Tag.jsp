<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		dr.set("Tag",$V("Tag"));
		dr.set("LinkURL",$V("LinkURL"));
		dr.set("TagText",$V("TagText"));		
		return true;
	}
});

function save(){
	DataGrid.save("dg1","com.xdarkness.cms.site.Tag.dg1Edit",function(){
		DataGrid.setParam("dg1",Constant.PageIndex,0);
		DataGrid.loadData("dg1");
		});
}

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 150;
	diag.Title = "新建Tag";
	diag.URL = "Site/TagDialog.jsp";
	diag.onLoad = function(){
		$DW.$("Tag").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.xdarkness.cms.site.Tag.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
			}
		});
	});
}


function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
Dialog.confirm("您确认要删除吗？",function(){
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	Server.sendRequest("com.xdarkness.cms.site.Tag.del",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("删除Tag成功");
			DataGrid.loadData('dg1');
		}
	});
});
}

function importWord(){
	var diag = new Dialog("Diag1");
	diag.Width = 550;
	diag.Height = 370;
	diag.Title = "批量导入Tag";
	diag.URL = "Site/BadWordImport.jsp";
	diag.OKEvent = importSave;
	diag.show();
}

function importSave(){
	$DW.importWords();
}

function exportWord(){
	var arr = DataGrid.getSelectedValue("dg1");
	var ids;
	if(!arr||arr.length==0){
		ids = "";
	}else{
		ids = arr.join(",");	
	}
	window.location = "BadWordExport.jsp?ids="+ids;
}

function loadWordData(){
	DataGrid.loadData("dg1");	
}

function doSearch(){
	var TagkeyWord = "";
	if ($V("TagkeyWord") != "请输入Tag关键字") {
		TagkeyWord = $V("TagkeyWord");
	}	
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","TagWord",TagkeyWord);
	DataGrid.loadData("dg1");
}

function delKeyWord() {
	var keyWord = $V("TagkeyWord");
	if (keyWord == "请输入Tag关键字") {
		$S("TagkeyWord","");
	}
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if(ele.id == 'TagkeyWord'||ele.id == 'Submitbutton'){
			doSearch();
		}
	}
}
</script>
</head>
<body>
<sky:init method="com.xdarkness.cms.site.Tag.init">
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img
						src="../Icons/icon011a1.gif" /> Tag列表</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;"><sky:button onClick="add()">
						<img src="../Icons/icon011a2.gif" />新建</sky:button> <sky:button
						onClick="save()">
						<img src="../Icons/icon011a16.gif" />保存</sky:button> <sky:button
						onClick="del()">
						<img src="../Icons/icon011a3.gif" />删除</sky:button><!--   <sky:button
						onClick="importWord()">
						
						<img src="../Icons/icon007a8.gif" />导入</sky:button> <sky:button
						onClick="exportWord()">
						<img src="../Icons/icon007a7.gif" />导出</sky:button>
						-->
					<div style="float: right; white-space: nowrap;"><input
						name="TagkeyWord" type="text" id="TagkeyWord" value="请输入Tag关键字"
						onFocus="delKeyWord();" style="width:110px"> <input
						type="button" name="Submitbutton" value="查询" onClick="doSearch()"
						id="Submitbutton"></div>
					</td>
				</tr>
				<tr>
					<td
						style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<sky:datagrid id="dg1"
						method="com.xdarkness.cms.site.Tag.dg1DataBind" size="15">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr xtype="head" class="dataTableHead">
								<td width="5%" xtype="RowNo"><strong>序号</strong></td>
								<td width="5%" xtype="selector" field="id">&nbsp;</td>
								<td width="16%" sortfield="TagWord"><b>Tag名称</b></td>
								<td width="31%" class="wrap"><b>Tag链接</b></td>
								<td width="12%" class="wrap"><b>引用次数</b></td>
								<td width="19%" class="wrap"><b>Tag描述</b></td>
								<td width="12%"><b>添加人</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td width="5%">&nbsp;</td>
								<td width="5%">&nbsp;</td>
								<td>${Tag}</td>
								<td>${LinkURL}</td>
								<td>${UsedCount}</td>
								<td>${TagText}</td>
								<td>${AddUser}</td>
							</tr>
							<tr xtype="edit" bgcolor="#E1F3FF">
								<td width="5%" bgcolor="#E1F3FF">&nbsp;</td>
								<td width="5%" bgcolor="#E1F3FF">&nbsp;</td>
								<td><input name="Tag" type="text" class="input1" id="Tag"
									value="${Tag}" size="20"></td>
								<td><input name="LinkURL" type="text" class="input1"
									id="LinkURL" value="${LinkURL}" size="30"></td>
								<td>${UsedCount}</td>
								<td><input name="TagText" type="text" class="input1"
									id="TagText" value="${TagText}" size="30"></td>
								<td>${AddUser}</td>
							</tr>
							<tr xtype="pagebar">
								<td colspan="7" align="center">${PageBar}</td>
							</tr>
						</table>
					</sky:datagrid></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</sky:init>
</body>
</html>