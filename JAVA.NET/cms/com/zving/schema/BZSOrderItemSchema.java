 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class BZSOrderItemSchema extends Schema
 {
   private Long OrderID;
   private Long GoodsID;
   private Long SiteID;
   private String UserName;
   private String SN;
   private String Name;
   private Float Price;
   private Float Discount;
   private Float DiscountPrice;
   private Long Count;
   private Float Amount;
   private Long Score;
   private String Memo;
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
     new SchemaColumn("OrderID", 7, 0, 0, 0, true, true), 
     new SchemaColumn("GoodsID", 7, 1, 0, 0, true, true), 
     new SchemaColumn("SiteID", 7, 2, 0, 0, true, false), 
     new SchemaColumn("UserName", 1, 3, 200, 0, false, false), 
     new SchemaColumn("SN", 1, 4, 50, 0, false, false), 
     new SchemaColumn("Name", 1, 5, 200, 0, false, false), 
     new SchemaColumn("Price", 5, 6, 12, 2, false, false), 
     new SchemaColumn("Discount", 5, 7, 12, 2, false, false), 
     new SchemaColumn("DiscountPrice", 5, 8, 12, 2, false, false), 
     new SchemaColumn("Count", 7, 9, 0, 0, false, false), 
     new SchemaColumn("Amount", 5, 10, 0, 0, false, false), 
     new SchemaColumn("Score", 7, 11, 0, 0, false, false), 
     new SchemaColumn("Memo", 1, 12, 200, 0, false, false), 
     new SchemaColumn("Prop1", 1, 13, 200, 0, false, false), 
     new SchemaColumn("Prop2", 1, 14, 200, 0, false, false), 
     new SchemaColumn("Prop3", 1, 15, 200, 0, false, false), 
     new SchemaColumn("Prop4", 1, 16, 200, 0, false, false), 
     new SchemaColumn("AddUser", 1, 17, 200, 0, true, false), 
     new SchemaColumn("AddTime", 0, 18, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 19, 200, 0, false, false), 
     new SchemaColumn("ModifyTime", 0, 20, 0, 0, false, false), 
     new SchemaColumn("BackupNo", 1, 21, 15, 0, true, true), 
     new SchemaColumn("BackupOperator", 1, 22, 200, 0, true, false), 
     new SchemaColumn("BackupTime", 0, 23, 0, 0, true, false), 
     new SchemaColumn("BackupMemo", 1, 24, 50, 0, false, false) };
   public static final String _TableCode = "BZSOrderItem";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into BZSOrderItem values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update BZSOrderItem set OrderID=?,GoodsID=?,SiteID=?,UserName=?,SN=?,Name=?,Price=?,Discount=?,DiscountPrice=?,Count=?,Amount=?,Score=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where OrderID=? and GoodsID=? and BackupNo=?";
   protected static final String _DeleteSQL = "delete from BZSOrderItem  where OrderID=? and GoodsID=? and BackupNo=?";
   protected static final String _FillAllSQL = "select * from BZSOrderItem  where OrderID=? and GoodsID=? and BackupNo=?";
 
   public BZSOrderItemSchema()
   {
     this.TableCode = "BZSOrderItem";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into BZSOrderItem values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZSOrderItem set OrderID=?,GoodsID=?,SiteID=?,UserName=?,SN=?,Name=?,Price=?,Discount=?,DiscountPrice=?,Count=?,Amount=?,Score=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where OrderID=? and GoodsID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZSOrderItem  where OrderID=? and GoodsID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZSOrderItem  where OrderID=? and GoodsID=? and BackupNo=?";
     this.HasSetFlag = new boolean[25];
   }
 
   protected Schema newInstance() {
     return new BZSOrderItemSchema();
   }
 
   protected SchemaSet newSet() {
     return new BZSOrderItemSet();
   }
 
   public BZSOrderItemSet query() {
     return query(null, -1, -1);
   }
 
   public BZSOrderItemSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public BZSOrderItemSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public BZSOrderItemSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (BZSOrderItemSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.OrderID = null; else this.OrderID = new Long(v.toString()); return; }
     if (i == 1) { if (v == null) this.GoodsID = null; else this.GoodsID = new Long(v.toString()); return; }
     if (i == 2) { if (v == null) this.SiteID = null; else this.SiteID = new Long(v.toString()); return; }
     if (i == 3) { this.UserName = ((String)v); return; }
     if (i == 4) { this.SN = ((String)v); return; }
     if (i == 5) { this.Name = ((String)v); return; }
     if (i == 6) { if (v == null) this.Price = null; else this.Price = new Float(v.toString()); return; }
     if (i == 7) { if (v == null) this.Discount = null; else this.Discount = new Float(v.toString()); return; }
     if (i == 8) { if (v == null) this.DiscountPrice = null; else this.DiscountPrice = new Float(v.toString()); return; }
     if (i == 9) { if (v == null) this.Count = null; else this.Count = new Long(v.toString()); return; }
     if (i == 10) { if (v == null) this.Amount = null; else this.Amount = new Float(v.toString()); return; }
     if (i == 11) { if (v == null) this.Score = null; else this.Score = new Long(v.toString()); return; }
     if (i == 12) { this.Memo = ((String)v); return; }
     if (i == 13) { this.Prop1 = ((String)v); return; }
     if (i == 14) { this.Prop2 = ((String)v); return; }
     if (i == 15) { this.Prop3 = ((String)v); return; }
     if (i == 16) { this.Prop4 = ((String)v); return; }
     if (i == 17) { this.AddUser = ((String)v); return; }
     if (i == 18) { this.AddTime = ((Date)v); return; }
     if (i == 19) { this.ModifyUser = ((String)v); return; }
     if (i == 20) { this.ModifyTime = ((Date)v); return; }
     if (i == 21) { this.BackupNo = ((String)v); return; }
     if (i == 22) { this.BackupOperator = ((String)v); return; }
     if (i == 23) { this.BackupTime = ((Date)v); return; }
     if (i != 24) return; this.BackupMemo = ((String)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.OrderID;
     if (i == 1) return this.GoodsID;
     if (i == 2) return this.SiteID;
     if (i == 3) return this.UserName;
     if (i == 4) return this.SN;
     if (i == 5) return this.Name;
     if (i == 6) return this.Price;
     if (i == 7) return this.Discount;
     if (i == 8) return this.DiscountPrice;
     if (i == 9) return this.Count;
     if (i == 10) return this.Amount;
     if (i == 11) return this.Score;
     if (i == 12) return this.Memo;
     if (i == 13) return this.Prop1;
     if (i == 14) return this.Prop2;
     if (i == 15) return this.Prop3;
     if (i == 16) return this.Prop4;
     if (i == 17) return this.AddUser;
     if (i == 18) return this.AddTime;
     if (i == 19) return this.ModifyUser;
     if (i == 20) return this.ModifyTime;
     if (i == 21) return this.BackupNo;
     if (i == 22) return this.BackupOperator;
     if (i == 23) return this.BackupTime;
     if (i == 24) return this.BackupMemo;
     return null;
   }
 
   public long getOrderID()
   {
     if (this.OrderID == null) return 0L;
     return this.OrderID.longValue();
   }
 
   public void setOrderID(long orderID)
   {
     this.OrderID = new Long(orderID);
   }
 
   public void setOrderID(String orderID)
   {
     if (orderID == null) {
       this.OrderID = null;
       return;
     }
     this.OrderID = new Long(orderID);
   }
 
   public long getGoodsID()
   {
     if (this.GoodsID == null) return 0L;
     return this.GoodsID.longValue();
   }
 
   public void setGoodsID(long goodsID)
   {
     this.GoodsID = new Long(goodsID);
   }
 
   public void setGoodsID(String goodsID)
   {
     if (goodsID == null) {
       this.GoodsID = null;
       return;
     }
     this.GoodsID = new Long(goodsID);
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
 
   public String getUserName()
   {
     return this.UserName;
   }
 
   public void setUserName(String userName)
   {
     this.UserName = userName;
   }
 
   public String getSN()
   {
     return this.SN;
   }
 
   public void setSN(String sN)
   {
     this.SN = sN;
   }
 
   public String getName()
   {
     return this.Name;
   }
 
   public void setName(String name)
   {
     this.Name = name;
   }
 
   public float getPrice()
   {
     if (this.Price == null) return 0.0F;
     return this.Price.floatValue();
   }
 
   public void setPrice(float price)
   {
     this.Price = new Float(price);
   }
 
   public void setPrice(String price)
   {
     if (price == null) {
       this.Price = null;
       return;
     }
     this.Price = new Float(price);
   }
 
   public float getDiscount()
   {
     if (this.Discount == null) return 0.0F;
     return this.Discount.floatValue();
   }
 
   public void setDiscount(float discount)
   {
     this.Discount = new Float(discount);
   }
 
   public void setDiscount(String discount)
   {
     if (discount == null) {
       this.Discount = null;
       return;
     }
     this.Discount = new Float(discount);
   }
 
   public float getDiscountPrice()
   {
     if (this.DiscountPrice == null) return 0.0F;
     return this.DiscountPrice.floatValue();
   }
 
   public void setDiscountPrice(float discountPrice)
   {
     this.DiscountPrice = new Float(discountPrice);
   }
 
   public void setDiscountPrice(String discountPrice)
   {
     if (discountPrice == null) {
       this.DiscountPrice = null;
       return;
     }
     this.DiscountPrice = new Float(discountPrice);
   }
 
   public long getCount()
   {
     if (this.Count == null) return 0L;
     return this.Count.longValue();
   }
 
   public void setCount(long count)
   {
     this.Count = new Long(count);
   }
 
   public void setCount(String count)
   {
     if (count == null) {
       this.Count = null;
       return;
     }
     this.Count = new Long(count);
   }
 
   public float getAmount()
   {
     if (this.Amount == null) return 0.0F;
     return this.Amount.floatValue();
   }
 
   public void setAmount(float amount)
   {
     this.Amount = new Float(amount);
   }
 
   public void setAmount(String amount)
   {
     if (amount == null) {
       this.Amount = null;
       return;
     }
     this.Amount = new Float(amount);
   }
 
   public long getScore()
   {
     if (this.Score == null) return 0L;
     return this.Score.longValue();
   }
 
   public void setScore(long score)
   {
     this.Score = new Long(score);
   }
 
   public void setScore(String score)
   {
     if (score == null) {
       this.Score = null;
       return;
     }
     this.Score = new Long(score);
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
 * Qualified Name:     com.zving.schema.BZSOrderItemSchema
 * JD-Core Version:    0.5.4
 */