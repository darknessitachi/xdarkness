 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class ZWInstanceSchema extends Schema
 {
   private Long ID;
   private Long WorkflowID;
   private String Name;
   private String DataID;
   private String State;
   private String Memo;
   private Date AddTime;
   private String AddUser;
   public static final SchemaColumn[] _Columns = { 
     new SchemaColumn("ID", 7, 0, 0, 0, true, true), 
     new SchemaColumn("WorkflowID", 7, 1, 0, 0, true, false), 
     new SchemaColumn("Name", 1, 2, 100, 0, false, false), 
     new SchemaColumn("DataID", 1, 3, 30, 0, false, false), 
     new SchemaColumn("State", 1, 4, 10, 0, false, false), 
     new SchemaColumn("Memo", 1, 5, 100, 0, false, false), 
     new SchemaColumn("AddTime", 0, 6, 0, 0, true, false), 
     new SchemaColumn("AddUser", 1, 7, 50, 0, true, false) };
   public static final String _TableCode = "ZWInstance";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into ZWInstance values(?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update ZWInstance set ID=?,WorkflowID=?,Name=?,DataID=?,State=?,Memo=?,AddTime=?,AddUser=? where ID=?";
   protected static final String _DeleteSQL = "delete from ZWInstance  where ID=?";
   protected static final String _FillAllSQL = "select * from ZWInstance  where ID=?";
 
   public ZWInstanceSchema()
   {
     this.TableCode = "ZWInstance";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into ZWInstance values(?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZWInstance set ID=?,WorkflowID=?,Name=?,DataID=?,State=?,Memo=?,AddTime=?,AddUser=? where ID=?";
     this.DeleteSQL = "delete from ZWInstance  where ID=?";
     this.FillAllSQL = "select * from ZWInstance  where ID=?";
     this.HasSetFlag = new boolean[8];
   }
 
   protected Schema newInstance() {
     return new ZWInstanceSchema();
   }
 
   protected SchemaSet newSet() {
     return new ZWInstanceSet();
   }
 
   public ZWInstanceSet query() {
     return query(null, -1, -1);
   }
 
   public ZWInstanceSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public ZWInstanceSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public ZWInstanceSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (ZWInstanceSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
     if (i == 1) { if (v == null) this.WorkflowID = null; else this.WorkflowID = new Long(v.toString()); return; }
     if (i == 2) { this.Name = ((String)v); return; }
     if (i == 3) { this.DataID = ((String)v); return; }
     if (i == 4) { this.State = ((String)v); return; }
     if (i == 5) { this.Memo = ((String)v); return; }
     if (i == 6) { this.AddTime = ((Date)v); return; }
     if (i != 7) return; this.AddUser = ((String)v); return;
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
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZWInstanceSchema
 * JD-Core Version:    0.5.4
 */