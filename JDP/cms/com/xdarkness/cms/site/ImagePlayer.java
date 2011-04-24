package com.xdarkness.cms.site;

import java.util.List;

import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.platform.Priv;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.schema.ZCImagePlayerSchema;
import com.xdarkness.schema.ZCImagePlayerSet;
import com.xdarkness.schema.ZCImageRelaSchema;
import com.xdarkness.schema.ZCImageRelaSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.TreeAction;
import com.xdarkness.framework.jaf.controls.TreeItem;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataRow;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;

public class ImagePlayer extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		String InnerCode = dga.getParam("CatalogInnerCode");
		if ((XString.isEmpty(InnerCode)) || (InnerCode == null)
				|| (InnerCode.equalsIgnoreCase("null"))) {
			InnerCode = "0";
		}
		QueryBuilder qb = new QueryBuilder(
				"select ZCImagePlayer.*,(SELECT Name from ZCCatalog where ZCCatalog.InnerCode = ZCImagePlayer.RelaCatalogInnerCode) as CatalogName from ZCImagePlayer where SiteID=? ",
				ApplicationPage.getCurrentSiteID());
		if (!InnerCode.equals("0")) {
			qb.append(" and RelaCatalogInnerCode = ? ", InnerCode);
		}
		qb.append(" Order by RelaCatalogInnerCode asc ");
		DataTable dt = qb.executeDataTable();
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				if (Priv.getPriv(User.getUserName(), "site", ApplicationPage
						.getCurrentSiteID()+"", "site_manage")) {
					return true;
				}
				DataRow dr = (DataRow) obj;
				String RelaCatalogInnerCode = dr
						.getString("RelaCatalogInnerCode");
				if ("0".equals(RelaCatalogInnerCode)) {
					return Priv.getPriv(User.getUserName(), "site", ApplicationPage
							.getCurrentSiteID()+"", "article_manage");
				}
				return Priv.getPriv(User.getUserName(), "article",
						RelaCatalogInnerCode, "article_modify");
			}
		});
		DataTable newdt = new DataTable(dt.getDataColumns(), null);
		for (int i = dga.getPageIndex() * dga.getPageSize(); (i < dt
				.getRowCount())
				&& (i < (dga.getPageIndex() + 1) * dga.getPageSize());) {
			newdt.insertRow(dt.getDataRow(i));

			i++;
		}

		for (int i = 0; i < newdt.getRowCount(); i++) {
			if (XString.isEmpty(newdt.getString(i, "CatalogName"))) {
				newdt.set(i, "CatalogName", "文档库");
			}
		}
		dga.bindData(newdt);
	}

	public static void treeDataBind(TreeAction ta) {
		Object obj = ta.getParams().get("SiteID");
		String siteID = ApplicationPage.getCurrentSiteID()+"";
		Object typeObj = ta.getParams().get("CatalogType");
		int catalogType = typeObj != null ? Integer
				.parseInt(typeObj.toString()) : 1;
		String parentTreeLevel = ta.getParams().getString("ParentLevel");
		String parentID = ta.getParams().getString("ParentID");
		DataTable dt = null;
		if (ta.isLazyLoad()) {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel>? and innerCode like ? and  exists (select 1 from zcimageplayer where RelaCatalogInnerCode=ZCCatalog.innercode) order by orderflag,innercode");

			qb.add(catalogType);
			qb.add(siteID);
			qb.add(parentTreeLevel);
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			dt = qb.executeDataTable();
		} else {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel-1 <=? and  exists (select 1 from zcimageplayer where RelaCatalogInnerCode=ZCCatalog.innercode) order by orderflag,innercode");

			qb.add(catalogType);
			qb.add(siteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
		}

		String siteName = "文档库";
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return Priv.getPriv(User.getUserName(), "article", dr
						.getString("InnerCode"), "article_manage");
			}
		});
		ta.setRootText(siteName);
		ta.bindData(dt);
		List items = ta.getItemList();
		for (int i = 1; i < items.size(); i++) {
			TreeItem item = (TreeItem) items.get(i);
			if ("Y".equals(item.getData().getString("SingleFlag")))
				item.setIcon("Icons/treeicon11.gif");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCImagePlayerSchema ImagePlayer = new ZCImagePlayerSchema();
		ZCImagePlayerSet set = ImagePlayer.query(new QueryBuilder(
				"where id in (" + ids + ")"));
		trans.add(set, OperateType.DELETE_AND_BACKUP);

		ZCImageRelaSchema imageRela = new ZCImageRelaSchema();
		ZCImageRelaSet imageSet = imageRela.query(new QueryBuilder(
				" where relaid in (" + ids + ") and RelaType = ?",
				"ImagePlayerImage"));
		trans.add(imageSet, OperateType.DELETE_AND_BACKUP);

		if (trans.commit()) {
			this.response.setStatus(1);
			this.response.setMessage("删除成功!");
		} else {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
		}
	}
}

/*
 * com.xdarkness.cms.site.ImagePlayer JD-Core Version: 0.6.0
 */