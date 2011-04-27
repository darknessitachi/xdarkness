 package com.abigdreamer.java.net;
 
 import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.abigdreamer.java.net.extend.ExtendManager;
import com.abigdreamer.java.net.io.FileUtil;
import com.abigdreamer.java.net.util.Mapx;
 
 public class SessionListener
   implements HttpSessionListener
 {
   private static Mapx map = new Mapx();
   private static Object mutex = new Object();
 
   public void sessionCreated(HttpSessionEvent arg0)
   {
     Config.OnlineUserCount += 1;
     synchronized (mutex) {
       HttpSession hs = arg0.getSession();
       map.put(hs.getId(), hs);
       if (ExtendManager.hasAction("AfterSessionCreate"))
         ExtendManager.executeAll("AfterSessionCreate", new Object[] { hs });
     }
   }
 
   public void sessionDestroyed(HttpSessionEvent arg0)
   {
     Config.OnlineUserCount -= 1;
     User.UserData u = (User.UserData)arg0.getSession().getAttribute("_SKY_USER");
     if (u != null) {
       if (u.isLogin()) {
         Config.LoginUserCount -= 1;
       }
       if (Config.isDebugMode()) {
         FileUtil.delete(Config.getContextRealPath() + "WEB-INF/cache/" + u.getSessionID());
       }
     }
     if (ExtendManager.hasAction("BeforeSessionDestroy")) {
       ExtendManager.executeAll("BeforeSessionDestroy", new Object[] { arg0.getSession() });
     }
     synchronized (mutex) {
       map.remove(arg0.getSession().getId());
     }
   }
 
   public static void clear()
   {
     synchronized (mutex) {
       map.clear();
     }
   }
 
   public static void forceExit()
   {
     synchronized (mutex) {
       Object[] ks = map.keyArray();
       Object[] vs = map.valueArray();
       HttpSession session = null;
       for (int i = 0; i < map.size(); i++) {
         if (ks[i].equals(User.getSessionID())) {
           continue;
         }
         session = (HttpSession)vs[i];
         session.invalidate();
       }
     }
   }
 
   public static void forceExit(String sid)
   {
     synchronized (mutex) {
       HttpSession session = (HttpSession)map.get(sid);
       session.invalidate();
     }
   }
 
   public static User.UserData[] getUsers()
   {
     Object[] vs = map.keyArray();
     User.UserData[] arr = new User.UserData[vs.length];
     HttpSession session = null;
     for (int i = 0; i < vs.length; i++) {
       session = (HttpSession)map.get(vs[i]);
       User.UserData u = (User.UserData)session.getAttribute("_SKY_USER");
       arr[i] = u;
     }
     return arr;
   }
 
   public static User.UserData[] getUsers(String status)
   {
     Object[] vs = map.keyArray();
     ArrayList arr = new ArrayList(vs.length);
     HttpSession session = null;
     for (int i = 0; i < vs.length; i++) {
       session = (HttpSession)map.get(vs[i]);
       User.UserData u = (User.UserData)session.getAttribute("_SKY_USER");
       if (status.equalsIgnoreCase(u.getStatus())) {
         arr.add(u);
       }
     }
     return (User.UserData[])arr.toArray(new User.UserData[arr.size()]);
   }
 
   public static List getUserNames(String status)
   {
     User.UserData[] arr = getUsers(status);
     List userNameArr = new ArrayList(arr.length);
     for (int i = 0; i < arr.length; i++) {
       userNameArr.add(arr[i].getUserName());
     }
     return userNameArr;
   }
 
   public static User.UserData getUser(String userName)
   {
     User.UserData[] users = getUsers();
     for (int i = 0; i < users.length; i++) {
       if (userName.equals(users[i].getUserName())) {
         return users[i];
       }
     }
     return null;
   }
 }

          
/*    com.xdarkness.framework.SessionListener
 * JD-Core Version:    0.6.0
 */