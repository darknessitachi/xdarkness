package com.abigdreamer.java.net.connection;

import java.lang.reflect.Method;
import java.sql.SQLException;

import com.abigdreamer.java.net.config.SystemConfig;
import com.abigdreamer.java.net.util.Mapx;
import com.abigdreamer.java.net.util.XString;

/**
 * 连接池管理器，用于获取数据库连接
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
		return poolName;
	}

	public static XConnectionConfig getDBConnConfig(String poolName) {

		return getDBConnectionPool(poolName).getDBConnConfig();
	}

	public static XConnectionPool getDBConnectionPool(String poolName) {
		poolName = convertPoolName(poolName);

		XConnectionPool pool = PoolMap.get(poolName);
		if (pool == null) {
			synchronized (mutex) {
				if (DBConfig.getDatabase(poolName) != null) {
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
	
	public static boolean isDB2() {
		return XConnectionPoolManager.getDBConnConfig().DBType == DBTypes.DB2;
	}

	public static boolean isOracle() {
		return XConnectionPoolManager.getDBConnConfig().DBType == DBTypes.ORACLE;
	}

	public static boolean isMysql() {
		return XConnectionPoolManager.getDBConnConfig().DBType == DBTypes.MYSQL;
	}

	public static boolean isSQLServer() {
		return XConnectionPoolManager.getDBConnConfig().DBType == DBTypes.MSSQL;
	}

	public static boolean isTomcat() {
		if (XString.isEmpty(SystemConfig.ContainerInfo)) {
			getJBossInfo();
		}
		return SystemConfig.ContainerInfo.toLowerCase().indexOf("tomcat") >= 0;
	}

	protected static void getJBossInfo() {
		String jboss = System.getProperty("jboss.home.dir");
		if (XString.isNotEmpty(jboss))
			try {
				Class<?> c = Class.forName("org.jboss.Version");
				Method m = c.getMethod("getInstance");
				Object o = m.invoke(null);
				m = c.getMethod("getMajor");
				Object major = m.invoke(o);
				m = c.getMethod("getMinor");
				Object minor = m.invoke(o);
				m = c.getMethod("getRevision");
				Object revision = m.invoke(o);
				m = c.getMethod("getTag");
				Object tag = m.invoke(o);
				SystemConfig.ContainerInfo = "JBoss/" + major + "."
						+ minor + "." + revision + "." + tag;
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public static boolean isJboss() {
		if (XString.isEmpty(SystemConfig.ContainerInfo)) {
			getJBossInfo();
		}
		return SystemConfig.ContainerInfo.toLowerCase().indexOf("jboss") >= 0;
	}

	public static boolean isWeblogic() {
		return SystemConfig.ContainerInfo.toLowerCase().indexOf("weblogic") >= 0;
	}

	public static boolean isWebSphere() {
		return SystemConfig.ContainerInfo.toLowerCase().indexOf("websphere") >= 0;
	}

}
