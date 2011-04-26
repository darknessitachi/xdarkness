<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>分类</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
var treeItem = null;
var tabType="User";

Application.onSiteChange(function(){
	Tree.loadData("tree1",function(){
		DataGrid.clear("dg1");
	});
});

Page.onLoad(function(){
	treeItem = $("tree1__TreeRoot");
	$("dg1").afterEdit = function(tr,dr){
		dr.set("Name",$V("Name"));
		//dr.set("IsUp",$V("dg1_IsUp_Checkbox"+tr.rowIndex)=="Y"?"Y":"N");
		dr.set("Store",$V("Store"));
		return true;
	}
	$("SearchContent").value = "输入商品名称";
	var ds = $("dg1").DataSource;
});

Page.onClick(function(){
$("dg1").onContextMenu = function(tr,evt){
	evt = getEvent(evt);
	var menu = new Menu();
	menu.Width = 150;
	menu.setEvent(evt);
	menu.setParam([]);
	
	var arr = DataGrid.getSelectedValue("dg1");
	var flag = false;
	if(arr && arr.length>1){
		flag = true;
	}

		menu.addItem("新建",add,"/Icons/icon050a2.gif");
		menu.addItem("修改",editDialog,"/Icons/icon050a4.gif");
		menu.addItem("删除",del,"/Icons/icon050a3.gif");
		menu.addItem("发布",publish,"/Icons/icon050a13.gif");
		menu.addItem("预览",preview,"/Icons/icon050a15.gif");
		menu.addItem("转移",move,"/Icons/icon003a7.gif");
		menu.addItem("排序",sortOrder,"/Icons/icon400a4.gif");
		menu.addItem("-");
		if(!flag){
			var dr = $("dg1").DataSource.Rows[tr.rowIndex-1];
			if(dr.get("Status")=="40"){
				menu.addItem("-");
				menu.addItem("上线",up,"Icons/icon026a7.gif");
			}else if(dr.get("Status")=="30"){
				menu.addItem("-");
				menu.addItem("下线",down,"Icons/icon026a5.gif");
			}
		}
	menu.addItem("导出成Excel",function(){DataGrid.toExcel("dg1")},"Framework/Images/FileType/xls.gif");
	menu.show();
}
});

function showMenu(event,ele){
	var cid = ele.getAttribute("cid");
	if(!cid){
		return ;
	}
	var menu = new Menu();
	menu.setEvent(event);
	menu.setParam(cid);
	menu.addItem("新建",add,"/Icons/icon050a2.gif");
	menu.addItem("修改",change,"/Icons/icon050a4.gif");
	menu.addItem("删除",delLib,"/Icons/icon050a3.gif");
	menu.addItem("发布",publishCatalog,"/Icons/icon050a13.gif");
	menu.addItem("预览",preview,"/Icons/icon050a15.gif");
	menu.addItem("-");
	// Type 0 表示节点为站点，1表示节点为栏目
   	
   	menu.addItem("属性",test);
	menu.show();
}

function change(param){
	var cid =param;
	if(!cid){
		return ;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 100;
	diag.Title = "修改分类";
	diag.URL = "Shop/GoodsLibEditDialog.jsp?ID="+cid;
	diag.onLoad = function(){
		$DW.$("Name").focus();		
	};
	diag.OKEvent = changeSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "修改分类名称";
	diag.show();
}

function changeSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
     } 
	Server.sendRequest("com.xdarkness.shop.GoodsLib.GoodsLibEdit",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				window.location.reload();
			}
		});
	});	
}

function test(param){
	Dialog.alert(param);
}

function onTreeClick(ele){
	var cid = ele.getAttribute("cid");
	$S("CatalogID",cid);
	treeItem = ele;
	$S("SearchContent","");
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","CatalogID",cid);
	DataGrid.setParam("dg1","SearchContent","");
	DataGrid.loadData("dg1");
}

function setNewTimeTop(ID){
	var dc = new DataCollection();
	dc.add("ID",ID);
	Server.sendRequest("com.xdarkness.shop.Goods.setNewTopper",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		})
	})
}

function setSpecialTimeTop(ID){
	var dc = new DataCollection();
	dc.add("ID",ID);
	Server.sendRequest("com.xdarkness.shop.Goods.setSpecialTopper",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		})
	})
}

