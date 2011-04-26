 package com.xdarkness.misc;
 
 import com.xdarkness.framework.orm.DBImport;
 
 public class DBConvert
 {
   public static void main(String[] args)
   {
     new DBImport().importDB(Config.getContextRealPath() + "WEB-INF/data/installer/Install.dat");
   }
 }

          
/*    com.xdarkness.misc.DBConvert
 * JD-Core Version:    0.6.0
 */