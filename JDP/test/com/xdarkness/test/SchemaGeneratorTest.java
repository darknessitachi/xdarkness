package com.xdarkness.test;

import org.junit.Test;

import com.xdarkness.framework.orm.SchemaGenerator;


public class SchemaGeneratorTest {

	@Test
	public void generate(){
		SchemaGenerator.execute("com.xdarkness.schema");
	}
}
