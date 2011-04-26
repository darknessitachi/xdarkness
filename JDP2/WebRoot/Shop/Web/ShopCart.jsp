<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="controls" prefix="sky"%>
<%@page import="com.xdarkness.schema.ZSGoodsSchema"%>
<%@page import="com.xdarkness.framework.data.QueryBuilder"%>
<%@page import="com.xdarkness.framework.utility.StringUtil"%>
<%@page import="com.xdarkness.framework.Config"%>
<%@page import="com.xdarkness.framework.User"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>购物车</title>
<link href="<%=Config.getContextPath()%>Include/Default.css" rel="stylesheet" type="text/css">
<link href="<%=Config.getContextPath()%>Include/front-end.css" rel="stylesheet" type="text/css" />
<script src="<%=Config.getContextPath()%>Framework/Main.js"></script>
<%@ include file="../../Include/Head.jsp" %>
<%
String SiteID = request.getParameter("SiteID");
if(StringUtil.isEmpty(SiteID)||SiteID==null||SiteID=="null"){
	SiteID = new QueryBuilder("select ID from ZCSite Order by AddTime Desc").executeOneValue()+"";
}
%>
<script>
Page.onLoad(function() {
	var totalPrice = 0.0;
	var arr = $N("ItemPrice");
	for(var i = 0; i < arr.length; i++) {
		totalPrice += parseFloat(arr[i].innerHTML);
	}
	$("TotalPrice").innerHTML = totalPrice;
});

function clearShopCart(){
	DataGrid.selectAll("dg1");
	doDel();
}

function doDel(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要删除的商品！");
		return;
	}
	Dialog.confirm("您确认要删除吗？",function(){
		var dc = new DataCollection();
		dc.add("IDs",arr.join());
		Server.sendRequest("com.xdarkness.shop.web.ShopCart.del",dc,function(response){
			if(response.Status==0){
				Dialog.alert(response.Message);
			}else{
				Dialog.alert("删除商品成功");
				DataGrid.loadData("dg1");
			}
		});
	});
}

function update(GoodsID, ele){
	var patrn = /^[1-9]+?[0-9]*$/;
	if(!patrn.exec(ele.value)) {
		alert("输入非法！请输入大于0的数字！");
		return;
	}
	var dc=new DataCollection();
	dc.add("GoodsID", GoodsID);
	dc.add("Count", ele.value);
	dc.add("SiteID", '<%=SiteID %>');
	Server.sendRequest("com.xdarkness.shop.web.ShopCart.update", dc, function(response){
		var sumPrice = parseFloat(response.Message);
		$("sumPrice_" + GoodsID).innerHTML = sumPrice.toFixed(2).toString();
	});
}

function goodsToFavorite(){
	var arr = DataGrid.getSelectedValue("dg1");
	if(!arr||arr.length==0){
		Dialog.alert("请先选择要添加进收藏夹的商品！");
		return;
	}
	var dc=new DataCollection();
	dc.add("IDs",arr.join());
	dc.add("SiteID", '<%=SiteID %>');
	Server.sendRequest("com.xdarkness.shop.web.ShopCart.addToFavorite",dc,function(response){
		if(response.Status==0){
			Dialog.alert(response.Message);
		}else{
			Dialog.alert("添加成功");
			DataGrid.loadData("dg1");
		}
	});
}

function loadTotalPrice() {
	
}
</script>
<style type="text/css">
<!--
.STYLE1 {font-size: 14px}
-->
</style>
</head>
<body>
<%@ include file="../../Include/Top.jsp"%>
<div class="wrap">
<div id="nav" style="margin-bottom:12px">
	首页 <%if(User.isLogin() && !User.isManager()){%> &raquo; <a href="../../Member/MemberInfo.jsp?cur=Menu_MI&SiteID=<%=SiteID%>">会员中心</a> <%} %> &raquo; 我的购物车
