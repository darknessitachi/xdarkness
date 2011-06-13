 package com.xdarkness.platform.plugin;
 
 public abstract class AbstractPlugin
   implements IPlugin
 {
   public String[] getExtendClasses()
   {
     return null;
   }
 
   public String[] getCronTaskClases() {
     return null;
   }
 
   public String[] getCacheClasses() {
     return null;
   }
 
   public IPluginInstaller getInstaller() {
     return new DefaultPluginInstaller(getPluginID());
   }
 
   public IPluginUninstaller getUninstaller() {
     return new DefaultPluginUninstaller(getPluginID());
   }
 
   public void onPause()
   {
   }
 
   public void onReuse()
   {
   }
 }

          
/*    com.xdarkness.framework.plugin.AbstractPlugin
 * JD-Core Version:    0.6.0
 */