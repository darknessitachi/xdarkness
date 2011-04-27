package com.abigdreamer.java.net.connection.nativejdbc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 * @author Darkness
 * Create on 2010-5-19 下午05:35:23
 * @version 1.0
 */
public class JBossNativeJdbcExtractor {
	private static final String WRAPPED_CONNECTION_NAME = "org.jboss.resource.adapter.jdbc.WrappedConnection";
	private static Class<?> wrappedConnectionClass;
	private static Method getUnderlyingConnectionMethod;
	

    public JBossNativeJdbcExtractor() {
    }

	public static void init() {
		try {
			wrappedConnectionClass = JBossNativeJdbcExtractor.class
					.getClassLoader()
					.loadClass(WRAPPED_CONNECTION_NAME);
			getUnderlyingConnectionMethod = wrappedConnectionClass.getMethod(
					"getUnderlyingConnection");
		} catch (Exception ex) {
			throw new IllegalStateException(
					"Could not initialize JBossNativeJdbcExtractor because JBoss API classes are not available: "
							+ ex);
		}
	}

	public static Connection doGetNativeConnection(Connection con)
			throws SQLException {
		if (wrappedConnectionClass == null) {
			init();
		}
		if (wrappedConnectionClass.isAssignableFrom(con.getClass())) {
			try {
				return (Connection) getUnderlyingConnectionMethod.invoke(con,
						new Object[0]);
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
