package com.xdarkness.member;

import com.xdarkness.schema.ZDMemberSchema;
import com.xdarkness.framework.sql.QueryBuilder;

public class Member extends ZDMemberSchema {
	private static final long serialVersionUID = 1L;

	public Member() {
	}

	public Member(String userName) {
		setUserName(userName);
	}

	public Member(String userName, String siteID) {
		setUserName(userName);
		setSiteID(siteID);
	}

	public void setPassword(String passWord) {
		super.setPassword(XString.md5Hex(passWord));
	}

	public void setUnMd5Password(String passWord) {
		super.setPassword(passWord);
	}

	public boolean isExists() {
		boolean flag = false;
		if (XString.isNotEmpty(getUserName())) {
			int count = new QueryBuilder(
					"select count(*) from ZDMember where UserName = ?",
					getUserName()).executeInt();
			if (count > 0) {
				flag = true;
			}
		}
		return flag;
	}

	public boolean isExistsCurrentSite() {
		boolean flag = false;
		if (XString.isNotEmpty(getUserName())) {
			int count = new QueryBuilder(
					"select count(*) from ZDMember where UserName = ? and SiteID = ?",
					getUserName(), getSiteID()).executeInt();
			if (count > 0) {
				flag = true;
			}
		}
		return flag;
	}

	public boolean checkPassWord(String passWord) {
		boolean flag = false;
		if (XString.isNotEmpty(passWord)) {
			passWord = XString.md5Hex(passWord.trim());
			if (getPassword().equals(passWord)) {
				flag = true;
			}
		}
		return flag;
	}
}

/*
 * com.xdarkness.member.Member JD-Core Version: 0.6.0
 */