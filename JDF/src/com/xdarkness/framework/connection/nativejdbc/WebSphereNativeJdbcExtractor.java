package com.xdarkness.framework.connection.nativejdbc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 * @author Darkness Create on 2010-5-19 下午05:35:39
 * @version 1.0
 */
public class WebSphereNativeJdbcExtractor {

	private static final String JDBC_ADAPTER_CONNECTION_NAME_5 = "com.ibm.ws.rsadapter.jdbc.WSJdbcConnection";

	private static final String JDBC_ADAPTER_UTIL_NAME_5 = "com.ibm.ws.rsadapter.jdbc.WSJdbcUtil";

	private static Class<?> webSphere5ConnectionClass;

	private static Method webSphere5NativeConnectionMethod;

	public WebSphereNativeJdbcExtractor() {
	}

	public static void init() {
		try {
			webSphere5ConnectionClass = WebSphereNativeJdbcExtractor.class
					.getClassLoader().loadClass(JDBC_ADAPTER_CONNECTION_NAME_5);
			Class<?> jdbcAdapterUtilClass = com.xdarkness.framework.connection.nativejdbc.WebSphereNativeJdbcExtractor.class
					.getClassLoader().loadClass(JDBC_ADAPTER_UTIL_NAME_5);
			webSphere5NativeConnectionMethod = jdbcAdapterUtilClass.getMethod(
					"getNativeConnection",
					new Class[] { webSphere5ConnectionClass });
		} catch (Exception ex) {
			throw new IllegalStateException(
					"Could not initialize WebSphereNativeJdbcExtractor because WebSphere API classes are not available: "
							+ ex);
		}
	}

	public static Connection doGetNativeConnection(Connection con)
			throws SQLException {
		if (webSphere5ConnectionClass == null) {
			init();
		}
		if (webSphere5ConnectionClass.isAssignableFrom(con.getClass())) {
			try {
				return ((Connection) webSphere5NativeConnectionMethod.invoke(
						null, new Object[] { con }));
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
