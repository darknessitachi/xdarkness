package com.xdarkness.bbs.admin;

import java.util.Date;

import com.xdarkness.bbs.ForumUtil;
import com.xdarkness.schema.ZCForumConfigSchema;
import com.xdarkness.schema.ZCForumConfigSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.Ajax;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;

public class ForumSetting extends Ajax {
	public static Mapx init(Mapx params) {
		long siteID = ForumUtil.getCurrentBBSSiteID();
		ZCForumConfigSet set = new ZCForumConfigSchema()
				.query(new QueryBuilder("where SiteID = ?", siteID));
		if (set.size() == 1) {
			params = set.get(0).toMapx();
		}
		DataTable dt = new QueryBuilder(
				"select TempCloseFlag from ZCForumConfig where SiteID = ?",
				siteID).executeDataTable();
		Mapx map = new Mapx();
		map.put("Y", "是");
		map.put("N", "否");
		params.put("TempCloseFlag", HtmlUtil.mapxToRadios("TempCloseFlag", map,
				dt.getRowCount() > 0 ? dt.getString(0, "TempCloseFlag") : ""));
		return params;
	}

	public void add() {
		Transaction trans = new Transaction();

		ZCForumConfigSchema config = new ZCForumConfigSchema();
		config.setID($V("ID"));
		config.fill();
		config.setSiteID(ForumUtil.getCurrentBBSSiteID());
		config.setValue(this.request);
		config.setAddTime(new Date());
		config.setAddUser(User.getUserName());
		trans.add(config, OperateType.DELETE_AND_INSERT);

		if (trans.commit()) {
			CacheManager.set("Forum", "Config", config.getID(), config);
			this.response.setLogInfo(1, "操作成功!");
		} else {
			this.response.setLogInfo(0, "操作失败!");
		}
	}
}

/*
 * com.xdarkness.bbs.admin.ForumSetting JD-Core Version: 0.6.0
 */