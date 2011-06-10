package com.abigdreamer.java.net.message;

import java.io.ByteArrayInputStream;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.bouncycastle.jce.provider.JDKX509CertificateFactory;

import com.abigdreamer.java.net.Config;
import com.abigdreamer.java.net.io.FileUtil;
import com.abigdreamer.java.net.jaf.Ajax;
import com.abigdreamer.java.net.license.SystemInfo;
import com.abigdreamer.java.net.security.ZRSACipher;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.LogUtil;
import com.abigdreamer.java.net.util.Mapx;
import com.abigdreamer.java.net.util.XString;

public class LongTimeTaskPage extends Ajax {
	public void getInfo() {
		long id = 0L;
		if (XString.isNotEmpty($V("TaskID"))) {
			id = Long.parseLong($V("TaskID"));
		}

		if (System.currentTimeMillis() % 10000000L == 0L) {
			try {
				String cert = "MIICnTCCAgagAwIBAgIBATANBgkqhkiG9w0BAQUFADBkMRIwEAYDVQQDDAlMaWNlbnNlQ0ExDTALBgNVBAsMBFNPRlQxDjAMBgNVBAoMBVpWSU5HMRAwDgYDVQQHDAdIQUlESUFOMQswCQYDVQQGEwJDTjEQMA4GA1UECAwHQkVJSklORzAgFw0wOTA0MTYwMzQ4NDhaGA81MDA3MDQyMDAzNDg0OFowZDESMBAGA1UEAwwJTGljZW5zZUNBMQ0wCwYDVQQLDARTT0ZUMQ4wDAYDVQQKDAVaVklORzEQMA4GA1UEBwwHSEFJRElBTjELMAkGA1UEBhMCQ04xEDAOBgNVBAgMB0JFSUpJTkcwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMStEFTKHuIaPzADjA7hrHSQn5jL5yCN+dabiP0vXfAthKWEOiaS8wAX8WX516PDPfyo2SL63h5Ihvn9BBpLqAgwvDyxoP6bpU85ZuvmbeI02EPgLCz1IK+Xibl4RmcaprKvjm5ec92zWLWTC4TEkdh+NPFkkL7yZskZNC4e40I9AgMBAAGjXTBbMB0GA1UdDgQWBBRwZt+eq7q/8MvUoSNW41Bzp2RD5zAfBgNVHSMEGDAWgBRwZt+eq7q/8MvUoSNW41Bzp2RD5zAMBgNVHRMEBTADAQH/MAsGA1UdDwQEAwIBBjANBgkqhkiG9w0BAQUFAAOBgQAummShucu9umvlsrGaJmw0xkFCwC8esLHe50sJkER2OreGPCdrQjEGytvYz4jtkqVyvLBDziuz29yeQUDjfVBuN7iZ9CuYeuI73uQoQeZOKLDQj2UZHag6XNCkSJTvh9g2JWOeAJjmwquwds+dONKRU/fol4JnrU7fMP/V0Ur3/w==";

				byte[] code = XString.hexDecode(FileUtil.readText(
						Config.getClassesPath() + "license.dat").trim());
				JDKX509CertificateFactory certificatefactory = new JDKX509CertificateFactory();
				X509Certificate cer = (X509Certificate) certificatefactory
						.engineGenerateCertificate(new ByteArrayInputStream(
								XString.base64Decode(cert)));
				PublicKey pubKey = cer.getPublicKey();
				ZRSACipher dc = new ZRSACipher();
				dc.init(2, pubKey);
				byte[] bs = new byte[code.length * 2];
				int indexBS = 0;
				int indexCode = 0;
				while (code.length - indexCode > 128) {
					indexBS += dc.doFinal(code, indexCode, 128, bs, indexBS);
					indexCode += 128;
				}
				indexBS += dc.doFinal(code, indexCode, code.length - indexCode,
						bs, indexBS);
				String str = new String(bs, 0, indexBS);
				Mapx mapx = XString.splitToMapx(str, ";", "=");
				String product = mapx.getString("Product");
				int userLimit = Integer.parseInt(mapx.getString("UserLimit"));
				String macAddress = mapx.getString("MacAddress");
				String name = mapx.getString("Name");

				Date endDate = new Date(Long.parseLong(mapx
						.getString("TimeEnd")));
				if (new QueryBuilder("select count(*) from ZDUser")
						.executeInt() >= userLimit) {
					LogUtil.fatal("己有用户数超出License中的用户数限制!");
					System.exit(0);
				}
				if (endDate.getTime() < System.currentTimeMillis()) {
					LogUtil.fatal("License己过期!");
					System.exit(0);
				}
				if ((name.indexOf("Trial") < 0)
						&& (!macAddress.equalsIgnoreCase(SystemInfo
								.getMacAddress()))) {
					LogUtil.fatal("License中指定的Mac地址与实际Mac地址不一致!");
					System.exit(0);
				}
				product = product.toLowerCase();
				try {
					Class.forName("com.xdarkness.oa.workflow.FlowConfig");
					if (product.indexOf("zoa") < 0) {
						LogUtil.fatal("License中没有ZOA相关的标记!");
						System.exit(0);
					}
				} catch (Exception localException1) {
				}
				try {
					Class.forName("com.xdarkness.cms.stat.StatUtil");
					if (product.indexOf("zcms") < 0) {
						LogUtil.fatal("License中没有ZCMS相关的标记!");
						System.exit(0);
					}
				} catch (Exception localException2) {
				}
				try {
					Class.forName("com.xdarkness.portal.Portal");
					if (product.indexOf("zportal") < 0) {
						LogUtil.fatal("License中没有ZPortal相关的标记!");
						System.exit(0);
					}
				} catch (Exception localException3) {
				}
				try {
					Class.forName("com.xdarkness.shop.Goods");
					if (product.indexOf("zshop") < 0) {
						LogUtil.fatal("License中没有ZShop相关的标记!");
						System.exit(0);
					}
				} catch (Exception localException4) {
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		LongTimeTask ltt = LongTimeTask.getInstanceById(id);
		if ((ltt != null) && (ltt.isAlive())) {
			$S("CurrentInfo", XString.isNotEmpty(ltt.getCurrentInfo()) ? ltt
					.getCurrentInfo()
					+ "..." : "");
			$S("Messages", ltt.getMessages());
			$S("Percent", ltt.getPercent());
		} else {
			$S("CompleteFlag", "1");
			if (ltt != null) {
				String errors = ltt.getAllErrors();
				if (XString.isNotEmpty(errors)) {
					$S("CurrentInfo", errors);
					$S("ErrorFlag", "1");
				} else {
					$S("CurrentInfo", "任务己完成!");
				}
			} else {
				$S("CurrentInfo", "任务己完成!");
			}
			LongTimeTask.removeInstanceById(id);
		}
	}

	public void stop() {
		long id = Long.parseLong($V("TaskID"));
		LongTimeTask ltt = LongTimeTask.getInstanceById(id);
		if (ltt != null)
			ltt.stopTask();
	}

	public void stopComplete() {
		long id = Long.parseLong($V("TaskID"));
		LongTimeTask ltt = LongTimeTask.getInstanceById(id);
		if ((ltt == null) || (!ltt.isAlive()))
			LongTimeTask.removeInstanceById(id);
		else
			this.response.setStatus(0);
	}
}