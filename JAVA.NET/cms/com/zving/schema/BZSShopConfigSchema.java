 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class BZSShopConfigSchema extends Schema
 {
   private Long SiteID;
   private String Name;
   private String Info;
   private String prop1;
   private String prop2;
   private String prop3;
   private String prop4;
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
     new SchemaColumn("Name", 1, 1, 50, 0, false, false), 
     new SchemaColumn("Info", 1, 2, 1024, 0, false, false), 
     new SchemaColumn("prop1", 1, 3, 50, 0, false, false), 
     new SchemaColumn("prop2", 1, 4, 50, 0, false, false), 
     new SchemaColumn("prop3", 1, 5, 50, 0, false, false), 
     new SchemaColumn("prop4", 1, 6, 50, 0, false, false), 
     new SchemaColumn("AddUser", 1, 7, 100, 0, true, false), 
     new SchemaColumn("AddTime", 0, 8, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 9, 100, 0, false, false), 
     new SchemaColumn("ModifyTime", 0, 10, 0, 0, false, false), 
     new SchemaColumn("BackupNo", 1, 11, 15, 0, true, true), 
     new SchemaColumn("BackupOperator", 1, 12, 200, 0, true, false), 
     new SchemaColumn("BackupTime", 0, 13, 0, 0, true, false), 
     new SchemaColumn("BackupMemo", 1, 14, 50, 0, false, false) };
   public static final String _TableCode = "BZSShopConfig";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into BZSShopConfig values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update BZSShopConfig set SiteID=?,Name=?,Info=?,prop1=?,prop2=?,prop3=?,prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where SiteID=? and BackupNo=?";
   protected static final String _DeleteSQL = "delete from BZSShopConfig  where SiteID=? and BackupNo=?";
   protected static final String _FillAllSQL = "select * from BZSShopConfig  where SiteID=? and BackupNo=?";
 
   public BZSShopConfigSchema()
   {
     this.TableCode = "BZSShopConfig";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into BZSShopConfig values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZSShopConfig set SiteID=?,Name=?,Info=?,prop1=?,prop2=?,prop3=?,prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where SiteID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZSShopConfig  where SiteID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZSShopConfig  where SiteID=? and BackupNo=?";
     this.HasSetFlag = new boolean[15];
   }
 
   protected Schema newInstance() {
     return new BZSShopConfigSchema();
   }
 
   protected SchemaSet newSet() {
     return new BZSShopConfigSet();
   }
 
   public BZSShopConfigSet query() {
     return query(null, -1, -1);
   }
 
   public BZSShopConfigSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public BZSShopConfigSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public BZSShopConfigSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (BZSShopConfigSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.SiteID = null; else this.SiteID = new Long(v.toString()); return; }
     if (i == 1) { this.Name = ((String)v); return; }
     if (i == 2) { this.Info = ((String)v); return; }
     if (i == 3) { this.prop1 = ((String)v); return; }
     if (i == 4) { this.prop2 = ((String)v); return; }
     if (i == 5) { this.prop3 = ((String)v); return; }
     if (i == 6) { this.prop4 = ((String)v); return; }
     if (i == 7) { this.AddUser = ((String)v); return; }
     if (i == 8) { this.AddTime = ((Date)v); return; }
     if (i == 9) { this.ModifyUser = ((String)v); return; }
     if (i == 10) { this.ModifyTime = ((Date)v); return; }
     if (i == 11) { this.BackupNo = ((String)v); return; }
     if (i == 12) { this.BackupOperator = ((String)v); return; }
     if (i == 13) { this.BackupTime = ((Date)v); return; }
     if (i != 14) return; this.BackupMemo = ((String)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.SiteID;
     if (i == 1) return this.Name;
     if (i == 2) return this.Info;
     if (i == 3) return this.prop1;
     if (i == 4) return this.prop2;
     if (i == 5) return this.prop3;
     if (i == 6) return this.prop4;
     if (i == 7) return this.AddUser;
     if (i == 8) return this.AddTime;
     if (i == 9) return this.ModifyUser;
     if (i == 10) return this.ModifyTime;
     if (i == 11) return this.BackupNo;
     if (i == 12) return this.BackupOperator;
     if (i == 13) return this.BackupTime;
     if (i == 14) return this.BackupMemo;
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
 
   public String getName()
   {
     return this.Name;
   }
 
   public void setName(String name)
   {
     this.Name = name;
   }
 
   public String getInfo()
   {
     return this.Info;
   }
 
   public void setInfo(String info)
   {
     this.Info = info;
   }
 
   public String getProp1()
   {
     return this.prop1;
   }
 
   public void setProp1(String prop1)
   {
     this.prop1 = prop1;
   }
 
   public String getProp2()
   {
     return this.prop2;
   }
 
   public void setProp2(String prop2)
   {
     this.prop2 = prop2;
   }
 
   public String getProp3()
   {
     return this.prop3;
   }
 
   public void setProp3(String prop3)
   {
     this.prop3 = prop3;
   }
 
   public String getProp4()
   {
     return this.prop4;
   }
 
   public void setProp4(String prop4)
   {
     this.prop4 = prop4;
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
 * Qualified Name:     com.zving.schema.BZSShopConfigSchema
 * JD-Core Version:    0.5.4
 */