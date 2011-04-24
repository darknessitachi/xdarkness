<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html>
<head>
<meta http-equiv="Content-ID" content="text/html; charset=utf-8" />
<title>订单项详细表</title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.row2Form dl,
.row3Form dl{ height:23px;}
</style>
<script src="../Framework/Main.js"></script>
<script src="../Framework/District.js"></script>
<script type="text/javascript">
Page.onLoad(function(){
	$("dg1").afterEdit = function(tr,dr){
		if(Verify.hasError()){
	 	 	return;
    	}														
		dr.set("UserName",$V("UserName"));
		dr.set("SN",$V("SN"));
		dr.set("Name",$V("Name"));
		dr.set("Price",$V("Price"));
		dr.set("Discount",$V("Discount"));
		dr.set("DiscountPrice",$V("DiscountPrice"));
		dr.set("Count",$V("Count"));
		dr.set("Amount",$V("Amount"));
		dr.set("Score",$V("Score"));
		return true;
	}
});

function add(){
	var diag = new Dialog("AddOrderItem");
	diag.Width = 400;
	diag.Height = 300;
	diag.Title = "新建订单详细项";
	diag.URL = "Shop/OrderItemDialog.jsp?OrderID=" + $V("OrderID") + "&UserName=" + $V("UserName");
	diag.onLoad = function(){
		$DW.$("SN").focus();
	};
	diag.OKEvent = addSave;
	diag.show();
}

function addSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
	  return;
     }
	Server.sendRequest("com.xdarkness.shop.OrderItem.add",dc,function(){
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
	DataGrid.save("dg1","com.xdarkness.shop.OrderItem.dg1Edit",function(){DataGrid.loadData('dg1');});
}

function del(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的行！");
		return;
	}
	Dialog.confirm("确定要删除选中的项吗？",function(){

		var dc = new DataCollection();	
		dc.add("IDs",arr.join());
		dc.add("OrderID",$V("OrderID"));
		dc.add("UserName",$V("UserName"));
		Server.sendRequest("com.xdarkness.shop.OrderItem.del",dc,function(){
			var response = Server.getResponse();
			Dialog.alert(response.Message,function(){
				if(response.Status==1){
					$D.close();
					window.location.reload();
				}
			});
		});
	});
}

function editOrder(){
	var dc = Form.getData($F("orderform"));
	Server.sendRequest("com.xdarkness.shop.Order.orderEdit",dc,function(){
			var response = Server.getResponse();
			Dialog.alert(response.Message,function(){
			if(response.Status==1){
				window.location.reload();
			}
			});
		});
}

function goodsInfo(id){
	var diag = new Dialog("DiagGoods");
	diag.Width = 460;
	diag.Height = 500;
	diag.Title = "商品详细信息";
	diag.URL = "Shop/GoodsInfo.jsp?ID="+id;
	diag.OKEvent = function(){
		$D.close();
	}
	diag.show();
	//window.open("GoodsInfo.jsp?ID="+id);
}

