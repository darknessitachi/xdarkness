 package com.zving.cms.stat;
 
 import com.zving.cms.stat.impl.CatalogStat;
 import com.zving.cms.stat.impl.ClientStat;
 import com.zving.cms.stat.impl.DistrictStat;
 import com.zving.cms.stat.impl.GlobalStat;
 import com.zving.cms.stat.impl.HourStat;
 import com.zving.cms.stat.impl.LeafStat;
 import com.zving.cms.stat.impl.SourceStat;
 import com.zving.cms.stat.impl.URLStat;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.DateUtil;
 import com.zving.framework.utility.LogUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.ServletUtil;
 import com.zving.framework.utility.StringUtil;
 import java.io.IOException;
 import java.io.PrintWriter;
 import java.util.Date;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.commons.logging.Log;
 
 public class VisitHandler
 {
   private static AbstractStat[] items;
   private static long lastTime = System.currentTimeMillis();
   public static final long HOUR = 3600000L;
   public static final long DAY = 86400000L;
   private static boolean isUpdating;
   private static boolean isSimulating;
   private static Object mutex = new Object();
   private static String CurrentPeriod;
 
   public static void update(long current, boolean isNewPeriod, boolean waitFlag)
   {
     VisitCount vc = null;
     while ((waitFlag) && (isUpdating)) {
       try {
         vc = (VisitCount)VisitCount.getInstance().clone();
         Thread.sleep(1000L);
       } catch (InterruptedException e) {
         e.printStackTrace();
       }
     }
     if (!isUpdating)
       synchronized (mutex) {
         if (!isUpdating) {
           if (vc == null) {
             vc = (VisitCount)VisitCount.getInstance().clone();
           }
           isUpdating = true;
           VisitCount vc2 = vc;
           boolean[] mArr = new boolean[items.length];
           for (int i = 0; i < items.length; ++i) {
             mArr[i] = items[i].isNewMonth;
           }
           new Thread(vc2, mArr, isNewPeriod) { private final boolean[] val$mArr;
             private final boolean val$isNewPeriod;
 
             public void run() { LogUtil.getLogger().info("正在更新统计信息......");
               long current = System.currentTimeMillis();
               Transaction tran = new Transaction();
               try {
                 for (int i = 0; i < VisitHandler.items.length; ++i) {
                   VisitHandler.items[i].update(tran, VisitHandler.this, current, this.val$mArr[i], this.val$isNewPeriod);
                 }
                 tran.commit();
               } catch (Throwable t) {
                 t.printStackTrace();
               } finally {
                 VisitHandler.isUpdating = false;
               }
               LogUtil.getLogger().info("更新统计信息耗时 " + (System.currentTimeMillis() - current) + " 毫秒."); } }
 
           .start();
         }
       }
   }
 
   public static void changePeriod(int type, long current)
   {
     synchronized (mutex) {
       String str = DateUtil.toString(new Date(current));
       if (str.equals(CurrentPeriod)) {
         return;
       }
       CurrentPeriod = str;
       if (isSimulating)
         update(current, true, true);
       else {
         update(current, true, false);
       }
       for (int i = 0; i < items.length; ++i)
         items[i].onPeriodChange(type, current);
     }
   }
 
   public static void init(long current)
   {
     if (items == null)
       synchronized (mutex) {
         if (items == null) {
           AbstractStat[] arr = { new ClientStat(), new GlobalStat(), new LeafStat(), 
             new HourStat(), new SourceStat(), new CatalogStat(), new URLStat(), new DistrictStat() };
           for (int i = 0; i < arr.length; ++i) {
             arr[i].init();
           }
           lastTime = current;
           items = arr;
         }
       }
   }
 
   public static void deal(Visit v)
   {
     init(v.VisitTime);
     long current = v.VisitTime;
     synchronized (mutex) {
       if (!DateUtil.toString(new Date(current)).equals(DateUtil.toString(new Date(lastTime)))) {
         changePeriod(1, current);
       }
       lastTime = current;
     }
     for (int i = 0; i < items.length; ++i)
       items[i].deal(v);
   }
 
   public static void deal(HttpServletRequest request, HttpServletResponse response)
   {
     long current = System.currentTimeMillis();
     if (items == null) {
       synchronized (mutex) {
         if (items == null) {
           init(current);
         }
       }
     }
     synchronized (mutex) {
       if (!DateUtil.toString(new Date(current)).equals(DateUtil.toString(new Date(lastTime)))) {
         changePeriod(1, current);
       }
       lastTime = current;
     }
 
     Mapx map = ServletUtil.getParameterMap(request, false);
     map.put("IP", StatUtil.getIP(request));
     map.put("UserAgent", request.getHeader("User-Agent"));
 
     Visit v = new Visit();
     v.UserAgent = map.getString("UserAgent");
     try {
       v.SiteID = Long.parseLong(map.getString("SiteID"));
     } catch (Exception e) {
       try {
         response.getWriter().println("alert('统计时发生错误:" + map.getString("SiteID") + "不是正确的SiteID')");
       } catch (IOException e1) {
         e1.printStackTrace();
       }
       return;
     }
 
     v.UniqueID = ServletUtil.getCookieValue(request, "UniqueID");
 
     if ("KeepAlive".equalsIgnoreCase(map.getString("Event"))) {
       GlobalStat.keepAlive(v.SiteID, v.UniqueID, current);
       return;
     }
     v.CatalogInnerCode = map.getString("CatalogInnerCode");
     v.Type = map.getString("Type");
     if (StringUtil.isEmpty(v.Type)) {
       v.Type = "Other";
     }
     v.Event = map.getString("Event");
     v.LeafID = ((StringUtil.isNotEmpty(map.getString("LeafID"))) ? Long.parseLong(map.getString("LeafID")) : 0L);
     v.IP = map.getString("IP");
     v.VisitTime = current;
     v.Referer = map.getString("Referer");
     v.URL = map.getString("URL");
 
     if (StringUtil.isNotEmpty(v.URL)) {
       v.URL = v.URL.replace('\'', '0').replace('\\', '0');
       String prefix = v.URL.substring(0, 8);
       String tail = v.URL.substring(8);
       tail = tail.replaceAll("/+", "/");
       v.URL = (prefix + tail);
     }
 
     if (StringUtil.isNotEmpty(v.Referer)) {
       v.Referer = v.Referer.replace('\'', '0').replace('\\', '0');
     }
     if (StringUtil.isEmpty(v.UserAgent)) {
       v.UserAgent = "Unknow";
     }
 
     if (!"Unload".equalsIgnoreCase(map.getString("Event"))) {
       try {
         String sites = ServletUtil.getCookieValue(request, "Sites");
         if (StringUtil.isEmpty(v.UniqueID)) {
           v.UniqueID = StatUtil.getUniqueID();
           v.RVFlag = false;
           ServletUtil.setCookieValue(request, response, "UniqueID", -1, v.UniqueID);
           ServletUtil.setCookieValue(request, response, "Sites", -1, "_" + v.SiteID);
         }
         else if ((StringUtil.isNotEmpty(sites)) && (sites.indexOf("_" + v.SiteID) >= 0)) {
           v.RVFlag = true;
         } else {
           v.RVFlag = false;
           ServletUtil.setCookieValue(request, response, "Sites", -1, sites + "_" + v.SiteID);
         }
 
         GlobalStat.dealUniqueID(v);
 
         v.Host = map.getString("Host");
         if (StringUtil.isNotEmpty(v.Host))
           v.Host = v.Host.toLowerCase();
         else {
           v.Host = "无";
         }
         v.CookieEnabled = ("1".equals(map.getString("ce")));
         v.FlashVersion = map.getString("fv");
         v.FlashEnabled = StringUtil.isEmpty(v.FlashVersion);
         v.JavaEnabled = ("1".equals(map.getString("je")));
         v.Language = StatUtil.getLanguage(map.getString("la"));
         if (v.Language.equals("其他")) {
           v.Language = StatUtil.getLanguage("; " + request.getHeader("accept-language") + ";");
         }
         v.OS = StatUtil.getOS(v.UserAgent);
         v.Browser = StatUtil.getBrowser(v.UserAgent);
         v.Referer = map.getString("Referer");
         v.Screen = map.getString("sr");
         v.ColorDepth = map.getString("cd");
         v.District = StatUtil.getDistrictCode(v.IP);
         v.IPFlag = (!GlobalStat.isTodayVisited(String.valueOf(v.SiteID), v.IP, v.VisitTime));
         v.Frequency = Integer.parseInt(map.getString("vq"));
       } catch (Exception ex) {
         ex.printStackTrace();
       }
     } else {
       v.StickTime = new Double(Math.ceil(Double.parseDouble(map.getString("StickTime")))).intValue();
       if (v.StickTime < 0) {
         v.StickTime = 10;
       }
     }
 
     for (int i = 0; i < items.length; ++i)
       items[i].deal(v);
   }
 
   public static void setSimulating(boolean isSimulating)
   {
     isSimulating = isSimulating;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.stat.VisitHandler
 * JD-Core Version:    0.5.4
 */