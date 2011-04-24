package com.xdarkness.framework;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.xdarkness.framework.config.SystemConfig;
import com.xdarkness.framework.connection.DBTypes;
import com.xdarkness.framework.connection.XConnectionPoolManager;
import com.xdarkness.framework.license.IProduct;
import com.xdarkness.framework.security.EncryptUtil;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.XString;

/**
 * @author Darkness create on 2010-11-30 上午11:53:18
 * 
 * @version 1.0
 * @since JDF 1.0
 */
public class Config {
	
	protected static String CONFIG_FILE_NAME;
	static {
		CONFIG_FILE_NAME = getClassesPath() + "framework.xml";
	}
	
	public static final String CONTEXT_REAL_PATH;
	static {
		CONTEXT_REAL_PATH = getContextRealPath();
	}
	/**
	 * 获取JDF框架路径
	 * 
	 * @return
	 */
	public static String getJDFClassPath() {
		URL url = Thread.currentThread().getContextClassLoader().getResource(
				"jdf-license.dat");
		return getResourceRealPath(url);
	}
	/**
	 * 获取系统ClassPath
	 * 
	 * @return
	 */
	public static String getClassPath() {
		URL url = Thread.currentThread().getContextClassLoader().getResource(
				"license.dat");
		return getResourceRealPath(url);
	}
	/**
	 * 获取资源路径
	 * @param url
	 * @return
	 */
	private static String getResourceRealPath(URL url) {
		try {
			String path = URLDecoder.decode(url.getPath(), System
					.getProperty("file.encoding"));
			String prefix = "file:/";
			if (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) {

				if (path.startsWith("/")) {
					path = path.substring(1);
				} else if (path.startsWith(prefix)) {
					path = path.substring(prefix.length());
				}
			} else if (path.startsWith(prefix)) {
				path = path.substring(prefix.length() - 1);
			}
			return path.substring(0, path.lastIndexOf("/") + 1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected static Mapx<String, String> configMap = new Mapx<String, String>();

	private static long LastUpdateTime = 0L;

	private static long RefershPeriod = 60000L;

	public static int OnlineUserCount = 0;

	public static int LoginUserCount = 0;

	public static boolean isInstalled = true;

	public static boolean isAllowLogin = true;

	public static String AppCode = null;

	public static String AppName = null;

	public static float MainVersion = 1.0F;

	public static float MinorVersion = 0.0F;

	private static boolean ComplexDepolyMode = false;
	public static int ServletMajorVersion;
	public static int ServletMinorVersion;
	private static Object mutex = new Object();

	public static boolean isPatching = false;

	public static boolean isNeedCheckPatch = false;

	public static void readConfigFileName(String fileName) {
		CONFIG_FILE_NAME = fileName;
		init();
	}

	static {
		init();
	}

	protected static void init() {
		if (System.currentTimeMillis() - LastUpdateTime > RefershPeriod) {
			synchronized (mutex) {
				if (System.currentTimeMillis() - LastUpdateTime > RefershPeriod) {
					File f = new File(CONFIG_FILE_NAME);
					if (!f.exists()) {
						LogUtil.warn("配置文件" + CONFIG_FILE_NAME + "未找到!");
						isInstalled = false;
					}

//					isNeedCheckPatch = LastUpdateTime == 0L;
					loadConfig();
					ComplexDepolyMode = "true".equals(configMap
							.get("App.ComplexDeployMode"));

					if (LastUpdateTime == 0L) {
						LogUtil.info("----" + AppCode + "(" + AppName
								+ "): Config Initialized----");
					}
					LastUpdateTime = System.currentTimeMillis();
				}
			}
		}
	}

	protected static File ConfigFile;

	protected static boolean findConfigFile() {
		ConfigFile = new File(CONFIG_FILE_NAME);
		if (!ConfigFile.exists()) {
			LogUtil.warn("配置文件" + CONFIG_FILE_NAME + "未找到!");
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public static void loadConfig() {
		SAXReader reader = new SAXReader(false);
		try {
			if (!findConfigFile()) {
				isInstalled = false;
				return;
			}
			Document doc = reader.read(ConfigFile);
			Element root = doc.getRootElement();
			Element application = root.element("application");
			List<Element> elements = application.elements();
			for (int i = 0; i < elements.size(); i++) {
				Element ele =  elements.get(i);
				configMap.put("App." + ele.attributeValue("name"), ele
						.getTextTrim());
			}
			if (root.element("allowUploadExt") != null) {
				Element allowUploadExt = root.element("allowUploadExt");
				List<Element> extTypes = allowUploadExt.elements();
				for (int i = 0; i < extTypes.size(); i++) {
					Element ele =  extTypes.get(i);
					configMap
							.put(ele.attributeValue("name"), ele.getTextTrim());
				}
			}
			loadDataBasesConfig(root.element("databases"));
		} catch (Exception e) {
			e.printStackTrace();
			isInstalled = true;
		}
	}

	public static Mapx<String, String> getMapx() {
		return configMap;
	}

	public static String getValue(String configName) {
		return (String) configMap.get(configName);
	}

	public static void setValue(String configName, String configValue) {
		configMap.put(configName, configValue);
	}

	public static final String CLASSPATH;
	static {
		CLASSPATH = getClassesPath();
	}

	public static String getClassesPath() {
		URL url = Thread.currentThread().getContextClassLoader().getResource(
				"license.dat");
		if (url == null)
			throw new RuntimeException("未找到license.dat");
		try {
			String path = URLDecoder.decode(url.getPath(),
					SystemConfig.FileEncoding);
			if (SystemConfig.OSName.toLowerCase().indexOf("windows") >= 0) {
				if (path.startsWith("/"))
					path = path.substring(1);
				else if (path.startsWith("file:/")) {
					path = path.substring(6);
				}
			} else if (path.startsWith("file:/")) {
				path = path.substring(5);
			}

			return path.substring(0, path.lastIndexOf("/") + 1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getContextRealPath() {
		if (configMap != null) {
			String str = (String) configMap.get("App.ContextRealPath");
			if (str != null) {
				return str;
			}
		}
		String path = getClassesPath();
		int index = path.indexOf("WEB-INF");
		if (index > 0) {
			path = path.substring(0, index);
		}
		return path;
	}

	

	public static String getContextPath() {
		// TODO need to refactor
		// if (ComplexDepolyMode) {
		// String path = (String) User.getValue("App.ContextPath");
		// if (StringUtil.isEmpty(path)) {
		// path = getValue("App.ContextPath");
		// }
		// return path;
		// }
		return getValue("App.ContextPath");
	}

	public static String getLogLevel() {
		return getValue("App.LogLevel");
	}

	static{
		initProduct();
	}
	private static void initProduct() {
		if (AppCode == null)
			try {
				IProduct p = (IProduct) Class.forName("com.xdarkness.Product")
						.newInstance();
				AppCode = p.getAppCode();
				AppName = p.getAppName();
				MainVersion = p.getMainVersion();
				MinorVersion = p.getMinorVersion();

				if (configMap.get("App.Code") != null) {
					AppCode = configMap.getString("App.Code");
					AppName = configMap.getString("App.Name");
				}
			} catch (Exception e) {
				AppCode = "SkyPlatform";
				AppName = "Sky开发平台";
			}
	}

	public static boolean isDebugMode() {
		return "true".equalsIgnoreCase(getValue("App.DebugMode"));
	}

	public static String getContainerVersion() {
		String str = SystemConfig.ContainerInfo;
		if (str.indexOf("/") > 0) {
			return str.substring(str.lastIndexOf("/") + 1);
		}
		return "0";
	}

	public static int getLoginUserCount() {
		return LoginUserCount;
	}

	public static int getOnlineUserCount() {
		return OnlineUserCount;
	}

	public static boolean isComplexDepolyMode() {
		return ComplexDepolyMode;
	}

	public static boolean isDebugLoglevel() {
		return "Debug".equalsIgnoreCase(getLogLevel());
	}

	public static String getLoginPage() {
		String str = configMap.getString("App.LoginPage");
		if (XString.isNotEmpty(str)) {
			return str;
		}
		return "Login.jsp";
	}

	@SuppressWarnings("unchecked")
	public static void loadDataBasesConfig(Element databases) {
		if (databases != null) {
			List<Element> dbs = databases.elements();
			for (int i = 0; i < dbs.size(); i++) {
				Element ele = dbs.get(i);
				String dbname = ele.attributeValue("name").trim();
				List<Element> configs = ele.elements();
				for (int k = 0; k < configs.size(); k++) {
					ele = configs.get(k);
					String attr = ele.attributeValue("name");
					String value = ele.getTextTrim();
					if ((attr.equalsIgnoreCase("Password"))
							&& (value.startsWith("$KEY"))) {
						value = EncryptUtil.decrypt3DES(value.substring(4),
								"27jrWz3sxrVbR+pnyg6j");
					}

					configMap.put("Database." + dbname + "." + attr, value);
				}
			}
		}
	}

	public static void update() {
		loadConfig();
		loadDBConfig();
	}

	public static void loadDBConfig() {
//		if ((configMap.get("Database.Default.Type") != null)
//				&& ("true".equals(configMap.get("App.ExistPlatformDB"))))
//			try {
//				DataTable dt = new QueryBuilder(
//						"select type,value from zdconfig").executeDataTable();
//				int i = 0;
//				do {
//					configMap.put(dt.getString(i, 0), dt.getString(i, 1));
//
//					i++;
//					if (dt == null)
//						break;
//				} while (i < dt.getRowCount());
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
	}

	public static boolean isDB2() {
		return XConnectionPoolManager.getDBConnConfig().DBType == DBTypes.DB2;
	}

	public static boolean isOracle() {
		return XConnectionPoolManager.getDBConnConfig().DBType == DBTypes.ORACLE;
	}

	public static boolean isMysql() {
		return XConnectionPoolManager.getDBConnConfig().DBType == DBTypes.MYSQL;
	}

	public static boolean isSQLServer() {
		return XConnectionPoolManager.getDBConnConfig().DBType == DBTypes.MSSQL;
	}

	public static boolean isTomcat() {
		if (XString.isEmpty(SystemConfig.ContainerInfo)) {
			getJBossInfo();
		}
		return SystemConfig.ContainerInfo.toLowerCase().indexOf("tomcat") >= 0;
	}

	protected static void getJBossInfo() {
		String jboss = System.getProperty("jboss.home.dir");
		if (XString.isNotEmpty(jboss))
			try {
				Class<?> c = Class.forName("org.jboss.Version");
				Method m = c.getMethod("getInstance");
				Object o = m.invoke(null);
				m = c.getMethod("getMajor");
				Object major = m.invoke(o);
				m = c.getMethod("getMinor");
				Object minor = m.invoke(o);
				m = c.getMethod("getRevision");
				Object revision = m.invoke(o);
				m = c.getMethod("getTag");
				Object tag = m.invoke(o);
				SystemConfig.ContainerInfo = "JBoss/" + major + "."
						+ minor + "." + revision + "." + tag;
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public static boolean isJboss() {
		if (XString.isEmpty(SystemConfig.ContainerInfo)) {
			getJBossInfo();
		}
		return SystemConfig.ContainerInfo.toLowerCase().indexOf("jboss") >= 0;
	}

	public static boolean isWeblogic() {
		return SystemConfig.ContainerInfo.toLowerCase().indexOf("weblogic") >= 0;
	}

	public static boolean isWebSphere() {
		return SystemConfig.ContainerInfo.toLowerCase().indexOf("websphere") >= 0;
	}

	public static String getAppCode() {
		return AppCode;
	}

	public static String getAppName() {
		return AppName;
	}

	public static float getMinorVersion() {
		return MinorVersion;
	}

	public static float getMainVersion() {
		return MainVersion;
	}
}
