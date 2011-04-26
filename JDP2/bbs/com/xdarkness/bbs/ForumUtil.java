package com.xdarkness.bbs;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.xdarkness.bbs.admin.ForumScore;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.schema.ZCForumConfigSchema;
import com.xdarkness.schema.ZCForumGroupSchema;
import com.xdarkness.schema.ZCForumMemberSchema;
import com.xdarkness.schema.ZCForumMemberSet;
import com.xdarkness.schema.ZCForumSchema;
import com.xdarkness.schema.ZCForumSet;
import com.xdarkness.schema.ZCThemeSchema;
import com.xdarkness.schema.ZCThemeSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;

public class ForumUtil {
	private static final String YES = "Y";

	public static boolean getForumStatus(String SiteID) {
		ZCForumConfigSchema config = ForumCache.getConfigBySiteID(SiteID);
		if (config == null) {
			return false;
		}
		return "Y".equalsIgnoreCase(config.getTempCloseFlag());
	}

	public static boolean getForumLock(String ForumID) {
		ZCForumSchema forum = ForumCache.getForum(ForumID);
		if (forum == null) {
			return false;
		}
		return "Y".equalsIgnoreCase(forum.getLocked());
	}

	public static boolean getForumPassword(String ForumID) {
		ZCForumSchema forum = ForumCache.getForum(ForumID);
		if (forum == null) {
			return false;
		}
		return XString.isNotEmpty(forum.getPassword());
	}

	public static boolean getForumDisplay(String ForumID) {
		ZCForumSchema forum = ForumCache.getForum(ForumID);
		if (forum == null) {
			return false;
		}
		return "Y".equalsIgnoreCase(forum.getVisible());
	}

	public static boolean isInitDB(String SiteID) {
		ZCForumConfigSchema config = ForumCache.getConfigBySiteID(SiteID);

		return config != null;
	}

	public static void allowVisit(String SiteID) {
		if (!isInitDB(SiteID)) {
			return;
		}
		createBBSUser(SiteID);
		ForumPriv priv = new ForumPriv(SiteID);
		if (priv.hasPriv("AllowVisit"))
			User.setValue("AllowMemberVisit", "Y");
		else
			User.setValue("AllowMemberVisit", "N");
	}

	public static void isUnLockGroup(String ForumID) {
		if ((XString.isEmpty(User.getUserName()))
				|| (XString.isEmpty(ForumID))) {
			return;
		}
		ZCForumSchema forum = ForumCache.getForum(ForumID);
		if (XString.isEmpty(forum.getUnLockID())) {
			return;
		}
		ZCForumMemberSchema member = new ZCForumMemberSchema();
		member.setUserName(User.getUserName());
		member.fill();
		String[] ids = forum.getUnLockID().split(",");
		for (int i = 0; i < ids.length; i++) {
			long id = Long.parseLong(ids[i]);
			if ((id == member.getUserGroupID()) || (id == member.getAdminID())
					|| (id == member.getDefinedID())) {
				User.setValue("UnLockGroup", "Y");
				return;
			}
		}
		User.setValue("UnLockGroup", "N");
	}

	public static void isUnPasswordGroup(String ForumID) {
		if (XString.isEmpty(User.getUserName())) {
			return;
		}
		ZCForumSchema forum = ForumCache.getForum(ForumID);
		ZCForumMemberSchema member = new ZCForumMemberSchema();
		member.setUserName(User.getUserName());
		member.fill();
		String[] ids = forum.getUnPasswordID().split(",");
		for (int i = 0; i < ids.length; i++) {
			long id = Long.parseLong(ids[i]);
			if ((id == member.getUserGroupID()) || (id == member.getAdminID())
					|| (id == member.getDefinedID())) {
				User.setValue("pass_" + ForumID, "Y");
				return;
			}
		}
		User.setValue("pass_" + ForumID, "N");
	}

