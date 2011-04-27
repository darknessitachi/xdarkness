 package com.zving.datachannel;
 
 import com.zving.framework.Framework;
 import com.zving.framework.RequestImpl;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.DataTableUtil;
 import com.zving.framework.messages.LongTimeTask;
 import com.zving.framework.utility.Mapx;
 import com.zving.schema.ZCInnerGatherSchema;
 
 public class SynchUtil
 {
   public static void gatherRemote(long id, LongTimeTask task)
   {
     ZCInnerGatherSchema gather = new ZCInnerGatherSchema();
     gather.setID(id);
     gather.fill();
     String data = gather.getTargetCatalog();
     DataTable dt = DataTableUtil.txtToDataTable(data, null, "\t", "\n");
     for (int i = 0; i < dt.getRowCount(); ++i) {
       String ServerAddr = dt.getString(i, "ServerAddr");
       String SiteID = dt.getString(i, "SiteID");
       String SiteName = dt.getString(i, "SiteName");
       String CatalogID = dt.getString(i, "CatalogID");
       String CatalogName = dt.getString(i, "CatalogName");
       String Password = dt.getString(i, "Password");
       if (task != null) {
         task.setCurrentInfo("正在采集站点 " + SiteName + " 下的栏目 " + CatalogName);
       }
       RequestImpl Request = new RequestImpl();
       Request.put("SiteID", SiteID);
       Request.put("CatalogID", CatalogID);
       Request.put("Password", Password);
       String LastAdded = "0";
       String LastModified = "0";
       if (dt.getDataColumn("LastAdded") != null)
         LastAdded = dt.getString(i, "LastAdded");
       else {
         dt.insertColumn("LastAdded");
       }
       if (dt.getDataColumn("LastModified") != null)
         LastModified = dt.getString(i, "LastModified");
       else {
         dt.insertColumn("LastModified");
       }
       Mapx map = Framework.callRemoteMethod(ServerAddr, "com.zving.datachannel.SynchService.sendData", Request);
       DataTable articles = (DataTable)map.get("Data");
       DataTable catalogs = (DataTable)map.get("Catalog");
       if (task != null) {
         task.setCurrentInfo("正在保存站点 " + SiteName + " 下的栏目 " + CatalogName + " 下的数据");
       }
 
       dt.set(i, "LastAdded", LastAdded);
       dt.set(i, "LastModified", LastModified);
     }
     gather.setTargetCatalog(dt.toString());
     gather.update();
   }
 
   public static void deployRemote(long id, LongTimeTask task)
   {
   }
 
   public static void main(String[] args)
   {
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.datachannel.SynchUtil
 * JD-Core Version:    0.5.4
 */