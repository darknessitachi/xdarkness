package com.xdarkness.cms.stat.impl;

import java.util.regex.Pattern;

import com.xdarkness.cms.stat.AbstractStat;
import com.xdarkness.cms.stat.Visit;
import com.xdarkness.cms.stat.VisitCount;

public class ClientStat extends AbstractStat {
	private static final String[] avgSubTypes = { "StickTime" };
	private static final String Type = "Client";
	Pattern srPattern = Pattern.compile("\\d{2,4}x\\d{2,4}", 34);

	public String getStatType() {
		return "Client";
	}

	public String[] getAverageSubTypes() {
		return avgSubTypes;
	}

	public void deal(Visit v) {
		if ("Unload".equals(v.Event)) {
			return;
		}
		if (XString.isEmpty(v.FlashVersion)) {
			v.FlashVersion = "其他";
		} else {
			v.FlashVersion = v.FlashVersion.replaceAll("(\\%20)+", " ");
			if (v.FlashVersion.indexOf(" ") > 0) {
				v.FlashVersion = v.FlashVersion.substring(0, v.FlashVersion
						.indexOf(" "));
			}
		}
		String cd = v.ColorDepth;
		if (XString.isEmpty(cd)) {
			cd = "其他";
		} else {
			cd = cd.replaceAll("\\D", "");
			if ((cd.equals("8")) || (cd.equals("16")) || (cd.equals("24"))
					|| (cd.equals("32")))
				cd = cd + "-bit";
			else {
				cd = "其他";
			}
		}
		v.ColorDepth = cd;

		String sr = v.Screen;
		if (XString.isNotEmpty(sr)) {
			if (sr.indexOf(",") > 0) {
				sr = sr.substring(0, sr.indexOf(","));
			}
			if (!this.srPattern.matcher(sr).matches())
				sr = "其他";
		} else {
			sr = "其他";
		}

		VisitCount.getInstance().add(v.SiteID, "Client", "ColorDepth",
				v.ColorDepth);
		VisitCount.getInstance().add(v.SiteID, "Client", "OS", v.OS);
		VisitCount.getInstance().add(v.SiteID, "Client", "Browser", v.Browser);
		VisitCount.getInstance()
				.add(v.SiteID, "Client", "Language", v.Language);
		VisitCount.getInstance().add(v.SiteID, "Client", "FlashVersion",
				v.FlashVersion);
		VisitCount.getInstance().add(v.SiteID, "Client", "JavaEnabled",
				String.valueOf(v.JavaEnabled));
		VisitCount.getInstance().add(v.SiteID, "Client", "CookieEnabled",
				String.valueOf(v.CookieEnabled));
		VisitCount.getInstance().add(v.SiteID, "Client", "Screen", sr);
	}
}

/*
 * com.xdarkness.cms.stat.impl.ClientStat JD-Core Version: 0.6.0
 */