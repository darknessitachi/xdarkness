package com.xdarkness.bbs;

import java.util.Date;

import com.xdarkness.bbs.admin.ForumScore;
import com.xdarkness.schema.ZCForumGroupSchema;
import com.xdarkness.schema.ZCForumMemberSchema;
import com.xdarkness.schema.ZCForumMemberSet;
import com.xdarkness.schema.ZCForumSchema;
import com.xdarkness.schema.ZCForumSet;
import com.xdarkness.schema.ZCPostSchema;
import com.xdarkness.schema.ZCPostSet;
import com.xdarkness.schema.ZCThemeSchema;
import com.xdarkness.schema.ZCThemeSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.DataListAction;
import com.xdarkness.framework.jaf.Ajax;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;

public class PostAudit extends Ajax {
	public static Mapx init(Mapx params) {
		String SiteID = params.getString("SiteID");
		ZCForumMemberSchema member = new ZCForumMemberSchema();
		ZCForumGroupSchema group = new ZCForumGroupSchema();
		member.setUserName(User.getUserName());
		member.fill();
		group = ForumCache.getGroup(member.getAdminID());
		group.fill();
		DataTable dt = null;
		if (group.getSystemName().equals("超级版主")) {
			QueryBuilder qb = new QueryBuilder(
					"select Name, ID from ZCForum where SiteID=? and ParentID<>0",
					SiteID);
			dt = qb.executeDataTable();
		} else {
			QueryBuilder qb = new QueryBuilder(
					"select Name,ID from zcforum where SiteID=? and (forumadmin like '%"
							+ User.getUserName()
							+ ",%' and ParentID<>0) or ParentID in (select ID from zcforum where SiteID=?"
							+ " and forumadmin like '%" + User.getUserName()
							+ ",%' and ParentID=0)", SiteID, SiteID);
			dt = qb.executeDataTable();
		}
		params.put("ForumOptions", HtmlUtil.dataTableToOptions(dt));
		params.put("BBSName", ForumUtil.getBBSName(params.getString("SiteID")));
		return params;
	}

	public static void getUnauditedPost(DataListAction dla) {
		String SiteID = dla.getParam("SiteID");
		String auditFlag = dla.getParams().getString("auditFlag");
		String forumID = dla.getParams().getString("ForumID");
		String typeID = dla.getParams().getString("TypeID");
		String first = dla.getParams().getString("First");
		QueryBuilder qb = new QueryBuilder(
				"select p.*, f.Name ForumName, '' as AuditFlag from ZCPost p, ZCForum f where p.SiteID=? and p.Invisible='Y' and p.ForumID=f.ID",
				SiteID);
		if (XString.isNotEmpty(typeID))
			qb.append(" and p.VerifyFlag=?", typeID);
		else {
			qb.append(" and p.VerifyFlag='N'");
		}

		if ((XString.isNotEmpty(forumID)) && (!forumID.equals("0"))) {
			if (!XString.checkID(forumID)) {
				LogUtil.warn("可能的SQL注入,PostAudit.getUnauditedPost:" + forumID);
				return;
			}
			qb.append(" and p.ForumID in (" + forumID + ")");
		}

		if (XString.isNotEmpty(first))
			qb.append(" and p.first=?", first);
		else {
			qb.append(" and p.first='Y'");
		}
		qb.append(" order by ID");
		int pageSize = dla.getPageSize();
		int pageIndex = dla.getPageIndex();
		DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
		dt.insertColumn("StatusCount");
		for (int i = 0; i < dt.getRowCount(); i++) {
			Mapx map = new Mapx();
			map.put("Y", "通过");
			map.put("N", "删除");
			map.put("X", "忽略");
			if (XString.isNotEmpty(auditFlag))
				dt.set(i, "AuditFlag", HtmlUtil.mapxToRadios("AuditFlag_"
						+ dt.get(i, "ID"), map, auditFlag, true));
			else {
				dt.set(i, "AuditFlag", HtmlUtil.mapxToRadios("AuditFlag_"
						+ dt.get(i, "ID"), map, "Y", true));
			}
			dt.set(i, "StatusCount", i);
		}

		dla.setTotal(qb);
		dla.bindData(dt);
	}

