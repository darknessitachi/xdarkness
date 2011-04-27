package com.xdarkness.cms.document;

import com.xdarkness.framework.jaf.controls.HtmlTable;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.orm.data.DataTableUtil;
import com.xdarkness.framework.sql.QueryBuilder;

public class ArticleDeployCatalog extends Page {
	public static void articleDialogDataBind(DataGridAction dga) {
		String innerCode = dga.getParam("CatalogInnerCode");
		String data = new QueryBuilder(
				"select targetCatalog from ZCInnerDeploy where CatalogInnerCode=?",
				innerCode).executeString();
		DataTable dt = null;
		if (XString.isEmpty(data)) {
			dt = new DataTable();
			dt.insertColumn("ServerAddr");
			dt.insertColumn("SiteID");
			dt.insertColumn("SiteName");
			dt.insertColumn("CatalogID");
			dt.insertColumn("CatalogName");
			dt.insertColumn("Password");
		} else {
			dt = DataTableUtil.txtToDataTable(data, "", "\t", "\n");
		}
		if (dt.getDataColumn("MD5") == null) {
			dt.insertColumn("MD5");
		}
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "MD5", XString.md5Hex(dt.getString(i, "ServerAddr")
					+ "," + dt.getString(i, "SiteID") + ","
					+ dt.getString(i, "CatalogID")));
		}
		dga.bindData(dt);
		HtmlTable table = dga.getTable();
		for (int i = 1; i < table.getChildren().size(); i++)
			table.getTR(i).removeAttribute("onclick");
	}
}

/*
 * com.xdarkness.cms.document.ArticleDeployCatalog JD-Core Version: 0.6.0
 */