</div>
      	<!-- 购物车 -->
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr valign="top">
		  	<%if(User.isLogin() && !User.isManager()){%>
            <td width="200"><%@ include file="../../Include/Menu.jsp" %></td>
			<%}%>
            <td width="20">&nbsp;</td>
            <td> 
        	<div class="forumbox">
                <div style="float:right">
                    <input id="GoodsID" type="hidden" value="<%=request.getParameter("GoodsID")%>"/>
             		<input id="GoodsCount" type="hidden" value="<%=request.getParameter("GoodsCount")%>"/>
                    <button type="button" style="position:relative" onclick="window.location='Favorite.jsp?cur=Menu_Fav&SiteID=<%=SiteID %>'">查看收藏夹</button>&nbsp;&nbsp;
                    <button type="button" style="position:relative" onclick="javascript:clearShopCart()">清空购物车</button>
                </div>
                <ul class="tabs" style="">
                    <li class="current"><a href="#;">我的购物车</a></li>
                </ul>
           		<div id="ShopCartTable" style="clear:both;">
           			<table width="100%" border="0" cellspacing="0" cellpadding="2">
           				<tr>
           					<td colspan="3">
	              				<sky:datagrid id="dg1" method="com.xdarkness.shop.web.ShopCart.dg1DataBind" page="false">
								<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
									<tr xtype="head" class="dataTableHead">
										<td width="5%" xtype="RowNo" align="center"><strong>序号</strong></td>
										<td width="5%" xtype="selector" field="ID" align="center">&nbsp;</td>
										<td width="10%" align="center"><b>商品图片</b></td>
										<td width="10%" align="center"><b>商品名称</b></td>
										<td width="13%" align="center"><b>市场价</b></td>
										<td width="20%" align="center"><b>商城价</b></td>
										<td width="8%" align="center"><b>数量</b></td>
										<td width="15%" align="center"><b>合计</b></td>
									</tr>
									<tr style1="background-color:#FFFFFF"
										style2="background-color:#F9FBFC">
										<td align="center">&nbsp;</td>
										<td align="center">&nbsp;</td>
										<td align="center"><img src="../wwwroot/ZCMSDemo/${image0}" alt="${Name}" width="80" /></td>
										<td>${Name}</td>
										<td align="center"><del><span id="del" style="color:red">￥${MarketPrice}元</span></del><br/></td>
										<td align="center">
											<b class="red"><span id="price_${ID}" style="color:green">￥${Price}元</span></b><br />
			                   				 为您节省：<br /><b class="red"><span >￥${SavePrice}元</span></b>
										</td>
										<td align="center"><input name="count" type="text" id="countProduct_${ID}" 
											style="width:30px;" value="${count}" maxlength=3 onChange="update('${ID}', this);"/></td>
										<td align="center"><b class="red">￥<span id="sumPrice_${ID}" name="ItemPrice">${sumPrice}</span>元</b></td>
									</tr>
								</table>
								</sky:datagrid>
							</td>
						</tr>
                		<tr>
		                 	<td>
			                 	<a href="javascript:doDel()"><img src="../../wwwroot/ZCMSDemo/images/delete_btn.gif" height="21" width="48" alt="删除" style="border:none;" /></a>
			                	<a href="javascript:void(0);" onClick="goodsToFavorite();"><img src="../../wwwroot/ZCMSDemo/images/remove_btn.gif" height="21" width="80" alt="移入收藏夹" style="border:none;" /></a>
			                </td>
		                   	<td>&nbsp;</td>
							<td><strong>共计:</strong><b class="red">￥<span id="TotalPrice"></span>元</b></td>
		                 </tr>
             		</table>
             	</div>
              <div class="gouwubtn" style="padding:5px; text-align:right;">
                <a href="Center.jsp?SiteID=<%=SiteID %>"><img src="../../wwwroot/ZCMSDemo/images/pay_btn.gif" alt="结算中心" /></a>
              </div>
          </div>
            </td>
          </tr>
        </table>
              
              <div class="about">
                <h2 style="background-color:#E5F3FE; color:#1F376D; font-size:13px; padding:5px 10px; font-family:'宋体'; margin-top:12px;">关于我的购物车</h2>
                <ol style="padding-left:15px; margin:12px 0px;">
                    <li>1 为方便您提交订单，您选入&ldquo;我的购物车&rdquo;中但尚未提交订单的商品我们将为您保留90天。</li>
                    <li>2 在商品保留期间，您所选择商品的价格、优惠政策、配送时间等信息可能发生变化。因此，您保留在购物车中的商品最终成交信息将以您提交订单成功当时公布的信息为准。</li>
                </ol>
              </div>
        <!-- 购物车结束 -->
</div>
<%@ include file="../../Include/Bottom.jsp" %>
</body>
</html>
