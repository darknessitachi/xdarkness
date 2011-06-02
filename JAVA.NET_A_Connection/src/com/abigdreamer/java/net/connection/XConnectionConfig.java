package com.abigdreamer.java.net.connection;


/**
 * 
 * Create on May 14, 2010 11:29:37 AM
 * 
 * @author XDarkness
 * @version 1.0
 */
public class XConnectionConfig {

	public String JNDIName = null;

	public boolean isJNDIPool = false;

	public int MaxConnCount = 1000;

	public int InitConnCount = 0;
	public int ConnCount;
	public int MaxConnUsingTime = 2 * 60 * 1000;

	public int RefershPeriod = 60 * 1000;
	public DBTypes DBType;
	public ServerTypes ServerType = ServerTypes.Tomcat;
	public String DBServerAddress;
	public int DBPort;
	public String DBName;
	public String DBUserName;
	public String DBPassword;
	public String TestTable;
	public String PoolName;
	public static String Charset = "UTF-8";
	public boolean isLatin1Charset;

	public boolean isOracle() {
		return DBType == DBTypes.ORACLE;
	}

	public boolean isDB2() {
		return DBType == DBTypes.DB2;
	}

	public boolean isSQLServer() {
		return DBType == DBTypes.MSSQL;
	}

	public boolean isSQLServer2000() {
		return DBType == DBTypes.MSSQL2000;
	}

	public boolean isMysql() {
		return DBType == DBTypes.MYSQL;
	}

	public boolean isSybase() {
		return DBType == DBTypes.SYBASE;
	}
}
