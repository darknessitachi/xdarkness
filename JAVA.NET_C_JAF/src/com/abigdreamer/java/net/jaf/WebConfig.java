package com.abigdreamer.java.net.jaf;

import com.abigdreamer.java.net.Config;
import com.abigdreamer.java.net.license.IProduct;

/**
 * @author Darkness create on 2010-11-30 上午11:53:18
 * 
 * @version 1.0
 * @since JDF 1.0
 */
public class WebConfig extends Config {
	
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
}

