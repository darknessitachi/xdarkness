 package com.zving.cmcore.tag;
 
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

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cmcore.tag.SimpleTag
 * JD-Core Version:    0.5.4
 */