package com.abigdreamer.java.net;

import java.beans.Introspector;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.LogManager;

import com.abigdreamer.java.net.clustering.server.SocketClusteringServer;
import com.abigdreamer.java.net.jaf.extend.ExtendManager;
import com.abigdreamer.java.net.schedule.CronManager;

public class MainContextListener implements ServletContextListener {
	private CronManager manager;

	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			if (this.manager != null) {
				this.manager.destory();
			}
			SessionListener.clear();

			SocketClusteringServer.stop();

			LogManager.shutdown();
			LogFactory.releaseAll();
			Introspector.flushCaches();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public void contextInitialized(ServletContextEvent arg0) {
		ServletContext sc = arg0.getServletContext();
		Config.configMap.put("System.ContainerInfo", sc.getServerInfo());
		// WebConfig.getJBossInfo();
		Config.init();
		this.manager = CronManager.getInstance();
		ExtendManager.loadConfig();
	}
}