	public static boolean isOperateMember(String userName) {
		if ((XString.isNotEmpty(userName)) && (isExistMember(userName))) {
			if (User.getUserName().equals("admin")) {
				return true;
			}
			if (User.getUserName().equals(userName)) {
				return true;
			}
			ZCForumMemberSchema member = new ZCForumMemberSchema();
			ZCForumGroupSchema group = null;
			ZCForumMemberSchema currentMember = new ZCForumMemberSchema();
			ZCForumGroupSchema currentGroup = null;
			member.setUserName(userName);
			member.fill();

			currentMember.setUserName(User.getUserName());
			currentMember.fill();

			group = ForumCache.getGroup(member.getAdminID());
			currentGroup = ForumCache.getGroup(currentMember.getAdminID());

			if (currentGroup.getSystemName().equals("系统管理员")) {
				return true;
			}
			if (member.getAdminID() == 0L)
				return true;
			if ((XString.isNotEmpty(currentGroup.getSystemName()))
					&& (currentGroup.getSystemName().equals("超级版主"))
					&& (!group.getSystemName().equals("超级版主"))
					&& (!group.getSystemName().equals("系统管理员"))) {
				return true;
			}
		}

		return false;
	}

	public static void userGroupChange(ZCForumMemberSchema member) {
		long SiteID = member.getSiteID();
		long ForumScore = member.getForumScore();
		QueryBuilder qb = new QueryBuilder(
				"select ID from ZCForumGroup where SiteID=? and LowerScore<=? and UpperScore>?",
				SiteID, ForumScore);
		qb.add(ForumScore);
		DataTable dt = qb.executeDataTable();
		if (dt.getRowCount() > 0)
			member.setUserGroupID(dt.getLong(0, "ID"));
	}

	public static void userGroupChange(ZCForumMemberSet memberSet) {
		for (int i = 0; i < memberSet.size(); i++)
			userGroupChange(memberSet.get(i));
	}

	public static void createBBSUser(Transaction trans, String userName,
			String SiteID) {
		if (!isInitDB(SiteID)) {
			return;
		}
		if (XString.isEmpty(userName)) {
			return;
		}
		ZCForumMemberSchema forumMember = new ZCForumMemberSchema();
		forumMember.setUserName(userName);
		if (forumMember.fill()) {
			return;
		}
		forumMember.setThemeCount(0L);
		forumMember.setReplyCount(0L);
		forumMember.setAdminID(0L);
		forumMember.setUserGroupID(0L);
		if ("Y".equalsIgnoreCase(Config.getValue("PublicUseBBS"))) {
			forumMember.setSiteID(0L);
			forumMember.setForumScore(new ForumScore(0L).InitScore);
		} else {
			forumMember.setSiteID(SiteID);
			forumMember.setForumScore(new ForumScore(SiteID).InitScore);
		}
		forumMember.setUseSelfImage("N");
		forumMember.setHeadImage("Images/SystemHeadImage/HeadImage01.gif");
		forumMember.setAddTime(new Date());
		forumMember.setAddUser(userName);

		userGroupChange(forumMember);
		if (trans != null)
			trans.add(forumMember, OperateType.INSERT);
		else
			forumMember.insert();
	}

	public static void createBBSUser(String SiteID) {
		createBBSUser(null, User.getUserName(), SiteID);
	}

	public static String[] getAdmins(String ForumID) {
		DataTable dt = new QueryBuilder(
				"select a.ForumAdmin,b.ForumAdmin ParentAdmin from ZCForum a,ZCForum b where a.ID=? and b.ID=a.ParentID",
				ForumID).executeDataTable();
		String ForumAdmins = "";
		if (dt.getRowCount() > 0) {
			String ForumAdmin = dt.getString(0, "ForumAdmin");
			if (ForumAdmin.endsWith(",")) {
				int index = ForumAdmin.lastIndexOf(",");
				ForumAdmin = ForumAdmin.substring(0, index);
			}
			String ParentAdmin = dt.getString(0, "ParentAdmin");
			if (ParentAdmin.endsWith(",")) {
				int index = ParentAdmin.lastIndexOf(",");
				ParentAdmin = ParentAdmin.substring(0, index);
			}
			if (XString.isNotEmpty(ForumAdmin)) {
				ForumAdmins = ForumAdmin;
				if (XString.isNotEmpty(ParentAdmin)) {
					ForumAdmins = ForumAdmins + "," + ParentAdmin;
				}
			} else if (XString.isNotEmpty(ParentAdmin)) {
				ForumAdmins = ParentAdmin;
			}
		}

		return ForumAdmins.split(",");
	}

