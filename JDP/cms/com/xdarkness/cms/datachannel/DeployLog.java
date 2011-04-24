package com.xdarkness.cms.datachannel;

import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.schema.ZCDeployLogSchema;
import com.xdarkness.schema.ZCDeployLogSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class DeployLog extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		String sql1 = "select a.id,a.jobid,a.message,a.begintime,a.endtime,b.method,b.host,b.source,b.target,(select codename from zdcode where codetype='DeployMethod' and parentCode='DeployMethod' and codevalue=b.method) as methodDesc from ZCDeployLog a,ZCDeployJob b where  a.JobID=b.ID and b.SiteID=? order by a.begintime desc";

		QueryBuilder qb = new QueryBuilder(sql1, ApplicationPage.getCurrentSiteID());
		dga.bindData(qb);
	}

	public static Mapx initDialog(Mapx params) {
		String sql = "select a.id,a.jobid,a.message,a.begintime,a.endtime,b.method,b.host,b.source,b.target,(select codename from zdcode where codetype='DeployMethod' and parentCode='DeployMethod' and codevalue=b.method) as methodDesc from ZCDeployLog a,ZCDeployJob b where  a.JobID=b.ID and a.ID=?";

		DataTable dt = new QueryBuilder(sql, params.getString("ID"))
				.executeDataTable();
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
		ZCDeployLogSchema ZCDeployLog = new ZCDeployLogSchema();
		ZCDeployLogSet set = ZCDeployLog.query(new QueryBuilder(tsql));

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
		trans.add(new QueryBuilder("delete from ZCDeployLog where siteid=?",
				ApplicationPage.getCurrentSiteID()));
		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
		}
	}
}

/*
 * com.xdarkness.cms.datachannel.DeployLog JD-Core Version: 0.6.0
 */