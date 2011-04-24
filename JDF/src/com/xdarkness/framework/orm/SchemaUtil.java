package com.xdarkness.framework.orm;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;

import org.apache.tools.zip.ZipFile;

import com.xdarkness.framework.connection.XConnectionConfig;
import com.xdarkness.framework.sql.DataAccess;
import com.xdarkness.framework.sql.IDataAccess;

public class SchemaUtil {

	public SchemaUtil() {
	}
	public static ArrayList getPrimaryKeyColumns(SchemaColumn[] scs) {
		ArrayList list = new ArrayList();
		for (int i = 0; i < scs.length; i++) {
			if (scs[i].isPrimaryKey()) {
				list.add(scs[i].getColumnName());
			}
		}
		return list;
	}
	public static SchemaColumn findColumn(String tableName, String columnName) {
		Schema schema = findSchema(tableName);
		return findColumn(schema.Columns, columnName);
	}

	public static SchemaColumn findColumn(SchemaColumn[] scs, String columnName) {
		for (int i = 0; i < scs.length; i++) {
			if (scs[i].getColumnName().equalsIgnoreCase(columnName)) {
				return scs[i];
			}
		}
		return null;
	}
	public static Schema findSchema(String tableName) {
		String[] arr = getAllSchemaClassName();
		for (int i = 0; i < arr.length; i++) {
			String name = arr[i].toLowerCase();
			if (!name.endsWith("." + tableName.toLowerCase() + "schema"))
				continue;
			try {
				return (Schema) Class.forName(arr[i]).newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public static boolean deleteByCondition(Schema conditionSchema) {
		return deleteByCondition(conditionSchema, new DataAccess(), 0);
	}

	public static boolean delete(Schema conditionSchema, IDataAccess dAccess) {
		return deleteByCondition(conditionSchema, dAccess, 1);
	}

	public static boolean deleteByCondition(Schema conditionSchema,
			IDataAccess dAccess, int bConnFlag) {
		SchemaColumn[] columns = conditionSchema.Columns;
		boolean firstFlag = true;
		StringBuffer sb = new StringBuffer(128);
		sb.append("delete from " + conditionSchema.TableCode);
		for (int i = 0; i < columns.length; ++i) {
			SchemaColumn sc = columns[i];
			if (!(conditionSchema.isNull(sc))) {
				if (firstFlag) {
					sb.append(" where ");
					sb.append(sc.getColumnName());
					sb.append("=?");
					firstFlag = false;
				} else {
					sb.append(" and ");
					sb.append(sc.getColumnName());
					sb.append("=?");
				}
			}
		}
		Connection conn = dAccess.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sb.toString(), 1003, 1007);
			int i = 0;
			for (int j = 0; i < columns.length; ++i) {
				SchemaColumn sc = columns[i];
				Object v = conditionSchema.getV(sc.getColumnOrder());
				if (v != null) {
					if (sc.getColumnType() == 0)
						pstmt.setDate(j + 1, new java.sql.Date(
								((java.util.Date) v).getTime()));
					else {
						pstmt.setObject(j + 1, v);
					}
					++j;
				}
			}
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				pstmt = null;
			}
			if (bConnFlag == 0) {
				conn = null;
				try {
					dAccess.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	public static boolean copyFieldValue(Schema srcSchema, Schema destSchema) {
		try {
			SchemaColumn srcSC[] = srcSchema.Columns;
			SchemaColumn destSC[] = destSchema.Columns;
			for (int i = 0; i < srcSC.length; i++) {
				for (int j = 0; j < destSC.length; j++) {
					if (!srcSC[i].getColumnName().equals(
							destSC[j].getColumnName()))
						continue;
					int order = destSC[j].getColumnOrder();
					Object v = srcSchema.getV(srcSC[i].getColumnOrder());
					if (v instanceof java.util.Date)
						destSchema.setV(order, ((java.util.Date) v).clone());
					if (v instanceof Double)
						destSchema.setV(order, new Double(((Double) v)
								.doubleValue()));
					if (v instanceof Float)
						destSchema.setV(order, new Float(((Float) v)
								.floatValue()));
					if (v instanceof Integer)
						destSchema.setV(order, new Integer(((Integer) v)
								.intValue()));
					if (v instanceof Long)
						destSchema
								.setV(order, new Long(((Long) v).longValue()));
					if (v instanceof byte[])
						destSchema.setV(order, ((byte[]) v).clone());
					if (v instanceof String)
						destSchema.setV(order, v);
					break;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public static Schema getZSchemaFromBSchema(Schema bSchema) {
		String TableCode = bSchema.TableCode;
		if (!(TableCode.startsWith("BZ")))
			throw new RuntimeException("必须传入B表的Schema");
		try {
			Class c = Class.forName(bSchema.NameSpace + "."
					+ TableCode.substring(1) + "Schema");
			Schema schema = (Schema) c.newInstance();
			for (int i = 0; i < schema.Columns.length; ++i) {
				schema.setV(i, bSchema.getV(i));
			}
			return schema;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static SchemaSet getZSetFromBSet(SchemaSet bset) {
		String TableCode = bset.TableCode;
		if (!TableCode.startsWith("BZ"))
			throw new RuntimeException("必须传入B表的Set");
		try {
			bset.sort("BackupNo", "asc");

			ArrayList list = new ArrayList();
			for (int i = 0; i < bset.Columns.length; i++) {
				if ((bset.Columns[i].isPrimaryKey())
						&& (!bset.Columns[i].getColumnName().equalsIgnoreCase(
								"BackupNo"))) {
					list.add(new Integer(i));
				}
			}
			int[] keys = new int[list.size()];
			for (int i = 0; i < list.size(); i++) {
				keys[i] = ((Integer) list.get(i)).intValue();
			}
			for (int i = 0; i < bset.size(); i++) {
				Object[] ks = new Object[keys.length];
				for (int j = 0; j < ks.length; j++) {
					ks[j] = bset.getObject(i).getV(j);
				}
				for (int j = i + 1; j < bset.size();) {
					boolean flag = true;
					for (int k = 0; k < keys.length; k++) {
						if (!bset.getObject(j).getV(keys[k]).equals(ks[k])) {
							flag = false;
							break;
						}
					}
					if (flag)
						bset.removeRange(j, 1);
					else {
						j++;
					}
				}
			}
			Class c = Class.forName(bset.NameSpace + "."
					+ TableCode.substring(1) + "Set");
			Class schemaClass = Class.forName(bset.NameSpace + "."
					+ TableCode.substring(1) + "Schema");
			SchemaSet set = (SchemaSet) c.newInstance();
			for (int j = 0; j < bset.size(); j++) {
				Schema schema = (Schema) schemaClass.newInstance();
				Schema bSchema = bset.getObject(j);
				for (int i = 0; i < schema.Columns.length; i++) {
					schema.setV(i, bSchema.getV(i));
				}
				set.add(schema);
			}
			return set;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setParam(SchemaColumn sc, PreparedStatement pstmt,
			XConnectionConfig config, int i, Object v) throws SQLException {
		if (v == null) {
			if (sc.getColumnType() == 7)
				pstmt.setNull(i + 1, -5);
			else if (sc.getColumnType() == 8)
				pstmt.setNull(i + 1, 4);
			else if (sc.getColumnType() == 10) {
//				if (config.isSybase())
//					LobUtil.setClob(conn, pstmt, i + 1, "");
//				else
//					pstmt.setNull(i + 1, 2005);
			} else if (sc.getColumnType() == 6)
				pstmt.setNull(i + 1, 8);
			else if (sc.getColumnType() == 5)
				pstmt.setNull(i + 1, 6);
			else if (sc.getColumnType() == 4)
				pstmt.setNull(i + 1, 3);
			else if (sc.getColumnType() == 0)
				pstmt.setNull(i + 1, 91);
			else if (sc.getColumnType() == 11)
				pstmt.setNull(i + 1, -7);
			else if (sc.getColumnType() == 9)
				pstmt.setNull(i + 1, 5);
			else {
				pstmt.setNull(i + 1, 12);
			}
		} else if (sc.getColumnType() == 0) {
			pstmt.setTimestamp(i + 1, new Timestamp(((java.util.Date) v)
					.getTime()));
		} else if (sc.getColumnType() == 10) {
			String str = (String) v;
//			if ((conn.getDBConfig().isLatin1Charset)
//					&& (conn.getDBConfig().isOracle())) {
//				try {
//					str = new String(str.getBytes(Constant.GlobalCharset),
//							"ISO-8859-1");
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
//			}
//			LobUtil.setClob(conn, pstmt, i + 1, str);
		} else if (sc.getColumnType() == 2) {
//			LobUtil.setBlob(conn, pstmt, i + 1, (byte[]) v);
		} else if (sc.getColumnType() == 1) {
			String str = (String) v;
//			if ((conn.getDBConfig().isLatin1Charset)
//					&& (conn.getDBConfig().isOracle())) {
//				try {
//					str = new String(str.getBytes(Constant.GlobalCharset),
//							"ISO-8859-1");
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
//			}
			pstmt.setString(i + 1, str);
		} else {
			pstmt.setObject(i + 1, v);
		}
	}

	public static String[] getAllSchemaClassName() {
		Class<?> c = null;
		ArrayList<String> list = new ArrayList<String>();
		try {
			c = Class.forName("com.xdarkness.schema.ZDCodeSchema");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		String path = c.getResource("ZDCodeSchema.class").getPath();
		if (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) {
			if (path.startsWith("/"))
				path = path.substring(1);
			else if (path.startsWith("file:/")) {
				path = path.substring(6);
			}
		} else if (path.startsWith("file:/")) {
			path = path.substring(5);
		}

		path = path.replaceAll("%20", " ");
		if (path.toLowerCase().indexOf(".jar!") > 0) {// 获取jar包中的schema
			try {
				path = path.substring(0, path.indexOf(".jar!") + ".jar!".length());
				ZipFile z = new ZipFile(path);
				Enumeration<?> all = z.getEntries();
				while (all.hasMoreElements()) {
					String name = all.nextElement().toString();
					if (name.startsWith("com.xdarkness.schema."))
						list.add(name);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {// 获取文件夹中的schema
			File p = new File(path.substring(0, path.toLowerCase().indexOf(
					"zdcodeschema.class")));
			File[] fs = p.listFiles();
			for (int i = 0; i < fs.length; i++) {
				if (fs[i].getName().endsWith("Schema.class")) {
					list.add("com/sky/schema/" + fs[i].getName());
				}
			}
		}
		
		String[] arr = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			String name = (String) list.get(i);
			name = name.replaceAll("\\/", ".");
			name = name.substring(0, name.length() - ".class".length());
			arr[i] = name;
		}
		return arr;
	}
	public static synchronized String getBackupNo() {
		return String.valueOf(BackupNoBase++).substring(1);
	}

	public static String getTableCode(Schema schema) {
		return schema.TableCode;
	}

	public static String getNameSpace(Schema schema) {
		return schema.NameSpace;
	}

	public static SchemaColumn[] getColumns(Schema schema) {
		return schema.Columns;
	}

	public static String getTableCode(SchemaSet set) {
		return set.TableCode;
	}

	public static String getNameSpace(SchemaSet set) {
		return set.NameSpace;
	}

	public static SchemaColumn[] getColumns(SchemaSet set) {
		return set.Columns;
	}

	private static long BackupNoBase = System.currentTimeMillis();

}
