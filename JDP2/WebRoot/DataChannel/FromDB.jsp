<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>配置</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 800;
	diag.Height = 460;
	diag.Title = "新建数据库采集任务";
	diag.URL = "DataChannel/FromDBDialog.jsp";
	diag.onLoad = function(){
	};
	diag.OKEvent = addSave;
	diag.ShowButtonRow = true;
	diag.show();
}

function edit(tr){
	var dt = DataGrid.getSelectedData("dg1");
	if(dt.getRowCount()==0){
		Dialog.alert("请先选择要修改的行!");
		return;
	}
	var dr = dt.Rows[0];
	var id = dr.get("ID");
	var diag = new Dialog("Diag1");
	diag.Width = 800;
	diag.Height = 460;
	diag.Title = "修改数据库采集任务";
	diag.URL = "DataChannel/FromDBDialog.jsp?ID="+id;
	diag.onLoad = function(){
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var iwin = $DW.Tab.getChildTab("Info").contentWindow;
	$DW.Tab.onChildTabClick("Info");
	if(iwin.Verify.hasError()){
		return;
	}
	var dc = iwin.Form.getData("F1");
	
	$DW.Tab.onChildTabClick("Column");
	var cwin = $DW.Tab.getChildTab("Column").contentWindow;
	if(!cwin.$||!cwin.$("dg1")||!cwin.checkData()){
		return;
	}
	var data = cwin.getData();
	dc.add("DataTable",data.DataTable);	
	dc.add("TitleUniteFlag",data.TitleUniteFlag);	
	dc.add("TitleUniteRule",data.TitleUniteRule);	
	dc.add("ContentUniteFlag",data.ContentUniteFlag);	
	dc.add("ContentUniteRule",data.ContentUniteRule);	
	dc.add("RedirectURLUniteFlag",data.RedirectURLUniteFlag);	
	dc.add("RedirectURLUniteRule",data.RedirectURLUniteRule);	
	Server.sendRequest("com.xdarkness.datachannel.FromDB.add",dc,function(response){
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
	var dc = new DataCollection();	
	dc.add("IDs",arr.join());
	Dialog.confirm("确定要删除该任务吗？",function(){
		Server.sendRequest("com.xdarkness.datachannel.FromDB.del",dc,function(response){
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert(response.Message);
				DataGrid.loadData("dg1");
			}
		});
	});
} 

function execute(restartFlag){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择行！");
		return;
	}
	var taskID;
	Dialog.confirm("确定要执行该任务吗？",function(){
		var dc = new DataCollection();	
		dc.add("ID",arr[0]);
		dc.add("RestartFlag",restartFlag?"Y":"N");
		Server.sendRequest("com.xdarkness.datachannel.FromDB.execute",dc,function(response){
			if(response.Status==1){
				taskID = response.get("TaskID");
				var p = new Progress(taskID,"正在采集数据...",700,150);
				p.show();
			}else{
				Dialog.alert(response.Message);	
			}
		});
	});
}

function conn(){
	var diag = new Dialog("DiagConn");
	diag.Width = 800;
	diag.Height = 400;
	diag.Title = "新建外部连接";
	diag.URL = "DataService/OuterDatabase.jsp";
	diag.show();
	diag.OKButton.hide();
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<tr>
				<td valign="middle" class="blockTd"><img src="../Icons/icon005a13.gif" width="20" height="20" />&nbsp;数据库采集任务列表</td>
			</tr>
			<tr>
				<td style="padding:0 8px 4px;">
				<sky:button onClick="add()"><img src="../Icons/icon018a2.gif" width="20" height="20" />新建</sky:button> 
				<sky:button onClick="edit()"><img src="../Icons/icon018a4.gif" width="20" height="20" />修改</sky:button> 
				<sky:button onClick="del()"><img src="../Icons/icon018a3.gif" width="20" height="20" />删除</sky:button> 
				<sky:button onClick="execute(false)"><img src="../Icons/icon403a12.gif" width="20" height="20" />手工执行</sky:button>
				<sky:button onClick="execute(true)"><img src="../Icons/icon400a13.gif" width="20" height="20" />重新开始采集</sky:button>
				<sky:button onClick="conn()"><img src="../Icons/icon006a10.gif" width="20" height="20" />外部连接</sky:button>
				</td>
			</tr>
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<sky:datagrid id="dg1" method="com.xdarkness.datachannel.FromDB.dg1DataBind" page="false">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
						<tr xtype="head" class="dataTableHead">
							<td width="4%" xtype="RowNo"><b>序号</b></td>
							<td width="3%" xtype="selector" field="ID">&nbsp;</td>
							<td width="4%"><b>ID</b></td>
							<td width="22%"><b>名称</b></td>
							<td width="10%"><b>状态</b></td>
							<td width="14%"><b>外部连接名称</b></td>
							<td width="12%"><b>表名称</b></td>
							<td width="14%"><b>采集到此栏目</b></td>
							<td width="17%"><b>添加时间</b></td>
						</tr>
						<tr onDblClick='edit();' style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>${ID}</td>
							<td>${Name}</td>
							<td>${StatusName}</td>
							<td>${DatabaseIDName}</td>
							<td>${TableName}</td>
							<td>${CatalogName}</td>
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