	public void exeAudit() {
		String SiteID = $V("SiteID");
		ForumScore forumScore = new ForumScore($V("SiteID"));
		Transaction trans = new Transaction();
		if (!XString.checkID($V("ids"))) {
			LogUtil.warn("可能的SQL注入,PostAudit.exeAudit:" + $V("ids"));
			return;
		}
		ZCPostSet postSet = new ZCPostSchema().query(new QueryBuilder(
				"where SiteID=? and id in(" + $V("ids") + ")", SiteID));
		ZCThemeSet themeSet = new ZCThemeSet();
		ZCForumSet forumSet = new ZCForumSet();

		ZCForumMemberSet userSet = new ZCForumMemberSet();
		Mapx map = new Mapx();

		for (int i = 0; i < postSet.size(); i++) {
			ZCPostSchema post = postSet.get(i);
			ZCThemeSchema theme = new ZCThemeSchema();
			theme.setID(post.getThemeID());
			ZCForumSchema forum = new ZCForumSchema();
			forum.setID(post.getForumID());
			ZCForumMemberSchema user = new ZCForumMemberSchema();
			user.setUserName(post.getAddUser());
			int indexTheme = ForumUtil.getValueOfThemeSet(themeSet, theme);
			int indexForum = ForumUtil.getValueOfForumSet(forumSet, forum);
			int indexMember = ForumUtil.getValueOfMemberSet(userSet, user);
			String newFlag = $V("AuditFlag_" + post.getID());
			if (map.get(post.getThemeID()) == null) {
				long layer = getMAXLayer(post.getThemeID()) + 1L;
				map.put(post.getThemeID(), layer);
			}
			if (XString.isNotEmpty(newFlag)) {
				if (newFlag.equals("Y")) {
					post.setVerifyFlag("Y");

					if (post.getFirst().equals("Y")) {
						theme.fill();
						theme.setVerifyFlag("Y");
						theme.setOrderTime(new Date());
						themeSet.add(theme);

						if (indexMember == -1) {
							user.fill();
							user.setThemeCount(user.getThemeCount() + 1L);
							user.setForumScore(user.getForumScore()
									+ forumScore.PublishTheme);
							userSet.add(user);
						} else {
							userSet.get(indexMember)
									.setThemeCount(
											userSet.get(indexMember)
													.getThemeCount() + 1L);
							userSet.get(indexMember).setForumScore(
									userSet.get(indexMember).getForumScore()
											+ forumScore.PublishTheme);
						}

						if (indexForum == -1) {
							forum.fill();
							forum.setPostCount(forum.getPostCount() + 1);
							forum.setThemeCount(forum.getThemeCount() + 1);
							forum.setLastPost(post.getSubject());
							forum.setLastPoster(post.getAddUser());
							forum.setLastThemeID(theme.getID());
							forumSet.add(forum);
						} else {
							forumSet.get(indexForum)
									.setPostCount(
											forumSet.get(indexForum)
													.getPostCount() + 1);
							forumSet.get(indexForum)
									.setThemeCount(
											forumSet.get(indexForum)
													.getThemeCount() + 1);
							forumSet.get(indexForum).setLastPost(
									post.getSubject());
							forumSet.get(indexForum).setLastPoster(
									post.getAddUser());
							forumSet.get(indexForum).setLastThemeID(
									theme.getID());
						}
					} else {
						post.setLayer(map.getLong(post.getThemeID()));
						map.put(post.getThemeID(), post.getLayer() + 1L);
						if (indexMember == -1) {
							user.fill();
							user.setReplyCount(user.getReplyCount() + 1L);
							user.setForumScore(user.getForumScore()
									+ forumScore.PublishPost);
							userSet.add(user);
						} else {
							userSet.get(indexMember)
									.setReplyCount(
											userSet.get(indexMember)
													.getThemeCount() + 1L);
							userSet.get(indexMember).setForumScore(
									userSet.get(indexMember).getForumScore()
											+ forumScore.PublishPost);
						}

						if (indexForum == -1) {
							forum.fill();
							forum.setPostCount(forum.getPostCount() + 1);
							forumSet.add(forum);
						} else {
							forumSet.get(indexForum)
									.setPostCount(
											forumSet.get(indexForum)
													.getPostCount() + 1);
						}

						if (indexTheme == -1) {
							theme.fill();
							theme.setReplyCount(theme.getReplyCount() + 1);
							themeSet.add(theme);
						} else {
							themeSet.get(indexTheme)
									.setReplyCount(
											themeSet.get(indexTheme)
													.getReplyCount() + 1);
						}
						theme.setLastPostTime(new Date());
					}
				} else if (newFlag.equals("N")) {
					post.setInvisible("N");
					if (post.getFirst().equals("Y")) {
						theme.fill();
						theme.setStatus("N");
						themeSet.add(theme);
					}
				} else if (newFlag.equals("Z")) {
					post.setApplyDel("N");
					if (post.getFirst().equals("Y")) {
						theme.fill();
						theme.setApplydel("N");
						themeSet.add(theme);
					}
				} else {
					post.setVerifyFlag("X");
					if (post.getFirst().equals("Y")) {
						theme.fill();
						theme.setVerifyFlag("X");
						themeSet.add(theme);
					}
				}
			}
		}
		trans.add(postSet, OperateType.UPDATE);
		trans.add(userSet, OperateType.UPDATE);
		trans.add(forumSet, OperateType.UPDATE);
		trans.add(themeSet, OperateType.UPDATE);
		if (trans.commit()) {
			for (int i = 0; i < forumSet.size(); i++) {
				CacheManager.set("Forum", "Forum", forumSet.get(i).getID(),
						forumSet.get(i));
			}
			this.response.setLogInfo(1, "操作成功");
		} else {
			this.response.setLogInfo(0, "操作失败!");
		}
	}

	private long getMAXLayer(long ThemeID) {
		QueryBuilder qb = new QueryBuilder(
				"select Layer from ZCPost where Invisible='Y' and ThemeID=? order by Layer desc",
				ThemeID);
		long layer = qb.executePagedDataTable(1, 0).getLong(0, 0);
		return layer;
	}

	public void applyDel() {
		Transaction trans = new Transaction();
		ZCPostSchema post = new ZCPostSchema();
		ZCThemeSchema theme = new ZCThemeSchema();
		post.setID($V("ID"));
		post.fill();
		post.setApplyDel("Y");
		if (post.getFirst().equals("Y")) {
			theme.setID(post.getThemeID());
			theme.fill();
			theme.setApplydel("Y");
			trans.add(theme, OperateType.UPDATE);
		}
		trans.add(post, OperateType.UPDATE);

		if (trans.commit())
			this.response.setLogInfo(1, "申请成功!");
		else
			this.response.setLogInfo(0, "申请失败!");
	}
}

/*
 * com.xdarkness.bbs.PostAudit JD-Core Version: 0.6.0
 */