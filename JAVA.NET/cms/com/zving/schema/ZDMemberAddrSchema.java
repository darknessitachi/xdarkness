 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class ZDMemberAddrSchema extends Schema
 {
   private Long ID;
   private String UserName;
   private String RealName;
   private String Country;
   private String Province;
   private String City;
   private String District;
   private String Address;
   private String ZipCode;
   private String Tel;
   private String Mobile;
   private String Email;
   private String IsDefault;
   private String AddUser;
   private Date AddTime;
   private String ModifyUser;
   private Date ModifyTime;
   public static final SchemaColumn[] _Columns = { 
     new SchemaColumn("ID", 7, 0, 20, 0, true, true), 
     new SchemaColumn("UserName", 1, 1, 200, 0, true, false), 
     new SchemaColumn("RealName", 1, 2, 100, 0, false, false), 
     new SchemaColumn("Country", 1, 3, 30, 0, false, false), 
     new SchemaColumn("Province", 1, 4, 6, 0, false, false), 
     new SchemaColumn("City", 1, 5, 6, 0, false, false), 
     new SchemaColumn("District", 1, 6, 6, 0, false, false), 
     new SchemaColumn("Address", 1, 7, 255, 0, false, false), 
     new SchemaColumn("ZipCode", 1, 8, 10, 0, false, false), 
     new SchemaColumn("Tel", 1, 9, 20, 0, false, false), 
     new SchemaColumn("Mobile", 1, 10, 20, 0, false, false), 
     new SchemaColumn("Email", 1, 11, 100, 0, false, false), 
     new SchemaColumn("IsDefault", 1, 12, 2, 0, false, false), 
     new SchemaColumn("AddUser", 1, 13, 200, 0, true, false), 
     new SchemaColumn("AddTime", 0, 14, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 15, 200, 0, false, false), 
     new SchemaColumn("ModifyTime", 0, 16, 0, 0, false, false) };
   public static final String _TableCode = "ZDMemberAddr";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into ZDMemberAddr values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update ZDMemberAddr set ID=?,UserName=?,RealName=?,Country=?,Province=?,City=?,District=?,Address=?,ZipCode=?,Tel=?,Mobile=?,Email=?,IsDefault=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
   protected static final String _DeleteSQL = "delete from ZDMemberAddr  where ID=?";
   protected static final String _FillAllSQL = "select * from ZDMemberAddr  where ID=?";
 
   public ZDMemberAddrSchema()
   {
     this.TableCode = "ZDMemberAddr";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into ZDMemberAddr values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZDMemberAddr set ID=?,UserName=?,RealName=?,Country=?,Province=?,City=?,District=?,Address=?,ZipCode=?,Tel=?,Mobile=?,Email=?,IsDefault=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.DeleteSQL = "delete from ZDMemberAddr  where ID=?";
     this.FillAllSQL = "select * from ZDMemberAddr  where ID=?";
     this.HasSetFlag = new boolean[17];
   }
 
   protected Schema newInstance() {
     return new ZDMemberAddrSchema();
   }
 
   protected SchemaSet newSet() {
     return new ZDMemberAddrSet();
   }
 
   public ZDMemberAddrSet query() {
     return query(null, -1, -1);
   }
 
   public ZDMemberAddrSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public ZDMemberAddrSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public ZDMemberAddrSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (ZDMemberAddrSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
     if (i == 1) { this.UserName = ((String)v); return; }
     if (i == 2) { this.RealName = ((String)v); return; }
     if (i == 3) { this.Country = ((String)v); return; }
     if (i == 4) { this.Province = ((String)v); return; }
     if (i == 5) { this.City = ((String)v); return; }
     if (i == 6) { this.District = ((String)v); return; }
     if (i == 7) { this.Address = ((String)v); return; }
     if (i == 8) { this.ZipCode = ((String)v); return; }
     if (i == 9) { this.Tel = ((String)v); return; }
     if (i == 10) { this.Mobile = ((String)v); return; }
     if (i == 11) { this.Email = ((String)v); return; }
     if (i == 12) { this.IsDefault = ((String)v); return; }
     if (i == 13) { this.AddUser = ((String)v); return; }
     if (i == 14) { this.AddTime = ((Date)v); return; }
     if (i == 15) { this.ModifyUser = ((String)v); return; }
     if (i != 16) return; this.ModifyTime = ((Date)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.ID;
     if (i == 1) return this.UserName;
     if (i == 2) return this.RealName;
     if (i == 3) return this.Country;
     if (i == 4) return this.Province;
     if (i == 5) return this.City;
     if (i == 6) return this.District;
     if (i == 7) return this.Address;
     if (i == 8) return this.ZipCode;
     if (i == 9) return this.Tel;
     if (i == 10) return this.Mobile;
     if (i == 11) return this.Email;
     if (i == 12) return this.IsDefault;
     if (i == 13) return this.AddUser;
     if (i == 14) return this.AddTime;
     if (i == 15) return this.ModifyUser;
     if (i == 16) return this.ModifyTime;
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
 
   public String getUserName()
   {
     return this.UserName;
   }
 
   public void setUserName(String userName)
   {
     this.UserName = userName;
   }
 
   public String getRealName()
   {
     return this.RealName;
   }
 
   public void setRealName(String realName)
   {
     this.RealName = realName;
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
 
   public String getMobile()
   {
     return this.Mobile;
   }
 
   public void setMobile(String mobile)
   {
     this.Mobile = mobile;
   }
 
   public String getEmail()
   {
     return this.Email;
   }
 
   public void setEmail(String email)
   {
     this.Email = email;
   }
 
   public String getIsDefault()
   {
     return this.IsDefault;
   }
 
   public void setIsDefault(String isDefault)
   {
     this.IsDefault = isDefault;
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
 * Qualified Name:     com.zving.schema.ZDMemberAddrSchema
 * JD-Core Version:    0.5.4
 */