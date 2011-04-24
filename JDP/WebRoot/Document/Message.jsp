<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><%=Config.getValue("App.Name")%></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function add(){
	var diag =new Dialog("diag1");
	diag.Width = 500;
	diag.Height = 250;
	diag.Title = "新消息";
	diag.URL = "Document/MessageDialog.jsp";
	diag.onLoad = function(){
		$DW.$("ToUser").focus();
	};
	diag.OKEvent = addSave
	diag.show();
}

function reply(){
    var dt = DataGrid.getSelectedData("dg1");
	var dr = dt.Rows;
	if(!dt||dr.length==0){
		Dialog.alert("请先选择要回复的信息！");
		return;
	}
	if(dr.length>1){
		Dialog.alert("只能回复1条信息！");
		return;
	}

	var diag =new Dialog("diag1");
	diag.Width = 500;
	diag.Height = 250;
	diag.Title = "回复消息";
	diag.URL = "Document/MessageReplyDialog.jsp?ID="+dr[0].get("ID");
	diag.OKEvent = replySave;
	diag.show();
}

function addSave(){
	if($DW.Verify.hasError()){
		return;
    }
	var dc = Form.getData($DW.$F("formMsg"));
	Server.sendRequest("com.xdarkness.cms.document.Message.add",dc,function(response){
		if(response.Status==1){
			DataGrid.setParam("dg1",Constant.PageIndex,0);
			DataGrid.loadData("dg1");
			$D.close();
		}else{
		   Dialog.alert(response.Message);
		}
	})
}

function replySave(){
	if($DW.Verify.hasError()){
		return;
    }
	var dc = Form.getData($DW.$F("formMsg"));
	Server.sendRequest("com.xdarkness.cms.document.Message.reply",dc,function(response){
		if(response.Status==1){
			DataGrid.setParam("dg1",Constant.PageIndex,0);
			DataGrid.loadData("dg1");
			Dialog.alert(response.Message);
			$D.close();
		}else{
		   Dialog.alert(response.Message);
		}
	})
}

function messageHistory(){
	var diag =new Dialog("diag1");
	diag.Width = 800;
	diag.Height = 400;
	diag.Title = "已发消息";
	diag.URL = "Document/MessageHistory.jsp";
	diag.show();
	diag.OKButton.hide();
	diag.CancelButton.value="关闭";
}

function messageDetail(fromUser){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请选择需要查看的消息！");
		return;
	}
	var diag =new Dialog("diag1");
	diag.Width = 500;
	diag.Height = 250;
	diag.Title = "查看消息";
	diag.URL = "Document/MessageDetailDialog.jsp?ID="+arr[0];
	diag.OKEvent = reply;
	diag.show();
	if(fromUser=="SYSTEM"){
		diag.OKButton.hide();
	}else{
		diag.OKButton.value = "回复";
	}
	DataGrid.loadData("dg1");
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
		Server.sendRequest("com.xdarkness.cms.document.Message.del",dc,function(response){
			if(response.Status==1){
				DataGrid.loadData("dg1");
			}
		});
	});
}

function setReadFlag(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join());
	Server.sendRequest("com.xdarkness.cms.document.Message.setReadFlag",dc,function(response){
		Dialog.alert(response.Message);
		if(response.Status==1){
			DataGrid.loadData("dg1");
		}
	});
}

setInterval(function(){DataGrid.loadData("dg1");},10000);
</script>
</head>
<sky:init method="com.xdarkness.cms.document.Message.init">
	<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img
						src="../Icons/icon028a1.gif" width="20" height="20" /> 短消息列表</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;">
					<sky:button onClick="add()"><img src="../Icons/icon028a2.gif" width="20" height="20" />新建</sky:button> 
					<sky:button onClick="reply()"><img src="../Icons/icon028a4.gif" width="20" height="20" />回复</sky:button> 
					<sky:button onClick="del()"><img src="../Icons/icon028a3.gif" width="20" height="20" />删除</sky:button> 
					<sky:button onClick="setReadFlag()"><img src="../Icons/icon034a4.gif" width="20" height="20" />标记为己读</sky:button>
					<sky:button onClick="messageHistory()"><img src="../Icons/icon028a7.gif" width="20" height="20" />已发信息</sky:button>
					</td>
				</tr>
				<tr>
					<td
						style="padding-top:2px;padding-left:6px;padding-right:6px;padding-bottom:2px;">
					<sky:datagrid id="dg1"
						method="com.xdarkness.cms.document.Message.dg1DataBind" size="15">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr xtype="head" class="dataTableHead">
								<td width="3%"  xtype="RowNo">&nbsp;</td>
								<td width="3%" xtype="selector" field="id">&nbsp;</td>
								<td width="3%">&nbsp;</td>
								<td width="57%" sortField="subject">标题</td>
								<td width="17%" sortField="fromuser"><strong>发送人</strong></td>
								<td width="17%" sortField="addtime" direction="DESC"><strong>发送时间</strong></td>
							</tr>
							<tr onDblClick="messageDetail('${FromUser}');">
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>${ReadFlagIcon}</td>
								<td>${Subject}</td>
								<td>${FromUser}</td>
								<td>${addTime}</td>
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
</sky:init>
</html>
