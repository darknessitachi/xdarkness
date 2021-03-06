 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class BZCArticleSchema extends Schema
 {
   private Long ID;
   private Long SiteID;
   private Long CatalogID;
   private String CatalogInnerCode;
   private String BranchInnerCode;
   private String Title;
   private String SubTitle;
   private String ShortTitle;
   private String TitleStyle;
   private String ShortTitleStyle;
   private String Author;
   private String Type;
   private String Attribute;
   private String URL;
   private String RedirectURL;
   private Long Status;
   private String Summary;
   private String Content;
   private String TopFlag;
   private Date TopDate;
   private String TemplateFlag;
   private String Template;
   private String CommentFlag;
   private String CopyImageFlag;
   private Long OrderFlag;
   private String ReferName;
   private String ReferURL;
   private String Keyword;
   private String Tag;
   private String RelativeArticle;
   private String RecommendArticle;
   private Long ReferType;
   private Long ReferSourceID;
   private Long HitCount;
   private Long StickTime;
   private String PublishFlag;
   private String Priority;
   private String LockUser;
   private Date PublishDate;
   private Date DownlineDate;
   private Date ArchiveDate;
   private Long WorkFlowID;
   private Long IssueID;
   private String Logo;
   private String PageTitle;
   private String ClusterSource;
   private String ClusterTarget;
   private String ReferTarget;
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
     new SchemaColumn("CatalogID", 7, 2, 0, 0, true, false), 
     new SchemaColumn("CatalogInnerCode", 1, 3, 100, 0, true, false), 
     new SchemaColumn("BranchInnerCode", 1, 4, 100, 0, false, false), 
     new SchemaColumn("Title", 1, 5, 200, 0, true, false), 
     new SchemaColumn("SubTitle", 1, 6, 200, 0, false, false), 
     new SchemaColumn("ShortTitle", 1, 7, 200, 0, false, false), 
     new SchemaColumn("TitleStyle", 1, 8, 100, 0, false, false), 
     new SchemaColumn("ShortTitleStyle", 1, 9, 100, 0, false, false), 
     new SchemaColumn("Author", 1, 10, 50, 0, false, false), 
     new SchemaColumn("Type", 1, 11, 2, 0, true, false), 
     new SchemaColumn("Attribute", 1, 12, 100, 0, false, false), 
     new SchemaColumn("URL", 1, 13, 200, 0, false, false), 
     new SchemaColumn("RedirectURL", 1, 14, 200, 0, false, false), 
     new SchemaColumn("Status", 7, 15, 0, 0, false, false), 
     new SchemaColumn("Summary", 1, 16, 2000, 0, false, false), 
     new SchemaColumn("Content", 10, 17, 0, 0, false, false), 
     new SchemaColumn("TopFlag", 1, 18, 2, 0, true, false), 
     new SchemaColumn("TopDate", 0, 19, 0, 0, false, false), 
     new SchemaColumn("TemplateFlag", 1, 20, 2, 0, true, false), 
     new SchemaColumn("Template", 1, 21, 100, 0, false, false), 
     new SchemaColumn("CommentFlag", 1, 22, 2, 0, true, false), 
     new SchemaColumn("CopyImageFlag", 1, 23, 2, 0, false, false), 
     new SchemaColumn("OrderFlag", 7, 24, 0, 0, true, false), 
     new SchemaColumn("ReferName", 1, 25, 100, 0, false, false), 
     new SchemaColumn("ReferURL", 1, 26, 200, 0, false, false), 
     new SchemaColumn("Keyword", 1, 27, 100, 0, false, false), 
     new SchemaColumn("Tag", 1, 28, 1000, 0, false, false), 
     new SchemaColumn("RelativeArticle", 1, 29, 200, 0, false, false), 
     new SchemaColumn("RecommendArticle", 1, 30, 200, 0, false, false), 
     new SchemaColumn("ReferType", 7, 31, 0, 0, false, false), 
     new SchemaColumn("ReferSourceID", 7, 32, 0, 0, false, false), 
     new SchemaColumn("HitCount", 7, 33, 0, 0, true, false), 
     new SchemaColumn("StickTime", 7, 34, 0, 0, true, false), 
     new SchemaColumn("PublishFlag", 1, 35, 2, 0, true, false), 
     new SchemaColumn("Priority", 1, 36, 2, 0, false, false), 
     new SchemaColumn("LockUser", 1, 37, 50, 0, false, false), 
     new SchemaColumn("PublishDate", 0, 38, 0, 0, false, false), 
     new SchemaColumn("DownlineDate", 0, 39, 0, 0, false, false), 
     new SchemaColumn("ArchiveDate", 0, 40, 0, 0, false, false), 
     new SchemaColumn("WorkFlowID", 7, 41, 0, 0, false, false), 
     new SchemaColumn("IssueID", 7, 42, 0, 0, false, false), 
     new SchemaColumn("Logo", 1, 43, 100, 0, false, false), 
     new SchemaColumn("PageTitle", 1, 44, 200, 0, false, false), 
     new SchemaColumn("ClusterSource", 1, 45, 200, 0, false, false), 
     new SchemaColumn("ClusterTarget", 1, 46, 1000, 0, false, false), 
     new SchemaColumn("ReferTarget", 1, 47, 1000, 0, false, false), 
     new SchemaColumn("Prop1", 1, 48, 50, 0, false, false), 
     new SchemaColumn("Prop2", 1, 49, 50, 0, false, false), 
     new SchemaColumn("Prop3", 1, 50, 50, 0, false, false), 
     new SchemaColumn("Prop4", 1, 51, 50, 0, false, false), 
     new SchemaColumn("AddUser", 1, 52, 50, 0, true, false), 
     new SchemaColumn("AddTime", 0, 53, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 54, 50, 0, false, false), 
     new SchemaColumn("ModifyTime", 0, 55, 0, 0, false, false), 
     new SchemaColumn("BackupNo", 1, 56, 15, 0, true, true), 
     new SchemaColumn("BackupOperator", 1, 57, 200, 0, true, false), 
     new SchemaColumn("BackupTime", 0, 58, 0, 0, true, false), 
     new SchemaColumn("BackupMemo", 1, 59, 50, 0, false, false) };
   public static final String _TableCode = "BZCArticle";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into BZCArticle values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update BZCArticle set ID=?,SiteID=?,CatalogID=?,CatalogInnerCode=?,BranchInnerCode=?,Title=?,SubTitle=?,ShortTitle=?,TitleStyle=?,ShortTitleStyle=?,Author=?,Type=?,Attribute=?,URL=?,RedirectURL=?,Status=?,Summary=?,Content=?,TopFlag=?,TopDate=?,TemplateFlag=?,Template=?,CommentFlag=?,CopyImageFlag=?,OrderFlag=?,ReferName=?,ReferURL=?,Keyword=?,Tag=?,RelativeArticle=?,RecommendArticle=?,ReferType=?,ReferSourceID=?,HitCount=?,StickTime=?,PublishFlag=?,Priority=?,LockUser=?,PublishDate=?,DownlineDate=?,ArchiveDate=?,WorkFlowID=?,IssueID=?,Logo=?,PageTitle=?,ClusterSource=?,ClusterTarget=?,ReferTarget=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
   protected static final String _DeleteSQL = "delete from BZCArticle  where ID=? and BackupNo=?";
   protected static final String _FillAllSQL = "select * from BZCArticle  where ID=? and BackupNo=?";
 
   public BZCArticleSchema()
   {
     this.TableCode = "BZCArticle";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into BZCArticle values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZCArticle set ID=?,SiteID=?,CatalogID=?,CatalogInnerCode=?,BranchInnerCode=?,Title=?,SubTitle=?,ShortTitle=?,TitleStyle=?,ShortTitleStyle=?,Author=?,Type=?,Attribute=?,URL=?,RedirectURL=?,Status=?,Summary=?,Content=?,TopFlag=?,TopDate=?,TemplateFlag=?,Template=?,CommentFlag=?,CopyImageFlag=?,OrderFlag=?,ReferName=?,ReferURL=?,Keyword=?,Tag=?,RelativeArticle=?,RecommendArticle=?,ReferType=?,ReferSourceID=?,HitCount=?,StickTime=?,PublishFlag=?,Priority=?,LockUser=?,PublishDate=?,DownlineDate=?,ArchiveDate=?,WorkFlowID=?,IssueID=?,Logo=?,PageTitle=?,ClusterSource=?,ClusterTarget=?,ReferTarget=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZCArticle  where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZCArticle  where ID=? and BackupNo=?";
     this.HasSetFlag = new boolean[60];
   }
 
   protected Schema newInstance() {
     return new BZCArticleSchema();
   }
 
   protected SchemaSet newSet() {
     return new BZCArticleSet();
   }
 
   public BZCArticleSet query() {
     return query(null, -1, -1);
   }
 
   public BZCArticleSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public BZCArticleSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public BZCArticleSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (BZCArticleSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
     if (i == 1) { if (v == null) this.SiteID = null; else this.SiteID = new Long(v.toString()); return; }
     if (i == 2) { if (v == null) this.CatalogID = null; else this.CatalogID = new Long(v.toString()); return; }
     if (i == 3) { this.CatalogInnerCode = ((String)v); return; }
     if (i == 4) { this.BranchInnerCode = ((String)v); return; }
     if (i == 5) { this.Title = ((String)v); return; }
     if (i == 6) { this.SubTitle = ((String)v); return; }
     if (i == 7) { this.ShortTitle = ((String)v); return; }
     if (i == 8) { this.TitleStyle = ((String)v); return; }
     if (i == 9) { this.ShortTitleStyle = ((String)v); return; }
     if (i == 10) { this.Author = ((String)v); return; }
     if (i == 11) { this.Type = ((String)v); return; }
     if (i == 12) { this.Attribute = ((String)v); return; }
     if (i == 13) { this.URL = ((String)v); return; }
     if (i == 14) { this.RedirectURL = ((String)v); return; }
     if (i == 15) { if (v == null) this.Status = null; else this.Status = new Long(v.toString()); return; }
     if (i == 16) { this.Summary = ((String)v); return; }
     if (i == 17) { this.Content = ((String)v); return; }
     if (i == 18) { this.TopFlag = ((String)v); return; }
     if (i == 19) { this.TopDate = ((Date)v); return; }
     if (i == 20) { this.TemplateFlag = ((String)v); return; }
     if (i == 21) { this.Template = ((String)v); return; }
     if (i == 22) { this.CommentFlag = ((String)v); return; }
     if (i == 23) { this.CopyImageFlag = ((String)v); return; }
     if (i == 24) { if (v == null) this.OrderFlag = null; else this.OrderFlag = new Long(v.toString()); return; }
     if (i == 25) { this.ReferName = ((String)v); return; }
     if (i == 26) { this.ReferURL = ((String)v); return; }
     if (i == 27) { this.Keyword = ((String)v); return; }
     if (i == 28) { this.Tag = ((String)v); return; }
     if (i == 29) { this.RelativeArticle = ((String)v); return; }
     if (i == 30) { this.RecommendArticle = ((String)v); return; }
     if (i == 31) { if (v == null) this.ReferType = null; else this.ReferType = new Long(v.toString()); return; }
     if (i == 32) { if (v == null) this.ReferSourceID = null; else this.ReferSourceID = new Long(v.toString()); return; }
     if (i == 33) { if (v == null) this.HitCount = null; else this.HitCount = new Long(v.toString()); return; }
     if (i == 34) { if (v == null) this.StickTime = null; else this.StickTime = new Long(v.toString()); return; }
     if (i == 35) { this.PublishFlag = ((String)v); return; }
     if (i == 36) { this.Priority = ((String)v); return; }
     if (i == 37) { this.LockUser = ((String)v); return; }
     if (i == 38) { this.PublishDate = ((Date)v); return; }
     if (i == 39) { this.DownlineDate = ((Date)v); return; }
     if (i == 40) { this.ArchiveDate = ((Date)v); return; }
     if (i == 41) { if (v == null) this.WorkFlowID = null; else this.WorkFlowID = new Long(v.toString()); return; }
     if (i == 42) { if (v == null) this.IssueID = null; else this.IssueID = new Long(v.toString()); return; }
     if (i == 43) { this.Logo = ((String)v); return; }
     if (i == 44) { this.PageTitle = ((String)v); return; }
     if (i == 45) { this.ClusterSource = ((String)v); return; }
     if (i == 46) { this.ClusterTarget = ((String)v); return; }
     if (i == 47) { this.ReferTarget = ((String)v); return; }
     if (i == 48) { this.Prop1 = ((String)v); return; }
     if (i == 49) { this.Prop2 = ((String)v); return; }
     if (i == 50) { this.Prop3 = ((String)v); return; }
     if (i == 51) { this.Prop4 = ((String)v); return; }
     if (i == 52) { this.AddUser = ((String)v); return; }
     if (i == 53) { this.AddTime = ((Date)v); return; }
     if (i == 54) { this.ModifyUser = ((String)v); return; }
     if (i == 55) { this.ModifyTime = ((Date)v); return; }
     if (i == 56) { this.BackupNo = ((String)v); return; }
     if (i == 57) { this.BackupOperator = ((String)v); return; }
     if (i == 58) { this.BackupTime = ((Date)v); return; }
     if (i != 59) return; this.BackupMemo = ((String)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.ID;
     if (i == 1) return this.SiteID;
     if (i == 2) return this.CatalogID;
     if (i == 3) return this.CatalogInnerCode;
     if (i == 4) return this.BranchInnerCode;
     if (i == 5) return this.Title;
     if (i == 6) return this.SubTitle;
     if (i == 7) return this.ShortTitle;
     if (i == 8) return this.TitleStyle;
     if (i == 9) return this.ShortTitleStyle;
     if (i == 10) return this.Author;
     if (i == 11) return this.Type;
     if (i == 12) return this.Attribute;
     if (i == 13) return this.URL;
     if (i == 14) return this.RedirectURL;
     if (i == 15) return this.Status;
     if (i == 16) return this.Summary;
     if (i == 17) return this.Content;
     if (i == 18) return this.TopFlag;
     if (i == 19) return this.TopDate;
     if (i == 20) return this.TemplateFlag;
     if (i == 21) return this.Template;
     if (i == 22) return this.CommentFlag;
     if (i == 23) return this.CopyImageFlag;
     if (i == 24) return this.OrderFlag;
     if (i == 25) return this.ReferName;
     if (i == 26) return this.ReferURL;
     if (i == 27) return this.Keyword;
     if (i == 28) return this.Tag;
     if (i == 29) return this.RelativeArticle;
     if (i == 30) return this.RecommendArticle;
     if (i == 31) return this.ReferType;
     if (i == 32) return this.ReferSourceID;
     if (i == 33) return this.HitCount;
     if (i == 34) return this.StickTime;
     if (i == 35) return this.PublishFlag;
     if (i == 36) return this.Priority;
     if (i == 37) return this.LockUser;
     if (i == 38) return this.PublishDate;
     if (i == 39) return this.DownlineDate;
     if (i == 40) return this.ArchiveDate;
     if (i == 41) return this.WorkFlowID;
     if (i == 42) return this.IssueID;
     if (i == 43) return this.Logo;
     if (i == 44) return this.PageTitle;
     if (i == 45) return this.ClusterSource;
     if (i == 46) return this.ClusterTarget;
     if (i == 47) return this.ReferTarget;
     if (i == 48) return this.Prop1;
     if (i == 49) return this.Prop2;
     if (i == 50) return this.Prop3;
     if (i == 51) return this.Prop4;
     if (i == 52) return this.AddUser;
     if (i == 53) return this.AddTime;
     if (i == 54) return this.ModifyUser;
     if (i == 55) return this.ModifyTime;
     if (i == 56) return this.BackupNo;
     if (i == 57) return this.BackupOperator;
     if (i == 58) return this.BackupTime;
     if (i == 59) return this.BackupMemo;
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
 
   public String getBranchInnerCode()
   {
     return this.BranchInnerCode;
   }
 
   public void setBranchInnerCode(String branchInnerCode)
   {
     this.BranchInnerCode = branchInnerCode;
   }
 
   public String getTitle()
   {
     return this.Title;
   }
 
   public void setTitle(String title)
   {
     this.Title = title;
   }
 
   public String getSubTitle()
   {
     return this.SubTitle;
   }
 
   public void setSubTitle(String subTitle)
   {
     this.SubTitle = subTitle;
   }
 
   public String getShortTitle()
   {
     return this.ShortTitle;
   }
 
   public void setShortTitle(String shortTitle)
   {
     this.ShortTitle = shortTitle;
   }
 
   public String getTitleStyle()
   {
     return this.TitleStyle;
   }
 
   public void setTitleStyle(String titleStyle)
   {
     this.TitleStyle = titleStyle;
   }
 
   public String getShortTitleStyle()
   {
     return this.ShortTitleStyle;
   }
 
   public void setShortTitleStyle(String shortTitleStyle)
   {
     this.ShortTitleStyle = shortTitleStyle;
   }
 
   public String getAuthor()
   {
     return this.Author;
   }
 
   public void setAuthor(String author)
   {
     this.Author = author;
   }
 
   public String getType()
   {
     return this.Type;
   }
 
   public void setType(String type)
   {
     this.Type = type;
   }
 
   public String getAttribute()
   {
     return this.Attribute;
   }
 
   public void setAttribute(String attribute)
   {
     this.Attribute = attribute;
   }
 
   public String getURL()
   {
     return this.URL;
   }
 
   public void setURL(String uRL)
   {
     this.URL = uRL;
   }
 
   public String getRedirectURL()
   {
     return this.RedirectURL;
   }
 
   public void setRedirectURL(String redirectURL)
   {
     this.RedirectURL = redirectURL;
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
 
   public String getSummary()
   {
     return this.Summary;
   }
 
   public void setSummary(String summary)
   {
     this.Summary = summary;
   }
 
   public String getContent()
   {
     return this.Content;
   }
 
   public void setContent(String content)
   {
     this.Content = content;
   }
 
   public String getTopFlag()
   {
     return this.TopFlag;
   }
 
   public void setTopFlag(String topFlag)
   {
     this.TopFlag = topFlag;
   }
 
   public Date getTopDate()
   {
     return this.TopDate;
   }
 
   public void setTopDate(Date topDate)
   {
     this.TopDate = topDate;
   }
 
   public String getTemplateFlag()
   {
     return this.TemplateFlag;
   }
 
   public void setTemplateFlag(String templateFlag)
   {
     this.TemplateFlag = templateFlag;
   }
 
   public String getTemplate()
   {
     return this.Template;
   }
 
   public void setTemplate(String template)
   {
     this.Template = template;
   }
 
   public String getCommentFlag()
   {
     return this.CommentFlag;
   }
 
   public void setCommentFlag(String commentFlag)
   {
     this.CommentFlag = commentFlag;
   }
 
   public String getCopyImageFlag()
   {
     return this.CopyImageFlag;
   }
 
   public void setCopyImageFlag(String copyImageFlag)
   {
     this.CopyImageFlag = copyImageFlag;
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
 
   public String getReferName()
   {
     return this.ReferName;
   }
 
   public void setReferName(String referName)
   {
     this.ReferName = referName;
   }
 
   public String getReferURL()
   {
     return this.ReferURL;
   }
 
   public void setReferURL(String referURL)
   {
     this.ReferURL = referURL;
   }
 
   public String getKeyword()
   {
     return this.Keyword;
   }
 
   public void setKeyword(String keyword)
   {
     this.Keyword = keyword;
   }
 
   public String getTag()
   {
     return this.Tag;
   }
 
   public void setTag(String tag)
   {
     this.Tag = tag;
   }
 
   public String getRelativeArticle()
   {
     return this.RelativeArticle;
   }
 
   public void setRelativeArticle(String relativeArticle)
   {
     this.RelativeArticle = relativeArticle;
   }
 
   public String getRecommendArticle()
   {
     return this.RecommendArticle;
   }
 
   public void setRecommendArticle(String recommendArticle)
   {
     this.RecommendArticle = recommendArticle;
   }
 
   public long getReferType()
   {
     if (this.ReferType == null) return 0L;
     return this.ReferType.longValue();
   }
 
   public void setReferType(long referType)
   {
     this.ReferType = new Long(referType);
   }
 
   public void setReferType(String referType)
   {
     if (referType == null) {
       this.ReferType = null;
       return;
     }
     this.ReferType = new Long(referType);
   }
 
   public long getReferSourceID()
   {
     if (this.ReferSourceID == null) return 0L;
     return this.ReferSourceID.longValue();
   }
 
   public void setReferSourceID(long referSourceID)
   {
     this.ReferSourceID = new Long(referSourceID);
   }
 
   public void setReferSourceID(String referSourceID)
   {
     if (referSourceID == null) {
       this.ReferSourceID = null;
       return;
     }
     this.ReferSourceID = new Long(referSourceID);
   }
 
   public long getHitCount()
   {
     if (this.HitCount == null) return 0L;
     return this.HitCount.longValue();
   }
 
   public void setHitCount(long hitCount)
   {
     this.HitCount = new Long(hitCount);
   }
 
   public void setHitCount(String hitCount)
   {
     if (hitCount == null) {
       this.HitCount = null;
       return;
     }
     this.HitCount = new Long(hitCount);
   }
 
   public long getStickTime()
   {
     if (this.StickTime == null) return 0L;
     return this.StickTime.longValue();
   }
 
   public void setStickTime(long stickTime)
   {
     this.StickTime = new Long(stickTime);
   }
 
   public void setStickTime(String stickTime)
   {
     if (stickTime == null) {
       this.StickTime = null;
       return;
     }
     this.StickTime = new Long(stickTime);
   }
 
   public String getPublishFlag()
   {
     return this.PublishFlag;
   }
 
   public void setPublishFlag(String publishFlag)
   {
     this.PublishFlag = publishFlag;
   }
 
   public String getPriority()
   {
     return this.Priority;
   }
 
   public void setPriority(String priority)
   {
     this.Priority = priority;
   }
 
   public String getLockUser()
   {
     return this.LockUser;
   }
 
   public void setLockUser(String lockUser)
   {
     this.LockUser = lockUser;
   }
 
   public Date getPublishDate()
   {
     return this.PublishDate;
   }
 
   public void setPublishDate(Date publishDate)
   {
     this.PublishDate = publishDate;
   }
 
   public Date getDownlineDate()
   {
     return this.DownlineDate;
   }
 
   public void setDownlineDate(Date downlineDate)
   {
     this.DownlineDate = downlineDate;
   }
 
   public Date getArchiveDate()
   {
     return this.ArchiveDate;
   }
 
   public void setArchiveDate(Date archiveDate)
   {
     this.ArchiveDate = archiveDate;
   }
 
   public long getWorkFlowID()
   {
     if (this.WorkFlowID == null) return 0L;
     return this.WorkFlowID.longValue();
   }
 
   public void setWorkFlowID(long workFlowID)
   {
     this.WorkFlowID = new Long(workFlowID);
   }
 
   public void setWorkFlowID(String workFlowID)
   {
     if (workFlowID == null) {
       this.WorkFlowID = null;
       return;
     }
     this.WorkFlowID = new Long(workFlowID);
   }
 
   public long getIssueID()
   {
     if (this.IssueID == null) return 0L;
     return this.IssueID.longValue();
   }
 
   public void setIssueID(long issueID)
   {
     this.IssueID = new Long(issueID);
   }
 
   public void setIssueID(String issueID)
   {
     if (issueID == null) {
       this.IssueID = null;
       return;
     }
     this.IssueID = new Long(issueID);
   }
 
   public String getLogo()
   {
     return this.Logo;
   }
 
   public void setLogo(String logo)
   {
     this.Logo = logo;
   }
 
   public String getPageTitle()
   {
     return this.PageTitle;
   }
 
   public void setPageTitle(String pageTitle)
   {
     this.PageTitle = pageTitle;
   }
 
   public String getClusterSource()
   {
     return this.ClusterSource;
   }
 
   public void setClusterSource(String clusterSource)
   {
     this.ClusterSource = clusterSource;
   }
 
   public String getClusterTarget()
   {
     return this.ClusterTarget;
   }
 
   public void setClusterTarget(String clusterTarget)
   {
     this.ClusterTarget = clusterTarget;
   }
 
   public String getReferTarget()
   {
     return this.ReferTarget;
   }
 
   public void setReferTarget(String referTarget)
   {
     this.ReferTarget = referTarget;
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
 * Qualified Name:     com.zving.schema.BZCArticleSchema
 * JD-Core Version:    0.5.4
 */