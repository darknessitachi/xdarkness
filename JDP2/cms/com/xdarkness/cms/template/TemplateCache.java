package com.xdarkness.cms.template;

import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class TemplateCache {
	private static ThreadLocal mapLocal = new ThreadLocal();

	public static DataTable getDataTable(String key) {
		if (XString.isEmpty(key)) {
			return null;
		}

		Mapx map = getCurrent();
		if (map == null) {
			map = new Mapx();
		}
		Object obj = map.get(key);
		DataTable dt = null;
		if (obj != null) {
			dt = (DataTable) obj;
		}
		return dt;
	}

	public static void setDataTable(String key, DataTable dt) {
		Mapx map = getCurrent();
		if (map == null) {
			map = new Mapx();
			setCurrent(map);
		}
		map.put(key, dt);
	}

	public static QueryBuilder getQueryBuilder(String key) {
		if (XString.isEmpty(key)) {
			return null;
		}

		Mapx map = getCurrent();
		if (map == null) {
			map = new Mapx();
		}
		Object obj = map.get(key);
		QueryBuilder qb = null;
		if (obj != null) {
			qb = (QueryBuilder) obj;
		}
		return qb;
	}

	public static void setQueryBuilder(String key, QueryBuilder qb) {
		Mapx map = getCurrent();
		if (map == null) {
			map = new Mapx();
			setCurrent(map);
		}
		map.put(key, qb);
	}

	public static void clear() {
		Mapx map = getCurrent();
		if (map != null) {
			map.clear();
			setCurrent(map);
		}
	}

	public static void setCurrent(Mapx map) {
		mapLocal.set(map);
	}

	public static Mapx getCurrent() {
		return (Mapx) mapLocal.get();
	}
}

/*
 * com.xdarkness.cms.template.TemplateCache JD-Core Version: 0.6.0
 */