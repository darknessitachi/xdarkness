 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class ZDColumnRelaSchema extends Schema
 {
   private Long ID;
   private Long ColumnID;
   private String ColumnCode;
   private String RelaType;
   private String RelaID;
   private String AddUser;
   private Date AddTime;
   private String ModifyUser;
   private Date ModifyTime;
   public static final SchemaColumn[] _Columns = { 
     new SchemaColumn("ID", 7, 0, 0, 0, true, true), 
     new SchemaColumn("ColumnID", 7, 1, 0, 0, true, false), 
     new SchemaColumn("ColumnCode", 1, 2, 100, 0, true, false), 
     new SchemaColumn("RelaType", 1, 3, 2, 0, false, false), 
     new SchemaColumn("RelaID", 1, 4, 100, 0, false, false), 
     new SchemaColumn("AddUser", 1, 5, 200, 0, true, false), 
     new SchemaColumn("AddTime", 0, 6, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 7, 200, 0, false, false), 
     new SchemaColumn("ModifyTime", 0, 8, 0, 0, false, false) };
   public static final String _TableCode = "ZDColumnRela";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into ZDColumnRela values(?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update ZDColumnRela set ID=?,ColumnID=?,ColumnCode=?,RelaType=?,RelaID=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
   protected static final String _DeleteSQL = "delete from ZDColumnRela  where ID=?";
   protected static final String _FillAllSQL = "select * from ZDColumnRela  where ID=?";
 
   public ZDColumnRelaSchema()
   {
     this.TableCode = "ZDColumnRela";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into ZDColumnRela values(?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZDColumnRela set ID=?,ColumnID=?,ColumnCode=?,RelaType=?,RelaID=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.DeleteSQL = "delete from ZDColumnRela  where ID=?";
     this.FillAllSQL = "select * from ZDColumnRela  where ID=?";
     this.HasSetFlag = new boolean[9];
   }
 
   protected Schema newInstance() {
     return new ZDColumnRelaSchema();
   }
 
   protected SchemaSet newSet() {
     return new ZDColumnRelaSet();
   }
 
   public ZDColumnRelaSet query() {
     return query(null, -1, -1);
   }
 
   public ZDColumnRelaSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public ZDColumnRelaSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public ZDColumnRelaSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (ZDColumnRelaSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
     if (i == 1) { if (v == null) this.ColumnID = null; else this.ColumnID = new Long(v.toString()); return; }
     if (i == 2) { this.ColumnCode = ((String)v); return; }
     if (i == 3) { this.RelaType = ((String)v); return; }
     if (i == 4) { this.RelaID = ((String)v); return; }
     if (i == 5) { this.AddUser = ((String)v); return; }
     if (i == 6) { this.AddTime = ((Date)v); return; }
     if (i == 7) { this.ModifyUser = ((String)v); return; }
     if (i != 8) return; this.ModifyTime = ((Date)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.ID;
     if (i == 1) return this.ColumnID;
     if (i == 2) return this.ColumnCode;
     if (i == 3) return this.RelaType;
     if (i == 4) return this.RelaID;
     if (i == 5) return this.AddUser;
     if (i == 6) return this.AddTime;
     if (i == 7) return this.ModifyUser;
     if (i == 8) return this.ModifyTime;
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
 
   public long getColumnID()
   {
     if (this.ColumnID == null) return 0L;
     return this.ColumnID.longValue();
   }
 
   public void setColumnID(long columnID)
   {
     this.ColumnID = new Long(columnID);
   }
 
   public void setColumnID(String columnID)
   {
     if (columnID == null) {
       this.ColumnID = null;
       return;
     }
     this.ColumnID = new Long(columnID);
   }
 
   public String getColumnCode()
   {
     return this.ColumnCode;
   }
 
   public void setColumnCode(String columnCode)
   {
     this.ColumnCode = columnCode;
   }
 
   public String getRelaType()
   {
     return this.RelaType;
   }
 
   public void setRelaType(String relaType)
   {
     this.RelaType = relaType;
   }
 
   public String getRelaID()
   {
     return this.RelaID;
   }
 
   public void setRelaID(String relaID)
   {
     this.RelaID = relaID;
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
 * Qualified Name:     com.zving.schema.ZDColumnRelaSchema
 * JD-Core Version:    0.5.4
 */