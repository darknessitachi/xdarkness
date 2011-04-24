package com.xdarkness.cms.datachannel;

import java.util.Date;

import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCDeployConfigSchema;
import com.xdarkness.schema.ZCDeployConfigSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class DeployConfig extends Page {
	public static Mapx init(Mapx params) {
		return null;
	}

	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder(
				"select * from ZCDeployConfig where siteid=?", ApplicationPage
						.getCurrentSiteID());
		qb.append(dga.getSortString());
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dt.decodeColumn("Method", CacheManager.getMapx("Code", "DeployMethod"));
		dga.bindData(dt);
	}

	public void add() {
		ZCDeployConfigSchema deployConfig = new ZCDeployConfigSchema();
		deployConfig.setID(NoUtil.getMaxID("DeployConfigID"));
		deployConfig.setSiteID(ApplicationPage.getCurrentSiteID());

		String sourceDir = $V("SourceDir");
		sourceDir = sourceDir.replace('\\', '/');
		deployConfig.setSourceDir(sourceDir);

		String target = $V("TargetDir");
		target = target.replace('\\', '/');
		deployConfig.setTargetDir(target);
		deployConfig.setMethod($V("Method"));
		deployConfig.setHost($V("Host"));
		String port = $V("Port");
		if ((port != null) && (!"".equals(port))) {
			deployConfig.setPort(Integer.parseInt(port));
		}
		deployConfig.setUserName($V("UserName"));
		deployConfig.setPassword($V("Password"));
		String beginDate = $V("BeginDate");
		String beginTime = $V("BeginTime");

		if ((!"".equals(beginDate)) && (!"null".equals(beginDate))
				&& (beginDate != null)) {
			deployConfig.setBeginTime(DateUtil.parseDateTime(beginDate + " "
					+ beginTime));
		}

		deployConfig.setUseFlag(Integer.parseInt($V("UseFlag")));
		deployConfig.setAddTime(new Date());
		deployConfig.setAddUser(User.getUserName());
		if (deployConfig.insert()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("发生错误!");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		String tsql = "where id in (" + ids + ")";
		ZCDeployConfigSchema ZCDeployConfig = new ZCDeployConfigSchema();
		ZCDeployConfigSet set = ZCDeployConfig.query(new QueryBuilder(tsql));

		Transaction trans = new Transaction();
		trans.add(set, OperateType.DELETE_AND_BACKUP);

		trans.add(new QueryBuilder(
				"delete from ZCDeployJob where configID in (" + ids + ")"));
		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
		}
	}

	public void edit() {
		ZCDeployConfigSchema deployConfig = new ZCDeployConfigSchema();
		deployConfig.setID(Long.parseLong($V("ID")));
		if (!deployConfig.fill()) {
			this.response.setStatus(0);
			this.response.setMessage("没有对应的定时任务!");
		}
		deployConfig.setSourceDir($V("SourceDir"));
		deployConfig.setTargetDir($V("TargetDir"));
		deployConfig.setMethod($V("Method"));
		deployConfig.setHost($V("Host"));
		String port = $V("Port");
		if ((port != null) && (!"".equals(port))) {
			deployConfig.setPort(Integer.parseInt(port));
		}
		deployConfig.setUserName($V("UserName"));
		deployConfig.setPassword($V("Password"));
		String beginDate = $V("BeginDate");
		String beginTime = $V("BeginTime");

		if ((!"".equals(beginDate)) && (!"null".equals(beginDate))
				&& (beginDate != null)) {
			deployConfig.setBeginTime(DateUtil.parseDateTime(beginDate + " "
					+ beginTime));
		}
		deployConfig.setUseFlag(Integer.parseInt($V("UseFlag")));
		deployConfig.setModifyTime(new Date());
		deployConfig.setModifyUser(User.getUserName());

		Transaction trans = new Transaction();
		trans.add(deployConfig, OperateType.UPDATE);
		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("发生错误!");
		}
	}

	public void deploy() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		String[] idArr = ids.split(",");
		Deploy deploy = new Deploy();
		for (int i = 0; i < idArr.length; i++) {
			long configID = Long.parseLong(idArr[i]);
			deploy.addOneJob(configID, true);
		}
	}
}

/*
 * com.xdarkness.cms.datachannel.DeployConfig JD-Core Version: 0.6.0
 */