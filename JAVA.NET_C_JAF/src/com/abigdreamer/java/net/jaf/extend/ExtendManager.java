 package com.abigdreamer.java.net.jaf.extend;
 
 import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.abigdreamer.java.net.Config;
import com.abigdreamer.java.net.util.LogUtil;
import com.abigdreamer.java.net.util.Mapx;
 
 public class ExtendManager
 {
   private static Mapx extendMap;
   private static Object mutex = new Object();
 
   public static void loadConfig() {
     if (extendMap == null)
       synchronized (mutex) {
         if (extendMap == null) {
           Mapx map = new Mapx();
           String path = Config.getContextRealPath() + "WEB-INF/classes/framework.xml";
           if (new File(path).exists()) {
             readConfigFile(path, map);
           }
           File[] fs = new File(Config.getContextRealPath() + "WEB-INF/classes").listFiles();
           for (int i = 0; i < fs.length; i++) {
             File f = fs[i];
             if ((f.isFile()) && (f.getName().endsWith(".xml"))) {
               readConfigFile(f.getAbsolutePath(), map);
             }
           }
           extendMap = map;
         }
       }
   }
 
   private static void readConfigFile(String path, Mapx map)
   {
     SAXReader reader = new SAXReader(false);
     try
     {
       Document doc = reader.read(new File(path));
       Element root = doc.getRootElement();
       Element extend = root.element("extend");
       if (extend != null) {
         List types = extend.elements("action");
         for (int i = 0; i < types.size(); i++) {
           Element type = (Element)types.get(i);
           String className = type.attributeValue("class");
           String enable = type.attributeValue("enable");
           try {
             Object obj = Class.forName(className).newInstance();
             if (!(obj instanceof IExtendAction)) {
               LogUtil.getLogger().warn("类" + className + "必须继承IExtendAction!");
             }
             else if (!"false".equals(enable)) {
               IExtendAction action = (IExtendAction)obj;
               Mapx targetMap = (Mapx)map.get(action.getTarget());
               if (targetMap == null) {
                 targetMap = new Mapx();
               }
               targetMap.put(action.getClass().getName(), action);
               map.put(action.getTarget(), targetMap);
             }
           } catch (InstantiationException e) {
             e.printStackTrace();
           } catch (IllegalAccessException e) {
             e.printStackTrace();
           } catch (ClassNotFoundException e) {
             e.printStackTrace();
           }
         }
       }
     } catch (DocumentException e) {
       e.printStackTrace();
     }
   }
 
   public static void addExtendAction(IExtendAction action, String target) {
     synchronized (mutex) {
       Mapx targetMap = (Mapx)extendMap.get(target);
       if (targetMap == null) {
         targetMap = new Mapx();
         extendMap.put(target, targetMap);
       }
       targetMap.put(action.getClass().getName(), action);
     }
   }
 
   public static boolean hasAction(String target) {
     loadConfig();
     return extendMap.get(target) != null;
   }
 
   public static IExtendAction[] find(String target) {
     loadConfig();
     Mapx targetMap = (Mapx)extendMap.get(target);
     if (targetMap == null) {
       return new IExtendAction[0];
     }
     IExtendAction[] arr = new IExtendAction[targetMap.size()];
     Object[] vs = targetMap.valueArray();
     for (int i = 0; i < arr.length; i++) {
       arr[i] = ((IExtendAction)vs[i]);
     }
     return arr;
   }
 
   public static void executeAll(String target, Object[] args)
   {
     IExtendAction[] actions = find(target);
     for (int i = 0; i < actions.length; i++)
       actions[i].execute(args);
   }
 }

          
/*    com.xdarkness.framework.extend.ExtendManager
 * JD-Core Version:    0.6.0
 */