package com.abigdreamer.java.net.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Darkness create on 2011-1-5 上午11:42:07
 * @version 1.0
 * @since JAVA.NET 1.0
 */
public class Mapx<K, V> extends LinkedHashMap<K, V> {
	private static final float DEFAULT_LOAD_FACTOR = 0.75F;
	private static final int DEFAULT_INIT_CAPACITY = 16;
	private static final long serialVersionUID = 200904201752L;
	private final int maxCapacity;
	private final boolean maxFlag;
	private int hitCount = 0;

	private int missCount = 0;

	private long lastWarnTime = 0L;
	private ExitEventListener listener;

	public Mapx(int maxCapacity, boolean LRUFlag) {
		super(maxCapacity, DEFAULT_LOAD_FACTOR, LRUFlag);
		this.maxCapacity = maxCapacity;
		this.maxFlag = true;
	}

	public Mapx(int maxCapacity) {
		this(maxCapacity, true);
	}

	public Mapx() {
		super(DEFAULT_INIT_CAPACITY, DEFAULT_LOAD_FACTOR, false);
		this.maxCapacity = 0;
		this.maxFlag = false;
	}

	@Override
	public Object clone() {
		Mapx map = (Mapx) super.clone();
		Object[] ks = keyArray();
		Object[] vs = valueArray();
		for (int i = 0; i < ks.length; ++i) {
			Object v = vs[i];
			if (v instanceof Mapx) {
				map.put(ks[i], ((Mapx) v).clone());
			}
		}
		return map;
	}

	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		boolean flag = (this.maxFlag) && (size() > this.maxCapacity);
		if ((flag) && (this.listener != null)) {
			this.listener.onExit(eldest.getKey(), eldest.getValue());
		}
		return flag;
	}

	public void setExitEventListener(ExitEventListener listener) {
		this.listener = listener;
	}

	public Object[] keyArray() {
		if (size() == 0) {
			return new Object[0];
		}
		Object[] arr = new Object[size()];
		int i = 0;
		for (Iterator<K> iter = keySet().iterator(); iter.hasNext();) {
			arr[(i++)] = iter.next();
		}
		return arr;
	}

	public Object[] valueArray() {
		if (size() == 0) {
			return new Object[0];
		}
		Object[] arr = new Object[size()];
		int i = 0;
		for (Iterator<V> iter = values().iterator(); iter.hasNext();) {
			arr[(i++)] = iter.next();
		}
		return arr;
	}

	public String getString(Object key) {
		Object o = get(key);
		if (o == null) {
			return null;
		}
		return o.toString();
	}

	public int getInt(Object key) {
		Object o = get(key);
		if ((o instanceof Number))
			return ((Number) o).intValue();
		if (o != null) {
			try {
				return Integer.parseInt(o.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public long getLong(Object key) {
		Object o = get(key);
		if ((o instanceof Number))
			return ((Number) o).longValue();
		if (o != null) {
			try {
				return Long.parseLong(o.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0L;
	}

	public V get(Object key) {
		V o = super.get(key);
		if (this.maxFlag) {
			if (o == null)
				this.missCount += 1;
			else {
				this.hitCount += 1;
			}
			if ((this.missCount > 1000)
					&& (this.hitCount * 1.0D / this.missCount < 0.1D)
					&& (System.currentTimeMillis() - this.lastWarnTime > 1000000L)) {
				this.lastWarnTime = System.currentTimeMillis();
				StackTraceElement[] stack = new Throwable().getStackTrace();
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < stack.length; ++i) {
					StackTraceElement ste = stack[i];
					if (ste.getClassName().indexOf("DBConnPoolImpl") == -1) {
						sb.append("\t");
						sb.append(ste.getClassName());
						sb.append(".");
						sb.append(ste.getMethodName());
						sb.append("(),行号:");
						sb.append(ste.getLineNumber());
						sb.append("\n");
					}
				}
				LogUtil.getLogger().warn("缓存命中率过低!");
				LogUtil.getLogger().warn(sb);
			}
		}

		return o;
	}

	public static Mapx<Object, Object> convertToMapx(Map<Object, Object> map) {
		Mapx<Object, Object> mapx = new Mapx<Object, Object>();
		mapx.putAll(map);
		return mapx;
	}

}
