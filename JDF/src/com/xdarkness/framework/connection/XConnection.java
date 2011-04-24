package com.xdarkness.framework.connection;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.ArrayUtils;

import com.xdarkness.framework.connection.nativejdbc.CommonsDbcpNativeJdbcExtractor;
import com.xdarkness.framework.connection.nativejdbc.JBossNativeJdbcExtractor;
import com.xdarkness.framework.connection.nativejdbc.WebLogicNativeJdbcExtractor;
import com.xdarkness.framework.connection.nativejdbc.WebSphereNativeJdbcExtractor;

/**
 * 
 * @author Darkness Create on 2010-5-19 下午04:19:06
 * @version 1.0
 */
public class XConnection implements Connection {

	protected Connection connection;
	protected XConnectionConfig DBConfig;

	protected boolean LongTimeFlag = false;
	protected long LastApplyTime;
	protected long LastWarnTime;
	protected long LastSuccessExecuteTime = System.currentTimeMillis();
	protected boolean isUsing = false;
	public boolean isBlockingTransactionStarted;

	/**
	 * 获取服务器自身本地连接
	 * 
	 * @return
	 */
	public Connection getPhysicalConnection() {
		if (this.DBConfig.isJNDIPool) {
			try {
				ServerTypes serverType = getServerType();
				if (serverType == ServerTypes.Tomcat) {
					return CommonsDbcpNativeJdbcExtractor
							.doGetNativeConnection(this.connection);
				} else if (serverType == ServerTypes.Weblogic) {
					return WebLogicNativeJdbcExtractor
							.doGetNativeConnection(this.connection);
				} else if (serverType == ServerTypes.WebSphere) {
					return WebSphereNativeJdbcExtractor
							.doGetNativeConnection(this.connection);
				} else {
					return JBossNativeJdbcExtractor
							.doGetNativeConnection(this.connection);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return this.connection;
	}

	/**
	 * 设置连接为非使用状态
	 */
	public void close() throws SQLException {
		this.isUsing = false;
		this.LastApplyTime = 0L;
		setAutoCommit(true);
		if (this.DBConfig.isJNDIPool)
			this.connection.close();
	}

	/**
	 * 关闭连接，并从连接池中删除
	 * 
	 * @throws SQLException
	 */
	public void closeReally() throws SQLException {
		XConnectionPool pool = (XConnectionPool) XConnectionPoolManager.PoolMap
				.get(this.DBConfig.DBName + ".");// .get(this.PoolName);
		if (pool != null) {
			ArrayUtils.removeElement(pool.conns, this);
			this.connection.close();
		}
	}

	public long getLastSuccessExecuteTime() {
		return this.LastSuccessExecuteTime;
	}

	public void setLastSuccessExecuteTime(long lastSuccessExecuteTime) {
		this.LastSuccessExecuteTime = lastSuccessExecuteTime;
	}

	public DBTypes getDBType() {
		if (this.DBConfig.DBType == null)
			this.DBConfig.DBType = DBTypes.MYSQL;
		return this.DBConfig.DBType;
	}

	public XConnectionConfig getDBConfig() {
		return DBConfig;
	}
	public void setDBType(DBTypes type) {
		this.DBConfig.DBType = type;
	}

	public String getPoolName() {
		return this.DBConfig.PoolName;
	}

	public void setPoolName(String poolName) {
		this.DBConfig.PoolName = poolName;
	}
	
	protected String CallerString = null;

	public ServerTypes getServerType() {
		return DBConfig.ServerType;
	}

	public void setServerType(ServerTypes serverType) {
		this.DBConfig.ServerType = serverType;
	}

	// ////////////////////////////////////////////////////////////////////////////////
	// ///////////// Proxy the inner Connection object connection
	// ////////////////////////////////
	// //////////////////////////////////////////////////////////////////////////////
	public int getHoldability() throws SQLException {
		return this.connection.getHoldability();
	}

	public int getTransactionIsolation() throws SQLException {
		return this.connection.getTransactionIsolation();
	}

	public void clearWarnings() throws SQLException {
		this.connection.clearWarnings();
	}

	public void commit() throws SQLException {
		this.connection.commit();
	}

	public void rollback() throws SQLException {
		this.connection.rollback();
	}

	public boolean getAutoCommit() throws SQLException {

		return this.connection.getAutoCommit();

	}

	public boolean isClosed() throws SQLException {
		return this.connection.isClosed();
	}

	public boolean isReadOnly() throws SQLException {
		return this.connection.isReadOnly();
	}

	public void setHoldability(int holdability) throws SQLException {
		this.connection.setHoldability(holdability);
	}

	public void setTransactionIsolation(int level) throws SQLException {
		this.connection.setTransactionIsolation(level);
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		if (this.connection.getAutoCommit() != autoCommit)
			this.connection.setAutoCommit(autoCommit);
	}

	public void setReadOnly(boolean readOnly) throws SQLException {
		this.connection.setReadOnly(readOnly);
	}

	public String getCatalog() throws SQLException {
		return this.connection.getCatalog();
	}

	public void setCatalog(String catalog) throws SQLException {
		this.connection.setCatalog(catalog);
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		return this.connection.getMetaData();
	}

	public SQLWarning getWarnings() throws SQLException {
		return this.connection.getWarnings();
	}

	public Savepoint setSavepoint() throws SQLException {
		return this.connection.setSavepoint();
	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		this.connection.releaseSavepoint(savepoint);
	}

	public void rollback(Savepoint savepoint) throws SQLException {
		this.connection.rollback(savepoint);
	}

	public Statement createStatement() throws SQLException {
		return this.connection.createStatement();
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency)
			throws SQLException {
		return this.connection.createStatement(resultSetType,
				resultSetConcurrency);
	}

	public Statement createStatement(int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return this.connection.createStatement(resultSetType,
				resultSetConcurrency, resultSetHoldability);
	}

	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return this.connection.getTypeMap();
	}

	public String nativeSQL(String sql) throws SQLException {
		return this.connection.nativeSQL(sql);
	}

	public CallableStatement prepareCall(String sql) throws SQLException {
		return this.connection.prepareCall(sql);
	}

	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		return this.connection.prepareCall(sql, resultSetType,
				resultSetConcurrency);
	}

	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return this.connection.prepareCall(sql, resultSetType,
				resultSetConcurrency, resultSetHoldability);
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return this.connection.prepareStatement(sql);
	}

	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
			throws SQLException {
		return this.connection.prepareStatement(sql, autoGeneratedKeys);
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		return this.connection.prepareStatement(sql, resultSetType,
				resultSetConcurrency);
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return this.connection.prepareStatement(sql, resultSetType,
				resultSetConcurrency, resultSetHoldability);
	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
			throws SQLException {
		return this.connection.prepareStatement(sql, columnIndexes);
	}

	public Savepoint setSavepoint(String name) throws SQLException {
		return this.connection.setSavepoint(name);
	}

	public PreparedStatement prepareStatement(String sql, String[] columnNames)
			throws SQLException {
		return this.connection.prepareStatement(sql, columnNames);
	}

	public Array createArrayOf(String typeName, Object[] elements)
			throws SQLException {

		return this.connection.createArrayOf(typeName, elements);
	}

	public Blob createBlob() throws SQLException {

		return this.connection.createBlob();
	}

	public Clob createClob() throws SQLException {

		return this.connection.createClob();
	}

	public NClob createNClob() throws SQLException {

		return this.connection.createNClob();
	}

	public SQLXML createSQLXML() throws SQLException {

		return this.connection.createSQLXML();
	}

	public Struct createStruct(String typeName, Object[] attributes)
			throws SQLException {

		return this.connection.createStruct(typeName, attributes);
	}

	public Properties getClientInfo() throws SQLException {

		return this.connection.getClientInfo();
	}

	public String getClientInfo(String name) throws SQLException {

		return this.connection.getClientInfo(name);
	}

	public boolean isValid(int timeout) throws SQLException {

		return this.connection.isValid(timeout);
	}

	public void setClientInfo(Properties properties)
			throws SQLClientInfoException {

		this.connection.setClientInfo(properties);
	}

	public void setClientInfo(String name, String value)
			throws SQLClientInfoException {

		this.connection.setClientInfo(name, value);
	}

	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {

		this.connection.setTypeMap(map);
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {

		return this.connection.isWrapperFor(iface);
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {

		return this.connection.unwrap(iface);
	}

}