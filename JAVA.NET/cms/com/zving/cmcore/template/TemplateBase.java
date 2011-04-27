 package com.zving.cmcore.template;
 
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

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cmcore.template.TemplateBase
 * JD-Core Version:    0.5.4
 */