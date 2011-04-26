package com.xdarkness.bbs.admin;

import com.xdarkness.bbs.ForumUtil;
import com.xdarkness.schema.ZCAdminGroupSchema;
import com.xdarkness.schema.ZCForumGroupSchema;
import com.xdarkness.schema.ZCForumMemberSchema;
import com.xdarkness.schema.ZCForumMemberSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;

public class ForumUserGroupOption extends Page {
	public static Mapx init(Mapx params) {
		return params;
	}

	public static Mapx initBasic(Mapx params) {
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID(params.getString("ID"));
		userGroup.fill();
		params = userGroup.toMapx();
		Mapx map = new Mapx();
		map.put("Y", "允许");
		map.put("N", "不允许");
		if ((XString.isEmpty(userGroup.getSystemName()))
				|| (!userGroup.getSystemName().equals("游客"))) {
			params.put("AllowHeadImage", HtmlUtil.mapxToRadios(
					"AllowHeadImage", map, userGroup.getAllowHeadImage()));
			params.put("AllowNickName", HtmlUtil.mapxToRadios("AllowNickName",
					map, userGroup.getAllowNickName()));
			params.put("AllowPanel", HtmlUtil.mapxToRadios("AllowPanel", map,
					userGroup.getAllowPanel()));
		} else {
			params.put("AllowHeadImage", "不允许");
			params.put("AllowNickName", "不允许");
			params.put("AllowPanel", "不允许");
		}
		params.put("AllowUserInfo", HtmlUtil.mapxToRadios("AllowUserInfo", map,
				userGroup.getAllowUserInfo()));
		params.put("AllowVisit", HtmlUtil.mapxToRadios("AllowVisit", map,
				userGroup.getAllowVisit()));
		params.put("AllowSearch", HtmlUtil.mapxToRadios("AllowSearch", map,
				userGroup.getAllowSearch()));
		return params;
	}

	public static Mapx initPostOption(Mapx params) {
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID(params.getString("ID"));
		userGroup.fill();
		params = userGroup.toMapx();
		Mapx map = new Mapx();
		map.put("Y", "允许");
		map.put("N", "不允许");
		if ((XString.isEmpty(userGroup.getSystemName()))
				|| (!userGroup.getSystemName().equals("游客"))) {
			params.put("AllowTheme", HtmlUtil.mapxToRadios("AllowTheme", map,
					userGroup.getAllowTheme()));
			params.put("AllowReply", HtmlUtil.mapxToRadios("AllowReply", map,
					userGroup.getAllowReply()));
			params.put("Verify", HtmlUtil.mapxToRadios("Verify", map, userGroup
					.getVerify()));
		} else {
			params.put("AllowTheme", "不允许");
			params.put("AllowReply", "不允许");
			params.put("Verify", "不允许");
		}
		return params;
	}

	public void editSave() {
		Transaction trans = new Transaction();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID($V("ID"));
		userGroup.fill();
		userGroup.setValue(this.request);
		trans.add(userGroup, OperateType.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Group", userGroup.getID(), userGroup);
			this.response.setLogInfo(1, "设置成功!");
		} else {
			this.response.setLogInfo(0, "操作失败！");
		}
	}

	public static Mapx initSpecailOption(Mapx params) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID(params.getString("ID"));
		userGroup.fill();
		params = userGroup.toMapx();
		Mapx map = new Mapx();
		map.put("Y", "允许");
		map.put("N", "不允许");
		params.put("AllowVisit", HtmlUtil.mapxToRadios("AllowVisit", map,
				userGroup.getAllowVisit()));
		params.put("AllowSearch", HtmlUtil.mapxToRadios("AllowSearch", map,
				userGroup.getAllowSearch()));
		params.put("AllowHeadImage", HtmlUtil.mapxToRadios("AllowHeadImage",
				map, userGroup.getAllowHeadImage()));
		params.put("AllowUserInfo", HtmlUtil.mapxToRadios("AllowUserInfo", map,
				userGroup.getAllowUserInfo()));
		params.put("AllowNickName", HtmlUtil.mapxToRadios("AllowNickName", map,
				userGroup.getAllowNickName()));
		String sql = "select a.Name,b.GroupID from ZCForumGroup a,ZCAdminGroup b where a.SiteID=? and a.ID=b.GroupID and a.type='2' and a.SystemName<>'系统管理员'";
		DataTable dt = new QueryBuilder(sql, SiteID).executeDataTable();
		params.put("AdminGroup", HtmlUtil.dataTableToOptions(dt, userGroup
				.getRadminID()));
		return params;
	}

	public void editSpecialSave() {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		Transaction trans = new Transaction();
		ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
		userGroup.setID($V("ID"));
		userGroup.fill();
		userGroup.setValue(this.request);
		ZCAdminGroupSchema newGroup = new ZCAdminGroupSchema();
		newGroup.setGroupID(userGroup.getID());

		if (newGroup.fill()) {
			if ($V("RadminID").equals("0")) {
				trans.add(newGroup, OperateType.DELETE_AND_BACKUP);
				ZCForumMemberSet memberSet = new ZCForumMemberSchema()
						.query(new QueryBuilder(
								"where SiteID=? and UserGroupID=?", SiteID,
								newGroup.getGroupID()));
				for (int i = 0; i < memberSet.size(); i++) {
					memberSet.get(i).setAdminID(0L);
				}
				trans.add(memberSet, OperateType.UPDATE);
			} else {
				ZCAdminGroupSchema adminGroup = new ZCAdminGroupSchema();
				adminGroup.setGroupID($V("RadminID"));
				adminGroup.fill();

				newGroup = adminGroup;
				newGroup.setGroupID(userGroup.getID());
				trans.add(newGroup, OperateType.UPDATE);

				ZCForumMemberSet memberSet = new ZCForumMemberSchema()
						.query(new QueryBuilder(
								"where SiteID=? and UserGroupID=?", SiteID,
								newGroup.getGroupID()));
				for (int i = 0; i < memberSet.size(); i++) {
					memberSet.get(i).setAdminID($V("RadminID"));
				}
				trans.add(memberSet, OperateType.UPDATE);
			}
		} else if (!$V("RadminID").equals("0")) {
			ZCAdminGroupSchema adminGroup = new ZCAdminGroupSchema();
			adminGroup.setGroupID($V("RadminID"));
			adminGroup.fill();

			newGroup = adminGroup;
			newGroup.setGroupID(userGroup.getID());
			trans.add(newGroup, OperateType.INSERT);

			ZCForumMemberSet memberSet = new ZCForumMemberSchema()
					.query(new QueryBuilder("where SiteID=? and UserGroupID=?",
							SiteID, newGroup.getGroupID()));
			for (int i = 0; i < memberSet.size(); i++) {
				memberSet.get(i).setAdminID($V("RadminID"));
			}
			trans.add(memberSet, OperateType.UPDATE);
		}

		trans.add(userGroup, OperateType.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Group", userGroup.getID(), userGroup);
			this.response.setLogInfo(1, "设置成功!");
		} else {
			this.response.setLogInfo(0, "操作失败！");
		}
	}
}

/*
 * com.xdarkness.bbs.admin.ForumUserGroupOption JD-Core Version: 0.6.0
 */