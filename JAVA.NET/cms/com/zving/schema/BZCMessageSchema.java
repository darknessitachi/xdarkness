 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class BZCMessageSchema extends Schema
 {
   private Long ID;
   private String FromUser;
   private String ToUser;
   private String Box;
   private Long ReadFlag;
   private Long PopFlag;
   private String Subject;
   private String Content;
   private Date AddTime;
   private String BackupNo;
   private String BackupOperator;
   private Date BackupTime;
   private String BackupMemo;
   public static final SchemaColumn[] _Columns = { 
     new SchemaColumn("ID", 7, 0, 0, 0, true, true), 
     new SchemaColumn("FromUser", 1, 1, 50, 0, false, false), 
     new SchemaColumn("ToUser", 1, 2, 50, 0, false, false), 
     new SchemaColumn("Box", 1, 3, 10, 0, false, false), 
     new SchemaColumn("ReadFlag", 7, 4, 0, 0, false, false), 
     new SchemaColumn("PopFlag", 7, 5, 0, 0, false, false), 
     new SchemaColumn("Subject", 1, 6, 500, 0, false, false), 
     new SchemaColumn("Content", 10, 7, 0, 0, false, false), 
     new SchemaColumn("AddTime", 0, 8, 0, 0, false, false), 
     new SchemaColumn("BackupNo", 1, 9, 15, 0, true, true), 
     new SchemaColumn("BackupOperator", 1, 10, 200, 0, true, false), 
     new SchemaColumn("BackupTime", 0, 11, 0, 0, true, false), 
     new SchemaColumn("BackupMemo", 1, 12, 50, 0, false, false) };
   public static final String _TableCode = "BZCMessage";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into BZCMessage values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update BZCMessage set ID=?,FromUser=?,ToUser=?,Box=?,ReadFlag=?,PopFlag=?,Subject=?,Content=?,AddTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
   protected static final String _DeleteSQL = "delete from BZCMessage  where ID=? and BackupNo=?";
   protected static final String _FillAllSQL = "select * from BZCMessage  where ID=? and BackupNo=?";
 
   public BZCMessageSchema()
   {
     this.TableCode = "BZCMessage";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into BZCMessage values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZCMessage set ID=?,FromUser=?,ToUser=?,Box=?,ReadFlag=?,PopFlag=?,Subject=?,Content=?,AddTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZCMessage  where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZCMessage  where ID=? and BackupNo=?";
     this.HasSetFlag = new boolean[13];
   }
 
   protected Schema newInstance() {
     return new BZCMessageSchema();
   }
 
   protected SchemaSet newSet() {
     return new BZCMessageSet();
   }
 
   public BZCMessageSet query() {
     return query(null, -1, -1);
   }
 
   public BZCMessageSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public BZCMessageSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public BZCMessageSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (BZCMessageSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
     if (i == 1) { this.FromUser = ((String)v); return; }
     if (i == 2) { this.ToUser = ((String)v); return; }
     if (i == 3) { this.Box = ((String)v); return; }
     if (i == 4) { if (v == null) this.ReadFlag = null; else this.ReadFlag = new Long(v.toString()); return; }
     if (i == 5) { if (v == null) this.PopFlag = null; else this.PopFlag = new Long(v.toString()); return; }
     if (i == 6) { this.Subject = ((String)v); return; }
     if (i == 7) { this.Content = ((String)v); return; }
     if (i == 8) { this.AddTime = ((Date)v); return; }
     if (i == 9) { this.BackupNo = ((String)v); return; }
     if (i == 10) { this.BackupOperator = ((String)v); return; }
     if (i == 11) { this.BackupTime = ((Date)v); return; }
     if (i != 12) return; this.BackupMemo = ((String)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.ID;
     if (i == 1) return this.FromUser;
     if (i == 2) return this.ToUser;
     if (i == 3) return this.Box;
     if (i == 4) return this.ReadFlag;
     if (i == 5) return this.PopFlag;
     if (i == 6) return this.Subject;
     if (i == 7) return this.Content;
     if (i == 8) return this.AddTime;
     if (i == 9) return this.BackupNo;
     if (i == 10) return this.BackupOperator;
     if (i == 11) return this.BackupTime;
     if (i == 12) return this.BackupMemo;
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
 
   public String getFromUser()
   {
     return this.FromUser;
   }
 
   public void setFromUser(String fromUser)
   {
     this.FromUser = fromUser;
   }
 
   public String getToUser()
   {
     return this.ToUser;
   }
 
   public void setToUser(String toUser)
   {
     this.ToUser = toUser;
   }
 
   public String getBox()
   {
     return this.Box;
   }
 
   public void setBox(String box)
   {
     this.Box = box;
   }
 
   public long getReadFlag()
   {
     if (this.ReadFlag == null) return 0L;
     return this.ReadFlag.longValue();
   }
 
   public void setReadFlag(long readFlag)
   {
     this.ReadFlag = new Long(readFlag);
   }
 
   public void setReadFlag(String readFlag)
   {
     if (readFlag == null) {
       this.ReadFlag = null;
       return;
     }
     this.ReadFlag = new Long(readFlag);
   }
 
   public long getPopFlag()
   {
     if (this.PopFlag == null) return 0L;
     return this.PopFlag.longValue();
   }
 
   public void setPopFlag(long popFlag)
   {
     this.PopFlag = new Long(popFlag);
   }
 
   public void setPopFlag(String popFlag)
   {
     if (popFlag == null) {
       this.PopFlag = null;
       return;
     }
     this.PopFlag = new Long(popFlag);
   }
 
   public String getSubject()
   {
     return this.Subject;
   }
 
   public void setSubject(String subject)
   {
     this.Subject = subject;
   }
 
   public String getContent()
   {
     return this.Content;
   }
 
   public void setContent(String content)
   {
     this.Content = content;
   }
 
   public Date getAddTime()
   {
     return this.AddTime;
   }
 
   public void setAddTime(Date addTime)
   {
     this.AddTime = addTime;
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
 * Qualified Name:     com.zving.schema.BZCMessageSchema
 * JD-Core Version:    0.5.4
 */