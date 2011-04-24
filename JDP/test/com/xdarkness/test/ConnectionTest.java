package com.xdarkness.test;

import java.sql.SQLException;

import com.xdarkness.framework.connection.XConnectionPoolManager;


public class ConnectionTest {

	@Test
	public void getConnection() throws SQLException {
		for(int i=0; i<10000; i++){
	    	com.xdarkness.framework.connection.XConnection conn = XConnectionPoolManager.getConnection();
	    	System.out.println(i + "==="+conn);
	    	conn.close();
	    }
	}
	
}
