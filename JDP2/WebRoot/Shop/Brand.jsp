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

});

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 300;
	diag.Title = "新建商品品牌";
	diag.URL = "Shop/BrandDialog.jsp";
	diag.onLoad = function(){
		$DW.$("Name").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.xdarkness.shop.Brand.add",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData('dg1');
			}
		});
	});
}

function edit(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs || drs.length==0){
		Dialog.alert("请先选择要修改的信息！");
		return;
	}else if(drs.length > 1){
		Dialog.alert("每次只能修改一条信息！");
		return;
	}
	dr = drs[0]; 
	
	var diag = new Dialog("Diag2");
	diag.Width = 500;
	diag.Height = 300;
	diag.Title = "编辑商品品牌";
	diag.URL = "Shop/BrandDialog.jsp?ID=" + dr.get("ID");
	diag.onLoad = function(){
		$DW.$("Name").focus();
	};
	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.xdarkness.shop.Brand.edit",dc,function(response){
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
	if(!arr || arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("您确认要删除吗？", function(){
		var dc = new DataCollection();
		dc.add("IDs", arr.join());
		Server.sendRequest("com.xdarkness.shop.Brand.del", dc, function(response){
			if(response.Status == 0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert(response.Message);
				DataGrid.loadData('dg1');
			}
		});
	});
}

function doSearch(){
	var searchWord = "";
	if ($V("BrandName") != "请输入品牌名称") {
		searchWord = $V("BrandName");
	}	
	DataGrid.setParam("dg1", Constant.PageIndex,0);
	DataGrid.setParam("dg1", "SearchWord", searchWord);
	DataGrid.loadData("dg1");
}

function delKeyWord() {
	var keyWord = $V("BrandName");
	if (keyWord == "请输入品牌名称") {
		$S("BrandName", "");
	}
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if(ele.id == 'BrandName'||ele.id == 'Submitbutton'){
			doSearch();
		}
	}
}
</script>
</head>
<body>
<%
String path = (Config.getContextPath() + Config.getValue("UploadDir") + "/" 
		+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/").replaceAll("//","/");
%>
<sky:init method="com.xdarkness.shop.Brand.init">
	<table width="100%" border="0" cellspacing="6" cellpadding="0" 
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td valign="middle" class="blockTd">
						<img src="../Icons/icon047a1.gif" />商品品牌列表
					</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;">
						<sky:button onClick="add()">
							<img src="../Icons/icon047a2.gif" />新建</sky:button> 
						<sky:button onClick="edit()">
							<img src="../Icons/icon047a4.gif" />编辑</sky:button> 
						<sky:button onClick="del()">
							<img src="../Icons/icon047a3.gif" />删除</sky:button> 
						<!-- <sky:button onClick="importWord()">
							<img src="../Icons/icon007a8.gif" />导入</sky:button> 
						<sky:button onClick="exportWord()">
							<img src="../Icons/icon007a7.gif" />导出</sky:button> -->
						<div style="float: right; white-space: nowrap;">
							<input name="BrandName" type="text" id="BrandName" value="请输入品牌名称"
								onFocus="delKeyWord();" style="width:110px" /> 
							<input type="button" name="Submitbutton" value="查询" id="Submitbutton"
								onClick="doSearch()" />
						</div>
					</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<sky:datagrid id="dg1" method="com.xdarkness.shop.Brand.dg1DataBind" size="13">
						<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
							<tr xtype="head" class="dataTableHead">
								<td width="5%" xtype="RowNo"><strong>序号</strong></td>
								<td width="5%" xtype="selector" field="ID">&nbsp;</td>
								<td width="10%"><b>品牌缩略图</b></td>
								<td width="13%"><b>品牌名称</b></td>
								<td width="20%"><b>品牌URL</b></td>
								<td width="20%"><b>品牌描述</b></td>
								<td width="15%"><b>添加时间</b></td>
								<td width="12%"><b>添加人</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td width="5%">&nbsp;</td>
								<td width="5%">&nbsp;</td>
								<td align="center"><img alt="${Name}" src="<%=path%>${ImagePath}" width="80" height="60"/></td>
								<td>${Name}</td>
								<td>${URL}</td>
								<td>${Info}</td>
								<td>${AddTime}</td>
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
