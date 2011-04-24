package com.xdarkness.plugin.webim.business;

public class IM {
	private int global = 0;// 系统消息
	private String custom = "";// 自定义回复消息标识
	private String identy = "";// 标识
	private String sender = "";// 发送者
	private String isOuter = "";// 是否为外单位
	private String content = "";// 发送内容
	private String cnsender = "";// 发送者中文名
	private String receiver = "";// 接收者
	private String sendDate = "";// 发送日期
	private String loginName = "";// 登录名
	private String contentStyle = "";// 内容格式

	public int getGlobal() {
		return global;
	}

	public void setGlobal(int global) {
		this.global = global;
	}

	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public String getIdenty() {
		return identy;
	}

	public void setIdenty(String identy) {
		this.identy = identy;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getIsOuter() {
		return isOuter;
	}

	public void setIsOuter(String isOuter) {
		this.isOuter = isOuter;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCnsender() {
		return cnsender;
	}

	public void setCnsender(String cnsender) {
		this.cnsender = cnsender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getContentStyle() {
		return contentStyle;
	}

	public void setContentStyle(String contentStyle) {
		this.contentStyle = contentStyle;
	}

}