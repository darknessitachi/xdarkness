<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文档列表</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function deleteReally(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}	
	Dialog.confirm("彻底删除后将不可恢复，确认彻底删除选中的文档吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());		
		dc.add("PageIndex",DataGrid.getParam("dg1",Constant.PageIndex));
		dc.add("PageSize",DataGrid.getParam("dg1",Constant.Size));		
		Server.sendRequest("com.xdarkness.cms.document.RecycleBin.deleteReally",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.setParam("dg1","CatalogID",$V("CatalogID"));
					DataGrid.loadData("dg1");
				}
			});
		});
	});
}


Page.onLoad(function(){
	$("dg1").onContextMenu = function(tr,evt){
		evt = getEvent(evt);
		var menu = new Menu();
		menu.Width = 150;
		menu.setEvent(evt);
		menu.setParam([]);
		
		menu.addItem("恢复",restoreDocument,"Icons/icon003a13.gif");
		menu.addItem("彻底删除",deleteReally,"Icons/icon003a3.gif");
		menu.addItem("-");
		menu.addItem("查看",preview,"Icons/icon003a15.gif");
		menu.addItem("导出成Excel",function(){DataGrid.toExcel("dg1")},"Framework/Images/FileType/xls.gif");
		
		menu.show();
	}
});

function restoreCatalog(){
	var diag = new Dialog("Diag1");
	diag.Width = 450;
	diag.Height = 340;
	diag.Title = "恢复栏目";
	diag.URL = "Document/RecycleBinCatalogDialog.jsp?CatalogID="+$V("CatalogID");
	diag.onLoad = function(){
	};
	diag.OKEvent =  restoreCatalogSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "恢复己删除的栏目";
	diag.Message = "恢复己删除的栏目和栏目中的文章。";
	diag.show();
}

function restoreCatalogSave(){
	var arr = $DW.$NV("CatalogID");
	if(!arr||arr.length<1){
		Dialog.alert("请先选择栏目!");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",$DW.$NV("CatalogID"));
	Dialog.wait("正在恢复栏目...");
	Server.sendRequest("com.xdarkness.cms.document.RecycleBin.restoreCatalog",dc,function(response){
		Dialog.closeEx();
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.setParam("dg1","CatalogID",$V("CatalogID"));
				DataGrid.loadData("dg1");
				Tree.loadData("tree1");
				$D.close();
			}
		});
	});
}

function restoreDocument(){	
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr || arr.length<1){
		Dialog.alert("请先选择文档!");
		return;
	}
	Dialog.confirm("确认要恢复选中的文档吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join(","));
		Server.sendRequest("com.xdarkness.cms.document.RecycleBin.restoreDocument",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("dg1");
				}
			});
		});
	});
}

function preview(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择一条记录!");
		return;	
	}
	window.open("../Services/PreviewDoc.jsp?Type=B&ArticleID="+arr[0]);
}


function onTreeClick(ele){
	var cid = ele.getAttribute("cid");
	var code = ele.getAttribute("innercode");
	$S("CatalogID",cid);
	if(!Tree.isRoot(ele)){
		Cookie.set("DocList.LastCatalog",cid,"2100-01-01");
		Cookie.set("DocList.LastCatalogCode",code,"2100-01-01");
	}else{
		Cookie.set("DocList.LastCatalog","0","2100-01-01");
	}
	DataGrid.setParam("dg1","CatalogID",cid);
	DataGrid.setParam("dg1","StartDate","");//栏目切换时需清掉时间和关键字选项
	DataGrid.setParam("dg1","EndDate","");
	DataGrid.setParam("dg1","Keyword","");
	$S("StartDate","");
	$S("EndDate","");
	$S("Keyword","");
	DataGrid.loadData("dg1");
	if(!initButtons(ele)){
		return;
	}
	Application.setAllPriv("article",code);
}

function initButtons(ele){
	if(Tree.isRoot(ele)){
		$("BtnRestore").disable();
		$("BtnDelete").disable();
		$("BtnView").disable();
		return false;
	}else{
		$("BtnRestore").enable();
		$("BtnDelete").enable();
		$("BtnView").enable();
		return true;
	}
}

Page.onReady(init);

