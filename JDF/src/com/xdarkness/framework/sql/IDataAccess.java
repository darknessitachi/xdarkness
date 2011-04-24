package com.xdarkness.framework.sql;

import java.sql.SQLException;
import java.util.List;

import com.xdarkness.framework.ICallbackStatement;
import com.xdarkness.framework.connection.XConnection;
import com.xdarkness.framework.orm.data.DataTable;

/**
 * @author Darkness
 * 
 *         Copyright (c) 2009 by Darkness
 * 
 * @date 2010-8-4 下午07:14:44
 * @version 1.0
 */
public interface IDataAccess {

	XConnection getConnection();

	void setAutoCommit(boolean bCommit) throws SQLException;

	void commit() throws SQLException;

	void rollback() throws SQLException;

	void close() throws SQLException;

	Object executeQuery(String sql, List<?> params, ICallbackStatement statement)
			throws SQLException;

	Object executeBatch(String sql, List<?> params) throws SQLException;

	Object execute(String sql, List<?> params, Boolean isQuery,
			Boolean isBatchMode, ICallbackStatement statement)
			throws SQLException;

	DataTable executeDataTable(String sql, List params) throws SQLException;

	Object executeOneValue(String sql, List params) throws SQLException;

	int executeNoQuery(String sql, List params) throws SQLException;

	int executeNoQuery(String sql, List params, Boolean isBatchModel)
			throws SQLException;
//	XConnection getConnection();
	//
//		void setAutoCommit(boolean bCommit) throws SQLException;
	//
//		void commit() throws SQLException;
	//
//		void rollback() throws SQLException;
	//
//		void close() throws SQLException;
	//
//		/**
//		 * 执行QueryBuilder，自定义处理结果，无需关心连接等的释放
//		 * 
//		 * @param qb
//		 * @param statement
//		 * @return
//		 * @throws SQLException
//		 */
//		Object execute(QueryBuilder qb, ICallbackStatement statement)
//				throws SQLException;
	//
//		/**
//		 * 执行QueryBuilder，自定义处理结果，无需关心连接等的释放
//		 * 
//		 * @param qb
//		 * @param statement
//		 * @return
//		 * @throws SQLException
//		 */
//		Object executeUpdate(QueryBuilder qb) throws SQLException;
	//
//		/**
//		 * 执行QueryBuilder，自定义处理结果，无需关心连接等的释放
//		 * 
//		 * @param qb
//		 * @param statement
//		 * @return
//		 * @throws SQLException
//		 */
//		Object execute(QueryBuilder qb, ICallbackStatement statement,
//				boolean isQuery) throws SQLException;
	//
//		/**
//		 * 获取表格数据
//		 * 
//		 * @param qb
//		 * @return
//		 * @throws SQLException
//		 */
//		DataTable executeDataTable(QueryBuilder qb) throws SQLException;
	//
//		/**
//		 * 查询分页表格数据
//		 * 
//		 * @param qb
//		 * @param pageSize
//		 * @param pageIndex
//		 * @return
//		 * @throws SQLException
//		 */
//		DataTable executePagedDataTable(QueryBuilder qb, int pageSize, int pageIndex)
//				throws SQLException;
	//
//		/**
//		 * 查询一个值
//		 * 
//		 * @param qb
//		 * @return
//		 * @throws SQLException
//		 */
//		Object executeOneValue(QueryBuilder qb) throws SQLException;
	//
//		int executeNoQuery(QueryBuilder qb) throws SQLException;
	//
//		/**
//		 * 根据该对象的主键填充该对象的其他列
//		 * 
//		 * @return
//		 */
//		Boolean fillSchema(Schema schema);
	//
//		SchemaSet querySet(final Schema schema, QueryBuilder qb);
}