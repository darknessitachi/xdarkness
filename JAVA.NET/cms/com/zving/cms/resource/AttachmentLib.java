 package com.zving.cms.resource;
 
 import com.zving.cms.datachannel.Publisher;
 import com.zving.cms.dataservice.ColumnUtil;
 import com.zving.cms.pub.CMSCache;
 import com.zving.cms.pub.CatalogUtil;
 import com.zving.cms.pub.SiteUtil;
 import com.zving.cms.site.Catalog;
 import com.zving.cms.site.CatalogShowConfig;
 import com.zving.framework.Config;
 import com.zving.framework.Page;
 import com.zving.framework.RequestImpl;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.controls.TreeAction;
 import com.zving.framework.data.DataRow;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.ChineseSpelling;
 import com.zving.framework.utility.FileUtil;
 import com.zving.framework.utility.Filter;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.platform.Priv;
 import com.zving.platform.UserLog;
 import com.zving.platform.pub.NoUtil;
 import com.zving.platform.pub.OrderUtil;
 import com.zving.schema.ZCAttachmentRelaSchema;
 import com.zving.schema.ZCAttachmentRelaSet;
 import com.zving.schema.ZCAttachmentSchema;
 import com.zving.schema.ZCAttachmentSet;
 import com.zving.schema.ZCCatalogSchema;
 import com.zving.schema.ZCCatalogSet;
 import com.zving.schema.ZCSiteSchema;
 import java.io.File;
 import java.util.Date;
 
 public class AttachmentLib extends Page
 {
   public static Mapx initEditDialog(Mapx params)
   {
     long ID = Long.parseLong(params.get("ID").toString());
     ZCCatalogSchema catalog = new ZCCatalogSchema();
     catalog.setID(ID);
     catalog.fill();
     params = catalog.toMapx();
     return params;
   }
 
   public void getPicSrc() {
     String ID = $V("PicID");
     String id = $V("ID");
     DataTable dt = new QueryBuilder("select path,filename from zcimage where id=?", ID).executeDataTable();
     if (dt.getRowCount() > 0) {
       this.Response.put("picSrc", Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
         SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + dt.get(0, "path").toString() + "s_" + 
         dt.get(0, "filename").toString());
       this.Response.put("ImagePath", dt.get(0, "path").toString() + "1_" + dt.get(0, "filename").toString());
     }
     Transaction trans = new Transaction();
     ZCAttachmentSchema attach = new ZCAttachmentSchema();
     if (StringUtil.isNotEmpty(id)) {
       attach.setID(id);
       attach.fill();
       attach.setValue(this.Request);
       attach.setImagePath((String)this.Response.get("ImagePath"));
       trans.add(attach, 2);
       trans.commit();
     } else {
       return;
     }
   }
 
   public static void saveCustomColumn(Transaction trans, Mapx map, long catalogID, long articleID, boolean newFlag) {
     DataTable columnDT = ColumnUtil.getColumnValue("2", articleID);
     if (columnDT.getRowCount() > 0) {
       trans.add(
         new QueryBuilder("delete from zdcolumnvalue where relatype=? and relaid = ?", 
         "2", articleID));
     }
     trans.add(ColumnUtil.getValueFromRequest(catalogID, articleID, map), 1);
   }
 
   public void AttachmentLibEdit() {
     ZCCatalogSchema catalog = new ZCCatalogSchema();
     catalog.setValue(this.Request);
     catalog.fill();
 
     String name = $V("Name");
     if ((!name.equals(catalog.getName())) && (Catalog.checkNameExists(name, catalog.getParentID()))) {
       this.Response.setLogInfo(0, "分类名称" + name + "已经存在。");
       return;
     }
 
     catalog.setValue(this.Request);
     catalog.setAlias(StringUtil.getChineseFirstAlpha(catalog.getName()));
     if (catalog.update()) {
       CatalogUtil.update(catalog.getID());
       this.Response.setLogInfo(1, "修改附件分类成功！");
     } else {
       this.Response.setLogInfo(0, "修改附件分类失败！");
     }
   }
 
   public static void dg1DataBindBrowse(DataGridAction dga) {
     String CatalogID = (String)dga.getParams().get("CatalogID");
     String Name = (String)dga.getParams().get("Name");
     QueryBuilder qb = new QueryBuilder("select * from ZCAttachment ");
     if (("".equals(CatalogID)) || ("0".equals(CatalogID)))
       qb.append(" where SiteID=?", Application.getCurrentSiteID());
     else {
       qb.append(" where CatalogID=?", CatalogID);
     }
     if (StringUtil.isNotEmpty(Name)) {
       qb.append(" and Name like ?", "%" + Name.trim() + "%");
     }
 
     int pageSize = dga.getPageSize();
     int pageIndex = dga.getPageIndex();
     qb.append(" order by ID desc");
     DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
     dt.insertColumn("SuffixImage");
     dt.insertColumn("AttachmentLink");
 
     for (int i = 0; i < dt.getRowCount(); ++i) {
       String suffix = dt.get(i, "Suffix");
       String[] ext = { "jpg", "gif", "zip", "rar", "bmp", "png", "doc", "xls", "html", "js", "mov", "mp4", "flv", 
         "rm", "wmv", "swf", "txt", "mp3", "avi", "ppt", "pdf", "pptx", "xlsx", "docx" };
       for (int j = 0; j < ext.length; ++j) {
         if (ext[j].equalsIgnoreCase(suffix)) {
           dt.set(i, "SuffixImage", "<img src='../Framework/Images/FileType/" + ext[j] + 
             ".gif' width='16' height='16' title='" + suffix + "'/>");
           break;
         }
       }
       if (dt.get(i, "SuffixImage") == null) {
         dt.set(i, "SuffixImage", 
           "<img src='../Framework/Images/FileType/unknown.gif' width='16' height='16' title='" + suffix + 
           "'/>");
       }
 
       if ("N".equals(SiteUtil.getAttachDownFlag(Application.getCurrentSiteID())))
         dt.set(i, "AttachmentLink", 
           (Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
           Application.getCurrentSiteAlias() + "/" + dt.getString(i, "Path") + dt.getString(i, 
           "filename")).replaceAll("//", "/"));
       else {
         dt.set(i, "AttachmentLink", 
           (Config.getContextPath() + "/Services/AttachDownLoad.jsp?id=" + dt
           .getString(i, "ID")).replaceAll("//", "/"));
       }
     }
     dga.setTotal(qb);
     dga.bindData(dt);
   }
 
   public static void dg1DataBindFlashBrowse(DataGridAction dga)
   {
     String CatalogID = (String)dga.getParams().get("CatalogID");
     String Name = (String)dga.getParams().get("Name");
     QueryBuilder qb = new QueryBuilder("select * from ZCAttachment ");
     if (("".equals(CatalogID)) || ("0".equals(CatalogID)))
       qb.append(" where SiteID =?", Application.getCurrentSiteID());
     else {
       qb.append(" where CatalogID = ?", CatalogID);
     }
     if (StringUtil.isNotEmpty(Name)) {
       qb.append(" and Name like ?", "%" + Name.trim() + "%");
     }
     qb.append(" and Suffix='swf' order by ID desc");
     int pageSize = dga.getPageSize();
     int pageIndex = dga.getPageIndex();
     DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
     dt.insertColumn("SuffixImage");
     dt.insertColumn("AttachmentLink");
 
     for (int i = 0; i < dt.getRowCount(); ++i) {
       String suffix = dt.get(i, "Suffix");
       String[] ext = { "jpg", "gif", "zip", "rar", "bmp", "png", "doc", "xls", "html", "js", "mov", "mp4", "flv", 
         "rm", "wmv", "swf", "txt", "mp3", "avi", "ppt", "pdf", "pptx", "xlsx", "docx" };
       for (int j = 0; j < ext.length; ++j) {
         if (ext[j].equalsIgnoreCase(suffix)) {
           dt.set(i, "SuffixImage", "<img src='../Framework/Images/FileType/" + ext[j] + 
             ".gif' width='16' height='16' title='" + suffix + "'/>");
           break;
         }
       }
       if (dt.get(i, "SuffixImage") == null) {
         dt.set(i, "SuffixImage", 
           "<img src='../Framework/Images/FileType/unknown.gif' width='16' height='16' title='" + suffix + 
           "'/>");
       }
 
       if ("N".equals(SiteUtil.getAttachDownFlag(Application.getCurrentSiteID())))
         dt.set(i, "AttachmentLink", 
           (Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
           Application.getCurrentSiteAlias() + "/" + dt.getString(i, "Path") + dt.getString(i, 
           "filename")).replaceAll("//", "/"));
       else {
         dt.set(i, "AttachmentLink", 
           (Config.getContextPath() + "/Services/AttachDownLoad.jsp?id=" + dt
           .getString(i, "ID")).replaceAll("//", "/"));
       }
     }
     dga.setTotal(qb);
     dga.bindData(dt);
   }
 
   public static void treeDataBind(TreeAction ta) {
     long SiteID = Application.getCurrentSiteID();
     DataTable dt = null;
     Mapx params = ta.getParams();
     String parentLevel = (String)params.get("ParentLevel");
     String parentID = (String)params.get("ParentID");
 
     String IDs = ta.getParam("IDs");
     if (StringUtil.isEmpty(IDs)) {
       IDs = ta.getParam("Cookie.Resource.LastAttachLib");
     }
     String[] codes = Catalog.getSelectedCatalogList(IDs, CatalogShowConfig.getAttachLibShowLevel());
 
     if (ta.isLazyLoad()) {
       QueryBuilder qb = new QueryBuilder(
         "select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type = 7 and SiteID =? and TreeLevel>? and innerCode like ?");
 
       qb.add(SiteID);
       qb.add(parentLevel);
       qb.add(CatalogUtil.getInnerCode(parentID) + "%");
       if (!CatalogShowConfig.isAttachLibLoadAllChild()) {
         qb.append(" and TreeLevel<?", Integer.parseInt(parentLevel) + 3);
         ta.setExpand(false);
       } else {
         ta.setExpand(true);
       }
       qb.append(" order by orderflag,innercode");
       dt = qb.executeDataTable();
     } else {
       ta.setLevel(CatalogShowConfig.getAttachLibShowLevel());
       QueryBuilder qb = new QueryBuilder(
         "select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type =? and SiteID =? and TreeLevel-1 <=? order by orderflag,innercode");
       qb.add(7);
       qb.add(SiteID);
       qb.add(ta.getLevel());
       dt = qb.executeDataTable();
       Catalog.prepareSelectedCatalogData(dt, codes, 7, SiteID, ta.getLevel());
     }
     ta.setRootText("附件库");
     dt = dt.filter(new Filter() {
       public boolean filter(Object obj) {
         DataRow dr = (DataRow)obj;
         return Priv.getPriv(User.getUserName(), "attach", dr.getString("InnerCode"), "attach_browse");
       }
     });
     ta.bindData(dt);
     Catalog.addSelectedBranches(ta, codes);
   }
 
   public static void dg1DataBind(DataGridAction dga) {
     String CatalogID = dga.getParam("CatalogID");
     if (StringUtil.isEmpty(CatalogID)) {
       CatalogID = dga.getParams().getString("Cookie.Resource.LastAttachLib");
       if ((StringUtil.isEmpty(CatalogID)) || ("null".equals(CatalogID))) {
         CatalogID = "0";
       }
       dga.getParams().put("CatalogID", CatalogID);
     }
     String Name = dga.getParam("SearchName");
     String StartDate = dga.getParam("StartDate");
     String EndDate = dga.getParam("EndDate");
     QueryBuilder qb = new QueryBuilder("select * from ZCAttachment");
     qb.append(" where 1 = 1");
     if (StringUtil.isNotEmpty(CatalogID)) {
       qb.append(" and CatalogID = ?", CatalogID);
     } else {
       dga.setTotal(0);
       dga.bindData(new DataTable());
       return;
     }
     if (StringUtil.isNotEmpty(Name)) {
       qb.append(" and Name like ?", "%" + Name.trim() + "%");
     }
     if (StringUtil.isNotEmpty(StartDate)) {
       qb.append(" and addtime >= ? ", StartDate);
     }
     if (StringUtil.isNotEmpty(EndDate)) {
       qb.append(" and addtime <= ? ", EndDate);
     }
     dga.setTotal(qb);
     qb.append(" order by orderflag desc");
     int pageSize = dga.getPageSize();
     int pageIndex = dga.getPageIndex();
     DataTable dt = qb.executePagedDataTable(pageSize, pageIndex);
 
     dt.insertColumn("LockImage");
     dt.insertColumn("SuffixImage");
     dt.decodeDateColumn("AddTime");
     dt.insertColumn("AttachmentLink");
 
     for (int i = 0; i < dt.getRowCount(); ++i) {
       String suffix = dt.get(i, "Suffix");
       String[] ext = { "jpg", "gif", "zip", "rar", "bmp", "png", "doc", "xls", "html", "js", "mov", "mp4", "flv", 
         "rm", "wmv", "swf", "txt", "mp3", "avi", "ppt", "pdf", "pptx", "xlsx", "docx" };
       if ("Y".equals(dt.get(i, "IsLocked"))) {
         dt.set(i, "LockImage", "<img src='../Icons/icon048a1.gif' width='20' height='20'/>");
       }
       for (int j = 0; j < ext.length; ++j) {
         if (ext[j].equalsIgnoreCase(suffix)) {
           dt.set(i, "SuffixImage", "<img src='../Framework/Images/FileType/" + ext[j] + 
             ".gif' width='16' height='16' title='" + suffix + "'/>");
           break;
         }
       }
       if (dt.get(i, "SuffixImage") == null) {
         dt.set(i, "SuffixImage", 
           "<img src='../Framework/Images/FileType/unknown.gif' width='16' height='16' title='" + suffix + 
           "'/>");
       }
 
       if ("N".equals(SiteUtil.getAttachDownFlag(Application.getCurrentSiteID())))
         dt.set(i, "AttachmentLink", 
           (Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
           Application.getCurrentSiteAlias() + "/" + dt.getString(i, "Path") + dt.getString(i, 
           "filename")).replaceAll("//", "/"));
       else {
         dt.set(i, "AttachmentLink", 
           (Config.getContextPath() + "/Services/AttachDownLoad.jsp?id=" + dt
           .getString(i, "ID")).replaceAll("//", "/"));
       }
     }
     dga.bindData(dt);
   }
 
   public void edit() {
     ZCAttachmentSchema attach = new ZCAttachmentSchema();
     attach.setValue(this.Request);
     attach.fill();
     attach.setValue(this.Request);
     if (attach.update())
       this.Response.setLogInfo(1, "修改附件成功");
     else
       this.Response.setLogInfo(0, "修改音频失败");
   }
 
   public void dg1Edit()
   {
     DataTable dt = (DataTable)this.Request.get("DT");
     ZCAttachmentSet set = new ZCAttachmentSet();
     StringBuffer logs = new StringBuffer("编辑附件:");
     for (int i = 0; i < dt.getRowCount(); ++i) {
       ZCAttachmentSchema attach = new ZCAttachmentSchema();
       attach.setValue(dt.getDataRow(i));
       attach.setModifyTime(new Date());
       attach.setModifyUser(User.getUserName());
       set.add(attach);
       logs.append(attach.getName() + ",");
     }
     if (set.update()) {
       UserLog.log("Resource", "EditAttachment", logs + "成功", this.Request.getClientIP());
       this.Response.setLogInfo(1, "保存成功!");
     } else {
       UserLog.log("Resource", "EditAttachment", logs + "失败", this.Request.getClientIP());
       this.Response.setLogInfo(0, "保存失败!");
     }
   }
 
   public void add() {
     String name = $V("Name");
     String parentID = $V("ParentID");
     String IT = $V("IndexTemplate");
     String DT = $V("DetailTemplate");
     String LT = $V("ListTemplate");
     String imagePath = $V("ImagePath");
     Transaction trans = new Transaction();
     ZCCatalogSchema catalog = new ZCCatalogSchema();
     ZCCatalogSchema pCatalog = new ZCCatalogSchema();
 
     catalog.setID(NoUtil.getMaxID("CatalogID"));
     catalog.setSiteID(Application.getCurrentSiteID());
 
     if ((parentID.equals("0")) || (StringUtil.isEmpty(parentID))) {
       catalog.setParentID(0L);
       catalog.setTreeLevel(1L);
       ZCSiteSchema site = new ZCSiteSchema();
       site.setID(catalog.getSiteID());
       site.fill();
       site.setAttachmentLibCount(site.getAttachmentLibCount() + 1L);
       trans.add(site, 2);
     } else {
       catalog.setParentID(Long.parseLong(parentID));
 
       pCatalog.setID(catalog.getParentID());
       pCatalog.fill();
 
       catalog.setTreeLevel(pCatalog.getTreeLevel() + 1L);
 
       pCatalog.setChildCount(pCatalog.getChildCount() + 1L);
       trans.add(pCatalog, 2);
     }
 
     String innerCode = CatalogUtil.createCatalogInnerCode(pCatalog.getInnerCode());
     catalog.setInnerCode(innerCode);
 
     catalog.setName(name);
     catalog.setURL(" ");
 
     String alias = ChineseSpelling.getFirstAlpha(name).toLowerCase();
     String existsName = Catalog.checkAliasExists(alias, catalog.getParentID());
     if (StringUtil.isNotEmpty(existsName)) {
       alias = alias + NoUtil.getMaxID("AliasNo");
     }
     catalog.setAlias(alias);
     catalog.setType(7L);
     catalog.setIndexTemplate(IT);
     catalog.setListTemplate(LT);
     catalog.setListNameRule("");
     catalog.setDetailTemplate(DT);
     catalog.setDetailNameRule("");
     catalog.setChildCount(0L);
     catalog.setIsLeaf(1L);
     catalog.setTotal(0L);
     catalog.setOrderFlag(Catalog.getCatalogOrderFlag(parentID, catalog.getType()));
     catalog.setLogo("");
     catalog.setListPageSize(10L);
     catalog.setListPage(-1L);
     catalog.setPublishFlag("Y");
     catalog.setHitCount(0L);
     catalog.setMeta_Keywords("");
     catalog.setMeta_Description("");
     catalog.setOrderColumn("");
     catalog.setImagePath(imagePath);
     catalog.setAddUser(User.getUserName());
     catalog.setAddTime(new Date());
 
     trans.add(catalog, 1);
 
     Catalog.initCatalogConfig(catalog, trans);
 
     if (trans.commit()) {
       CatalogUtil.update(catalog.getID());
       UserLog.log("Resource", "AddTypeAttachment", "新建附件分类" + catalog.getName() + " 成功", 
         this.Request.getClientIP());
       this.Response.setLogInfo(1, "新建附件分类成功！");
     } else {
       UserLog.log("Resource", "AddTypeAttachment", "新建附件分类" + catalog.getName() + " 成功", 
         this.Request.getClientIP());
       this.Response.setLogInfo(0, "新建附件分类失败！");
     }
   }
 
   public void delLib() {
     String catalogID = $V("catalogID");
     ZCCatalogSchema catalog = new ZCCatalogSchema();
     catalog.setID(Long.parseLong(catalogID));
     if (!catalog.fill()) {
       this.Response.setLogInfo(0, "没有附件分类！");
       return;
     }
 
     if ("N".equals(catalog.getProp4())) {
       this.Response.setLogInfo(0, "不能删除该附件分类！");
       return;
     }
     QueryBuilder qb = new QueryBuilder("where InnerCode like ?", catalog.getInnerCode() + "%");
     ZCCatalogSet catalogSet = new ZCCatalogSchema().query(qb);
     Transaction trans = new Transaction();
     ZCAttachmentSet attachmentSet = new ZCAttachmentSchema().query(
       new QueryBuilder("where CatalogInnerCode like ?", catalog.getInnerCode() + "%"));
     for (int i = 0; i < attachmentSet.size(); ++i) {
       ZCAttachmentRelaSet AttachmentRelaSet = new ZCAttachmentRelaSchema().query(
         new QueryBuilder(" where id =?", 
         attachmentSet.get(i).getID()));
       trans.add(AttachmentRelaSet, 5);
     }
 
     File file = new File(Config.getContextRealPath() + Config.getValue("UploadDir") + "/" + 
       SiteUtil.getAlias(Application.getCurrentSiteID()) + "/upload/Attachment/" + 
       CatalogUtil.getPath(Long.parseLong(catalogID)));
     FileUtil.delete(file);
     trans.add(attachmentSet, 5);
     trans.add(catalogSet, 5);
     if (trans.commit()) {
       CMSCache.removeCatalogSet(catalogSet);
 
       this.Response.setLogInfo(1, "删除附件分类成功！");
 
       Publisher p = new Publisher();
       p.deleteFileTask(attachmentSet);
     } else {
       this.Response.setLogInfo(0, "删除附件分类失败！");
     }
   }
 
   public void publish() {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       this.Response.setStatus(0);
       this.Response.setMessage("操作数据库时发生错误!");
       return;
     }
     ZCAttachmentSchema attachment = new ZCAttachmentSchema();
     ZCAttachmentSet set = attachment.query(new QueryBuilder("where id in(" + ids + ")"));
 
     this.Response.setStatus(1);
     Publisher p = new Publisher();
     long id = p.publishSetTask("Attachment", set);
     $S("TaskID", id);
   }
 
   public void sortColumn() {
     String target = $V("Target");
     String orders = $V("Orders");
     String type = $V("Type");
     String catalogID = $V("CatalogID");
     if ((!StringUtil.checkID(target)) && (!StringUtil.checkID(orders))) {
       return;
     }
 
     if (StringUtil.isNotEmpty(catalogID)) {
       if (OrderUtil.updateOrder("ZCAttachment", type, target, orders, " CatalogID = " + catalogID))
         this.Response.setLogInfo(1, "排序成功");
       else {
         this.Response.setLogInfo(0, "排序失败");
       }
     }
     else if (OrderUtil.updateOrder("ZCAttachment", type, target, orders, " SiteID = " + 
       Application.getCurrentSiteID()))
       this.Response.setLogInfo(1, "排序成功");
     else
       this.Response.setLogInfo(0, "排序失败");
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.resource.AttachmentLib
 * JD-Core Version:    0.5.4
 */