package com.xdarkness.test;

import java.net.URL;

public class ClassLoaderTest {

	public void test() {
		URL url = ClassLoaderTest.class.getClassLoader().getResource("com/xdarkness/test/ClassLoaderTest.class");
        url = ClassLoaderTest.class.getClassLoader().getResource("/log4j.config");
        url = ClassLoaderTest.class.getResource("ClassLoaderTest.class");
        url = ClassLoaderTest.class.getResource("/com/xdarkness/test/ClassLoaderTest.class");
	}
}
