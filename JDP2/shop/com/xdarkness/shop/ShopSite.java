package com.xdarkness.shop;

import com.xdarkness.cms.datachannel.Publisher;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.schema.ZCSiteSchema;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.util.Mapx;

public class ShopSite extends Page {
	public static Mapx init(Mapx params) {
		ZCSiteSchema site = new ZCSiteSchema();
		site.setID(ApplicationPage.getCurrentSiteID());
		site.fill();
		return site.toMapx();
	}

	public void publishIndex() {
		Publisher p = new Publisher();
		if (p.publishIndex(ApplicationPage.getCurrentSiteID())) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("保存数据发生错误!");
		}
	}
}

/*
 * com.xdarkness.shop.ShopSite JD-Core Version: 0.6.0
 */