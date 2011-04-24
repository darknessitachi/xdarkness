package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCForum<br>
 * 表代码：ZCForum<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID<br>
 */
public class ZCForumSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private Long ParentID;

	private String Type;

	private String Name;

	private String Status;

	private String Pic;

	private String Visible;

	private String Info;

	private Integer ThemeCount;

	private String Verify;

	private String Locked;

	private String UnLockID;

	private String AllowTheme;

	private String EditPost;

	private String ReplyPost;

	private String Recycle;

	private String AllowHTML;

	private String AllowFace;

	private String AnonyPost;

	private String URL;

	private String Image;

	private String Password;

	private String UnPasswordID;

	private String Word;

	private Integer PostCount;

	private String ForumAdmin;

	private Integer TodayPostCount;

	private Long LastThemeID;

	private String LastPost;

	private String LastPoster;

	private Long OrderFlag;

	private String prop1;

	private String prop2;

	private String prop3;

	private String prop4;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 20 , 0 , false , false),
		new SchemaColumn("ParentID", DataColumn.LONG, 2, 20 , 0 , true , false),
		new SchemaColumn("Type", DataColumn.STRING, 3, 20 , 0 , true , false),
		new SchemaColumn("Name", DataColumn.STRING, 4, 100 , 0 , true , false),
		new SchemaColumn("Status", DataColumn.STRING, 5, 2 , 0 , true , false),
		new SchemaColumn("Pic", DataColumn.STRING, 6, 100 , 0 , false , false),
		new SchemaColumn("Visible", DataColumn.STRING, 7, 2 , 0 , false , false),
		new SchemaColumn("Info", DataColumn.STRING, 8, 1000 , 0 , false , false),
		new SchemaColumn("ThemeCount", DataColumn.INTEGER, 9, 11 , 0 , true , false),
		new SchemaColumn("Verify", DataColumn.STRING, 10, 2 , 0 , false , false),
		new SchemaColumn("Locked", DataColumn.STRING, 11, 2 , 0 , false , false),
		new SchemaColumn("UnLockID", DataColumn.STRING, 12, 300 , 0 , false , false),
		new SchemaColumn("AllowTheme", DataColumn.STRING, 13, 2 , 0 , false , false),
		new SchemaColumn("EditPost", DataColumn.STRING, 14, 2 , 0 , false , false),
		new SchemaColumn("ReplyPost", DataColumn.STRING, 15, 2 , 0 , false , false),
		new SchemaColumn("Recycle", DataColumn.STRING, 16, 2 , 0 , false , false),
		new SchemaColumn("AllowHTML", DataColumn.STRING, 17, 2 , 0 , false , false),
		new SchemaColumn("AllowFace", DataColumn.STRING, 18, 2 , 0 , false , false),
		new SchemaColumn("AnonyPost", DataColumn.STRING, 19, 2 , 0 , false , false),
		new SchemaColumn("URL", DataColumn.STRING, 20, 200 , 0 , false , false),
		new SchemaColumn("Image", DataColumn.STRING, 21, 200 , 0 , false , false),
		new SchemaColumn("Password", DataColumn.STRING, 22, 100 , 0 , false , false),
		new SchemaColumn("UnPasswordID", DataColumn.STRING, 23, 300 , 0 , false , false),
		new SchemaColumn("Word", DataColumn.STRING, 24, 200 , 0 , false , false),
		new SchemaColumn("PostCount", DataColumn.INTEGER, 25, 11 , 0 , true , false),
		new SchemaColumn("ForumAdmin", DataColumn.STRING, 26, 100 , 0 , false , false),
		new SchemaColumn("TodayPostCount", DataColumn.INTEGER, 27, 11 , 0 , false , false),
		new SchemaColumn("LastThemeID", DataColumn.LONG, 28, 20 , 0 , false , false),
		new SchemaColumn("LastPost", DataColumn.STRING, 29, 100 , 0 , false , false),
		new SchemaColumn("LastPoster", DataColumn.STRING, 30, 100 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 31, 20 , 0 , true , false),
		new SchemaColumn("prop1", DataColumn.STRING, 32, 50 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 33, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 34, 50 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 35, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 36, 100 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 37, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 38, 100 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 39, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCForum";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into ZCForum values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCForum set ID=?,SiteID=?,ParentID=?,Type=?,Name=?,Status=?,Pic=?,Visible=?,Info=?,ThemeCount=?,Verify=?,Locked=?,UnLockID=?,AllowTheme=?,EditPost=?,ReplyPost=?,Recycle=?,AllowHTML=?,AllowFace=?,AnonyPost=?,URL=?,Image=?,Password=?,UnPasswordID=?,Word=?,PostCount=?,ForumAdmin=?,TodayPostCount=?,LastThemeID=?,LastPost=?,LastPoster=?,OrderFlag=?,prop1=?,prop2=?,prop3=?,prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCForum  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCForum  where ID=?";

	public ZCForumSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[40];
	}

	protected Schema newInstance(){
		return new ZCForumSchema();
	}

	protected SchemaSet newSet(){
		return new ZCForumSet();
	}

	public ZCForumSet query() {
		return query(null, -1, -1);
	}

	public ZCForumSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCForumSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCForumSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCForumSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){ParentID = null;}else{ParentID = new Long(v.toString());}return;}
		if (i == 3){Type = (String)v;return;}
		if (i == 4){Name = (String)v;return;}
		if (i == 5){Status = (String)v;return;}
		if (i == 6){Pic = (String)v;return;}
		if (i == 7){Visible = (String)v;return;}
		if (i == 8){Info = (String)v;return;}
		if (i == 9){if(v==null){ThemeCount = null;}else{ThemeCount = new Integer(v.toString());}return;}
		if (i == 10){Verify = (String)v;return;}
		if (i == 11){Locked = (String)v;return;}
		if (i == 12){UnLockID = (String)v;return;}
		if (i == 13){AllowTheme = (String)v;return;}
		if (i == 14){EditPost = (String)v;return;}
		if (i == 15){ReplyPost = (String)v;return;}
		if (i == 16){Recycle = (String)v;return;}
		if (i == 17){AllowHTML = (String)v;return;}
		if (i == 18){AllowFace = (String)v;return;}
		if (i == 19){AnonyPost = (String)v;return;}
		if (i == 20){URL = (String)v;return;}
		if (i == 21){Image = (String)v;return;}
		if (i == 22){Password = (String)v;return;}
		if (i == 23){UnPasswordID = (String)v;return;}
		if (i == 24){Word = (String)v;return;}
		if (i == 25){if(v==null){PostCount = null;}else{PostCount = new Integer(v.toString());}return;}
		if (i == 26){ForumAdmin = (String)v;return;}
		if (i == 27){if(v==null){TodayPostCount = null;}else{TodayPostCount = new Integer(v.toString());}return;}
		if (i == 28){if(v==null){LastThemeID = null;}else{LastThemeID = new Long(v.toString());}return;}
		if (i == 29){LastPost = (String)v;return;}
		if (i == 30){LastPoster = (String)v;return;}
		if (i == 31){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 32){prop1 = (String)v;return;}
		if (i == 33){prop2 = (String)v;return;}
		if (i == 34){prop3 = (String)v;return;}
		if (i == 35){prop4 = (String)v;return;}
		if (i == 36){AddUser = (String)v;return;}
		if (i == 37){AddTime = (Date)v;return;}
		if (i == 38){ModifyUser = (String)v;return;}
		if (i == 39){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return ParentID;}
		if (i == 3){return Type;}
		if (i == 4){return Name;}
		if (i == 5){return Status;}
		if (i == 6){return Pic;}
		if (i == 7){return Visible;}
		if (i == 8){return Info;}
		if (i == 9){return ThemeCount;}
		if (i == 10){return Verify;}
		if (i == 11){return Locked;}
		if (i == 12){return UnLockID;}
		if (i == 13){return AllowTheme;}
		if (i == 14){return EditPost;}
		if (i == 15){return ReplyPost;}
		if (i == 16){return Recycle;}
		if (i == 17){return AllowHTML;}
		if (i == 18){return AllowFace;}
		if (i == 19){return AnonyPost;}
		if (i == 20){return URL;}
		if (i == 21){return Image;}
		if (i == 22){return Password;}
		if (i == 23){return UnPasswordID;}
		if (i == 24){return Word;}
		if (i == 25){return PostCount;}
		if (i == 26){return ForumAdmin;}
		if (i == 27){return TodayPostCount;}
		if (i == 28){return LastThemeID;}
		if (i == 29){return LastPost;}
		if (i == 30){return LastPoster;}
		if (i == 31){return OrderFlag;}
		if (i == 32){return prop1;}
		if (i == 33){return prop2;}
		if (i == 34){return prop3;}
		if (i == 35){return prop4;}
		if (i == 36){return AddUser;}
		if (i == 37){return AddTime;}
		if (i == 38){return ModifyUser;}
		if (i == 39){return ModifyTime;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getID() {
		if(ID==null){return 0;}
		return ID.longValue();
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(long iD) {
		this.ID = new Long(iD);
    }

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		if (iD == null){
			this.ID = null;
			return;
		}
		this.ID = new Long(iD);
    }

	/**
	* 获取字段SiteID的值，该字段的<br>
	* 字段名称 :SiteID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getSiteID() {
		if(SiteID==null){return 0;}
		return SiteID.longValue();
	}

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :SiteID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :SiteID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSiteID(String siteID) {
		if (siteID == null){
			this.SiteID = null;
			return;
		}
		this.SiteID = new Long(siteID);
    }

	/**
	* 获取字段ParentID的值，该字段的<br>
	* 字段名称 :ParentID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getParentID() {
		if(ParentID==null){return 0;}
		return ParentID.longValue();
	}

	/**
	* 设置字段ParentID的值，该字段的<br>
	* 字段名称 :ParentID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setParentID(long parentID) {
		this.ParentID = new Long(parentID);
    }

	/**
	* 设置字段ParentID的值，该字段的<br>
	* 字段名称 :ParentID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setParentID(String parentID) {
		if (parentID == null){
			this.ParentID = null;
			return;
		}
		this.ParentID = new Long(parentID);
    }

	/**
	* 获取字段Type的值，该字段的<br>
	* 字段名称 :Type<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* 设置字段Type的值，该字段的<br>
	* 字段名称 :Type<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* 获取字段Name的值，该字段的<br>
	* 字段名称 :Name<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* 设置字段Name的值，该字段的<br>
	* 字段名称 :Name<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* 获取字段Status的值，该字段的<br>
	* 字段名称 :Status<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* 设置字段Status的值，该字段的<br>
	* 字段名称 :Status<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
    }

	/**
	* 获取字段Pic的值，该字段的<br>
	* 字段名称 :Pic<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPic() {
		return Pic;
	}

	/**
	* 设置字段Pic的值，该字段的<br>
	* 字段名称 :Pic<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPic(String pic) {
		this.Pic = pic;
    }

	/**
	* 获取字段Visible的值，该字段的<br>
	* 字段名称 :Visible<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getVisible() {
		return Visible;
	}

	/**
	* 设置字段Visible的值，该字段的<br>
	* 字段名称 :Visible<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setVisible(String visible) {
		this.Visible = visible;
    }

	/**
	* 获取字段Info的值，该字段的<br>
	* 字段名称 :Info<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInfo() {
		return Info;
	}

	/**
	* 设置字段Info的值，该字段的<br>
	* 字段名称 :Info<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInfo(String info) {
		this.Info = info;
    }

	/**
	* 获取字段ThemeCount的值，该字段的<br>
	* 字段名称 :ThemeCount<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getThemeCount() {
		if(ThemeCount==null){return 0;}
		return ThemeCount.intValue();
	}

	/**
	* 设置字段ThemeCount的值，该字段的<br>
	* 字段名称 :ThemeCount<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setThemeCount(int themeCount) {
		this.ThemeCount = new Integer(themeCount);
    }

	/**
	* 设置字段ThemeCount的值，该字段的<br>
	* 字段名称 :ThemeCount<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setThemeCount(String themeCount) {
		if (themeCount == null){
			this.ThemeCount = null;
			return;
		}
		this.ThemeCount = new Integer(themeCount);
    }

	/**
	* 获取字段Verify的值，该字段的<br>
	* 字段名称 :Verify<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getVerify() {
		return Verify;
	}

	/**
	* 设置字段Verify的值，该字段的<br>
	* 字段名称 :Verify<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setVerify(String verify) {
		this.Verify = verify;
    }

	/**
	* 获取字段Locked的值，该字段的<br>
	* 字段名称 :Locked<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLocked() {
		return Locked;
	}

	/**
	* 设置字段Locked的值，该字段的<br>
	* 字段名称 :Locked<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLocked(String locked) {
		this.Locked = locked;
    }

	/**
	* 获取字段UnLockID的值，该字段的<br>
	* 字段名称 :UnLockID<br>
	* 数据类型 :varchar(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getUnLockID() {
		return UnLockID;
	}

	/**
	* 设置字段UnLockID的值，该字段的<br>
	* 字段名称 :UnLockID<br>
	* 数据类型 :varchar(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUnLockID(String unLockID) {
		this.UnLockID = unLockID;
    }

	/**
	* 获取字段AllowTheme的值，该字段的<br>
	* 字段名称 :AllowTheme<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowTheme() {
		return AllowTheme;
	}

	/**
	* 设置字段AllowTheme的值，该字段的<br>
	* 字段名称 :AllowTheme<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowTheme(String allowTheme) {
		this.AllowTheme = allowTheme;
    }

	/**
	* 获取字段EditPost的值，该字段的<br>
	* 字段名称 :EditPost<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getEditPost() {
		return EditPost;
	}

	/**
	* 设置字段EditPost的值，该字段的<br>
	* 字段名称 :EditPost<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEditPost(String editPost) {
		this.EditPost = editPost;
    }

	/**
	* 获取字段ReplyPost的值，该字段的<br>
	* 字段名称 :ReplyPost<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getReplyPost() {
		return ReplyPost;
	}

	/**
	* 设置字段ReplyPost的值，该字段的<br>
	* 字段名称 :ReplyPost<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReplyPost(String replyPost) {
		this.ReplyPost = replyPost;
    }

	/**
	* 获取字段Recycle的值，该字段的<br>
	* 字段名称 :Recycle<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRecycle() {
		return Recycle;
	}

	/**
	* 设置字段Recycle的值，该字段的<br>
	* 字段名称 :Recycle<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRecycle(String recycle) {
		this.Recycle = recycle;
    }

	/**
	* 获取字段AllowHTML的值，该字段的<br>
	* 字段名称 :AllowHTML<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowHTML() {
		return AllowHTML;
	}

	/**
	* 设置字段AllowHTML的值，该字段的<br>
	* 字段名称 :AllowHTML<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowHTML(String allowHTML) {
		this.AllowHTML = allowHTML;
    }

	/**
	* 获取字段AllowFace的值，该字段的<br>
	* 字段名称 :AllowFace<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowFace() {
		return AllowFace;
	}

	/**
	* 设置字段AllowFace的值，该字段的<br>
	* 字段名称 :AllowFace<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowFace(String allowFace) {
		this.AllowFace = allowFace;
    }

	/**
	* 获取字段AnonyPost的值，该字段的<br>
	* 字段名称 :AnonyPost<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAnonyPost() {
		return AnonyPost;
	}

	/**
	* 设置字段AnonyPost的值，该字段的<br>
	* 字段名称 :AnonyPost<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAnonyPost(String anonyPost) {
		this.AnonyPost = anonyPost;
    }

	/**
	* 获取字段URL的值，该字段的<br>
	* 字段名称 :URL<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getURL() {
		return URL;
	}

	/**
	* 设置字段URL的值，该字段的<br>
	* 字段名称 :URL<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setURL(String uRL) {
		this.URL = uRL;
    }

	/**
	* 获取字段Image的值，该字段的<br>
	* 字段名称 :Image<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getImage() {
		return Image;
	}

	/**
	* 设置字段Image的值，该字段的<br>
	* 字段名称 :Image<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setImage(String image) {
		this.Image = image;
    }

	/**
	* 获取字段Password的值，该字段的<br>
	* 字段名称 :Password<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPassword() {
		return Password;
	}

	/**
	* 设置字段Password的值，该字段的<br>
	* 字段名称 :Password<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPassword(String password) {
		this.Password = password;
    }

	/**
	* 获取字段UnPasswordID的值，该字段的<br>
	* 字段名称 :UnPasswordID<br>
	* 数据类型 :varchar(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getUnPasswordID() {
		return UnPasswordID;
	}

	/**
	* 设置字段UnPasswordID的值，该字段的<br>
	* 字段名称 :UnPasswordID<br>
	* 数据类型 :varchar(300)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUnPasswordID(String unPasswordID) {
		this.UnPasswordID = unPasswordID;
    }

	/**
	* 获取字段Word的值，该字段的<br>
	* 字段名称 :Word<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getWord() {
		return Word;
	}

	/**
	* 设置字段Word的值，该字段的<br>
	* 字段名称 :Word<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setWord(String word) {
		this.Word = word;
    }

	/**
	* 获取字段PostCount的值，该字段的<br>
	* 字段名称 :PostCount<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getPostCount() {
		if(PostCount==null){return 0;}
		return PostCount.intValue();
	}

	/**
	* 设置字段PostCount的值，该字段的<br>
	* 字段名称 :PostCount<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setPostCount(int postCount) {
		this.PostCount = new Integer(postCount);
    }

	/**
	* 设置字段PostCount的值，该字段的<br>
	* 字段名称 :PostCount<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setPostCount(String postCount) {
		if (postCount == null){
			this.PostCount = null;
			return;
		}
		this.PostCount = new Integer(postCount);
    }

	/**
	* 获取字段ForumAdmin的值，该字段的<br>
	* 字段名称 :ForumAdmin<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getForumAdmin() {
		return ForumAdmin;
	}

	/**
	* 设置字段ForumAdmin的值，该字段的<br>
	* 字段名称 :ForumAdmin<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setForumAdmin(String forumAdmin) {
		this.ForumAdmin = forumAdmin;
    }

	/**
	* 获取字段TodayPostCount的值，该字段的<br>
	* 字段名称 :TodayPostCount<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getTodayPostCount() {
		if(TodayPostCount==null){return 0;}
		return TodayPostCount.intValue();
	}

	/**
	* 设置字段TodayPostCount的值，该字段的<br>
	* 字段名称 :TodayPostCount<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTodayPostCount(int todayPostCount) {
		this.TodayPostCount = new Integer(todayPostCount);
    }

	/**
	* 设置字段TodayPostCount的值，该字段的<br>
	* 字段名称 :TodayPostCount<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTodayPostCount(String todayPostCount) {
		if (todayPostCount == null){
			this.TodayPostCount = null;
			return;
		}
		this.TodayPostCount = new Integer(todayPostCount);
    }

	/**
	* 获取字段LastThemeID的值，该字段的<br>
	* 字段名称 :LastThemeID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getLastThemeID() {
		if(LastThemeID==null){return 0;}
		return LastThemeID.longValue();
	}

	/**
	* 设置字段LastThemeID的值，该字段的<br>
	* 字段名称 :LastThemeID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLastThemeID(long lastThemeID) {
		this.LastThemeID = new Long(lastThemeID);
    }

	/**
	* 设置字段LastThemeID的值，该字段的<br>
	* 字段名称 :LastThemeID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLastThemeID(String lastThemeID) {
		if (lastThemeID == null){
			this.LastThemeID = null;
			return;
		}
		this.LastThemeID = new Long(lastThemeID);
    }

	/**
	* 获取字段LastPost的值，该字段的<br>
	* 字段名称 :LastPost<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLastPost() {
		return LastPost;
	}

	/**
	* 设置字段LastPost的值，该字段的<br>
	* 字段名称 :LastPost<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLastPost(String lastPost) {
		this.LastPost = lastPost;
    }

	/**
	* 获取字段LastPoster的值，该字段的<br>
	* 字段名称 :LastPoster<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLastPoster() {
		return LastPoster;
	}

	/**
	* 设置字段LastPoster的值，该字段的<br>
	* 字段名称 :LastPoster<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLastPoster(String lastPoster) {
		this.LastPoster = lastPoster;
    }

	/**
	* 获取字段OrderFlag的值，该字段的<br>
	* 字段名称 :OrderFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getOrderFlag() {
		if(OrderFlag==null){return 0;}
		return OrderFlag.longValue();
	}

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :OrderFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :OrderFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setOrderFlag(String orderFlag) {
		if (orderFlag == null){
			this.OrderFlag = null;
			return;
		}
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :prop3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :prop3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* 获取字段prop4的值，该字段的<br>
	* 字段名称 :prop4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return prop4;
	}

	/**
	* 设置字段prop4的值，该字段的<br>
	* 字段名称 :prop4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.prop4 = prop4;
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :ModifyUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :ModifyUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段ModifyTime的值，该字段的<br>
	* 字段名称 :ModifyTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* 设置字段ModifyTime的值，该字段的<br>
	* 字段名称 :ModifyTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
    }

}