<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.alipay.util.*"%>
<%
	String partner = ""; //partner合作伙伴id（必须填写）
	String privateKey = ""; //partner 的对应交易安全校验码（必须填写）
	String alipayNotifyURL = "http://notify.alipay.com/trade/notify_query.do?" + "partner=" + partner + "&notify_id=" + request.getParameter("notify_id");

	//获取支付宝ATN返回结果，true是正确的订单信息，false 是无效的
	String responseTxt = CheckURL.check(alipayNotifyURL);

	Map params = new HashMap();
	//获得POST 过来参数设置到新的params中
	Map requestParams = request.getParameterMap();
	for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
		String name = (String) iter.next();
		String[] values = (String[]) requestParams.get(name);
		String valueStr = "";
		for (int i = 0; i < values.length; i++) {
			valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
		}
		params.put(name, valueStr);
	}

	String mysign = com.alipay.util.SignatureHelper.sign(params, privateKey);

	if (mysign.equals(request.getParameter("sign")) && responseTxt.equals("true")) {

		out.println("success");

		//如果您申请了支付宝的购物卷功能，请在返回的信息里面不要做金额的判断，否则会出现校验通不过，出现调单。如果您需要获取买家所使用购物卷的金额,
		//请获取返回信息的这个字段discount的值，取绝对值，就是买家付款优惠的金额。即 原订单的总金额=买家付款返回的金额total_fee +|discount|.

	} else {
		out.println("fail");
	}
%>
