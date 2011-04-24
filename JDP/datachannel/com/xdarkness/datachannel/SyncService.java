package com.xdarkness.datachannel;

import java.util.Date;

import com.xdarkness.cms.pub.CMSCache;
import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.schema.ZCCatalogConfigSchema;
import com.xdarkness.framework.jaf.Ajax;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;

public class SyncService extends Ajax {
	public void sendData() {
		String CatalogID = $V("CatalogID");
		String Password = $V("Password");
		String LastAdded = $V("LastAdded");
		String LastModified = $V("LastModified");
		ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(CatalogID);
		if ("Y".equals(config.getAllowInnerGather())) {
			if ((XString.isNotEmpty(config.getInnerGatherPassword()))
					&& (!config.getInnerGatherPassword().equals(Password))) {
				$S("Error", "采集密钥不正确!");
				return;
			}
		} else {
			$S("Error", "远程栏目不允许采集!");
		}
		String InnerCode = CatalogUtil.getInnerCode(CatalogID);

		QueryBuilder qb = new QueryBuilder(
				"select * from ZCArticle where Status=? and CatalogInnerCode like ? and (AddTime>? or ModifyTime>?) order by id");
		qb.add(30);
		qb.add(InnerCode + "%");
		qb.add(new Date(Long.parseLong(LastAdded)));
		qb.add(new Date(Long.parseLong(LastModified)));
		DataTable dt = qb.executePagedDataTable(100, 0);
		$S("Data", dt);

		qb = new QueryBuilder(
				"select * from ZCArticle where Status=? and CatalogInnerCode like ? and (AddTime>? or ModifyTime>?) order by id");
		qb.add(30);
		qb.add(InnerCode + "%");
		qb.add(new Date(Long.parseLong(LastAdded)));
		qb.add(new Date(Long.parseLong(LastModified)));
		dt = qb.executePagedDataTable(100, 0);

		$S("Data", dt);
	}
}

/*
 * com.xdarkness.datachannel.SyncService JD-Core Version: 0.6.0
 */