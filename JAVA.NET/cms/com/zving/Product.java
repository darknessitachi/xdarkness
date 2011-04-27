 package com.zving;
 
 import com.zving.framework.license.IProduct;
 
 public class Product
   implements IProduct
 {
   public String getAppCode()
   {
     return "ZCMS";
   }
 
   public String getAppName() {
     return "泽元内容管理系统";
   }
 
   public float getMainVersion() {
     return 1.3F;
   }
 
   public float getMinorVersion() {
     return 1.0F;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.Product
 * JD-Core Version:    0.5.4
 */