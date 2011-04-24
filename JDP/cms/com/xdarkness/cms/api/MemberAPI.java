package com.xdarkness.cms.api;

import java.util.Iterator;

import com.xdarkness.schema.ZDMemberSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.util.Errorx;
import com.xdarkness.framework.util.Mapx;

public class MemberAPI implements APIInterface {
	private Mapx params;

	public boolean delete() {
		String UserName = this.params.getString("UserName");
		if (XString.isNotEmpty(UserName)) {
			ZDMemberSchema member = new ZDMemberSchema();
			member.setUserName(UserName);
			if (!member.fill()) {
				return false;
			}
			Transaction trans = new Transaction();
			trans.add(member, OperateType.DELETE);

			return trans.commit();
		}

		return false;
	}

	public long insert() {
		return insert(new Transaction());
	}

	public long insert(Transaction trans) {
		String UserName = this.params.getString("UserName");
		String PassWord = this.params.getString("PassWord");
		String realname = this.params.getString("RealName");
		String Email = this.params.getString("Email");
		String Type = "none";
		String Status = "N";
		if ((XString.isEmpty(UserName)) || (XString.isEmpty(PassWord))
				|| (XString.isEmpty(Email))) {
			return -1L;
		}
		if (UserName.length() > 20) {
			Errorx.addError("会员名最多20位");
			return -1L;
		}
		ZDMemberSchema member = new ZDMemberSchema();
		member.setUserName(UserName);
		if (member.fill()) {
			Errorx.addError(UserName + "会员已经存在!");
			return -1L;
		}

		member.setName(realname);
		member.setPassword(XString.md5Hex(PassWord));
		member.setEmail(Email);
		member.setType(Type);
		member.setStatus(Status);
		trans.add(member, OperateType.INSERT);
		if (trans.commit())
			return 1L;
		return -1L;
	}

	public boolean setSchema(Schema schema) {
		return false;
	}

	public boolean update() {
		String UserName = this.params.getString("UserName");
		String realname = this.params.getString("RealName");
		String PassWord = this.params.getString("PassWord");
		String Email = this.params.getString("Email");
		String Type = "none";
		String Status = "N";
		if ((XString.isEmpty(UserName)) || (XString.isEmpty(PassWord))
				|| (XString.isEmpty(Email))) {
			return false;
		}
		if (UserName.length() > 20) {
			Errorx.addError("会员名最多20位");
			return false;
		}
		if (!XString.isNotEmpty(UserName)) {
			Errorx.addError(UserName + "会员不存在!");
			return false;
		}
		Transaction trans = new Transaction();
		ZDMemberSchema member = new ZDMemberSchema();
		member.setUserName(UserName);
		member.fill();
		member.setName(realname);
		member.setPassword(XString.md5Hex(PassWord));
		member.setEmail(Email);
		member.setType(Type);
		member.setStatus(Status);
		trans.add(member, OperateType.UPDATE);
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
				this.params.put(key, "");
		}
	}
}

/*
 * com.xdarkness.cms.api.MemberAPI JD-Core Version: 0.6.0
 */