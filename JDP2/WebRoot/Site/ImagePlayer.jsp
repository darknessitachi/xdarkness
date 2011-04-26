<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html> 
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>图片播放器</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 800;
	diag.Height = 400;
	diag.Title = "新建"+this.document.title;
	diag.URL = "Site/ImagePlayerDialog.jsp?ImagePlayerID=&RelaCatalog="+$V("CatalogInnerCode");
	diag.ShowButtonRow = false;
	diag.show();
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
		Server.sendRequest("com.xdarkness.cms.site.ImagePlayer.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("dg1");
					if(currentTreeItem){
						Tree.loadData("tree1",function(){Tree.select("tree1","cid",currentTreeItem.getAttribute("cid"))});
					}
				}
			});
		});
	});
}

function edit(){
	var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("请先选择要编辑的播放器！");
		return;
	}
	if(drs.length>1){
		Dialog.alert("只能修改1条信息！");
		return;
	}
	dr = drs[0];
	
	var diag = new Dialog("Diag1");
	diag.Width = 800;
	diag.Height = 500;
	diag.Title = this.document.title;
	var url = "Site/ImagePlayerDialog.jsp?ImagePlayerID="+dr.get("ID")+"&ImageSource="+dr.get("ImageSource") + "&RelaCatalog=" + dr.get("RelaCatalogInnerCode");
	diag.URL = url;
	diag.ShowButtonRow = false;
	diag.show();
}

var currentTreeItem;
function onTreeClick(ele){
	currentTreeItem = ele;
	var cid =  ele.getAttribute("cid");
	var code = ele.getAttribute("innercode");
	$S("CatalogInnerCode",code);
	DataGrid.setParam("dg1","CatalogID",cid);
	DataGrid.setParam("dg1","CatalogInnerCode",code);
	DataGrid.loadData("dg1");
}

</script>
</head>
<body scroll="no">
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
        <table width="180" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td style="padding:6px;" class="blockTd"><sky:tree id="tree1"
					style="height:450px;width:160px;"
					method="com.xdarkness.cms.site.ImagePlayer.treeDataBind" level="2"
					lazy="true">
					<p cid='${ID}' innercode='${InnerCode}' parentid='${ParentID}' onClick="onTreeClick(this);" oncontextmenu="showMenu(event,this);">${Name}</p>
				</sky:tree></td>
			</tr>
		</table>
        </td>
        <td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td valign="middle" class="blockTd"><img src="../Icons/icon039a1.gif" /> 播放器列表
                <input type="hidden" id="CatalogInnerCode" name="CatalogInnerCode" value="" />
                </td>
			</tr>
			<tr>
				<td style="padding:0 8px 4px;"><sky:button onClick="add()">
					<img src="../Icons/icon039a2.gif" />新建</sky:button> <sky:button onClick="edit()">
					<img src="../Icons/icon039a4.gif" />编辑</sky:button> <sky:button onClick="del()">
					<img src="../Icons/icon039a3.gif" />删除</sky:button></td>
			</tr>
			<tr>
				<td
					style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<sky:datagrid id="dg1"
					method="com.xdarkness.cms.site.ImagePlayer.dg1DataBind" size="15">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr xtype="head" class="dataTableHead">
							<td width="5%" xtype="RowNo">序号</td>
							<td width="5%" xtype="selector" field="id">&nbsp;</td>
							<td width="25%"><strong>名称</strong></td>
							<td width="25%"><strong>代码</strong></td>
							<td width="15%"><strong>高度</strong></td>
							<td width="15%"><strong>宽度</strong></td>
                            <td width="10%"><strong>所属栏目</strong></td>
						</tr>
						<tr onDblClick="edit();">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>${Name}</td>
							<td>${Code}</td>
							<td>${Height}px</td>
							<td>${Width}px</td>
                            <td>${CatalogName}</td>
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
</body>
</html>
