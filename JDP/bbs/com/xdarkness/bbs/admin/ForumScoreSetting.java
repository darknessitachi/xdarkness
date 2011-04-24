package com.xdarkness.bbs.admin;

import java.util.Date;

import com.xdarkness.bbs.ForumUtil;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCForumScoreSchema;
import com.xdarkness.schema.ZCForumScoreSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class ForumScoreSetting extends Page {
	public static Mapx init(Mapx params) {
		ZCForumScoreSet set = new ZCForumScoreSchema().query(new QueryBuilder(
				"where SiteID=?", ForumUtil.getCurrentBBSSiteID()));
		if (set.size() > 0) {
			params = set.get(0).toMapx();
		}
		return params;
	}

	public void save() {
		Transaction trans = new Transaction();
		ZCForumScoreSchema forumScore = new ZCForumScoreSchema();
		if (XString.isNotEmpty($V("ID"))) {
			forumScore.setID($V("ID"));
			forumScore.fill();
			forumScore.setValue(this.request);
			trans.add(forumScore, OperateType.UPDATE);
		} else {
			forumScore.setID(NoUtil.getMaxID("ForumScoreID"));
			forumScore.setValue(this.request);
			forumScore.setAddUser(User.getUserName());
			forumScore.setAddTime(new Date());
			trans.add(forumScore, OperateType.INSERT);
		}
		if (trans.commit()) {
			CacheManager.set("Forum", "Score", forumScore.getID(), forumScore);
			this.response.setLogInfo(1, "操作成功！");
		} else {
			this.response.setLogInfo(0, "操作失败");
		}
	}
}

/*
 * com.xdarkness.bbs.admin.ForumScoreSetting JD-Core Version: 0.6.0
 */