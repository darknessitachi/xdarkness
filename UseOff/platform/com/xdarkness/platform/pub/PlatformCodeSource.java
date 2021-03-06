package com.xdarkness.platform.pub;

import com.abigdreamer.java.net.cache.CacheManager;
import com.abigdreamer.java.net.jaf.controls.CodeSource;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.orm.data.DataTableUtil;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.Mapx;
import com.abigdreamer.java.net.util.XString;

public class PlatformCodeSource extends CodeSource {
	public DataTable getCodeData(String codeType, Mapx params) {
		DataTable dt = null;
		String conditionField = params.getString("ConditionField");
		String conditionValue = params.getString("ConditionValue");
		if ("District".equals(codeType)) {
			QueryBuilder qb = new QueryBuilder(
					"select code,name from ZDDistrict where " + conditionField
							+ "=?", conditionValue);
			String parentCode = params.getString("ParentCode");
			if (XString.isNotEmpty(parentCode)) {
				qb.append(" and Code like ?");

				if ((parentCode.startsWith("11"))
						|| (parentCode.startsWith("12"))
						|| (parentCode.startsWith("31"))
						|| (parentCode.startsWith("50"))) {
					qb.add(parentCode.substring(0, 2) + "%");
					qb.append(" and TreeLevel=3");
				} else if (parentCode.endsWith("0000")) {
					qb.add(parentCode.substring(0, 2) + "%");
					qb.append(" and TreeLevel=2");
				} else if (parentCode.endsWith("00")) {
					qb.add(parentCode.substring(0, 4) + "%");
					qb.append(" and TreeLevel=3");
				} else {
					qb.add("#");
				}
			} else if (conditionField.equals("1")) {
				return new DataTable();
			}
			dt = qb.executeDataTable();
		} else if ("User".equals(codeType)) {
			QueryBuilder qb = new QueryBuilder(
					"select UserName,UserName as 'Name',RealName,isBranchAdmin from ZDUser where "
							+ conditionField + "=?", conditionValue);
			dt = qb.executeDataTable();
		} else {
			Mapx map = CacheManager.getMapx("Code", codeType);
			if (conditionValue.equals("2")) {
				map.remove("01");
			}
			if (map != null) {
				dt = DataTableUtil.mapToDataTable(map);
			}
		}
		return dt;
	}
}

/*
 * com.xdarkness.platform.pub.PlatformCodeSource JD-Core Version: 0.6.0
 */