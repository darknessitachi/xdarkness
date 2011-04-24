package com.xdarkness.cms.api;

import java.util.Date;
import java.util.Iterator;
import java.util.regex.Pattern;

import com.xdarkness.schema.ZDBranchSchema;
import com.xdarkness.schema.ZDBranchSet;
import com.xdarkness.schema.ZDPrivilegeSchema;
import com.xdarkness.schema.ZDUserRoleSchema;
import com.xdarkness.schema.ZDUserRoleSet;
import com.xdarkness.schema.ZDUserSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Errorx;
import com.xdarkness.framework.util.Mapx;

public class UserAPI implements APIInterface {
	private Mapx params;
	private static final Pattern userPattern = Pattern.compile(
			"[\\w@\\.\\,一-龥]*", 34);

	public boolean delete() {
		String username = this.params.getString("Username");
		username = username.toLowerCase();

		if (!userPattern.matcher(username).matches()) {
			return false;
		}

		if ("administrator".equalsIgnoreCase(username)) {
			return false;
		}

		if ("admin".equalsIgnoreCase(username)) {
			return false;
		}

		ZDUserSchema user = new ZDUserSchema();
		user.setUserName(username);
		if (!user.fill()) {
			return false;
		}

		Transaction trans = new Transaction();

		ZDUserRoleSchema userRole = new ZDUserRoleSchema();
		userRole.setUserName(user.getUserName());
		ZDUserRoleSet userRoleSet = userRole.query();
		trans.add(userRoleSet, OperateType.DELETE);

		trans.add(new ZDPrivilegeSchema().query(new QueryBuilder(
				"where OwnerType=? and Owner=?", "U", user.getUserName())), OperateType.DELETE);
		trans.add(user, OperateType.DELETE);

		return trans.commit();
	}

	public long insert() {
		return insert(new Transaction());
	}

	public long insert(Transaction trans) {
		String username = this.params.getString("Username");
		String realname = this.params.getString("RealName");
		String password = this.params.getString("Password");
		String email = this.params.getString("Email");
		String branchCode = this.params.getString("BranchCode");
		String isBranchAdmin = this.params.getString("IsBranchAdmin");
		String status = this.params.getString("Status");

		String type = this.params.getString("Type");
		if ((XString.isEmpty(username)) || (XString.isEmpty(password))) {
			return -1L;
		}

		username = username.toLowerCase();
		if (!userPattern.matcher(username).matches()) {
			Errorx.addError("用户名最多20位，仅限英文字母，数字，汉字，半角“.”、“@”");
			return -1L;
		}

		ZDUserSchema user = new ZDUserSchema();
		user.setUserName(username);
		if (user.fill()) {
			Errorx.addError("用户" + username + "已经存在.");
			return -1L;
		}

		user.setRealName(realname);
		if (XString.isEmpty(realname)) {
			user.setRealName(username);
		}

		ZDBranchSchema branch = new ZDBranchSchema();
		if (XString.isNotEmpty(branchCode)) {
			branch.setBranchCode(branchCode);
			ZDBranchSet set = branch.query();
			if ((set == null) || (set.size() < 1)) {
				Errorx.addError(branchCode + "机构编码有误.");
				return -1L;
			}

			user.setBranchInnerCode(set.get(0).getBranchInnerCode());
		} else {
			user.setBranchInnerCode("0001");
		}

		if ("Y".equals(isBranchAdmin))
			user.setIsBranchAdmin("Y");
		else {
			user.setIsBranchAdmin("N");
		}

		if ("S".equals(status))
			user.setStatus("S");
		else {
			user.setStatus("N");
		}

		user.setPassword(XString.md5Hex(password));
		if (XString.isEmpty(type))
			user.setType("0");
		else {
			user.setType(type);
		}
		user.setEmail(email);
		user.setProp1(this.params.getString("Prop1"));
		user.setAddTime(new Date());
		user.setAddUser("wsdl");

		trans.add(user, OperateType.INSERT);

		String roleCodes = this.params.getString("RoleCode");
		if (XString.isNotEmpty(roleCodes)) {
			roleCodes = "'" + roleCodes.replaceAll(",", "','") + "'";
			DataTable dt = new QueryBuilder(
					"select RoleCode from zdrole where RoleCode in ("
							+ roleCodes + ")").executeDataTable();

			String[] RoleCodes = (String[]) dt.getColumnValues(0);
			Date addTime = new Date();
			for (int i = 0; i < RoleCodes.length; i++) {
				if (XString.isEmpty(RoleCodes[i])) {
					continue;
				}
				ZDUserRoleSchema userRole = new ZDUserRoleSchema();
				userRole.setUserName(user.getUserName());
				userRole.setRoleCode(RoleCodes[i]);
				userRole.setAddTime(addTime);
				userRole.setAddUser("wsdl");

				trans.add(userRole, OperateType.INSERT);
			}
		} else {
			ZDUserRoleSchema userRole = new ZDUserRoleSchema();
			userRole.setUserName(user.getUserName());
			userRole.setRoleCode("everyone");
			userRole.setAddTime(new Date());
			userRole.setAddUser("wsdl");

			trans.add(userRole, OperateType.INSERT);
		}

		if (trans.commit()) {
			return 1L;
		}
		Errorx.addError("新建用户" + username + "失败。");
		return -1L;
	}

