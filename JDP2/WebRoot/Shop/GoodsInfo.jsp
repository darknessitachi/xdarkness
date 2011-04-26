<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
function imageBrowse(){
	var diag = new Dialog("Diag4");
	diag.Width = 780;
	diag.Height = 460;
	diag.Title ="浏览图片库";
	diag.URL = "Resource/ImageLibDialog.jsp?Search=Search&SelectType=radio";
	diag.OKEvent = ok;
	diag.show();
}

function ok(){
	if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageUpload")){
 		$DW.Tab.getCurrentTab().contentWindow.upload();
	}
	else if($DW.Tab.getCurrentTab()==$DW.Tab.getChildTab("ImageBrowse")){
	 	$DW.Tab.getCurrentTab().contentWindow.onReturnBack();
	}
}

function onReturnBack(returnID){
	var dc = new DataCollection();
	dc.add("PicID",returnID);
	Server.sendRequest("com.xdarkness.shop.Brand.getPicSrc",dc,function(response){
		$("ImagePath").src = response.get("picSrc");
		alert(response.get("picSrc"));
		$("ImageID").value = returnID;
	})
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body class="dialogBody">
<div style="display:none"><iframe name="formTarget"
	src="javascript:void(0)"></iframe></div>
<sky:init method="com.xdarkness.shop.GoodsLib.initGoodsInfo">
	<form id="form2" method="post"><input type="hidden" id="ID"
		name="ID" value="${ID}">
	<table width="100%" cellpadding="2" cellspacing="0">
		<tr>
			<td width="43%" height="25" align='right'>商品类别：</td>
			<td width="57%" height="25">${CatalogName}</td>
		</tr>
		<tr>
			<td height="25" align='right'>所属品牌：</td>
			<td height="25">${BrandCatalogName}</td>
		</tr>
		<tr>
			<td align='right'>商品图片：</td>
			<td height="25"><img src="${imgpath}" width="100" height="75"></td>
		</tr>
		<tr>
			<td height="25" align='right'>编号：</td>
			<td height="25">${SN}</td>
		</tr>
		<tr>
			<td height="25" align='right'>名称：</td>
			<td height="25">${Name}</td>
		</tr>
		<tr>
			<td height="25" align='right'>规格：</td>
			<td height="25">${Standard}</td>
		</tr>
		<tr>
			<td height="25" align='right'>厂家：</td>
			<td height="25">${Factory}</td>
		</tr>
		<tr>
			<td height="25" align='right'>批准文号：</td>
			<td height="25">${Alias}</td>
		</tr>
		<tr>
			<td height="25" align='right'>市场价格：</td>
			<td height="25">${MarketPrice}</td>
		</tr>
		<tr>
			<td height="25" align='right'>会员价格：</td>
			<td height="25">${MemberPrice}</td>
		</tr>
		<tr>
			<td height="25" align='right'>VIP价格：</td>
			<td height="25">${VIPPrice}</td>
		</tr>
		<tr>
			<td height="25" align='right'>库存：</td>
			<td height="25">${Store}</td>
		</tr>
		<tr>
			<td height="25" align='right'>单位：</td>
			<td height="25">${Unit}</td>
		</tr>
		<!-- <tr>
			<td height="25" align='right'><strong>是否上架：</strong></td>
			<td height="25"><strong>${IsUp}</strong></td>
		</tr>
		<tr>
			<td align='right'>上架时间：</td>
			<td>${UpTime}</td>
		</tr> -->
		<tr>
			<td height="25" align='right'>积分：</td>
			<td height="25">${Score}</td>
		</tr>
		<tr>
			<td height="25" align='right'>销售数量：</td>
			<td height="25">${SaleCount}</td>
		</tr>
		${CustomColumn}
	</table>
	</form>
</sky:init>
</body>
</html>
