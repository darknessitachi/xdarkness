<%@page import="java.util.Date"%>
<%@page import="com.xdarkness.framework.data.QueryBuilder"%>
<%@page import="com.xdarkness.framework.extend.ExtendManager"%>
<%@page import="com.xdarkness.framework.utility.StringUtil"%>
<%@page import="com.xdarkness.member.Member"%>
<%@page import="com.xdarkness.framework.User"%>
<%
	String flag = "Y";

	String userName = request.getParameter("UserName");
	String passWord = request.getParameter("PassWord");
	String siteID = request.getParameter("SiteID");
	Member member = new Member(userName, siteID);
	if (member.isExistsCurrentSite()) {
		member.fill();
		if (member.checkPassWord(passWord)) {
			String VerifyCookie = StringUtil.md5Hex(member.getPassword().substring(0, 10)
					+ (System.currentTimeMillis() + ""));
			//Cookie.setCookie("VerifyCookie", VerifyCookie, CookieTime);
			//Cookie.setCookie("UserName", userName, CookieTime);
			User.setManager(false);
			User.setLogin(true);
			User.setUserName(userName);
			User.setRealName(member.getName());
			User.setType(member.getType());
			User.setMember(true);
			User.setValue("SiteID", member.getSiteID() + "");
			User.setValue("UserName", member.getUserName() + "");
			User.setValue("Type", member.getType());
			ExtendManager.executeAll("Member.Login", new Object[] { member });// 调用扩展
			if (StringUtil.isNotEmpty(member.getName())) {
				User.setValue("Name", member.getName());
			} else {
				User.setValue("Name", member.getUserName());
			}
			member.setLoginMD5(VerifyCookie);
			member.setMemberLevel(new QueryBuilder("select ID from ZDMemberLevel where Score <= "
					+ member.getScore() + " order by Score desc").executeOneValue()
					+ "");
			member.setLastLoginTime(new Date());
			member.update();
			
			StringBuffer sb = new StringBuffer();
			sb.append("<div id='loginMenu2'>");
			sb.append("<b>欢迎：" + User.getRealName() + "</b>&nbsp;");
			sb.append("[<a href='javascript:void(0);' onClick='goMemberCenter();'>会员中心</a>]&nbsp;");
			//sb.append("[<a href='javascript:void(0);' onClick='goShopCart();'>购物车</a>]&nbsp;");
			//sb.append("[<a href='javascript:void(0);' onClick='goFavorite();'>收藏夹</a>]&nbsp;");
			sb.append("[<a href='javascript:void(0);' onClick='doLogout();'>退出</a>]</form>");
			sb.append("</div>");
			
			response.getWriter().write(flag + "&" + sb.toString());
		} else {
			flag = "N";
			response.getWriter().write(flag + "&会员名或密码错误，请重新输入");
		}
	} else {
		flag = "N";
		response.getWriter().write(flag + "&会员名不存在，请重新输入");
	}
%>