package com.xdarkness.framework.orm;

import java.sql.SQLException;
import java.util.ArrayList;

import com.xdarkness.framework.connection.DBTypes;
import com.xdarkness.framework.connection.XConnectionPoolManager;
import com.xdarkness.framework.data.Transaction;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.LogUtil;

public class TableCreator {
	private ArrayList<String> list = new ArrayList<String>();
	private DBTypes dbType;

	public TableCreator() {
		this.dbType = XConnectionPoolManager.getDBConnConfig().DBType;
	}

	public TableCreator(DBTypes dbType) {
		this.dbType = dbType;
	}

	public void createTable(SchemaColumn[] scs, String tableCode)
			throws Exception {
		createTable(scs, tableCode, true);
	}

	public void createTable(SchemaColumn[] scs, String tableCode, boolean create)
			throws Exception {
		if (!create) {
			this.list.add("delete from " + tableCode);
		} else {
			dropTable(tableCode);
			this.list.add(createTable(scs, tableCode, this.dbType));
		}
	}

	public static String createTable(SchemaColumn[] scs, String tableCode,
			DBTypes DBType) throws Exception {
		if (DBType == DBTypes.MSSQL) {
			return createTableMSSQL(scs, tableCode, DBType);
		}
		if (DBType == DBTypes.MYSQL) {
			return createTableMYSQL(scs, tableCode, DBType);
		}
		if (DBType == DBTypes.ORACLE) {
			return createTableOracle(scs, tableCode, DBType);
		}
		if (DBType == DBTypes.DB2) {
			return createTableDB2(scs, tableCode, DBType);
		}
		if (DBType == DBTypes.SYBASE) {
			return createTableSybase(scs, tableCode, DBType);
		}
		return null;
	}

	// public void executeAndClear() {
	// Transaction tran = new Transaction();
	// executeAndClear(tran);
	// tran.commit();
	// }

	public void executeAndClear(Transaction tran) {
		for (int i = 0; i < this.list.size(); i++) {
			QueryBuilder qb = new QueryBuilder(this.list.get(i).toString());
			tran.add(qb);
		}
		this.list.clear();
	}

	public void executeAndClear() {
		for (int i = 0; i < this.list.size(); i++) {
			QueryBuilder qb = new QueryBuilder(this.list.get(i).toString());
			try {
				qb.executeNoQuery();
			} catch (SQLException e) {
				if (qb.getSQL().startsWith("drop")) {
					String table = qb.getSQL();
					table = table.substring(table.indexOf(" ", 8)).trim();

					LogUtil.warn("未能删除表，可能不存在：" + table);
				} else {
					LogUtil.warn(qb.getSQL());
					e.printStackTrace();
				}
			}
		}
		this.list.clear();
	}

	public String[] getSQLArray() {
		String[] arr = new String[this.list.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = this.list.get(i).toString();
		}
		return arr;
	}

