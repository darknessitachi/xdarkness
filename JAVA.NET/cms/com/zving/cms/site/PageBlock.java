 package com.zving.cms.site;
 
 import com.zving.cms.datachannel.Deploy;
 import com.zving.cms.pub.CatalogUtil;
 import com.zving.cms.template.PageGenerator;
 import com.zving.framework.Config;
 import com.zving.framework.Page;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.Errorx;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.platform.pub.NoUtil;
 import com.zving.schema.ZCPageBlockItemSchema;
 import com.zving.schema.ZCPageBlockItemSet;
 import com.zving.schema.ZCPageBlockSchema;
 import com.zving.schema.ZCPageBlockSet;
 import java.util.Date;
 
 public class PageBlock extends Page
 {
   public static void dg1DataBind(DataGridAction dga)
   {
     QueryBuilder qb = new QueryBuilder("select a.* from ZCPageBlock a");
     String catalogID = dga.getParam("CatalogID");
     if (StringUtil.isNotEmpty(catalogID))
       qb.append(",zccatalog b where a.catalogid=b.id and b.innercode like ?", CatalogUtil.getInnerCode(catalogID) + 
         "%");
     else {
       qb.append(" where a.siteid=?", Application.getCurrentSiteID());
     }
     if (!Config.isDebugMode()) {
       qb.append(" and a.type<>4");
     }
     qb.append(" order by a.type asc");
     DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
     dt.insertColumn("CatalogIDName");
     dt.insertColumn("CatalogPath");
     for (int i = 0; i < dt.getRowCount(); ++i) {
       if ((StringUtil.isNotEmpty(dt.getString(i, "CatalogID"))) && (!"0".equals(dt.getString(i, "CatalogID")))) {
         dt.set(i, "CatalogIDName", CatalogUtil.getName(dt.getString(i, "CatalogID")));
         String str = "/";
         String id = dt.getString(i, "CatalogID");
         String parentid = "";
         do {
           if (StringUtil.isNotEmpty(parentid)) {
             id = parentid;
           }
           parentid = CatalogUtil.getParentID(id);
           str = "/" + CatalogUtil.getName(id) + str;
         }
         while (!
           parentid.equals("0"));
         dt.set(i, "CatalogPath", str);
       } else {
         dt.set(i, "CatalogIDName", "全站");
         dt.set(i, "CatalogPath", "");
       }
     }
     dga.setTotal(qb);
     dga.bindData(dt);
   }
 
   public static void dialogDataBind(DataGridAction dga)
   {
     String id = (String)dga.getParams().get("ID");
 
     DataTable dt = null;
     if (StringUtil.isNotEmpty(id))
       dt = new QueryBuilder("select title,URL from zcpageblockItem where blockid=?", id).executeDataTable();
     else {
       dt = new QueryBuilder("select '' as title,'' as URL from zcpageblockItem").executePagedDataTable(1, 0);
     }
     dga.bindData(dt);
   }
 
   public static void blockItemDataBind(DataGridAction dga) {
     String id = dga.getParam("ID");
     DataTable dt = null;
     if (StringUtil.isNotEmpty(id)) {
       dt = new QueryBuilder("select id,title,URL from zcpageblockItem where blockid=?", id).executeDataTable();
     }
     if ((dt == null) || (dt.getRowCount() == 0)) {
       String sql = "select 0 as id,'' as title,'' as URL from zcpageblockItem";
       dt = new QueryBuilder(sql).executePagedDataTable(1, 0);
     }
     dga.setTotal(dt.getRowCount());
     dga.bindData(dt);
   }
 
   public void edit() {
     Transaction trans = new Transaction();
 
     ZCPageBlockSchema block = new ZCPageBlockSchema();
     long blockID = Long.parseLong($V("ID"));
     block.setID(blockID);
     if (!block.fill()) {
       this.Response.setStatus(0);
       this.Response.setMessage("没有找到对应的区块!");
     }
     block.setName($V("Name"));
     block.setCode($V("Code"));
     block.setFileName($V("FileName"));
 
     block.setTemplate($V("Template"));
     block.setSortField($V("SortField"));
 
     block.setType(Integer.parseInt($V("Type")));
     block.setContent($V("Content"));
     block.setModifyTime(new Date());
     block.setModifyUser(User.getUserName());
 
     trans.add(block, 2);
 
     if (Integer.parseInt($V("Type")) == 2) {
       trans.add(new QueryBuilder("delete from ZCPageBlockItem where blockid=?", blockID));
       String[] title = $V("ItemTitle").split("\\^");
       String[] url = $V("ItemURL").split("\\^");
       for (int i = 0; i < title.length; ++i) {
         ZCPageBlockItemSchema item = new ZCPageBlockItemSchema();
         item.setID(NoUtil.getMaxID("PageBlockID"));
         item.setBlockID(blockID);
         item.setTitle(title[i]);
         item.setURL(url[i]);
         item.setImage($V("Image"));
         item.setAddTime(new Date());
         item.setAddUser(User.getUserName());
         trans.add(item, 1);
       }
     }
 
     if (trans.commit()) {
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("发生错误!");
     }
   }
 
   public static Mapx init(Mapx params) {
     if ((params.get("ID") != null) && (!"".equals(params.get("ID")))) {
       long ID = Long.parseLong(params.get("ID").toString());
       ZCPageBlockSchema block = new ZCPageBlockSchema();
       block.setID(ID);
       block.fill();
       Mapx mapx = block.toMapx();
       mapx.put("BlockType", block.getType());
       return mapx;
     }
     return params;
   }
 
   public static Mapx initParam(Mapx params)
   {
     return params;
   }
 
   public void add() {
     Transaction trans = new Transaction();
 
     ZCPageBlockSchema block = new ZCPageBlockSchema();
     long blockID = NoUtil.getMaxID("PageBlockID");
     block.setID(blockID);
     block.setSiteID(Application.getCurrentSiteID());
     String obj = $V("CatalogID");
     if (StringUtil.isNotEmpty(obj)) {
       block.setCatalogID(Long.parseLong(obj.toString()));
     }
 
     block.setName($V("Name"));
     block.setCode($V("Code"));
     block.setFileName($V("FileName"));
     block.setTemplate($V("Template"));
     block.setSortField($V("SortField"));
 
     block.setType(Integer.parseInt($V("Type")));
     block.setContent($V("Content"));
     block.setAddTime(new Date());
     block.setAddUser(User.getUserName());
 
     trans.add(block, 1);
 
     if (Integer.parseInt($V("Type")) == 2) {
       String[] title = $V("ItemTitle").split("\\^");
       String[] url = $V("ItemURL").split("\\^");
       for (int i = 0; i < title.length; ++i) {
         ZCPageBlockItemSchema item = new ZCPageBlockItemSchema();
         item.setID(NoUtil.getMaxID("PageBlockID"));
         item.setBlockID(blockID);
         item.setTitle(title[i]);
         item.setURL(url[i]);
         item.setImage($V("Image"));
         item.setAddTime(new Date());
         item.setAddUser(User.getUserName());
         trans.add(item, 1);
       }
     }
 
     if (trans.commit()) {
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("发生错误!");
     }
   }
 
   public void del() {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       this.Response.setStatus(0);
       this.Response.setMessage("传入ID时发生错误!");
       return;
     }
     Transaction trans = new Transaction();
     ZCPageBlockSchema block = new ZCPageBlockSchema();
     ZCPageBlockSet set = block.query(new QueryBuilder("where id in (" + ids + ")"));
     trans.add(set, 5);
 
     ZCPageBlockItemSchema blockItem = new ZCPageBlockItemSchema();
     ZCPageBlockItemSet itemSet = blockItem.query(new QueryBuilder("where blockID in (" + ids + ")"));
     trans.add(itemSet, 5);
 
     if (trans.commit()) {
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("操作数据库时发生错误!");
     }
   }
 
   public void generate()
   {
     PageGenerator p = new PageGenerator();
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       this.Response.setStatus(0);
       this.Response.setMessage("传入ID时发生错误!");
       return;
     }
     ZCPageBlockSchema block = new ZCPageBlockSchema();
     ZCPageBlockSet set = block.query(new QueryBuilder("where id in (" + ids + ")"));
     if (p.staticPageBlock(set)) {
       Deploy d = new Deploy();
       d.addJobs(Application.getCurrentSiteID(), p.getFileList());
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("生成区块失败。" + Errorx.printString());
     }
   }
 
   public void copy() {
     String BlockID = $V("ID");
     if (!StringUtil.checkID(BlockID)) {
       this.Response.setStatus(0);
       this.Response.setMessage("传入BlockID时发生错误!");
       return;
     }
     String catalogIDs = $V("CatalogIDs");
     if (!StringUtil.checkID(catalogIDs)) {
       this.Response.setStatus(0);
       this.Response.setMessage("传入CatalogID时发生错误!");
       return;
     }
     Transaction trans = new Transaction();
     ZCPageBlockSchema site = new ZCPageBlockSchema();
     ZCPageBlockSet set = site.query(new QueryBuilder("where id in (" + BlockID + ")"));
     String copySiteID = "";
 
     String[] catalogArr = catalogIDs.split(",");
     for (int i = 0; i < set.size(); ++i) {
       ZCPageBlockSchema block = set.get(i);
       for (int j = 0; j < catalogArr.length; ++j) {
         ZCPageBlockSchema blockClone = (ZCPageBlockSchema)block.clone();
         blockClone.setID(NoUtil.getMaxID("PageBlockID"));
         blockClone.setCatalogID(catalogArr[j]);
         if ("".equals(copySiteID)) {
           copySiteID = CatalogUtil.getSiteID(catalogArr[j]);
         }
         blockClone.setSiteID(copySiteID);
         trans.add(blockClone, 1);
 
         String sqlPageBlockCount = "update zccatalog set total = total+1 where id=?";
         trans.add(new QueryBuilder(sqlPageBlockCount, catalogArr[j]));
       }
     }
     if (trans.commit()) {
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("操作数据库时发生错误!");
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.site.PageBlock
 * JD-Core Version:    0.5.4
 */