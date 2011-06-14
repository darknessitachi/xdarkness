package com.xdarkness.platform.page;

import java.util.List;

import com.abigdreamer.java.net.data.BlockingTransaction;
import com.abigdreamer.java.net.data.Transaction;
import com.abigdreamer.java.net.jaf.Page;
import com.abigdreamer.java.net.jaf.controls.HtmlUtil;
import com.abigdreamer.java.net.jaf.controls.grid.DataGridAction;
import com.abigdreamer.java.net.orm.OperateType;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.orm.data.DataRow;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.Filter;
import com.abigdreamer.java.net.util.Mapx;
import com.abigdreamer.java.net.util.XString;
import com.xdarkness.platform.Priv;
import com.xdarkness.platform.RolePriv;
import com.zving.schema.ZDPrivilegeSchema;
import com.zving.schema.ZDPrivilegeSet;

public class UserTabCatalogPage extends Page {
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

		String PrivType = params.getString("PrivType");
		if (XString.isEmpty(PrivType)) {
			PrivType = params.getString("OldPrivType");
			if (XString.isEmpty(PrivType)) {
				PrivType = "article";
			}
		}
		params.put("PrivType", HtmlUtil.mapxToOptions(
				RoleTabCatalogPage.PrivTypeMap, PrivType));
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String userName = dga.getParam("UserName");
		String siteID = dga.getParam("SiteID");
		if (XString.isEmpty(siteID)) {
			siteID = dga.getParam("OldSiteID");
			if (XString.isEmpty(siteID)) {
				siteID = ApplicationPage.getCurrentSiteID()+"";
			}
			if ((XString.isNotEmpty(siteID))
					&& (!Priv.getPriv(userName, "site", siteID, "site_browse"))) {
				siteID = "";
			}
		}

		if (XString.isEmpty(siteID)) {
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
			if (dt.getRowCount() > 0) {
				siteID = dt.getString(0, "ID");
			} else {
				dga.bindData(new DataTable());
				return;
			}
		}

		String PrivType = dga.getParam("PrivType");
		if (XString.isEmpty(PrivType)) {
			PrivType = dga.getParam("OldPrivType");
			if (XString.isEmpty(PrivType)) {
				PrivType = "article";
			}
		}

		DataTable catalogDT = Priv.getCatalogPrivDT(userName, siteID, PrivType,
				true);
		catalogDT = DataGridAction.sortTreeDataTable(catalogDT, "ID",
				"ParentInnerCode");
		dga.bindData(catalogDT);
	}

	public void dg1Edit() {
		DataTable resultDT = (DataTable) this.request.get("DT");
		Transaction trans = new BlockingTransaction();
		String UserName = $V("UserName");
		String PrivType = $V("PrivType");
		String SiteID = $V("SiteID");
		ZDPrivilegeSchema p = new ZDPrivilegeSchema();
		QueryBuilder qb1 = new QueryBuilder(
				"where OwnerType='U' and Owner=? and PrivType=? and exists (select '1' from zccatalog where innercode=ZDPrivilege.id and siteid=?)",
				UserName, PrivType);
		qb1.add(SiteID);
		trans.add(p.query(qb1), OperateType.DELETE_AND_BACKUP);
		QueryBuilder qb2 = new QueryBuilder(
				"where OwnerType='U' and Owner=? and PrivType='site' and ID=?",
				UserName, SiteID);
		qb2.append(" and code like ?", PrivType + "_%");
		trans.add(p.query(qb2), OperateType.DELETE_AND_BACKUP);
		for (int i = 0; i < resultDT.getRowCount(); i++) {
			for (int j = 0; j < resultDT.getColCount(); j++) {
				if (resultDT.getDataColumn(j).getColumnName().toLowerCase()
						.indexOf("_") > 0) {
					if ("√".equals(resultDT.getString(i, j)))
						resultDT.set(i, j, "1");
					else {
						resultDT.set(i, j, "0");
					}
				}
			}
		}

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
		for (int i = 0; (i < rolePrivDT.getRowCount()) && (i < 1); i++) {
			for (int j = 0; j < rolePrivDT.getColCount(); j++) {
				if (rolePrivDT.getDataColumn(j).getColumnName().toLowerCase()
						.indexOf("_") > 0) {
					rolePrivDT.set(i, j, RolePriv.getRolePriv(RoleCodes,
							"site", rolePrivDT.getString(i, "ID"), rolePrivDT
									.getDataColumn(j).getColumnName()
									.toLowerCase()) ? "1" : "0");
				}
			}
		}
		for (int i = 1; i < rolePrivDT.getRowCount(); i++) {
			for (int j = 0; j < rolePrivDT.getColCount(); j++) {
				if (rolePrivDT.getDataColumn(j).getColumnName().toLowerCase()
						.indexOf("_") > 0) {
					rolePrivDT.set(i, j, RolePriv.getRolePriv(RoleCodes,
							PrivType, rolePrivDT.getString(i, "ID"), rolePrivDT
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
					} else if (("0".equals(v1)) || (XString.isEmpty(v1))) {
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

		ZDPrivilegeSet set = new ZDPrivilegeSet();
		for (int i = 0; i < resultDT.getRowCount(); i++) {
			DataRow dr = resultDT.getDataRow(i);
			for (int j = 0; j < dr.getColumnCount(); j++) {
				if ((dr.getDataColumn(j).getColumnName().toLowerCase().indexOf(
						"_") <= 0)
						|| ("0".equals(dr.getString(j)))) {
					continue;
				}
				ZDPrivilegeSchema priv = new ZDPrivilegeSchema();
				priv.setOwnerType("U");
				priv.setOwner(UserName);
				priv.setID(dr.getString("ID"));
				priv.setPrivType(dr.getString("PrivType"));
				priv.setCode(dr.getDataColumn(j).getColumnName().toLowerCase());
				priv.setValue(dr.getString(j));
				set.add(priv);
			}
		}

		qb1 = new QueryBuilder("where OwnerType='U' and Owner=?", UserName);
		System.out.println(p.query(qb1));
		trans.add(set, OperateType.INSERT);
		if (trans.commit()) {
			Priv.updateAllPriv(UserName);
			this.response.setLogInfo(1, "修改成功!");
		} else {
			this.response.setLogInfo(0, "修改失败!");
		}
	}
}
