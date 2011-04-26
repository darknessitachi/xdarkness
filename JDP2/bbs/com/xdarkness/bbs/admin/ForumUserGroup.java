package com.xdarkness.bbs.admin;

import java.util.Date;

import com.xdarkness.bbs.ForumUtil;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCForumGroupSchema;
import com.xdarkness.schema.ZCForumGroupSet;
import com.xdarkness.schema.ZCForumMemberSchema;
import com.xdarkness.schema.ZCForumMemberSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.DataListAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class ForumUserGroup extends Page {
	public static final String USERGROUP = "1";
	public static final String SYSTEMGROUP = "2";
	public static final String DEFINEDGROUP = "3";

	public static Mapx init(Mapx params) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		String ID = params.getString("GroupID");
		String originalImage = new QueryBuilder(
				"select Image from ZCForumGroup where SiteID=? and ID=?",
				SiteID, ID).executeString();
		params.put("ID", ID);
		params.put("originalImage", originalImage);
		return params;
	}

	public static void getList(DataListAction dla) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		QueryBuilder qb = new QueryBuilder(
				"select * from ZCForumGroup where SiteID=? and type='1' order by OrderFlag",
				SiteID);
		dla.bindData(qb);
	}

	public void add() {
		Transaction trans = new Transaction();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setType("1");
		userGroup.setSiteID(ForumUtil.getCurrentBBSSiteID());
		ZCForumGroupSet set = userGroup.query();
		set.sort("OrderFlag");
		int index = -1;
		for (int i = 0; i < set.size(); i++) {
			if (Long.parseLong($V("LowerScore")) < set.get(i).getLowerScore()) {
				set.get(i).setOrderFlag(set.get(i).getOrderFlag() + 1L);
				if (i == set.size() - 1) {
					userGroup.setOrderFlag(set.get(i).getOrderFlag() - 1L);
					index = i;
				}
			} else {
				if (Long.parseLong($V("LowerScore")) == set.get(i)
						.getLowerScore()) {
					this.response.setLogInfo(1, "最少到达积分已存在");
					return;
				}
				if (i != 0) {
					index = i - 1;
				}
				set.get(i).setUpperScore($V("LowerScore"));
				userGroup.setOrderFlag(set.get(i).getOrderFlag() + 1L);
				break;
			}
		}
		trans.add(set, OperateType.UPDATE);
		userGroup.setID(NoUtil.getMaxID("ForumGroupID"));
		userGroup.setValue(this.request);
		if (set.size() == 0) {
			userGroup.setOrderFlag(1L);
		}
		if (index == -1)
			userGroup.setUpperScore(999999L);
		else {
			userGroup.setUpperScore(set.get(index).getLowerScore());
		}
		userGroup.setSiteID(ForumUtil.getCurrentBBSSiteID());
		userGroup.setAllowAutograph("Y");
		userGroup.setAllowFace("Y");
		userGroup.setAllowHeadImage("N");
		userGroup.setAllowNickName("Y");
		userGroup.setAllowPanel("Y");
		userGroup.setAllowReply("Y");
		userGroup.setAllowSearch("Y");
		userGroup.setAllowTheme("Y");
		userGroup.setAllowUserInfo("Y");
		userGroup.setAllowVisit("Y");
		userGroup.setImage("../Images/SystemHeadImage/HeadImage01.gif");
		userGroup.setAddUser(User.getUserName());
		userGroup.setAddTime(new Date());
		userGroup.setVerify("N");
		trans.add(userGroup, OperateType.INSERT);
		if (trans.commit()) {
			CacheManager.set("Forum", "Group", userGroup.getID(), userGroup);
			this.response.setLogInfo(1, "添加成功");
		} else {
			this.response.setLogInfo(0, "操作失败");
		}
	}

	public void del() {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		Transaction trans = new Transaction();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID($V("ID"));
		userGroup.fill();

		ZCForumGroupSet set = userGroup.query(new QueryBuilder(
				"where SiteID=? and OrderFlag> ? ", SiteID, userGroup
						.getOrderFlag()));
		for (int i = 0; i < set.size(); i++) {
			set.get(i).setOrderFlag(set.get(i).getOrderFlag() - 1L);
		}
		trans.add(set, OperateType.UPDATE);

		DataTable dt = new QueryBuilder(
				"select ID from ZCForumGroup where SiteID=? and UpperScore=?",
				SiteID, userGroup.getLowerScore()).executeDataTable();
		ZCForumMemberSet memberSet = new ZCForumMemberSchema()
				.query(new QueryBuilder("where SiteID=? and UserGroupID=?",
						SiteID, userGroup.getID()));
		if (dt.getRowCount() > 0) {
			trans.add(new QueryBuilder("update ZCForumGroup set UpperScore="
					+ userGroup.getUpperScore() + " where "
					+ " SiteID=? and ID=?", SiteID, dt.getString(0, "ID")));

			for (int i = 0; i < memberSet.size(); i++)
				memberSet.get(i).setUserGroupID(dt.getString(0, "ID"));
		} else {
			DataTable dtNext = new QueryBuilder(
					"select ID from ZCForumGroup where SiteID=? and LowerScore=?",
					SiteID, userGroup.getUpperScore()).executeDataTable();
			trans
					.add(new QueryBuilder(
							"update ZCForumGroup set LowerScore=-99999 where SiteID=? and ID=?",
							SiteID, dtNext.getString(0, "ID")));

			if (dtNext.getRowCount() > 0) {
				for (int i = 0; i < memberSet.size(); i++)
					memberSet.get(i).setUserGroupID(dtNext.getString(0, "ID"));
			} else {
				this.response.setLogInfo(0, "您至少要留下一个用户组");
				return;
			}
		}
		trans.add(userGroup, OperateType.DELETE_AND_BACKUP);
		trans.add(memberSet, OperateType.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Group", userGroup.getID(), userGroup);
			for (int i = 0; i < set.size(); i++) {
				CacheManager.set("Forum", "Group", set.get(i).getID(), set
						.get(i));
			}
			this.response.setLogInfo(1, "删除成功");
		} else {
			this.response.setLogInfo(0, "操作失败");
		}
	}

	public void save() {
		Transaction trans = new Transaction();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setType("1");
		userGroup.setSiteID(ForumUtil.getCurrentBBSSiteID());
		ZCForumGroupSet set = userGroup.query();
		for (int i = 0; i < set.size(); i++) {
			String LowerScore = $V("LowerScore_" + set.get(i).getID()).trim();
			String Color = $V("Color_" + set.get(i).getID()).trim();
			set.get(i).setLowerScore(LowerScore);
			set.get(i).setColor(Color);
		}
		set.sort("LowerScore", "asc");
		for (int i = 0; i < set.size(); i++) {
			if ((i > 0)
					&& (set.get(i).getLowerScore() == set.get(i - 1)
							.getLowerScore())) {
				this.response.setLogInfo(0, "最少到达积分不能相同");
				return;
			}
			set.get(i).setOrderFlag(i + 1);
			if (i == set.size() - 1) {
				set.get(i).setUpperScore(99999L);
				break;
			}
			set.get(i).setUpperScore(set.get(i + 1).getLowerScore());
		}
		trans.add(set, OperateType.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Group", userGroup.getID(), userGroup);
			this.response.setLogInfo(1, "更新成功");
		} else {
			this.response.setLogInfo(0, "操作失败");
		}
	}

	public static void getListSystemGroup(DataListAction dla) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		QueryBuilder qb = new QueryBuilder(
				"select * from ZCForumGroup where SiteID=? and type='2'",
				SiteID);
		dla.bindData(qb);
	}

	public static void getListSpecialGroup(DataListAction dla) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		QueryBuilder qb = new QueryBuilder(
				"select * from ZCForumGroup where SiteID=? and type='3'",
				SiteID);
		dla.bindData(qb);
	}

	public void addSpecialGroup() {
		Transaction trans = new Transaction();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID(NoUtil.getMaxID("ForumGroupID"));
		userGroup.setType("3");
		userGroup.setValue(this.request);
		userGroup.setSiteID(ForumUtil.getCurrentBBSSiteID());
		userGroup.setRadminID(0L);
		userGroup.setAllowAutograph("Y");
		userGroup.setAllowFace("Y");
		userGroup.setAllowHeadImage("N");
		userGroup.setAllowNickName("Y");
		userGroup.setAllowPanel("Y");
		userGroup.setAllowReply("Y");
		userGroup.setAllowSearch("Y");
		userGroup.setAllowTheme("Y");
		userGroup.setAllowUserInfo("Y");
		userGroup.setAllowVisit("Y");
		userGroup.setVerify("N");
		userGroup.setImage("../Images/SystemHeadImage/HeadImage01.gif");
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

	public void imageSave() {
		Transaction trans = new Transaction();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID($V("ID"));
		userGroup.fill();
		userGroup.setImage($V("select"));

		trans.add(userGroup, OperateType.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Group", userGroup.getID(), userGroup);
			this.response.setLogInfo(1, "添加成功");
		} else {
			this.response.setLogInfo(0, "操作失败");
		}
	}

	public void editSpecialGroup() {
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setType("3");
		userGroup.setSiteID(ForumUtil.getCurrentBBSSiteID());
		ZCForumGroupSet set = userGroup.query();
		for (int i = 0; i < set.size(); i++) {
			set.get(i).setColor($V("Color_" + set.get(i).getID()));
		}
		if (set.update()) {
			for (int i = 0; i < set.size(); i++) {
				CacheManager.set("Forum", "Group", set.get(i).getID(), set
						.get(i));
			}
			this.response.setLogInfo(1, "添加成功");
		} else {
			this.response.setLogInfo(0, "操作失败");
		}
	}

	public void editSystemGroup() {
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setType("2");
		userGroup.setSiteID(ForumUtil.getCurrentBBSSiteID());
		ZCForumGroupSet set = userGroup.query();
		for (int i = 0; i < set.size(); i++) {
			set.get(i).setColor($V("Color_" + set.get(i).getID()));
		}
		if (set.update()) {
			for (int i = 0; i < set.size(); i++) {
				CacheManager.set("Forum", "Group", set.get(i).getID(), set
						.get(i));
			}
			this.response.setLogInfo(1, "添加成功");
		} else {
			this.response.setLogInfo(0, "操作失败");
		}
	}

	public void delSpecailGroup() {
		Transaction trans = new Transaction();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID($V("ID"));
		userGroup.fill();
		ZCForumMemberSet memberSet = new ZCForumMemberSchema()
				.query(new QueryBuilder("where DefinedID=?", userGroup.getID()));
		for (int i = 0; i < memberSet.size(); i++) {
			memberSet.get(i).setDefinedID(0L);
		}
		trans.add(userGroup, OperateType.DELETE_AND_BACKUP);
		trans.add(memberSet, OperateType.UPDATE);
		if (trans.commit()) {
			CacheManager.remove("Forum", "Group", userGroup.getID());
			this.response.setLogInfo(1, "删除成功");
		} else {
			this.response.setLogInfo(0, "操作失败");
		}
	}
}

/*
 * com.xdarkness.bbs.admin.ForumUserGroup JD-Core Version: 0.6.0
 */