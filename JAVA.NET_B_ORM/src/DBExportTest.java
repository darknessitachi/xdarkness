
import org.junit.Test;

import com.abigdreamer.java.net.orm.DBImport;

public class DBExportTest {

//	@Test
//	public void export() {
//		new DBExport().exportDB("C:/test2.db");
//	}

	 @Test
	 public void importDB(){
		 new DBImport().importDB("C:/test2.db");
	 }
}
