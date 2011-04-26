 package com.xdarkness.cmcore.template;
 
 public abstract class TemplateBase
 {
   private TemplateContext context;
 
   public TemplateContext getContext()
   {
     return this.context;
   }
 
   public void setContext(TemplateContext context) {
     this.context = context;
   }
 
   public abstract String getTemplateFilePath();
 
   public abstract void execute()
     throws Exception;
 
   public abstract TemplateConfig getConfig();
 }

          
/*    com.xdarkness.cmcore.template.TemplateBase
 * JD-Core Version:    0.6.0
 */