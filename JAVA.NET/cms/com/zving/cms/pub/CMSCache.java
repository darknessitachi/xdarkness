 package com.zving.cms.pub;
 
 import com.zving.framework.cache.CacheManager;
 import com.zving.framework.cache.CacheProvider;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.utility.LogUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.schema.ZCCatalogConfigSchema;
 import com.zving.schema.ZCCatalogConfigSet;
 import com.zving.schema.ZCCatalogSchema;
 import com.zving.schema.ZCCatalogSet;
 import com.zving.schema.ZCSiteSchema;
 import com.zving.schema.ZCSiteSet;
 import com.zving.schema.ZCTagSchema;
 import com.zving.schema.ZCTagSet;
 
 public class CMSCache extends CacheProvider
 {
   private static final int CACHESIZE = 10000;
   public static final String ProviderName = "CMS";
 
   public String getProviderName()
   {
     return "CMS";
   }
 
   public void onKeySet(String type, Object key, Object value)
   {
     if (type.equals("Catalog")) {
       ZCCatalogSchema catalog = (ZCCatalogSchema)value;
       CacheManager.set(getProviderName(), "CatalogInnerCode", catalog.getInnerCode(), catalog.getID());
 
       Mapx map = CacheManager.getMapx(getProviderName(), "CatalogName");
       if (catalog.getParentID() != 0L)
       {
         if (StringUtil.isEmpty(map.getString(catalog.getSiteID() + "|" + catalog.getName()))) {
           CacheManager.set(getProviderName(), "CatalogName", catalog.getSiteID() + "|" + 
             catalog.getName(), catalog.getID());
         }
 
         if (!StringUtil.isEmpty(map.getString(catalog.getSiteID() + "|" + catalog.getName() + "|" + 
           catalog.getParentID()))) return;
         CacheManager.set(getProviderName(), "CatalogName", catalog.getSiteID() + "|" + 
           catalog.getName() + "|" + catalog.getParentID(), catalog.getID());
       }
       else
       {
         CacheManager.set(getProviderName(), "CatalogName", catalog.getSiteID() + "|" + catalog.getName(), 
           catalog.getID());
         CacheManager.set(getProviderName(), "CatalogName", catalog.getSiteID() + "|" + catalog.getName() + 
           "|" + catalog.getParentID(), catalog.getID());
       }
     }
   }
 
   public void onKeyNotFound(String type, Object key) {
     if ((key == null) || (StringUtil.isEmpty(String.valueOf(key))) || ("null".equals(key))) {
       return;
     }
 
     if (type.equals("Site")) {
       ZCSiteSchema schema = new ZCSiteSchema();
       schema.setID(String.valueOf(key));
       if (schema.fill()) {
         CacheManager.set(getProviderName(), type, schema.getID(), schema);
       }
     }
 
     if (type.equals("CatalogConfig")) {
       ZCCatalogConfigSchema schema = new ZCCatalogConfigSchema();
       String id = String.valueOf(key);
       if (id.indexOf(',') > 0) {
         String[] arr = id.split(",");
         schema.setSiteID(arr[0]);
         schema.setCatalogID("0");
       } else {
         schema.setCatalogID(id);
       }
       ZCCatalogConfigSet set = schema.query();
       if (set.size() > 0) {
         schema = set.get(0);
       } else {
         LogUtil.warn("未找到ID=" + id + "的ZCCatalogConfig记录!");
         return;
       }
       CacheManager.set(getProviderName(), type, id, schema);
     }
 
     if (type.equals("Catalog")) {
       ZCCatalogSchema schema = new ZCCatalogSchema();
       schema.setID(String.valueOf(key));
       if (schema.fill()) {
         CacheManager.set(getProviderName(), type, schema.getID(), schema);
       }
     }
 
     if (type.equals("CatalogInnerCode")) {
       ZCCatalogSchema schema = new ZCCatalogSchema();
       schema.setInnerCode(String.valueOf(key));
       ZCCatalogSet set = schema.query();
       for (int i = 0; i < set.size(); ++i) {
         schema = set.get(i);
 
         CacheManager.set(getProviderName(), "Catalog", schema.getID(), schema);
       }
     }
 
     if (type.equals("CatalogName")) {
       String[] arr = key.toString().split("\\|");
       String SiteID = arr[0];
       String Name = arr[1];
       String ParentID = null;
       if (arr.length > 2) {
         ParentID = arr[2];
       }
       QueryBuilder qb = new QueryBuilder("where SiteID=? and Name=?", SiteID, Name);
       if (StringUtil.isNotEmpty(ParentID)) {
         qb.append(" and ParentID=?", ParentID);
       }
       ZCCatalogSchema schema = new ZCCatalogSchema();
       ZCCatalogSet set = schema.query(qb);
       for (int i = 0; i < set.size(); ++i) {
         schema = set.get(i);
         CacheManager.set(getProviderName(), "Catalog", schema.getID(), schema);
       }
     }
 
     if (type.equals("Tag")) {
       ZCTagSchema tag = new ZCTagSchema();
       String[] arr = key.toString().split("\\|");
       tag.setSiteID(arr[0]);
       tag.setTag(arr[1]);
       ZCTagSet set = tag.query();
       for (int i = 0; i < set.size(); ++i) {
         tag = set.get(i);
         CacheManager.set(getProviderName(), "Tag", key, tag);
       }
     }
   }
 
   public void onTypeNotFound(String type) {
     if (type.equals("Site")) {
       ZCSiteSet set = new ZCSiteSchema().query();
       for (int i = 0; i < set.size(); ++i) {
         CacheManager.set(getProviderName(), type, set.get(i).getID(), set.get(i));
       }
     }
     if (type.equals("CatalogConfig")) {
       CacheManager.setMapx(getProviderName(), type, new Mapx(10000));
     }
     if (type.equals("Catalog")) {
       CacheManager.setMapx(getProviderName(), type, new Mapx(10000));
     }
     if (type.equals("CatalogName")) {
       CacheManager.setMapx(getProviderName(), type, new Mapx(10000));
     }
     if (type.equals("CatalogInnerCode")) {
       CacheManager.setMapx(getProviderName(), type, new Mapx(10000));
     }
 
     if (type.equals("Tag")) {
       CacheManager.setMapx(getProviderName(), type, new Mapx(10000));
       ZCTagSchema tag = new ZCTagSchema();
       ZCTagSet set = tag.query();
       for (int i = 0; i < set.size(); ++i) {
         tag = set.get(i);
         CacheManager.set(getProviderName(), "Tag", tag.getSiteID() + "|" + tag.getTag(), tag);
       }
     }
   }
 
   public static ZCSiteSchema getSite(String id) {
     return (ZCSiteSchema)CacheManager.get("CMS", "Site", id);
   }
 
   public static ZCSiteSchema getSite(long id) {
     return (ZCSiteSchema)CacheManager.get("CMS", "Site", id);
   }
 
   public static ZCCatalogSchema getCatalogByInnerCode(String innerCode) {
     return (ZCCatalogSchema)CacheManager.get("CMS", "Catalog", CacheManager.get("CMS", 
       "CatalogInnerCode", innerCode));
   }
 
   public static ZCCatalogSchema getCatalog(String id) {
     return (ZCCatalogSchema)CacheManager.get("CMS", "Catalog", id);
   }
 
   public static ZCCatalogSchema getCatalog(long id) {
     return (ZCCatalogSchema)CacheManager.get("CMS", "Catalog", id);
   }
 
   public static ZCCatalogSchema getCatalog(long siteID, String name) {
     return (ZCCatalogSchema)CacheManager.get("CMS", "Catalog", CacheManager.get("CMS", 
       "CatalogName", siteID + "|" + name));
   }
 
   public static ZCCatalogSchema getCatalog(String siteID, String name) {
     return (ZCCatalogSchema)CacheManager.get("CMS", "Catalog", CacheManager.get("CMS", 
       "CatalogName", siteID + "|" + name));
   }
 
   public static ZCCatalogSchema getCatalog(long siteID, long parentID, String name) {
     return (ZCCatalogSchema)CacheManager.get("CMS", "Catalog", CacheManager.get("CMS", 
       "CatalogName", siteID + "|" + name + "|" + parentID));
   }
 
   public static ZCCatalogSchema getCatalog(String siteID, long parentID, String name) {
     return (ZCCatalogSchema)CacheManager.get("CMS", "Catalog", CacheManager.get("CMS", 
       "CatalogName", siteID + "|" + name + "|" + parentID));
   }
 
   public static ZCCatalogConfigSchema getCatalogConfig(String id) {
     return (ZCCatalogConfigSchema)CacheManager.get("CMS", "CatalogConfig", id);
   }
 
   public static ZCCatalogConfigSchema getCatalogConfig(long id) {
     return (ZCCatalogConfigSchema)CacheManager.get("CMS", "CatalogConfig", id);
   }
 
   public static ZCTagSchema getTag(long siteID, String tagName) {
     return (ZCTagSchema)CacheManager.get("CMS", "Tag", siteID + "|" + tagName);
   }
 
   public static void removeCatalog(ZCCatalogSchema catalog) {
     CacheManager.remove("CMS", "Catalog", catalog.getID());
     CacheManager.remove("CMS", "CatalogConfig", catalog.getID());
     CacheManager.remove("CMS", "CatalogInnerCode", catalog.getSiteID() + "|" + catalog.getName());
     CacheManager.remove("CMS", "CatalogInnerCode", catalog.getSiteID() + "|" + catalog.getName() + "|" + 
       catalog.getParentID());
   }
 
   public static void removeCatalogSet(ZCCatalogSet catalogSet) {
     for (int i = 0; i < catalogSet.size(); ++i)
       removeCatalog(catalogSet.get(i));
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.pub.CMSCache
 * JD-Core Version:    0.5.4
 */