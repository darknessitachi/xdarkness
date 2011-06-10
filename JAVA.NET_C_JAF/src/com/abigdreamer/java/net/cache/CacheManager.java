package com.abigdreamer.java.net.cache;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.abigdreamer.java.net.Config;
import com.abigdreamer.java.net.clustering.Clustering;
import com.abigdreamer.java.net.util.LogUtil;
import com.abigdreamer.java.net.util.Mapx;

public class CacheManager {
	private static Object mutex = new Object();
	private static Mapx ProviderMap;

	private static void loadConfig() {
		if (ProviderMap == null) {
			String path = Config.getContextRealPath()
					+ "WEB-INF/classes/framework.xml";
			SAXReader reader = new SAXReader(false);
			try {
				Document doc = reader.read(new File(path));
				Element root = doc.getRootElement();
				Element cache = root.element("cache");
				if (cache != null) {
					List types = cache.elements();
					ProviderMap = new Mapx();
					for (int i = 0; i < types.size(); i++) {
						Element type = (Element) types.get(i);
						String className = type.attributeValue("class");
						try {
							CacheProvider cp = (CacheProvider) Class.forName(
									className).newInstance();
							ProviderMap.put(cp.getProviderName(), cp);
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
			CacheProvider cp = new CodeCache();
			ProviderMap.put(cp.getProviderName(), cp);
		}
	}

	public static CacheProvider getCache(String type) {
		synchronized (mutex) {
			if (ProviderMap == null) {
				loadConfig();
			}
		}
		return (CacheProvider) ProviderMap.get(type);
	}

	public static Object get(String providerName, String type, int key) {
		return get(providerName, type, String.valueOf(key));
	}

	public static Object get(String providerName, String type, long key) {
		return get(providerName, type, String.valueOf(key));
	}

	public static Object get(String providerName, String type, Object key) {
		CacheProvider cp = getCache(providerName);
		if (cp == null) {
			throw new RuntimeException("未找到CacheProvider:" + providerName);
		}
		Mapx map = (Mapx) cp.TypeMap.get(type);
		if (map == null) {
			synchronized (cp) {
				if (Clustering.isClustering()) {
					map = Clustering.getMapx(cp.getProviderName() + "_" + type);
					if (map == null) {
						cp.onTypeNotFound(type);
						map = (Mapx) cp.TypeMap.get(type);
						if (map != null)
							Clustering.putMapx(cp.getProviderName() + "_"
									+ type, map);
					} else {
						cp.TypeMap.put(type, map);
					}
				} else if (map == null) {
					cp.onTypeNotFound(type);
				}

				map = (Mapx) cp.TypeMap.get(type);
				if (map == null) {
					LogUtil.warn("指定的CacheProvider:" + providerName + "下不存在类型为"
							+ type + "的缓存。");
					return null;
				}
			}
		}
		if (!map.containsKey(key)) {
			synchronized (map) {
				if (Clustering.isClustering()) {
					if (Clustering.containsKey(cp.getProviderName() + "_"
							+ type + "." + key)) {
						String str = Clustering.get(cp.getProviderName() + "_"
								+ type + "." + key);
						map.put(key, str);
					} else {
						cp.onKeyNotFound(type, key);
						if (map.containsKey(key))
							Clustering.put(cp.getProviderName() + "_" + type
									+ "." + key, map.getString(key));
					}
				} else if (!map.containsKey(key)) {
					cp.onKeyNotFound(type, key);
				}
				if (!map.containsKey(key)) {
					LogUtil.warn("获取缓存数据失败:" + providerName + "," + type + ","
							+ key);
				}
			}
		}
		return map.get(key);
	}

	public static void set(String providerName, String type, int key,
			Object value) {
		set(providerName, type, String.valueOf(key), value);
	}

	public static void set(String providerName, String type, long key,
			Object value) {
		set(providerName, type, String.valueOf(key), value);
	}

	public static void set(String providerName, String type, long key,
			long value) {
		set(providerName, type, String.valueOf(key), String.valueOf(value));
	}

	public static void set(String providerName, String type, long key, int value) {
		set(providerName, type, String.valueOf(key), String.valueOf(value));
	}

	public static void set(String providerName, String type, int key, long value) {
		set(providerName, type, String.valueOf(key), String.valueOf(value));
	}

	public static void set(String providerName, String type, int key, int value) {
		set(providerName, type, String.valueOf(key), String.valueOf(value));
	}

	public static void set(String providerName, String type, Object key,
			long value) {
		set(providerName, type, key, String.valueOf(value));
	}

	public static void set(String providerName, String type, Object key,
			int value) {
		set(providerName, type, key, String.valueOf(value));
	}

	public static void set(String providerName, String type, Object key,
			Object value) {
		CacheProvider cp = getCache(providerName);
		if (cp == null) {
			LogUtil.warn("未找到CacheProvider:" + providerName);
			return;
		}
		Mapx map = (Mapx) cp.TypeMap.get(type);
		if (map == null) {
			map = new Mapx();
			synchronized (cp) {
				cp.TypeMap.put(type, map);
			}
		}
		synchronized (map) {
			map.put(key, value);
			if (Clustering.isClustering()) {
				Clustering.putObject(cp.getProviderName() + "_" + type + "."
						+ key, value);
			}
			cp.onKeySet(type, key, value);
		}
	}

	public static void remove(String providerName, String type, int key) {
		remove(providerName, type, String.valueOf(key));
	}

	public static void remove(String providerName, String type, long key) {
		remove(providerName, type, String.valueOf(key));
	}

	public static void remove(String providerName, String type, Object key) {
		CacheProvider cp = getCache(providerName);
		if (cp == null) {
			LogUtil.warn("未找到CacheProvider:" + providerName);
			return;
		}
		Mapx map = (Mapx) cp.TypeMap.get(type);
		if (map == null) {
			LogUtil.warn("指定的CacheProvider:" + providerName + "下没有类型为" + type
					+ "的缓存");
			return;
		}
		synchronized (map) {
			map.remove(key);
			if (Clustering.isClustering())
				Clustering
						.remove(cp.getProviderName() + "_" + type + "." + key);
		}
	}

	public static void removeType(String providerName, String type) {
		CacheProvider cp = getCache(providerName);
		if (cp == null) {
			LogUtil.warn("未找到CacheProvider:" + providerName);
			return;
		}
		synchronized (cp) {
			cp.TypeMap.remove(type);
			if (Clustering.isClustering())
				Clustering.remove(cp.getProviderName() + "_" + type);
		}
	}

	public static Mapx getMapx(String providerName, String type) {
		CacheProvider cp = getCache(providerName);
		if (cp == null) {
			LogUtil.warn("未找到CacheProvider:" + providerName);
			return null;
		}
		Mapx map = (Mapx) cp.TypeMap.get(type);
		if (map == null) {
			synchronized (cp) {
				if (Clustering.isClustering()) {
					map = Clustering.getMapx(cp.getProviderName() + "_" + type);
					if (map == null) {
						cp.onTypeNotFound(type);
						map = (Mapx) cp.TypeMap.get(type);
						if (map != null)
							Clustering.putMapx(cp.getProviderName() + "_"
									+ type, map);
					} else {
						cp.TypeMap.put(type, map);
					}
				} else if (map == null) {
					cp.onTypeNotFound(type);
				}

				map = (Mapx) cp.TypeMap.get(type);
				if (map == null) {
					LogUtil.warn("指定的CacheProvider:" + providerName + "下不存在类型为"
							+ type + "的缓存。");
					return null;
				}
			}
		}
		return map;
	}

	public static void setMapx(String providerName, String type, Mapx map) {
		CacheProvider cp = getCache(providerName);
		if (cp == null) {
			LogUtil.warn("未找到CacheProvider:" + providerName);
			return;
		}
		cp.TypeMap.put(type, map);
		if (Clustering.isClustering())
			Clustering.putMapx(cp.getProviderName() + "_" + type, map);
	}

	public static Mapx get(String providerName, String type) {
		return getMapx(providerName, type);
	}
}

/*
 * com.xdarkness.framework.cache.CacheManager JD-Core Version: 0.6.0
 */