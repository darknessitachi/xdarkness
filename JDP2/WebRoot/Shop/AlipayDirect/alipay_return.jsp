<%
	/*
	名称：付完款后跳转的页面
	功能：同服务器返回功能，但容易出现掉单.
	版本：2.0
	日期：2008-12-31
	作者：支付宝公司销售部技术支持团队
	联系：0571-26888888
	版权：支付宝公司
	 */
%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.alipay.util.*"%>
<%@ page import="com.alipay.config.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title></title>
	</head>
	<body>
		<%		
			String partner = AlipayConfig.partnerID; //支付宝合作伙伴id (账户内提取)
			String privateKey = AlipayConfig.key; //支付宝安全校验码(账户内提取)
			//**********************************************************************************
			//如果您服务器不支持https交互，可以使用http的验证查询地址
			/*注意下面的注释，如果在测试的时候导致response等于空值的情况，请将下面一个注释，打开上面一个验证连接，另外检查本地端口，
			请挡开80或者443端口*/
			//String alipayNotifyURL = "https://www.alipay.com/cooperate/gateway.do?service=notify_verify"
			String alipayNotifyURL = "http://notify.alipay.com/trade/notify_query.do?"
					+ "partner="
					+ partner
					+ "&notify_id="
					+ request.getParameter("notify_id");
			//**********************************************************************************
			String sign = request.getParameter("sign");
			//获取支付宝ATN返回结果，true是正确的订单信息，false 是无效的
			String responseTxt = CheckURL.check(alipayNotifyURL);

			Map params = new HashMap();
			//获得POST 过来参数设置到新的params中
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter
					.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化（现在已经使用）
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8");

				params.put(name, valueStr);
			}

			String mysign = com.alipay.util.SignatureHelper_return.sign(params,
					privateKey);

			if (mysign.equals(request.getParameter("sign"))
					&& responseTxt.equals("true")) {
				//即时到账中需要判断各个交易的状态----WAIT_BUYER_PAY -->TRADE_FINISHED（可以参考notify）
				//在这里可以写入数据处理,
				if (request.getParameter("trade_status").equals(
						"TRADE_FINISHED")||request.getParameter("trade_status").equals(
						"TRADE_SUCCESS")) {
					//以下输出测试时候用，可以删除
					String get_order = request.getParameter("out_trade_no");
					String get_total_fee = request.getParameter("total_fee");
					String get_subject = new String(request.getParameter(
							"subject").getBytes("ISO-8859-1"), "UTF-8");
					String get_body = new String(request.getParameter("body")
							.getBytes("ISO-8859-1"), "UTF-8");
//如果您申请了支付宝的购物卷功能，请在返回的信息里面不要做金额的判断，否则会出现校验通不过，出现调单。如果您需要获取买家所使用购物卷的金额,
//请获取返回信息的这个字段discount的值，取绝对值，就是买家付款优惠的金额。即 原订单的总金额=买家付款返回的金额total_fee +|discount|.


		%>
		<style type="text/css">
<!--
.style1 {
	color: #FF0000
}
-->
</style>

		<table width="450" height="300" border="0" align="center">
			<tr>
				<td valign="top" background="images/AliPay.gif">
					<table width="444" border="0">
						<tr>
							<td height="42">
								<SPAN
									style="FONT-SIZE: 14px; COLOR: #FF6600; FONT-FAMILY: Verdana">您的订单号为:<B
									style="FONT-SIZE: 18px"> <%=get_order%></b>
								</span>
							</td>
						</tr>
						<tr>
							<td height="47">
								<SPAN
									style="FONT-SIZE: 14px; COLOR: #FF6600; FONT-FAMILY: Verdana">订单价格:<B
									style="FONT-SIZE: 18px"> <%=get_total_fee%></b>
								</span>
							</td>
						</tr>
						<tr>
							<td height="47">
								<SPAN
									style="FONT-SIZE: 14px; COLOR: #FF6600; FONT-FAMILY: Verdana">商品名称:<B
									style="FONT-SIZE: 18px"> <%=get_subject%></b>
								</span>
							</td>
						</tr>
						<tr>
							<td height="47">
								<SPAN
									style="FONT-SIZE: 14px; COLOR: #FF6600; FONT-FAMILY: Verdana">商品描述:<B
									style="FONT-SIZE: 18px"> <%=get_body%></b>
								</span>
							</td>
						</tr>
						<tr>
							<td height="47">
								<div align="center">
									<span class="style1"><font size="+3">付款成功!</font>
									</span>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<%
			}

			} else {
				//out.println("支付失败");
				//打印，收到消息比对sign的计算结果和传递来的sign是否匹配
				//out.println(mysign + "--------------------" + sign + "<br>");
				//out.println("responseTxt=" + responseTxt + "<br>");
		%>
		<style type="text/css">
<!--
.style1 {
	color: #FF0000
}
-->
</style>

		<table width="450" height="300" border="0" align="center">
			<tr>
				<td valign="top" background="images/AliPay.gif">
					<table width="444" border="0">
						<tr>
							<td height="42">
								<SPAN
									style="FONT-SIZE: 14px; COLOR: #FF6600; FONT-FAMILY: Verdana">mysign=<B
									style="FONT-SIZE: 18px"> <%=mysign%></b>
								</span>
							</td>
						</tr>
						<tr>
							<td height="47">
								<SPAN
									style="FONT-SIZE: 14px; COLOR: #FF6600; FONT-FAMILY: Verdana">sign=<B
									style="FONT-SIZE: 18px"> <%=sign%></b>
								</span>
							</td>
						</tr>
						<tr>
							<td height="47">
								<SPAN
									style="FONT-SIZE: 14px; COLOR: #FF6600; FONT-FAMILY: Verdana">responseTxt=<B
									style="FONT-SIZE: 18px"> <%=responseTxt%></b>
								</span>
							</td>
						</tr>
						<tr>
							<td height="47">
								<div align="center">
									<span class="style1"><font size="+3">付款失败!</font>
									</span>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<%
			}
		%>




	</body>
</html>
