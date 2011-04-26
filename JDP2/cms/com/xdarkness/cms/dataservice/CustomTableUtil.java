package com.xdarkness.cms.dataservice;

import java.sql.SQLException;
import java.util.ArrayList;

import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.schema.ZCCustomTableColumnSchema;
import com.xdarkness.schema.ZCCustomTableColumnSet;
import com.xdarkness.schema.ZCCustomTableSchema;
import com.xdarkness.schema.ZCCustomTableSet;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.DataAccess;
import com.xdarkness.framework.sql.QueryBuilder;

public class CustomTableUtil {
	public static int getTotal(ZCCustomTableSchema table, String wherePart) {
		if ((wherePart != null)
				&& (!wherePart.trim().toLowerCase().startsWith("where"))) {
			throw new RuntimeException("指定的wherePart不是以where开头的字符串");
		}

		DataAccess da = null;
		String code = table.getCode();
		if (table.getType().equals("Link")) {
			da = new DataAccess(OuterDatabase.getConnection(table
					.getDatabaseID()));
			code = table.getOldCode();
		} else {
			da = new DataAccess();
		}

		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from ");
		sb.append(code);
		if (wherePart != null) {
			sb.append(" ");
			sb.append(wherePart);
		}
		try {
			int i = new QueryBuilder(sb.toString()).executeInt();
			return i;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

	public static int getTotal(String tableCode, long databaseID,
			String wherePart) {
		if ((wherePart != null)
				&& (!wherePart.trim().toLowerCase().startsWith("where"))) {
			throw new RuntimeException("指定的wherePart不是以where开头的字符串");
		}

		DataAccess da = new DataAccess(OuterDatabase.getConnection(databaseID));
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) from ");
		sb.append(tableCode);
		if (wherePart != null) {
			sb.append(" ");
			sb.append(wherePart);
		}
		try {
			int i = new QueryBuilder(sb.toString()).executeInt();
			return i;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}

	public static int getTotal(long tableID) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID(tableID);
		table.fill();
		return getTotal(table, null);
	}

	public static int getTotal(String tableCode, String wherePart) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setCode(tableCode);
		ZCCustomTableSet set = table.query();
		if ((set == null) || (set.size() < 1)) {
			return -1;
		}
		return getTotal(set.get(0), wherePart);
	}

