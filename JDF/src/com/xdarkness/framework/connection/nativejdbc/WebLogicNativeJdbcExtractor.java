package com.xdarkness.framework.connection.nativejdbc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 * @author Darkness Create on 2010-5-19 下午05:35:32
 * @version 1.0
 */
public class WebLogicNativeJdbcExtractor {

	private static final String JDBC_EXTENSION_NAME = "weblogic.jdbc.extensions.WLConnection";

	private static Class<?> jdbcExtensionClass;

	private static Method getVendorConnectionMethod;

	public WebLogicNativeJdbcExtractor() {
	}

	public static void init() {
		try {
			jdbcExtensionClass = WebLogicNativeJdbcExtractor.class
					.getClassLoader().loadClass(JDBC_EXTENSION_NAME);
			getVendorConnectionMethod = jdbcExtensionClass
					.getMethod("getVendorConnection");
		} catch (Exception ex) {
			throw new IllegalStateException(
					"Could not initialize WebLogicNativeJdbcExtractor because WebLogic API classes are not available: "
							+ ex);
		}
	}

	public static Connection doGetNativeConnection(Connection con)
			throws SQLException {
		if (jdbcExtensionClass == null) {
			init();
		}
		if (jdbcExtensionClass.isAssignableFrom(con.getClass())) {
			try {
				return ((Connection) getVendorConnectionMethod.invoke(con,
						new Object[0]));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return con;
	}

}
