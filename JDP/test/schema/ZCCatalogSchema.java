/*      */ package schema;
/*      */ 
/*      */ import java.util.Date;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
/*      */ 
/*      */ public class ZCCatalogSchema extends Schema
/*      */ {
/*      */   private Long ID;
/*      */   private Long ParentID;
/*      */   private Long SiteID;
/*      */   private String Name;
/*      */   private String InnerCode;
/*      */   private String BranchInnerCode;
/*      */   private String Alias;
/*      */   private String URL;
/*      */   private String ImagePath;
/*      */   private Long Type;
/*      */   private String IndexTemplate;
/*      */   private String ListTemplate;
/*      */   private String ListNameRule;
/*      */   private String DetailTemplate;
/*      */   private String DetailNameRule;
/*      */   private String RssTemplate;
/*      */   private String RssNameRule;
/*      */   private String Workflow;
/*      */   private Long TreeLevel;
/*      */   private Long ChildCount;
/*      */   private Long IsLeaf;
/*      */   private Long IsDirty;
/*      */   private Long Total;
/*      */   private Long OrderFlag;
/*      */   private String Logo;
/*      */   private Long ListPageSize;
/*      */   private Long ListPage;
/*      */   private String PublishFlag;
/*      */   private String SingleFlag;
/*      */   private Long HitCount;
/*      */   private String Meta_Keywords;
/*      */   private String Meta_Description;
/*      */   private String OrderColumn;
/*      */   private Long Integral;
/*      */   private String KeywordFlag;
/*      */   private String KeywordSetting;
/*      */   private String AllowContribute;
/*      */   private String ClusterSourceID;
/*      */   private String Prop1;
/*      */   private String Prop2;
/*      */   private String Prop3;
/*      */   private String Prop4;
/*      */   private String AddUser;
/*      */   private Date AddTime;
/*      */   private String ModifyUser;
/*      */   private Date ModifyTime;
/*  108 */   public static final SchemaColumn[] _Columns = { 
/*  109 */     new SchemaColumn("ID", 7, 0, 0, 0, true, true), 
/*  110 */     new SchemaColumn("ParentID", 7, 1, 0, 0, true, false), 
/*  111 */     new SchemaColumn("SiteID", 7, 2, 0, 0, true, false), 
/*  112 */     new SchemaColumn("Name", 1, 3, 100, 0, true, false), 
/*  113 */     new SchemaColumn("InnerCode", 1, 4, 100, 0, true, false), 
/*  114 */     new SchemaColumn("BranchInnerCode", 1, 5, 100, 0, false, false), 
/*  115 */     new SchemaColumn("Alias", 1, 6, 100, 0, true, false), 
/*  116 */     new SchemaColumn("URL", 1, 7, 100, 0, false, false), 
/*  117 */     new SchemaColumn("ImagePath", 1, 8, 50, 0, false, false), 
/*  118 */     new SchemaColumn("Type", 7, 9, 0, 0, true, false), 
/*  119 */     new SchemaColumn("IndexTemplate", 1, 10, 200, 0, false, false), 
/*  120 */     new SchemaColumn("ListTemplate", 1, 11, 200, 0, false, false), 
/*  121 */     new SchemaColumn("ListNameRule", 1, 12, 200, 0, false, false), 
/*  122 */     new SchemaColumn("DetailTemplate", 1, 13, 200, 0, false, false), 
/*  123 */     new SchemaColumn("DetailNameRule", 1, 14, 200, 0, false, false), 
/*  124 */     new SchemaColumn("RssTemplate", 1, 15, 200, 0, false, false), 
/*  125 */     new SchemaColumn("RssNameRule", 1, 16, 200, 0, false, false), 
/*  126 */     new SchemaColumn("Workflow", 1, 17, 100, 0, false, false), 
/*  127 */     new SchemaColumn("TreeLevel", 7, 18, 0, 0, true, false), 
/*  128 */     new SchemaColumn("ChildCount", 7, 19, 0, 0, true, false), 
/*  129 */     new SchemaColumn("IsLeaf", 7, 20, 0, 0, true, false), 
/*  130 */     new SchemaColumn("IsDirty", 7, 21, 0, 0, false, false), 
/*  131 */     new SchemaColumn("Total", 7, 22, 0, 0, true, false), 
/*  132 */     new SchemaColumn("OrderFlag", 7, 23, 0, 0, true, false), 
/*  133 */     new SchemaColumn("Logo", 1, 24, 100, 0, false, false), 
/*  134 */     new SchemaColumn("ListPageSize", 7, 25, 0, 0, false, false), 
/*  135 */     new SchemaColumn("ListPage", 7, 26, 0, 0, false, false), 
/*  136 */     new SchemaColumn("PublishFlag", 1, 27, 2, 0, true, false), 
/*  137 */     new SchemaColumn("SingleFlag", 1, 28, 2, 0, false, false), 
/*  138 */     new SchemaColumn("HitCount", 7, 29, 0, 0, false, false), 
/*  139 */     new SchemaColumn("Meta_Keywords", 1, 30, 200, 0, false, false), 
/*  140 */     new SchemaColumn("Meta_Description", 1, 31, 200, 0, false, false), 
/*  141 */     new SchemaColumn("OrderColumn", 1, 32, 20, 0, false, false), 
/*  142 */     new SchemaColumn("Integral", 7, 33, 0, 0, false, false), 
/*  143 */     new SchemaColumn("KeywordFlag", 1, 34, 2, 0, false, false), 
/*  144 */     new SchemaColumn("KeywordSetting", 1, 35, 50, 0, false, false), 
/*  145 */     new SchemaColumn("AllowContribute", 1, 36, 2, 0, false, false), 
/*  146 */     new SchemaColumn("ClusterSourceID", 1, 37, 50, 0, false, false), 
/*  147 */     new SchemaColumn("Prop1", 1, 38, 50, 0, false, false), 
/*  148 */     new SchemaColumn("Prop2", 1, 39, 50, 0, false, false), 
/*  149 */     new SchemaColumn("Prop3", 1, 40, 50, 0, false, false), 
/*  150 */     new SchemaColumn("Prop4", 1, 41, 50, 0, false, false), 
/*  151 */     new SchemaColumn("AddUser", 1, 42, 200, 0, true, false), 
/*  152 */     new SchemaColumn("AddTime", 0, 43, 0, 0, true, false), 
/*  153 */     new SchemaColumn("ModifyUser", 1, 44, 200, 0, false, false), 
/*  154 */     new SchemaColumn("ModifyTime", 0, 45, 0, 0, false, false) };
/*      */   public static final String _TableCode = "ZCCatalog";
/*      */   public static final String _NameSpace = "com.xdarkness.schema";
/*      */   protected static final String _InsertAllSQL = "insert into ZCCatalog values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*      */   protected static final String _UpdateAllSQL = "update ZCCatalog set ID=?,ParentID=?,SiteID=?,Name=?,InnerCode=?,BranchInnerCode=?,Alias=?,URL=?,ImagePath=?,Type=?,IndexTemplate=?,ListTemplate=?,ListNameRule=?,DetailTemplate=?,DetailNameRule=?,RssTemplate=?,RssNameRule=?,Workflow=?,TreeLevel=?,ChildCount=?,IsLeaf=?,IsDirty=?,Total=?,OrderFlag=?,Logo=?,ListPageSize=?,ListPage=?,PublishFlag=?,SingleFlag=?,HitCount=?,Meta_Keywords=?,Meta_Description=?,OrderColumn=?,Integral=?,KeywordFlag=?,KeywordSetting=?,AllowContribute=?,ClusterSourceID=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
/*      */   protected static final String _DeleteSQL = "delete from ZCCatalog  where ID=?";
/*      */   protected static final String _FillAllSQL = "select * from ZCCatalog  where ID=?";
/*      */ 
/*      */   public ZCCatalogSchema()
/*      */   {
/*  170 */     this.TableCode = "ZCCatalog";
/*  171 */     this.NameSpace = "com.xdarkness.schema";
/*  172 */     this.Columns = _Columns;
/*  173 */     this.InsertAllSQL = "insert into ZCCatalog values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
/*  174 */     this.UpdateAllSQL = "update ZCCatalog set ID=?,ParentID=?,SiteID=?,Name=?,InnerCode=?,BranchInnerCode=?,Alias=?,URL=?,ImagePath=?,Type=?,IndexTemplate=?,ListTemplate=?,ListNameRule=?,DetailTemplate=?,DetailNameRule=?,RssTemplate=?,RssNameRule=?,Workflow=?,TreeLevel=?,ChildCount=?,IsLeaf=?,IsDirty=?,Total=?,OrderFlag=?,Logo=?,ListPageSize=?,ListPage=?,PublishFlag=?,SingleFlag=?,HitCount=?,Meta_Keywords=?,Meta_Description=?,OrderColumn=?,Integral=?,KeywordFlag=?,KeywordSetting=?,AllowContribute=?,ClusterSourceID=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
/*  175 */     this.DeleteSQL = "delete from ZCCatalog  where ID=?";
/*  176 */     this.FillAllSQL = "select * from ZCCatalog  where ID=?";
/*  177 */     this.HasSetFlag = new boolean[46];
/*      */   }
/*      */ 
/*      */   protected Schema newInstance() {
/*  181 */     return new ZCCatalogSchema();
/*      */   }
/*      */ 
/*      */   protected SchemaSet newSet() {
/*  185 */     return new ZCCatalogSet();
/*      */   }
/*      */ 
/*      */   public ZCCatalogSet query() {
/*  189 */     return query(-1, -1);
/*      */   }
/*      */ 
/*      */   public ZCCatalogSet query(int pageSize, int pageIndex) {
/*  201 */     return (ZCCatalogSet)querySet(pageSize, pageIndex);
/*      */   }
/*      */ 
/*      */   public void setV(int i, Object v) {
/*  205 */     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
/*  206 */     if (i == 1) { if (v == null) this.ParentID = null; else this.ParentID = new Long(v.toString()); return; }
/*  207 */     if (i == 2) { if (v == null) this.SiteID = null; else this.SiteID = new Long(v.toString()); return; }
/*  208 */     if (i == 3) { this.Name = ((String)v); return; }
/*  209 */     if (i == 4) { this.InnerCode = ((String)v); return; }
/*  210 */     if (i == 5) { this.BranchInnerCode = ((String)v); return; }
/*  211 */     if (i == 6) { this.Alias = ((String)v); return; }
/*  212 */     if (i == 7) { this.URL = ((String)v); return; }
/*  213 */     if (i == 8) { this.ImagePath = ((String)v); return; }
/*  214 */     if (i == 9) { if (v == null) this.Type = null; else this.Type = new Long(v.toString()); return; }
/*  215 */     if (i == 10) { this.IndexTemplate = ((String)v); return; }
/*  216 */     if (i == 11) { this.ListTemplate = ((String)v); return; }
/*  217 */     if (i == 12) { this.ListNameRule = ((String)v); return; }
/*  218 */     if (i == 13) { this.DetailTemplate = ((String)v); return; }
/*  219 */     if (i == 14) { this.DetailNameRule = ((String)v); return; }
/*  220 */     if (i == 15) { this.RssTemplate = ((String)v); return; }
/*  221 */     if (i == 16) { this.RssNameRule = ((String)v); return; }
/*  222 */     if (i == 17) { this.Workflow = ((String)v); return; }
/*  223 */     if (i == 18) { if (v == null) this.TreeLevel = null; else this.TreeLevel = new Long(v.toString()); return; }
/*  224 */     if (i == 19) { if (v == null) this.ChildCount = null; else this.ChildCount = new Long(v.toString()); return; }
/*  225 */     if (i == 20) { if (v == null) this.IsLeaf = null; else this.IsLeaf = new Long(v.toString()); return; }
/*  226 */     if (i == 21) { if (v == null) this.IsDirty = null; else this.IsDirty = new Long(v.toString()); return; }
/*  227 */     if (i == 22) { if (v == null) this.Total = null; else this.Total = new Long(v.toString()); return; }
/*  228 */     if (i == 23) { if (v == null) this.OrderFlag = null; else this.OrderFlag = new Long(v.toString()); return; }
/*  229 */     if (i == 24) { this.Logo = ((String)v); return; }
/*  230 */     if (i == 25) { if (v == null) this.ListPageSize = null; else this.ListPageSize = new Long(v.toString()); return; }
/*  231 */     if (i == 26) { if (v == null) this.ListPage = null; else this.ListPage = new Long(v.toString()); return; }
/*  232 */     if (i == 27) { this.PublishFlag = ((String)v); return; }
/*  233 */     if (i == 28) { this.SingleFlag = ((String)v); return; }
/*  234 */     if (i == 29) { if (v == null) this.HitCount = null; else this.HitCount = new Long(v.toString()); return; }
/*  235 */     if (i == 30) { this.Meta_Keywords = ((String)v); return; }
/*  236 */     if (i == 31) { this.Meta_Description = ((String)v); return; }
/*  237 */     if (i == 32) { this.OrderColumn = ((String)v); return; }
/*  238 */     if (i == 33) { if (v == null) this.Integral = null; else this.Integral = new Long(v.toString()); return; }
/*  239 */     if (i == 34) { this.KeywordFlag = ((String)v); return; }
/*  240 */     if (i == 35) { this.KeywordSetting = ((String)v); return; }
/*  241 */     if (i == 36) { this.AllowContribute = ((String)v); return; }
/*  242 */     if (i == 37) { this.ClusterSourceID = ((String)v); return; }
/*  243 */     if (i == 38) { this.Prop1 = ((String)v); return; }
/*  244 */     if (i == 39) { this.Prop2 = ((String)v); return; }
/*  245 */     if (i == 40) { this.Prop3 = ((String)v); return; }
/*  246 */     if (i == 41) { this.Prop4 = ((String)v); return; }
/*  247 */     if (i == 42) { this.AddUser = ((String)v); return; }
/*  248 */     if (i == 43) { this.AddTime = ((Date)v); return; }
/*  249 */     if (i == 44) { this.ModifyUser = ((String)v); return; }
/*  250 */     if (i == 45) { this.ModifyTime = ((Date)v); return; }
/*      */   }
/*      */ 
/*      */   public Object getV(int i) {
/*  254 */     if (i == 0) return this.ID;
/*  255 */     if (i == 1) return this.ParentID;
/*  256 */     if (i == 2) return this.SiteID;
/*  257 */     if (i == 3) return this.Name;
/*  258 */     if (i == 4) return this.InnerCode;
/*  259 */     if (i == 5) return this.BranchInnerCode;
/*  260 */     if (i == 6) return this.Alias;
/*  261 */     if (i == 7) return this.URL;
/*  262 */     if (i == 8) return this.ImagePath;
/*  263 */     if (i == 9) return this.Type;
/*  264 */     if (i == 10) return this.IndexTemplate;
/*  265 */     if (i == 11) return this.ListTemplate;
/*  266 */     if (i == 12) return this.ListNameRule;
/*  267 */     if (i == 13) return this.DetailTemplate;
/*  268 */     if (i == 14) return this.DetailNameRule;
/*  269 */     if (i == 15) return this.RssTemplate;
/*  270 */     if (i == 16) return this.RssNameRule;
/*  271 */     if (i == 17) return this.Workflow;
/*  272 */     if (i == 18) return this.TreeLevel;
/*  273 */     if (i == 19) return this.ChildCount;
/*  274 */     if (i == 20) return this.IsLeaf;
/*  275 */     if (i == 21) return this.IsDirty;
/*  276 */     if (i == 22) return this.Total;
/*  277 */     if (i == 23) return this.OrderFlag;
/*  278 */     if (i == 24) return this.Logo;
/*  279 */     if (i == 25) return this.ListPageSize;
/*  280 */     if (i == 26) return this.ListPage;
/*  281 */     if (i == 27) return this.PublishFlag;
/*  282 */     if (i == 28) return this.SingleFlag;
/*  283 */     if (i == 29) return this.HitCount;
/*  284 */     if (i == 30) return this.Meta_Keywords;
/*  285 */     if (i == 31) return this.Meta_Description;
/*  286 */     if (i == 32) return this.OrderColumn;
/*  287 */     if (i == 33) return this.Integral;
/*  288 */     if (i == 34) return this.KeywordFlag;
/*  289 */     if (i == 35) return this.KeywordSetting;
/*  290 */     if (i == 36) return this.AllowContribute;
/*  291 */     if (i == 37) return this.ClusterSourceID;
/*  292 */     if (i == 38) return this.Prop1;
/*  293 */     if (i == 39) return this.Prop2;
/*  294 */     if (i == 40) return this.Prop3;
/*  295 */     if (i == 41) return this.Prop4;
/*  296 */     if (i == 42) return this.AddUser;
/*  297 */     if (i == 43) return this.AddTime;
/*  298 */     if (i == 44) return this.ModifyUser;
/*  299 */     if (i == 45) return this.ModifyTime;
/*  300 */     return null;
/*      */   }
/*      */ 
/*      */   public long getID()
/*      */   {
/*  311 */     if (this.ID == null) return 0L;
/*  312 */     return this.ID.longValue();
/*      */   }
/*      */ 
/*      */   public void setID(long iD)
/*      */   {
/*  323 */     this.ID = new Long(iD);
/*      */   }
/*      */ 
/*      */   public void setID(String iD)
/*      */   {
/*  334 */     if (iD == null) {
/*  335 */       this.ID = null;
/*  336 */       return;
/*      */     }
/*  338 */     this.ID = new Long(iD);
/*      */   }
/*      */ 
/*      */   public long getParentID()
/*      */   {
/*  349 */     if (this.ParentID == null) return 0L;
/*  350 */     return this.ParentID.longValue();
/*      */   }
/*      */ 
/*      */   public void setParentID(long parentID)
/*      */   {
/*  361 */     this.ParentID = new Long(parentID);
/*      */   }
/*      */ 
/*      */   public void setParentID(String parentID)
/*      */   {
/*  372 */     if (parentID == null) {
/*  373 */       this.ParentID = null;
/*  374 */       return;
/*      */     }
/*  376 */     this.ParentID = new Long(parentID);
/*      */   }
/*      */ 
/*      */   public long getSiteID()
/*      */   {
/*  387 */     if (this.SiteID == null) return 0L;
/*  388 */     return this.SiteID.longValue();
/*      */   }
/*      */ 
/*      */   public void setSiteID(long siteID)
/*      */   {
/*  399 */     this.SiteID = new Long(siteID);
/*      */   }
/*      */ 
/*      */   public void setSiteID(String siteID)
/*      */   {
/*  410 */     if (siteID == null) {
/*  411 */       this.SiteID = null;
/*  412 */       return;
/*      */     }
/*  414 */     this.SiteID = new Long(siteID);
/*      */   }
/*      */ 
/*      */   public String getName()
/*      */   {
/*  425 */     return this.Name;
/*      */   }
/*      */ 
/*      */   public void setName(String name)
/*      */   {
/*  436 */     this.Name = name;
/*      */   }
/*      */ 
/*      */   public String getInnerCode()
/*      */   {
/*  447 */     return this.InnerCode;
/*      */   }
/*      */ 
/*      */   public void setInnerCode(String innerCode)
/*      */   {
/*  458 */     this.InnerCode = innerCode;
/*      */   }
/*      */ 
/*      */   public String getBranchInnerCode()
/*      */   {
/*  469 */     return this.BranchInnerCode;
/*      */   }
/*      */ 
/*      */   public void setBranchInnerCode(String branchInnerCode)
/*      */   {
/*  480 */     this.BranchInnerCode = branchInnerCode;
/*      */   }
/*      */ 
/*      */   public String getAlias()
/*      */   {
/*  491 */     return this.Alias;
/*      */   }
/*      */ 
/*      */   public void setAlias(String alias)
/*      */   {
/*  502 */     this.Alias = alias;
/*      */   }
/*      */ 
/*      */   public String getURL()
/*      */   {
/*  513 */     return this.URL;
/*      */   }
/*      */ 
/*      */   public void setURL(String uRL)
/*      */   {
/*  524 */     this.URL = uRL;
/*      */   }
/*      */ 
/*      */   public String getImagePath()
/*      */   {
/*  535 */     return this.ImagePath;
/*      */   }
/*      */ 
/*      */   public void setImagePath(String imagePath)
/*      */   {
/*  546 */     this.ImagePath = imagePath;
/*      */   }
/*      */ 
/*      */   public long getType()
/*      */   {
/*  561 */     if (this.Type == null) return 0L;
/*  562 */     return this.Type.longValue();
/*      */   }
/*      */ 
/*      */   public void setType(long type)
/*      */   {
/*  577 */     this.Type = new Long(type);
/*      */   }
/*      */ 
/*      */   public void setType(String type)
/*      */   {
/*  592 */     if (type == null) {
/*  593 */       this.Type = null;
/*  594 */       return;
/*      */     }
/*  596 */     this.Type = new Long(type);
/*      */   }
/*      */ 
/*      */   public String getIndexTemplate()
/*      */   {
/*  607 */     return this.IndexTemplate;
/*      */   }
/*      */ 
/*      */   public void setIndexTemplate(String indexTemplate)
/*      */   {
/*  618 */     this.IndexTemplate = indexTemplate;
/*      */   }
/*      */ 
/*      */   public String getListTemplate()
/*      */   {
/*  629 */     return this.ListTemplate;
/*      */   }
/*      */ 
/*      */   public void setListTemplate(String listTemplate)
/*      */   {
/*  640 */     this.ListTemplate = listTemplate;
/*      */   }
/*      */ 
/*      */   public String getListNameRule()
/*      */   {
/*  651 */     return this.ListNameRule;
/*      */   }
/*      */ 
/*      */   public void setListNameRule(String listNameRule)
/*      */   {
/*  662 */     this.ListNameRule = listNameRule;
/*      */   }
/*      */ 
/*      */   public String getDetailTemplate()
/*      */   {
/*  673 */     return this.DetailTemplate;
/*      */   }
/*      */ 
/*      */   public void setDetailTemplate(String detailTemplate)
/*      */   {
/*  684 */     this.DetailTemplate = detailTemplate;
/*      */   }
/*      */ 
/*      */   public String getDetailNameRule()
/*      */   {
/*  695 */     return this.DetailNameRule;
/*      */   }
/*      */ 
/*      */   public void setDetailNameRule(String detailNameRule)
/*      */   {
/*  706 */     this.DetailNameRule = detailNameRule;
/*      */   }
/*      */ 
/*      */   public String getRssTemplate()
/*      */   {
/*  717 */     return this.RssTemplate;
/*      */   }
/*      */ 
/*      */   public void setRssTemplate(String rssTemplate)
/*      */   {
/*  728 */     this.RssTemplate = rssTemplate;
/*      */   }
/*      */ 
/*      */   public String getRssNameRule()
/*      */   {
/*  739 */     return this.RssNameRule;
/*      */   }
/*      */ 
/*      */   public void setRssNameRule(String rssNameRule)
/*      */   {
/*  750 */     this.RssNameRule = rssNameRule;
/*      */   }
/*      */ 
/*      */   public String getWorkflow()
/*      */   {
/*  761 */     return this.Workflow;
/*      */   }
/*      */ 
/*      */   public void setWorkflow(String workflow)
/*      */   {
/*  772 */     this.Workflow = workflow;
/*      */   }
/*      */ 
/*      */   public long getTreeLevel()
/*      */   {
/*  783 */     if (this.TreeLevel == null) return 0L;
/*  784 */     return this.TreeLevel.longValue();
/*      */   }
/*      */ 
/*      */   public void setTreeLevel(long treeLevel)
/*      */   {
/*  795 */     this.TreeLevel = new Long(treeLevel);
/*      */   }
/*      */ 
/*      */   public void setTreeLevel(String treeLevel)
/*      */   {
/*  806 */     if (treeLevel == null) {
/*  807 */       this.TreeLevel = null;
/*  808 */       return;
/*      */     }
/*  810 */     this.TreeLevel = new Long(treeLevel);
/*      */   }
/*      */ 
/*      */   public long getChildCount()
/*      */   {
/*  821 */     if (this.ChildCount == null) return 0L;
/*  822 */     return this.ChildCount.longValue();
/*      */   }
/*      */ 
/*      */   public void setChildCount(long childCount)
/*      */   {
/*  833 */     this.ChildCount = new Long(childCount);
/*      */   }
/*      */ 
/*      */   public void setChildCount(String childCount)
/*      */   {
/*  844 */     if (childCount == null) {
/*  845 */       this.ChildCount = null;
/*  846 */       return;
/*      */     }
/*  848 */     this.ChildCount = new Long(childCount);
/*      */   }
/*      */ 
/*      */   public long getIsLeaf()
/*      */   {
/*  862 */     if (this.IsLeaf == null) return 0L;
/*  863 */     return this.IsLeaf.longValue();
/*      */   }
/*      */ 
/*      */   public void setIsLeaf(long isLeaf)
/*      */   {
/*  877 */     this.IsLeaf = new Long(isLeaf);
/*      */   }
/*      */ 
/*      */   public void setIsLeaf(String isLeaf)
/*      */   {
/*  891 */     if (isLeaf == null) {
/*  892 */       this.IsLeaf = null;
/*  893 */       return;
/*      */     }
/*  895 */     this.IsLeaf = new Long(isLeaf);
/*      */   }
/*      */ 
/*      */   public long getIsDirty()
/*      */   {
/*  909 */     if (this.IsDirty == null) return 0L;
/*  910 */     return this.IsDirty.longValue();
/*      */   }
/*      */ 
/*      */   public void setIsDirty(long isDirty)
/*      */   {
/*  924 */     this.IsDirty = new Long(isDirty);
/*      */   }
/*      */ 
/*      */   public void setIsDirty(String isDirty)
/*      */   {
/*  938 */     if (isDirty == null) {
/*  939 */       this.IsDirty = null;
/*  940 */       return;
/*      */     }
/*  942 */     this.IsDirty = new Long(isDirty);
/*      */   }
/*      */ 
/*      */   public long getTotal()
/*      */   {
/*  956 */     if (this.Total == null) return 0L;
/*  957 */     return this.Total.longValue();
/*      */   }
/*      */ 
/*      */   public void setTotal(long total)
/*      */   {
/*  971 */     this.Total = new Long(total);
/*      */   }
/*      */ 
/*      */   public void setTotal(String total)
/*      */   {
/*  985 */     if (total == null) {
/*  986 */       this.Total = null;
/*  987 */       return;
/*      */     }
/*  989 */     this.Total = new Long(total);
/*      */   }
/*      */ 
/*      */   public long getOrderFlag()
/*      */   {
/* 1000 */     if (this.OrderFlag == null) return 0L;
/* 1001 */     return this.OrderFlag.longValue();
/*      */   }
/*      */ 
/*      */   public void setOrderFlag(long orderFlag)
/*      */   {
/* 1012 */     this.OrderFlag = new Long(orderFlag);
/*      */   }
/*      */ 
/*      */   public void setOrderFlag(String orderFlag)
/*      */   {
/* 1023 */     if (orderFlag == null) {
/* 1024 */       this.OrderFlag = null;
/* 1025 */       return;
/*      */     }
/* 1027 */     this.OrderFlag = new Long(orderFlag);
/*      */   }
/*      */ 
/*      */   public String getLogo()
/*      */   {
/* 1038 */     return this.Logo;
/*      */   }
/*      */ 
/*      */   public void setLogo(String logo)
/*      */   {
/* 1049 */     this.Logo = logo;
/*      */   }
/*      */ 
/*      */   public long getListPageSize()
/*      */   {
/* 1060 */     if (this.ListPageSize == null) return 0L;
/* 1061 */     return this.ListPageSize.longValue();
/*      */   }
/*      */ 
/*      */   public void setListPageSize(long listPageSize)
/*      */   {
/* 1072 */     this.ListPageSize = new Long(listPageSize);
/*      */   }
/*      */ 
/*      */   public void setListPageSize(String listPageSize)
/*      */   {
/* 1083 */     if (listPageSize == null) {
/* 1084 */       this.ListPageSize = null;
/* 1085 */       return;
/*      */     }
/* 1087 */     this.ListPageSize = new Long(listPageSize);
/*      */   }
/*      */ 
/*      */   public long getListPage()
/*      */   {
/* 1098 */     if (this.ListPage == null) return 0L;
/* 1099 */     return this.ListPage.longValue();
/*      */   }
/*      */ 
/*      */   public void setListPage(long listPage)
/*      */   {
/* 1110 */     this.ListPage = new Long(listPage);
/*      */   }
/*      */ 
/*      */   public void setListPage(String listPage)
/*      */   {
/* 1121 */     if (listPage == null) {
/* 1122 */       this.ListPage = null;
/* 1123 */       return;
/*      */     }
/* 1125 */     this.ListPage = new Long(listPage);
/*      */   }
/*      */ 
/*      */   public String getPublishFlag()
/*      */   {
/* 1139 */     return this.PublishFlag;
/*      */   }
/*      */ 
/*      */   public void setPublishFlag(String publishFlag)
/*      */   {
/* 1153 */     this.PublishFlag = publishFlag;
/*      */   }
/*      */ 
/*      */   public String getSingleFlag()
/*      */   {
/* 1164 */     return this.SingleFlag;
/*      */   }
/*      */ 
/*      */   public void setSingleFlag(String singleFlag)
/*      */   {
/* 1175 */     this.SingleFlag = singleFlag;
/*      */   }
/*      */ 
/*      */   public long getHitCount()
/*      */   {
/* 1186 */     if (this.HitCount == null) return 0L;
/* 1187 */     return this.HitCount.longValue();
/*      */   }
/*      */ 
/*      */   public void setHitCount(long hitCount)
/*      */   {
/* 1198 */     this.HitCount = new Long(hitCount);
/*      */   }
/*      */ 
/*      */   public void setHitCount(String hitCount)
/*      */   {
/* 1209 */     if (hitCount == null) {
/* 1210 */       this.HitCount = null;
/* 1211 */       return;
/*      */     }
/* 1213 */     this.HitCount = new Long(hitCount);
/*      */   }
/*      */ 
/*      */   public String getMeta_Keywords()
/*      */   {
/* 1224 */     return this.Meta_Keywords;
/*      */   }
/*      */ 
/*      */   public void setMeta_Keywords(String meta_Keywords)
/*      */   {
/* 1235 */     this.Meta_Keywords = meta_Keywords;
/*      */   }
/*      */ 
/*      */   public String getMeta_Description()
/*      */   {
/* 1246 */     return this.Meta_Description;
/*      */   }
/*      */ 
/*      */   public void setMeta_Description(String meta_Description)
/*      */   {
/* 1257 */     this.Meta_Description = meta_Description;
/*      */   }
/*      */ 
/*      */   public String getOrderColumn()
/*      */   {
/* 1268 */     return this.OrderColumn;
/*      */   }
/*      */ 
/*      */   public void setOrderColumn(String orderColumn)
/*      */   {
/* 1279 */     this.OrderColumn = orderColumn;
/*      */   }
/*      */ 
/*      */   public long getIntegral()
/*      */   {
/* 1290 */     if (this.Integral == null) return 0L;
/* 1291 */     return this.Integral.longValue();
/*      */   }
/*      */ 
/*      */   public void setIntegral(long integral)
/*      */   {
/* 1302 */     this.Integral = new Long(integral);
/*      */   }
/*      */ 
/*      */   public void setIntegral(String integral)
/*      */   {
/* 1313 */     if (integral == null) {
/* 1314 */       this.Integral = null;
/* 1315 */       return;
/*      */     }
/* 1317 */     this.Integral = new Long(integral);
/*      */   }
/*      */ 
/*      */   public String getKeywordFlag()
/*      */   {
/* 1328 */     return this.KeywordFlag;
/*      */   }
/*      */ 
/*      */   public void setKeywordFlag(String keywordFlag)
/*      */   {
/* 1339 */     this.KeywordFlag = keywordFlag;
/*      */   }
/*      */ 
/*      */   public String getKeywordSetting()
/*      */   {
/* 1350 */     return this.KeywordSetting;
/*      */   }
/*      */ 
/*      */   public void setKeywordSetting(String keywordSetting)
/*      */   {
/* 1361 */     this.KeywordSetting = keywordSetting;
/*      */   }
/*      */ 
/*      */   public String getAllowContribute()
/*      */   {
/* 1372 */     return this.AllowContribute;
/*      */   }
/*      */ 
/*      */   public void setAllowContribute(String allowContribute)
/*      */   {
/* 1383 */     this.AllowContribute = allowContribute;
/*      */   }
/*      */ 
/*      */   public String getClusterSourceID()
/*      */   {
/* 1394 */     return this.ClusterSourceID;
/*      */   }
/*      */ 
/*      */   public void setClusterSourceID(String clusterSourceID)
/*      */   {
/* 1405 */     this.ClusterSourceID = clusterSourceID;
/*      */   }
/*      */ 
/*      */   public String getProp1()
/*      */   {
/* 1416 */     return this.Prop1;
/*      */   }
/*      */ 
/*      */   public void setProp1(String prop1)
/*      */   {
/* 1427 */     this.Prop1 = prop1;
/*      */   }
/*      */ 
/*      */   public String getProp2()
/*      */   {
/* 1438 */     return this.Prop2;
/*      */   }
/*      */ 
/*      */   public void setProp2(String prop2)
/*      */   {
/* 1449 */     this.Prop2 = prop2;
/*      */   }
/*      */ 
/*      */   public String getProp3()
/*      */   {
/* 1460 */     return this.Prop3;
/*      */   }
/*      */ 
/*      */   public void setProp3(String prop3)
/*      */   {
/* 1471 */     this.Prop3 = prop3;
/*      */   }
/*      */ 
/*      */   public String getProp4()
/*      */   {
/* 1482 */     return this.Prop4;
/*      */   }
/*      */ 
/*      */   public void setProp4(String prop4)
/*      */   {
/* 1493 */     this.Prop4 = prop4;
/*      */   }
/*      */ 
/*      */   public String getAddUser()
/*      */   {
/* 1504 */     return this.AddUser;
/*      */   }
/*      */ 
/*      */   public void setAddUser(String addUser)
/*      */   {
/* 1515 */     this.AddUser = addUser;
/*      */   }
/*      */ 
/*      */   public Date getAddTime()
/*      */   {
/* 1526 */     return this.AddTime;
/*      */   }
/*      */ 
/*      */   public void setAddTime(Date addTime)
/*      */   {
/* 1537 */     this.AddTime = addTime;
/*      */   }
/*      */ 
/*      */   public String getModifyUser()
/*      */   {
/* 1548 */     return this.ModifyUser;
/*      */   }
/*      */ 
/*      */   public void setModifyUser(String modifyUser)
/*      */   {
/* 1559 */     this.ModifyUser = modifyUser;
/*      */   }
/*      */ 
/*      */   public Date getModifyTime()
/*      */   {
/* 1570 */     return this.ModifyTime;
/*      */   }
/*      */ 
/*      */   public void setModifyTime(Date modifyTime)
/*      */   {
/* 1581 */     this.ModifyTime = modifyTime;
/*      */   }
/*      */ }
