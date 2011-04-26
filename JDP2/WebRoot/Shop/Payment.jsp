<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>支付方式信息表</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		if(Verify.hasError()){
	 	 return;
    }														
		dr.set("Name",$V("Name"));
		dr.set("Info",$V("Info"));
		return true;
	}
});

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 800;
	diag.Height = 400;
	diag.Title = "新建支付方式";
	diag.URL = "Shop/PaymentService/PmtAliPay.jsp?PaymentSelect=Shop/PaymentService/PmtAliPay.jsp";
	diag.ShowButtonRow = false;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "新建支付方式信息";
	diag.Message = "新建支付名称等";
	diag.show();
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("确定要删除该项吗？",function(){
		var dc = new DataCollection();	
		dc.add("IDs",arr.join());
		Server.sendRequest("com.xdarkness.shop.Payment.del",dc,function(){
			var response = Server.getResponse();
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert(response.Message);
				DataGrid.loadData("dg1");
			}
		});
		},function(){
		return;
	} );
}

function edit(){
  var dt = DataGrid.getSelectedData("dg1");
	var drs = dt.Rows;
	if(!drs||drs.length==0){
		Dialog.alert("请先选择要修改的选项！");
		return;
	}
	if(drs.length>1){
		Dialog.alert("只能选择1条信息修改！");
		return;
	}
	dr = drs[0]; 
  editDialog(dr);
}

function editDialog(dr){
	var diag = new Dialog("Diag1");
	diag.Width = 800;
	diag.Height = 400;
	diag.Title = "支付方式信息修改";
	diag.URL = dr.get("PmtLinkName") + "?PaymentSelect=" + dr.get("PmtLinkName") + "&ID=" + dr.get("ID");
	diag.onLoad = function(){
		
	};
	diag.ShowButtonRow = false;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "修改支付方式信息";
	diag.Message = "修改支付名称等";
	diag.show();
}
</script>
</head>
<%
String path = (Config.getContextPath() + Config.getValue("UploadDir") + "/" 
	+ SiteUtil.getAlias(Application.getCurrentSiteID()) + "/").replaceAll("//","/");
%>
<body>
<table width="100%" border="0" cellspacing="6" cellpadding="0"
	style="border-collapse: separate; border-spacing: 6px;">
	<tr valign="top">
		<td>
		<table width="100%" border="0" cellspacing="0" cellpadding="6"
			class="blockTable">
			<tr>
				<td valign="middle" class="blockTd"><img
					src="../Icons/icon009a1.gif" />支付方式信息表</td>
			</tr>
			<tr>
				<td style="padding:8px 10px;"><sky:button onClick="add()">
					<img src="../Icons/icon009a2.gif" />新建</sky:button> <sky:button onClick="edit()">
					<img src="../Icons/icon009a4.gif" />修改</sky:button> <sky:button onClick="del()">
					<img src="../Icons/icon009a3.gif" />删除</sky:button></td>
			</tr>
			<tr>
				<td
					style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
				<sky:datagrid id="dg1" method="com.xdarkness.shop.Payment.dg1DataBind"
					size="15">
					<table width="100%" cellpadding="2" cellspacing="0"
						class="dataTable">
						<tr xtype="head" class="dataTableHead">
							<td width="4%" align="center" xtype="RowNo"><b>序号</b></td>
							<td width="3%" xtype="selector" field="ID">&nbsp;</td>
							<td width="13%" align="center">图片</td>
							<td width="20%" align="center">支付方式名称</td>
							<td width="60%">支付方式说明</td>
							<td style="display:none">支付方式链接</td>
						</tr>
						<tr style1="background-color:#FFFFFF"
							style2="background-color:#F9FBFC">
							<td height="22" align="center">&nbsp;</td>
							<td>&nbsp;</td>
							<td align="center" title="${Name}"><img src="<%=path%>${ImagePath}" width="150" height="40" /></td>
							<td align="center" title="${Name}">${Name}</td>
							<td title="${Info}">${Info}</td>
							<td style="display:none">${PmtLinkName}</td>
						</tr>
						<tr xtype="pagebar">
							<td height="25" colspan="6">${PageBar}</td>
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
