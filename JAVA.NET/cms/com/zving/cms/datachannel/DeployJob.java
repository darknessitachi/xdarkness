 package com.zving.cms.datachannel;
 
 import com.zving.framework.Page;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.cache.CacheManager;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.data.DataRow;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.Errorx;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.schema.ZCDeployJobSchema;
 import com.zving.schema.ZCDeployJobSet;
 
 public class DeployJob extends Page
 {
   public static Mapx init(Mapx params)
   {
     return null;
   }
 
   public static void dg1DataBind(DataGridAction dga) {
     QueryBuilder qb = new QueryBuilder("select * from ZCDeployJob where siteid=? order by addtime desc", 
       Application.getCurrentSiteID());
     dga.setTotal(qb);
     DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
     dt.decodeColumn("status", Deploy.depolyStatus);
     dt.decodeColumn("Method", CacheManager.getMapx("Code", "DeployMethod"));
     dga.bindData(dt);
   }
 
   public static Mapx initDialog(Mapx params) {
     String sql = "select * from ZCDeployJob a where id=? ";
     DataTable dt = new QueryBuilder(sql, params.getString("ID")).executeDataTable();
     dt.decodeColumn("status", Deploy.depolyStatus);
     dt.decodeColumn("Method", CacheManager.getMapx("Code", "DeployMethod"));
     if ((dt != null) && (dt.getRowCount() > 0)) {
       params.putAll(dt.get(0).toCaseIgnoreMapx());
     }
     return params;
   }
 
   public void del() {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       this.Response.setStatus(0);
       this.Response.setMessage("传入ID时发生错误!");
       return;
     }
     String tsql = "where id in (" + ids + ")";
     ZCDeployJobSchema ZCDeployJob = new ZCDeployJobSchema();
     ZCDeployJobSet set = ZCDeployJob.query(new QueryBuilder(tsql));
 
     Transaction trans = new Transaction();
     trans.add(set, 3);
     if (trans.commit()) {
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("操作数据库时发生错误!");
     }
   }
 
   public void delAll() {
     Transaction trans = new Transaction();
     trans.add(new QueryBuilder("delete from zcdeployjob where siteid=?", Application.getCurrentSiteID()));
     if (trans.commit()) {
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("操作数据库时发生错误!");
     }
   }
 
   public void reExecuteJob()
   {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       this.Response.setStatus(0);
       this.Response.setMessage("传入ID时发生错误!");
       return;
     }
     String tsql = "where id in (" + ids + ")";
     ZCDeployJobSchema ZCDeployJob = new ZCDeployJobSchema();
     ZCDeployJobSet set = ZCDeployJob.query(new QueryBuilder(tsql));
     Deploy helper = new Deploy();
 
     for (int i = 0; i < set.size(); ++i) {
       helper.executeJob(set.get(i));
     }
 
     if (Errorx.hasError()) {
       this.Response.setStatus(0);
       this.Response.setMessage("分发错误。" + Errorx.printString());
     } else {
       this.Response.setStatus(1);
     }
   }
 
   public void executeFailJob()
   {
     String tsql = "where status=? and siteid=?";
     ZCDeployJobSchema ZCDeployJob = new ZCDeployJobSchema();
     ZCDeployJobSet set = ZCDeployJob.query(new QueryBuilder(tsql, 3L, Application.getCurrentSiteID()));
     Deploy helper = new Deploy();
     for (int i = 0; i < set.size(); ++i) {
       helper.executeJob(set.get(i));
     }
     if (Errorx.hasError()) {
       this.Response.setStatus(0);
       this.Response.setMessage("分发错误。" + Errorx.printString());
     } else {
       this.Response.setStatus(1);
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.datachannel.DeployJob
 * JD-Core Version:    0.5.4
 */