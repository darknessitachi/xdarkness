<%
	/*
	功能：设置支付宝所需参数
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
<%@ page import="java.util.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>支付宝支付</title>
	</head>

	<%
		UtilDate date=new UtilDate();
		String paygateway = "https://www.alipay.com/cooperate/gateway.do?"; //支付接口（不可以修改）
		String service = "create_direct_pay_by_user";//快速付款交易服务（不可以修改）
		String sign_type = "MD5";//文件加密机制（不可以修改）
		String out_trade_no = request.getParameter("out_orderNo").trim();//商户网站订单（也就是外部订单号，是通过客户网站传给支付宝，不可以重复）
		String input_charset = "";//页面编码（不可以修改）
		//partner和key提取方法：登陆签约支付宝账户--->点击“商家服务”就可以看到
		String partner = ""; //支付宝合作伙伴id (账户内提取)
		String key = ""; //支付宝安全校验码(账户内提取)
		String body = request.getParameter("goodBody").trim(); //商品阿描述，推荐格式：商品名称（订单编号：订单编号）
		String total_fee = request.getParameter("goodPrice").trim(); //订单总价
		String payment_type = "1";//支付宝类型.1代表商品购买（目前填写1即可，不可以修改）
		String seller_email = ""; //卖家支付宝帐户,例如：gwl25@126.com
		String subject = request.getParameter("goodName").trim()+ "*商品订单号：" + out_trade_no; //商品名称
		String show_url = request.getParameter("show_good_url").trim();     //根据集成的网站而定，例如：http://wow.alipay.com
		
		String path = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort();// "http://190.10.2.33:7001/"; 

		String notify_url = path + "/jsp_direct_gbk/alipay_notify.jsp"; //通知接收URL(本地测试时，服务器返回无法测试)
		String return_url = path + "/jsp_direct_gbk/alipay_return.jsp"; //支付完成后跳转返回的网址URL
		//注意以上两个地址 要用 http://格式的完整路径
		/*以下两个参数paymethod和defaultbank可以选择不使用，如果不使用需要注销，并在Payment类的方法中也要注销*/
		String paymethod = request.getParameter("paymethod").trim();//赋值:bankPay(网银);cartoon(卡通); directPay(余额)。
		String defaultbank = request.getParameter("defaultbank").trim();
		//ICBCB2C	中国工商银行
		//CMB		招商银行
		//CCB		中国建设银行
		//ABC		中国农业银行
		//SPDB		上海浦东发展银行
		//SPDBB2B	上海浦东发展银行(B2B)
		//CIB	兴业银行
		//GDB		广东发展银行
		//SDB	深圳发展银行
		//CMBC		中国民生银行
		//COMM		交通银行
		//POSTGC		邮政储蓄银行
		//CITIC		中信银行
		//CCBVISA	建行VISA
		//VISA		VISA
		
		String ItemUrl = Payment.CreateUrlDirect(paygateway, service, sign_type,
				out_trade_no, input_charset, partner, key, show_url, body,
				total_fee, payment_type, seller_email, subject, notify_url,
				return_url, paymethod, defaultbank);
	%>
	
	<body bgcolor="#FF9900">
     <font color="#FF0000" size="+1">支付宝即时支付入口：</font><a href="<%=ItemUrl%>"><img src="images/alipay_1.gif" border="0">
	</a>
    <br>
    <br>
    您可以检查提交给支付宝的URL是否存在空值
    <br>
    <br>
    生成的URL：<%=ItemUrl%>
	</body>
</html>