function memberInfo(id){
	if(id!=''){
		var diag = new Dialog("DiagMember");
		diag.Width = 460;
		diag.Height = 300;
		diag.Title = "会员详细信息";
		diag.URL = "Shop/MemberInfo.jsp?UserName=" + id
		diag.OKEvent = function(){
			$D.close();
		}
		diag.show();
	}
}
</script>
</head>
<body>
<sky:init method="com.xdarkness.shop.Order.initEditDialog">
	<form id="orderform"><input type="hidden" id="ID" value="${ID}" />
	<input type="hidden" id="OrderID" value="${OrderID}" /> <input
		type="hidden" id="UserName" value="${UserName}" />
	<table width="100%" border="0" cellspacing="0" cellpadding="6"
		class="blockTable">
		<tr>
			<td valign="middle" class="blockTd"><img
				src="../Icons/icon018a6.gif" /> 订单<b style="color:#F60;">${OrderID}</b>详细信息</td>
		</tr>
		<tr>
			<td style="padding:8px 10px;"><sky:button onClick="editOrder()">
				<img src="../Icons/icon026a4.gif" />修改</sky:button> </td>
		</tr>
		<tr>
			<td style="padding:0px 10px;">
			<table width="100%" cellpadding="2" cellspacing="0" class="dataTable" style=" table-layout:auto">
				<tr>
					<td colspan="2" align="center">订单基本信息</td>
				</tr>
				<tr>
					<td colspan="2" class="row3Form">
                    	<dl>
                        	<dt>会员名：</dt>
                            <dd><input type="text" value="${UserName}" id="UserName" name="UserName" class="inputText"></dd>
                        </dl>
                        <dl>
                        	<dt>订单状态：</dt>
                            <dd><sky:select id="Status">${Status}</sky:select></dd>
                        </dl>
                        <dl>
                        	<dt style="width:35%;">是否有效：</dt>
                            <dd style="width:65%;">${IsValid}</dd>
                        </dl>
                        <dl>
                        	<dt>商品金额：</dt>
                            <dd><input value="${Amount}" type="text" id="Amount2" name="Amount2" class="inputText" verify="商品金额为数字|NotNull&&Number"></dd>
                        </dl>
                        <dl>
                        	<dt>配送金额：</dt>
                            <dd><input value="${SendFee}" type="text" id="SendFee" name="SendFee" class="inputText" verify="配送金额为数字|NotNull&&Number"></dd>
                        </dl>
                        <dl>
                        	<dt>订单金额：</dt>
                            <dd><input value="${OrderAmount}" type="text" id="OrderAmount" name="OrderAmount" class="inputText" verify="订单金额为数字|NotNull&&Number"></dd>
                        </dl>
                        <dl>
                        	<dt>收货人：</dt>
                            <dd><input value="${Name}" type="text" id="Name" name="Name" class="inputText" verify="收货人的姓名|NotNull&&Regex=\D+"></dd>
                        </dl>
                        <dl>
                        	<dt>固定电话：</dt>
                            <dd><input value="${Tel}" type="text" id="Tel" name="Tel" class="inputText" verify="固定电话|NotNull"></dd>
                        </dl>
                        <dl>
                        	<dt>移动电话：</dt>
                            <dd><input value="${Mobile}" type="text" id="Mobile" name="Mobile" class="inputText" verify="移动电话|NotNull"></dd>
                        </dl>
                        <dl>
                        	<dt>配送方式：</dt>
                            <dd><sky:select id="SendType" style="width:180">${SendType}</sky:select></dd>
                        </dl>
                        <dl>
                        	<dt>支付方式：</dt>
                            <dd><sky:select id="PaymentType" style="width:180">${PaymentType}</sky:select></dd>
                        </dl>
                        <dl>
                        	<dt>订单积分：</dt>
                            <dd><input value="${Score}" type="text" id="Score2" name="Score2" class="inputText" verify="积分|NotNull&&Int"></dd>
                        </dl>
                        <dl>
                        	<dt>是否开发票：</dt>
                            <dd>${HasInvoice}</dd>
                        </dl>
                        <dl>
                        	<dt>发票抬头：</dt>
                            <dd><input value="${InvoiceTitle}" type="text" id="InvoiceTitle" name="InvoiceTitle" size=20 class="inputText" verify="NotNull"></dd>
                        </dl>
                        
                    </td>
				</tr>
                <tr>
                    <td align="right" width="6%">&nbsp;&nbsp;送货时间</td>
                    <td align="left"  class="row3Form" width="94%">
                    	<dl>
                        	<dt>送货时间段：</dt>
                            <dd><sky:select id="SendTimeSlice">${SendTimeSlice}</sky:select></dd>
                        </dl>
                        <dl>
                        	<dt>送货开始时间：</dt>
                            <dd><input value="${SendBeginDate}" type="text" id="SendBeginDate" name="SendBeginDate" xtype="Date" class="inputText" verify="NotNull"></dd>
                        </dl>
                        <dl >
                        	<dt>送货结束时间：</dt>
                            <dd><input value="${SendEndDate}" type="text" id="SendEndDate" name="SendEndDate" xtype="Date" class="inputText" verify="NotNull"></dd>
                        </dl>
                    </td>
                </tr>
                <tr>
                    <td align="right" valign="top" width="6%">&nbsp;&nbsp;送货地址</td>
                    <td align="left"  class="row3Form" width="94%">
                    	<dl>
                        	<dt>省份：</dt>
                            <dd><sky:select id="Province" value="${Province}" onChange="changeProvince();" listHeight="300"> </sky:select></dd>
                        </dl>
                        <dl>
                        	<dt>城市：</dt>
                            <dd><sky:select id="City" value="${City}" onChange="changeCity();" listHeight="300"> </sky:select></dd>
                        </dl>
                        <dl >
                        	<dt>区县：</dt>
                            <dd><sky:select id="District" value="${District}" listHeight="300"> </sky:select></dd>
                        </dl>
                        <dl style="width:44%">
                        	<dt style="width:30%">详细地址：</dt>
                            <dd style="width:70%"><input value="${Address}" type="text" id="Address" size=24 name="Address" class="inputText" verify="地址|NotNull"></dd>
                        </dl>
                    </td>
                </tr>
				<tr>
					<td colspan="2" class="row3Form">
                    	<dl>
                        	<dt>邮编：</dt>
                            <dd><input value="${ZipCode}" type="text" id="ZipCode" name="ZipCode" class="inputText" verify="邮编|NotNull&&Number"></dd>
                        </dl>
                        <dl style="width:45%;">
                        	<dt>送货特殊说明：</dt>
                            <dd><input value="${SendInfo}" type="text" id="SendInfo" name="SendInfo" size=25 class="inputText" verify="NotNull" /></dd>
                        </dl>
                    </td>
				</tr>

			</table>
			</td>
		</tr>
		<tr>
			<td valign="middle" class="blockTd"><img
				src="../Icons/icon026a12.gif" /> 订单项详细表</td>
		</tr>
		<tr>
			<td style="padding:8px 10px;"><sky:button onClick="add()">
				<img src="../Icons/icon026a2.gif" />新建</sky:button> <sky:button onClick="del()">
				<img src="../Icons/icon026a3.gif" />删除</sky:button></td>
		</tr>
		<tr>
			<td
				style="padding-top:0px;padding-left:6px;padding-right:6px;padding-bottom:8px;">
			<sky:datagrid id="dg1" method="com.xdarkness.shop.OrderItem.dg1DataBind"
				size="10">
				<table width="100%" cellpadding="2" cellspacing="0"
					class="dataTable">
					<tr xtype="head" class="dataTableHead">
						<td width="5%" xtype="RowNo"><b>序号</b></td>
						<td width="4%" xtype="selector" field="GoodsID">&nbsp;</td>
						<td width="41%"><b>名称</b></td>
						<td width="8%"><b>价格</b></td>
						<td width="8%"><b>折扣</b></td>
						<td width="8%"><b>折扣价</b></td>
						<td width="8%"><b>数量</b></td>
						<td width="8%"><b>小计</b></td>
						<td width="10%"><b>积分</b></td>

					</tr>
					<tr style1="background-color:#FFFFFF"
						style2="background-color:#F9FBFC">
						<td height="22" align="center">&nbsp;</td>
						<td>&nbsp;</td>
						<td><span title="${Name}">${Name}</span></td>
						<td>${Price}</td>
						<td>${Discount}</td>
						<td>${DiscountPrice}</td>
						<td>${Count}</td>
						<td>${Amount}</td>
						<td>${Score}</td>
					</tr>

					<tr xtype="pagebar">
						<td height="25" colspan="12">${PageBar}</td>
					</tr>
				</table>
			</sky:datagrid></td>
		</tr>
	</table>
	</form>
</sky:init>
</body>
</html>
