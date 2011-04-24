package com.xdarkness.platform;

/**
 * @author Darkness
 *
 * 	      Copyright (c) 2009 by Darkness
 *
 * @date 2010-10-26 下午03:04:17
 * @version 1.0
 */

//import java.io.IOException;
//import java.util.List;
//
//import javax.faces.application.Application;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.xml.registry.infomodel.User;
//
//import com.xdarkness.framework.Config;
//import com.xdarkness.framework.jaf.Cookies;
//import com.xdarkness.framework.jaf.IPage;
//import com.xdarkness.framework.jaf.JafRequest;
//import com.xdarkness.framework.jaf.JafResponse;
//import com.xdarkness.framework.orm.data.DataCollection;
//import com.xdarkness.framework.orm.data.DataRow;
//import com.xdarkness.framework.orm.data.DataTable;
//import com.xdarkness.framework.sql.QueryBuilder;
//import com.xdarkness.framework.util.Filter;
//import com.xdarkness.framework.util.Mapx;
//import com.xdarkness.framework.util.StringUtil;
//import com.xdarkness.schema.ZDUserSchema;
//import com.xdarkness.schema.ZDUserSet;
//
//public class CopyOfLogin implements IPage {
//	private static ArrayList wrongList = new ArrayList();
//
//	public static void ssoLogin(HttpServletRequest request,
//			HttpServletResponse response, String username) {
//		if (username == null) {
//			return;
//		}
//		ZDUserSchema user = new ZDUserSchema();
//		user.setUserName(username);
//		ZDUserSet userSet = user.query();
//
//		if ((!Config.isAllowLogin) && (!username.equalsIgnoreCase("admin"))) {
//			UserLog.log("Log", "Login", "临时禁止登录.用户名：" + username, request
//					.getRemoteAddr(), username);
//			return;
//		}
//
//		if ((userSet == null) || (userSet.size() < 1)) {
//			UserLog.log("Log", "Login", "SSO登陆失败.用户名：" + username, request
//					.getRemoteAddr(), username);
//		} else {
//			user = userSet.get(0);
//			User.setUserName(user.getUserName());
//			User.setRealName(user.getRealName());
//			User.setBranchInnerCode(user.getBranchInnerCode());
//			User.setType(user.getType());
//			User.setValue("Prop1", user.getProp1());
//			User.setValue("Prop2", user.getProp2());
//			User.setValue("Prop3", user.getProp3());
//			User.setValue("Prop4", user.getProp4());
//			User.setManager(true);
//			User.setLogin(true);
//
//			UserLog.log("Log", "Login", "登录成功", request.getRemoteAddr());
//
//			DataTable dt = new QueryBuilder(
//					"select name,id from ZCSite order by BranchInnerCode ,orderflag ,id")
//					.executeDataTable();
//			dt = dt.filter(new Filter() {
//				public boolean filter(Object obj) {
//					DataRow dr = (DataRow) obj;
//					return Priv.getPriv(User.getUserName(), "site", dr
//							.getString("ID"), "site_browse");
//				}
//			});
//			if (dt.getRowCount() > 0)
//				Application.setCurrentSiteID(dt.getString(0, 1));
//			else {
//				Application.setCurrentSiteID("");
//			}
//			try {
//				String path = request.getParameter("Referer");
//				LogUtil.info("SSOLogin,Referer:" + path);
//				if (StringUtil.isNotEmpty(path)) {
//					if ((StringUtil.isNotEmpty(request.getParameter("t")))
//							&& (!"null".equalsIgnoreCase(request
//									.getParameter("t"))))
//						response.sendRedirect(path + "?t="
//								+ request.getParameter("t"));
//					else
//						response.sendRedirect(path);
//				} else
//					response.sendRedirect("Application.jsp");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	private boolean checkVerifyCode() {
//		String userName = $V("UserName").toLowerCase();
//		if (wrongList.contains(userName)) {
//			Object authCode = User.getValue("_SKY_AUTHKEY");
//			if ((authCode == null)
//					|| (!authCode.equals(getValue("VerifyCode")))) {
//				this.response.setStatus(0);
//				this.response.setMessage("验证码输入错误");
//				return false;
//			}
//		}
//		return true;
//	}
//
//	private boolean isAllowLogin() {
//		String userName = getValue("UserName").toLowerCase();
//
//		if ((!Config.isAllowLogin) && (!userName.equalsIgnoreCase("admin"))) {
//			UserLog.log("Log", "Login", "临时禁止登录.用户名" + getValue("UserName"),
//					this.request.getClientIP(), getValue("UserName"));
//
//			this.response.setStatus(0);
//			this.response.setMessage("临时禁止登录，请与系统管理员联系!");
//
//			return false;
//		}
//
//		return true;
//	}
//
//	private ZDUserSchema checkPassword() {
//		String Password = StringUtil.md5Hex(getValue("Password"));
//		ZDUserSchema user = new ZDUserSchema();
//		user.setUserName(getValue("UserName").toLowerCase());
//		user.setPassword(Password);
//		ZDUserSet userSet = user.query();
//
//		if ((userSet == null) || (userSet.size() < 1)) {
//			UserLog.log("Log", "Login", "登陆失败.用户名：" + getValue("UserName"),
//					this.request.getClientIP(), getValue("UserName"));
//
//			this.response.setStatus(0);
//			this.response.setMessage("用户名或密码输入错误");
//			if (!wrongList.contains(userName)) {
//				synchronized (wrongList) {
//					if (wrongList.size() > 20000) {
//						wrongList.clear();
//					}
//					wrongList.add(userName);
//				}
//			}
//			return null;
//		}
//		return user = userSet.get(0);
//	}
//
//	private boolean checkStatus(ZDUserSchema user) {
//		if ((!"admin".equalsIgnoreCase(user.getUserName()))
//				&& ("S".equals(user.getStatus()))) {
//			UserLog.log("Log", "Login", "登陆失败.用户名：" + getValue("UserName")
//					+ "已停用", this.request.getClientIP(), getValue("UserName"));
//
//			this.response.setStatus(0);
//			this.response.setMessage("该用户处于停用状态，请联系管理员！");
//			return false;
//		}
//		return true;
//	}
//
//	private void setCurrentUser(ZDUserSchema user) {
//		User.setUserName(user.getUserName());
//		User.setRealName(user.getRealName());
//		User.setBranchInnerCode(user.getBranchInnerCode());
//		User.setType(user.getType());
//		User.setValue("Prop1", user.getProp1());
//		User.setValue("Prop2", user.getProp2());
//		User.setValue("Prop3", user.getProp3());
//		User.setValue("Prop4", user.getProp4());
//		User.setManager(true);
//		User.setLogin(true);
//
//		UserLog.log("Log", "Login", "登陆成功", this.request.getClientIP());
//	}
//
//	private void setCurrentSite() {
//		DataTable dt = new QueryBuilder(
//				"select name,id from ZCSite order by BranchInnerCode ,orderflag ,id")
//				.executeDataTable();
//		dt = dt.filter(new Filter() {
//			public boolean filter(Object obj) {
//				DataRow dr = (DataRow) obj;
//				return Priv.getPriv(User.getUserName(), "site", dr
//						.getString("ID"), "site_browse");
//			}
//		});
//		String siteID = getCookie().getCookie("SiteID");
//		if ((StringUtil.isNotEmpty(siteID))
//				&& (Priv.getPriv(User.getUserName(), "site", siteID,
//						"site_browse"))) {
//			Application.setCurrentSiteID(siteID);
//		} else if (dt.getRowCount() > 0)
//			Application.setCurrentSiteID(dt.getString(0, 1));
//		else {
//			Application.setCurrentSiteID("");
//		}
//	}
//
//	/**
//	 * 
//	 */
//	public void submit() {
//
//		if (!checkVerifyCode())
//			return;
//
//		if (!isAllowLogin())
//			return;
//
//		ZDUserSchema user = checkPassword();
//		if (user == null)
//			return;
//
//		if (!checkStatus(user))
//			return;
//
//		setCurrentUser(user);
//
//		setCurrentSite();
//
//		this.response.setStatus(1);
//		redirect("Application.jsp");
//	}
//
//	public void getAllPriv() {
//		getAllPriv(this.response);
//	}
//
//	public static DataCollection getAllPriv(DataCollection Response) {
//		if ("admin".equalsIgnoreCase(User.getUserName())) {
//			Response.put("isBranchAdmin", "Y");
//		} else {
//			String UserName = User.getUserName();
//			long SiteID = Application.getCurrentSiteID();
//			List roleCodeList = PubFun.getRoleCodesByUserName(UserName);
//			Response.put("isBranchAdmin", "N");
//			StringBuffer privTypes = new StringBuffer();
//			StringBuffer privTypeItems = new StringBuffer();
//			Object[] ks = Priv.PRIV_MAP.keyArray();
//			Object[] vs = Priv.PRIV_MAP.valueArray();
//			for (int i = 0; i < Priv.PRIV_MAP.size(); ++i) {
//				if ("menu".equals(ks[i])) {
//					continue;
//				}
//				privTypes.append(ks[i].toString());
//				privTypes.append(",");
//				Mapx map = (Mapx) vs[i];
//				Object[] ks2 = map.keyArray();
//				for (int j = 0; j < map.size(); ++j) {
//					privTypeItems.append(ks2[j].toString());
//					privTypeItems.append(",");
//				}
//			}
//			privTypes.deleteCharAt(privTypes.length() - 1);
//			privTypeItems.deleteCharAt(privTypeItems.length() - 1);
//			Response.put("privTypes", privTypes.toString());
//			Response.put("privTypeItems", privTypeItems.toString());
//			Response.put("roleCodes", (roleCodeList == null) ? "" : StringUtil
//					.join(roleCodeList.toArray()));
//
//			QueryBuilder qb = new QueryBuilder(
//					"select ID,Code,Value from ZDPrivilege where OwnerType =? and Owner=? and PrivType='site' and ID =?");
//
//			qb.add("U");
//			qb.add(UserName);
//			qb.add(SiteID);
//			Response.put("siteDT", qb.executeDataTable());
//
//			for (int i = 0; i < Priv.PRIV_MAP.size(); ++i) {
//				if ("menu".equals(ks[i]))
//					continue;
//				if ("site".equals(ks[i])) {
//					continue;
//				}
//				qb = new QueryBuilder(
//						"select ID,Code,Value from ZDPrivilege where OwnerType =? and Owner=? and PrivType=? and exists (select '' from ZCCatalog where SiteID =? and ZCCatalog.InnerCode = ZDPrivilege.ID ) ");
//				qb.add("U");
//				qb.add(UserName);
//				qb.add(ks[i]);
//				qb.add(SiteID);
//				Response.put(ks[i] + "DT", qb.executeDataTable());
//			}
//			if (roleCodeList == null) {
//				return Response;
//			}
//			for (int i = 0; i < roleCodeList.size(); ++i) {
//				qb = new QueryBuilder(
//						"select ID,Code,Value from ZDPrivilege where OwnerType =? and Owner=? and PrivType='site' and ID =?");
//
//				qb.add("R");
//				qb.add(roleCodeList.get(i));
//				qb.add(SiteID);
//				Response.put(roleCodeList.get(i) + "site" + "DT", qb
//						.executeDataTable());
//				for (int j = 0; j < Priv.PRIV_MAP.size(); ++j) {
//					if ("menu".equals(ks[j]))
//						continue;
//					if ("site".equals(ks[j])) {
//						continue;
//					}
//					qb = new QueryBuilder(
//							"select ID,Code,Value from ZDPrivilege where OwnerType =? and Owner=? and PrivType=? and exists (select '' from ZCCatalog where SiteID =? and ZCCatalog.InnerCode = ZDPrivilege.ID ) ");
//					qb.add("R");
//					qb.add(roleCodeList.get(i));
//					qb.add(ks[j]);
//					qb.add(SiteID);
//					Response.put(roleCodeList.get(i) + ks[j].toString() + "DT",
//							qb.executeDataTable());
//				}
//			}
//		}
//		return Response;
//	}
//
//	/**
//	 * 获取当前用户验证码
//	 */
//	public void getVerifyCode() {
//		if (!this.request.getClientIP().equals("127.0.0.1")) {
//			return;
//		}
//		this.response.put("VerifyCode", User.getValue("_SKY_AUTHKEY"));
//	}
//
//	/**
//	 * Page初始化方法
//	 */
//	public static Mapx<String, String> init(Mapx<String, String> params) {
//		params.put("AppCode", Config.getAppCode());
//		params.put("AppName", Config.getAppName());
//		return params;
//	}
//
//}
