<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>论坛版块管理</title>
<link href="../../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../../Framework/Main.js"></script>
<script>
function add(){
	var diag = new Dialog("Diag1"); 
	diag.Width = 450;
	diag.Height =200;
	diag.Title = "添加板块";
	diag.URL = "BBS/Admin/ForumAddAdmin.jsp";
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = Form.getData($DW.$F("form1"));
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.xdarkness.bbs.admin.ForumAdmin.add",dc,function(response){
		Dialog.alert(response.Message);
		if(response.Status==1){
			$D.close();
			DataGrid.loadData('dg1');
		}
	});
}

function edit(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	if(arr.length>1){
		Dialog.alert("一次只能修改一行！");
		return;
	}
	var dc = new DataCollection();
	dc.add("ID",arr[0]);
	Server.sendRequest("com.xdarkness.bbs.admin.ForumAdmin.isGroup",dc,function(response){
		var diag = new Dialog("Diag1");
		
		diag.Title = "板块设置";
		if(response.Status==1){
			diag.Width = 450;
			diag.Height =350;
			diag.URL = "BBS/Admin/ForumOption.jsp?ID="+arr[0];
			diag.OKEvent = editSave;
		}else{
			diag.Width = 350;
			diag.Height =150;
			diag.URL = "BBS/Admin/ForumParentOptionDialog.jsp?ID="+arr[0];
			diag.OKEvent = editSaveParent;
		}
		diag.show();
	});
}

function editSave(){
	var basicWin = $DW.Tab.getChildTab("ForumBasic").contentWindow;
	var postWin = $DW.Tab.getChildTab("PostOption").contentWindow;
	if(basicWin.Verify.hasError() || postWin.Verify.hasError()){
		return;
	}
	var dc1 = basicWin.Form.getData("form1");
	var dc2 = postWin.Form.getData("form2");
	dc1.addAll(dc2);
	dc1.add("Locked",basicWin.$NV("Locked"))
	dc1.add("ForumAdmin",basicWin.$("ForumAdmin").innerHTML);
	dc1.add("UnLockID",basicWin.$V("UnLockID"));
	dc1.add("UnPasswordID",basicWin.$V("UnPasswordID"));
	Server.sendRequest("com.xdarkness.bbs.admin.ForumAdmin.editSave",dc1,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("dg1");
			}
		});
	});
}
function editSaveParent(){
	var dc = $DW.Form.getData("form1");
	dc.add("ForumAdmin",$DW.$("ForumAdmin").innerHTML);
	Server.sendRequest("com.xdarkness.bbs.admin.ForumAdmin.editSave",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("dg1");
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
	Dialog.confirm("删除后不可恢复，确认要删除？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.xdarkness.bbs.admin.ForumAdmin.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData("dg1");
				}
			});
		});
	});
	
}

function clickAllSelect(){
	var f = $("AllSelect").checked;
	var menuTree = $N("MenuID");
	var menuTreeLength = menuTree.length;
	for(var i=0;i<menuTreeLength;i++){
		menuTree[i].checked = f;
	}
}
</script>
	</head>
	<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img src="../../Icons/icon022a1.gif" width="20" height="20" />板块列表</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;">
					<sky:button onClick="add()"><img src="../../Icons/icon022a2.gif" width="20" height="20" />新建</sky:button>
					<sky:button onClick="edit()"><img src="../../Icons/icon022a4.gif" width="20" height="20" />编辑</sky:button>
					<sky:button onClick="del()"><img src="../../Icons/icon022a3.gif" width="20" height="20" />删除</sky:button></td>
				</tr>
				<tr>
					<td style="padding:0px 5px;">
					<sky:datagrid id="dg1" method="com.xdarkness.bbs.admin.ForumAdmin.dg1DataBind" page="false">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable" afterdrag="sortMenu">
							<tr xtype="head" class="dataTableHead">
								<td width="4%" xtype="RowNo"></td>
								<td width="4%" xtype="selector" field="id">&nbsp;</td>
								<td width="16%" xtype="tree" level="treelevel"><b>版块名称</b></td>
								<td width="13%" ><b>版主</b></td>
								<td width="38%" >版块简介</td>
								<td width="5%" ><b>主题数</b></td>
								<td width="5%" ><b>帖子数</b></td>
								<td width="15%" ><b>添加时间</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF" style2="background-color:#F9FBFC" ondblclick="edit()">
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;${Name}</td>
								<td>${ForumAdmin}</td>
								<td>${Info}</td>
								<td>${ThemeCount}</td>
								<td>${PostCount}</td>
								<td>${AddTime}</td>
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
