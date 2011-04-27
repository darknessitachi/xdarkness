 package com.abigdreamer.java.net.extend;
 
 public abstract class BeforePageMethodInvokeAction
   implements IExtendAction
 {
   public static final String Type = "BeforePageMethodInvoke";
 
   public String getTarget()
   {
     return "BeforePageMethodInvoke";
   }
 
   public void execute(Object[] args)
   {
     execute((String)args[0]);
   }
 
   public abstract void execute(String paramString);
 }

          
/*    com.xdarkness.framework.extend.BeforePageMethodInvokeAction
 * JD-Core Version:    0.6.0
 */