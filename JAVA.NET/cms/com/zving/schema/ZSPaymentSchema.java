 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class ZSPaymentSchema extends Schema
 {
   private Long ID;
   private Long SiteID;
   private String Name;
   private String Info;
   private String IsVisible;
   private String ImagePath;
   private String Memo;
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
     new SchemaColumn("SiteID", 7, 1, 0, 0, true, false), 
     new SchemaColumn("Name", 1, 2, 200, 0, false, false), 
     new SchemaColumn("Info", 1, 3, 1000, 0, false, false), 
     new SchemaColumn("IsVisible", 1, 4, 1, 0, false, false), 
     new SchemaColumn("ImagePath", 1, 5, 200, 0, false, false), 
     new SchemaColumn("Memo", 1, 6, 200, 0, false, false), 
     new SchemaColumn("Prop1", 1, 7, 200, 0, false, false), 
     new SchemaColumn("Prop2", 1, 8, 200, 0, false, false), 
     new SchemaColumn("Prop3", 1, 9, 200, 0, false, false), 
     new SchemaColumn("Prop4", 1, 10, 200, 0, false, false), 
     new SchemaColumn("AddUser", 1, 11, 200, 0, true, false), 
     new SchemaColumn("AddTime", 0, 12, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 13, 200, 0, false, false), 
     new SchemaColumn("ModifyTime", 0, 14, 0, 0, false, false) };
   public static final String _TableCode = "ZSPayment";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into ZSPayment values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update ZSPayment set ID=?,SiteID=?,Name=?,Info=?,IsVisible=?,ImagePath=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
   protected static final String _DeleteSQL = "delete from ZSPayment  where ID=?";
   protected static final String _FillAllSQL = "select * from ZSPayment  where ID=?";
 
   public ZSPaymentSchema()
   {
     this.TableCode = "ZSPayment";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into ZSPayment values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZSPayment set ID=?,SiteID=?,Name=?,Info=?,IsVisible=?,ImagePath=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.DeleteSQL = "delete from ZSPayment  where ID=?";
     this.FillAllSQL = "select * from ZSPayment  where ID=?";
     this.HasSetFlag = new boolean[15];
   }
 
   protected Schema newInstance() {
     return new ZSPaymentSchema();
   }
 
   protected SchemaSet newSet() {
     return new ZSPaymentSet();
   }
 
   public ZSPaymentSet query() {
     return query(null, -1, -1);
   }
 
   public ZSPaymentSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public ZSPaymentSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public ZSPaymentSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (ZSPaymentSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
     if (i == 1) { if (v == null) this.SiteID = null; else this.SiteID = new Long(v.toString()); return; }
     if (i == 2) { this.Name = ((String)v); return; }
     if (i == 3) { this.Info = ((String)v); return; }
     if (i == 4) { this.IsVisible = ((String)v); return; }
     if (i == 5) { this.ImagePath = ((String)v); return; }
     if (i == 6) { this.Memo = ((String)v); return; }
     if (i == 7) { this.Prop1 = ((String)v); return; }
     if (i == 8) { this.Prop2 = ((String)v); return; }
     if (i == 9) { this.Prop3 = ((String)v); return; }
     if (i == 10) { this.Prop4 = ((String)v); return; }
     if (i == 11) { this.AddUser = ((String)v); return; }
     if (i == 12) { this.AddTime = ((Date)v); return; }
     if (i == 13) { this.ModifyUser = ((String)v); return; }
     if (i != 14) return; this.ModifyTime = ((Date)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.ID;
     if (i == 1) return this.SiteID;
     if (i == 2) return this.Name;
     if (i == 3) return this.Info;
     if (i == 4) return this.IsVisible;
     if (i == 5) return this.ImagePath;
     if (i == 6) return this.Memo;
     if (i == 7) return this.Prop1;
     if (i == 8) return this.Prop2;
     if (i == 9) return this.Prop3;
     if (i == 10) return this.Prop4;
     if (i == 11) return this.AddUser;
     if (i == 12) return this.AddTime;
     if (i == 13) return this.ModifyUser;
     if (i == 14) return this.ModifyTime;
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
 
   public String getInfo()
   {
     return this.Info;
   }
 
   public void setInfo(String info)
   {
     this.Info = info;
   }
 
   public String getIsVisible()
   {
     return this.IsVisible;
   }
 
   public void setIsVisible(String isVisible)
   {
     this.IsVisible = isVisible;
   }
 
   public String getImagePath()
   {
     return this.ImagePath;
   }
 
   public void setImagePath(String imagePath)
   {
     this.ImagePath = imagePath;
   }
 
   public String getMemo()
   {
     return this.Memo;
   }
 
   public void setMemo(String memo)
   {
     this.Memo = memo;
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
 * Qualified Name:     com.zving.schema.ZSPaymentSchema
 * JD-Core Version:    0.5.4
 */