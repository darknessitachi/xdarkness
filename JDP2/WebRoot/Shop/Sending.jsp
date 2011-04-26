<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<%
	response.setHeader("Pragma","No-Cache");
	response.setHeader("Cache-Control","No-Cache");
	response.setDateHeader("Expires", 0);
%>
<html>
<head>
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>配送单列表</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		if(Verify.hasError()){
	 	 	return;
    	}	
		dr.set("Status",$V("dg1_Status_DropDownList"+tr.rowIndex));
		return true;
	}
});


function save(){
	DataGrid.save("dg1","com.xdarkness.shop.Order.dg1Edit",function(){DataGrid.loadData('dg1');});
}

function doSearchUserName(){
	if(!$V("searchUserName")||!$V("searchUserName").trim()){
		Dialog.alert("会员名称不能为空！");
		return;
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","searchUserName",$V("searchUserName"));
	DataGrid.loadData("dg1");
}

function printOrder(){
	var dt = DataGrid.getSelectedData("dg1");
	if(dt.getRowCount()==0){
		Dialog.alert("请先选择要打印的行!");
		return;
	}
	
	if (dt.getRowCount()>1) {
		Dialog.alert("一次只能打印一行记录!");
		return;
	}
	print(dt.getDataRow(0).get("ID"));
}

function print(OrderID){
	/*var diag = new Dialog("Diag2");
	diag.Width = 650;
	diag.Height = 400;
	diag.Title = "配送单";
	diag.URL = "Shop/SendingPrint.jsp?OrderID=" + OrderID;
	diag.ShowButtonRow = false;
	diag.show();
	DataGrid.loadData("dg1");*/
	window.open("Web/SendingPrint.jsp?OrderID=" + OrderID);
}
</script>
</head>
<body>
<sky:init method="com.xdarkness.shop.Order.initStatusSelect">
	<input type="hidden" id="ID" value="${ID}" />
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img
						src="../Icons/icon026a1.gif" />配送单列表</td>
				</tr>
				<tr>
					<td style="padding:8px 10px;"><sky:button onClick="save()">
						<img src="../Icons/icon026a16.gif" />保存</sky:button> <sky:button
						onClick="printOrder();">
						<img src="../Icons/icon013a1.gif" />打印</sky:button>
					<div style="float: right">会员名称：<input type="text"
						name="searchUserName" id="searchUserName"> <input
						type="button" name="Submit" value="查询"
						onClick="doSearchUserName()"></div>
					</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<sky:datagrid id="dg1" method="com.xdarkness.shop.web.Sending.dg1DataBind"
						size="15">
						<table width="100%" cellpadding="2" cellspacing="0"
							class="dataTable">
							<tr xtype="head" class="dataTableHead">
								<td width="4%" xtype="RowNo"><b>序号</b></td>
								<td width="3%" xtype="selector" field="ID">&nbsp;</td>
								<td width="11%"><b>订单号</b></td>
								<td width="7%">会员名称</td>
								<td width="9%" xtype="DropDownList"
									field="Status"
									sql="select CodeValue,CodeName from ZDCode where ParentCode ='Order.Status' and codevalue not in ('0','1','8','9') Order by CodeOrder"><b>订单状态</b></td>
								<td width="7%"><b>商品金额</b></td>
								<td width="7%"><b>配送金额</b></td>
								<td width="7%"><b>订单金额</b></td>
								<td width="8%">收货人</td>
								<td width="15%"><b>送货地址</b></td>
								<td width="6%"><b>开发票</b></td>
								<td width="5%" xtype='Edit'><b>编辑</b></td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td align="center">&nbsp;</td>
								<td>&nbsp;</td>
								<td><a href="javascript:void(0);" title="${ID}"
									onclick="print('${ID}');">${ID}</a></td>
								<td>${UserName}</td>
								<td></td>
								<td>${Amount}</td>
								<td>${SendFee}</td>
								<td>${OrderAmount}</td>
								<td>${Name}</td>
								<td>${Address}</td>
								<td>${IsValidName}</td>
								<td>&nbsp;</td>
							</tr>
							<tr xtype="edit" bgcolor="#E1F3FF">
								<td height="100" align="center">&nbsp;</td>
								<td>&nbsp;</td>
								<td>${ID}</td>
								<td>${UserName}</td>
								<td></td>
								<td>${Amount}</td>
								<td>${SendFee}</td>
								<td>${OrderAmount}</td>
								<td>${Name}</td>
								<td>${Address}</td>
								<td>${IsValidName}</td>
								<td>&nbsp;</td>
							</tr>
							<tr xtype="pagebar">
								<td height="25" colspan="12">${PageBar}</td>
							</tr>
						</table>
					</sky:datagrid></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
</sky:init>
</body>
</html>
