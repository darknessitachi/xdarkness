package com.xdarkness.framework.clustering;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.xdarkness.framework.Config;
import com.xdarkness.framework.User.UserData;
import com.xdarkness.framework.clustering.server.SocketClusteringServer;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;

public class Clustering {
	private static long LastUpdateTime;
	private static long RefershPeriod = 60000L;
	private static Mapx configMap;
	private static Server[] Servers;
	private static Object mutex = new Object();

	private static void init() {
		if (System.currentTimeMillis() - LastUpdateTime > RefershPeriod)
			synchronized (mutex) {
				if (System.currentTimeMillis() - LastUpdateTime > RefershPeriod) {
					String path = Config.getContextRealPath();
					File f = new File(path + "WEB-INF/classes/clustering.xml");
					if (!f.exists()) {
						return;
					}
					SAXReader reader = new SAXReader(false);
					try {
						Document doc = reader.read(f);
						Element root = doc.getRootElement();
						List elements = root.elements();
						configMap = new Mapx();
						for (int i = 0; i < elements.size(); i++) {
							Element ele = (Element) elements.get(i);
							if (ele.getName().equalsIgnoreCase("config")) {
								configMap.put(ele.attributeValue("name"), ele
										.getTextTrim());
							} else {
								List serverList = ele.elements("server");
								Server[] arr = new Server[serverList.size()];
								for (int j = 0; j < serverList.size(); j++) {
									Element s = (Element) serverList.get(j);
									Server server = new Server();
									server.URL = s.attributeValue("url");
									server.Weight = Integer.parseInt(s
											.attributeValue("weight"));
									server.RetryCount = Integer.parseInt(s
											.attributeValue("retrycount"));
									server.Timeout = Integer.parseInt(s
											.attributeValue("timeout"));
									arr[j] = server;
								}
								Servers = arr;
							}
						}
						tryInitServer();
						if (LastUpdateTime == 0L)
							LogUtil.info("----" + Config.getAppCode() + "("
									+ Config.getAppName()
									+ "): Clustering Initialized----");
					} catch (Exception e) {
						e.printStackTrace();
						LogUtil.info("----" + Config.getAppCode() + "("
								+ Config.getAppName()
								+ "): Clustering Failure----");
					}
				}
				LastUpdateTime = System.currentTimeMillis();
			}
	}

	private static void tryInitServer() {
		String flag = configMap.getString("UserAsClusteringServer");
		if (!"true".equalsIgnoreCase(flag)) {
			return;
		}
		String type = configMap.getString("ClusteringServerType");
		String portStr = configMap.getString("SocketPort");
		if ("Socket".equalsIgnoreCase(type))
			try {
				final int port = Integer.parseInt(portStr);
				new Thread() {

					public void run() {
						SocketClusteringServer.start(port);
					}
				}.start();
			} catch (Exception e) {
				throw new RuntimeException("clustering.xml中配置的SocketPort不是数字："
						+ portStr);
			}
	}

	public static boolean isClustering() {
		init();
		if (configMap == null) {
			return false;
		}
		return "true".equals(configMap.getString("ClusteringEnable"));
	}

	public static boolean isClusteringServer() {
		init();
		if (configMap == null) {
			return false;
		}
		return "true".equals(configMap.getString("UserAsClusteringServer"));
	}

	public static void put(String key, String value) {
		init();
	}

	public static void putObject(String key, Object value) {
		init();
	}

	public static String get(String key) {
		init();
		return null;
	}

	public static Object getObject(String key) {
		init();
		return null;
	}

	public static boolean containsKey(String key) {
		init();
		return false;
	}

	public static void remove(String key) {
		init();
	}

	public static void putMapx(String key, Mapx map) {
		init();
	}

	public static Mapx getMapx(String key) {
		init();
		return null;
	}

	public static String[] getAllKeys() {
		init();
		return null;
	}

	public static void cacheUser(UserData user) {
		init();
	}

	public static class Server {
		public String URL;
		public int RetryCount;
		public int Timeout;
		public int Weight;
		public boolean isAlive;
	}
}
