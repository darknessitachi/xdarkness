package com.xdarkness.framework.connection;

import java.sql.SQLException;

import com.xdarkness.framework.Config;
import com.xdarkness.framework.util.Mapx;

/**
 * 
 * Create on May 14, 2010 11:41:46 AM
 * 
 * @author XDarkness
 * @version 1.0
 */
public class XConnectionPoolManager {

	protected static Mapx<String, XConnectionPool> PoolMap = new Mapx<String, XConnectionPool>();
	private static Object mutex = new Object();

	/**
	 * 转换连接池的名称
	 * 
	 * @param poolName
	 * @return
	 */
	private static String convertPoolName(String poolName) {
		if (poolName == null || poolName.equals(""))
			poolName = "Default";
		return poolName + ".";
	}

	public static XConnectionConfig getDBConnConfig(String poolName) {

		return getDBConnectionPool(poolName).getDBConnConfig();
	}

	public static XConnectionPool getDBConnectionPool(String poolName) {
		poolName = convertPoolName(poolName);

		XConnectionPool pool = PoolMap.get(poolName);
		if (pool == null) {
			synchronized (mutex) {
				if (Config.getValue("Database." + poolName + "Type") != null) {
					pool = new XConnectionPool(poolName);
					PoolMap.put(poolName, pool);
				} else {
					throw new RuntimeException("指定的连接池不存在:" + poolName);
				}
			}
		}
		return pool;
	}

	public static XConnection getConnection(String poolName,
			boolean bLongTimeFlag) {
		return getConnection(poolName, bLongTimeFlag, true);
	}

	public static XConnection getConnection(String poolName,
			boolean bLongTimeFlag, boolean bCurrentThreadConnectionFlag) {

		// if (bCurrentThreadConnectionFlag) {
		// XConnection conn = BlockingTransaction
		// .getCurrentThreadConnection();
		// if ((conn != null) && (conn.DBConfig.PoolName.equals(poolName))) {
		// return conn;
		// }
		// }

		return getDBConnectionPool(poolName).getConnection(bLongTimeFlag);
	}

	public static Mapx<String, XConnectionPool> getPoolMap() {
		return PoolMap;
	}

	public XConnectionPoolManager() {
	}

	public static XConnection getConnection() {
		return getConnection(null, false);
	}

	public static XConnection getConnection(boolean bLongTimeFlag) {
		return getConnection(null, bLongTimeFlag);
	}

	public static XConnection getConnection(String poolName) {
		return getConnection(poolName, false);
	}

	private static boolean inited = false;

	public static XConnectionConfig getDBConnConfig() {
		if (!inited) {
			try {
				getConnection().close();
				inited = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return getDBConnConfig(null);
	}
}
