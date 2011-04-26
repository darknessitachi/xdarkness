package com.xdarkness.datachannel;

import com.xdarkness.schema.ZCInnerGatherSchema;
import com.xdarkness.framework.Framework;
import com.xdarkness.framework.jaf.JafRequest;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.orm.data.DataTableUtil;
import com.xdarkness.framework.util.Mapx;

public class SynchUtil {
	public static void gatherRemote(long id, LongTimeTask task) {
		ZCInnerGatherSchema gather = new ZCInnerGatherSchema();
		gather.setID(id);
		gather.fill();
		String data = gather.getTargetCatalog();
		DataTable dt = DataTableUtil.txtToDataTable(data, "\t", "\n");
		for (int i = 0; i < dt.getRowCount(); i++) {
			String ServerAddr = dt.getString(i, "ServerAddr");
			String SiteID = dt.getString(i, "SiteID");
			String SiteName = dt.getString(i, "SiteName");
			String CatalogID = dt.getString(i, "CatalogID");
			String CatalogName = dt.getString(i, "CatalogName");
			String Password = dt.getString(i, "Password");
			if (task != null) {
				task.setCurrentInfo("正在采集站点 " + SiteName + " 下的栏目 "
						+ CatalogName);
			}
			JafRequest Request = new JafRequest();
			Request.put("SiteID", SiteID);
			Request.put("CatalogID", CatalogID);
			Request.put("Password", Password);
			String LastAdded = "0";
			String LastModified = "0";
			if (dt.getDataColumn("LastAdded") != null)
				LastAdded = dt.getString(i, "LastAdded");
			else {
				dt.insertColumn("LastAdded");
			}
			if (dt.getDataColumn("LastModified") != null)
				LastModified = dt.getString(i, "LastModified");
			else {
				dt.insertColumn("LastModified");
			}
			Mapx map = Framework.callRemoteMethod(ServerAddr,
					"com.xdarkness.datachannel.SynchService.sendData", Request);
			DataTable articles = (DataTable) map.get("Data");
			DataTable catalogs = (DataTable) map.get("Catalog");
			if (task != null) {
				task.setCurrentInfo("正在保存站点 " + SiteName + " 下的栏目 "
						+ CatalogName + " 下的数据");
			}

			dt.set(i, "LastAdded", LastAdded);
			dt.set(i, "LastModified", LastModified);
		}
		gather.setTargetCatalog(dt.toString());
		gather.update();
	}

	public static void deployRemote(long id, LongTimeTask task) {
	}

	public static void main(String[] args) {
	}
}

/*
 * com.xdarkness.datachannel.SynchUtil JD-Core Version: 0.6.0
 */