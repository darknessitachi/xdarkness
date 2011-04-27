 package com.zving.cms.template;
 
 import com.zving.cms.dataservice.Advertise;
 import com.zving.cms.dataservice.ColumnUtil;
 import com.zving.cms.dataservice.CustomTableUtil;
 import com.zving.cms.dataservice.Form;
 import com.zving.cms.dataservice.Vote;
 import com.zving.cms.pub.CatalogUtil;
 import com.zving.cms.pub.PubFun;
 import com.zving.cms.pub.SiteUtil;
 import com.zving.cms.stat.report.SourceReport;
 import com.zving.framework.Config;
 import com.zving.framework.controls.HtmlP;
 import com.zving.framework.controls.TreeAction;
 import com.zving.framework.data.DBUtil;
 import com.zving.framework.data.DataColumn;
 import com.zving.framework.data.DataRow;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.utility.DateUtil;
 import com.zving.framework.utility.StringUtil;
 import com.zving.schema.ZCArticleSchema;
 import com.zving.schema.ZCArticleSet;
 import com.zving.schema.ZCCatalogSchema;
 import com.zving.schema.ZCCatalogSet;
 import com.zving.schema.ZCCustomTableSchema;
 import com.zving.schema.ZCCustomTableSet;
 import com.zving.schema.ZCImagePlayerSchema;
 import com.zving.schema.ZCImagePlayerSet;
 import com.zving.schema.ZCSiteSchema;
 import com.zving.schema.ZCTagSchema;
 import com.zving.schema.ZCVoteSchema;
 import com.zving.schema.ZCVoteSet;
 import com.zving.statical.TemplateData;
 import java.io.File;
 import java.lang.reflect.Method;
 import java.util.Date;
 
 public class CmsTemplateData extends TemplateData
 {
   private static final String SEPARATE = "|";
   private DataRow block;
   private DataRow doc;
   private DataRow catalog;
   private DataRow site;
   private int level;
   private String levelStr;
 
   public void setSite(ZCSiteSchema site)
   {
     this.site = site.toDataRow();
   }
 
   public void setCatalog(ZCCatalogSchema catalog) {
     setCatalog(catalog.toDataRow());
   }
 
   public void setDoc(Schema schema) {
     this.doc = schema.toDataRow();
   }
 
   public DataRow getDoc() {
     return this.doc;
   }
 
   public void setDoc(DataRow doc) {
     this.doc = doc;
   }
 
   public DataRow getCatalog() {
     return this.catalog;
   }
 
   public void setCatalog(DataRow catalog) {
     this.catalog = catalog;
     ColumnUtil.extendCatalogColumnData(this.catalog, this.site.getLong("ID"), getLevelStr());
   }
 
   public DataRow getSite() {
     return this.site;
   }
 
   public void setSite(DataRow site) {
     this.site = site;
   }
 
   public DataTable getList(String item, String name, String displayLevel, String count) {
     return getCatalogList(item, "article", name, displayLevel, count, null);
   }
 
   public DataTable getList(String item, String name, String displayLevel, String count, String condition) {
     return getCatalogList(item, "article", name, displayLevel, count, condition);
   }
 
   public DataTable getCatalogList(String item, String catalogType, String name, String displayLevel, String count, String condition)
   {
     DataTable dt = null;
 
     String key = item + "_" + catalogType + "_" + displayLevel + "_" + name + "_" + count + "_" + condition + "_" + 
       this.level;
     dt = TemplateCache.getDataTable(key);
     if (dt != null) {
       return dt;
     }
 
     if ((count == null) || ("".equals(count)) || ("null".equals(count))) {
       count = "20";
     }
     int pageSize = Integer.parseInt(count);
     if ("catalog".equalsIgnoreCase(item)) {
       String typeSql = null;
       if ("article".equals(catalogType))
         typeSql = " and type =1";
       else if ("image".equals(catalogType))
         typeSql = " and type =4";
       else if ("video".equals(catalogType))
         typeSql = " and type =5";
       else if ("audio".equals(catalogType))
         typeSql = " and type =6";
       else if ("attachment".equals(catalogType))
         typeSql = " and type =7";
       else if ("magazine".equalsIgnoreCase(catalogType))
         typeSql = " and type =3";
       else if ("goods".equals(catalogType))
         typeSql = " and type =9";
       else if ("brand".equals(catalogType))
         typeSql = " and type =10";
       else {
         typeSql = " and type =1";
       }
 
       String parentID = null;
 
       if ((StringUtil.isNotEmpty(name)) && (!"null".equalsIgnoreCase(name)) && 
         (this.site != null)) {
         if (name.indexOf("/") != -1)
           parentID = CatalogUtil.getIDByNames(this.site.getString("ID"), name);
         else {
           parentID = CatalogUtil.getIDByName(this.site.getString("ID"), name);
         }
 
         if ((StringUtil.isEmpty(parentID)) && (StringUtil.isDigit(name))) {
           parentID = name;
         }
 
       }
 
       String parentSql = "";
       if ("root".equalsIgnoreCase(displayLevel)) {
         if (StringUtil.isEmpty(parentID)) {
           parentID = "0";
         }
         parentSql = " and parentid=" + parentID;
       } else if ("current".equalsIgnoreCase(displayLevel)) {
         if ((StringUtil.isEmpty(parentID)) && 
           (this.catalog != null)) {
           parentID = this.catalog.getString("ParentID");
         }
 
         parentSql = " and parentid=" + parentID;
       } else if ("child".equalsIgnoreCase(displayLevel)) {
         if ((StringUtil.isEmpty(parentID)) && 
           (this.catalog != null))
         {
           if ("1".equals(this.catalog.getString("isLeaf")))
           {
             if ("magazine".equalsIgnoreCase(catalogType))
               parentID = this.catalog.getString("ID");
             else
               parentID = this.catalog.getString("ParentID");
           }
           else {
             parentID = this.catalog.getString("ID");
           }
         }
 
         parentSql = " and parentid=" + parentID;
       } else if ("all".equalsIgnoreCase(displayLevel)) {
         if ((StringUtil.isEmpty(parentID)) && 
           (this.catalog != null))
         {
           if ("1".equals(this.catalog.getString("isLeaf")))
             parentID = this.catalog.getString("ParentID");
           else {
             parentID = this.catalog.getString("ID");
           }
         }
 
         parentSql = " and innercode like '" + CatalogUtil.getInnerCode(parentID) + "%'";
       } else {
         if ((StringUtil.isEmpty(parentID)) && 
           (this.catalog != null))
         {
           if ("1".equals(this.catalog.getString("isLeaf")))
             parentID = this.catalog.getString("ParentID");
           else {
             parentID = this.catalog.getString("ID");
           }
         }
 
         parentSql = " and parentid=" + parentID;
       }
 
       String sql = "";
       if (StringUtil.isEmpty(parentID)) {
         return null;
       }
       if ((StringUtil.isEmpty(condition)) || ("null".equalsIgnoreCase(condition)))
         condition = "";
       else {
         condition = " and " + condition;
       }
       sql = "select * from zccatalog where siteID=? and PublishFlag='Y' " + parentSql + typeSql + condition + 
         " Order by orderflag,id ";
       if ("magazine".equalsIgnoreCase(catalogType)) {
         sql = "select * from zccatalog where siteID=? " + parentSql + typeSql + condition + 
           " Order by orderflag desc,id desc";
       }
       QueryBuilder qb = new QueryBuilder(sql);
       qb.add(this.site.getString("ID"));
       dt = qb.executePagedDataTable(pageSize, 0);
 
       String levelString = getLevelStr();
       dt.insertColumn("Link");
       dt.insertColumn("_RowNo");
       for (int i = 0; i < dt.getRowCount(); ++i) {
         dt.set(i, "_RowNo", i + 1);
         dt.set(i, "ImagePath", levelString + dt.getString(i, "ImagePath"));
         String url = CatalogUtil.getLink(dt.getString(i, "ID"), levelString);
         dt.set(i, "Link", url);
       }
       ColumnUtil.extendCatalogColumnData(dt, this.site.getLong("ID"), levelString);
     }
     TemplateCache.setDataTable(key, dt);
 
     return dt;
   }
 
   public DataTable getDocList(String item, String catalogName, String displayLevel, String type, String count) {
     return getDocList(item, catalogName, displayLevel, null, type, count, "");
   }
 
   public DataTable getDocList(String item, String catalogName, String type, String count) {
     return getDocList(item, catalogName, "all", null, type, count, "");
   }
 
   public DataTable getDocList(String item, String catalogName, String displayLevel, String orderType, String count, String condition)
   {
     return getDocList(item, catalogName, displayLevel, "", orderType, count, condition);
   }
 
   public DataTable getDocList(String item, String catalogName, String displayLevel, String hasAttribute, String orderType, String countStr, String condition)
   {
     DataTable dt = null;
     String key = item + "_" + catalogName + "_" + displayLevel + "_" + hasAttribute + "_" + orderType + "_" + 
       countStr + "_" + condition + "_" + this.level;
 
     if (!"relate".equalsIgnoreCase(orderType)) {
       dt = TemplateCache.getDataTable(key);
       if (dt != null) {
         return dt;
       }
     }
 
     if ((countStr == null) || ("".equals(countStr)) || ("null".equals(countStr))) {
       countStr = "50";
     }
     int pageSize = Integer.parseInt(countStr);
     dt = getPagedDocList(item, catalogName, displayLevel, hasAttribute, orderType, condition, pageSize, 0);
     TemplateCache.setDataTable(key, dt);
     return dt;
   }
 
   public DataTable getPagedDocList(String item, String catalogName, String displayLevel, String hasAttribute, String orderType, String condition, int pageSize, int pageIndex)
   {
     DataTable dt = null;
 
     QueryBuilder qb = getDocListQueryBuilder(item, catalogName, displayLevel, hasAttribute, orderType, condition, 
       "*");
     dt = qb.executePagedDataTable(pageSize, pageIndex);
 
     if (dt == null) {
       return null;
     }
 
     dt.insertColumn("ArticleLink");
     dt.insertColumn("_RowNo");
 
     String levelString = getLevelStr();
     String[] columnValue = new String[dt.getRowCount()];
     String[] catalogValue = new String[dt.getRowCount()];
     String[] catalogLink = new String[dt.getRowCount()];
     if (dt.getRowCount() > 0) {
       ColumnUtil.extendDocColumnData(dt, dt.getString(0, "catalogID"));
       dt.insertColumn("filePath");
     }
 
     if (("article".equalsIgnoreCase(item)) || ("goods".equalsIgnoreCase(item))) {
       dt.insertColumns(new String[] { "FirstImagePath", "FirstVideoImage", "FirstVideoHtml", "FirstTag", 
         "FirstTagUrl" });
       dealTag(dt);
     } else if ("video".equalsIgnoreCase(item)) {
       dt.insertColumn("RelaVideoHtml");
     }
 
     for (int i = 0; i < dt.getRowCount(); ++i) {
       dt.set(i, "_RowNo", i + 1);
       String docID = dt.getString(i, "ID");
       String docCatalogID = dt.getString(i, "catalogID");
 
       ZCCatalogSchema catalogSchema = CatalogUtil.getSchema(docCatalogID);
 
       catalogValue[i] = catalogSchema.getName();
       catalogLink[i] = CatalogUtil.getLink(docCatalogID, levelString);
 
       if (("article".equalsIgnoreCase(item)) || ("goods".equalsIgnoreCase(item))) {
         if ("4".equals((String)dt.get(i, "Type"))) {
           columnValue[i] = ((String)dt.get(i, "RedirectURL"));
         } else {
           columnValue[i] = (levelString + PubFun.getDocURL(dt.get(i)));
 
           String imagePath = Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
             SiteUtil.getAlias(this.site.getLong("ID")) + "/";
           imagePath = imagePath.replaceAll("/+", "/");
 
           String attachPath = Config.getContextPath() + "/Services/AttachDownLoad.jsp";
           attachPath = imagePath.replaceAll("/+", "/");
 
           String content = dt.getString(i, "Content");
           content = content.replaceAll(imagePath, 
             PubFun.getLevelStr(CatalogUtil.getDetailLevel(docCatalogID)));
           content = content.replaceAll(attachPath, Config.getValue("ServicesContext") + "AttachDownLoad.jsp");
         }
 
         PubFun.dealArticleMedia(dt, levelString, "image,attchment,video");
       }
       else if ("image".equalsIgnoreCase(item))
       {
         DataTable imageRelaTable = new QueryBuilder("select a.id as imageID,b.id,b.catalogid,b.publishdate from zcimagerela a ,zcarticle b where a.relatype='ArticleImage' and a.relaid=b.id and a.id=?", 
           docID).executeDataTable();
         if ((imageRelaTable != null) && (imageRelaTable.getRowCount() > 0)) {
           String articleCatalogID = imageRelaTable.getString(0, "catalogid");
           String articleID = imageRelaTable.getString(0, "id");
           String nameRule = CatalogUtil.getSchema(articleCatalogID).getDetailNameRule();
           if (StringUtil.isNotEmpty(nameRule)) {
             HtmlNameParser nameParser = new HtmlNameParser(this.site, null, imageRelaTable.get(0), nameRule);
             HtmlNameRule h = nameParser.getNameRule();
             dt.set(i, "articlelink", levelString + h.getFullPath());
           } else {
             ZCArticleSchema article = new ZCArticleSchema();
             article.setID(articleID);
             String articleLink = "";
             if (article.fill())
               articleLink = levelString + PubFun.getArticleURL(article);
             else {
               articleLink = "#";
             }
 
             dt.set(i, "articlelink", articleLink);
           }
         } else {
           dt.set(i, "articlelink", "#");
         }
 
         columnValue[i] = (levelString + CatalogUtil.getPath(docCatalogID) + docID + ".shtml");
       } else if ("video".equalsIgnoreCase(item)) {
         int vCount = Integer.parseInt(dt.getString(i, "Count"));
         String path = "";
         for (int n = 0; n < vCount; ++n) {
           if (n != 0) {
             path = path + "|";
           }
           path = path + dt.getString(i, "path") + n + "_" + dt.getString(i, "FileName");
         }
         dt.set(i, "filePath", path);
         String nameRule = CatalogUtil.getSchema(docCatalogID).getDetailNameRule();
         if (StringUtil.isNotEmpty(nameRule)) {
           HtmlNameParser nameParser = new HtmlNameParser(this.site, null, dt.get(i), nameRule);
           HtmlNameRule h = nameParser.getNameRule();
           columnValue[i] = (levelString + h.getFullPath());
         } else {
           columnValue[i] = (levelString + CatalogUtil.getPath(docCatalogID) + docID + ".shtml");
         }
 
         dt.set(i, "RelaVideoHtml", PubFun.getVideoHtml(dt.getDataRow(i)));
       } else if ("attachment".equalsIgnoreCase(item)) {
         if ((StringUtil.isNotEmpty(docCatalogID)) && ("N".equals(CatalogUtil.getAttachDownFlag(docCatalogID))))
           columnValue[i] = (levelString + dt.getString(i, "path") + dt.getString(i, "filename"));
         else if ("N".equals(SiteUtil.getAttachDownFlag(dt.getString(i, "SiteID"))))
           columnValue[i] = (levelString + dt.getString(i, "path") + dt.getString(i, "filename"));
         else
           columnValue[i] = (Config.getValue("ServicesContext") + "/AttachDownLoad.jsp?id=" + docID);
       }
       else {
         String nameRule = CatalogUtil.getSchema(docCatalogID).getDetailNameRule();
         if (StringUtil.isNotEmpty(nameRule)) {
           HtmlNameParser nameParser = new HtmlNameParser(this.site, null, dt.get(i), nameRule);
           HtmlNameRule h = nameParser.getNameRule();
           columnValue[i] = (levelString + h.getFullPath());
         } else {
           columnValue[i] = (levelString + CatalogUtil.getPath(docCatalogID) + docID + ".shtml");
         }
       }
     }
 
     dt.insertColumn("Link", columnValue);
     dt.insertColumn("CatalogName", catalogValue);
     dt.insertColumn("CatalogLink", catalogLink);
     dt.insertColumn("BranchName");
     for (int i = 0; i < dt.getRowCount(); ++i) {
       dt.set(i, "BranchName", PubFun.getBranchName(dt.getString(i, "BranchInnerCode")));
     }
 
     if ((this.doc != null) && ((
       ("relate".equalsIgnoreCase(orderType)) || ("recommend".equalsIgnoreCase(orderType))))) {
       String relaIDs = "";
       if ("relate".equalsIgnoreCase(orderType))
         relaIDs = this.doc.getString("RelativeArticle");
       else if ("recommend".equalsIgnoreCase(orderType)) {
         relaIDs = this.doc.getString("RecommendArticle");
       }
 
       String[] ids = relaIDs.split("\\,");
       DataTable result = new DataTable(dt.getDataColumns(), null);
       for (int i = 0; i < ids.length; ++i) {
         for (int j = 0; j < dt.getRowCount(); ++j) {
           if (dt.getString(j, "ID").equals(ids[i])) {
             result.insertRow(dt.getDataRow(j));
             break;
           }
         }
       }
 
       dt = result;
     }
 
     return dt;
   }
 
   public int getPagedDocListTotal(String item, String catalogName, String displayLevel, String hasAttribute, String orderType, String condition)
   {
     QueryBuilder qb = getDocListQueryBuilder(item, catalogName, displayLevel, hasAttribute, orderType, condition, 
       "*");
     int total = DBUtil.getCount(qb);
     return total;
   }
 
   public QueryBuilder getDocListQueryBuilder(String item, String catalogName, String displayLevel, String hasAttribute, String orderType, String condition, String cols)
   {
     String typeStr = "";
     String key = item + "_" + catalogName + "_" + displayLevel + "_" + hasAttribute + "_" + orderType + "_" + 
       condition + "_" + cols + "_querybuilder";
     QueryBuilder qb = TemplateCache.getQueryBuilder(key);
     if (qb != null) {
       return qb;
     }
     qb = new QueryBuilder();
 
     if (("article".equalsIgnoreCase(item)) || ("goods".equalsIgnoreCase(item))) {
       if (("relate".equalsIgnoreCase(orderType)) && (this.doc != null)) {
         String relateID = this.doc.getString("RelativeArticle");
         String keyword = this.doc.getString("keyword");
         if (StringUtil.isNotEmpty(relateID)) {
           typeStr = typeStr + " and id in (" + relateID + ") ";
         } else if ((keyword != null) && (!"".equalsIgnoreCase(keyword))) {
           String[] keywordArr = keyword.split(",");
           for (int i = 0; i < keywordArr.length; ++i)
             typeStr = typeStr + " and keyword like '%" + keywordArr[i] + "%'";
         }
         else {
           typeStr = typeStr + " and 1=2";
         }
         typeStr = typeStr + " order by topflag desc,orderflag desc,publishdate desc, id desc";
       } else if (("recommend".equalsIgnoreCase(orderType)) && (this.doc != null)) {
         String recommendArticle = this.doc.getString("RecommendArticle");
         if (StringUtil.isNotEmpty(recommendArticle))
           typeStr = typeStr + " and id in (" + recommendArticle + ") ";
         else {
           typeStr = typeStr + " and 1=2";
         }
         typeStr = typeStr + " order by topflag desc,orderflag desc,publishdate desc, id desc";
       } else if ("hitcount".equalsIgnoreCase(orderType)) {
         typeStr = typeStr + " order by hitcount desc,topflag desc,orderflag desc";
       } else if ("top".equalsIgnoreCase(orderType)) {
         typeStr = typeStr + " and topflag='1' order by orderflag desc";
       } else if ("recent".equalsIgnoreCase(orderType)) {
         typeStr = typeStr + " order by topflag desc, publishdate desc,orderflag desc, id desc";
       } else {
         typeStr = typeStr + " order by topflag desc,orderflag desc,publishdate desc, id desc";
       }
 
       if ((StringUtil.isNotEmpty(hasAttribute)) && (!"null".equals(hasAttribute))) {
         String[] attribute = hasAttribute.split(",");
         if (attribute.length > 0) {
           String attributeSql = "";
           for (int i = 0; i < attribute.length; ++i) {
             String attr = attribute[i].trim();
             if (StringUtil.isNotEmpty(attr)) {
               attributeSql = attributeSql + " and attribute like '%" + attribute[i] + "%'";
             }
           }
           typeStr = attributeSql + typeStr;
         }
       }
     }
     else if ("recent".equalsIgnoreCase(orderType)) {
       typeStr = typeStr + " order by id desc";
     } else {
       typeStr = typeStr + " order by id desc";
     }
 
     String catalogID = null;
     if ((StringUtil.isNotEmpty(catalogName)) && (!"null".equalsIgnoreCase(catalogName))) {
       if (catalogName.indexOf(",") != -1)
         catalogID = CatalogUtil.getIDsByName(this.site.getString("ID"), catalogName);
       else if (catalogName.indexOf("/") != -1)
         catalogID = CatalogUtil.getIDByNames(this.site.getString("ID"), catalogName);
       else {
         catalogID = CatalogUtil.getIDByName(this.site.getString("ID"), catalogName);
       }
 
       if ((StringUtil.isEmpty(catalogID)) && (StringUtil.isDigit(catalogName)))
         catalogID = catalogName;
     }
     else if ("relate".equalsIgnoreCase(orderType)) {
       displayLevel = "root";
     } else {
       if (this.catalog != null) {
         catalogID = this.catalog.getString("ID");
       }
       if (this.doc != null) {
         catalogID = this.doc.getString("CatalogID");
       }
 
     }
 
     if ((!"root".equalsIgnoreCase(displayLevel)) && (StringUtil.isEmpty(catalogID))) {
       return null;
     }
 
     String parentInnerCode = "";
     if ((StringUtil.isNotEmpty(catalogID)) && (catalogID.indexOf(",") != -1)) {
       String[] catalogIDs = catalogID.split(",");
       for (int m = 0; m < catalogIDs.length; ++m) {
         parentInnerCode = parentInnerCode + CatalogUtil.getInnerCode(catalogIDs[m]) + ",";
       }
 
       if (StringUtil.isNotEmpty(parentInnerCode))
         parentInnerCode = parentInnerCode.substring(0, parentInnerCode.length() - 1);
     }
     else {
       parentInnerCode = CatalogUtil.getInnerCode(catalogID);
     }
     String catalogStr = " 1=1 ";
 
     if ("root".equalsIgnoreCase(displayLevel)) {
       catalogStr = " siteid=?";
       qb.add(this.site.getLong("id"));
     } else if ("current".equalsIgnoreCase(displayLevel)) {
       if ((StringUtil.isNotEmpty(catalogID)) && (catalogID.indexOf(",") != -1)) {
         catalogStr = " catalogid in (";
         catalogStr = catalogStr + catalogID;
         catalogStr = catalogStr + ")";
       } else {
         catalogStr = " catalogid=?";
         qb.add(catalogID);
       }
     } else if ("child".equalsIgnoreCase(displayLevel)) {
       if ((StringUtil.isNotEmpty(catalogID)) && (catalogID.indexOf(",") != -1)) {
         catalogStr = " catalogid not in (";
         catalogStr = catalogStr + catalogID;
         catalogStr = catalogStr + ")";
         String[] innercodes = parentInnerCode.split(",");
         for (int m = 0; m < innercodes.length; ++m)
           if (m == 0) {
             catalogStr = catalogStr + " and (cataloginnercode like '";
             catalogStr = catalogStr + innercodes[m] + "%'";
           } else if (m == innercodes.length - 1) {
             catalogStr = catalogStr + " or cataloginnercode like '";
             catalogStr = catalogStr + innercodes[m] + "%')";
           } else {
             catalogStr = catalogStr + " or cataloginnercode like '";
             catalogStr = catalogStr + innercodes[m] + "%'";
           }
       }
       else {
         catalogStr = " catalogid <> ?";
         qb.add(catalogID);
         catalogStr = catalogStr + " and cataloginnercode like ?";
         qb.add(parentInnerCode + "%");
       }
     } else if ("all".equalsIgnoreCase(displayLevel)) {
       if ((StringUtil.isNotEmpty(catalogID)) && (catalogID.indexOf(",") != -1)) {
         String[] innercodes = parentInnerCode.split(",");
         for (int m = 0; m < innercodes.length; ++m)
           if (m == 0) {
             catalogStr = catalogStr + " and (cataloginnercode like '";
             catalogStr = catalogStr + innercodes[m] + "%'";
           } else if (m == innercodes.length - 1) {
             catalogStr = catalogStr + " or cataloginnercode like '";
             catalogStr = catalogStr + innercodes[m] + "%')";
           } else {
             catalogStr = catalogStr + " or cataloginnercode like '";
             catalogStr = catalogStr + innercodes[m] + "%'";
           }
       }
       else {
         catalogStr = catalogStr + " and cataloginnercode like ?";
         qb.add(parentInnerCode + "%");
       }
     }
     else if ((StringUtil.isNotEmpty(catalogID)) && (catalogID.indexOf(",") != -1)) {
       String[] innercodes = parentInnerCode.split(",");
       for (int m = 0; m < innercodes.length; ++m)
         if (m == 0) {
           catalogStr = catalogStr + " and (cataloginnercode like '";
           catalogStr = catalogStr + innercodes[m] + "%'";
         } else if (m == innercodes.length - 1) {
           catalogStr = catalogStr + " or cataloginnercode like '";
           catalogStr = catalogStr + innercodes[m] + "%')";
         } else {
           catalogStr = catalogStr + " or cataloginnercode like '";
           catalogStr = catalogStr + innercodes[m] + "%'";
         }
     }
     else {
       catalogStr = catalogStr + " and cataloginnercode like ?";
       qb.add(parentInnerCode + "%");
     }
 
     String statusStr = "";
     if (("article".equals(item)) || ("goods".equals(item))) {
       statusStr = " and status in(20,30) and (publishdate<=? or publishdate is null)";
 
       qb.add(new Date());
     }
     if ((StringUtil.isEmpty(condition)) || ("null".equals(condition))) {
       condition = "1=1";
     }
     String sql = "";
     if (item.equals("goods"))
       sql = sql + "select " + cols + " from zs";
     else {
       sql = sql + "select " + cols + " from zc";
     }
     sql = sql + item + " where " + condition + " and " + catalogStr + statusStr + typeStr;
     qb.setSQL(sql);
     TemplateCache.setQueryBuilder(key, qb);
     return qb;
   }
 
   public DataTable getMagazineList(String item, String name, String count) {
     return getMagazineList(item, null, name, count, null);
   }
 
   public DataTable getMagazineList(String item, String name, String count, String condition) {
     return getMagazineList(item, null, name, count, condition);
   }
 
   public DataTable getMagazineList(String item, String type, String name, String count, String condition)
   {
     DataTable dt = null;
 
     String key = item + "_" + type + "_" + name + "_" + count + "_" + condition;
     dt = TemplateCache.getDataTable(key);
     if (dt != null) {
       return dt;
     }
 
     if ((count == null) || ("".equals(count)) || ("null".equals(count))) {
       count = "20";
     }
     int pageSize = Integer.parseInt(count);
     if ("magazine".equalsIgnoreCase(item)) {
       String sql = "";
       if (StringUtil.isEmpty(name)) {
         sql = "select * from ZCMagazine where siteID=? ";
         QueryBuilder qb = new QueryBuilder(sql);
         qb.add(this.site.getString("ID"));
         dt = qb.executePagedDataTable(pageSize, 0);
       } else {
         sql = "select * from ZCMagazine where siteID=? and name=?";
         QueryBuilder qb = new QueryBuilder(sql);
         qb.add(this.site.getString("ID"));
         qb.add(name);
         dt = qb.executePagedDataTable(pageSize, 0);
       }
     }
     TemplateCache.setDataTable(key, dt);
 
     return dt;
   }
 
   public DataTable getMagazineIssueList(String item, String name, String type, String count, String condition)
   {
     DataTable dt = null;
 
     String key = item + "_" + name + "_" + type + "_" + count + "_" + condition;
     dt = TemplateCache.getDataTable(key);
     if (dt != null) {
       return dt;
     }
 
     if ((count == null) || ("".equals(count)) || ("null".equals(count))) {
       count = "20";
     }
     String levelStr = getLevelStr();
     int pageSize = Integer.parseInt(count);
     if ("magazineissue".equalsIgnoreCase(item)) {
       String sql = "";
       QueryBuilder qb = new QueryBuilder();
       if (StringUtil.isEmpty(name)) {
         sql = "select * from ZCMagazine where siteID=?";
         qb.setSQL(sql);
         qb.add(this.site.getString("ID"));
       } else {
         sql = "select * from ZCMagazine where siteID=? and name=?";
         qb.setSQL(sql);
         qb.add(this.site.getString("ID"));
         qb.add(name);
       }
       DataTable magazineDT = qb.executeDataTable();
 
       if (magazineDT.getRowCount() > 0) {
         sql = "select * from ZCMagazineIssue where MagazineID=?";
         String catalogID = "";
         if ((this.catalog != null) && (!type.equalsIgnoreCase("all"))) {
           catalogID = this.catalog.getString("ID");
           String catalogName = CatalogUtil.getName(catalogID);
           int catalogType = (int)CatalogUtil.getCatalogType(catalogID);
           if ((catalogType == 3) && 
             (catalogName.indexOf("年第") != -1)) {
             String currentYear = catalogName.substring(0, catalogName.indexOf("年第"));
             String currentPeriodNum = catalogName.substring(catalogName.indexOf("年第") + 2, catalogName
               .indexOf("期"));
             sql = "select * from ZCMagazineIssue where MagazineID=? and Year='" + currentYear + 
               "' and PeriodNum='" + currentPeriodNum + "'";
           }
         }
 
         sql = sql + " order by year desc,periodnum desc,ID desc";
 
         qb = new QueryBuilder(sql);
         qb.add(magazineDT.getString(0, "ID"));
         dt = qb.executePagedDataTable(pageSize, 0);
         dt.insertColumn("PrevLink");
         dt.insertColumn("NextLink");
         dt.insertColumn("Link");
 
         if (dt.getRowCount() > 0) {
           for (int i = 0; i < dt.getRowCount() - 1; ++i) {
             String prevCatalogID = CatalogUtil.getIDByNames(this.site.getString("ID"), name + "/" + 
               dt.getString(i, "Year") + "年第" + dt.getString(i, "PeriodNum") + "期");
             dt.set(i, "PrevLink", CatalogUtil.getLink(prevCatalogID, levelStr));
           }
 
           for (int i = 1; i < dt.getRowCount(); ++i) {
             String nextCatalogID = CatalogUtil.getIDByNames(this.site.getString("ID"), name + "/" + 
               dt.getString(i - 1, "Year") + "年第" + dt.getString(i - 1, "PeriodNum") + "期");
             dt.set(0, "NextLink", CatalogUtil.getLink(nextCatalogID, levelStr));
           }
 
           DataTable magazineIssueDT = new QueryBuilder(
             "select * from ZCMagazineIssue where MagazineID=? and ID>? order by year desc,periodnum desc,ID desc", 
             magazineDT.getString(0, "ID"), dt.getString(0, "ID")).executeDataTable();
           if (magazineIssueDT.getRowCount() > 0) {
             String nextCatalogID = CatalogUtil.getIDByNames(this.site.getString("ID"), name + "/" + 
               magazineIssueDT.getString(0, "Year") + "年第" + 
               magazineIssueDT.getString(0, "PeriodNum") + "期");
             dt.set(0, "NextLink", CatalogUtil.getLink(nextCatalogID, levelStr));
           } else {
             dt.set(0, "NextLink", "#");
           }
 
           magazineIssueDT = new QueryBuilder(
             "select * from ZCMagazineIssue where MagazineID=? and ID<? order by year desc,periodnum desc,ID desc", 
             magazineDT.getString(0, "ID"), dt.getString(0, "ID")).executeDataTable();
           if (magazineIssueDT.getRowCount() > 0) {
             String nextCatalogID = CatalogUtil.getIDByNames(this.site.getString("ID"), name + "/" + 
               magazineIssueDT.getString(0, "Year") + "年第" + 
               magazineIssueDT.getString(0, "PeriodNum") + "期");
             dt.set(dt.getRowCount() - 1, "PrevLink", CatalogUtil.getLink(nextCatalogID, levelStr));
           } else {
             dt.set(dt.getRowCount() - 1, "PrevLink", "#");
           }
 
           for (int m = 0; m < dt.getRowCount(); ++m) {
             String ID = CatalogUtil.getIDByNames(this.site.getString("ID"), name + "/" + dt.getString(m, "Year") + 
               "年第" + dt.getString(m, "PeriodNum") + "期");
             ColumnUtil.extendCatalogColumnData(dt.getDataRow(m), SiteUtil.getURL(this.site.getString("ID")));
             if (StringUtil.isDigit(ID))
               dt.set(m, "Link", CatalogUtil.getLink(ID, levelStr));
             else
               dt.set(m, "Link", "#");
           }
           break label1122:
         }
 
         return null;
       }
 
       return null;
     }
 
     label1122: TemplateCache.setDataTable(key, dt);
 
     return dt;
   }
 
   public DataTable getFriendLinkList(String item, String group, String count, String condition)
   {
     DataTable dt = new DataTable();
     if ((count == null) || ("".equalsIgnoreCase(count))) {
       count = "20";
     }
     String sql = "select * from zclink where siteID = " + this.site.getString("ID") + " and LinkGroupID = " + 
       "(select id from zclinkgroup where siteID = " + this.site.getString("ID") + 
       " and name=?) order by OrderFlag desc,id desc";
 
     int pageSize = Integer.parseInt(count);
     dt = new QueryBuilder(sql, group).executePagedDataTable(pageSize, 0);
     return dt;
   }
 
   public DataTable getBlockList(String item, String count, String condition)
   {
     DataTable dt = new DataTable();
     if ((count == null) || ("".equalsIgnoreCase(count))) {
       count = "20";
     }
     String blockid = this.block.getString("ID");
     String blockType = this.block.getString("Type");
     String sortField = this.block.getString("SortField");
     String catalogID = this.block.getString("CatalogID");
     String siteID = this.block.getString("SiteID");
 
     String sql = "";
     QueryBuilder qb = new QueryBuilder();
     if ("1".equalsIgnoreCase(blockType)) {
       if ((catalogID != null) && (!"".equalsIgnoreCase(catalogID))) {
         sql = "select * from zcarticle where catalogid = ? order by " + sortField + " desc";
         qb.add(catalogID);
       } else {
         sql = "select * from zcarticle where exists(select id from zccatalog where siteid = ? and id=zcarticle.catalogid) order by " + 
           sortField + " desc";
         qb.add(siteID);
       }
     } else if ("2".equalsIgnoreCase(blockType)) {
       sql = "select * from zcpageblockitem where blockid = ? order by id";
       qb.add(blockid);
     }
     qb.setSQL(sql);
 
     int pageSize = Integer.parseInt(count);
     dt = qb.executePagedDataTable(pageSize, 0);
 
     String levelString = "";
     for (int i = 0; i < this.level; ++i) {
       levelString = levelString + "../";
     }
 
     int size = dt.getRowCount();
     String[] columnValue = new String[dt.getRowCount()];
     for (int i = 0; i < size; ++i) {
       if ("1".equalsIgnoreCase(blockType))
         columnValue[i] = (levelString + PubFun.getDocURL(dt.get(i)));
       else if ("2".equalsIgnoreCase(blockType)) {
         columnValue[i] = ((String)dt.get(i, "URL"));
       }
     }
 
     dt.insertColumn("Link", columnValue);
     return dt;
   }
 
   public String getTree(String id, String method, String tagBody, String style, String levelStr) {
     TreeAction ta = new TreeAction();
 
     ta.setTagBody(tagBody);
     ta.setMethod(method);
     ta.setID(id);
 
     int level = Integer.parseInt(levelStr);
     if (level <= 0) {
       level = 999;
     }
     ta.setLevel(level);
     ta.setStyle(style);
 
     HtmlP p = new HtmlP();
     try {
       p.parseHtml(ta.getTagBody());
 
       ta.setTemplate(p);
 
       int index = method.lastIndexOf('.');
       String className = method.substring(0, index);
       method = method.substring(index + 1);
       Class c = Class.forName(className);
       Method m = c.getMethod(method, new Class[] { TreeAction.class });
       m.invoke(null, new Object[] { ta });
     }
     catch (Exception e) {
       e.printStackTrace();
     }
 
     return ta.getHtml();
   }
 
   public String getAD(String name, String type) {
     StringBuffer sb = new StringBuffer();
     if ((StringUtil.isEmpty(name)) || (name == null)) {
       sb.append("广告的代码为空，请修改模板");
     } else {
       DataTable positionDT = new QueryBuilder("select * from zcadposition where PositionName = ? and siteid=?", 
         name, this.site.getString("ID")).executeDataTable();
       if (positionDT.getRowCount() <= 0) {
         sb.append("广告版位代码：" + name + "没有找到");
       }
       else if (positionDT.get(0, "PositionType").equals("code")) {
         DataTable adDt = new QueryBuilder(
           "select * from ZCAdvertisement where PositionID = ? and IsOpen = 'Y'", positionDT
           .getString(0, "ID")).executeDataTable();
         sb.append(adDt.getString(0, "AdContent"));
       }
       else if ((type != null) && (StringUtil.isNotEmpty(type)) && (type.equalsIgnoreCase("json"))) {
         sb.append(Advertise.getJson(positionDT.getString(0, "ID")));
       } else {
         String jsName = positionDT.getString(0, "jsname");
         sb.append("<script language='javascript' src='" + getLevelStr() + jsName + "'></script>");
 
         if (!new File(new StringBuffer(String.valueOf(Config.getContextRealPath())).append(Config.getValue("Statical.TargetDir")).append("/")
           .append(SiteUtil.getAlias(positionDT.getString(0, "SiteID"))).append("/").toString().replaceAll("/+", "/") + 
           positionDT.getString(0, "JsName")).exists()) {
           Advertise.CreateJSCode(positionDT.getString(0, "ID"));
         }
       }
 
     }
 
     return sb.toString();
   }
 
   public String getForm(String code) {
     StringBuffer sb = new StringBuffer();
     if (StringUtil.isEmpty(code)) {
       sb.append("表单ID为空，请修改模板");
     } else {
       ZCCustomTableSet set = new ZCCustomTableSchema().query(new QueryBuilder("where Code=?", code));
       if ((code == null) || (set.size() <= 0)) {
         sb.append("表单：" + code + "没有找到");
       } else {
         String parseContent = Form.getRuntimeFormContent(set.get(0));
         sb.append(parseContent);
         sb.append("</form>");
       }
     }
     return sb.toString();
   }
 
   public String getVote(String name, String id)
   {
     StringBuffer sb = new StringBuffer();
     ZCVoteSchema vote = new ZCVoteSchema();
     QueryBuilder qb = null;
     if ((StringUtil.isNotEmpty(id)) && (StringUtil.isDigit(id)))
       qb = new QueryBuilder(" where id=? and siteid=?", id, this.site.getString("ID"));
     else if ((StringUtil.isNotEmpty(name)) && (!"null".equals(name)))
       qb = new QueryBuilder(" where title=? and siteid=?", name, this.site.getString("ID"));
     else {
       return "<font color=red>没有找到对应的调查。</font>";
     }
 
     ZCVoteSet set = vote.query(qb);
     if (set.size() < 1) {
       return "<font color=red>没有找到对应的调查。</font>";
     }
     vote = set.get(0);
     sb.append("<!--" + vote.getTitle() + "-->\n");
     sb.append("<script language='javascript' src='" + 
       new StringBuffer(String.valueOf(getLevelStr())).append("js/vote_").append(vote.getID()).toString().replaceAll("/+", "/") + ".js'></script>");
 
     if (!new File(new StringBuffer(String.valueOf(Config.getContextRealPath())).append(Config.getValue("Statical.TargetDir")).append("/")
       .append(SiteUtil.getAlias(vote.getSiteID())).append("/js/").toString().replaceAll("/+", "/") + 
       "vote_" + vote.getID() + ".js").exists()) {
       Vote.generateJS(vote.getID());
     }
     return sb.toString();
   }
 
   public DataRow getVoteData(String name, String id) {
     ZCVoteSchema vote = new ZCVoteSchema();
     QueryBuilder qb = null;
     if ((StringUtil.isNotEmpty(id)) && (StringUtil.isDigit(id)))
       qb = new QueryBuilder(" where id=? and siteid=?", id, this.site.getString("ID"));
     else if ((StringUtil.isNotEmpty(name)) && (!"null".equals(name)))
       qb = new QueryBuilder(" where title=? and siteid=?", name, this.site.getString("ID"));
     else {
       return null;
     }
 
     ZCVoteSet set = vote.query(qb);
     if (set.size() < 1) {
       return null;
     }
     vote = set.get(0);
     return vote.toDataRow();
   }
 
   public DataTable getVoteSubjects(String voteID, int count) {
     QueryBuilder qb = new QueryBuilder("select * from zcvoteSubject where VoteID = ? order by ID", voteID);
     return qb.executePagedDataTable(count, 0);
   }
 
   public DataTable getVoteItems(String subjectID, String inputType, int count) {
     QueryBuilder qb = new QueryBuilder("select * from zcvoteitem where subjectID = ? order by ID", subjectID);
     DataTable dt = qb.executePagedDataTable(count, 0);
     dt.insertColumn("html");
 
     if ("Y".equals(inputType))
       inputType = "checkbox";
     else {
       inputType = "radio";
     }
     for (int i = 0; i < dt.getRowCount(); ++i) {
       String html = "";
       if ("0".equals(dt.getString(i, "ItemType")))
         html = "<label><input name='Subject_" + subjectID + "' type='" + inputType + "' value='" + 
           dt.getString(i, "id") + "' />" + dt.getString(i, "item") + "</label>\n";
       else {
         html = "<label><input name='Subject_" + subjectID + "' type='" + inputType + "' value='" + 
           dt.getString(i, "id") + "' id='Subject_" + subjectID + "_Item_" + dt.getString(i, "id") + 
           "_Button'/>" + dt.getString(i, "item") + "</label><input id='Subject_" + subjectID + "_Item_" + 
           dt.getString(i, "id") + "' name='Subject_" + subjectID + "_Item_" + dt.getString(i, "id") + 
           "' type='text' value='' onClick=\\\"clickInput('Subject_" + subjectID + "_Item_" + 
           dt.getString(i, "id") + "');\\\"/>\n";
       }
       dt.set(i, "html", html);
     }
     return dt;
   }
 
   public String getComment(String count) {
     return "<script src=\"" + Config.getValue("ServicesContext") + Config.getValue("CommentListJS") + "?RelaID=" + 
       this.doc.getString("ID") + "&CatalogID=" + this.doc.getString("CatalogID") + "&CatalogType=" + 
       this.catalog.getString("Type") + "&SiteID=" + this.site.getString("id") + "&Count=" + count + "\"></script>";
   }
 
   public String getImagePlayer(String name, String width, String height, String count, String charwidth)
   {
     if ((StringUtil.isEmpty(charwidth)) || ("null".equalsIgnoreCase(charwidth))) {
       charwidth = "100";
     }
     StringBuffer sb = new StringBuffer();
     ZCImagePlayerSchema imagePlayer = new ZCImagePlayerSchema();
     imagePlayer.setName(name);
     imagePlayer.setSiteID(this.site.getString("id"));
     ZCImagePlayerSet set = imagePlayer.query();
     if (set.size() > 0) {
       imagePlayer = set.get(0);
     } else {
       imagePlayer = new ZCImagePlayerSchema();
       imagePlayer.setCode(name);
       imagePlayer.setSiteID(this.site.getString("id"));
       set = imagePlayer.query();
       if (set.size() > 0) {
         imagePlayer = set.get(0);
       } else {
         sb.append("没有" + name + "对应的图片播放器，请检查" + name + "是否正确。");
         return sb.toString();
       }
     }
 
     int imageCount = 6;
     if (StringUtil.isDigit(count)) {
       imageCount = Integer.parseInt(count);
     }
 
     StringBuffer pics = new StringBuffer();
     StringBuffer links = new StringBuffer();
     StringBuffer texts = new StringBuffer();
     DataTable dt = null;
 
     if ("1".equals(imagePlayer.getImageSource())) {
       String catalogStr = " and cataloginnercode like '%'";
       if ((StringUtil.isNotEmpty(imagePlayer.getRelaCatalogInnerCode())) && 
         (!"null".equalsIgnoreCase(imagePlayer.getRelaCatalogInnerCode()))) {
         catalogStr = " and cataloginnercode like '" + imagePlayer.getRelaCatalogInnerCode() + "%'";
       }
 
       String statusStr = " and status in(20,30) and (publishdate<='" + 
         DateUtil.getCurrentDateTime() + "' or publishdate is null)";
 
       String attributeSql = " and attribute like '%image%'";
       String typeStr = " order by publishdate desc,orderflag desc, id desc";
       dt = new QueryBuilder("select * from zcarticle where siteID=?" + catalogStr + statusStr + attributeSql + 
         typeStr, imagePlayer.getSiteID()).executePagedDataTable(imageCount, 0);
       dt.insertColumns(new String[] { "FirstImagePath" });
       PubFun.dealArticleMedia(dt, getLevelStr(), "image");
 
       for (int i = 0; i < dt.getRowCount(); ++i) {
         String imagePath = dt.getString(i, "FirstImagePath");
         if (StringUtil.isEmpty(imagePath)) continue; if (imagePath.toLowerCase().indexOf("nopicture.jpg") >= 0) {
           continue;
         }
         if (i != 0) {
           pics.append("|");
           links.append("|");
           texts.append("|");
         }
         imagePath = dt.getString(i, "FirstImagePath").substring(
           dt.getString(i, "FirstImagePath").indexOf("upload/"));
         pics.append(getLevelStr() + imagePath);
         String siteUrl = SiteUtil.getURL(dt.getString(i, "SiteID"));
         if (siteUrl.endsWith("shtml")) {
           siteUrl = siteUrl.substring(0, siteUrl.lastIndexOf("/"));
         }
 
         if (!siteUrl.endsWith("/")) {
           siteUrl = siteUrl + "/";
         }
 
         links.append(siteUrl + PubFun.getDocURL(dt.getDataRow(i)));
         if (StringUtil.isNotEmpty(dt.getString(i, "ShortTitle")))
           texts.append(StringUtil.subStringEx(dt.getString(i, "ShortTitle"), Integer.parseInt(charwidth)));
         else
           texts.append(StringUtil.subStringEx(dt.getString(i, "Title"), Integer.parseInt(charwidth)));
       }
     }
     else {
       String sql = "select b.* from ZCImageRela a,zcimage b where a.id = b.id  and a.RelaID=" + 
         imagePlayer.getID() + " and a.RelaType='" + "ImagePlayerImage" + 
         "' order by a.orderflag desc, a.addtime desc";
       dt = new QueryBuilder(sql).executePagedDataTable(imageCount, 0);
 
       for (int i = 0; i < dt.getRowCount(); ++i) {
         if (i != 0) {
           pics.append("|");
           links.append("|");
           texts.append("|");
         }
         pics.append(getLevelStr() + dt.getString(i, "path") + "1_" + dt.getString(i, "FileName"));
         if (StringUtil.isNotEmpty(getLevelStr() + dt.getString(i, "LinkURL"))) {
           links.append(dt.getString(i, "LinkURL"));
         }
         if (StringUtil.isNotEmpty(dt.getString(i, "LinkText"))) {
           texts.append(StringUtil.subStringEx(dt.getString(i, "LinkText"), Integer.parseInt(charwidth)));
         }
       }
     }
 
     int pWidth = 320; int pHeight = 240;
     if (!StringUtil.isDigit(width))
       pWidth = (int)imagePlayer.getWidth();
     else {
       pWidth = Integer.parseInt(width);
     }
 
     if (!StringUtil.isDigit(height))
       pHeight = (int)imagePlayer.getHeight();
     else {
       pHeight = Integer.parseInt(height);
     }
 
     int imgHeight = pHeight;
     if ("Y".equals(imagePlayer.getIsShowText())) {
       imgHeight = pHeight - 22;
     }
     String showText = "";
     if ("Y".equals(imagePlayer.getIsShowText()))
       showText = "&show_text=1&textheight=22";
     else {
       showText = "&show_text=0";
     }
 
     sb.append("<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" width=\"" + pWidth + "\" height=\"" + 
       pHeight + "\">");
     sb.append("<param name=\"movie\" value=\"" + getLevelStr() + "images/ImagePlayer.swf\">");
     sb.append("<param name=\"quality\" value=\"high\">");
     sb.append("<param name=\"wmode\" value=\"transparent\">");
     sb.append("<param name=\"FlashVars\" value=\"pics=" + pics.toString() + "&links=" + links.toString() + 
       "&texts=" + texts.toString() + "&borderwidth=" + pWidth + "&borderheight=" + imgHeight + showText + 
       "&overtxtcolor=FFFF00&txtcolor=FFFF00\"/>");
     sb.append("<embed src=\"" + getLevelStr() + 
       "images/ImagePlayer.swf\" type=\"application/x-shockwave-flash\" wmode=\"transparent\" " + 
       "FlashVars=\"wmode=transparent&pics=" + pics.toString() + "&links=" + links.toString() + "&texts=" + 
       texts.toString() + "&borderwidth=" + pWidth + "&borderheight=" + imgHeight + showText);
 
     sb.append("&button_pos=4&overtxtcolor=FFFF00&txtcolor=FFFF00\" width=\"" + pWidth + "\" height=\"" + pHeight + 
       "\">");
     sb.append("</embed>");
     sb.append("</object>");
 
     return sb.toString();
   }
 
   public String getLinkURL(String type, String code, String name, String spliter) {
     StringBuffer sb = new StringBuffer();
     if ("catalog".equalsIgnoreCase(type)) {
       String levelString = "";
       for (int i = 0; i < this.level; ++i) {
         levelString = levelString + "../";
       }
       if ((code != null) && (!"".equals(code)) && (!"null".equals(code))) {
         ZCCatalogSchema catalog = new ZCCatalogSchema();
         catalog.setAlias(code);
         catalog.setSiteID(Long.parseLong(this.site.getString("ID")));
         ZCCatalogSet set = catalog.query();
         if (set.size() < 1) {
           return "#";
         }
         catalog = set.get(0);
         return CatalogUtil.getLink(catalog.getID(), levelString);
       }
       if ((name != null) && (!"".equals(name)) && (!"null".equals(name))) {
         ZCCatalogSchema catalog = null;
         if (StringUtil.isDigit(name)) {
           catalog = CatalogUtil.getSchema(Long.parseLong(name));
         }
         else {
           String id = CatalogUtil.getIDByNames(this.site.getString("ID"), name);
           catalog = CatalogUtil.getSchema(Long.parseLong(id));
         }
         if (catalog == null) {
           return "#";
         }
         return CatalogUtil.getLink(catalog.getID(), levelString);
       }
     }
     else if ("article".equalsIgnoreCase(type)) {
       String levelString = "";
       for (int i = 0; i < this.level; ++i) {
         levelString = levelString + "../";
       }
       if ((name != null) && (!"".equals(name)) && (!"null".equals(name))) {
         ZCArticleSchema article = new ZCArticleSchema();
         if (StringUtil.isDigit(name)) {
           article.setID(Long.parseLong(name));
         }
         else if (name.indexOf("@") != -1) {
           String title = name.substring(0, name.indexOf("@"));
           name = name.substring(name.indexOf("@") + 1);
           String catlaogID = null;
           if (name.indexOf("/") != -1)
             catlaogID = CatalogUtil.getIDByNames(this.site.getString("ID"), name);
           else {
             catlaogID = CatalogUtil.getIDByName(this.site.getString("ID"), name);
           }
           if (StringUtil.isNotEmpty(catlaogID)) {
             article.setCatalogID(catlaogID);
           }
           article.setTitle(title);
         } else {
           article.setTitle(name);
         }
 
         ZCArticleSet set = article.query();
         if ((set != null) && (set.size() > 0)) {
           article = set.get(0);
           sb.append(levelString + PubFun.getArticleURL(article));
         }
       }
     } else if ("CurrentPosition".equalsIgnoreCase(type)) {
       if ((spliter == null) || ("".equals(spliter)) || ("null".equals(spliter))) {
         spliter = ">";
       }
 
       Object obj = this.catalog.get("ID");
       long catalogID = (obj == null) ? 0L : ((Long)obj).longValue();
       sb.append(PubFun.getCurrentPage(catalogID, this.level, name, spliter, "_self"));
     } else if ("HomeURL".equalsIgnoreCase(type)) {
       if ((StringUtil.isEmpty(name)) || ("null".equalsIgnoreCase(name))) {
         name = "首页";
       }
       sb.append("<a href='" + getLevelStr() + "'>" + name + "</a>");
     } else if ("MessageBoard".equalsIgnoreCase(type)) {
       String BoardID = "";
       if (StringUtil.isNotEmpty(name)) {
         BoardID = new QueryBuilder("select ID from ZCMessageBoard where Name = ? and SiteID = ?", name, this.site
           .getString("ID")).executeString();
       }
       sb.append(Config.getValue("ServicesContext") + "/" + "MessageBoardPage.jsp?BoardID=" + BoardID);
     }
     return sb.toString();
   }
 
   public DataRow getCatalog(String type, String name) {
     DataRow docRow = null;
     if ("catalog".equalsIgnoreCase(type)) {
       String levelString = "";
       for (int i = 0; i < this.level; ++i) {
         levelString = levelString + "../";
       }
       if ((name != null) && (!"".equals(name)) && (!"null".equals(name))) {
         ZCCatalogSchema catalog = new ZCCatalogSchema();
         String id;
         String id;
         if (name.indexOf("/") != -1)
           id = CatalogUtil.getIDByNames(this.site.getString("ID"), name);
         else {
           id = CatalogUtil.getIDByName(this.site.getString("ID"), name);
         }
 
         if ((StringUtil.isEmpty(id)) && (StringUtil.isDigit(name))) {
           id = name;
         }
         catalog.setID(Long.parseLong(id));
 
         if (!catalog.fill()) {
           return null;
         }
         docRow = catalog.toDataRow();
         docRow.insertColumn("link", CatalogUtil.getLink(catalog.getID(), levelString));
       }
 
       ColumnUtil.extendCatalogColumnData(docRow, this.site.getLong("ID"), getLevelStr());
     }
     return docRow;
   }
 
   public DataRow getDocument(String type, String name) {
     DataRow docRow = null;
     if ("article".equalsIgnoreCase(type)) {
       String levelString = "";
       for (int i = 0; i < this.level; ++i) {
         levelString = levelString + "../";
       }
       if ((name != null) && (!"".equals(name)) && (!"null".equals(name))) {
         ZCArticleSchema article = new ZCArticleSchema();
         if (StringUtil.isDigit(name)) {
           article.setID(Long.parseLong(name));
         }
         else if (name.indexOf("@") != -1) {
           String title = name.substring(0, name.indexOf("@"));
           name = name.substring(name.indexOf("@") + 1);
 
           String catlaogID = null;
           if (name.indexOf("/") != -1)
             catlaogID = CatalogUtil.getIDByNames(this.site.getString("ID"), name);
           else {
             catlaogID = CatalogUtil.getIDByName(this.site.getString("ID"), name);
           }
           if (StringUtil.isNotEmpty(catlaogID)) {
             article.setCatalogID(catlaogID);
           }
 
           article.setTitle(title);
         } else {
           article.setTitle(name);
         }
 
         ZCArticleSet set = article.query();
         if ((set != null) && (set.size() > 0)) {
           article = set.get(0);
           docRow = article.toDataRow();
           docRow.insertColumn("link", levelString + PubFun.getArticleURL(article));
         }
       }
     }
     return docRow;
   }
 
   public DataRow getCurrentIssue(String type, String name) {
     DataRow docRow = null;
     if ("magazine".equalsIgnoreCase(type)) {
       String levelString = "";
       for (int i = 0; i < this.level; ++i) {
         levelString = levelString + "../";
       }
       String sql = "";
       QueryBuilder qb = new QueryBuilder();
       if (StringUtil.isEmpty(name)) {
         sql = "select * from ZCMagazine where siteID=?";
         qb.setSQL(sql);
         qb.add(this.site.getString("ID"));
       } else {
         sql = "select * from ZCMagazine where siteID=? and name=?";
         qb.setSQL(sql);
         qb.add(this.site.getString("ID"));
         qb.add(name);
       }
       DataTable magazineDT = qb.executeDataTable();
 
       if (magazineDT.getRowCount() > 0) {
         DataTable dt = new QueryBuilder("select * from ZCMagazineIssue where MagazineID=? and Year='" + 
           magazineDT.getString(0, "CurrentYear") + "' and PeriodNum='" + 
           magazineDT.getString(0, "CurrentPeriodNum") + "'", magazineDT.getString(0, "ID"))
           .executePagedDataTable(1, 0);
         if ((dt != null) && (dt.getRowCount() > 0)) {
           docRow = dt.get(0);
           String catalogName = docRow.getString("Year") + "年第" + docRow.getString("PeriodNum") + "期";
           String parentID = new QueryBuilder("select ID from zccatalog where type='3' and name=? and siteID=? and parentID=0", 
             name, this.site.getString("ID")).executeString();
           DataTable catalogDT = new QueryBuilder("select * from zccatalog where type='3' and name='" + 
             catalogName + "' and siteID=? and parentID=?", this.site.getString("ID"), 
             parentID).executePagedDataTable(1, 0);
           if ((catalogDT != null) && (catalogDT.getRowCount() > 0)) {
             docRow.insertColumn("CatalogInnerCode", catalogDT.getString(0, "InnerCode"));
             docRow.insertColumn("CatalogID", catalogDT.getString(0, "ID"));
             docRow.insertColumn("CatalogName", catalogDT.getString(0, "Name"));
           }
         }
       }
     }
     return docRow;
   }
 
   public String getPage(String type, String value, String name, String target) {
     StringBuffer sb = new StringBuffer();
     if ("currentpage".equalsIgnoreCase(type)) {
       if ((value == null) || ("".equals(value)) || ("null".equals(value))) {
         value = ">";
       }
 
       if ((target == null) || ("".equals(target)) || ("null".equals(target))) {
         value = "_self";
       }
 
       Object obj = this.catalog.get("ID");
       long catalogID = (obj == null) ? 0L : ((Long)obj).longValue();
       sb.append(PubFun.getCurrentPage(catalogID, this.level, name, value, target));
     } else if ("index".equalsIgnoreCase(type)) {
       sb.append(getLevelStr() + "index.shtml");
     }
     return sb.toString();
   }
 
   public DataTable getPagedDataTable(DataTable dt, int type) {
     int count = this.PageSize;
     if ((this.PageIndex + 1) * this.PageSize > this.Total) {
       count = this.Total - this.PageIndex * this.PageSize;
     }
     Object[][] values = new Object[count][dt.getColCount()];
     for (int i = 0; i < count; ++i) {
       values[i] = dt.getDataRow(this.PageIndex * this.PageSize + i).getDataValues();
     }
     return new DataTable(dt.getDataColumns(), values);
   }
 
   public String getLevelStr() {
     if (StringUtil.isEmpty(this.levelStr)) {
       this.levelStr = PubFun.getLevelStr(this.level);
     }
     return this.levelStr;
   }
 
   public static DataTable getSiteMap(long siteID) {
     return getSiteMap(siteID);
   }
 
   public static DataTable getSiteMap(String siteID) {
     if (!StringUtil.checkID(siteID)) {
       return null;
     }
 
     DataTable dt = new QueryBuilder("select treelevel,innercode,name,id,orderflag from ZCCatalog where siteID=? and type=1  order by orderflag,innercode", 
       siteID).executeDataTable();
 
     dt.insertColumn("Link");
     for (int i = 0; i < dt.getRowCount(); ++i) {
       String url = CatalogUtil.getLink(dt.getLong(i, "ID"), PubFun.getLevelStr(CatalogUtil.getLevel(dt.getLong(i, 
         "ID"))));
       if ((dt.getString(i, "url").startsWith("http")) || (dt.getString(i, "url").startsWith("/")) || 
         (dt.getString(i, "url").startsWith("https"))) {
         url = dt.getString(i, "url");
       }
       dt.set(i, "Link", url);
     }
     return dt;
   }
 
   public DataTable getCustomData(String tableName, String CountStr, String condition) {
     int count = 20;
     if (StringUtil.isDigit(CountStr)) {
       count = Integer.parseInt(CountStr);
     }
     String wherePart = "where 1=1 ";
     if ((StringUtil.isNotEmpty(condition)) && (!condition.equals("null"))) {
       wherePart = wherePart + condition;
     }
     return CustomTableUtil.getData(tableName, new QueryBuilder(wherePart), count, 0);
   }
 
   public DataTable getKeywords(String type, String CountStr)
   {
     int count = 20;
     if (StringUtil.isDigit(CountStr)) {
       count = Integer.parseInt(CountStr);
     }
     if (StringUtil.isEmpty(type)) {
       type = type + "week";
     }
 
     Date today = new Date();
 
     int days = -1;
     if ("day".equals(type))
       days = -1;
     else if ("week".equals(type))
       days = -7;
     else if ("month".equals(type))
       days = -31;
     else if ("season".equals(type))
       days = -90;
     else if ("year".equals(type)) {
       days = -365;
     }
     Date startDate = DateUtil.addDay(today, days);
     return SourceReport.getKeywordData(this.site.getLong("id"), startDate, today, count);
   }
 
   public DataTable getArticlePages()
   {
     if (this.doc == null) {
       return null;
     }
     String[] titles = this.doc.getString("PageTitle").split("\\|", -1);
     String[] contents = this.doc.getString("FullContent").split("<!--_ZVING_PAGE_BREAK_-->", -1);
 
     if (titles.length != contents.length) {
       return null;
     }
 
     DataTable dt = new DataTable();
     DataColumn[] columns = { new DataColumn("rownum", 1), 
       new DataColumn("title", 1), new DataColumn("content", 1) };
     String[] rowNum = new String[titles.length];
     for (int i = 0; i < rowNum.length; ++i) {
       String[] rowValues = { i + 1, titles[i], contents[i] };
       DataRow row = new DataRow(columns, rowValues);
       rowNum[i] = (i + 1);
       dt.insertRow(row);
     }
     return dt;
   }
 
   public void dealTag(DataTable dtArticle)
   {
     String searchUrl = getSearchURL();
     searchUrl = StringUtil.replaceEx(searchUrl, "Result.jsp", "Tag.jsp");
     for (int j = 0; j < dtArticle.getRowCount(); ++j) {
       String tag = dtArticle.getString(j, "Tag");
       if (StringUtil.isNotEmpty(tag)) {
         String[] tags = tag.split("\\s");
         tag = tags[0];
         ZCTagSchema tagSchema = SiteUtil.getTag(dtArticle.getLong(j, "SiteID"), tag);
         if (tagSchema == null) {
           continue;
         }
         String tagUrl = tagSchema.getLinkURL();
         if (StringUtil.isEmpty(tagUrl)) {
           tagUrl = searchUrl + "?site=" + dtArticle.getLong(j, "SiteID") + "&query=%00" + 
             StringUtil.urlEncode(tag, "UTF-8");
         }
         dtArticle.set(j, "FirstTag", tag);
         dtArticle.set(j, "FirstTagUrl", tagUrl);
       }
     }
   }
 
   public String getSearchURL() {
     String serviceUrl = Config.getValue("ServicesContext");
     String context = serviceUrl;
     if (serviceUrl.endsWith("/")) {
       context = serviceUrl.substring(0, serviceUrl.length() - 1);
     }
     int index = context.lastIndexOf('/');
     if (index != -1) {
       context = context.substring(0, index);
     }
     String searchUrl = context + "/Search/Result.jsp";
     return searchUrl;
   }
 
   public int getTotal(String type, long catlaogID) {
     int count = new QueryBuilder("select count(*) from zc" + type + " where catalogid=" + catlaogID + 
       " and status=30").executeInt();
     return count;
   }
 
   public DataRow getBlock() {
     return this.block;
   }
 
   public void setBlock(DataRow block) {
     this.block = block;
   }
 
   public int getLevel() {
     return this.level;
   }
 
   public void setLevel(int level) {
     this.level = level;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.template.CmsTemplateData
 * JD-Core Version:    0.5.4
 */