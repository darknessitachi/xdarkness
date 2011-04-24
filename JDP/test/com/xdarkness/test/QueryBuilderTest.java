package com.xdarkness.test;

import com.xdarkness.framework.sql.QueryBuilder;

public class QueryBuilderTest {

	@Test
	public void queryDatatable(){
		QueryBuilder query = new QueryBuilder("SELECT * FROM Test");
		assertTrue(120==query.setPagedQuery(false).setPageIndex(1).setPageSize(20).executeDataTable().getRowCount());
	}
	
	@Test
	public void queryPagedDatatable(){
		QueryBuilder query = new QueryBuilder("SELECT * FROM Test");
		assertTrue(20==query.setPagedQuery(true).setPageIndex(1).setPageSize(20).executeDataTable().getRowCount());
	}
	
}
