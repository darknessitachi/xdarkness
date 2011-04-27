package com.abigdreamer.java.net;

import com.abigdreamer.java.net.license.IProduct;

public class Product implements IProduct {
	public String getAppCode() {
		return "CMS";
	}

	public String getAppName() {
		return "天空内容管理系统";
	}

	public float getMainVersion() {
		return 1.3F;
	}

	public float getMinorVersion() {
		return 1.0F;
	}
}