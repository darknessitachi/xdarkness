 package com.xdarkness.cmcore.tag;
 
 public abstract class SimpleTag extends TagBase
 {
   public boolean isIterative()
   {
     return false;
   }
 
   public int onTagEnd() {
     return 2;
   }
 
   public boolean prepareNext() {
     return false;
   }
 }

          
/*    com.xdarkness.cmcore.tag.SimpleTag
 * JD-Core Version:    0.6.0
 */