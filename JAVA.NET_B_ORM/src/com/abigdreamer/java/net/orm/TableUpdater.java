package com.abigdreamer.java.net.orm;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.commons.lang.ArrayUtils;

import com.abigdreamer.java.net.connection.DBTypes;
import com.abigdreamer.java.net.sql.UpdateSQLParser;
import com.abigdreamer.java.net.util.CaseIgnoreMapx;
import com.abigdreamer.java.net.util.Mapx;
import com.abigdreamer.java.net.util.XString;

public class TableUpdater {
	public static String toOracleSQL(String fileName) {
		UpdateSQLParser usp = new UpdateSQLParser(fileName);
		String[] arr = usp.convertToSQLArray("ORACLE");
		StringBuffer sb = new StringBuffer();
		sb
				.append("alter session set nls_date_format = 'YYYY-MM-DD HH24:MI:SS';\n");
		sb.append("create or replace procedure proc_dropifexists(\n");
		sb.append("\t    p_table in varchar2\n");
		sb.append("\t) is\n");
		sb.append("\t    v_count number(10);\n");
		sb.append("\tbegin\n");
		sb.append("\t   select count(*)\n");
		sb.append("\t   into v_count\n");
		sb.append("\t   from user_objects\n");
		sb.append("\t   where object_name = upper(p_table);\n");
		sb.append("\t   if v_count > 0 then\n");
		sb
				.append("\t      execute immediate 'drop table ' || p_table ||' purge';\n");
		sb.append("\t   end if;\n");

		for (int i = 0; i < arr.length; i++) {
			String line = arr[i];
			if ((XString.isEmpty(line)) || (line.startsWith("/*"))) {
				sb.append(line + "\n");
			} else
				sb.append(arr[i] + ";\n");
		}
		return sb.toString();
	}

