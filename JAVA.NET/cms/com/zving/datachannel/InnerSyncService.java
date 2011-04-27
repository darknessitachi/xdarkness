 package com.zving.datachannel;
 
 import com.zving.cms.pub.CMSCache;
 import com.zving.cms.pub.CatalogUtil;
 import com.zving.cms.pub.SiteUtil;
 import com.zving.cms.site.Catalog;
 import com.zving.framework.Ajax;
 import com.zving.framework.Config;
 import com.zving.framework.Framework;
 import com.zving.framework.RequestImpl;
 import com.zving.framework.data.BlockingTransaction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.DataTableUtil;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.messages.LongTimeTask;
 import com.zving.framework.utility.CaseIgnoreMapx;
 import com.zving.framework.utility.FileUtil;
 import com.zving.framework.utility.LogUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.NumberUtil;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.pub.ImageUtilEx;
 import com.zving.platform.pub.NoUtil;
 import com.zving.platform.pub.OrderUtil;
 import com.zving.schema.ZCArticleSchema;
 import com.zving.schema.ZCArticleSet;
 import com.zving.schema.ZCCatalogConfigSchema;
 import com.zving.schema.ZCCatalogSchema;
 import com.zving.schema.ZCCatalogSet;
 import com.zving.schema.ZCImageRelaSchema;
 import com.zving.schema.ZCImageRelaSet;
 import com.zving.schema.ZCImageSchema;
 import com.zving.schema.ZCImageSet;
 import com.zving.schema.ZCInnerDeploySchema;
 import com.zving.schema.ZCInnerGatherSchema;
 import com.zving.schema.ZDColumnRelaSchema;
 import com.zving.schema.ZDColumnRelaSet;
 import com.zving.schema.ZDColumnSchema;
 import com.zving.schema.ZDColumnSet;
 import com.zving.schema.ZDColumnValueSchema;
 import com.zving.schema.ZDColumnValueSet;
 import java.io.File;
 import java.util.Date;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 
 public class InnerSyncService extends Ajax
 {
   static Pattern imagePattern = Pattern.compile("<img\\s.*?zcmsimagerela=\"(.*?)\".*?>", 2);
 
   private static long ExecutingGatherID = 0L;
 
   private static long ExecutingDeployID = 0L;
 
   public void sendData()
   {
     String CatalogID = $V("CatalogID");
     String Password = $V("Password");
     String LastTime = $V("LastTime");
     String SyncArticleModify = $V("SyncArticleModify");
     String SyncCatalogInsert = $V("SyncCatalogInsert");
     String SyncCatalogModify = $V("SyncCatalogModify");
 
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
 
     prepareData(this.Response, InnerCode, new Date(Long.parseLong(LastTime)), "Y".equals(SyncArticleModify), "Y"
       .equals(SyncCatalogInsert), "Y".equals(SyncCatalogModify), false);
     $S("Success", "1");
   }
 
   public void sendImage()
   {
     String ID = $V("ID");
     String FileName = $V("FileName");
     ZCImageSchema image = new ZCImageSchema();
     image.setID(ID);
     if (!image.fill()) {
       $S("Error", "错误的图片ID:" + ID);
       return;
     }
     if (!image.getFileName().equals(FileName)) {
       $S("Error", "错误的图片文件名:" + FileName);
     }
     String path = Config.getContextRealPath() + Config.getValue("UploadDir") + "/" + 
       SiteUtil.getAlias(image.getSiteID()) + "/" + image.getPath() + "/" + image.getSrcFileName();
     path = path.replaceAll("\\/+", "/");
     byte[] bs = FileUtil.readByte(path);
     String data = StringUtil.base64Encode(bs);
     $S("Data", data);
     $S("Success", "1");
   }
 
   public void receiveData()
   {
     String CatalogID = $V("CatalogID");
     String SourceCatalogID = $V("SourceCatalogID");
     String Password = $V("Password");
     String ServerAddr = $V("ServerAddr");
     ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(CatalogID);
     if ("Y".equals(config.getAllowInnerDeploy())) {
       if ((StringUtil.isNotEmpty(config.getInnerDeployPassword())) && 
         (!config.getInnerDeployPassword().equals(Password))) {
         $S("Error", "分发密钥不正确!");
         return;
       }
     }
     else {
       $S("Error", "远程栏目不允许分发!");
     }
     dealData(this.Request, CatalogID, ServerAddr, SourceCatalogID, null);
     $S("Success", "1");
   }
 
   public static void executeGather(ZCInnerGatherSchema gather, LongTimeTask task)
   {
     if (ExecutingGatherID == gather.getID()) {
       return;
     }
     ExecutingGatherID = gather.getID();
     if (task == null)
       task = LongTimeTask.createEmptyInstance();
     try
     {
       String data = gather.getTargetCatalog();
       DataTable dt = DataTableUtil.txtToDataTable(data, null, "\t", "\n");
       for (int i = 0; i < dt.getRowCount(); ++i) {
         String ServerAddr = dt.getString(i, "ServerAddr");
         String SiteID = dt.getString(i, "SiteID");
         String SiteName = dt.getString(i, "SiteName");
         String CatalogID = dt.getString(i, "CatalogID");
         String CatalogName = dt.getString(i, "CatalogName");
         String Password = dt.getString(i, "Password");
         String LastTime = "0";
         if (dt.getDataColumn("LastTime") != null)
           LastTime = dt.getString(i, "LastTime");
         else {
           dt.insertColumn("LastTime");
         }
         if (StringUtil.isEmpty(LastTime)) {
           LastTime = "0";
         }
         task.setCurrentInfo("正在采集站点 [" + SiteName + "] 下的栏目 [" + CatalogName + "]");
         Mapx map = null;
         Date NextTime = null;
         if (ServerAddr.equalsIgnoreCase("localhost")) {
           map = new Mapx();
           NextTime = prepareData(map, CatalogUtil.getInnerCode(CatalogID), 
             new Date(Long.parseLong(LastTime)), "Y".equals(gather.getSyncArticleModify()), "Y"
             .equals(gather.getSyncCatalogInsert()), "Y".equals(gather.getSyncCatalogModify()), 
             false);
           map.put("Success", "1");
         } else {
           RequestImpl Request = new RequestImpl();
           Request.put("SiteID", SiteID);
           Request.put("CatalogID", CatalogID);
           Request.put("Password", Password);
           Request.put("SyncArticleModify", gather.getSyncArticleModify());
           Request.put("SyncCatalogInsert", gather.getSyncCatalogInsert());
           Request.put("SyncCatalogModify", gather.getSyncCatalogModify());
           Request.put("LastTime", LastTime);
           map = Framework.callRemoteMethod(ServerAddr, "com.zving.datachannel.InnerSyncService.sendData", 
             Request);
           NextTime = new Date(Long.parseLong(LastTime));
         }
         if (map == null) {
           task.setCurrentInfo("网站群采集任务执行失败：无法访问服务器" + ServerAddr);
           task.setPercent(100);
         }
         Date NextTime;
         Mapx map;
         String CatalogName;
         String CatalogID;
         String SiteName;
         String ServerAddr;
         while (true)
         {
           return;
           String LastTime;
           String Password;
           String SiteID;
           if ("1".equals(map.get("Success"))) break;
           task.setCurrentInfo("网站群采集任务执行失败：" + map.getString("Error"));
           task.setPercent(100);
         }
 
         if (task != null) {
           task.setCurrentInfo("正在保存站点 [" + SiteName + "] 下的栏目 [" + CatalogName + "] 下的数据");
         }
         dealData(map, CatalogUtil.getIDByInnerCode(gather.getCatalogInnerCode()), ServerAddr, CatalogID, null);
         dt.set(i, "LastTime", NextTime.getTime());
       }
       gather.setTargetCatalog(dt.toString());
     }
     finally {
       ExecutingGatherID = 0L; } ExecutingGatherID = 0L;
   }
 
   public static synchronized void executeDeploy(ZCInnerDeploySchema deploy, LongTimeTask task)
   {
     if (ExecutingDeployID == deploy.getID()) {
       return;
     }ExecutingDeployID = deploy.getID();
     DataTable dt;
     int i;
     try { if (task == null) {
         task = LongTimeTask.createEmptyInstance();
       }
       String SyncArticleModify = deploy.getSyncArticleModify();
       String SyncCatalogInsert = deploy.getSyncCatalogInsert();
       String SyncCatalogModify = deploy.getSyncCatalogModify();
 
       String InnerCode = deploy.getCatalogInnerCode();
       String ServiceContext = Config.getValue("ServicesContext");
       if (ServiceContext.endsWith("/")) {
         ServiceContext = ServiceContext.substring(0, ServiceContext.length() - 1);
       }
       ServiceContext = ServiceContext.substring(0, ServiceContext.lastIndexOf("/") + 1);
       String SourceCatalogID = CatalogUtil.getIDByInnerCode(deploy.getCatalogInnerCode());
       String data = deploy.getTargetCatalog();
       dt = DataTableUtil.txtToDataTable(data, null, "\t", "\n");
       i = 0; break label832:
       String ServerAddr = dt.getString(i, "ServerAddr");
       String SiteID = dt.getString(i, "SiteID");
       String SiteName = dt.getString(i, "SiteName");
       String CatalogID = dt.getString(i, "CatalogID");
       String CatalogName = dt.getString(i, "CatalogName");
       String Password = dt.getString(i, "Password");
       task.setCurrentInfo("正在分发数据到站点 [" + SiteName + "] 下的栏目 [" + CatalogName + "]");
 
       RequestImpl Request = new RequestImpl();
       Request.put("SiteID", SiteID);
       Request.put("CatalogID", CatalogID);
       Request.put("SourceCatalogID", SourceCatalogID);
       Request.put("Password", Password);
       Request.put("ServerAddr", ServiceContext);
       String LastTime = "0";
       if (dt.getDataColumn("LastTime") != null)
         LastTime = dt.getString(i, "LastTime");
       else {
         dt.insertColumn("LastTime");
       }
       if ((StringUtil.isEmpty(LastTime)) || (LastTime.equalsIgnoreCase("_ZVING_NULL"))) {
         LastTime = "0";
       }
       Request.put("LastTime", LastTime);
 
       Date NextTime = prepareData(Request, InnerCode, new Date(Long.parseLong(LastTime)), "Y"
         .equals(SyncArticleModify), "Y".equals(SyncCatalogInsert), "Y".equals(SyncCatalogModify), 
         deploy.getDeployType().equals("2"));
 
       String md5 = StringUtil.md5Hex(ServerAddr + "," + SiteID + "," + CatalogID);
       DataTable articles = Request.getDataTable("ZCArticle-Add");
       if (articles != null) {
         for (int j = articles.getRowCount() - 1; j >= 0; --j) {
           if ((!deploy.getDeployType().equals("2")) || 
             (articles.getString(j, "ClusterTarget").indexOf(md5) >= 0)) continue;
           articles.deleteRow(j);
         }
       }
 
       articles = Request.getDataTable("ZCArticle-Modify");
       if (articles != null) {
         for (int j = articles.getRowCount() - 1; j >= 0; --j) {
           if ((!deploy.getDeployType().equals("2")) || 
             (articles.getString(j, "ClusterTarget").indexOf(md5) >= 0)) continue;
           articles.deleteRow(j);
         }
 
       }
 
       Mapx map = null;
       if (ServerAddr.equalsIgnoreCase("localhost"))
         if (deploy.getAddUser().equals("admin")) {
           map = new Mapx();
 
           dealData(Request, CatalogID, ServerAddr, SourceCatalogID, deploy);
           map.put("Success", "1");
         } else {
           InnerSyncService ss = new InnerSyncService();
           ss.setRequest(Request);
           ss.receiveData();
           map = ss.getResponse();
         }
       else {
         map = Framework.callRemoteMethod(ServerAddr, "com.zving.datachannel.InnerSyncService.receiveData", 
           Request);
       }
       if (map == null) {
         task.setCurrentInfo("网站群分发任务执行失败：无法访问远程服务器" + ServerAddr);
         task.setPercent(100);
       }
       Date NextTime;
       while (true)
       {
         return;
         Mapx map;
         DataTable articles;
         String md5;
         String LastTime;
         RequestImpl Request;
         String Password;
         String CatalogName;
         String CatalogID;
         String SiteName;
         String SiteID;
         String ServerAddr;
         if ("1".equals(map.get("Success"))) break;
         task.setCurrentInfo("网站群分发任务执行失败：" + map.getString("Error"));
         task.setPercent(100);
       }
 
       dt.set(i, "LastTime", NextTime.getTime());
       deploy.setTargetCatalog(dt.toString());
       deploy.update();
 
       label832: ++i; }
     finally
     {
       ExecutingDeployID = 0L; } ExecutingDeployID = 0L;
   }
 
   public static synchronized Date prepareData(Mapx map, String InnerCode, Date LastTime, boolean isSyncArticleModify, boolean isSyncCatalogInsert, boolean isSyncCatalogModify, boolean isManualDeploy)
   {
     String siteurl = SiteUtil.getURL(CatalogUtil.getSiteIDByInnerCode(InnerCode)) + "/";
     if (!siteurl.endsWith("/")) {
       siteurl = siteurl + "/";
     }
     String uploadPath = Config.getContextPath() + Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(CatalogUtil.getSiteIDByInnerCode(InnerCode)) + "/";
     uploadPath = uploadPath.replaceAll("/+", "/");
 
     Date NextTime = new Date(LastTime.getTime());
 
     QueryBuilder qb = new QueryBuilder(
       "select * from ZCArticle where (Status=? or Status=?) and CatalogInnerCode like ? and AddTime>?");
     qb.add(30);
     qb.add(20);
     qb.add(InnerCode + "%");
     qb.add(LastTime);
     if (isManualDeploy) {
       qb.append(" and ClusterTarget is not null and ClusterTarget<>''");
     }
     qb.append(" order by id");
     DataTable dt = qb.executePagedDataTable(50, 0);
     map.put("ZCArticle-Add", dt);
     for (int i = 0; i < dt.getRowCount(); ++i) {
       if (dt.getDate(i, "AddTime").getTime() > NextTime.getTime()) {
         NextTime = dt.getDate(i, "AddTime");
       }
       String content = dt.getString(i, "Content");
       content = content.replaceAll(uploadPath + "upload/Attach", siteurl + "upload/Attach");
       content = content.replaceAll(uploadPath + "upload/Audio", siteurl + "upload/Audio");
       content = content.replaceAll("../upload/Video", siteurl + "upload/Video");
       content = content.replaceAll("zcmsvideorela=\".*?\"", "");
       content = content.replaceAll("zcmsattachrela=\".*?\"", "");
       dt.set(i, "Content", content);
     }
     String ids = StringUtil.join(dt.getColumnValues("ID"));
 
     if (isSyncArticleModify) {
       qb = new QueryBuilder(
         "select * from ZCArticle where (Status=? or Status=?) and CatalogInnerCode like ? and ModifyTime>? and AddTime<?");
       qb.add(30);
       qb.add(20);
       qb.add(InnerCode + "%");
       qb.add(LastTime);
       qb.add(LastTime);
       if (isManualDeploy) {
         qb.append(" and ClusterTarget is not null and ClusterTarget<>''");
       }
       qb.append(" order by id");
       dt = qb.executePagedDataTable(50, 0);
       map.put("ZCArticle-Modify", dt);
       for (int i = 0; i < dt.getRowCount(); ++i) {
         if (dt.getDate(i, "ModifyTime").getTime() > NextTime.getTime()) {
           NextTime = dt.getDate(i, "ModifyTime");
         }
         String content = dt.getString(i, "Content");
         content = content.replaceAll(uploadPath + "upload/Attach", siteurl + "upload/Attach");
         content = content.replaceAll(uploadPath + "upload/Audio", siteurl + "upload/Audio");
         content = content.replaceAll("../upload/Video", siteurl + "upload/Video");
         content = content.replaceAll("zcmsvideorela=\".*?\"", "");
         content = content.replaceAll("zcmsattachrela=\".*?\"", "");
         dt.set(i, "Content", content);
       }
       if (StringUtil.isNotEmpty(ids))
         ids = ids + "," + StringUtil.join(dt.getColumnValues("ID"));
       else {
         ids = StringUtil.join(dt.getColumnValues("ID"));
       }
       if (ids.endsWith(",")) {
         ids = ids.substring(0, ids.length() - 1);
       }
 
       qb = new QueryBuilder(
         "select distinct ID from BZCArticle where CatalogInnerCode like ? and not exists (select 1 from ZCArticle where ZCArticle.ID=BZCArticle.ID)");
       qb.add(InnerCode + "%");
       dt = qb.executeDataTable();
       map.put("DeletedArticles", StringUtil.join(dt.getColumnValues(0)));
     }
 
     if (StringUtil.isNotEmpty(ids))
     {
       dt = new QueryBuilder("select * from ZDColumnValue where RelaID in (" + ids + ") and RelaType=?", 
         "2").executeDataTable();
       map.put("ZDColumnValue-Article", dt);
 
       dt = new QueryBuilder("select * from ZCImageRela where RelaID in (" + ids + ") and RelaType=?", 
         "ArticleImage").executeDataTable();
       map.put("ZCImageRela", dt);
 
       ids = StringUtil.join(dt.getColumnValues("ID"));
       if (StringUtil.isNotEmpty(ids)) {
         qb = new QueryBuilder("select * from ZCImage where Status=? and id in (" + ids + ") order by id");
         qb.add(30);
         dt = qb.executeDataTable();
         map.put("ZCImage", dt);
       }
     }
 
     if (isSyncCatalogInsert)
     {
       qb = new QueryBuilder("select * from ZCCatalog where InnerCode like ? and AddTime>? order by id");
       qb.add(InnerCode + "%");
       qb.add(LastTime);
       dt = qb.executePagedDataTable(50, 0);
       map.put("ZCCatalog-Add", dt);
       ids = StringUtil.join(dt.getColumnValues("ID"));
 
       if (isSyncCatalogModify)
       {
         qb = new QueryBuilder(
           "select * from ZCCatalog where InnerCode like ? and AddTime<? and ModifyTime>? order by id");
         qb.add(InnerCode + "%");
         qb.add(LastTime);
         qb.add(LastTime);
         dt = qb.executePagedDataTable(50, 0);
         map.put("ZCCatalog-Modify", dt);
         if (StringUtil.isNotEmpty(ids))
           ids = ids + "," + StringUtil.join(dt.getColumnValues("ID"));
         else {
           ids = StringUtil.join(dt.getColumnValues("ID"));
         }
         if (ids.endsWith(",")) {
           ids = ids.substring(0, ids.length() - 1);
         }
       }
       if (StringUtil.isNotEmpty(ids)) {
         String types = "1,0";
 
         dt = new QueryBuilder("select * from ZDColumnRela where RelaType in (" + types + ") and RelaID in(" + 
           ids + ")").executeDataTable();
         map.put("ZDColumnRela", dt);
 
         String columnIDs = StringUtil.join(dt.getColumnValues("ColumnID"));
         if (StringUtil.isNotEmpty(columnIDs)) {
           dt = new QueryBuilder("select * from ZDColumn where id in (" + columnIDs + ")").executeDataTable();
           map.put("ZDColumn", dt);
         }
 
         dt = new QueryBuilder("select * from ZDColumnValue where RelaType=0 and RelaID in(" + 
           ids + ")").executeDataTable();
         map.put("ZDColumnValue-Catalog", dt);
       }
     }
     map.put("Success", "1");
     return NextTime;
   }
 
   public static void dealData(Mapx map, String catalogID, String ServerAddr, String sourceCatalogID, ZCInnerDeploySchema deploy)
   {
     long AfterSyncStatus = 0L;
     long AfterModifyStatus = 60L;
     boolean SyncCatalogInsert = false;
     boolean SyncCatalogModify = false;
     boolean SyncArticleModify = false;
 
     ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(catalogID);
     if (deploy != null) {
       AfterSyncStatus = deploy.getAfterSyncStatus();
       AfterModifyStatus = deploy.getAfterModifyStatus();
       SyncCatalogInsert = "Y".equals(deploy.getSyncCatalogInsert());
       SyncCatalogModify = "Y".equals(deploy.getSyncCatalogModify());
       SyncArticleModify = "Y".equals(deploy.getSyncArticleModify());
     } else {
       AfterSyncStatus = config.getAfterSyncStatus();
       AfterModifyStatus = config.getAfterModifyStatus();
       SyncCatalogInsert = "Y".equals(config.getSyncCatalogInsert());
       SyncCatalogModify = "Y".equals(config.getSyncCatalogModify());
       SyncArticleModify = "Y".equals(config.getSyncArticleModify());
     }
     String ServiceContext = Config.getValue("ServicesContext");
     if (ServiceContext.endsWith("/")) {
       ServiceContext = ServiceContext.substring(0, ServiceContext.length() - 1);
     }
     ServiceContext = ServiceContext.substring(0, ServiceContext.lastIndexOf("/") + 1);
     if (ServerAddr.equalsIgnoreCase("localhost")) {
       ServerAddr = ServiceContext;
     }
     boolean isSameServer = false;
     if (ServerAddr.equalsIgnoreCase(ServiceContext)) {
       isSameServer = true;
     }
 
     ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
     if (catalog == null) {
       return;
     }
     ZCCatalogSet catalogSet = new ZCCatalogSchema().query(
       new QueryBuilder("where InnerCode like ?", catalog
       .getInnerCode() + 
       "%"));
 
     DataTable columns = (DataTable)map.get("ZDColumn");
     DataTable articleColumnValues = (DataTable)map.get("ZDColumnValue-Article");
     DataTable catalogColumnValues = (DataTable)map.get("ZDColumnValue-Catalog");
     DataTable columnRela = (DataTable)map.get("ZDColumnRela");
 
     Mapx idmap = new Mapx();
 
     for (int i = 0; i < catalogSet.size(); ++i) {
       String ClusterSourceID = catalogSet.get(i).getClusterSourceID();
       if (StringUtil.isNotEmpty(ClusterSourceID)) {
         String[] arr = ClusterSourceID.split(",");
         String OldCatalogID = arr[(arr.length - 1)];
         idmap.put("Catalog." + OldCatalogID, catalogSet.get(i).getID());
       }
     }
     idmap.put("Catalog." + sourceCatalogID, catalogID);
 
     Transaction tran = new BlockingTransaction();
 
     if (SyncCatalogModify) {
       DataTable dt = (DataTable)map.get("ZCCatalog-Modify");
       if ((dt != null) && (dt.getRowCount() > 0)) {
         dt.sort("ModifyTime");
         ZCCatalogSet set = new ZCCatalogSet();
         for (int i = 0; i < dt.getRowCount(); ++i) {
           ZCCatalogSchema c2 = null;
           String ClusterSourceID = ServerAddr + "," + dt.getString(i, "SiteID") + "," + dt.getString(i, "ID");
           int index = 0;
           for (int j = 0; j < catalogSet.size(); ++j) {
             if ((!StringUtil.isNotEmpty(catalogSet.get(j).getClusterSourceID())) || 
               (!catalogSet.get(j).getClusterSourceID().equals(ClusterSourceID))) continue;
             c2 = catalogSet.get(j);
             index = j;
             break;
           }
 
           if (c2 == null) {
             continue;
           }
           ZCCatalogSchema c = new ZCCatalogSchema();
           c.setValue(dt.getDataRow(i));
           c.setID(c2.getID());
           c.setParentID(idmap.getString("Catalog." + c.getParentID()));
           c.setSiteID(c2.getSiteID());
           String parentInnerCode = c2.getInnerCode();
           for (int k = 0; k < catalogSet.size(); ++k) {
             if (catalogSet.get(k).getID() == c.getParentID()) {
               parentInnerCode = catalogSet.get(k).getInnerCode();
               break;
             }
           }
           c.setInnerCode(CatalogUtil.createCatalogInnerCode(parentInnerCode));
           String name = Catalog.checkAliasExists(c.getAlias(), catalog.getSiteID(), c.getParentID());
           if (StringUtil.isNotEmpty(name)) {
             LogUtil.warn("网站群同步栏目添加时发现栏目别名[" + c.getAlias() + "]己被栏目[" + name + "]占用!");
           }
           else {
             c.setClusterSourceID(ClusterSourceID);
             c.setOrderFlag(c2.getOrderFlag());
 
             set.add(c);
             catalogSet.set(index, c);
           }
         }
         tran.add(set, 2);
       }
 
     }
 
     if (SyncCatalogInsert) {
       DataTable dt = (DataTable)map.get("ZCCatalog-Add");
       if ((dt != null) && (dt.getRowCount() > 0)) {
         dt.sort("AddTime");
         ZCCatalogSet set = new ZCCatalogSet();
         for (int i = 0; i < dt.getRowCount(); ++i) {
           if (sourceCatalogID.equals(dt.getString(i, "ID"))) {
             continue;
           }
           ZCCatalogSchema c = new ZCCatalogSchema();
           c.setValue(dt.getDataRow(i));
           c.setSiteID(catalog.getSiteID());
           c.setID(NoUtil.getMaxID("CatalogID"));
           c.setParentID(idmap.getString("Catalog." + c.getParentID()));
           String parentInnerCode = catalog.getInnerCode();
           String url = catalog.getURL();
           for (int k = 0; k < catalogSet.size(); ++k) {
             if (catalogSet.get(k).getID() == c.getParentID()) {
               parentInnerCode = catalogSet.get(k).getInnerCode();
               url = catalogSet.get(k).getURL();
               break;
             }
           }
           if (!url.endsWith("/")) {
             url = url + "/";
           }
           url = url + c.getAlias() + "/";
           c.setURL(url);
           c.setInnerCode(CatalogUtil.createCatalogInnerCode(parentInnerCode));
           String name = Catalog.checkAliasExists(c.getAlias(), catalog.getSiteID(), c.getParentID());
           if (StringUtil.isNotEmpty(name)) {
             LogUtil.warn("网站群同步栏目添加时发现栏目别名" + c.getAlias() + "己被栏目" + name + "占用!");
           }
           else {
             c.setClusterSourceID(ServerAddr + "," + dt.getString(i, "SiteID") + "," + dt.getString(i, "ID"));
             set.add(c);
             catalogSet.add(c);
             idmap.put("Catalog." + dt.getString(i, "ID"), c.getID());
           }
         }
         tran.add(set, 1);
       }
 
     }
 
     if ((columnRela != null) && (columnRela.getRowCount() > 0)) {
       ZDColumnRelaSet crSet = new ZDColumnRelaSet();
       ZDColumnSet cSet = new ZDColumnSet();
       ZDColumnValueSet vSetUpdate = new ZDColumnValueSet();
       ZDColumnValueSet vSetInsert = new ZDColumnValueSet();
       for (int i = 0; i < columnRela.getRowCount(); ++i) {
         ZDColumnRelaSchema cr = new ZDColumnRelaSchema();
         cr.setValue(columnRela.getDataRow(i));
         cr.setRelaID(idmap.getString("Catalog." + cr.getRelaID()));
         cr.setID(NoUtil.getMaxID("ColumnRelaID"));
 
         ZDColumnRelaSchema cr2 = new ZDColumnRelaSchema();
         cr2.setColumnCode(cr.getColumnCode());
         cr2.setRelaID(cr.getRelaID());
         cr2.setRelaType(cr2.getRelaType());
         ZDColumnRelaSet set = cr2.query();
         if (set.size() == 0) {
           crSet.add(cr);
           if ((columns != null) && (columns.getRowCount() > 0))
             for (int j = 0; j < columns.getRowCount(); ++j)
               if (columns.getLong(j, "ID") == cr.getColumnID()) {
                 ZDColumnSchema column = new ZDColumnSchema();
                 column.setValue(columns.getDataRow(j));
                 column.setID(NoUtil.getMaxID("ColumnID"));
                 column.setSiteID(config.getSiteID());
                 cSet.add(column);
                 idmap.put("Column." + columns.getString(j, "ID"), column.getID());
                 cr.setColumnID(column.getID());
               }
         }
         else
         {
           idmap.put("Column." + columnRela.getString(i, "ColumnID"), set.get(0).getColumnID());
         }
       }
       if ((catalogColumnValues != null) && (catalogColumnValues.getRowCount() > 0)) {
         for (int j = 0; j < catalogColumnValues.getRowCount(); ++j) {
           ZDColumnValueSchema v = new ZDColumnValueSchema();
           v.setColumnID(idmap.getLong("Column." + catalogColumnValues.getString(j, "ColumnID")));
           v.setRelaID(idmap.getString("Catalog." + catalogColumnValues.getString(j, "RelaID")));
           v.setRelaType(catalogColumnValues.getString(j, "RelaType"));
           ZDColumnValueSet set2 = v.query();
           if (set2.size() > 0) {
             v = set2.get(0);
             v.setTextValue(catalogColumnValues.getString(j, "TextValue"));
             vSetUpdate.add(v);
           } else {
             v.setValue(catalogColumnValues.getDataRow(j));
             v.setID(NoUtil.getMaxID("ColumnValueID"));
             v.setColumnID(idmap.getLong("Column." + catalogColumnValues.getString(j, "ColumnID")));
             v.setRelaID(idmap.getString("Catalog." + catalogColumnValues.getString(j, "RelaID")));
             v.setRelaType(catalogColumnValues.getString(j, "RelaType"));
             vSetInsert.add(v);
           }
         }
       }
       tran.add(crSet, 1);
       tran.add(cSet, 1);
       tran.add(vSetUpdate, 2);
       tran.add(vSetInsert, 1);
     }
 
     String autoSaveLib = Config.getValue("AutoSaveImageLib");
     if (StringUtil.isEmpty(autoSaveLib)) {
       autoSaveLib = "默认图片";
     }
     String imageCatalogID = new QueryBuilder("select ID from ZCCatalog where type='4' and Name =?  and siteid=?", 
       autoSaveLib, catalog.getSiteID()).executeString();
 
     if (StringUtil.isEmpty(imageCatalogID)) {
       imageCatalogID = new QueryBuilder("select ID from ZCCatalog where type='4' and siteid=?", 
         catalog.getSiteID()).executeString();
     }
     ZCCatalogSchema imageCatalog = CatalogUtil.getSchema(imageCatalogID);
 
     DataTable imageRelas = (DataTable)map.get("ZCImageRela");
     DataTable images = (DataTable)map.get("ZCImage");
     ZCImageRelaSet rSet = new ZCImageRelaSet();
     ZCImageSet iSet = new ZCImageSet();
 
     ZCArticleSet aSet = new ZCArticleSet();
     ZDColumnValueSet vSet = new ZDColumnValueSet();
     ZDColumnValueSet vSetUpdate = new ZDColumnValueSet();
     if (SyncArticleModify)
     {
       String DeletedArticles = map.getString("DeletedArticles");
       if (!StringUtil.checkID(DeletedArticles)) {
         LogUtil.warn("InnerSyncService.dealData()可能的SQL注入攻击:" + DeletedArticles);
         return;
       }
       if (StringUtil.isNotEmpty(DeletedArticles)) {
         ZCArticleSet set = new ZCArticleSchema().query(
           new QueryBuilder("where ID in (" + DeletedArticles + 
           ")"));
         tran.add(set, 5);
       }
 
       DataTable dt = (DataTable)map.get("ZCArticle-Modify");
       if ((dt != null) && (dt.getRowCount() > 0)) {
         for (int i = 0; i < dt.getRowCount(); ++i) {
           ZCArticleSchema article = new ZCArticleSchema();
           String sourceID = ServerAddr + "," + dt.getString(i, "SiteID") + "," + dt.getString(i, "CatalogID");
 
           article.setCatalogID(config.getCatalogID());
           article.setSiteID(config.getSiteID());
           article.setClusterSource(sourceID + "," + dt.getString(i, "ID"));
           ZCArticleSet set = article.query();
           if (set.size() == 0) {
             continue;
           }
           ZCArticleSchema article2 = set.get(0);
           article.setValue(dt.getDataRow(i));
           article.setID(article2.getID());
           article.setCatalogID(article2.getCatalogID());
           article.setCatalogInnerCode(article2.getCatalogInnerCode());
           article.setSiteID(article2.getSiteID());
           article.setOrderFlag(article2.getOrderFlag());
           article.setStatus(AfterModifyStatus);
           article.setClusterSource(sourceID + "," + dt.getString(i, "ID"));
           article.setBranchInnerCode("0001");
           article.setReferTarget("");
           article.setClusterTarget("");
           aSet.add(article);
 
           QueryBuilder qb = new QueryBuilder("delete from ZCImageRela where RelaType=? and RelaID=?");
           qb.add("ArticleImage");
           qb.add(article.getID());
           tran.add(qb);
 
           if (articleColumnValues != null) {
             for (int j = 0; j < articleColumnValues.getRowCount(); ++j) {
               if ((!articleColumnValues.getString(j, "RelaType").equals("2")) || 
                 (!articleColumnValues.getString(j, "RelaID").equals(dt.getString(i, "ID")))) continue;
               ZDColumnValueSchema v = new ZDColumnValueSchema();
               v.setColumnID(idmap.getLong("Column." + articleColumnValues.getString(j, "ColumnID")));
               v.setRelaID(article.getID());
               v.setRelaType(articleColumnValues.getString(j, "RelaType"));
               ZDColumnValueSet set2 = v.query();
               if (set2.size() > 0) {
                 v = set2.get(0);
                 v.setTextValue(articleColumnValues.getString(j, "TextValue"));
                 vSetUpdate.add(v);
               } else {
                 v.setValue(articleColumnValues.getDataRow(j));
                 v.setID(NoUtil.getMaxID("ColumnValueID"));
                 v.setColumnID(idmap.getLong("Column." + 
                   articleColumnValues.getString(j, "ColumnID")));
                 v.setRelaID(article.getID());
                 v.setRelaType(articleColumnValues.getString(j, "RelaType"));
                 vSet.add(v);
               }
             }
 
           }
 
           if ((isSameServer) && (dt.getLong(i, "SiteID") == catalog.getSiteID())) {
             continue;
           }
           if (imageRelas == null) continue; if (images == null)
           {
             continue;
           }
 
           for (int j = 0; j < imageRelas.getRowCount(); ++j) {
             if ((!imageRelas.getString(j, "RelaType").equals("ArticleImage")) || 
               (!imageRelas.getString(j, "RelaID").equals(dt.getString(i, "ID")))) continue;
             ZCImageRelaSchema ir = new ZCImageRelaSchema();
             ir.setValue(imageRelas.getDataRow(j));
             for (int k = 0; k < images.getRowCount(); ++k)
               if (images.getLong(k, "ID") == ir.getID()) {
                 ZCImageSchema image = new ZCImageSchema();
                 image.setSiteID(catalog.getSiteID());
                 image.setSourceURL(ServerAddr + "?ImageID=" + ir.getID());
                 ZCImageSet set2 = image.query();
                 if (set2.size() > 0) {
                   image = set2.get(0);
                   idmap.put("Image." + ir.getID(), image);
                 }
                 else {
                   image.setValue(images.getDataRow(k));
                   image.setID(NoUtil.getMaxID("DocID"));
                   image.setSiteID(catalog.getSiteID());
                   image.setCatalogID(imageCatalog.getID());
                   image.setCatalogInnerCode(imageCatalog.getInnerCode());
                   image.setStatus(20L);
                   image.setPath("upload/Image/" + CatalogUtil.getPath(imageCatalog.getID()));
                   image.setOrderFlag(OrderUtil.getDefaultOrder());
                   image.setSourceURL(ServerAddr + "?ImageID=" + ir.getID());
 
                   int random = NumberUtil.getRandomInt(10000);
                   String ext = image.getFileName()
                     .substring(image.getFileName().lastIndexOf(".") + 1);
                   String newFileName = image.getID() + random + "." + ext;
 
                   RequestImpl request = new RequestImpl();
                   request.put("ID", ir.getID());
                   request.put("FileName", image.getFileName());
                   Mapx data = null;
                   if (isSameServer) {
                     InnerSyncService service = new InnerSyncService();
                     service.setRequest(request);
                     service.sendImage();
                     data = service.getResponse();
                   } else {
                     data = Framework.callRemoteMethod(ServerAddr, 
                       "com.zving.datachannel.InnerSyncService.sendImage", request);
                   }
                   if ("1".equals(data.getString("Success"))) {
                     String str = data.getString("Data");
                     byte[] bs = StringUtil.base64Decode(str);
                     String path = SiteUtil.getAbsolutePath(catalog.getSiteID()) + image.getPath();
                     new File(path).mkdirs();
                     image.setFileName(newFileName);
                     image.setSrcFileName(newFileName);
                     newFileName = path + newFileName;
                     FileUtil.writeByte(newFileName, bs);
                     try {
                       ImageUtilEx.afterUploadImage(image, path);
                       idmap.put("Image." + ir.getID(), image);
                     } catch (Throwable e) {
                       e.printStackTrace();
                     }
                   }
 
                   ir.setID(image.getID());
                   iSet.add(image);
                   break;
                 }
               }
             ir.setRelaID(article.getID());
             rSet.add(ir);
           }
 
           String html = article.getContent();
           html = dealImageTag(html, idmap, catalog);
           article.setContent(html);
         }
         tran.add(aSet, 2);
         tran.add(vSetUpdate, 2);
         tran.add(vSet, 1);
       }
 
     }
 
     DataTable dt = (DataTable)map.get("ZCArticle-Add");
     dt.sort("OrderFlag", "ASC");
     aSet = new ZCArticleSet();
     vSet = new ZDColumnValueSet();
     for (int i = 0; i < dt.getRowCount(); ++i) {
       ZCArticleSchema article = new ZCArticleSchema();
       article.setValue(dt.getDataRow(i));
       String sourceID = ServerAddr + "," + article.getSiteID() + "," + article.getCatalogID();
       article.setID(NoUtil.getMaxID("DocID"));
       article.setSiteID(catalog.getSiteID());
 
       boolean flag = false;
       for (int j = 0; j < catalogSet.size(); ++j) {
         if (sourceID.equals(catalogSet.get(j).getClusterSourceID())) {
           article.setCatalogID(catalogSet.get(j).getID());
           article.setCatalogInnerCode(catalogSet.get(j).getInnerCode());
           flag = true;
           break;
         }
       }
       if (!flag) {
         article.setCatalogID(catalog.getID());
         article.setCatalogInnerCode(catalog.getInnerCode());
       }
 
       ZCArticleSchema article2 = new ZCArticleSchema();
       article2.setCatalogID(article.getCatalogID());
       article2.setSiteID(article.getSiteID());
       article2.setClusterSource(sourceID + "," + dt.getString(i, "ID"));
       if (article2.query().size() > 0) {
         continue;
       }
       article.setClusterSource(sourceID + "," + dt.getString(i, "ID"));
 
       article.setStatus(AfterSyncStatus);
       article.setOrderFlag(OrderUtil.getDefaultOrder());
       article.setBranchInnerCode("0001");
       aSet.add(article);
 
       if (articleColumnValues != null) {
         for (int j = 0; j < articleColumnValues.getRowCount(); ++j) {
           if ((!articleColumnValues.getString(j, "RelaType").equals("2")) || 
             (!articleColumnValues.getString(j, "RelaID").equals(dt.getString(i, "ID")))) continue;
           ZDColumnValueSchema v = new ZDColumnValueSchema();
           v.setValue(articleColumnValues.getDataRow(j));
           v.setID(NoUtil.getMaxID("ColumnValueID"));
           v.setColumnID(idmap.getLong("Column." + articleColumnValues.getString(j, "ColumnID")));
           v.setRelaID(article.getID());
           v.setRelaType(articleColumnValues.getString(j, "RelaType"));
           vSet.add(v);
         }
 
       }
 
       if ((isSameServer) && (dt.getLong(i, "SiteID") == catalog.getSiteID())) {
         continue;
       }
       if (imageRelas == null) continue; if (images == null) {
         continue;
       }
 
       for (int j = 0; j < imageRelas.getRowCount(); ++j) {
         if ((!imageRelas.getString(j, "RelaType").equals("ArticleImage")) || 
           (!imageRelas.getString(j, "RelaID").equals(dt.getString(i, "ID")))) continue;
         ZCImageRelaSchema ir = new ZCImageRelaSchema();
         ir.setValue(imageRelas.getDataRow(j));
         for (int k = 0; k < images.getRowCount(); ++k)
           if (images.getLong(k, "ID") == ir.getID()) {
             ZCImageSchema image = new ZCImageSchema();
             image.setSiteID(catalog.getSiteID());
             image.setSourceURL(ServerAddr + "?ImageID=" + ir.getID());
             ZCImageSet set2 = image.query();
             if (set2.size() > 0) {
               image = set2.get(0);
               idmap.put("Image." + ir.getID(), image);
             }
             else {
               image.setValue(images.getDataRow(k));
               image.setID(NoUtil.getMaxID("DocID"));
               image.setSiteID(catalog.getSiteID());
               image.setCatalogID(imageCatalog.getID());
               image.setCatalogInnerCode(imageCatalog.getInnerCode());
               image.setStatus(20L);
               image.setPath("upload/Image/" + CatalogUtil.getPath(imageCatalog.getID()));
               image.setOrderFlag(OrderUtil.getDefaultOrder());
               image.setSourceURL(ServerAddr + "?ImageID=" + ir.getID());
 
               int random = NumberUtil.getRandomInt(10000);
               String ext = image.getFileName().substring(image.getFileName().lastIndexOf(".") + 1);
               String newFileName = image.getID() + random + "." + ext;
 
               RequestImpl request = new RequestImpl();
               request.put("ID", ir.getID());
               request.put("FileName", image.getFileName());
               Mapx data = null;
               if (isSameServer) {
                 InnerSyncService service = new InnerSyncService();
                 service.setRequest(request);
                 service.sendImage();
                 data = service.getResponse();
               } else {
                 data = Framework.callRemoteMethod(ServerAddr, 
                   "com.zving.datachannel.InnerSyncService.sendImage", request);
               }
               if ("1".equals(data.getString("Success"))) {
                 String str = data.getString("Data");
                 byte[] bs = StringUtil.base64Decode(str);
                 String path = SiteUtil.getAbsolutePath(catalog.getSiteID()) + image.getPath();
                 new File(path).mkdirs();
                 image.setFileName(newFileName);
                 image.setSrcFileName(newFileName);
                 newFileName = path + newFileName;
                 FileUtil.writeByte(newFileName, bs);
                 try {
                   ImageUtilEx.afterUploadImage(image, path);
                   idmap.put("Image." + ir.getID(), image);
                 } catch (Throwable e) {
                   e.printStackTrace();
                 }
               }
 
               ir.setID(image.getID());
               iSet.add(image);
               break;
             }
           }
         ir.setRelaID(article.getID());
         rSet.add(ir);
       }
 
       String html = article.getContent();
       html = dealImageTag(html, idmap, catalog);
       article.setContent(html);
     }
     tran.add(aSet, 1);
     tran.add(vSet, 1);
     tran.add(rSet, 1);
     tran.add(iSet, 1);
     tran.commit();
   }
 
   public static String dealImageTag(String html, Mapx idmap, ZCCatalogSchema catalog)
   {
     Matcher m = imagePattern.matcher(html);
     StringBuffer sb = new StringBuffer();
     int lastIndex = 0;
     while (m.find(lastIndex)) {
       int start = m.start();
       sb.append(html.substring(lastIndex, start));
       String tag = m.group(0);
       tag = tag.substring(tag.indexOf(" "));
       tag = tag.substring(0, tag.length() - 1).trim();
       if (tag.endsWith("/")) {
         tag = tag.substring(0, tag.length() - 1).trim();
       }
       tag = tag.replaceAll("\\s+", " ");
       Mapx attrs = StringUtil.splitToMapx(tag, " ", "=");
       attrs = new CaseIgnoreMapx(attrs);
       String oldID = attrs.getString("zcmsimagerela");
       oldID = oldID.substring(1, oldID.length() - 1);
       ZCImageSchema image = (ZCImageSchema)idmap.get("Image." + oldID);
       if (image != null) {
         attrs.put("zcmsimagerela", "\"" + image.getID() + "\"");
         String src = SiteUtil.getPath(catalog.getSiteID()) + "/" + image.getPath() + "/1_" + 
           image.getFileName();
         attrs.put("src", "\"" + src.replaceAll("\\/+", "/") + "\"");
         sb.append("<img");
         Object[] ks = attrs.keyArray();
         for (int k = 0; k < ks.length; ++k) {
           sb.append(" ");
           sb.append(ks[k]);
           sb.append("=");
           sb.append(attrs.get(ks[k]));
         }
         sb.append(" />");
       } else {
         sb.append(m.group(0));
       }
       lastIndex = m.end();
     }
     sb.append(html.substring(lastIndex));
     return sb.toString();
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.datachannel.InnerSyncService
 * JD-Core Version:    0.5.4
 */