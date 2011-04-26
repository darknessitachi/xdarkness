package com.xdarkness.platform;

import java.util.Date;

import com.xdarkness.framework.Config;
import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.jaf.Ajax;
import com.xdarkness.framework.license.LicenseInfo;
import com.xdarkness.framework.util.DateUtil;

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
