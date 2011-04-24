package com.xdarkness.cms.workflow;

import java.sql.SQLException;

import com.xdarkness.cms.datachannel.Publisher;
import com.xdarkness.schema.ZCArticleSchema;
import com.xdarkness.schema.ZCArticleSet;
import com.xdarkness.workflow.Context;
import com.xdarkness.workflow.methods.NodeMethod;

public class PublishMethod extends NodeMethod {
	public void execute(Context context) {
		context.getTransaction().addExecutor(
				new Executor(context.getInstance().getDataID()) {
					public boolean execute() {
						ZCArticleSet set = new ZCArticleSet();
						ZCArticleSchema article = new ZCArticleSchema();
						article.setID(this.param.toString());
						article.fill();
						if ((article.getPublishDate() != null)
								&& (article.getPublishDate().getTime() > System
										.currentTimeMillis()))
							article.setStatus(20L);
						else {
							article.setStatus(30L);
						}
						set.add(article);
						Publisher p = new Publisher();
						try {
							p.publishArticle(set);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return true;
					}
				});
	}
}

/*
 * com.xdarkness.cms.workflow.PublishMethod JD-Core Version: 0.6.0
 */