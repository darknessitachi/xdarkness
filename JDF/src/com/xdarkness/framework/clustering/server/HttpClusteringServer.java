 package com.xdarkness.framework.clustering.server;
 
 import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xdarkness.framework.clustering.Clustering;
import com.xdarkness.framework.util.LogUtil;
 
 public class HttpClusteringServer extends HttpServlet
 {
   private static final long serialVersionUID = 1L;
 
   public void doGet(HttpServletRequest request, HttpServletResponse response)
   {
     if (!Clustering.isClusteringServer()) {
       try {
         LogUtil.warn("本应用未被配置成集群服务器!");
         response.getWriter().print("本应用未被配置成集群服务器!");
       } catch (IOException e) {
         e.printStackTrace();
       }
     }
     String type = request.getParameter("Type");
     String key = request.getParameter("Key");
     String action = request.getParameter("Action");
     String value = request.getParameter("Value");
     String result = ClusteringData.dealRequest(type, key, action, value);
     try {
       response.getWriter().print(result);
     } catch (IOException e) {
       e.printStackTrace();
     }
   }
 }

          
/*    com.xdarkness.framework.clustering.server.HttpClusteringServer
 * JD-Core Version:    0.6.0
 */