package com.xdarkness.platform.page;

import java.util.Date;

import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.User;
import com.xdarkness.framework.cache.CacheManager;
import com.xdarkness.framework.data.Transaction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.XString;
import com.xdarkness.platform.Cms;
import com.xdarkness.platform.Priv;
import com.xdarkness.platform.RolePriv;
import com.xdarkness.platform.pub.PlatformCache;
import com.xdarkness.schema.ZDPrivilegeSchema;
import com.xdarkness.schema.ZDRoleSchema;
import com.xdarkness.schema.ZDUserRoleSchema;
import com.xdarkness.schema.ZDUserRoleSet;

public class RoleTabBasicPage extends Page {
	public static Mapx init(Mapx params) {
		String RoleCode = params.getString("RoleCode");
		if ((RoleCode == null) || ("".equals(RoleCode))) {
			RoleCode = params.getString("Cookie.Role.LastRoleCode");
			if ((RoleCode == null) || ("".equals(RoleCode))) {
				return params;
			}
		}
		ZDRoleSchema role = new ZDRoleSchema();
		role.setRoleCode(RoleCode);
		if (!role.fill()) {
			LogUtil.warn("查询不到该角色！！！");
			return params;
		}
		Mapx map = role.toMapx();
		map
				.put(
						"BranchName",
						new QueryBuilder(
								"select name from zdbranch where BranchInnerCode=? Order by OrderFlag",
								role.getBranchInnerCode()).executeString());
		return map;
	}

	public static Mapx initEditDialog(Mapx params) {
		ZDRoleSchema role = new ZDRoleSchema();
		role.setRoleCode(params.get("RoleCode").toString());
		if (!role.fill()) {
			LogUtil.info("没有查询到该角色！！！");
			return params;
		}
		Mapx map = role.toMapx();
		return map;
	}

	public static Mapx initDialog(Mapx params) {
		params.put("BranchInnerCode", Cms.getBranchOptions());
		return params;
	}

	public void add() {
		ZDRoleSchema role = new ZDRoleSchema();
		role.setValue(this.request);
		role.setRoleCode(role.getRoleCode().toLowerCase());
		if (role.fill()) {
			this.response.setLogInfo(0, "角色编码" + role.getRoleCode()
					+ "已经存在了，请选择另外的角色编码！");
			return;
		}
		Date currentDate = new Date();
		String currentUserName = User.getUserName();
		role.setAddTime(currentDate);
		role.setAddUser(currentUserName);

		Transaction trans = new Transaction();
		trans.add(role, OperateType.INSERT);

		if (trans.commit()) {
			RolePriv.updateAllPriv(role.getRoleCode());
			CacheManager.set("Platform", "Role", role.getRoleCode(), role);
			this.response.setLogInfo(1, "新建成功");
		} else {
			this.response.setLogInfo(0, "新建失败");
		}
	}

	public void save() {
		ZDRoleSchema role = new ZDRoleSchema();
		role.setRoleCode($V("RoleCode"));
		role.fill();

		role.setValue(this.request);
		role.setModifyTime(new Date());
		role.setModifyUser(User.getUserName());

		if (role.update()) {
			CacheManager.set("Platform", "Role", role.getRoleCode(), role);
			this.response.setLogInfo(1, "修改成功");
		} else {
			this.response.setLogInfo(0, "修改失败");
		}
	}

	public void del() {
		String RoleCode = this.request.getString("RoleCode");
		Transaction trans = new Transaction();
		ZDRoleSchema role = new ZDRoleSchema();
		role.setRoleCode(RoleCode);
		role.fill();
		if ("everyone".equalsIgnoreCase(RoleCode)) {
			this.response.setLogInfo(0, "everyone为系统自带的角色，不能删除！");
			UserLogPage.log("User", "DelROLE", "删除角色:" + role.getRoleName() + "失败",
					this.request.getClientIP());
			return;
		}
		if ("admin".equalsIgnoreCase(RoleCode)) {
			this.response.setLogInfo(0, "admin为系统自带的角色，不能删除！");
			UserLogPage.log("User", "DelROLE", "删除角色:" + role.getRoleName() + "失败",
					this.request.getClientIP());
			return;
		}

		trans.add(role, OperateType.DELETE_AND_BACKUP);

		ZDUserRoleSchema userRole = new ZDUserRoleSchema();
		ZDUserRoleSet userRoleSet = userRole.query(new QueryBuilder(
				"where RoleCode =?", RoleCode));

		trans.add(userRoleSet, OperateType.DELETE_AND_BACKUP);

		trans.add(new ZDPrivilegeSchema().query(new QueryBuilder(
				"where OwnerType=? and Owner=?", "R", RoleCode)), OperateType.DELETE_AND_BACKUP);

		if (trans.commit()) {
			PlatformCache.removeRole(role.getRoleCode());

			RolePriv.updateAllPriv(RoleCode);
			UserLogPage.log("User", "DelROLE", "删除角色:" + role.getRoleName() + "成功",
					this.request.getClientIP());
			this.response.setLogInfo(1, "删除成功!");
		} else {
			UserLogPage.log("User", "DelROLE", "删除角色:" + role.getRoleName() + "失败",
					this.request.getClientIP());
			this.response.setLogInfo(0, "删除失败!");
		}
	}

