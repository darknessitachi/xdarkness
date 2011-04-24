<%@page import="com.xdarkness.framework.Config"%>
<%@page import="com.xdarkness.framework.User"%>
<%@page import="com.xdarkness.framework.utility.Mapx"%>
<%@page import="com.xdarkness.framework.data.Transaction"%>
<%@page import="com.xdarkness.framework.utility.ServletUtil"%>
<%@page import="com.xdarkness.framework.utility.StringUtil"%>
<%
 	String GoodsID = request.getParameter("GoodsID");
	String buyNowFlag = request.getParameter("BuyNowFlag");
	Mapx map = new Mapx();
	String shopCartCookie = ServletUtil.getCookieValue(request, "ShopCart");
	if(StringUtil.isNotEmpty(shopCartCookie)) {
		String[] goodsArray = shopCartCookie.split("X");
		for(int i = 0; i < goodsArray.length; i++) {
			map.put(goodsArray[i].split("-")[0], goodsArray[i].split("-")[1]);
		}
	}
	if(map.containsKey(GoodsID)) {
		map.put(GoodsID, map.getInt(GoodsID) + 1);
	} else {
		map.put(GoodsID, 1);
	}
	StringBuffer sb = new StringBuffer();
	for(int i = 0; i < map.keyArray().length; i++) {
		sb.append(map.keyArray()[i]);
		sb.append("-");
		sb.append(map.getString(map.keyArray()[i]));
		if(i < (map.keyArray().length - 1)) {
			sb.append("X");
		}
	}
	ServletUtil.setCookieValue(request, response, "ShopCart", sb.toString());
	if("1".equals(buyNowFlag)) {
		response.sendRedirect("ShopCart.jsp?SiteID=" + request.getParameter("SiteID"));
	} else {
		response.getWriter().write("N&成功加入购物车!");
	}
%>