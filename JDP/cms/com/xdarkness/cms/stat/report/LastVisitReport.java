package com.xdarkness.cms.stat.report;

import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.framework.jaf.controls.DataListAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;

public class LastVisitReport extends Page {
	public static void dg1DataBind(DataListAction dga) {
		QueryBuilder qb = new QueryBuilder(
				"select * from ZCVisitLog where SiteID=? order by AddTime desc",
				ApplicationPage.getCurrentSiteID());
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dt.insertColumn("RowNo");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (XString.isEmpty(dt.getString(i, "District"))) {
				dt.set(i, "District", "未知地区");
			}
			if (XString.isEmpty(dt.getString(i, "Language"))) {
				dt.set(i, "Language", "未知");
			}
			if (XString.isEmpty(dt.getString(i, "FlashVersion"))) {
				dt.set(i, "Language", "未知");
			}
			if (XString.isEmpty(dt.getString(i, "Referer"))) {
				dt.set(i, "Referer", "无(直接进入)");
			}
			dt.set(i, "RowNo", dga.getPageSize() * dga.getPageIndex() + i + 1);
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}
}

/*
 * com.xdarkness.cms.stat.report.LastVisitReport JD-Core Version: 0.6.0
 */