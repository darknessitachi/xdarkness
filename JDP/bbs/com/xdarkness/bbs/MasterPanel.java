package com.xdarkness.bbs;

import com.xdarkness.schema.ZCForumGroupSchema;
import com.xdarkness.schema.ZCForumMemberSchema;
import com.xdarkness.schema.ZCForumSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.Ajax;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;

public class MasterPanel extends Ajax {
	public static Mapx init(Mapx params) {
		params.put("Priv", ForumUtil.initPriv(params.getString("SiteID")));
		ForumUtil.adminPriv(params);
		params.put("BBSName", ForumUtil.getBBSName(params.getString("SiteID")));
		return params;
	}

	public void perInfoSave() {
		ZCForumMemberSchema forumUser = new ZCForumMemberSchema();
		forumUser.setUserName($V("UserName"));
		forumUser.fill();
		forumUser.setNickName($V("NickName"));

		Transaction trans = new Transaction();
		trans.add(forumUser, OperateType.UPDATE);
		if (trans.commit())
			this.response.setLogInfo(1, "修改成功!");
		else
			this.response.setLogInfo(0, "修改失败!");
	}

	public static Mapx searchUserInit(Mapx params) {
		String userName = params.getString("UserName");
		if ((XString.isNotEmpty(userName))
				&& (!ForumUtil.isExistMember(userName))) {
			params.remove("UserName");
			params.put("Message", "用户名不存在！");
			return params;
		}

		if (XString.isNotEmpty(userName)) {
			if (ForumUtil.isOperateMember(userName)) {
				ZCForumMemberSchema member = new ZCForumMemberSchema();
				member.setUserName(userName);
				member.fill();
				params.putAll(member.toMapx());
			} else {
				params.remove("UserName");
				params.put("Message", "您无权编辑该用户！");
			}
		}

		return params;
	}

	public void changeForum() {
		String forumID = $V("ForumID");
		if (XString.isEmpty(forumID)) {
			this.response.put("Info", "");
			return;
		}
		ZCForumSchema forum = new ZCForumSchema();
		forum.setID(forumID);

		if (forum.fill())
			this.response.put("Info", forum.getInfo());
		else
			this.response.setLogInfo(0, "该板块不存在!");
	}

	public static Mapx forumEditInit(Mapx params) {
		ZCForumMemberSchema member = new ZCForumMemberSchema();
		ZCForumGroupSchema group = new ZCForumGroupSchema();
		member.setUserName(User.getUserName());
		member.fill();
		group.setID(member.getAdminID());
		group.fill();
		DataTable dt = null;
		if (group.getSystemName().equals("超级版主")) {
			dt = new QueryBuilder(
					"select Name, ID from ZCForum where SiteID=?", params
							.getString("SiteID")).executeDataTable();
		} else {
			QueryBuilder qb = new QueryBuilder(
					"select Name,ID from zcforum where SiteID=? and (forumadmin like '%"
							+ User.getUserName()
							+ ",%' and ParentID<>0) or ParentID in (select ID from zcforum where SiteID=? "
							+ " and forumadmin like '%" + User.getUserName()
							+ ",%' and ParentID=0)",
					params.getString("SiteID"), params.getString("SiteID"));
			dt = qb.executeDataTable();
		}

		params.put("ForumOptions", HtmlUtil.dataTableToOptions(dt));
		return params;
	}

	public void editInfoSave() {
		ZCForumMemberSchema member = new ZCForumMemberSchema();

		member.setUserName($V("UName"));
		member.fill();
		member.setNickName($V("NickName"));
		member.setForumSign($V("ForumSign"));

		Transaction trans = new Transaction();
		trans.add(member, OperateType.UPDATE);
		if (trans.commit())
			this.response.setLogInfo(1, "修改成功!");
		else
			this.response.setLogInfo(0, "修改失败!");
	}

	public void editForumSave() {
		ZCForumSchema forum = new ZCForumSchema();

		forum.setID($V("ForumID"));
		forum.fill();
		forum.setInfo($V("Info"));

		Transaction trans = new Transaction();
		trans.add(forum, OperateType.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Forum", forum.getID(), forum);
			this.response.setLogInfo(1, "修改成功!");
		} else {
			this.response.setLogInfo(0, "修改失败!");
		}
	}
}

/*
 * com.xdarkness.bbs.MasterPanel JD-Core Version: 0.6.0
 */