	public static void dg1DataBind(DataGridAction dga) {
		String RoleCode = dga.getParam("RoleCode");
		if ((RoleCode == null) || ("".equals(RoleCode))) {
			RoleCode = dga.getParams().getString("Cookie.Role.LastRoleCode");
			if ((RoleCode == null) || ("".equals(RoleCode))) {
				dga.bindData(new DataTable());
				return;
			}
		}
		QueryBuilder qb = new QueryBuilder(
				"select * from ZDUser where exists (select UserName from ZDUserRole b where b.UserName = ZDUser.UserName and b.RoleCode=?)",
				RoleCode);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dt.insertColumn("RoleNames");
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "RoleNames", Cms.getRoleNames(Priv
					.getRoleCodesByUserName(dt.getString(i, "UserName"))));
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	public static void bindUserList(DataGridAction dga) {
		String roleCode = dga.getParam("RoleCode");
		String searchUserName = dga.getParam("SearchUserName");
		QueryBuilder qb = new QueryBuilder("select * from ZDUser");
		qb.append(" where BranchInnerCode like ?", User.getBranchInnerCode()
				+ "%");
		qb
				.append(
						" and not exists (select '' from zduserrole where zduserrole.roleCode=? and zduserrole.userName=zduser.userName)",
						roleCode);
		if (XString.isNotEmpty(searchUserName)) {
			qb.append(" and (UserName like ?", "%" + searchUserName.trim()
					+ "%");

			qb
					.append(" or realname like ?)", "%" + searchUserName.trim()
							+ "%");
		}
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dt.decodeColumn("Status", UserListPage.STATUS_MAP);
		dga.bindData(dt);
	}

	public void addUserToRole() {
		String RoleCode = $V("RoleCode");
		if (XString.isEmpty(RoleCode)) {
			this.response.setLogInfo(0, "角色不能为空");
			return;
		}
		String UserNameStr = $V("UserNames");
		String[] UserNames = UserNameStr.split(",");
		Date currentDate = new Date();
		String currentUserName = User.getUserName();
		Transaction trans = new Transaction();

		ZDUserRoleSet set = new ZDUserRoleSet();
		for (int i = 0; i < UserNames.length; i++) {
			if (XString.isEmpty(UserNames[i])) {
				continue;
			}
			ZDUserRoleSchema userRole = new ZDUserRoleSchema();
			userRole.setUserName(UserNames[i]);
			userRole.setRoleCode(RoleCode);
			userRole.setAddTime(currentDate);
			userRole.setAddUser(currentUserName);
			set.add(userRole);
		}
		trans.add(set, OperateType.INSERT);
		if (trans.commit()) {
			for (int i = 0; i < set.size(); i++) {
				PlatformCache.addUserRole(set.get(i).getUserName(), set.get(i)
						.getRoleCode());
			}
			this.response.setLogInfo(1, "添加成功");
		} else {
			this.response.setLogInfo(0, "添加失败");
		}
	}

	public void delUserFromRole() {
		String RoleCode = $V("RoleCode");
		String UserNameStr = $V("UserNames");
		String[] UserNames = UserNameStr.split(",");
		Transaction trans = new Transaction();

		ZDUserRoleSet set = new ZDUserRoleSet();
		for (int i = 0; i < UserNames.length; i++) {
			DataTable dt = new QueryBuilder(
					"select RoleCode from ZDUserRole where UserName=? and RoleCode!=?",
					UserNames[i], RoleCode).executeDataTable();
			String[] RoleCodes = new String[dt.getRowCount()];
			for (int j = 0; j < dt.getRowCount(); j++) {
				RoleCodes[j] = dt.getString(j, 0);
			}
			ZDUserRoleSchema userRole = new ZDUserRoleSchema();
			userRole.setUserName(UserNames[i]);
			userRole.setRoleCode(RoleCode);
			userRole.fill();
			set.add(userRole);
		}
		trans.add(set, OperateType.DELETE_AND_BACKUP);
		if (trans.commit()) {
			for (int i = 0; i < set.size(); i++) {
				PlatformCache.removeUserRole(set.get(i).getUserName(), set.get(
						i).getRoleCode());
			}
			this.response.setLogInfo(1, "删除成功");
		} else {
			this.response.setLogInfo(0, "删除失败");
		}
	}
}
