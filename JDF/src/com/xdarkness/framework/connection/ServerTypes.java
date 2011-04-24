package com.xdarkness.framework.connection;

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
