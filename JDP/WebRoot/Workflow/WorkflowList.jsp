<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>工作流列表</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function save(){
	DataGrid.save("dg1","com.xdarkness.workflow.WorkflowPage.save",function(){DataGrid.loadData('dg1');});
}

function add(){
	var url = Server.ContextPath+"Workflow/WorkflowConfig.jsp";
	var width  = (screen.availWidth-10)+"px";
    var height = (screen.availHeight-50)+"px";
    var left  = 0;
    var top   = 0;
 	var args = "toolbar=0,location=0,maximize=1,directories=0,status=0,menubar=0,scrollbars=1,resizable=1,left=" + left+ ",top=" + top + ", width="+width+", height="+height;
    var w = window.open(url,"",args);
    if ( !w ){
		Dialog.alert( "发现弹出窗口被阻止，请更改浏览器设置，以便正常使用本功能!" ) ;
		return ;
	}
}

function editDialog(ID){
	var url = Server.ContextPath+"Workflow/WorkflowConfig.jsp?ID="+ID;
	var width  = (screen.availWidth-10)+"px";
    var height = (screen.availHeight-50)+"px";
    var left  = 0;
    var top   = 0;
 	var args = "toolbar=0,location=0,maximize=1,directories=0,status=0,menubar=0,scrollbars=1,resizable=1,left=" + left+ ",top=" + top + ", width="+width+", height="+height;
    var w = window.open(url,"",args);
    if ( !w ){
		Dialog.alert( "发现弹出窗口被阻止，请更改浏览器设置，以便正常使用本功能!" ) ;
		return ;
	}
}


function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	Dialog.confirm("您确认要删除选中的工作流吗？",function(){
		Server.sendRequest("com.xdarkness.workflow.WorkflowPage.del",dc,function(response){
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					DataGrid.loadData('dg1');
				}				
			});
		});
	})
}

</script>
</head>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
            <tr>
              <td valign="middle" class="blockTd"><img src="../Icons/icon400a9.gif" width="20" height="20" /> 工作流列表</td>
            </tr>
			<tr>
				<td style="padding:0 8px 4px;">
				<sky:button onClick="add()"><img src="../Icons/icon404a8.gif" />新建</sky:button>
				<sky:button onClick="DataGrid.edit(event,'dg1')"><img src="../Icons/icon403a10.gif" />修改</sky:button>
				<sky:button onClick="del()"><img src="../Icons/icon404a5.gif" />删除</sky:button></td>
			</tr>
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<sky:datagrid id="dg1" method="com.xdarkness.workflow.WorkflowPage.dg1DataBind" size="15">
					<table cellpadding="2" cellspacing="0" class="dataTable" width="100%">
						<tr xtype="head" class="dataTableHead">
							<td width="5%" xtype="RowNo">序号</td>
							<td width="4%" xtype="selector" field="ID">&nbsp;</td>
							<td width="21%"><b>工作流名称</b></td>
						    <td width="16%">最后修改时间</td> 
						    <td width="54%">备注</td> 
						</tr>
						<tr ondblclick="editDialog(${ID})" style1="background-color:#FFFFFF" style2="background-color:#F9FBFC">
							<td>&nbsp;</td>
							<td></td>
							<td>${Name}</td>
							<td>${ModifyTime}</td>
						    <td>${Memo}</td>
						</tr>
						<tr xtype="pagebar">
							<td colspan="5" align="center">${PageBar}</td>
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
