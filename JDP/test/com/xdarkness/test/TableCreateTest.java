package com.xdarkness.test;

import java.io.File;

import com.xdarkness.framework.connection.XConnectionPoolManager;
import com.xdarkness.framework.orm.TableCreator;

public class TableCreateTest {

	@Test
	public void create() throws Exception {
		TableCreator tableCreator = new TableCreator(XConnectionPoolManager
				.getDBConnConfig().DBType);

		File file = new File(Config.getClassesPath() + "com/sky/schema");
		File[] schemas = file.listFiles();
		for (File schemaFile : schemas) {
			String fileName = schemaFile.getName().replace(".class", "");
			if (fileName.endsWith("Schema")) {
				Class<?> clazz = Class.forName("com.xdarkness.schema." + fileName);
				Schema schema = (Schema) clazz.newInstance();
				tableCreator.createTable(schema.getColumns(), schema
						.getTableCode(), true);
			}
		}

		tableCreator.executeAndClear();

	}

}
