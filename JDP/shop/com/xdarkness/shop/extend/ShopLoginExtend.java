package com.xdarkness.shop.extend;

import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.member.Member;
import com.xdarkness.framework.extend.IExtendAction;

public class ShopLoginExtend implements IExtendAction {
	public String getTarget() {
		return "Member.Login";
	}

	public void execute(Object[] args) {
		Member member = (Member) args[0];
		String siteID = String.valueOf(member.getSiteID());
		SiteUtil.isShopEnable(siteID);
	}

	public String getName() {
		return "Shop会员登录逻辑扩展";
	}
}
