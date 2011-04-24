package com.xdarkness.framework.orm;

import java.io.Serializable;
import java.sql.SQLException;

import com.xdarkness.framework.sql.IDataAccess;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.LogUtil;

/**
 * @author Darkness
 * 
 *         Copyright (c) 2009 by Darkness
 * 
 * @date 2010-8-4 下午07:29:15
 * @version 1.0
 */
public abstract class AbstractSchema implements Serializable, Cloneable {
	protected transient IDataAccess mDataAccess;
	protected String InsertAllSQL;
	protected String UpdateAllSQL;
	protected String FillAllSQL;
	protected String DeleteSQL;

	protected String TableCode;
	protected SchemaColumn[] Columns;
	protected String NameSpace;
	protected int bConnFlag;
	protected boolean bOperateFlag;
	protected int operateColumnOrders[];

	public abstract void initInsertQueryBuilder(QueryBuilder queryBuilder);

	public boolean insert() {
		try {
			QueryBuilder queryBuilder = new QueryBuilder(InsertAllSQL);

			initInsertQueryBuilder(queryBuilder);

			return queryBuilder.executeNoQuery() != -1;
		} catch (Throwable e) {
//			if ((e instanceof BatchUpdateException)) {
//				LogUtil.warn(toDataTable());
//			}
			LogUtil.warn("操作表" + this.TableCode + "时发生错误:" + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public void setUpdataParams(QueryBuilder queryBuilder, Schema schema) {
		if (schema.bOperateFlag)
			for (int i = 0; i < schema.operateColumnOrders.length; ++i) {
				queryBuilder.add(schema.getV(schema.operateColumnOrders[i]));
				// SchemaUtil.setParam(
				// this.Columns[this.operateColumnOrders[i]], pstmt,
				// conn, this.operateColumnOrders[i], v);
			}
		else {
			for (int i = 0; i < schema.Columns.length; ++i) {
				queryBuilder.add(schema.getV(i));
			}
		}
		queryBuilder.add(schema.getPrimaryKeyValue("update"));
	}

	public abstract void initQueryBuilder(QueryBuilder queryBuilder);

	public boolean update() {
		String sql = getUpdateSql();
		QueryBuilder queryBuilder = new QueryBuilder(sql);

		initQueryBuilder(queryBuilder);

		try {
			return queryBuilder.executeNoQuery() != -1;
		} catch (Throwable e) {
			LogUtil.getLogger().warn(
					"操作表" + this.TableCode + "时发生错误:" + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	public abstract void initDeleteQueryBuilder(QueryBuilder queryBuilder);

	public boolean delete() {
		QueryBuilder queryBuilder = new QueryBuilder(this.DeleteSQL);
		initDeleteQueryBuilder(queryBuilder);
		try {
			return queryBuilder.executeNoQuery() != -1;
		} catch (SQLException e) {
			LogUtil.warn("操作表" + this.TableCode + "时发生错误!");
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteAndInsert() {
		try {
			// this.setAutoCommit(false);
			this.delete();
			this.insert();
			// this.commit();
			return true;
		} catch (Throwable e) {
			e.printStackTrace();
			// try {
			// this.rollback();
			// } catch (SQLException e1) {
			// e1.printStackTrace();
			// }
			return false;
		} finally {
			// try {
			// this.setAutoCommit(true);
			// this.close();
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
	}

	public boolean deleteAndBackup(
			String backupOperator, String backupMemo) {
		try {
			try {
				// this.setAutoCommit(false);
				this.delete();
				this.getBackUpSchema(backupOperator, backupMemo)
						.insert();
				// this.commit();
				return true;
			} catch (Throwable e) {
				e.printStackTrace();
				// try {
				// this.rollback();
				// } catch (SQLException e1) {
				// e1.printStackTrace();
				// }
				return false;
			} finally {
				// try {
				// this.setAutoCommit(true);
				// this.close();
				// } catch (SQLException e) {
				// e.printStackTrace();
				// }
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean backup() {
		return backup( null, null);
	}

	public boolean backup( String backupOperator,
			String backupMemo) {
		try {
			return this.getBackUpSchema(backupOperator, backupMemo)
					.insert();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 设置insert操作的所有参数
	 * 
	 * @param queryBuilder
	 * @param schema
	 * @throws SQLException
	 */
	public void setParams(QueryBuilder queryBuilder, Schema schema)
			throws SQLException {
		for (int i = 0; i < schema.Columns.length; ++i) {
			SchemaColumn sc = schema.Columns[i];
			if ((sc.isMandatory()) && (schema.getV(i) == null)) {
				throw new SQLException("表" + schema.TableCode + "的列"
						+ sc.getColumnName() + "不能为空");
			}

			queryBuilder.add(schema.getV(i));
			// Object v = getV(i);
			// SchemaUtil.setParam(sc, pstmt, conn, i, v);
		}
	}

	public boolean deleteAndBackup() {
		return deleteAndBackup( null, null);
	}

	/**
	 * 获取更新的SQL语句
	 * 
	 * @return
	 */
	protected String getUpdateSql() {
		String sql = this.UpdateAllSQL;
		if (this.bOperateFlag) {
			StringBuffer sb = new StringBuffer("update " + this.TableCode
					+ " set ");
			for (int i = 0; i < this.operateColumnOrders.length; ++i) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append(this.Columns[this.operateColumnOrders[i]]
						.getColumnName());
				sb.append("=?");
			}
			sb.append(sql.substring(sql.indexOf(" where")));
			sql = sb.toString();
		}
		return sql;
	}

	protected String getSelectSql() {
		String sql = this.FillAllSQL;
		if (this.bOperateFlag) {// 设置过需要操作的列，则只查询设置的这些操作列，默认为全部查询
			StringBuffer sb = new StringBuffer("select ");
			for (int i = 0; i < this.operateColumnOrders.length; ++i) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append(this.Columns[this.operateColumnOrders[i]]
						.getColumnName());
			}
			sb.append(sql.substring(sql.indexOf(" from")));
			sql = sb.toString();
		}
		return sql;
	}

	public abstract AbstractSchema getBackUpSchema(String backupOperator,
			String backupMemo);

	public String getTableCode() {
		return TableCode;
	}

	public void setTableCode(String tableCode) {
		TableCode = tableCode;
	}

	public SchemaColumn[] getColumns() {
		return Columns;
	}

	public void setColumns(SchemaColumn[] columns) {
		Columns = columns;
	}

}
