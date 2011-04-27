 package com.zving.cms.datachannel;
 
 import com.zving.cms.pub.CatalogUtil;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.utility.LogUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.pub.ConfigEanbleTaskManager;
 import com.zving.schema.ZCArticleSchema;
 import com.zving.schema.ZCArticleSet;
 import com.zving.schema.ZCAttachmentSchema;
 import com.zving.schema.ZCAttachmentSet;
 import com.zving.schema.ZCAudioSchema;
 import com.zving.schema.ZCAudioSet;
 import com.zving.schema.ZCCatalogConfigSchema;
 import com.zving.schema.ZCCatalogSchema;
 import com.zving.schema.ZCImageSchema;
 import com.zving.schema.ZCImageSet;
 import com.zving.schema.ZCVideoSchema;
 import com.zving.schema.ZCVideoSet;
 import java.io.PrintStream;
 import java.util.Date;
 import org.apache.commons.logging.Log;
 
 public class PublishTaskManager extends ConfigEanbleTaskManager
 {
   public void execute(long id)
   {
     LogUtil.getLogger().info("PublishTaskManager开始发布内容");
 
     ZCCatalogConfigSchema config = new ZCCatalogConfigSchema();
     config.setID(id);
     if (config.fill()) {
       ZCCatalogSchema catalog = new ZCCatalogSchema();
       catalog.setID(config.getCatalogID());
       if (!catalog.fill()) {
         return;
       }
       Publisher p = new Publisher();
       if (catalog.getType() == 1L) {
         ZCArticleSet set = new ZCArticleSet();
         if (StringUtil.isNotEmpty(config.getCatalogID()))
           set = new ZCArticleSchema().query(
             new QueryBuilder(" where status =20 and (publishdate<=? or publishdate is null) and CatalogInnerCode like ?", 
             new Date(), 
             CatalogUtil.getInnerCode(config.getCatalogID()) + "%"));
         else {
           set = new ZCArticleSchema().query(
             new QueryBuilder(" where status =20 and (publishdate<=? or publishdate is null) and SiteID=?", 
             new Date(), config.getSiteID()));
         }
         p.publishArticle(set);
       } else if (catalog.getType() == 5L) {
         ZCVideoSet set = new ZCVideoSet();
         System.out.println(CatalogUtil.getInnerCode(config.getCatalogID()));
         if (StringUtil.isNotEmpty(config.getCatalogID())) {
           set = new ZCVideoSchema().query(
             new QueryBuilder(" where status =20 and (publishdate<=? or publishdate is null) and CatalogInnerCode like ?", 
             new Date(), CatalogUtil.getInnerCode(config.getCatalogID()) + "%"));
         }
         else {
           set = new ZCVideoSchema().query(
             new QueryBuilder(" where status =20 and (publishdate<=? or publishdate is null) and SiteID=?", 
             new Date(), config.getSiteID()));
         }
         p.publishDocs("video", set, true, null);
       } else if (catalog.getType() == 4L) {
         ZCImageSet set = new ZCImageSet();
         if (StringUtil.isNotEmpty(config.getCatalogID()))
           set = new ZCImageSchema().query(
             new QueryBuilder(" where status =20 and (publishdate<=? or publishdate is null) and CatalogInnerCode like ?", 
             new Date(), CatalogUtil.getInnerCode(config.getCatalogID()) + "%"));
         else {
           set = new ZCImageSchema().query(
             new QueryBuilder(" where status =20 and (publishdate<=? or publishdate is null) and SiteID=?", 
             new Date(), config.getSiteID()));
         }
         p.publishDocs("image", set, true, null);
       } else if (catalog.getType() == 6L) {
         ZCAudioSet set = new ZCAudioSet();
         if (StringUtil.isNotEmpty(config.getCatalogID())) {
           set = new ZCAudioSchema().query(
             new QueryBuilder(" where status =20 and (publishdate<=? or publishdate is null) and CatalogInnerCode like ?", 
             new Date(), CatalogUtil.getInnerCode(config.getCatalogID()) + "%"));
           System.out.println(set.size());
         } else {
           set = new ZCAudioSchema().query(
             new QueryBuilder(" where status =20 and (publishdate<=? or publishdate is null) and SiteID=?", 
             new Date(), config.getSiteID()));
         }
         p.publishDocs("audio", set, true, null);
       } else if (catalog.getType() == 7L) {
         ZCAttachmentSet set = new ZCAttachmentSet();
         if (StringUtil.isNotEmpty(config.getCatalogID())) {
           set = new ZCAttachmentSchema().query(
             new QueryBuilder(" where status =20 and (publishdate<=? or publishdate is null) and CatalogInnerCode like ?", 
             new Date(), CatalogUtil.getInnerCode(config.getCatalogID()) + "%"));
           System.out.println(set.size());
         } else {
           set = new ZCAttachmentSchema().query(
             new QueryBuilder(" where status =20 and (publishdate<=? or publishdate is null) and SiteID=?", 
             new Date(), config.getSiteID()));
         }
         p.publishDocs("attachment", set, true, null);
       }
     }
 
     LogUtil.getLogger().info("PublishTaskManager结束发布内容");
   }
 
   public String getCode() {
     return "Publisher";
   }
 
   public String getName() {
     return "内容发布任务";
   }
 
   public Mapx getConfigEnableTasks() {
     Mapx map = new Mapx();
     map.put("-1", "发布全部文档");
     map.put("0", "全局区块发布");
     return map;
   }
 
   public boolean isRunning(long arg0) {
     return false;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.datachannel.PublishTaskManager
 * JD-Core Version:    0.5.4
 */