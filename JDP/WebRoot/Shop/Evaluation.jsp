<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>商品评价管理</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
function del() {
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr || arr.length == 0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("您确认要删除评价吗?", function() {
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.xdarkness.shop.Evaluation.del", dc, function(response) {
			Dialog.alert(response.Message,function() {
				if(response.Status==1){
					DataGrid.loadData("dg1");
				}
			});
		});
	})
}

function changeVerifyStatus() {
	DataGrid.setParam("dg1", "VerifyStatus", $V("VerifyStatus"));
	DataGrid.loadData("dg1");
}

function showDetail() {
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr || arr.length == 0) {
		Dialog.alert("请选择需要查看的评论！");
		return;
	} else if(arr.length > 1) {
		Dialog.alert("请选择需要查看的1条评论！！");
		return;
	}
	var diag = new Dialog("diag1");
	diag.Width = 500;
	diag.Height = 300;
	diag.Title = "评论详细";
	diag.URL = "Shop/EvaluationDetail.jsp?ID=" + arr[0];
	diag.ShowButtonRow = false;
	diag.show();
}

function Pass(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr || arr.length == 0) {
		Dialog.alert("请选择需要审核的评论！");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs", arr.join(","));
	dc.add("Type", "Pass");
	Server.sendRequest("com.xdarkness.shop.Evaluation.Verify", dc, function(response) {
		if(response.Status == 1) {
			Dialog.alert(response.Message, function() {
				DataGrid.loadData("dg1");
			});
		} else {
		   Dialog.alert(response.Message);
		}
	})
}

function NoPass(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请选择需要审核的评论！");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs",arr.join(","));
	dc.add("Type","NoPass");
	Server.sendRequest("com.xdarkness.shop.Evaluation.Verify",dc,function(response){
		if(response.Status==1){
			Dialog.alert(response.Message,function(){
				DataGrid.loadData("dg1");
			});
		}else{
		   Dialog.alert(response.Message);
		}
	})
}
</script>
</head>
<sky:init method="com.xdarkness.shop.Evaluation.init">
	<body>
	<table width="100%" border="0" cellspacing="6" cellpadding="0" style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6" class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img src="../Icons/icon002a1.gif" /> 商品评价管理</td>
				</tr>
				<tr>
					<td style="padding:0 8px 4px;">
                    <sky:button onClick="Pass()"><img src="../Icons/icon018a2.gif" />审核通过</sky:button>
                    <sky:button onClick="NoPass()"><img src="../Icons/icon018a1.gif" />审核不通过</sky:button>
                    <sky:button onClick="del()"><img src="../Icons/icon018a3.gif" />删除</sky:button>
                    <sky:button onClick="showDetail()"><img src="../Icons/icon018a1.gif" />查看详情</sky:button>
                    &nbsp;&nbsp;<span color="#999999">审核状态</span>
                    <sky:select id="VerifyStatus" style="width:60px" onChange="changeVerifyStatus();"> ${VerifyStatusOptions} </sky:select>
                    <input type="hidden" id="CatalogID" name="CatalogID" value=""/>
                    </td>
				</tr>
				<tr>   
					<td style="padding:0px 5px;">
					<sky:datagrid id="dg1" method="com.xdarkness.shop.Evaluation.dg1DataBind" size="15">
						<table width="100%" cellpadding="2" cellspacing="0"	class="dataTable">
							<tr xtype="head"  class="dataTableHead">
								<td width="2%">&nbsp;</td>
								<td width="3%" xtype="selector" field="id">&nbsp;</td>
								<td width="17%"><b>关联商品</b></td>
								<td width="9%"><b>评论标题</b></td>
                                <td width="23%"><b>评论内容</b></td>
								<td width="8%"><b>评论者</b></td>
                                <td width="10%"><b>商品所属类别</b></td>
								<td width="8%">审核状态</td>
								<td width="14%"><b>时间</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC" onDblClick="showDetail();">
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td title="${GoodsName}">${GoodsName}</td>
								<td title="${Title}">${Title}</td>
                                <td title="${Content}">${Content}</td>
								<td title="${AddUser}">${AddUser}</td>
                                <td title="${CatalogName}">${CatalogName}</td>
								<td title="${VerifyFlagName}">${VerifyFlagName}</td>
								<td title="${addTime}">${addTime}</td>
							</tr>
							<tr xtype="pagebar">
								<td height="13" colspan="10" align="center">${PageBar}</td>
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
