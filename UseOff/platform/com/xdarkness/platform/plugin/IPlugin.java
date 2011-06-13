package com.xdarkness.platform.plugin;

import java.util.Date;

public abstract interface IPlugin
{
  public abstract String getVesion();

  public abstract Date getPublishDate();

  public abstract String getName();

  public abstract String getAuthor();

  public abstract String getPluginID();

  public abstract PluginMenu[] getMenus();

  public abstract String[] getExtendClasses();

  public abstract String[] getCronTaskClases();

  public abstract PluginTable[] getTables();

  public abstract String[] getCacheClasses();

  public abstract String[] getInstallFileNames();

  public abstract String[] getUninstallFileNames();

  public abstract IPluginInstaller getInstaller();

  public abstract IPluginUninstaller getUninstaller();

  public abstract void onPause();

  public abstract void onReuse();
}

          
/*    com.xdarkness.framework.plugin.IPlugin
 * JD-Core Version:    0.6.0
 */