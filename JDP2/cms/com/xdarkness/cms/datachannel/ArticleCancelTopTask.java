package com.xdarkness.cms.datachannel;

import java.sql.SQLException;
import java.util.Date;

import com.xdarkness.schema.ZCArticleSchema;
import com.xdarkness.schema.ZCArticleSet;
import com.xdarkness.framework.sql.QueryBuilder;

public class ArticleCancelTopTask extends GeneralTask {
	public void execute() {
		QueryBuilder qb = new QueryBuilder(
				"update ZCArticle set TopFlag='0' where topflag='1' and topdate is not null and topdate<=?",
				new Date());
		try {
			qb.executeNoQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ZCArticleSchema article = new ZCArticleSchema();
		ZCArticleSet set = article.query(new QueryBuilder(
				"where topflag='1' and topdate is not null and topdate<=?",
				new Date()));
		if ((set != null) && (set.size() > 0)) {
			Publisher p = new Publisher();
			try {
				p.publishArticle(set);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public long getID() {
		return 200912091105L;
	}

	public String getName() {
		return "过期的置顶文章取消置顶";
	}
}

/*
 * com.xdarkness.cms.datachannel.ArticleCancelTopTask JD-Core Version: 0.6.0
 */