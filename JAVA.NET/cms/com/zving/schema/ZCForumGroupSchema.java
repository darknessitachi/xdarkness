 package com.zving.schema;
 
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.orm.Schema;
 import com.zving.framework.orm.SchemaColumn;
 import com.zving.framework.orm.SchemaSet;
 import java.util.Date;
 
 public class ZCForumGroupSchema extends Schema
 {
   private Long ID;
   private Long SiteID;
   private Long RadminID;
   private String Name;
   private String SystemName;
   private String Type;
   private String Color;
   private String Image;
   private Long LowerScore;
   private Long UpperScore;
   private String AllowTheme;
   private String AllowReply;
   private String AllowSearch;
   private String AllowUserInfo;
   private String AllowPanel;
   private String AllowNickName;
   private String AllowVisit;
   private String AllowHeadImage;
   private String AllowFace;
   private String AllowAutograph;
   private String Verify;
   private String AllowEditUser;
   private String AllowForbidUser;
   private String AllowEditForum;
   private String AllowVerfyPost;
   private String AllowDel;
   private String AllowEdit;
   private String Hide;
   private String RemoveTheme;
   private String MoveTheme;
   private String TopTheme;
   private String BrightTheme;
   private String UpOrDownTheme;
   private String BestTheme;
   private String prop1;
   private String prop2;
   private String prop3;
   private String prop4;
   private String AddUser;
   private Date AddTime;
   private String ModifyUser;
   private Date ModifyTime;
   private Long OrderFlag;
   public static final SchemaColumn[] _Columns = { 
     new SchemaColumn("ID", 7, 0, 0, 0, true, true), 
     new SchemaColumn("SiteID", 7, 1, 0, 0, false, false), 
     new SchemaColumn("RadminID", 7, 2, 0, 0, false, false), 
     new SchemaColumn("Name", 1, 3, 100, 0, true, false), 
     new SchemaColumn("SystemName", 1, 4, 100, 0, false, false), 
     new SchemaColumn("Type", 1, 5, 100, 0, true, false), 
     new SchemaColumn("Color", 1, 6, 100, 0, false, false), 
     new SchemaColumn("Image", 1, 7, 100, 0, false, false), 
     new SchemaColumn("LowerScore", 7, 8, 0, 0, false, false), 
     new SchemaColumn("UpperScore", 7, 9, 0, 0, false, false), 
     new SchemaColumn("AllowTheme", 1, 10, 2, 0, false, false), 
     new SchemaColumn("AllowReply", 1, 11, 2, 0, false, false), 
     new SchemaColumn("AllowSearch", 1, 12, 2, 0, false, false), 
     new SchemaColumn("AllowUserInfo", 1, 13, 2, 0, false, false), 
     new SchemaColumn("AllowPanel", 1, 14, 2, 0, false, false), 
     new SchemaColumn("AllowNickName", 1, 15, 2, 0, false, false), 
     new SchemaColumn("AllowVisit", 1, 16, 2, 0, false, false), 
     new SchemaColumn("AllowHeadImage", 1, 17, 2, 0, false, false), 
     new SchemaColumn("AllowFace", 1, 18, 2, 0, false, false), 
     new SchemaColumn("AllowAutograph", 1, 19, 2, 0, false, false), 
     new SchemaColumn("Verify", 1, 20, 2, 0, false, false), 
     new SchemaColumn("AllowEditUser", 1, 21, 2, 0, false, false), 
     new SchemaColumn("AllowForbidUser", 1, 22, 2, 0, false, false), 
     new SchemaColumn("AllowEditForum", 1, 23, 2, 0, false, false), 
     new SchemaColumn("AllowVerfyPost", 1, 24, 2, 0, false, false), 
     new SchemaColumn("AllowDel", 1, 25, 2, 0, false, false), 
     new SchemaColumn("AllowEdit", 1, 26, 2, 0, false, false), 
     new SchemaColumn("Hide", 1, 27, 2, 0, false, false), 
     new SchemaColumn("RemoveTheme", 1, 28, 2, 0, false, false), 
     new SchemaColumn("MoveTheme", 1, 29, 2, 0, false, false), 
     new SchemaColumn("TopTheme", 1, 30, 2, 0, false, false), 
     new SchemaColumn("BrightTheme", 1, 31, 2, 0, false, false), 
     new SchemaColumn("UpOrDownTheme", 1, 32, 2, 0, false, false), 
     new SchemaColumn("BestTheme", 1, 33, 2, 0, false, false), 
     new SchemaColumn("prop1", 1, 34, 50, 0, false, false), 
     new SchemaColumn("prop2", 1, 35, 50, 0, false, false), 
     new SchemaColumn("prop3", 1, 36, 50, 0, false, false), 
     new SchemaColumn("prop4", 1, 37, 50, 0, false, false), 
     new SchemaColumn("AddUser", 1, 38, 100, 0, true, false), 
     new SchemaColumn("AddTime", 0, 39, 0, 0, true, false), 
     new SchemaColumn("ModifyUser", 1, 40, 100, 0, false, false), 
     new SchemaColumn("ModifyTime", 0, 41, 0, 0, false, false), 
     new SchemaColumn("OrderFlag", 7, 42, 0, 0, false, false) };
   public static final String _TableCode = "ZCForumGroup";
   public static final String _NameSpace = "com.zving.schema";
   protected static final String _InsertAllSQL = "insert into ZCForumGroup values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
   protected static final String _UpdateAllSQL = "update ZCForumGroup set ID=?,SiteID=?,RadminID=?,Name=?,SystemName=?,Type=?,Color=?,Image=?,LowerScore=?,UpperScore=?,AllowTheme=?,AllowReply=?,AllowSearch=?,AllowUserInfo=?,AllowPanel=?,AllowNickName=?,AllowVisit=?,AllowHeadImage=?,AllowFace=?,AllowAutograph=?,Verify=?,AllowEditUser=?,AllowForbidUser=?,AllowEditForum=?,AllowVerfyPost=?,AllowDel=?,AllowEdit=?,Hide=?,RemoveTheme=?,MoveTheme=?,TopTheme=?,BrightTheme=?,UpOrDownTheme=?,BestTheme=?,prop1=?,prop2=?,prop3=?,prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,OrderFlag=? where ID=?";
   protected static final String _DeleteSQL = "delete from ZCForumGroup  where ID=?";
   protected static final String _FillAllSQL = "select * from ZCForumGroup  where ID=?";
 
   public ZCForumGroupSchema()
   {
     this.TableCode = "ZCForumGroup";
     this.NameSpace = "com.zving.schema";
     this.Columns = _Columns;
     this.InsertAllSQL = "insert into ZCForumGroup values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZCForumGroup set ID=?,SiteID=?,RadminID=?,Name=?,SystemName=?,Type=?,Color=?,Image=?,LowerScore=?,UpperScore=?,AllowTheme=?,AllowReply=?,AllowSearch=?,AllowUserInfo=?,AllowPanel=?,AllowNickName=?,AllowVisit=?,AllowHeadImage=?,AllowFace=?,AllowAutograph=?,Verify=?,AllowEditUser=?,AllowForbidUser=?,AllowEditForum=?,AllowVerfyPost=?,AllowDel=?,AllowEdit=?,Hide=?,RemoveTheme=?,MoveTheme=?,TopTheme=?,BrightTheme=?,UpOrDownTheme=?,BestTheme=?,prop1=?,prop2=?,prop3=?,prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,OrderFlag=? where ID=?";
     this.DeleteSQL = "delete from ZCForumGroup  where ID=?";
     this.FillAllSQL = "select * from ZCForumGroup  where ID=?";
     this.HasSetFlag = new boolean[43];
   }
 
   protected Schema newInstance() {
     return new ZCForumGroupSchema();
   }
 
   protected SchemaSet newSet() {
     return new ZCForumGroupSet();
   }
 
   public ZCForumGroupSet query() {
     return query(null, -1, -1);
   }
 
   public ZCForumGroupSet query(QueryBuilder qb) {
     return query(qb, -1, -1);
   }
 
   public ZCForumGroupSet query(int pageSize, int pageIndex) {
     return query(null, pageSize, pageIndex);
   }
 
   public ZCForumGroupSet query(QueryBuilder qb, int pageSize, int pageIndex) {
     return (ZCForumGroupSet)querySet(qb, pageSize, pageIndex);
   }
 
   public void setV(int i, Object v) {
     if (i == 0) { if (v == null) this.ID = null; else this.ID = new Long(v.toString()); return; }
     if (i == 1) { if (v == null) this.SiteID = null; else this.SiteID = new Long(v.toString()); return; }
     if (i == 2) { if (v == null) this.RadminID = null; else this.RadminID = new Long(v.toString()); return; }
     if (i == 3) { this.Name = ((String)v); return; }
     if (i == 4) { this.SystemName = ((String)v); return; }
     if (i == 5) { this.Type = ((String)v); return; }
     if (i == 6) { this.Color = ((String)v); return; }
     if (i == 7) { this.Image = ((String)v); return; }
     if (i == 8) { if (v == null) this.LowerScore = null; else this.LowerScore = new Long(v.toString()); return; }
     if (i == 9) { if (v == null) this.UpperScore = null; else this.UpperScore = new Long(v.toString()); return; }
     if (i == 10) { this.AllowTheme = ((String)v); return; }
     if (i == 11) { this.AllowReply = ((String)v); return; }
     if (i == 12) { this.AllowSearch = ((String)v); return; }
     if (i == 13) { this.AllowUserInfo = ((String)v); return; }
     if (i == 14) { this.AllowPanel = ((String)v); return; }
     if (i == 15) { this.AllowNickName = ((String)v); return; }
     if (i == 16) { this.AllowVisit = ((String)v); return; }
     if (i == 17) { this.AllowHeadImage = ((String)v); return; }
     if (i == 18) { this.AllowFace = ((String)v); return; }
     if (i == 19) { this.AllowAutograph = ((String)v); return; }
     if (i == 20) { this.Verify = ((String)v); return; }
     if (i == 21) { this.AllowEditUser = ((String)v); return; }
     if (i == 22) { this.AllowForbidUser = ((String)v); return; }
     if (i == 23) { this.AllowEditForum = ((String)v); return; }
     if (i == 24) { this.AllowVerfyPost = ((String)v); return; }
     if (i == 25) { this.AllowDel = ((String)v); return; }
     if (i == 26) { this.AllowEdit = ((String)v); return; }
     if (i == 27) { this.Hide = ((String)v); return; }
     if (i == 28) { this.RemoveTheme = ((String)v); return; }
     if (i == 29) { this.MoveTheme = ((String)v); return; }
     if (i == 30) { this.TopTheme = ((String)v); return; }
     if (i == 31) { this.BrightTheme = ((String)v); return; }
     if (i == 32) { this.UpOrDownTheme = ((String)v); return; }
     if (i == 33) { this.BestTheme = ((String)v); return; }
     if (i == 34) { this.prop1 = ((String)v); return; }
     if (i == 35) { this.prop2 = ((String)v); return; }
     if (i == 36) { this.prop3 = ((String)v); return; }
     if (i == 37) { this.prop4 = ((String)v); return; }
     if (i == 38) { this.AddUser = ((String)v); return; }
     if (i == 39) { this.AddTime = ((Date)v); return; }
     if (i == 40) { this.ModifyUser = ((String)v); return; }
     if (i == 41) { this.ModifyTime = ((Date)v); return; }
     if (i != 42) return; if (v == null) this.OrderFlag = null; else this.OrderFlag = new Long(v.toString()); return;
   }
 
   public Object getV(int i) {
     if (i == 0) return this.ID;
     if (i == 1) return this.SiteID;
     if (i == 2) return this.RadminID;
     if (i == 3) return this.Name;
     if (i == 4) return this.SystemName;
     if (i == 5) return this.Type;
     if (i == 6) return this.Color;
     if (i == 7) return this.Image;
     if (i == 8) return this.LowerScore;
     if (i == 9) return this.UpperScore;
     if (i == 10) return this.AllowTheme;
     if (i == 11) return this.AllowReply;
     if (i == 12) return this.AllowSearch;
     if (i == 13) return this.AllowUserInfo;
     if (i == 14) return this.AllowPanel;
     if (i == 15) return this.AllowNickName;
     if (i == 16) return this.AllowVisit;
     if (i == 17) return this.AllowHeadImage;
     if (i == 18) return this.AllowFace;
     if (i == 19) return this.AllowAutograph;
     if (i == 20) return this.Verify;
     if (i == 21) return this.AllowEditUser;
     if (i == 22) return this.AllowForbidUser;
     if (i == 23) return this.AllowEditForum;
     if (i == 24) return this.AllowVerfyPost;
     if (i == 25) return this.AllowDel;
     if (i == 26) return this.AllowEdit;
     if (i == 27) return this.Hide;
     if (i == 28) return this.RemoveTheme;
     if (i == 29) return this.MoveTheme;
     if (i == 30) return this.TopTheme;
     if (i == 31) return this.BrightTheme;
     if (i == 32) return this.UpOrDownTheme;
     if (i == 33) return this.BestTheme;
     if (i == 34) return this.prop1;
     if (i == 35) return this.prop2;
     if (i == 36) return this.prop3;
     if (i == 37) return this.prop4;
     if (i == 38) return this.AddUser;
     if (i == 39) return this.AddTime;
     if (i == 40) return this.ModifyUser;
     if (i == 41) return this.ModifyTime;
     if (i == 42) return this.OrderFlag;
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
 
   public long getRadminID()
   {
     if (this.RadminID == null) return 0L;
     return this.RadminID.longValue();
   }
 
   public void setRadminID(long radminID)
   {
     this.RadminID = new Long(radminID);
   }
 
   public void setRadminID(String radminID)
   {
     if (radminID == null) {
       this.RadminID = null;
       return;
     }
     this.RadminID = new Long(radminID);
   }
 
   public String getName()
   {
     return this.Name;
   }
 
   public void setName(String name)
   {
     this.Name = name;
   }
 
   public String getSystemName()
   {
     return this.SystemName;
   }
 
   public void setSystemName(String systemName)
   {
     this.SystemName = systemName;
   }
 
   public String getType()
   {
     return this.Type;
   }
 
   public void setType(String type)
   {
     this.Type = type;
   }
 
   public String getColor()
   {
     return this.Color;
   }
 
   public void setColor(String color)
   {
     this.Color = color;
   }
 
   public String getImage()
   {
     return this.Image;
   }
 
   public void setImage(String image)
   {
     this.Image = image;
   }
 
   public long getLowerScore()
   {
     if (this.LowerScore == null) return 0L;
     return this.LowerScore.longValue();
   }
 
   public void setLowerScore(long lowerScore)
   {
     this.LowerScore = new Long(lowerScore);
   }
 
   public void setLowerScore(String lowerScore)
   {
     if (lowerScore == null) {
       this.LowerScore = null;
       return;
     }
     this.LowerScore = new Long(lowerScore);
   }
 
   public long getUpperScore()
   {
     if (this.UpperScore == null) return 0L;
     return this.UpperScore.longValue();
   }
 
   public void setUpperScore(long upperScore)
   {
     this.UpperScore = new Long(upperScore);
   }
 
   public void setUpperScore(String upperScore)
   {
     if (upperScore == null) {
       this.UpperScore = null;
       return;
     }
     this.UpperScore = new Long(upperScore);
   }
 
   public String getAllowTheme()
   {
     return this.AllowTheme;
   }
 
   public void setAllowTheme(String allowTheme)
   {
     this.AllowTheme = allowTheme;
   }
 
   public String getAllowReply()
   {
     return this.AllowReply;
   }
 
   public void setAllowReply(String allowReply)
   {
     this.AllowReply = allowReply;
   }
 
   public String getAllowSearch()
   {
     return this.AllowSearch;
   }
 
   public void setAllowSearch(String allowSearch)
   {
     this.AllowSearch = allowSearch;
   }
 
   public String getAllowUserInfo()
   {
     return this.AllowUserInfo;
   }
 
   public void setAllowUserInfo(String allowUserInfo)
   {
     this.AllowUserInfo = allowUserInfo;
   }
 
   public String getAllowPanel()
   {
     return this.AllowPanel;
   }
 
   public void setAllowPanel(String allowPanel)
   {
     this.AllowPanel = allowPanel;
   }
 
   public String getAllowNickName()
   {
     return this.AllowNickName;
   }
 
   public void setAllowNickName(String allowNickName)
   {
     this.AllowNickName = allowNickName;
   }
 
   public String getAllowVisit()
   {
     return this.AllowVisit;
   }
 
   public void setAllowVisit(String allowVisit)
   {
     this.AllowVisit = allowVisit;
   }
 
   public String getAllowHeadImage()
   {
     return this.AllowHeadImage;
   }
 
   public void setAllowHeadImage(String allowHeadImage)
   {
     this.AllowHeadImage = allowHeadImage;
   }
 
   public String getAllowFace()
   {
     return this.AllowFace;
   }
 
   public void setAllowFace(String allowFace)
   {
     this.AllowFace = allowFace;
   }
 
   public String getAllowAutograph()
   {
     return this.AllowAutograph;
   }
 
   public void setAllowAutograph(String allowAutograph)
   {
     this.AllowAutograph = allowAutograph;
   }
 
   public String getVerify()
   {
     return this.Verify;
   }
 
   public void setVerify(String verify)
   {
     this.Verify = verify;
   }
 
   public String getAllowEditUser()
   {
     return this.AllowEditUser;
   }
 
   public void setAllowEditUser(String allowEditUser)
   {
     this.AllowEditUser = allowEditUser;
   }
 
   public String getAllowForbidUser()
   {
     return this.AllowForbidUser;
   }
 
   public void setAllowForbidUser(String allowForbidUser)
   {
     this.AllowForbidUser = allowForbidUser;
   }
 
   public String getAllowEditForum()
   {
     return this.AllowEditForum;
   }
 
   public void setAllowEditForum(String allowEditForum)
   {
     this.AllowEditForum = allowEditForum;
   }
 
   public String getAllowVerfyPost()
   {
     return this.AllowVerfyPost;
   }
 
   public void setAllowVerfyPost(String allowVerfyPost)
   {
     this.AllowVerfyPost = allowVerfyPost;
   }
 
   public String getAllowDel()
   {
     return this.AllowDel;
   }
 
   public void setAllowDel(String allowDel)
   {
     this.AllowDel = allowDel;
   }
 
   public String getAllowEdit()
   {
     return this.AllowEdit;
   }
 
   public void setAllowEdit(String allowEdit)
   {
     this.AllowEdit = allowEdit;
   }
 
   public String getHide()
   {
     return this.Hide;
   }
 
   public void setHide(String hide)
   {
     this.Hide = hide;
   }
 
   public String getRemoveTheme()
   {
     return this.RemoveTheme;
   }
 
   public void setRemoveTheme(String removeTheme)
   {
     this.RemoveTheme = removeTheme;
   }
 
   public String getMoveTheme()
   {
     return this.MoveTheme;
   }
 
   public void setMoveTheme(String moveTheme)
   {
     this.MoveTheme = moveTheme;
   }
 
   public String getTopTheme()
   {
     return this.TopTheme;
   }
 
   public void setTopTheme(String topTheme)
   {
     this.TopTheme = topTheme;
   }
 
   public String getBrightTheme()
   {
     return this.BrightTheme;
   }
 
   public void setBrightTheme(String brightTheme)
   {
     this.BrightTheme = brightTheme;
   }
 
   public String getUpOrDownTheme()
   {
     return this.UpOrDownTheme;
   }
 
   public void setUpOrDownTheme(String upOrDownTheme)
   {
     this.UpOrDownTheme = upOrDownTheme;
   }
 
   public String getBestTheme()
   {
     return this.BestTheme;
   }
 
   public void setBestTheme(String bestTheme)
   {
     this.BestTheme = bestTheme;
   }
 
   public String getProp1()
   {
     return this.prop1;
   }
 
   public void setProp1(String prop1)
   {
     this.prop1 = prop1;
   }
 
   public String getProp2()
   {
     return this.prop2;
   }
 
   public void setProp2(String prop2)
   {
     this.prop2 = prop2;
   }
 
   public String getProp3()
   {
     return this.prop3;
   }
 
   public void setProp3(String prop3)
   {
     this.prop3 = prop3;
   }
 
   public String getProp4()
   {
     return this.prop4;
   }
 
   public void setProp4(String prop4)
   {
     this.prop4 = prop4;
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
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZCForumGroupSchema
 * JD-Core Version:    0.5.4
 */