package com.xdarkness.framework.cache;

import com.xdarkness.framework.Config;
import com.xdarkness.framework.orm.data.DataRow;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;

public class CodeCache extends CacheProvider {
	public String getProviderName() {
		return "Code";
	}

	public void onKeyNotFound(String type, Object key) {
		if (!"true".equals(Config.getValue("App.ExistPlatformDB"))) {
			return;
		}

		Object value = new QueryBuilder(
				"select CodeName from ZDCode where CodeType=? and CodeValue=? order by CodeOrder,CodeValue",
				type, key).executeOneValue();
		CacheManager.set(getProviderName(), type, key, value);
	}

	public void onTypeNotFound(String type) {
		if (!"true".equals(Config.getValue("App.ExistPlatformDB"))) {
			return;
		}
		DataTable dt = new QueryBuilder(
				"select CodeType,ParentCode,CodeName,CodeValue from ZDCode where CodeType=? order by CodeOrder,CodeValue",
				type).executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			DataRow dr = dt.getDataRow(i);
			String codetype = dr.getString("CodeType");
			String parentcode = dr.getString("ParentCode");
			if (parentcode.equals("System")) {
				continue;
			}
			CacheManager.set(getProviderName(), codetype, dr.get("CodeValue"),
					dr.get("CodeName"));
		}
	}
}

/*
 * com.xdarkness.framework.cache.CodeCache JD-Core Version: 0.6.0
 */