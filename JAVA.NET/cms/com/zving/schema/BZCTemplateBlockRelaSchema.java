 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class BZCTemplateBlockRelaSchema extends Schema
 {
   private Long SiteID;
   private String FileName;
   private Long BlockID;
   private String Prop1;
   private String Prop2;
   private String AddUser;
   private Date AddTime;
   private String ModifyUser;
   private Date ModifyTime;
   private String BackupNo;
   private String BackupOperator;
   private Date BackupTime;
   private String BackupMemo;
   public static final SchemaColumn[] _Columns = { 
     new SchemaColumn("SiteID", 7, 0, 0, 0, true, true), 
     new SchemaColumn("FileName", 1, 1, 100, 0, true, true), 
     new SchemaColumn("BlockID", 7, 2, 0, 0, true, true), 
     new SchemaColumn("Prop1", 1, 3, 50, 0, false, false), 
     new SchemaColumn("Prop2", 1, 4, 50, 0, false, false), 
     new SchemaColumn("AddUser", 1, 5, 200, 0, true, false), 
     new SchemaColumn("AddTime", 0, 6, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 7, 200, 0, false, false), 
     new SchemaColumn("ModifyTime", 0, 8, 0, 0, false, false), 
     new SchemaColumn("BackupNo", 1, 9, 15, 0, true, true), 
     new SchemaColumn("BackupOperator", 1, 10, 200, 0, true, false), 
     new SchemaColumn("BackupTime", 0, 11, 0, 0, true, false), 
     new SchemaColumn("BackupMemo", 1, 12, 50, 0, false, false) };
   public static final String _TableCode = "BZCTemplateBlockRela";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into BZCTemplateBlockRela values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update BZCTemplateBlockRela set SiteID=?,FileName=?,BlockID=?,Prop1=?,Prop2=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where SiteID=? and FileName=? and BlockID=? and BackupNo=?";
   protected static final String _DeleteSQL = "delete from BZCTemplateBlockRela  where SiteID=? and FileName=? and BlockID=? and BackupNo=?";
   protected static final String _FillAllSQL = "select * from BZCTemplateBlockRela  where SiteID=? and FileName=? and BlockID=? and BackupNo=?";
 
   public BZCTemplateBlockRelaSchema()
   {
     this.TableCode = "BZCTemplateBlockRela";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into BZCTemplateBlockRela values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZCTemplateBlockRela set SiteID=?,FileName=?,BlockID=?,Prop1=?,Prop2=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where SiteID=? and FileName=? and BlockID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZCTemplateBlockRela  where SiteID=? and FileName=? and BlockID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZCTemplateBlockRela  where SiteID=? and FileName=? and BlockID=? and BackupNo=?";
     this.HasSetFlag = new boolean[13];
   }
 
   protected Schema newInstance() {
     return new BZCTemplateBlockRelaSchema();
   }
 
   protected SchemaSet newSet() {
     return new BZCTemplateBlockRelaSet();
   }
 
   public BZCTemplateBlockRelaSet query() {
     return query(null, -1, -1);
   }
 
   public BZCTemplateBlockRelaSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public BZCTemplateBlockRelaSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public BZCTemplateBlockRelaSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (BZCTemplateBlockRelaSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.SiteID = null; else this.SiteID = new Long(v.toString()); return; }
     if (i == 1) { this.FileName = ((String)v); return; }
     if (i == 2) { if (v == null) this.BlockID = null; else this.BlockID = new Long(v.toString()); return; }
     if (i == 3) { this.Prop1 = ((String)v); return; }
     if (i == 4) { this.Prop2 = ((String)v); return; }
     if (i == 5) { this.AddUser = ((String)v); return; }
     if (i == 6) { this.AddTime = ((Date)v); return; }
     if (i == 7) { this.ModifyUser = ((String)v); return; }
     if (i == 8) { this.ModifyTime = ((Date)v); return; }
     if (i == 9) { this.BackupNo = ((String)v); return; }
     if (i == 10) { this.BackupOperator = ((String)v); return; }
     if (i == 11) { this.BackupTime = ((Date)v); return; }
     if (i != 12) return; this.BackupMemo = ((String)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.SiteID;
     if (i == 1) return this.FileName;
     if (i == 2) return this.BlockID;
     if (i == 3) return this.Prop1;
     if (i == 4) return this.Prop2;
     if (i == 5) return this.AddUser;
     if (i == 6) return this.AddTime;
     if (i == 7) return this.ModifyUser;
     if (i == 8) return this.ModifyTime;
     if (i == 9) return this.BackupNo;
     if (i == 10) return this.BackupOperator;
     if (i == 11) return this.BackupTime;
     if (i == 12) return this.BackupMemo;
     return null;
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
 
   public String getFileName()
   {
     return this.FileName;
   }
 
   public void setFileName(String fileName)
   {
     this.FileName = fileName;
   }
 
   public long getBlockID()
   {
     if (this.BlockID == null) return 0L;
     return this.BlockID.longValue();
   }
 
   public void setBlockID(long blockID)
   {
     this.BlockID = new Long(blockID);
   }
 
   public void setBlockID(String blockID)
   {
     if (blockID == null) {
       this.BlockID = null;
       return;
     }
     this.BlockID = new Long(blockID);
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
 
   public String getBackupNo()
   {
     return this.BackupNo;
   }
 
   public void setBackupNo(String backupNo)
   {
     this.BackupNo = backupNo;
   }
 
   public String getBackupOperator()
   {
     return this.BackupOperator;
   }
 
   public void setBackupOperator(String backupOperator)
   {
     this.BackupOperator = backupOperator;
   }
 
   public Date getBackupTime()
   {
     return this.BackupTime;
   }
 
   public void setBackupTime(Date backupTime)
   {
     this.BackupTime = backupTime;
   }
 
   public String getBackupMemo()
   {
     return this.BackupMemo;
   }
 
   public void setBackupMemo(String backupMemo)
   {
     this.BackupMemo = backupMemo;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZCTemplateBlockRelaSchema
 * JD-Core Version:    0.5.4
 */