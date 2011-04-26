package com.xdarkness.member;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.framework.extend.ExtendManager;
import com.xdarkness.framework.jaf.Ajax;
import com.xdarkness.framework.jaf.Cookies;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.ServletUtil;

public class Login extends Ajax {
	private static ArrayList wrongList = new ArrayList();

	public static Mapx initSiteLinks(Mapx params) {
		String SiteID = params.getString("SiteID");
		String UserName = User.getUserName();
		String SiteLinks = "";
		if (XString.isEmpty(SiteID)) {
			if (XString.isEmpty(UserName)) {
				params.put("SiteLinks", "获取网站链接失败");
				return params;
			}
			Member member = new Member(UserName);
			member.fill();
			SiteID = member.getSiteID()+"";
		}

		if ((User.isLogin()) && (User.isMember())) {
			params.put("display", "none");
		}
		String SiteAlias = SiteUtil.getAlias(SiteID);
		String Path = Config.getContextPath() + Config.getValue("UploadDir")
				+ "/" + SiteAlias + "/";
		DataTable dt = new QueryBuilder(
				"select * from zccatalog where Type = 1 and ParentID = 0 and SiteID = ?",
				SiteID).executePagedDataTable(10, 0);
		SiteLinks = SiteLinks + "<a href='" + Path
				+ "'>首页</a>&nbsp;&nbsp;&nbsp;";

		if ((dt != null) && (dt.getRowCount() > 0)) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				SiteLinks = SiteLinks + "<a href='" + Path
						+ dt.getString(i, "URL") + "'>"
						+ dt.getString(i, "Name") + "</a>&nbsp;&nbsp;&nbsp;";
			}
		}
		params.put("SiteID", SiteID);
		params.put("SiteLinks", SiteLinks);
		return params;
	}

	public void doLogin() {
		Object authCode = User.getValue("_SKY_AUTHKEY");
		String userName = $V("UserName");
		if ((wrongList.contains(userName))
				&& ((authCode == null) || (!authCode.equals($V("VerifyCode"))))) {
			this.response.setStatus(0);
			this.response.setMessage("验证码输入错误");
			return;
		}

		String passWord = $V("PassWord");

		int cookieTime = 0;
		if (XString.isNotEmpty($V("CookieTime")))
			cookieTime = Integer.parseInt($V("CookieTime"));
		else {
			cookieTime = 1800;
		}

		Member member = new Member(userName);
		if (member.isExists()) {
			if (!member.fill()) {
				this.response.setLogInfo(0, "用户名或密码错误，请重新输入");
			}
			if (member.checkPassWord(passWord)) {
				String VerifyCookie = XString.md5Hex(member.getPassword()
						.substring(0, 10)
						+ System.currentTimeMillis());
				this.cookie.setCookie("VerifyCookie", VerifyCookie, cookieTime);
				this.cookie.setCookie("UserName", userName, cookieTime);
				User.setManager(false);
				User.setLogin(true);
				User.setUserName(userName);
				User.setRealName(member.getName());
				User.setType(member.getType());
				User.setMember(true);
				User.setValue("SiteID", member.getSiteID());
				User.setValue("UserName", member.getUserName());
				User.setValue("Type", member.getType());
				ExtendManager.executeAll("Member.Login",
						new Object[] { member });
				if (XString.isNotEmpty(member.getName()))
					User.setValue("Name", member.getName());
				else {
					User.setValue("Name", member.getUserName());
				}
				member.setLoginMD5(VerifyCookie);
				member.setMemberLevel(new QueryBuilder(new StringBuffer(
						"select ID from ZDMemberLevel where Score <= ").append(
						member.getScore()).append(" order by Score desc")
						.toString()).executeOneValue()+"");

				member.setLastLoginIP(this.request.getClientIP());
				member.setLastLoginTime(new Date());
				member.update();
				this.response.setLogInfo(1, "登录成功，欢迎您 " + userName);
				this.response.put("UserName", userName);
				this.response.put("Name", member.getName());
				this.response.put("Type", member.getType());
				synchronized (wrongList) {
					wrongList.remove(userName);
				}
			}
			this.response.setLogInfo(0, "用户名或密码错误，请重新输入");
			if (!wrongList.contains(userName))
				synchronized (wrongList) {
					if (wrongList.size() > 20000) {
						wrongList.clear();
					}
					wrongList.add(userName);
				}
		} else {
			this.response.setLogInfo(0, "用户名不存在，请重新输入");
		}
	}

	public static void checkAndLogin(HttpServletRequest request) {
		String VerifyCookie = ServletUtil.getCookieValue(request,
				"VerifyCookie");
		String UserName = ServletUtil.getCookieValue(request, "UserName");
		if ((!User.isManager()) && (!User.isLogin())
				&& (XString.isNotEmpty(UserName))) {
			Member member = new Member(UserName);
			if ((member.fill())
					&& (XString.isNotEmpty(member.getLoginMD5()))
					&& (member.getLoginMD5().equalsIgnoreCase(VerifyCookie))) {
				User.setManager(false);
				User.setLogin(true);
				User.setUserName(member.getUserName());
				User.setRealName(member.getName());
				User.setType(member.getType());
				User.setMember(true);
				User.setValue("SiteID", member.getSiteID());
				User.setValue("UserName", member.getUserName());
				User.setValue("Type", member.getType());

				ExtendManager.executeAll("Member.Login",
						new Object[] { member });

				if (XString.isNotEmpty(member.getName()))
					User.setValue("Name", member.getName());
				else {
					User.setValue("Name", member.getUserName());
				}
				member.setLoginMD5(VerifyCookie);
				member.setLastLoginIP(request.getRemoteAddr());
				member.setLastLoginTime(new Date());
				member.update();
			}
		}
	}

	public void loginComment(HttpServletRequest request,
			HttpServletResponse response, String userName, String passWord) {
		Member member = new Member(userName);
		if (member.isExists()) {
			member.fill();
			if (member.checkPassWord(passWord)) {
				String VerifyCookie = XString.md5Hex(member.getPassword()
						.substring(0, 10)
						+ System.currentTimeMillis());
				this.cookie = new Cookies(request);
				this.cookie.setCookie("VerifyCookie", VerifyCookie, 0);
				this.cookie.setCookie("UserName", userName, 0);
				this.cookie.writeToResponse(request, response);
				User.setManager(false);
				User.setLogin(true);
				User.setUserName(userName);
				User.setRealName(member.getName());
				User.setType(member.getType());
				User.setMember(true);
				User.setValue("SiteID", member.getSiteID());
				User.setValue("UserName", member.getUserName());
				User.setValue("Type", member.getType());

				ExtendManager.executeAll("Member.Login",
						new Object[] { member });

				if (XString.isNotEmpty(member.getName()))
					User.setValue("Name", member.getName());
				else {
					User.setValue("Name", member.getUserName());
				}
				member.setLoginMD5(VerifyCookie);
				member.setMemberLevel(new QueryBuilder(new StringBuffer(
						"select ID from ZDMemberLevel where Score <= ").append(
						member.getScore()).append(" order by Score desc")
						.toString()).executeOneValue()+"");

				member.setLastLoginIP(request.getRemoteAddr());
				member.setLastLoginTime(new Date());
				member.update();
			}
		}
	}
}

/*
 * com.xdarkness.member.Login JD-Core Version: 0.6.0
 */