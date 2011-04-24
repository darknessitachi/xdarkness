package com.xdarkness.cms.stat;

import java.util.Date;

import com.xdarkness.cms.stat.impl.GlobalStat;

public class StatUpdateTask extends GeneralTask {
	private long LastUpdateTime = System.currentTimeMillis();

	public void execute() {
		long current = System.currentTimeMillis();
		VisitHandler.init(current);
		GlobalStat.dealVisitHistory(current);
		if (!DateUtil.toString(new Date(current)).equals(
				DateUtil.toString(new Date(this.LastUpdateTime))))
			VisitHandler.changePeriod(1, current);
		else {
			VisitHandler.update(System.currentTimeMillis(), false, false);
		}
		this.LastUpdateTime = current;
	}

	public long getID() {
		return 200812191853L;
	}

	public String getName() {
		return "定时更新CMS统计信息";
	}
}

/*
 * com.xdarkness.cms.stat.StatUpdateTask JD-Core Version: 0.6.0
 */