function init(){
	if(Cookie.get("DocList.LastCatalog")=="0"){
		Tree.CurrentItem = $("tree1").$T("p")[0];
		Tree.CurrentItem.onclick.apply(Tree.CurrentItem);
	}else{
		var node = Tree.getNode("tree1","cid",Cookie.get("DocList.LastCatalog"));
		//如果没有展开则载入
		if(!node){
			var code = Cookie.get("DocList.LastCatalogCode");
			while(code&&code.length>6){
				code = code.substring(0,code.length-6);
				node = Tree.getNode("tree1","innercode",code);
				if(node){
					Tree.lazyLoad(node,function(){
						var img = Tree.getLastBranchIcon(node);
						Tree.changeIcon(img,node);
						node = Tree.getNode("tree1","cid",Cookie.get("DocList.LastCatalog"));
						Tree.selectNode(node);
						if(!initButtons(node)){
							return;
						}
						Application.setAllPriv("article",code);
						Tree.scrollToNode(node);
					});
					break;
				}
			}
		}else{
			Tree.selectNode(node);
			if(!initButtons(node)){
				return;
			}
			Application.setAllPriv("article",node.getAttribute("innercode"));
			Tree.scrollToNode(node);
		}
		
		$S("CatalogID",Cookie.get("DocList.LastCatalog"));
		initButtons(Tree.CurrentItem);
		Application.setAllPriv("article",Tree.CurrentItem.getAttribute("innerCode"));
	}
	Application.setAllPriv("article",Application.CurrentSite);
}

document.onkeydown = function(event){
	event = getEvent(event);
	if(event.keyCode==13){
		var ele = event.srcElement || event.target;
		if(ele.id == 'Keyword'||ele.id == 'Submitbutton'){
			search();
		}
	}
}

function search(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","Keyword",$V("Keyword"));
	DataGrid.setParam("dg1","StartDate",$V("StartDate"));
	DataGrid.setParam("dg1","EndDate",$V("EndDate"));
	DataGrid.loadData("dg1");
}
</script>
</head>
<body>
<input type="hidden" id="CatalogID">
<input type="hidden" id="ListType" value='${ListType}'>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td width="180">
		<table width="180" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td style="padding:6px;" class="blockTd"><sky:tree id="tree1"
					style="height:440px;width:160px;"
					method="com.xdarkness.cms.document.RecycleBin.treeDataBind" level="2"
					lazy="true">
					<p cid='${ID}' innercode='${InnerCode}'
						onClick="onTreeClick(this);" afterdrag='afterTreeDragEnd'
						oncontextmenu="showMenu(event,this);stopEvent(event);">${Name}</p>
				</sky:tree></td>
			</tr>
		</table>
		</td>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="blockTable">
			<tr>
				<td width="65%" style="padding:6px 10px;" class="blockTd">
				<div style="float: left">
				<sky:button id="BtnRestore" priv="article_modify" onClick="restoreDocument()">
					<img src="../Icons/icon003a13.gif" width="20" height="20" />恢复文档</sky:button> 
				<sky:button id="BtnDelete" priv="article_modify" onClick="deleteReally()">
					<img src="../Icons/icon003a3.gif" width="20" height="20" />彻底删除</sky:button>
				<sky:button
					id="BtnView" priv="article_modify" onClick="preview()">
					<img src="../Icons/icon003a15.gif" width="20" height="20" />查看内容</sky:button> 
				<sky:button
					id="BtnCatalog" priv="site_manage" onClick="restoreCatalog()">
					<img src="../Icons/icon001a13.gif" width="20" height="20" />恢复栏目</sky:button> 
				</div>
				</td>
			</tr>
			<tr>
				<td style="padding:2px 10px;">
				<div style="float:left;">列出: 
				&nbsp;从 <input type="text" id="StartDate" style="width:90px; "
					xtype='date'> 至 <input type="text" id="EndDate"
					style="width:90px; " xtype='date'> &nbsp;关键词: <input
					name="Keyword" type="text" id="Keyword"> <input
					type="button" name="Submitbutton" id="Submitbutton" value="查询"
					onClick="search()"></div>
				</td>
			</tr>
			<tr>
				<td
					style="padding-top:2px;padding-left:6px;padding-right:6px;padding-bottom:2px;">
				<sky:datagrid id="dg1" method="com.xdarkness.cms.document.RecycleBin.dg1DataBind" size="15">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
						<tr xtype="head" class="dataTableHead">
							<td width="5%" height="30" xtype="RowNo"><img
								src="../Framework/Images/icon_drag.gif" width="16" height="16"></td>
							<td width="4%" height="30" xtype="Selector" field="id">&nbsp;</td>
							<td width="49%" sortfield="orderflag" direction=""><b>标题</b></td>
							<td width="11%"><strong>删除人</strong></td>
							<td width="12%"><strong>删除前状态</strong></td>
							<td width="15%" sortfield="publishdate" direction=""><strong>删除时间</strong></td>
						</tr>
						<tr onDblClick="preview(${ID});">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td style="${TitleStyle}">${Title}</td>
							<td>${BackupOperator}</td>
							<td>${StatusName}</td>
							<td title="${PublishDate}">${BackupTime}</td>
						</tr>
						<tr xtype="pagebar">
							<td colspan="9" align="left">${PageBar}</td>
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
