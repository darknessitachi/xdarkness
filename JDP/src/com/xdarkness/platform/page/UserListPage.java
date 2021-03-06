package com.xdarkness.platform.page;

import java.util.Date;
import java.util.regex.Pattern;

import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.User;
import com.xdarkness.framework.cache.CacheManager;
import com.xdarkness.framework.data.Transaction;
import com.xdarkness.framework.jaf.JafRequest;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.jaf.controls.TreeAction;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.license.LicenseInfo;
import com.xdarkness.framework.orm.data.DataCollection;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Errorx;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.XString;
import com.xdarkness.platform.Cms;
import com.xdarkness.platform.Priv;
import com.xdarkness.schema.ZDPrivilegeSchema;
import com.xdarkness.schema.ZDUserRoleSchema;
import com.xdarkness.schema.ZDUserRoleSet;
import com.xdarkness.schema.ZDUserSchema;
import com.xdarkness.schema.ZDUserSet;

public class UserListPage extends Page {
	public static final String ADMINISTRATOR = "admin";
	public static final String STATUS_NORMAL = "N";
	public static final String STATUS_STOP = "S";
	public static final Mapx<String, String> STATUS_MAP = new Mapx<String, String>();
	static String Password;
	private static Pattern userPattern;
	private static Pattern idPattern;

	static {
		STATUS_MAP.put("N", "正常");
		STATUS_MAP.put("S", "停用");

		Password = "zvingzving";

		userPattern = Pattern.compile("[\\w@\\.一-龥]{1,20}", 34);

		idPattern = Pattern.compile("[\\w@\\.\\,一-龥]*", 34);
	}

	public static Mapx<String, Object> init(Mapx<String, Object> params) {
		params.put("IsBranchAdmin", HtmlUtil.codeToRadios("IsBranchAdmin",
				"YesOrNo", "N"));
		params.put("Status", HtmlUtil.mapxToRadios("Status", STATUS_MAP, "N"));
		params.put("BranchInnerCode", Cms.getBranchOptions());
		return params;
	}

