 package com.abigdreamer.java.net.extend;

import javax.servlet.http.HttpSession;
 
  
 public abstract class AfterSessionCreateAction
   implements IExtendAction
 {
   public static final String Type = "AfterSessionCreate";
 
   public String getTarget()
   {
     return "AfterSessionCreate";
   }
 
   public void execute(Object[] args)
   {
     HttpSession session = (HttpSession)args[0];
     execute(session);
   }
 
   public abstract void execute(HttpSession paramHttpSession);
 }

          
/*    com.xdarkness.framework.extend.AfterSessionCreateAction
 * JD-Core Version:    0.6.0
 */