function setHotTimeTop(ID){
	var dc = new DataCollection();
	dc.add("ID",ID);
	Server.sendRequest("com.xdarkness.shop.Goods.setHotTopper",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		})
	})
}

function setOrderTimeTop(ID){
	var dc = new DataCollection();
	dc.add("ID",ID);
	Server.sendRequest("com.xdarkness.shop.Goods.setOrderTopper",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		});
	})
}

function add(){
	var CatalogID = treeItem.getAttribute("cid");
	var CatalogName = treeItem.innerHTML.split(">")[treeItem.innerHTML.split(">").length-1];
	if(!CatalogID){
		Dialog.alert("请先选择一个商品分类");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 700;
	diag.Height = 450;
	diag.Title = "添加商品";
	diag.URL = "Shop/GoodsDialog.jsp?CatalogID=" + CatalogID;
	diag.onLoad = function(){
		$DW.Selector.setValueEx($DW.$("CatalogID"), CatalogID, CatalogName);
		//$DW.$("SN").focus();
	};
	diag.OKEvent = addSave;
	diag.ShowMessageRow = false;
	diag.MessageTitle = "添加商品";
	diag.Message = "请选择商品所属类别，默认为您所点击的商品类别，您可以选择新添加商品的类别和品牌，填写您新添加商品的相关信息";
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
     }
	
	Server.sendRequest("com.xdarkness.shop.Goods.add",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.loadData("dg1");
				$D.close();
			}
		});
	});
}

function save(){
	DataGrid.save("dg1","com.xdarkness.shop.Goods.save",function(){
		DataGrid.setParam("dg1","SearchContent",$V("SearchContent"));
		DataGrid.loadData("dg1");
	});
}

function editDialog(dr){	
	if(!dr){
		var dt = DataGrid.getSelectedData("dg1");
		if(dt.getRowCount() == 0){
			Dialog.alert("请先选择要修改的行!");
			return;
		}
		dr = dt.getDataRow(0);
	}
	var diag = new Dialog("Diag2");
	diag.Width = 700;
	diag.Height = 450;
	diag.Title = "编辑商品信息";
	diag.URL = "Shop/GoodsDialog.jsp?ID=" + dr.get("ID");
	diag.onLoad = function(){
		$DW.Selector.setValueEx($DW.$("CatalogID"), dr.get("CatalogID"), dr.get("CatalogIDName"));
		//$DW.$("SN").focus();
	};
	diag.OKEvent = editSave;
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData("form2");
	//dc.add("IsUp",$DW.$NV("IsUp"));
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.xdarkness.shop.Goods.dg1Edit",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				DataGrid.setParam("dg1",Constant.PageIndex,0);
				if(treeItem.getAttribute("cid")==null){
					DataGrid.setParam("dg1","CatalogID","");
				}else{
					DataGrid.setParam("dg1","CatalogID",treeItem.getAttribute("cid"));	
				}
				DataGrid.setParam("dg1","SearchContent",$V("SearchContent"));
				DataGrid.loadData("dg1");
				$D.close();
			}
		})
	});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		alert("请先选择要删除的行！");
		return;
	}
	if(!window.confirm("确定要删除该商品吗？")){
		return;
	}
	else{
		var dc = new DataCollection();	
		dc.add("IDs",arr.join());
		Server.sendRequest("com.xdarkness.shop.Goods.del",dc,function(){
			var response = Server.getResponse();
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.setParam("dg1","SearchContent",$V("SearchContent"));
					DataGrid.loadData("dg1");
				}
			})
		});
	}
} 

function copy(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要复制的商品！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 300;
	diag.Height = 100;
	diag.Title = "复制商品到另一个栏目";
	diag.URL = "Shop/GoodsCopyDialog.jsp?CatalogID="+treeItem.getAttribute("cid");
	diag.OKEvent = copySave;
	diag.show();
}

function copySave(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!$DW.$V("CatalogID")){
		Dialog.alert("商品不能复制到该栏目下！");
		return;
	}
	if($DW.$V("CatalogID")==treeItem.getAttribute("cid")){
		Dialog.alert("商品已经在所属栏目下了，请重新选择另一个所属栏目！");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	dc.add("CatalogID",$DW.$V("CatalogID"));
	Server.sendRequest("com.xdarkness.shop.GoodsLib.copy",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
			}
		});
	});
}

