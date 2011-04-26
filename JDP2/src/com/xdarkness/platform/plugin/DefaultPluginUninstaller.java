 package com.xdarkness.platform.plugin;
 
  
 public class DefaultPluginUninstaller
   implements IPluginUninstaller
 {
   private String pluginID;
 
   public DefaultPluginUninstaller(String pluginID)
   {
     this.pluginID = pluginID;
   }
 
   public int uninstall()
   {
     System.out.println(this.pluginID);
     return 0;
   }
 }

          
/*    com.xdarkness.framework.plugin.DefaultPluginUninstaller
 * JD-Core Version:    0.6.0
 */