	public static int isAdmin(String ForumID, String SiteID) {
		if (XString.isNotEmpty(User.getUserName())) {
			if (User.getUserName().equals("admin")) {
				return 1;
			}
			String sqlAdmin = "select count(*) from ZCForumMember a,ZCForumGroup b where a.SiteID=? and a.UserName='"
					+ User.getUserName()
					+ "' and a.AdminID=b.ID and b.SystemName='系统管理员'";
			int countAdmin = new QueryBuilder(sqlAdmin, SiteID).executeInt();
			if (countAdmin > 0) {
				return 1;
			}

			String sql = "select count(*) from ZCForumMember a,ZCForumGroup b where a.SiteID=? and a.UserName='"
					+ User.getUserName()
					+ "' and a.AdminID=b.ID and b.SystemName='超级版主'";
			int count = new QueryBuilder(sql, SiteID).executeInt();
			if (count > 0) {
				return 2;
			}
			String[] forumAdmins = getAdmins(ForumID);
			for (int i = 0; i < forumAdmins.length; i++) {
				if (forumAdmins[i].equals(User.getUserName())) {
					return 3;
				}
			}
		}
		return 0;
	}

	public static boolean isForumAdmin(String ForumID, String member) {
		String[] forumAdmins = getAdmins(ForumID);
		for (int i = 0; i < forumAdmins.length; i++) {
			if (forumAdmins[i].equals(member)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isForumAdmin(String ForumID) {
		return isForumAdmin(ForumID, User.getUserName());
	}

	public static boolean isOperateTheme(String ForumID, String SiteID) {
		if (!User.isLogin()) {
			return false;
		}
		String sql = "select count(*) from ZCForumMember a,ZCForumGroup b where a.SiteID=? and a.UserName='"
				+ User.getUserName()
				+ "' and a.AdminID=b.ID and b.SystemName='版主'";
		int count = new QueryBuilder(sql, SiteID).executeInt();
		if (count > 0) {
			String[] forumAdmins = getAdmins(ForumID);
			for (int i = 0; i < forumAdmins.length; i++) {
				if (forumAdmins[i].equals(User.getUserName())) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	public static String operateThemeButton(String ForumID, String SiteID) {
		StringBuffer sb = new StringBuffer();
		if (isOperateTheme(ForumID, SiteID)) {
			ForumPriv priv = new ForumPriv(SiteID, ForumID);
			if (priv.hasPriv("RemoveTheme")) {
				sb.append("<a href='#;' onclick='del()'>删除主题</a>");
			}
			if (priv.hasPriv("MoveTheme")) {
				sb.append("<a href='#;' onclick='move()'>移动主题</a>");
			}
			if (priv.hasPriv("BrightTheme")) {
				sb.append("<a href='#;' onclick='bright()'>高亮/取消显示</a>");
			}
			if (priv.hasPriv("TopTheme")) {
				sb.append("<a href='#;' onclick='top()'>置顶/解除置顶</a>");
			}
			if (priv.hasPriv("UpOrDownTheme")) {
				sb.append("<a href='#;' onclick='upOrDown()'>提升/下沉主题</a>");
			}
			if (priv.hasPriv("BestTheme")) {
				sb.append("<a href='#;' onclick='best()'>设为/取消精华</a>");
			}
		}
		return sb.toString();
	}

	public static boolean isExistMember(String members) {
		if (!XString.checkID(members)) {
			LogUtil.warn("可能的SQL注入,ForumUtil.isExistMember:" + members);
			return true;
		}
		String[] ForumAdmins = members.trim().split(",");
		StringBuffer sql = new StringBuffer(
				"select count(*) from ZDMember where UserName='"
						+ ForumAdmins[0] + "'");
		for (int i = 1; i < ForumAdmins.length; i++) {
			sql.append(" or UserName='" + ForumAdmins[i] + "'");
		}
		int count = new QueryBuilder(sql.toString()).executeInt();

		return count == ForumAdmins.length;
	}

	public static void addAdminID(Transaction trans, String ForumID,
			String ForumAdmin) {
		String[] ForumAdmins = ForumAdmin.trim().split(",");
		ZCForumSchema forum = new ZCForumSchema();
		forum.setID(ForumID);
		forum.fill();
		checkDelAdmin(trans, ForumAdmins, forum);

		long groupID = ForumCache.getGroupBySystemName(
				String.valueOf(getCurrentBBSSiteID()), "版主").getID();
		for (int i = 0; i < ForumAdmins.length; i++) {
			ZCForumMemberSchema member = new ZCForumMemberSchema();
			member.setUserName(ForumAdmins[i]);
			member.fill();
			if (member.getAdminID() == 0L) {
				member.setAdminID(groupID);
			} else {
				long id = ForumCache.getGroupBySystemName(
						String.valueOf(getCurrentBBSSiteID()), "禁止访问").getID();
				if (member.getAdminID() == id) {
					member.setAdminID(groupID);
				}
			}
			trans.add(member, OperateType.UPDATE);
		}
	}

	public static String getBBSName(String SiteID) {
		ZCForumConfigSchema config = ForumCache.getConfigBySiteID(SiteID);
		if (config == null) {
			return null;
		}
		return config.getName();
	}

	public static String initPriv(String ForumID, String SiteID) {
		StringBuffer sb = new StringBuffer();
		ForumPriv priv = new ForumPriv(SiteID);
		if (User.isLogin()) {
			sb.append("<a href='Logout.jsp?SiteID=" + SiteID + "'>退出</a>");
		}
		if (priv.hasPriv("AllowSearch")) {
			sb.append(" | <a href='ThemeSearch.jsp?SiteID=" + SiteID
					+ "'>搜索</a>");
		}
		if (priv.hasPriv("AllowPanel")) {
			sb.append(" | <a href='ControlPanel.jsp?SiteID=" + SiteID
					+ "'>控制面板</a> | <a href='MyThemes.jsp?SiteID=" + SiteID
					+ "'>我的话题</a> ");
		}
		if (priv.hasPriv("Admin")) {
			sb.append(" | <a href='MasterPanel.jsp?SiteID=" + SiteID
					+ "'>版主管理面版</a>");
		}
		if (XString.isNotEmpty(ForumID)) {
			ForumRule rule = new ForumRule(ForumID);
			if ((rule.getRule("AllowTheme")) || (isAdmin(ForumID, SiteID) > 0)) {
				sb.append(" |<a href='ThemeAdd.jsp?SiteID=" + SiteID
						+ "&ForumID=" + ForumID + "'>发表新话题</a>");
			}
		}

		return sb.toString();
	}

	public static String initPriv(String SiteID) {
		return initPriv(null, SiteID);
	}

	public static void adminPriv(Mapx map) {
		String SiteID = map.getString("SiteID");
		ForumPriv priv = new ForumPriv(SiteID);
		if (priv.hasPriv("AllowEditUser")) {
			map.put("AllowEditUser", "<a href='MasterPanel.jsp?SiteID="
					+ SiteID + "'>编辑用户</a>");
		}

		if (priv.hasPriv("AllowEditForum")) {
			map.put("AllowEditForum", "<a href='ForumEdit.jsp?SiteID=" + SiteID
					+ "'>板块编辑</a>");
		}
		if (priv.hasPriv("AllowVerfyPost"))
			map.put("AllowVerfyPost", "<a href='PostAudit.jsp?SiteID=" + SiteID
					+ "'>帖子审核</a>");
	}

	public static void changeLastTheme(ZCForumSchema originalForum,
			ZCForumSchema targetForum, String ids) {
		if (!XString.checkID(ids)) {
			LogUtil.warn("可能的SQL注入,ForumUtil.changeLastTheme:" + ids);
			return;
		}
		String sqlMin = "select ID from ZCTheme where status='Y' and ForumID="
				+ originalForum.getID() + " and ID in(" + ids
				+ ") order by ID desc";
		DataTable dtMin = new QueryBuilder(sqlMin).executePagedDataTable(1, 0);
		String sqlNext = "select count(*) from ZCTheme where status='Y' and VerifyFlag='Y' and ForumID="
				+ originalForum.getID()
				+ " and ID>"
				+ dtMin.getString(0, "ID")
				+ " and ID not in(" + ids + ")";
		int count = new QueryBuilder(sqlNext).executeInt();
		if (count == 0) {
			String sqlOriginal = "select Subject,AddUser,ID from ZCTheme where status='Y' and VerifyFlag='Y' and ForumID="
					+ originalForum.getID()
					+ " and ID not in("
					+ ids
					+ ") order by ID desc";
			DataTable dt = new QueryBuilder(sqlOriginal).executePagedDataTable(
					1, 0);
			if (dt.getRowCount() > 0) {
				originalForum.setLastPost(dt.getString(0, "Subject"));
				originalForum.setLastPoster(dt.getString(0, "AddUser"));
				originalForum.setLastThemeID(dt.getString(0, "ID"));
			} else {
				originalForum.setLastPost("");
				originalForum.setLastPoster("");
				originalForum.setLastThemeID("");
			}
		}
		if (targetForum != null) {
			String sqlTarget = "select count(*) from ZCTheme where status='Y' and VerifyFlag='Y' and ForumID="
					+ targetForum.getID()
					+ " and ID>"
					+ dtMin.getString(0, "ID");
			int countTarget = new QueryBuilder(sqlTarget).executeInt();
			if (countTarget == 0) {
				String sql = "select Subject,AddUser,ID from ZCTheme where ID="
						+ dtMin.getString(0, "ID");
				DataTable dt = new QueryBuilder(sql)
						.executePagedDataTable(1, 0);
				if (dt.getRowCount() > 0) {
					targetForum.setLastPost(dt.getString(0, "Subject"));
					targetForum.setLastPoster(dt.getString(0, "AddUser"));
					targetForum.setLastThemeID(dt.getString(0, "ID"));
				} else {
					targetForum.setLastPost("");
					targetForum.setLastPoster("");
					targetForum.setLastThemeID("");
				}
			}
		}
	}

	public static void changeLastTheme(ZCForumSchema originalForum, String ids) {
		changeLastTheme(originalForum, null, ids);
	}

	public static long getCurrentBBSSiteID() {
		if ("Y".equalsIgnoreCase(Config.getValue("PublicUseBBS"))) {
			return 0L;
		}
		int count = new QueryBuilder("select ID from ZCSite").executeInt();
		if (count > 0) {
			return ApplicationPage.getCurrentSiteID();
		}
		return 0L;
	}

	public static String getCurrentBBSSiteID(String SiteID) {
		if ("Y".equalsIgnoreCase(Config.getValue("PublicUseBBS"))) {
			SiteID = "0";
		} else if (XString.isNotEmpty(User.getUserName())) {
			if ((User.isManager()) && (User.isLogin())) {
				SiteID = getCurrentBBSSiteID()+"";
			} else if (!"admin".equals(User.getUserName())) {
				SiteID = new QueryBuilder(
						"select SiteID from ZCForumMember where UserName='"
								+ User.getUserName() + "'").executeString();
			}

		} else if (XString.isEmpty(SiteID)) {
			String sql = "select ID from ZCSite";
			DataTable dt = new QueryBuilder(sql).executePagedDataTable(1, 0);
			if (dt.getRowCount() > 0)
				SiteID = dt.getString(0, "ID");
			else {
				SiteID = "0";
			}

		}

		return SiteID;
	}

	public static boolean isNotSendTheme(String SiteID, String ForumID) {
		return !isSendTheme(SiteID, ForumID);
	}

	public static boolean isSendTheme(String SiteID, String ForumID) {
		ForumPriv priv = new ForumPriv(SiteID);
		ForumRule rule = new ForumRule(ForumID);

		return ((rule.getRule("AllowTheme")) || (isAdmin(ForumID, SiteID) > 0))
				&& (priv.hasPriv("AllowTheme"));
	}

	public static boolean isNotReplyPost(String SiteID, String ForumID) {
		return !isReplyPost(SiteID, ForumID);
	}

	public static boolean isReplyPost(String SiteID, String ForumID) {
		ForumPriv priv = new ForumPriv(SiteID);
		ForumRule rule = new ForumRule(ForumID);

		return ((rule.getRule("ReplyPost")) || (isAdmin(ForumID, SiteID) > 0))
				&& (priv.hasPriv("AllowReply"));
	}

	public static boolean isEditPost(String SiteID, String ForumID,
			String UserName) {
		ForumPriv priv = new ForumPriv(SiteID);
		ForumRule rule = new ForumRule(ForumID);
		if ((isAdmin(ForumID, SiteID) > 0) && (priv.hasPriv("AllowEdit"))) {
			return true;
		}

		return (rule.getRule("EditPost"))
				&& (XString.isNotEmpty(User.getUserName()))
				&& (User.getUserName().equals(UserName));
	}

	public static int getValueOfMemberSet(ZCForumMemberSet memberSet,
			ZCForumMemberSchema member) {
		for (int j = 0; j < memberSet.size(); j++) {
			if (member.getUserName().equals(memberSet.get(j).getUserName())) {
				return j;
			}
		}
		return -1;
	}

	public static int getValueOfForumSet(ZCForumSet forumSet,
			ZCForumSchema forum) {
		for (int j = 0; j < forumSet.size(); j++) {
			if (forum.getID() == forumSet.get(j).getID()) {
				return j;
			}
		}
		return -1;
	}

	public static int getValueOfThemeSet(ZCThemeSet themeSet,
			ZCThemeSchema theme) {
		for (int j = 0; j < themeSet.size(); j++) {
			if (theme.getID() == themeSet.get(j).getID()) {
				return j;
			}
		}
		return -1;
	}

	private static void checkDelAdmin(Transaction trans, String[] ForumAdmins,
			ZCForumSchema forum) {
		String admins = XString.isEmpty(forum.getForumAdmin()) ? "" : forum
				.getForumAdmin();
		String[] oldAdmins = admins.split(",");
		String checkAdmin = "";
		for (int i = 0; i < oldAdmins.length; i++) {
			int j = 0;
			while (!oldAdmins[i].equals(ForumAdmins[j])) {
				j++;
				if (j < ForumAdmins.length) {
					continue;
				}

				checkAdmin = checkAdmin + oldAdmins[i] + ",";
			}
		}
		if (checkAdmin.length() == 0) {
			return;
		}
		if (!XString.checkID(checkAdmin)) {
			LogUtil.warn("可能的SQL注入,ForumUtil.checkDelAdmin:" + checkAdmin);
			return;
		}
		String[] checkAdmins = checkAdmin.split(",");
		for (int i = 0; i < checkAdmins.length; i++) {
			String sql = "select count(*) from ZCForum where ForumAdmin like '%"
					+ checkAdmins[i] + "," + "%'";
			int count = new QueryBuilder(sql).executeInt();
			if (count == 1) {
				ZCForumMemberSchema member = new ZCForumMemberSchema();
				member.setUserName(checkAdmins[i]);
				member.fill();
				QueryBuilder qb = new QueryBuilder(
						"select SystemName from ZCForumGroup where ID=?",
						member.getAdminID());
				DataTable dtType = qb.executeDataTable();
				if ((dtType.getString(0, "SystemName").equals("超级版主"))
						|| (dtType.getString(0, "SystemName").equals("系统管理员"))) {
					return;
				}
				member.setAdminID(0L);

				trans.add(member, OperateType.UPDATE);
			}
		}
	}

	public static String getCurrentName(HttpServletRequest request) {
		String siteID = request.getParameter("SiteID");
		if (XString.isEmpty(siteID)) {
			siteID = request.getParameter("site");
			if (XString.isEmpty(siteID)) {
				siteID = (String) User.getValue("SiteID");
				if (XString.isEmpty(siteID)) {
					return null;
				}
			}
		}
		Mapx map = CacheManager.getMapx("Forum", "Config");
		Object[] vs = map.valueArray();
		for (int i = 0; i < vs.length; i++) {
			ZCForumConfigSchema config = (ZCForumConfigSchema) vs[i];
			if (config.getSiteID() == Long.parseLong(siteID)) {
				return config.getName();
			}
		}
		return "";
	}
}

/*
 * com.xdarkness.bbs.ForumUtil JD-Core Version: 0.6.0
 */