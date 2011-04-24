package com.xdarkness.bbs.admin;

import com.xdarkness.bbs.ForumUtil;
import com.xdarkness.schema.ZCForumGroupSchema;
import com.xdarkness.schema.ZCForumMemberSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;

public class ForumUserInfo extends Page {
	public static Mapx init(Mapx params) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		DataTable dt = new QueryBuilder(
				"select Name,ID from ZCForumGroup where SiteID=? and type=?",
				SiteID, "1").executeDataTable();
		params.put("UserGroup", HtmlUtil.dataTableToOptions(dt));

		DataTable dtSystem = new QueryBuilder(
				"select Name,ID from ZCForumGroup where SiteID=? and type=?",
				SiteID, "2").executeDataTable();

		params.put("UserSystemGroup", HtmlUtil.dataTableToOptions(dtSystem));

		DataTable dtDefined = new QueryBuilder(
				"select Name,ID from ZCForumGroup where SiteID=? and type=?",
				SiteID, "3").executeDataTable();

		params.put("UserDefinedGroup", HtmlUtil.dataTableToOptions(dtDefined));
		return params;
	}

	public static void getList(DataGridAction dga) {
		String UserGroupID = dga.getParam("UserGroup");
		String UserSystemGroup = dga.getParam("UserSystemGroup");
		String UserDefinedGroup = dga.getParam("UserDefinedGroup");
		String LowerScore = dga.getParam("LowerScore");
		String UpperScore = dga.getParam("UpperScore");
		String UserName = dga.getParam("ForumUserName");
		long SiteID = ForumUtil.getCurrentBBSSiteID();

		QueryBuilder qb = new QueryBuilder(
				"select a.UserName,b.Name,c.Name AdminName,d.Name DefinedName,a.ThemeCount,a.ForumScore from ZCForumMember a join ZCForumGroup b on(b.ID=a.UserGroupID) left join ZCForumGroup c on(a.AdminID=c.ID) left join ZCForumGroup d on(a.DefinedID=d.ID) where a.SiteID=?",
				SiteID);

		if (XString.isNotEmpty(UserName)) {
			qb.append(" and UserName like ?", "%" + UserName + "%");
		}
		if (XString.isNotEmpty(LowerScore)) {
			qb.append(" and ForumScore>=?", LowerScore);
		}
		if (XString.isNotEmpty(UpperScore)) {
			qb.append(" and ForumScore<=?", UpperScore);
		}
		if ((XString.isNotEmpty(UserSystemGroup))
				&& (!UserSystemGroup.equals("0"))) {
			qb.append(" and a.AdminID=?", UserSystemGroup);
		}
		if ((XString.isNotEmpty(UserDefinedGroup))
				&& (!UserDefinedGroup.equals("0"))) {
			qb.append(" and a.DefinedID=?", UserDefinedGroup);
		}
		if ((XString.isNotEmpty(UserGroupID)) && (!UserGroupID.equals("0"))) {
			qb.append(" and a.UserGroupID=?", UserGroupID);
		}
		dga.bindData(qb);
	}

	public static Mapx initEditDialog(Mapx params) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		ZCForumMemberSchema member = new ZCForumMemberSchema();
		String userName = params.getString("UserName");
		member.setUserName(userName);
		member.fill();
		ZCForumGroupSchema group = new ZCForumGroupSchema();
		group.setID(member.getUserGroupID());
		group.fill();
		params.put("UserGroup", group.getName());
		QueryBuilder qb1 = new QueryBuilder(
				"select Name,ID from ZCForumGroup where SiteID=? and Type=?",
				SiteID, "3");
		QueryBuilder qb2 = new QueryBuilder(
				"select Name,ID from ZCForumGroup where SiteID=? and (SystemName='版主' or SystemName='超级版主' or SystemName='禁止访问')",
				SiteID);
		DataTable dtDefinedGroup = qb1.executeDataTable();
		DataTable dtSystemGroup = qb2.executeDataTable();
		params.put("DefinedGroup", HtmlUtil.dataTableToOptions(dtDefinedGroup,
				member.getDefinedID()));
		params.put("SystemGroup", HtmlUtil.dataTableToOptions(dtSystemGroup,
				member.getAdminID()));

		DataTable dt1 = new QueryBuilder(
				"select Name from ZCForum where  Forumadmin like ?", "%"
						+ userName + ",%").executeDataTable();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < dt1.getRowCount(); i++) {
			sb.append(dt1.get(i, "Name"));
			if (i != dt1.getRowCount() - 1) {
				sb.append(",");
			}
		}
		params.put("Forums", sb.toString());
		params.put("ForumAdminID", dtSystemGroup.getString(0, "ID"));
		params.put("ForumVisit", dtSystemGroup.getString(2, "ID"));
		params.put("ThemeCount", member.getThemeCount());
		params.put("ForumScore", member.getForumScore());

		return params;
	}

	public void editUserGroup() {
		if ($V("UserName").equals("admin")) {
			this.response.setLogInfo(0, "系统管理员不能更改！");
			return;
		}
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		Transaction trans = new Transaction();
		ZCForumMemberSchema member = new ZCForumMemberSchema();
		member.setUserName($V("UserName"));
		member.fill();
		member.setForumScore($V("ForumScore"));
		member.setThemeCount($V("ThemeCount"));
		member.setDefinedID($V("DefinedID"));
		if (!$V("AdminID").equals("0")) {
			member.setAdminID($V("AdminID"));
		} else {
			int count = new QueryBuilder(
					"select count(*) from ZCForum where ForumAdmin like ?", "%"
							+ member.getUserName() + ",%").executeInt();
			if (count > 0) {
				long adminID = new QueryBuilder(
						"select ID from ZCForumGroup where SystemName='版主' and SiteID=?",
						SiteID).executeLong();
				member.setAdminID(adminID);
			} else {
				member.setAdminID(0L);
			}
		}

		ForumUtil.userGroupChange(member);
		trans.add(member, OperateType.UPDATE);
		if (trans.commit())
			this.response.setLogInfo(1, "操作成功");
		else
			this.response.setLogInfo(0, "操作失败");
	}
}

/*
 * com.xdarkness.bbs.admin.ForumUserInfo JD-Core Version: 0.6.0
 */