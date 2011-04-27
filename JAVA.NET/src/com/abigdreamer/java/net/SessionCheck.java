 package com.abigdreamer.java.net;
 
 import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abigdreamer.java.net.jaf.Ajax;
 
 public class SessionCheck
 {
   public static void check(HttpServletRequest request, HttpServletResponse response)
   {
   }
 
   public static boolean check(Class c)
   {
     if (!Ajax.class.isAssignableFrom(c)) {
       if (!User.isLogin()) {
         return false;
       }
       if (!User.isManager()) {
         return false;
       }
     }
     return true;
   }
 }

          
/*    com.xdarkness.framework.SessionCheck
 * JD-Core Version:    0.6.0
 */