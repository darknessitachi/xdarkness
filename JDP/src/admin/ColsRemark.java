package admin;

import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.sql.QueryBuilder;

public class ColsRemark extends Page {
	
	public static void dg1DataBind(DataGridAction dga) {
		dga.bindData(new QueryBuilder(
						"SELECT * FROM colsremark c WHERE c.tablename='category'")
						.executeDataTable());
	}
}
