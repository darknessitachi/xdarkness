 package com.abigdreamer.java.net.clustering.server;
 
 import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.abigdreamer.java.net.util.LogUtil;
import com.abigdreamer.java.net.util.XString;
 
 public class SocketClusteringServer
 {
   private static ServerSocket so;
   private static boolean stopFlag = false;
   private static Object mutex = new Object();
 
   public static void start(int port) {
     if (so == null)
       synchronized (mutex) {
         if (so == null)
           try {
             ServerSocket so = new ServerSocket(port);
             while (true)
               try {
                 if (stopFlag) {
                   break;
                 }
                 new RequestHandler(so.accept()); continue;
               } catch (IOException e) {
                 e.printStackTrace();
               }
           }
           catch (IOException e) {
             e.printStackTrace();
           }
       }
   }
 
   public static void stop()
   {
   }
 
   private static class RequestHandler
     implements Runnable
   {
     private Socket incoming;
 
     public RequestHandler(Socket incoming)
     {
       try
       {
         this.incoming = incoming;
         new Thread(this).start();
       } catch (Exception e) {
         e.printStackTrace();
       }
     }
 
     public void run() {
       try {
         BufferedReader br = new BufferedReader(new InputStreamReader(this.incoming.getInputStream()));
         StringBuffer sb = new StringBuffer();
         String line;
         while ((line = br.readLine()) != null)
         {
           if (line.equals("End")) {
             OutputStream outStream = this.incoming.getOutputStream();
             PrintWriter out = new PrintWriter(outStream);
             out.println(dealRequest(sb.toString()));
             out.println("End");
             out.flush();
             sb = new StringBuffer();
           } else if (line.equals("Shutdown")) {
             OutputStream outStream = this.incoming.getOutputStream();
             PrintWriter out = new PrintWriter(outStream);
             out.println(dealRequest(sb.toString()));
             out.println("End");
             out.flush();
             sb = new StringBuffer();
           } else {
             sb.append(line);
             sb.append("\n");
           }
         }
       } catch (IOException e) {
         LogUtil.info(e.getMessage());
       }
     }
 
     public String dealRequest(String request) {
       String type = null; String key = null; String action = null; String value = null;
       String[] arr = request.split("\\n");
       for (int i = 0; i < arr.length; i++) {
         String line = arr[i];
         if (XString.isNotEmpty(line)) {
           int index = line.indexOf(":");
           if (index > 0) {
             String k = line.substring(0, index);
             String v = line.substring(index + 1);
             if (k.equals("Type"))
               type = v;
             else if (k.equals("Key"))
               key = v;
             else if (k.equals("Action"))
               action = v;
             else if (k.equals("Value")) {
               value = v;
             }
           }
         }
       }
       return ClusteringData.dealRequest(type, key, action, value);
     }
   }
 }

          
/*    com.xdarkness.framework.clustering.server.SocketClusteringServer
 * JD-Core Version:    0.6.0
 */