 package com.zving.cms.datachannel;
 
 import com.zving.cms.pub.CatalogUtil;
 import com.zving.cms.pub.PubFun;
 import com.zving.cms.pub.SiteUtil;
 import com.zving.cms.resource.ConfigImageLib;
 import com.zving.cms.template.PageGenerator;
 import com.zving.framework.Config;
 import com.zving.framework.User;
 import com.zving.framework.data.DataRow;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.messages.LongTimeTask;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaSet;
 import com.zving.framework.utility.Errorx;
 import com.zving.framework.utility.FileUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.pub.NoUtil;
 import com.zving.schema.ZCArticleLogSchema;
 import com.zving.schema.ZCArticleSchema;
 import com.zving.schema.ZCArticleSet;
 import com.zving.schema.ZCAttachmentSchema;
 import com.zving.schema.ZCAudioSchema;
 import com.zving.schema.ZCCatalogSchema;
 import com.zving.schema.ZCImageSchema;
 import com.zving.schema.ZCVideoSchema;
 import com.zving.schema.ZSGoodsSchema;
 import com.zving.schema.ZSGoodsSet;
 import java.util.ArrayList;
 import java.util.Date;
 
 public class Publisher
 {
   public boolean publishAll(long siteID)
   {
     PageGenerator p = new PageGenerator();
     if (p.staticSite(siteID)) {
       Deploy d = new Deploy();
       d.addJobs(siteID, p.getFileList());
       return true;
     }
     return false;
   }
 
   public boolean publishAll(long siteID, LongTimeTask task)
   {
     PageGenerator p = new PageGenerator(task);
     if (p.staticSite(siteID)) {
       Deploy d = new Deploy();
       d.addJobs(siteID, p.getFileList());
       return true;
     }
     return false;
   }
 
   public boolean publishIndex(long siteID, LongTimeTask task)
   {
     PageGenerator p = new PageGenerator(task);
     if (p.staticSiteIndex(siteID)) {
       Deploy d = new Deploy();
       d.addJobs(siteID, p.getFileList());
       return true;
     }
     return false;
   }
 
   public boolean publishIndex(long siteID)
   {
     PageGenerator p = new PageGenerator();
     if (p.staticSiteIndex(siteID)) {
       Deploy d = new Deploy();
       d.addJobs(siteID, p.getFileList());
       return true;
     }
     return false;
   }
 
   public boolean publishArticle(ZCArticleSet articleSet, boolean generateCatalog, LongTimeTask task)
   {
     if ((articleSet == null) || (articleSet.size() == 0)) {
       return true;
     }
     Transaction trans = new Transaction();
     if (publishArticle(trans, articleSet, generateCatalog, task)) {
       if (!trans.commit()) {
         Errorx.addError("更新文章状态失败。");
         return false;
       }
       return true;
     }
     return false;
   }
 
   public boolean publishArticle(Transaction trans, ZCArticleSet articleSet, boolean generateCatalog, LongTimeTask task)
   {
     if ((articleSet == null) || (articleSet.size() <= 0)) {
       return true;
     }
     PageGenerator p = new PageGenerator(task);
     if (task != null) {
       task.setCurrentInfo("开始发布文章");
       task.setPercent(15);
     }
     Deploy d = new Deploy();
     Date date = new Date();
     for (int i = 0; i < articleSet.size(); ++i) {
       ZCArticleSchema article = articleSet.get(i);
       ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
 
       if (article.getStatus() == 40L) {
         String msg = "文档处于下线状态，不能发布。如需发布，请先上线后再操作。";
         if (task != null) {
           task.addError(msg);
         }
         Errorx.addError(msg);
       }
       else
       {
         String workflowName = CatalogUtil.getWorkflow(article.getCatalogID());
 
         if ((StringUtil.isNotEmpty(workflowName)) && (article.getStatus() == 10L)) {
           String msg = "文档需审核，不能发布。";
           if (task != null) {
             task.addError(msg);
           }
           Errorx.addError(msg);
         }
         else if ((StringUtil.isNotEmpty(workflowName)) && (article.getStatus() == 0L) && 
           (!"admin".equals(article.getAddUser()))) {
           String msg = "文档需审核，不能发布。";
           if (task != null) {
             task.addError(msg);
           }
           Errorx.addError(msg);
         }
         else if ((article.getPublishDate() != null) && (article.getPublishDate().getTime() > new Date().getTime())) {
           String msg = "未到上线时间，文档不能发布！";
           if (task != null) {
             task.addError(msg);
           }
           Errorx.addError(msg);
         }
         else
         {
           QueryBuilder qb = new QueryBuilder("update zcarticle set modifyuser=?,modifytime=?,status=? where id=?");
           if (article.getPublishDate() == null) {
             qb = new QueryBuilder(
               "update zcarticle set publishdate=?,modifyuser=?,modifytime=?,status=? where id=?");
             qb.add(date);
             article.setPublishDate(date);
           }
 
           if (User.getCurrent() == null) {
             qb.add("System");
             article.setModifyUser("System");
           } else {
             qb.add(User.getUserName());
             article.setModifyUser(User.getUserName());
           }
           qb.add(date);
           qb.add(30);
           qb.add(article.getID());
           qb.executeNoQuery();
           article.setModifyTime(date);
           article.setStatus(30L);
 
           if (!p.staticDoc("Article", article)) {
             continue;
           }
 
           d.addJobs(articleSet.get(0).getSiteID(), p.getFileList());
 
           if (task != null) {
             task.setPercent(30 + i / articleSet.size() * 50);
             task.setCurrentInfo("正在发布:" + article.getTitle());
           }
           articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
           articleLog.setArticleID(article.getID());
           articleLog.setAction("PUBLISH");
           articleLog.setActionDetail("发布文章");
           if (User.getCurrent() == null)
             articleLog.setAddUser("SYSTEM");
           else {
             articleLog.setAddUser(User.getUserName());
           }
 
           articleLog.setAddTime(date);
           trans.add(articleLog, 1);
 
           trans.add(
             new QueryBuilder("update zcimage set status=30 where status<>? and exists (select 1 from zcimagerela where relaid=? and id=zcimage.id)", 
             30L, article.getID()));
           trans.add(
             new QueryBuilder("update zcvideo set status=30 where status<>? and exists (select id from zcvideorela where relaid=? and id=zcvideo.id)", 
             30L, article.getID()));
           trans
             .add(new QueryBuilder(
             "update zcattachment set status=30 where status<>? and exists (select id from zcattachmentrela where relaid=? and id=zcattachment.id)", 
             30L, article.getID()));
           trans.add(
             new QueryBuilder("update zcaudio set status=30 where status<>? and exists (select id from zcaudiorela where relaid=? and id=zcaudio.id)", 
             30L, article.getID()));
         }
       }
     }
     if (generateCatalog)
     {
       Mapx catalogMap = new Mapx();
       for (int k = 0; k < articleSet.size(); ++k) {
         catalogMap.put(articleSet.get(k).getCatalogID(), articleSet.get(k).getCatalogID());
         String pid = CatalogUtil.getParentID(articleSet.get(k).getCatalogID());
         while ((StringUtil.isNotEmpty(pid)) && (!"null".equals(pid)) && (!"0".equals(pid))) {
           catalogMap.put(pid, pid);
           pid = CatalogUtil.getParentID(pid);
         }
 
       }
 
       Object[] vs = catalogMap.valueArray();
       for (int j = 0; j < catalogMap.size(); ++j) {
         String listpage = CatalogUtil.getData(vs[j].toString()).getString("ListPage");
         if ((StringUtil.isEmpty(listpage)) || ("0".equals(listpage)) || ("-1".equals(listpage))) {
           listpage = "20";
         }
         publishCatalog(Long.parseLong(vs[j].toString()), false, false, Integer.parseInt(listpage));
         if (task != null) {
           task.setPercent(task.getPercent() + 5);
           task.setCurrentInfo("发布栏目页面");
         }
       }
     }
     return true;
   }
 
   public boolean publishDocs(String docType, SchemaSet docSet, boolean generateCatalog, LongTimeTask task) {
     if ((docSet == null) || (docSet.size() <= 0)) {
       return true;
     }
     PageGenerator p = new PageGenerator(task);
     if (task != null) {
       task.setCurrentInfo("开始发布");
     }
 
     Transaction trans = new Transaction();
     Mapx catalogMap = new Mapx();
     long siteID = 0L;
     Deploy d = new Deploy();
     for (int i = 0; i < docSet.size(); ++i) {
       Schema doc = docSet.getObject(i);
 
       if (!p.staticDoc(docType, (Schema)doc.clone()))
       {
         continue;
       }
 
       DataRow row = doc.toDataRow();
 
       siteID = row.getLong("SiteID");
       d.addJobs(siteID, p.getFileList());
 
       Date publishDate = row.getDate("PublishDate");
       if (publishDate == null) {
         publishDate = new Date();
       }
       String modifyUser = null;
       if (User.getCurrent() == null)
         modifyUser = "sys";
       else {
         modifyUser = User.getUserName();
       }
 
       QueryBuilder qb = new QueryBuilder("update zc" + docType + 
         " set publishdate=?,modifytime=?,modifyuser=?,status=? where id=?");
       qb.add(publishDate);
       qb.add(new Date());
       qb.add(modifyUser);
       qb.add(30);
       qb.add(row.get("ID"));
       trans.add(qb);
 
       catalogMap.put(row.getString("catalogid"), row.getString("catalogid"));
 
       if (task != null) {
         task.setPercent(30 + i / docSet.size() * 50);
         task.setCurrentInfo("正在发布:" + row.getString("Name"));
       }
     }
 
     if (!trans.commit()) {
       Errorx.addError("更新文档状态失败。");
       return false;
     }
 
     if (generateCatalog)
     {
       Object[] vs = catalogMap.valueArray();
       for (int j = 0; j < catalogMap.size(); ++j) {
         String listpage = CatalogUtil.getData(vs[j].toString()).getString("ListPage");
         if ((StringUtil.isEmpty(listpage)) || ("0".equals(listpage)) || ("-1".equals(listpage))) {
           listpage = "20";
         }
         publishCatalog(Long.parseLong(vs[j].toString()), false, false, Integer.parseInt(listpage));
         if (task != null) {
           task.setPercent(task.getPercent() + 5);
           task.setCurrentInfo("发布栏目页面");
         }
       }
 
     }
 
     return !Errorx.hasError();
   }
 
   public boolean publishArticle(ZCArticleSet articleSet)
   {
     return publishArticle(articleSet, true, null);
   }
 
   public boolean publishArticle(ZCArticleSet articleSet, LongTimeTask task) {
     return publishArticle(articleSet, true, task);
   }
 
   public boolean publishGoods(ZSGoodsSet goodsSet, LongTimeTask task)
   {
     return publishGoods(goodsSet, true, task);
   }
 
   public boolean publishGoods(ZSGoodsSet goodsSet, boolean generateCatalog, LongTimeTask task) {
     Transaction trans = new Transaction();
     if (publishGoods(trans, goodsSet, generateCatalog, task)) {
       if (!trans.commit()) {
         Errorx.addError("更新商品状态失败。");
         return false;
       }
       return true;
     }
     return false;
   }
 
   public boolean publishGoods(Transaction trans, ZSGoodsSet goodsSet, boolean generateCatalog, LongTimeTask task) {
     PageGenerator p = new PageGenerator(task);
     if (task != null) {
       task.setCurrentInfo("开始发布商品");
     }
     Deploy d = new Deploy();
     Date date = new Date();
     for (int i = 0; i < goodsSet.size(); ++i) {
       ZSGoodsSchema goods = goodsSet.get(i);
 
       if (goods.getStatus().equals("40")) {
         String msg = "商品处于下线状态，不能发布。如需发布，请先上线后再操作。";
         if (task != null) {
           task.addError(msg);
         }
         Errorx.addError(msg);
       }
       else
       {
         String workflowName = CatalogUtil.getWorkflow(goods.getCatalogID());
         if ((StringUtil.isNotEmpty(workflowName)) && (goods.getStatus().equals("10"))) {
           String msg = "商品需审核，不能发布。";
           if (task != null) {
             task.addError(msg);
           }
           Errorx.addError(msg);
         }
         else
         {
           QueryBuilder qb = new QueryBuilder("update zsgoods set modifyuser=?,modifytime=?,status=? where id=?");
           if (goods.getPublishDate() == null) {
             qb = new QueryBuilder("update zsgoods set publishdate=?,modifyuser=?,modifytime=?,status=? where id=?");
             qb.add(date);
             goods.setPublishDate(date);
           }
 
           if (User.getCurrent() == null) {
             qb.add("System");
             goods.setModifyUser("System");
           } else {
             qb.add(User.getUserName());
             goods.setModifyUser(User.getUserName());
           }
           qb.add(date);
           qb.add(30);
           qb.add(goods.getID());
           qb.executeNoQuery();
           goods.setModifyTime(date);
           goods.setStatus("30");
 
           if (!p.staticDoc("Article", goods)) {
             continue;
           }
 
           d.addJobs(goodsSet.get(0).getSiteID(), p.getFileList());
 
           if (task != null) {
             task.setPercent(30 + i / goodsSet.size() * 50);
             task.setCurrentInfo("正在发布:" + goods.getName());
           }
         }
       }
     }
     if (generateCatalog)
     {
       Mapx catalogMap = new Mapx();
       for (int k = 0; k < goodsSet.size(); ++k) {
         catalogMap.put(goodsSet.get(k).getCatalogID(), goodsSet.get(k).getCatalogID());
         String pid = CatalogUtil.getParentID(goodsSet.get(k).getCatalogID());
         while ((StringUtil.isNotEmpty(pid)) && (!"null".equals(pid)) && (!"0".equals(pid))) {
           catalogMap.put(pid, pid);
           pid = CatalogUtil.getParentID(pid);
         }
 
       }
 
       Object[] vs = catalogMap.valueArray();
       for (int j = 0; j < catalogMap.size(); ++j) {
         String listpage = CatalogUtil.getData(vs[j].toString()).getString("ListPage");
         if ((StringUtil.isEmpty(listpage)) || ("0".equals(listpage)) || ("-1".equals(listpage))) {
           listpage = "20";
         }
         publishCatalog(Long.parseLong(vs[j].toString()), false, false, Integer.parseInt(listpage));
         if (task != null) {
           task.setPercent(task.getPercent() + 5);
           task.setCurrentInfo("发布栏目页面");
         }
       }
     }
     return true;
   }
 
   public boolean publishCatalog(long catalogID, boolean child, boolean detail)
   {
     return publishCatalog(catalogID, child, detail, 0, null);
   }
 
   public boolean publishCatalog(long catalogID, boolean child, boolean detail, int publishSize) {
     return publishCatalog(catalogID, child, detail, publishSize, null);
   }
 
   public boolean publishCatalog(long catalogID, boolean child, boolean detail, LongTimeTask task) {
     return publishCatalog(catalogID, child, detail, 0, task);
   }
 
   public boolean publishCatalog(long catalogID, boolean child, boolean detail, int publishPages, LongTimeTask task)
   {
     PageGenerator p = new PageGenerator(task);
     ZCCatalogSchema catalog = new ZCCatalogSchema();
     catalog.setID(catalogID);
     catalog.fill();
 
     if ((publishPages == 0) && (catalog.getListPage() > 0L)) {
       publishPages = (int)catalog.getListPage();
     }
 
     if ((catalog.getListPage() > 0L) && (catalog.getListPage() < publishPages)) {
       publishPages = (int)catalog.getListPage();
     }
 
     if (task != null) {
       task.setPercent(30);
     }
 
     if (!p.staticCatalog(catalog, detail, publishPages)) {
       return false;
     }
 
     if (task != null) {
       task.setPercent(45);
     }
 
     if ((child) && 
       (!p.staticChildCatalog(catalogID, detail, publishPages))) {
       return false;
     }
 
     if (task != null) {
       task.setPercent(75);
     }
 
     if (detail) {
       Transaction trans = new Transaction();
       String wherePart = null;
       String catalogWherePart = " CatalogID=" + catalog.getID();
 
       if (child) {
         catalogWherePart = " CatalogInnerCode like '" + catalog.getInnerCode() + "%' ";
       }
       wherePart = " where " + catalogWherePart + " and status in(" + 20 + "," + 
         30 + ") and publishdate is null";
 
       trans.add(
         new QueryBuilder("update zcarticle set publishDate = ?,status=30" + 
         wherePart, new Date()));
       if (!trans.commit()) {
         return false;
       }
     }
 
     Deploy d = new Deploy();
     d.addJobs(catalog.getSiteID(), p.getFileList());
 
     if (task != null) {
       task.setPercent(100);
     }
 
     return true;
   }
 
   public void deletePubishedFile(ZCImageSchema image) {
     String contextRealPath = Config.getContextRealPath();
     String staticDir = contextRealPath + Config.getValue("Statical.TargetDir").replace('\\', '/');
     String filePath = staticDir + "/" + SiteUtil.getAlias(image.getSiteID()) + "/upload/" + 
       CatalogUtil.getPath(image.getCatalogID()) + "/" + image.getID() + ".shtml";
     FileUtil.delete(filePath);
   }
 
   public void deletePubishedFile(SchemaSet set)
   {
     String contextRealPath = Config.getContextRealPath();
     String staticDir = contextRealPath + Config.getValue("Statical.TargetDir").replace('\\', '/') + "/";
     ArrayList fileList = new ArrayList();
     long siteID = 0L;
     for (int i = 0; i < set.size(); ++i) {
       Schema doc = set.getObject(i);
       siteID = doc.toDataRow().getLong("SiteID");
       if (doc instanceof ZCArticleSchema) {
         ZCArticleSchema article = (ZCArticleSchema)doc;
         String filePath = staticDir + SiteUtil.getAlias(article.getSiteID()) + "/" + 
           PubFun.getArticleURL(article);
         fileList.add(filePath);
       } else if (doc instanceof ZCImageSchema) {
         ZCImageSchema image = (ZCImageSchema)doc;
         String basePath = staticDir + SiteUtil.getAlias(image.getSiteID()) + "/" + image.getPath();
         Mapx configFields = new Mapx();
         configFields.putAll(ConfigImageLib.getImageLibConfig(image.getSiteID()));
         int count = Integer.parseInt(configFields.get("Count").toString());
         for (int j = 1; j <= count; ++j) {
           fileList.add(basePath + j + "_" + image.getFileName());
         }
         fileList.add(basePath + "s_" + image.getFileName());
         fileList.add(basePath + image.getSrcFileName());
         fileList.add(staticDir + SiteUtil.getAlias(image.getSiteID()) + "/" + 
           CatalogUtil.getPath(image.getCatalogID()) + "/" + image.getID() + ".shtml");
       } else if (doc instanceof ZCAttachmentSchema) {
         ZCAttachmentSchema attach = (ZCAttachmentSchema)doc;
         String basePath = staticDir + SiteUtil.getAlias(attach.getSiteID()) + "/" + attach.getPath();
         fileList.add(basePath + attach.getSrcFileName());
         if ((StringUtil.isNotEmpty(attach.getImagePath())) && 
           (attach.getImagePath().indexOf("nopicture.jpg") == -1)) {
           String coverImage = staticDir + "/" + attach.getImagePath();
           fileList.add(coverImage);
         }
         fileList.add(staticDir + SiteUtil.getAlias(attach.getSiteID()) + "/" + 
           CatalogUtil.getPath(attach.getCatalogID()) + "/" + attach.getID() + ".shtml");
       } else if (doc instanceof ZCVideoSchema) {
         ZCVideoSchema video = (ZCVideoSchema)doc;
         String basePath = staticDir + SiteUtil.getAlias(video.getSiteID()) + "/" + video.getPath();
         fileList.add(basePath + video.getSrcFileName());
         fileList.add(basePath + video.getImageName());
         fileList.add(basePath + video.getFileName());
         fileList.add(staticDir + SiteUtil.getAlias(video.getSiteID()) + "/" + 
           CatalogUtil.getPath(video.getCatalogID()) + "/" + video.getID() + ".shtml");
       } else if (doc instanceof ZCAudioSchema) {
         ZCAudioSchema audio = (ZCAudioSchema)doc;
         String basePath = staticDir + SiteUtil.getAlias(audio.getSiteID()) + "/" + audio.getPath() + 
           audio.getSrcFileName();
         fileList.add(basePath + audio.getSrcFileName());
         fileList.add(basePath + audio.getFileName());
         fileList.add(staticDir + SiteUtil.getAlias(audio.getSiteID()) + "/" + 
           CatalogUtil.getPath(audio.getCatalogID()) + "/" + audio.getID() + ".shtml");
       }
 
     }
 
     for (int i = 0; i < fileList.size(); ++i) {
       FileUtil.delete((String)fileList.get(i));
     }
 
     Deploy deploy = new Deploy();
     if ((fileList.size() > 0) && (siteID != 0L))
       deploy.addJobs(siteID, fileList, "delete");
   }
 
   public long publishSetTask(String type, SchemaSet set)
   {
     LongTimeTask ltt = new LongTimeTask(type, set) { private final String val$type;
       private final SchemaSet val$set;
 
       public void execute() { Publisher p = new Publisher();
         p.publishDocs(this.val$type, this.val$set, true, this);
         setPercent(100); }
 
     };
     ltt.setUser(User.getCurrent());
     ltt.start();
     return ltt.getTaskID();
   }
 
   public long deleteFileTask(SchemaSet set) {
     LongTimeTask ltt = new LongTimeTask(set) { private final SchemaSet val$set;
 
       public void execute() { Publisher p = new Publisher();
         if ((this.val$set != null) && (this.val$set.size() > 0)) {
           p.deletePubishedFile(this.val$set);
           String listpage = CatalogUtil.getData(this.val$set.getObject(0).toDataRow().getString("CatalogID")).getString("ListPage");
           if ((StringUtil.isEmpty(listpage)) || ("0".equals(listpage)) || ("-1".equals(listpage))) {
             listpage = "20";
           }
           p.publishCatalog(this.val$set.getObject(0).toDataRow().getLong("CatalogID"), false, false, Integer.parseInt(listpage));
           setPercent(100);
         } }
 
     };
     ltt.setUser(User.getCurrent());
     ltt.start();
     return ltt.getTaskID();
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.datachannel.Publisher
 * JD-Core Version:    0.5.4
 */