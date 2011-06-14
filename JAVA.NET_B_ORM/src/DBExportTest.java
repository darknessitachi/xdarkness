
import org.junit.Test;

import com.abigdreamer.java.net.orm.DBExport;
import com.abigdreamer.java.net.orm.DBImport;

public class DBExportTest {

	@Test
	public void export() {
		new DBExport().exportDB("C:/test2.db");
	}

	 @Test
	 public void importDB(){
		 new DBImport().importDB("C:/test2.db");
//		 new DBImport().importDB("C:/Projects/UseOff/WebRoot/WEB-INF/data/backup/DB_20101116172820.dat");
	 }
}
