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
function evaluate(goodsID){
	var diag = new Dialog("Diag1");
	diag.Width = 400;
	diag.Height = 200;
	diag.URL = "Shop/Web/EvaluationDialog.jsp?GoodsID=" + goodsID;
	diag.OKEvent = evaluateSave;
	diag.ShowMessageRow = true;
	diag.MessageTitle = "添加商品评价";
	diag.Message = "商品评价一旦添加不可修改！";
	diag.show();
}

function evaluateSave(){
	var dc = $DW.Form.getData("form2");
	if($DW.Verify.hasError()){
		return;
	}
	Server.sendRequest("com.xdarkness.shop.web.MemberEvaluation.evaluate", dc, function(response) {
		if(response.Status == 0){
			Dialog.alert(response.Message);
			$D.close();
		} else {
			Dialog.alert(response.Message);
			$D.close();
			DataGrid.loadData('dg1');
		}
	});
}
</script>
</head>
<body>
<%@ include file="../../Include/Top.jsp" %>
<%@ include file="../../Member/Verify.jsp"%>
<div class="wrap">
<div id="nav" style="margin-bottom:12px">
	首页  &raquo; <a href="../../Member/MemberInfo.jsp?cur=Menu_MI&SiteID=<%=SiteID%>">会员中心</a>  &raquo; 商品评价
</div>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr valign="top">
    <td width="200"><%@ include file="../../Include/Menu.jsp" %></td>
    <td width="20">&nbsp;</td>
    <td>  
    <div class="forumbox">
		<div style="float:right">
			<strong>评价提醒：</strong>只能对半年内购买的商品进行评价！
		</div>
	    <ul class="tabs">
			<li class="current">&nbsp;我的购买记录</li>
		</ul>
	    <sky:datagrid id="dg1" method="com.xdarkness.shop.web.MemberEvaluation.dg1DataBind" size="13">
			<table width="100%" cellpadding="2" cellspacing="0" class="dataTable">
				<tr xtype="head" class="dataTableHead">
		            <td width="15%" align="center">商品图片</td>
		            <td width="40%" align="center">商品名称</td>
		            <td width="25%" align="center">购买时间</td>
		            <td width="10%" align="center">是否评价</td>
				</tr>
	     		<tr>
			        <td align="center">
			        	<img src="${Image}" height="100" width="100"/>
			        </td>
			        <td title="${Name}">${Name}</td>
		            <td align="center">${AddTime}</td>
			        <td align="center">${Evaluate}</td>
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