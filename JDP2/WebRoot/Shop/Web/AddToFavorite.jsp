<%@page import="com.xdarkness.framework.utility.StringUtil"%>
<%@page import="com.xdarkness.framework.data.QueryBuilder"%>
<%@page import="com.xdarkness.framework.User"%>
<%@page import="com.xdarkness.framework.utility.Mapx"%>
<%@page import="com.xdarkness.schema.ZSFavoriteSchema"%>
<%@page import="java.util.Date"%>
<%
String flag = "N";
if(!User.isMember()||!User.isLogin()){
	String cSiteID = request.getParameter("SiteID");
	if(StringUtil.isEmpty(cSiteID)){
		cSiteID = new QueryBuilder("select ID from ZCSite order by AddTime desc").executeOneValue()+"";
	}
	response.getWriter().write(flag + "&" + "收藏夹需要登录后才能使用!");
} else if(User.isManager()) {
	response.getWriter().write(flag + "&" + "收藏夹需要前台用户登录后才能使用!");
} else {
	String GoodsID = request.getParameter("GoodsID");
	String SiteID = request.getParameter("SiteID");
	String PriceNote = request.getParameter("PriceNote");
	ZSFavoriteSchema fav = new ZSFavoriteSchema();
	fav.setGoodsID(GoodsID);
	if(User.isManager()) {
		fav.setUserName(User.getUserName());
	} else {
		fav.setUserName(User.getValue("UserName").toString());
	}
	fav.setSiteID(SiteID);
	if(fav.fill()) {
		if(fav.getPriceNoteFlag().equals("N") && PriceNote.equals("Y")) {
			fav.setPriceNoteFlag(PriceNote);
			if (fav.update()) {
				flag = "Y";
				response.getWriter().write(flag + "&" + "操作成功!");
			} else {
				response.getWriter().write(flag + "&操作失败！请尝试刷新页面重新设置！");
			}
		} else {
			response.getWriter().write(flag + "&" + "该商品已经存在您的收藏夹中!");
		}
	} else {
		if(User.isManager()) {
			fav.setAddUser(User.getUserName());
		} else {
			fav.setAddUser(User.getValue("UserName").toString());
		}
		fav.setAddTime(new Date());
		fav.setPriceNoteFlag(PriceNote);
		if (fav.insert()) {
			flag = "Y";
			response.getWriter().write(flag + "&" + "操作成功!");
		} else {
			response.getWriter().write(flag + "&操作失败！请尝试刷新页面重新收藏！");
		}
	}
}
	
%>