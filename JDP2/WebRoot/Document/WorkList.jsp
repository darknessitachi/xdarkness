<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文档审核</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function add(){
	var catalogID = $V("CatalogID");
	if(catalogID==""){
		Dialog.alert("请选择栏目！");
		return;
	}
	var width  = (screen.availWidth-10)+"px";
  	var height = (screen.availHeight-50)+"px";
  	var leftm  = 0;
  	var topm   = 0;
  
 	var args = "toolbar=0,location=0,maximize=1,directories=0,status=0,menubar=0,scrollbars=0, resizable=1,left=" + leftm+ ",top=" + topm + ", width="+width+", height="+height;
  	var url="Article.jsp?CatalogID=" + catalogID;
  	var w = window.open(url,"",args);
  	if ( !w ){
		Dialog.alert( "发现弹出窗口被阻止，请更改浏览器设置，以便正常使用本功能!" ) ;
		return ;
	}
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	
	Dialog.confirm("确认删除选中的文档吗？",function(){
			var dc = new DataCollection();
			dc.add("IDs",arr.join());
			Server.sendRequest("com.xdarkness.cms.document.ArticleList.del",dc,function(response){
				if(response.Status==0){
					Dialog.alert(response.Message);
				}else{
					Dialog.alert("成功删除文档");
					DataGrid.setParam("dg1","CatalogID",$V("CatalogID"));
					DataGrid.setParam("dg1",Constant.PageIndex,0);
		      DataGrid.loadData("dg1");
				}
			});
	});
}

function edit(id){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择要编辑的文章！");
		return;
	}
	var id = arr[0];
	var width  = (screen.availWidth-10)+"px";
	var height = (screen.availHeight-50)+"px";
	var leftm  = 0;
	var topm   = 0;
	var args = "toolbar=0,location=0,maximize=1,directories=0,status=0,menubar=0,scrollbars=0, resizable=1,left=" + leftm+ ",top=" + topm + ", width="+width+", height="+height;
	var url="Article.jsp?ArticleID=" + id;
	var w = window.open(url,"",args);
	if(!w){
		Dialog.alert( "发现弹出窗口被阻止，请更改浏览器设置，以便正常使用本功能!" ) ;
		return ;
	}
}

function onTreeClick(ele){
	var cid=  ele.getAttribute("cid");
	DataGrid.setParam("dg1","CatalogID",cid);
	DataGrid.loadData("dg1");
	$S("CatalogID",cid);
}

function delKeyWord() {
	if ($V("Keyword") == "请输入标题") {
		$S("Keyword","");
	}
}

function search(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	if ($V("Keyword") != "请输入标题" && $V("Keyword") && $V("Keyword").trim()) {
		DataGrid.setParam("dg1","Keyword",$V("Keyword"));
	} else {
		DataGrid.setParam("dg1","Keyword","");
	}
	DataGrid.loadData("dg1");
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

function doAction(EntryID,ActionID,ArticleID){
	var dc = new DataCollection();
	dc.add("EntryID",EntryID);
	dc.add("ActionID",ActionID);
	dc.add("ArticleID",ArticleID);
	Server.sendRequest("com.xdarkness.cms.document.Article.doAction",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
   				DataGrid.loadData("dg1");
			}
		});
	});
}

function changeType(){
   	DataGrid.setParam("dg1",Constant.PageIndex,0);
	if ($V("Keyword") != "请输入标题" && $V("Keyword") && $V("Keyword").trim()) {
		DataGrid.setParam("dg1","Keyword",$V("Keyword"));
	} else {
		DataGrid.setParam("dg1","Keyword","");
	}
	DataGrid.setParam("dg1","Type",$V("Type"));
	DataGrid.loadData("dg1");
}

Page.onLoad(function(){
	$("article_applystep").disable();
	if($("BtnPublish")){
		$("BtnPublish").disable();
	}
	if($("BtnForceEnd")){
		$("BtnForceEnd").disable();
	}
});

function onRowClick(ele){
	var index = ele.rowIndex-1;
	if("Unread"==$("dg1").DataSource.Rows[index].get("State")){
		$("article_applystep").enable();
	}else{
		$("article_applystep").disable();
	}
	if($("BtnPublish")){
		if("20"==$("dg1").DataSource.Rows[index].get("Status")||"30"==$("dg1").DataSource.Rows[index].get("Status")){
			$("BtnPublish").enable();
		}else{
			$("BtnPublish").disable();
		}
	}
	if($("BtnForceEnd")){
		if("10"==$("dg1").DataSource.Rows[index].get("Status")){
			$("BtnForceEnd").enable();
		}else{
			$("BtnForceEnd").disable();
		}	}
}

function applyStep(){
	var dt = $("dg1").getSelectedData();
	if(!dt||dt.Rows.length==0){
		Dialog.alert("请先选择记录!");
		return;
	}
	for(var i=dt.Rows.length-1;i>=0;i--){
		if("Unread"!=dt.Rows[i].get("State")){
			dt.deleteRow(i);
		}
	}
	if(dt.Rows.length==0){
		Dialog.alert("选中的记录中没有处于待申请状态的!");
		return;
	}
	var dc = new DataCollection();
	dc.add("Data",dt);
	Server.sendRequest("com.xdarkness.cms.document.WorkList.applyStep",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
   				DataGrid.loadData("dg1");
			}
		});
	});
}

