package com.xdarkness.cms.datachannel;

import java.sql.SQLException;
import java.util.Date;

import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.platform.pub.ConfigEanbleTaskManager;
import com.xdarkness.schema.ZCArticleSchema;
import com.xdarkness.schema.ZCArticleSet;
import com.xdarkness.schema.ZCAttachmentSchema;
import com.xdarkness.schema.ZCAttachmentSet;
import com.xdarkness.schema.ZCAudioSchema;
import com.xdarkness.schema.ZCAudioSet;
import com.xdarkness.schema.ZCCatalogConfigSchema;
import com.xdarkness.schema.ZCCatalogSchema;
import com.xdarkness.schema.ZCImageSchema;
import com.xdarkness.schema.ZCImageSet;
import com.xdarkness.schema.ZCVideoSchema;
import com.xdarkness.schema.ZCVideoSet;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;

public class PublishTaskManager extends ConfigEanbleTaskManager {
	public void execute(long id) {
		LogUtil.getLogger().info("PublishTaskManager开始发布内容");

		ZCCatalogConfigSchema config = new ZCCatalogConfigSchema();
		config.setID(id);
		if (config.fill()) {
			ZCCatalogSchema catalog = new ZCCatalogSchema();
			catalog.setID(config.getCatalogID());
			if (!catalog.fill()) {
				return;
			}
			Publisher p = new Publisher();
			if (catalog.getType() == 1L) {
				ZCArticleSet set = new ZCArticleSet();
				if (XString.isNotEmpty(config.getCatalogID() + ""))
					set = new ZCArticleSchema()
							.query(new QueryBuilder(
									" where status =20 and (publishdate<=? or publishdate is null) and CatalogInnerCode like ?",
									new Date(), CatalogUtil.getInnerCode(config
											.getCatalogID())
											+ "%"));
				else {
					set = new ZCArticleSchema()
							.query(new QueryBuilder(
									" where status =20 and (publishdate<=? or publishdate is null) and SiteID=?",
									new Date(), config.getSiteID()));
				}
				try {
					p.publishArticle(set);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (catalog.getType() == 5L) {
				ZCVideoSet set = new ZCVideoSet();
				System.out.println(CatalogUtil.getInnerCode(config
						.getCatalogID()));
				if (XString.isNotEmpty(config.getCatalogID() + "")) {
					set = new ZCVideoSchema()
							.query(new QueryBuilder(
									" where status =20 and (publishdate<=? or publishdate is null) and CatalogInnerCode like ?",
									new Date(), CatalogUtil.getInnerCode(config
											.getCatalogID())
											+ "%"));
				} else {
					set = new ZCVideoSchema()
							.query(new QueryBuilder(
									" where status =20 and (publishdate<=? or publishdate is null) and SiteID=?",
									new Date(), config.getSiteID()));
				}
				p.publishDocs("video", set, true, null);
			} else if (catalog.getType() == 4L) {
				ZCImageSet set = new ZCImageSet();
				if (XString.isNotEmpty(config.getCatalogID() + ""))
					set = new ZCImageSchema()
							.query(new QueryBuilder(
									" where status =20 and (publishdate<=? or publishdate is null) and CatalogInnerCode like ?",
									new Date(), CatalogUtil.getInnerCode(config
											.getCatalogID())
											+ "%"));
				else {
					set = new ZCImageSchema()
							.query(new QueryBuilder(
									" where status =20 and (publishdate<=? or publishdate is null) and SiteID=?",
									new Date(), config.getSiteID()));
				}
				p.publishDocs("image", set, true, null);
			} else if (catalog.getType() == 6L) {
				ZCAudioSet set = new ZCAudioSet();
				if (XString.isNotEmpty(config.getCatalogID() + "")) {
					set = new ZCAudioSchema()
							.query(new QueryBuilder(
									" where status =20 and (publishdate<=? or publishdate is null) and CatalogInnerCode like ?",
									new Date(), CatalogUtil.getInnerCode(config
											.getCatalogID())
											+ "%"));
					System.out.println(set.size());
				} else {
					set = new ZCAudioSchema()
							.query(new QueryBuilder(
									" where status =20 and (publishdate<=? or publishdate is null) and SiteID=?",
									new Date(), config.getSiteID()));
				}
				p.publishDocs("audio", set, true, null);
			} else if (catalog.getType() == 7L) {
				ZCAttachmentSet set = new ZCAttachmentSet();
				if (XString.isNotEmpty(config.getCatalogID() + "")) {
					set = new ZCAttachmentSchema()
							.query(new QueryBuilder(
									" where status =20 and (publishdate<=? or publishdate is null) and CatalogInnerCode like ?",
									new Date(), CatalogUtil.getInnerCode(config
											.getCatalogID())
											+ "%"));
					System.out.println(set.size());
				} else {
					set = new ZCAttachmentSchema()
							.query(new QueryBuilder(
									" where status =20 and (publishdate<=? or publishdate is null) and SiteID=?",
									new Date(), config.getSiteID()));
				}
				p.publishDocs("attachment", set, true, null);
			}
		}

		LogUtil.getLogger().info("PublishTaskManager结束发布内容");
	}

	public String getCode() {
		return "Publisher";
	}

	public String getName() {
		return "内容发布任务";
	}

	public Mapx getConfigEnableTasks() {
		Mapx map = new Mapx();
		map.put("-1", "发布全部文档");
		map.put("0", "全局区块发布");
		return map;
	}

	public boolean isRunning(long arg0) {
		return false;
	}
}

/*
 * com.xdarkness.cms.datachannel.PublishTaskManager JD-Core Version: 0.6.0
 */