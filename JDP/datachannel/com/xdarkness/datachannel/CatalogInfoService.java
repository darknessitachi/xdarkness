package com.xdarkness.datachannel;

import com.xdarkness.framework.Framework;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Ajax;
import com.xdarkness.framework.jaf.JafRequest;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class CatalogInfoService extends Ajax {
	public void getRemoteSiteInfo() {
		String url = $V("ServerAddr");
		if (url.equalsIgnoreCase("localhost")) {
			getSiteInfo();
		} else {
			Mapx map = Framework.callRemoteMethod(url,
					"com.xdarkness.datachannel.CatalogInfoService.getSiteInfo",
					this.request);
			if (map == null) {
				return;
			}
			Object[] ks = map.keyArray();
			for (int i = 0; i < ks.length; i++)
				$S(ks[i].toString(), map.get(ks[i]));
		}
	}

	public void getRemoteCatalogInfo() {
		String url = $V("ServerAddr");
		if (url.equalsIgnoreCase("localhost")) {
			getCatalogInfo();
		} else {
			Mapx map = Framework.callRemoteMethod(url,
					"com.xdarkness.datachannel.CatalogInfoService.getCatalogInfo",
					this.request);
			if (map == null) {
				return;
			}
			Object[] ks = map.keyArray();
			for (int i = 0; i < ks.length; i++)
				$S(ks[i].toString(), map.get(ks[i]));
		}
	}

	public void getSiteInfo() {
		String type = $V("Type");
		if ("Gather".equalsIgnoreCase(type))
			type = "Gather";
		else {
			type = "Deploy";
		}
		if (("admin".equalsIgnoreCase(User.getUserName())) && (User.isLogin())) {
			DataTable dt = new QueryBuilder(
					"select ID,Name from ZCSite order by orderFlag")
					.executeDataTable();
			$S("SiteTable", dt);
		} else {
			DataTable dt = new QueryBuilder(
					"select ID,Name from ZCSite where exists (select 1 from ZCCatalogConfig where SiteID=ZCSite.ID and AllowInner"
							+ type + "='Y')  order by orderFlag")
					.executeDataTable();
			$S("SiteTable", dt);
		}
	}

	public void getCatalogInfo() {
		String type = $V("Type");
		if ("Gather".equalsIgnoreCase(type))
			type = "Gather";
		else {
			type = "Deploy";
		}
		String siteID = $V("SiteID");
		DataTable dt = null;
		if (("admin".equalsIgnoreCase(User.getUserName())) && (User.isLogin()))
			dt = new QueryBuilder(
					"select ID,Name,ParentID,TreeLevel from ZCCatalog where SiteID=? and Type=?  order by orderFlag,innercode",
					siteID, 1).executeDataTable();
		else {
			dt = new QueryBuilder(
					"select ID,Name,ParentID,TreeLevel from ZCCatalog where SiteID=? and Type=? and exists (select 1 from ZCCatalogConfig where CatalogID=ZCCatalog.ID and AllowInner"
							+ type + "='Y')  order by orderFlag,innercode",
					siteID, 1).executeDataTable();
		}
		dt = DataGridAction.sortTreeDataTable(dt, "ID", "ParentID");
		for (int i = 0; i < dt.getRowCount(); i++) {
			String prefix = "";
			for (int j = 1; j < dt.getInt(i, "TreeLevel"); j++) {
				prefix = prefix + "　";
			}
			dt.set(i, "Name", prefix + dt.getString(i, "Name"));
		}
		$S("CatalogTable", dt);
	}

	public static DataTable getLocalSites(Mapx params) {
		String type = params.getString("Type");
		CatalogInfoService info = new CatalogInfoService();
		JafRequest request = new JafRequest();
		request.put("Type", type);
		info.setRequest(request);
		info.getSiteInfo();
		DataTable dt = info.getResponse().getDataTable("SiteTable");
		return dt;
	}
}

/*
 * com.xdarkness.datachannel.CatalogInfoService JD-Core Version: 0.6.0
 */