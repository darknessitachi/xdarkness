 package com.xdarkness.cms.webservice;
 
 import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.codehaus.xfire.XFire;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.ServiceFactory;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.transport.http.XFireServlet;

import com.xdarkness.framework.util.LogUtil;
 
 public class XFireConfigServlet extends XFireServlet
 {
   private static Log log = LogUtil.getLogger();
 
   public XFire createXFire() throws ServletException {
     XFire xfire = super.createXFire();
     ServiceFactory factory = new ObjectServiceFactory(xfire.getTransportManager(), null);
 
     Service service = factory.create(CmsService.class);
     service.setProperty("xfire.serviceImplClass", CmsServiceImpl.class);
     xfire.getServiceRegistry().register(service);
 
     Service userService = factory.create(UserOperator.class);
     userService.setProperty("xfire.serviceImplClass", UserOperatorImpl.class);
     xfire.getServiceRegistry().register(userService);
 
     if ((xfire == null) || (xfire.getServiceRegistry() == null) || (xfire.getServiceRegistry().getServices() == null) || (xfire.getServiceRegistry().getServices().size() == 0)) {
       xfire = super.createXFire();
     }
 
     log.info("发布webservice");
 
     return xfire;
   }
 }

          
/*    com.xdarkness.cms.webservice.XFireConfigServlet
 * JD-Core Version:    0.6.0
 */