	public static Mapx<String, Object> initEditDialog(Mapx<String, Object> params) {
		String userName = params.getString("UserName");
		ZDUserSchema user = new ZDUserSchema();
		user.setUserName(userName);
		user.fill();
		params = user.toMapx();
		params.put("IsBranchAdmin", HtmlUtil.codeToRadios("IsBranchAdmin",
				"YesOrNo", user.getIsBranchAdmin()));
		params.put("Status", HtmlUtil.mapxToRadios("Status", STATUS_MAP, user
				.getStatus()));
		params.put("BranchInnerCode", Cms.getBranchOptions(user
				.getBranchInnerCode()));
		params.put("Password", Password);
		return params;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String searchUserName = dga.getParam("SearchUserName");
		QueryBuilder qb = new QueryBuilder("select * from ZDUser ");
		qb.append(" where BranchInnerCode like ? ", User.getBranchInnerCode()
				+ "%");
		if (XString.isNotEmpty(searchUserName)) {
			qb.append(" and (UserName like ? ", "%" + searchUserName.trim()
					+ "%");

			qb.append(" or realname like ? )", "%" + searchUserName.trim()
					+ "%");
		}
		qb.append(" order by AddTime desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dt.decodeColumn("BranchInnerCode", new QueryBuilder(
				"select BranchInnerCode,Name from ZDBranch").executeDataTable()
				.toMapx(0, 1));
		dt.decodeColumn("Status", STATUS_MAP);
		dt.insertColumn("RoleNames");
		for (int i = 0; i < dt.getRowCount(); i++) {
			dt.set(i, "RoleNames", Cms.getRoleNames(Priv
					.getRoleCodesByUserName(dt.getString(i, "UserName"))));
		}
		dga.bindData(dt);
	}

	public static void initRoleTree(TreeAction ta) {
		DataTable dt = new QueryBuilder(
				"select RoleCode,'' as ParentID,'1' as TreeLevel,RoleName,'' as Checked from zdrole ")
				.executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			if ("everyone".equalsIgnoreCase(dt.getString(i, "RoleCode"))) {
				dt.set(i, "Checked", "Checked");
			}
		}
		ta.setRootText("角色");
		ta.setIdentifierColumnName("RoleCode");
		ta.bindData(dt);
	}

	public static void initEditRoleTree(TreeAction ta) {
		String userName = ta.getParam("UserName");
		DataTable dt = new QueryBuilder(
				"select RoleCode,'' as ParentID,'1' as TreeLevel,RoleName,(select 'Checked' from ZDUserRole b where b.RoleCode=ZDRole.RoleCode and UserName=?) as Checked from zdrole ",
				userName).executeDataTable();
		ta.setRootText("角色");
		ta.setIdentifierColumnName("RoleCode");
		ta.bindData(dt);
	}

	public void add() {
		if (new QueryBuilder("select count(*) from ZDUser").executeInt() >= LicenseInfo
				.getUserLimit()) {
			this.response.setError("后台用户数超出限制，请联系泽元软件更换License！");
			return;
		}
		Transaction trans = new Transaction();
		if (!add(trans, this.request)) {
			this.response.setLogInfo(0, Errorx.printString());
			return;
		}
		if (trans.commit()) {
			this.response.setLogInfo(1, "新建用户成功!");
			Priv.updateAllPriv($V("UserName"));
		} else {
			this.response.setLogInfo(0, "新建用户失败!");
		}
	}

	public static boolean add(Transaction trans, DataCollection dc) {
		String userName = dc.getString("UserName");
		if (!userPattern.matcher(userName).matches()) {
			Errorx.addError("用户名最多20位，仅限英文字母，数字，汉字，半角“.”、“@”");
			return false;
		}
		ZDUserSchema user = new ZDUserSchema();
		user.setValue(dc);
		user.setUserName(user.getUserName().toLowerCase());
		if (user.fill()) {
			Errorx.addError(dc.getString("UserName") + "用户已经存在!");
			return false;
		}

		user.setPassword(XString.md5Hex(dc.getString("Password")));
		if ((dc.getString("Type") == null) || ("".equals(dc.getString("Type"))))
			user.setType("0");
		else {
			user.setType(dc.getString("Type"));
		}
		user.setProp1(dc.getString("Prop1"));
		user.setAddTime(new Date());
		user.setAddUser(User.getUserName());
		trans.add(user, OperateType.INSERT);

		String roleCodes = dc.getString("RoleCode");
		if (XString.isEmpty(roleCodes)) {
			return true;
		}
		String[] RoleCodes = roleCodes.split(",");
		String currentUserName = User.getUserName();

		CacheManager.set("Platform", "User", user.getUserName(), user);
		CacheManager.set("Platform", "UserRole", user.getUserName(), roleCodes);

		for (int i = 0; i < RoleCodes.length; i++) {
			if ((XString.isEmpty(RoleCodes[i]))
					|| (XString.isEmpty(user.getUserName()))) {
				continue;
			}
			ZDUserRoleSchema userRole = new ZDUserRoleSchema();
			userRole.setUserName(user.getUserName());
			userRole.setRoleCode(RoleCodes[i]);
			userRole.setAddTime(new Date());
			userRole.setAddUser(currentUserName);
			trans.add(userRole, OperateType.INSERT);
		}
		return true;
	}

	public void save() {
		Transaction trans = new Transaction();
		if (!save(trans, this.request)) {
			return;
		}
		if (trans.commit()) {
			this.response.setLogInfo(1, "修改成功");
			Priv.updateAllPriv($V("UserName"));
		} else {
			this.response.setLogInfo(0, "修改失败");
		}
	}

	public boolean save(Transaction trans, DataCollection dc) {
		ZDUserSchema user = new ZDUserSchema();
		String newPassword = dc.getString("NewPassword");
		String newConfirmPassword = dc.getString("NewConfirmPassword");
		if (!newPassword.equals(newConfirmPassword)) {
			this.response.setLogInfo(0, "密码不一致!");
			return false;
		}
		user.setUserName(dc.getString("UserName"));
		if (!user.fill()) {
			return false;
		}
		user.setValue(dc);
		if (("admin".equalsIgnoreCase(user.getUserName()))
				&& ("S".equalsIgnoreCase(user.getStatus()))) {
			this.response.setLogInfo(0, "admin为系统自带的用户，拥有最高管理权限，不能停用!");
			return false;
		}
		user.setModifyTime(new Date());
		user.setModifyUser(User.getUserName());
		if ((XString.isNotEmpty(newPassword))
				&& (!Password.equals(newPassword))) {
			user.setPassword(XString.md5Hex(newPassword));
		}
		trans.add(user, OperateType.UPDATE);

		ZDUserRoleSchema userRole = new ZDUserRoleSchema();
		userRole.setUserName(user.getUserName());
		trans.add(userRole.query(), OperateType.DELETE_AND_BACKUP);

		String roleCodes = dc.getString("RoleCode");
		if (XString.isEmpty(roleCodes)) {
			return true;
		}
		String[] RoleCodes = roleCodes.split(",");
		String currentUserName = User.getUserName();

		CacheManager.set("Platform", "User", user.getUserName(), user);
		CacheManager.set("Platform", "UserRole", user.getUserName(), roleCodes);

		for (int i = 0; i < RoleCodes.length; i++) {
			if ((XString.isEmpty(RoleCodes[i]))
					|| (XString.isEmpty(user.getUserName()))) {
				continue;
			}
			userRole = new ZDUserRoleSchema();
			userRole.setUserName(user.getUserName());
			userRole.setRoleCode(RoleCodes[i]);
			userRole.setAddTime(new Date());
			userRole.setAddUser(currentUserName);
			trans.add(userRole, OperateType.INSERT);
		}

		return true;
	}

	public void del() {
		String UserNames = $V("UserNames");
		if (!idPattern.matcher(UserNames).matches()) {
			this.response.setLogInfo(0, "传入用户名称时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		if (!del(trans, this.request)) {
			this.response.setLogInfo(0, Errorx.printString());
			return;
		}
		if (trans.commit()) {
			UserLogPage.log("User", "DelUser", "删除用户:" + UserNames + "成功",
					this.request.getClientIP());
			this.response.setLogInfo(1, "删除用户成功!");
		} else {
			UserLogPage.log("User", "DelUser", "删除用户:" + UserNames + "失败",
					this.request.getClientIP());
			this.response.setLogInfo(0, "删除用户失败!");
		}
	}

	public void stopUser() {
		String UserNames = $V("UserNames");
		if (!idPattern.matcher(UserNames).matches()) {
			this.response.setLogInfo(0, "传入用户名称时发生错误!");
			return;
		}
		ZDUserSchema user = new ZDUserSchema();
		ZDUserSet userSet = user.query(new QueryBuilder(" where UserName in ('"
				+ UserNames.replaceAll(",", "','") + "')"));
		for (int i = 0; i < userSet.size(); i++) {
			if ("admin".equalsIgnoreCase(userSet.get(i).getUserName())) {
				this.response.setLogInfo(0, "admin为系统自带的用户，拥有最高管理权限，不能停用!");
				return;
			}
			userSet.get(i).setStatus("S");
		}
		if (userSet.update())
			this.response.setLogInfo(1, "停用用户成功!");
		else
			this.response.setLogInfo(0, "停用用户失败!");
	}

	public static boolean del(Transaction trans, DataCollection dc) {
		String UserNames = dc.getString("UserNames");
		ZDUserSchema user = new ZDUserSchema();
		ZDUserSet userSet = user.query(new QueryBuilder(" where UserName in ('"
				+ UserNames.replaceAll(",", "','") + "')"));
		trans.add(userSet, OperateType.DELETE_AND_BACKUP);

		for (int i = 0; i < userSet.size(); i++) {
			user = userSet.get(i);
			if (User.getUserName().equals(user.getUserName())) {
				Errorx.addError("当前用户为：" + User.getUserName() + ",不能删除自身用户!");
				UserLogPage.log("User", "DelUser", "删除用户:" + user.getUserName()
						+ "失败", ((JafRequest) dc).getClientIP());
				return false;
			}
			if ("admin".equalsIgnoreCase(user.getUserName())) {
				Errorx.addError("admin为系统自带的用户，拥有最高管理权限，不能删除!");
				UserLogPage.log("User", "DelUser", "删除用户:" + user.getUserName()
						+ "失败", ((JafRequest) dc).getClientIP());
				return false;
			}

			CacheManager.remove("Platform", "User", user.getUserName());
			CacheManager.remove("Platform", "UserRole", user.getUserName());

			ZDUserRoleSchema userRole = new ZDUserRoleSchema();
			userRole.setUserName(user.getUserName());
			ZDUserRoleSet userRoleSet = userRole.query();
			trans.add(userRoleSet, OperateType.DELETE_AND_BACKUP);

			trans.add(new ZDPrivilegeSchema().query(new QueryBuilder(
					"where OwnerType=? and Owner=?", "U", user.getUserName())),
					OperateType.DELETE_AND_BACKUP);
		}

		return true;
	}
}
