 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class ZWStepSchema extends Schema
 {
   private Long ID;
   private Long WorkflowID;
   private Long InstanceID;
   private String DataVersionID;
   private Integer NodeID;
   private Integer ActionID;
   private Long PreviousStepID;
   private String Owner;
   private Date StartTime;
   private Date FinishTime;
   private String State;
   private String Operators;
   private String AllowUser;
   private String AllowOrgan;
   private String AllowRole;
   private String Memo;
   private Date AddTime;
   private String AddUser;
   public static final SchemaColumn[] _Columns = { 
     new SchemaColumn("ID", 7, 0, 0, 0, true, true), 
     new SchemaColumn("WorkflowID", 7, 1, 0, 0, true, false), 
     new SchemaColumn("InstanceID", 7, 2, 0, 0, true, false), 
     new SchemaColumn("DataVersionID", 1, 3, 30, 0, false, false), 
     new SchemaColumn("NodeID", 8, 4, 0, 0, false, false), 
     new SchemaColumn("ActionID", 8, 5, 0, 0, false, false), 
     new SchemaColumn("PreviousStepID", 7, 6, 0, 0, false, false), 
     new SchemaColumn("Owner", 1, 7, 50, 0, false, false), 
     new SchemaColumn("StartTime", 0, 8, 0, 0, false, false), 
     new SchemaColumn("FinishTime", 0, 9, 0, 0, false, false), 
     new SchemaColumn("State", 1, 10, 10, 0, false, false), 
     new SchemaColumn("Operators", 1, 11, 400, 0, false, false), 
     new SchemaColumn("AllowUser", 1, 12, 4000, 0, false, false), 
     new SchemaColumn("AllowOrgan", 1, 13, 4000, 0, false, false), 
     new SchemaColumn("AllowRole", 1, 14, 4000, 0, false, false), 
     new SchemaColumn("Memo", 1, 15, 400, 0, false, false), 
     new SchemaColumn("AddTime", 0, 16, 0, 0, true, false), 
     new SchemaColumn("AddUser", 1, 17, 50, 0, true, false) };
   public static final String _TableCode = "ZWStep";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into ZWStep values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update ZWStep set ID=?,WorkflowID=?,InstanceID=?,DataVersionID=?,NodeID=?,ActionID=?,PreviousStepID=?,Owner=?,StartTime=?,FinishTime=?,State=?,Operators=?,AllowUser=?,AllowOrgan=?,AllowRole=?,Memo=?,AddTime=?,AddUser=? where ID=?";
   protected static final String _DeleteSQL = "delete from ZWStep  where ID=?";
   protected static final String _FillAllSQL = "select * from ZWStep  where ID=?";
 
   public ZWStepSchema()
   {
     this.TableCode = "ZWStep";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into ZWStep values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZWStep set ID=?,WorkflowID=?,InstanceID=?,DataVersionID=?,NodeID=?,ActionID=?,PreviousStepID=?,Owner=?,StartTime=?,FinishTime=?,State=?,Operators=?,AllowUser=?,AllowOrgan=?,AllowRole=?,Memo=?,AddTime=?,AddUser=? where ID=?";
     this.DeleteSQL = "delete from ZWStep  where ID=?";
     this.FillAllSQL = "select * from ZWStep  where ID=?";
     this.HasSetFlag = new boolean[18];
   }
 
   protected Schema newInstance() {
     return new ZWStepSchema();
   }
 
   protected SchemaSet newSet() {
     return new ZWStepSet();
   }
 
   public ZWStepSet query() {
     return query(null, -1, -1);
   }
 
   public ZWStepSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public ZWStepSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public ZWStepSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (ZWStepSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
     if (i == 1) { if (v == null) this.WorkflowID = null; else this.WorkflowID = new Long(v.toString()); return; }
     if (i == 2) { if (v == null) this.InstanceID = null; else this.InstanceID = new Long(v.toString()); return; }
     if (i == 3) { this.DataVersionID = ((String)v); return; }
     if (i == 4) { if (v == null) this.NodeID = null; else this.NodeID = new Integer(v.toString()); return; }
     if (i == 5) { if (v == null) this.ActionID = null; else this.ActionID = new Integer(v.toString()); return; }
     if (i == 6) { if (v == null) this.PreviousStepID = null; else this.PreviousStepID = new Long(v.toString()); return; }
     if (i == 7) { this.Owner = ((String)v); return; }
     if (i == 8) { this.StartTime = ((Date)v); return; }
     if (i == 9) { this.FinishTime = ((Date)v); return; }
     if (i == 10) { this.State = ((String)v); return; }
     if (i == 11) { this.Operators = ((String)v); return; }
     if (i == 12) { this.AllowUser = ((String)v); return; }
     if (i == 13) { this.AllowOrgan = ((String)v); return; }
     if (i == 14) { this.AllowRole = ((String)v); return; }
     if (i == 15) { this.Memo = ((String)v); return; }
     if (i == 16) { this.AddTime = ((Date)v); return; }
     if (i != 17) return; this.AddUser = ((String)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.ID;
     if (i == 1) return this.WorkflowID;
     if (i == 2) return this.InstanceID;
     if (i == 3) return this.DataVersionID;
     if (i == 4) return this.NodeID;
     if (i == 5) return this.ActionID;
     if (i == 6) return this.PreviousStepID;
     if (i == 7) return this.Owner;
     if (i == 8) return this.StartTime;
     if (i == 9) return this.FinishTime;
     if (i == 10) return this.State;
     if (i == 11) return this.Operators;
     if (i == 12) return this.AllowUser;
     if (i == 13) return this.AllowOrgan;
     if (i == 14) return this.AllowRole;
     if (i == 15) return this.Memo;
     if (i == 16) return this.AddTime;
     if (i == 17) return this.AddUser;
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
 
   public long getInstanceID()
   {
     if (this.InstanceID == null) return 0L;
     return this.InstanceID.longValue();
   }
 
   public void setInstanceID(long instanceID)
   {
     this.InstanceID = new Long(instanceID);
   }
 
   public void setInstanceID(String instanceID)
   {
     if (instanceID == null) {
       this.InstanceID = null;
       return;
     }
     this.InstanceID = new Long(instanceID);
   }
 
   public String getDataVersionID()
   {
     return this.DataVersionID;
   }
 
   public void setDataVersionID(String dataVersionID)
   {
     this.DataVersionID = dataVersionID;
   }
 
   public int getNodeID()
   {
     if (this.NodeID == null) return 0;
     return this.NodeID.intValue();
   }
 
   public void setNodeID(int nodeID)
   {
     this.NodeID = new Integer(nodeID);
   }
 
   public void setNodeID(String nodeID)
   {
     if (nodeID == null) {
       this.NodeID = null;
       return;
     }
     this.NodeID = new Integer(nodeID);
   }
 
   public int getActionID()
   {
     if (this.ActionID == null) return 0;
     return this.ActionID.intValue();
   }
 
   public void setActionID(int actionID)
   {
     this.ActionID = new Integer(actionID);
   }
 
   public void setActionID(String actionID)
   {
     if (actionID == null) {
       this.ActionID = null;
       return;
     }
     this.ActionID = new Integer(actionID);
   }
 
   public long getPreviousStepID()
   {
     if (this.PreviousStepID == null) return 0L;
     return this.PreviousStepID.longValue();
   }
 
   public void setPreviousStepID(long previousStepID)
   {
     this.PreviousStepID = new Long(previousStepID);
   }
 
   public void setPreviousStepID(String previousStepID)
   {
     if (previousStepID == null) {
       this.PreviousStepID = null;
       return;
     }
     this.PreviousStepID = new Long(previousStepID);
   }
 
   public String getOwner()
   {
     return this.Owner;
   }
 
   public void setOwner(String owner)
   {
     this.Owner = owner;
   }
 
   public Date getStartTime()
   {
     return this.StartTime;
   }
 
   public void setStartTime(Date startTime)
   {
     this.StartTime = startTime;
   }
 
   public Date getFinishTime()
   {
     return this.FinishTime;
   }
 
   public void setFinishTime(Date finishTime)
   {
     this.FinishTime = finishTime;
   }
 
   public String getState()
   {
     return this.State;
   }
 
   public void setState(String state)
   {
     this.State = state;
   }
 
   public String getOperators()
   {
     return this.Operators;
   }
 
   public void setOperators(String operators)
   {
     this.Operators = operators;
   }
 
   public String getAllowUser()
   {
     return this.AllowUser;
   }
 
   public void setAllowUser(String allowUser)
   {
     this.AllowUser = allowUser;
   }
 
   public String getAllowOrgan()
   {
     return this.AllowOrgan;
   }
 
   public void setAllowOrgan(String allowOrgan)
   {
     this.AllowOrgan = allowOrgan;
   }
 
   public String getAllowRole()
   {
     return this.AllowRole;
   }
 
   public void setAllowRole(String allowRole)
   {
     this.AllowRole = allowRole;
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
 * Qualified Name:     com.zving.schema.ZWStepSchema
 * JD-Core Version:    0.5.4
 */