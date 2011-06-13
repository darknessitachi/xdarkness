package com.xdarkness.framework.database;

import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.sql.QueryBuilder;

public class MySqlHelper {

	public static void main(String[] args) {
		DataTable dataTable = new QueryBuilder("show tables")
				.executeDataTable();
		System.out.println(dataTable);
	}
}
