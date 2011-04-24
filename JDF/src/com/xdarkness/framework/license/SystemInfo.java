package com.xdarkness.framework.license;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bouncycastle.jce.provider.JDKX509CertificateFactory;

import com.xdarkness.framework.Config;
import com.xdarkness.framework.config.SystemConfig;
import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.security.ZRSACipher;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.XString;

public class SystemInfo {
	public static final String getMacAddress() {
		String os = System.getProperty("os.name").toLowerCase();
		String output = null;
		try {
			String cmd = "ipconfig /all";
			if (os.indexOf("windows") < 0) {
				cmd = "ifconfig";
			}
			Process proc = Runtime.getRuntime().exec(cmd);
			InputStream is = proc.getInputStream();
			output = FileUtil.readText(is, SystemConfig.FileEncoding);
		} catch (Exception ex) {
			String cmd = "ipconfig /all";
			if (os.indexOf("windows") < 0)
				cmd = "/sbin/ifconfig";
			try {
				Process proc = Runtime.getRuntime().exec(cmd);
				InputStream is = proc.getInputStream();
				output = FileUtil.readText(is, SystemConfig.FileEncoding);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Pattern p = Pattern.compile(
				"([0-9a-zA-z]{2}[\\:\\-]){5}[0-9a-zA-z]{2}", 32);
		Matcher m = p.matcher(output);
		int lastIndex = 0;
		StringBuffer sb = new StringBuffer();
		while (m.find(lastIndex)) {
			if (lastIndex != 0) {
				sb.append(",");
			}
			sb.append(m.group(0));
			lastIndex = m.end();
		}
		return sb.toString();

	}

	/**
	 * 检查授权信息
	 */
	public static void checkLicense() {
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
			indexBS += dc.doFinal(code, indexCode, code.length - indexCode, bs,
					indexBS);
			String str = new String(bs, 0, indexBS);// 得到解密后的license字符串

			Mapx mapx = XString.splitToMapx(str, ";", "=");
			String product = mapx.getString("Product");
			String macAddress = mapx.getString("MacAddress");
			String name = mapx.getString("Name");

			Date endDate = new Date(Long.parseLong(mapx.getString("TimeEnd")));
			if (endDate.getTime() < System.currentTimeMillis()) {
				LogUtil.fatal("License己过期!");
				System.exit(0);
			}
			if ((name.indexOf("Trial") < 0)
					&& (!macAddress
							.equalsIgnoreCase(SystemInfo.getMacAddress()))) {
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
				Class.forName("com.xdarkness.shop.Goods");
				if (product.indexOf("zshop") < 0) {
					LogUtil.fatal("License中没有ZShop相关的标记!");
					System.exit(0);
				}
			} catch (Exception localException3) {
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void main(String[] args) {
		try {
			System.out.println("  Operating System: "
					+ System.getProperty("os.name"));
			System.out.println("  IP/Localhost: "
					+ InetAddress.getLocalHost().getHostAddress());
			System.out.println("  MAC Address: " + getMacAddress());
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
