package com.xdarkness.platform.page;

import java.sql.SQLException;

import com.xdarkness.framework.Config;
import com.xdarkness.framework.User;
import com.xdarkness.framework.jaf.controls.HtmlScript;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataCollection;
import com.xdarkness.framework.orm.data.DataRow;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Filter;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.XString;
import com.xdarkness.platform.Priv;

public class ApplicationPage extends Page {
	public static Mapx init(Mapx params) {
		DataTable dt = null;
		dt = new QueryBuilder("select name,id from zcsite order by orderflag")
				.executeDataTable();
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return Priv.getPriv(User.getUserName(), "site", dr
						.getString("ID"), "site_browse");
			}
		});
		if ((getCurrentSiteID() == 0L) && (dt.getRowCount() > 0)) {
			setCurrentSiteID(dt.getString(0, "ID"));
		}
		params
				.put("Sites", HtmlUtil.dataTableToOptions(dt,
						getCurrentSiteID()));
		DataTable dtsite = new QueryBuilder(
				"select ID,Name from zcsite order by orderflag")
				.executeDataTable();
		StringBuffer sitestb = new StringBuffer();
		for (int i = 0; i < dtsite.getRowCount(); i++) {
			if (dtsite.getString(i, "ID").equals(getCurrentSiteID())) {
				sitestb
						.append("<a value=\""
								+ dtsite.getString(i, "ID")
								+ "\" class=\"ahover\" hidefocus href=\"javascript:void(0);\">"
								+ dtsite.getString(i, "Name") + "</a>");
				params.put("CurrentSiteName", dtsite.getString(i, "Name"));
				params.put("CurrentSiteId", dtsite.getString(i, "ID"));
			} else {
				sitestb.append("<a value=\"" + dtsite.getString(i, "ID")
						+ "\" hidefocus href=\"javascript:void(0);\">"
						+ dtsite.getString(i, "Name") + "</a>");
			}
		}
		params.put("ZCSites", sitestb.toString());

		dt = new QueryBuilder(
				"select name,id from zdmenu where  visiable='Y' and parentID=0 order by OrderFlag")
				.executeDataTable();
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return Priv.getPriv(User.getUserName(), "menu", ApplicationPage
						.getCurrentSiteID()
						+ "-" + dr.getString("id"), "menu_browse");
			}
		});
		boolean hasMenu = false;
		String template = "<a id='_Menu_${ID}' onclick='Application.onMainMenuClick(this);return false;' hidefocus='true'><b>${Name}</b></a>";
		String menuHtml = HtmlUtil.replaceWithDataTable(dt, template);
		if (dt.getRowCount() > 0) {
			hasMenu = true;
		}

		StringBuffer sb = new StringBuffer();

		template = "arr.push([${ID},\"${Name}\",\"${URL}\",\"${Icon}\"]);";
		sb.append("var arr;");
		for (int i = 0; i < dt.getRowCount(); i++) {
			String id = dt.getString(i, "ID");
			sb.append("arr = [];");
			DataTable dt2 = new QueryBuilder(
					"select name,id,url,icon from zdmenu where visiable='Y' and parentID=? order by OrderFlag",
					id).executeDataTable();
			dt2 = dt2.filter(new Filter() {
				public boolean filter(Object obj) {
					DataRow dr = (DataRow) obj;
					return Priv.getPriv(User.getUserName(), "menu", ApplicationPage
							.getCurrentSiteID()
							+ "-" + dr.getString("id"), "menu_browse");
				}
			});
			sb.append(HtmlUtil.replaceWithDataTable(dt2, template));
			sb.append("$('_Menu_" + id + "').ChildArray = arr;");
			if (dt2.getRowCount() > 0) {
				hasMenu = true;
			}
		}

		HtmlScript script = new HtmlScript();
		script.setInnerHTML(sb.toString());
		if (hasMenu)
			params.put("Menu", menuHtml + script.getOuterHtml());
		else {
			params
					.put("Menu",
							"<font color='yellow'>对不起，你没有任何菜单权限，请联系'管理员'分配菜单权限后再登陆！</font>");
		}

		DataCollection privDC = LoginPage.getAllPriv(new DataCollection());
		String priv = XString.htmlEncode(privDC.toXML().replaceAll("\\s+",
				" "));
		params.put("Privileges", priv);
		return params;
	}

	public void changeSite() {
		String SiteID = $V("SiteID");
		setCurrentSiteID(SiteID);
	}

	public static void setCurrentSiteID(String siteID) {
		if (XString.isEmpty(siteID))
			User.setValue("_CurrentSiteID", "");
		else
			User.setValue("_CurrentSiteID", siteID);
	}

	public static long getCurrentSiteID() {
		String id = (String) User.getValue("_CurrentSiteID");
		if (XString.isEmpty(id)) {
			if ("admin".equals(User.getUserName())) {
				LogUtil.error("请在站点管理->站点列表下先创建站点");
				return 0L;
			}
			LogUtil.error("用户：" + User.getUserName() + "没有任何站点的浏览权限，请先设置权限再登陆");
			return 0L;
		}

		return Long.parseLong(id);
	}

	public void changePassword() throws SQLException {
		String OldPassword = $V("OldPassword");
		String Password = $V("Password");
		QueryBuilder qb = new QueryBuilder(
				"update ZDUser set Password=? where UserName=? and Password=?");
		qb.add(XString.md5Hex(Password));
		qb.add(User.getUserName());
		qb.add(XString.md5Hex(OldPassword));
		if (qb.executeNoQuery() > 0) {
			UserLogPage.log("User", "EditPassword", "修改密码成功", this.request
					.getClientIP());
			this.response.setMessage("修改密码成功");
			this.response.setStatus(1);
		} else {
			UserLogPage.log("User", "EditPassword", "修改密码失败", this.request
					.getClientIP());
			this.response.setStatus(0);
			this.response.setMessage("修改密码失败，旧密码不正确");
		}
	}

	public void logout() {
		String logouturl = Config.getContextPath() + "Logout.jsp";

		this.response.put("Status", 1);
		UserLogPage.log("Log", "Logout", "正常退出系统", this.request.getClientIP());

		redirect(logouturl);
	}
}

/*
 * com.xdarkness.platform.Application JD-Core Version: 0.6.0
 */