package com.xdarkness.framework.jaf;

import com.xdarkness.framework.orm.data.DataCollection;

/**
 * 
 * @author Darkness Create on 2010-5-29 上午10:44:29
 * @version 1.0
 * @since JDF1.0
 */
public class JafRequest extends DataCollection {

	public JafRequest() {
	}

	public String getServerName() {
		return ServerName;
	}

	public void setServerName(String serverName) {
		ServerName = serverName;
	}

	public int getPort() {
		return Port;
	}

	public void setPort(int port) {
		Port = port;
	}

	public String getScheme() {
		return Scheme;
	}

	public void setScheme(String scheme) {
		Scheme = scheme;
	}

	public String getClassName() {
		return ClassName;
	}

	public void setClassName(String className) {
		ClassName = className;
	}

	public String getClientIP() {
		return ClientIP;
	}

	public void setClientIP(String clientIP) {
		ClientIP = clientIP;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String url) {
		URL = url;
	}

	private static final long serialVersionUID = 1L;
	private String URL;
	private String ClientIP;
	private String ClassName;
	private String ServerName;
	private int Port;
	private String Scheme;
}
