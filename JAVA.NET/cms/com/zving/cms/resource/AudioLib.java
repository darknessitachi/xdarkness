 package com.zving.cms.resource;
 
 import com.zving.cms.datachannel.Publisher;
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
 import com.zving.framework.utility.NumberUtil;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.platform.Priv;
 import com.zving.platform.UserLog;
 import com.zving.platform.pub.NoUtil;
 import com.zving.platform.pub.OrderUtil;
 import com.zving.schema.ZCAudioRelaSchema;
 import com.zving.schema.ZCAudioRelaSet;
 import com.zving.schema.ZCAudioSchema;
 import com.zving.schema.ZCAudioSet;
 import com.zving.schema.ZCCatalogSchema;
 import com.zving.schema.ZCCatalogSet;
 import com.zving.schema.ZCSiteSchema;
 import java.io.File;
 import java.util.Date;
 
 public class AudioLib extends Page
 {
   public static Mapx initDialog(Mapx params)
   {
     String ID = params.getString("ID");
     String imagePath = "upload/Image/nocover.jpg";
     if (StringUtil.isEmpty(ID)) {
       params.put("ImagePath", imagePath);
       params.put("PicSrc", Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
         SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + imagePath);
       return params;
     }
     ZCCatalogSchema catalog = new ZCCatalogSchema();
     catalog.setID(ID);
     catalog.fill();
     params = catalog.toMapx();
     imagePath = catalog.getImagePath();
     params.put("PicSrc", Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
       SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + imagePath);
     return params;
   }
 
   public void getPicSrc() {
     String ID = $V("PicID");
     DataTable dt = new QueryBuilder("select path,filename from zcimage where id=?", ID).executeDataTable();
     if (dt.getRowCount() > 0) {
       this.Response.put("picSrc", Config.getContextPath() + Config.getValue("UploadDir") + "/" + 
         SiteUtil.getAlias(Application.getCurrentSiteID()) + "/" + dt.get(0, "path").toString() + "s_" + 
         dt.get(0, "filename").toString());
       this.Response.put("ImagePath", dt.get(0, "path").toString() + "1_" + dt.get(0, "filename").toString());
     }
   }
 
   public void AudioLibEdit() {
     ZCCatalogSchema catalog = new ZCCatalogSchema();
     catalog.setValue(this.Request);
     catalog.fill();
     catalog.setValue(this.Request);
     catalog.setAlias(StringUtil.getChineseFirstAlpha(catalog.getName()));
     if (catalog.update()) {
       CatalogUtil.update(catalog.getID());
       this.Response.setLogInfo(1, "修改音频分类成功！");
     } else {
       this.Response.setLogInfo(0, "修改音频分类失败！");
     }
   }
 
   public static void dg1DataBindBrowse(DataGridAction dga) {
     String Search = (String)dga.getParams().get("Search");
     if ((Search == null) || ("".equals(Search))) {
       dga.bindData(new DataTable());
       return;
     }
     String CatalogID = (String)dga.getParams().get("_CatalogID");
     String Name = (String)dga.getParams().get("Name");
     String StartDate = dga.getParam("StartDate");
     String EndDate = dga.getParam("EndDate");
     String Info = (String)dga.getParams().get("Info");
     QueryBuilder qb = new QueryBuilder("select * from ZCAudio ");
     if (StringUtil.isEmpty(CatalogID))
       qb.append(" where SiteID =?", Application.getCurrentSiteID());
     else {
       qb.append(" where CatalogID =?", CatalogID);
     }
     if (StringUtil.isNotEmpty(Name)) {
       qb.append(" and Name like ?", "%" + Name.trim() + "%");
     }
     if (StringUtil.isNotEmpty(StartDate)) {
       qb.append(" and addtime >= ? ", StartDate);
     }
     if (StringUtil.isNotEmpty(EndDate)) {
       qb.append(" and addtime <= ? ", EndDate);
       qb.add(EndDate);
     }
     if (StringUtil.isNotEmpty(Info)) {
       qb.append(" and Info like ?", "%" + Info.trim() + "%");
     }
     qb.append(" order by ID desc");
     dga.setTotal(qb);
     DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
     dt.insertColumn("Size");
     for (int i = 0; i < dt.getRowCount(); ++i) {
       dt.set(i, "Size", NumberUtil.round(dt.getLong(i, "FileSize") * 1.0D / 1024.0D / 1024.0D, 2));
     }
 
     dga.bindData(qb);
   }
 
   public static void treeDataBind(TreeAction ta) {
     long SiteID = Application.getCurrentSiteID();
     DataTable dt = null;
     Mapx params = ta.getParams();
     String parentLevel = (String)params.get("ParentLevel");
     String parentID = (String)params.get("ParentID");
 
     String IDs = ta.getParam("IDs");
     if (StringUtil.isEmpty(IDs)) {
       IDs = ta.getParam("Cookie.Resource.LastAudioLib");
     }
     String[] codes = Catalog.getSelectedCatalogList(IDs, CatalogShowConfig.getAudioLibShowLevel());
 
     if (ta.isLazyLoad()) {
       QueryBuilder qb = new QueryBuilder(
         "select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type =? and SiteID =? and TreeLevel>? and innerCode like ?");
       qb.add(6);
       qb.add(SiteID);
       qb.add(parentLevel);
       qb.add(CatalogUtil.getInnerCode(parentID) + "%");
       if (!CatalogShowConfig.isAudioLibLoadAllChild()) {
         qb.append(" and TreeLevel<?", Integer.parseInt(parentLevel) + 3);
         ta.setExpand(false);
       } else {
         ta.setExpand(true);
       }
       qb.append(" order by orderflag,innercode");
       dt = qb.executeDataTable();
     } else {
       ta.setLevel(CatalogShowConfig.getAudioLibShowLevel());
       QueryBuilder qb = new QueryBuilder(
         "select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type =? and SiteID =? and TreeLevel-1 <=? order by orderflag,innercode");
       qb.add(6);
       qb.add(SiteID);
       qb.add(ta.getLevel());
       dt = qb.executeDataTable();
       Catalog.prepareSelectedCatalogData(dt, codes, 6, SiteID, ta.getLevel());
     }
     ta.setRootText("音频库");
     dt = dt.filter(new Filter() {
       public boolean filter(Object obj) {
         DataRow dr = (DataRow)obj;
         return Priv.getPriv(User.getUserName(), "audio", dr.getString("InnerCode"), "audio_browse");
       }
     });
     ta.bindData(dt);
     Catalog.addSelectedBranches(ta, codes);
   }
 
   public static void dg1DataBind(DataGridAction dga) {
     String Alias = Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(Application.getCurrentSiteID()) + "/";
     String CatalogID = dga.getParam("CatalogID");
     if (StringUtil.isEmpty(CatalogID)) {
       CatalogID = dga.getParams().getString("Cookie.Resource.LastAudioLib");
       if ((StringUtil.isEmpty(CatalogID)) || ("null".equals(CatalogID))) {
         CatalogID = "0";
       }
       dga.getParams().put("CatalogID", CatalogID);
     }
     String Name = dga.getParam("Name");
     QueryBuilder qb = new QueryBuilder();
     StringBuffer conditions = new StringBuffer();
     conditions.append(" where 1 = 1");
     if (StringUtil.isNotEmpty(CatalogID)) {
       conditions.append(" and CatalogID = ?");
       qb.add(CatalogID);
     } else {
       dga.setTotal(0);
       dga.bindData(new DataTable());
       return;
     }
     if (StringUtil.isNotEmpty(Name)) {
       conditions.append(" and Name like ?");
       qb.add("%" + Name.trim() + "%");
     }
     qb.setSQL("select * from ZCAudio" + conditions);
     dga.setTotal(qb);
     qb.setSQL("select ZCAudio.*,'" + Alias + "' as Alias from ZCAudio" + conditions + 
       " order by orderflag desc,id desc");
     DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
     dt.decodeDateColumn("AddTime");
     dga.bindData(dt);
   }
 
   public void edit() {
     ZCAudioSchema Audio = new ZCAudioSchema();
     Audio.setValue(this.Request);
     Audio.fill();
     Audio.setValue(this.Request);
     if (Audio.update())
     {
       this.Response.setLogInfo(1, "修改音频成功");
     }
     else this.Response.setLogInfo(0, "修改音频失败");
   }
 
   public void dg1Edit()
   {
     DataTable dt = (DataTable)this.Request.get("DT");
     ZCAudioSet set = new ZCAudioSet();
     StringBuffer logs = new StringBuffer("编辑音频:");
     for (int i = 0; i < dt.getRowCount(); ++i) {
       ZCAudioSchema Audio = new ZCAudioSchema();
       Audio.setValue(dt.getDataRow(i));
       Audio.setModifyTime(new Date());
       Audio.setModifyUser(User.getUserName());
       set.add(Audio);
       logs.append(Audio.getName() + ",");
     }
     if (set.update()) {
       UserLog.log("Resource", "EditAudio", logs + "成功", this.Request.getClientIP());
       this.Response.setLogInfo(1, "保存成功!");
     } else {
       UserLog.log("Resource", "EditAudio", logs + "失败", this.Request.getClientIP());
       this.Response.setLogInfo(0, "保存失败!");
     }
   }
 
   public void add() {
     String name = $V("Name");
     String parentID = $V("ParentID");
     String IT = $V("IndexTemplate");
     String DT = $V("DetailTemplate");
     String LT = $V("ListTemplate");
     Transaction trans = new Transaction();
     ZCCatalogSchema catalog = new ZCCatalogSchema();
     ZCCatalogSchema pCatalog = new ZCCatalogSchema();
 
     String imagePath = $V("ImagePath");
     catalog.setID(NoUtil.getMaxID("CatalogID"));
     catalog.setSiteID(Application.getCurrentSiteID());
 
     if ((parentID.equals("0")) || (StringUtil.isEmpty(parentID))) {
       catalog.setParentID(0L);
       catalog.setTreeLevel(1L);
       ZCSiteSchema site = new ZCSiteSchema();
       site.setID(catalog.getSiteID());
       site.fill();
       site.setImageLibCount(site.getImageLibCount() + 1L);
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
     catalog.setType(6L);
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
     catalog.setAddUser(User.getUserName());
     catalog.setAddTime(new Date());
     catalog.setImagePath(imagePath);
     trans.add(catalog, 1);
 
     Catalog.initCatalogConfig(catalog, trans);
 
     if (trans.commit()) {
       CatalogUtil.update(catalog.getID());
       UserLog.log("Resource", "AddTypeAudio", "新建音频分类:" + catalog.getName() + " 成功", this.Request
         .getClientIP());
       this.Response.setLogInfo(1, "新建音频分类成功!");
     } else {
       UserLog.log("Resource", "AddTypeAudio", "新建音频分类:" + catalog.getName() + " 失败", this.Request
         .getClientIP());
       this.Response.setLogInfo(0, "新建音频分类失败!");
     }
   }
 
   public void delLib() {
     String catalogID = $V("catalogID");
     ZCCatalogSchema catalog = new ZCCatalogSchema();
     catalog.setID(Long.parseLong(catalogID));
     if (!catalog.fill()) {
       this.Response.setLogInfo(0, "没有音频分类！");
       return;
     }
 
     if ("N".equals(catalog.getProp4())) {
       this.Response.setLogInfo(0, "不能删除该音频分类！");
       return;
     }
 
     ZCCatalogSet catalogSet = new ZCCatalogSchema().query(
       new QueryBuilder("where InnerCode like ?", catalog
       .getInnerCode() + 
       "%"));
     Transaction trans = new Transaction();
     ZCAudioSet audio = new ZCAudioSchema().query(
       new QueryBuilder("where CatalogInnerCode like ?", catalog
       .getInnerCode() + 
       "%"));
     for (int i = 0; i < audio.size(); ++i) {
       FileUtil.delete(Config.getContextRealPath() + audio.get(i).getPath() + audio.get(i).getFileName());
       ZCAudioRelaSet AudioRelaSet = new ZCAudioRelaSchema().query(
         new QueryBuilder(" where id = ?", audio.get(i)
         .getID()));
       trans.add(AudioRelaSet, 5);
     }
 
     File file = new File(Config.getContextRealPath() + Config.getValue("UploadDir") + "/" + 
       SiteUtil.getAlias(Application.getCurrentSiteID()) + "/upload/Audio/" + 
       CatalogUtil.getPath(Long.parseLong(catalogID)));
     FileUtil.delete(file);
 
     trans.add(audio, 5);
     trans.add(catalogSet, 5);
     if (trans.commit()) {
       CMSCache.removeCatalogSet(catalogSet);
       this.Response.setLogInfo(1, "删除音频分类成功！");
 
       Publisher p = new Publisher();
       p.deleteFileTask(audio);
     } else {
       this.Response.setLogInfo(0, "删除音频分类失败！");
     }
   }
 
   public void publish() {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       this.Response.setStatus(0);
       this.Response.setMessage("操作数据库时发生错误!");
       return;
     }
     ZCAudioSchema Audio = new ZCAudioSchema();
     ZCAudioSet set = Audio.query(new QueryBuilder("where id in(" + ids + ")"));
 
     this.Response.setStatus(1);
 
     Publisher p = new Publisher();
     long id = p.publishSetTask("Audio", set);
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
       if (OrderUtil.updateOrder("ZCAudio", type, target, orders, " CatalogID = " + catalogID))
         this.Response.setLogInfo(1, "排序成功");
       else {
         this.Response.setLogInfo(0, "排序失败");
       }
     }
     else if (OrderUtil.updateOrder("ZCAudio", type, target, orders, " SiteID = " + Application.getCurrentSiteID()))
       this.Response.setLogInfo(1, "排序成功");
     else
       this.Response.setLogInfo(0, "排序失败");
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.resource.AudioLib
 * JD-Core Version:    0.5.4
 */