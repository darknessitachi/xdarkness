package com.abigdreamer.java.net.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.abigdreamer.java.net.Config;
import com.abigdreamer.java.net.Constant;
import com.abigdreamer.java.net.util.LogUtil;
import com.abigdreamer.java.net.util.Mapx;
import com.abigdreamer.java.net.util.XString;

/**
 * 
 * Create on May 14, 2010 11:54:58 AM
 * 
 * @author XDarkness
 * @version 1.0
 */
public class XConnectionPool {

	private XConnectionConfig dcc;
	protected XConnection conns[];

	public XConnectionPool(String poolName) {
		System.out.println("================1=");
		dcc = new XConnectionConfig();
		dcc.PoolName = poolName;
	}

	/**
	 * 根据连接配置初始化连接池
	 * 
	 * @param config
	 */
	public XConnectionPool(XConnectionConfig config) {
		System.out.println("================2=");
		dcc = config;
		if (XConnectionPoolManager.getPoolMap().get(dcc.PoolName + ".") != null)
			throw new RuntimeException("连接池序列中己存在名为" + dcc.PoolName
					+ "的连接池，不能覆盖！");
		XConnectionPoolManager.getPoolMap().put(dcc.PoolName + ".", this);

		if (!dcc.isJNDIPool) {
			conns = new XConnection[dcc.MaxConnCount];
			try {
				for (int i = 0; i < dcc.InitConnCount; i++)
					conns[i] = createConnection(dcc, false);

				dcc.ConnCount = dcc.InitConnCount;
			} catch (Exception e) {
				LogUtil.getLogger().warn(dcc.PoolName + ",创建连接失败");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取连接池中的所有连接
	 * 
	 * @return
	 */
	public XConnection[] getDBConns() {
		return conns;
	}

	/**
	 * 清除连接池中的所有连接
	 */
	public void clear() {
		if (conns == null)
			return;
		for (int i = 0; i < conns.length; i++)
			try {
				conns[i].connection.close();
				conns[i] = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}

		dcc.ConnCount = 0;
	}

	/**
	 * 获取当前连接数据库类型
	 * 
	 * @return
	 */
	public DBTypes getDBType() {
		return dcc.DBType;
	}

	/**
	 * 获取连接配置
	 * 
	 * @return
	 */
	public XConnectionConfig getDBConnConfig() {
		return dcc;
	}

	public void init() {
		init(this.dcc, Config.getMapx());
		this.conns = new XConnection[this.dcc.MaxConnCount];
		try {
			for (int i = 0; i < this.dcc.InitConnCount; i++) {
				this.conns[i] = createConnection(this.dcc, false);
			}
			this.dcc.ConnCount = this.dcc.InitConnCount;
		} catch (Exception e) {
			LogUtil.getLogger().warn(this.dcc.PoolName + ",创建连接失败");
			e.printStackTrace();
		}
	}

	/**
	 * 根据Config初始化连接池
	 * 
	 * 
	 */
	public static void init(XConnectionConfig dcc, Mapx<String, String> map) {
		if (dcc.DBType != null)
			return;

		dcc.DBType = DBTypes.getDBType(map.getString("Database." + dcc.PoolName
				+ "Type"));
		dcc.JNDIName = map.getString("Database." + dcc.PoolName + "JNDIName");
		dcc.isLatin1Charset = "true".equalsIgnoreCase(map.getString("Database."
				+ dcc.PoolName + "isLatin1Charset"));

		if (XString.isNotEmpty(dcc.JNDIName)) {
			dcc.isJNDIPool = true;
		} else {
			dcc.DBServerAddress = map.getString("Database." + dcc.PoolName
					+ "ServerAddress");
			dcc.DBName = map.getString("Database." + dcc.PoolName + "Name");
			dcc.DBUserName = map.getString("Database." + dcc.PoolName
					+ "UserName");
			dcc.DBPassword = map.getString("Database." + dcc.PoolName
					+ "Password");
			dcc.TestTable = map.getString("Database." + dcc.PoolName
					+ "TestTable");

			if ((dcc.DBType == null) || (dcc.DBType.equals(""))) {
				throw new RuntimeException("缺少配置项DB.Type");
			}
			if ((dcc.DBServerAddress == null)
					|| (dcc.DBServerAddress.equals(""))) {
				throw new RuntimeException("缺少配置项DB.ServerAddress");
			}
			if ((dcc.DBName == null) || (dcc.DBName.equals(""))) {
				throw new RuntimeException("缺少配置项DB.Name");
			}
			if ((dcc.DBUserName == null) || (dcc.DBUserName.equals(""))) {
				throw new RuntimeException("缺少配置项DB.UserName");
			}
			if ((dcc.DBPassword == null) || (dcc.DBPassword.equals(""))) {
				throw new RuntimeException("缺少配置项DB.Password");
			}

			if (dcc.DBPort == 0) {
				dcc.DBPort = 3306;
			}
			String s = map.getString("Database." + dcc.PoolName
					+ "InitConnCount");
			try {
				dcc.InitConnCount = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				dcc.InitConnCount = 0;
				LogUtil.getLogger().warn(
						"配置项DB.InitConnCount错误," + s + "不是有效的整数，该配置项将采用默认值0!");
			}
			s = map.getString("Database." + dcc.PoolName + "MaxConnCount");
			try {
				dcc.MaxConnCount = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				dcc.MaxConnCount = 20;
				LogUtil.getLogger().warn(
						"配置项DB.MaxConnCount错误," + s + "不是有效的整数,该配置项将采用默认值20!");
			}
			s = map.getString("Database." + dcc.PoolName + "Port");
			try {
				dcc.DBPort = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				dcc.DBPort = getDefaultPort(dcc);
				LogUtil.getLogger().warn(
						"配置项DB.Port错误," + s + "不是有效的整数，该配置项将采用默认值!");
			}

		}
	}

	/**
	 * 获取连接，非长连接，默认超时时间为 2分钟
	 * 
	 * @return
	 */
	public XConnection getConnection() {
		return getConnection(false);
	}

	/**
	 * 获取连接
	 * 
	 * @param bLongTimeFlag
	 *            是否为长连接
	 * @return
	 */
	public XConnection getConnection(boolean bLongTimeFlag) {
		if (this.dcc.DBType == null) {
			init();
		}
		if (this.dcc.isJNDIPool) {
			return getJNDIPoolConnection(this.dcc);
		}
		long now = System.currentTimeMillis();
		XConnection conn = null;
		synchronized (this) {
			for (int i = 0; i < this.dcc.ConnCount; ++i) {
				conn = this.conns[i];
				if (conn.isUsing) {
					if (!(conn.LongTimeFlag)) {
						if (now - conn.LastSuccessExecuteTime > this.dcc.MaxConnUsingTime) {
							LogUtil
									.error(this.dcc.PoolName
											+ ":检测到连接使用超时，程序可能存在连接池泄漏，将自动关闭连接。以下是调用堆栈:");
							LogUtil.error(conn.CallerString);
							try {
								if (!(conn.connection.getAutoCommit())) {
									conn.connection.rollback();
								}
								conn.closeReally();
								this.dcc.ConnCount--;
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					} else {
						if ((now - conn.LastSuccessExecuteTime <= 4 * this.dcc.MaxConnUsingTime)
								|| (now - conn.LastWarnTime <= 300000L))
							continue;
						LogUtil.getLogger().warn(
								this.dcc.PoolName + ":检测到连接长时间使用，共使用了"
										+ (now - conn.LastSuccessExecuteTime)
										+ "毫秒。以下是调用堆栈:");
						LogUtil.getLogger().warn(conn.CallerString);
						conn.LastWarnTime = now;
					}
				} else if (!conn.isBlockingTransactionStarted) {
					conn.LongTimeFlag = bLongTimeFlag;
					conn.isUsing = true;
					conn.LastApplyTime = now;
					setCaller(conn);

					if (System.currentTimeMillis()
							- conn.getLastSuccessExecuteTime() > this.dcc.RefershPeriod) {

						PreparedStatement pstmt = null;
						try {
							pstmt = conn.prepareStatement("select 1 from "
									+ this.dcc.TestTable + " where 1=2");
							pstmt.execute();
						} catch (Exception e) {
							// DataAccess dAccess = new DataAccess(conn);
							// try {
							// dAccess.executeOneValue(new QueryBuilder(
							// "select 1 from " + this.dcc.TestTable
							// + " where 1=2"));
							// } catch (Exception e) {
							LogUtil.getLogger().warn(
									this.dcc.PoolName + ":发现连接失效，重新连接");
							try {
								pstmt.close();
							} catch (SQLException e2) {
								e2.printStackTrace();
							}
							try {
								conn.connection.close();
							} catch (SQLException localSQLException1) {
							}
							try {
								conn.connection = createConnection(this.dcc,
										bLongTimeFlag).connection;
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}

					}
					return conn;
				}
			}
			if (this.dcc.ConnCount >= this.dcc.MaxConnCount)
				throw new RuntimeException("DBConnPoolImpl,"
						+ this.dcc.PoolName + ":所有连接都在使用，无法分配连接!");
			try {
				conn = createConnection(this.dcc, bLongTimeFlag);
				this.conns[this.dcc.ConnCount] = conn;
				this.dcc.ConnCount += 1;
				LogUtil.info(this.dcc.PoolName + ":创建新连接，总数："
						+ this.dcc.ConnCount);
				setCaller(conn);
				return conn;
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("DBConnPoolImpl,"
						+ this.dcc.PoolName + ":创建连接失败!");
			}

		}
	}

	/**
	 * 获取JNDI方式的连接
	 * 
	 * @param dbcc
	 * @return
	 */
	public static XConnection getJNDIPoolConnection(XConnectionConfig dbcc) {
		try {
			Context ctx = new InitialContext();
			Connection conn = null;
			if (Config.isTomcat()) {
				ctx = (Context) ctx.lookup("java:comp/env");
				DataSource ds = (DataSource) ctx.lookup(dbcc.JNDIName);
				conn = ds.getConnection();
			} else if (Config.isJboss()) {
				Hashtable<String, String> environment = new Hashtable<String, String>();
				environment.put("java.naming.factory.initial",
						"org.jnp.interfaces.NamingContextFactory");
				environment.put("java.naming.factory.url.pkgs",
						"org.jboss.naming.client ");
				environment.put("java.naming.provider.url",
						"jnp://127.0.0.1:1099");
				ctx = new InitialContext(environment);
				DataSource ds = (DataSource) ctx
						.lookup("java:" + dbcc.JNDIName);
				conn = ds.getConnection();
			} else {
				DataSource ds = (DataSource) ctx.lookup(dbcc.JNDIName);
				conn = ds.getConnection();
			}
			if (dbcc.DBType == DBTypes.ORACLE) {
				Statement stmt = conn.createStatement(1005, 1008);
				stmt
						.execute("alter session set nls_date_format = 'YYYY-MM-DD HH24:MI:SS'");
				stmt.close();
			} else if (dbcc.DBType == DBTypes.MYSQL) {
				Statement stmt = conn.createStatement(1005, 1008);
				stmt.execute("SET NAMES '"
						+ Constant.GlobalCharset.replaceAll("\\-", "")
								.toLowerCase() + "'");
				stmt.close();
			}
			XConnection dbconn = new XConnection();
			dbconn.connection = conn;
			dbconn.DBConfig = dbcc;
			return dbconn;
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.warn("查找JNDI连接池失败!" + e.getMessage());
		}
		return null;
	}

	/**
	 * 根据连接配置创建连接
	 * 
	 * @param dbcc
	 * @param bLongTimeFlag
	 * @return
	 * @throws Exception
	 */
	public static XConnection createConnection(XConnectionConfig dbcc,
			boolean bLongTimeFlag) throws Exception {
		Connection conn = null;
		if (dbcc.isJNDIPool)
			return getJNDIPoolConnection(dbcc);
		if (dbcc.DBType == DBTypes.ORACLE) {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Properties props = new Properties();
			props.setProperty("user", dbcc.DBUserName);
			props.setProperty("password", dbcc.DBPassword);
			props.setProperty("oracle.jdbc.V8Compatible", "true");
			conn = DriverManager.getConnection(getJdbcUrl(dbcc), props);
			Statement stmt = conn.createStatement(1005, 1008);
			stmt
					.execute("alter session set nls_date_format = 'YYYY-MM-DD HH24:MI:SS'");
			stmt.close();
		} else if (dbcc.DBType == DBTypes.INFORMIX) {
			Class.forName("com.informix.jdbc.IfxDriver");
			conn = DriverManager.getConnection(getJdbcUrl(dbcc));
		} else if (dbcc.DBType == DBTypes.MSSQL) {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(getJdbcUrl(dbcc),
					dbcc.DBUserName, dbcc.DBPassword);
		} else if (dbcc.DBType == DBTypes.DB2) {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			conn = DriverManager.getConnection(getJdbcUrl(dbcc),
					dbcc.DBUserName, dbcc.DBPassword);
		} else if (dbcc.DBType == DBTypes.SYBASE) {
			Class.forName("com.sybase.jdbc2.jdbc.SybDriver");
			conn = DriverManager.getConnection(getJdbcUrl(dbcc),
					dbcc.DBUserName, dbcc.DBPassword);
		} else if (dbcc.DBType == DBTypes.MYSQL) {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(getJdbcUrl(dbcc),
					dbcc.DBUserName, dbcc.DBPassword);
			Statement stmt = conn.createStatement(1005, 1008);
			stmt.execute("SET NAMES '"
					+ Constant.GlobalCharset.replaceAll("\\-", "")
							.toLowerCase() + "'");
			stmt.close();
		} else if (dbcc.DBType == DBTypes.MSSQL2000) {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			conn = DriverManager.getConnection(getJdbcUrl(dbcc),
					dbcc.DBUserName, dbcc.DBPassword);
		} else {
			LogUtil.getLogger().error("目前暂不支持此种类型的数据库!");
		}
		XConnection dbconn = new XConnection();
		dbconn.connection = conn;
		dbconn.isUsing = true;
		dbconn.LongTimeFlag = bLongTimeFlag;
		dbconn.LastApplyTime = System.currentTimeMillis();
		dbconn.DBConfig = dbcc;
		return dbconn;
	}

	/**
	 * 获取jdbc连接字符串
	 * 
	 * @param dcc
	 * @return
	 */
	public static String getJdbcUrl(XConnectionConfig dcc) {
		StringBuffer sUrl = new StringBuffer(128);
		if (dcc.DBType == DBTypes.ORACLE) {
			sUrl.append("jdbc:oracle:thin:@");
			sUrl.append(dcc.DBServerAddress);
			sUrl.append(":");
			sUrl.append(dcc.DBPort);
			sUrl.append(":");
			sUrl.append(dcc.DBName);
		}
		if (dcc.DBType == DBTypes.INFORMIX) {
			sUrl.append("jdbc:informix-sqli://");
			sUrl.append(dcc.DBServerAddress);
			sUrl.append(":");
			sUrl.append(dcc.DBPort);
			sUrl.append(dcc.DBName);
			sUrl.append(":");
			sUrl.append("informixserver=");
			sUrl.append(dcc.DBName);
			sUrl.append(";");
			sUrl.append("user=");
			sUrl.append(dcc.DBUserName);
			sUrl.append(";");
			sUrl.append("dcc.DBPassword=");
			sUrl.append(dcc.DBPassword);
			sUrl.append(";");
		}
		if (dcc.DBType == DBTypes.MSSQL) {
			sUrl.append("jdbc:sqlserver://");
			sUrl.append(dcc.DBServerAddress);
			sUrl.append(":");
			sUrl.append(dcc.DBPort);
			sUrl.append(";DatabaseName=");
			sUrl.append(dcc.DBName);
		}
		if (dcc.DBType == DBTypes.MSSQL2000) {
			sUrl.append("jdbc:jtds:sqlserver://");
			sUrl.append(dcc.DBServerAddress);
			sUrl.append(":");
			sUrl.append(dcc.DBPort);
			sUrl.append(";DatabaseName=");
			sUrl.append(dcc.DBName);
			sUrl.append(";useLOBs=false");
		}
		if (dcc.DBType == DBTypes.DB2) {
			sUrl.append("jdbc:db2://");
			sUrl.append(dcc.DBServerAddress);
			sUrl.append(":");
			sUrl.append(dcc.DBPort);
			sUrl.append("/");
			sUrl.append(dcc.DBName);
		}
		if (dcc.DBType == DBTypes.SYBASE) {
			sUrl.append("jdbc:sybase:Tds:");
			sUrl.append(dcc.DBServerAddress);
			sUrl.append(":");
			sUrl.append(dcc.DBPort);
			sUrl.append("/");
			sUrl.append(dcc.DBName);
		}
		if (dcc.DBType == DBTypes.MYSQL) {
			sUrl.append("jdbc:mysql://");
			sUrl.append(dcc.DBServerAddress);
			sUrl.append(":");
			sUrl.append(dcc.DBPort);
			sUrl.append("/");
			sUrl.append(dcc.DBName);
		}
		return sUrl.toString();
	}

	/**
	 * 设置连接堆栈信息
	 * 
	 * @param conn
	 */
	private void setCaller(XConnection conn) {
		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < stack.length; i++) {
			StackTraceElement ste = stack[i];
			if (ste.getClassName().indexOf("DBConnPoolImpl") == -1) {
				sb.append("\t");
				sb.append(ste.getClassName());
				sb.append(".");
				sb.append(ste.getMethodName());
				sb.append("(),行号:");
				sb.append(ste.getLineNumber());
				sb.append("\n");
			}
		}

		conn.CallerString = sb.toString();
	}

	/**
	 * 获取数据库默认端口
	 * 
	 * @return
	 */
	private static int getDefaultPort(XConnectionConfig dcc) {
		if (dcc.DBType == DBTypes.MSSQL) {
			return 1433;
		}
		if (dcc.DBType == DBTypes.ORACLE) {
			return 1521;
		}
		if (dcc.DBType == DBTypes.DB2) {
			return 50000;
		}
		if (dcc.DBType == DBTypes.MYSQL) {
			return 3306;
		}
		if (dcc.DBType.equals("SYBASE")) {
			return 5000;
		}
		return 0;
	}

}
