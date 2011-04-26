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
	diag.Height = 440;
	diag.Title = "新建网站群分发任务";
	diag.URL = "DataChannel/DeployCatalogDialog.jsp";
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
	diag.Width = 900;
	diag.Height = 440;
	diag.Title = "修改网站群分发任务";
	diag.URL = "DataChannel/DeployCatalogDialog.jsp?ID="+id;
	diag.onLoad = function(){
		$DW.Selector.setValueEx("CatalogID",dr.get("CatalogID"),dr.get("CatalogName"));
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	if($DW.Verify.hasError()){
		return;
	}
	if($DW.$("dg1").DataSource.Rows.length==0){
		Dialog.alert("请先添加目标栏目!");
		return;
	}
	var dc = $DW.Form.getData();
	dc.add("Data",$DW.$("dg1").DataSource);
	Server.sendRequest("com.xdarkness.datachannel.DeployCatalog.add",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message);
			$D.close();
			DataGrid.loadData("dg1");
		}else{
			Dialog.alert(response.Message);
		}
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
		Server.sendRequest("com.xdarkness.datachannel.DeployCatalog.del",dc,function(response){
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
		Server.sendRequest("com.xdarkness.datachannel.DeployCatalog.execute",dc,function(response){
			if(response.Status==1){
				taskID = response.get("TaskID");
				var p = new Progress(taskID,"正在分发数据...",700,150);
				p.show();
			}else{
				Dialog.alert(response.Message);	
			}
		});
	});
}
</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
			<tr>
				<td valign="middle" class="blockTd"><img src="../Icons/icon003a7.gif" width="20" height="20" />&nbsp;网站群分发任务列表</td>
			</tr>
			<tr>
				<td style="padding:0 8px 4px;">
				<sky:button onClick="add()"><img src="../Icons/icon018a2.gif" width="20" height="20" />新建</sky:button> 
				<sky:button onClick="edit()"><img src="../Icons/icon018a4.gif" width="20" height="20" />修改</sky:button> 
				<sky:button onClick="del()"><img src="../Icons/icon018a3.gif" width="20" height="20" />删除</sky:button> 
				<sky:button onClick="execute(false)"><img src="../Icons/icon403a12.gif" width="20" height="20" />手工执行</sky:button>
				<sky:button onClick="execute(true)"><img src="../Icons/icon400a13.gif" width="20" height="20" />重新开始分发</sky:button>
				</td>
			</tr>
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<sky:datagrid id="dg1" method="com.xdarkness.datachannel.DeployCatalog.dg1DataBind" page="false">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
						<tr xtype="head" class="dataTableHead">
							<td width="5%" xtype="RowNo"><b>序号</b></td>
							<td width="4%" xtype="selector" field="ID">&nbsp;</td>
							<td width="6%"><b>任务ID</b></td>
							<td width="27%"><b>名称</b></td>
							<td width="13%"><b>状态</b></td>
							<td width="13%"><b>分发类型</b></td>
							<td width="21%"><b>从此栏目开始分发</b></td>
							<td width="24%"><b>添加时间</b></td>
						</tr>
						<tr onDblClick='edit();' style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>${ID}</td>
							<td>${Name}</td>
							<td>${StatusName}</td>
							<td>${DeployTypeName}</td>
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
