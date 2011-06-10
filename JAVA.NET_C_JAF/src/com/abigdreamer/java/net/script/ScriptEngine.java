package com.abigdreamer.java.net.script;

import java.io.ByteArrayInputStream;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bouncycastle.jce.provider.JDKX509CertificateFactory;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.ScriptableObject;

import bsh.EvalError;
import bsh.Interpreter;

import com.abigdreamer.java.net.Config;
import com.abigdreamer.java.net.io.FileUtil;
import com.abigdreamer.java.net.license.SystemInfo;
import com.abigdreamer.java.net.security.ZRSACipher;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.LogUtil;
import com.abigdreamer.java.net.util.Mapx;
import com.abigdreamer.java.net.util.XString;

public class ScriptEngine {
	public static final int LANG_JAVASCRIPT = 0;
	public static final int LANG_JAVA = 1;
	private int language;
	private ArrayList carr = new ArrayList();

	private ArrayList parr = new ArrayList();

	private Mapx funcMap = new Mapx();
	private Mapx exceptionMap = new Mapx();

	private Mapx varMap = new Mapx();
	private boolean isNeedCheck;
	private static final Pattern JavaLineInfoPattern = Pattern.compile(
			"error at line (\\d*?)\\, column (\\d*?)\\.", 34);

	public ScriptEngine(int language) {
		this.language = language;
	}

	public void importClass(String className) {
		this.carr.add(className);
	}

	public void importPackage(String pacckageName) {
		this.parr.add(pacckageName);
	}

	public void compileFunction(String funcName, String script)
			throws EvalException {
		if ((this.isNeedCheck) && (!SecurityChecker.check(script))) {
			EvalException ee = new EvalException("脚本中引用了被禁止的包或者类!", "", "", 0,
					0);
			this.exceptionMap.put(funcName, ee);
			throw ee;
		}
		this.exceptionMap.remove(funcName);

		StringBuffer sb = new StringBuffer();
		if (this.language == 1) {
			for (int i = 0; i < this.carr.size(); i++) {
				sb.append("import " + this.carr.get(i) + ";\n");
			}
			for (int i = 0; i < this.parr.size(); i++) {
				sb.append("import " + this.parr.get(i) + ".*;\n");
			}
			sb.append(funcName + "(){\n");
			sb.append(script);
			sb.append("}\n");
			Interpreter itp = new Interpreter();
			try {
				itp.eval(sb.toString());
			} catch (EvalError e) {
				e.printStackTrace();
				String message = e.getMessage();
				Matcher m = JavaLineInfoPattern.matcher(message);
				int row = 0;
				int col = 0;
				String lineSource = "";
				if (m.find()) {
					row = Integer.parseInt(m.group(1));
					if (row <= this.carr.size()) {
						message = "引入类发生错误!";
						lineSource = this.carr.get(row - 1).toString();
					} else if (row <= this.parr.size()) {
						message = "引入包发生错误!";
						lineSource = this.parr.get(row - 1).toString();
					} else {
						row = row - this.carr.size() - this.parr.size() - 1;
						lineSource = script.split("\\n")[(row - 1)];
						col = Integer.parseInt(m.group(2));
					}
				}
				throw new EvalException("第" + row + "行有语法错误: " + lineSource,
						message, lineSource, row, col);
			}
			this.funcMap.put(funcName, itp);
		} else {
			for (int i = 0; i < this.carr.size(); i++) {
				sb.append("importClass(Packages." + this.carr.get(i) + ");\n");
			}
			for (int i = 0; i < this.parr.size(); i++) {
				sb
						.append("importPackage(Packages." + this.parr.get(i)
								+ ");\n");
			}
			sb.append("function " + funcName + "(){\n");
			sb.append(script);
			sb.append("}\n");
			sb.append(funcName + "();\n");
			Context ctx = Context.enter();
			ctx.setOptimizationLevel(1);
			Script compiledScript = null;
			try {
				compiledScript = ctx.compileString(sb.toString(), "", 1, null);
			} catch (EvaluatorException e) {
				int row = e.lineNumber() - 1;
				throw new EvalException(
						"第" + row + "行有语法错误: " + e.lineSource(),
						e.getMessage(), e.lineSource(), row, e.columnNumber());
			}
			this.funcMap.put(funcName, compiledScript);
		}
	}

