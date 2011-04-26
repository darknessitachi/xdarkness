<%
	/*
	功能：支付宝输入页面，这个页面可以集成到商户网站，实际参数的传入可以根据业务决定
	接口名称：标准即时到账接口
	版本：2.0
	日期：2008-12-31
	作者：支付宝公司销售部技术支持团队
	联系：0571-26888888
	版权：支付宝公司
	 */
%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.alipay.util.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>支付宝支付--即时支付接口</title>
		<script language="JavaScript">
		function alipayClick(){
			for(var   i=0;   i<alipay_direct.paymethod_1.length;   i++)     
      		{   
          	  if(alipay_direct.paymethod_1[i].checked){
			     alipay_direct.paymethod.value=alipay_direct.paymethod_1[i].value
			  }   
      		} 
			for(var   i=0;   i<alipay_direct.defaultbank_1.length;   i++)     
      		{   
          	  if(alipay_direct.defaultbank_1[i].checked){
			     alipay_direct.defaultbank.value=alipay_direct.defaultbank_1[i].value
			  }   
      		} 
		}
	    </script>
	</head>
	<body>

		<form name="alipay_direct" action="alipay_pay.jsp" method="post" target="_blank">
			<table width="100%" border="0">
				<tr>
					<th colspan="2" scope="col">
						<a href="http://www.alipay.com" target="_blank"><img
								src="images/logo.gif" border="0" align='left' /> </a>
					</th>
				</tr>
                <tr>
    <td colspan="2" height="2" bgcolor="#ff7300"></td>
  </tr>
				<tr>
					<td align="right">
						商家订单号：
					</td>
					<td>
						<%
							UtilDate date = new UtilDate(); //调取支付宝工具类生成订单号
							String get_order = date.getOrderNum();
						%>
						<input type="text" name="out_orderNo" id="out_orderNo"
							value="<%=get_order%>" readonly/>
					</td>
				</tr>
                <tr>
					<td width="16%" align="right">商品名称：					</td>
			  <td width="84%">
						<input type="text" name="goodName" id="goodName">
					</td>
				</tr>
                <tr>
					<td width="16%" align="right">商品描述：					</td>
			  <td width="84%">
						<input type="text" name="goodBody" id="goodBody">
					</td>
				</tr>
                <tr>
					<td width="16%" align="right">订单总价：					</td>
			  <td width="84%">
						<input type="text" name="goodPrice" id="goodPrice">
					</td>
				</tr>
                <tr>
					<td width="16%" align="right">商品展示地址：					</td>
			  <td width="84%">
						<input type="text" name="show_good_url" id="show_good_url"> <span style="color:#ff7300;">*</span>例如：根据集成的网站而定，例如：http://wow.alipay.com
					</td>
				</tr>
               <tr>
					<td width="16%" align="right">选择默认支付选项卡：					</td>
			  <td width="84%">
              <table>
              	<tr>
                  <td><input type="radio" name="paymethod_1" value="directPay" checked>支付宝余额支付</td> 
                  <td><input type="radio" name="paymethod_1" value="bankPay">网银支付</td> 
                  <td><input type="radio" name="paymethod_1" value="cartoon">支付宝卡通支付</td> 
                </tr>
              </table>
                        <input type="hidden" name="paymethod" id="paymethod"/>
					</td>
				</tr>
                
                <tr>
					<td width="16%" align="right">选择默认银行：					</td>
			  <td width="84%">
              			<table>
                        <tr>
                        <td><input type="radio" name="defaultbank_1" value="CMB" checked><img src="images/icon_zsyh_s.gif" border="0"/></td>
                        <td><input type="radio" name="defaultbank_1" value="ICBCB2C"><img src="images/icon_zggsyh_s.gif" border="0"/> </td>
                        <td><input type="radio" name="defaultbank_1" value="CCB"><img src="images/icon_ccb_s.gif" border="0"/></td>
                        </tr>
                         <tr>
                        <td><input type="radio" name="defaultbank_1" value="ABC"><img src="images/icon_abc_s.gif" border="0"/></td>
                        <td><input type="radio" name="defaultbank_1" value="SPDB"><img src="images/icon_spdb_s.gif" border="0"/></td>
                        <td><input type="radio" name="defaultbank_1" value="POSTGC"><img src="images/POSTGC.jpg" border="0"/></td>
                        </tr>
                         <tr>
                        <td><input type="radio" name="defaultbank_1" value="CITIC"><img src="images/icon_itic_s.gif" border="0"/> </td>
                        <td> <input type="radio" name="defaultbank_1" value="CIB"><img src='images/index_38.gif' border="0"/></td>
                        <td><input type="radio" name="defaultbank_1" value="GDB"><img src="images/icon_gdb_s.gif" border="0"/></td>
                        </tr>
                         <tr>
                        <td><input type="radio" name="defaultbank_1" value="SDB"><img src="images/icon_sdb_s.gif" border="0"/></td>
                        <td><input type="radio" name="defaultbank_1" value="CMBC"><img src="images/icon_cmbc_s.gif" border="0"/></td>
                        <td><input type="radio" name="defaultbank_1" value="COMM"><img src="images/icon_comm_s.gif" border="0"/></td>
                        </tr>
                        </table>
                        <input type="hidden" name="defaultbank" id="defaultbank"/>
					</td>
				</tr>
                
				<tr>
					<td align="right">
						操作：
					</td>
					<td>
						<input type="submit" name="submit" value="支付宝即时支付" onClick="alipayClick()" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
