package com.abigdreamer.java.net.orm;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abigdreamer.java.net.ICallbackStatement;
import com.abigdreamer.java.net.connection.DBTypes;
import com.abigdreamer.java.net.connection.XConnection;
import com.abigdreamer.java.net.connection.XConnectionPoolManager;
import com.abigdreamer.java.net.data.LobUtil;
import com.abigdreamer.java.net.orm.data.DataCollection;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.orm.data.DataRow;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.CaseIgnoreMapx;
import com.abigdreamer.java.net.util.DateUtil;
import com.abigdreamer.java.net.util.LogUtil;
import com.abigdreamer.java.net.util.Mapx;
import com.abigdreamer.java.net.util.XString;

public abstract class Schema extends AbstractSchema implements Serializable,
		Cloneable {
	public abstract Object getV(int i);

	protected abstract Schema newInstance();

	protected abstract SchemaSet newSet();

	private static final long serialVersionUID = 1L;

	protected boolean HasSetFlag[];
	protected Object[] OldKeys;

	public Schema() {
		bOperateFlag = false;
	}

	public void initInsertQueryBuilder(QueryBuilder queryBuilder) {
		try {
			setParams(queryBuilder, this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void initDeleteQueryBuilder(QueryBuilder queryBuilder) {
		queryBuilder.add(this.getPrimaryKeyValue("delete"));
	}

	private int pkIndex = 0;

	/**
	 * 获取主键的值
	 * 
	 * @return
	 */
	protected Object[] getPrimaryKeyValue(String operate) {
		List<Object> primaryKeyValues = new ArrayList<Object>();
		for (int i = 0; i < this.Columns.length; ++i) {
			SchemaColumn sc = this.Columns[i];
			if (sc.isPrimaryKey()) {
				Object v = getV(sc.getColumnOrder());
				if (this.OldKeys != null) {
					v = this.OldKeys[(pkIndex++)];
				}
				if (v == null) {
					LogUtil.getLogger().warn(
							"不满足" + operate + "的条件，" + this.TableCode
									+ "Schema的" + sc.getColumnName() + "为空");
				}
				primaryKeyValues.add(v);
			}
		}

		return primaryKeyValues.toArray();
	}

	/**
	 * 设置主键值
	 * @param v
	 */
	public void setPrimaryKey(Object v) {
		for (int i = 0; i < this.Columns.length; ++i) {
			SchemaColumn sc = this.Columns[i];
			if (sc.isPrimaryKey()) {
				setV(sc.getColumnOrder(), v);
			}
		}
	}

	@Override
	public void initQueryBuilder(QueryBuilder queryBuilder) {
		setUpdataParams(queryBuilder, this);
	}

	/*
	 * 设置需要操作的列
	 */
	public void setOperateColumns(String colNames[]) {
		if (colNames == null || colNames.length == 0) {
			bOperateFlag = false;
			return;
		}

		operateColumnOrders = new int[colNames.length];

		int k = 0;
		for (int i = 0; i < colNames.length; i++) {
			boolean flag = false;
			for (int j = 0; j < Columns.length; j++) {// 判断给定的列是否存在
				if (colNames[i].toString().toLowerCase().equals(
						Columns[j].getColumnName().toLowerCase())) {
					operateColumnOrders[k] = j;
					k++;
					flag = true;
					break;
				}
			}

			if (!flag)
				throw new RuntimeException("指定的列名" + colNames[i] + "不正确");
		}

		bOperateFlag = true;
	}

	protected Object[] getOldKeys() {
		return this.OldKeys;
	}

	public void setOperateColumns(int colOrder[]) {
		if (colOrder == null || colOrder.length == 0) {
			bOperateFlag = false;
		} else {
			operateColumnOrders = colOrder;
			bOperateFlag = true;
		}
	}

	protected boolean isNull(SchemaColumn sc) {
		return getV(sc.getColumnOrder()) == null;
	}

	public void setValue(Mapx map) {
		Object value = null;
		Object key = null;
		Object ks[] = map.keyArray();
		Object vs[] = map.valueArray();
		for (int i = 0; i < map.size(); i++) {
			value = vs[i];
			key = ks[i];
			for (int j = 0; j < Columns.length;) {
				SchemaColumn sc = Columns[j];
				if (key == null
						|| !key.toString().equalsIgnoreCase(sc.getColumnName()))
					continue;
				try {
					int type = sc.getColumnType();
					if (type == 0) {
						if (value != null && !"".equals(value))
							setV(j, DateUtil.parseDateTime(value.toString()));
					} else if (type == 6)
						setV(j, new Double(value.toString()));
					else if (type == 5)
						setV(j, new Float(value.toString()));
					else if (type == 7)
						setV(j, new Long(value.toString()));
					else if (type == 8)
						setV(j, new Integer(value.toString()));
					else
						setV(j, value);
					break;
				} catch (Exception exception) {
					j++;
				}
			}

		}

	}

	public void setValue(DataCollection dc) {
		String value = null;
		String key = null;
		Object ks[] = dc.keyArray();
		Object vs[] = dc.valueArray();
		for (int i = 0; i < dc.size(); i++)
			if ((vs[i] instanceof String) || vs[i] == null) {
				value = (String) vs[i];
				key = (String) ks[i];

				for (int j = 0; j < Columns.length; j++) {
					SchemaColumn sc = Columns[j];
					if (!key.equalsIgnoreCase(sc.getColumnName()))
						continue;
					try {
						int type = sc.getColumnType();
						if (type == 0) {
							if (value != null && !"".equals(value))
								if (DateUtil.isTime(value.toString()))
									setV(j, DateUtil.parseDateTime(value
											.toString(), "HH:mm:ss"));
								else
									setV(j, DateUtil.parseDateTime(value
											.toString()));
						} else if (type == 6)
							setV(j, new Double(value.toString()));
						else if (type == 5)
							setV(j, new Float(value.toString()));
						else if (type == 7)
							setV(j, new Long(value.toString()));
						else if (type == 8)
							setV(j, new Integer(value.toString()));
						else
							setV(j, value);
						break;
					} catch (Exception exception) {

					}
				}

			}

	}

	public void setValue(DataRow dr) {
		String value = null;
		String key = null;
		boolean webMode = dr.isWebMode();
		dr.setWebMode(false);
		for (int i = 0; i < dr.getColumnCount(); i++) {
			value = dr.getString(i);
			key = dr.getDataColumns()[i].getColumnName();
			for (int j = 0; j < Columns.length;) {
				SchemaColumn sc = Columns[j];
				if (!key.equalsIgnoreCase(sc.getColumnName()))
					continue;
				try {
					int type = sc.getColumnType();
					if (type == 0) {
						if (value != null && !"".equals(value))
							setV(j, DateUtil.parseDateTime(value.toString()));
					} else if (type == 6)
						setV(j, new Double(value.toString()));
					else if (type == 5)
						setV(j, new Float(value.toString()));
					else if (type == 7)
						setV(j, new Long(value.toString()));
					else if (type == 8)
						setV(j, new Integer(value.toString()));
					else
						setV(j, value);
					break;
				} catch (Exception exception) {
					j++;
				}
			}

		}

		dr.setWebMode(webMode);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for (int i = 0; i < Columns.length; i++) {
			sb.append(Columns[i].getColumnName());
			sb.append(":");
			sb.append(getV(i));
			sb.append(" ");
		}

		sb.append("}");
		return sb.toString();
	}

	public Object clone() {
		Schema s = newInstance();
		SchemaUtil.copyFieldValue(this, s);
		return s;
	}

	public Mapx<String, Object> toMapx() {
		Mapx<String, Object> map = new Mapx<String, Object>();
		for (int i = 0; i < Columns.length; i++)
			map.put(Columns[i].getColumnName(), getV(i));

		return map;
	}

	public CaseIgnoreMapx toCaseIgnoreMapx() {
		CaseIgnoreMapx map = new CaseIgnoreMapx();
		for (int i = 0; i < Columns.length; i++)
			map.put(Columns[i].getColumnName(), getV(i));

		return map;
	}

	public Mapx toMapx(boolean toLowerCase) {
		Mapx map = new Mapx();
		for (int i = 0; i < Columns.length; i++) {
			String colName = Columns[i].getColumnName();
			if (toLowerCase)
				colName = colName.toLowerCase();
			map.put(colName, getV(i));
		}

		return map;
	}

	public DataRow toDataRow() {
		int len = Columns.length;
		DataColumn dcs[] = new DataColumn[len];
		Object values[] = new Object[len];
		for (int i = 0; i < len; i++) {
			DataColumn dc = new DataColumn();
			dc.setColumnName(Columns[i].getColumnName());
			dc.setColumnType(Columns[i].getColumnType());
			dcs[i] = dc;
			values[i] = getV(i);
		}

		return new DataRow(dcs, values);
	}

	public int getColumnCount() {
		return Columns.length;
	}

	public abstract void setV(int i, Object obj);

	public Boolean fill() {
		String sql = getSelectSql();
		try {
			QueryBuilder queryBuilder = new QueryBuilder(sql);
			queryBuilder.add(getPrimaryKeyValue("fill"));

			final Schema schema = this;

			return (Boolean) queryBuilder.execute(new ICallbackStatement() {

				public Object execute(XConnection connection,
						PreparedStatement stmt, ResultSet rs)
						throws SQLException {
					if (rs.next()) {
						if (schema.bOperateFlag) {
							for (int i = 0; i < schema.operateColumnOrders.length; ++i)
								if (schema.Columns[schema.operateColumnOrders[i]]
										.getColumnType() == 10) {
									if (XConnectionPoolManager
											.getDBConnConfig().DBType == DBTypes.ORACLE
											|| XConnectionPoolManager
													.getDBConnConfig().DBType == DBTypes.DB2)
										schema.setV(
												schema.operateColumnOrders[i],
												LobUtil.clobToString(rs
														.getClob(i + 1)));
									else
										schema.setV(
												schema.operateColumnOrders[i],
												rs.getObject(i + 1));
								} else if (schema.Columns[schema.operateColumnOrders[i]]
										.getColumnType() == 2)
									schema.setV(schema.operateColumnOrders[i],
											LobUtil.blobToBytes(rs
													.getBlob(i + 1)));
								else
									schema.setV(schema.operateColumnOrders[i],
											rs.getObject(i + 1));

							// if ((this.Columns[order].getColumnType() == 10)
							// || (this.Columns[order].getColumnType() == 1)) {
							// String str = (String) schema.getV(order);
							// if ((latin1Flag) && (StringUtil.isNotEmpty(str)))
							// {
							// try {
							// str = new String(str.getBytes("ISO-8859-1"),
							// Constant.GlobalCharset);
							// } catch (UnsupportedEncodingException e) {
							// e.printStackTrace();
							// }
							// }
							// schema.setV(order, str);
							// }
							// if (this.Columns[order].isPrimaryKey())
							// pklist.add(schema.getV(order));
						} else {

							for (int i = 0; i < schema.Columns.length; ++i) {
								if (schema.Columns[i].getColumnType() == 10) {
									if ((XConnectionPoolManager
											.getDBConnConfig().DBType == DBTypes.ORACLE)
											|| (XConnectionPoolManager
													.getDBConnConfig().DBType == DBTypes.DB2))
										schema.setV(i, LobUtil.clobToString(rs
												.getClob(i + 1)));
									else
										schema.setV(i, rs.getObject(i + 1));
								} else if (schema.Columns[i].getColumnType() == 2)
									schema.setV(i, LobUtil.blobToBytes(rs
											.getBlob(i + 1)));
								else {
									schema.setV(i, rs.getObject(i + 1));
								}

							}
						}
						return true;
					}
					return false;
				}
			});

		} catch (Exception e) {
		}
		// schema.OldKeys = pklist.toArray();
		return false;
	}

	public SchemaSet querySet(QueryBuilder qb) {

		try {

			final Schema schema = this;
			return (SchemaSet) qb.execute(new ICallbackStatement() {

				public Object execute(XConnection connection,
						PreparedStatement stmt, ResultSet rs)
						throws SQLException {

					SchemaSet set = schema.newSet();
					while (rs.next()) {

						Schema newSchema = schema.newInstance();
						int i;
						if (schema.bOperateFlag) {
							for (i = 0; i < schema.operateColumnOrders.length; ++i) {
								if (schema.Columns[schema.operateColumnOrders[i]]
										.getColumnType() == 10) {
									if ((XConnectionPoolManager
											.getDBConnConfig().isOracle())
											|| (XConnectionPoolManager
													.getDBConnConfig().isDB2()))
										newSchema.setV(
												schema.operateColumnOrders[i],
												LobUtil.clobToString(rs
														.getClob(i + 1)));
									else
										newSchema.setV(
												schema.operateColumnOrders[i],
												rs.getObject(i + 1));
								} else if (schema.Columns[schema.operateColumnOrders[i]]
										.getColumnType() == 2)
									newSchema.setV(
											schema.operateColumnOrders[i],
											LobUtil.blobToBytes(rs
													.getBlob(i + 1)));
								else
									newSchema.setV(
											schema.operateColumnOrders[i], rs
													.getObject(i + 1));
							}
						} else {
							for (i = 0; i < schema.Columns.length; ++i) {
								if (schema.Columns[i].getColumnType() == 10) {
									if ((XConnectionPoolManager
											.getDBConnConfig().isOracle())
											|| (XConnectionPoolManager
													.getDBConnConfig().isDB2()))
										newSchema.setV(i,
												LobUtil.clobToString(rs
														.getClob(i + 1)));
									else
										newSchema.setV(i, rs.getObject(i + 1));
								} else if (schema.Columns[i].getColumnType() == 2)
									newSchema.setV(i, LobUtil.blobToBytes(rs
											.getBlob(i + 1)));
								else {
									newSchema.setV(i, rs.getObject(i + 1));
								}
							}
						}
						set.add(newSchema);
					}
					set.setOperateColumns(schema.operateColumnOrders);
					return set;
				}
			});

			// if ((pageSize > 0) && (!(conn.getDBType().equals("MSSQL2000"))))
			// {
			// qb.getParams().remove(qb.getParams().size() - 1);
			// qb.getParams().remove(qb.getParams().size() - 1);
			// }

		} catch (Exception e) {
			LogUtil.getLogger().error("操作表" + TableCode + "时发生错误!");
			e.printStackTrace();
			return null;
		} finally {
		}
	}

	public SchemaSet querySet(int pageSize, int pageIndex) {
		return querySet(null, pageSize, pageIndex);
	}

	public SchemaSet querySet(QueryBuilder queryBuilder, int pageSize,
			int pageIndex) {
		if (queryBuilder == null) {
			queryBuilder = new QueryBuilder(" WHERE 1=1 ");
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < Columns.length; ++i) {
				SchemaColumn sc = Columns[i];
				if (!(isNull(sc))) {
					Object v = getV(sc.getColumnOrder());
					if (v == null) {// TODO null值暂时跳过，除非主动设置null
						continue;
					}
					queryBuilder.add(v);
					sb.append(" and ");
					sb.append(sc.getColumnName());
					sb.append("=?");

				}
			}
			queryBuilder.append(sb.toString());
		}

		if ((queryBuilder != null)
				&& queryBuilder.getSQL() != null
				&& (!(queryBuilder.getSQL().trim().toLowerCase()
						.startsWith("where")))) {
			throw new RuntimeException("QueryBuilder中的SQL语句不是以where开头的字符串");
		}
		if ((queryBuilder != null) && queryBuilder.getSQL() == null) {
			queryBuilder.setSQL("");
		}

		StringBuffer sb = new StringBuffer("select ");

		int i;
		if (bOperateFlag) {
			for (i = 0; i < operateColumnOrders.length; ++i) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append(Columns[operateColumnOrders[i]].getColumnName());
			}
		} else if (XConnectionPoolManager.getDBConnConfig().DBType == DBTypes.MSSQL) {
			for (i = 0; i < Columns.length; ++i) {
				if (i != 0) {
					sb.append(",");
				}
				sb.append(Columns[i].getColumnName());
			}
		} else
			sb.append("*");

		sb.append(" from " + TableCode);

		sb.append(" ");
		sb.append(queryBuilder.getSQL());

		queryBuilder.setSQL(sb.toString());

		queryBuilder.setPagedQuery(true);
		if (pageSize == -1) {
			pageSize = Integer.MAX_VALUE;
		}
		if (pageIndex == -1) {
			pageIndex = 0;
		}
		queryBuilder.setPageSize(pageSize);
		queryBuilder.setPageIndex(pageIndex);
		return querySet(queryBuilder);
	}

	public AbstractSchema getBackUpSchema(String backupOperator,
			String backupMemo) {
		backupOperator = (XString.isEmpty(backupOperator)) ? "admin"
				: backupOperator;
		Class<?> c = null;
		try {
			c = Class.forName("com.xdarkness.schema.B" + this.TableCode + "Schema");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Schema bSchema = null;
		try {
			bSchema = (Schema) c.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i = 0;
		for (; i < this.Columns.length; ++i) {
			bSchema.setV(i, this.getV(i));
		}
		bSchema.setV(i, SchemaUtil.getBackupNo());
		bSchema.setV(i + 1, backupOperator);
		bSchema.setV(i + 2, new Date());
		bSchema.setV(i + 3, backupMemo);
		return bSchema;
	}

	public SchemaSet querySet(final Schema schema, QueryBuilder qb) {

		try {

			// String pageSQL = qb.getSQL();
			// if (pageSize > 0) {
			// pageSQL = DataAccess.getPagedSQL(conn.getDBType().toString(),
			// qb, pageSize, pageIndex);
			// }

			// if ((pageSize > 0) && (!conn.getDBConfig().isSQLServer2000())) {
			// qb.getParams().remove(qb.getParams().size() - 1);
			// qb.getParams().remove(qb.getParams().size() - 1);
			// }
			return (SchemaSet) qb.execute(new ICallbackStatement() {

				public Object execute(XConnection connection,
						PreparedStatement stmt, ResultSet rs)
						throws SQLException {

					// SchemaSet set = newSet();
					// while (rs.next()) {
					// Schema schema = newInstance();
					// setVAll(conn, schema, rs);
					// set.add(schema);
					// }
					// set.setOperateColumns(this.operateColumnOrders);
					// SchemaSet localSchemaSet1 = set;
					// return localSchemaSet1;
					//					
					SchemaSet set = schema.newSet();
					while (rs.next()) {

						Schema newSchema = schema.newInstance();
						int i;
						if (schema.bOperateFlag) {
							for (i = 0; i < schema.operateColumnOrders.length; ++i) {
								if (schema.Columns[schema.operateColumnOrders[i]]
										.getColumnType() == 10) {
									if ((XConnectionPoolManager
											.getDBConnConfig().isOracle())
											|| (XConnectionPoolManager
													.getDBConnConfig().isDB2()))
										newSchema.setV(
												schema.operateColumnOrders[i],
												LobUtil.clobToString(rs
														.getClob(i + 1)));
									else
										newSchema.setV(
												schema.operateColumnOrders[i],
												rs.getObject(i + 1));
								} else if (schema.Columns[schema.operateColumnOrders[i]]
										.getColumnType() == 2)
									newSchema.setV(
											schema.operateColumnOrders[i],
											LobUtil.blobToBytes(rs
													.getBlob(i + 1)));
								else
									newSchema.setV(
											schema.operateColumnOrders[i], rs
													.getObject(i + 1));
							}
						} else {
							for (i = 0; i < schema.Columns.length; ++i) {
								if (schema.Columns[i].getColumnType() == 10) {
									if ((XConnectionPoolManager
											.getDBConnConfig().isOracle())
											|| (XConnectionPoolManager
													.getDBConnConfig().isDB2()))
										newSchema.setV(i,
												LobUtil.clobToString(rs
														.getClob(i + 1)));
									else
										newSchema.setV(i, rs.getObject(i + 1));
								} else if (schema.Columns[i].getColumnType() == 2)
									newSchema.setV(i, LobUtil.blobToBytes(rs
											.getBlob(i + 1)));
								else {
									newSchema.setV(i, rs.getObject(i + 1));
								}
							}
						}
						set.add(newSchema);
					}
					set.setOperateColumns(schema.operateColumnOrders);
					return set;
				}
			});

			// if ((pageSize > 0) && (!(conn.getDBType().equals("MSSQL2000"))))
			// {
			// qb.getParams().remove(qb.getParams().size() - 1);
			// qb.getParams().remove(qb.getParams().size() - 1);
			// }

		} catch (Exception e) {
			LogUtil.getLogger().error("操作表" + schema.TableCode + "时发生错误!");
			e.printStackTrace();
			return null;
		} finally {
		}
	}

}
