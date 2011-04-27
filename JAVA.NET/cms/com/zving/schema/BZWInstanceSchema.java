 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class BZWInstanceSchema extends Schema
 {
   private Long ID;
   private Long WorkflowID;
   private String Name;
   private String DataID;
   private String State;
   private String Memo;
   private Date AddTime;
   private String AddUser;
   private String BackupNo;
   private String BackupOperator;
   private Date BackupTime;
   private String BackupMemo;
   public static final SchemaColumn[] _Columns = { 
     new SchemaColumn("ID", 7, 0, 0, 0, true, true), 
     new SchemaColumn("WorkflowID", 7, 1, 0, 0, true, false), 
     new SchemaColumn("Name", 1, 2, 100, 0, false, false), 
     new SchemaColumn("DataID", 1, 3, 30, 0, false, false), 
     new SchemaColumn("State", 1, 4, 10, 0, false, false), 
     new SchemaColumn("Memo", 1, 5, 100, 0, false, false), 
     new SchemaColumn("AddTime", 0, 6, 0, 0, true, false), 
     new SchemaColumn("AddUser", 1, 7, 50, 0, true, false), 
     new SchemaColumn("BackupNo", 1, 8, 15, 0, true, true), 
     new SchemaColumn("BackupOperator", 1, 9, 200, 0, true, false), 
     new SchemaColumn("BackupTime", 0, 10, 0, 0, true, false), 
     new SchemaColumn("BackupMemo", 1, 11, 50, 0, false, false) };
   public static final String _TableCode = "BZWInstance";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into BZWInstance values(?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update BZWInstance set ID=?,WorkflowID=?,Name=?,DataID=?,State=?,Memo=?,AddTime=?,AddUser=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
   protected static final String _DeleteSQL = "delete from BZWInstance  where ID=? and BackupNo=?";
   protected static final String _FillAllSQL = "select * from BZWInstance  where ID=? and BackupNo=?";
 
   public BZWInstanceSchema()
   {
     this.TableCode = "BZWInstance";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into BZWInstance values(?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZWInstance set ID=?,WorkflowID=?,Name=?,DataID=?,State=?,Memo=?,AddTime=?,AddUser=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZWInstance  where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZWInstance  where ID=? and BackupNo=?";
     this.HasSetFlag = new boolean[12];
   }
 
   protected Schema newInstance() {
     return new BZWInstanceSchema();
   }
 
   protected SchemaSet newSet() {
     return new BZWInstanceSet();
   }
 
   public BZWInstanceSet query() {
     return query(null, -1, -1);
   }
 
   public BZWInstanceSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public BZWInstanceSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public BZWInstanceSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (BZWInstanceSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
     if (i == 1) { if (v == null) this.WorkflowID = null; else this.WorkflowID = new Long(v.toString()); return; }
     if (i == 2) { this.Name = ((String)v); return; }
     if (i == 3) { this.DataID = ((String)v); return; }
     if (i == 4) { this.State = ((String)v); return; }
     if (i == 5) { this.Memo = ((String)v); return; }
     if (i == 6) { this.AddTime = ((Date)v); return; }
     if (i == 7) { this.AddUser = ((String)v); return; }
     if (i == 8) { this.BackupNo = ((String)v); return; }
     if (i == 9) { this.BackupOperator = ((String)v); return; }
     if (i == 10) { this.BackupTime = ((Date)v); return; }
     if (i != 11) return; this.BackupMemo = ((String)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.ID;
     if (i == 1) return this.WorkflowID;
     if (i == 2) return this.Name;
     if (i == 3) return this.DataID;
     if (i == 4) return this.State;
     if (i == 5) return this.Memo;
     if (i == 6) return this.AddTime;
     if (i == 7) return this.AddUser;
     if (i == 8) return this.BackupNo;
     if (i == 9) return this.BackupOperator;
     if (i == 10) return this.BackupTime;
     if (i == 11) return this.BackupMemo;
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
 
   public long getWorkflowID()
   {
     if (this.WorkflowID == null) return 0L;
     return this.WorkflowID.longValue();
   }
 
   public void setWorkflowID(long workflowID)
   {
     this.WorkflowID = new Long(workflowID);
   }
 
   public void setWorkflowID(String workflowID)
   {
     if (workflowID == null) {
       this.WorkflowID = null;
       return;
     }
     this.WorkflowID = new Long(workflowID);
   }
 
   public String getName()
   {
     return this.Name;
   }
 
   public void setName(String name)
   {
     this.Name = name;
   }
 
   public String getDataID()
   {
     return this.DataID;
   }
 
   public void setDataID(String dataID)
   {
     this.DataID = dataID;
   }
 
   public String getState()
   {
     return this.State;
   }
 
   public void setState(String state)
   {
     this.State = state;
   }
 
   public String getMemo()
   {
     return this.Memo;
   }
 
   public void setMemo(String memo)
   {
     this.Memo = memo;
   }
 
   public Date getAddTime()
   {
     return this.AddTime;
   }
 
   public void setAddTime(Date addTime)
   {
     this.AddTime = addTime;
   }
 
   public String getAddUser()
   {
     return this.AddUser;
   }
 
   public void setAddUser(String addUser)
   {
     this.AddUser = addUser;
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
 * Qualified Name:     com.zving.schema.BZWInstanceSchema
 * JD-Core Version:    0.5.4
 */