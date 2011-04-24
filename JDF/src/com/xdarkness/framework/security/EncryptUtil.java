package com.xdarkness.framework.security;

import java.io.ByteArrayInputStream;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.JDKX509CertificateFactory;

import com.xdarkness.framework.Config;
import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.license.SystemInfo;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.XString;

public class EncryptUtil {
	public static final String DEFAULT_KEY = "27jrWz3sxrVbR+pnyg6j";

	public static String encrypt3DES(String str, String password) {
		String strResult = null;
		try {
			byte[] key = password.getBytes();
			byte[] encodeString = str.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(key, "DESede");
			Z3DESCipher cipher = new Z3DESCipher();
			cipher.init(1, skeySpec);
			byte[] cipherByte = cipher.doFinal(encodeString);
			strResult = XString.base64Encode(cipherByte);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return strResult;
	}

	public static String decrypt3DES(String srcStr, String password) {
		String strResult = null;
		try {
			byte[] key = password.getBytes();
			byte[] src = XString.base64Decode(srcStr);
			SecretKeySpec skeySpec = new SecretKeySpec(key, "DESede");
			Z3DESCipher cipher = new Z3DESCipher();
			cipher.init(2, skeySpec);
			byte[] cipherByte = cipher.doFinal(src);
			strResult = new String(cipherByte);
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
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
					Class.forName("com.xdarkness.shop.AdvanceShop");
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

		return strResult;
	}

	public static void main(String[] args) {
		System.out.println(encrypt3DES("TEST", "27jrWz3sxrVbR+pnyg6j"));
		System.out.println(decrypt3DES(encrypt3DES("TEST",
				"27jrWz3sxrVbR+pnyg6j"), "27jrWz3sxrVbR+pnyg6j"));
	}
}