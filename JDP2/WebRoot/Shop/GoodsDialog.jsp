<%@include file="../Include/Init.jsp"%>
<%@ taglib uri="controls" prefix="sky"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<script type="text/javascript">
//检测上架日期格式
function checkUpTime(){
	var upTime=$V("PublishDate");
	var valid=/^\d{4}-((0{0,1}[1-9]{1})|(1[0-2]{1}))-((0{0,1}[1-9]{1})|([1-2]{1}[0-9]{1})|(3[0-1]{1}))$/;
	if(valid.test(upTime)){
		return true;
	}
	return false;
}

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
		$("ImageID").value = returnID;
	})
}

function checkSN() {
	var dc=new DataCollection();
	dc.add("SN",$V("SN"));
	Server.sendRequest("com.xdarkness.shop.Goods.checkSN",dc,function(){
		var response = Server.getResponse();
		if(response.Status==0){
				var tip = Tip.show($("SN"),response.Message,false);
			}else{
				Tip.close($("SN"));				
			}
	});		
}

function isNotEmpty(str) {
	if(str != null && str != "") {
		return true;
	}
	return false;
}
function setDiscount() {
	if(isNotEmpty($V("MarketPrice"))){
		if(isNotEmpty($V("Price"))) {
			$("Discount").value = ($V("Price")/$V("MarketPrice")).toFixed("2")*100;
			$("OfferPrice").value = ($V("MarketPrice") - $V("Price")).toFixed("2");
		}
	}
}
function setOfferPrice() {
	if(isNotEmpty($V("MarketPrice"))){
		if(isNotEmpty($V("Discount"))) {
			$("Price").value = ($V("MarketPrice")*$V("Discount")/100).toFixed("2");
			$("OfferPrice").value = ($V("MarketPrice") - $V("Price")).toFixed("2");
		}
	}
}
function onUploadCompleted(returnID){
	onReturnBack(returnID);
}
Page.onLoad(function(){
	if(isNotEmpty($V("Discount"))) {
		$("Discount").value = parseInt($V("Discount"));
	}
});
</script>
</head>
<body class="dialogBody">
<sky:init method="com.xdarkness.shop.Goods.initDialog">
<div style="display:none"><iframe name="formTarget" src="javascript:void(0)"></iframe></div>
<form id="form2" method="post" action="checkSN()">
<!-- <input type="hidden" id="CatalogID" name="CatalogID" value="${CatalogID}" /> -->
<input type="hidden" id="ID" name="ID" value="${ID}" />
<input name="PicSrc1" type="hidden" id="PicSrc1" value="${PicSrc1}"/>
<table width="100%" cellpadding="2" cellspacing="0">
	<tr>
		<td width="700" height="10">
			<div style="text-align:right;"></div>
		</td>
		<td width="700"></td>
	</tr>
	<tr>
		<td height="25" align="right">
			<div style="text-align:right;">商品类别：</div>
		</td>
		<td height="25">
			<sky:select id="CatalogID" listWidth="200" listHeight="305" listURL="Shop/GoodsLibSelectList.jsp"></sky:select>
		</td>
	</tr>
	<tr>
		<td height="25" align="right">
			<div style="text-align:right;">所属品牌：</div>
		</td>
		<td height="25">
			<sky:select id="BrandID" verify="NotNull">${BrandOptions}</sky:select>
		</td>
	</tr>
	<tr>
		<td align="right">
			<div style="text-align:right;">商品图片：</div>
		</td>
		<td align="left">
			<input name="ImageID" type="hidden" id="ImageID" size=8 />
			<img src="${PicSrc}" width="150" name="ImagePath" id="ImagePath"> 
			<input type="button" name="Submit" value="浏览..." onClick="imageBrowse()">
		</td>
	</tr>
	<tr>
		<td height="25">
			<div style="text-align:right;">名称：</div>
		</td>
		<td height="25">
			<input id="Name" name="Name" type="text" class="input1"
				value="${Name}" size=36 verify="名称|NotNull" />
		</td>
	</tr>
	<tr>
		<td height="25">
			<div style="text-align:right;">规格：</div>
		</td>
		<td height="25">
			<input id="Standard" name="Standard" type="text"
				value="${Standard}" class="input1" verify="规格|NotNull" size=36 />
		</td>
	</tr>
	<tr>
		<td height="25">
			<div style="text-align:right;">厂家：</div>
		</td>
		<td height="25">
			<input id="Factory" name="Factory" type="text"
				value="${Factory}" class="input1" verify="厂家|NotNull" size=36 />
		</td>
	</tr>
	<tr>
		<td height="25">
		<div style="text-align:right;">市场价：</div>
		</td>
		<td height="25"><input id="MarketPrice" name="MarketPrice"
			type="text" value="${MarketPrice}" onChange="setDiscountAndOfferPrice();" class="input1"
			verify="商城价为数字|Regex=\d+([.]{1}\d+){0,1}&&NotNull" /></td>
	</tr>
	<tr>
		<td height="25">
		<div style="text-align:right;">商城价：</div>
		</td>
		<td height="25"><input id="Price" name="Price"
			type="text" value="${Price}" onChange="setDiscount();" class="input1"
			verify="优惠价为数字|Regex=\d+([.]{1}\d+){0,1}&&NotNull" /></td>
	</tr>
	<tr>
		<td height="25">
		<div style="text-align:right;">折扣：</div>
		</td>
		<td height="25"><input id="Discount" name="Discount" onChange="setOfferPrice();" verify="Int&&NotNull"
			type="text" value="${Discount}" class="input1"/></td>
	</tr>
	<tr>
		<td height="25">
		<div style="text-align:right;">节省：</div>
		</td>
		<td height="25"><input id="OfferPrice" readonly='true' name="OfferPrice"
			type="text" value="${OfferPrice}" class="input1"/></td>
	</tr>
	<tr>
		<td height="25">
		<div style="text-align:right;">库存：</div>
		</td>
		<td height="25"><input id="Store" name="Store" type="text"
			value="${Store}" class="input1" size=36
			verify="库存|Int&&Length<12&&NotNull" /></td>
	</tr>
	<tr>
		<td height="25">
		<div style="text-align:right;">数量单位：</div>
		</td>
		<td height="25"><input id="Unit" name="Unit" type="text"
			value="${Unit}" class="input1" size=36
			verify="单位|Regex=\S{1,7}&&NotNull" /></td>
	</tr>
	<tr>
		<td height="25">
		<div style="text-align:right;">上架时间：</div>
		</td>
		<td class="tdgrey1"><input
			name="PublishDate" type="text" value="${PublishDate}" class="inputText"
			id="PublishDate" xtype="Date" 
			verify="上架时间格式为yyyy-MM-dd|Script=checkUpTime()&&NotNull" />
	</tr>
	<tr>
		<td height="25">
		<div style="text-align:right;">积分：</div>
		</td>
		<td height="25"><input id="Score" name="Score" type="text"
			value="${Score}" class="input1" size=36
			verify="积分|Int&&Length<12&&NotNull" /></td>
	</tr>
	<tr>
		<td height="25">
		<div style="text-align:right;">销售数量：</div>
		</td>
		<td height="25"><input id="SaleCount" name="SaleCount"
			type="text" value="${SaleCount}" class="input1" size=36
			verify="销售数量|Int&&Length<12&&NotNull" /></td>
	</tr>
	<tr>
		<td height="25">
		<div style="text-align:right;">商品介绍：</div>
		</td>
		<td height="25"><textarea id="Content" name="Content" style="width:300px;height:150px"
			class="input1" >${Content}</textarea></td>
	</tr>
	${CustomColumn}
</table>
</form>
</sky:init>
</body>
</html>
