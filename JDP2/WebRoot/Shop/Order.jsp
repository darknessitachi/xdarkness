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
<title>订单列表</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script>
Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		if(Verify.hasError()){
	 	 return;
    }	
		dr.set("Status",$V("dg1_Status_DropDownList"+tr.rowIndex));
		dr.set("Name",$V("Name"));
		dr.set("HasInvoice",$V("dg1_HasInvoice_DropDownList"+tr.rowIndex));
		return true;
	}
});

function add(){
	var diag = new Dialog("Diag1");
	diag.Width = 600;
	diag.Height = 450;
	diag.Title = "新建订单";	
	diag.URL = "Shop/OrderDialog.jsp";
	diag.onLoad = function(){
		$DW.$("UserName").focus();
	};
	diag.OKEvent = addSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "新建订单信息";
	diag.Message = "新建订单ID、订单状态等";
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	
	if($DW.Verify.hasError()){
	  return;
     }
     var province=$DW.$V("Province");
     var city=$DW.$V("City");
     var district=$DW.$V("District");
     if(province==""){
     	Dialog.alert("请选择省份");
     	return;
     }
     
     if(city==""){
     	Dialog.alert("请选择城市");
     	return;
     }
     
     if(district==""){
     	Dialog.alert("请选择区县");
     	return;
     }
     
	Server.sendRequest("com.xdarkness.shop.Order.add",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
		if(response.Status==1){
			$D.close();
			window.location.reload();
		}
		});
	});
}

function save(){
	DataGrid.save("dg1","com.xdarkness.shop.Order.dg1Edit",function(){DataGrid.loadData('dg1');});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("确定要删除该订单吗？",function(){

		var dc = new DataCollection();	
		dc.add("IDs",arr.join());
		Server.sendRequest("com.xdarkness.shop.Order.del",dc,function(){
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
	});
} 

function createSending(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要生成配送单的订单 ！");
		return;
	}
	/*var diag = new Dialog("Diag1");
	diag.Width = 300;
	diag.Height = 100;
	diag.Title = "选择配送药店";
	diag.URL = "Shop/StoreSelect.jsp";
	diag.OKEvent = createSendSave;
	diag.show();*/
	createSendSave();
}

function createSendSave(){
	var arr = DataGrid.getSelectedValue("dg1");
	var dc = new DataCollection();	
	dc.add("IDs",arr.join());
	//dc.add("StoreCode",$DW.$V("StoreCode"));
	Server.sendRequest("com.xdarkness.shop.Order.createSending",dc,function(){
		var response = Server.getResponse();
		Dialog.alert(response.Message,function(){
			if(response.Status==1){
				$D.close();
				DataGrid.loadData("dg1");
			}
		});
	});
}

function doSearchUserName(){
	if(!$V("searchUserName")||!$V("searchUserName").trim()){
		Dialog.alert("会员名称不能为空！");
		return;
	}
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","searchUserName",$V("searchUserName"));
	DataGrid.setParam("dg1","searchStatus",$V("StatusSelect"));
	DataGrid.loadData("dg1");
}

function doStatusSearch(){
	DataGrid.setParam("dg1",Constant.PageIndex,0);
	DataGrid.setParam("dg1","searchUserName",$V("searchUserName"));
	DataGrid.setParam("dg1","searchStatus",$V("StatusSelect"));
	DataGrid.loadData("dg1");
}

function clickOrderItem(ID, UserName, back){
	//window.location = "OrderItem.jsp?OrderID=" + ID + "&MemberID=" + MemberID + 
	//	"&back=" + back + "&PageIndex=" + DataGrid.getParam("dg1", Constant.PageIndex);
	
	var diag = new Dialog("Diag1");
	diag.Width = 800;
	diag.Height = 500;
	diag.Title = "订单详情";
	diag.URL = "Shop/OrderItem.jsp?OrderID=" + ID + "&UserName=" + UserName + 
		"&back=" + back + "&PageIndex=" + DataGrid.getParam("dg1", Constant.PageIndex);
	diag.ShowButtonRow = false;
	diag.show();
}
</script>
</head>
<body>
<sky:init method="com.xdarkness.shop.Order.initStatusSelect">
	<table width="100%" border="0" cellspacing="6" cellpadding="0"
		style="border-collapse: separate; border-spacing: 6px;">
		<tr valign="top">
			<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6"
				class="blockTable">
				<tr>
					<td valign="middle" class="blockTd"><img
						src="../Icons/icon018a1.gif" /> 订单列表</td>
				</tr>
				<tr>
					<td style="padding:8px 10px;">
						<sky:button onClick="add()">
							<img src="../Icons/icon018a2.gif" />新建</sky:button>
						<sky:button onClick="save()">
							<img src="../Icons/icon018a16.gif" />保存</sky:button> 
						<sky:button onClick="createSending()">
							<img src="../Icons/icon018a7.gif" />生成配送单</sky:button> 
						<!--<sky:button onClick="del()"> <img src="../Icons/icon026a3.gif" />删除</sky:button>-->
						<div style="float: right">
							订单状态：
							<sky:select id="StatusSelect" style="width:100"
								onChange="doStatusSearch();">${StatusSelect}</sky:select>
							&nbsp;&nbsp;会员名称：
							<input type="text" name="searchUserName" id="searchUserName"> 
							<input type="button" name="Submit" value="查询" onClick="doSearchUserName()">
						</div>
					</td>
				</tr>
				<tr>
					<td style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
					<sky:datagrid id="dg1" method="com.xdarkness.shop.Order.dg1DataBind" size="15">
						<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
							<tr xtype="head" class="dataTableHead">
								<td width="4%" xtype="RowNo">序号</td>
								<td width="3%" xtype="selector" field="ID">&nbsp;</td>
								<td width="10%">订单号</td>
								<td width="9%">会员名称</td>
								<td width="8%" zstyle='width:70px;' xtype="DropDownList"
									field="Status"
									sql="select CodeValue,CodeName from ZDCode where ParentCode ='Order.Status' Order by CodeOrder">订单状态</td>
								<td width="7%">商品金额</td>
								<td width="7%">配送金额</td>
								<td width="7%">订单金额</td>
								<td width="8%">收货人</td>
								<td width="15%">送货地址</td>
								<td width="6%" zstyle='width:45px;' xtype="DropDownList"
									field="HasInvoice"
									sql="select CodeValue,CodeName from ZDCode where ParentCode ='Order.HasInvoice' Order by CodeOrder">开发票</td>
							</tr>
							<tr style1="background-color:#FFFFFF"
								style2="background-color:#F9FBFC">
								<td align="center">&nbsp;</td>
								<td>&nbsp;</td>
								<td><a href="#" onclick="clickOrderItem('${ID}','${UserName}','Order.jsp');" title="${ID}">${ID}</a></td>
								<td>${UserName}</td>
								<td></td>
								<td>${Amount}</td>
								<td>${SendFee}</td>
								<td>${OrderAmount}</td>
								<td>${Name}</td>
								<td>${Address}</td>
								<td>&nbsp;</td>
							</tr>
							<tr xtype="edit" bgcolor="#E1F3FF">
								<td align="center" bgcolor="#E1F3FF">&nbsp;</td>
								<td>&nbsp;</td>
								<td>${ID}</td>
								<td>${UserName}</td>
								<td></td>
								<td>${Amount}</td>
								<td>${SendFee}</td>
								<td>${OrderAmount}</td>
								<td><input type="text" id="Name" value="${Name}"></td>
								<td><input type="text" id="Address" value="${Address}"></td>
								<td></td>
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
