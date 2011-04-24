package com.xdarkness.bbs.admin;

import java.util.Date;

import com.xdarkness.bbs.ForumUtil;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.schema.ZCForumSchema;
import com.xdarkness.schema.ZCForumSet;
import com.xdarkness.schema.ZCPostSchema;
import com.xdarkness.schema.ZCPostSet;
import com.xdarkness.schema.ZCThemeSchema;
import com.xdarkness.schema.ZCThemeSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;

public class ForumAdmin extends Page {
	public static Mapx init(Mapx params) {
		return params;
	}

	public static Mapx initAddDialog(Mapx params) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		DataTable dt = new QueryBuilder(
				"select Name,ID from ZCForum where SiteID=? and ParentID=0 order by orderflag",
				SiteID).executeDataTable();
		params.put("ParentForum", HtmlUtil.dataTableToOptions(dt));
		return params;
	}

	public static Mapx initEditDialog(Mapx params) {
		String ID = params.getString("ID");
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		DataTable dt1 = new QueryBuilder(
				"select ParentID from ZCForum where SiteID=? and ID=?", ID)
				.executeDataTable();
		DataTable dt = new QueryBuilder(
				"select Name,ID from ZCForum where SiteID=? and ParentID=0 order by orderflag",
				SiteID).executeDataTable();
		params.put("ParentForum", HtmlUtil.dataTableToOptions(dt, dt1
				.getString(0, "ParentID")));
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		QueryBuilder qb = new QueryBuilder(
				"select ID,ParentID,Name,Info,Addtime,Type,ForumAdmin,ThemeCount,PostCount,'' as Expand,'' as TreeLevel,'' as EditButton from ZCForum where  SiteID=? order by OrderFlag",
				SiteID);
		DataTable dt = qb.executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if ("group".equals(dt.get(i, "Type"))) {
				dt.set(i, "Expand", "Y");
				dt.set(i, "TreeLevel", "0");
			} else {
				dt.set(i, "Expand", "N");
				dt.set(i, "TreeLevel", "1");
			}
			if (dt.getString(i, "ForumAdmin").endsWith(",")) {
				int index = dt.getString(i, "ForumAdmin").lastIndexOf(",");
				dt.set(i, "ForumAdmin", dt.getString(i, "ForumAdmin")
						.substring(0, index));
			}
		}
		dga.bindData(dt);
	}

	public void add() {
		String name = $V("Name");
		ZCForumSchema forum = new ZCForumSchema();
		forum.setName(name);
		forum.setValue(this.request);
		forum.setID(NoUtil.getMaxID("ForumID"));
		if (forum.getParentID() == 0L)
			forum.setType("group");
		else {
			forum.setType("forum");
		}
		forum.setSiteID(ForumUtil.getCurrentBBSSiteID());
		forum.setStatus("0");
		forum.setThemeCount(0);
		forum.setPostCount(0);
		forum.setTodayPostCount(0);
		forum.setVisible("Y");
		forum.setLocked("N");
		forum.setUnLockID("0");
		forum.setReplyPost("Y");
		forum.setVerify("N");
		forum.setAllowTheme("Y");
		forum.setAnonyPost("N");
		forum.setEditPost("Y");
		forum.setRecycle("N");
		forum.setAllowHTML("N");
		forum.setAllowFace("Y");
		forum.setUnPasswordID("0");

		forum.setOrderFlag(OrderUtil.getDefaultOrder());
		forum.setAddTime(new Date());
		forum.setAddUser(User.getUserName());
		Transaction trans = new Transaction();
		trans.add(forum, OperateType.INSERT);
		if (trans.commit()) {
			CacheManager.set("Forum", "Forum", forum.getID(), forum);
			this.response.setLogInfo(1, "操作成功!");
		} else {
			this.response.setLogInfo(0, "操作失败!");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		long SiteID = ForumUtil.getCurrentBBSSiteID();

		ZCForumSchema forumCheck = new ZCForumSchema();
		ZCForumSet set = forumCheck.query(new QueryBuilder("where id in(" + ids
				+ ")"));
		for (int i = 0; i < set.size(); i++) {
			forumCheck = set.get(i);
			if (forumCheck.getParentID() == 0L) {
				long count = new QueryBuilder(
						"select count(*) from ZCForum where SiteID=? and ParentID=? and ID not in ("
								+ ids + ")", SiteID, forumCheck.getID())
						.executeLong();
				if (count > 0L) {
					this.response.setStatus(0);
					this.response.setMessage("不能删除分区\"" + forumCheck.getName()
							+ "\",该分区下还有未被选中的板块!");
					return;
				}
			}
		}

		String[] IDs = getForumIDs(ids);
		Transaction trans = new Transaction();
		for (int n = 0; n < IDs.length; n++) {
			ZCForumSchema forum = new ZCForumSchema();
			forum.setID(IDs[n]);
			forum.fill();
			trans.add(forum, OperateType.DELETE_AND_BACKUP);
			if (forum.getParentID() == 0L) {
				ZCForumSet forumSet = forum
						.query(new QueryBuilder(
								"where SiteID=? and ParentID=?", SiteID, forum
										.getID()));
				trans.add(forumSet, OperateType.DELETE_AND_BACKUP);

				for (int i = 0; i < forumSet.size(); i++) {
					ZCThemeSchema theme = new ZCThemeSchema();
					theme.setID(forumSet.get(i).getID());
					ZCThemeSet themeSet = theme.query();
					trans.add(themeSet, OperateType.DELETE_AND_BACKUP);
					ZCPostSchema post = new ZCPostSchema();

					for (int j = 0; j < themeSet.size(); j++) {
						post.setID(themeSet.get(i).getID());
						ZCPostSet postSet = post.query();
						trans.add(postSet, OperateType.DELETE_AND_BACKUP);
					}
				}
			} else {
				ZCThemeSchema theme = new ZCThemeSchema();
				theme.setForumID(forum.getID());
				ZCThemeSet themeSet = theme.query();
				trans.add(themeSet, OperateType.DELETE_AND_BACKUP);
				ZCPostSchema post = new ZCPostSchema();

				for (int i = 0; i < themeSet.size(); i++) {
					post.setThemeID(themeSet.get(i).getID());
					ZCPostSet postSet = post.query();
					trans.add(postSet, OperateType.DELETE_AND_BACKUP);
				}
			}
		}
		if (trans.commit()) {
			for (int n = 0; n < IDs.length; n++) {
				CacheManager.remove("Forum", "Forum", IDs[n]);
			}
			this.response.setLogInfo(1, "删除成功!");
		} else {
			this.response.setLogInfo(0, "操作失败!");
		}
	}

	public void dg1Edit() {
		Transaction trans = new Transaction();
		ZCForumSchema forum = new ZCForumSchema();
		forum.setID($V("ID"));
		forum.fill();
		forum.setName($V("Name"));

		if (($V("ForumAdmin").trim().length() == 0)
				|| (ForumUtil.isExistMember($V("ForumAdmin")))) {
			if (($V("ForumAdmin").trim().length() == 0)
					|| (isExistAdmin(trans, $V("ForumAdmin"), forum))) {
				forum.setForumAdmin($V("ForumAdmin") + ",");
				ForumUtil.addAdminID(trans, forum.getID()+"", forum
						.getForumAdmin());
			} else {
				this.response.setLogInfo(0, "请勿将分区版主重复设置在板块下");
				return;
			}
		} else {
			this.response.setLogInfo(0, "输入了不存在的用户名");
			return;
		}

		trans.add(forum, OperateType.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Forum", forum.getID(), forum);
			this.response.setLogInfo(1, "操作成功！");
		} else {
			this.response.setLogInfo(0, "操作失败！");
		}
	}

	public void editSave() {
		Transaction trans = new Transaction();
		ZCForumSchema forum = new ZCForumSchema();
		forum.setID($V("ID"));
		forum.fill();
		forum.setValue(this.request);
		if (($V("ForumAdmin").trim().length() == 0)
				|| (isExistAdmin(trans, $V("ForumAdmin"), forum))) {
			forum.setForumAdmin($V("ForumAdmin") + ",");
			ForumUtil.addAdminID(trans, forum.getID()+"", forum.getForumAdmin());
		} else {
			this.response.setLogInfo(0, "请勿将分区版主重复设置在板块下");
			return;
		}
		trans.add(forum, OperateType.UPDATE);
		if (trans.commit()) {
			CacheManager.set("Forum", "Forum", forum.getID(), forum);
			this.response.setLogInfo(1, "操作成功！");
		} else {
			this.response.setLogInfo(0, "操作失败");
		}
	}

	public void isGroup() {
		String id = $V("ID");
		ZCForumSchema forum = new ZCForumSchema();
		forum.setID(id);
		forum.fill();
		if (forum.getParentID() == 0L)
			this.response.setStatus(0);
		else
			this.response.setStatus(1);
	}

	private String[] getForumIDs(String ids) {
		long SiteID = ForumUtil.getCurrentBBSSiteID();
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return null;
		}
		QueryBuilder qb = new QueryBuilder(
				"select ID from ZCForum where SiteID=? and (ID in (" + ids
						+ ") and ParentID not in (" + ids + ")) or (ID in ("
						+ ids + ") and ParentID=0)", SiteID);
		DataTable dt = qb.executeDataTable();
		String[] IDs = new String[dt.getRowCount()];
		for (int i = 0; i < dt.getRowCount(); i++) {
			IDs[i] = dt.getString(i, "ID");
		}
		return IDs;
	}

	private boolean isExistAdmin(Transaction trans, String forumAdmins,
			ZCForumSchema forum) {
		String[] admins = forumAdmins.split(",");
		if (forum.getParentID() == 0L) {
			DataTable dt = new QueryBuilder(
					"select ForumAdmin,ID from ZCForum where ParentID=?", forum
							.getID()).executeDataTable();
			for (int i = 0; i < dt.getRowCount(); i++)
				for (int j = 0; j < admins.length; j++) {
					String ParentAdmin = dt.getString(i, "ForumAdmin");
					if (ParentAdmin.indexOf(admins[j] + ",") >= 0) {
						ParentAdmin = ParentAdmin.replaceAll(admins[j] + ",",
								"");
						trans.add(new QueryBuilder(
								"update ZCForum set ForumAdmin=? where ID=?",
								ParentAdmin, dt.getString(i, "ID")));
					}
				}
		} else {
			QueryBuilder qb = new QueryBuilder(
					"select ForumAdmin from ZCForum where ID=?", forum
							.getParentID());
			String groupAdmin = qb.executeString();
			if (XString.isEmpty(groupAdmin)) {
				return true;
			}
			for (int i = 0; i < admins.length; i++) {
				if (groupAdmin.indexOf(admins[i] + ",") >= 0) {
					return false;
				}
			}
		}
		return true;
	}
}

/*
 * com.xdarkness.bbs.admin.ForumAdmin JD-Core Version: 0.6.0
 */