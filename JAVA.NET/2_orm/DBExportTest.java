

import org.junit.Test;

import com.abigdreamer.java.net.orm.DBExport;
import com.abigdreamer.java.net.orm.DBImport;

public class DBExportTest {

	@Test
	public void export(){
		new DBExport().exportDB("D:/test2.db");
	}
	
	@Test
	public void importDB(){
		new DBImport().importDB("D:/test2.db");
	}
}
