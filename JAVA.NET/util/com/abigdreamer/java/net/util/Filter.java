 package com.abigdreamer.java.net.util;
 
 public abstract class Filter
 {
   protected Object Param;
 
   public Filter()
   {
   }
 
   public Filter(Object param)
   {
     this.Param = param;
   }
 
   public abstract boolean filter(Object paramObject);
 }

          
/*    com.xdarkness.framework.utility.Filter
 * JD-Core Version:    0.6.0
 */