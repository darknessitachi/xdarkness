package com.xdarkness.platform.plugin;

import com.xdarkness.platform.MenuType;


/**
 * 
 * @author Darkness
 * Create on May 21, 2010 2:46:01 PM
 * @version 1.0
 * @since JDF1.0
 */
public class PluginMenu {

	public String Name;
	public String URL;
	public MenuType type;
	
	public PluginMenu (String name, String url, MenuType parentId) {
		this.Name = name;
		this.URL = url;
		this.type = parentId;
	}
}
