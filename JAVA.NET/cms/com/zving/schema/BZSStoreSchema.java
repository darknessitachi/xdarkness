 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class BZSStoreSchema extends Schema
 {
   private String StoreCode;
   private String ParentCode;
   private String Name;
   private String Alias;
   private Long TreeLevel;
   private Long SiteID;
   private Long OrderFlag;
   private String URL;
   private String Info;
   private String Country;
   private String Province;
   private String City;
   private String District;
   private String Address;
   private String ZipCode;
   private String Tel;
   private String Fax;
   private String Mobile;
   private String Contacter;
   private String ContacterEmail;
   private String ContacterTel;
   private String ContacterMobile;
   private String ContacterQQ;
   private String ContacterMSN;
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
     new SchemaColumn("StoreCode", 1, 0, 100, 0, true, true), 
     new SchemaColumn("ParentCode", 1, 1, 100, 0, true, false), 
     new SchemaColumn("Name", 1, 2, 100, 0, true, false), 
     new SchemaColumn("Alias", 1, 3, 100, 0, false, false), 
     new SchemaColumn("TreeLevel", 7, 4, 0, 0, true, false), 
     new SchemaColumn("SiteID", 7, 5, 0, 0, true, false), 
     new SchemaColumn("OrderFlag", 7, 6, 0, 0, true, false), 
     new SchemaColumn("URL", 1, 7, 100, 0, false, false), 
     new SchemaColumn("Info", 1, 8, 2000, 0, false, false), 
     new SchemaColumn("Country", 1, 9, 30, 0, false, false), 
     new SchemaColumn("Province", 1, 10, 6, 0, false, false), 
     new SchemaColumn("City", 1, 11, 6, 0, false, false), 
     new SchemaColumn("District", 1, 12, 6, 0, false, false), 
     new SchemaColumn("Address", 1, 13, 400, 0, false, false), 
     new SchemaColumn("ZipCode", 1, 14, 10, 0, false, false), 
     new SchemaColumn("Tel", 1, 15, 20, 0, false, false), 
     new SchemaColumn("Fax", 1, 16, 20, 0, false, false), 
     new SchemaColumn("Mobile", 1, 17, 30, 0, false, false), 
     new SchemaColumn("Contacter", 1, 18, 40, 0, false, false), 
     new SchemaColumn("ContacterEmail", 1, 19, 100, 0, false, false), 
     new SchemaColumn("ContacterTel", 1, 20, 20, 0, false, false), 
     new SchemaColumn("ContacterMobile", 1, 21, 20, 0, false, false), 
     new SchemaColumn("ContacterQQ", 1, 22, 20, 0, false, false), 
     new SchemaColumn("ContacterMSN", 1, 23, 50, 0, false, false), 
     new SchemaColumn("Memo", 1, 24, 200, 0, false, false), 
     new SchemaColumn("Prop1", 1, 25, 200, 0, false, false), 
     new SchemaColumn("Prop2", 1, 26, 200, 0, false, false), 
     new SchemaColumn("Prop3", 1, 27, 200, 0, false, false), 
     new SchemaColumn("Prop4", 1, 28, 200, 0, false, false), 
     new SchemaColumn("AddUser", 1, 29, 200, 0, true, false), 
     new SchemaColumn("AddTime", 0, 30, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 31, 200, 0, false, false), 
     new SchemaColumn("ModifyTime", 0, 32, 0, 0, false, false), 
     new SchemaColumn("BackupNo", 1, 33, 15, 0, true, true), 
     new SchemaColumn("BackupOperator", 1, 34, 200, 0, true, false), 
     new SchemaColumn("BackupTime", 0, 35, 0, 0, true, false), 
     new SchemaColumn("BackupMemo", 1, 36, 50, 0, false, false) };
   public static final String _TableCode = "BZSStore";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into BZSStore values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update BZSStore set StoreCode=?,ParentCode=?,Name=?,Alias=?,TreeLevel=?,SiteID=?,OrderFlag=?,URL=?,Info=?,Country=?,Province=?,City=?,District=?,Address=?,ZipCode=?,Tel=?,Fax=?,Mobile=?,Contacter=?,ContacterEmail=?,ContacterTel=?,ContacterMobile=?,ContacterQQ=?,ContacterMSN=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where StoreCode=? and BackupNo=?";
   protected static final String _DeleteSQL = "delete from BZSStore  where StoreCode=? and BackupNo=?";
   protected static final String _FillAllSQL = "select * from BZSStore  where StoreCode=? and BackupNo=?";
 
   public BZSStoreSchema()
   {
     this.TableCode = "BZSStore";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into BZSStore values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZSStore set StoreCode=?,ParentCode=?,Name=?,Alias=?,TreeLevel=?,SiteID=?,OrderFlag=?,URL=?,Info=?,Country=?,Province=?,City=?,District=?,Address=?,ZipCode=?,Tel=?,Fax=?,Mobile=?,Contacter=?,ContacterEmail=?,ContacterTel=?,ContacterMobile=?,ContacterQQ=?,ContacterMSN=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where StoreCode=? and BackupNo=?";
     this.DeleteSQL = "delete from BZSStore  where StoreCode=? and BackupNo=?";
     this.FillAllSQL = "select * from BZSStore  where StoreCode=? and BackupNo=?";
     this.HasSetFlag = new boolean[37];
   }
 
   protected Schema newInstance() {
     return new BZSStoreSchema();
   }
 
   protected SchemaSet newSet() {
     return new BZSStoreSet();
   }
 
   public BZSStoreSet query() {
     return query(null, -1, -1);
   }
 
   public BZSStoreSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public BZSStoreSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public BZSStoreSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (BZSStoreSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { this.StoreCode = ((String)v); return; }
     if (i == 1) { this.ParentCode = ((String)v); return; }
     if (i == 2) { this.Name = ((String)v); return; }
     if (i == 3) { this.Alias = ((String)v); return; }
     if (i == 4) { if (v == null) this.TreeLevel = null; else this.TreeLevel = new Long(v.toString()); return; }
     if (i == 5) { if (v == null) this.SiteID = null; else this.SiteID = new Long(v.toString()); return; }
     if (i == 6) { if (v == null) this.OrderFlag = null; else this.OrderFlag = new Long(v.toString()); return; }
     if (i == 7) { this.URL = ((String)v); return; }
     if (i == 8) { this.Info = ((String)v); return; }
     if (i == 9) { this.Country = ((String)v); return; }
     if (i == 10) { this.Province = ((String)v); return; }
     if (i == 11) { this.City = ((String)v); return; }
     if (i == 12) { this.District = ((String)v); return; }
     if (i == 13) { this.Address = ((String)v); return; }
     if (i == 14) { this.ZipCode = ((String)v); return; }
     if (i == 15) { this.Tel = ((String)v); return; }
     if (i == 16) { this.Fax = ((String)v); return; }
     if (i == 17) { this.Mobile = ((String)v); return; }
     if (i == 18) { this.Contacter = ((String)v); return; }
     if (i == 19) { this.ContacterEmail = ((String)v); return; }
     if (i == 20) { this.ContacterTel = ((String)v); return; }
     if (i == 21) { this.ContacterMobile = ((String)v); return; }
     if (i == 22) { this.ContacterQQ = ((String)v); return; }
     if (i == 23) { this.ContacterMSN = ((String)v); return; }
     if (i == 24) { this.Memo = ((String)v); return; }
     if (i == 25) { this.Prop1 = ((String)v); return; }
     if (i == 26) { this.Prop2 = ((String)v); return; }
     if (i == 27) { this.Prop3 = ((String)v); return; }
     if (i == 28) { this.Prop4 = ((String)v); return; }
     if (i == 29) { this.AddUser = ((String)v); return; }
     if (i == 30) { this.AddTime = ((Date)v); return; }
     if (i == 31) { this.ModifyUser = ((String)v); return; }
     if (i == 32) { this.ModifyTime = ((Date)v); return; }
     if (i == 33) { this.BackupNo = ((String)v); return; }
     if (i == 34) { this.BackupOperator = ((String)v); return; }
     if (i == 35) { this.BackupTime = ((Date)v); return; }
     if (i != 36) return; this.BackupMemo = ((String)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.StoreCode;
     if (i == 1) return this.ParentCode;
     if (i == 2) return this.Name;
     if (i == 3) return this.Alias;
     if (i == 4) return this.TreeLevel;
     if (i == 5) return this.SiteID;
     if (i == 6) return this.OrderFlag;
     if (i == 7) return this.URL;
     if (i == 8) return this.Info;
     if (i == 9) return this.Country;
     if (i == 10) return this.Province;
     if (i == 11) return this.City;
     if (i == 12) return this.District;
     if (i == 13) return this.Address;
     if (i == 14) return this.ZipCode;
     if (i == 15) return this.Tel;
     if (i == 16) return this.Fax;
     if (i == 17) return this.Mobile;
     if (i == 18) return this.Contacter;
     if (i == 19) return this.ContacterEmail;
     if (i == 20) return this.ContacterTel;
     if (i == 21) return this.ContacterMobile;
     if (i == 22) return this.ContacterQQ;
     if (i == 23) return this.ContacterMSN;
     if (i == 24) return this.Memo;
     if (i == 25) return this.Prop1;
     if (i == 26) return this.Prop2;
     if (i == 27) return this.Prop3;
     if (i == 28) return this.Prop4;
     if (i == 29) return this.AddUser;
     if (i == 30) return this.AddTime;
     if (i == 31) return this.ModifyUser;
     if (i == 32) return this.ModifyTime;
     if (i == 33) return this.BackupNo;
     if (i == 34) return this.BackupOperator;
     if (i == 35) return this.BackupTime;
     if (i == 36) return this.BackupMemo;
     return null;
   }
 
   public String getStoreCode()
   {
     return this.StoreCode;
   }
 
   public void setStoreCode(String storeCode)
   {
     this.StoreCode = storeCode;
   }
 
   public String getParentCode()
   {
     return this.ParentCode;
   }
 
   public void setParentCode(String parentCode)
   {
     this.ParentCode = parentCode;
   }
 
   public String getName()
   {
     return this.Name;
   }
 
   public void setName(String name)
   {
     this.Name = name;
   }
 
   public String getAlias()
   {
     return this.Alias;
   }
 
   public void setAlias(String alias)
   {
     this.Alias = alias;
   }
 
   public long getTreeLevel()
   {
     if (this.TreeLevel == null) return 0L;
     return this.TreeLevel.longValue();
   }
 
   public void setTreeLevel(long treeLevel)
   {
     this.TreeLevel = new Long(treeLevel);
   }
 
   public void setTreeLevel(String treeLevel)
   {
     if (treeLevel == null) {
       this.TreeLevel = null;
       return;
     }
     this.TreeLevel = new Long(treeLevel);
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
 
   public long getOrderFlag()
   {
     if (this.OrderFlag == null) return 0L;
     return this.OrderFlag.longValue();
   }
 
   public void setOrderFlag(long orderFlag)
   {
     this.OrderFlag = new Long(orderFlag);
   }
 
   public void setOrderFlag(String orderFlag)
   {
     if (orderFlag == null) {
       this.OrderFlag = null;
       return;
     }
     this.OrderFlag = new Long(orderFlag);
   }
 
   public String getURL()
   {
     return this.URL;
   }
 
   public void setURL(String uRL)
   {
     this.URL = uRL;
   }
 
   public String getInfo()
   {
     return this.Info;
   }
 
   public void setInfo(String info)
   {
     this.Info = info;
   }
 
   public String getCountry()
   {
     return this.Country;
   }
 
   public void setCountry(String country)
   {
     this.Country = country;
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
 
   public String getFax()
   {
     return this.Fax;
   }
 
   public void setFax(String fax)
   {
     this.Fax = fax;
   }
 
   public String getMobile()
   {
     return this.Mobile;
   }
 
   public void setMobile(String mobile)
   {
     this.Mobile = mobile;
   }
 
   public String getContacter()
   {
     return this.Contacter;
   }
 
   public void setContacter(String contacter)
   {
     this.Contacter = contacter;
   }
 
   public String getContacterEmail()
   {
     return this.ContacterEmail;
   }
 
   public void setContacterEmail(String contacterEmail)
   {
     this.ContacterEmail = contacterEmail;
   }
 
   public String getContacterTel()
   {
     return this.ContacterTel;
   }
 
   public void setContacterTel(String contacterTel)
   {
     this.ContacterTel = contacterTel;
   }
 
   public String getContacterMobile()
   {
     return this.ContacterMobile;
   }
 
   public void setContacterMobile(String contacterMobile)
   {
     this.ContacterMobile = contacterMobile;
   }
 
   public String getContacterQQ()
   {
     return this.ContacterQQ;
   }
 
   public void setContacterQQ(String contacterQQ)
   {
     this.ContacterQQ = contacterQQ;
   }
 
   public String getContacterMSN()
   {
     return this.ContacterMSN;
   }
 
   public void setContacterMSN(String contacterMSN)
   {
     this.ContacterMSN = contacterMSN;
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
 * Qualified Name:     com.zving.schema.BZSStoreSchema
 * JD-Core Version:    0.5.4
 */