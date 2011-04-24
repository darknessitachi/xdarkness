package com.xdarkness.test;

import com.xdarkness.framework.orm.DBExport;
import com.xdarkness.framework.orm.DBImport;

public class DBExportTest {

	@Test
	public void export(){
		new DBExport().exportDB("D:/test2.db");
	}
	
	@Test
	public void importDB(){
		new DBImport().importDB("D:/test2.db");
	}
}
