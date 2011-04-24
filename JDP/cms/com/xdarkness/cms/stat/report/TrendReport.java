package com.xdarkness.cms.stat.report;

import java.util.Date;

import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class TrendReport extends Page {
	public static Mapx init(Mapx param) {
		return param;
	}

	public void getChartData() {
		Date start = DateUtil.parse($V("StartDate"));
		Date end = DateUtil.parse($V("EndDate"));
		String item = $V("Item");
		DataTable dt = getTreadData(start, end, $V("Type"), $V("SubType"), item);
		dt.deleteRow(0);
		dt.sort("Date", "asc");
		if (dt.getColCount() > 1) {
			if (("Client".equals($V("Type")))
					&& ("District".equals($V("SubType")))) {
				item = new QueryBuilder(
						"select Name from ZDDistrict where Code=?", item)
						.executeString();
			}
			dt.getDataColumn(1).setColumnName(item);
		}
		String xml = ChartUtil.getLine2DChart(dt, 8);
		$S("Data", xml);
	}

	public static DataTable getTreadData(Date start, Date end, String type,
			String subtype, String item) {
		String period1 = DateUtil.toString(start, "yyyyMM");
		String period2 = DateUtil.toString(end, "yyyyMM");
		DataTable dt = null;
		if ((type.equals("Client")) && (subtype.equals("District"))) {
			QueryBuilder qb = new QueryBuilder(
					"select * from ZCStatItem where SiteID=? and Type=? and SubType=? and Period>=? and Period<=? and Item like ?");

			qb.add(ApplicationPage.getCurrentSiteID());
			qb.add(type);
			qb.add(subtype);
			qb.add(period1);
			qb.add(period2);
			if (item.equals("000000")) {
				qb
						.setSQL("select * from ZCStatItem where SiteID=? and Type=? and SubType=? and Period>=? and Period<=? and (Item not like '000%' or Item='000000')");
			} else if (item.endsWith("0000"))
				qb.add(item.substring(0, 2) + "%");
			else {
				qb.add(item);
			}

			dt = qb.executeDataTable();
			dt = ReportUtil.toDateTable(dt, start, end);
			for (int i = 0; i < dt.getRowCount(); i++) {
				int total = 0;
				for (int j = 1; j < dt.getColCount(); j++) {
					total += dt.getInt(i, j);
				}
				dt.set(i, 1, total);
			}
			for (int j = 2; j < dt.getColCount(); j++) {
				dt.deleteColumn(j);
			}
			dt.getDataColumn(1).setColumnName("000000");
		} else {
			QueryBuilder qb = new QueryBuilder(
					"select * from ZCStatItem where SiteID=? and Type=? and SubType=? and Item=? and Period>=? and Period<=?");

			qb.add(ApplicationPage.getCurrentSiteID());
			qb.add(type);
			qb.add(subtype);
			qb.add(item);
			qb.add(period1);
			qb.add(period2);
			dt = qb.executeDataTable();
			dt = ReportUtil.toDateTable(dt, start, end);
		}
		return dt;
	}
}
