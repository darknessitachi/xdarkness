

import java.io.File;

import org.junit.Test;

import com.abigdreamer.java.net.Config;
import com.abigdreamer.java.net.connection.XConnectionPoolManager;
import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.TableCreator;

public class TableCreateTest {

	@Test
	public void create() throws Exception {
		TableCreator tableCreator = new TableCreator(XConnectionPoolManager
				.getDBConnConfig().DBType);

		File file = new File(Config.getClassesPath() + "com/abigdreamer/schema");
		File[] schemas = file.listFiles();
		for (File schemaFile : schemas) {
			String fileName = schemaFile.getName().replace(".class", "");
			if (fileName.endsWith("Schema")) {
				Class<?> clazz = Class.forName("com.abigdreamer.schema." + fileName);
				Schema schema = (Schema) clazz.newInstance();
				tableCreator.createTable(schema.getColumns(), schema
						.getTableCode(), true);
			}
		}

		tableCreator.executeAndClear();

	}

}
