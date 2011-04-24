package com.xdarkness.framework.util;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.apache.tools.ant.filters.StringInputStream;

import com.xdarkness.framework.Config;
import com.xdarkness.framework.io.FileUtil;

public class LogUtil {
	private static boolean initFlag = false;

	private static Log skylog = null;

	public static void init() {
		PrintStream syserr = System.err;
		try {
			Log4jErrorPrintStream errStream = new Log4jErrorPrintStream(
					System.err);
			System.setErr(errStream);
			String fileName = Config.getClassesPath() + "log4j.config";
			String txt = FileUtil.readText(fileName);
			txt = XString.replaceEx(txt, "%{ContextRealPath}", Config
					.getContextRealPath());
			Properties ps = new Properties();
			StringInputStream si = new StringInputStream(txt);
			ps.load(si);
			si.close();
			PropertyConfigurator.configure(ps);
			skylog = LogFactory.getLog("SKY");
		} catch (Exception e) {
			System.setErr(syserr);
			e.printStackTrace();
		}
	}

	public static Log getLogger() {
		if (!initFlag) {
			init();
			initFlag = true;
		}
		return skylog;
	}

	public static void info(Object obj) {
		Log log = getLogger();
		if (log == null) {
			return;
		}
		log.info(obj);
	}

	public static void debug(Object obj) {
		Log log = getLogger();
		if (log == null) {
			return;
		}
		log.debug(obj);
	}

	public static void warn(Object obj) {
		Log log = getLogger();
		if (log == null) {
			return;
		}
		log.warn(obj);
	}

	public static void error(Object obj) {
		Log log = getLogger();
		if (log == null) {
			return;
		}
		log.error(obj);
	}

	public static void fatal(Object obj) {
		Log log = getLogger();
		if (log == null) {
			return;
		}
		log.fatal(obj);
	}

	static class Log4jErrorPrintStream extends PrintStream {
		Log4jErrorPrintStream(OutputStream out) {
			super(out);
		}

		public void println(String str) {
			try {
				if (LogUtil.skylog != null)
					LogUtil.skylog.error(str);
			} catch (Throwable e) {
				System.out
						.println("LogUtil.Log4jErrorPrintStream.println()发生错误："
								+ e.getMessage());
			}
		}

		public void println(Object obj) {
			try {
				if (LogUtil.skylog != null)
					LogUtil.skylog.error(obj);
			} catch (Throwable e) {
				System.out
						.println("LogUtil.Log4jErrorPrintStream.println()发生错误："
								+ e.getMessage());
			}
		}
	}
}

/*
 * com.xdarkness.framework.utility.LogUtil JD-Core Version: 0.6.0
 */