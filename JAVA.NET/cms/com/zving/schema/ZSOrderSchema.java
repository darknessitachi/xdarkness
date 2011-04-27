 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class ZSOrderSchema extends Schema
 {
   private Long ID;
   private Long SiteID;
   private String UserName;
   private String IsValid;
   private String Status;
   private Float Amount;
   private Float SendFee;
   private Float OrderAmount;
   private Long Score;
   private String Name;
   private String Province;
   private String City;
   private String District;
   private String Address;
   private String ZipCode;
   private String Tel;
   private String Mobile;
   private String HasInvoice;
   private String InvoiceTitle;
   private Date SendBeginDate;
   private Date SendEndDate;
   private String SendTimeSlice;
   private String SendInfo;
   private String SendType;
   private String PaymentType;
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
     new SchemaColumn("UserName", 1, 2, 200, 0, false, false), 
     new SchemaColumn("IsValid", 1, 3, 1, 0, false, false), 
     new SchemaColumn("Status", 1, 4, 40, 0, false, false), 
     new SchemaColumn("Amount", 5, 5, 12, 2, true, false), 
     new SchemaColumn("SendFee", 5, 6, 12, 2, false, false), 
     new SchemaColumn("OrderAmount", 5, 7, 12, 2, false, false), 
     new SchemaColumn("Score", 7, 8, 0, 0, true, false), 
     new SchemaColumn("Name", 1, 9, 30, 0, false, false), 
     new SchemaColumn("Province", 1, 10, 6, 0, false, false), 
     new SchemaColumn("City", 1, 11, 6, 0, false, false), 
     new SchemaColumn("District", 1, 12, 6, 0, false, false), 
     new SchemaColumn("Address", 1, 13, 255, 0, false, false), 
     new SchemaColumn("ZipCode", 1, 14, 10, 0, false, false), 
     new SchemaColumn("Tel", 1, 15, 20, 0, false, false), 
     new SchemaColumn("Mobile", 1, 16, 20, 0, false, false), 
     new SchemaColumn("HasInvoice", 1, 17, 1, 0, true, false), 
     new SchemaColumn("InvoiceTitle", 1, 18, 100, 0, false, false), 
     new SchemaColumn("SendBeginDate", 0, 19, 0, 0, false, false), 
     new SchemaColumn("SendEndDate", 0, 20, 0, 0, false, false), 
     new SchemaColumn("SendTimeSlice", 1, 21, 40, 0, false, false), 
     new SchemaColumn("SendInfo", 1, 22, 200, 0, false, false), 
     new SchemaColumn("SendType", 1, 23, 40, 0, false, false), 
     new SchemaColumn("PaymentType", 1, 24, 40, 0, false, false), 
     new SchemaColumn("Memo", 1, 25, 200, 0, false, false), 
     new SchemaColumn("Prop1", 1, 26, 200, 0, false, false), 
     new SchemaColumn("Prop2", 1, 27, 200, 0, false, false), 
     new SchemaColumn("Prop3", 1, 28, 200, 0, false, false), 
     new SchemaColumn("Prop4", 1, 29, 200, 0, false, false), 
     new SchemaColumn("AddUser", 1, 30, 200, 0, true, false), 
     new SchemaColumn("AddTime", 0, 31, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 32, 200, 0, false, false), 
     new SchemaColumn("ModifyTime", 0, 33, 0, 0, false, false) };
   public static final String _TableCode = "ZSOrder";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into ZSOrder values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update ZSOrder set ID=?,SiteID=?,UserName=?,IsValid=?,Status=?,Amount=?,SendFee=?,OrderAmount=?,Score=?,Name=?,Province=?,City=?,District=?,Address=?,ZipCode=?,Tel=?,Mobile=?,HasInvoice=?,InvoiceTitle=?,SendBeginDate=?,SendEndDate=?,SendTimeSlice=?,SendInfo=?,SendType=?,PaymentType=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
   protected static final String _DeleteSQL = "delete from ZSOrder  where ID=?";
   protected static final String _FillAllSQL = "select * from ZSOrder  where ID=?";
 
   public ZSOrderSchema()
   {
     this.TableCode = "ZSOrder";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into ZSOrder values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZSOrder set ID=?,SiteID=?,UserName=?,IsValid=?,Status=?,Amount=?,SendFee=?,OrderAmount=?,Score=?,Name=?,Province=?,City=?,District=?,Address=?,ZipCode=?,Tel=?,Mobile=?,HasInvoice=?,InvoiceTitle=?,SendBeginDate=?,SendEndDate=?,SendTimeSlice=?,SendInfo=?,SendType=?,PaymentType=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.DeleteSQL = "delete from ZSOrder  where ID=?";
     this.FillAllSQL = "select * from ZSOrder  where ID=?";
     this.HasSetFlag = new boolean[34];
   }
 
   protected Schema newInstance() {
     return new ZSOrderSchema();
   }
 
   protected SchemaSet newSet() {
     return new ZSOrderSet();
   }
 
   public ZSOrderSet query() {
     return query(null, -1, -1);
   }
 
   public ZSOrderSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public ZSOrderSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public ZSOrderSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (ZSOrderSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
     if (i == 1) { if (v == null) this.SiteID = null; else this.SiteID = new Long(v.toString()); return; }
     if (i == 2) { this.UserName = ((String)v); return; }
     if (i == 3) { this.IsValid = ((String)v); return; }
     if (i == 4) { this.Status = ((String)v); return; }
     if (i == 5) { if (v == null) this.Amount = null; else this.Amount = new Float(v.toString()); return; }
     if (i == 6) { if (v == null) this.SendFee = null; else this.SendFee = new Float(v.toString()); return; }
     if (i == 7) { if (v == null) this.OrderAmount = null; else this.OrderAmount = new Float(v.toString()); return; }
     if (i == 8) { if (v == null) this.Score = null; else this.Score = new Long(v.toString()); return; }
     if (i == 9) { this.Name = ((String)v); return; }
     if (i == 10) { this.Province = ((String)v); return; }
     if (i == 11) { this.City = ((String)v); return; }
     if (i == 12) { this.District = ((String)v); return; }
     if (i == 13) { this.Address = ((String)v); return; }
     if (i == 14) { this.ZipCode = ((String)v); return; }
     if (i == 15) { this.Tel = ((String)v); return; }
     if (i == 16) { this.Mobile = ((String)v); return; }
     if (i == 17) { this.HasInvoice = ((String)v); return; }
     if (i == 18) { this.InvoiceTitle = ((String)v); return; }
     if (i == 19) { this.SendBeginDate = ((Date)v); return; }
     if (i == 20) { this.SendEndDate = ((Date)v); return; }
     if (i == 21) { this.SendTimeSlice = ((String)v); return; }
     if (i == 22) { this.SendInfo = ((String)v); return; }
     if (i == 23) { this.SendType = ((String)v); return; }
     if (i == 24) { this.PaymentType = ((String)v); return; }
     if (i == 25) { this.Memo = ((String)v); return; }
     if (i == 26) { this.Prop1 = ((String)v); return; }
     if (i == 27) { this.Prop2 = ((String)v); return; }
     if (i == 28) { this.Prop3 = ((String)v); return; }
     if (i == 29) { this.Prop4 = ((String)v); return; }
     if (i == 30) { this.AddUser = ((String)v); return; }
     if (i == 31) { this.AddTime = ((Date)v); return; }
     if (i == 32) { this.ModifyUser = ((String)v); return; }
     if (i != 33) return; this.ModifyTime = ((Date)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.ID;
     if (i == 1) return this.SiteID;
     if (i == 2) return this.UserName;
     if (i == 3) return this.IsValid;
     if (i == 4) return this.Status;
     if (i == 5) return this.Amount;
     if (i == 6) return this.SendFee;
     if (i == 7) return this.OrderAmount;
     if (i == 8) return this.Score;
     if (i == 9) return this.Name;
     if (i == 10) return this.Province;
     if (i == 11) return this.City;
     if (i == 12) return this.District;
     if (i == 13) return this.Address;
     if (i == 14) return this.ZipCode;
     if (i == 15) return this.Tel;
     if (i == 16) return this.Mobile;
     if (i == 17) return this.HasInvoice;
     if (i == 18) return this.InvoiceTitle;
     if (i == 19) return this.SendBeginDate;
     if (i == 20) return this.SendEndDate;
     if (i == 21) return this.SendTimeSlice;
     if (i == 22) return this.SendInfo;
     if (i == 23) return this.SendType;
     if (i == 24) return this.PaymentType;
     if (i == 25) return this.Memo;
     if (i == 26) return this.Prop1;
     if (i == 27) return this.Prop2;
     if (i == 28) return this.Prop3;
     if (i == 29) return this.Prop4;
     if (i == 30) return this.AddUser;
     if (i == 31) return this.AddTime;
     if (i == 32) return this.ModifyUser;
     if (i == 33) return this.ModifyTime;
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
 
   public String getUserName()
   {
     return this.UserName;
   }
 
   public void setUserName(String userName)
   {
     this.UserName = userName;
   }
 
   public String getIsValid()
   {
     return this.IsValid;
   }
 
   public void setIsValid(String isValid)
   {
     this.IsValid = isValid;
   }
 
   public String getStatus()
   {
     return this.Status;
   }
 
   public void setStatus(String status)
   {
     this.Status = status;
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
 
   public float getSendFee()
   {
     if (this.SendFee == null) return 0.0F;
     return this.SendFee.floatValue();
   }
 
   public void setSendFee(float sendFee)
   {
     this.SendFee = new Float(sendFee);
   }
 
   public void setSendFee(String sendFee)
   {
     if (sendFee == null) {
       this.SendFee = null;
       return;
     }
     this.SendFee = new Float(sendFee);
   }
 
   public float getOrderAmount()
   {
     if (this.OrderAmount == null) return 0.0F;
     return this.OrderAmount.floatValue();
   }
 
   public void setOrderAmount(float orderAmount)
   {
     this.OrderAmount = new Float(orderAmount);
   }
 
   public void setOrderAmount(String orderAmount)
   {
     if (orderAmount == null) {
       this.OrderAmount = null;
       return;
     }
     this.OrderAmount = new Float(orderAmount);
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
 
   public String getName()
   {
     return this.Name;
   }
 
   public void setName(String name)
   {
     this.Name = name;
   }
 
   public String getProvince()
   {
     return this.Province;
   }
 
   public void setProvince(String province)
   {
     this.Province = province;
   }
 
   public String getCity()
   {
     return this.City;
   }
 
   public void setCity(String city)
   {
     this.City = city;
   }
 
   public String getDistrict()
   {
     return this.District;
   }
 
   public void setDistrict(String district)
   {
     this.District = district;
   }
 
   public String getAddress()
   {
     return this.Address;
   }
 
   public void setAddress(String address)
   {
     this.Address = address;
   }
 
   public String getZipCode()
   {
     return this.ZipCode;
   }
 
   public void setZipCode(String zipCode)
   {
     this.ZipCode = zipCode;
   }
 
   public String getTel()
   {
     return this.Tel;
   }
 
   public void setTel(String tel)
   {
     this.Tel = tel;
   }
 
   public String getMobile()
   {
     return this.Mobile;
   }
 
   public void setMobile(String mobile)
   {
     this.Mobile = mobile;
   }
 
   public String getHasInvoice()
   {
     return this.HasInvoice;
   }
 
   public void setHasInvoice(String hasInvoice)
   {
     this.HasInvoice = hasInvoice;
   }
 
   public String getInvoiceTitle()
   {
     return this.InvoiceTitle;
   }
 
   public void setInvoiceTitle(String invoiceTitle)
   {
     this.InvoiceTitle = invoiceTitle;
   }
 
   public Date getSendBeginDate()
   {
     return this.SendBeginDate;
   }
 
   public void setSendBeginDate(Date sendBeginDate)
   {
     this.SendBeginDate = sendBeginDate;
   }
 
   public Date getSendEndDate()
   {
     return this.SendEndDate;
   }
 
   public void setSendEndDate(Date sendEndDate)
   {
     this.SendEndDate = sendEndDate;
   }
 
   public String getSendTimeSlice()
   {
     return this.SendTimeSlice;
   }
 
   public void setSendTimeSlice(String sendTimeSlice)
   {
     this.SendTimeSlice = sendTimeSlice;
   }
 
   public String getSendInfo()
   {
     return this.SendInfo;
   }
 
   public void setSendInfo(String sendInfo)
   {
     this.SendInfo = sendInfo;
   }
 
   public String getSendType()
   {
     return this.SendType;
   }
 
   public void setSendType(String sendType)
   {
     this.SendType = sendType;
   }
 
   public String getPaymentType()
   {
     return this.PaymentType;
   }
 
   public void setPaymentType(String paymentType)
   {
     this.PaymentType = paymentType;
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
 * Qualified Name:     com.zving.schema.ZSOrderSchema
 * JD-Core Version:    0.5.4
 */