	public String getAllSQL() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < this.list.size(); i++) {
			sb.append(this.list.get(i));
			if ((this.dbType == DBTypes.MSSQL)
					|| (this.dbType == DBTypes.SYBASE))
				sb.append("\ngo\n");
			else {
				sb.append(";\n");
			}
		}
		return sb.toString();
	}

	public static String toSQLType(int columnType, DBTypes DBType) {
		if (DBType == DBTypes.MSSQL) {
			if (columnType == 3)
				return "numeric";
			if (columnType == 2)
				return "varbinary(MAX)";
			if (columnType == 0)
				return "datetime";
			if (columnType == 4)
				return "decimal";
			if (columnType == 6)
				return "numeric";
			if (columnType == 5)
				return "numeric";
			if (columnType == 8)
				return "int";
			if (columnType == 7)
				return "bigint";
			if (columnType == 9)
				return "int";
			if (columnType == 1)
				return "varchar";
			if (columnType == 10) {
				return "text";
			}
		}
		if (DBType == DBTypes.SYBASE) {
			if (columnType == 3)
				return "numeric";
			if (columnType == 2)
				return "varbinary(MAX)";
			if (columnType == 0)
				return "datetime";
			if (columnType == 4)
				return "decimal";
			if (columnType == 6)
				return "numeric";
			if (columnType == 5)
				return "numeric";
			if (columnType == 8)
				return "int";
			if (columnType == 7)
				return "numeric(20)";
			if (columnType == 9)
				return "int";
			if (columnType == 1)
				return "varchar";
			if (columnType == 10) {
				return "text";
			}
		}
		if (DBType == DBTypes.ORACLE) {
			if (columnType == 3)
				return "DOUBLE PRECISION";
			if (columnType == 2)
				return "BLOB";
			if (columnType == 0)
				return "DATE";
			if (columnType == 4)
				return "DECIMAL";
			if (columnType == 6)
				return "NUMBER";
			if (columnType == 5)
				return "NUMBER";
			if (columnType == 8)
				return "INTEGER";
			if (columnType == 7)
				return "INTEGER";
			if (columnType == 9)
				return "INTEGER";
			if (columnType == 1)
				return "VARCHAR2";
			if (columnType == 10) {
				return "CLOB";
			}
		}
		if (DBType == DBTypes.DB2) {
			if (columnType == 3)
				return "DOUBLE PRECISION";
			if (columnType == 2)
				return "BLOB";
			if (columnType == 0)
				return "TIMESTAMP";
			if (columnType == 4)
				return "DECIMAL";
			if (columnType == 6)
				return "NUMERIC";
			if (columnType == 5)
				return "NUMERIC";
			if (columnType == 8)
				return "INTEGER";
			if (columnType == 7)
				return "BIGINT";
			if (columnType == 9)
				return "INTEGER";
			if (columnType == 1)
				return "VARCHAR";
			if (columnType == 10) {
				return "CLOB";
			}
		}
		if (DBType == DBTypes.MYSQL) {
			if (columnType == 3)
				return "double";
			if (columnType == 2)
				return "binary varying(MAX)";
			if (columnType == 0)
				return "datetime";
			if (columnType == 4)
				return "decimal";
			if (columnType == 6)
				return "double";
			if (columnType == 5)
				return "float";
			if (columnType == 8)
				return "int";
			if (columnType == 7)
				return "bigint";
			if (columnType == 9)
				return "int";
			if (columnType == 1)
				return "varchar";
			if (columnType == 10) {
				return "mediumtext";
			}
		}
		throw new RuntimeException("数据库类型错误:" + DBType + "，或字段类型未知:"
				+ columnType);
	}

	public static String createTableMSSQL(SchemaColumn[] scs, String tableCode,
			DBTypes DBType) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("create table " + tableCode + "(\n");
		StringBuffer ksb = new StringBuffer();
		for (int i = 0; i < scs.length; i++) {
			SchemaColumn sc = scs[i];
			if (i != 0) {
				sb.append(",\n");
			}
			sb.append("\t" + sc.getColumnName() + " ");
			String sqlType = toSQLType(sc.getColumnType(), DBType);
			sb.append(sqlType + " ");
			if ((sc.getColumnType() == 1)
					|| (sqlType.equalsIgnoreCase("NUMERIC"))
					|| (sqlType.equalsIgnoreCase("DECIMAL"))) {
				if ((sc.getLength() == 0) && (sc.getColumnType() == 1)) {
					throw new RuntimeException("必须为varchar类型设定长度，请检查字段"
							+ sc.getColumnName() + "的设置");
				}
				sb.append(getFieldExtDesc(sc.getLength(), sc.getPrecision()));
			}
			if (sc.isMandatory()) {
				sb.append("not null");
			}
			if (sc.isPrimaryKey()) {
				if (ksb.length() == 0)
					ksb.append("\tconstraint PK_" + tableCode
							+ " primary key nonclustered (");
				else {
					ksb.append(",");
				}
				ksb.append(sc.getColumnName());
			}
		}
		if (ksb.length() != 0) {
			ksb.append(")");
			sb.append(",\n" + ksb);
		}
		sb.append("\n)");
		return sb.toString();
	}

	public static String createTableSybase(SchemaColumn[] scs,
			String tableCode, DBTypes DBType) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("create table " + tableCode + "(\n");
		StringBuffer ksb = new StringBuffer();
		for (int i = 0; i < scs.length; i++) {
			SchemaColumn sc = scs[i];
			if (i != 0) {
				sb.append(",\n");
			}
			if (sc.getColumnName().equalsIgnoreCase("Count"))
				sb.append("\t\"" + sc.getColumnName() + "\" ");
			else {
				sb.append("\t" + sc.getColumnName() + " ");
			}
			String sqlType = toSQLType(sc.getColumnType(), DBType);
			sb.append(sqlType + " ");
			if ((sc.getColumnType() == 1)
					|| (sqlType.equalsIgnoreCase("NUMERIC"))
					|| (sqlType.equalsIgnoreCase("DECIMAL"))) {
				if ((sc.getLength() == 0) && (sc.getColumnType() == 1)) {
					throw new RuntimeException("必须为varchar类型设定长度，请检查字段"
							+ sc.getColumnName() + "的设置");
				}
				sb.append(getFieldExtDesc(sc.getLength(), sc.getPrecision()));
			}
			if (sc.isMandatory())
				sb.append("not null");
			else {
				sb.append("null");
			}
			if (sc.isPrimaryKey()) {
				if (ksb.length() == 0)
					ksb.append("\tconstraint PK_" + tableCode
							+ " primary key nonclustered (");
				else {
					ksb.append(",");
				}
				ksb.append(sc.getColumnName());
			}
		}
		if (ksb.length() != 0) {
			ksb.append(")");
			sb.append(",\n" + ksb);
		}
		sb.append("\n)");
		return sb.toString();
	}

	public static String createTableOracle(SchemaColumn[] scs,
			String tableCode, DBTypes DBType) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("create table " + tableCode + "(\n");
		StringBuffer ksb = new StringBuffer();
		for (int i = 0; i < scs.length; i++) {
			SchemaColumn sc = scs[i];
			if (i != 0) {
				sb.append(",\n");
			}
			sb.append("\t" + sc.getColumnName() + " ");
			String sqlType = toSQLType(sc.getColumnType(), DBType);
			sb.append(sqlType + " ");
			if ((sc.getColumnType() == 1)
					|| (sqlType.equalsIgnoreCase("NUMBER"))
					|| (sqlType.equalsIgnoreCase("DECIMAL"))) {
				if ((sc.getLength() == 0) && (sc.getColumnType() == 1)) {
					throw new RuntimeException("必须为varchar类型设定长度，请检查字段"
							+ sc.getColumnName() + "的设置");
				}
				sb.append(getFieldExtDesc(sc.getLength(), sc.getPrecision()));
			}
			if (sc.isMandatory()) {
				sb.append("not null");
			}
			if (sc.isPrimaryKey()) {
				if (ksb.length() == 0)
					ksb.append("\tconstraint PK_" + tableCode
							+ " primary key (");
				else {
					ksb.append(",");
				}
				ksb.append(sc.getColumnName());
			}
		}
		if (ksb.length() != 0) {
			ksb.append(")");
			sb.append(",\n" + ksb);
		}
		sb.append("\n)");
		return sb.toString();
	}

	public static String createTableDB2(SchemaColumn[] scs, String tableCode,
			DBTypes DBType) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("create table " + tableCode + "(\n");
		StringBuffer ksb = new StringBuffer();
		for (int i = 0; i < scs.length; i++) {
			SchemaColumn sc = scs[i];
			if (i != 0) {
				sb.append(",\n");
			}
			sb.append("\t" + sc.getColumnName() + " ");
			String sqlType = toSQLType(sc.getColumnType(), DBType);
			sb.append(sqlType + " ");
			if ((sc.getColumnType() == 1)
					|| (sqlType.equalsIgnoreCase("NUMERIC"))
					|| (sqlType.equalsIgnoreCase("DECIMAL"))) {
				if ((sc.getLength() == 0) && (sc.getColumnType() == 1)) {
					throw new RuntimeException("必须为varchar2类型设定长度，请检查字段"
							+ sc.getColumnName() + "的设置");
				}
				sb.append(getFieldExtDesc(sc.getLength(), sc.getPrecision()));
			}
			if (sc.isMandatory()) {
				sb.append("not null");
			}
			if (sc.isPrimaryKey()) {
				if (ksb.length() == 0) {
					String pkName = tableCode;
					if (pkName.length() > 15) {
						pkName = pkName.substring(0, 15);
					}
					ksb.append("\tconstraint PK_" + pkName + " primary key (");
				} else {
					ksb.append(",");
				}
				ksb.append(sc.getColumnName());
			}
		}
		if (ksb.length() != 0) {
			ksb.append(")");
			sb.append(",\n" + ksb);
		}
		sb.append("\n)");
		return sb.toString();
	}

	public static String createTableMYSQL(SchemaColumn[] scs, String tableCode,
			DBTypes DBType) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("create table " + tableCode + "\n(\n");
		StringBuffer ksb = new StringBuffer();
		for (int i = 0; i < scs.length; i++) {
			SchemaColumn sc = scs[i];
			if (i != 0) {
				sb.append(",\n");
			}
			sb.append("\t" + sc.getColumnName() + " ");
			String sqlType = toSQLType(sc.getColumnType(), DBType);
			sb.append(sqlType + " ");
			if ((sc.getLength() == 0) && (sc.getColumnType() == 1)) {
				throw new RuntimeException("必须为varchar类型设定长度，请检查字段"
						+ sc.getColumnName() + "的设置");
			}
			sb.append(getFieldExtDesc(sc.getLength(), sc.getPrecision()));
			if (sc.isMandatory()) {
				sb.append("not null");
			}
			if (sc.isPrimaryKey()) {
				if (ksb.length() == 0)
					ksb.append("\tprimary key (");
				else {
					ksb.append(",");
				}
				ksb.append(sc.getColumnName());
			}
		}
		if (ksb.length() != 0) {
			ksb.append(")");
			sb.append(",\n" + ksb);
		}
		sb.append("\n)");
		// sb.append("\n) engine=InnoDB default charset="
		// + Constant.GlobalCharset.replaceAll("\\-", "").toLowerCase());
		return sb.toString();
	}

	public void dropTable(String tableCode) {
		String sql = dropTable(tableCode, this.dbType);
		this.list.add(sql);
	}

	public static String dropTable(String tableCode, DBTypes dbType) {
		String dropSQL = null;
		if ((dbType == DBTypes.MSSQL) || (dbType == DBTypes.SYBASE)) {
			dropSQL = "if exists (select 1 from  sysobjects where id = object_id('"
					+ tableCode + "') and type='U') drop table " + tableCode;
		}
		if (dbType == DBTypes.ORACLE) {
			dropSQL = "drop table " + tableCode + " cascade constraints";
		}
		if (dbType == DBTypes.DB2) {
			dropSQL = "drop table " + tableCode;
		}
		if (dbType == DBTypes.MYSQL) {
			dropSQL = "drop table if exists " + tableCode;
		}
		return dropSQL;
	}

	public static String getFieldExtDesc(int length, int precision) {
		if (length != 0) {
			StringBuffer sb = new StringBuffer();
			sb.append("(");
			sb.append(length);
			if (precision != 0) {
				sb.append(",");
				sb.append(precision);
			}
			sb.append(") ");
			return sb.toString();
		}
		return "";
	}
}
