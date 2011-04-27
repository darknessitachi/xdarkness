 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class BZCDBGatherSchema extends Schema
 {
   private Long ID;
   private Long SiteID;
   private String Name;
   private Long DatabaseID;
   private String TableName;
   private Long CatalogID;
   private Long ArticleStatus;
   private String PathReplacePartOld;
   private String PathReplacePartNew;
   private String NewRecordRule;
   private String SQLCondition;
   private String Status;
   private String MappingConfig;
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
     new SchemaColumn("ID", 7, 0, 0, 0, true, true), 
     new SchemaColumn("SiteID", 7, 1, 0, 0, true, false), 
     new SchemaColumn("Name", 1, 2, 200, 0, true, false), 
     new SchemaColumn("DatabaseID", 7, 3, 0, 0, true, false), 
     new SchemaColumn("TableName", 1, 4, 50, 0, true, false), 
     new SchemaColumn("CatalogID", 7, 5, 0, 0, true, false), 
     new SchemaColumn("ArticleStatus", 7, 6, 0, 0, true, false), 
     new SchemaColumn("PathReplacePartOld", 1, 7, 200, 0, false, false), 
     new SchemaColumn("PathReplacePartNew", 1, 8, 200, 0, false, false), 
     new SchemaColumn("NewRecordRule", 1, 9, 200, 0, false, false), 
     new SchemaColumn("SQLCondition", 1, 10, 200, 0, false, false), 
     new SchemaColumn("Status", 1, 11, 2, 0, false, false), 
     new SchemaColumn("MappingConfig", 10, 12, 0, 0, true, false), 
     new SchemaColumn("Memo", 1, 13, 400, 0, false, false), 
     new SchemaColumn("Prop1", 1, 14, 50, 0, false, false), 
     new SchemaColumn("Prop2", 1, 15, 50, 0, false, false), 
     new SchemaColumn("Prop3", 1, 16, 50, 0, false, false), 
     new SchemaColumn("Prop4", 1, 17, 50, 0, false, false), 
     new SchemaColumn("AddUser", 1, 18, 200, 0, true, false), 
     new SchemaColumn("AddTime", 0, 19, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 20, 200, 0, false, false), 
     new SchemaColumn("ModifyTime", 0, 21, 0, 0, false, false), 
     new SchemaColumn("BackupNo", 1, 22, 15, 0, true, true), 
     new SchemaColumn("BackupOperator", 1, 23, 200, 0, true, false), 
     new SchemaColumn("BackupTime", 0, 24, 0, 0, true, false), 
     new SchemaColumn("BackupMemo", 1, 25, 50, 0, false, false) };
   public static final String _TableCode = "BZCDBGather";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into BZCDBGather values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update BZCDBGather set ID=?,SiteID=?,Name=?,DatabaseID=?,TableName=?,CatalogID=?,ArticleStatus=?,PathReplacePartOld=?,PathReplacePartNew=?,NewRecordRule=?,SQLCondition=?,Status=?,MappingConfig=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
   protected static final String _DeleteSQL = "delete from BZCDBGather  where ID=? and BackupNo=?";
   protected static final String _FillAllSQL = "select * from BZCDBGather  where ID=? and BackupNo=?";
 
   public BZCDBGatherSchema()
   {
     this.TableCode = "BZCDBGather";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into BZCDBGather values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZCDBGather set ID=?,SiteID=?,Name=?,DatabaseID=?,TableName=?,CatalogID=?,ArticleStatus=?,PathReplacePartOld=?,PathReplacePartNew=?,NewRecordRule=?,SQLCondition=?,Status=?,MappingConfig=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZCDBGather  where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZCDBGather  where ID=? and BackupNo=?";
     this.HasSetFlag = new boolean[26];
   }
 
   protected Schema newInstance() {
     return new BZCDBGatherSchema();
   }
 
   protected SchemaSet newSet() {
     return new BZCDBGatherSet();
   }
 
   public BZCDBGatherSet query() {
     return query(null, -1, -1);
   }
 
   public BZCDBGatherSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public BZCDBGatherSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public BZCDBGatherSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (BZCDBGatherSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
     if (i == 1) { if (v == null) this.SiteID = null; else this.SiteID = new Long(v.toString()); return; }
     if (i == 2) { this.Name = ((String)v); return; }
     if (i == 3) { if (v == null) this.DatabaseID = null; else this.DatabaseID = new Long(v.toString()); return; }
     if (i == 4) { this.TableName = ((String)v); return; }
     if (i == 5) { if (v == null) this.CatalogID = null; else this.CatalogID = new Long(v.toString()); return; }
     if (i == 6) { if (v == null) this.ArticleStatus = null; else this.ArticleStatus = new Long(v.toString()); return; }
     if (i == 7) { this.PathReplacePartOld = ((String)v); return; }
     if (i == 8) { this.PathReplacePartNew = ((String)v); return; }
     if (i == 9) { this.NewRecordRule = ((String)v); return; }
     if (i == 10) { this.SQLCondition = ((String)v); return; }
     if (i == 11) { this.Status = ((String)v); return; }
     if (i == 12) { this.MappingConfig = ((String)v); return; }
     if (i == 13) { this.Memo = ((String)v); return; }
     if (i == 14) { this.Prop1 = ((String)v); return; }
     if (i == 15) { this.Prop2 = ((String)v); return; }
     if (i == 16) { this.Prop3 = ((String)v); return; }
     if (i == 17) { this.Prop4 = ((String)v); return; }
     if (i == 18) { this.AddUser = ((String)v); return; }
     if (i == 19) { this.AddTime = ((Date)v); return; }
     if (i == 20) { this.ModifyUser = ((String)v); return; }
     if (i == 21) { this.ModifyTime = ((Date)v); return; }
     if (i == 22) { this.BackupNo = ((String)v); return; }
     if (i == 23) { this.BackupOperator = ((String)v); return; }
     if (i == 24) { this.BackupTime = ((Date)v); return; }
     if (i != 25) return; this.BackupMemo = ((String)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.ID;
     if (i == 1) return this.SiteID;
     if (i == 2) return this.Name;
     if (i == 3) return this.DatabaseID;
     if (i == 4) return this.TableName;
     if (i == 5) return this.CatalogID;
     if (i == 6) return this.ArticleStatus;
     if (i == 7) return this.PathReplacePartOld;
     if (i == 8) return this.PathReplacePartNew;
     if (i == 9) return this.NewRecordRule;
     if (i == 10) return this.SQLCondition;
     if (i == 11) return this.Status;
     if (i == 12) return this.MappingConfig;
     if (i == 13) return this.Memo;
     if (i == 14) return this.Prop1;
     if (i == 15) return this.Prop2;
     if (i == 16) return this.Prop3;
     if (i == 17) return this.Prop4;
     if (i == 18) return this.AddUser;
     if (i == 19) return this.AddTime;
     if (i == 20) return this.ModifyUser;
     if (i == 21) return this.ModifyTime;
     if (i == 22) return this.BackupNo;
     if (i == 23) return this.BackupOperator;
     if (i == 24) return this.BackupTime;
     if (i == 25) return this.BackupMemo;
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
 
   public long getDatabaseID()
   {
     if (this.DatabaseID == null) return 0L;
     return this.DatabaseID.longValue();
   }
 
   public void setDatabaseID(long databaseID)
   {
     this.DatabaseID = new Long(databaseID);
   }
 
   public void setDatabaseID(String databaseID)
   {
     if (databaseID == null) {
       this.DatabaseID = null;
       return;
     }
     this.DatabaseID = new Long(databaseID);
   }
 
   public String getTableName()
   {
     return this.TableName;
   }
 
   public void setTableName(String tableName)
   {
     this.TableName = tableName;
   }
 
   public long getCatalogID()
   {
     if (this.CatalogID == null) return 0L;
     return this.CatalogID.longValue();
   }
 
   public void setCatalogID(long catalogID)
   {
     this.CatalogID = new Long(catalogID);
   }
 
   public void setCatalogID(String catalogID)
   {
     if (catalogID == null) {
       this.CatalogID = null;
       return;
     }
     this.CatalogID = new Long(catalogID);
   }
 
   public long getArticleStatus()
   {
     if (this.ArticleStatus == null) return 0L;
     return this.ArticleStatus.longValue();
   }
 
   public void setArticleStatus(long articleStatus)
   {
     this.ArticleStatus = new Long(articleStatus);
   }
 
   public void setArticleStatus(String articleStatus)
   {
     if (articleStatus == null) {
       this.ArticleStatus = null;
       return;
     }
     this.ArticleStatus = new Long(articleStatus);
   }
 
   public String getPathReplacePartOld()
   {
     return this.PathReplacePartOld;
   }
 
   public void setPathReplacePartOld(String pathReplacePartOld)
   {
     this.PathReplacePartOld = pathReplacePartOld;
   }
 
   public String getPathReplacePartNew()
   {
     return this.PathReplacePartNew;
   }
 
   public void setPathReplacePartNew(String pathReplacePartNew)
   {
     this.PathReplacePartNew = pathReplacePartNew;
   }
 
   public String getNewRecordRule()
   {
     return this.NewRecordRule;
   }
 
   public void setNewRecordRule(String newRecordRule)
   {
     this.NewRecordRule = newRecordRule;
   }
 
   public String getSQLCondition()
   {
     return this.SQLCondition;
   }
 
   public void setSQLCondition(String sQLCondition)
   {
     this.SQLCondition = sQLCondition;
   }
 
   public String getStatus()
   {
     return this.Status;
   }
 
   public void setStatus(String status)
   {
     this.Status = status;
   }
 
   public String getMappingConfig()
   {
     return this.MappingConfig;
   }
 
   public void setMappingConfig(String mappingConfig)
   {
     this.MappingConfig = mappingConfig;
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
 * Qualified Name:     com.zving.schema.BZCDBGatherSchema
 * JD-Core Version:    0.5.4
 */