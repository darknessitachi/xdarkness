 package com.zving.cmcore.template;
 
 import java.util.ArrayList;
 
 public class TemplateContext
 {
   ArrayList list = new ArrayList();
 
   boolean isSSIContext = false;
 
   public void setSSIContext(boolean isSSIContext) {
     this.isSSIContext = isSSIContext;
   }
 
   public void addError(String message) {
     this.list.add(message);
     throw new RuntimeException();
   }
 
   public ArrayList getErrors() {
     return this.list;
   }
 
   public boolean isSSIContext() {
     return this.isSSIContext;
   }
 
   public void output(String html)
   {
   }
 
   public String getHolderValue(String holder) {
     if (holder.startsWith("${")) {
       holder = holder.substring(2);
     }
     if (holder.endsWith("}")) {
       holder = holder.substring(0, holder.length() - 1);
     }
 
     return null;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cmcore.template.TemplateContext
 * JD-Core Version:    0.5.4
 */