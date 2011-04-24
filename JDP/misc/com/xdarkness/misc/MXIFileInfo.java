 package com.xdarkness.misc;
 
 import java.io.File;
 
 public class MXIFileInfo
 {
   public static void main(String[] args)
   {
     File[] fs = new File("D:/Program Files/Adobe/Adobe Extension Manager CS4/Samples/Dreamweaver/ZCMS").listFiles();
     StringBuffer sb = new StringBuffer();
     for (int i = 0; i < fs.length; i++) {
       String name = fs[i].getName();
       String prefix = name.substring(0, name.indexOf('.'));
       System.out.println("<file source=\"" + name + "\" destination=\"$dreamweaver/configuration/Objects/ZCMS\" />");
       if (name.endsWith(".htm")) {
         sb.append("<button file=\"ZCMS/" + name + "\" id=\"ZCMS_Template_" + prefix + "\" image=\"ZCMS/" + prefix + 
           ".gif\" />\n");
       }
     }
     System.out.println("\n\n");
     System.out.println(sb);
   }
 }

          
/*    com.xdarkness.misc.MXIFileInfo
 * JD-Core Version:    0.6.0
 */