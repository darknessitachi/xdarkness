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
<title></title>
<link href="../Include/Default.css" rel="stylesheet" type="text/css" />
<script src="../Framework/Main.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	/*输入商品数量后计算总价,总积分*/
	function calculate(){
		var qty=$V("Count");
		var price=$V("DiscountPrice");
		var score=$V("Score");
		if(qty.length==0||price.length==0||score.length==0){
			return;
		}
		var valid=/^[1-9]{1}[0-9]*$/;
		if(!valid.test(qty)){
			return;
		}
		var amount=parseInt(qty)*parseFloat(price);
		$S("Amount",amount);
		var score=parseInt(qty)*parseInt(score);
		$S("Score",score);
	}
	/*商品的编号可以为空，可能通过药片编号和名称输入*/
	
	/*校验输入的商品编号，如果成功，显示编号,价格，折扣，折扣价格,积分，如果失败，显示提示信息*/
	function checkSN(){
		if($V("SN").length==0){
			return;
		}
		var valid=/^[0-9]+$/;
		if(!valid.test($V("SN"))){
			$("SNCheck").innerHTML="<font color='red'>编号为数字串!</font>";
			$S("Name","");
			$S("Price","");
			$S("Discount","");
			$S("DiscountPrice","");
			$S("Amount","");
			$S("Score","");
			$("NameCheck").innerHTML="";
			return;
		}
		var dc=new DataCollection();
		dc.add("SN",$V("SN"));
		Server.sendRequest("com.xdarkness.shop.OrderItem.checkSN",dc,function(){
			var response = Server.getResponse();
			if(response.Status==1){
				var name=response.get("Name");
				var price=response.get("Price");
				var discount=response.get("Discount");
				var discountprice=response.get("DiscountPrice");
				var score=response.get("Score");
				var goodsID=response.get("GoodsID");
				var factory=response.get("Factory");
				var standard=response.get("Standard");
				$S("Name",name);
				$S("Price",price);
				$S("Discount",discount);
				$S("DiscountPrice",discountprice);
				$S("GoodsID",goodsID);
				$S("Factory",factory);
				$S("Standard",standard);
				var qty=$V("Count");
				var valid=/^[1-9]{1}[0-9]*$/;
				if(valid.test(qty)){
					$S("Score",parseInt(qty)*parseFloat(score));
					$S("Amount",parseInt(qty)*parseFloat(discountprice));
				}else{
					$S("Score",score);
				}
				$("SNCheck").innerHTML="";
				$("NameCheck").innerHTML="";
				
			}else{
				$S("Name","");
				$S("Price","");
				$S("Discount","");
				$S("DiscountPrice","");
				$S("Amount","");
				$S("Score","");
				$("NameCheck").innerHTML="";
				$("SNCheck").innerHTML="<font color='red'>没有这个编号的商品!</font>";
			}
		});		
	}
	
	/*校验输入的商品名称，如果成功，显示价格，折扣，折扣价格,积分,如果失败，显示提示信息*/
	function checkName(){
		if($V("SN").length!=0){
			return;
		}
		if($V("Name").length==0){
			return;
		}
		var dc=new DataCollection();
		dc.add("Name",$V("Name"));
		Server.sendRequest("com.xdarkness.shop.OrderItem.checkName",dc,function(){
			var response = Server.getResponse();
			if(response.Status==1){
				var sn=response.get("SN");
				var price=response.get("Price");
				var discount=response.get("Discount");
				var discountprice=response.get("DiscountPrice");
				var score=response.get("Score");
				var goodsID=response.get("GoodsID");
				var factory=response.get("Factory");
				var standard=response.get("Standard");
				$S("SN",sn);
				$S("Price",price);
				$S("Discount",discount);
				$S("DiscountPrice",discountprice);
				$S("GoodsID",goodsID);
				$S("Factory",factory);
				$S("Standard",standard);
				var qty=$V("Count");
				var valid=/^[1-9]{1}[0-9]*$/;
				if(valid.test(qty)){
					$S("Score",parseInt(qty)*parseFloat(score));
					$S("Amount",parseInt(qty)*parseFloat(discountprice));
				}else{
					$S("Score",score);
				}
				$("SNCheck").innerHTML="";
				$("NameCheck").innerHTML="";
			}else{
				$S("SN","");
				$S("Price","");
				$S("Discount","");
				$S("DiscountPrice","");
				$S("Amount","");
				$S("Score","");
				$("SNCheck").innerHTML="";
				$("NameCheck").innerHTML="<font color='red'>没有这个名称的商品!</font>";
			}
		});		
	}
</script>
</head>
<body class="dialogBody">
<sky:init method="com.xdarkness.shop.OrderItem.init">
	<form id="form2">
	<table width="100%" cellpadding="2" cellspacing="0">
		<tr>
			<td height="10" class="tdgrey2" colspan="2" align="center">输入编号添加商品,如果商品没有编号,请输入商品全名</td>
		</tr>
		<tr>
			<td width="89" height="10" class="tdgrey2"></td>
			<td class="tdgrey2"><input type="hidden" id="OrderID"
				value="${OrderID}" /> <input type="hidden" id="UserName"
				value="${UserName}" /> <input type="hidden" name="GoodsID" value=""
				id="GoodsID" /></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">编号：</td>
			<td class="tdgrey2"><input value="" type="text" id="SN"
				name="SN" class="inputText" onblur="checkSN();"> <span
				id="SNCheck"></span></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">名称：</td>
			<td class="tdgrey2"><input value="" type="text" id="Name"
				name="Name" class="inputText" verify="名称为字符串|NotNull"
				onblur="checkName();"> <span id="NameCheck"></span></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">厂家：</td>
			<td class="tdgrey2"><input value="" type="text" id="Factory"
				name="Factory" class="inputText" readonly="readonly"></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">规格：</td>
			<td class="tdgrey2"><input value="" type="text" id="Standard"
				name="" Standard""  class="inputText" readonly="readonly"></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">价格：</td>
			<td class="tdgrey2"><input value="" type="text" id="Price"
				name="Price" class="inputText" readonly="readonly"></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">折扣：</td>
			<td class="tdgrey2"><input value="" type="text" id="Discount"
				name="Discount" class="inputText" readonly="readonly"></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">折扣价：</td>
			<td class="tdgrey2"><input value="" type="text"
				id="DiscountPrice" name="DiscountPrice" class="inputText"
				readonly="readonly"></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">数量：</td>
			<td class="tdgrey2"><input value="" type="text" id="Count"
				name="Count" class="inputText" verify="数量|NotNull&&Int"
				onBlur="calculate()"></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">小计：</td>
			<td class="tdgrey2"><input value="" type="text" id="Amount"
				name="Amount" class="inputText" verify="小计|NotNull"
				></td>
		</tr>
		<tr>
			<td height="25" align="right" class="tdgrey1">积分：</td>
			<td class="tdgrey2"><input value="" type="text" id="Score"
				name="Score" class="inputText" readonly="readonly"></td>
		</tr>
	</table>
	</form>
</sky:init>
</body>
</html>
