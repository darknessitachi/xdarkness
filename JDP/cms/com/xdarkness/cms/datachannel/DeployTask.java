package com.xdarkness.cms.datachannel;

import java.sql.SQLException;
import java.util.Date;

import com.xdarkness.schema.ZCDeployConfigSchema;
import com.xdarkness.schema.ZCDeployConfigSet;
import com.xdarkness.schema.ZCDeployJobSchema;
import com.xdarkness.schema.ZCDeployJobSet;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.LogUtil;

public class DeployTask extends GeneralTask {
	public void execute() {
		ZCDeployConfigSet configSet = new ZCDeployConfigSchema().query();
		if (configSet.size() > 0) {
			for (int i = 0; i < configSet.size(); i++) {
				int count = new QueryBuilder(
						"select count(*) from ZCDeployJob where RetryCount<5 and status<>? and configID=?",
						2L, configSet.get(i).getID()).executeInt();
				int batchCount = count / 1000;
				for (int j = 0; j < batchCount + 1; j++) {
					ZCDeployJobSchema job = new ZCDeployJobSchema();
					ZCDeployJobSet jobs = job.query(new QueryBuilder(
							"where RetryCount<5 and status<>? and configID=?",
							2L, configSet.get(i).getID()), 1000, j);
					if ((jobs != null) && (jobs.size() > 0)) {
						LogUtil.getLogger().info("执行分发任务 任务数：" + jobs.size());
						Deploy d = new Deploy();
						d.executeBatchJob(configSet.get(i), jobs);
					}
				}
			}

		}

		Date yesterday = DateUtil.addHour(new Date(), -5);
		Date beforeYesterday = DateUtil.addHour(new Date(), -5);
		try {
			new QueryBuilder(
					"delete from ZCDeployJob where RetryCount>=5  and status=3 and addtime<=?",
					beforeYesterday).executeNoQuery();

			new QueryBuilder(
					"delete from ZCDeployJob where  status=2 and addtime<=?",
					yesterday).executeNoQuery();
			new QueryBuilder(
					"delete from ZCDeployLog where not exists (select id from ZCDeployJob where  ZCDeployLog.jobid= ZCDeployJob.id)")
					.executeNoQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public long getID() {
		return 200907241819L;
	}

	public String getName() {
		return "分发到目录定时任务";
	}
}

/*
 * com.xdarkness.cms.datachannel.DeployTask JD-Core Version: 0.6.0
 */