package com.xdarkness.cms.dataservice;

import java.util.Date;

import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.util.Mapx;

public class PublishColumn extends Page {
	public static Mapx initSearch(Mapx params) {
		String dateStr = DateUtil.toString(new Date(), "yyyy-MM-dd");
		Mapx map = new Mapx();
		map.put("today", dateStr);
		return map;
	}
}

/*
 * com.xdarkness.cms.dataservice.PublishColumn JD-Core Version: 0.6.0
 */