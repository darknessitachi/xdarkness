package com.xdarkness.cms.datachannel;

import java.sql.SQLException;

import com.xdarkness.schema.ZCArticleSet;
import com.xdarkness.framework.orm.SchemaSet;

public class PublishMonitor {
	public static ZCArticleSet set = new ZCArticleSet();

	public static PublishThread thread = null;

	public static long interval = 1000L;

	private static Object mutex = new Object();

	public void init() {
		String publishInterval = Config.getValue("PublishInterval");
		if (XString.isDigit(publishInterval)) {
			interval = Long.parseLong(publishInterval);
		}
		synchronized (mutex) {
			if (thread == null)
				synchronized (mutex) {
					thread = new PublishThread();
					thread.start();
				}
		}
	}

	public void addJob(SchemaSet newSet) {
		init();

		synchronized (set) {
			set.add(newSet);
		}
	}

	public void clean() {
		synchronized (set) {
			set.clear();
		}
	}

	public void execute(ZCArticleSet newSet) {
		Publisher p = new Publisher();
		try {
			p.publishArticle(newSet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class PublishThread extends Thread {
		PublishThread() {
		}

		public void run() {
			while (true) {
				try {
					sleep(PublishMonitor.interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				ZCArticleSet newSet = new ZCArticleSet();
				synchronized (PublishMonitor.set) {
					newSet.add(PublishMonitor.set);
					PublishMonitor.set.clear();
				}

				PublishMonitor.this.execute(newSet);
			}
		}
	}
}

/*
 * com.xdarkness.cms.datachannel.PublishMonitor JD-Core Version: 0.6.0
 */