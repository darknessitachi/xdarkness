<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.xdarkness.framework.Config"%>
<%@ taglib uri="controls" prefix="sky" %>
<%@page import="com.xdarkness.framework.utility.Mapx"%>
<%@page import="com.xdarkness.framework.data.DataTable"%>
<%@page import="com.xdarkness.framework.utility.ServletUtil"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>结算中心</title>
<link href="<%=Config.getContextPath()%>Include/Default.css" rel="stylesheet" type="text/css">
<link href="<%=Config.getContextPath()%>Include/front-end.css" rel="stylesheet" type="text/css" />
<script src="<%=Config.getContextPath()%>Framework/Main.js" type="text/javascript"></script>
<script src="<%=Config.getContextPath()%>Framework/District.js"></script>
<%@ include file="../../Include/Head.jsp" %>
<%
String SiteID = request.getParameter("SiteID");
if(StringUtil.isEmpty(SiteID)||SiteID==null||SiteID=="null"){
	SiteID = new QueryBuilder("select ID from ZCSite Order by AddTime Desc").executeOneValue()+"";
}
//判断购物车是否为空，在生成订单后将购物车清空，生成订单后不能回退到该页面。
String shopCartCookie = ServletUtil.getCookieValue(request, "ShopCart");
if(StringUtil.isEmpty(shopCartCookie)) {
	out.println("<script>alert('非会员无权限!');window.history.go(-1);</script>");
	//out.println("<script>alert('您的购物车是空的，请先挑选商品!');window.location='" + Config.getContextPath() + "wwwroot/ZCMSDemo/index.shtml';</script>");
	return;
}
if(User.isManager()) {
	out.println("<script>alert('非会员无权限!');window.history.go(-1);</script>");
	//out.println("<script>alert('非会员无权限!');window.location='" + Config.getContextPath() + "wwwroot/ZCMSDemo/index.shtml';</script>");
	return;
}

%>
<script type="text/javascript">
Page.onLoad(function(){
	var AddrID = $NV("SendAddr");
	if(!AddrID||AddrID==""||AddrID==null){
		$("Address_Other").checked = true;
		$("newAddress").style.display="";
	}else{
		changeAddress(AddrID);
	}
});

function checkBeginDate(){
	var date=$V("SendBeginDate");
	var valid=/^\d{4}-((0{0,1}[1-9]{1})|(1[0-2]{1}))-((0{0,1}[1-9]{1})|([1-2]{1}[0-9]{1})|(3[0-1]{1}))$/;
	if(valid.test(date)){
		return true;
	}else{
		return false;
	}
}

function checkEndDate(){
	var enddate=$V("SendEndDate");
	var begindate=$V("SendBeginDate");
	var valid=/^\d{4}-((0{0,1}[1-9]{1})|(1[0-2]{1}))-((0{0,1}[1-9]{1})|([1-2]{1}[0-9]{1})|(3[0-1]{1}))$/;
	if(!valid.test(enddate)){
		return true;
	}
	if(begindate>enddate){
		return false;
	}
	return true;
}


function addOrder(){
	if(Verify.hasError()){
	   return;
	}
	var dc =Form.getData("Order");
	dc.add("Amount",$('SalePrice').innerHTML);
	dc.add("SendFee",$('SendPrice').innerHTML);
	dc.add("OrderAmount",$('PayPrice').innerHTML);
	Server.sendRequest("com.xdarkness.shop.web.OrderByCenter.add",dc,function(response){
        if(response.Status==1){
        	Dialog.alert("新增订单成功！");
        	window.location="OrderSubmitResult.jsp?PaymentTypeId=" + $NV("PaymentTypeRadio") + "&OrderID=" + response.Message;
        }else{
        	Dialog.alert("生成订单发生错误！");
        }
	});
   
}

function changeSendType(){
  $("SendPrice").innerHTML=$V("SendType");
  $("PayPrice").innerHTML=(+$('SalePrice').innerHTML+(+$('SendPrice').innerHTML)).toFixed(2);
}


