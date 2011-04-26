package com.xdarkness.plugin.webim.business;

import java.util.Date;

import com.xdarkness.framework.util.XString;

public class OnLineUser {
	private int userStatus;// 用户状态

	private String scratch;// 个性回复
	private String userName;// 用户名
	private String loginName;// 登录名
	private String headImg = Profile.userHeadImage;// 个性头像
	private Date loginTime;// 登录时间
	private Date exitTime;

	public OnLineUser() {
	}

	/**
	 * 初始化
	 * 
	 * @param userName
	 *            用户名
	 * @param loginName
	 *            登录名
	 * @param userStatus
	 *            用户状态
	 * @param headImg
	 *            个性头像
	 * @param scratch
	 *            个性回复
	 */
	public OnLineUser(String userName, String loginName, int userStatus,
			String headImg, String scratch) {
		this.scratch = scratch;
		this.userName = userName;
		this.loginName = loginName;
		this.userStatus = userStatus;
		this.loginTime = new Date();
		this.exitTime = this.loginTime;

		if (!XString.isEmpty(headImg)) {
			this.headImg = headImg;
		}
	}

	/**
	 * 获取用户状态
	 * 
	 * @param loginer
	 *            在线者
	 * @param cache
	 *            在线信息集合
	 * @return
	 */
	public int GetUserStatus(String loginer, OnLine cache) {
		int status = Profile.offLineStatus;

		if (cache != null) {
			OnLine onLine = cache;

			if (onLine.CheckLoginer(loginer)) {
				status = onLine.getOnLineList().get(loginer).getUserStatus();
			}
		}

		return status;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public String getScratch() {
		return scratch;
	}

	public void setScratch(String scratch) {
		this.scratch = scratch;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getExitTime() {
		return exitTime;
	}

	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}

}
