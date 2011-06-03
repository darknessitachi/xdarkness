package com.abigdreamer.java.net;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.abigdreamer.java.net.config.SystemConfig;
import com.abigdreamer.java.net.util.Mapx;

public class Config {

//	private static long LastUpdateTime = 0L;
//	private static long RefershPeriod = 60000L;
//	private static Object mutex = new Object();
	
	protected static String CONFIG_FILE_NAME;
	
	static {
		CONFIG_FILE_NAME = getClassesPath() + "framework.xml";
	}
	
	protected static Mapx<String, String> configMap = new Mapx<String, String>();
	
	static {
		init();
	}

	protected static void init() {
//		if (System.currentTimeMillis() - LastUpdateTime > RefershPeriod) {
//			synchronized (mutex) {
//				if (System.currentTimeMillis() - LastUpdateTime > RefershPeriod) {
//					File f = new File(CONFIG_FILE_NAME);
//					if (!f.exists()) {
//						LogUtil.warn("配置文件" + CONFIG_FILE_NAME + "未找到!");
//						isInstalled = false;
//					}

//					isNeedCheckPatch = LastUpdateTime == 0L;
					loadConfig();
//					ComplexDepolyMode = "true".equals(configMap
//							.get("App.ComplexDeployMode"));
//
//					if (LastUpdateTime == 0L) {
//						LogUtil.info("----" + AppCode + "(" + AppName
//								+ "): Config Initialized----");
//					}
//					LastUpdateTime = System.currentTimeMillis();
//				}
//			}
//		}
	}
	
	protected static File ConfigFile;
	
	public static Element getRootElement() {
		SAXReader reader = new SAXReader(false);
		try {
			ConfigFile = new File(CONFIG_FILE_NAME);
			Document doc = reader.read(ConfigFile);
			return doc.getRootElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static boolean isDebugMode() {
		return "true".equalsIgnoreCase(getValue("App.DebugMode"));
	}
	

	public static String getValue(String configName) {
		return (String) configMap.get(configName);
	}

	public static String getLogLevel() {
		return getValue("App.LogLevel");
	}
	

	public static boolean isDebugLoglevel() {
		return "Debug".equalsIgnoreCase(getLogLevel());
	}
	
	@SuppressWarnings("unchecked")
	public static void loadConfig() {
		SAXReader reader = new SAXReader(false);
		try {
//			if (!findConfigFile()) {
//				isInstalled = false;
//				return;
//			}
			ConfigFile = new File(CONFIG_FILE_NAME);
			Document doc = reader.read(ConfigFile);
			Element root = doc.getRootElement();
			Element application = root.element("application");
			List<Element> elements = application.elements();
			for (int i = 0; i < elements.size(); i++) {
				Element ele =  elements.get(i);
				configMap.put("App." + ele.attributeValue("name"), ele.getTextTrim());
			}
//			if (root.element("allowUploadExt") != null) {
//				Element allowUploadExt = root.element("allowUploadExt");
//				List<Element> extTypes = allowUploadExt.elements();
//				for (int i = 0; i < extTypes.size(); i++) {
//					Element ele =  extTypes.get(i);
//					configMap
//							.put(ele.attributeValue("name"), ele.getTextTrim());
//				}
//			}
//			loadDataBasesConfig(root.element("databases"));
		} catch (Exception e) {
			e.printStackTrace();
//			isInstalled = true;
		}
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
//		if (configMap != null) {
//			String str = (String) configMap.get("App.ContextRealPath");
//			if (str != null) {
//				return str;
//			}
//		}
		String path = getClassesPath();
		int index = path.indexOf("WEB-INF");
		if (index > 0) {
			path = path.substring(0, index);
		}
		return path;
	}
}
