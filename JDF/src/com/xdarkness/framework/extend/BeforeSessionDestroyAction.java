 package com.xdarkness.framework.extend;
 
 public abstract class BeforeSessionDestroyAction
   implements IExtendAction
 {
   public static final String Type = "BeforeSessionDestroy";
 
   public String getTarget()
   {
     return "BeforeSessionDestroy";
   }
 
   public void execute(Object[] args)
   {
   }
 }

          
/*    com.xdarkness.framework.extend.BeforeSessionDestroyAction
 * JD-Core Version:    0.6.0
 */