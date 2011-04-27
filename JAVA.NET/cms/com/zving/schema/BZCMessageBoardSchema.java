 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class BZCMessageBoardSchema extends Schema
 {
   private Long ID;
   private Long SiteID;
   private String Name;
   private String IsOpen;
   private String Description;
   private String MessageCount;
   private Long RelaCatalogID;
   private String Prop1;
   private String Prop2;
   private String Prop3;
   private String Prop4;
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
     new SchemaColumn("SiteID", 7, 1, 0, 0, true, false), 
     new SchemaColumn("Name", 1, 2, 100, 0, true, false), 
     new SchemaColumn("IsOpen", 1, 3, 2, 0, true, false), 
     new SchemaColumn("Description", 1, 4, 500, 0, false, false), 
     new SchemaColumn("MessageCount", 1, 5, 20, 0, false, false), 
     new SchemaColumn("RelaCatalogID", 7, 6, 0, 0, false, false), 
     new SchemaColumn("Prop1", 1, 7, 50, 0, false, false), 
     new SchemaColumn("Prop2", 1, 8, 50, 0, false, false), 
     new SchemaColumn("Prop3", 1, 9, 50, 0, false, false), 
     new SchemaColumn("Prop4", 1, 10, 50, 0, false, false), 
     new SchemaColumn("AddUser", 1, 11, 200, 0, true, false), 
     new SchemaColumn("AddTime", 0, 12, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 13, 200, 0, false, false), 
     new SchemaColumn("ModifyTime", 0, 14, 0, 0, false, false), 
     new SchemaColumn("BackupNo", 1, 15, 15, 0, true, true), 
     new SchemaColumn("BackupOperator", 1, 16, 200, 0, true, false), 
     new SchemaColumn("BackupTime", 0, 17, 0, 0, true, false), 
     new SchemaColumn("BackupMemo", 1, 18, 50, 0, false, false) };
   public static final String _TableCode = "BZCMessageBoard";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into BZCMessageBoard values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update BZCMessageBoard set ID=?,SiteID=?,Name=?,IsOpen=?,Description=?,MessageCount=?,RelaCatalogID=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
   protected static final String _DeleteSQL = "delete from BZCMessageBoard  where ID=? and BackupNo=?";
   protected static final String _FillAllSQL = "select * from BZCMessageBoard  where ID=? and BackupNo=?";
 
   public BZCMessageBoardSchema()
   {
     this.TableCode = "BZCMessageBoard";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into BZCMessageBoard values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZCMessageBoard set ID=?,SiteID=?,Name=?,IsOpen=?,Description=?,MessageCount=?,RelaCatalogID=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZCMessageBoard  where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZCMessageBoard  where ID=? and BackupNo=?";
     this.HasSetFlag = new boolean[19];
   }
 
   protected Schema newInstance() {
     return new BZCMessageBoardSchema();
   }
 
   protected SchemaSet newSet() {
     return new BZCMessageBoardSet();
   }
 
   public BZCMessageBoardSet query() {
     return query(null, -1, -1);
   }
 
   public BZCMessageBoardSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public BZCMessageBoardSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public BZCMessageBoardSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (BZCMessageBoardSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
     if (i == 1) { if (v == null) this.SiteID = null; else this.SiteID = new Long(v.toString()); return; }
     if (i == 2) { this.Name = ((String)v); return; }
     if (i == 3) { this.IsOpen = ((String)v); return; }
     if (i == 4) { this.Description = ((String)v); return; }
     if (i == 5) { this.MessageCount = ((String)v); return; }
     if (i == 6) { if (v == null) this.RelaCatalogID = null; else this.RelaCatalogID = new Long(v.toString()); return; }
     if (i == 7) { this.Prop1 = ((String)v); return; }
     if (i == 8) { this.Prop2 = ((String)v); return; }
     if (i == 9) { this.Prop3 = ((String)v); return; }
     if (i == 10) { this.Prop4 = ((String)v); return; }
     if (i == 11) { this.AddUser = ((String)v); return; }
     if (i == 12) { this.AddTime = ((Date)v); return; }
     if (i == 13) { this.ModifyUser = ((String)v); return; }
     if (i == 14) { this.ModifyTime = ((Date)v); return; }
     if (i == 15) { this.BackupNo = ((String)v); return; }
     if (i == 16) { this.BackupOperator = ((String)v); return; }
     if (i == 17) { this.BackupTime = ((Date)v); return; }
     if (i != 18) return; this.BackupMemo = ((String)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.ID;
     if (i == 1) return this.SiteID;
     if (i == 2) return this.Name;
     if (i == 3) return this.IsOpen;
     if (i == 4) return this.Description;
     if (i == 5) return this.MessageCount;
     if (i == 6) return this.RelaCatalogID;
     if (i == 7) return this.Prop1;
     if (i == 8) return this.Prop2;
     if (i == 9) return this.Prop3;
     if (i == 10) return this.Prop4;
     if (i == 11) return this.AddUser;
     if (i == 12) return this.AddTime;
     if (i == 13) return this.ModifyUser;
     if (i == 14) return this.ModifyTime;
     if (i == 15) return this.BackupNo;
     if (i == 16) return this.BackupOperator;
     if (i == 17) return this.BackupTime;
     if (i == 18) return this.BackupMemo;
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
 
   public String getIsOpen()
   {
     return this.IsOpen;
   }
 
   public void setIsOpen(String isOpen)
   {
     this.IsOpen = isOpen;
   }
 
   public String getDescription()
   {
     return this.Description;
   }
 
   public void setDescription(String description)
   {
     this.Description = description;
   }
 
   public String getMessageCount()
   {
     return this.MessageCount;
   }
 
   public void setMessageCount(String messageCount)
   {
     this.MessageCount = messageCount;
   }
 
   public long getRelaCatalogID()
   {
     if (this.RelaCatalogID == null) return 0L;
     return this.RelaCatalogID.longValue();
   }
 
   public void setRelaCatalogID(long relaCatalogID)
   {
     this.RelaCatalogID = new Long(relaCatalogID);
   }
 
   public void setRelaCatalogID(String relaCatalogID)
   {
     if (relaCatalogID == null) {
       this.RelaCatalogID = null;
       return;
     }
     this.RelaCatalogID = new Long(relaCatalogID);
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
 * Qualified Name:     com.zving.schema.BZCMessageBoardSchema
 * JD-Core Version:    0.5.4
 */