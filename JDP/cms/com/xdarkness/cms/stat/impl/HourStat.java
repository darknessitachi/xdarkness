package com.xdarkness.cms.stat.impl;

import java.util.Date;

import com.xdarkness.cms.stat.AbstractStat;
import com.xdarkness.cms.stat.Visit;
import com.xdarkness.cms.stat.VisitCount;

public class HourStat extends AbstractStat {
	private static final String[] avgSubTypes = { "StickTime" };
	private static final String Type = "Hour";

	public String getStatType() {
		return "Hour";
	}

	public String[] getAverageSubTypes() {
		return avgSubTypes;
	}

	public void deal(Visit v) {
		String h = DateUtil.toString(new Date(v.VisitTime), "HH");
		if ("Unload".equals(v.Event)) {
			VisitCount.getInstance().addAverage(v.SiteID, "Hour", "StickTime",
					h, v.StickTime);
		} else {
			VisitCount.getInstance().add(v.SiteID, "Hour", "PV", h);
			if (v.UVFlag) {
				VisitCount.getInstance().add(v.SiteID, "Hour", "UV", h);
				if (v.RVFlag)
					VisitCount.getInstance().add(v.SiteID, "Hour",
							"NewVisitor", h);
				else {
					VisitCount.getInstance().add(v.SiteID, "Hour",
							"ReturnVisitor", h);
				}
			}
			if (v.IPFlag)
				VisitCount.getInstance().add(v.SiteID, "Hour", "IP", h);
		}
	}
}

/*
 * com.xdarkness.cms.stat.impl.HourStat JD-Core Version: 0.6.0
 */