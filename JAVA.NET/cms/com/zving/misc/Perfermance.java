 package com.zving.misc;
 
 import com.zving.framework.utility.ServletUtil;
 import java.io.PrintStream;
 
 public class Perfermance
 {
   public static void main(String[] args)
   {
     int threadCount = 4;
     for (int k = 0; k < 4; ++k)
       new Thread() {
         public void run() {
           long start = System.currentTimeMillis();
           long last = System.currentTimeMillis();
           for (int i = 0; i < 2500; ++i) {
             if (i % 100 == 0) {
               System.out.println(i + ",百次耗时:" + (System.currentTimeMillis() - last));
               last = System.currentTimeMillis();
             }
 
             try
             {
               ServletUtil.getURLContent("http://localhost:8080/ZCMS/Platform/Code.jsp");
             } catch (Exception e) {
               e.printStackTrace();
             }
           }
           System.out.println(System.currentTimeMillis() - start);
         }
       }
       .start();
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.misc.Perfermance
 * JD-Core Version:    0.5.4
 */