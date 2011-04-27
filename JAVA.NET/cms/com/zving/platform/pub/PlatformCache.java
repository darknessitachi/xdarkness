 package com.zving.platform.pub;
 
 import com.zving.framework.cache.CacheManager;
 import com.zving.framework.cache.CacheProvider;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.schema.ZDBranchSchema;
 import com.zving.schema.ZDBranchSet;
 import com.zving.schema.ZDRoleSchema;
 import com.zving.schema.ZDRoleSet;
 import com.zving.schema.ZDUserRoleSchema;
 import com.zving.schema.ZDUserRoleSet;
 import com.zving.schema.ZDUserSchema;
 import com.zving.schema.ZDUserSet;
 
 public class PlatformCache extends CacheProvider
 {
   public static final String ProviderName = "Platform";
 
   public String getProviderName()
   {
     return "Platform";
   }
 
   public void onKeyNotFound(String type, Object key) {
     if (type.equals("UserRole")) {
       ZDUserRoleSchema schema = new ZDUserRoleSchema();
       schema.setUserName(key.toString());
       ZDUserRoleSet set = schema.query();
       StringBuffer sb = new StringBuffer();
       for (int i = 0; i < set.size(); ++i) {
         if (i != 0) {
           sb.append(",");
         }
         sb.append(set.get(i).getRoleCode());
       }
       if (set.size() > 0)
         CacheManager.set(getProviderName(), type, key, sb.toString());
     }
   }
 
   public void onTypeNotFound(String type)
   {
     if (type.equals("User")) {
       ZDUserSet set = new ZDUserSchema().query();
       for (int i = 0; i < set.size(); ++i) {
         CacheManager.set(getProviderName(), type, set.get(i).getUserName(), set.get(i));
       }
     }
     if (type.equals("Role")) {
       ZDRoleSet set = new ZDRoleSchema().query();
       for (int i = 0; i < set.size(); ++i) {
         CacheManager.set(getProviderName(), type, set.get(i).getRoleCode(), set.get(i));
       }
     }
     if (type.equals("UserRole")) {
       CacheManager.setMapx(getProviderName(), "UserRole", new Mapx());
     }
     if (type.equals("Branch")) {
       ZDBranchSet set = new ZDBranchSchema().query();
       for (int i = 0; i < set.size(); ++i)
         CacheManager.set(getProviderName(), type, set.get(i).getBranchInnerCode(), set.get(i));
     }
   }
 
   public static ZDUserSchema getUser(String userName)
   {
     return (ZDUserSchema)CacheManager.get("Platform", "User", userName);
   }
 
   public static ZDRoleSchema getRole(String roleCode) {
     return (ZDRoleSchema)CacheManager.get("Platform", "Role", roleCode);
   }
 
   public static String getUserRole(String userName) {
     return (String)CacheManager.get("Platform", "UserRole", userName);
   }
 
   public static ZDBranchSchema getBranch(String innerCode) {
     return (ZDBranchSchema)CacheManager.get("Platform", "Branch", innerCode);
   }
 
   public static void removeRole(String roleCode) {
     CacheManager.remove("Platform", "Role", roleCode);
     Mapx map = CacheManager.getMapx("Platform", "UserRole");
     synchronized (map) {
       Object[] ks = map.keyArray();
       Object[] vs = map.valueArray();
       roleCode = "," + roleCode + ",";
       for (int i = 0; i < vs.length; ++i) {
         String ur = "," + vs[i] + ",";
         if (ur.indexOf(roleCode) >= 0) {
           ur = StringUtil.replaceEx(ur, roleCode, ",");
         }
         ur = ur.substring(0, ur.length() - 1);
         map.put(ks[i], ur);
       }
     }
   }
 
   public static void addUserRole(String userName, String roleCode) {
     String roles = (String)CacheManager.get("Platform", "UserRole", userName);
     if (StringUtil.isEmpty(roles))
       CacheManager.set("Platform", "UserRole", userName, roleCode);
     else
       CacheManager.set("Platform", "UserRole", userName, roles + "," + roleCode);
   }
 
   public static void removeUserRole(String userName, String roleCode)
   {
     String roles = (String)CacheManager.get("Platform", "UserRole", userName);
     if (StringUtil.isEmpty(roles)) {
       return;
     }
     String ur = "," + roles + ",";
     if (ur.indexOf(roleCode) >= 0) {
       ur = StringUtil.replaceEx(ur, roleCode, ",");
     }
     ur = ur.substring(0, ur.length() - 1);
     CacheManager.set("Platform", "UserRole", userName, ur);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.platform.pub.PlatformCache
 * JD-Core Version:    0.5.4
 */