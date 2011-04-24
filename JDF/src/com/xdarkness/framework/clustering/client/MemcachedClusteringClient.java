 package com.xdarkness.framework.clustering.client;
 
 import com.xdarkness.framework.clustering.Clustering.Server;
 
 public class MemcachedClusteringClient extends ClusteringClient
 {
   private Server server;
 
   public MemcachedClusteringClient(Server server)
   {
     this.server = server;
   }
 
   public String executeMethod(String type, String action, String key, String value) {
     return null;
   }
 }

          
/*    com.xdarkness.framework.clustering.client.MemcachedClusteringClient
 * JD-Core Version:    0.6.0
 */