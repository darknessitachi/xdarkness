package com.xdarkness.platform;

import com.abigdreamer.java.net.Config;
import com.abigdreamer.java.net.io.FileUtil;
import com.abigdreamer.java.net.jaf.Ajax;
import com.abigdreamer.java.net.license.LicenseInfo;

public class License extends Ajax {
	public void initRequest() {
		String customer = $V("Customer");
		$S("Request", LicenseInfo.getLicenseRequest(customer));
	}

	public void saveLicense() {
		String license = $V("License");
		if (LicenseInfo.verifyLicense(license)) {
			FileUtil.writeText(Config.getContextRealPath()
					+ "WEB-INF/classes/license.dat", license);
			LicenseInfo.update();
			this.response.setMessage("保存成功!");
		} else {
			this.response.setError("无效的许可证!");
		}
	}

}
