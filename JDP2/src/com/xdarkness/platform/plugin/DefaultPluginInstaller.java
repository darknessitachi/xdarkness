 package com.xdarkness.platform.plugin;
 
 public class DefaultPluginInstaller
   implements IPluginInstaller
 {
   private String pluginID;
 
   public DefaultPluginInstaller(String pluginID)
   {
     this.pluginID = pluginID;
   }
 
   public int install()
   {
     IPlugin plugin = PluginManager.getPlugin(this.pluginID);
     PluginUtil.installMenu(plugin);
     PluginUtil.installTable(plugin);
     PluginUtil.installCronTask(plugin);
     PluginUtil.installCache(plugin);
     PluginUtil.installExtend(plugin);
     PluginUtil.installFile(plugin);
     return 0;
   }
 }

          
/*    com.xdarkness.framework.plugin.DefaultPluginInstaller
 * JD-Core Version:    0.6.0
 */