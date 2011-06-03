package com.abigdreamer.java.net.sql;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abigdreamer.java.net.Config;
import com.abigdreamer.java.net.Constant;
import com.abigdreamer.java.net.connection.DBTypes;
import com.abigdreamer.java.net.connection.XConnection;
import com.abigdreamer.java.net.connection.XConnectionPoolManager;
import com.abigdreamer.java.net.data.LobUtil;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.util.LogUtil;

/**
 * 
 * Create on May 14, 2010 1:11:48 PM
 * 
 * @author XDarkness
 * @version 1.0
 */
public class DataAccess implements IDataAccess  {

	public XConnection conn;

	public DataAccess() {
	}

	public DataAccess(XConnection conn) {
		this.conn = conn;
	}

	private void initConnection() {
		if (conn == null)
			conn = XConnectionPoolManager.getConnection();
	}

	/* (non-Javadoc)
	 * @see com.xdarkness.framework.sql.IDataAccess#getConnection()
	 */
	public XConnection getConnection() {

		initConnection();

		return conn;
	}

	/* (non-Javadoc)
	 * @see com.xdarkness.framework.sql.IDataAccess#setAutoCommit(boolean)
	 */
	public void setAutoCommit(boolean bCommit) throws SQLException {
		getConnection().setAutoCommit(bCommit);
	}

	/* (non-Javadoc)
	 * @see com.xdarkness.framework.sql.IDataAccess#commit()
	 */
	public void commit() throws SQLException {
		if (conn != null) {
			conn.commit();
		}
	}

	/* (non-Javadoc)
	 * @see com.xdarkness.framework.sql.IDataAccess#rollback()
	 */
	public void rollback() throws SQLException {
		if (conn != null) {
			conn.rollback();
		}
	}

