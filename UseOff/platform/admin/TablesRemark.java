package admin;

import java.sql.SQLException;

import com.abigdreamer.java.net.jaf.Page;
import com.abigdreamer.java.net.jaf.controls.HtmlUtil;
import com.abigdreamer.java.net.orm.data.DataRow;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.Mapx;

public class TablesRemark extends Page {

	public static Mapx<String, Object> init(Mapx<String, Object> params) {
		String tablename = params.getString("tablename");
		DataRow dataRow = new QueryBuilder(
				"SELECT * FROM TablesRemark t WHERE t.tablename=?").add(
				tablename).executeDataTable().get(0);
		params.putAll(dataRow.toMapx());

		params.put("categoryid", HtmlUtil.dataTableToOptions(getTableType(),
				dataRow.getString("categoryid")));
		return params;
	}

	public static DataTable getTableType() {
		return (new QueryBuilder("select chinaname,refid from categoryvalue"))
				.executeDataTable();
	}

	public void update() {
		try {
			new QueryBuilder(
					"UPDATE TablesRemark t SET t.alias=?,t.categoryid=?,t.remark=? WHERE t.tablename=?")
					.add($V("alias")).add($V("categoryid")).add($V("remark")).add(
							$V("tablename")).executeNoQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		response.Message = "更新数据成功！";
	}
}