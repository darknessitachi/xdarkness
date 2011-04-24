package com.xdarkness.bbs.admin;

import java.util.Date;

import com.xdarkness.bbs.ForumUtil;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCForumConfigSchema;
import com.xdarkness.schema.ZCForumGroupSchema;
import com.xdarkness.schema.ZCForumMemberSchema;
import com.xdarkness.schema.ZCForumScoreSchema;
import com.xdarkness.schema.ZDMemberPersonInfoSchema;
import com.xdarkness.schema.ZDMemberSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.sql.QueryBuilder;

public class DBInit extends Page {
	public void DBDataInit() {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		QueryBuilder qb = new QueryBuilder(
				"select count(*) from ZCForumGroup where SiteID=? and (SystemName='版主' or SystemName='超级版主' or SystemName='系统管理员' or SystemName='游客')",
				SiteID);
		int count = qb.executeInt();
		if (count == 0) {
			Transaction trans = new Transaction();
			ZCForumGroupSchema group1 = new ZCForumGroupSchema();
			group1.setID(NoUtil.getMaxID("ForumGroupID"));
			group1.setRadminID(group1.getID());
			group1.setName("版主");
			group1.setSystemName("版主");
			group1.setType("2");
			group1.setSiteID(ForumUtil.getCurrentBBSSiteID());
			group1.setAllowAutograph("Y");
			group1.setAllowFace("Y");
			group1.setAllowHeadImage("Y");
			group1.setAllowNickName("Y");
			group1.setAllowPanel("Y");
			group1.setAllowReply("Y");
			group1.setAllowSearch("Y");
			group1.setAllowTheme("Y");
			group1.setAllowUserInfo("Y");
			group1.setAllowVisit("Y");
			group1.setVerify("Y");
			group1.setImage("../Images/SystemHeadImage/HeadImage01.gif");
			group1.setAddUser("system");
			group1.setAddTime(new Date());
			group1.setAllowEditUser("Y");
			group1.setAllowForbidUser("Y");
			group1.setAllowEditForum("Y");
			group1.setAllowVerfyPost("Y");
			group1.setAllowDel("Y");
			group1.setAllowEdit("Y");
			group1.setHide("Y");
			group1.setTopTheme("Y");
			group1.setBestTheme("Y");
			group1.setBrightTheme("Y");
			group1.setRemoveTheme("Y");
			group1.setMoveTheme("Y");
			group1.setUpOrDownTheme("Y");

			trans.add(group1, OperateType.INSERT);

			ZCForumGroupSchema group2 = new ZCForumGroupSchema();
			group2.setID(NoUtil.getMaxID("ForumGroupID"));
			group2.setRadminID(group2.getID());
			group2.setName("超级版主");
			group2.setSystemName("超级版主");
			group2.setType("2");
			group2.setSiteID(ForumUtil.getCurrentBBSSiteID());
			group2.setAllowAutograph("Y");
			group2.setAllowFace("Y");
			group2.setAllowHeadImage("Y");
			group2.setAllowNickName("Y");
			group2.setAllowPanel("Y");
			group2.setAllowReply("Y");
			group2.setAllowSearch("Y");
			group2.setAllowTheme("Y");
			group2.setAllowUserInfo("Y");
			group2.setAllowVisit("Y");
			group2.setVerify("Y");
			group2.setImage("../Images/SystemHeadImage/HeadImage01.gif");
			group2.setAddUser("system");
			group2.setAddTime(new Date());

			group2.setAllowEditUser("Y");
			group2.setAllowForbidUser("Y");
			group2.setAllowEditForum("Y");
			group2.setAllowVerfyPost("Y");
			group2.setAllowDel("Y");
			group2.setAllowEdit("Y");
			group2.setHide("Y");
			group2.setTopTheme("Y");
			group2.setBestTheme("Y");
			group2.setBrightTheme("Y");
			group2.setRemoveTheme("Y");
			group2.setMoveTheme("Y");
			group2.setUpOrDownTheme("Y");
			trans.add(group2, OperateType.INSERT);

			ZCForumGroupSchema group3 = new ZCForumGroupSchema();
			group3.setID(NoUtil.getMaxID("ForumGroupID"));
			group3.setRadminID(group3.getID());
			group3.setName("系统管理员");
			group3.setSystemName("系统管理员");
			group3.setType("2");
			group3.setSiteID(ForumUtil.getCurrentBBSSiteID());
			group3.setAllowAutograph("Y");
			group3.setAllowFace("Y");
			group3.setAllowHeadImage("Y");
			group3.setAllowNickName("Y");
			group3.setAllowPanel("Y");
			group3.setAllowReply("Y");
			group3.setAllowSearch("Y");
			group3.setAllowTheme("Y");
			group3.setAllowUserInfo("Y");
			group3.setAllowVisit("Y");
			group3.setVerify("Y");
			group3.setImage("../Images/SystemHeadImage/HeadImage01.gif");
			group3.setAddUser("system");
			group3.setAddTime(new Date());

			group3.setAllowEditUser("Y");
			group3.setAllowForbidUser("Y");
			group3.setAllowEditForum("Y");
			group3.setAllowVerfyPost("Y");
			group3.setAllowDel("Y");
			group3.setAllowEdit("Y");
			group3.setHide("Y");
			group3.setTopTheme("Y");
			group3.setBestTheme("Y");
			group3.setBrightTheme("Y");
			group3.setRemoveTheme("Y");
			group3.setMoveTheme("Y");
			group3.setUpOrDownTheme("Y");
			trans.add(group3, OperateType.INSERT);

			ZCForumGroupSchema group4 = new ZCForumGroupSchema();
			group4.setID(NoUtil.getMaxID("ForumGroupID"));
			group4.setName("游客");
			group4.setSystemName("游客");
			group4.setType("2");
			group4.setSiteID(ForumUtil.getCurrentBBSSiteID());
			group4.setAllowAutograph("N");
			group4.setAllowFace("N");
			group4.setAllowHeadImage("N");
			group4.setAllowNickName("N");
			group4.setAllowPanel("N");
			group4.setAllowReply("N");
			group4.setAllowSearch("N");
			group4.setAllowTheme("N");
			group4.setAllowUserInfo("N");
			group4.setAllowVisit("Y");
			group4.setVerify("N");
			group4.setImage("../Images/SystemHeadImage/HeadImage01.gif");

			group4.setAllowEditUser("N");
			group4.setAllowForbidUser("N");
			group4.setAllowEditForum("N");
			group4.setAllowVerfyPost("N");
			group4.setAllowDel("N");
			group4.setAllowEdit("N");
			group4.setHide("N");
			group4.setTopTheme("N");
			group4.setBestTheme("N");
			group4.setBrightTheme("N");
			group4.setRemoveTheme("N");
			group4.setMoveTheme("N");
			group4.setUpOrDownTheme("N");

			group4.setAddUser("system");
			group4.setAddTime(new Date());
			trans.add(group4, OperateType.INSERT);

			ZCForumGroupSchema group5 = new ZCForumGroupSchema();
			group5.setID(NoUtil.getMaxID("ForumGroupID"));
			group5.setName("禁止访问");
			group5.setSystemName("禁止访问");
			group5.setType("2");
			group5.setSiteID(ForumUtil.getCurrentBBSSiteID());
			group5.setAllowAutograph("N");
			group5.setAllowFace("N");
			group5.setAllowHeadImage("N");
			group5.setAllowNickName("N");
			group5.setAllowPanel("N");
			group5.setAllowReply("N");
			group5.setAllowSearch("N");
			group5.setAllowTheme("N");
			group5.setAllowUserInfo("N");
			group5.setAllowVisit("N");
			group5.setVerify("N");
			group5.setImage("../Images/SystemHeadImage/HeadImage01.gif");
			group5.setAllowEditUser("N");
			group5.setAllowForbidUser("N");
			group5.setAllowEditForum("N");
			group5.setAllowVerfyPost("N");
			group5.setAllowDel("N");
			group5.setAllowEdit("N");
			group5.setHide("N");
			group5.setTopTheme("N");
			group5.setBestTheme("N");
			group5.setBrightTheme("N");
			group5.setRemoveTheme("N");
			group5.setMoveTheme("N");
			group5.setUpOrDownTheme("N");
			group5.setAddUser("system");
			group5.setAddTime(new Date());
			trans.add(group5, OperateType.INSERT);

			ZCForumScoreSchema forumScore = new ZCForumScoreSchema();
			forumScore.setID(NoUtil.getMaxID("ForumGroupID"));
			forumScore.setSiteID(ForumUtil.getCurrentBBSSiteID());
			forumScore.setInitScore(20L);
			forumScore.setPublishTheme(5L);
			forumScore.setDeleteTheme(-5L);
			forumScore.setPublishPost(2L);
			forumScore.setDeletePost(-2L);
			forumScore.setBest(10L);
			forumScore.setCancelBest(-10L);
			forumScore.setBright(5L);
			forumScore.setCancelBright(-5L);
			forumScore.setTopTheme(10L);
			forumScore.setCancelTop(-10L);
			forumScore.setUpTheme(5L);
			forumScore.setDownTheme(-5L);
			forumScore.setUpload(5L);
			forumScore.setDownload(-2L);
			forumScore.setSearch(0L);
			forumScore.setVote(2L);
			forumScore.setAddUser("system");
			forumScore.setAddTime(new Date());
			trans.add(forumScore, OperateType.INSERT);

			ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
			userGroup.setID(NoUtil.getMaxID("ForumGroupID"));
			userGroup.setSiteID(ForumUtil.getCurrentBBSSiteID());
			userGroup.setName("新手上路");
			userGroup.setType("1");
			userGroup.setLowerScore(-99999L);
			userGroup.setUpperScore(99999L);
			userGroup.setOrderFlag(1L);
			userGroup.setAllowAutograph("N");
			userGroup.setAllowFace("N");
			userGroup.setAllowHeadImage("N");
			userGroup.setAllowNickName("N");
			userGroup.setAllowPanel("Y");
			userGroup.setAllowReply("Y");
			userGroup.setAllowSearch("N");
			userGroup.setAllowTheme("Y");
			userGroup.setAllowUserInfo("N");
			userGroup.setAllowVisit("Y");
			userGroup.setVerify("N");
			userGroup.setImage("../Images/SystemHeadImage/HeadImage01.gif");
			userGroup.setAllowEditUser("N");
			userGroup.setAllowForbidUser("N");
			userGroup.setAllowEditForum("N");
			userGroup.setAllowVerfyPost("N");
			userGroup.setAllowDel("N");
			userGroup.setAllowEdit("N");
			userGroup.setHide("N");
			userGroup.setTopTheme("N");
			userGroup.setBestTheme("N");
			userGroup.setBrightTheme("N");
			userGroup.setRemoveTheme("N");
			userGroup.setMoveTheme("N");
			userGroup.setUpOrDownTheme("N");
			userGroup.setAddUser("system");
			userGroup.setAddTime(new Date());
			trans.add(userGroup, OperateType.INSERT);

			ZCForumConfigSchema forumConfig = new ZCForumConfigSchema();
			forumConfig.setID(NoUtil.getMaxID("ForumConfigID"));
			forumConfig.setName("ZvingBBS");
			forumConfig.setSiteID(ForumUtil.getCurrentBBSSiteID());
			forumConfig.setSubdomains("");
			forumConfig.setDes("");
			forumConfig.setTempCloseFlag("N");
			forumConfig.setAddTime(new Date());
			forumConfig.setAddUser("system");
			trans.add(forumConfig, OperateType.INSERT);

			ZDMemberSchema member = new ZDMemberSchema();
			member.setUserName("admin");
			if (!member.fill()) {
				member.setPassword(XString.md5Hex("admin"));
				member.setEmail("admin@sky.com");
				member.setSiteID(ForumUtil.getCurrentBBSSiteID());
				member.setRegIP(this.request.getClientIP());
				member.setValue(this.request);
				member.setType("person");
				member.setStatus("Y");
				member.setScore("0");
				member
						.setMemberLevel("" + new QueryBuilder(
								"select ID from ZDMemberLevel where Score <= 0 order by Score desc")
								.executeOneValue());

				member.setRegTime(new Date());
				member.setName("系统管理员");
				ZDMemberPersonInfoSchema personInfo = new ZDMemberPersonInfoSchema();
				personInfo.setUserName(member.getUserName());
				ZCForumMemberSchema forumMember = new ZCForumMemberSchema();
				forumMember.setUserName(member.getUserName());
				forumMember.setSiteID(ForumUtil.getCurrentBBSSiteID());
				forumMember.setThemeCount(0L);
				forumMember.setReplyCount(0L);
				forumMember.setAdminID(group3.getID());
				forumMember.setUserGroupID(group3.getID());
				forumMember.setDefinedID(0L);
				forumMember.setUseSelfImage("N");
				forumMember
						.setHeadImage("../Images/SystemHeadImage/HeadImage01.gif");
				forumMember.setAddUser(member.getUserName());
				forumMember.setAddTime(new Date());
				trans.add(member, OperateType.INSERT);
				trans.add(personInfo, OperateType.INSERT);
				trans.add(forumMember, OperateType.INSERT);
			}

			if (trans.commit()) {
				CacheManager.set("Forum", "Group", group1.getID(), group1);
				CacheManager.set("Forum", "Group", group2.getID(),
						group2);
				CacheManager.set("Forum", "Group", group3.getID(), group3);
				CacheManager.set("Forum", "Group", group4.getID(), group4);
				CacheManager.set("Forum", "Group", group5.getID(), group5);
				CacheManager
						.set("Forum", "Group", userGroup.getID(), userGroup);
				CacheManager.set("Forum", "Config", forumConfig.getID(),
						forumConfig);
				CacheManager.set("Forum", "Score", forumScore.getID(),
						forumScore);
				this.response.setLogInfo(1, "初始化成功！");
			} else {
				this.response.setLogInfo(0, "初始化失败！");
			}
		} else {
			this.response.setLogInfo(0, "数据已存在！");
		}
	}
}

/*
 * com.xdarkness.bbs.admin.DBInit JD-Core Version: 0.6.0
 */