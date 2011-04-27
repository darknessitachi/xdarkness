 package com.zving.misc;
 
 import com.zving.framework.Config;
 import com.zving.framework.orm.DBImport;
 
 public class DBConvert
 {
   public static void main(String[] args)
   {
     new DBImport().importDB(Config.getContextRealPath() + "WEB-INF/data/installer/Install.dat");
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.misc.DBConvert
 * JD-Core Version:    0.5.4
 */