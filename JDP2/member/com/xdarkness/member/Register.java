package com.xdarkness.member;

import java.util.Date;

import com.xdarkness.cms.dataservice.MemberField;
import com.xdarkness.schema.ZDMemberCompanyInfoSchema;
import com.xdarkness.schema.ZDMemberPersonInfoSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.Ajax;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class Register extends Ajax {
	public static Mapx init(Mapx params) {
		String SiteID = params.getString("SiteID");
		if (XString.isEmpty(SiteID)) {
			SiteID = new QueryBuilder(
					"select ID from ZCSite Order by AddTime desc")
					.executeOneValue() + "";
		}
		params.put("Columns", MemberField.getColumns(SiteID));
		return params;
	}

	public void checkUser() {
		String UserName = $V("UserName");
		Member member = new Member(UserName);
		if (member.isExists())
			this.response.setLogInfo(0, "用户名已存在,请选择另外的用户名");
		else
			this.response.setLogInfo(1, "用户名未注册,您可以放心使用");
	}

	public void register() {
		String userName = $V("UserName");
		String passWord = $V("PassWord");
		String eMail = $V("Email");
		String Type = $V("Type");
		String VerifyCode = $V("VerifyCode");
		Object authCode = User.getValue("_SKY_AUTHKEY");
		if ((authCode != null) && (!authCode.equals(VerifyCode))) {
			this.response.setLogInfo(0, "验证码错误");
			return;
		}
		Transaction trans = new Transaction();
		Member member = new Member(userName);
		member.setName(userName);
		member = MemberField.setPropValues(member, this.request);
		member.setEmail(eMail);
		member.setType(Type);
		member.setSiteID($V("SiteID"));
		member.setRegIP(this.request.getClientIP());
		member.setValue(this.request);
		member.setPassword(passWord.trim());
		member.setStatus("X");
		member.setRegTime(new Date());
		member.setScore("0");
		member
				.setMemberLevel(new QueryBuilder(
						"select ID from ZDMemberLevel where Score <= 0 order by Score desc")
						.executeOneValue()+"");

		member.setLastLoginIP(this.request.getClientIP());
		member.setLastLoginTime(new Date());
		if (XString.isEmpty(member.getName())) {
			member.setName("注册用户");
		}
		trans.add(member, OperateType.INSERT);
		if (Type.equalsIgnoreCase("Person")) {
			ZDMemberPersonInfoSchema person = new ZDMemberPersonInfoSchema();
			person.setUserName(member.getUserName());
			person.setNickName(member.getName());
			person.setMobile($V("Mobile"));
			person.setAddress($V("Address"));
			person.setZipCode($V("ZipCode"));
			trans.add(person, OperateType.INSERT);
		} else {
			ZDMemberCompanyInfoSchema company = new ZDMemberCompanyInfoSchema();
			company.setUserName(member.getUserName());
			company.setCompanyName(member.getName());
			company.setEmail(member.getEmail());
			company.setMobile($V("Mobile"));
			company.setAddress($V("Address"));
			company.setZipCode($V("ZipCode"));
			trans.add(company, OperateType.INSERT);
		}

		if (trans.commit()) {
			this.cookie.setCookie("UserName", userName);
			User.setLogin(true);
			User.setUserName(userName);
			User.setMember(true);
			User.setValue("UserName", member.getUserName());
			User.setValue("Type", member.getType());
			User.setValue("SiteID", member.getSiteID());

			if (XString.isNotEmpty(member.getName()))
				User.setValue("Name", member.getName());
			else {
				User.setValue("Name", member.getUserName());
			}
			this.response.setLogInfo(1, "注册成功");
		} else {
			this.response.setLogInfo(0, "注册失败");
		}
	}
}
