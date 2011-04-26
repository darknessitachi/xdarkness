<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.xdarkness.framework.Config"%>
<%@ taglib uri="controls" prefix="sky" %>
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
function removeFavorites() {
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr || arr.length == 0) {
		Dialog.alert("请先选择要移出收藏夹的商品！");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs", arr.join());
	remove(dc);
}

function priceNote(ID, priceNoteFlag) {
	if(priceNoteFlag == "Y") {
		Dialog.alert("该商品已经设置了降价提醒！");
		return;
	}
	var dc = new DataCollection();
	dc.add("IDs", ID);
	dc.add("SiteID", $("SiteID").value);
	Server.sendRequest("com.xdarkness.shop.web.MemberFavorite.priceNote", dc, function(response) {
		Dialog.alert(response.Message);
	});
}

function removeFavorite(ID) {
	var dc = new DataCollection();
	dc.add("IDs", ID);
	remove(dc);
}

function remove(dc){
	dc.add("SiteID", $("SiteID").value);
	Dialog.confirm("您确认要移出收藏夹吗？", function() {
		Server.sendRequest("com.xdarkness.shop.web.MemberFavorite.removeFromFavorite", dc, function(response) {
			if(response.Status == 0){
				Dialog.alert(response.Message);
			} else {
				Dialog.alert(response.Message);
				DataGrid.loadData('dg1');
			}
		});
	});
}

function addToCart(GoodsID) {
	var dc = new DataCollection();
	dc.add("GoodsID", GoodsID);
	dc.add("BuyNowFlag", 0);
	Server.sendRequest("com.xdarkness.shop.web.MemberFavorite.addToCart", dc, function(response) {
			if(response.Status == 1){
				window.open("<%=Config.getContextPath()%>Shop/Web/ShopCart.jsp?cur=Menu_SC&SiteID=<%=SiteID%>");
			} else {
				Dialog.alert(response.Message);
			}
		});
	window.open(url);
}

function allToCart() {
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr || arr.length == 0) {
		Dialog.alert("请先选择要购买的商品！");
		return;
	}
	addToCart(arr.toString().replace(new RegExp(",","g"), "-"));
}

function toShopCart(){
	window.open("<%=Config.getContextPath()%>Shop/Web/ShopCart.jsp?cur=Menu_SC&SiteID=<%=SiteID%>");
}
</script>
</head>
<body>
<%@ include file="../../Include/Top.jsp" %>
<%@ include file="../../Member/Verify.jsp"%>
<div class="wrap">
<div id="nav" style="margin-bottom:12px">
	首页  &raquo; <a href="../../Member/MemberInfo.jsp?cur=Menu_MI&SiteID=<%=SiteID%>">会员中心</a>  &raquo; 我的收藏夹
</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr valign="top">
    <td width="200">
	<%@ include file="../../Include/Menu.jsp" %></td>
    <td width="20">&nbsp;</td>
    <td>  
    <div class="forumbox">
    	<div style="float:right">
			<button type="button" onclick="allToCart();" style="position:relative">放入购物车</button>&nbsp;&nbsp;
			<button type="button" onclick="removeFavorites();" style="position:relative">移出收藏夹</button>
			</div>
	    <ul class="tabs">
			<li class="current">&nbsp;收藏夹商品列表</li>
		</ul>
	    <sky:datagrid id="dg1" method="com.xdarkness.shop.web.MemberFavorite.dg1DataBind" size="13">
			<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
				<tr xtype="head" class="dataTableHead">
		            <td width="5%" align="center" xtype="selector" field="ID">&nbsp;</td>
		            <td width="15%" align="center">商品图片</td>
		            <td width="45%" align="center">商品信息</td>
		            <td width="15%" align="center">价格</td>
		            <td width="20%" align="center">&nbsp;</td>
				</tr>
	     		<tr>
	        		<td align="center">&nbsp;</td>
			        <td align="center">
			        	<img src="${Image}" height="100" width="75" alt="" />
			        </td>
			        <td title="${Name}">
			        	<h4>${Name}</h4>
			          	收藏时间：${AddTime}
			        </td>
		            <td align="center"><span style="color:red">￥${Price}</span></td>
			        <td align="center">
			        	<div style="margin-bottom:10px"><button type="button" onClick="addToCart('${ID}');">放入购物车</button></div>
			        	<div style="margin-bottom:10px"><button type="button" onClick="removeFavorite('${ID}')">移出收藏夹</button></div>
						<div><button type="button" onClick="priceNote('${ID}', '${PriceNoteFlag}')">降价提醒？</button></div>
					</td>
				</tr>
			</table>
		</sky:datagrid>
	    <div style="text-align:right; padding:5px;">
	    	<button type="button" onclick="toShopCart();">查看购物车</button>
		</div>
	</div>
	</td>
  </tr>
</table>
</div>
<%@ include file="../../Include/Bottom.jsp" %>
</body>
</html>