	/* (non-Javadoc)
	 * @see com.xdarkness.framework.sql.IDataAccess#close()
	 */
	public void close() throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}

	private PreparedStatement getStatement(String sql) throws SQLException{
		return conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY,
				ResultSet.CONCUR_READ_ONLY);
	}
	private PreparedStatement initStmt(String sql, List<?> params) throws SQLException {
		PreparedStatement stmt = getStatement(sql);

		setParams(stmt, params, this.conn);

		return stmt;
	}
	
	private PreparedStatement initBatchStmt(String sql, List<?> batches) throws SQLException {
		PreparedStatement stmt = getStatement(sql);

		setBatchParams(stmt, batches, this.conn);

		return stmt;
	}

	/* (non-Javadoc)
	 * @see com.xdarkness.framework.sql.IDataAccess#executeQuery(java.lang.String, java.util.List, com.xdarkness.framework.ICallbackStatement)
	 */
	public Object executeQuery(String sql,
			ICallbackStatement statement) throws SQLException {
		return execute(sql, null, true, false, statement);
	}
	
	public Object executeQuery(String sql, List<?> params,
			ICallbackStatement statement) throws SQLException {
		return execute(sql, params, true, false, statement);
	}
	
	/* (non-Javadoc)
	 * @see com.xdarkness.framework.sql.IDataAccess#executeBatch(java.lang.String, java.util.List)
	 */
	public Object executeBatch(String sql, List<?> params) throws SQLException {
		return execute(sql, params, false, true, null);
	}
	
	/* (non-Javadoc)
	 * @see com.xdarkness.framework.sql.IDataAccess#executeUpdate(java.lang.String, java.util.List)
	 */
	private Object executeUpdate(String sql, List<?> params) throws SQLException {
		return execute(sql, params, false, false, null);
	}
	
	/* (non-Javadoc)
	 * @see com.xdarkness.framework.sql.IDataAccess#executeUpdate(java.lang.String, java.util.List, java.lang.Boolean)
	 */
	private Object executeUpdate(String sql, List<?> params, Boolean isBatchMode) throws SQLException {
		return execute(sql, params, false, isBatchMode, null);
	}
	
	/* (non-Javadoc)
	 * @see com.xdarkness.framework.sql.IDataAccess#execute(java.lang.String, java.util.List, java.lang.Boolean, java.lang.Boolean, com.xdarkness.framework.ICallbackStatement)
	 */
	public Object execute(String sql, List<?> params,
			Boolean isQuery, Boolean isBatchMode, 
			ICallbackStatement statement) throws SQLException {
		long current = System.currentTimeMillis();
		initConnection();

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			if(isBatchMode){
				stmt = initBatchStmt(sql, params);
				//conn.setAutoCommit(false);
			} else {
				stmt = initStmt(sql, params);
			}
			
			if (!isQuery) {// 非查询操作
				Object r = null;
				if (isBatchMode) {
					r = stmt.executeBatch();
				} else {
					r = stmt.executeUpdate();
				}
				conn.setLastSuccessExecuteTime(System.currentTimeMillis());
				
				return r;
			}

			rs = stmt.executeQuery();

			conn.setLastSuccessExecuteTime(System.currentTimeMillis());

			return statement.execute(conn, stmt, rs/* latin1Flag*/);
		} catch (SQLException e) {
//			LogUtil.error(qb);
			throw e;
		} finally {
			try {
//				if (!this.conn.getDBConfig().isSQLServer2000()) {
//					qb.getParams().remove(qb.getParams().size() - 1);
//					qb.getParams().remove(qb.getParams().size() - 1);
//				}
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (stmt != null) {
					stmt.close();
					stmt = null;
				}
				if (conn != null && !conn.isClosed()) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				LogUtil.info("连接关闭失败！");
			}
			if (Config.isDebugLoglevel()) {
				long time = System.currentTimeMillis() - current;
				LogUtil.info(time + "ms\t" + sql);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xdarkness.framework.orm.IDataAccess#executeDataTable(com.xdarkness.framework.
	 * data.QueryBuilder)
	 */
	/* (non-Javadoc)
	 * @see com.xdarkness.framework.sql.IDataAccess#executeDataTable(java.lang.String, java.util.List)
	 */
	public DataTable executeDataTable(String sql, List params) throws SQLException {

		LogUtil.debug(sql);

		return (DataTable) executeQuery(sql, params, new ICallbackStatement() {

			public Object execute(XConnection connection,
					PreparedStatement stmt, ResultSet rs) {

				return new DataTable(rs);
			}

		});

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xdarkness.framework.orm.IDataAccess#executePagedDataTable(com.xdarkness.framework
	 * .data.QueryBuilder, int, int)
	 */
//	public DataTable executePagedDataTable(String sql, List params, int pageSize,
//			int pageIndex) throws SQLException {
//
////		qb.setPagedQuery(true);
//
//		if (pageSize < 1) {
//			pageSize = Integer.MAX_VALUE;
//		}
//		if (pageIndex < 0) {
//			pageIndex = 0;
//		}
//
////		qb.setPageSize(pageSize);
////		qb.setPageIndex(pageIndex);
//
//		return (DataTable) execute(qb, new ICallbackStatement() {
//
//			public Object execute(QueryBuilder qb, XConnection connection,
//					PreparedStatement stmt, ResultSet rs) {
//
//				DataTable dt = null;
////				if (connection.getDBType().equals("MSSQL2000"))
////					dt = new DataTable(rs, qb.getPageSize(), qb.getPageIndex());
////				else {
////					dt = new DataTable(rs);
////				}
////				if (!(connection.getDBType().equals("MSSQL2000"))) {
////					qb.getParams().remove(qb.getParams().size() - 1);
////					qb.getParams().remove(qb.getParams().size() - 1);
////				}
//				if (this.conn.getDBConfig().isSQLServer2000()) {
//					dt = new DataTable(rs, pageSize, pageIndex, latin1Flag);
//				} else {
//					dt = new DataTable(rs, latin1Flag);
//					if ((this.conn.getDBConfig().isOracle())
//							|| (this.conn.getDBConfig().isDB2())
//							|| (this.conn.getDBConfig().isSQLServer())) {
//						dt.deleteColumn(dt.getColCount() - 1);
//					}
//				}
//				return dt;
//			}
//
//		});
//	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.xdarkness.framework.orm.IDataAccess#executeOneValue(com.xdarkness.framework.data
	 * .QueryBuilder)
	 */
	/* (non-Javadoc)
	 * @see com.xdarkness.framework.sql.IDataAccess#executeOneValue(java.lang.String, java.util.List)
	 */
	public Object executeOneValue(String sql, List params) throws SQLException {

		return executeQuery(sql, params, new ICallbackStatement() {

			public Object execute( XConnection connection,
					PreparedStatement stmt, ResultSet rs) throws SQLException {
				Object object = null;
				if (rs.next()) {
					object = rs.getObject(1);
					if (object instanceof Clob) {
						object = LobUtil.clobToString((Clob) object);
					}
					if (object instanceof Blob) {
						object = LobUtil.blobToBytes((Blob) object);
					}
				}

				return object;
			}

		});
	}

	/* (non-Javadoc)
	 * @see com.xdarkness.framework.sql.IDataAccess#executeNoQuery(java.lang.String, java.util.List)
	 */
	public int executeNoQuery(String sql, List params) throws SQLException {

		try {
			return (Integer) executeUpdate(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public int executeNoQuery(String sql, List params,Boolean isBatchModel) throws SQLException {

		try {
			Object object = executeUpdate(sql, params, isBatchModel);
			if(isBatchModel){
				return 0;
			}
			return (Integer) object;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

//	public void deleteAndBackup(AbstractSchema abstractSchema) {
//		deleteAndBackup(abstractSchema, null, null);
//	}

//	public Boolean fillSchema(final Schema schema) {
//		String sql = schema.getSelectSql();
//		try {
//			QueryBuilder queryBuilder = new QueryBuilder(sql);
//			queryBuilder.add(schema.getPrimaryKeyValue("fill"));
//
//			return (Boolean) this.execute(queryBuilder,
//					new ICallbackStatement() {
//
//						public Object execute(QueryBuilder qb,
//								XConnection connection, PreparedStatement stmt,
//								ResultSet rs) throws SQLException {
//							if (rs.next()) {
//								if (schema.bOperateFlag) {
//									for (int i = 0; i < schema.operateColumnOrders.length; ++i)
//										if (schema.Columns[schema.operateColumnOrders[i]]
//												.getColumnType() == 10) {
//											if (conn.getDBType() == DBTypes.ORACLE
//													|| conn.getDBType() == DBTypes.DB2)
//												schema
//														.setV(
//																schema.operateColumnOrders[i],
//																LobUtil
//																		.clobToString(rs
//																				.getClob(i + 1)));
//											else
//												schema
//														.setV(
//																schema.operateColumnOrders[i],
//																rs
//																		.getObject(i + 1));
//										} else if (schema.Columns[schema.operateColumnOrders[i]]
//												.getColumnType() == 2)
//											schema
//													.setV(
//															schema.operateColumnOrders[i],
//															LobUtil
//																	.blobToBytes(rs
//																			.getBlob(i + 1)));
//										else
//											schema
//													.setV(
//															schema.operateColumnOrders[i],
//															rs.getObject(i + 1));
//								} else {
//
//									for (int i = 0; i < schema.Columns.length; ++i) {
//										if (schema.Columns[i].getColumnType() == 10) {
//											if ((conn.getDBType() == DBTypes.ORACLE)
//													|| (conn.getDBType() == DBTypes.DB2))
//												schema
//														.setV(
//																i,
//																LobUtil
//																		.clobToString(rs
//																				.getClob(i + 1)));
//											else
//												schema.setV(i, rs
//														.getObject(i + 1));
//										} else if (schema.Columns[i]
//												.getColumnType() == 2)
//											schema.setV(i, LobUtil
//													.blobToBytes(rs
//															.getBlob(i + 1)));
//										else {
//											schema.setV(i, rs.getObject(i + 1));
//										}
//
//									}
//								}
//								return true;
//							}
//							return false;
//						}
//					});
//
//		} catch (Exception e) {
//		}
//		return false;
//	}


	/**
	 * 设置insert操作的所有参数
	 * 
	 * @param queryBuilder
	 * @param schema
	 * @throws SQLException
	 */
//	public void setParams(QueryBuilder queryBuilder, Schema schema)
//			throws SQLException {
//		for (int i = 0; i < schema.Columns.length; ++i) {
//			SchemaColumn sc = schema.Columns[i];
//			if ((sc.isMandatory()) && (schema.getV(i) == null)) {
//				throw new SQLException("表" + schema.TableCode + "的列"
//						+ sc.getColumnName() + "不能为空");
//			}
//
//			queryBuilder.add(schema.getV(i));
//		}
//	}
//	public void insert(Schema schema) {
//		schema.setDataAccess(this);
//		schema.insert();
//	}
	

//	public void setUpdataParams(QueryBuilder queryBuilder, Schema schema) {
//		if (schema.bOperateFlag)
//			for (int i = 0; i < schema.operateColumnOrders.length; ++i) {
//				queryBuilder.add(schema.getV(schema.operateColumnOrders[i]));
//			}
//		else {
//			for (int i = 0; i < schema.Columns.length; ++i) {
//				queryBuilder.add(schema.getV(i));
//			}
//		}
//		queryBuilder.add(schema.getPrimaryKeyValue("update"));
//	}

//	public boolean update(AbstractSchema abstractSchema) {
//		String sql = abstractSchema.getUpdateSql();
//		QueryBuilder queryBuilder = new QueryBuilder(sql);
//
//		if (abstractSchema instanceof SchemaSet) {
//			queryBuilder.setBatchMode(true);
//			SchemaSet schemaSet = (SchemaSet) abstractSchema;
//			for (int k = 0; k < schemaSet.elementCount; ++k) {
//				Schema schema = schemaSet.elementData[k];
//				setUpdataParams(queryBuilder, schema);
//				queryBuilder.addBatch();
//			}
//		} else if (abstractSchema instanceof Schema) {
//			setUpdataParams(queryBuilder, (Schema) abstractSchema);
//		}
//
//		try {
//			return this.executeNoQuery(queryBuilder) != -1;
//		} catch (Throwable e) {
//			LogUtil.warn("操作表" + abstractSchema.TableCode + "时发生错误!");
//			e.printStackTrace();
//		}
//		return false;
//	}

//	public boolean delete(AbstractSchema abstractSchema) {
//		QueryBuilder queryBuilder = new QueryBuilder(abstractSchema.DeleteSQL);
//		if (abstractSchema instanceof SchemaSet) {
//			queryBuilder.setBatchMode(true);
//			SchemaSet schemaSet = (SchemaSet) abstractSchema;
//			for (int k = 0; k < schemaSet.elementCount; ++k) {
//				Schema schema = schemaSet.elementData[k];
//				queryBuilder.add(schema.getPrimaryKeyValue("delete"));
//				queryBuilder.addBatch();
//			}
//		} else {
//			queryBuilder.add(abstractSchema.getPrimaryKeyValue("delete"));
//		}
//		try {
//			return executeNoQuery(queryBuilder) != -1;
//		} catch (SQLException e) {
//			LogUtil.warn("操作表" + abstractSchema.TableCode + "时发生错误!");
//			e.printStackTrace();
//		}
//		return false;
//	}

//	public boolean deleteAndInsert(AbstractSchema abstractSchema) {
//		try {
//			this.setAutoCommit(false);
//			delete(abstractSchema);
//			insert(abstractSchema);
//			this.commit();
//			return true;
//		} catch (Throwable e) {
//			e.printStackTrace();
//			try {
//				this.rollback();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}
//			return false;
//		} finally {
//			try {
//				this.setAutoCommit(true);
//				this.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}

//	public boolean deleteAndBackup(AbstractSchema abstractSchema,
//			String backupOperator, String backupMemo) {
//		try {
//			try {
//				this.setAutoCommit(false);
//				delete(abstractSchema);
//				insert(abstractSchema.getBackUpSchema(backupOperator,
//						backupMemo));
//				this.commit();
//				return true;
//			} catch (Throwable e) {
//				e.printStackTrace();
//				try {
//					this.rollback();
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
//				return false;
//			} finally {
//				try {
//					this.setAutoCommit(true);
//					this.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

//	public boolean backup(AbstractSchema abstractSchema) {
//		return backup(abstractSchema, null, null);
//	}
//
//	public boolean backup(AbstractSchema abstractSchema, String backupOperator,
//			String backupMemo) {
//		try {
//			return insert(abstractSchema.getBackUpSchema(backupOperator,
//					backupMemo));
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}

	/**
	 * 设置PreparedStatement中的参数
	 * 
	 * @param stmt
	 * @param qb
	 * @param conn
	 * @throws SQLException
	 */
	public static void setParams(PreparedStatement stmt, Boolean isBatchMode,
			List<?> list, XConnection conn) throws SQLException {
		if (isBatchMode) {
			setBatchParams(stmt, list, conn);
		} else {
			// if(qb.getSQL().trim().equals("")){// 查询时如果queryBuilder为null，new
			// QueryBuilder的bug
			// return;
			// }
			setPreparedStatementParams(stmt, list, conn);
		}
	}

	public static void setParams(PreparedStatement stmt, List<?> list,
			XConnection conn) throws SQLException {
		setParams(stmt, false, list, conn);
	}

	public static void setBatchParams(PreparedStatement stmt, List<?> batches,
			XConnection conn) throws SQLException {
		for (int k = 0; k < batches.size(); k++) {
			List<?> params = (ArrayList<?>) batches.get(k);
			setPreparedStatementParams(stmt, params, conn);
			stmt.addBatch();
		}
	}

	/**
	 * 设置PreparedStatement的参数
	 * 
	 * @param stmt
	 * @param params
	 * @param conn
	 * @throws SQLException
	 */
	public static void setPreparedStatementParams(PreparedStatement stmt,
			List<?> params, XConnection conn) throws SQLException {
		
		if(params == null || params.size()==0){
			return;
		}
		
		for (int i = 1; i <= params.size(); i++) {
			Object o = params.get(i - 1);
			if (o == null)
				stmt.setNull(i, Types.VARCHAR);
			else if (o instanceof Byte)
				stmt.setByte(i, ((Byte) o).byteValue());
			else if (o instanceof Short)
				stmt.setShort(i, ((Short) o).shortValue());
			else if (o instanceof Integer)
				stmt.setInt(i, ((Integer) o).intValue());
			else if (o instanceof Long)
				stmt.setLong(i, ((Long) o).longValue());
			else if (o instanceof Float)
				stmt.setFloat(i, ((Float) o).floatValue());
			else if (o instanceof Double)
				stmt.setDouble(i, ((Double) o).doubleValue());
			else if (o instanceof Date)
				stmt.setTimestamp(i, new Timestamp(((Date) o).getTime()));
			else if (o instanceof String) {
				String str = (String) o;
				if ((conn.getDBConfig().isLatin1Charset)
						&& (conn.getDBConfig().DBType == DBTypes.ORACLE)) {
					try {
						str = new String(str.getBytes(Constant.GlobalCharset),
								"ISO-8859-1");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				stmt.setString(i, str);
			} else if (o instanceof Clob)
				LobUtil.setClob(conn, conn.getDBType()
						.toString(), stmt, i, o);
			else if (o instanceof byte[])
				LobUtil.setBlob(conn, conn.getDBType()
						.toString(), stmt, i, (byte[]) o);
			else
				stmt.setObject(i, o);
		}
	}
	
}
