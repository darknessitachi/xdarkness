 package com.zving.datachannel;
 
 import com.zving.cms.document.Article;
 import com.zving.cms.pub.CatalogUtil;
 import com.zving.framework.Page;
 import com.zving.framework.RequestImpl;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.DataTableUtil;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.messages.LongTimeTask;
 import com.zving.framework.utility.HtmlUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.platform.pub.NoUtil;
 import com.zving.schema.ZCInnerGatherSchema;
 import com.zving.schema.ZCInnerGatherSet;
 import java.util.Date;
 
 public class FromCatalog extends Page
 {
   public static Mapx init(Mapx params)
   {
     String id = params.getString("ID");
     String syncCatalogInsert = "N";
     String syncCatalogModify = "N";
     String syncArticleModify = "N";
     String afterInsertStatus = "0";
     String afterModifyStatus = "60";
     if (StringUtil.isNotEmpty(id)) {
       ZCInnerGatherSchema ig = new ZCInnerGatherSchema();
       ig.setID(id);
       ig.fill();
       params.putAll(ig.toMapx());
       syncCatalogInsert = ig.getSyncCatalogInsert();
       syncCatalogModify = ig.getSyncCatalogModify();
       syncArticleModify = ig.getSyncArticleModify();
       afterInsertStatus = ig.getAfterInsertStatus();
       afterModifyStatus = ig.getAfterModifyStatus();
     } else {
       params.put("SiteID", Application.getCurrentSiteID());
     }
     params.put("SyncCatalogInsert", HtmlUtil.codeToRadios("SyncCatalogInsert", "YesOrNo", syncCatalogInsert));
     params.put("SyncCatalogModify", HtmlUtil.codeToRadios("SyncCatalogModify", "YesOrNo", syncCatalogModify));
     params.put("SyncArticleModify", HtmlUtil.codeToRadios("SyncArticleModify", "YesOrNo", syncArticleModify));
     params.put("AfterInsertStatus", HtmlUtil.mapxToOptions(Article.STATUS_MAP, afterInsertStatus));
     params.put("AfterModifyStatus", HtmlUtil.mapxToOptions(Article.STATUS_MAP, afterModifyStatus));
     return params;
   }
 
   public static void dg1DataBind(DataGridAction dga) {
     String sql = "select * from ZCInnerGather where SiteID=?";
     DataTable dt = new QueryBuilder(sql, Application.getCurrentSiteID()).executeDataTable();
     dt.insertColumn("CatalogName");
     dt.insertColumn("CatalogID");
     for (int i = 0; i < dt.getRowCount(); ++i) {
       dt.set(i, "CatalogName", CatalogUtil.getNameByInnerCode(dt.getString(i, "CatalogInnerCode")));
       dt.set(i, "CatalogID", CatalogUtil.getIDByInnerCode(dt.getString(i, "CatalogInnerCode")));
     }
     Mapx map = new Mapx();
     map.put("Y", "启用");
     map.put("N", "停用");
     dt.decodeColumn("Status", map);
     dga.bindData(dt);
   }
 
   public static void dialogDataBind(DataGridAction dga) {
     String id = dga.getParam("ID");
     DataTable dt = (DataTable)dga.getParams().get("Data");
     if (dt != null) {
       dga.bindData(dt);
     }
     else if (StringUtil.isEmpty(id)) {
       dt = new DataTable();
       dt.insertColumn("ServerAddr");
       dt.insertColumn("SiteID");
       dt.insertColumn("SiteName");
       dt.insertColumn("CatalogID");
       dt.insertColumn("CatalogName");
       dt.insertColumn("Password");
       dga.bindData(dt);
     } else {
       ZCInnerGatherSchema ig = new ZCInnerGatherSchema();
       ig.setID(id);
       ig.fill();
       String data = ig.getTargetCatalog();
       dt = DataTableUtil.txtToDataTable(data, null, "\t", "\n");
       dga.bindData(dt);
     }
   }
 
   public void add()
   {
     String id = $V("ID");
     DataTable dt = (DataTable)this.Request.get("Data");
     ZCInnerGatherSchema ig = new ZCInnerGatherSchema();
     Transaction tran = new Transaction();
     if (StringUtil.isNotEmpty(id)) {
       ig.setID(id);
       ig.fill();
       ig.setModifyTime(new Date());
       ig.setModifyUser(User.getUserName());
       tran.add(ig, 2);
     } else {
       ig.setID(NoUtil.getMaxID("InnerGatherID"));
       ig.setAddTime(new Date());
       ig.setAddUser(User.getUserName());
       tran.add(ig, 1);
     }
     ig.setValue(this.Request);
     ig.setSiteID(Application.getCurrentSiteID());
     for (int i = dt.getRowCount() - 1; i >= 0; --i) {
       if ((!dt.getString(i, "ServerAddr").equalsIgnoreCase("localhost")) || 
         (dt.getLong(i, "SiteID") != ig.getSiteID()) || 
         (!dt.getString(i, "CatalogID").equals(CatalogUtil.getIDByInnerCode(ig.getCatalogInnerCode())))) continue;
       dt.deleteRow(i);
     }
 
     ig.setTargetCatalog(dt.toString());
     ig.setCatalogInnerCode(CatalogUtil.getInnerCode($V("CatalogID")));
     if (tran.commit())
       this.Response.setMessage("保存成功!");
     else
       this.Response.setMessage("保存数据时发生错误!");
   }
 
   public void del()
   {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       return;
     }
     ZCInnerGatherSet set = new ZCInnerGatherSchema().query(new QueryBuilder("where id in (" + ids + ")"));
     if (set.deleteAndBackup())
       this.Response.setMessage("删除成功!");
     else
       this.Response.setMessage("删除数据时发生错误!");
   }
 
   public void execute()
   {
     long id = Long.parseLong($V("ID"));
     boolean restartFlag = "Y".equals($V("RestartFlag"));
     LongTimeTask ltt = LongTimeTask.getInstanceByType("InnerGather" + id);
     if (ltt != null) {
       if (!ltt.isAlive()) {
         LongTimeTask.removeInstanceById(ltt.getTaskID());
       } else {
         this.Response.setError("相关任务正在运行中，请先中止！");
         return;
       }
     }
     ltt = new LongTimeTask(id, restartFlag) { private final long val$id;
       private final boolean val$restartFlag;
 
       public void execute() { ZCInnerGatherSchema gather = new ZCInnerGatherSchema();
         gather.setID(this.val$id);
         gather.fill();
         DataTable dt = DataTableUtil.txtToDataTable(gather.getTargetCatalog(), null, "\t", "\n");
         if (this.val$restartFlag) {
           dt.deleteColumn("LastTime");
           gather.setTargetCatalog(dt.toString());
         }
         InnerSyncService.executeGather(gather, this);
         setPercent(100); }
 
     };
     ltt.setType("InnerGather" + id);
     ltt.setUser(User.getCurrent());
     ltt.start();
     $S("TaskID", ltt.getTaskID());
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.datachannel.FromCatalog
 * JD-Core Version:    0.5.4
 */