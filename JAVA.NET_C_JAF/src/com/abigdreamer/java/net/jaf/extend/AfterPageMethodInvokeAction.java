 package com.abigdreamer.java.net.jaf.extend;
 
 public abstract class AfterPageMethodInvokeAction
   implements IExtendAction
 {
   public static final String Type = "AfterPageMethodInvoke";
 
   public String getTarget()
   {
     return "AfterPageMethodInvoke";
   }
 
   public void execute(Object[] args)
   {
     execute((String)args[0]);
   }
 
   public abstract void execute(String paramString);
 }

          
/*    com.xdarkness.framework.extend.AfterPageMethodInvokeAction
 * JD-Core Version:    0.6.0
 */