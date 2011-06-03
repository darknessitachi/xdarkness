package com.abigdreamer.java.net.util;

import java.util.Map;

/**
 * 
 * @author Darkness 
 * create on 2011 2011-1-7 下午03:40:47
 * @version 1.0
 * @since JAVA.NET 1.0
 */
public class CaseIgnoreMapx<V> extends Mapx<Object, V> {
	private static final long serialVersionUID = 1L;

	public CaseIgnoreMapx() {
	}

	public CaseIgnoreMapx(Map<Object, V> map) {
		if(map != null){
			putAll(map);
		}
	}

	@Override
	public V put(Object key, V value) {
		if ((key != null) && ((key instanceof String))) {
			return super.put(key.toString().toLowerCase(), value);
		}
		return super.put(key, value);
	}

	@Override
	public V get(Object key) {
		if ((key != null) && ((key instanceof String))) {
			return super.get(key.toString().toLowerCase());
		}
		return super.get(key);
	}

	@Override
	public boolean containsKey(Object key) {
		if ((key != null) && ((key instanceof String))) {
			return super.containsKey(key.toString().toLowerCase());
		}
		return super.containsKey(key);
	}

	@Override
	public V remove(Object key) {
		if ((key != null) && ((key instanceof String))) {
			return super.remove(key.toString().toLowerCase());
		}
		return super.remove(key);
	}
}
