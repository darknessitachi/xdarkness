 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class ZCCatalogConfigSchema extends Schema
 {
   private Long ID;
   private Long SiteID;
   private Long CatalogID;
   private String CatalogInnerCode;
   private String CronExpression;
   private String PlanType;
   private Date StartTime;
   private String IsUsing;
   private Long HotWordType;
   private String AllowStatus;
   private String AfterEditStatus;
   private String Encoding;
   private String ArchiveTime;
   private String AttachDownFlag;
   private String BranchManageFlag;
   private String AllowInnerDeploy;
   private String InnerDeployPassword;
   private String SyncCatalogInsert;
   private String SyncCatalogModify;
   private String SyncArticleModify;
   private Long AfterSyncStatus;
   private Long AfterModifyStatus;
   private String AllowInnerGather;
   private String InnerGatherPassword;
   private String AllowComment;
   private String CommentAnonymous;
   private String CommentVerify;
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
     new SchemaColumn("CatalogID", 7, 2, 0, 0, false, false), 
     new SchemaColumn("CatalogInnerCode", 1, 3, 100, 0, false, false), 
     new SchemaColumn("CronExpression", 1, 4, 100, 0, false, false), 
     new SchemaColumn("PlanType", 1, 5, 10, 0, false, false), 
     new SchemaColumn("StartTime", 0, 6, 0, 0, false, false), 
     new SchemaColumn("IsUsing", 1, 7, 2, 0, true, false), 
     new SchemaColumn("HotWordType", 7, 8, 0, 0, false, false), 
     new SchemaColumn("AllowStatus", 1, 9, 50, 0, false, false), 
     new SchemaColumn("AfterEditStatus", 1, 10, 50, 0, false, false), 
     new SchemaColumn("Encoding", 1, 11, 20, 0, false, false), 
     new SchemaColumn("ArchiveTime", 1, 12, 10, 0, false, false), 
     new SchemaColumn("AttachDownFlag", 1, 13, 2, 0, false, false), 
     new SchemaColumn("BranchManageFlag", 1, 14, 2, 0, false, false), 
     new SchemaColumn("AllowInnerDeploy", 1, 15, 2, 0, false, false), 
     new SchemaColumn("InnerDeployPassword", 1, 16, 255, 0, false, false), 
     new SchemaColumn("SyncCatalogInsert", 1, 17, 2, 0, false, false), 
     new SchemaColumn("SyncCatalogModify", 1, 18, 2, 0, false, false), 
     new SchemaColumn("SyncArticleModify", 1, 19, 2, 0, false, false), 
     new SchemaColumn("AfterSyncStatus", 7, 20, 0, 0, false, false), 
     new SchemaColumn("AfterModifyStatus", 7, 21, 0, 0, false, false), 
     new SchemaColumn("AllowInnerGather", 1, 22, 2, 0, false, false), 
     new SchemaColumn("InnerGatherPassword", 1, 23, 255, 0, false, false), 
     new SchemaColumn("AllowComment", 1, 24, 2, 0, false, false), 
     new SchemaColumn("CommentAnonymous", 1, 25, 2, 0, false, false), 
     new SchemaColumn("CommentVerify", 1, 26, 2, 0, false, false), 
     new SchemaColumn("Prop1", 1, 27, 50, 0, false, false), 
     new SchemaColumn("Prop2", 1, 28, 50, 0, false, false), 
     new SchemaColumn("Prop3", 1, 29, 50, 0, false, false), 
     new SchemaColumn("Prop4", 1, 30, 50, 0, false, false), 
     new SchemaColumn("AddUser", 1, 31, 200, 0, true, false), 
     new SchemaColumn("AddTime", 0, 32, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 33, 200, 0, false, false), 
     new SchemaColumn("ModifyTime", 0, 34, 0, 0, false, false) };
   public static final String _TableCode = "ZCCatalogConfig";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into ZCCatalogConfig values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update ZCCatalogConfig set ID=?,SiteID=?,CatalogID=?,CatalogInnerCode=?,CronExpression=?,PlanType=?,StartTime=?,IsUsing=?,HotWordType=?,AllowStatus=?,AfterEditStatus=?,Encoding=?,ArchiveTime=?,AttachDownFlag=?,BranchManageFlag=?,AllowInnerDeploy=?,InnerDeployPassword=?,SyncCatalogInsert=?,SyncCatalogModify=?,SyncArticleModify=?,AfterSyncStatus=?,AfterModifyStatus=?,AllowInnerGather=?,InnerGatherPassword=?,AllowComment=?,CommentAnonymous=?,CommentVerify=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
   protected static final String _DeleteSQL = "delete from ZCCatalogConfig  where ID=?";
   protected static final String _FillAllSQL = "select * from ZCCatalogConfig  where ID=?";
 
   public ZCCatalogConfigSchema()
   {
     this.TableCode = "ZCCatalogConfig";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into ZCCatalogConfig values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZCCatalogConfig set ID=?,SiteID=?,CatalogID=?,CatalogInnerCode=?,CronExpression=?,PlanType=?,StartTime=?,IsUsing=?,HotWordType=?,AllowStatus=?,AfterEditStatus=?,Encoding=?,ArchiveTime=?,AttachDownFlag=?,BranchManageFlag=?,AllowInnerDeploy=?,InnerDeployPassword=?,SyncCatalogInsert=?,SyncCatalogModify=?,SyncArticleModify=?,AfterSyncStatus=?,AfterModifyStatus=?,AllowInnerGather=?,InnerGatherPassword=?,AllowComment=?,CommentAnonymous=?,CommentVerify=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.DeleteSQL = "delete from ZCCatalogConfig  where ID=?";
     this.FillAllSQL = "select * from ZCCatalogConfig  where ID=?";
     this.HasSetFlag = new boolean[35];
   }
 
   protected Schema newInstance() {
     return new ZCCatalogConfigSchema();
   }
 
   protected SchemaSet newSet() {
     return new ZCCatalogConfigSet();
   }
 
   public ZCCatalogConfigSet query() {
     return query(null, -1, -1);
   }
 
   public ZCCatalogConfigSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public ZCCatalogConfigSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public ZCCatalogConfigSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (ZCCatalogConfigSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
     if (i == 1) { if (v == null) this.SiteID = null; else this.SiteID = new Long(v.toString()); return; }
     if (i == 2) { if (v == null) this.CatalogID = null; else this.CatalogID = new Long(v.toString()); return; }
     if (i == 3) { this.CatalogInnerCode = ((String)v); return; }
     if (i == 4) { this.CronExpression = ((String)v); return; }
     if (i == 5) { this.PlanType = ((String)v); return; }
     if (i == 6) { this.StartTime = ((Date)v); return; }
     if (i == 7) { this.IsUsing = ((String)v); return; }
     if (i == 8) { if (v == null) this.HotWordType = null; else this.HotWordType = new Long(v.toString()); return; }
     if (i == 9) { this.AllowStatus = ((String)v); return; }
     if (i == 10) { this.AfterEditStatus = ((String)v); return; }
     if (i == 11) { this.Encoding = ((String)v); return; }
     if (i == 12) { this.ArchiveTime = ((String)v); return; }
     if (i == 13) { this.AttachDownFlag = ((String)v); return; }
     if (i == 14) { this.BranchManageFlag = ((String)v); return; }
     if (i == 15) { this.AllowInnerDeploy = ((String)v); return; }
     if (i == 16) { this.InnerDeployPassword = ((String)v); return; }
     if (i == 17) { this.SyncCatalogInsert = ((String)v); return; }
     if (i == 18) { this.SyncCatalogModify = ((String)v); return; }
     if (i == 19) { this.SyncArticleModify = ((String)v); return; }
     if (i == 20) { if (v == null) this.AfterSyncStatus = null; else this.AfterSyncStatus = new Long(v.toString()); return; }
     if (i == 21) { if (v == null) this.AfterModifyStatus = null; else this.AfterModifyStatus = new Long(v.toString()); return; }
     if (i == 22) { this.AllowInnerGather = ((String)v); return; }
     if (i == 23) { this.InnerGatherPassword = ((String)v); return; }
     if (i == 24) { this.AllowComment = ((String)v); return; }
     if (i == 25) { this.CommentAnonymous = ((String)v); return; }
     if (i == 26) { this.CommentVerify = ((String)v); return; }
     if (i == 27) { this.Prop1 = ((String)v); return; }
     if (i == 28) { this.Prop2 = ((String)v); return; }
     if (i == 29) { this.Prop3 = ((String)v); return; }
     if (i == 30) { this.Prop4 = ((String)v); return; }
     if (i == 31) { this.AddUser = ((String)v); return; }
     if (i == 32) { this.AddTime = ((Date)v); return; }
     if (i == 33) { this.ModifyUser = ((String)v); return; }
     if (i != 34) return; this.ModifyTime = ((Date)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.ID;
     if (i == 1) return this.SiteID;
     if (i == 2) return this.CatalogID;
     if (i == 3) return this.CatalogInnerCode;
     if (i == 4) return this.CronExpression;
     if (i == 5) return this.PlanType;
     if (i == 6) return this.StartTime;
     if (i == 7) return this.IsUsing;
     if (i == 8) return this.HotWordType;
     if (i == 9) return this.AllowStatus;
     if (i == 10) return this.AfterEditStatus;
     if (i == 11) return this.Encoding;
     if (i == 12) return this.ArchiveTime;
     if (i == 13) return this.AttachDownFlag;
     if (i == 14) return this.BranchManageFlag;
     if (i == 15) return this.AllowInnerDeploy;
     if (i == 16) return this.InnerDeployPassword;
     if (i == 17) return this.SyncCatalogInsert;
     if (i == 18) return this.SyncCatalogModify;
     if (i == 19) return this.SyncArticleModify;
     if (i == 20) return this.AfterSyncStatus;
     if (i == 21) return this.AfterModifyStatus;
     if (i == 22) return this.AllowInnerGather;
     if (i == 23) return this.InnerGatherPassword;
     if (i == 24) return this.AllowComment;
     if (i == 25) return this.CommentAnonymous;
     if (i == 26) return this.CommentVerify;
     if (i == 27) return this.Prop1;
     if (i == 28) return this.Prop2;
     if (i == 29) return this.Prop3;
     if (i == 30) return this.Prop4;
     if (i == 31) return this.AddUser;
     if (i == 32) return this.AddTime;
     if (i == 33) return this.ModifyUser;
     if (i == 34) return this.ModifyTime;
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
 
   public String getCatalogInnerCode()
   {
     return this.CatalogInnerCode;
   }
 
   public void setCatalogInnerCode(String catalogInnerCode)
   {
     this.CatalogInnerCode = catalogInnerCode;
   }
 
   public String getCronExpression()
   {
     return this.CronExpression;
   }
 
   public void setCronExpression(String cronExpression)
   {
     this.CronExpression = cronExpression;
   }
 
   public String getPlanType()
   {
     return this.PlanType;
   }
 
   public void setPlanType(String planType)
   {
     this.PlanType = planType;
   }
 
   public Date getStartTime()
   {
     return this.StartTime;
   }
 
   public void setStartTime(Date startTime)
   {
     this.StartTime = startTime;
   }
 
   public String getIsUsing()
   {
     return this.IsUsing;
   }
 
   public void setIsUsing(String isUsing)
   {
     this.IsUsing = isUsing;
   }
 
   public long getHotWordType()
   {
     if (this.HotWordType == null) return 0L;
     return this.HotWordType.longValue();
   }
 
   public void setHotWordType(long hotWordType)
   {
     this.HotWordType = new Long(hotWordType);
   }
 
   public void setHotWordType(String hotWordType)
   {
     if (hotWordType == null) {
       this.HotWordType = null;
       return;
     }
     this.HotWordType = new Long(hotWordType);
   }
 
   public String getAllowStatus()
   {
     return this.AllowStatus;
   }
 
   public void setAllowStatus(String allowStatus)
   {
     this.AllowStatus = allowStatus;
   }
 
   public String getAfterEditStatus()
   {
     return this.AfterEditStatus;
   }
 
   public void setAfterEditStatus(String afterEditStatus)
   {
     this.AfterEditStatus = afterEditStatus;
   }
 
   public String getEncoding()
   {
     return this.Encoding;
   }
 
   public void setEncoding(String encoding)
   {
     this.Encoding = encoding;
   }
 
   public String getArchiveTime()
   {
     return this.ArchiveTime;
   }
 
   public void setArchiveTime(String archiveTime)
   {
     this.ArchiveTime = archiveTime;
   }
 
   public String getAttachDownFlag()
   {
     return this.AttachDownFlag;
   }
 
   public void setAttachDownFlag(String attachDownFlag)
   {
     this.AttachDownFlag = attachDownFlag;
   }
 
   public String getBranchManageFlag()
   {
     return this.BranchManageFlag;
   }
 
   public void setBranchManageFlag(String branchManageFlag)
   {
     this.BranchManageFlag = branchManageFlag;
   }
 
   public String getAllowInnerDeploy()
   {
     return this.AllowInnerDeploy;
   }
 
   public void setAllowInnerDeploy(String allowInnerDeploy)
   {
     this.AllowInnerDeploy = allowInnerDeploy;
   }
 
   public String getInnerDeployPassword()
   {
     return this.InnerDeployPassword;
   }
 
   public void setInnerDeployPassword(String innerDeployPassword)
   {
     this.InnerDeployPassword = innerDeployPassword;
   }
 
   public String getSyncCatalogInsert()
   {
     return this.SyncCatalogInsert;
   }
 
   public void setSyncCatalogInsert(String syncCatalogInsert)
   {
     this.SyncCatalogInsert = syncCatalogInsert;
   }
 
   public String getSyncCatalogModify()
   {
     return this.SyncCatalogModify;
   }
 
   public void setSyncCatalogModify(String syncCatalogModify)
   {
     this.SyncCatalogModify = syncCatalogModify;
   }
 
   public String getSyncArticleModify()
   {
     return this.SyncArticleModify;
   }
 
   public void setSyncArticleModify(String syncArticleModify)
   {
     this.SyncArticleModify = syncArticleModify;
   }
 
   public long getAfterSyncStatus()
   {
     if (this.AfterSyncStatus == null) return 0L;
     return this.AfterSyncStatus.longValue();
   }
 
   public void setAfterSyncStatus(long afterSyncStatus)
   {
     this.AfterSyncStatus = new Long(afterSyncStatus);
   }
 
   public void setAfterSyncStatus(String afterSyncStatus)
   {
     if (afterSyncStatus == null) {
       this.AfterSyncStatus = null;
       return;
     }
     this.AfterSyncStatus = new Long(afterSyncStatus);
   }
 
   public long getAfterModifyStatus()
   {
     if (this.AfterModifyStatus == null) return 0L;
     return this.AfterModifyStatus.longValue();
   }
 
   public void setAfterModifyStatus(long afterModifyStatus)
   {
     this.AfterModifyStatus = new Long(afterModifyStatus);
   }
 
   public void setAfterModifyStatus(String afterModifyStatus)
   {
     if (afterModifyStatus == null) {
       this.AfterModifyStatus = null;
       return;
     }
     this.AfterModifyStatus = new Long(afterModifyStatus);
   }
 
   public String getAllowInnerGather()
   {
     return this.AllowInnerGather;
   }
 
   public void setAllowInnerGather(String allowInnerGather)
   {
     this.AllowInnerGather = allowInnerGather;
   }
 
   public String getInnerGatherPassword()
   {
     return this.InnerGatherPassword;
   }
 
   public void setInnerGatherPassword(String innerGatherPassword)
   {
     this.InnerGatherPassword = innerGatherPassword;
   }
 
   public String getAllowComment()
   {
     return this.AllowComment;
   }
 
   public void setAllowComment(String allowComment)
   {
     this.AllowComment = allowComment;
   }
 
   public String getCommentAnonymous()
   {
     return this.CommentAnonymous;
   }
 
   public void setCommentAnonymous(String commentAnonymous)
   {
     this.CommentAnonymous = commentAnonymous;
   }
 
   public String getCommentVerify()
   {
     return this.CommentVerify;
   }
 
   public void setCommentVerify(String commentVerify)
   {
     this.CommentVerify = commentVerify;
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
 * Qualified Name:     com.zving.schema.ZCCatalogConfigSchema
 * JD-Core Version:    0.5.4
 */