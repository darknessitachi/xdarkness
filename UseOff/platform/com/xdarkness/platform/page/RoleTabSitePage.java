package com.xdarkness.platform.page;

import java.util.Map;

import com.abigdreamer.java.net.data.Transaction;
import com.abigdreamer.java.net.jaf.Page;
import com.abigdreamer.java.net.jaf.controls.grid.DataGridAction;
import com.abigdreamer.java.net.orm.OperateType;
import com.abigdreamer.java.net.orm.data.DataRow;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.XString;
import com.abigdreamer.schema.ZDPrivilegeSchema;
import com.xdarkness.platform.Priv;
import com.xdarkness.platform.RolePriv;

public class RoleTabSitePage extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		String RoleCode = dga.getParam("RoleCode");
		if (XString.isEmpty(RoleCode)) {
			RoleCode = dga.getParam("Role.LastRoleCode");
			if (XString.isEmpty(RoleCode)) {
				dga.bindData(new DataTable());
				return;
			}
		}
		String PrivType = dga.getParam("PrivType");
		StringBuffer sb = new StringBuffer();
		sb.append(",'" + RoleCode + "' as RoleCode");
		Object[] ks = Priv.SITE_MAP.keyArray();
		for (int i = 0; i < Priv.SITE_MAP.size(); i++) {
			sb.append(",'' as " + ks[i]);
		}

		String sql = "select ID,Name,0 as TreeLevel ,'site' as PrivType"
				+ sb.toString() + " from ZCSite a order by orderflag ,id";
		dga.setTotal(new QueryBuilder("select * from ZCSite a"));
		DataTable siteDT = new QueryBuilder(sql).executePagedDataTable(dga
				.getPageSize(), dga.getPageIndex());
		Map PrivTypeMap = RolePriv.getPrivTypeMap(RoleCode, PrivType);
		DataRow dr = null;
		for (int i = 0; i < siteDT.getRowCount(); i++) {
			dr = siteDT.getDataRow(i);
			String ID = dr.getString("ID");
			Map mapRow = (Map) PrivTypeMap.get(ID);
			if (mapRow != null) {
				for (int j = 0; j < dr.getColumnCount(); j++) {
					if (dr.getDataColumn(j).getColumnName().toLowerCase()
							.indexOf("_") > 0) {
						dr.set(j, "0".equals(mapRow.get(dr.getDataColumn(j)
								.getColumnName().toLowerCase())) ? "" : "√");
					}
				}
			}
		}
		dga.bindData(siteDT);
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) this.request.get("DT");
		String RoleCode = $V("RoleCode");

		Transaction trans = new Transaction();
		ZDPrivilegeSchema p = new ZDPrivilegeSchema();
		for (int i = 0; i < dt.getRowCount(); i++) {
			for (int j = 0; j < dt.getColCount(); j++) {
				if (dt.getDataColumn(j).getColumnName().toLowerCase().indexOf(
						"_") > 0) {
					trans.add(p.query(new QueryBuilder(
							"where OwnerType='R' and Owner =? and PrivType = '"
									+ dt.getString(i, "PrivType")
									+ "' and ID = '"
									+ dt.getString(i, "ID")
									+ "' and Code = '"
									+ dt.getDataColumn(j).getColumnName()
											.toLowerCase() + "' ", RoleCode)),
							OperateType.DELETE_AND_BACKUP);
				}
			}
		}

		for (int i = 0; i < dt.getRowCount(); i++) {
			DataRow dr = dt.getDataRow(i);
			for (int j = 0; j < dr.getColumnCount(); j++) {
				if (dr.getDataColumn(j).getColumnName().toLowerCase().indexOf(
						"_") > 0) {
					ZDPrivilegeSchema priv = new ZDPrivilegeSchema();
					priv.setOwnerType("R");
					priv.setOwner(dr.getString("RoleCode"));
					priv.setID(dr.getString("ID"));
					priv.setPrivType(dr.getString("PrivType"));
					priv.setCode(dr.getDataColumn(j).getColumnName()
							.toLowerCase());
					priv.setValue("".equals(dr.getString(j)) ? "0" : "1");
					trans.add(priv, OperateType.INSERT);
				}
			}
		}
		if (trans.commit()) {
			RolePriv.updateAllPriv(RoleCode);
			this.response.setLogInfo(1, "修改成功!");
		} else {
			this.response.setLogInfo(0, "修改失败!");
		}
	}
}
