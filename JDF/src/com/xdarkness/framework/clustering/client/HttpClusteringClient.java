 package com.xdarkness.framework.clustering.client;
 
 import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import com.xdarkness.framework.clustering.Clustering.Server;
import com.xdarkness.framework.util.LogUtil;
 
 public class HttpClusteringClient extends ClusteringClient
 {
   private HttpClient httpClient;
   private Server server;
 
   public HttpClusteringClient(Server server)
   {
     this.server = server;
     if (!server.URL.startsWith("http://"))
       throw new RuntimeException("错误的集群服务器URL：" + server.URL);
   }
 
   private void init()
   {
     if (this.httpClient == null) {
       SimpleHttpConnectionManager cm = new SimpleHttpConnectionManager();
       HttpConnectionManagerParams hcmp = new HttpConnectionManagerParams();
       hcmp.setDefaultMaxConnectionsPerHost(1);
       hcmp.setConnectionTimeout(3000);
       hcmp.setSoTimeout(3000);
       cm.setParams(hcmp);
       this.httpClient = new HttpClient(cm);
     }
   }
 
   public String executeMethod(String type, String key, String action, String value) {
     init();
     for (int i = 0; i < this.server.RetryCount; ) {
       try {
         PostMethod pm = new PostMethod(this.server.URL);
         pm.addParameter("Type", type);
         pm.addParameter("Action", action);
         pm.addParameter("Key", key);
         if (value != null) {
           pm.addParameter("Value", value);
         }
         this.httpClient.executeMethod(pm);
         if (pm.getStatusCode() != 200) {
           LogUtil.info("HttpClusteringClient.get()状态代码异常:" + pm.getStatusCode() + ";URL=" + this.server.URL);
         }
         else {
           String result = pm.getResponseBodyAsString();
           return result.trim();
         }
       } catch (Exception e) {
         LogUtil.info("HttpClusteringClient.get()发生异常:" + e.getMessage() + ";URL=" + this.server.URL);
 
         i++;
       }
 
     }
 
     synchronized (this.server) {
       this.server.isAlive = false;
     }
     throw new RuntimeException("HttpClusteringClient.get()发生错误，重试" + this.server.RetryCount + "次皆失败;URL=" + this.server.URL);
   }
 }

          
/*    com.xdarkness.framework.clustering.client.HttpClusteringClient
 * JD-Core Version:    0.6.0
 */