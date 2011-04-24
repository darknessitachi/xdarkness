package com.xdarkness.cms.stat.report;

import com.xdarkness.cms.dataservice.Advertise;
import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class TopReport extends Page {
	public static void dgArticleDataBind(DataGridAction dga) {
		String id = dga.getParam("ID");
		String code = null;
		if (XString.isEmpty(id))
			code = "";
		else {
			code = CatalogUtil.getInnerCode(id);
		}
		dga.bindData(getTopArticleHitData(code, dga.getPageSize(), dga
				.getPageIndex()));
	}

	public static void dgImageDataBind(DataGridAction dga) {
		String id = dga.getParam("ID");
		String code = null;
		if (XString.isEmpty(id))
			code = "";
		else {
			code = CatalogUtil.getInnerCode(id);
		}
		dga.bindData(getTopImageHitData(code, dga.getPageSize(), dga
				.getPageIndex()));
	}

	public static void dgVideoDataBind(DataGridAction dga) {
		String id = dga.getParam("ID");
		String code = null;
		if (XString.isEmpty(id))
			code = "";
		else {
			code = CatalogUtil.getInnerCode(id);
		}
		dga.bindData(getTopVideoHitData(code, dga.getPageSize(), dga
				.getPageIndex()));
	}

	public static void dgADDataBind(DataGridAction dga) {
		DataTable dt = getTopADData(dga.getParam("PositionID"));
		ReportUtil.addTrend(dt, "Leaf", "AD", "ID");
		dga.bindData(dt);
	}

	public static DataTable getTopArticleHitData(String catalogInnerCode,
			int pageSize, int pageIndex) {
		QueryBuilder qb = new QueryBuilder(
				"select ID,CatalogInnerCode,Title,HitCount,StickTime,AddUser from ZCArticle where SiteID=? and CatalogInnerCode like '"
						+ catalogInnerCode + "%' order by HitCount desc",
				ApplicationPage.getCurrentSiteID());
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
		dt.insertColumn("CatalogInnerCodeName");
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "CatalogInnerCodeName", CatalogUtil.getNameByInnerCode(dt
					.getString(i, "CatalogInnerCode")));
		}
		return dt;
	}

	public static DataTable getTopImageHitData(String catalogInnerCode,
			int pageSize, int pageIndex) {
		QueryBuilder qb = new QueryBuilder(
				"select ID,CatalogInnerCode,Name,HitCount,StickTime,AddUser from ZCImage where SiteID=? and CatalogInnerCode like '"
						+ catalogInnerCode + "%' order by HitCount desc",
				ApplicationPage.getCurrentSiteID());
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
		dt.insertColumn("CatalogInnerCodeName");
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "CatalogInnerCodeName", CatalogUtil.getNameByInnerCode(dt
					.getString(i, "CatalogInnerCode")));
		}
		return dt;
	}

	public static DataTable getTopVideoHitData(String catalogInnerCode,
			int pageSize, int pageIndex) {
		QueryBuilder qb = new QueryBuilder(
				"select ID,CatalogInnerCode,Name,HitCount,StickTime,AddUser from ZCVideo where SiteID=? and CatalogInnerCode like '"
						+ catalogInnerCode + "%' order by HitCount desc",
				ApplicationPage.getCurrentSiteID());
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
		dt.insertColumn("CatalogInnerCodeName");
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "CatalogInnerCodeName", CatalogUtil.getNameByInnerCode(dt
					.getString(i, "CatalogInnerCode")));
		}
		return dt;
	}

	public static DataTable getTopADData(String positionID) {
		QueryBuilder qb = new QueryBuilder(
				"select ID,PositionID,ADType,ADName,HitCount from ZCAdvertisement where SiteID=?");
		qb.add(ApplicationPage.getCurrentSiteID());
		if (XString.isNotEmpty(positionID)) {
			qb.append(" and PositionID=?", positionID);
		}
		qb.append(" order by OrderFlag desc,HitCount desc");
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0) {
			dt.decodeColumn("ADType", Advertise.ADTypes);
			Mapx map = new QueryBuilder(
					"select ID,PositionName from ZCADPosition where SiteID=?",
					ApplicationPage.getCurrentSiteID()).executeDataTable().toMapx(
					0, 1);
			dt.decodeColumn("PositionID", map);
		}
		return dt;
	}

	public static DataTable getADPositionList(Mapx params) {
		QueryBuilder qb = new QueryBuilder(
				"select ID,PositionName from ZCADPosition where SiteID=?",
				ApplicationPage.getCurrentSiteID());
		return qb.executeDataTable();
	}
}

/*
 * com.xdarkness.cms.stat.report.TopReport JD-Core Version: 0.6.0
 */