	public boolean setSchema(Schema schema) {
		return false;
	}

	public boolean update() {
		String username = this.params.getString("Username");
		String realname = this.params.getString("RealName");
		String password = this.params.getString("Password");
		String email = this.params.getString("Email");
		String branchCode = this.params.getString("BranchCode");
		String isBranchAdmin = this.params.getString("IsBranchAdmin");
		String status = this.params.getString("Status");

		String type = this.params.getString("Type");
		if (XString.isEmpty(username)) {
			return false;
		}

		username = username.toLowerCase();
		if (!userPattern.matcher(username).matches()) {
			Errorx.addError("用户名最多20位，仅限英文字母，数字，汉字，半角“.”、“@”");
			return false;
		}
		ZDUserSchema user = new ZDUserSchema();
		user.setUserName(username);
		if (!user.fill()) {
			Errorx.addError(username + "用户不存在.");
			return false;
		}

		if (XString.isNotEmpty(branchCode)) {
			ZDBranchSchema branch = new ZDBranchSchema();
			branch.setBranchCode(branchCode);
			ZDBranchSet set = branch.query();
			if ((set == null) || (set.size() < 1)) {
				Errorx.addError(branchCode + "机构编码有误.");
				return false;
			}

			user.setBranchInnerCode(set.get(0).getBranchInnerCode());
		}

		Transaction trans = new Transaction();
		if (XString.isNotEmpty(realname)) {
			user.setRealName(realname);
		}

		if ("Y".equals(isBranchAdmin))
			user.setIsBranchAdmin("Y");
		else {
			user.setIsBranchAdmin("N");
		}

		if (("suspend".equals(this.params.getString("OperationType")))
				&& ("S".equals(user.getStatus()))) {
			Errorx.addError("用户" + username + "已经暂停。");
			return false;
		}

		if (("restore".equals(this.params.getString("OperationType")))
				&& ("N".equals(user.getStatus()))) {
			Errorx.addError("用户" + username + "未暂停。");
			return false;
		}

		if ("S".equals(status))
			user.setStatus("S");
		else {
			user.setStatus("N");
		}

		if (XString.isNotEmpty(password)) {
			user.setPassword(XString.md5Hex(password));
		}
		if (XString.isEmpty(type))
			user.setType("0");
		else {
			user.setType(type);
		}
		if (XString.isNotEmpty(email)) {
			user.setEmail(email);
		}
		user.setProp1(this.params.getString("Prop1"));
		user.setModifyTime(new Date());
		user.setModifyUser("wsdl");

		trans.add(user, OperateType.UPDATE);

		String roleCodes = this.params.getString("RoleCode");
		if (XString.isNotEmpty(roleCodes)) {
			ZDUserRoleSchema userRole = new ZDUserRoleSchema();
			userRole.setUserName(user.getUserName());
			trans.add(userRole.query(), OperateType.DELETE);

			roleCodes = "'" + roleCodes.replaceAll(",", "','") + "'";
			DataTable dt = new QueryBuilder(
					"select RoleCode from zdrole where RoleCode in ("
							+ roleCodes + ")").executeDataTable();
			String[] RoleCodes = (String[]) dt.getColumnValues(0);

			Date date = new Date();
			for (int i = 0; i < RoleCodes.length; i++) {
				if (XString.isEmpty(RoleCodes[i])) {
					continue;
				}
				userRole = new ZDUserRoleSchema();
				userRole.setUserName(user.getUserName());
				userRole.setRoleCode(RoleCodes[i]);
				userRole.setAddTime(date);
				userRole.setAddUser("wsdl");
				trans.add(userRole, OperateType.INSERT);
			}

		}

		return trans.commit();
	}

	public Mapx getParams() {
		return this.params;
	}

	public void setParams(Mapx params) {
		convertParams(params);
		this.params = params;
	}

	public void convertParams(Mapx params) {
		Iterator iter = params.keySet().iterator();
		while (iter.hasNext()) {
			Object key = iter.next();
			String value = params.getString(key);
			if ((XString.isEmpty(value)) || ("null".equalsIgnoreCase(value)))
				params.put(key, "");
		}
	}
}

/*
 * com.xdarkness.cms.api.UserAPI JD-Core Version: 0.6.0
 */