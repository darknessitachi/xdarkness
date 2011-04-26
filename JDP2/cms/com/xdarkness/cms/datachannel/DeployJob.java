package com.xdarkness.cms.datachannel;

import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.schema.ZCDeployJobSchema;
import com.xdarkness.schema.ZCDeployJobSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Errorx;
import com.xdarkness.framework.util.Mapx;

public class DeployJob extends Page {
	public static Mapx init(Mapx params) {
		return null;
	}

	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder(
				"select * from ZCDeployJob where siteid=? order by addtime desc",
				ApplicationPage.getCurrentSiteID());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dt.decodeColumn("status", Deploy.depolyStatus);
		dt.decodeColumn("Method", CacheManager.getMapx("Code", "DeployMethod"));
		dga.bindData(dt);
	}

	public static Mapx initDialog(Mapx params) {
		String sql = "select * from ZCDeployJob a where id=? ";
		DataTable dt = new QueryBuilder(sql, params.getString("ID"))
				.executeDataTable();
		dt.decodeColumn("status", Deploy.depolyStatus);
		dt.decodeColumn("Method", CacheManager.getMapx("Code", "DeployMethod"));
		if ((dt != null) && (dt.getRowCount() > 0)) {
			params.putAll(dt.get(0).toCaseIgnoreMapx());
		}
		return params;
	}

	public void del() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		String tsql = "where id in (" + ids + ")";
		ZCDeployJobSchema ZCDeployJob = new ZCDeployJobSchema();
		ZCDeployJobSet set = ZCDeployJob.query(new QueryBuilder(tsql));

		Transaction trans = new Transaction();
		trans.add(set, OperateType.DELETE);
		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
		}
	}

	public void delAll() {
		Transaction trans = new Transaction();
		trans.add(new QueryBuilder("delete from zcdeployjob where siteid=?",
				ApplicationPage.getCurrentSiteID()));
		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
		}
	}

	public void reExecuteJob() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		String tsql = "where id in (" + ids + ")";
		ZCDeployJobSchema ZCDeployJob = new ZCDeployJobSchema();
		ZCDeployJobSet set = ZCDeployJob.query(new QueryBuilder(tsql));
		Deploy helper = new Deploy();

		for (int i = 0; i < set.size(); i++) {
			helper.executeJob(set.get(i));
		}

		if (Errorx.hasError()) {
			this.response.setStatus(0);
			this.response.setMessage("分发错误。" + Errorx.printString());
		} else {
			this.response.setStatus(1);
		}
	}

	public void executeFailJob() {
		String tsql = "where status=? and siteid=?";
		ZCDeployJobSchema ZCDeployJob = new ZCDeployJobSchema();
		ZCDeployJobSet set = ZCDeployJob.query(new QueryBuilder(tsql, 3L,
				ApplicationPage.getCurrentSiteID()));
		Deploy helper = new Deploy();
		for (int i = 0; i < set.size(); i++) {
			helper.executeJob(set.get(i));
		}
		if (Errorx.hasError()) {
			this.response.setStatus(0);
			this.response.setMessage("分发错误。" + Errorx.printString());
		} else {
			this.response.setStatus(1);
		}
	}
}

/*
 * com.xdarkness.cms.datachannel.DeployJob JD-Core Version: 0.6.0
 */