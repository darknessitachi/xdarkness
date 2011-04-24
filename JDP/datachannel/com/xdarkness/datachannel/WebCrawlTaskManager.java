package com.xdarkness.datachannel;

import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.ConfigEanbleTaskManager;
import com.xdarkness.schema.ZCWebGatherSchema;
import com.xdarkness.search.crawl.CrawlConfig;
import com.xdarkness.search.crawl.Crawler;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class WebCrawlTaskManager extends ConfigEanbleTaskManager {
	public static final String CODE = "WebCrawl";
	Mapx runningMap = new Mapx();

	public boolean isRunning(long id) {
		int r = this.runningMap.getInt(new Long(id));
		return r != 0;
	}

	public Mapx getConfigEnableTasks() {
		DataTable dt = null;
		if (User.getCurrent() != null)
			dt = new QueryBuilder(
					"select id,name from ZCWebGather where siteid=?",
					ApplicationPage.getCurrentSiteID()).executeDataTable();
		else {
			dt = new QueryBuilder("select id,name from ZCWebGather")
					.executeDataTable();
		}
		return dt.toMapx(0, 1);
	}

	public void execute(long id) {
		this.runningMap.put(new Long(id), OperateType.INSERT);
		final ZCWebGatherSchema wg = new ZCWebGatherSchema();
		wg.setID(id);
		if (wg.fill()) {
			if ("N".equals(wg.getStatus())) {
				return;
			}
			Thread thread = new Thread() {

				public void run() {
					try {
						CrawlConfig cc = new CrawlConfig();
						cc.parse(wg);
						Crawler crawler = new Crawler();
						crawler.setConfig(cc);
						crawler.crawl();
					} finally {
						WebCrawlTaskManager.this.runningMap.remove(new Long(
								wg.getID()));
					}
				}
			};
			thread.start();
		} else {
			System.out.println("没有找到对应的抓取任务.任务ID:" + id);
		}
	}

	public String getCode() {
		return "WebCrawl";
	}

	public String getName() {
		return "Web采集任务";
	}
}

/*
 * com.xdarkness.datachannel.WebCrawlTaskManager JD-Core Version: 0.6.0
 */