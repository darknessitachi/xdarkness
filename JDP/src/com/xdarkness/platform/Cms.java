package com.xdarkness.platform;
import java.util.List;

import com.xdarkness.framework.User;
import com.xdarkness.framework.cache.CacheManager;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.XString;
import com.xdarkness.schema.ZDRoleSchema;


/**
 * @author Darkness 
 * create on 2010-12-3 下午03:12:28
 * @version 1.0
 * @since JDP 1.0
 */
public class Cms {
	public static String getCurrentSiteAlias() {
//		return SiteUtil.getAlias(getCurrentSiteID());
		return "";
	} 
	
	public static String getBranchOptions() {
		return getBranchOptions(null);
	}

	public static String getBranchOptions(Object checkedValue) {
		DataTable dt = new QueryBuilder(
				"select Name,BranchInnerCode,TreeLevel from ZDBranch where BranchInnerCode like ? order by OrderFlag",
				User.getBranchInnerCode() + "%").executeDataTable();
		indentDataTable(dt);
		return HtmlUtil.dataTableToOptions(dt, checkedValue);
	}
	
	public static void indentDataTable(DataTable dt) {
		indentDataTable(dt, 0, 2, 1);
	}

	public static void indentDataTable(DataTable dt, int n, int m,
			int firstLevel) {
		for (int i = 0; i < dt.getRowCount(); i++) {
			int level = Integer.parseInt(dt.getString(i, m));
			StringBuffer sb = new StringBuffer();
			for (int j = firstLevel; j < level; j++) {
				sb.append("　");
			}
			dt.set(i, n, sb.toString() + dt.getString(i, n));
		}
	}
	public static String getRoleNames(List roleCodes) {
		if ((roleCodes == null) || (roleCodes.size() == 0)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		boolean first = false;
		for (int i = 0; i < roleCodes.size(); i++) {
			String roleName = getRoleName((String) roleCodes.get(i));
			if (XString.isNotEmpty(roleName)) {
				if (first) {
					sb.append(",");
				}
				sb.append(roleName);
				first = true;
			}
		}
		return sb.toString();
	}
	public static String getRoleName(String roleCode) {
		ZDRoleSchema role = (ZDRoleSchema) CacheManager.get("Platform", "Role",
				roleCode);
		if (role == null) {
			return null;
		}
		return role.getRoleName();
	}
	public static DataTable District = null;
	private static Mapx DistrictMap = null;
	public static void initDistrict() {
		District = new QueryBuilder(
				"select Name,Code,TreeLevel,Type from zddistrict")
				.executeDataTable();
		Mapx map = new Mapx();
		for (int i = 0; i < District.getRowCount(); i++) {
			map.put(District.get(i, 1), District.get(i, 0));
		}
		DistrictMap = map;
	}

}
