package com.abigdreamer.java.net.cache;

import com.abigdreamer.java.net.util.Mapx;

public abstract class CacheProvider {
	protected Mapx TypeMap = new Mapx();

	public abstract String getProviderName();

	public void onKeySet(String type, Object key, Object value) {
	}

	public abstract void onTypeNotFound(String paramString);

	public abstract void onKeyNotFound(String paramString, Object paramObject);
}
