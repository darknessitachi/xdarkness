 package com.zving.cms.template;
 
 import com.zving.cms.pub.CatalogUtil;
 import com.zving.cms.pub.SiteUtil;
 
 public class TemplateUtil
 {
   public static String getCatalogNames(String innerCode)
   {
     return getCatalogNames(innerCode, "/");
   }
 
   public static String getCatalogNames(String innerCode, String spliter) {
     return getCatalogNames(innerCode, spliter, true, true);
   }
 
   public static String getCatalogNames(String innerCode, String spliter, boolean descFlag, boolean siteNameFlag) {
     StringBuffer sb = new StringBuffer();
     if (descFlag) {
       while (innerCode.length() > 0) {
         sb.append(CatalogUtil.getNameByInnerCode(innerCode));
         sb.append(spliter);
         innerCode = innerCode.substring(0, innerCode.length() - 6);
       }
     } else {
       int length = 6;
       while (length <= innerCode.length()) {
         sb.append(CatalogUtil.getNameByInnerCode(innerCode.substring(0, length)));
         sb.append(spliter);
         length += 6;
       }
     }
     if (siteNameFlag) {
       sb.append(SiteUtil.getName(CatalogUtil.getSiteIDByInnerCode(innerCode)));
       return sb.toString();
     }
     return sb.substring(0, sb.length() - spliter.length());
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.template.TemplateUtil
 * JD-Core Version:    0.5.4
 */