	public static DataTable getData(long tableID) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID(tableID);
		table.fill();
		return getData(table);
	}

	public static DataTable getData(String tableCode) {
		return getData(tableCode, null);
	}

	public static DataTable getData(String tableCode, QueryBuilder wherePart) {
		return getData(tableCode, wherePart, -1, -1);
	}

	public static DataTable getData(String tableCode, QueryBuilder wherePart,
			int pageSize, int pageIndex) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setCode(tableCode);
		ZCCustomTableSet set = table.query();
		if ((set == null) || (set.size() < 1)) {
			return null;
		}
		return getData(set.get(0), wherePart, pageSize, pageIndex);
	}

	public static DataTable getData(ZCCustomTableSchema table) {
		return getData(table, null);
	}

	public static DataTable getData(ZCCustomTableSchema table, String wherePart) {
		return getData(table, null, -1, -1);
	}

	public static DataTable getData(long tableID, int pageSize, int pageIndex) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID(tableID);
		table.fill();
		return getData(table, null, pageSize, pageIndex);
	}

	public static DataTable getData(long tableID, QueryBuilder wherePart,
			int pageSize, int pageIndex) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setID(tableID);
		table.fill();
		return getData(table, wherePart, pageSize, pageIndex);
	}

	public static DataTable getData(ZCCustomTableSchema table,
			QueryBuilder wherePart, int pageSize, int pageIndex) {
		if ((wherePart != null)
				&& (!wherePart.getSQL().trim().toLowerCase()
						.startsWith("where"))) {
			throw new RuntimeException("指定的wherePart不是以where开头的字符串");
		}

		DataAccess da = null;
		DataTable dt = null;
		String code = table.getCode();
		if (table.getType().equals("Link")) {
			da = new DataAccess(OuterDatabase.getConnection(table
					.getDatabaseID()));
			code = table.getOldCode();
		} else {
			da = new DataAccess();
		}

		if (wherePart != null)
			wherePart
					.setSQL("select * from " + code + " " + wherePart.getSQL());
		else {
			wherePart = new QueryBuilder("select * from " + code);
		}

		pageIndex = pageIndex < 0 ? 0 : pageIndex;
		try {
			if (pageSize > 0) {
				if (da.getConnection().getDBConfig().isSQLServer()) {
					prepareForSQLServer(wherePart, table.getID());
				}
				dt = wherePart.executePagedDataTable(pageSize, pageIndex);
			} else {
				dt = wherePart.executeDataTable();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				da.close();
			} catch (SQLException el) {
				e.printStackTrace();
			}
		} finally {
			try {
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dt;
	}

	public static DataTable getData(String tableCode, long databaseID,
			QueryBuilder wherePart, int pageSize, int pageIndex) {
		if ((wherePart != null)
				&& (!wherePart.getSQL().trim().toLowerCase()
						.startsWith("where"))) {
			throw new RuntimeException("指定的wherePart不是以where开头的字符串");
		}

		DataAccess da = new DataAccess(OuterDatabase.getConnection(databaseID));
		DataTable dt = null;
		if (wherePart != null)
			wherePart.setSQL("select * from " + tableCode + " "
					+ wherePart.getSQL());
		else {
			wherePart = new QueryBuilder("select * from " + tableCode);
		}

		pageIndex = pageIndex < 0 ? 0 : pageIndex;
		try {
			if (pageSize > 0) {
				if (da.getConnection().getDBConfig().isSQLServer()) {
					long id = new QueryBuilder(
							"select ID from ZCCustomTable where Code=?",
							tableCode).executeLong();
					prepareForSQLServer(wherePart, id);
				}
				dt = wherePart.executePagedDataTable( pageSize, pageIndex);
			} else {
				dt = wherePart.executeDataTable();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				da.close();
			} catch (SQLException le) {
				e.printStackTrace();
			}
		} finally {
			try {
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dt;
	}

	private static void prepareForSQLServer(QueryBuilder qb, long tableID) {
		if (qb.getSQL().indexOf(" order by ") > 0) {
			return;
		}
		ZCCustomTableColumnSchema ctc = new ZCCustomTableColumnSchema();
		ctc.setTableID(tableID);
		ZCCustomTableColumnSet set = ctc.query();
		String orderColumn = null;
		for (int i = 0; i < set.size(); i++) {
			ctc = set.get(i);
			if ("Y".equals(ctc.getIsAutoID())) {
				orderColumn = ctc.getCode();
				break;
			}
		}
		if (XString.isEmpty(orderColumn)) {
			for (int i = 0; i < set.size(); i++) {
				ctc = set.get(i);
				if ("Y".equals(ctc.getIsPrimaryKey())) {
					orderColumn = ctc.getCode();
					break;
				}
			}
		}
		if (XString.isEmpty(orderColumn)) {
			orderColumn = set.get(0).getCode();
		}
		qb.append(" order by " + orderColumn);
	}

	public static DataTable executeDataTable(String tableCode, QueryBuilder qb) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setCode(tableCode);
		ZCCustomTableSet set = table.query();
		if ((set == null) || (set.size() < 1)) {
			return null;
		}
		return executeDataTable(set.get(0), qb);
	}

	public static DataTable executeDataTable(ZCCustomTableSchema table,
			QueryBuilder qb) {
		DataAccess da = null;
		DataTable dt = null;
		String code = table.getCode();
		if (table.getType().equals("Link")) {
			da = new DataAccess(OuterDatabase.getConnection(table
					.getDatabaseID()));
			code = table.getOldCode();
		} else {
			da = new DataAccess();
		}
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(code);
		qb.setSQL("select * from " + code + " " + qb.getSQL());
		try {
			dt = qb.executeDataTable();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				da.close();
			} catch (SQLException el) {
				e.printStackTrace();
			}
		} finally {
			try {
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dt;
	}

	public static Object executeOneValue(String tableCode, QueryBuilder qb) {
		ZCCustomTableSchema table = new ZCCustomTableSchema();
		table.setCode(tableCode);
		ZCCustomTableSet set = table.query();
		if ((set == null) || (set.size() < 1)) {
			return null;
		}
		return executeOneValue(set.get(0), qb);
	}

	public static Object executeOneValue(ZCCustomTableSchema table,
			QueryBuilder qb) {
		DataAccess da = null;
		Object dt = null;
		if (table.getType().equals("Link"))
			da = new DataAccess(OuterDatabase.getConnection(table
					.getDatabaseID()));
		else {
			da = new DataAccess();
		}
		try {
			dt = qb.executeOneValue();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				da.close();
			} catch (SQLException el) {
				e.printStackTrace();
			}
		} finally {
			try {
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return dt;
	}

	public static String updateData(ZCCustomTableSchema table, DataTable dt) {
		DataAccess da = null;
		String code = table.getCode();
		if (table.getType().equals("Link")) {
			da = new DataAccess(OuterDatabase.getConnection(table
					.getDatabaseID()));
			code = table.getOldCode();
		} else {
			da = new DataAccess();
		}
		try {
			da.setAutoCommit(false);
			ZCCustomTableColumnSet set = new ZCCustomTableColumnSchema()
					.query(new QueryBuilder("where TableID=?", table.getID()));

			QueryBuilder qb = new QueryBuilder("delete from " + code
					+ " where 1=1 ");
			qb.setBatchMode(true);
			ArrayList list = new ArrayList(4);
			for (int i = 0; i < set.size(); i++) {
				if ("Y".equals(set.get(i).getIsPrimaryKey())) {
					qb.append(" and " + set.get(i).getCode() + "=?");
					list.add(set.get(i).getCode());
				}
			}
			for (int i = 0; i < dt.getRowCount(); i++) {
				for (int j = 0; j < list.size(); j++) {
					String v = dt.getString(i, list.get(j).toString());
					if (dt.getDataColumn(list.get(j).toString() + "_Old") != null) {
						v = dt.getString(i, list.get(j).toString() + "_Old");
					}
					if (XString.isEmpty(v)) {
						v = null;
					}
					qb.add(v);
				}
				qb.addBatch();
			}
			qb.executeNoQuery();

			StringBuffer sb = new StringBuffer("insert into " + code + "(");
			for (int j = 0; j < set.size(); j++) {
				if (j != 0) {
					sb.append(",");
				}
				sb.append(set.get(j).getCode());
			}
			sb.append(") values (");
			for (int j = 0; j < set.size(); j++) {
				if (j != 0) {
					sb.append(",");
				}
				sb.append("?");
			}
			sb.append(")");
			qb = new QueryBuilder(sb.toString());
			qb.setBatchMode(true);
			int i;
			for ( i = 0; i < dt.getRowCount(); i++) {
				for (int j = 0; j < set.size(); j++) {
					ZCCustomTableColumnSchema column = set.get(j);
					String v = dt.getString(i, set.get(j).getCode());
					if (XString.isEmpty(v)) {
						v = null;
						if ("Y".equals(set.get(j).getIsAutoID())) {
							v = String.valueOf(OrderUtil.getDefaultOrder());
						}
					}
					String str1;
					if ((("Y".equals(column.getIsMandatory())) || ("Y"
							.equals(column.getIsPrimaryKey())))
							&& (XString.isEmpty(v))) {
						str1 = column.getName() + "不能为空!\\n";
						return str1;
					}
					int dataType = Integer.parseInt(column.getDataType());
					if (v != null) {
						if ((column.getMaxLength() != 0)
								&& (v.length() < column.getMaxLength())) {
							str1 = column.getName() + "数据过长，最大允许"
									+ column.getMaxLength() + "个字符!\\n";
							return str1;
						}
						try {
							if (dataType == 0) {
								v = DateUtil.toDateTimeString(DateUtil
										.parseDateTime(v));
								if (v == null) {
									throw new SQLException("日期时间错误");
								}
							}
							if ((dataType == 8) || (dataType == 9)) {
								v = String.valueOf(new Double(Double
										.parseDouble(v)).intValue());
							}
							if (dataType == 7) {
								v = String.valueOf(new Double(Double
										.parseDouble(v)).longValue());
							}
							if (dataType == 5) {
								v = String.valueOf(new Double(Double
										.parseDouble(v)).floatValue());
							}
							if ((dataType == 4) || (dataType == 6)
									|| (dataType == 3))
								v = String.valueOf(Double.parseDouble(v));
						} catch (Exception e) {
							str1 = column.getName() + "数据不正确!\\n";
							try {
								da.setAutoCommit(true);
								da.close();
							} catch (SQLException el) {
								e.printStackTrace();
							}
							return str1;
						}
					}
					qb.add(v);
				}
				qb.addBatch();
			}
			qb.executeNoQuery();
			da.commit();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				da.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			String str1 = e.getMessage();
			return str1;
		} finally {
			try {
				da.setAutoCommit(true);
				da.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

/*
 * com.xdarkness.cms.dataservice.CustomTableUtil JD-Core Version: 0.6.0
 */