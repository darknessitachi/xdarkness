package com.xdarkness.cms.dataservice;

import com.xdarkness.cms.api.SearchAPI;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.ConfigEanbleTaskManager;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class FullTextTaskManager extends ConfigEanbleTaskManager {
	public static final String CODE = "IndexMaintenance";
	Mapx runningMap = new Mapx();

	public boolean isRunning(long id) {
		int r = this.runningMap.getInt(new Long(id));
		return r != 0;
	}

	public void execute(final long id) {
		this.runningMap.put(new Long(id), 1);
		new Thread() {

			public void run() {
				try {
					SearchAPI.update(id);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					FullTextTaskManager.this.runningMap.remove(new Long(
							id));
				}
			}
		}.start();
	}

	public Mapx getConfigEnableTasks() {
		DataTable dt = null;
		if (User.getCurrent() != null)
			dt = new QueryBuilder(
					"select id,name from ZCFullText where siteid=?",
					ApplicationPage.getCurrentSiteID()).executeDataTable();
		else {
			dt = new QueryBuilder("select id,name from ZCFullText")
					.executeDataTable();
		}
		return dt.toMapx(0, 1);
	}

	public String getCode() {
		return "IndexMaintenance";
	}

	public String getName() {
		return "索引维护任务";
	}
}

/*
 * com.xdarkness.cms.dataservice.FullTextTaskManager JD-Core Version: 0.6.0
 */