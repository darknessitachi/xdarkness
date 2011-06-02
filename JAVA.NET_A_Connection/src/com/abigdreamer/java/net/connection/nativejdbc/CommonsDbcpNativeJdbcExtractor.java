package com.abigdreamer.java.net.connection.nativejdbc;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 * Create on May 14, 2010 11:39:57 AM
 * @author XDarkness
 * @version 1.0
 */
public class CommonsDbcpNativeJdbcExtractor {

	public CommonsDbcpNativeJdbcExtractor() {
	}

	private static Object getInnermostDelegate(Object connection) throws SQLException {
		if (connection == null)
			return null;
		try {
			Class<?> classToAnalyze = connection.getClass();
			while (!(Modifier.isPublic(classToAnalyze.getModifiers()))) {
				classToAnalyze = classToAnalyze.getSuperclass();
				if (classToAnalyze == null) {
					return connection;
				}
			}
			Method getInnermostDelegate = classToAnalyze
					.getMethod(GET_INNERMOST_DELEGATE_METHOD_NAME);
			Object delegate = getInnermostDelegate.invoke(connection, new Object[0]);
			return ((delegate != null) ? delegate : connection);
		} catch (SecurityException ex) {
			throw new IllegalStateException(
					"Commons DBCP getInnermostDelegate method is not accessible: "
							+ ex);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static Connection doGetNativeConnection(Connection connection)
			throws SQLException {
		return (Connection) getInnermostDelegate(connection);
	}

	private static final String GET_INNERMOST_DELEGATE_METHOD_NAME = "getInnermostDelegate";
}
