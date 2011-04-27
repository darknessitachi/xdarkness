 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class ZCInnerGatherSchema extends Schema
 {
   private Long ID;
   private Long SiteID;
   private String Name;
   private String CatalogInnerCode;
   private String TargetCatalog;
   private String SyncCatalogInsert;
   private String SyncCatalogModify;
   private String SyncArticleModify;
   private Long AfterInsertStatus;
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
     new SchemaColumn("CatalogInnerCode", 1, 3, 200, 0, true, false), 
     new SchemaColumn("TargetCatalog", 1, 4, 4000, 0, true, false), 
     new SchemaColumn("SyncCatalogInsert", 1, 5, 2, 0, false, false), 
     new SchemaColumn("SyncCatalogModify", 1, 6, 2, 0, false, false), 
     new SchemaColumn("SyncArticleModify", 1, 7, 2, 0, false, false), 
     new SchemaColumn("AfterInsertStatus", 7, 8, 0, 0, false, false), 
     new SchemaColumn("AfterModifyStatus", 7, 9, 0, 0, false, false), 
     new SchemaColumn("Status", 1, 10, 2, 0, false, false), 
     new SchemaColumn("Prop1", 1, 11, 50, 0, false, false), 
     new SchemaColumn("Prop2", 1, 12, 50, 0, false, false), 
     new SchemaColumn("Prop3", 1, 13, 50, 0, false, false), 
     new SchemaColumn("Prop4", 1, 14, 50, 0, false, false), 
     new SchemaColumn("AddUser", 1, 15, 200, 0, true, false), 
     new SchemaColumn("AddTime", 0, 16, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 17, 200, 0, false, false), 
     new SchemaColumn("ModifyTime", 0, 18, 0, 0, false, false) };
   public static final String _TableCode = "ZCInnerGather";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into ZCInnerGather values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update ZCInnerGather set ID=?,SiteID=?,Name=?,CatalogInnerCode=?,TargetCatalog=?,SyncCatalogInsert=?,SyncCatalogModify=?,SyncArticleModify=?,AfterInsertStatus=?,AfterModifyStatus=?,Status=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
   protected static final String _DeleteSQL = "delete from ZCInnerGather  where ID=?";
   protected static final String _FillAllSQL = "select * from ZCInnerGather  where ID=?";
 
   public ZCInnerGatherSchema()
   {
     this.TableCode = "ZCInnerGather";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into ZCInnerGather values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZCInnerGather set ID=?,SiteID=?,Name=?,CatalogInnerCode=?,TargetCatalog=?,SyncCatalogInsert=?,SyncCatalogModify=?,SyncArticleModify=?,AfterInsertStatus=?,AfterModifyStatus=?,Status=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.DeleteSQL = "delete from ZCInnerGather  where ID=?";
     this.FillAllSQL = "select * from ZCInnerGather  where ID=?";
     this.HasSetFlag = new boolean[19];
   }
 
   protected Schema newInstance() {
     return new ZCInnerGatherSchema();
   }
 
   protected SchemaSet newSet() {
     return new ZCInnerGatherSet();
   }
 
   public ZCInnerGatherSet query() {
     return query(null, -1, -1);
   }
 
   public ZCInnerGatherSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public ZCInnerGatherSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public ZCInnerGatherSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (ZCInnerGatherSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
     if (i == 1) { if (v == null) this.SiteID = null; else this.SiteID = new Long(v.toString()); return; }
     if (i == 2) { this.Name = ((String)v); return; }
     if (i == 3) { this.CatalogInnerCode = ((String)v); return; }
     if (i == 4) { this.TargetCatalog = ((String)v); return; }
     if (i == 5) { this.SyncCatalogInsert = ((String)v); return; }
     if (i == 6) { this.SyncCatalogModify = ((String)v); return; }
     if (i == 7) { this.SyncArticleModify = ((String)v); return; }
     if (i == 8) { if (v == null) this.AfterInsertStatus = null; else this.AfterInsertStatus = new Long(v.toString()); return; }
     if (i == 9) { if (v == null) this.AfterModifyStatus = null; else this.AfterModifyStatus = new Long(v.toString()); return; }
     if (i == 10) { this.Status = ((String)v); return; }
     if (i == 11) { this.Prop1 = ((String)v); return; }
     if (i == 12) { this.Prop2 = ((String)v); return; }
     if (i == 13) { this.Prop3 = ((String)v); return; }
     if (i == 14) { this.Prop4 = ((String)v); return; }
     if (i == 15) { this.AddUser = ((String)v); return; }
     if (i == 16) { this.AddTime = ((Date)v); return; }
     if (i == 17) { this.ModifyUser = ((String)v); return; }
     if (i != 18) return; this.ModifyTime = ((Date)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.ID;
     if (i == 1) return this.SiteID;
     if (i == 2) return this.Name;
     if (i == 3) return this.CatalogInnerCode;
     if (i == 4) return this.TargetCatalog;
     if (i == 5) return this.SyncCatalogInsert;
     if (i == 6) return this.SyncCatalogModify;
     if (i == 7) return this.SyncArticleModify;
     if (i == 8) return this.AfterInsertStatus;
     if (i == 9) return this.AfterModifyStatus;
     if (i == 10) return this.Status;
     if (i == 11) return this.Prop1;
     if (i == 12) return this.Prop2;
     if (i == 13) return this.Prop3;
     if (i == 14) return this.Prop4;
     if (i == 15) return this.AddUser;
     if (i == 16) return this.AddTime;
     if (i == 17) return this.ModifyUser;
     if (i == 18) return this.ModifyTime;
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
 
   public long getAfterInsertStatus()
   {
     if (this.AfterInsertStatus == null) return 0L;
     return this.AfterInsertStatus.longValue();
   }
 
   public void setAfterInsertStatus(long afterInsertStatus)
   {
     this.AfterInsertStatus = new Long(afterInsertStatus);
   }
 
   public void setAfterInsertStatus(String afterInsertStatus)
   {
     if (afterInsertStatus == null) {
       this.AfterInsertStatus = null;
       return;
     }
     this.AfterInsertStatus = new Long(afterInsertStatus);
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
 * Qualified Name:     com.zving.schema.ZCInnerGatherSchema
 * JD-Core Version:    0.5.4
 */