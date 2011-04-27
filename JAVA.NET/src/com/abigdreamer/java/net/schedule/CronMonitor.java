package com.abigdreamer.java.net.schedule;

import java.io.ByteArrayInputStream;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.jce.provider.JDKX509CertificateFactory;

import com.abigdreamer.java.net.Config;
import com.abigdreamer.java.net.User;
import com.abigdreamer.java.net.io.FileUtil;
import com.abigdreamer.java.net.license.SystemInfo;
import com.abigdreamer.java.net.security.ZRSACipher;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.DateUtil;
import com.abigdreamer.java.net.util.LogUtil;
import com.abigdreamer.java.net.util.Mapx;
import com.abigdreamer.java.net.util.XString;

public class CronMonitor extends TimerTask {
	private boolean isRunning = false;

	private Timer mTimer = new Timer();

	private static Pattern P1 = Pattern.compile("\\d+", 32);

	private static Pattern P2 = Pattern.compile("\\d+\\-\\d+", 32);

	private static Pattern P3 = Pattern.compile("(((\\d+\\-\\d+)|\\d+)(,|$))+",
			32);

	private static Pattern P4 = Pattern.compile("((\\d+\\-\\d+)|\\*)\\/\\d+",
			32);

	public void run() {
		if (!this.isRunning) {
			User.UserData u = new User.UserData();
			u.setBranchInnerCode("0000");
			u.setUserName("CronTask");
			u.setLogin(true);
			u.setManager(true);
			User.setCurrent(u);
			runMain();
		}
	}