function changeAddress(AddrID){
	if(AddrID=="Other"){
		$("newAddress").style.display="";
	}else{
		var dc = new DataCollection();
		dc.add("AddrID",AddrID);
		Server.sendRequest("com.xdarkness.member.MemberAddress.getAddress",dc,function(response){
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				$S("RealName",response.get("RealName"));
				$S("UserName",response.get("UserName"));
				$S("Tel",response.get("Tel"));
				initProvince($("Province"),$("City"),$("District"),response.get("Province"),response.get("City"),response.get("District"));
				$S("Address",response.get("Address"));
				$S("ZipCode",response.get("ZipCode"));
				$S("Email",response.get("Email"));
				$S("Mobile",response.get("Mobile"));
				$("newAddress").style.display="none";
			}
		});	
	}
}
</script>
</head>
<body>
<%@ include file="../../Include/Top.jsp" %>
<div class="wrap">
<%@ include file="../../Member/Verify.jsp"%>
<div id="nav"><a href="#">首页</a>  &raquo; 结算中心</div>
<br>
<sky:init method="com.xdarkness.shop.web.OrderByCenter.init">
<form id="Order" name="form"  method="post" >
<input name="SiteID" id="SiteID" value="<%=SiteID %>" type="hidden"> 
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr valign="top">
    <td>  <div class="forumbox">
      <h4>结算中心</h4>
      
      <ul class="tabs">
        <li class="current"><a href="#;">送货明细</a></li>
      </ul>
    
      <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#eeeeee" class="forumTable" style="margin-bottom:20px;">
            <tr>
            <td width="90%">
            <div style="padding-left:60px;">
            <sky:datalist id="dg1" method="com.xdarkness.shop.web.OrderByCenter.dg1DataList" page="false">
            <p style="margin-bottom:2px;">
            <label for="Address_${ID}" onClick="changeAddress('${ID}');">
            <input type="radio" id="Address_${ID}" name="SendAddr" value="${ID}" ${IsDefaultName} />
            ${RealName}&nbsp;&nbsp;&nbsp;${ProvinceName}&nbsp;${CityName}&nbsp;${DistrictName}${Address}
            </label></p>
            </sky:datalist>
            <p style="margin-bottom:2px;">
            <label for="Address_Other" onClick="changeAddress('Other');"><input type="radio" id="Address_Other" name="SendAddr" value="Other" />使用其它地址</label></p></div>
            <div id="newAddress" style="border:1px dotted #D0EBFF; display:none;">
            <table width="100%" cellpadding="2" cellspacing="0">
                <tr>
                    <td width="11%" align="right">收货人：</td>
                    <td width="40%"><input name="RealName" type="text" value="" id="RealName" verify="NotNull" />
                    <input name="UserName" id="UserName" type="hidden" value="" />
                    <input name="ID" id="ID" type="hidden" value="" />
                    <input name="IsDefault" id="IsDefault" type="hidden" value="Y" /></td>
                    <td width="17%" align="right">固定电话：</td>
                    <td width="32%"><input name="Tel" type="text" id="Tel" value=""/></td>
                </tr>
                <tr>
                    <td align="right">省份：</td>
                    <td colspan="3">
                    <sky:select id="Province" onChange="changeProvince();" listHeight="300" value="" verify="NotNull"> </sky:select>&nbsp;
                    <sky:select id="City" onChange="changeCity();" listHeight="300" value="" verify="NotNull"> </sky:select>&nbsp;
                    <sky:select id="District" listHeight="300" value="" verify="NotNull"> </sky:select>
                    </td>
                    </tr>
                <tr>
                    <td align="right">地址：</td>
                    <td><input name="Address" type="text" id="Address" style="width:250px;" value="" verify="NotNull"/></td>
                    <td align="right">邮编：</td>
                    <td><input name="ZipCode" type="text" id="ZipCode" value=""/></td>
                </tr>
                <tr>
                    <td align="right">电子邮件：</td>
                    <td><input name="Email" type="text" id="Email" value=""/></td>
                    <td align="right">手机：</td>
                    <td><input name="Mobile" type="text" id="Mobile" value=""/></td>
                </tr>
            </table>
            </div>
            </td>
            </tr>
      </table>
      <ul class="tabs">
        <li class="current"><a href="#;">订单基本信息</a></li>
      </ul>
      <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#eeeeee" class="forumTable" style="margin-bottom:20px;">
        	<tr>
				<td width="11%" height="25" align="right" class="tdgrey1">会员名：</td>
				<td width="44%" align="left" class="tdgrey2"><%=User.getValue("Name") %></td>
					
				<td width="13%" height="25" align="right" class="tdgrey1">是否开发票：</td>
				<td width="32%" align="left">${HasInvoice}</td>
				
			</tr>
			<tr id="tr_hidden" style="display:none">
				<td height="25" align="right" class="tdgrey1">积分：</td>
				<td class="tdgrey2" align="left">
					<input value="0" type="text" id="Score" name="Score" class="inputText"></td>
				<td height="25" align="right" class="tdgrey1">金额：</td>
				<td class="tdgrey2" align="left">
					<input value="0" type="text" id="Amount" name="Amount" class="inputText"></td>
			</tr>
			<tr>
				<td height="25" align="right" class="tdgrey1">发票抬头：</td>
				<td class="tdgrey2" colspan="3" align="left"><input name="InvoiceTitle"
					type="text" class="inputText" id="InvoiceTitle" value="" size="65"></td>
			</tr>
			<tr>
	            <td height="25" align="right" class="tdgrey1">送货特殊说明：</td>
	            <td><input size=65 value=""
					type="text" id="SendInfo" name="SendInfo" class="inputText"></td>
            </tr>
      </table>
      <!-- 配送方式 -->
      <ul class="tabs">
        <li class="current"><a href="#;">配送方式</a></li>
      </ul>
      <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#eeeeee" class="forumTable" style="margin-bottom:20px;">
		<tr>
			<td height="25" colspan="4" align="left" class="tdgrey1">${SendTypeRadio}</td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">配送时间：</td>
			<td class="tdgrey2" colspan="3" align="left">${SendTimeSlice}</td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">送货特殊说明：</td>
			<td class="tdgrey2" colspan="3" align="left">
				<input size=65 value="" type="text" id="SendInfo" name="SendInfo" class="inputText"></td>
		</tr>
      </table>
      <!-- 支付方式 -->
      <ul class="tabs">
        <li class="current"><a href="#;">支付方式</a></li>
      </ul>
      <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#eeeeee" class="forumTable" style="margin-bottom:20px;">
		<tr>
			<td height="25" colspan="4" align="left" class="tdgrey1">${PaymentTypeRadio}</td>
		</tr>
      </table>
      
      
	  <ul class="tabs">
        <li class="current"><a href="#;">商品列表</a></li>
      </ul>
      <input id="Status" type="hidden" value="0"/>
	  <input id="IsValid" type="hidden" value="Y"/>
      <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#eeeeee" class="forumTable" style="margin-bottom:20px;">
        <thead>
      
          <tr>
            <td width="5%">序号</td>
            <td width="40%">商品名称</td>
            <td width="8%">商城单价</td>
            <td width="6%">数量</td>
            <td width="10%">总价</td>
            </tr>
          </thead>
            <sky:datalist id="dg1" method="com.xdarkness.shop.web.OrderByCenter.dg1DataBind" size="13">
        <tr>
          <td width="5%">${RowNo}</td>
          <td width="40%"><h4>${Name}</h4></td>
          <td width="8%"><i>￥${Price}元</i></td>
          <td width="6%">${Count}</td>
          <td width="10%"><i>￥${sumPrice}元</i></td>         
        </tr>
           </sky:datalist>
        </table>
      <ul class="tabs">
        <li class="current"><a href="#;">订单价格</a></li>
      </ul>
      <table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#eeeeee" class="forumTable" style="margin-bottom:20px;">
         <sky:datalist id="dg2" method="com.xdarkness.shop.web.OrderByCenter.dg2DataBind" size="13">
          <tr>
                <td width="5%">&nbsp;</td>
                <td width="30%">&nbsp;</td>
                <td width="12%"><strong>商品总价：</strong>￥<span id="SalePrice">${SalePrice}</span>元</td>
                <td width="12%"><strong>配送价格：</strong>￥<span id="SendPrice">${SendPrice}</span>元</td>
                <td width="12%">共需支付：<i>￥<span id="PayPrice">${PayPrice}</span>元</i></td>
          </tr>
        </sky:datalist>
      </table>
      
          <div style="padding:5px;"><input type="button" name="submit" value="生成订单" onclick="addOrder()"/> 
          &nbsp; <a href="ShopCart.jsp?SiteID=<%=SiteID %>">返回购物车</a></div>
      </div>
</td>
  </tr>
</table>
</sky:init>
</form>
</div>
<%@ include file="../../Include/Bottom.jsp" %>
</body>
</html>