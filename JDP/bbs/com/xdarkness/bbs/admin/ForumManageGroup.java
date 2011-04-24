package com.xdarkness.bbs.admin;

import java.util.Date;

import com.xdarkness.bbs.ForumUtil;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCForumGroupSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.DataListAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;

public class ForumManageGroup extends Page {
	public static void getListManageGroup(DataListAction dla) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		QueryBuilder qb = new QueryBuilder(
				"select f1.Name, f2.SystemName, f1.Type, a.* from ZCForumGroup f1, ZCForumGroup f2, ZCAdminGroup a where f1.SiteID=?  and f1.ID=a.GroupID and f1.RadminID=f2.ID",
				SiteID);
		DataTable dt = qb.executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (dt.get(i, "Type").equals("2"))
				dt.set(i, "Type", "内置");
			else {
				dt.set(i, "Type", "自定义");
			}
		}
		dla.setTotal(qb);
		dla.bindData(dt);
	}

	public void addManageGroup() {
		Transaction trans = new Transaction();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID(NoUtil.getMaxID("ForumGroupID"));
		userGroup.setType("2");
		userGroup.setValue(this.request);
		userGroup.setAddUser(User.getUserName());
		userGroup.setAddTime(new Date());
		trans.add(userGroup, OperateType.INSERT);
		if (trans.commit()) {
			CacheManager.set("Forum", "Group", userGroup.getID(), userGroup);
			this.response.setLogInfo(1, "添加成功");
		} else {
			this.response.setLogInfo(0, "操作失败");
		}
	}
}

/*
 * com.xdarkness.bbs.admin.ForumManageGroup JD-Core Version: 0.6.0
 */