package com.xdarkness.plugin.webim.business;

import java.util.HashMap;
import java.util.Map;

public class OnLine {
	// 在线信息集合
	private Map<String, OnLineUser> onLineList = new HashMap<String, OnLineUser>();

	public Map<String, OnLineUser> getOnLineList() {
		return onLineList;
	}

	/**
	 * 追加在线信息
	 * 
	 * @param loginer
	 *            在线者
	 * @param onLineUser
	 *            在线者信息
	 */
	public void AppendLoginer(String loginer, OnLineUser onLineUser) {
		onLineList.put(loginer, onLineUser);
	}

	/**
	 * 删除在线信息
	 * 
	 * @param loginer
	 *            在线者
	 */
	public void RemoveLoginer(String loginer) {
		onLineList.remove(loginer);
	}

	// / <summary>
	// / 检查在线信息
	// / </summary>
	// / <param name="loginer">在线者</param>
	// / <returns></returns>
	public boolean CheckLoginer(String loginer) {
		return onLineList.containsKey(loginer);
	}

	// / <summary>
	// / 获取在线信息
	// / </summary>
	// / <param name="loginer">在线者</param>
	// / <returns></returns>
	public OnLineUser GetOnLineUser(String loginer) {
		return onLineList.get(loginer);
	}
}