	public Object executeFunction(String funcName) throws EvalException {
		Object ee = this.exceptionMap.get(funcName);
		if (ee != null) {
			throw ((EvalException) ee);
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

		Object o = this.funcMap.get(funcName);
		if (this.language == 1)
			try {
				Interpreter itp = (Interpreter) o;
				Object[] ks = this.varMap.keyArray();
				Object[] vs = this.varMap.valueArray();
				for (int i = 0; i < this.varMap.size(); i++) {
					itp.set(ks[i].toString(), vs[i]);
				}
				return itp.eval(funcName + "();");
			} catch (EvalError e) {
				e.printStackTrace();
				String message = e.getMessage();
				int col = 0;
				int row = e.getErrorLineNumber() - 1;
				throw new EvalException("第" + row + "行有语法错误: "
						+ e.getErrorText(), message, e.getErrorText(), row, col);
			}
		try {
			Script compiledScript = (Script) o;
			Context ctx = Context.enter();
			ImporterTopLevel scope = new ImporterTopLevel(ctx);
			Object[] ks = this.varMap.keyArray();
			Object[] vs = this.varMap.valueArray();
			for (int i = 0; i < this.varMap.size(); i++) {
				ScriptableObject.putProperty(scope, ks[i].toString(), vs[i]);
			}
			return compiledScript.exec(ctx, scope);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void setVar(String name, Object value) {
		this.varMap.put(name, value);
	}

	public int getLanguage() {
		return this.language;
	}

	public static Object evalJavaScript(String js) throws EvalException {
		Context ctx = Context.enter();
		ImporterTopLevel scope = new ImporterTopLevel(ctx);
		ctx.setOptimizationLevel(1);
		Script compiledScript = null;
		int row;
		try {
			compiledScript = ctx.compileString(js, "", 1, null);
			return compiledScript.exec(ctx, scope);
		} catch (EvaluatorException e) {
			row = e.lineNumber() - 1;
			throw new EvalException("第" + row + "行有语法错误: " + e.lineSource(), e
					.getMessage(), e.lineSource(), row, e.columnNumber());
		}
		
	}

	public static Object evalJava(String java) throws EvalException {
		Interpreter itp = new Interpreter();
		String message;
		int row;
		int col;
		String lineSource;
		try {
			return itp.eval(java);
		} catch (EvalError e) {
			message = e.getMessage();
			Matcher m = JavaLineInfoPattern.matcher(message);
			row = 0;
			col = 0;
			lineSource = "";
			if (m.find()) {
				row = Integer.parseInt(m.group(1));
				lineSource = java.split("\\n")[(row - 1)];
				col = Integer.parseInt(m.group(2));
			}
		}
		throw new EvalException("第" + row + "行有语法错误: " + lineSource, message,
				lineSource, row, col);
	}

	public void exit() {
		if (this.language == 0)
			Context.exit();
	}

	public boolean isNeedCheck() {
		return this.isNeedCheck;
	}

	public void setNeedCheck(boolean isNeedCheck) {
		this.isNeedCheck = isNeedCheck;
	}

	public static void main(String[] args) {
		ScriptEngine se = new ScriptEngine(1);
		se.setNeedCheck(false);
		se.importPackage("com.xdarkness.framework.cache");
		se.importPackage("com.xdarkness.framework.data");
		se.importPackage("com.xdarkness.framework.utility");
		se.importPackage("com.xdarkness.statical");
		se.importPackage("com.xdarkness.cms.template");
		se.importPackage("com.xdarkness.cms.pub");
		se.importPackage("com.xdarkness.cms.site");
		se.importPackage("com.xdarkness.cms.document");
		String script = FileUtil.readText("H:/Script.txt");
		try {
			se.compileFunction("a", script);
			LogUtil.info(se.executeFunction("a"));
		} catch (EvalException e) {
			e.printStackTrace();
		}
	}
}