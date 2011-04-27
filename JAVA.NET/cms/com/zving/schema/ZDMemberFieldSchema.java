 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class ZDMemberFieldSchema extends Schema
 {
   private Long SiteID;
   private String Name;
   private String Code;
   private String RealField;
   private String VerifyType;
   private Integer MaxLength;
   private String InputType;
   private String DefaultValue;
   private String ListOption;
   private String HTML;
   private String IsMandatory;
   private Long OrderFlag;
   private Integer RowSize;
   private Integer ColSize;
   private String AddUser;
   private Date AddTime;
   private String ModifyUser;
   private Date ModifyTime;
   public static final SchemaColumn[] _Columns = { 
     new SchemaColumn("SiteID", 7, 0, 0, 0, true, true), 
     new SchemaColumn("Name", 1, 1, 50, 0, false, false), 
     new SchemaColumn("Code", 1, 2, 50, 0, true, true), 
     new SchemaColumn("RealField", 1, 3, 20, 0, false, false), 
     new SchemaColumn("VerifyType", 1, 4, 2, 0, true, false), 
     new SchemaColumn("MaxLength", 8, 5, 0, 0, false, false), 
     new SchemaColumn("InputType", 1, 6, 20, 0, true, false), 
     new SchemaColumn("DefaultValue", 1, 7, 50, 0, false, false), 
     new SchemaColumn("ListOption", 1, 8, 1000, 0, false, false), 
     new SchemaColumn("HTML", 10, 9, 0, 0, false, false), 
     new SchemaColumn("IsMandatory", 1, 10, 2, 0, true, false), 
     new SchemaColumn("OrderFlag", 7, 11, 0, 0, false, false), 
     new SchemaColumn("RowSize", 8, 12, 0, 0, false, false), 
     new SchemaColumn("ColSize", 8, 13, 0, 0, false, false), 
     new SchemaColumn("AddUser", 1, 14, 200, 0, true, false), 
     new SchemaColumn("AddTime", 0, 15, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 16, 200, 0, false, false), 
     new SchemaColumn("ModifyTime", 0, 17, 0, 0, false, false) };
   public static final String _TableCode = "ZDMemberField";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into ZDMemberField values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update ZDMemberField set SiteID=?,Name=?,Code=?,RealField=?,VerifyType=?,MaxLength=?,InputType=?,DefaultValue=?,ListOption=?,HTML=?,IsMandatory=?,OrderFlag=?,RowSize=?,ColSize=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where SiteID=? and Code=?";
   protected static final String _DeleteSQL = "delete from ZDMemberField  where SiteID=? and Code=?";
   protected static final String _FillAllSQL = "select * from ZDMemberField  where SiteID=? and Code=?";
 
   public ZDMemberFieldSchema()
   {
     this.TableCode = "ZDMemberField";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into ZDMemberField values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZDMemberField set SiteID=?,Name=?,Code=?,RealField=?,VerifyType=?,MaxLength=?,InputType=?,DefaultValue=?,ListOption=?,HTML=?,IsMandatory=?,OrderFlag=?,RowSize=?,ColSize=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where SiteID=? and Code=?";
     this.DeleteSQL = "delete from ZDMemberField  where SiteID=? and Code=?";
     this.FillAllSQL = "select * from ZDMemberField  where SiteID=? and Code=?";
     this.HasSetFlag = new boolean[18];
   }
 
   protected Schema newInstance() {
     return new ZDMemberFieldSchema();
   }
 
   protected SchemaSet newSet() {
     return new ZDMemberFieldSet();
   }
 
   public ZDMemberFieldSet query() {
     return query(null, -1, -1);
   }
 
   public ZDMemberFieldSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public ZDMemberFieldSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public ZDMemberFieldSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (ZDMemberFieldSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.SiteID = null; else this.SiteID = new Long(v.toString()); return; }
     if (i == 1) { this.Name = ((String)v); return; }
     if (i == 2) { this.Code = ((String)v); return; }
     if (i == 3) { this.RealField = ((String)v); return; }
     if (i == 4) { this.VerifyType = ((String)v); return; }
     if (i == 5) { if (v == null) this.MaxLength = null; else this.MaxLength = new Integer(v.toString()); return; }
     if (i == 6) { this.InputType = ((String)v); return; }
     if (i == 7) { this.DefaultValue = ((String)v); return; }
     if (i == 8) { this.ListOption = ((String)v); return; }
     if (i == 9) { this.HTML = ((String)v); return; }
     if (i == 10) { this.IsMandatory = ((String)v); return; }
     if (i == 11) { if (v == null) this.OrderFlag = null; else this.OrderFlag = new Long(v.toString()); return; }
     if (i == 12) { if (v == null) this.RowSize = null; else this.RowSize = new Integer(v.toString()); return; }
     if (i == 13) { if (v == null) this.ColSize = null; else this.ColSize = new Integer(v.toString()); return; }
     if (i == 14) { this.AddUser = ((String)v); return; }
     if (i == 15) { this.AddTime = ((Date)v); return; }
     if (i == 16) { this.ModifyUser = ((String)v); return; }
     if (i != 17) return; this.ModifyTime = ((Date)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.SiteID;
     if (i == 1) return this.Name;
     if (i == 2) return this.Code;
     if (i == 3) return this.RealField;
     if (i == 4) return this.VerifyType;
     if (i == 5) return this.MaxLength;
     if (i == 6) return this.InputType;
     if (i == 7) return this.DefaultValue;
     if (i == 8) return this.ListOption;
     if (i == 9) return this.HTML;
     if (i == 10) return this.IsMandatory;
     if (i == 11) return this.OrderFlag;
     if (i == 12) return this.RowSize;
     if (i == 13) return this.ColSize;
     if (i == 14) return this.AddUser;
     if (i == 15) return this.AddTime;
     if (i == 16) return this.ModifyUser;
     if (i == 17) return this.ModifyTime;
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
 
   public String getCode()
   {
     return this.Code;
   }
 
   public void setCode(String code)
   {
     this.Code = code;
   }
 
   public String getRealField()
   {
     return this.RealField;
   }
 
   public void setRealField(String realField)
   {
     this.RealField = realField;
   }
 
   public String getVerifyType()
   {
     return this.VerifyType;
   }
 
   public void setVerifyType(String verifyType)
   {
     this.VerifyType = verifyType;
   }
 
   public int getMaxLength()
   {
     if (this.MaxLength == null) return 0;
     return this.MaxLength.intValue();
   }
 
   public void setMaxLength(int maxLength)
   {
     this.MaxLength = new Integer(maxLength);
   }
 
   public void setMaxLength(String maxLength)
   {
     if (maxLength == null) {
       this.MaxLength = null;
       return;
     }
     this.MaxLength = new Integer(maxLength);
   }
 
   public String getInputType()
   {
     return this.InputType;
   }
 
   public void setInputType(String inputType)
   {
     this.InputType = inputType;
   }
 
   public String getDefaultValue()
   {
     return this.DefaultValue;
   }
 
   public void setDefaultValue(String defaultValue)
   {
     this.DefaultValue = defaultValue;
   }
 
   public String getListOption()
   {
     return this.ListOption;
   }
 
   public void setListOption(String listOption)
   {
     this.ListOption = listOption;
   }
 
   public String getHTML()
   {
     return this.HTML;
   }
 
   public void setHTML(String hTML)
   {
     this.HTML = hTML;
   }
 
   public String getIsMandatory()
   {
     return this.IsMandatory;
   }
 
   public void setIsMandatory(String isMandatory)
   {
     this.IsMandatory = isMandatory;
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
 
   public int getRowSize()
   {
     if (this.RowSize == null) return 0;
     return this.RowSize.intValue();
   }
 
   public void setRowSize(int rowSize)
   {
     this.RowSize = new Integer(rowSize);
   }
 
   public void setRowSize(String rowSize)
   {
     if (rowSize == null) {
       this.RowSize = null;
       return;
     }
     this.RowSize = new Integer(rowSize);
   }
 
   public int getColSize()
   {
     if (this.ColSize == null) return 0;
     return this.ColSize.intValue();
   }
 
   public void setColSize(int colSize)
   {
     this.ColSize = new Integer(colSize);
   }
 
   public void setColSize(String colSize)
   {
     if (colSize == null) {
       this.ColSize = null;
       return;
     }
     this.ColSize = new Integer(colSize);
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
 * Qualified Name:     com.zving.schema.ZDMemberFieldSchema
 * JD-Core Version:    0.5.4
 */