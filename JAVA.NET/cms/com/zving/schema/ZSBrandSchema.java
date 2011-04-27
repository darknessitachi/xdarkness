 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class ZSBrandSchema extends Schema
 {
   private Long ID;
   private Long SiteID;
   private String Name;
   private String BranchInnerCode;
   private String Alias;
   private String URL;
   private String ImagePath;
   private String Info;
   private String IndexTemplate;
   private String ListTemplate;
   private String ListNameRule;
   private String DetailTemplate;
   private String DetailNameRule;
   private String RssTemplate;
   private String RssNameRule;
   private Long OrderFlag;
   private Long ListPageSize;
   private Long ListPage;
   private String PublishFlag;
   private String SingleFlag;
   private Long HitCount;
   private String Meta_Keywords;
   private String Meta_Description;
   private String KeywordFlag;
   private String KeywordSetting;
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
     new SchemaColumn("Name", 1, 2, 100, 0, true, false), 
     new SchemaColumn("BranchInnerCode", 1, 3, 100, 0, false, false), 
     new SchemaColumn("Alias", 1, 4, 100, 0, true, false), 
     new SchemaColumn("URL", 1, 5, 100, 0, false, false), 
     new SchemaColumn("ImagePath", 1, 6, 50, 0, false, false), 
     new SchemaColumn("Info", 1, 7, 1024, 0, false, false), 
     new SchemaColumn("IndexTemplate", 1, 8, 200, 0, false, false), 
     new SchemaColumn("ListTemplate", 1, 9, 200, 0, false, false), 
     new SchemaColumn("ListNameRule", 1, 10, 200, 0, false, false), 
     new SchemaColumn("DetailTemplate", 1, 11, 200, 0, false, false), 
     new SchemaColumn("DetailNameRule", 1, 12, 200, 0, false, false), 
     new SchemaColumn("RssTemplate", 1, 13, 200, 0, false, false), 
     new SchemaColumn("RssNameRule", 1, 14, 200, 0, false, false), 
     new SchemaColumn("OrderFlag", 7, 15, 0, 0, true, false), 
     new SchemaColumn("ListPageSize", 7, 16, 0, 0, false, false), 
     new SchemaColumn("ListPage", 7, 17, 0, 0, false, false), 
     new SchemaColumn("PublishFlag", 1, 18, 2, 0, true, false), 
     new SchemaColumn("SingleFlag", 1, 19, 2, 0, false, false), 
     new SchemaColumn("HitCount", 7, 20, 0, 0, false, false), 
     new SchemaColumn("Meta_Keywords", 1, 21, 200, 0, false, false), 
     new SchemaColumn("Meta_Description", 1, 22, 200, 0, false, false), 
     new SchemaColumn("KeywordFlag", 1, 23, 2, 0, false, false), 
     new SchemaColumn("KeywordSetting", 1, 24, 50, 0, false, false), 
     new SchemaColumn("Memo", 1, 25, 200, 0, false, false), 
     new SchemaColumn("Prop1", 1, 26, 50, 0, false, false), 
     new SchemaColumn("Prop2", 1, 27, 50, 0, false, false), 
     new SchemaColumn("Prop3", 1, 28, 50, 0, false, false), 
     new SchemaColumn("Prop4", 1, 29, 50, 0, false, false), 
     new SchemaColumn("AddUser", 1, 30, 200, 0, true, false), 
     new SchemaColumn("AddTime", 0, 31, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 32, 200, 0, false, false), 
     new SchemaColumn("ModifyTime", 0, 33, 0, 0, false, false) };
   public static final String _TableCode = "ZSBrand";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into ZSBrand values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update ZSBrand set ID=?,SiteID=?,Name=?,BranchInnerCode=?,Alias=?,URL=?,ImagePath=?,Info=?,IndexTemplate=?,ListTemplate=?,ListNameRule=?,DetailTemplate=?,DetailNameRule=?,RssTemplate=?,RssNameRule=?,OrderFlag=?,ListPageSize=?,ListPage=?,PublishFlag=?,SingleFlag=?,HitCount=?,Meta_Keywords=?,Meta_Description=?,KeywordFlag=?,KeywordSetting=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
   protected static final String _DeleteSQL = "delete from ZSBrand  where ID=?";
   protected static final String _FillAllSQL = "select * from ZSBrand  where ID=?";
 
   public ZSBrandSchema()
   {
     this.TableCode = "ZSBrand";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into ZSBrand values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZSBrand set ID=?,SiteID=?,Name=?,BranchInnerCode=?,Alias=?,URL=?,ImagePath=?,Info=?,IndexTemplate=?,ListTemplate=?,ListNameRule=?,DetailTemplate=?,DetailNameRule=?,RssTemplate=?,RssNameRule=?,OrderFlag=?,ListPageSize=?,ListPage=?,PublishFlag=?,SingleFlag=?,HitCount=?,Meta_Keywords=?,Meta_Description=?,KeywordFlag=?,KeywordSetting=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.DeleteSQL = "delete from ZSBrand  where ID=?";
     this.FillAllSQL = "select * from ZSBrand  where ID=?";
     this.HasSetFlag = new boolean[34];
   }
 
   protected Schema newInstance() {
     return new ZSBrandSchema();
   }
 
   protected SchemaSet newSet() {
     return new ZSBrandSet();
   }
 
   public ZSBrandSet query() {
     return query(null, -1, -1);
   }
 
   public ZSBrandSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public ZSBrandSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public ZSBrandSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (ZSBrandSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
     if (i == 1) { if (v == null) this.SiteID = null; else this.SiteID = new Long(v.toString()); return; }
     if (i == 2) { this.Name = ((String)v); return; }
     if (i == 3) { this.BranchInnerCode = ((String)v); return; }
     if (i == 4) { this.Alias = ((String)v); return; }
     if (i == 5) { this.URL = ((String)v); return; }
     if (i == 6) { this.ImagePath = ((String)v); return; }
     if (i == 7) { this.Info = ((String)v); return; }
     if (i == 8) { this.IndexTemplate = ((String)v); return; }
     if (i == 9) { this.ListTemplate = ((String)v); return; }
     if (i == 10) { this.ListNameRule = ((String)v); return; }
     if (i == 11) { this.DetailTemplate = ((String)v); return; }
     if (i == 12) { this.DetailNameRule = ((String)v); return; }
     if (i == 13) { this.RssTemplate = ((String)v); return; }
     if (i == 14) { this.RssNameRule = ((String)v); return; }
     if (i == 15) { if (v == null) this.OrderFlag = null; else this.OrderFlag = new Long(v.toString()); return; }
     if (i == 16) { if (v == null) this.ListPageSize = null; else this.ListPageSize = new Long(v.toString()); return; }
     if (i == 17) { if (v == null) this.ListPage = null; else this.ListPage = new Long(v.toString()); return; }
     if (i == 18) { this.PublishFlag = ((String)v); return; }
     if (i == 19) { this.SingleFlag = ((String)v); return; }
     if (i == 20) { if (v == null) this.HitCount = null; else this.HitCount = new Long(v.toString()); return; }
     if (i == 21) { this.Meta_Keywords = ((String)v); return; }
     if (i == 22) { this.Meta_Description = ((String)v); return; }
     if (i == 23) { this.KeywordFlag = ((String)v); return; }
     if (i == 24) { this.KeywordSetting = ((String)v); return; }
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
     if (i == 2) return this.Name;
     if (i == 3) return this.BranchInnerCode;
     if (i == 4) return this.Alias;
     if (i == 5) return this.URL;
     if (i == 6) return this.ImagePath;
     if (i == 7) return this.Info;
     if (i == 8) return this.IndexTemplate;
     if (i == 9) return this.ListTemplate;
     if (i == 10) return this.ListNameRule;
     if (i == 11) return this.DetailTemplate;
     if (i == 12) return this.DetailNameRule;
     if (i == 13) return this.RssTemplate;
     if (i == 14) return this.RssNameRule;
     if (i == 15) return this.OrderFlag;
     if (i == 16) return this.ListPageSize;
     if (i == 17) return this.ListPage;
     if (i == 18) return this.PublishFlag;
     if (i == 19) return this.SingleFlag;
     if (i == 20) return this.HitCount;
     if (i == 21) return this.Meta_Keywords;
     if (i == 22) return this.Meta_Description;
     if (i == 23) return this.KeywordFlag;
     if (i == 24) return this.KeywordSetting;
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
 
   public String getName()
   {
     return this.Name;
   }
 
   public void setName(String name)
   {
     this.Name = name;
   }
 
   public String getBranchInnerCode()
   {
     return this.BranchInnerCode;
   }
 
   public void setBranchInnerCode(String branchInnerCode)
   {
     this.BranchInnerCode = branchInnerCode;
   }
 
   public String getAlias()
   {
     return this.Alias;
   }
 
   public void setAlias(String alias)
   {
     this.Alias = alias;
   }
 
   public String getURL()
   {
     return this.URL;
   }
 
   public void setURL(String uRL)
   {
     this.URL = uRL;
   }
 
   public String getImagePath()
   {
     return this.ImagePath;
   }
 
   public void setImagePath(String imagePath)
   {
     this.ImagePath = imagePath;
   }
 
   public String getInfo()
   {
     return this.Info;
   }
 
   public void setInfo(String info)
   {
     this.Info = info;
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
 
   public String getListNameRule()
   {
     return this.ListNameRule;
   }
 
   public void setListNameRule(String listNameRule)
   {
     this.ListNameRule = listNameRule;
   }
 
   public String getDetailTemplate()
   {
     return this.DetailTemplate;
   }
 
   public void setDetailTemplate(String detailTemplate)
   {
     this.DetailTemplate = detailTemplate;
   }
 
   public String getDetailNameRule()
   {
     return this.DetailNameRule;
   }
 
   public void setDetailNameRule(String detailNameRule)
   {
     this.DetailNameRule = detailNameRule;
   }
 
   public String getRssTemplate()
   {
     return this.RssTemplate;
   }
 
   public void setRssTemplate(String rssTemplate)
   {
     this.RssTemplate = rssTemplate;
   }
 
   public String getRssNameRule()
   {
     return this.RssNameRule;
   }
 
   public void setRssNameRule(String rssNameRule)
   {
     this.RssNameRule = rssNameRule;
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
 
   public long getListPageSize()
   {
     if (this.ListPageSize == null) return 0L;
     return this.ListPageSize.longValue();
   }
 
   public void setListPageSize(long listPageSize)
   {
     this.ListPageSize = new Long(listPageSize);
   }
 
   public void setListPageSize(String listPageSize)
   {
     if (listPageSize == null) {
       this.ListPageSize = null;
       return;
     }
     this.ListPageSize = new Long(listPageSize);
   }
 
   public long getListPage()
   {
     if (this.ListPage == null) return 0L;
     return this.ListPage.longValue();
   }
 
   public void setListPage(long listPage)
   {
     this.ListPage = new Long(listPage);
   }
 
   public void setListPage(String listPage)
   {
     if (listPage == null) {
       this.ListPage = null;
       return;
     }
     this.ListPage = new Long(listPage);
   }
 
   public String getPublishFlag()
   {
     return this.PublishFlag;
   }
 
   public void setPublishFlag(String publishFlag)
   {
     this.PublishFlag = publishFlag;
   }
 
   public String getSingleFlag()
   {
     return this.SingleFlag;
   }
 
   public void setSingleFlag(String singleFlag)
   {
     this.SingleFlag = singleFlag;
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
 
   public String getKeywordFlag()
   {
     return this.KeywordFlag;
   }
 
   public void setKeywordFlag(String keywordFlag)
   {
     this.KeywordFlag = keywordFlag;
   }
 
   public String getKeywordSetting()
   {
     return this.KeywordSetting;
   }
 
   public void setKeywordSetting(String keywordSetting)
   {
     this.KeywordSetting = keywordSetting;
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
 * Qualified Name:     com.zving.schema.ZSBrandSchema
 * JD-Core Version:    0.5.4
 */