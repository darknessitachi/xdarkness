 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class ZCPaperIssueSchema extends Schema
 {
   private Long ID;
   private Long PaperID;
   private String Year;
   private String PeriodNum;
   private String CoverImage;
   private String CoverTemplate;
   private Date PublishDate;
   private Long Status;
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
     new SchemaColumn("PaperID", 7, 1, 0, 0, true, false), 
     new SchemaColumn("Year", 1, 2, 50, 0, false, false), 
     new SchemaColumn("PeriodNum", 1, 3, 50, 0, true, false), 
     new SchemaColumn("CoverImage", 1, 4, 100, 0, false, false), 
     new SchemaColumn("CoverTemplate", 1, 5, 100, 0, false, false), 
     new SchemaColumn("PublishDate", 0, 6, 0, 0, true, false), 
     new SchemaColumn("Status", 7, 7, 0, 0, false, false), 
     new SchemaColumn("Memo", 1, 8, 1000, 0, false, false), 
     new SchemaColumn("Prop1", 1, 9, 50, 0, false, false), 
     new SchemaColumn("Prop2", 1, 10, 50, 0, false, false), 
     new SchemaColumn("Prop3", 1, 11, 50, 0, false, false), 
     new SchemaColumn("Prop4", 1, 12, 50, 0, false, false), 
     new SchemaColumn("AddUser", 1, 13, 200, 0, true, false), 
     new SchemaColumn("AddTime", 0, 14, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 15, 200, 0, false, false), 
     new SchemaColumn("ModifyTime", 0, 16, 0, 0, false, false) };
   public static final String _TableCode = "ZCPaperIssue";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into ZCPaperIssue values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update ZCPaperIssue set ID=?,PaperID=?,Year=?,PeriodNum=?,CoverImage=?,CoverTemplate=?,PublishDate=?,Status=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
   protected static final String _DeleteSQL = "delete from ZCPaperIssue  where ID=?";
   protected static final String _FillAllSQL = "select * from ZCPaperIssue  where ID=?";
 
   public ZCPaperIssueSchema()
   {
     this.TableCode = "ZCPaperIssue";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into ZCPaperIssue values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZCPaperIssue set ID=?,PaperID=?,Year=?,PeriodNum=?,CoverImage=?,CoverTemplate=?,PublishDate=?,Status=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.DeleteSQL = "delete from ZCPaperIssue  where ID=?";
     this.FillAllSQL = "select * from ZCPaperIssue  where ID=?";
     this.HasSetFlag = new boolean[17];
   }
 
   protected Schema newInstance() {
     return new ZCPaperIssueSchema();
   }
 
   protected SchemaSet newSet() {
     return new ZCPaperIssueSet();
   }
 
   public ZCPaperIssueSet query() {
     return query(null, -1, -1);
   }
 
   public ZCPaperIssueSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public ZCPaperIssueSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public ZCPaperIssueSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (ZCPaperIssueSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
     if (i == 1) { if (v == null) this.PaperID = null; else this.PaperID = new Long(v.toString()); return; }
     if (i == 2) { this.Year = ((String)v); return; }
     if (i == 3) { this.PeriodNum = ((String)v); return; }
     if (i == 4) { this.CoverImage = ((String)v); return; }
     if (i == 5) { this.CoverTemplate = ((String)v); return; }
     if (i == 6) { this.PublishDate = ((Date)v); return; }
     if (i == 7) { if (v == null) this.Status = null; else this.Status = new Long(v.toString()); return; }
     if (i == 8) { this.Memo = ((String)v); return; }
     if (i == 9) { this.Prop1 = ((String)v); return; }
     if (i == 10) { this.Prop2 = ((String)v); return; }
     if (i == 11) { this.Prop3 = ((String)v); return; }
     if (i == 12) { this.Prop4 = ((String)v); return; }
     if (i == 13) { this.AddUser = ((String)v); return; }
     if (i == 14) { this.AddTime = ((Date)v); return; }
     if (i == 15) { this.ModifyUser = ((String)v); return; }
     if (i != 16) return; this.ModifyTime = ((Date)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.ID;
     if (i == 1) return this.PaperID;
     if (i == 2) return this.Year;
     if (i == 3) return this.PeriodNum;
     if (i == 4) return this.CoverImage;
     if (i == 5) return this.CoverTemplate;
     if (i == 6) return this.PublishDate;
     if (i == 7) return this.Status;
     if (i == 8) return this.Memo;
     if (i == 9) return this.Prop1;
     if (i == 10) return this.Prop2;
     if (i == 11) return this.Prop3;
     if (i == 12) return this.Prop4;
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
 
   public long getPaperID()
   {
     if (this.PaperID == null) return 0L;
     return this.PaperID.longValue();
   }
 
   public void setPaperID(long paperID)
   {
     this.PaperID = new Long(paperID);
   }
 
   public void setPaperID(String paperID)
   {
     if (paperID == null) {
       this.PaperID = null;
       return;
     }
     this.PaperID = new Long(paperID);
   }
 
   public String getYear()
   {
     return this.Year;
   }
 
   public void setYear(String year)
   {
     this.Year = year;
   }
 
   public String getPeriodNum()
   {
     return this.PeriodNum;
   }
 
   public void setPeriodNum(String periodNum)
   {
     this.PeriodNum = periodNum;
   }
 
   public String getCoverImage()
   {
     return this.CoverImage;
   }
 
   public void setCoverImage(String coverImage)
   {
     this.CoverImage = coverImage;
   }
 
   public String getCoverTemplate()
   {
     return this.CoverTemplate;
   }
 
   public void setCoverTemplate(String coverTemplate)
   {
     this.CoverTemplate = coverTemplate;
   }
 
   public Date getPublishDate()
   {
     return this.PublishDate;
   }
 
   public void setPublishDate(Date publishDate)
   {
     this.PublishDate = publishDate;
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
 * Qualified Name:     com.zving.schema.ZCPaperIssueSchema
 * JD-Core Version:    0.5.4
 */