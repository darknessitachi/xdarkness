package com.xdarkness.framework.database;

import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;

public class MySqlHelper {

	public static void main(String[] args) {
		DataTable dataTable = new QueryBuilder("show tables")
				.executeDataTable();
		System.out.println(dataTable);
	}
}
