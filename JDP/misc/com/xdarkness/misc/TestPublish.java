package com.xdarkness.misc;

import java.sql.SQLException;

import com.xdarkness.cms.datachannel.Publisher;
import com.xdarkness.schema.ZCArticleSchema;
import com.xdarkness.schema.ZCArticleSet;
import com.xdarkness.framework.sql.QueryBuilder;

public class TestPublish {
	public static void main(String[] args) throws SQLException {
		long t = System.currentTimeMillis();
		User.UserData u = new User.UserData();
		u.setUserName("admin");
		u.setLogin(true);
		u.setManager(true);
		User.setCurrent(u);
		ZCArticleSet set = new ZCArticleSchema().query(new QueryBuilder(
				"where id=?", 10193));
		Publisher p = new Publisher();
		p.publishArticle(set);
		System.out.println(System.currentTimeMillis() - t);
	}
}
