package com.xdarkness.cms.document;

import java.util.Date;

import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCArticleLogSchema;
import com.xdarkness.schema.ZCArticleLogSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class ArticleNote extends Page {
	public static Mapx init(Mapx params) {
		if ((XString.isNotEmpty(params.getString("ID")))
				&& (XString.isDigit(params.getString("ID")))) {
			ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
			articleLog.setID(params.getString("ID"));
			articleLog.fill();
			params.putAll(articleLog.toCaseIgnoreMapx());
		}
		return params;
	}

	public static void noteDataBind(DataGridAction dga) {
		String articleID = dga.getParam("ArticleID");
		QueryBuilder qb = new QueryBuilder(
				"select * from ZCArticlelog where action='NOTE' and articleID=? order by ID desc",
				articleID);
		dga.bindData(qb);
	}

	public static void logDataBind(DataGridAction dga) {
		String articleID = (String) dga.getParams().get("ArticleID");
		String userName = (String) dga.getParams().get("UserName");
		String startDate = (String) dga.getParams().get("startDate");
		String endDate = (String) dga.getParams().get("endDate");
		QueryBuilder qb = new QueryBuilder(
				"select * from ZCArticlelog where action<>'NOTE' and articleID=? order by addtime",
				articleID);
		if (XString.isNotEmpty(userName)) {
			qb.append(" and AddUser=?", userName);
		}
		if (XString.isNotEmpty(startDate)) {
			qb.append(" and AddTime>=?", startDate);
		}
		if (XString.isNotEmpty(endDate)) {
			qb.append(" and AddTime<?", DateUtil.addDay(
					DateUtil.parse(endDate), 1));
		}
		dga.bindData(qb);
	}

	public void del() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCArticleLogSchema log = new ZCArticleLogSchema();
		ZCArticleLogSet set = log.query(new QueryBuilder("where id in (" + ids
				+ ")"));
		trans.add(set, OperateType.DELETE_AND_BACKUP);

		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
		}
	}

	public void add() {
		Transaction trans = new Transaction();
		if ((XString.isNotEmpty($V("Content")))
				&& ($V("Content").length() > 400)) {
			this.response.setStatus(0);
			this.response.setMessage("批注内容不能超过400个字!");
			return;
		}

		add(trans, $V("ArticleID"), $V("Content"));
		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
		}
	}

	public static void add(Transaction trans, String articleID, String content) {
		long id = Long.parseLong(articleID);

		ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
		articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
		articleLog.setArticleID(id);
		articleLog.setAction("NOTE");
		articleLog.setActionDetail(content);
		articleLog.setAddUser(User.getUserName());
		articleLog.setAddTime(new Date());
		trans.add(articleLog, OperateType.INSERT);
	}
}

/*
 * com.xdarkness.cms.document.ArticleNote JD-Core Version: 0.6.0
 */