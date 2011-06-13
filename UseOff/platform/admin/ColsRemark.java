package admin;

import com.abigdreamer.java.net.jaf.Page;
import com.abigdreamer.java.net.jaf.controls.grid.DataGridAction;
import com.abigdreamer.java.net.sql.QueryBuilder;

public class ColsRemark extends Page {
	
	public static void dg1DataBind(DataGridAction dga) {
		dga.bindData(new QueryBuilder(
						"SELECT * FROM colsremark c WHERE c.tablename='category'")
						.executeDataTable());
	}
}
