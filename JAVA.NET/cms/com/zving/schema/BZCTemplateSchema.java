 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class BZCTemplateSchema extends Schema
 {
   private Long ID;
   private String Code;
   private Long SiteID;
   private String Name;
   private String FileName;
   private String Type;
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
     new SchemaColumn("ID", 7, 0, 0, 0, true, true), 
     new SchemaColumn("Code", 1, 1, 100, 0, true, false), 
     new SchemaColumn("SiteID", 7, 2, 0, 0, true, false), 
     new SchemaColumn("Name", 1, 3, 100, 0, true, false), 
     new SchemaColumn("FileName", 1, 4, 100, 0, true, false), 
     new SchemaColumn("Type", 1, 5, 2, 0, false, false), 
     new SchemaColumn("Prop1", 1, 6, 50, 0, false, false), 
     new SchemaColumn("Prop2", 1, 7, 50, 0, false, false), 
     new SchemaColumn("AddUser", 1, 8, 200, 0, true, false), 
     new SchemaColumn("AddTime", 0, 9, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 10, 200, 0, true, false), 
     new SchemaColumn("ModifyTime", 0, 11, 0, 0, true, false), 
     new SchemaColumn("BackupNo", 1, 12, 15, 0, true, true), 
     new SchemaColumn("BackupOperator", 1, 13, 200, 0, true, false), 
     new SchemaColumn("BackupTime", 0, 14, 0, 0, true, false), 
     new SchemaColumn("BackupMemo", 1, 15, 50, 0, false, false) };
   public static final String _TableCode = "BZCTemplate";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into BZCTemplate values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update BZCTemplate set ID=?,Code=?,SiteID=?,Name=?,FileName=?,Type=?,Prop1=?,Prop2=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
   protected static final String _DeleteSQL = "delete from BZCTemplate  where ID=? and BackupNo=?";
   protected static final String _FillAllSQL = "select * from BZCTemplate  where ID=? and BackupNo=?";
 
   public BZCTemplateSchema()
   {
     this.TableCode = "BZCTemplate";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into BZCTemplate values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZCTemplate set ID=?,Code=?,SiteID=?,Name=?,FileName=?,Type=?,Prop1=?,Prop2=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZCTemplate  where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZCTemplate  where ID=? and BackupNo=?";
     this.HasSetFlag = new boolean[16];
   }
 
   protected Schema newInstance() {
     return new BZCTemplateSchema();
   }
 
   protected SchemaSet newSet() {
     return new BZCTemplateSet();
   }
 
   public BZCTemplateSet query() {
     return query(null, -1, -1);
   }
 
   public BZCTemplateSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public BZCTemplateSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public BZCTemplateSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (BZCTemplateSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
     if (i == 1) { this.Code = ((String)v); return; }
     if (i == 2) { if (v == null) this.SiteID = null; else this.SiteID = new Long(v.toString()); return; }
     if (i == 3) { this.Name = ((String)v); return; }
     if (i == 4) { this.FileName = ((String)v); return; }
     if (i == 5) { this.Type = ((String)v); return; }
     if (i == 6) { this.Prop1 = ((String)v); return; }
     if (i == 7) { this.Prop2 = ((String)v); return; }
     if (i == 8) { this.AddUser = ((String)v); return; }
     if (i == 9) { this.AddTime = ((Date)v); return; }
     if (i == 10) { this.ModifyUser = ((String)v); return; }
     if (i == 11) { this.ModifyTime = ((Date)v); return; }
     if (i == 12) { this.BackupNo = ((String)v); return; }
     if (i == 13) { this.BackupOperator = ((String)v); return; }
     if (i == 14) { this.BackupTime = ((Date)v); return; }
     if (i != 15) return; this.BackupMemo = ((String)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.ID;
     if (i == 1) return this.Code;
     if (i == 2) return this.SiteID;
     if (i == 3) return this.Name;
     if (i == 4) return this.FileName;
     if (i == 5) return this.Type;
     if (i == 6) return this.Prop1;
     if (i == 7) return this.Prop2;
     if (i == 8) return this.AddUser;
     if (i == 9) return this.AddTime;
     if (i == 10) return this.ModifyUser;
     if (i == 11) return this.ModifyTime;
     if (i == 12) return this.BackupNo;
     if (i == 13) return this.BackupOperator;
     if (i == 14) return this.BackupTime;
     if (i == 15) return this.BackupMemo;
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
 
   public String getCode()
   {
     return this.Code;
   }
 
   public void setCode(String code)
   {
     this.Code = code;
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
 
   public String getFileName()
   {
     return this.FileName;
   }
 
   public void setFileName(String fileName)
   {
     this.FileName = fileName;
   }
 
   public String getType()
   {
     return this.Type;
   }
 
   public void setType(String type)
   {
     this.Type = type;
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
 * Qualified Name:     com.zving.schema.BZCTemplateSchema
 * JD-Core Version:    0.5.4
 */