	public static String toSQLServerSQL(String fileName) {
		UpdateSQLParser usp = new UpdateSQLParser(fileName);
		String[] arr = usp.convertToSQLArray("MSSQL");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			String line = arr[i];
			if ((XString.isEmpty(line)) || (line.startsWith("/*"))) {
				sb.append(line + "\n");
			} else
				sb.append(arr[i] + "\ngo\n");
		}
		return sb.toString();
	}

	public static String toMysqlrSQL(String fileName) {
		UpdateSQLParser usp = new UpdateSQLParser(fileName);
		String[] arr = usp.convertToSQLArray("MYSQL");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			String line = arr[i];
			if ((XString.isEmpty(line)) || (line.startsWith("/*"))) {
				sb.append(line + "\n");
			} else
				sb.append(arr[i] + ";\n");
		}
		return sb.toString();
	}

	public static String toDB2SQL(String fileName) {
		UpdateSQLParser usp = new UpdateSQLParser(fileName);
		String[] arr = usp.convertToSQLArray("DB2");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			String line = arr[i];
			if ((XString.isEmpty(line)) || (line.startsWith("/*"))) {
				sb.append(line + "\n");
			} else
				sb.append(arr[i] + ";\n");
		}
		return sb.toString();
	}

	public static void addIndexes(StringBuffer sb, String tableName,
			DBTypes dbType) {
		try {
			Class c = Class.forName("com.xdarkness.platform.pub.Install");
			Method m = c.getMethod("getIndexSQLForTable", new Class[] {
					String.class, String.class });
			Object obj = m.invoke(null, new Object[] { tableName, dbType });
			ArrayList list = (ArrayList) obj;
			for (int i = 0; i < list.size(); i++)
				sb.append(list.get(i) + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addPrimaryKey(StringBuffer sb, String tableName,
			DBTypes dbType) {
		try {
			SchemaColumn[] scs = SchemaUtil.findSchema(tableName).Columns;
			ArrayList pkList = SchemaUtil.getPrimaryKeyColumns(scs);
			AlterKeyInfo info = new AlterKeyInfo();
			info.TableName = tableName;
			info.NewKeys = XString.join(pkList);
			String[] arr = info.toSQLArray(dbType);
			for (int i = 0; i < arr.length; i++)
				sb.append(arr[i] + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static class AlterKeyInfo extends TableUpdateInfo implements
			Serializable {
		private static final long serialVersionUID = 1L;
		public String TableName;
		public String NewKeys;
		public boolean DropFlag;

		public String[] toSQLArray(DBTypes dbType) {
			if (this.DropFlag) {
				String sql = "alter table " + this.TableName
						+ " drop primary key";
				if ("MSSQL".equals(dbType)) {
					sql = "alter table " + this.TableName
							+ " drop constraint PK_" + this.TableName;
				}
				return new String[] { sql };
			}
			String sql = "alter table " + this.TableName + " add primary key ("
					+ this.NewKeys + ")";
			if ("MSSQL".equals(dbType)) {
				sql = "alter table " + this.TableName + " add constraint PK_"
						+ this.TableName + " primary key  NONCLUSTERED("
						+ this.NewKeys + ")";
			}
			return new String[] { sql };
		}
	}

	public static class AlterTableInfo extends TableUpdateInfo implements
			Serializable {
		private static final long serialVersionUID = 1L;
		public String TableName;
		public String Action;
		public int ColumnType;
		public String OldColumnName;
		public String NewColumnName;
		public String AfterColumn;
		public int Length;
		public int Precision;
		public boolean Mandatory;

		public String[] toSQLArray(DBTypes dbType) {
			return toSQLArray(dbType, new ArrayList(), new CaseIgnoreMapx());
		}

		public String[] toSQLArray(DBTypes dbType, ArrayList togetherColumns,
				Mapx exclusiveMapx) {
			exclusiveMapx.put(this.OldColumnName, "1");
			if (!togetherColumns.contains(this)) {
				togetherColumns.add(0, this);
			}
			if (dbType.equals("MYSQL")) {
				if (this.Action.equalsIgnoreCase("add")) {
					String[] arr = new String[togetherColumns.size()];
					for (int i = 0; i < togetherColumns.size(); i++) {
						AlterTableInfo info = (AlterTableInfo) togetherColumns
								.get(i);
						String fieldExt = TableCreator.getFieldExtDesc(
								info.Length, info.Precision).trim();
						String sql = "alter table "
								+ info.TableName
								+ " add column "
								+ info.OldColumnName
								+ " "
								+ TableCreator.toSQLType(info.ColumnType,
										dbType) + fieldExt;
						if (info.Mandatory) {
							sql = sql + " not null";
						}
						if (XString.isNotEmpty(info.AfterColumn)) {
							sql = sql + " after " + info.AfterColumn;
						}
						arr[i] = sql;
					}
					return arr;
				}
				if (this.Action.equalsIgnoreCase("drop")) {
					String sql = "alter table " + this.TableName
							+ " drop column " + this.OldColumnName;
					return new String[] { sql };
				}
				if ((this.Action.equalsIgnoreCase("modify"))
						|| (this.Action.equalsIgnoreCase("change"))) {
					String sql = "alter table " + this.TableName
							+ " change column " + this.OldColumnName;
					if (XString.isNotEmpty(this.NewColumnName)) {
						sql = sql + " " + this.NewColumnName;
					}
					if (this.ColumnType == -1) {
						System.out.println(1);
					}
					sql = sql + " "
							+ TableCreator.toSQLType(this.ColumnType, dbType);
					String fieldExt = TableCreator.getFieldExtDesc(this.Length,
							this.Precision).trim();
					sql = sql + fieldExt;
					if (this.Mandatory) {
						sql = sql + " not null";
					}
					return new String[] { sql };
				}
			}
			if (dbType.equals("ORACLE")) {
				if (this.Action.equalsIgnoreCase("add")) {
					StringBuffer sb = new StringBuffer();

					if (XString.isNotEmpty(this.AfterColumn)) {
						Schema schema = SchemaUtil.findSchema(this.TableName);
						SchemaColumn[] scs = (SchemaColumn[]) schema.Columns
								.clone();
						ArrayList list = new ArrayList();
						for (int i = 0; i < scs.length; i++) {
							if ((exclusiveMapx == null)
									|| (!exclusiveMapx.containsKey(scs[i]
											.getColumnName()))) {
								list.add(scs[i].getColumnName());
							}
							for (int j = togetherColumns.size() - 1; j >= 0; j--) {
								AlterTableInfo info = (AlterTableInfo) togetherColumns
										.get(j);
								String columnName = info.AfterColumn;
								if (scs[i].getColumnName().equalsIgnoreCase(
										columnName)) {
									list.add("'0' as " + info.OldColumnName);
								}
							}

						}

						sb.append("create table " + this.TableName
								+ "_TMP as select " + XString.join(list)
								+ " from " + this.TableName + "\n");
						for (int j = 0; j < togetherColumns.size(); j++) {
							AlterTableInfo info = (AlterTableInfo) togetherColumns
									.get(j);
							String fieldExt = TableCreator.getFieldExtDesc(
									info.Length, info.Precision).trim();
							if (this.Mandatory) {
								if (info.ColumnType == 1) {
									sb.append("update " + info.TableName
											+ "_TMP set " + info.OldColumnName
											+ "='0'\n");
								} else if (info.ColumnType == 0) {
									sb.append("update " + info.TableName
											+ "_TMP set " + info.OldColumnName
											+ "=null\n");
									sb.append("alter table "
											+ info.TableName
											+ "_TMP modify "
											+ info.OldColumnName
											+ " "
											+ TableCreator.toSQLType(
													info.ColumnType, dbType)
											+ fieldExt + "\n");
									sb.append("update " + info.TableName
											+ "_TMP set " + info.OldColumnName
											+ "='1970-01-01 00:00:00'\n");
								} else {
									sb.append("update " + info.TableName
											+ "_TMP set " + info.OldColumnName
											+ "=null\n");
									sb.append("alter table "
											+ info.TableName
											+ "_TMP modify "
											+ info.OldColumnName
											+ " "
											+ TableCreator.toSQLType(
													info.ColumnType, dbType)
											+ fieldExt + "\n");
									sb.append("update " + info.TableName
											+ "_TMP set " + info.OldColumnName
											+ "=0\n");
								}
							} else
								sb.append("update " + info.TableName
										+ "_TMP set " + info.OldColumnName
										+ "=null\n");

							sb.append("alter table "
									+ info.TableName
									+ "_TMP modify "
									+ info.OldColumnName
									+ " "
									+ TableCreator.toSQLType(info.ColumnType,
											dbType) + fieldExt
									+ (info.Mandatory ? " not null" : "")
									+ "\n");
						}
						sb.append("drop table " + this.TableName + "\n");
						sb.append("rename " + this.TableName + "_TMP to "
								+ this.TableName + "\n");
						TableUpdater.addPrimaryKey(sb, this.TableName, dbType);
						TableUpdater.addIndexes(sb, this.TableName, dbType);
					} else {
						for (int j = 0; j < togetherColumns.size(); j++) {
							AlterTableInfo info = (AlterTableInfo) togetherColumns
									.get(j);
							String fieldExt = TableCreator.getFieldExtDesc(
									info.Length, info.Precision).trim();
							sb.append("alter table "
									+ info.TableName
									+ " add "
									+ info.OldColumnName
									+ " "
									+ TableCreator.toSQLType(info.ColumnType,
											dbType) + fieldExt);
							if (info.Mandatory) {
								sb.append(" not null");
							}
							sb.append("\\n");
						}
					}
					return sb.toString().split("\\n");
				}
				if (this.Action.equalsIgnoreCase("drop")) {
					String sql = "alter table " + this.TableName
							+ " drop column " + this.OldColumnName;
					return new String[] { sql };
				}
				if ((this.Action.equalsIgnoreCase("modify"))
						|| (this.Action.equalsIgnoreCase("change"))) {
					if ((XString.isNotEmpty(this.NewColumnName))
							&& (!this.NewColumnName
									.equalsIgnoreCase(this.OldColumnName))) {
						Schema schema = SchemaUtil.findSchema(this.TableName);
						SchemaColumn[] scs = (SchemaColumn[]) schema.Columns
								.clone();
						ArrayList list = new ArrayList();
						for (int i = 0; i < scs.length; i++) {
							if ((exclusiveMapx == null)
									|| (!exclusiveMapx.containsKey(scs[i]
											.getColumnName()))) {
								if (scs[i].getColumnName().equalsIgnoreCase(
										this.NewColumnName))
									list.add(this.OldColumnName + " as "
											+ this.NewColumnName);
								else {
									list.add(scs[i].getColumnName());
								}
							}
						}
						StringBuffer sb = new StringBuffer();
						sb.append("create table " + this.TableName
								+ "_TMP as select " + XString.join(list)
								+ " from " + this.TableName + "\n");
						sb.append("drop table " + this.TableName + "\n");
						sb.append("rename " + this.TableName + "_TMP to "
								+ this.TableName + "\n");
						TableUpdater.addPrimaryKey(sb, this.TableName, dbType);
						TableUpdater.addIndexes(sb, this.TableName, dbType);
						return sb.toString().split("\\n");
					}
					String sql = "alter table " + this.TableName + " modify "
							+ this.OldColumnName;
					sql = sql + " "
							+ TableCreator.toSQLType(this.ColumnType, dbType);
					String fieldExt = TableCreator.getFieldExtDesc(this.Length,
							this.Precision).trim();
					sql = sql + fieldExt;
					if (this.Mandatory) {
						sql = sql + " not null";
					}
					return new String[] { sql };
				}
			}
			if (dbType.equals("MSSQL")) {
				if (this.Action.equalsIgnoreCase("add")) {
					ArrayList sqlList = new ArrayList();
					if (XString.isNotEmpty(this.AfterColumn)) {
						Schema schema = SchemaUtil.findSchema(this.TableName);
						SchemaColumn[] scs = (SchemaColumn[]) schema.Columns
								.clone();
						ArrayList listInsert = new ArrayList();
						ArrayList listSelect = new ArrayList();
						ArrayList pkList = new ArrayList();
						for (int i = scs.length - 1; i >= 0; i--) {
							if ((exclusiveMapx == null)
									|| (!exclusiveMapx.containsKey(scs[i]
											.getColumnName()))) {
								listInsert.add(scs[i].getColumnName());
								listSelect.add(scs[i].getColumnName());
							}
							if (scs[i].isPrimaryKey()) {
								pkList.add(scs[i].getColumnName());
							}
							for (int j = 0; j < togetherColumns.size(); j++) {
								AlterTableInfo info = (AlterTableInfo) togetherColumns
										.get(j);
								String columnName = info.AfterColumn;
								if (scs[i].getColumnName().equalsIgnoreCase(
										columnName)) {
									SchemaColumn sc = new SchemaColumn(
											info.OldColumnName,
											info.ColumnType, i, info.Length,
											info.Precision, info.Mandatory,
											false);
									scs = (SchemaColumn[]) ArrayUtils.add(scs,
											i + 1, sc);

									if (info.Mandatory) {
										listInsert.add(info.OldColumnName);
										listSelect.add("''0'' as "
												+ info.OldColumnName);
									}
								}
							}
						}

						for (int i = scs.length - 1; i > 0; i--) {
							boolean duplicateFlag = false;
							for (int j = i - 1; j >= 0; j--) {
								if (scs[i].getColumnName().equalsIgnoreCase(
										scs[j].getColumnName())) {
									scs = (SchemaColumn[]) ArrayUtils.remove(
											scs, i);
									duplicateFlag = true;
									break;
								}
							}
							if (duplicateFlag) {
								continue;
							}
							if ((exclusiveMapx != null)
									&& (exclusiveMapx.containsKey(scs[i]
											.getColumnName()))) {
								boolean removeFlag = true;
								for (int j = 0; j < togetherColumns.size(); j++) {
									AlterTableInfo info = (AlterTableInfo) togetherColumns
											.get(j);
									String columnName = info.OldColumnName;
									if (scs[i].getColumnName()
											.equalsIgnoreCase(columnName)) {
										removeFlag = false;
									}
								}
								if (removeFlag)
									scs = (SchemaColumn[]) ArrayUtils.remove(
											scs, i);
							}
						}
						try {
							sqlList.add(TableCreator.dropTable(this.TableName
									+ "_TMP", dbType));
							String sql = TableCreator.createTable(scs,
									this.TableName + "_TMP", dbType);

							int index = sql.indexOf(",\n\tconstraint");
							sql = sql.substring(0, index) + ")";
							sqlList.add(sql);
						} catch (Exception e) {
							e.printStackTrace();
						}

						sqlList.add("if exists(select * from " + this.TableName
								+ ") exec ('insert into " + this.TableName
								+ "_TMP (" + XString.join(listInsert)
								+ ") select " + XString.join(listSelect)
								+ " from " + this.TableName + "')");
						sqlList.add("drop table " + this.TableName);
						sqlList.add("sp_rename '" + this.TableName + "_TMP', '"
								+ this.TableName + "', 'OBJECT'");
						StringBuffer sb = new StringBuffer();
						TableUpdater.addPrimaryKey(sb, this.TableName, dbType);
						TableUpdater.addIndexes(sb, this.TableName, dbType);
						String[] arr = sb.toString().split("\\n");
						for (int i = 0; i < arr.length; i++)
							sqlList.add(arr[i]);
					} else {
						for (int j = 0; j < togetherColumns.size(); j++) {
							AlterTableInfo info = (AlterTableInfo) togetherColumns
									.get(j);
							String fieldExt = TableCreator.getFieldExtDesc(
									info.Length, info.Precision).trim();
							String sql = "alter table "
									+ info.TableName
									+ " add "
									+ info.OldColumnName
									+ " "
									+ TableCreator.toSQLType(info.ColumnType,
											dbType) + fieldExt;
							if (info.Mandatory) {
								sql = sql + " not null";
							}
							sqlList.add(sql);
						}
					}
					String[] arr = new String[sqlList.size()];
					for (int i = 0; i < arr.length; i++) {
						arr[i] = ((String) sqlList.get(i));
					}
					return arr;
				}
				if (this.Action.equalsIgnoreCase("drop")) {
					String sql = "alter table " + this.TableName
							+ " drop column " + this.OldColumnName;
					return new String[] { sql };
				}
				if ((this.Action.equalsIgnoreCase("modify"))
						|| (this.Action.equalsIgnoreCase("change"))) {
					ArrayList sqlList = new ArrayList();
					SchemaColumn column = SchemaUtil.findColumn(this.TableName,
							this.OldColumnName);
					if (column == null) {
						column = SchemaUtil.findColumn(this.TableName,
								this.NewColumnName);
					}
					if (column == null) {
						System.out.println(1);
					}
					if (column.isPrimaryKey()) {
						sqlList.add("alter table " + this.TableName
								+ " drop constraint PK_" + this.TableName);
					}
					String sql = "alter table " + this.TableName
							+ " alter column " + this.OldColumnName;
					sql = sql + " "
							+ TableCreator.toSQLType(this.ColumnType, dbType);
					String fieldExt = TableCreator.getFieldExtDesc(this.Length,
							this.Precision).trim();
					sql = sql + fieldExt;
					if (this.Mandatory) {
						sql = sql + " not null";
					}
					sqlList.add(sql);
					if ((XString.isNotEmpty(this.NewColumnName))
							&& (!this.NewColumnName
									.equalsIgnoreCase(this.OldColumnName))) {
						sqlList.add(" sp_rename '" + this.TableName + "."
								+ this.OldColumnName + "','"
								+ this.NewColumnName + "','column'");
					}
					if (column.isPrimaryKey()) {
						SchemaColumn[] scs = SchemaUtil
								.findSchema(this.TableName).Columns;
						ArrayList pkList = SchemaUtil.getPrimaryKeyColumns(scs);
						sqlList.add("alter table " + this.TableName
								+ " add constraint PK_" + this.TableName
								+ " primary key NONCLUSTERED("
								+ XString.join(pkList) + ")");
					}
					String[] arr = new String[sqlList.size()];
					for (int i = 0; i < arr.length; i++) {
						arr[i] = ((String) sqlList.get(i));
					}
					return arr;
				}
			}
			return null;
		}
	}

	public static class CommentInfo extends TableUpdateInfo implements
			Serializable {
		private static final long serialVersionUID = 1L;
		public String Comment;

		public String[] toSQLArray(DBTypes dbType) {
			return new String[] { this.Comment };
		}
	}

	public static class CreateTableInfo extends TableUpdateInfo implements
			Serializable {
		private static final long serialVersionUID = 1L;
		public String TableName;
		public ArrayList Columns = new ArrayList();

		public String[] toSQLArray(DBTypes dbType) {
			try {
				SchemaColumn[] scs = new SchemaColumn[this.Columns.size()];
				for (int i = 0; i < scs.length; i++) {
					scs[i] = ((SchemaColumn) this.Columns.get(i));
				}
				return new String[] { TableCreator.createTable(scs,
						this.TableName, dbType) };
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	public static class DropTableInfo extends TableUpdateInfo implements
			Serializable {
		private static final long serialVersionUID = 1L;
		public String TableName;

		public String[] toSQLArray(DBTypes dbType) {
			return new String[] { TableCreator
					.dropTable(this.TableName, dbType) };
		}
	}

	public static class SQLInfo extends TableUpdateInfo implements Serializable {
		private static final long serialVersionUID = 1L;
		public String SQL;

		public String[] toSQLArray(DBTypes dbType) {
			if (this.SQL.trim().endsWith(";")) {
				this.SQL = this.SQL.substring(0, this.SQL.length() - 1).trim();
			}
			return new String[] { this.SQL };
		}
	}
}
