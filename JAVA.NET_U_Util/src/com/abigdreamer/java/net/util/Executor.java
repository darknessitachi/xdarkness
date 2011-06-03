 package com.abigdreamer.java.net.util;
 
 public abstract class Executor
 {
   protected Object param;
 
   public Executor(Object param)
   {
     this.param = param;
   }
 
   public abstract boolean execute();
 }

          
/*    com.xdarkness.framework.utility.Executor
 * JD-Core Version:    0.6.0
 */