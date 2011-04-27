package com.xdarkness.test;

import org.junit.Test;

import com.abigdreamer.java.net.orm.SchemaGenerator;


public class SchemaGeneratorTest {

	@Test
	public void generate(){
		SchemaGenerator.execute("com.xdarkness.schema");
	}
}
