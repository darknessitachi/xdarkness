package com.xdarkness.framework.schedule;

import java.io.File;
import java.util.List;
import java.util.Timer;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.xdarkness.framework.Config;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.XString;

public class CronManager {
	private Timer mTimer;
	private CronMonitor mMonitor;
	private Mapx map = new Mapx();
	private static CronManager instance;
	private long interval;

	public static synchronized CronManager getInstance() {
		if (instance == null) {
			instance = new CronManager();
		}
		return instance;
	}

	public Mapx getManagers() {
		return this.map;
	}

	private CronManager() {
		init();
	}

	public void init() {
		if (!Config.isInstalled) {
			return;
		}
		loadConfig();
		this.mTimer = new Timer(true);
		this.mMonitor = new CronMonitor();
		this.mTimer.schedule(this.mMonitor, 0L, this.interval);
		LogUtil.info("----" + Config.getAppCode() + "(" + Config.getAppName()
				+ "): CronManager Initialized----");
	}

	private void loadConfig() {
		String path = Config.getContextRealPath()
				+ "WEB-INF/classes/framework.xml";
		SAXReader reader = new SAXReader(false);
		try {
			Document doc = reader.read(new File(path));
			Element root = doc.getRootElement();
			Element cron = root.element("cron");
			List types = cron.elements();
			GeneralTaskManager gtm = new GeneralTaskManager();
			this.map.put(gtm.getCode(), gtm);
			for (int i = 0; i < types.size(); i++) {
				Element type = (Element) types.get(i);
				String tag = type.getName();
				if (tag.equals("config")) {
					String name = type.attributeValue("name");
					String value = type.getText();
					if (name.equals("RefreshInterval"))
						this.interval = Long.parseLong(value);
				} else if (tag.equals("taskManager")) {
					String className = type.attributeValue("class");
					try {
						Object o = Class.forName(className).newInstance();
						if ((o instanceof AbstractTaskManager)) {
							AbstractTaskManager ctm = (AbstractTaskManager) o;
							this.map.put(ctm.getCode(), ctm);
							continue;
						}
						throw new RuntimeException("指定的类" + className
								+ "不是CronTaskManager的子类.");
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				} else if (tag.equals("task")) {
					String className = type.attributeValue("class");
					String time = type.attributeValue("time");
					try {
						Object o = Class.forName(className).newInstance();
						if ((o instanceof GeneralTask)) {
							GeneralTask gt = (GeneralTask) Class.forName(
									className).newInstance();
							if (XString.isEmpty(time)) {
								throw new RuntimeException("指定的任务类" + className
										+ "没有配置cron表达式.");
							}
							gt.cronExpression = time;
							gtm.add(gt);
						} else {
							throw new RuntimeException("指定的类" + className
									+ "不是GeneralTask的子类.");
						}
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public Mapx getTaskTypes() {
		Mapx rmap = new Mapx();
		Object[] vs = this.map.valueArray();
		for (int i = 0; i < this.map.size(); i++) {
			AbstractTaskManager ctm = (AbstractTaskManager) vs[i];
			if ((ctm instanceof GeneralTaskManager)) {
				continue;
			}
			rmap.put(ctm.getCode(), ctm.getName());
		}
		return rmap;
	}

	public Mapx getConfigEnableTasks(String code) {
		AbstractTaskManager ctm = (AbstractTaskManager) this.map.get(code);
		if (ctm == null) {
			return null;
		}
		return ctm.getConfigEnableTasks();
	}

	public AbstractTaskManager getCronTaskManager(String code) {
		return (AbstractTaskManager) this.map.get(code);
	}

	public void destory() {
		if (this.mMonitor != null) {
			this.mMonitor.destory();
			this.mTimer.cancel();
		}
	}

	public long getInterval() {
		return this.interval;
	}
}