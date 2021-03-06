 package com.zving.cms.dataservice;
 
 import com.zving.cms.pub.CatalogUtil;
 import com.zving.cms.pub.SiteUtil;
 import com.zving.framework.Config;
 import com.zving.framework.Page;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.controls.TreeAction;
 import com.zving.framework.controls.TreeItem;
 import com.zving.framework.data.DataRow;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.FileUtil;
 import com.zving.framework.utility.Filter;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.platform.Priv;
 import com.zving.platform.pub.NoUtil;
 import com.zving.schema.ZCAdPositionSchema;
 import com.zving.schema.ZCAdPositionSet;
 import com.zving.schema.ZCAdvertisementSchema;
 import com.zving.schema.ZCAdvertisementSet;
 import java.io.File;
 import java.io.IOException;
 import java.util.Date;
 import java.util.List;
 
 public class AdvertiseLayout extends Page
 {
   public static Mapx PosTypes = new Mapx();
 
   static { PosTypes.put("banner", "矩形横幅");
     PosTypes.put("fixure", "固定位置");
     PosTypes.put("float", "漂浮移动");
     PosTypes.put("couplet", "对联广告");
     PosTypes.put("imagechange", "图片轮换广告");
     PosTypes.put("imagelist", "图片列表广告");
     PosTypes.put("text", "文字广告");
     PosTypes.put("code", "代码调用"); }
 
   public static void dg1DataBind(DataGridAction dga)
   {
     String RelaCatalogID = dga.getParam("CatalogID");
     if ((StringUtil.isEmpty(RelaCatalogID)) || (RelaCatalogID == null) || (RelaCatalogID.equalsIgnoreCase("null"))) {
       RelaCatalogID = "0";
     }
     String SearchContent = dga.getParam("SearchContent");
     QueryBuilder qb = new QueryBuilder(
       "select a.id id,a.SiteID SiteID,a.PositionName PositionName,a.PositionType PositionType,a.Description Description,a.RelaCatalogID RelaCatalogID,'' as AdType,a.JSName,a.PositionWidth PositionSizeWidth,a.PositionHeight PositionSizeHeight,(SELECT Name from ZCCatalog where ZCCatalog.ID = a.RelaCatalogID) as CatalogName from zcadposition a ");
 
     qb.append(" where SiteID = ?", Application.getCurrentSiteID());
     if (StringUtil.isNotEmpty(SearchContent)) {
       qb.append(" and PositionName like ?", "%" + SearchContent.trim() + "%");
     }
     if (!RelaCatalogID.equals("0"))
       qb.append(" and RelaCatalogID = ?  order by a.id desc", RelaCatalogID);
     else {
       qb.append("  order by a.RelaCatalogID asc");
     }
     DataTable dt = qb.executeDataTable();
     dt = dt.filter(new Filter() {
       public boolean filter(Object obj) {
         if (Priv.getPriv(User.getUserName(), "site", Application.getCurrentSiteID(), "site_manage")) {
           return true;
         }
         DataRow dr = (DataRow)obj;
         String RelaCatalogID = dr.getString("RelaCatalogID");
         if ("0".equals(RelaCatalogID)) {
           return Priv.getPriv(User.getUserName(), "site", Application.getCurrentSiteID(), 
             "article_manage");
         }
         return Priv.getPriv(User.getUserName(), "article", CatalogUtil.getInnerCode(RelaCatalogID), 
           "article_modify");
       }
     });
     DataTable newdt = new DataTable(dt.getDataColumns(), null);
     for (int i = dga.getPageIndex() * dga.getPageSize(); (i < dt.getRowCount()) && 
       (i < (dga.getPageIndex() + 1) * dga.getPageSize()); )
     {
       newdt.insertRow(dt.getDataRow(i));
 
       ++i;
     }
 
     for (int i = 0; i < newdt.getRowCount(); ++i) {
       if (StringUtil.isEmpty(newdt.getString(i, "CatalogName"))) {
         newdt.set(i, "CatalogName", "文档库");
       }
       String AdType = new QueryBuilder("select AdType from zcadvertisement  where positionid=? and isopen='Y'", 
         dt.getString(i, "ID")).executeOneValue();
 
       if (AdType.equalsIgnoreCase("null")) {
         AdType = "";
       }
       newdt.set(i, "ADType", AdType);
     }
     newdt.decodeColumn("ADType", Advertise.ADTypes);
     newdt.decodeColumn("PositionType", PosTypes);
     dga.setTotal(dt.getRowCount());
     dga.bindData(newdt);
   }
 
   public static void treeDataBind(TreeAction ta) {
     Object obj = ta.getParams().get("SiteID");
     String siteID = Application.getCurrentSiteID();
     Object typeObj = ta.getParams().get("CatalogType");
     int catalogType = (typeObj != null) ? Integer.parseInt(typeObj.toString()) : 1;
     String parentTreeLevel = ta.getParams().getString("ParentLevel");
     String parentID = ta.getParams().getString("ParentID");
     DataTable dt = null;
     if (ta.isLazyLoad()) {
       QueryBuilder qb = new QueryBuilder(
         "select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel>? and innerCode like ? and exists (select 1 from ZCAdposition where RelaCatalogID=ZCCatalog.ID) order by orderflag,innercode");
 
       qb.add(catalogType);
       qb.add(siteID);
       qb.add(parentTreeLevel);
       qb.add(CatalogUtil.getInnerCode(parentID) + "%");
       dt = qb.executeDataTable();
     } else {
       QueryBuilder qb = new QueryBuilder(
         "select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel-1 <=? and exists (select 1 from ZCAdposition where RelaCatalogID=ZCCatalog.ID) order by orderflag,innercode");
 
       qb.add(catalogType);
       qb.add(siteID);
       qb.add(ta.getLevel());
       dt = qb.executeDataTable();
     }
 
     String siteName = "文档库";
     dt = dt.filter(new Filter() {
       public boolean filter(Object obj) {
         DataRow dr = (DataRow)obj;
         return Priv.getPriv(User.getUserName(), "article", dr.getString("InnerCode"), "article_browse");
       }
     });
     ta.setRootText(siteName);
     ta.bindData(dt);
     List items = ta.getItemList();
     for (int i = 1; i < items.size(); ++i) {
       TreeItem item = (TreeItem)items.get(i);
       if ("Y".equals(item.getData().getString("SingleFlag")))
         item.setIcon("Icons/treeicon11.gif");
     }
   }
 
   public static Mapx DialogInit(Mapx params)
   {
     String id = (String)params.get("ID");
 
     if (StringUtil.isNotEmpty(id)) {
       ZCAdPositionSchema position = new ZCAdPositionSchema();
       position.setID(id);
       position.fill();
       params.putAll(position.toMapx());
       if (position.getPositionType().equals("text")) {
         params.put("PositionWidth", "0");
         params.put("PositionHeight", "0");
       }
     } else {
       params.put("Align", "Y");
       params.put("Scroll", "Y");
     }
     return params;
   }
 
   public void add() {
     ZCAdPositionSchema position = new ZCAdPositionSchema();
     String id = $V("ID");
     String PositionName = $V("PositionName");
 
     if (StringUtil.isNotEmpty(id)) {
       position.setID(id);
       position.fill();
       if (!PositionName.equals(position.getPositionName())) {
         int NameCount = new QueryBuilder("select count(*) from zcadposition where PositionName = ? and SiteID = ?", PositionName, Application.getCurrentSiteID()).executeInt();
         if (NameCount > 0) {
           this.Response.setLogInfo(0, "已经有同名的版位，请您重新填写版位名");
           return;
         }
       }
       position.setCode(id);
       position.setModifyUser(User.getUserName());
       position.setModifyTime(new Date());
       position.setJsName(createJS("modify", position));
     } else {
       int NameCount = new QueryBuilder("select count(*) from zcadposition where PositionName = ? and SiteID = ? ", PositionName, Application.getCurrentSiteID()).executeInt();
       if (NameCount > 0) {
         this.Response.setLogInfo(0, "已经有同名的版位，请您重新填写版位名");
         return;
       }
       position.setID(NoUtil.getMaxID("AdPositionID"));
       position.setCode(position.getID());
       position.setAddUser(User.getUserName());
       position.setAddTime(new Date());
       position.setJsName(createJS("add", position));
     }
     position.setSiteID(Application.getCurrentSiteID());
     position.setValue(this.Request);
     position.setRelaCatalogID($V("RelaCatalogID"));
     if (position.getAlign().equals("Y")) {
       position.setPaddingLeft(0L);
       position.setPaddingTop(0L);
     }
     if (position.getPositionType().equals("text")) {
       position.setPositionWidth("0");
       position.setPositionHeight("0");
     }
 
     if (StringUtil.isNotEmpty(id)) {
       ZCAdvertisementSchema adv = new ZCAdvertisementSchema();
       if ($V("hPositionType").equals($V("PositionType"))) {
         ZCAdvertisementSet advset = new ZCAdvertisementSchema().query(
           new QueryBuilder("where positionid=? and isopen='Y'", id));
 
         if (advset.size() != 0) {
           adv = advset.get(0);
           if (!Advertise.CreateJSCode(adv, position)) {
             this.Response.setStatus(0);
             this.Response.setMessage("生成广告js发生错误!");
           }
         }
       } else {
         ZCAdvertisementSet advset = new ZCAdvertisementSchema()
           .query(new QueryBuilder("where positionid=?", id));
         if (advset.size() > 0) {
           Transaction trans = new Transaction();
           trans.add(advset, 5);
           trans.commit();
         }
       }
 
     }
 
     if (StringUtil.isNotEmpty(id)) {
       if (position.update())
         this.Response.setLogInfo(1, "修改成功");
       else {
         this.Response.setLogInfo(0, "发生错误");
       }
     }
     else if (position.insert())
       this.Response.setLogInfo(1, "新增成功");
     else
       this.Response.setLogInfo(0, "发生错误");
   }
 
   public void del()
   {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       this.Response.setStatus(0);
       this.Response.setMessage("传入ID时发生错误!");
       return;
     }
     Transaction trans = new Transaction();
     ZCAdPositionSchema ad = new ZCAdPositionSchema();
     ZCAdvertisementSchema adv = new ZCAdvertisementSchema();
     ZCAdPositionSet set = ad.query(new QueryBuilder("where id in (" + ids + ")"));
     ZCAdvertisementSet adSet = adv.query(new QueryBuilder("where PositionID in (" + ids + ")"));
     trans.add(set, 3);
     trans.add(adSet, 3);
     if (trans.commit()) {
       for (int i = 0; i < set.size(); ++i) {
         ZCAdPositionSchema position = set.get(i);
         String JSPath = Config.getContextRealPath() + Config.getValue("UploadDir") + "/" + 
           SiteUtil.getAlias(position.getSiteID()) + "/" + position.getJsName();
         File file = new File(JSPath);
         if (file.exists()) {
           file.delete();
         }
       }
       this.Response.setLogInfo(1, "删除版位成功！");
     } else {
       this.Response.setLogInfo(0, "操作数据库时发生错误!");
     }
   }
 
   public void copy() {
     String id = $V("ID");
     ZCAdPositionSchema ad = new ZCAdPositionSchema();
     ad.setID(Long.parseLong(id));
     ad.fill();
     String PositionName = ad.getPositionName();
     PositionName = "复制  " + PositionName;
     ad.setID(NoUtil.getMaxID("AdPositionID"));
     int count = 0;
     String Code = ad.getCode();
     do {
       Code = "CopyOf" + Code;
       count = new QueryBuilder("select count(*) from zcadposition where Code = ?", Code).executeInt();
     }while (count > 0);
     ad.setCode(Code);
     ad.setAddUser(User.getUserName());
     ad.setAddTime(new Date());
     ad.setJsName(createJS("copy", ad));
     ad.setPositionName(PositionName);
     if (ad.insert()) {
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("发生错误!");
     }
   }
 
   public static String createJS(String type, ZCAdPositionSchema adp)
   {
     String path = Config.getContextRealPath() + Config.getValue("UploadDir") + "/" + 
       Application.getCurrentSiteAlias() + "/js/";
     String oldPath = adp.getJsName();
     File f = new File(path);
     if (!f.exists()) {
       f.mkdir();
     }
     String filename = "";
 
     if (type.equalsIgnoreCase("add")) {
       filename = adp.getCode() + ".js";
       path = path + filename;
       File file = new File(path);
       if (file.exists())
         file.delete();
       try
       {
         file.createNewFile();
       } catch (IOException e) {
         e.printStackTrace();
       }
       path = "js/" + filename;
     } else if (type.equalsIgnoreCase("modify")) {
       if ("".equals(oldPath)) {
         filename = adp.getCode() + ".js";
         path = path + filename;
         File file = new File(path);
         if (file.exists())
           file.delete();
         try
         {
           file.createNewFile();
         } catch (IOException e) {
           e.printStackTrace();
         }
         path = "js/" + filename;
       } else {
         path = Config.getContextRealPath() + Config.getValue("UploadDir") + "/" + 
           Application.getCurrentSiteAlias() + "/" + oldPath;
         FileUtil.delete(path);
         File file = new File(path);
         if (!file.exists()) {
           try {
             file.createNewFile();
           } catch (IOException e) {
             e.printStackTrace();
           }
         }
         path = oldPath;
       }
     } else if (type.equalsIgnoreCase("copy")) {
       filename = adp.getCode() + ".js";
       path = path + filename;
       File file = new File(path);
       if (file.exists())
         file.delete();
       try
       {
         file.createNewFile();
       } catch (IOException e) {
         e.printStackTrace();
       }
       path = "js/" + filename;
     }
     return path;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.dataservice.AdvertiseLayout
 * JD-Core Version:    0.5.4
 */