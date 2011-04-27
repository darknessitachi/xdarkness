 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class ZCDeployJobSchema extends Schema
 {
   private Long ID;
   private Long ConfigID;
   private Long SiteID;
   private String Source;
   private String Target;
   private String Method;
   private String Operation;
   private String Host;
   private Long Port;
   private String UserName;
   private String Password;
   private Long Status;
   private Long RetryCount;
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
     new SchemaColumn("ConfigID", 7, 1, 0, 0, true, false), 
     new SchemaColumn("SiteID", 7, 2, 0, 0, true, false), 
     new SchemaColumn("Source", 1, 3, 255, 0, false, false), 
     new SchemaColumn("Target", 1, 4, 255, 0, false, false), 
     new SchemaColumn("Method", 1, 5, 50, 0, false, false), 
     new SchemaColumn("Operation", 1, 6, 100, 0, false, false), 
     new SchemaColumn("Host", 1, 7, 255, 0, false, false), 
     new SchemaColumn("Port", 7, 8, 0, 0, false, false), 
     new SchemaColumn("UserName", 1, 9, 100, 0, false, false), 
     new SchemaColumn("Password", 1, 10, 100, 0, false, false), 
     new SchemaColumn("Status", 7, 11, 0, 0, false, false), 
     new SchemaColumn("RetryCount", 7, 12, 0, 0, false, false), 
     new SchemaColumn("Prop1", 1, 13, 50, 0, false, false), 
     new SchemaColumn("Prop2", 1, 14, 50, 0, false, false), 
     new SchemaColumn("Prop3", 1, 15, 50, 0, false, false), 
     new SchemaColumn("Prop4", 1, 16, 50, 0, false, false), 
     new SchemaColumn("AddUser", 1, 17, 200, 0, true, false), 
     new SchemaColumn("AddTime", 0, 18, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 19, 200, 0, false, false), 
     new SchemaColumn("ModifyTime", 0, 20, 0, 0, false, false) };
   public static final String _TableCode = "ZCDeployJob";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into ZCDeployJob values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update ZCDeployJob set ID=?,ConfigID=?,SiteID=?,Source=?,Target=?,Method=?,Operation=?,Host=?,Port=?,UserName=?,Password=?,Status=?,RetryCount=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
   protected static final String _DeleteSQL = "delete from ZCDeployJob  where ID=?";
   protected static final String _FillAllSQL = "select * from ZCDeployJob  where ID=?";
 
   public ZCDeployJobSchema()
   {
     this.TableCode = "ZCDeployJob";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into ZCDeployJob values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZCDeployJob set ID=?,ConfigID=?,SiteID=?,Source=?,Target=?,Method=?,Operation=?,Host=?,Port=?,UserName=?,Password=?,Status=?,RetryCount=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.DeleteSQL = "delete from ZCDeployJob  where ID=?";
     this.FillAllSQL = "select * from ZCDeployJob  where ID=?";
     this.HasSetFlag = new boolean[21];
   }
 
   protected Schema newInstance() {
     return new ZCDeployJobSchema();
   }
 
   protected SchemaSet newSet() {
     return new ZCDeployJobSet();
   }
 
   public ZCDeployJobSet query() {
     return query(null, -1, -1);
   }
 
   public ZCDeployJobSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public ZCDeployJobSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public ZCDeployJobSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (ZCDeployJobSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
     if (i == 1) { if (v == null) this.ConfigID = null; else this.ConfigID = new Long(v.toString()); return; }
     if (i == 2) { if (v == null) this.SiteID = null; else this.SiteID = new Long(v.toString()); return; }
     if (i == 3) { this.Source = ((String)v); return; }
     if (i == 4) { this.Target = ((String)v); return; }
     if (i == 5) { this.Method = ((String)v); return; }
     if (i == 6) { this.Operation = ((String)v); return; }
     if (i == 7) { this.Host = ((String)v); return; }
     if (i == 8) { if (v == null) this.Port = null; else this.Port = new Long(v.toString()); return; }
     if (i == 9) { this.UserName = ((String)v); return; }
     if (i == 10) { this.Password = ((String)v); return; }
     if (i == 11) { if (v == null) this.Status = null; else this.Status = new Long(v.toString()); return; }
     if (i == 12) { if (v == null) this.RetryCount = null; else this.RetryCount = new Long(v.toString()); return; }
     if (i == 13) { this.Prop1 = ((String)v); return; }
     if (i == 14) { this.Prop2 = ((String)v); return; }
     if (i == 15) { this.Prop3 = ((String)v); return; }
     if (i == 16) { this.Prop4 = ((String)v); return; }
     if (i == 17) { this.AddUser = ((String)v); return; }
     if (i == 18) { this.AddTime = ((Date)v); return; }
     if (i == 19) { this.ModifyUser = ((String)v); return; }
     if (i != 20) return; this.ModifyTime = ((Date)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.ID;
     if (i == 1) return this.ConfigID;
     if (i == 2) return this.SiteID;
     if (i == 3) return this.Source;
     if (i == 4) return this.Target;
     if (i == 5) return this.Method;
     if (i == 6) return this.Operation;
     if (i == 7) return this.Host;
     if (i == 8) return this.Port;
     if (i == 9) return this.UserName;
     if (i == 10) return this.Password;
     if (i == 11) return this.Status;
     if (i == 12) return this.RetryCount;
     if (i == 13) return this.Prop1;
     if (i == 14) return this.Prop2;
     if (i == 15) return this.Prop3;
     if (i == 16) return this.Prop4;
     if (i == 17) return this.AddUser;
     if (i == 18) return this.AddTime;
     if (i == 19) return this.ModifyUser;
     if (i == 20) return this.ModifyTime;
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
 
   public long getConfigID()
   {
     if (this.ConfigID == null) return 0L;
     return this.ConfigID.longValue();
   }
 
   public void setConfigID(long configID)
   {
     this.ConfigID = new Long(configID);
   }
 
   public void setConfigID(String configID)
   {
     if (configID == null) {
       this.ConfigID = null;
       return;
     }
     this.ConfigID = new Long(configID);
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
 
   public String getSource()
   {
     return this.Source;
   }
 
   public void setSource(String source)
   {
     this.Source = source;
   }
 
   public String getTarget()
   {
     return this.Target;
   }
 
   public void setTarget(String target)
   {
     this.Target = target;
   }
 
   public String getMethod()
   {
     return this.Method;
   }
 
   public void setMethod(String method)
   {
     this.Method = method;
   }
 
   public String getOperation()
   {
     return this.Operation;
   }
 
   public void setOperation(String operation)
   {
     this.Operation = operation;
   }
 
   public String getHost()
   {
     return this.Host;
   }
 
   public void setHost(String host)
   {
     this.Host = host;
   }
 
   public long getPort()
   {
     if (this.Port == null) return 0L;
     return this.Port.longValue();
   }
 
   public void setPort(long port)
   {
     this.Port = new Long(port);
   }
 
   public void setPort(String port)
   {
     if (port == null) {
       this.Port = null;
       return;
     }
     this.Port = new Long(port);
   }
 
   public String getUserName()
   {
     return this.UserName;
   }
 
   public void setUserName(String userName)
   {
     this.UserName = userName;
   }
 
   public String getPassword()
   {
     return this.Password;
   }
 
   public void setPassword(String password)
   {
     this.Password = password;
   }
 
   public long getStatus()
   {
     if (this.Status == null) return 0L;
     return this.Status.longValue();
   }
 
   public void setStatus(long status)
   {
     this.Status = new Long(status);
   }
 
   public void setStatus(String status)
   {
     if (status == null) {
       this.Status = null;
       return;
     }
     this.Status = new Long(status);
   }
 
   public long getRetryCount()
   {
     if (this.RetryCount == null) return 0L;
     return this.RetryCount.longValue();
   }
 
   public void setRetryCount(long retryCount)
   {
     this.RetryCount = new Long(retryCount);
   }
 
   public void setRetryCount(String retryCount)
   {
     if (retryCount == null) {
       this.RetryCount = null;
       return;
     }
     this.RetryCount = new Long(retryCount);
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
 * Qualified Name:     com.zving.schema.ZCDeployJobSchema
 * JD-Core Version:    0.5.4
 */