function transfer(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要转移的商品！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 300;
	diag.Height = 100;
	diag.Title = "转移商品到另一个栏目";
	diag.URL = "Shop/GoodsCopyDialog.jsp";
	diag.OKEvent = transferSave;
	diag.show();
}

function transferSave(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要转移的商品！");
		return;
	}
	if(!$DW.$V("CatalogID")){
		Dialog.alert("商品不能转移到该栏目下！");
		return;
	}
	if($DW.$V("CatalogID")==treeItem.getAttribute("cid")){
		Dialog.alert("商品已经在所属栏目下了，请重新选择另一个所属栏目！");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	dc.add("CatalogID",$DW.$V("CatalogID"));
	Server.sendRequest("com.xdarkness.shop.GoodsLib.transfer",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.setParam("dg1",Constant.PageIndex,0);
				DataGrid.setParam("dg1","CatalogID",treeItem.getAttribute("cid"));
				DataGrid.loadData("dg1");
			}
		});
	});
}

function delLib(param){
	var catalogID =param;
	if(!catalogID){
		return ;
	}
	Dialog.confirm("该栏目下的商品都将被删除，你确认要删除此栏目吗？",function(){
		var dc = new DataCollection();
		dc.add("catalogID",catalogID);
		Server.sendRequest("com.xdarkness.shop.GoodsLib.delLib",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
				if(response.Status==1){
					window.location.reload();
				}
			})
		});
	});
}

function clickImage(ele){
	var box = $("ImageID_box"+$(ele).$A("ImageID"));
	if(box.checked){
		box.checked = false;
	}else{
		box.checked = true;
	}
}

function doSearch(){
	if(!$V("SearchContent")||!$V("SearchContent").trim()){
		Dialog.alert("查询名称不能为空！");
		return;
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	if(treeItem.getAttribute("cid")==null){
		DataGrid.setParam("dg1","CatalogID","");
	}else{
		DataGrid.setParam("dg1","CatalogID",treeItem.getAttribute("cid"));	
	}
	DataGrid.setParam("dg1","SearchContent",$V("SearchContent"));
	DataGrid.loadData("dg1");
}

function clickView(id){
	view(id);
}

function publishCatalog(){
	var diag = new Dialog("Diag1");
	var nodeType = $V("CatalogID")=="" ? "0":"1";
	diag.Width = 340;
	diag.Height = 150;
	diag.Title = "发布";
	diag.URL = "Site/CatalogGenerateDialog.jsp";
	diag.onLoad = function(){
		if(nodeType == "0"){
			$DW.$("tr_Flag").style.display="none";
			
		}
	};
	diag.OKEvent = publishCatalogSave;
	diag.show();
}

function publishCatalogSave(){
	$E.disable($D.OKButton);
	$E.disable($D.CancelButton);
	$E.show($DW.$("Message"));
	$DW.msg();
	var dc = $DW.Form.getData("form2");
	var nodeType = $V("CatalogID")=="" ? "0":"1";
	dc.add("type",nodeType);
	dc.add("CatalogID",Tree.CurrentItem.getAttribute("cid"));
	Server.sendRequest("com.xdarkness.cms.site.Catalog.publish",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
			$D.close();
		}else{
			$D.close();
			var p = new Progress(response.get("TaskID"),"正在生成静态文件...");
			p.show();
		}
	});
}

function publish() {
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择要发布的商品！");
		return;
	}
	
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	Server.sendRequest("com.xdarkness.shop.GoodsLib.publish", dc, function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			var p = new Progress(response.get("TaskID"),"正在发布...");
			p.show(function(){
				$("dg1").loadData();
			});
		}
	});
}

function preview(){
	var arr = DataGrid.getSelectedValue("dg1");
	
	if(!arr || arr.length<1){
		if($V("CatalogID") && $V("CatalogID") != "0"){
			window.open("PreviewGoods.jsp?Type=1&SiteID="+Cookie.get("SiteID")+"&CatalogID="+$V("CatalogID"));
		}else{
			window.open("PreviewGoods.jsp?Type=0&SiteID="+Cookie.get("SiteID"));
		}
	}else{
		window.open("PreviewGoods.jsp?GoodsID="+arr[0]);
	}	
}


