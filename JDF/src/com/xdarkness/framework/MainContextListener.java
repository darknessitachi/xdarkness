 package com.xdarkness.framework;
 
 import java.beans.Introspector;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.LogManager;

import com.xdarkness.framework.clustering.server.SocketClusteringServer;
import com.xdarkness.framework.extend.ExtendManager;
import com.xdarkness.framework.schedule.CronManager;
 
 public class MainContextListener
   implements ServletContextListener
 {
   private CronManager manager;
 
   public void contextDestroyed(ServletContextEvent arg0)
   {
     try
     {
       if (this.manager != null) {
         this.manager.destory();
       }
       SessionListener.clear();
 
       SocketClusteringServer.stop();
 
       LogManager.shutdown();
       LogFactory.releaseAll();
       Introspector.flushCaches();
     } catch (Throwable t) {
       t.printStackTrace();
     }
   }
 
   public void contextInitialized(ServletContextEvent arg0)
   {
     ServletContext sc = arg0.getServletContext();
     Config.configMap.put("System.ContainerInfo", sc.getServerInfo());
     Config.getJBossInfo();
     Config.init();
     this.manager = CronManager.getInstance();
     ExtendManager.loadConfig();
   }
 }

          
/*    com.xdarkness.framework.MainContextListener
 * JD-Core Version:    0.6.0
 */