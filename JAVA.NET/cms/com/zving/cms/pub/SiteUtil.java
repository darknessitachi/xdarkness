 package com.zving.cms.pub;
 
 import com.zving.cms.site.CatalogConfig;
 import com.zving.framework.Config;
 import com.zving.framework.cache.CacheManager;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.utility.StringUtil;
 import com.zving.schema.ZCCatalogConfigSchema;
 import com.zving.schema.ZCCatalogConfigSet;
 import com.zving.schema.ZCSiteSchema;
 import com.zving.schema.ZCTagSchema;
 
 public class SiteUtil
 {
   static
   {
     CatalogConfig.initCatalogConfig();
   }
 
   public static String getPath(long siteID)
   {
     return getPath(siteID);
   }
 
   public static String getPath(String siteID)
   {
     String path = Config.getContextPath() + Config.getValue("Statical.TargetDir") + "/" + getAlias(siteID) + "/";
     return path.replaceAll("/+", "/");
   }
 
   public static String getAbsolutePath(long siteID)
   {
     return getAbsolutePath(siteID);
   }
 
   public static String getAbsolutePath(String siteID)
   {
     String path = Config.getContextRealPath() + Config.getValue("Statical.TargetDir") + "/" + getAlias(siteID) + 
       "/";
     return path.replaceAll("/+", "/");
   }
 
   public static String getName(long siteID)
   {
     return getName(siteID);
   }
 
   public static String getName(String siteID)
   {
     if (StringUtil.isEmpty(siteID)) {
       return null;
     }
     ZCSiteSchema site = getSchema(siteID);
     if (site == null) {
       return null;
     }
     return site.getName();
   }
 
   public static synchronized ZCSiteSchema getSchema(String siteID) {
     return CMSCache.getSite(siteID);
   }
 
   public static synchronized ZCSiteSchema getSchema(long siteID) {
     return CMSCache.getSite(siteID);
   }
 
   public static String getAlias(long siteID)
   {
     return getAlias(siteID);
   }
 
   public static String getAlias(String siteID)
   {
     if (StringUtil.isEmpty(siteID)) {
       return null;
     }
     ZCSiteSchema site = getSchema(siteID);
     if (site == null) {
       return null;
     }
     return site.getAlias();
   }
 
   public static String getCode(long siteID)
   {
     return getAlias(siteID);
   }
 
   public static String getCode(String siteID)
   {
     return getAlias(siteID);
   }
 
   public static String getURL(long siteID)
   {
     return getURL(siteID);
   }
 
   public static String getURL(String siteID)
   {
     ZCSiteSchema site = getSchema(siteID);
     if (site == null) {
       return "";
     }
     String url = site.getURL();
     if (StringUtil.isEmpty(url)) {
       return "";
     }
 
     if ("http://".equals(url)) {
       return "";
     }
     if (!url.startsWith("http://")) {
       url = "http://" + url;
     }
     return url;
   }
 
   public static String getArchiveTime(long siteID)
   {
     return getArchiveTime(siteID);
   }
 
   public static synchronized String getArchiveTime(String siteID)
   {
     return CMSCache.getCatalogConfig(siteID + ",0").getArchiveTime();
   }
 
   public static String getAttachDownFlag(long siteID)
   {
     return getAttachDownFlag(siteID);
   }
 
   public static synchronized String getAttachDownFlag(String siteID)
   {
     return CMSCache.getCatalogConfig(siteID + ",0").getAttachDownFlag();
   }
 
   public static void update(long siteID) {
     update(siteID);
   }
 
   public static synchronized boolean getCommentAuditFlag(String siteID)
   {
     ZCSiteSchema site = getSchema(siteID);
     if (site == null) {
       return false;
     }
     String commentAuditFlag = site.getCommentAuditFlag();
     return "Y".equalsIgnoreCase(commentAuditFlag);
   }
 
   public static boolean getCommentAuditFlag(long siteID) {
     return getCommentAuditFlag(siteID);
   }
 
   public static synchronized void update(String siteID) {
     ZCSiteSchema site = new ZCSiteSchema();
     site.setID(siteID);
     if (!site.fill())
       return;
     ZCCatalogConfigSchema config = new ZCCatalogConfigSchema();
     ZCCatalogConfigSet configSet = config.query(new QueryBuilder(" where CatalogID=0 and SiteID=?", siteID));
     if ((configSet != null) && (configSet.size() > 0)) {
       config = configSet.get(0);
       CacheManager.set("CMS", "Config", siteID + ",0", config);
     }
     CacheManager.set("CMS", "Site", siteID, site);
   }
 
   public static boolean isBBSEnable(String siteID)
   {
     ZCSiteSchema site = getSchema(siteID);
     if (site == null) {
       return false;
     }
     String flag = site.getBBSEnableFlag();
     return "Y".equalsIgnoreCase(flag);
   }
 
   public static boolean isShopEnable(String siteID) {
     ZCSiteSchema site = getSchema(siteID);
     if (site == null) {
       return false;
     }
     String flag = site.getShopEnableFlag();
     return "Y".equalsIgnoreCase(flag);
   }
 
   public static ZCTagSchema getTag(long siteID, String tag) {
     return CMSCache.getTag(siteID, tag);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.pub.SiteUtil
 * JD-Core Version:    0.5.4
 */