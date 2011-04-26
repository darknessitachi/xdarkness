package com.xdarkness.cms.datachannel;

import com.xdarkness.schema.ZCArticleSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.LogUtil;

public class ArticleArchiveTask extends GeneralTask {
	public static void main(String[] args) {
		ArticleArchiveTask t = new ArticleArchiveTask();
		t.execute();
	}

	public void execute() {
		LogUtil.getLogger().info("定时归档任务");
		Transaction trans = new Transaction();
		int size = 500;
		int count = new QueryBuilder(
				"select count(*) from ZCArticle where ArchiveDate<=?", DateUtil
						.getCurrentDateTime()).executeInt();
		int page = count / size;
		if (count % size == 0) {
			page--;
		}
		DataTable articleDt = new DataTable();
		for (int i = 0; i < page; i++) {
			articleDt = new QueryBuilder(
					"select * from ZCArticle where ArchiveDate<=?", DateUtil
							.getCurrentDateTime()).executePagedDataTable(size,
					page);
			for (int j = 0; j < articleDt.getRowCount(); j++) {
				ZCArticleSchema article = new ZCArticleSchema();
				article.setID(articleDt.getString(j, "ID"));
				article.fill();
				article.setStatus(50L);
				trans.add(article, OperateType.DELETE_AND_BACKUP);
			}

			trans.setBackupMemo("Archive");
			trans.commit();
			trans.clear();
		}
		LogUtil.getLogger().info("扫描定时归档任务结束");
	}

	public long getID() {
		return 200912081905L;
	}

	public String getName() {
		return "对文章进行归档";
	}
}

/*
 * com.xdarkness.cms.datachannel.ArticleArchiveTask JD-Core Version: 0.6.0
 */