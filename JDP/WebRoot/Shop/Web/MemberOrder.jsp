<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.xdarkness.framework.Config"%>
<%@ taglib uri="controls" prefix="sky"%>
<%@page import="com.xdarkness.framework.utility.StringUtil"%>
<%@page import="com.xdarkness.framework.data.QueryBuilder"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>会员中心</title>
<link href="<%=Config.getContextPath()%>Include/Default.css" rel="stylesheet" type="text/css">
<link href="<%=Config.getContextPath()%>Include/front-end.css" rel="stylesheet" type="text/css" />
<script src="<%=Config.getContextPath()%>Framework/Main.js" type="text/javascript"></script>
<%@ include file="../../Include/Head.jsp" %>
<%
String SiteID = request.getParameter("SiteID");
if(StringUtil.isEmpty(SiteID)||SiteID==null||SiteID=="null"){
	SiteID = new QueryBuilder("select ID from ZCSite Order by AddTime Desc").executeOneValue()+"";
}
%>
<script type="text/javascript">
function cancel(orderID){
	if(confirm("确定取消吗？")) {
		var dc=new DataCollection();
		dc.add("OrderID", orderID);
		Server.sendRequest("com.xdarkness.shop.web.MemberOrder.cancel", dc, function(response){
			var response = Server.getResponse();
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert(response.Message);
				DataGrid.loadData("dg1");
			}
		});	
	}
}

function showOrderDetails(orderID){
	var diag = new Dialog("Diag2");
	diag.Width = 650;
	diag.Height = 400;
	diag.Title = "订单详情（订单编号：" + orderID + "）";
	diag.URL = "Shop/Web/SendingPrint.jsp?OrderID=" + orderID;
	diag.ShowButtonRow = false;
	diag.show();
	DataGrid.loadData("dg1");
}

function doSearch() {
	DataGrid.setParam("dg1", Constant.PageIndex, 0);
	DataGrid.setParam("dg1", "OrderID", $V("OrderID"));
	DataGrid.setParam("dg1", "StartDate", $V("StartDate"));
	DataGrid.setParam("dg1", "EndDate", $V("EndDate"));
	DataGrid.setParam("dg1", "SiteID", "<%=SiteID %>");
	DataGrid.loadData("dg1");
}
</script>
</head>
<body>
<%@ include file="../../Include/Top.jsp" %>
<%@ include file="../../Member/Verify.jsp"%>
<div class="wrap">
<div id="nav" style="margin-bottom:12px">
	首页  &raquo; <a href="../../Member/MemberInfo.jsp?cur=Menu_MI&SiteID=<%=SiteID%>">会员中心</a>  &raquo; 我的订单
</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr valign="top">
    <td width="200"><%@ include file="../../Include/Menu.jsp" %></td>
    <td width="20">&nbsp;</td>
    <td>  
    <div class="forumbox">
    <div style="float:right">
    	订单编号：<input type="text" id="OrderID" name="OrderID"/>&nbsp;
    	时间段：<input type="text" id="StartDate" name=""StartDate"" xtype="Date" class="inputText" size="13" readonly>&nbsp;到
    		   <input type="text" id="EndDate" name="EndDate" xtype="Date" class="inputText" size="13" readonly>
    	&nbsp;<button type="button" onclick="doSearch();">查询订单</button>
    </div>
    <ul class="tabs" style="">
		<li class="current"><a href="#;">订单列表</a></li>
	</ul>
    <sky:datagrid id="dg1" method="com.xdarkness.shop.web.MemberOrder.dg1DataBind" size="10">
		<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
			<tr xtype="head" class="dataTableHead">
				<td width="5%" align="center" xtype="RowNo"></td>
	            <td width="18%" align="center">订单编号</td>
	            <td width="10%" align="center">收货人</td>
	            <td width="10%" align="center">支付方式</td>
	            <td width="15%" align="center">订单金额</td>
	            <td width="18%" align="center">下单时间</td>
	            <td width="15%" align="center">订单状态</td>
	            <td width="9%" align="center">操作</td>
		    </tr>
	       	<tr>
		        <td align="center">&nbsp;</td>
		        <td><a href="#" onclick="showOrderDetails('${ID}')">${ID}</a></td>
		        <td align="center">${Name}</td>
		        <td align="center">${PaymentTypeName}</td>
		        <td align="center">${OrderAmount}</td>
		        <td align="center">${AddTime}</td>
		        <td align="center">${StatusName}</td>
		        <td align="center">${CancelCol}</td>
		        <!--<td align="center"><a href="javascript:void(0)" onClick="cancel('${ID}')">取消订单</a></td>
			--></tr>
			<tr xtype="pagebar">
				<td height="25" colspan="12">${PageBar}</td>
			</tr>
		</table>
	</sky:datagrid>
</div>
</td>
</tr>
</table>
</div>
<%@ include file="../../Include/Bottom.jsp" %>
</body>
</html>