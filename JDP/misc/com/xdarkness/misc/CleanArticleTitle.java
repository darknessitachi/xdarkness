package com.xdarkness.misc;

import java.sql.SQLException;

import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;

public class CleanArticleTitle {
	public static void main(String[] args) throws SQLException {
		DataTable dt = new QueryBuilder("select id,title from ZCArticle")
				.executeDataTable();
		QueryBuilder qb = new QueryBuilder(
				"update ZCArticle set Title=? where id=?");
		qb.setBatchMode(true);
		for (int i = 0; i < dt.getRowCount(); i++) {
			String id = dt.getString(i, 0);
			String title = dt.getString(i, 1);
			title = XString.htmlDecode(title);
			title = title.replaceAll("\\<.*?\\>", "");
			qb.add(title);
			qb.add(id);
			qb.addBatch();
		}
		qb.executeNoQuery();
	}
}

/*
 * com.xdarkness.misc.CleanArticleTitle JD-Core Version: 0.6.0
 */