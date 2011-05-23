package com.abigdreamer.java.net.connection;

/**
 * 服务器类型
 * 
 * @author Darkness
 * @date May 19, 2011 11:20:45 PM
 * @version 1.0.0
 */
public class ServerTypes {

	private String serverType;
	
	private ServerTypes() {
	}
	
	private ServerTypes(String serverType) {
		this.serverType = serverType;
	}
	
	public static final ServerTypes Tomcat = new ServerTypes("Tomcat");
	public static final ServerTypes Weblogic = new ServerTypes("Weblogic");
	public static final ServerTypes WebSphere = new ServerTypes("WebSphere");
	public static final ServerTypes JBoss = new ServerTypes("JBoss");
	
	@Override
	public String toString() {
		return this.serverType;
	}
}