function move(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择要转移的商品！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 390;
	diag.Title = "选择转移栏目";
	diag.URL = "Shop/CatalogListDialog.jsp?Type=0";
	diag.OKEvent = moveSave;
	diag.show();
}

function moveSave(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择要转移的商品！");
		return;
	}
	
	var arrDest = $DW.$NV("CatalogID");
	if(arrDest == null || arrDest.length==0){
		Dialog.alert("请先选择转移的目标栏目！");
		return;
	}
	
	if ($V("CatalogID") == arrDest[0]) {
		Dialog.alert("您所选择的商品已经在该栏目下，请选择其他栏目！");
		return;
	}
	
	var dc = new DataCollection();
	dc.add("GoodsIDs",arr.join());
	dc.add("CatalogID",arrDest[0]);
	Dialog.wait("正在转移商品...");
	Server.sendRequest("com.xdarkness.shop.GoodsLib.move",dc,function(response){
		Dialog.closeEx();
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("转移商品成功");
			$D.close();
			DataGrid.setParam("dg1","CatalogID",$V("CatalogID"));
			DataGrid.setParam("dg1",Constant.PageIndex,0);
	  		DataGrid.loadData("dg1");
		}
	});
}


function copy(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择要复制的文档！");
		return;
	}
	
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 420;
	diag.Title = "选择复制到的栏目";
	diag.URL = "Shop/CatalogListDialog.jsp?Type=1&Action=copy";
	diag.OKEvent = copySave;
	diag.show();
}

function copySave(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择要复制的商品！");
		return;
	}
	
	var arrDest = $DW.$NV("CatalogID");
	if(arrDest == null || arrDest.length==0){
		Dialog.alert("请先选择复制的目标栏目！");
		return;
	}
	
	var dc = new DataCollection();
	dc.add("GoodsIDs",arr.join());
	dc.add("CatalogIDs",arrDest.join());
	dc.add("ReferType",$DW.$NV("ReferType"));
	Dialog.wait("正在复制商品...");
	Server.sendRequest("com.xdarkness.shop.GoodsLib.copy",dc,function(response){
		Dialog.closeEx();
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("成功复制文档",function(){
				$D.close();
				DataGrid.setParam("dg1","CatalogID",$V("CatalogID"));
				DataGrid.setParam("dg1",Constant.PageIndex,0);
	      		DataGrid.loadData("dg1");
			});
		}
	});
}

function sortOrder(){
	if(DataGrid.getParam("dg1",Constant.SortString)){
		Dialog.confirm("默认排序下才可能调整顺序，是否要切换到默认排序？",function(){
			DataGrid.setParam("dg1",Constant.SortString,"");
			DataGrid.loadData("dg1",function(){
				Dialog.alert("切换成功!");
			});
		});
		return;
	}
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择商品！");
		return;
	}
	var diag = new Dialog("Diag1");
	diag.Width = 650;
	diag.Height = 310;
	diag.Title = "调整商品顺序";
	diag.URL = "Shop/GoodsSortDialog.jsp?CatalogID="+DataGrid.getParam("dg1","CatalogID");
	diag.onLoad = function(){
	};
	diag.OKEvent =  sortOrderSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "调整商品顺序";
	diag.Message = "调整商品顺序，使商品排列在当前对话框中选中的商品之前。";
	diag.show();
}

function sortOrderSave(){
	var dt = $DW.DataGrid.getSelectedData("dg1");
	if(!dt||dt.Rows.length<1){
		Dialog.alert("请先选择行!");
		return;
	}
	var dc = new DataCollection();
	dc.add("Target",dt.get(0,"OrderFlag"));
	var arr = [];
	dt = DataGrid.getSelectedData("dg1");
	for(var i=0;i<dt.Rows.length;i++){
		arr.push(dt.get(i,"OrderFlag"));
	}
	dc.add("Orders",arr.join(","));
	dc.add("Type","Before");
	dc.add("CatalogID",$V("CatalogID"));
	Dialog.wait("正在调整商品顺序...");
	Server.sendRequest("com.xdarkness.shop.GoodsLib.sortGoods",dc,function(response){
		Dialog.closeEx();
		if(response.Status==1){
			Dialog.alert("操作成功!",function(){
				$D.close();
				DataGrid.loadData("dg1");
			});
		}
	});
}

