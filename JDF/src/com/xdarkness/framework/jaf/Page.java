package com.xdarkness.framework.jaf;

import org.apache.commons.logging.Log;

import com.xdarkness.framework.util.LogUtil;

/**
 * 
 * @author Darkness Create on 2010-5-29 上午10:36:16
 * @version 1.0
 * @since JDF1.0
 */
public abstract class Page implements IPage {

	/**
	 * IPage接口的实现方法，在该Page类初始化的时候，系统会自动注入所需对象
	 */
	// //////////////////////////////////////////////////////////////////////////
	// //////////////////// IPage接口的实现方法 开始///////////////////////////////
	// /////////////////////////////////////////////////////////////////////////
	protected JafResponse response = new JafResponse();
	protected JafRequest request;
	protected Cookies cookie;
	protected Log Log = LogUtil.getLogger();

	public JafResponse getResponse() {
		return response;
	}

	public void setResponse(JafResponse response) {
		this.response = response;
	}

	public JafRequest getRequest() {
		return request;
	}

	public void setRequest(JafRequest request) {
		this.request = request;
	}

	public Cookies getCookie() {
		return cookie;
	}

	public void setCookie(Cookies cookies) {
		this.cookie = cookies;
	}

	public String getValue(String id) {
		return request.getString(id);
	}

	public void setValue(String id, Object value) {
		response.put(id, value);
	}

	public String $V(String id) {
		return request.getString(id);
	}

	public void $S(String id, Object value) {
		response.put(id, value);
	}

	public void redirect(String url) {
		response.put("_SKY_SCRIPT", "window.location=\"" + url + "\";");
	}
	// //////////////////////////////////////////////////////////////////////////
	// //////////////////// IPage接口的实现方法 结束///////////////////////////////
	// /////////////////////////////////////////////////////////////////////////
}