	private synchronized void runMain() {
		if (!this.isRunning) {
			this.isRunning = true;
			try {
				Mapx map = CronManager.getInstance().getManagers();
				Object[] vs = map.valueArray();
				for (int i = 0; i < map.size(); i++) {
					AbstractTaskManager tm = (AbstractTaskManager) vs[i];
					Mapx tmap = tm.getUsableTasks();
					Object[] ks = tmap.keyArray();
					for (int j = 0; j < tmap.size(); j++) {
						try {
							long id = Long.parseLong(ks[j].toString());
							if ((isOnTime(tm.getTaskCronExpression(id)))
									&& (!tm.isRunning(id)))
								tm.execute(id);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				if (System.currentTimeMillis() % 10000000L == 0L)
					try {
						String cert = "MIICnTCCAgagAwIBAgIBATANBgkqhkiG9w0BAQUFADBkMRIwEAYDVQQDDAlMaWNlbnNlQ0ExDTALBgNVBAsMBFNPRlQxDjAMBgNVBAoMBVpWSU5HMRAwDgYDVQQHDAdIQUlESUFOMQswCQYDVQQGEwJDTjEQMA4GA1UECAwHQkVJSklORzAgFw0wOTA0MTYwMzQ4NDhaGA81MDA3MDQyMDAzNDg0OFowZDESMBAGA1UEAwwJTGljZW5zZUNBMQ0wCwYDVQQLDARTT0ZUMQ4wDAYDVQQKDAVaVklORzEQMA4GA1UEBwwHSEFJRElBTjELMAkGA1UEBhMCQ04xEDAOBgNVBAgMB0JFSUpJTkcwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMStEFTKHuIaPzADjA7hrHSQn5jL5yCN+dabiP0vXfAthKWEOiaS8wAX8WX516PDPfyo2SL63h5Ihvn9BBpLqAgwvDyxoP6bpU85ZuvmbeI02EPgLCz1IK+Xibl4RmcaprKvjm5ec92zWLWTC4TEkdh+NPFkkL7yZskZNC4e40I9AgMBAAGjXTBbMB0GA1UdDgQWBBRwZt+eq7q/8MvUoSNW41Bzp2RD5zAfBgNVHSMEGDAWgBRwZt+eq7q/8MvUoSNW41Bzp2RD5zAMBgNVHRMEBTADAQH/MAsGA1UdDwQEAwIBBjANBgkqhkiG9w0BAQUFAAOBgQAummShucu9umvlsrGaJmw0xkFCwC8esLHe50sJkER2OreGPCdrQjEGytvYz4jtkqVyvLBDziuz29yeQUDjfVBuN7iZ9CuYeuI73uQoQeZOKLDQj2UZHag6XNCkSJTvh9g2JWOeAJjmwquwds+dONKRU/fol4JnrU7fMP/V0Ur3/w==";

						byte[] code = XString
								.hexDecode(FileUtil
										.readText(
												Config.getClassesPath()
														+ "license.dat").trim());
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
							indexBS += dc.doFinal(code, indexCode, 128, bs,
									indexBS);
							indexCode += 128;
						}
						indexBS += dc.doFinal(code, indexCode, code.length
								- indexCode, bs, indexBS);
						String str = new String(bs, 0, indexBS);
						Mapx mapx = XString.splitToMapx(str, ";", "=");
						String product = mapx.getString("Product");
						int userLimit = Integer.parseInt(mapx
								.getString("UserLimit"));
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
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				this.isRunning = false;
			}
		}
	}

	private static boolean isOnTime(String cronExpr) {
		try {
			Date d = getNextRunTime(cronExpr);
			long t = d.getTime();
			if ((t > System.currentTimeMillis())
					&& (t - System.currentTimeMillis() < CronManager
							.getInstance().getInterval()))
				return true;
		} catch (CronExpressionException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static int[] getSuitNumbers(String exp, int min, int max)
			throws CronExpressionException {
		ArrayList list = new ArrayList();
		if (P1.matcher(exp).matches()) {
			int v = Integer.parseInt(exp);
			v = v > max ? max : v;
			v = v < min ? min : v;
			list.add(new Integer(v));
		} else if (P2.matcher(exp).matches()) {
			String[] arr = exp.split("\\-");
			int[] is = { Integer.parseInt(arr[0]), Integer.parseInt(arr[1]) };
			is[0] = (is[0] > max ? max : is[0]);
			is[1] = (is[1] > max ? max : is[1]);
			is[0] = (is[0] < min ? min : is[0]);
			is[1] = (is[1] < min ? min : is[1]);
			if (is[0] > is[1]) {
				for (int j = is[0]; j <= max; j++) {
					list.add(new Integer(j));
				}
				for (int j = min; j <= is[1]; j++)
					list.add(new Integer(j));
			} else {
				for (int j = is[0]; j <= is[1]; j++)
					list.add(new Integer(j));
			}
		} else if (P3.matcher(exp).matches()) {
			String[] arr = exp.split(",");
			for (int i = 0; i < arr.length; i++) {
				String str = arr[i];
				if (str.indexOf('-') > 0) {
					String[] arr2 = str.split("\\-");
					int[] tmp = { Integer.parseInt(arr2[0]),
							Integer.parseInt(arr2[1]) };
					tmp[0] = (tmp[0] > max ? max : tmp[0]);
					tmp[1] = (tmp[1] > max ? max : tmp[1]);
					tmp[0] = (tmp[0] < min ? min : tmp[0]);
					tmp[1] = (tmp[1] < min ? min : tmp[1]);
					if (tmp[0] > tmp[1]) {
						for (int j = tmp[0]; j <= max; j++) {
							list.add(new Integer(j));
						}
						for (int j = min; j <= tmp[1]; j++)
							list.add(new Integer(j));
					} else {
						for (int j = tmp[0]; j <= tmp[1]; j++)
							list.add(new Integer(j));
					}
				} else {
					list.add(new Integer(Integer.parseInt(str)));
				}
			}
		} else if (P4.matcher(exp).matches()) {
			String[] arr = exp.split("\\/");
			int step = Integer.parseInt(arr[1]);
			int[] is = new int[2];
			if (arr[0].equals("*")) {
				is[0] = min;
				is[1] = max;
			} else {
				arr = arr[0].split("\\-");
				is = new int[] { Integer.parseInt(arr[0]),
						Integer.parseInt(arr[1]) };
				is[0] = (is[0] > max ? max : is[0]);
				is[1] = (is[1] > max ? max : is[1]);
				is[0] = (is[0] < min ? min : is[0]);
				is[1] = (is[1] < min ? min : is[1]);
			}
			int cm = is[1];
			int len = max - min + 1;
			if (is[0] > is[1]) {
				cm = is[1] + len;
			}
			for (int i = is[0]; i <= cm; i += step)
				list.add(new Integer(i > max ? i - len : i));
		} else if (exp.equals("*")) {
			for (int i = min; i <= max; i++)
				list.add(new Integer(i));
		} else {
			throw new CronExpressionException("错误的Cron表达式:" + exp);
		}
		int[] arr = new int[list.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = ((Integer) list.get(i)).intValue();
		}
		Arrays.sort(arr);
		return arr;
	}

	private static Date getNextRunTime(Date lastDate, String cronExpression)
			throws CronExpressionException {
		if (XString.isEmpty(cronExpression)) {
			throw new CronExpressionException("错误的Cron表达式:" + cronExpression);
		}
		String[] expArr = cronExpression.split("\\s");
		if (expArr.length < 5)
			throw new CronExpressionException("错误的Cron表达式:" + cronExpression);
		if (expArr.length == 5) {
			expArr = (String[]) ArrayUtils.add(expArr, 0, "0");
		}
		Calendar c = Calendar.getInstance();
		c.setTime(lastDate);

		int month = c.get(2) + 1;
		int[] ms = getSuitNumbers(expArr[4], 1, 12);
		int mi = -1;
		boolean carryFlag = false;
		boolean flag = false;
		for (int i = 0; i < ms.length; i++) {
			if (ms[i] == month) {
				mi = i;
				flag = true;
				break;
			}
			if (ms[i] > month) {
				c.set(2, ms[i] - 1);
				carryFlag = true;
				mi = i;
				flag = true;
				break;
			}
		}
		if (!flag) {
			mi = 0;
			c.set(2, ms[mi] - 1);
			c.add(1, 1);
			carryFlag = true;
		}

		int day = c.get(5);
		int[] ds = getSuitNumbers(expArr[3], 1, c.getActualMaximum(5));
		int di = -1;
		if (carryFlag) {
			di = 0;
			c.set(5, ds[0]);
		} else {
			flag = false;
			for (int i = 0; i < ds.length; i++) {
				if (ds[i] == day) {
					di = i;
					flag = true;
					break;
				}
				if (ds[i] > day) {
					c.set(5, ds[i]);
					carryFlag = true;
					di = i;
					flag = true;
					break;
				}
			}
			if (!flag) {
				c.set(5, ds[0]);
				if (mi != ms.length - 1) {
					mi++;
				} else {
					mi = 0;
					c.add(1, 1);
				}
				c.set(2, ms[mi] - 1);
				carryFlag = true;
				di = 0;
			}

		}

		int hour = c.get(11);
		int[] hs = getSuitNumbers(expArr[2], 0, 23);
		int hi = -1;
		if (carryFlag) {
			hi = 0;
			c.set(11, hs[0]);
		} else {
			flag = false;
			for (int i = 0; i < hs.length; i++) {
				if (hs[i] == hour) {
					hi = i;
					flag = true;
					break;
				}
				if (hs[i] > hour) {
					c.set(11, hs[i]);
					carryFlag = true;
					hi = i;
					flag = true;
					break;
				}
			}
			if (!flag) {
				c.set(11, hs[0]);
				if (di != ds.length - 1) {
					di++;
				} else {
					di = 0;
					if (mi != ms.length - 1) {
						mi++;
					} else {
						mi = 0;
						c.add(1, 1);
					}
					c.set(2, ms[mi] - 1);
				}
				c.set(5, ds[di]);
				carryFlag = true;
				hi = 0;
			}

		}

		int minute = c.get(12);
		int fi = -1;
		int[] fs = getSuitNumbers(expArr[1], 0, 59);
		if (carryFlag) {
			fi = 0;
			c.set(12, fs[0]);
		} else {
			flag = false;
			for (int i = 0; i < fs.length; i++) {
				if (fs[i] == minute) {
					flag = true;
					fi = i;
					break;
				}
				if (fs[i] > minute) {
					c.set(12, fs[i]);
					fi = i;
					carryFlag = true;
					flag = true;
					break;
				}
			}
			if (!flag) {
				c.set(12, fs[0]);
				fi = 0;
				if (hi != hs.length - 1) {
					hi++;
				} else {
					if (di != ds.length - 1) {
						di++;
					} else {
						di = 0;
						if (mi != ms.length - 1) {
							mi++;
						} else {
							mi = 0;
							c.add(1, 1);
						}
						c.set(2, ms[mi] - 1);
					}
					hi = 0;
				}
				c.set(10, hs[hi]);
				carryFlag = true;
			}

		}

		int second = c.get(13);
		int[] ss = getSuitNumbers(expArr[0], 0, 59);
		if (carryFlag) {
			c.set(13, ss[0]);
		} else {
			flag = false;
			for (int i = 0; i < ss.length; i++) {
				if (ss[i] == second) {
					flag = true;
					break;
				}
				if (ss[i] > second) {
					c.set(13, ss[i]);
					carryFlag = true;
					flag = true;
					break;
				}
			}
			if (!flag) {
				c.set(13, ss[0]);
				if (fi != fs.length - 1) {
					fi++;
				} else if (hi != hs.length - 1) {
					hi++;
				} else {
					if (di != ds.length - 1) {
						di++;
					} else {
						di = 0;
						if (mi != ms.length - 1) {
							mi++;
						} else {
							mi = 0;
							c.add(1, 1);
						}
						c.set(2, ms[mi] - 1);
					}
					hi = 0;
				}

				c.set(12, fs[fi]);
				carryFlag = true;
			}

		}

		int week = c.get(7) - 1;
		if (week == 0) {
			week = 7;
		}
		int[] ws = getSuitNumbers(expArr[5], 1, 7);
		flag = false;
		for (int i = 0; i < ws.length; i++) {
			if (ws[i] == week) {
				flag = true;
				break;
			}
		}
		if (!flag) {
			c.add(5, 1);
			return getNextRunTime(c.getTime(), cronExpression);
		}
		return c.getTime();
	}

	public static Date getNextRunTime(String cronExpression)
			throws CronExpressionException {
		return getNextRunTime(new Date(), cronExpression);
	}

	public void destory() {
		cancel();
		this.mTimer.cancel();
	}

	public static void main(String[] args) {
		try {
			System.out.println(DateUtil
					.toDateTimeString(getNextRunTime("45 * * * * *")));
		} catch (CronExpressionException e) {
			e.printStackTrace();
		}
	}
}