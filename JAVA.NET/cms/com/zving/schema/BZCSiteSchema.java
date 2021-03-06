 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class BZCSiteSchema extends Schema
 {
   private Long ID;
   private String Name;
   private String Alias;
   private String Info;
   private String BranchInnerCode;
   private String URL;
   private String RootPath;
   private String IndexTemplate;
   private String ListTemplate;
   private String DetailTemplate;
   private String EditorCss;
   private String Workflow;
   private Long OrderFlag;
   private String LogoFileName;
   private String MessageBoardFlag;
   private String CommentAuditFlag;
   private Long ChannelCount;
   private Long MagzineCount;
   private Long SpecialCount;
   private Long ImageLibCount;
   private Long VideoLibCount;
   private Long AudioLibCount;
   private Long AttachmentLibCount;
   private Long ArticleCount;
   private Long HitCount;
   private String ConfigXML;
   private String AutoIndexFlag;
   private String AutoStatFlag;
   private String HeaderTemplate;
   private String TopTemplate;
   private String BottomTemplate;
   private String AllowContribute;
   private String BBSEnableFlag;
   private String ShopEnableFlag;
   private String Meta_Keywords;
   private String Meta_Description;
   private String Prop1;
   private String Prop2;
   private String Prop3;
   private String Prop4;
   private String Prop5;
   private String Prop6;
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
     new SchemaColumn("Name", 1, 1, 100, 0, true, false), 
     new SchemaColumn("Alias", 1, 2, 100, 0, true, false), 
     new SchemaColumn("Info", 1, 3, 100, 0, false, false), 
     new SchemaColumn("BranchInnerCode", 1, 4, 100, 0, true, false), 
     new SchemaColumn("URL", 1, 5, 100, 0, true, false), 
     new SchemaColumn("RootPath", 1, 6, 100, 0, false, false), 
     new SchemaColumn("IndexTemplate", 1, 7, 100, 0, false, false), 
     new SchemaColumn("ListTemplate", 1, 8, 100, 0, false, false), 
     new SchemaColumn("DetailTemplate", 1, 9, 100, 0, false, false), 
     new SchemaColumn("EditorCss", 1, 10, 100, 0, false, false), 
     new SchemaColumn("Workflow", 1, 11, 100, 0, false, false), 
     new SchemaColumn("OrderFlag", 7, 12, 0, 0, false, false), 
     new SchemaColumn("LogoFileName", 1, 13, 100, 0, false, false), 
     new SchemaColumn("MessageBoardFlag", 1, 14, 2, 0, false, false), 
     new SchemaColumn("CommentAuditFlag", 1, 15, 1, 0, false, false), 
     new SchemaColumn("ChannelCount", 7, 16, 0, 0, true, false), 
     new SchemaColumn("MagzineCount", 7, 17, 0, 0, true, false), 
     new SchemaColumn("SpecialCount", 7, 18, 0, 0, true, false), 
     new SchemaColumn("ImageLibCount", 7, 19, 0, 0, true, false), 
     new SchemaColumn("VideoLibCount", 7, 20, 0, 0, true, false), 
     new SchemaColumn("AudioLibCount", 7, 21, 0, 0, true, false), 
     new SchemaColumn("AttachmentLibCount", 7, 22, 0, 0, true, false), 
     new SchemaColumn("ArticleCount", 7, 23, 0, 0, true, false), 
     new SchemaColumn("HitCount", 7, 24, 0, 0, true, false), 
     new SchemaColumn("ConfigXML", 10, 25, 0, 0, false, false), 
     new SchemaColumn("AutoIndexFlag", 1, 26, 2, 0, false, false), 
     new SchemaColumn("AutoStatFlag", 1, 27, 2, 0, false, false), 
     new SchemaColumn("HeaderTemplate", 1, 28, 100, 0, false, false), 
     new SchemaColumn("TopTemplate", 1, 29, 100, 0, false, false), 
     new SchemaColumn("BottomTemplate", 1, 30, 100, 0, false, false), 
     new SchemaColumn("AllowContribute", 1, 31, 2, 0, false, false), 
     new SchemaColumn("BBSEnableFlag", 1, 32, 2, 0, false, false), 
     new SchemaColumn("ShopEnableFlag", 1, 33, 2, 0, false, false), 
     new SchemaColumn("Meta_Keywords", 1, 34, 200, 0, false, false), 
     new SchemaColumn("Meta_Description", 1, 35, 400, 0, false, false), 
     new SchemaColumn("Prop1", 1, 36, 100, 0, false, false), 
     new SchemaColumn("Prop2", 1, 37, 100, 0, false, false), 
     new SchemaColumn("Prop3", 1, 38, 100, 0, false, false), 
     new SchemaColumn("Prop4", 1, 39, 100, 0, false, false), 
     new SchemaColumn("Prop5", 1, 40, 100, 0, false, false), 
     new SchemaColumn("Prop6", 1, 41, 100, 0, false, false), 
     new SchemaColumn("AddUser", 1, 42, 200, 0, true, false), 
     new SchemaColumn("AddTime", 0, 43, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 44, 200, 0, false, false), 
     new SchemaColumn("ModifyTime", 0, 45, 0, 0, false, false), 
     new SchemaColumn("BackupNo", 1, 46, 15, 0, true, true), 
     new SchemaColumn("BackupOperator", 1, 47, 200, 0, true, false), 
     new SchemaColumn("BackupTime", 0, 48, 0, 0, true, false), 
     new SchemaColumn("BackupMemo", 1, 49, 50, 0, false, false) };
   public static final String _TableCode = "BZCSite";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into BZCSite values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update BZCSite set ID=?,Name=?,Alias=?,Info=?,BranchInnerCode=?,URL=?,RootPath=?,IndexTemplate=?,ListTemplate=?,DetailTemplate=?,EditorCss=?,Workflow=?,OrderFlag=?,LogoFileName=?,MessageBoardFlag=?,CommentAuditFlag=?,ChannelCount=?,MagzineCount=?,SpecialCount=?,ImageLibCount=?,VideoLibCount=?,AudioLibCount=?,AttachmentLibCount=?,ArticleCount=?,HitCount=?,ConfigXML=?,AutoIndexFlag=?,AutoStatFlag=?,HeaderTemplate=?,TopTemplate=?,BottomTemplate=?,AllowContribute=?,BBSEnableFlag=?,ShopEnableFlag=?,Meta_Keywords=?,Meta_Description=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Prop5=?,Prop6=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
   protected static final String _DeleteSQL = "delete from BZCSite  where ID=? and BackupNo=?";
   protected static final String _FillAllSQL = "select * from BZCSite  where ID=? and BackupNo=?";
 
   public BZCSiteSchema()
   {
     this.TableCode = "BZCSite";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into BZCSite values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZCSite set ID=?,Name=?,Alias=?,Info=?,BranchInnerCode=?,URL=?,RootPath=?,IndexTemplate=?,ListTemplate=?,DetailTemplate=?,EditorCss=?,Workflow=?,OrderFlag=?,LogoFileName=?,MessageBoardFlag=?,CommentAuditFlag=?,ChannelCount=?,MagzineCount=?,SpecialCount=?,ImageLibCount=?,VideoLibCount=?,AudioLibCount=?,AttachmentLibCount=?,ArticleCount=?,HitCount=?,ConfigXML=?,AutoIndexFlag=?,AutoStatFlag=?,HeaderTemplate=?,TopTemplate=?,BottomTemplate=?,AllowContribute=?,BBSEnableFlag=?,ShopEnableFlag=?,Meta_Keywords=?,Meta_Description=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Prop5=?,Prop6=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZCSite  where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZCSite  where ID=? and BackupNo=?";
     this.HasSetFlag = new boolean[50];
   }
 
   protected Schema newInstance() {
     return new BZCSiteSchema();
   }
 
   protected SchemaSet newSet() {
     return new BZCSiteSet();
   }
 
   public BZCSiteSet query() {
     return query(null, -1, -1);
   }
 
   public BZCSiteSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public BZCSiteSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public BZCSiteSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (BZCSiteSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
     if (i == 1) { this.Name = ((String)v); return; }
     if (i == 2) { this.Alias = ((String)v); return; }
     if (i == 3) { this.Info = ((String)v); return; }
     if (i == 4) { this.BranchInnerCode = ((String)v); return; }
     if (i == 5) { this.URL = ((String)v); return; }
     if (i == 6) { this.RootPath = ((String)v); return; }
     if (i == 7) { this.IndexTemplate = ((String)v); return; }
     if (i == 8) { this.ListTemplate = ((String)v); return; }
     if (i == 9) { this.DetailTemplate = ((String)v); return; }
     if (i == 10) { this.EditorCss = ((String)v); return; }
     if (i == 11) { this.Workflow = ((String)v); return; }
     if (i == 12) { if (v == null) this.OrderFlag = null; else this.OrderFlag = new Long(v.toString()); return; }
     if (i == 13) { this.LogoFileName = ((String)v); return; }
     if (i == 14) { this.MessageBoardFlag = ((String)v); return; }
     if (i == 15) { this.CommentAuditFlag = ((String)v); return; }
     if (i == 16) { if (v == null) this.ChannelCount = null; else this.ChannelCount = new Long(v.toString()); return; }
     if (i == 17) { if (v == null) this.MagzineCount = null; else this.MagzineCount = new Long(v.toString()); return; }
     if (i == 18) { if (v == null) this.SpecialCount = null; else this.SpecialCount = new Long(v.toString()); return; }
     if (i == 19) { if (v == null) this.ImageLibCount = null; else this.ImageLibCount = new Long(v.toString()); return; }
     if (i == 20) { if (v == null) this.VideoLibCount = null; else this.VideoLibCount = new Long(v.toString()); return; }
     if (i == 21) { if (v == null) this.AudioLibCount = null; else this.AudioLibCount = new Long(v.toString()); return; }
     if (i == 22) { if (v == null) this.AttachmentLibCount = null; else this.AttachmentLibCount = new Long(v.toString()); return; }
     if (i == 23) { if (v == null) this.ArticleCount = null; else this.ArticleCount = new Long(v.toString()); return; }
     if (i == 24) { if (v == null) this.HitCount = null; else this.HitCount = new Long(v.toString()); return; }
     if (i == 25) { this.ConfigXML = ((String)v); return; }
     if (i == 26) { this.AutoIndexFlag = ((String)v); return; }
     if (i == 27) { this.AutoStatFlag = ((String)v); return; }
     if (i == 28) { this.HeaderTemplate = ((String)v); return; }
     if (i == 29) { this.TopTemplate = ((String)v); return; }
     if (i == 30) { this.BottomTemplate = ((String)v); return; }
     if (i == 31) { this.AllowContribute = ((String)v); return; }
     if (i == 32) { this.BBSEnableFlag = ((String)v); return; }
     if (i == 33) { this.ShopEnableFlag = ((String)v); return; }
     if (i == 34) { this.Meta_Keywords = ((String)v); return; }
     if (i == 35) { this.Meta_Description = ((String)v); return; }
     if (i == 36) { this.Prop1 = ((String)v); return; }
     if (i == 37) { this.Prop2 = ((String)v); return; }
     if (i == 38) { this.Prop3 = ((String)v); return; }
     if (i == 39) { this.Prop4 = ((String)v); return; }
     if (i == 40) { this.Prop5 = ((String)v); return; }
     if (i == 41) { this.Prop6 = ((String)v); return; }
     if (i == 42) { this.AddUser = ((String)v); return; }
     if (i == 43) { this.AddTime = ((Date)v); return; }
     if (i == 44) { this.ModifyUser = ((String)v); return; }
     if (i == 45) { this.ModifyTime = ((Date)v); return; }
     if (i == 46) { this.BackupNo = ((String)v); return; }
     if (i == 47) { this.BackupOperator = ((String)v); return; }
     if (i == 48) { this.BackupTime = ((Date)v); return; }
     if (i != 49) return; this.BackupMemo = ((String)v); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.ID;
     if (i == 1) return this.Name;
     if (i == 2) return this.Alias;
     if (i == 3) return this.Info;
     if (i == 4) return this.BranchInnerCode;
     if (i == 5) return this.URL;
     if (i == 6) return this.RootPath;
     if (i == 7) return this.IndexTemplate;
     if (i == 8) return this.ListTemplate;
     if (i == 9) return this.DetailTemplate;
     if (i == 10) return this.EditorCss;
     if (i == 11) return this.Workflow;
     if (i == 12) return this.OrderFlag;
     if (i == 13) return this.LogoFileName;
     if (i == 14) return this.MessageBoardFlag;
     if (i == 15) return this.CommentAuditFlag;
     if (i == 16) return this.ChannelCount;
     if (i == 17) return this.MagzineCount;
     if (i == 18) return this.SpecialCount;
     if (i == 19) return this.ImageLibCount;
     if (i == 20) return this.VideoLibCount;
     if (i == 21) return this.AudioLibCount;
     if (i == 22) return this.AttachmentLibCount;
     if (i == 23) return this.ArticleCount;
     if (i == 24) return this.HitCount;
     if (i == 25) return this.ConfigXML;
     if (i == 26) return this.AutoIndexFlag;
     if (i == 27) return this.AutoStatFlag;
     if (i == 28) return this.HeaderTemplate;
     if (i == 29) return this.TopTemplate;
     if (i == 30) return this.BottomTemplate;
     if (i == 31) return this.AllowContribute;
     if (i == 32) return this.BBSEnableFlag;
     if (i == 33) return this.ShopEnableFlag;
     if (i == 34) return this.Meta_Keywords;
     if (i == 35) return this.Meta_Description;
     if (i == 36) return this.Prop1;
     if (i == 37) return this.Prop2;
     if (i == 38) return this.Prop3;
     if (i == 39) return this.Prop4;
     if (i == 40) return this.Prop5;
     if (i == 41) return this.Prop6;
     if (i == 42) return this.AddUser;
     if (i == 43) return this.AddTime;
     if (i == 44) return this.ModifyUser;
     if (i == 45) return this.ModifyTime;
     if (i == 46) return this.BackupNo;
     if (i == 47) return this.BackupOperator;
     if (i == 48) return this.BackupTime;
     if (i == 49) return this.BackupMemo;
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
 
   public String getInfo()
   {
     return this.Info;
   }
 
   public void setInfo(String info)
   {
     this.Info = info;
   }
 
   public String getBranchInnerCode()
   {
     return this.BranchInnerCode;
   }
 
   public void setBranchInnerCode(String branchInnerCode)
   {
     this.BranchInnerCode = branchInnerCode;
   }
 
   public String getURL()
   {
     return this.URL;
   }
 
   public void setURL(String uRL)
   {
     this.URL = uRL;
   }
 
   public String getRootPath()
   {
     return this.RootPath;
   }
 
   public void setRootPath(String rootPath)
   {
     this.RootPath = rootPath;
   }
 
   public String getIndexTemplate()
   {
     return this.IndexTemplate;
   }
 
   public void setIndexTemplate(String indexTemplate)
   {
     this.IndexTemplate = indexTemplate;
   }
 
   public String getListTemplate()
   {
     return this.ListTemplate;
   }
 
   public void setListTemplate(String listTemplate)
   {
     this.ListTemplate = listTemplate;
   }
 
   public String getDetailTemplate()
   {
     return this.DetailTemplate;
   }
 
   public void setDetailTemplate(String detailTemplate)
   {
     this.DetailTemplate = detailTemplate;
   }
 
   public String getEditorCss()
   {
     return this.EditorCss;
   }
 
   public void setEditorCss(String editorCss)
   {
     this.EditorCss = editorCss;
   }
 
   public String getWorkflow()
   {
     return this.Workflow;
   }
 
   public void setWorkflow(String workflow)
   {
     this.Workflow = workflow;
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
 
   public String getLogoFileName()
   {
     return this.LogoFileName;
   }
 
   public void setLogoFileName(String logoFileName)
   {
     this.LogoFileName = logoFileName;
   }
 
   public String getMessageBoardFlag()
   {
     return this.MessageBoardFlag;
   }
 
   public void setMessageBoardFlag(String messageBoardFlag)
   {
     this.MessageBoardFlag = messageBoardFlag;
   }
 
   public String getCommentAuditFlag()
   {
     return this.CommentAuditFlag;
   }
 
   public void setCommentAuditFlag(String commentAuditFlag)
   {
     this.CommentAuditFlag = commentAuditFlag;
   }
 
   public long getChannelCount()
   {
     if (this.ChannelCount == null) return 0L;
     return this.ChannelCount.longValue();
   }
 
   public void setChannelCount(long channelCount)
   {
     this.ChannelCount = new Long(channelCount);
   }
 
   public void setChannelCount(String channelCount)
   {
     if (channelCount == null) {
       this.ChannelCount = null;
       return;
     }
     this.ChannelCount = new Long(channelCount);
   }
 
   public long getMagzineCount()
   {
     if (this.MagzineCount == null) return 0L;
     return this.MagzineCount.longValue();
   }
 
   public void setMagzineCount(long magzineCount)
   {
     this.MagzineCount = new Long(magzineCount);
   }
 
   public void setMagzineCount(String magzineCount)
   {
     if (magzineCount == null) {
       this.MagzineCount = null;
       return;
     }
     this.MagzineCount = new Long(magzineCount);
   }
 
   public long getSpecialCount()
   {
     if (this.SpecialCount == null) return 0L;
     return this.SpecialCount.longValue();
   }
 
   public void setSpecialCount(long specialCount)
   {
     this.SpecialCount = new Long(specialCount);
   }
 
   public void setSpecialCount(String specialCount)
   {
     if (specialCount == null) {
       this.SpecialCount = null;
       return;
     }
     this.SpecialCount = new Long(specialCount);
   }
 
   public long getImageLibCount()
   {
     if (this.ImageLibCount == null) return 0L;
     return this.ImageLibCount.longValue();
   }
 
   public void setImageLibCount(long imageLibCount)
   {
     this.ImageLibCount = new Long(imageLibCount);
   }
 
   public void setImageLibCount(String imageLibCount)
   {
     if (imageLibCount == null) {
       this.ImageLibCount = null;
       return;
     }
     this.ImageLibCount = new Long(imageLibCount);
   }
 
   public long getVideoLibCount()
   {
     if (this.VideoLibCount == null) return 0L;
     return this.VideoLibCount.longValue();
   }
 
   public void setVideoLibCount(long videoLibCount)
   {
     this.VideoLibCount = new Long(videoLibCount);
   }
 
   public void setVideoLibCount(String videoLibCount)
   {
     if (videoLibCount == null) {
       this.VideoLibCount = null;
       return;
     }
     this.VideoLibCount = new Long(videoLibCount);
   }
 
   public long getAudioLibCount()
   {
     if (this.AudioLibCount == null) return 0L;
     return this.AudioLibCount.longValue();
   }
 
   public void setAudioLibCount(long audioLibCount)
   {
     this.AudioLibCount = new Long(audioLibCount);
   }
 
   public void setAudioLibCount(String audioLibCount)
   {
     if (audioLibCount == null) {
       this.AudioLibCount = null;
       return;
     }
     this.AudioLibCount = new Long(audioLibCount);
   }
 
   public long getAttachmentLibCount()
   {
     if (this.AttachmentLibCount == null) return 0L;
     return this.AttachmentLibCount.longValue();
   }
 
   public void setAttachmentLibCount(long attachmentLibCount)
   {
     this.AttachmentLibCount = new Long(attachmentLibCount);
   }
 
   public void setAttachmentLibCount(String attachmentLibCount)
   {
     if (attachmentLibCount == null) {
       this.AttachmentLibCount = null;
       return;
     }
     this.AttachmentLibCount = new Long(attachmentLibCount);
   }
 
   public long getArticleCount()
   {
     if (this.ArticleCount == null) return 0L;
     return this.ArticleCount.longValue();
   }
 
   public void setArticleCount(long articleCount)
   {
     this.ArticleCount = new Long(articleCount);
   }
 
   public void setArticleCount(String articleCount)
   {
     if (articleCount == null) {
       this.ArticleCount = null;
       return;
     }
     this.ArticleCount = new Long(articleCount);
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
 
   public String getConfigXML()
   {
     return this.ConfigXML;
   }
 
   public void setConfigXML(String configXML)
   {
     this.ConfigXML = configXML;
   }
 
   public String getAutoIndexFlag()
   {
     return this.AutoIndexFlag;
   }
 
   public void setAutoIndexFlag(String autoIndexFlag)
   {
     this.AutoIndexFlag = autoIndexFlag;
   }
 
   public String getAutoStatFlag()
   {
     return this.AutoStatFlag;
   }
 
   public void setAutoStatFlag(String autoStatFlag)
   {
     this.AutoStatFlag = autoStatFlag;
   }
 
   public String getHeaderTemplate()
   {
     return this.HeaderTemplate;
   }
 
   public void setHeaderTemplate(String headerTemplate)
   {
     this.HeaderTemplate = headerTemplate;
   }
 
   public String getTopTemplate()
   {
     return this.TopTemplate;
   }
 
   public void setTopTemplate(String topTemplate)
   {
     this.TopTemplate = topTemplate;
   }
 
   public String getBottomTemplate()
   {
     return this.BottomTemplate;
   }
 
   public void setBottomTemplate(String bottomTemplate)
   {
     this.BottomTemplate = bottomTemplate;
   }
 
   public String getAllowContribute()
   {
     return this.AllowContribute;
   }
 
   public void setAllowContribute(String allowContribute)
   {
     this.AllowContribute = allowContribute;
   }
 
   public String getBBSEnableFlag()
   {
     return this.BBSEnableFlag;
   }
 
   public void setBBSEnableFlag(String bBSEnableFlag)
   {
     this.BBSEnableFlag = bBSEnableFlag;
   }
 
   public String getShopEnableFlag()
   {
     return this.ShopEnableFlag;
   }
 
   public void setShopEnableFlag(String shopEnableFlag)
   {
     this.ShopEnableFlag = shopEnableFlag;
   }
 
   public String getMeta_Keywords()
   {
     return this.Meta_Keywords;
   }
 
   public void setMeta_Keywords(String meta_Keywords)
   {
     this.Meta_Keywords = meta_Keywords;
   }
 
   public String getMeta_Description()
   {
     return this.Meta_Description;
   }
 
   public void setMeta_Description(String meta_Description)
   {
     this.Meta_Description = meta_Description;
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
 
   public String getProp5()
   {
     return this.Prop5;
   }
 
   public void setProp5(String prop5)
   {
     this.Prop5 = prop5;
   }
 
   public String getProp6()
   {
     return this.Prop6;
   }
 
   public void setProp6(String prop6)
   {
     this.Prop6 = prop6;
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
 * Qualified Name:     com.zving.schema.BZCSiteSchema
 * JD-Core Version:    0.5.4
 */