 package com.zving.datachannel;
 
 import com.zving.cms.pub.CMSCache;
 import com.zving.cms.pub.CatalogUtil;
 import com.zving.framework.Ajax;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.utility.StringUtil;
 import com.zving.schema.ZCCatalogConfigSchema;
 import java.util.Date;
 
 public class SyncService extends Ajax
 {
   public void sendData()
   {
     String CatalogID = $V("CatalogID");
     String Password = $V("Password");
     String LastAdded = $V("LastAdded");
     String LastModified = $V("LastModified");
     ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(CatalogID);
     if ("Y".equals(config.getAllowInnerGather())) {
       if ((StringUtil.isNotEmpty(config.getInnerGatherPassword())) && 
         (!config.getInnerGatherPassword().equals(Password))) {
         $S("Error", "采集密钥不正确!");
         return;
       }
     }
     else {
       $S("Error", "远程栏目不允许采集!");
     }
     String InnerCode = CatalogUtil.getInnerCode(CatalogID);
 
     QueryBuilder qb = new QueryBuilder(
       "select * from ZCArticle where Status=? and CatalogInnerCode like ? and (AddTime>? or ModifyTime>?) order by id");
     qb.add(30);
     qb.add(InnerCode + "%");
     qb.add(new Date(Long.parseLong(LastAdded)));
     qb.add(new Date(Long.parseLong(LastModified)));
     DataTable dt = qb.executePagedDataTable(100, 0);
     $S("Data", dt);
 
     qb = new QueryBuilder(
       "select * from ZCArticle where Status=? and CatalogInnerCode like ? and (AddTime>? or ModifyTime>?) order by id");
     qb.add(30);
     qb.add(InnerCode + "%");
     qb.add(new Date(Long.parseLong(LastAdded)));
     qb.add(new Date(Long.parseLong(LastModified)));
     dt = qb.executePagedDataTable(100, 0);
 
     $S("Data", dt);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.datachannel.SyncService
 * JD-Core Version:    0.5.4
 */