function articleLog(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择要编辑的文章！");
		return;
	}
	var id = arr[0];
	var diag = new Dialog("Diag1");
	diag.Width = 680;
	diag.Height = 350;
	diag.Title = "处理历史";
	diag.URL = "Document/ArticleLogDialog.jsp?ArticleID="+id;
	diag.onLoad = function(){
	};
	diag.show();
	$E.hide(diag.OKButton);
	diag.CancelButton.value = "确 定";
}

function preview(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr || arr.length<1){
		Dialog.alert("请先选择要预览的文章！");
		return;
	}else{
		window.open("Preview.jsp?ArticleID="+arr[0]);
	}	
}

function publish(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(arr == null || arr.length==0){
		Dialog.alert("请先选择要发布的文章！");
		return;
	}
	
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	Server.sendRequest("com.xdarkness.cms.document.ArticleList.publish",dc,function(response){
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

function workflowConfig(){
	var dt = $("dg1").getSelectedData();
	if(!dt||dt.Rows.length==0){
		Dialog.alert("请先选择要查看流程的文章!");
		return;
	}
	var url = Server.ContextPath+"Workflow/WorkflowDetail.jsp?ID="+dt.get(0,"workflowconfigid");
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

function forceEnd(){
	var dt = $("dg1").getSelectedData();
	if(!dt||dt.Rows.length==0){
		Dialog.alert("请先选择记录!");
		return;
	}
	for(var i=dt.Rows.length-1;i>=0;i--){
		if("10"!=dt.Rows[i].get("Status")){
			dt.deleteRow(i);
		}
	}
	if(dt.Rows.length==0){
		Dialog.alert("选中的记录中没有处于流转中状态的!");
		return;
	}
	var dc = new DataCollection();
	dc.add("Data",dt);
	Server.sendRequest("com.xdarkness.cms.document.WorkList.forceEnd",dc,function(response){
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
   				DataGrid.loadData("dg1");
			}
		});
	});
}
</script>
</head>
<body>
<input type="hidden" id="CatalogID">
<input type="hidden" id="ListType" value='${ListType}'>
<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td></td>
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="4" class="blockTable">
			<tr>
				<td colspan="2" valign="middle" class="blockTd"><img src="../Icons/icon018a1.gif" />文档审核列表</td>
			</tr>
			<tr>
				<td style="padding:0 8px 4px;">
				<sky:button priv="true" onClick="DataGrid.edit(event,'dg1')"><img src="../Icons/icon018a4.gif" />打开</sky:button> 
				<sky:button id="article_applystep" onClick="applyStep()"><img src="../Icons/icon018a16.gif" width="20" height="20" />申请处理</sky:button> 
				<sky:button id="article_manage" onClick="del()"><img src="../Icons/icon018a3.gif" />删除</sky:button>
				<sky:button id="article_manage" onClick="articleLog()"><img src="../Icons/icon_column.gif" width="20" height="20" />处理历史</sky:button>
				<%
					if (!UserListPage.ADMINISTRATOR.equals(User.getUserName())) {
				%>
				<sky:button id="BtnPublish" priv="article_modify" onClick="publish()"><img src="../Icons/icon003a13.gif" width="20" height="20" />发布</sky:button>
				<sky:button id="BtnPreview" priv="article_browse" onClick="preview()"><img src="../Icons/icon403a3.gif" width="20" height="20" />预览</sky:button>
				<%
					}else{
				%>
				<sky:button id="BtnForceEnd" priv="article_modify" onClick="forceEnd()"><img src="../Icons/icon400a12.gif" width="20" height="20" />强行结束流程</sky:button>
				<%
					}
				%>
				<sky:button id="BtnWorkflowConfig" onClick="workflowConfig()"><img src="../Icons/icon042a1.gif" width="20" height="20" />查看流程</sky:button>
					<sky:select id="Type" onChange="changeType()" style="width:110px;">
					<%
						if(UserListPage.ADMINISTRATOR.equals(User.getUserName())){
					%>
					<span value="ALL">所有流转中的文档</span>
					<%} %>
					<span value="TOME">待我处理的文档</span>
					<span value="HANDLED">我处理过的文档</span>
				</sky:select><input name="Keyword" type="text" id="Keyword" value="请输入标题" onFocus="delKeyWord();" style="width:100px"> <input type="button" name="Submitbutton" id="Submitbutton" value="查询" onClick="search()"></td>
			</tr>
			<tr>
				<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;"><sky:datagrid id="dg1" method="com.xdarkness.cms.document.WorkList.dg1DataBind" size="15">
					<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
						<tr xtype="head" class="dataTableHead">
							<td width="3%" height="30" xtype="RowNo"></td>
							<td width="2%" xtype="selector" field="id">&nbsp;</td>
							<td width="10%"><b>所属栏目</b></td>
							<td width="28%"><b>标题</b></td>
							<td width="8%"><strong>创建者</strong></td>
							<td width="10%"><strong>添加时间</strong></td>
							<td width="8%"><strong>文档状态</strong></td>
							<td width="8%"><strong>上次动作</strong></td>
							<td width="7%"><strong>当前步骤</strong></td>
							<td width="8%"><strong>标志</strong></td>
                            <td width="8%"><strong>处理者</strong></td>
						</tr>
						<tr onDblClick="edit(${ID});" onclick="onRowClick(this)">
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>${CatalogIDName}</td>
							<td>${Title}</td>
							<td>${AddUser}</td>
							<td>${AddTime}</td>
							<td>${StatusName}</td>
							<td>${ActionName}</td>
							<td>${StepName}</td>
							<td>${StateName}</td>
                            <td>${Owner}</td>
						</tr>
						<tr xtype="pagebar">
							<td colspan="10" align="left">${PageBar}</td>
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
