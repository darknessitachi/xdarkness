 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class ZSShopConfigSchema extends Schema
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
     new SchemaColumn("ModifyTime", 0, 10, 0, 0, false, false) };
   public static final String _TableCode = "ZSShopConfig";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into ZSShopConfig values(?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update ZSShopConfig set SiteID=?,Name=?,Info=?,prop1=?,prop2=?,prop3=?,prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where SiteID=?";
   protected static final String _DeleteSQL = "delete from ZSShopConfig  where SiteID=?";
   protected static final String _FillAllSQL = "select * from ZSShopConfig  where SiteID=?";
 
   public ZSShopConfigSchema()
   {
     this.TableCode = "ZSShopConfig";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into ZSShopConfig values(?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZSShopConfig set SiteID=?,Name=?,Info=?,prop1=?,prop2=?,prop3=?,prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where SiteID=?";
     this.DeleteSQL = "delete from ZSShopConfig  where SiteID=?";
     this.FillAllSQL = "select * from ZSShopConfig  where SiteID=?";
     this.HasSetFlag = new boolean[11];
   }
 
   protected Schema newInstance() {
     return new ZSShopConfigSchema();
   }
 
   protected SchemaSet newSet() {
     return new ZSShopConfigSet();
   }
 
   public ZSShopConfigSet query() {
     return query(null, -1, -1);
   }
 
   public ZSShopConfigSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public ZSShopConfigSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public ZSShopConfigSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (ZSShopConfigSet)querySet(qb, pageSize, pageIndex);
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
     if (i != 10) return; this.ModifyTime = ((Date)v); return;
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
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZSShopConfigSchema
 * JD-Core Version:    0.5.4
 */