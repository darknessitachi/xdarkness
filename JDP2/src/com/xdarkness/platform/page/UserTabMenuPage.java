package com.xdarkness.platform.page;

import java.util.LinkedList;
import java.util.List;

import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.data.Transaction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.orm.data.DataRow;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Filter;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.XString;
import com.xdarkness.platform.Priv;
import com.xdarkness.platform.RolePriv;
import com.xdarkness.schema.ZDPrivilegeSchema;

public class UserTabMenuPage extends Page {
	public static Mapx init(Mapx params) {
		String userName = params.getString("UserName");
		DataTable dt = new QueryBuilder(
				"select name,id from zcsite order by orderflag ,id")
				.executeDataTable();
		dt = dt.filter(new Filter(userName) {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return Priv.getPriv((String) this.Param, "site", dr
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
		String sql = "select ID ,Name,Icon,Type,'' as TreeLevel  from ZDMenu where (parentid in(select id from ZDMenu where parentid=0 and visiable='Y') or parentid=0) and visiable='Y' order by OrderFlag";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
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
		String UserName = $V("UserName");
		if (XString.isEmpty(UserName)) {
			this.response.put("checkedMenu", "");
			return;
		}
		String SiteID = $V("SiteID");
		List list = new LinkedList();
		String sql = "select ID ,Name,Icon,Type,'' as TreeLevel  from ZDMenu where (parentid in (select id from ZDMenu where parentid=0 and visiable='Y') or parentid=0) and visiable='Y' order by OrderFlag";
		DataTable dt = new QueryBuilder(sql).executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (Priv.getPriv(UserName, "menu", SiteID + "-"
					+ dt.getString(i, "ID"), "menu_browse")) {
				list.add(dt.getString(i, "ID"));
			}
		}
		this.response.put("checkedMenu", XString.join(list.toArray()));
	}

	public void save() {
		String UserName = $V("UserName");
		long SiteID = Long.parseLong($V("SiteID"));
		DataTable resultDT = (DataTable) this.request.get("dt");
		String[] RoleCodes = new String[0];
		List roleCodeList = Priv.getRoleCodesByUserName(UserName);
		if ((roleCodeList != null) && (roleCodeList.size() != 0)) {
			RoleCodes = (String[]) roleCodeList.toArray(new String[roleCodeList
					.size()]);
		}
		DataColumn[] types = resultDT.getDataColumns();
		DataColumn[] copyTypes = new DataColumn[types.length];
		System.arraycopy(types, 0, copyTypes, 0, types.length);
		Object[][] copyValues = new Object[resultDT.getRowCount()][types.length];
		for (int i = 0; i < copyValues.length; i++) {
			System.arraycopy(resultDT.getDataRow(i).getDataValues(), 0,
					copyValues[i], 0, types.length);
		}
		DataTable rolePrivDT = new DataTable(copyTypes, copyValues);
		for (int i = 0; i < rolePrivDT.getRowCount(); i++) {
			for (int j = 0; j < rolePrivDT.getColCount(); j++) {
				if (rolePrivDT.getDataColumn(j).getColumnName().toLowerCase()
						.indexOf("_") > 0) {
					rolePrivDT.set(i, j, RolePriv.getRolePriv(RoleCodes,
							"menu", SiteID + "-"
									+ rolePrivDT.getString(i, "ID"), rolePrivDT
									.getDataColumn(j).getColumnName()
									.toLowerCase()) ? "1" : "0");
				}
			}

		}

		String v1 = null;
		String v2 = null;
		for (int i = 0; i < rolePrivDT.getRowCount(); i++) {
			for (int j = 0; j < rolePrivDT.getColCount(); j++) {
				if (rolePrivDT.getDataColumn(j).getColumnName().toLowerCase()
						.indexOf("_") > 0) {
					v1 = rolePrivDT.getString(i, j);
					v2 = resultDT.getString(i, j);
					if (v1.equals(v2)) {
						resultDT.set(i, j, "0");
					} else if ("0".equals(v1)) {
						if ("1".equals(v2))
							resultDT.set(i, j, "1");
					} else {
						if ((!"1".equals(v1)) || (!"0".equals(v2)))
							continue;
						resultDT.set(i, j, "-1");
					}
				}
			}

		}

		Transaction trans = new Transaction();
		QueryBuilder qb = new QueryBuilder(
				" where OwnerType=? and Owner=? and PrivType='menu' and ID like ?",
				"U", $V("UserName"));
		qb.add(SiteID + "%");
		trans.add(new ZDPrivilegeSchema().query(qb), OperateType.DELETE_AND_BACKUP);
		for (int i = 0; i < resultDT.getRowCount(); i++) {
			if ((!"1".equals(resultDT.getString(i, "menu_browse")))
					&& (!"-1".equals(resultDT.getString(i, "menu_browse"))))
				continue;
			ZDPrivilegeSchema priv = new ZDPrivilegeSchema();
			priv.setOwnerType("U");
			priv.setOwner(UserName);
			priv.setID(SiteID + "-" + resultDT.getString(i, "ID"));
			priv.setPrivType("menu");
			priv.setCode("menu_browse");
			priv.setValue(resultDT.getString(i, "menu_browse"));
			trans.add(priv, OperateType.INSERT);
		}

		if (trans.commit()) {
			Priv.updatePriv(UserName, "menu");
			this.response.setLogInfo(1, "保存成功!");
		} else {
			this.response.setLogInfo(0, "保存失败!");
		}
	}
}
