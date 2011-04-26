
<%
	/*
	名称：付款过程中服务器通知页面
	功能：服务器通知返回，不会出现掉单情况，推荐使用
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
		/*乱码解决，这段代码在出现乱码时使用,但是不一定能解决所有的问题，所以建议写过滤器实现编码控制。
		如果mysign和sign不相等也可以使用这段代码转化*/
		//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "UTF-8"); //乱码解决
		params.put(name, valueStr);
	}

	String mysign = com.alipay.util.SignatureHelper.sign(params,
			privateKey);

	if (mysign.equals(request.getParameter("sign"))
			&& responseTxt.equals("true")) {
		/*可以在不同状态下获取订单信息，操作商户数据库使数据同步*/
		if (request.getParameter("trade_status") == "WAIT_BUYER_PAY") {
			//等待买家付款
			//在这里可以写入数据处理
			out.println("success"); //注意一定要返回给支付宝一个成功的信息
		} else if (request.getParameter("trade_status") == "TRADE_FINISHED"
				|| request.getParameter("trade_status").equals(
						"TRADE_SUCCESS")) {

			//在这里可以写入数据处理,
			out.println("success"); //注意一定要返回给支付宝一个成功的信息

//如果您申请了支付宝的购物卷功能，请在返回的信息里面不要做金额的判断，否则会出现校验通不过，出现调单。如果您需要获取买家所使用购物卷的金额,
//请获取返回信息的这个字段discount的值，取绝对值，就是买家付款优惠的金额。即 原订单的总金额=买家付款返回的金额total_fee +|discount|.


		}
	} else {
		out.println("fail" + "<br>");
		//打印，收到消息比对sign的计算结果和传递来的sign是否匹配
		out.println(mysign + "--------------------"
				+ request.getParameter("sign") + "<br>");
	}
%>
