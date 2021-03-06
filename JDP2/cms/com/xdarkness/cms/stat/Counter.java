package com.xdarkness.cms.stat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.ServletUtil;

public class Counter {
	private static Mapx articleMap = new Mapx(10000);
	private static Mapx videoMap = new Mapx(10000);
	private static Mapx imageMap = new Mapx(10000);
	private static Mapx totalMap = new Mapx();
	private static Mapx todayMap = new Mapx();

	public static synchronized int getArticleHitCount(long leafID) {
		Object o = articleMap.get(new Long(leafID));
		if (o == null) {
			int c = new QueryBuilder(
					"select HitCount from ZCArticle where ID=?", leafID)
					.executeInt();
			articleMap.put(new Long(leafID), new Integer(c));
			return c;
		}
		return ((Integer) o).intValue();
	}

	public static synchronized int getImageHitCount(long leafID) {
		Object o = imageMap.get(new Long(leafID));
		if (o == null) {
			int c = new QueryBuilder("select HitCount from ZCImage where ID=?",
					leafID).executeInt();
			imageMap.put(new Long(leafID), new Integer(c));
			return c;
		}
		return ((Integer) o).intValue();
	}

	public static synchronized int getVideoHitCount(long leafID) {
		Object o = videoMap.get(new Long(leafID));
		if (o == null) {
			int c = new QueryBuilder("select HitCount from ZCVideo where ID=?",
					leafID).executeInt();
			videoMap.put(new Long(leafID), new Integer(c));
			return c;
		}
		return ((Integer) o).intValue();
	}

	public static synchronized int getCount(String type, long leafID) {
		if ("Article".equalsIgnoreCase(type))
			return getArticleHitCount(leafID);
		if ("Image".equalsIgnoreCase(type))
			return getImageHitCount(leafID);
		if ("Video".equalsIgnoreCase(type)) {
			return getVideoHitCount(leafID);
		}
		return 0;
	}

	public static synchronized void add(String type, long leafID) {
		if ("Article".equalsIgnoreCase(type)) {
			Object o = articleMap.get(new Long(leafID));
			if (o == null) {
				int c = new QueryBuilder(
						"select HitCount from ZCArticle where ID=?", leafID)
						.executeInt();
				articleMap.put(new Long(leafID), new Integer(c + 1));
			} else {
				articleMap.put(new Long(leafID), new Integer(((Integer) o)
						.intValue() + 1));
			}
		} else if ("Image".equalsIgnoreCase(type)) {
			Object o = imageMap.get(new Long(leafID));
			if (o == null) {
				int c = new QueryBuilder(
						"select HitCount from ZCImage where ID=?", leafID)
						.executeInt();
				imageMap.put(new Long(leafID), new Integer(c + 1));
			} else {
				imageMap.put(new Long(leafID), new Integer(((Integer) o)
						.intValue() + 1));
			}
		} else if ("Video".equalsIgnoreCase(type)) {
			Object o = videoMap.get(new Long(leafID));
			if (o == null) {
				int c = new QueryBuilder(
						"select HitCount from ZCVideo where ID=?", leafID)
						.executeInt();
				videoMap.put(new Long(leafID), new Integer(c + 1));
			} else {
				videoMap.put(new Long(leafID), new Integer(((Integer) o)
						.intValue() + 1));
			}
		}
	}

	public static void deal(HttpServletRequest request,
			HttpServletResponse response) {
		Mapx map = ServletUtil.getParameterMap(request, false);
		String type = map.getString("Type");
		try {
			long id = Long.parseLong(map.getString("ID"));
			long count = 0L;
			if ("Total".equalsIgnoreCase(type))
				count = getTotalHitCount(id);
			else if ("Today".equalsIgnoreCase(type))
				count = getTodayHitCount(id);
			else {
				count = getCount(type, id);
			}
			response.getWriter().print("document.write(" + count + ");");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static synchronized int getTotalHitCount(long siteID) {
		Object o = totalMap.get(new Long(siteID));
		if (o == null) {
			int c = initTotalHitCount(siteID);
			totalMap.put(new Long(siteID), new Integer(c));
			return c;
		}
		return ((Integer) o).intValue();
	}

	public static synchronized long getTodayHitCount(long siteID) {
		Object o = todayMap.get(new Long(siteID));
		if (o == null) {
			int c = initTodayHitCount(siteID);
			todayMap.put(new Long(siteID), new Integer(c));
			return c;
		}
		return ((Integer) o).intValue();
	}

	public static synchronized void addTotalHitCount(long siteID) {
		Object o = totalMap.get(new Long(siteID));
		if (o == null) {
			int c = initTotalHitCount(siteID);
			totalMap.put(new Long(siteID), new Integer(c + 1));
		} else {
			totalMap.put(new Long(siteID), new Integer(
					((Integer) o).intValue() + 1));
		}
	}

	public static synchronized void addTodayHitCount(long siteID) {
		Object o = todayMap.get(new Long(siteID));
		if (o == null) {
			int c = initTodayHitCount(siteID);
			todayMap.put(new Long(siteID), new Integer(c + 1));
		} else {
			todayMap.put(new Long(siteID), new Integer(
					((Integer) o).intValue() + 1));
		}
	}

	public static synchronized void clearTodayHitCount(long siteID) {
		todayMap.put(new Long(siteID), new Integer(0));
	}

	private static int initTotalHitCount(long siteID) {
		DataTable dt = new QueryBuilder(
				"select * from ZCStatItem where SiteID=? and Type='Global' and SubType='PV'",
				siteID).executeDataTable();
		int c = 0;
		String month = DateUtil.getCurrentDate("yyyyMM");
		int d = DateUtil.getDayOfMonth();
		for (int i = 0; i < dt.getRowCount(); i++) {
			for (int j = 5; j < dt.getColCount(); j++) {
				if ((dt.getString(i, "Period").equals(month)) && (i - 4 == d)) {
					continue;
				}
				c += dt.getInt(i, j);
			}
		}
		c = (int) (c + getTodayHitCount(siteID));
		return c;
	}

	private static int initTodayHitCount(long siteID) {
		String month = DateUtil.getCurrentDate("yyyyMM");
		DataTable dt = new QueryBuilder(
				"select * from ZCStatItem where SiteID=? and Type='Global' and Period=? and SubType='PV'",
				siteID, month).executeDataTable();
		return dt.getInt(0, DateUtil.getDayOfMonth() + 4);
	}
}

/*
 * com.xdarkness.cms.stat.Counter JD-Core Version: 0.6.0
 */