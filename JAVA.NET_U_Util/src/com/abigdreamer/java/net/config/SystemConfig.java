package com.abigdreamer.java.net.config;

public class SystemConfig {

	public static final String JavaVersion = System.getProperty("java.version");
	public static final String JavaVendor = System.getProperty("java.vendor");
	public static final String JavaHome = System.getProperty("java.home");
	public static final String OSPatchLevel = System
			.getProperty("sun.os.patch.level");
	public static final String OSArch = System.getProperty("os.arch");
	public static final String OSVersion = System.getProperty("os.version");
	public static final String OSName = System.getProperty("os.name");
	public static final String OSUserLanguage = System
			.getProperty("user.language");
	public static final String OSUserName = System.getProperty("user.name");
	public static final String LineSeparator = System
			.getProperty("line.separator");
	public static final String FileSeparator = System
			.getProperty("file.separator");
	public static final String FileEncoding = System
			.getProperty("file.encoding");
	public static String ContainerInfo = "";
	public static boolean isWindows() {
		return SystemConfig.OSName.toLowerCase().indexOf("windows") >= 0;
	}
}
