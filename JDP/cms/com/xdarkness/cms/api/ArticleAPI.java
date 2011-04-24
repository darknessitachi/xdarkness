 package com.xdarkness.cms.api;
 
 import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang.ArrayUtils;

import com.xdarkness.cms.dataservice.ColumnUtil;
import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.cms.pub.PubFun;
import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.schema.ZCArticleLogSchema;
import com.xdarkness.schema.ZCArticleSchema;
import com.xdarkness.schema.ZDColumnValueSchema;
import com.xdarkness.schema.ZDColumnValueSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;
 
 public class ArticleAPI
   implements APIInterface
 {
   private ZCArticleSchema article;
   private Mapx customData;
   private Mapx params;
 
   public boolean setSchema(Schema schema)
   {
     this.article = ((ZCArticleSchema)schema);
     return false;
   }
 
   public long insert()
   {
     Transaction trans = new Transaction();
     if (insert(trans) > 0L) {
       if (trans.commit()) {
         return 1L;
       }
       return -1L;
     }
     return -1L;
   }
 
   public long insert(Transaction trans)
   {
     long articleID = NoUtil.getMaxID("DocID");
     this.article.setID(articleID);
     if (this.article.getCatalogID() == 0L) {
       return -1L;
     }
     this.article.setSiteID(CatalogUtil.getSiteID(this.article.getCatalogID()));
 
     String innerCode = CatalogUtil.getInnerCode(this.article.getCatalogID());
     this.article.setCatalogInnerCode(innerCode);
     if (this.article.getType() == null) {
       this.article.setType("1");
     }
 
     if (this.article.getTopFlag() == null) {
       this.article.setTopFlag("0");
     }
 
     if (this.article.getCommentFlag() == null) {
       this.article.setCommentFlag("1");
     }
 
     if (this.article.getContent() == null) {
       this.article.setContent("");
     }
 
     this.article.setStickTime(0L);
 
     this.article.setPriority("1");
     this.article.setTemplateFlag("0");
 
     this.article.setPublishFlag("1");
 
     if (this.article.getOrderFlag() == 0L) {
       if (this.article.getPublishDate() != null)
         this.article.setOrderFlag(this.article.getPublishDate().getTime());
       else {
         this.article.setOrderFlag(OrderUtil.getDefaultOrder());
       }
     }
     this.article.setHitCount(0L);
     if (this.article.getStatus() == 0L) {
       this.article.setStatus(0L);
     }
     this.article.setAddTime(new Date(this.article.getOrderFlag()));
 
     if (this.article.getAddUser() == null) {
       this.article.setAddUser("admin");
     }
 
     trans.add(this.article, OperateType.INSERT);
 
     String sqlArticleCount = "update zccatalog set total = total+1,isdirty=1 where id=?";
     trans.add(new QueryBuilder(sqlArticleCount, this.article.getCatalogID()));
 
     if (this.customData != null) {
       addCustomData(trans);
     }
 
     ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
     articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
     articleLog.setAction("INSERT");
     articleLog.setActionDetail("添加新文章");
     articleLog.setArticleID(articleID);
 
     articleLog.setAddUser("admin");
     articleLog.setAddTime(new Date());
     trans.add(articleLog, OperateType.INSERT);
 
     return 1L;
   }
 
   private void addCustomData(Transaction trans)
   {
     ZDColumnValueSchema ColumnValue = null;
     ZDColumnValueSet ColumnValueSet = new ZDColumnValueSet();
     DataTable dt = new QueryBuilder(
       "select b.code code,b.listopt listopt,b.showmod showmod from zdcustomcolumnrela a, zdcustomcolumnitem b where a.type=2 and b.classcode='Sys_CMS' and a.customid=b.id and a.typeid=?", 
       this.article.getCatalogID()).executeDataTable();
     if (dt.getRowCount() > 0) {
       for (int i = 0; i < dt.getRowCount(); i++) {
         String code = "";
         String showMode = "";
         String textValue = "";
         String index = "";
         String[] list = (String[])null;
 
         code = dt.getString(i, "code");
         showMode = dt.getString(i, "ShowMod");
         if (("1".equals(showMode)) || ("2".equals(showMode))) {
           textValue = this.customData.getString(code);
         }
         if (("3".equals(showMode)) || ("4".equals(showMode))) {
           list = dt.getString(i, "listopt").split("\n");
           textValue = this.customData.getString(code);
           index = ArrayUtils.indexOf(list, textValue)+"";
         }
         if ("5".equals(showMode)) {
           list = dt.getString(i, "listopt").split("\n");
           textValue = this.customData.getString(code);
           String[] values = textValue.split("\\|");
           for (int j = 0; j < values.length; j++) {
             if (j != values.length - 1)
               index = index + ArrayUtils.indexOf(list, values[j]) + "|";
             else {
               index = index + ArrayUtils.indexOf(list, values[j]);
             }
           }
         }
 
         ColumnValue = new ZDColumnValueSchema();
         ColumnValue.setID(NoUtil.getMaxID("ColumnValueID"));
         ColumnValue.setColumnCode(code);
         textValue = textValue == null ? "" : textValue;
         ColumnValue.setTextValue(textValue);
         ColumnValue.setRelaID(this.article.getID()+"");
         ColumnValueSet.add(ColumnValue);
       }
     }
     trans.add(
       new QueryBuilder("delete from zdcolumnvalue where classcode='Sys_CMS' and articleid=?", this.article
       .getID()));
     trans.add(ColumnValueSet, OperateType.INSERT);
   }
 
   public boolean update() {
     String articleID = this.params.getString("DocID");
 
     ZCArticleSchema article1 = new ZCArticleSchema();
     article1.setID(articleID);
     if (!article1.fill()) {
       return false;
     }
     if (article1.getCatalogID() == 0L) {
       return false;
     }
 
     article1.setTitle(this.params.getString("Title"));
     article1.setAuthor(this.params.getString("Author"));
     String content = this.params.getString("Content");
     String publishDate = this.params.getString("PublishDate");
 
     if (XString.isNotEmpty(this.params.getString("TopFlag"))) {
       article1.setTopFlag(this.params.getString("TopFlag"));
     }
 
     if (XString.isNotEmpty(this.params.getString("CommentFlag"))) {
       article1.setTopFlag(this.params.getString("CommentFlag"));
     }
 
     if (XString.isNotEmpty(this.params.getString("CommentFlag"))) {
       article1.setTopFlag(this.params.getString("CommentFlag"));
     }
 
     if (XString.isNotEmpty(content)) {
       article1.setContent(content);
     }
 
     if (XString.isNotEmpty(publishDate)) {
       try {
         article1.setPublishDate(DateUtil.parse(publishDate, "yyyy-MM-dd"));
       }
       catch (Exception localException)
       {
       }
     }
     article1.setModifyTime(new Date(article1.getOrderFlag()));
 
     article1.setModifyUser("wsdl");
 
     Transaction trans = new Transaction();
 
     trans.add(article1, OperateType.UPDATE);
 
     ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
     articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
     articleLog.setAction("UPDATE");
     articleLog.setActionDetail("编辑文章");
     articleLog.setArticleID(articleID);
 
     articleLog.setAddUser("wsdl");
     articleLog.setAddTime(new Date());
     trans.add(articleLog, OperateType.INSERT);
 
     return trans.commit();
   }
 
   public boolean delete()
   {
     if (this.article == null) {
       return false;
     }
 
     long articleID = this.article.getID();
     Transaction trans = new Transaction();
 
     trans.add(this.article, OperateType.DELETE);
 
     ZDColumnValueSchema colValue = new ZDColumnValueSchema();
     colValue.setRelaID(articleID+"");
     ZDColumnValueSet colValueSet = colValue.query();
     if ((colValueSet != null) && (!colValueSet.isEmpty())) {
       trans.add(colValueSet, OperateType.DELETE);
     }
 
     String sqlArticleCount = "update zccatalog set total = total-1,isdirty=1 where innercode in(" + 
       CatalogUtil.getParentCatalogCode(this.article.getCatalogInnerCode()) + ")";
     trans.add(new QueryBuilder(sqlArticleCount));
 
     ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
     articleLog.setArticleID(articleID);
     articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
     articleLog.setAction("DELETE");
     articleLog.setActionDetail("删除。删除原因：wsdl删除");
     articleLog.setAddUser("wsdl");
     articleLog.setAddTime(new Date());
     trans.add(articleLog, OperateType.INSERT);
 
     return trans.commit();
   }
 
   public Mapx getCustomData()
   {
     return this.customData;
   }
 
   public void setCustomData(Mapx customData) {
     this.customData = customData;
   }
 
   public static DataTable getPagedDataTable(long catalogID, int pageSize, int pageIndex) {
     DataTable dt = new QueryBuilder("select * from zcarticle where catalogid=?", catalogID).executePagedDataTable(
       pageSize, pageIndex);
     ColumnUtil.extendDocColumnData(dt, catalogID);
     return dt;
   }
 
   public static DataTable getDataTable(long catalogID) {
     return getPagedDataTable(catalogID, -1, -1);
   }
 
   public static String getPreviewURL(long artilceID)
   {
     ZCArticleSchema article = new ZCArticleSchema();
     article.setID(artilceID);
     if (!article.fill()) {
       return null;
     }
 
     String url = Config.getValue("Statical.TargetDir") + "/" + SiteUtil.getAlias(article.getSiteID()) + 
       PubFun.getArticleURL(article);
     url.replaceAll("//", "/");
     return url;
   }
 
   public static String getPublishedURL(long artilceID) {
     ZCArticleSchema article = new ZCArticleSchema();
     article.setID(artilceID);
     String url = null;
     if (article.fill())
       url = SiteUtil.getURL(article.getSiteID()) + "/" + PubFun.getArticleURL(article);
     else {
       url = "#";
     }
     url.replaceAll("/+", "/");
     return url;
   }
 
   public static void main(String[] args) {
     DataTable dt = getPagedDataTable(5954L, 2, 0);
     System.out.println(dt.toString());
   }
 
   public Mapx getParams() {
     return this.params;
   }
 
   public void setParams(Mapx params) {
     this.params = params;
     convertParams(params);
   }
 
   public void convertParams(Mapx params) {
     Iterator iter = params.keySet().iterator();
     while (iter.hasNext()) {
       Object key = iter.next();
       String value = params.getString(key);
       if ((XString.isEmpty(value)) || ("null".equalsIgnoreCase(value)))
         params.put(key, "");
     }
   }
 }

          
/*    com.xdarkness.cms.api.ArticleAPI
 * JD-Core Version:    0.6.0
 */