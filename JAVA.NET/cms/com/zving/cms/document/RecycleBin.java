 package com.zving.cms.document;
 
 import com.zving.cms.pub.CatalogUtil;
 import com.zving.cms.site.Catalog;
 import com.zving.framework.Config;
 import com.zving.framework.Page;
 import com.zving.framework.RequestImpl;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.controls.TreeAction;
 import com.zving.framework.controls.TreeItem;
 import com.zving.framework.data.BlockingTransaction;
 import com.zving.framework.data.DataColumn;
 import com.zving.framework.data.DataRow;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.orm.SchemaUtil;
 import com.zving.framework.utility.Filter;
 import com.zving.framework.utility.HtmlUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.platform.Priv;
 import com.zving.platform.UserLog;
 import com.zving.platform.pub.NoUtil;
 import com.zving.schema.BZCArticleLogSchema;
 import com.zving.schema.BZCArticleLogSet;
 import com.zving.schema.BZCArticleSchema;
 import com.zving.schema.BZCArticleSet;
 import com.zving.schema.BZCAttachmentRelaSchema;
 import com.zving.schema.BZCAttachmentRelaSet;
 import com.zving.schema.BZCAudioRelaSchema;
 import com.zving.schema.BZCAudioRelaSet;
 import com.zving.schema.BZCCatalogConfigSchema;
 import com.zving.schema.BZCCatalogConfigSet;
 import com.zving.schema.BZCCatalogSchema;
 import com.zving.schema.BZCCatalogSet;
 import com.zving.schema.BZCCommentSchema;
 import com.zving.schema.BZCCommentSet;
 import com.zving.schema.BZCImageRelaSchema;
 import com.zving.schema.BZCImageRelaSet;
 import com.zving.schema.BZCPageBlockSchema;
 import com.zving.schema.BZCPageBlockSet;
 import com.zving.schema.BZCVideoRelaSchema;
 import com.zving.schema.BZCVideoRelaSet;
 import com.zving.schema.BZDColumnRelaSchema;
 import com.zving.schema.BZDColumnRelaSet;
 import com.zving.schema.BZDColumnSchema;
 import com.zving.schema.BZDColumnSet;
 import com.zving.schema.BZDColumnValueSchema;
 import com.zving.schema.BZDColumnValueSet;
 import com.zving.schema.BZWInstanceSchema;
 import com.zving.schema.BZWInstanceSet;
 import com.zving.schema.BZWStepSchema;
 import com.zving.schema.BZWStepSet;
 import com.zving.schema.ZCArticleLogSchema;
 import com.zving.schema.ZCArticleSchema;
 import com.zving.schema.ZCArticleSet;
 import com.zving.schema.ZCCatalogSchema;
 import com.zving.schema.ZCCatalogSet;
 import java.io.File;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 
 public class RecycleBin extends Page
 {
   public static void treeDataBind(TreeAction ta)
   {
     String siteID = Application.getCurrentSiteID();
     String parentTreeLevel = (String)ta.getParams().get("ParentLevel");
     String parentID = (String)ta.getParams().get("ParentID");
     DataTable dt = null;
     if (ta.isLazyLoad()) {
       QueryBuilder qb = new QueryBuilder(
         "select ID,ParentID,TreeLevel,Name,InnerCode from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel>? and innerCode like ? order by orderflag,innercode ");
       qb.add(1);
       qb.add(siteID);
       qb.add(parentTreeLevel);
       qb.add(CatalogUtil.getInnerCode(parentID) + "%");
       dt = qb.executeDataTable();
     } else {
       QueryBuilder qb = new QueryBuilder(
         "select ID,ParentID,TreeLevel,Name,InnerCode,SingleFlag from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel-1 <=? order by orderflag,innercode ");
       qb.add(1);
       qb.add(siteID);
       qb.add(ta.getLevel());
       dt = qb.executeDataTable();
     }
     ta.setRootText("文档库");
     dt = dt.filter(new Filter() {
       public boolean filter(Object obj) {
         DataRow dr = (DataRow)obj;
         return Priv.getPriv(User.getUserName(), "article", dr.getString("InnerCode"), "article_browse");
       }
     });
     ta.bindData(dt);
     List items = ta.getItemList();
     for (int i = 1; i < items.size(); ++i) {
       TreeItem item = (TreeItem)items.get(i);
       if ("Y".equals(item.getData().getString("SingleFlag")))
         item.setIcon("Icons/treeicon11.gif");
     }
   }
 
   public static void treeCatalogDataBind(TreeAction ta)
   {
     String siteID = Application.getCurrentSiteID();
     if (!Priv.getPriv("site", siteID, "site_manage")) {
       ta.setRootText("当前用户没有站点管理权限");
       return;
     }
     String catalogID = ta.getParam("CatalogID");
     if (CatalogUtil.getSchema(catalogID) == null) {
       catalogID = "";
     }
     DataTable dt = null;
     QueryBuilder qb = new QueryBuilder("select * from BZCCatalog Where Type = ? and SiteID = ? and TreeLevel-1 <=?");
     qb.add(1);
     qb.add(siteID);
     qb.add(ta.getLevel());
     if (StringUtil.isNotEmpty(catalogID)) {
       String innerCode = CatalogUtil.getInnerCode(catalogID);
       qb.append(" and InnerCode like ?", innerCode + "%");
     }
     qb.append(" and not exists (select 1 from ZCCatalog where ID=BZCCatalog.ID)");
     qb.append(" order by orderflag,innercode,backupNo desc");
     dt = qb.executeDataTable();
     for (int i = dt.getRowCount() - 1; i > 0; --i) {
       if (dt.getInt(i, "ID") == dt.getInt(i - 1, "ID")) {
         dt.deleteRow(i);
       }
     }
     Mapx map = dt.toMapx("ID", "InnerCode");
     ZCCatalogSet set = new ZCCatalogSet();
     for (int i = 0; i < dt.getRowCount(); ++i) {
       String pid = dt.getString(i, "ParentID");
       dt.set(i, "Prop1", "B");
       while ((!"0".equals(pid)) && (!map.containsKey(pid))) {
         ZCCatalogSchema catalog = CatalogUtil.getSchema(pid);
         catalog.setProp1("Z");
         map.put(pid, catalog.getInnerCode());
         set.add(catalog);
         pid = catalog.getParentID();
       }
     }
     DataTable dt2 = set.toDataTable();
     dt2.insertColumn("BackupNo");
     dt2.insertColumn("BackupOperator");
     dt2.insertColumn("BackupTime");
     dt2.insertColumn("BackupMemo");
     dt.union(dt2);
 
     if (dt.getRowCount() == 0) {
       if (StringUtil.isEmpty(catalogID))
         ta.setRootText("当前站点下没有被删除的栏目");
       else
         ta.setRootText("栏目 <font class='red'>" + CatalogUtil.getName(catalogID) + "</font> 下没有被删除的栏目");
     }
     else {
       ta.setRootText("文档库");
     }
 
     ta.bindData(dt);
     List items = ta.getItemList();
     for (int i = 1; i < items.size(); ++i) {
       TreeItem item = (TreeItem)items.get(i);
       if ("Y".equals(item.getData().getString("SingleFlag"))) {
         item.setIcon("Icons/treeicon11.gif");
       }
       String text = item.getText();
       if ("Z".equals(item.getData().getString("Prop1")))
         text = "<font class='red'>" + text + "</font>";
       else {
         text = text + "&nbsp;<font color='#ccc'>(" + item.getData().getString("Total") + "篇文章)</font>";
       }
       item.setText(text);
     }
   }
 
   public static void dg1DataBind(DataGridAction dga) {
     String catalogID = (String)dga.getParams().get("CatalogID");
     if (StringUtil.isEmpty(catalogID)) {
       catalogID = dga.getParams().getString("Cookie.DocList.LastCatalog");
       if ((StringUtil.isEmpty(catalogID)) || ("null".equals(catalogID))) {
         catalogID = "0";
       }
       dga.getParams().put("CatalogID", catalogID);
     }
     String keyword = (String)dga.getParams().get("Keyword");
     String startDate = (String)dga.getParams().get("StartDate");
     String endDate = (String)dga.getParams().get("EndDate");
     QueryBuilder qb = new QueryBuilder("select distinct id from BZCArticle where CatalogID=?");
     qb.add(catalogID);
     if (StringUtil.isNotEmpty(keyword)) {
       qb.append(" and title like ? ", "%" + keyword.trim() + "%");
     }
     if (StringUtil.isNotEmpty(startDate)) {
       qb.append(" and publishdate >= ? ", startDate);
     }
     if (StringUtil.isNotEmpty(endDate)) {
       qb.append(" and publishdate <= ? ", endDate);
     }
     qb.append(" and not exists (select 1 from ZCArticle where ID=BZCArticle.ID)");
     dga.setTotal(qb);
     qb.append(dga.getSortString());
 
     DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
 
     StringBuffer sb = new StringBuffer(
       "select ID,Attribute,Title,AddUser,PublishDate,Addtime,Status,WorkFlowID,Type,TopFlag,OrderFlag,TitleStyle,TopDate,BackupTime,BackupOperator,BackupNo from BZCArticle where id in (");
 
     for (int i = 0; i < dt.getRowCount(); ++i) {
       if (i != 0) {
         sb.append(",");
       }
       sb.append(dt.getString(i, "ID"));
     }
     if (dt.getRowCount() == 0) {
       sb.append("0");
     }
     qb = new QueryBuilder(sb.toString() + ") ");
     qb.append(dga.getSortString());
     if (StringUtil.isNotEmpty(dga.getSortString()))
       qb.append(" ,id desc,BackupNo desc");
     else {
       qb.append(" order by id desc,BackupNo desc");
     }
     dt = qb.executeDataTable();
     for (int i = dt.getRowCount() - 1; i > 0; --i) {
       if (dt.getInt(i, "ID") == dt.getInt(i - 1, "ID")) {
         dt.deleteRow(i);
       }
     }
     dt.sort("BackupNo", "desc");
     if ((dt != null) && (dt.getRowCount() > 0)) {
       dt.decodeColumn("Status", Article.STATUS_MAP);
       dt.getDataColumn("BackupTime").setDateFormat("yy-MM-dd HH:mm");
     }
     Mapx attributemap = HtmlUtil.codeToMapx("ArticleAttribute");
     if (dt.getRowCount() > 0) {
       for (int i = 0; i < dt.getRowCount(); ++i) {
         if (StringUtil.isNotEmpty(dt.getString(i, "Attribute"))) {
           String[] array = dt.getString(i, "Attribute").split(",");
           String attributeName = "";
           for (int j = 0; j < array.length; ++j) {
             if (j != array.length - 1)
               attributeName = attributeName + attributemap.getString(array[j]) + ",";
             else {
               attributeName = attributeName + attributemap.getString(array[j]);
             }
           }
 
           dt.set(i, "Title", dt.getString(i, "Title") + "<font class='red'>[" + attributeName + "]</font>");
         }
       }
     }
     dga.bindData(dt);
   }
 
   public void restoreDocument() {
     restoreDocument($V("IDs"), false);
   }
 
   public void restoreDocument(String ids, boolean batchMode)
   {
     if ((!StringUtil.checkID(ids)) && (!batchMode)) {
       this.Response.setStatus(0);
       this.Response.setMessage("错误的参数,ID不能为空!");
       return;
     }
     Transaction trans = new Transaction();
     BZCArticleSet set = new BZCArticleSchema().query(
       new QueryBuilder("where id in(" + ids + 
       ") or id in (select id from bzcarticle where refersourceid in (" + ids + ") )"));
     ZCArticleSet zset = (ZCArticleSet)SchemaUtil.getZSetFromBSet(set);
     for (int i = 0; i < zset.size(); ++i) {
       long status = zset.get(i).getStatus();
       if ((status == 30L) || (status == 20L))
         zset.get(i).setStatus(20L);
       else if ((status == 40L) || (status == 50L))
         zset.get(i).setStatus(status);
       else {
         zset.get(i).setStatus(0L);
       }
     }
     trans.add(zset, 1);
     StringBuffer logs = new StringBuffer("恢复文章:");
 
     if (set.size() > 0)
     {
       BZDColumnValueSchema colValue = new BZDColumnValueSchema();
       BZDColumnValueSet colValueSet = colValue.query(
         new QueryBuilder("where RelaID in (" + ids + 
         ") and RelaType=?", "2"));
       trans.add(SchemaUtil.getZSetFromBSet(colValueSet), 1);
 
       BZCImageRelaSchema imageRela = new BZCImageRelaSchema();
       BZCImageRelaSet imageRelaSet = imageRela.query(
         new QueryBuilder("where RelaID in (" + ids + 
         ") and RelaType=?", "ArticleImage"));
       trans.add(SchemaUtil.getZSetFromBSet(imageRelaSet), 1);
 
       BZCVideoRelaSchema videoRela = new BZCVideoRelaSchema();
       BZCVideoRelaSet videoRelaSet = videoRela.query(
         new QueryBuilder("where RelaID in (" + ids + 
         ") and RelaType=?", "ArticleVideo"));
       trans.add(SchemaUtil.getZSetFromBSet(videoRelaSet), 1);
 
       BZCAttachmentRelaSchema attachmentRela = new BZCAttachmentRelaSchema();
       BZCAttachmentRelaSet attachmentRelaSet = attachmentRela.query(
         new QueryBuilder("where RelaID in (" + ids + 
         ") and RelaType=?", "ArticleAttach"));
       trans.add(SchemaUtil.getZSetFromBSet(attachmentRelaSet), 1);
 
       BZCAudioRelaSchema audioRela = new BZCAudioRelaSchema();
       BZCAudioRelaSet audioRelaSet = audioRela.query(new QueryBuilder("where RelaID in (" + ids + ")"));
       trans.add(SchemaUtil.getZSetFromBSet(audioRelaSet), 1);
 
       BZCCommentSchema comment = new BZCCommentSchema();
       BZCCommentSet commentSet = comment.query(new QueryBuilder("where RelaID in (" + ids + ")"));
       trans.add(SchemaUtil.getZSetFromBSet(commentSet), 1);
     }
 
     if (!batchMode) {
       for (int i = 0; i < set.size(); ++i) {
         ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
         articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
         articleLog.setArticleID(set.get(i).getID());
         articleLog.setAction("RESTORE");
         articleLog.setActionDetail("恢复成功");
         articleLog.setAddUser(User.getUserName());
         articleLog.setAddTime(new Date());
         trans.add(articleLog, 1);
         logs.append(set.get(i).getTitle() + ",");
       }
     }
 
     if (!batchMode) {
       String innerCode = "";
       if (set.size() > 0) {
         innerCode = set.get(0).getCatalogInnerCode();
         String sqlArticleCount = "update zccatalog set total=total+" + set.size() + 
           ",isdirty=1 where innercode in(" + CatalogUtil.getParentCatalogCode(innerCode) + ")";
         trans.add(new QueryBuilder(sqlArticleCount));
       }
       if (trans.commit()) {
         for (int i = 0; i < innerCode.length() / 6; ++i) {
           String str = innerCode.substring(0, 6 + i * 6);
           String id = CatalogUtil.getIDByInnerCode(str);
           CatalogUtil.update(id);
         }
         UserLog.log("Article", "Article", logs + "成功", this.Request.getClientIP());
         this.Response.setStatus(1);
         this.Response.setMessage("恢复成功!");
       } else {
         UserLog.log("Article", "DelArticle", logs + "失败", this.Request.getClientIP());
         this.Response.setStatus(0);
         this.Response.setMessage("操作数据库时发生错误:" + trans.getExceptionMessage());
       }
     }
   }
 
   public void deleteReally() {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       this.Response.setStatus(0);
       this.Response.setMessage("错误的参数!");
       return;
     }
     Transaction trans = new Transaction();
     BZCArticleSchema article = new BZCArticleSchema();
     BZCArticleSet set = article.query(
       new QueryBuilder("where id in(" + ids + 
       ") or id in(select id from bzcarticle where refersourceid in (" + ids + ") )"));
 
     trans.add(set, 3);
     StringBuffer logs = new StringBuffer("彻底删除文章:");
     if (set.size() > 0)
     {
       BZDColumnValueSchema colValue = new BZDColumnValueSchema();
       BZDColumnValueSet colValueSet = colValue.query(
         new QueryBuilder("where RelaID in (" + ids + 
         ") and RelaType=?", "2"));
       trans.add(colValueSet, 3);
 
       BZCImageRelaSchema imageRela = new BZCImageRelaSchema();
       BZCImageRelaSet imageRelaSet = imageRela.query(
         new QueryBuilder("where RelaID in (" + ids + 
         ") and RelaType=?", "ArticleImage"));
       trans.add(imageRelaSet, 3);
 
       BZCVideoRelaSchema videoRela = new BZCVideoRelaSchema();
       BZCVideoRelaSet videoRelaSet = videoRela.query(
         new QueryBuilder("where RelaID in (" + ids + 
         ") and RelaType=?", "ArticleVideo"));
       trans.add(videoRelaSet, 3);
 
       BZCAttachmentRelaSchema attachmentRela = new BZCAttachmentRelaSchema();
       BZCAttachmentRelaSet attachmentRelaSet = attachmentRela.query(
         new QueryBuilder("where RelaID in (" + ids + 
         ") and RelaType=?", "ArticleAttach"));
       trans.add(attachmentRelaSet, 3);
 
       BZCAudioRelaSchema audioRela = new BZCAudioRelaSchema();
       BZCAudioRelaSet audioRelaSet = audioRela.query(new QueryBuilder("where RelaID in (" + ids + ")"));
       trans.add(audioRelaSet, 3);
 
       BZCCommentSchema comment = new BZCCommentSchema();
       BZCCommentSet commentSet = comment.query(new QueryBuilder("where RelaID in (" + ids + ")"));
       trans.add(commentSet, 3);
 
       BZCArticleLogSchema articleLog = new BZCArticleLogSchema();
       BZCArticleLogSet logset = articleLog.query(new QueryBuilder("where ArticleID in (" + ids + ")"));
       trans.add(logset, 3);
 
       BZWStepSchema step = new BZWStepSchema();
       BZWStepSet stepset = step.query(
         new QueryBuilder("where InstanceID in (select id from ZWInstance where DataID in (" + ids + "))"));
       trans.add(stepset, 3);
 
       BZWInstanceSchema instance = new BZWInstanceSchema();
       BZWInstanceSet instanceset = instance.query(new QueryBuilder("where DataID in (" + ids + ")"));
       trans.add(instanceset, 3);
     }
 
     if (trans.commit()) {
       UserLog.log("Article", "DelArticle", logs + "成功", this.Request.getClientIP());
       this.Response.setStatus(1);
       this.Response.setMessage("彻底删除成功!");
     } else {
       UserLog.log("Article", "DelArticle", logs + "失败", this.Request.getClientIP());
       this.Response.setStatus(0);
       this.Response.setMessage("操作数据库时发生错误!");
     }
   }
 
   public void restoreCatalog() {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       this.Response.setStatus(0);
       this.Response.setMessage("错误的参数!");
       return;
     }
     String[] arr = ids.split(",");
     ArrayList list = new ArrayList(20);
     for (int i = 0; i < arr.length; ++i) {
       if ((!StringUtil.isNotEmpty(arr[i])) || 
         (CatalogUtil.getSchema(arr[i]) != null)) continue;
       list.add(arr[i]);
     }
 
     if (list.size() == 0) {
       this.Response.setError("栏目已经存在，不能再次恢复！");
       return;
     }
     ids = StringUtil.join(list);
     BZCCatalogSet set = new BZCCatalogSchema().query(
       new QueryBuilder("where id in (" + ids + 
       ") order by backupNo desc"));
     for (int i = set.size() - 1; i > 0; --i) {
       if (set.get(i).getID() == set.get(i - 1).getID()) {
         set.remove(set.get(i));
       }
 
     }
 
     for (int i = 0; i < set.size(); ++i) {
       BZCCatalogSchema catalog = set.get(i);
       if (CatalogUtil.getSchema(catalog.getParentID()) != null) {
         if (Catalog.checkNameExists(catalog.getName(), catalog.getParentID())) {
           this.Response.setError("不能恢复栏目“" + catalog.getName() + "”,上级栏目下已有同名子栏目！");
           return;
         }
         String existsName = Catalog.checkAliasExists(catalog.getAlias(), catalog.getParentID());
         if (StringUtil.isNotEmpty(existsName)) {
           this.Response.setError("栏目“" + existsName + "”已使用了别名" + catalog.getAlias());
           return;
         }
       }
     }
     list.clear();
 
     for (int i = 0; i < set.size(); ++i) {
       BZCCatalogSchema catalog = set.get(i);
       for (int j = i + 1; j < set.size(); ++j) {
         if (set.get(j).getParentID() == catalog.getParentID()) {
           if (set.get(j).getName().equals(catalog.getName())) {
             set.get(j).setName(set.get(j).getName() + "_" + j);
           }
           if (set.get(j).getAlias().equals(catalog.getAlias())) {
             set.get(j).setAlias(set.get(j).getAlias() + "_" + j);
           }
         }
       }
       list.add(catalog.getID());
     }
     ids = StringUtil.join(list);
 
     BlockingTransaction tran = new BlockingTransaction();
     tran.add(SchemaUtil.getZSetFromBSet(set), 1);
 
     BZCPageBlockSet blockSet = new BZCPageBlockSchema().query(new QueryBuilder("where CatalogID in (" + ids + ")"));
     tran.add(SchemaUtil.getZSetFromBSet(blockSet), 1);
 
     BZCCatalogConfigSet configSet = new BZCCatalogConfigSchema().query(
       new QueryBuilder("where CatalogID in (" + 
       ids + ")"));
     tran.add(SchemaUtil.getZSetFromBSet(configSet), 1);
 
     String types = "1,0";
     BZDColumnRelaSet columnRelaSet = new BZDColumnRelaSchema().query(
       new QueryBuilder(" where RelaType in (" + 
       types + ") and RelaID in(" + ids + ")"));
     tran.add(SchemaUtil.getZSetFromBSet(columnRelaSet), 1);
 
     BZDColumnSet columnSet = new BZDColumnSchema().query(
       new QueryBuilder(" where ID in(" + 
       StringUtil.join(columnRelaSet.toDataTable().getColumnValues("ColumnID")) + ")"));
     tran.add(SchemaUtil.getZSetFromBSet(columnSet), 1);
 
     BZDColumnValueSet columnValueSet = new BZDColumnValueSchema().query(
       new QueryBuilder(" where RelaType in (" + 
       types + ") and RelaID in(" + ids + ")"));
     tran.add(SchemaUtil.getZSetFromBSet(columnValueSet), 1);
 
     for (int i = 0; i < set.size(); ++i) {
       QueryBuilder qb = new QueryBuilder("select distinct id from BZCArticle where CatalogID=?");
       qb.add(set.get(i).getID());
       DataTable dt = qb.executeDataTable();
       for (int j = 0; j * 100.0D < dt.getRowCount(); ++j) {
         list = new ArrayList();
         for (int k = j * 100; (k < (j + 1) * 100) && (k < dt.getRowCount()); ++k) {
           list.add(dt.getString(k, "ID"));
         }
         ids = StringUtil.join(list);
         restoreDocument(ids, true);
       }
     }
     if (tran.commit())
     {
       for (int i = 0; i < set.size(); ++i) {
         String path = Config.getContextRealPath() + CatalogUtil.getAbsolutePath(set.get(i).getID());
         File f = new File(path);
         if (!f.exists()) {
           f.mkdir();
         }
       }
       this.Response.setMessage("恢复成功!");
     } else {
       this.Response.setError("恢复失败:" + tran.getExceptionMessage());
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.document.RecycleBin
 * JD-Core Version:    0.5.4
 */