package com.xdarkness.platform.page;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abigdreamer.java.net.User;
import com.abigdreamer.java.net.jaf.Ajax;
import com.abigdreamer.java.net.jaf.WebConfig;
import com.abigdreamer.java.net.orm.data.DataCollection;
import com.abigdreamer.java.net.orm.data.DataRow;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.Filter;
import com.abigdreamer.java.net.util.LogUtil;
import com.abigdreamer.java.net.util.XString;
import com.xdarkness.platform.Priv;
import com.zving.schema.ZDUserSchema;
import com.zving.schema.ZDUserSet;

/**
 * @author Darkness
 * 
 * Copyright (c) 2009 by Darkness
 * 
 * @date 2010-10-26 下午03:04:17
 * @version 1.0
 */
public class LoginPage extends Ajax {
	private static ArrayList wrongList = new ArrayList();

	public static void ssoLogin(HttpServletRequest request,
			HttpServletResponse response, String username) {
		if (username == null) {
			return;
		}
		ZDUserSchema user = new ZDUserSchema();
		user.setUserName(username);
		ZDUserSet userSet = user.query();

		if ((!WebConfig.isAllowLogin) && (!username.equalsIgnoreCase("admin"))) {
			UserLogPage.log("Log", "Login", "临时禁止登录.用户名：" + username, request
					.getRemoteAddr(), username);
			return;
		}

		if ((userSet == null) || (userSet.size() < 1)) {
			UserLogPage.log("Log", "Login", "SSO登陆失败.用户名：" + username, request
					.getRemoteAddr(), username);
		} else {
			user = userSet.get(0);
			User.setUserName(user.getUserName());
			User.setRealName(user.getRealName());
			User.setBranchInnerCode(user.getBranchInnerCode());
			User.setType(user.getType());
			User.setValue("Prop1", user.getProp1());
			User.setValue("Prop2", user.getProp2());
			User.setValue("Prop3", user.getProp3());
			User.setValue("Prop4", user.getProp4());
			User.setManager(true);
			User.setLogin(true);

			UserLogPage.log("Log", "Login", "登录成功", request.getRemoteAddr());

			DataTable dt = new QueryBuilder(
					"select name,id from zcsite order by BranchInnerCode ,orderflag ,id")
					.executeDataTable();
			dt = dt.filter(new Filter() {
				public boolean filter(Object obj) {
					DataRow dr = (DataRow) obj;
					return Priv.getPriv(User.getUserName(), "site", dr
							.getString("ID"), "site_browse");
				}
			});
			if (dt.getRowCount() > 0)
				ApplicationPage.setCurrentSiteID(dt.getString(0, 1));
			else {
				ApplicationPage.setCurrentSiteID("");
			}
			try {
				String path = request.getParameter("Referer");
				LogUtil.info("SSOLogin,Referer:" + path);
				if (XString.isNotEmpty(path)) {
					if ((XString.isNotEmpty(request.getParameter("t")))
							&& (!"null".equalsIgnoreCase(request
									.getParameter("t"))))
						response.sendRedirect(path + "?t="
								+ request.getParameter("t"));
					else
						response.sendRedirect(path);
				} else
					response.sendRedirect("Application.jsp");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void submit() {
		String userName = $V("UserName").toLowerCase();
		if (wrongList.contains(userName)) {
			Object authCode = User.getValue("_SKY_AUTHKEY");
			if ((authCode == null) || (!authCode.equals($V("VerifyCode")))) {
				this.response.setStatus(0);
				this.response.setMessage("验证码输入错误");
				return;
			}
		}
		String Password = XString.md5Hex($V("Password"));
		ZDUserSchema user = new ZDUserSchema();
		user.setUserName(userName);
		ZDUserSet userSet = user.query();

		if ((!WebConfig.isAllowLogin)
				&& (!user.getUserName().equalsIgnoreCase("admin"))) {
			UserLogPage.log("Log", "Login", "临时禁止登录.用户名" + $V("UserName"),
					this.request.getClientIP(), $V("UserName"));
			this.response.setStatus(0);
			this.response.setMessage("临时禁止登录，请与系统管理员联系!");
			return;
		}

		if ((userSet == null) || (userSet.size() < 1)) {
			UserLogPage.log("Log", "Login", "登陆失败.用户名：" + $V("UserName"),
					this.request.getClientIP(), $V("UserName"));
			this.response.setStatus(0);
			this.response.setMessage("用户名或密码输入错误");
		} else {
			user = userSet.get(0);
			if (!user.getPassword().equalsIgnoreCase(Password)) {
				this.response.setStatus(0);
				this.response.setMessage("用户名或密码输入错误");
				if (!wrongList.contains(userName)) {
					synchronized (wrongList) {
						if (wrongList.size() > 20000) {
							wrongList.clear();
						}
						wrongList.add(userName);
					}
				}
				return;
			}
			if ((!"admin".equalsIgnoreCase(user.getUserName()))
					&& ("S".equals(user.getStatus()))) {
				UserLogPage.log("Log", "Login", "登陆失败.用户名：" + $V("UserName")
						+ "已停用", this.request.getClientIP(), $V("UserName"));

				this.response.setStatus(0);
				this.response.setMessage("该用户处于停用状态，请联系管理员！");
				return;
			}
			User.setUserName(user.getUserName());
			User.setRealName(user.getRealName());
			User.setBranchInnerCode(user.getBranchInnerCode());
			User.setType(user.getType());
			User.setValue("Prop1", user.getProp1());
			User.setValue("Prop2", user.getProp2());
			User.setValue("Prop3", user.getProp3());
			User.setValue("Prop4", user.getProp4());
			User.setManager(true);
			User.setLogin(true);

			UserLogPage.log("Log", "Login", "登陆成功", this.request.getClientIP());

			DataTable dt = new QueryBuilder(
					"select name,id from zcsite order by orderflag")
					.executeDataTable();
			dt = dt.filter(new Filter() {
				public boolean filter(Object obj) {
					DataRow dr = (DataRow) obj;
					return Priv.getPriv(User.getUserName(), "site", dr
							.getString("ID"), "site_browse");
				}
			});
			String siteID = getCookie().getCookie("SiteID");
			if ((XString.isNotEmpty(siteID))
					&& (Priv.getPriv(User.getUserName(), "site", siteID,
							"site_browse"))) {
				ApplicationPage.setCurrentSiteID(siteID);
			} else if (dt.getRowCount() > 0)
				ApplicationPage.setCurrentSiteID(dt.getString(0, 1));
			else {
				ApplicationPage.setCurrentSiteID("");
			}

			this.response.setStatus(1);
			synchronized (wrongList) {
				wrongList.remove(userName);
			}
			redirect("Application.jsp");
		}
	}

	public void getAllPriv() {
		getAllPriv(this.response);
	}

	public static DataCollection getAllPriv(DataCollection Response) {
		if ("admin".equalsIgnoreCase(User.getUserName())) {
			Response.put("isBranchAdmin", "Y");
		} else {
			Response.put("isBranchAdmin", "N");
			StringBuffer privTypes = new StringBuffer();
			Object[] ks = Priv.PRIV_MAP.keyArray();
			for (int i = 0; i < Priv.PRIV_MAP.size(); ++i) {
				if ("menu".equals(ks[i])) {
					continue;
				}
				privTypes.append(ks[i].toString());
				privTypes.append(",");
			}
			privTypes.deleteCharAt(privTypes.length() - 1);
			Response.put("privTypes", privTypes.toString());

			Response.put("siteDT", Priv.getSitePrivDT(User.getUserName(),
					ApplicationPage.getCurrentSiteID()+"", "site"));

			for (int i = 0; i < Priv.PRIV_MAP.size(); ++i) {
				if ("menu".equals(ks[i]))
					continue;
				if ("site".equals(ks[i])) {
					continue;
				}
				Response.put(ks[i] + "DT", Priv.getCatalogPrivDT(User
						.getUserName(), ApplicationPage.getCurrentSiteID()+"",
						(String) ks[i]));
			}
		}

		return Response;
	}
}
