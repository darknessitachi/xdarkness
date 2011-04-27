package com.abigdreamer.java.net.connection;

import java.util.ArrayList;
import java.util.List;

public class DBTypes {

	private String dbType;
	
	private DBTypes() {
	}
	
	private DBTypes(String dbType) {
		this.dbType = dbType;
	}
	
	public String toString() {
		return this.dbType;
	}
	
	public static DBTypes getDBType(String dbType) {
		for (int i = 0; i < DBTypes.size(); i++) {
			DBTypes type = DBTypes.get(i);
			if(type.toString().equals(dbType)) {
				return type;
			}
		}
		
		DBTypes type = new DBTypes(dbType);
		DBTypes.add(type);
		return type;
	}
	public static final DBTypes DB2 = new DBTypes("DB2");
	public static final DBTypes MYSQL = new DBTypes("MYSQL");
	public static final DBTypes MSSQL = new DBTypes("MSSQL");
	public static final DBTypes MSSQL2000 = new DBTypes("MSSQL2000");
	public static final DBTypes SYBASE = new DBTypes("SYBASE");
	public static final DBTypes INFORMIX = new DBTypes("INFORMIX");
	public static final DBTypes ORACLE = new DBTypes("ORACLE");
	public static List<DBTypes> DBTypes = new ArrayList<DBTypes>();
	
	static {
		DBTypes.add(DB2);
		DBTypes.add(MYSQL);
		DBTypes.add(MSSQL);
		DBTypes.add(MSSQL2000);
		DBTypes.add(SYBASE);
		DBTypes.add(INFORMIX);
		DBTypes.add(ORACLE);
	}
	
}
