 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class ZCInnerDeploySchema extends Schema
 {
   private Long ID;
   private Long SiteID;
   private String Name;
   private String DeployType;
   private String CatalogInnerCode;
   private String TargetCatalog;
   private String SyncCatalogInsert;
   private String SyncCatalogModify;
   private String SyncArticleModify;
   private Long AfterSyncStatus;
   private Long AfterModifyStatus;
   private String Status;
   private String Prop1;
   private String Prop2;
   private String Prop3;
   private String Prop4;
   private String AddUser;
   private Date AddTime;
   private String ModifyUser;
   private Date ModifyTime;
   public static final SchemaColumn[] _Columns = { 
     new SchemaColumn("ID", 7, 0, 0, 0, true, true), 
     new SchemaColumn("SiteID", 7, 1, 0, 0, true, false), 
     new SchemaColumn("Name", 1, 2, 200, 0, true, false), 
     new SchemaColumn("DeployType", 1, 3, 2, 0, true, false), 
     new SchemaColumn("CatalogInnerCode", 1, 4, 200, 0, true, false), 
     new SchemaColumn("TargetCatalog", 1, 5, 4000, 0, true, false), 
     new SchemaColumn("SyncCatalogInsert", 1, 6, 2, 0, false, false), 
     new SchemaColumn("SyncCatalogModify", 1, 7, 2, 0, false, false), 
     new SchemaColumn("SyncArticleModify", 1, 8, 2, 0, false, false), 
     new SchemaColumn("AfterSyncStatus", 7, 9, 0, 0, false, false), 
     new SchemaColumn("AfterModifyStatus", 7, 10, 0, 0, false, false), 
     new SchemaColumn("Status", 1, 11, 2, 0, false, false), 
     new SchemaColumn("Prop1", 1, 12, 50, 0, false, false), 
     new SchemaColumn("Prop2", 1, 13, 50, 0, false, false), 
     new SchemaColumn("Prop3", 1, 14, 50, 0, false, false), 
     new SchemaColumn("Prop4", 1, 15, 50, 0, false, false), 
     new SchemaColumn("AddUser", 1, 16, 200, 0, true, false), 
     new SchemaColumn("AddTime", 0, 17, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 18, 200, 0, false, false), 
     new SchemaColumn("ModifyTime", 0, 19, 0, 0, false, false) };
   public static final String _TableCode = "ZCInnerDeploy";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into ZCInnerDeploy values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update ZCInnerDeploy set ID=?,SiteID=?,Name=?,DeployType=?,CatalogInnerCode=?,TargetCatalog=?,SyncCatalogInsert=?,SyncCatalogModify=?,SyncArticleModify=?,AfterSyncStatus=?,AfterModifyStatus=?,Status=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
   protected static final String _DeleteSQL = "delete from ZCInnerDeploy  where ID=?";
   protected static final String _FillAllSQL = "select * from ZCInnerDeploy  where ID=?";
 
   public ZCInnerDeploySchema()
   {
     this.TableCode = "ZCInnerDeploy";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into ZCInnerDeploy values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZCInnerDeploy set ID=?,SiteID=?,Name=?,DeployType=?,CatalogInnerCode=?,TargetCatalog=?,SyncCatalogInsert=?,SyncCatalogModify=?,SyncArticleModify=?,AfterSyncStatus=?,AfterModifyStatus=?,Status=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.DeleteSQL = "delete from ZCInnerDeploy  where ID=?";
     this.FillAllSQL = "select * from ZCInnerDeploy  where ID=?";
     this.HasSetFlag = new boolean[20];
   }
 
   protected Schema newInstance() {
     return new ZCInnerDeploySchema();
   }
 
   protected SchemaSet newSet() {
     return new ZCInnerDeploySet();
   }
 
   public ZCInnerDeploySet query() {
     return query(null, -1, -1);
   }
 
   public ZCInnerDeploySet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public ZCInnerDeploySet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public ZCInnerDeploySet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (ZCInnerDeploySet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
     if (i == 1) { if (v == null) this.SiteID = null; else this.SiteID = new Long(v.toString()); return; }
     if (i == 2) { this.Name = ((String)v); return; }
     if (i == 3) { this.DeployType = ((String)v); return; }
     if (i == 4) { this.CatalogInnerCode = ((String)v); return; }
     if (i == 5) { this.TargetCatalog = ((String)v); return; }
     if (i == 6) { this.SyncCatalogInsert = ((String)v); return; }
     if (i == 7) { this.SyncCatalogModify = ((String)v); return; }
     if (i == 8) { this.SyncArticleModify = ((String)v); return; }
     if (i == 9) { if (v == null) this.AfterSyncStatus = null; else this.AfterSyncStatus = new Long(v.toString()); return; }
     if (i == 10) { if (v == null) this.AfterModifyStatus = null; else this.AfterModifyStatus = new Long(v.toString()); return; }
     if (i == 11) { this.Status = ((String)v); return; }
     if (i == 12) { this.Prop1 = ((String)v); return; }
     if (i == 13) { this.Prop2 = ((String)v); return; }
     if (i == 14) { this.Prop3 = ((String)v); return; }
     if (i == 15) { this.Prop4 = ((String)v); return; }
     if (i == 16) { this.AddUser = ((String)v); return; }
     if (i == 17) { this.AddTime = ((Date)v); return; }
     if (i == 18) { this.ModifyUser = ((String)v); return; }
     if (i != 19) return; this.ModifyTime = ((Date)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.ID;
     if (i == 1) return this.SiteID;
     if (i == 2) return this.Name;
     if (i == 3) return this.DeployType;
     if (i == 4) return this.CatalogInnerCode;
     if (i == 5) return this.TargetCatalog;
     if (i == 6) return this.SyncCatalogInsert;
     if (i == 7) return this.SyncCatalogModify;
     if (i == 8) return this.SyncArticleModify;
     if (i == 9) return this.AfterSyncStatus;
     if (i == 10) return this.AfterModifyStatus;
     if (i == 11) return this.Status;
     if (i == 12) return this.Prop1;
     if (i == 13) return this.Prop2;
     if (i == 14) return this.Prop3;
     if (i == 15) return this.Prop4;
     if (i == 16) return this.AddUser;
     if (i == 17) return this.AddTime;
     if (i == 18) return this.ModifyUser;
     if (i == 19) return this.ModifyTime;
     return null;
   }
 
   public long getID()
   {
     if (this.ID == null) return 0L;
     return this.ID.longValue();
   }
 
   public void setID(long iD)
   {
     this.ID = new Long(iD);
   }
 
   public void setID(String iD)
   {
     if (iD == null) {
       this.ID = null;
       return;
     }
     this.ID = new Long(iD);
   }
 
   public long getSiteID()
   {
     if (this.SiteID == null) return 0L;
     return this.SiteID.longValue();
   }
 
   public void setSiteID(long siteID)
   {
     this.SiteID = new Long(siteID);
   }
 
   public void setSiteID(String siteID)
   {
     if (siteID == null) {
       this.SiteID = null;
       return;
     }
     this.SiteID = new Long(siteID);
   }
 
   public String getName()
   {
     return this.Name;
   }
 
   public void setName(String name)
   {
     this.Name = name;
   }
 
   public String getDeployType()
   {
     return this.DeployType;
   }
 
   public void setDeployType(String deployType)
   {
     this.DeployType = deployType;
   }
 
   public String getCatalogInnerCode()
   {
     return this.CatalogInnerCode;
   }
 
   public void setCatalogInnerCode(String catalogInnerCode)
   {
     this.CatalogInnerCode = catalogInnerCode;
   }
 
   public String getTargetCatalog()
   {
     return this.TargetCatalog;
   }
 
   public void setTargetCatalog(String targetCatalog)
   {
     this.TargetCatalog = targetCatalog;
   }
 
   public String getSyncCatalogInsert()
   {
     return this.SyncCatalogInsert;
   }
 
   public void setSyncCatalogInsert(String syncCatalogInsert)
   {
     this.SyncCatalogInsert = syncCatalogInsert;
   }
 
   public String getSyncCatalogModify()
   {
     return this.SyncCatalogModify;
   }
 
   public void setSyncCatalogModify(String syncCatalogModify)
   {
     this.SyncCatalogModify = syncCatalogModify;
   }
 
   public String getSyncArticleModify()
   {
     return this.SyncArticleModify;
   }
 
   public void setSyncArticleModify(String syncArticleModify)
   {
     this.SyncArticleModify = syncArticleModify;
   }
 
   public long getAfterSyncStatus()
   {
     if (this.AfterSyncStatus == null) return 0L;
     return this.AfterSyncStatus.longValue();
   }
 
   public void setAfterSyncStatus(long afterSyncStatus)
   {
     this.AfterSyncStatus = new Long(afterSyncStatus);
   }
 
   public void setAfterSyncStatus(String afterSyncStatus)
   {
     if (afterSyncStatus == null) {
       this.AfterSyncStatus = null;
       return;
     }
     this.AfterSyncStatus = new Long(afterSyncStatus);
   }
 
   public long getAfterModifyStatus()
   {
     if (this.AfterModifyStatus == null) return 0L;
     return this.AfterModifyStatus.longValue();
   }
 
   public void setAfterModifyStatus(long afterModifyStatus)
   {
     this.AfterModifyStatus = new Long(afterModifyStatus);
   }
 
   public void setAfterModifyStatus(String afterModifyStatus)
   {
     if (afterModifyStatus == null) {
       this.AfterModifyStatus = null;
       return;
     }
     this.AfterModifyStatus = new Long(afterModifyStatus);
   }
 
   public String getStatus()
   {
     return this.Status;
   }
 
   public void setStatus(String status)
   {
     this.Status = status;
   }
 
   public String getProp1()
   {
     return this.Prop1;
   }
 
   public void setProp1(String prop1)
   {
     this.Prop1 = prop1;
   }
 
   public String getProp2()
   {
     return this.Prop2;
   }
 
   public void setProp2(String prop2)
   {
     this.Prop2 = prop2;
   }
 
   public String getProp3()
   {
     return this.Prop3;
   }
 
   public void setProp3(String prop3)
   {
     this.Prop3 = prop3;
   }
 
   public String getProp4()
   {
     return this.Prop4;
   }
 
   public void setProp4(String prop4)
   {
     this.Prop4 = prop4;
   }
 
   public String getAddUser()
   {
     return this.AddUser;
   }
 
   public void setAddUser(String addUser)
   {
     this.AddUser = addUser;
   }
 
   public Date getAddTime()
   {
     return this.AddTime;
   }
 
   public void setAddTime(Date addTime)
   {
     this.AddTime = addTime;
   }
 
   public String getModifyUser()
   {
     return this.ModifyUser;
   }
 
   public void setModifyUser(String modifyUser)
   {
     this.ModifyUser = modifyUser;
   }
 
   public Date getModifyTime()
   {
     return this.ModifyTime;
   }
 
   public void setModifyTime(Date modifyTime)
   {
     this.ModifyTime = modifyTime;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZCInnerDeploySchema
 * JD-Core Version:    0.5.4
 */