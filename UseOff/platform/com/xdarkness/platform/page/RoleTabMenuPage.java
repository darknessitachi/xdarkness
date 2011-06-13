package com.xdarkness.platform.page;

import com.abigdreamer.java.net.User;
import com.abigdreamer.java.net.data.Transaction;
import com.abigdreamer.java.net.jaf.Page;
import com.abigdreamer.java.net.jaf.controls.HtmlUtil;
import com.abigdreamer.java.net.jaf.controls.grid.DataGridAction;
import com.abigdreamer.java.net.orm.OperateType;
import com.abigdreamer.java.net.orm.data.DataRow;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.Filter;
import com.abigdreamer.java.net.util.Mapx;
import com.abigdreamer.java.net.util.XString;
import com.abigdreamer.schema.ZDPrivilegeSchema;
import com.xdarkness.platform.Priv;
import com.xdarkness.platform.RolePriv;

public class RoleTabMenuPage extends Page {
	public static Mapx init(Mapx params) {
		String roleCode = params.getString("RoleCode");
		DataTable dt = new QueryBuilder(
				"select name,id from zcsite order by orderflag ,id")
				.executeDataTable();
		dt = dt.filter(new Filter(roleCode) {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return RolePriv.getRolePriv(
						new String[] { (String) this.Param }, "site", dr
								.getString("ID"), "site_browse");
			}
		});
		String SiteID = params.getString("SiteID");
		if (XString.isEmpty(SiteID)) {
			SiteID = params.getString("OldSiteID");
			if (XString.isEmpty(SiteID)) {
				SiteID = ApplicationPage.getCurrentSiteID()+"";
			}
		}
		params.put("SiteID", HtmlUtil.dataTableToOptions(dt, SiteID));
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String sql = "select ID ,Name,Icon,Type,'' as TreeLevel  from ZDMenu where (parentid in (select id from ZDMenu where parentid=0 and visiable='Y') or parentid=0) and visiable='Y'order by OrderFlag";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		if (!"admin".equals(User.getUserName()))
			dt = dt.filter(new Filter() {
				public boolean filter(Object obj) {
					DataRow dr = (DataRow) obj;
					return Priv.getPriv(User.getUserName(), "menu", dr
							.getString("id"), "menu_browse");
				}
			});
		for (int i = 0; i < dt.getRowCount(); i++) {
			if ("2".equals(dt.get(i, "Type")))
				dt.set(i, "TreeLevel", "1");
			else {
				dt.set(i, "TreeLevel", "0");
			}
		}
		dga.bindData(dt);
	}

	public void getCheckedMenu() {
		String RoleCode = $V("RoleCode");
		if (XString.isEmpty(RoleCode)) {
			RoleCode = $V("Role.LastRoleCode");
			if (XString.isEmpty(RoleCode)) {
				this.response.put("checkedMenu", "");
				return;
			}
		}
		String SiteID = $V("SiteID");
		QueryBuilder qb = new QueryBuilder(
				"select ID from ZDPrivilege where OwnerType=? and Owner=? and PrivType='menu' and ID like ? and Value='1'",
				"R", RoleCode);
		qb.add(SiteID + "-%");

		DataTable dt = qb.executeDataTable();
		this.response
				.put("checkedMenu", XString.join(dt.getColumnValues(0)));
	}

	public void save() {
		String RoleCode = $V("RoleCode");
		String SiteID = $V("SiteID");
		DataTable dt = (DataTable) this.request.get("dt");
		Transaction trans = new Transaction();
		QueryBuilder qb = new QueryBuilder(
				"where OwnerType=? and Owner=? and PrivType='menu' and ID like ?",
				"R", RoleCode);
		qb.add(SiteID + "-%");
		trans.add(new ZDPrivilegeSchema().query(qb), OperateType.DELETE_AND_BACKUP);
		for (int i = 0; i < dt.getRowCount(); i++) {
			if ("1".equals(dt.getString(i, "menu_browse"))) {
				ZDPrivilegeSchema priv = new ZDPrivilegeSchema();
				priv.setOwnerType("R");
				priv.setOwner(RoleCode);
				priv.setID(SiteID + "-" + dt.getString(i, "ID"));
				priv.setPrivType("menu");
				priv.setCode("menu_browse");
				priv.setValue(dt.getString(i, "menu_browse"));
				trans.add(priv, OperateType.INSERT);
			}
		}

		if (trans.commit()) {
			RolePriv.updatePriv(RoleCode, "menu");
			this.response.setLogInfo(1, "修改成功!");
		} else {
			this.response.setLogInfo(0, "修改失败!");
		}
	}
}

/*
 * com.xdarkness.platform.RoleTabMenu JD-Core Version: 0.6.0
 */