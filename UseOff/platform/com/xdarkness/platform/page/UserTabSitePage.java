package com.xdarkness.platform.page;

import java.util.List;

import com.abigdreamer.java.net.data.Transaction;
import com.abigdreamer.java.net.jaf.Page;
import com.abigdreamer.java.net.jaf.controls.grid.DataGridAction;
import com.abigdreamer.java.net.orm.OperateType;
import com.abigdreamer.java.net.orm.data.DataRow;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.Mapx;
import com.xdarkness.platform.Priv;
import com.xdarkness.platform.RolePriv;
import com.zving.schema.ZDPrivilegeSchema;

public class UserTabSitePage extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		Mapx map = dga.getParams();
		String UserName = (String) map.get("UserName");
		if ((UserName == null) || ("".equals(UserName))) {
			dga.bindData(new DataTable());
			return;
		}
		String s = "";
		String PrivType = (String) map.get("PrivType");
		StringBuffer sb = new StringBuffer();
		sb.append(",'" + UserName + "' as UserName");

		Object[] ks = Priv.SITE_MAP.keyArray();
		for (int i = 0; i < Priv.SITE_MAP.size(); i++) {
			sb.append(",'" + s + "' as " + ks[i].toString());
		}

		String sql = "select ID,Name,0 as TreeLevel ,'site' as PrivType "
				+ sb.toString() + " from ZCSite a order by orderflag ,id";
		DataTable siteDT = new QueryBuilder(sql).executePagedDataTable(dga
				.getPageSize(), dga.getPageIndex());
		DataRow dr = null;
		for (int i = 0; i < siteDT.getRowCount(); i++) {
			dr = siteDT.getDataRow(i);
			for (int j = 0; j < dr.getColumnCount(); j++) {
				String columnName = dr.getDataColumn(j).getColumnName()
						.toLowerCase().toLowerCase();
				if (columnName.indexOf("_") > 0) {
					dr.set(j, Priv.getPriv(UserName, PrivType, dr
							.getString("ID"), columnName) ? "√" : "");
				}
			}
		}
		dga.setTotal(new QueryBuilder("select * from ZCSite a "));
		dga.bindData(siteDT);
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) this.request.get("DT");
		String UserName = $V("UserName");
		String PrivType = $V("PrivType");
		String[] RoleCodes = new String[0];
		List roleCodeList = Priv.getRoleCodesByUserName(UserName);
		if ((roleCodeList != null) && (roleCodeList.size() != 0)) {
			RoleCodes = (String[]) roleCodeList.toArray(new String[roleCodeList
					.size()]);
		}
		Transaction trans = new Transaction();
		ZDPrivilegeSchema p = new ZDPrivilegeSchema();
		for (int i = 0; i < dt.getRowCount(); i++) {
			for (int j = 0; j < dt.getColCount(); j++) {
				if (dt.getDataColumn(j).getColumnName().toLowerCase().indexOf(
						"_") > 0) {
					trans.add(p.query(new QueryBuilder(
							"where OwnerType='U' and Owner=? and PrivType = '"
									+ dt.getString(i, "PrivType")
									+ "' and ID = '"
									+ dt.getString(i, "ID")
									+ "' and Code = '"
									+ dt.getDataColumn(j).getColumnName()
											.toLowerCase() + "' ", UserName)),
							OperateType.DELETE_AND_BACKUP);
				}
			}
		}
		boolean value = false;
		for (int i = 0; i < dt.getRowCount(); i++) {
			DataRow dr = dt.getDataRow(i);

			int j = 0;
			boolean same = true;

			while (j < dr.getColumnCount()) {
				if (dr.getDataColumn(j).getColumnName().toLowerCase().indexOf(
						"_") > 0) {
					value = "√".equals(dr.getString(j));
					boolean roleValue = RolePriv.getRolePriv(RoleCodes,
							PrivType, dr.getString("ID"), dr.getDataColumn(j)
									.getColumnName().toLowerCase());
					if (value != roleValue) {
						same = false;
						break;
					}
				}
				j++;
			}
			if (same) {
				continue;
			}
			boolean roleValue = false;
			for (j = 0; j < dr.getColumnCount(); j++) {
				if (dr.getDataColumn(j).getColumnName().toLowerCase().indexOf(
						"_") > 0) {
					value = "√".equals(dr.getString(j));
					roleValue = RolePriv.getRolePriv(RoleCodes, PrivType, dr
							.getString("ID"), dr.getDataColumn(j)
							.getColumnName().toLowerCase());

					ZDPrivilegeSchema userPriv = new ZDPrivilegeSchema();
					userPriv.setOwnerType("U");
					userPriv.setOwner(UserName);
					userPriv.setID(dr.getString("ID"));
					userPriv.setPrivType(dr.getString("PrivType"));
					userPriv.setCode(dr.getDataColumn(j).getColumnName()
							.toLowerCase());
					if (roleValue) {
						if (value)
							userPriv.setValue("0");
						else {
							userPriv.setValue("-1");
						}
					} else if (value)
						userPriv.setValue("1");
					else {
						userPriv.setValue("0");
					}

					trans.add(userPriv, OperateType.INSERT);
				}
			}
		}
		if (trans.commit()) {
			Priv.updateAllPriv(UserName);
			this.response.setLogInfo(1, "修改成功!");
		} else {
			this.response.setLogInfo(0, "修改失败!");
		}
	}
}