function down(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择商品！");
		return;
	}
	var dc = new DataCollection();
	dc.add("GoodsIDs",arr.join(","));
	Server.sendRequest("com.xdarkness.shop.GoodsLib.down",dc,function(response){
		if(response.Status==1){
			Dialog.alert("操作成功!",function(){
				DataGrid.loadData("dg1");
			});
		}																			
	});
}

function up(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择商品！");
		return;
	}
	var dc = new DataCollection();
	dc.add("GoodsIDs",arr.join(","));
	Server.sendRequest("com.xdarkness.shop.GoodsLib.up",dc,function(response){
		if(response.Status==1){
			Dialog.alert("操作成功!",function(){
				DataGrid.loadData("dg1");
			});
		}																			
	});
}
</script>
</head>
<body onContextMenu="return false;">
<input type="hidden" id="CatalogID">
<%String basePath=Config.getContextPath()+Config.getValue("UploadDir")+Application.getCurrentSiteAlias(); %>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td width="180">
		<table width="180" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td style="padding:6px;"><sky:tree id="tree1"
					style="width:160px;height:480px"
					method="com.xdarkness.shop.GoodsLib.treeDataBind" level="3" lazy="true"
					expand="false">
					<p cid='${ID}' onClick="onTreeClick(this);"
						onContextMenu="showMenu(event,this);">${Name}</p>

				</sky:tree></td>
			</tr>
		</table>
		</td>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td>
					<table cellpadding="4">
					<tr><td>
					<div style="float: left">
						<sky:button id="addBtn" onClick="add()">
							<img src="../Icons/icon050a2.gif" />新建</sky:button>
						<sky:button id="editBtn" onClick="editDialog()"> 
							<img src="../Icons/icon050a4.gif" />修改</sky:button>
						<sky:button id="delBtn" onClick="del()">
							<img src="../Icons/icon050a3.gif" />删除</sky:button>
						<sky:button id="publishBtn" onClick="publish()">
							<img src="../Icons/icon050a13.gif" />发布</sky:button> 
						<sky:button id="publishBtn" onClick="down()">
							<img src="../Icons/icon050a13.gif" />下线</sky:button> 
						<sky:button id="PreviewBtn" onClick="preview()">
							<img src="../Icons/icon050a15.gif" width="20" height="20" />预览</sky:button>
						<!--<sky:button id="BtnCopy" onClick="copy()">
							<img src="../Icons/icon003a12.gif" width="20" height="20" />复制</sky:button> -->
						<sky:button id="BtnMove" onClick="move()">
							<img src="../Icons/icon003a7.gif" width="20" height="20" />转移</sky:button> 
						<sky:button id="BtnOrder" onClick="sortOrder()">
							<img src="../Icons/icon400a4.gif" width="20" height="20" />排序</sky:button>
					  </div>
						</td>
					</tr>
					<tr>
						<td>
						  按名称搜索:
						<input name="SearchContent" type="text" onFocus="this.value=''" id="SearchContent" value="" size='15'> 
						<input type="button" name="Submit" value="查询" onClick="doSearch()">
						</td>
					</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td ><sky:datagrid id="dg1"
					method="com.xdarkness.shop.GoodsLib.dg1DataBind" size="15">
					<table class="dataTable" cellspacing="0" cellpadding="2"
						width="100%">
						<tr xtype="head" class="dataTableHead">
							<td width="5%" xtype="RowNo"><strong>序号</strong></td>
							<td width="4%" xtype="selector" field="ID">&nbsp;</td>
							<td style="display:none">类别ID</td>
							<td width="36%"><strong>名称</strong></td>
							<td width="12%"><strong>价格</strong></td>
							<td width="12%"><strong>库存</strong></td>
							<td width="17%"><strong>上架时间</strong></td>
							<td width="14%"><strong>状态</strong></td>
						</tr>
						<tr style1="background-color:#FFFFFF"
							style2="background-color:#F9FBFC" onDblClick="editDialog()">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td style="display:none">${CatalogID}</td>
							<td title="${Factory}"><span title="${Name}">${Name}</span></td>
							<td>${MarketPrice}</td>
							<td>${Store}</td>
							<td title="${PublishDate}">${PublishDate}</td>
							<td>${StatusName}</td>
						</tr>
						<tr xtype="pagebar">
							<td height="25" colspan="6" align="center">${PageBar}</td>
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
