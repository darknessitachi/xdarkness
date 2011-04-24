package com.xdarkness.cms.stat.report;

import java.util.Date;

import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;

public class ClientReport extends Page {
	public void getChartData() {
		String type = $V("Type");
		Date start = DateUtil.parse("1970-01-01");
		Date end = new Date();
		DataTable dt = getClientData(ApplicationPage.getCurrentSiteID(), type,
				start, end);
		String xml = ChartUtil.getPie3DChart(dt, 8, 1.0D);
		$S("Data", xml);
	}

	public static void dgOSDataBind(DataGridAction dga) {
		dga.bindData(getDataGridData(ApplicationPage.getCurrentSiteID(), "OS"));
	}

	public static void dgBrowserDataBind(DataGridAction dga) {
		dga
				.bindData(getDataGridData(ApplicationPage.getCurrentSiteID(),
						"Browser"));
	}

	public static void dgLanguageDataBind(DataGridAction dga) {
		dga
				.bindData(getDataGridData(ApplicationPage.getCurrentSiteID(),
						"Language"));
	}

	public static void dgColorDepthDataBind(DataGridAction dga) {
		dga.bindData(getDataGridData(ApplicationPage.getCurrentSiteID(),
				"ColorDepth"));
	}

	public static void dgScreenDataBind(DataGridAction dga) {
		dga.bindData(getDataGridData(ApplicationPage.getCurrentSiteID(), "Screen"));
	}

	public static void dgFlashVersionDataBind(DataGridAction dga) {
		dga.bindData(getDataGridData(ApplicationPage.getCurrentSiteID(),
				"FlashVersion"));
	}

	public static void dgJavaEnabledDataBind(DataGridAction dga) {
		dga.bindData(getDataGridData(ApplicationPage.getCurrentSiteID(),
				"JavaEnabled"));
	}

	public static void dgCookieEnabledDataBind(DataGridAction dga) {
		dga.bindData(getDataGridData(ApplicationPage.getCurrentSiteID(),
				"CookieEnabled"));
	}

	private static DataTable getDataGridData(long siteID, String subtype) {
		Date start = DateUtil.parse("1970-01-01");
		Date end = new Date();
		DataTable dt = getClientData(ApplicationPage.getCurrentSiteID(), subtype,
				start, end);
		ReportUtil.addTrend(dt, "Client", subtype);
		ReportUtil.computeRate(dt, subtype, "Rate");
		dt.sort("Rate");
		ReportUtil.addSuffix(dt, "Rate", "%");
		ReportUtil.addTotal(dt, new String[] { subtype });
		dt.set(0, "Rate", "100.00%");
		return dt;
	}

	private static DataTable getClientData(long siteID, String subtype,
			Date start, Date end) {
		if (!XString.verify(subtype, "String")) {
			return null;
		}
		String period1 = DateUtil.toString(start, "yyyyMM");
		String period2 = DateUtil.toString(end, "yyyyMM");
		QueryBuilder qb = new QueryBuilder(
				"select * from ZCStatItem where SiteID=? and Type='Client' and SubType=? and Period>=? and Period<=? order by Period desc");
		qb.add(siteID);
		qb.add(subtype);
		qb.add(period1);
		qb.add(period2);
		DataTable dt = qb.executeDataTable();
		dt = ReportUtil.toItemTable(dt, start, end, true);
		if (subtype.equals("JavaEnabled")) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (dt.getString(i, "Item").equals("true"))
					dt.set(i, "Item", "支持Applet");
				else {
					dt.set(i, "Item", "不支持Applet");
				}
			}
		}
		if (subtype.equals("CookieEnabled")) {
			for (int i = 0; i < dt.getRowCount(); i++) {
				if (dt.getString(i, "Item").equals("true"))
					dt.set(i, "Item", "允许Cookie");
				else {
					dt.set(i, "Item", "不允许Cookie");
				}
			}
		}
		if ((subtype.equals("Language")) && (dt.getRowCount() > 8)) {
			ReportUtil.prepareForPie3D(dt, 8);
		}
		return dt;
	}
}

/*
 * com.xdarkness.cms.stat.report.ClientReport JD-Core Version: 0.6.0
 */