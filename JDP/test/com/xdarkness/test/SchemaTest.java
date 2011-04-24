package com.xdarkness.test;

import java.util.Date;

import schema.ZCCatalogSchema;

public class SchemaTest {

	@Test
	public void fill() {
		ZCCatalogSchema schema = new ZCCatalogSchema();
		schema.setID(8676);
		schema.fill();
		Assert.assertTrue(schema.getSiteID() == 206);
	}
	
	@Test
	public void insert() {
		ZCCatalogSchema schema = new ZCCatalogSchema();
		schema.setID(8676);
		schema.fill();
		schema.setID(8086);
		schema.insert();
		Assert.assertTrue(schema.getSiteID() == 206 && schema.getID() == 8086);
	}
	
	@Test
	public void update() {
		ZCCatalogSchema schema = new ZCCatalogSchema();
		schema.setID(8676);
		schema.fill();
		schema.setAddTime(new Date());
		schema.setName("未知幻影");
		schema.update();
		Assert.assertTrue(
				schema.getSiteID() == 206 
				&& schema.getID() == 8676
				&& schema.getName().equals("未知幻影"));
	}
	
	@Test
	public void querySet() {
		ZCCatalogSchema schema = new ZCCatalogSchema();
		int size = schema.query().size();
		Assert.assertTrue(size == 42);
	}
}
