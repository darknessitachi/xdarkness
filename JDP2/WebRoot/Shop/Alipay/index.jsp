<%@page import="com.alipay.util.Payment"%>
<%
	long time = System.currentTimeMillis();//当前系统时间

	String paygateway = "https://www.alipay.com/cooperate/gateway.do?"; //'支付接口
	String service = "trade_create_by_buyer";//	create_direct_pay_by_user
	String sign_type = "MD5";
	String out_trade_no = time + ""; //商户网站订单
	String input_charset = "UTF-8";
	String partner = "2088002462262822"; //支付宝合作伙伴id (账户内提取)
	String key = "2615npzvkstmd5ylrcdnqqfe9qe0hp92"; //支付宝安全校验码(账户内提取)
	String seller_email = "lei.huang.ray@hotmail.com"; //卖家支付宝帐户,例如：gwl25@126.com
	//******以上是账户信息，以下是商品信息**************************
	String body = "阿"; //商品描述，推荐格式：商品名称（订单编号：订单编号）
	String subject = "test"; //商品名称
	String price = "0.01"; //订单总价
	String quantity = "1";
	String show_url = "www.zving.com";
	String payment_type = "1";
	String discount = "0";
	//******物流信息和支付宝通知，一般商城不需要通知，请删除此参数，并且在payment.java里面相应删除参数********
	String logistics_type = "";
	String logistics_fee = "";
	String logistics_payment = "";
	String notify_url = "http://localhost:8080/ZCMS/Shop/MemberOrder.jsp";//通知接收URL
	String return_url = "http://localhost:8080/ZCMS/Shop/MemberOrder.jsp"; //支付完成后跳转返回的网址URL

	String ItemUrl = Payment.CreateUrlShiwu(paygateway, service, sign_type, out_trade_no, input_charset, partner, key, seller_email, body, subject, price, quantity, show_url, payment_type, discount, logistics_type, logistics_fee, logistics_payment, return_url, notify_url);
	//notify_url需要的话请把这个参数加上到上面createurl
%>

<a href="<%=ItemUrl%>"> <img src="images/alipay_bwrx.gif" border="0">支付宝付款</a>
