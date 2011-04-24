package com.xdarkness.plugin.useoff;

import java.util.Date;

import com.xdarkness.framework.util.DateUtil;
import com.xdarkness.platform.MenuType;
import com.xdarkness.platform.plugin.AbstractPlugin;
import com.xdarkness.platform.plugin.PluginMenu;
import com.xdarkness.platform.plugin.PluginTable;

/**
 * 
 * @author Darkness Create on 2010-5-25 下午12:58:03
 * @version 1.0
 * @since JDF1.0
 */
public class UseOffPlugin extends AbstractPlugin {

	public String[] getInstallFileNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public PluginMenu[] getMenus() {

		return new PluginMenu[] {
				new PluginMenu("财务管理", "", MenuType.FirstLevel),
				new PluginMenu("消费记录", "UseOffList.jsp", MenuType.SecondLevel) };
	}

	public String getName() {

		return "财务管理";
	}
	
	public String getPluginID() {

		return "UseOff";
	}

	public Date getPublishDate() {

		return DateUtil.parse("2010-5-25");
	}

	public PluginTable[] getTables() {
		/**
		 * 
		 * 
CREATE TABLE `useoff` (
  `id` int(11) NOT NULL auto_increment,
  `money` double NOT NULL default '0',
  `useFor` varchar(100) default NULL,
  `moneyType` varchar(5) default NULL,
  `description` varchar(200) default NULL,
  `createTime` datetime default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
       
);
		 */
		return null;
	}

	public String[] getUninstallFileNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getVesion() {

		return "1.0";
	}

	public String getAuthor() {
		// TODO Auto-generated method stub
		return null;
	}

}
