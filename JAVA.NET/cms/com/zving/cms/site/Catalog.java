 package com.zving.cms.site;
 
 import com.zving.cms.datachannel.Publisher;
 import com.zving.cms.pub.CatalogUtil;
 import com.zving.cms.pub.PubFun;
 import com.zving.cms.pub.SiteUtil;
 import com.zving.cms.resource.AttachmentLib;
 import com.zving.cms.resource.AudioLib;
 import com.zving.cms.resource.ImageLib;
 import com.zving.cms.resource.VideoLib;
 import com.zving.cms.stat.impl.CatalogStat;
 import com.zving.framework.Config;
 import com.zving.framework.Page;
 import com.zving.framework.RequestImpl;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.controls.TreeAction;
 import com.zving.framework.controls.TreeItem;
 import com.zving.framework.data.DataCollection;
 import com.zving.framework.data.DataColumn;
 import com.zving.framework.data.DataRow;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.messages.LongTimeTask;
 import com.zving.framework.utility.ChineseSpelling;
 import com.zving.framework.utility.DateUtil;
 import com.zving.framework.utility.FileUtil;
 import com.zving.framework.utility.Filter;
 import com.zving.framework.utility.HtmlUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.platform.Priv;
 import com.zving.platform.UserLog;
 import com.zving.platform.pub.NoUtil;
 import com.zving.schema.ZCArticleSchema;
 import com.zving.schema.ZCAttachmentSchema;
 import com.zving.schema.ZCAudioSchema;
 import com.zving.schema.ZCCatalogConfigSchema;
 import com.zving.schema.ZCCatalogSchema;
 import com.zving.schema.ZCCatalogSet;
 import com.zving.schema.ZCImageSchema;
 import com.zving.schema.ZCMagazineIssueSchema;
 import com.zving.schema.ZCMagazineSchema;
 import com.zving.schema.ZCPageBlockItemSchema;
 import com.zving.schema.ZCPageBlockItemSet;
 import com.zving.schema.ZCPageBlockSchema;
 import com.zving.schema.ZCPageBlockSet;
 import com.zving.schema.ZCSiteSchema;
 import com.zving.schema.ZCVideoSchema;
 import com.zving.schema.ZDColumnRelaSchema;
 import com.zving.schema.ZDColumnRelaSet;
 import com.zving.schema.ZDColumnValueSchema;
 import com.zving.schema.ZDColumnValueSet;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.List;
 
 public class Catalog extends Page
 {
   public static final int CATALOG = 1;
   public static final int SUBJECT = 2;
   public static final int MAGAZINE = 3;
   public static final int IMAGELIB = 4;
   public static final int VIDEOLIB = 5;
   public static final int AUDIOLIB = 6;
   public static final int ATTACHMENTLIB = 7;
   public static final int NEWSPAPER = 8;
   public static final int SHOP_GOODS = 9;
   public static final int SHOP_GOODS_BRAND = 10;
   public static final int SHOP_GOODS_TYPE = 11;
   public static final Mapx CatalogTypeMap = new Mapx();
 
   static {
     CatalogTypeMap.put("1", "栏目");
     CatalogTypeMap.put("2", "专题");
     CatalogTypeMap.put("3", "杂志");
     CatalogTypeMap.put("4", "图片");
     CatalogTypeMap.put("5", "视频");
     CatalogTypeMap.put("6", "音频");
     CatalogTypeMap.put("7", "附件");
     CatalogTypeMap.put("9", "商品");
     CatalogTypeMap.put("10", "品牌");
     CatalogTypeMap.put("11", "商品其他分类");
   }
 
   public static Mapx init(Mapx params) {
     Object o1 = params.get("CatalogID");
     if (o1 == null) {
       o1 = params.get("Cookie.DocList.LastCatalog");
     }
     if (o1 != null) {
       long ID = Long.parseLong(o1.toString());
       ZCCatalogSchema catalog = new ZCCatalogSchema();
       catalog.setID(ID);
       if (catalog.fill()) {
         Mapx map = catalog.toMapx();
 
         String publishFlag = catalog.getPublishFlag();
         if (StringUtil.isEmpty(publishFlag)) {
           publishFlag = "Y";
         }
         map.put("PublishFlag", HtmlUtil.codeToRadios("PublishFlag", "YesOrNo", publishFlag));
 
         String allowContribute = catalog.getAllowContribute();
         if (StringUtil.isEmpty(allowContribute)) {
           allowContribute = "N";
         }
         map.put("AllowContribute", HtmlUtil.codeToRadios("AllowContribute", "YesOrNo", allowContribute));
 
         String singleFlag = catalog.getSingleFlag();
         if ((StringUtil.isEmpty(singleFlag)) || (singleFlag.equals("N")))
           map.put("SingleFlagN", "checked");
         else {
           map.put("SingleFlagY", "checked");
         }
         map.put("SingleFlag", HtmlUtil.codeToRadios("SingleFlag", "YesOrNo", singleFlag));
         DataTable workflowDT = new QueryBuilder("select name,ID from ZWWorkflow").executeDataTable();
         map.put("Workflow", HtmlUtil.dataTableToOptions(workflowDT, catalog.getWorkflow(), true));
 
         String keywordFlag = catalog.getKeywordFlag();
         if (StringUtil.isEmpty(keywordFlag)) {
           keywordFlag = "N";
         }
 
         if (StringUtil.isNotEmpty(catalog.getIndexTemplate())) {
           String s1 = "<a href='javascript:void(0);' onclick=\"openEditor('" + catalog.getIndexTemplate() + 
             "')\"><img src='../Platform/Images/icon_edit.gif'" + " width='14' height='14'></a>";
           map.put("edit", s1);
         }
         if (StringUtil.isNotEmpty(catalog.getListTemplate())) {
           String s1 = "<a href='javascript:void(0);' onclick=\"openEditor('" + catalog.getListTemplate() + 
             "')\"><img src='../Platform/Images/icon_edit.gif'" + " width='14' height='14'></a>";
           map.put("edit1", s1);
         }
         if (StringUtil.isNotEmpty(catalog.getDetailTemplate())) {
           String s1 = "<a href='javascript:void(0);' onclick=\"openEditor('" + catalog.getDetailTemplate() + 
             "')\"><img src='../Platform/Images/icon_edit.gif'" + " width='14' height='14'></a>";
           map.put("edit2", s1);
         }
         if (StringUtil.isNotEmpty(catalog.getRssTemplate())) {
           String s1 = "<a href='javascript:void(0);' onclick=\"openEditor('" + catalog.getRssTemplate() + 
             "')\"><img src='../Platform/Images/icon_edit.gif'" + " width='14' height='14'></a>";
           map.put("edit3", s1);
         }
 
         if (StringUtil.isEmpty(catalog.getDetailNameRule())) {
           map.put("DetailNameRule", "/${catalogpath}/${document.id}.shtml");
         }
 
         if (StringUtil.isEmpty(catalog.getListNameRule())) {
           map.put("ListNameRule", "/${catalogpath}/${year}/${month}/");
         }
 
         if ("Y".equals(catalog.getSingleFlag())) {
           map.put("IsDisplay", "none");
         }
 
         String imagePath = catalog.getImagePath();
         if (StringUtil.isNotEmpty(imagePath))
           imagePath = Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
             SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + catalog.getImagePath();
         else {
           imagePath = "../Images/addpicture.jpg";
         }
         map.put("ImageFullPath", imagePath);
         return map;
       }
     }
     return null;
   }
 
   public static Mapx initStructure(Mapx params) {
     String id = params.getString("ID");
     QueryBuilder qb = new QueryBuilder("select Name,ID,TreeLevel,ParentID from ZCCatalog ");
     if (StringUtil.isEmpty(id)) {
       qb.append(" where SiteID=?", Application.getCurrentSiteID());
     } else {
       String innerCode = CatalogUtil.getInnerCode(id);
       qb.append(" where InnerCode like ?", innerCode + "%");
     }
     qb.append(" and Type=?", params.get("Type"));
     DataTable dt = qb.executeDataTable();
     dt = DataGridAction.sortTreeDataTable(dt, "ID", "ParentID");
     PubFun.indentDataTable(dt);
     StringBuffer sb = new StringBuffer();
     for (int i = 0; i < dt.getRowCount(); ++i) {
       sb.append(dt.getString(i, "Name") + "\n");
     }
     String structure = sb.toString();
     structure = StringUtil.replaceEx(structure, "　", "  ");
     params.put("Structure", structure);
     return params;
   }
 
   public static Mapx initDialog(Mapx params) {
     params.put("SiteID", Application.getCurrentSiteID());
     params.put("PublishFlag", HtmlUtil.codeToRadios("PublishFlag", "YesOrNo", "Y"));
     params.put("SingleFlagN", "checked");
     params.put("AllowContribute", HtmlUtil.codeToRadios("AllowContribute", "YesOrNo", "N"));
     params.put("CatalogType", HtmlUtil.codeToOptions("CatalogType"));
     params.put("DetailNameRule", "/${catalogpath}/${document.id}.shtml");
     params.put("ListNameRule", "/${catalogpath}/${year}/${month}/");
     params.put("ListTemplate", "/template/list.html");
     params.put("DetailTemplate", "/template/detail.html");
     params.put("ListPage", "-1");
     DataTable workflowDT = new QueryBuilder("select name,ID from ZWWorkflow").executeDataTable();
     params.put("Workflow", HtmlUtil.dataTableToOptions(workflowDT, true));
     return params;
   }
 
   public void getPicSrc() {
     String ID = $V("PicID");
     QueryBuilder qb = new QueryBuilder("select path,filename from zcimage where id=?", ID);
     DataTable dt = qb.executeDataTable();
     if (dt.getRowCount() > 0) {
       this.Response.put("picSrc", dt.get(0, "path").toString() + "1_" + dt.get(0, "filename").toString());
       this.Response.put("ImagePath", dt.get(0, "path").toString() + "1_" + dt.get(0, "filename").toString());
     }
   }
 
   public static void treeDataBind(TreeAction ta) {
     Object obj = ta.getParams().get("SiteID");
     String siteID = Application.getCurrentSiteID();
     Object typeObj = ta.getParams().get("CatalogType");
     int catalogType = (typeObj != null) ? Integer.parseInt(typeObj.toString()) : 1;
     String parentTreeLevel = ta.getParams().getString("ParentLevel");
     String parentID = ta.getParams().getString("ParentID");
 
     String IDs = ta.getParam("IDs");
     if (StringUtil.isEmpty(IDs)) {
       IDs = ta.getParam("Cookie.DocList.LastCatalog");
     }
     String[] codes = getSelectedCatalogList(IDs, CatalogShowConfig.getArticleCatalogShowLevel());
 
     DataTable dt = null;
     if (ta.isLazyLoad()) {
       QueryBuilder qb = new QueryBuilder(
         "select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel>? and innerCode like ?");
 
       qb.add(catalogType);
       qb.add(siteID);
       qb.add(parentTreeLevel);
       qb.add(CatalogUtil.getInnerCode(parentID) + "%");
       if (!CatalogShowConfig.isArticleCatalogLoadAllChild()) {
         qb.append(" and TreeLevel<?", Integer.parseInt(parentTreeLevel) + 3);
         ta.setExpand(false);
       } else {
         ta.setExpand(true);
       }
       qb.append(" order by orderflag,innercode");
       dt = qb.executeDataTable();
     } else {
       ta.setLevel(CatalogShowConfig.getArticleCatalogShowLevel());
       QueryBuilder qb = new QueryBuilder(
         "select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel-1 <=? order by orderflag,innercode");
 
       qb.add(catalogType);
       qb.add(siteID);
       qb.add(ta.getLevel());
       dt = qb.executeDataTable();
       prepareSelectedCatalogData(dt, codes, catalogType, siteID, ta.getLevel());
     }
 
     String siteName = "文档库";
     if (catalogType == 9)
       siteName = "商品库";
     else if (catalogType == 10) {
       siteName = "商品品牌库";
     }
     dt = dt.filter(new Filter() {
       public boolean filter(Object obj) {
         DataRow dr = (DataRow)obj;
         return Priv.getPriv(User.getUserName(), "article", dr.getString("InnerCode"), "article_browse");
       }
     });
     String inputType = (String)ta.getParams().get("Type");
     if ("3".equals(inputType))
       ta.setRootText("<input type='radio' name=CatalogID id='_site' value='" + siteID + "'><label for='_site'>" + 
         siteName + "</label>");
     else if ("2".equals(inputType))
       ta.setRootText("<input type='CheckBox' name=CatalogID id='_site' value='" + siteID + 
         "' onclick='selectAll()'><label for='_site'>" + siteName + "</label>");
     else {
       ta.setRootText(siteName);
     }
     ta.bindData(dt);
     addSelectedBranches(ta, codes);
     List items = ta.getItemList();
     for (int i = 1; i < items.size(); ++i) {
       TreeItem item = (TreeItem)items.get(i);
       if ("Y".equals(item.getData().getString("SingleFlag")))
         item.setIcon("Icons/treeicon11.gif");
     }
   }
 
   public static String[] getSelectedCatalogList(String IDs, int level)
   {
     Mapx map = new Mapx();
     if (StringUtil.isNotEmpty(IDs)) {
       String[] arr = IDs.split("\\,");
       for (int i = 0; i < arr.length; ++i) {
         if (StringUtil.isNotEmpty(arr[i])) {
           String innerCode = CatalogUtil.getInnerCode(arr[i]);
           if ((!StringUtil.isNotEmpty(innerCode)) || 
             (innerCode.length() <= level * 6)) continue;
           innerCode = innerCode.substring(0, level * 6);
           map.put(innerCode, "1");
         }
       }
 
     }
 
     String[] codes = new String[map.size()];
     Object[] ks = map.keyArray();
     for (int i = 0; i < map.size(); ++i) {
       codes[i] = ks[i].toString();
     }
     return codes;
   }
 
   public static void prepareSelectedCatalogData(DataTable dt, String[] codes, int catalogType, String siteID, int level)
   {
     for (int i = 0; i < codes.length; ++i) {
       String innerCode = codes[i];
       QueryBuilder qb = new QueryBuilder(
         "select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type=? and SiteID=? and TreeLevel>? and InnerCode like ? order by orderflag,innercode");
 
       qb.add(catalogType);
       qb.add(siteID);
       qb.add(level + 1);
       qb.add(innerCode + "%");
       DataTable dt2 = qb.executeDataTable();
       dt.union(dt2);
     }
   }
 
   public static void addSelectedBranches(TreeAction ta, String[] codes)
   {
     List list = ta.getItemList();
     for (int i = 0; i < list.size(); ++i) {
       TreeItem item = (TreeItem)list.get(i);
       if (item.isRoot()) {
         continue;
       }
       for (int j = 0; j < codes.length; ++j)
         if (item.getData().getString("InnerCode").equals(codes[j].toString())) {
           item.setLazy(false);
           item.setExpanded(true);
           try {
             int level = ta.getLevel();
             ta.setLevel(1000);
             ta.addChild(item);
             ta.setLevel(level);
           } catch (Exception e) {
             e.printStackTrace();
           }
         }
     }
   }
 
   public static void treeDiagDataBind(TreeAction ta)
   {
     Object obj = ta.getParams().get("SiteID");
     String siteID = Application.getCurrentSiteID();
     Object typeObj = ta.getParams().get("CatalogType");
     int catalogType = (typeObj != null) ? Integer.parseInt(typeObj.toString()) : 1;
     String parentTreeLevel = ta.getParams().getString("ParentLevel");
     String parentID = ta.getParams().getString("ParentID");
     DataTable dt = null;
 
     String IDs = ta.getParam("IDs");
     String[] codes = getSelectedCatalogList(IDs, CatalogShowConfig.getArticleCatalogShowLevel());
 
     if (ta.isLazyLoad()) {
       QueryBuilder qb = new QueryBuilder(
         "select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type=? and SiteID=? and TreeLevel>? and innerCode like ?");
 
       qb.add(catalogType);
       qb.add(siteID);
       qb.add(parentTreeLevel);
       qb.add(CatalogUtil.getInnerCode(parentID) + "%");
       if (!CatalogShowConfig.isArticleCatalogLoadAllChild()) {
         qb.append(" and TreeLevel<?", Integer.parseInt(parentTreeLevel) + 3);
         ta.setExpand(false);
       } else {
         ta.setExpand(true);
       }
       qb.append(" order by orderflag,innercode");
       dt = qb.executeDataTable();
     } else {
       ta.setLevel(CatalogShowConfig.getArticleCatalogShowLevel());
       QueryBuilder qb = new QueryBuilder(
         "select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type=? and SiteID=? and TreeLevel-1<=? order by orderflag,innercode");
 
       qb.add(catalogType);
       qb.add(siteID);
       qb.add(ta.getLevel());
       dt = qb.executeDataTable();
       prepareSelectedCatalogData(dt, codes, catalogType, siteID, ta.getLevel());
     }
     dt = DataGridAction.sortTreeDataTable(dt, "ID", "ParentID");
 
     String siteName = "文档库";
     if (catalogType == 9) {
       siteName = "商品库";
     }
     if (catalogType == 10) {
       siteName = "商品品牌库";
     }
     String inputType = (String)ta.getParams().get("Type");
     if ("3".equals(inputType))
       ta.setRootText("<input type='radio' name=CatalogID id='_site' value='" + siteID + "'><label for='_site'>" + 
         siteName + "</label>");
     else if ("2".equals(inputType))
       ta.setRootText("<input type='CheckBox' name=CatalogID id='_site' value='" + siteID + 
         "' onclick='selectAll()'><label for='_site'>" + siteName + "</label>");
     else {
       ta.setRootText(siteName);
     }
     dt = dt.filter(new Filter() {
       public boolean filter(Object obj) {
         DataRow dr = (DataRow)obj;
 
         return Priv.getPriv(User.getUserName(), "article", dr.getString("InnerCode"), "article_browse");
       }
     });
     ta.bindData(dt);
     addSelectedBranches(ta, codes);
   }
 
   public static void treeResourceDataBind(TreeAction ta) {
     String siteID = ta.getParam("SiteID");
     siteID = Application.getCurrentSiteID();
     String catalogTypeStr = ta.getParam("CatalogType");
     if (catalogTypeStr == null) {
       catalogTypeStr = ta.getParams().getString("Cookie.ResourceCatalog.CatalogType");
     }
     int catalogType = (catalogTypeStr != null) ? Integer.parseInt(catalogTypeStr.toString()) : 4;
     if (4 == catalogType)
       ImageLib.treeDataBind(ta);
     else if (5 == catalogType)
       VideoLib.treeDataBind(ta);
     else if (6 == catalogType)
       AudioLib.treeDataBind(ta);
     else if (7 == catalogType)
       AttachmentLib.treeDataBind(ta);
   }
 
   public void dg1Edit()
   {
     DataTable dt = (DataTable)this.Request.get("DT");
     int count = dt.getRowCount();
     QueryBuilder qb = new QueryBuilder("update ZCCatalog set ");
     for (int i = 0; i < count; ++i) {
       qb.append(dt.get(i, "InnerCode") + "=?", dt.get(i, "Value"));
     }
     if (count != 0) {
       qb.append(",");
     }
     qb.append("modifyuser = ?", User.getUserName());
     qb.append(",modifytime = ?", new Date());
     qb.append(" where ID = ?", dt.get(0, "ID_key"));
     if (qb.executeNoQuery() != -1) {
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("发生错误!");
     }
   }
 
   public void add()
   {
     Transaction trans = new Transaction();
     ZCCatalogSchema catalog = add(this.Request, trans);
     if ((catalog != null) && (trans.commit()))
     {
       CatalogUtil.update(catalog.getID());
       UserLog.log("Catalog", "AddCataLog", "增加栏目:" + catalog.getName() + "成功！", this.Request
         .getClientIP());
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       UserLog.log("Catalog", "AddCataLog", "增加栏目:" + this.Request.getString("Name") + "失败！", 
         this.Request.getClientIP());
       this.Response.setMessage("添加栏目发生错误！" + this.Request.getString("ErrorMessage"));
     }
   }
 
   public ZCCatalogSchema add(DataCollection dc, Transaction trans)
   {
     String alias = dc.getString("Alias").trim();
     String name = dc.getString("Name").trim();
     int treeLevel = 1;
 
     ZCCatalogSchema pCatalog = new ZCCatalogSchema();
     ZCCatalogSchema catalog = new ZCCatalogSchema();
     String type = dc.getString("Type");
     if ((StringUtil.isEmpty(type)) || ("null".equals(type))) {
       type = "1";
     }
 
     catalog.setValue(dc);
     String parentID = dc.getString("ParentID");
 
     if ((parentID != null) && (!"0".equals(parentID)) && (!"null".equals(parentID))) {
       pCatalog.setID(Integer.parseInt(parentID));
       pCatalog.fill();
 
       treeLevel = (int)pCatalog.getTreeLevel() + 1;
       String existsName = checkAliasExists(alias, pCatalog.getID());
       if (StringUtil.isNotEmpty(existsName)) {
         dc.put("ErrorMessage", "栏目“" + existsName + "”已经使用了别名" + alias);
         return null;
       }
 
       if (checkNameExists(name, pCatalog.getID())) {
         dc.put("ErrorMessage", "栏目名已经被使用");
         return null;
       }
 
       catalog.setParentID(pCatalog.getID());
       catalog.setSiteID(pCatalog.getSiteID());
       catalog.setTreeLevel(treeLevel);
       pCatalog.setChildCount(pCatalog.getChildCount() + 1L);
       pCatalog.setIsLeaf(0L);
       trans.add(pCatalog, 2);
     } else {
       ZCSiteSchema site = new ZCSiteSchema();
       site.setID(Application.getCurrentSiteID());
       site.fill();
 
       catalog.setParentID(0L);
       catalog.setSiteID(site.getID());
 
       String existsName = checkAliasExists(alias, pCatalog.getID());
       if (StringUtil.isNotEmpty(existsName)) {
         dc.put("ErrorMessage", "栏目“" + existsName + "”已经使用了别名" + alias);
         return null;
       }
 
       if (checkNameExists(name, pCatalog.getID())) {
         dc.put("ErrorMessage", "栏目名已经被使用");
         return null;
       }
 
       catalog.setTreeLevel(treeLevel);
       parentID = "0";
 
       if ("1".equals(type))
         site.setChannelCount(site.getChannelCount() + 1L);
       else if ("2".equals(type))
         site.setSpecialCount(site.getSpecialCount() + 1L);
       else if ("3".equals(type)) {
         site.setMagzineCount(site.getMagzineCount() + 1L);
       }
       trans.add(site, 2);
     }
 
     String url = dc.getString("URL");
     if ((url == null) || ("".equals(url)) || ("http://".equals($V("URL"))) || ("https://".equals($V("URL")))) {
       if (url == null) {
         url = "";
       }
       if ((parentID != null) && (!"0".equals(parentID)) && (!"null".equals(parentID))) {
         url = url + CatalogUtil.getPath(catalog.getParentID());
       }
       url = url + dc.getString("Alias") + "/";
     }
     long catalogID = NoUtil.getMaxID("CatalogID");
     catalog.setID(catalogID);
 
     String innerCode = CatalogUtil.createCatalogInnerCode(pCatalog.getInnerCode());
     catalog.setInnerCode(innerCode);
     catalog.setBranchInnerCode(User.getBranchInnerCode());
     catalog.setURL(url);
     catalog.setType(type);
     catalog.setChildCount(0L);
     catalog.setIsLeaf(1L);
     catalog.setTotal(0L);
     catalog.setHitCount(0L);
 
     String orderFlag = getCatalogOrderFlag(parentID, type);
     catalog.setOrderFlag(Long.parseLong(orderFlag) + 1L);
 
     String listPageSize = dc.getString("ListPageSize");
     if (StringUtil.isEmpty(listPageSize))
       catalog.setListPageSize(20L);
     else {
       catalog.setListPageSize(Integer.parseInt(listPageSize));
     }
 
     String listPage = dc.getString("ListPage");
     if (StringUtil.isEmpty(listPage))
       catalog.setListPage("-1");
     else {
       catalog.setListPage(Integer.parseInt(listPage));
     }
 
     String publishFlag = dc.getString("PublishFlag");
     if ((publishFlag != null) && (!"".equals(publishFlag)))
       catalog.setPublishFlag(publishFlag);
     else {
       catalog.setPublishFlag("Y");
     }
 
     if ("Y".equals(dc.get("SingleFlag")))
       catalog.setSingleFlag("Y");
     else {
       catalog.setSingleFlag("N");
     }
 
     if ("Y".equals(dc.get("AllowContribute")))
       catalog.setAllowContribute("Y");
     else {
       catalog.setAllowContribute("N");
     }
 
     catalog.setAddUser(User.getUserName());
     catalog.setAddTime(new Date());
 
     trans.add(catalog, 1);
     trans.add(
       new QueryBuilder("update zccatalog set orderflag=orderflag+1 where orderflag>? and type=?", 
       orderFlag, type));
 
     initCatalogConfig(catalog, trans);
     return catalog;
   }
 
   public long add(ZCCatalogSchema parent, ZCCatalogSchema catalog, Transaction trans) {
     String type = catalog.getType();
     if ((StringUtil.isEmpty(type)) || ("null".equals(type))) {
       type = "1";
     }
 
     if (parent != null) {
       catalog.setParentID(parent.getID());
       catalog.setSiteID(parent.getSiteID());
       catalog.setTreeLevel(parent.getTreeLevel() + 1L);
       parent.setChildCount(parent.getChildCount() + 1L);
       parent.setIsLeaf(0L);
       trans.add((ZCCatalogSchema)parent.clone(), 2);
     } else {
       ZCSiteSchema site = new ZCSiteSchema();
       site.setID(Application.getCurrentSiteID());
       site.fill();
 
       catalog.setParentID(0L);
       catalog.setSiteID(site.getID());
       catalog.setTreeLevel(1L);
 
       if ("1".equals(type))
         site.setChannelCount(site.getChannelCount() + 1L);
       else if ("2".equals(type))
         site.setSpecialCount(site.getSpecialCount() + 1L);
       else if ("3".equals(type)) {
         site.setMagzineCount(site.getMagzineCount() + 1L);
       }
       trans.add(site, 2);
     }
 
     String orderFlag = getCatalogOrderFlag(parent.getID(), type);
     catalog.setOrderFlag(Long.parseLong(orderFlag) + 1L);
 
     long catalogID = NoUtil.getMaxID("CatalogID");
     catalog.setID(catalogID);
 
     String innerCode = CatalogUtil.createCatalogInnerCode(parent.getInnerCode());
     catalog.setInnerCode(innerCode);
     catalog.setBranchInnerCode(User.getBranchInnerCode());
     catalog.setAddUser(User.getUserName());
     catalog.setAddTime(new Date());
 
     trans.add(catalog, 1);
     initCatalogConfig(catalog, trans);
 
     return catalogID;
   }
 
   public static void initCatalogConfig(ZCCatalogSchema catalog, Transaction trans) {
     ZCCatalogConfigSchema config = new ZCCatalogConfigSchema();
     config.setID(NoUtil.getMaxID("CatalogConfigID"));
     config.setAddTime(new Date());
     if (User.getCurrent() != null)
       config.setAddUser(User.getUserName());
     else {
       config.setAddUser("admin");
     }
 
     config.setStartTime(new Date());
     config.setArchiveTime("12");
     config.setAttachDownFlag("Y");
     config.setSiteID(catalog.getSiteID());
     config.setCatalogID(catalog.getID());
     config.setCatalogInnerCode(catalog.getInnerCode());
     config.setIsUsing("N");
     config.setPlanType("Period");
     String CommentVerify = "";
     if (SiteUtil.getCommentAuditFlag(catalog.getSiteID()))
       CommentVerify = "Y";
     else {
       CommentVerify = "N";
     }
     config.setCommentVerify(CommentVerify);
     config.setCommentAnonymous("N");
     config.setAllowComment("Y");
     Calendar c = Calendar.getInstance();
     c.set(c.get(1), c.get(2), c.get(5), 22, 0, 0);
     StringBuffer sb = new StringBuffer();
     int minute = c.get(12);
     int hour = c.get(11);
     int day = c.get(5);
     sb.append(minute);
     sb.append(" ");
     sb.append(hour);
     sb.append(" ");
     sb.append(day);
     sb.append("-");
     sb.append(day - 1);
     sb.append("/1");
     sb.append(" * *");
     config.setCronExpression(sb.toString());
     trans.add(config, 1);
   }
 
   public void save() {
     if (!Priv.getPriv("article", $V("InnerCode"), "article_manage")) {
       this.Response.setLogInfo(0, "操作失败，你没有权限进行此操作！");
       return;
     }
 
     ZCCatalogSchema catalog = new ZCCatalogSchema();
     catalog.setID(Long.parseLong($V("ID")));
     catalog.fill();
 
     String name = $V("Name");
     if ((!name.equals(catalog.getName())) && (checkNameExists(name, catalog.getParentID()))) {
       this.Response.setLogInfo(0, "栏目名称" + name + "已经存在。");
       UserLog.log("Catalog", "UpdateCataLog", "修改栏目:" + catalog.getName() + "失败！", this.Request
         .getClientIP());
       return;
     }
 
     String alias = $V("Alias");
     String existsName = checkAliasExists(alias, catalog.getParentID());
     if ((!alias.equals(catalog.getAlias())) && (StringUtil.isNotEmpty(existsName))) {
       UserLog.log("Catalog", "UpdateCataLog", "修改栏目:" + catalog.getName() + "失败！", this.Request
         .getClientIP());
       this.Response.setLogInfo(0, "栏目“" + existsName + "”已使用了别名" + alias);
       return;
     }
 
     String oldWorkflow = catalog.getWorkflow();
     catalog.setValue(this.Request);
     catalog.setModifyUser(User.getUserName());
     catalog.setModifyTime(new Date());
 
     if (StringUtil.isEmpty($V("ListPageSize"))) {
       catalog.setListPageSize(-1L);
     }
 
     Transaction trans = new Transaction();
     trans.add(catalog, 2);
 
     String extend = $V("Extend");
     if ((!"1".equals(extend)) && 
       ("2".equals(extend))) {
       String innerCode = catalog.getInnerCode();
       QueryBuilder qb = new QueryBuilder(
         "update zccatalog set IndexTemplate=?,ListTemplate=?,DetailTemplate=?,rssTemplate=? where innercode like ? and TreeLevel>?");
 
       qb.add($V("IndexTemplate"));
       qb.add($V("ListTemplate"));
       qb.add($V("DetailTemplate"));
       qb.add($V("RssTemplate"));
       qb.add(innerCode + "%");
       qb.add(catalog.getTreeLevel());
 
       trans.add(qb);
     }
 
     String wfExtend = $V("WorkFlowExtend");
     if ("1".equals(wfExtend))
     {
       DataTable tempDT = new QueryBuilder("select distinct catalogid from zcarticle a where a.status=10 and a.catalogid= ?", 
         catalog.getID()).executeDataTable();
       if ((tempDT.getRowCount() > 0) && 
         (StringUtil.isNotEmpty(oldWorkflow)) && (!oldWorkflow.equalsIgnoreCase(catalog.getWorkflow()))) {
         this.Response.setLogInfo(0, "本栏目下还存在‘流转中’的文章,不能更改工作流!");
         return;
       }
     }
     else if ("2".equals(wfExtend))
     {
       DataTable tempDT = new QueryBuilder("select distinct catalogid from zcarticle a where a.status=10 and a.cataloginnercode like ?", 
         catalog.getInnerCode() + "%")
         .executeDataTable();
       if (tempDT.getRowCount() > 0) {
         String tempStr = "";
         for (int i = 0; i < tempDT.getRowCount(); ++i) {
           if (!CatalogUtil.getWorkflow(tempDT.getString(i, 0)).equalsIgnoreCase(catalog.getWorkflow())) {
             tempStr = tempStr + CatalogUtil.getName(tempDT.getString(i, 0)) + " ";
           }
         }
         if (StringUtil.isNotEmpty(tempStr)) {
           this.Response.setLogInfo(0, tempStr + "栏目下还存在‘流转中’的文章,不能更改工作流!");
           return;
         }
       }
       trans.add(
         new QueryBuilder("update zccatalog set workflow =? where innercode like ?", 
         catalog.getWorkflow(), catalog.getInnerCode() + "%"));
     }
 
     if (trans.commit()) {
       CatalogUtil.update(catalog.getID());
       this.Response.setLogInfo(1, "保存成功!");
       UserLog.log("Catalog", "UpdateCataLog", "修改栏目:" + catalog.getName() + "成功！", this.Request
         .getClientIP());
     } else {
       UserLog.log("Catalog", "UpdateCataLog", "修改栏目:" + catalog.getName() + "失败！", this.Request
         .getClientIP());
       this.Response.setLogInfo(0, "保存失败!");
     }
   }
 
   public void saveTemplate() {
     Transaction trans = new Transaction();
     ZCCatalogSchema catalog = new ZCCatalogSchema();
     catalog.setID(Long.parseLong($V("ID")));
     catalog.fill();
     catalog.setValue(this.Request);
     catalog.setModifyUser(User.getUserName());
     catalog.setModifyTime(new Date());
 
     trans.add(catalog, 2);
 
     if (trans.commit())
       this.Response.setLogInfo(1, "保存模板成功");
     else
       this.Response.setLogInfo(0, "保存模板失败");
   }
 
   public static void importTreeDataBind(TreeAction ta)
   {
     Object obj = ta.getParams().get("ParentID");
     long parentID = (obj != null) ? Long.parseLong(obj.toString()) : 0L;
 
     String type = (ta.getParams().get("Type") == null) ? null : ta.getParams().get("Type").toString();
     String rootText = null;
 
     if (parentID == 0L) {
       rootText = "文档库";
       if ((type != null) && (!"".equals(type)) && (!"null".equals(type)))
         rootText = CatalogTypeMap.getString(type) + "库";
     }
     else {
       rootText = new QueryBuilder("select Name from ZCCatalog where ID=?", String.valueOf(parentID))
         .executeString();
       rootText = (rootText == null) ? "" : rootText;
     }
     ta.setRootText(rootText);
     DataTable dt = getTreeDataTable(ta.getParam("BatchColumn"));
     ta.bindData(dt);
   }
 
   private static DataTable getTreeDataTable(String text) {
     String[] catalogs = text.split("\n");
     DataTable dt = new DataTable();
     DataColumn ID = new DataColumn("ID", 3);
     DataColumn ParentID = new DataColumn("ParentID", 3);
     DataColumn Level = new DataColumn("TreeLevel", 8);
     DataColumn Name = new DataColumn("Name", 1);
     dt.insertColumn(ID);
     dt.insertColumn(ParentID);
     dt.insertColumn(Level);
     dt.insertColumn(Name);
     for (int i = 0; i < catalogs.length; ++i) {
       String catalog = catalogs[i];
 
       if (StringUtil.isEmpty(catalog.trim())) {
         continue;
       }
       catalog = StringUtil.toDBC(catalog);
       catalog = StringUtil.rightTrim(catalog);
       int length = 0;
       for (int k = 0; k < catalog.length(); ++k) {
         if (catalog.charAt(k) != ' ') {
           length = k;
           break;
         }
       }
       int currentLevel = length / 2;
       Object[] catalogRow = { new Long(i + 1), new Long(0L), new Integer(currentLevel), catalog.trim() };
       dt.insertRow(catalogRow);
     }
     for (int i = 0; i < dt.getRowCount(); ++i) {
       int level = dt.getInt(i, "TreeLevel");
       int id = dt.getInt(i, "ID");
       for (int j = i + 1; j < dt.getRowCount(); ++j) {
         if (dt.getInt(j, "TreeLevel") <= level) {
           break;
         }
         if (dt.getInt(j, "TreeLevel") == level + 1) {
           dt.set(j, "ParentID", id);
         }
       }
     }
     return dt;
   }
 
   public void importCatalog()
   {
     long parentID = Long.parseLong($V("ParentID"));
     String text = $V("BatchColumn");
     String type = $V("Type");
     if ((StringUtil.isEmpty(type)) || ("null".equals(type))) {
       type = "1";
     }
     Transaction trans = new Transaction();
     DataTable dt = getTreeDataTable(text);
     Mapx catalogMap = new Mapx();
     ZCSiteSchema site = new ZCSiteSchema();
     site.setID(Application.getCurrentSiteID());
     site.fill();
     if (parentID != 0L) {
       ZCCatalogSchema catalog = new ZCCatalogSchema();
       catalog.setID(parentID);
       catalog.fill();
       catalogMap.put("0", catalog);
       trans.add(catalog, 2);
     } else {
       trans.add(site, 2);
     }
     String orderFlag = getCatalogOrderFlag(parentID, type);
     if (StringUtil.isEmpty(orderFlag)) {
       orderFlag = "0";
     }
 
     for (int i = 0; i < dt.getRowCount(); ++i) {
       ZCCatalogSchema catalog = new ZCCatalogSchema();
       String pid = dt.getString(i, "ParentID");
       ZCCatalogSchema pCatalog = (ZCCatalogSchema)catalogMap.get(pid);
       if (pCatalog == null) {
         pCatalog = new ZCCatalogSchema();
       }
       String catalogName = dt.getString(i, "Name");
 
       if (checkNameExists(catalogName, pCatalog.getID())) {
         continue;
       }
       String alias = ChineseSpelling.getFirstAlpha(catalogName).toLowerCase();
       String existsName = checkAliasExists(alias, pCatalog.getID());
       if (StringUtil.isNotEmpty(existsName)) {
         alias = alias + NoUtil.getMaxID("AliasNo");
       }
       String innerCode = null;
       catalog.setParentID(pCatalog.getID());
       catalog.setSiteID(site.getID());
       catalog.setTreeLevel(pCatalog.getTreeLevel() + 1L);
       pCatalog.setChildCount(pCatalog.getChildCount() + 1L);
       pCatalog.setIsLeaf(0L);
       innerCode = pCatalog.getInnerCode();
       if (parentID == 0L) {
         if ("1".equals(type))
           site.setChannelCount(site.getChannelCount() + 1L);
         else if ("2".equals(type))
           site.setSpecialCount(site.getSpecialCount() + 1L);
         else if ("3".equals(type))
           site.setMagzineCount(site.getMagzineCount() + 1L);
         else if ("4".equals(type)) {
           site.setImageLibCount(site.getMagzineCount() + 1L);
         }
       }
       innerCode = CatalogUtil.createCatalogInnerCode(innerCode);
       catalog.setInnerCode(innerCode);
 
       catalog.setID(NoUtil.getMaxID("CatalogID"));
       catalog.setName(catalogName);
       catalog.setAlias(alias);
       String url = pCatalog.getURL();
       if (StringUtil.isEmpty(url)) {
         url = alias + "/";
       } else {
         if (!url.endsWith("/")) {
           url = url + "/";
         }
         url = url + alias + "/";
       }
       catalog.setURL(url);
       if ((StringUtil.isNotEmpty(type)) && (!"null".equals(type)))
         catalog.setType(Integer.parseInt(type));
       else {
         catalog.setType(1L);
       }
 
       catalog.setListTemplate($V("ListTemplate"));
       catalog.setDetailTemplate($V("DetailTemplate"));
       catalog.setChildCount(0L);
       catalog.setIsLeaf(1L);
       catalog.setTotal(0L);
       catalog.setLogo($V("Logo"));
       catalog.setListPageSize(20L);
       catalog.setPublishFlag("Y");
       catalog.setOrderFlag(orderFlag);
       catalog.setSingleFlag("N");
       catalog.setKeywordFlag("N");
       catalog.setAllowContribute("N");
       catalog.setHitCount(0L);
       catalog.setMeta_Keywords($V("Meta_Keywords"));
       catalog.setMeta_Description($V("Meta_Description"));
       catalog.setOrderColumn($V("OrderColumn"));
       catalog.setKeywordSetting("NO");
       catalog.setListPage("-1");
       catalog.setListPageSize(20L);
       catalog.setProp1($V("Prop1"));
       catalog.setProp2($V("Prop2"));
       catalog.setProp3($V("Prop3"));
       catalog.setProp4($V("Prop4"));
       catalog.setAddUser(User.getUserName());
       catalog.setAddTime(new Date());
 
       catalogMap.put(dt.getString(i, "ID"), catalog);
       trans.add(catalog, 1);
       initCatalogConfig(catalog, trans);
     }
     if (trans.commit()) {
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("插入数据发生错误!");
     }
   }
 
   public void move()
   {
     ZCCatalogSchema catalog = new ZCCatalogSchema();
     long catalogID = Long.parseLong($V("CatalogID"));
     long parentID = Long.parseLong($V("ParentID"));
     catalog.setID(catalogID);
     catalog.fill();
     Transaction tran = new Transaction();
 
     long TreeLevel = 0L;
     String parentInnerCode = "";
     if (parentID == 0L) {
       TreeLevel = 1L;
       if (catalog.getTreeLevel() == 1L) {
         this.Response.setStatus(0);
         this.Response.setMessage("选择的栏目和目标栏目处于同一级，不能转移。");
         return;
       }
 
       ZCSiteSchema site = new ZCSiteSchema();
       site.setID(Application.getCurrentSiteID());
       site.fill();
 
       long type = catalog.getType();
       if (type == 1L)
         site.setChannelCount(site.getChannelCount() + 1L);
       else if (type == 2L)
         site.setSpecialCount(site.getSpecialCount() + 1L);
       else if (type == 3L) {
         site.setMagzineCount(site.getMagzineCount() + 1L);
       }
       tran.add(site, 2);
     } else {
       ZCCatalogSchema pCatalog = new ZCCatalogSchema();
       pCatalog.setID(parentID);
       pCatalog.fill();
 
       parentInnerCode = pCatalog.getInnerCode();
       TreeLevel = pCatalog.getTreeLevel() + 1L;
 
       if ((catalog.getTreeLevel() == TreeLevel) && (catalog.getParentID() == parentID)) {
         this.Response.setStatus(0);
         this.Response.setMessage("选择的栏目和目标栏目处于同一级，不能转移。");
         return;
       }
 
       pCatalog.setChildCount(pCatalog.getChildCount() + 1L);
       pCatalog.setIsLeaf(0L);
       tran.add(pCatalog, 2);
     }
 
     long oldParentID = catalog.getParentID();
     if (oldParentID == 0L) {
       ZCSiteSchema site = new ZCSiteSchema();
       site.setID(Application.getCurrentSiteID());
       site.fill();
 
       long type = catalog.getType();
       if (type == 1L)
         site.setChannelCount(site.getChannelCount() - 1L);
       else if (type == 2L)
         site.setSpecialCount(site.getSpecialCount() - 1L);
       else if (type == 3L) {
         site.setMagzineCount(site.getMagzineCount() - 1L);
       }
       tran.add(site, 2);
     } else {
       tran.add(new QueryBuilder("update zccatalog set childcount=childcount-1 where id=?", oldParentID));
       tran.add(new QueryBuilder("update zccatalog set isLeaf=1 where childcount=0 and id=?", oldParentID));
     }
 
     String orderflag = getCatalogOrderFlag(parentID, catalog.getType());
     String innerCode = (TreeLevel == 1L) ? CatalogUtil.createCatalogInnerCode("") : 
       CatalogUtil.createCatalogInnerCode(parentInnerCode);
 
     CatalogStat.updateInnerCode(tran, catalog.getSiteID(), catalog.getInnerCode(), innerCode);
 
     catalog.setInnerCode(innerCode);
     catalog.setTreeLevel(TreeLevel);
 
     catalog.setParentID(parentID);
     catalog.setOrderFlag(getCatalogOrderFlag(parentID, catalog.getType()));
     catalog.setModifyUser(User.getUserName());
     catalog.setModifyTime(new Date());
 
     tran.add(catalog, 2);
     tran.add(new QueryBuilder("update zccatalog set orderflag = " + orderflag + 1 + " where id =?", catalogID));
     tran.add(new QueryBuilder("update zcarticle set CatalogInnerCode=? where catalogid=?", innerCode, catalogID));
     tran.add(new QueryBuilder("update bzcarticle set CatalogInnerCode=? where catalogid=?", innerCode, catalogID));
 
     Mapx map = new Mapx();
     if (catalog.getChildCount() > 0L) {
       ZCCatalogSet childCatalogSet = new ZCCatalogSchema().query(new QueryBuilder("where parentid=?", catalogID));
       for (int i = 0; i < childCatalogSet.size(); ++i) {
         Mapx childMap = moveChild(childCatalogSet.get(i), TreeLevel, innerCode, orderflag + 2 + i);
         map.putAll(childMap);
       }
     }
 
     Object[] ks = map.keyArray();
     for (int i = 0; i < map.size(); ++i) {
       String sql = ks[i].toString();
       tran.add(new QueryBuilder(sql));
     }
 
     String count = new QueryBuilder("select count(*) from zccatalog where innercode like '" + 
       CatalogUtil.getInnerCode(catalogID) + "%' and type=" + catalog.getType()).executeString();
     tran.add(
       new QueryBuilder("update zccatalog set orderflag=" + orderflag + count + " where orderflag > " + 
       orderflag + " and type=" + catalog.getType() + " and innercode not like '" + 
       CatalogUtil.getInnerCode(catalogID) + "%'"));
 
     if (tran.commit()) {
       CatalogUtil.update(catalogID);
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("保存数据发生错误!");
     }
   }
 
   public Mapx moveChild(ZCCatalogSchema catalog, long TreeLevel, String parentInnerCode, String orderflag)
   {
     Mapx map = new Mapx();
     long catalogID = catalog.getID();
 
     TreeLevel += 1L;
 
     String innerCode = CatalogUtil.createCatalogInnerCode(parentInnerCode);
 
     map.put("update zccatalog set TreeLevel=" + TreeLevel + ",innercode='" + innerCode + "',modifyuser='" + 
       User.getUserName() + "',modifytime='" + DateUtil.getCurrentDate() + "',OrderFlag=" + orderflag + 
       " where id=" + catalogID, new Integer(7));
 
     map.put("update zcarticle set CatalogInnerCode='" + innerCode + "' where catalogid=" + catalogID, 
       new Integer(7));
     map.put("update bzcarticle set CatalogInnerCode='" + innerCode + "' where catalogid=" + catalogID, 
       new Integer(7));
 
     if (catalog.getChildCount() > 0L) {
       ZCCatalogSet childCatalogSet = new ZCCatalogSchema().query(new QueryBuilder("where parentid=?", catalogID));
       for (int i = 0; i < childCatalogSet.size(); ++i) {
         Mapx childMap = moveChild(childCatalogSet.get(i), TreeLevel, innerCode, orderflag + 1 + i);
         map.putAll(childMap);
       }
     }
 
     return map;
   }
 
   public void publish()
   {
     if ("0".equals($V("type"))) {
       long id = publishAllTask(Application.getCurrentSiteID());
       this.Response.setStatus(1);
       $S("TaskID", id);
     } else {
       long catalogID = Long.parseLong($V("CatalogID"));
       boolean childFlag = "Y".equals($V("ChildFlag"));
       boolean detailFlag = "Y".equals($V("DetailFlag"));
       long id = publishTask(catalogID, childFlag, detailFlag);
       this.Response.setStatus(1);
       $S("TaskID", id);
     }
   }
 
   private long publishTask(long catalogID, boolean child, boolean detail) {
     LongTimeTask ltt = new LongTimeTask(catalogID, child, detail) { private final long val$catalogID;
       private final boolean val$child;
       private final boolean val$detail;
 
       public void execute() { Publisher p = new Publisher();
         p.publishCatalog(this.val$catalogID, this.val$child, this.val$detail, this);
         setPercent(100); }
 
     };
     ltt.setUser(User.getCurrent());
     ltt.start();
     return ltt.getTaskID();
   }
 
   public long publishAllTask(long siteID) {
     LongTimeTask ltt = new LongTimeTask(siteID) { private final long val$siteID;
 
       public void execute() { Publisher p = new Publisher();
         p.publishAll(this.val$siteID, this);
         setPercent(100); }
 
     };
     ltt.setUser(User.getCurrent());
     ltt.start();
     return ltt.getTaskID();
   }
 
   public void del()
   {
     Transaction trans = new Transaction();
     String ID = $V("CatalogID");
     String name = CatalogUtil.getName(ID);
     deleteCatalog(trans, Long.parseLong(ID));
     if (trans.commit()) {
       CatalogUtil.update(ID);
       UserLog.log("Site", "DelCataLog", "删除栏目成功:" + name, this.Request.getClientIP());
 
       this.Response.setStatus(1);
     } else {
       UserLog.log("Site", "DelCataLog", "删除栏目失败:" + name, this.Request.getClientIP());
       this.Response.setStatus(0);
       this.Response.setMessage("删除栏目失败");
     }
   }
 
   public void batchDel() {
     String IDs = $V("IDs");
     if ((StringUtil.isEmpty(IDs)) || (IDs.equals("0"))) {
       this.Response.setMessage("不能删除文档库根节点!");
       return;
     }
     String[] arr = IDs.split(",");
     Mapx map = new Mapx();
     for (int i = 0; i < arr.length; ++i) {
       String id = arr[i];
       if ((StringUtil.isNotEmpty(id)) && (!id.equals("0"))) {
         String innerCode = CatalogUtil.getInnerCode(id);
         map.put(innerCode, id);
       }
     }
     Object[] ks = map.keyArray();
     for (int i = 0; i < ks.length; ++i) {
       String innerCode = ks[i].toString();
       while (innerCode.length() > 6) {
         innerCode = innerCode.substring(0, innerCode.length() - 6);
         if (map.containsKey(innerCode)) {
           map.remove(ks[i]);
           break;
         }
       }
     }
     Transaction trans = new Transaction();
     Object[] catalogs = map.valueArray();
     for (int i = 0; i < catalogs.length; ++i) {
       deleteCatalog(trans, Long.parseLong(String.valueOf(catalogs[i])));
     }
     if (trans.commit()) {
       for (int i = 0; i < catalogs.length; ++i) {
         String name = CatalogUtil.getName(String.valueOf(catalogs[i]));
         UserLog.log("Site", "DelCataLog", "删除栏目成功:" + name, this.Request.getClientIP());
         CatalogUtil.update(String.valueOf(catalogs[i]));
       }
       this.Response.setStatus(1);
       this.Response.setMessage("批量删除栏目成功");
     } else {
       UserLog.log("Site", "DelCataLog", "批量删除栏目失败", this.Request.getClientIP());
       this.Response.setStatus(0);
       this.Response.setMessage("批量删除栏目失败");
     }
   }
 
   public void clean()
   {
     Transaction trans = new Transaction();
     String ID = $V("CatalogID");
     trans
       .add(new QueryBuilder(
       "delete from zcarticlelog where exists(select articleid from zcarticle where id=zcarticlelog.articleid and catalogid=?)", 
       ID));
     trans.add(new QueryBuilder("delete from zcarticle where catalogid=?", ID));
 
     trans.add(
       new QueryBuilder("delete from zdcolumnvalue where relatype=? and exists(select id from zcarticle where catalogid=? and id=zdcolumnvalue.relaid)", 
       "2", ID));
 
     trans.add(
       new QueryBuilder("delete from zcimagerela where RelaType=? and exists(select id from zcarticle where catalogid=? and id=zcimagerela.RelaID)", 
       "ArticleImage", ID));
 
     trans.add(
       new QueryBuilder("delete from ZCAttachmentRela where RelaType=? and exists(select id from zcarticle where catalogid=? and id=ZCAttachmentRela.RelaID)", 
       "ArticleAttach", ID));
 
     trans.add(
       new QueryBuilder("delete from ZCVideoRela where RelaType=? and exists(select id from zcarticle where catalogid=? and id=ZCVideoRela.RelaID)", 
       "ArticleVideo", ID));
 
     trans.add(
       new QueryBuilder("delete from ZCAudioRela where exists(select id from zcarticle where catalogid=? and id=ZCAudioRela.RelaID)", 
       ID));
 
     trans.add(
       new QueryBuilder("delete from ZCVoteItem where exists(select id from zcarticle where catalogid=? and id=ZCVoteItem.VoteDocID)", 
       ID));
 
     trans.add(
       new QueryBuilder("delete from ZCComment where exists(select id from zcarticle where catalogid=? and id=ZCComment.RelaID)", 
       ID));
 
     if (trans.commit()) {
       Publisher p = new Publisher();
       FileUtil.delete(CatalogUtil.getAbsolutePath(Long.parseLong(ID)));
       p.publishCatalog(Long.parseLong(ID), false, true);
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("清空栏目失败");
     }
   }
 
   public static void deleteCatalog(Transaction trans, long ID) {
     ZCCatalogSchema catalog = new ZCCatalogSchema();
     catalog.setID(ID);
     catalog.fill();
 
     ZCCatalogSchema pCatalog = new ZCCatalogSchema();
     pCatalog.setID(catalog.getParentID());
     pCatalog.fill();
     pCatalog.setChildCount(pCatalog.getChildCount() - 1L);
     if (pCatalog.getChildCount() == 0L)
       pCatalog.setIsLeaf(1L);
     else {
       pCatalog.setIsLeaf(0L);
     }
     trans.add(pCatalog, 2);
 
     ZCCatalogSet catalogSet = catalog.query(
       new QueryBuilder(" where InnerCode like ?", catalog.getInnerCode() + 
       "%"));
     trans.add(catalogSet, 5);
     trans.add(new ZCArticleSchema().query(
       new QueryBuilder(" where CatalogInnerCode like ?", catalog.getInnerCode() + 
       "%")), 5);
     trans.add(new ZCImageSchema().query(
       new QueryBuilder(" where CatalogInnerCode like ?", catalog.getInnerCode() + 
       "%")), 5);
     trans.add(new ZCVideoSchema().query(
       new QueryBuilder(" where CatalogInnerCode like ?", catalog.getInnerCode() + 
       "%")), 5);
     trans.add(new ZCAudioSchema().query(
       new QueryBuilder(" where CatalogInnerCode like ?", catalog.getInnerCode() + 
       "%")), 5);
     trans.add(new ZCAttachmentSchema().query(
       new QueryBuilder(" where CatalogInnerCode like ?", catalog
       .getInnerCode() + 
       "%")), 5);
     trans.add(new ZCCatalogConfigSchema().query(
       new QueryBuilder(" where siteID=? and CatalogInnerCode like ?", 
       catalog.getSiteID(), catalog.getInnerCode() + "%")), 5);
 
     String ids = "";
     for (int i = 0; i < catalogSet.size(); ++i) {
       ids = ids + catalogSet.get(i).getID();
       if (i != catalogSet.size() - 1) {
         ids = ids + ",";
       }
 
       FileUtil.delete(CatalogUtil.getAbsolutePath(catalogSet.get(i).getID()));
     }
     ZCPageBlockSet blockSet = new ZCPageBlockSchema().query(new QueryBuilder(" where catalogid in (" + ids + ")"));
     for (int i = 0; i < blockSet.size(); ++i) {
       ZCPageBlockItemSet itemSet = new ZCPageBlockItemSchema().query(
         new QueryBuilder(" where blockID=?", 
         blockSet.get(i).getID()));
       trans.add(itemSet, 5);
     }
     trans.add(blockSet, 5);
 
     String idsStr = "'" + ids.replaceAll(",", "','") + "'";
 
     ZDColumnRelaSet columnRelaSet = new ZDColumnRelaSchema().query(
       new QueryBuilder(" where RelaType=? and RelaID in(" + idsStr + ")", "1"));
     trans.add(columnRelaSet, 5);
 
     ZDColumnValueSet columnValueSet1 = new ZDColumnValueSchema().query(
       new QueryBuilder(" where RelaType=? and RelaID in(" + idsStr + ")", "1"));
     trans.add(columnValueSet1, 5);
 
     String wherepart = " where exists (select ID from zcarticle where cataloginnercode like '" + 
       catalog.getInnerCode() + "%' and ID=zdcolumnvalue.relaID )";
     if (Config.isDB2()) {
       wherepart = " where exists (select ID from zcarticle where cataloginnercode like '" + 
         catalog.getInnerCode() + "%' and char(ID)=zdcolumnvalue.relaID )";
     }
     ZDColumnValueSet columnValueSet2 = new ZDColumnValueSchema().query(new QueryBuilder(wherepart));
     trans.add(columnValueSet2, 5);
 
     if (catalog.getType() != 3L)
       return;
     DataTable delids = new QueryBuilder("select * from ZCMagazineIssue where ID not in (" + ids + 
       ") order by Year desc,PeriodNum desc").executeDataTable();
     if ((delids != null) && (delids.getRowCount() > 0)) {
       String issueid = delids.getString(0, "ID");
 
       String[] idsarry = ids.split(",");
       boolean curentyearflag = false;
       ZCMagazineIssueSchema issue = new ZCMagazineIssueSchema();
       issue.setID(idsarry[0]);
       issue.fill();
 
       ZCMagazineSchema magazine = new ZCMagazineSchema();
       magazine.setID(issue.getMagazineID());
       magazine.fill();
 
       String currentyear = magazine.getCurrentYear();
       String periodnum = magazine.getCurrentPeriodNum();
       String coverimage = magazine.getCoverImage();
       if (magazine.fill()) {
         ZCMagazineIssueSchema issues = new ZCMagazineIssueSchema();
         issues.setID(issueid);
         issues.fill();
 
         if ((currentyear.equalsIgnoreCase(issues.getYear())) && 
           (periodnum.equalsIgnoreCase(issues.getPeriodNum()))) {
           curentyearflag = true;
         } else {
           currentyear = issues.getYear();
           periodnum = issues.getPeriodNum();
           coverimage = issues.getCoverImage();
         }
 
         if (curentyearflag) {
           magazine.setTotal(magazine.getTotal() - idsarry.length);
           magazine.setModifyTime(new Date());
           magazine.setModifyUser(User.getUserName());
           trans.add(magazine, 2);
         } else {
           magazine.setTotal(magazine.getTotal() - idsarry.length);
           magazine.setCurrentYear(currentyear);
           magazine.setCurrentPeriodNum(periodnum);
           magazine.setCoverImage(coverimage);
           magazine.setModifyTime(new Date());
           magazine.setModifyUser(User.getUserName());
           trans.add(magazine, 2);
         }
       }
       trans.add(new ZCMagazineSchema().query(new QueryBuilder("where ID in (" + ids + ")")), 
         5);
       trans.add(new ZCMagazineIssueSchema().query(new QueryBuilder("where ID in (" + ids + ")")), 
         5);
     }
   }
 
   public static void createDefaultCatalog(String siteID, int type)
   {
     createDefaultCatalog(Long.parseLong(siteID), type);
   }
 
   public static void createDefaultCatalog(long siteID, int type) {
     DataTable dt = new QueryBuilder("select id from zccatalog where siteid =?  and type=?", siteID, type)
       .executePagedDataTable(1, 0);
     if ((dt != null) && (dt.getRowCount() > 0)) {
       return;
     }
     Transaction trans = new Transaction();
 
     ZCCatalogSchema defaultCatalog = new ZCCatalogSchema();
     defaultCatalog.setID(NoUtil.getMaxID("CatalogID"));
     defaultCatalog.setSiteID(siteID);
     defaultCatalog.setParentID(0L);
     defaultCatalog.setInnerCode(CatalogUtil.createCatalogInnerCode(""));
     defaultCatalog.setTreeLevel(1L);
     String Name = "";
     switch (type)
     {
     case 4:
       Name = "默认图片";
       break;
     case 5:
       Name = "默认视频";
       break;
     case 6:
       Name = "默认音频";
       break;
     case 7:
       Name = "默认附件";
       break;
     default:
       Name = "默认图片";
     }
 
     defaultCatalog.setName(Name);
     defaultCatalog.setURL("");
     defaultCatalog.setAlias(StringUtil.getChineseFirstAlpha(Name));
     defaultCatalog.setType(type);
     defaultCatalog.setListTemplate("");
     defaultCatalog.setListNameRule("/${catalogpath}/${year}/${month}/");
     defaultCatalog.setDetailTemplate("");
     defaultCatalog.setDetailNameRule("");
     defaultCatalog.setChildCount(0L);
     defaultCatalog.setIsLeaf(1L);
     defaultCatalog.setTotal(0L);
     defaultCatalog.setOrderFlag(getCatalogOrderFlag(0L, type));
     defaultCatalog.setLogo("");
     defaultCatalog.setListPageSize(20L);
     defaultCatalog.setListPage(-1L);
     defaultCatalog.setPublishFlag("Y");
     defaultCatalog.setHitCount(0L);
     defaultCatalog.setMeta_Keywords("");
     defaultCatalog.setMeta_Description("");
     defaultCatalog.setOrderColumn("");
     defaultCatalog.setProp4("Y");
     defaultCatalog.setAddUser(User.getUserName());
     defaultCatalog.setAddTime(new Date());
     trans.add(defaultCatalog, 1);
     trans.commit();
   }
 
   public void sortCatalog()
   {
     String CatalogType = this.Request.get("CatalogType").toString();
     int Move = Integer.parseInt(this.Request.get("Move").toString());
     String CatalogID = this.Request.get("CatalogID").toString();
     if (sortCatalog(CatalogID, Move, CatalogType))
       this.Response.setLogInfo(1, "排序成功！");
     else
       this.Response.setLogInfo(0, "排序失败！");
   }
 
   public static boolean sortCatalog(String CatalogID, int Move, String CatalogType)
   {
     ZCCatalogSchema catalog = new ZCCatalogSchema();
     catalog.setID(CatalogID);
     if (!catalog.fill()) {
       return true;
     }
 
     String TreeLevel = catalog.getTreeLevel();
 
     DataTable allDT = new QueryBuilder("select * from zccatalog where siteID = ? and Type =? order by orderflag,innercode", 
       Application.getCurrentSiteID(), CatalogType).executeDataTable();
 
     int selfSize = new QueryBuilder(
       "select count(1) from zccatalog where siteID = " + Application.getCurrentSiteID() + 
       " and innercode like '" + catalog.getInnerCode() + "%' and Type = ?", CatalogType)
       .executeInt();
     List catalogList = new ArrayList();
     if (Move > 0) {
       int count = 0;
       int index = 0;
       boolean isMove = false;
       for (int i = allDT.getRowCount() - 1; i >= 0; --i) {
         if ((isMove) && 
           (allDT.getString(i, "TreeLevel").equals(TreeLevel)) && ((
           (TreeLevel.equals("1")) || (allDT.getString(i, "InnerCode").startsWith(
           catalog.getInnerCode().substring(0, catalog.getInnerCode().length() - 6)))))) {
           ++count;
         }
         if (allDT.getString(i, "ID").equals(CatalogID)) {
           index = i;
           isMove = true; } else {
           if (allDT.getString(i, "InnerCode").startsWith(catalog.getInnerCode())) {
             continue;
           }
           catalogList.add(allDT.getDataRow(i));
         }
 
         if ((count != Move) && 
           (((count == 0) || (i - 1 < 0) || (count >= Move) || 
           (Integer.parseInt(allDT.getString(i - 1, 
           "TreeLevel")) >= Integer.parseInt(TreeLevel)))) && ((
           (count == 0) || (count >= Move) || (i != 0)))) continue;
         for (int j = index + selfSize - 1; j >= index; --j) {
           catalogList.add(allDT.getDataRow(j));
         }
         Move = -1;
       }
 
       if (count > 0) {
         Transaction trans = new Transaction();
         for (int i = 0; i < catalogList.size(); ++i) {
           DataRow dr = (DataRow)catalogList.get(i);
           trans.add(
             new QueryBuilder("update zccatalog set orderflag = ? where ID = ?", catalogList.size() - 
             i, dr.getString("ID")));
         }
         return trans.commit();
       }
     } else {
       Move = -Move;
       int count = 0;
       int index = 0;
       boolean isMove = false;
       for (int i = 0; i < allDT.getRowCount(); ++i) {
         if ((isMove) && 
           (allDT.getString(i, "TreeLevel").equals(TreeLevel)) && ((
           (TreeLevel.equals("1")) || (allDT.getString(i, "InnerCode").startsWith(
           catalog.getInnerCode().substring(0, catalog.getInnerCode().length() - 6)))))) {
           ++count;
         }
         if (allDT.getString(i, "ID").equals(CatalogID)) {
           index = i;
           i = i - 1 + selfSize;
           isMove = true;
         } else {
           catalogList.add(allDT.getDataRow(i));
         }
 
         if ((count != Move) && 
           (((count == 0) || (i + 1 >= allDT.getRowCount()) || (count >= Move) || 
           (Integer.parseInt(allDT
           .getString(i + 1, "TreeLevel")) >= Integer.parseInt(TreeLevel)))) && ((
           (count == 0) || (count >= Move) || (i != allDT.getRowCount() - 1)))) continue;
         for (int j = index; j < index + selfSize; ++j) {
           catalogList.add(allDT.getDataRow(j));
         }
         Move = -1;
       }
 
       if (count > 0) {
         Transaction trans = new Transaction();
         for (int i = 0; i < catalogList.size(); ++i) {
           DataRow dr = (DataRow)catalogList.get(i);
           trans.add(
             new QueryBuilder("update zccatalog set orderflag = ? where ID = ?", i + 1, dr
             .getString("ID")));
         }
         return trans.commit();
       }
     }
     return true;
   }
 
   public static String getCatalogOrderFlag(long ParentID, long CatalogType) {
     return getCatalogOrderFlag(ParentID, CatalogType);
   }
 
   public static String getCatalogOrderFlag(String ParentID, long CatalogType) {
     return getCatalogOrderFlag(ParentID, CatalogType);
   }
 
   public static String getCatalogOrderFlag(long ParentID, String CatalogType) {
     return getCatalogOrderFlag(ParentID, CatalogType);
   }
 
   public static String checkAliasExists(String alias, long parentID)
   {
     return checkAliasExists(alias, Application.getCurrentSiteID(), parentID);
   }
 
   public static String checkAliasExists(String alias, long siteID, long parentID) {
     QueryBuilder qb = new QueryBuilder("select name from zccatalog where alias=? and parentID=? and siteID=?", 
       alias, parentID);
     qb.add(siteID);
     return qb.executeString();
   }
 
   public static boolean checkNameExists(String Name, long parentID)
   {
     int count = new QueryBuilder("select count(*) from zccatalog where name=? and parentID=? and siteID=" + 
       Application.getCurrentSiteID(), Name, parentID).executeInt();
     return count > 0;
   }
 
   public static String getCatalogOrderFlag(String ParentID, String CatalogType) {
     DataTable parentDT = null;
     if ((StringUtil.isEmpty(ParentID)) || ("0".equals(ParentID))) {
       parentDT = new QueryBuilder("select * from zccatalog where siteID =? and type =? and TreeLevel = 1 order by orderflag,innercode", 
         Application.getCurrentSiteID(), CatalogType).executeDataTable();
     } else {
       String innercode = CatalogUtil.getInnerCode(ParentID);
       parentDT = new QueryBuilder("select * from zccatalog where siteID =? and type =? and innercode like '" + 
         innercode + "%' order by orderflag,innercode", Application.getCurrentSiteID(), CatalogType)
         .executeDataTable();
     }
     if ((parentDT != null) && (parentDT.getRowCount() > 0)) {
       return parentDT.getString(parentDT.getRowCount() - 1, "OrderFlag");
     }
     return "0";
   }
 
   public static void main(String[] args)
   {
     ZCCatalogSet set = new ZCCatalogSchema().query(new QueryBuilder("where siteid=14 order by id"));
     for (int k = 1; k < 30; ++k)
       for (int i = 0; i < set.size(); ++i) {
         ZCCatalogSchema schema = (ZCCatalogSchema)set.get(i).clone();
         if (schema.getTreeLevel() == 1L)
           schema.setInnerCode(StringUtil.leftPad(String.valueOf(Integer.parseInt(schema.getInnerCode()
             .substring(0, 4)) + 
             set.size() * k / 3), '0', 4));
         else if (schema.getTreeLevel() == 2L)
           schema.setInnerCode(StringUtil.leftPad(String.valueOf(Integer.parseInt(schema.getInnerCode()
             .substring(0, 4)) + 
             set.size() * k / 3), '0', 4) + 
             "0001");
         else if (schema.getTreeLevel() == 3L) {
           schema.setInnerCode(StringUtil.leftPad(String.valueOf(Integer.parseInt(schema.getInnerCode()
             .substring(0, 4)) + 
             set.size() * k / 3), '0', 4) + 
             "00010001");
         }
 
         schema.setID(schema.getID() + set.size() * k);
         if (schema.getParentID() != 0L) {
           schema.setParentID(schema.getParentID() + set.size() * k);
         }
         schema.insert();
       }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.site.Catalog
 * JD-Core Version:    0.5.4
 */