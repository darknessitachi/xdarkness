<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>栏目</title>

<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script src="../Framework/Spell.js"></script>
<script src="../Editor/fckeditor.js"></script>
<script src="../Framework/Controls/StyleToolbar.js"></script>
<script>
Page.onLoad(function(){
	
});

var editor;
function getContent(){
	editor = FCKeditorAPI.GetInstance('Content');
    return editor.GetXHTML(false);	
}

//鼠标点击当前页面时，隐藏右键菜单
var iFrame =parent.parent;
Page.onClick(function(){
	var div = iFrame.$("_DivContextMenu")
	if(div){
			$E.hide(div);
	}
});


function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 400;
	diag.Title = "新建期号";
	diag.URL = "Site/MagazineIssueDialog.jsp?MagazineID="+$V("MagazineID");
	
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "新建期号";
	diag.Message = "设置期刊年号、期号、首页图片、模板文件等";
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData($F("form2"));
  dc.add("MagazineID",$V("MagazineID"));
  if($DW.Verify.hasError()){
	    return;
	}

	Server.sendRequest("com.xdarkness.cms.site.MagazineIssue.add",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("新建成功",function(){
				$D.close();
			  DataGrid.setParam("dg1",Constant.PageIndex,0);
			  window.location.reload();
			});

		}
	});
}

function copy(){
	var diag = new Dialog("Diag1");
	diag.Width = 500;
	diag.Height = 300;
	diag.Title = "复制期刊";
	diag.URL = "Site/PageBlockCopyDialog.jsp";
	diag.OKEvent = copySave;
	diag.show();
}

function copySave(){
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	
	Dialog.confirm("确认删除选中的期号吗？",function(){
			var dc = new DataCollection();
			dc.add("IDs",arr.join());
			
			Server.sendRequest("com.xdarkness.cms.site.MagazineIssue.del",dc,function(response){
				if(response.Status==0){
					alert(response.Message);
				}else{
					Dialog.alert("删除成功",function(){
						DataGrid.setParam("dg1",Constant.PageIndex,0);
						window.parent.Tree.loadData("tree1");
					    DataGrid.loadData("dg1");
					});
				}
			});
	});

}

function edit(){
  var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("请先选择要修改的刊号！");
		return;
	}
	if(drs.length>1){
		Dialog.alert("只能修改1条信息！");
		return;
	}
	dr = drs[0]; 
  editDialog(dr);
}

function editDialog(dr){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr || arr.length==0){
		Dialog.alert("请先选择要修改的行！");
		return;
	}
	var ID = arr[0];
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 400;
	diag.Title = "修改期刊";
	diag.URL = "Site/MagazineIssueDialog.jsp?ID="+ID;
	
	diag.onLoad = function(){
		$DW.Form.setValue(dr);
		$DW.$S("PublishDate",dr.get("PubDate"));
	};
	
	diag.OKEvent = editSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "修改期号";
	diag.Message = "设置期刊年号、期号、首页图片、模板文件等";
	diag.show();
	diag.show();
}

function editSave(){
	var dc = $DW.Form.getData($F("form2"));
  dc.add("MagazineID",$V("MagazineID"));
  editor = $DW.FCKeditorAPI.GetInstance('Content');
  var content = editor.GetXHTML(false);
    dc.add("Memo",content);
  
  if($DW.Verify.hasError()){
	    return;
	}
	
	Server.sendRequest("com.xdarkness.cms.site.MagazineIssue.edit",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("修改成功",function(){
				$D.close();
				DataGrid.setParam("dg1",Constant.PageIndex,0);
				DataGrid.loadData("dg1");
			});
		}
	});
}
/**
function generate(){
		var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择要生成的行！");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	
	Server.sendRequest("com.xdarkness.cms.site.MagazineIssue.generate",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("发布期刊成功",function(){
				$D.close();
				DataGrid.setParam("dg1",Constant.PageIndex,0);
				DataGrid.loadData("dg1");
			});
		}
	});
}
*/

function publish(){
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
	diag.OKEvent = publishSave;
	diag.show();
}

function publishSave(){
    var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择要发布的期号！");
		return;
	}
	$E.disable($D.OKButton);
	$E.disable($D.CancelButton);
	$E.show($DW.$("Message"));
	$DW.msg();

	var dc = $DW.Form.getData("form2");
    dc.add("IDs",arr.join());	

	Server.sendRequest("com.xdarkness.cms.site.Magazine.publish",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
			$D.close();
		}else{
			$D.close();
			var taskID = response.get("TaskID");
			var p = new Progress(taskID,"正在发布...");
			p.show();
		}
	});
}


</script>

</head>
<body>
<sky:init method="com.xdarkness.cms.site.MagazineIssue.init">

	<div style="padding:2px;">
	<table width="100%" cellpadding="4" cellspacing="0">
		<tr>
			<td><sky:button onClick="add();">
				<img src="../Icons/icon010a2.gif" />新建</sky:button> <sky:button
				onClick="edit();">
				<img src="../Icons/icon010a4.gif" />编辑</sky:button> <sky:button
				onClick="del();">
				<img src="../Icons/icon010a3.gif" />删除</sky:button> <!--<sky:button
				onClick="copy();">
				<img src="../Platform/Images/tab1_tri.gif" />复制</sky:button>--></td>
		</tr>
	</table>

	    <input name="MagazineID" type="hidden" id="MagazineID"
		value="${MagazineID}" />
		<input name="CatalogID" type="hidden" id="CatalogID"
		value="${CatalogID}" /> <sky:datagrid id="dg1"
		method="com.xdarkness.cms.site.MagazineIssue.dg1DataBind" size="15">
		<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
			<tr xtype="head" class="dataTableHead">
				<td width="5%" xtype="RowNo">序号</td>
				<td width="3%" xtype="selector" field="id">&nbsp;</td>
				<td><strong>年号</strong></td>
				<td><strong>期号</strong></td>
				<td><strong>出版日期</strong></td>
				<td><strong>状态</strong></td>
				<td><strong>添加时间</strong></td>
			</tr>
			<tr style1="background-color:#FFFFFF"
				style2="background-color:#F9FBFC">
				<td width="5">&nbsp;</td>
				<td width="36">&nbsp;</td>
				<td>${Year}</td>
				<td>${PeriodNum}</td>
				<td>${pubDate}</td>
				<td>${Status}</td>
				<td>${AddTime}</td>
			</tr>
			<tr xtype="pagebar">
				<td colspan="7" align="center">${PageBar}</td>
			</tr>
		</table>
	</sky:datagrid></div>
</sky:init>
</body>
</html>
