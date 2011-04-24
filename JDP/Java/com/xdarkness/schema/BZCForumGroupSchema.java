package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCForumGroup备份<br>
 * 表代码：BZCForumGroup<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZCForumGroupSchema extends Schema {
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

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 20 , 0 , false , false),
		new SchemaColumn("RadminID", DataColumn.LONG, 2, 20 , 0 , false , false),
		new SchemaColumn("Name", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("SystemName", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("Type", DataColumn.STRING, 5, 100 , 0 , true , false),
		new SchemaColumn("Color", DataColumn.STRING, 6, 100 , 0 , false , false),
		new SchemaColumn("Image", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("LowerScore", DataColumn.LONG, 8, 20 , 0 , false , false),
		new SchemaColumn("UpperScore", DataColumn.LONG, 9, 20 , 0 , false , false),
		new SchemaColumn("AllowTheme", DataColumn.STRING, 10, 2 , 0 , false , false),
		new SchemaColumn("AllowReply", DataColumn.STRING, 11, 2 , 0 , false , false),
		new SchemaColumn("AllowSearch", DataColumn.STRING, 12, 2 , 0 , false , false),
		new SchemaColumn("AllowUserInfo", DataColumn.STRING, 13, 2 , 0 , false , false),
		new SchemaColumn("AllowPanel", DataColumn.STRING, 14, 2 , 0 , false , false),
		new SchemaColumn("AllowNickName", DataColumn.STRING, 15, 2 , 0 , false , false),
		new SchemaColumn("AllowVisit", DataColumn.STRING, 16, 2 , 0 , false , false),
		new SchemaColumn("AllowHeadImage", DataColumn.STRING, 17, 2 , 0 , false , false),
		new SchemaColumn("AllowFace", DataColumn.STRING, 18, 2 , 0 , false , false),
		new SchemaColumn("AllowAutograph", DataColumn.STRING, 19, 2 , 0 , false , false),
		new SchemaColumn("Verify", DataColumn.STRING, 20, 2 , 0 , false , false),
		new SchemaColumn("AllowEditUser", DataColumn.STRING, 21, 2 , 0 , false , false),
		new SchemaColumn("AllowForbidUser", DataColumn.STRING, 22, 2 , 0 , false , false),
		new SchemaColumn("AllowEditForum", DataColumn.STRING, 23, 2 , 0 , false , false),
		new SchemaColumn("AllowVerfyPost", DataColumn.STRING, 24, 2 , 0 , false , false),
		new SchemaColumn("AllowDel", DataColumn.STRING, 25, 2 , 0 , false , false),
		new SchemaColumn("AllowEdit", DataColumn.STRING, 26, 2 , 0 , false , false),
		new SchemaColumn("Hide", DataColumn.STRING, 27, 2 , 0 , false , false),
		new SchemaColumn("RemoveTheme", DataColumn.STRING, 28, 2 , 0 , false , false),
		new SchemaColumn("MoveTheme", DataColumn.STRING, 29, 2 , 0 , false , false),
		new SchemaColumn("TopTheme", DataColumn.STRING, 30, 2 , 0 , false , false),
		new SchemaColumn("BrightTheme", DataColumn.STRING, 31, 2 , 0 , false , false),
		new SchemaColumn("UpOrDownTheme", DataColumn.STRING, 32, 2 , 0 , false , false),
		new SchemaColumn("BestTheme", DataColumn.STRING, 33, 2 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 34, 50 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 35, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 36, 50 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 37, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 38, 100 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 39, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 40, 100 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 41, 0 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 42, 20 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 43, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 44, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 45, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 46, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCForumGroup";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into BZCForumGroup values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCForumGroup set ID=?,SiteID=?,RadminID=?,Name=?,SystemName=?,Type=?,Color=?,Image=?,LowerScore=?,UpperScore=?,AllowTheme=?,AllowReply=?,AllowSearch=?,AllowUserInfo=?,AllowPanel=?,AllowNickName=?,AllowVisit=?,AllowHeadImage=?,AllowFace=?,AllowAutograph=?,Verify=?,AllowEditUser=?,AllowForbidUser=?,AllowEditForum=?,AllowVerfyPost=?,AllowDel=?,AllowEdit=?,Hide=?,RemoveTheme=?,MoveTheme=?,TopTheme=?,BrightTheme=?,UpOrDownTheme=?,BestTheme=?,prop1=?,prop2=?,prop3=?,prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,OrderFlag=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCForumGroup  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCForumGroup  where ID=? and BackupNo=?";

	public BZCForumGroupSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[47];
	}

	protected Schema newInstance(){
		return new BZCForumGroupSchema();
	}

	protected SchemaSet newSet(){
		return new BZCForumGroupSet();
	}

	public BZCForumGroupSet query() {
		return query(null, -1, -1);
	}

	public BZCForumGroupSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCForumGroupSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCForumGroupSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCForumGroupSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){RadminID = null;}else{RadminID = new Long(v.toString());}return;}
		if (i == 3){Name = (String)v;return;}
		if (i == 4){SystemName = (String)v;return;}
		if (i == 5){Type = (String)v;return;}
		if (i == 6){Color = (String)v;return;}
		if (i == 7){Image = (String)v;return;}
		if (i == 8){if(v==null){LowerScore = null;}else{LowerScore = new Long(v.toString());}return;}
		if (i == 9){if(v==null){UpperScore = null;}else{UpperScore = new Long(v.toString());}return;}
		if (i == 10){AllowTheme = (String)v;return;}
		if (i == 11){AllowReply = (String)v;return;}
		if (i == 12){AllowSearch = (String)v;return;}
		if (i == 13){AllowUserInfo = (String)v;return;}
		if (i == 14){AllowPanel = (String)v;return;}
		if (i == 15){AllowNickName = (String)v;return;}
		if (i == 16){AllowVisit = (String)v;return;}
		if (i == 17){AllowHeadImage = (String)v;return;}
		if (i == 18){AllowFace = (String)v;return;}
		if (i == 19){AllowAutograph = (String)v;return;}
		if (i == 20){Verify = (String)v;return;}
		if (i == 21){AllowEditUser = (String)v;return;}
		if (i == 22){AllowForbidUser = (String)v;return;}
		if (i == 23){AllowEditForum = (String)v;return;}
		if (i == 24){AllowVerfyPost = (String)v;return;}
		if (i == 25){AllowDel = (String)v;return;}
		if (i == 26){AllowEdit = (String)v;return;}
		if (i == 27){Hide = (String)v;return;}
		if (i == 28){RemoveTheme = (String)v;return;}
		if (i == 29){MoveTheme = (String)v;return;}
		if (i == 30){TopTheme = (String)v;return;}
		if (i == 31){BrightTheme = (String)v;return;}
		if (i == 32){UpOrDownTheme = (String)v;return;}
		if (i == 33){BestTheme = (String)v;return;}
		if (i == 34){prop1 = (String)v;return;}
		if (i == 35){prop2 = (String)v;return;}
		if (i == 36){prop3 = (String)v;return;}
		if (i == 37){prop4 = (String)v;return;}
		if (i == 38){AddUser = (String)v;return;}
		if (i == 39){AddTime = (Date)v;return;}
		if (i == 40){ModifyUser = (String)v;return;}
		if (i == 41){ModifyTime = (Date)v;return;}
		if (i == 42){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 43){BackupNo = (String)v;return;}
		if (i == 44){BackupOperator = (String)v;return;}
		if (i == 45){BackupTime = (Date)v;return;}
		if (i == 46){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return RadminID;}
		if (i == 3){return Name;}
		if (i == 4){return SystemName;}
		if (i == 5){return Type;}
		if (i == 6){return Color;}
		if (i == 7){return Image;}
		if (i == 8){return LowerScore;}
		if (i == 9){return UpperScore;}
		if (i == 10){return AllowTheme;}
		if (i == 11){return AllowReply;}
		if (i == 12){return AllowSearch;}
		if (i == 13){return AllowUserInfo;}
		if (i == 14){return AllowPanel;}
		if (i == 15){return AllowNickName;}
		if (i == 16){return AllowVisit;}
		if (i == 17){return AllowHeadImage;}
		if (i == 18){return AllowFace;}
		if (i == 19){return AllowAutograph;}
		if (i == 20){return Verify;}
		if (i == 21){return AllowEditUser;}
		if (i == 22){return AllowForbidUser;}
		if (i == 23){return AllowEditForum;}
		if (i == 24){return AllowVerfyPost;}
		if (i == 25){return AllowDel;}
		if (i == 26){return AllowEdit;}
		if (i == 27){return Hide;}
		if (i == 28){return RemoveTheme;}
		if (i == 29){return MoveTheme;}
		if (i == 30){return TopTheme;}
		if (i == 31){return BrightTheme;}
		if (i == 32){return UpOrDownTheme;}
		if (i == 33){return BestTheme;}
		if (i == 34){return prop1;}
		if (i == 35){return prop2;}
		if (i == 36){return prop3;}
		if (i == 37){return prop4;}
		if (i == 38){return AddUser;}
		if (i == 39){return AddTime;}
		if (i == 40){return ModifyUser;}
		if (i == 41){return ModifyTime;}
		if (i == 42){return OrderFlag;}
		if (i == 43){return BackupNo;}
		if (i == 44){return BackupOperator;}
		if (i == 45){return BackupTime;}
		if (i == 46){return BackupMemo;}
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
	* 获取字段RadminID的值，该字段的<br>
	* 字段名称 :RadminID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getRadminID() {
		if(RadminID==null){return 0;}
		return RadminID.longValue();
	}

	/**
	* 设置字段RadminID的值，该字段的<br>
	* 字段名称 :RadminID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRadminID(long radminID) {
		this.RadminID = new Long(radminID);
    }

	/**
	* 设置字段RadminID的值，该字段的<br>
	* 字段名称 :RadminID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRadminID(String radminID) {
		if (radminID == null){
			this.RadminID = null;
			return;
		}
		this.RadminID = new Long(radminID);
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
	* 获取字段SystemName的值，该字段的<br>
	* 字段名称 :SystemName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSystemName() {
		return SystemName;
	}

	/**
	* 设置字段SystemName的值，该字段的<br>
	* 字段名称 :SystemName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSystemName(String systemName) {
		this.SystemName = systemName;
    }

	/**
	* 获取字段Type的值，该字段的<br>
	* 字段名称 :Type<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* 设置字段Type的值，该字段的<br>
	* 字段名称 :Type<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* 获取字段Color的值，该字段的<br>
	* 字段名称 :Color<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getColor() {
		return Color;
	}

	/**
	* 设置字段Color的值，该字段的<br>
	* 字段名称 :Color<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setColor(String color) {
		this.Color = color;
    }

	/**
	* 获取字段Image的值，该字段的<br>
	* 字段名称 :Image<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getImage() {
		return Image;
	}

	/**
	* 设置字段Image的值，该字段的<br>
	* 字段名称 :Image<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setImage(String image) {
		this.Image = image;
    }

	/**
	* 获取字段LowerScore的值，该字段的<br>
	* 字段名称 :LowerScore<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getLowerScore() {
		if(LowerScore==null){return 0;}
		return LowerScore.longValue();
	}

	/**
	* 设置字段LowerScore的值，该字段的<br>
	* 字段名称 :LowerScore<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLowerScore(long lowerScore) {
		this.LowerScore = new Long(lowerScore);
    }

	/**
	* 设置字段LowerScore的值，该字段的<br>
	* 字段名称 :LowerScore<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLowerScore(String lowerScore) {
		if (lowerScore == null){
			this.LowerScore = null;
			return;
		}
		this.LowerScore = new Long(lowerScore);
    }

	/**
	* 获取字段UpperScore的值，该字段的<br>
	* 字段名称 :UpperScore<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getUpperScore() {
		if(UpperScore==null){return 0;}
		return UpperScore.longValue();
	}

	/**
	* 设置字段UpperScore的值，该字段的<br>
	* 字段名称 :UpperScore<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUpperScore(long upperScore) {
		this.UpperScore = new Long(upperScore);
    }

	/**
	* 设置字段UpperScore的值，该字段的<br>
	* 字段名称 :UpperScore<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUpperScore(String upperScore) {
		if (upperScore == null){
			this.UpperScore = null;
			return;
		}
		this.UpperScore = new Long(upperScore);
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
	* 获取字段AllowReply的值，该字段的<br>
	* 字段名称 :AllowReply<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowReply() {
		return AllowReply;
	}

	/**
	* 设置字段AllowReply的值，该字段的<br>
	* 字段名称 :AllowReply<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowReply(String allowReply) {
		this.AllowReply = allowReply;
    }

	/**
	* 获取字段AllowSearch的值，该字段的<br>
	* 字段名称 :AllowSearch<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowSearch() {
		return AllowSearch;
	}

	/**
	* 设置字段AllowSearch的值，该字段的<br>
	* 字段名称 :AllowSearch<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowSearch(String allowSearch) {
		this.AllowSearch = allowSearch;
    }

	/**
	* 获取字段AllowUserInfo的值，该字段的<br>
	* 字段名称 :AllowUserInfo<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowUserInfo() {
		return AllowUserInfo;
	}

	/**
	* 设置字段AllowUserInfo的值，该字段的<br>
	* 字段名称 :AllowUserInfo<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowUserInfo(String allowUserInfo) {
		this.AllowUserInfo = allowUserInfo;
    }

	/**
	* 获取字段AllowPanel的值，该字段的<br>
	* 字段名称 :AllowPanel<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowPanel() {
		return AllowPanel;
	}

	/**
	* 设置字段AllowPanel的值，该字段的<br>
	* 字段名称 :AllowPanel<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowPanel(String allowPanel) {
		this.AllowPanel = allowPanel;
    }

	/**
	* 获取字段AllowNickName的值，该字段的<br>
	* 字段名称 :AllowNickName<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowNickName() {
		return AllowNickName;
	}

	/**
	* 设置字段AllowNickName的值，该字段的<br>
	* 字段名称 :AllowNickName<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowNickName(String allowNickName) {
		this.AllowNickName = allowNickName;
    }

	/**
	* 获取字段AllowVisit的值，该字段的<br>
	* 字段名称 :AllowVisit<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowVisit() {
		return AllowVisit;
	}

	/**
	* 设置字段AllowVisit的值，该字段的<br>
	* 字段名称 :AllowVisit<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowVisit(String allowVisit) {
		this.AllowVisit = allowVisit;
    }

	/**
	* 获取字段AllowHeadImage的值，该字段的<br>
	* 字段名称 :AllowHeadImage<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowHeadImage() {
		return AllowHeadImage;
	}

	/**
	* 设置字段AllowHeadImage的值，该字段的<br>
	* 字段名称 :AllowHeadImage<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowHeadImage(String allowHeadImage) {
		this.AllowHeadImage = allowHeadImage;
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
	* 获取字段AllowAutograph的值，该字段的<br>
	* 字段名称 :AllowAutograph<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowAutograph() {
		return AllowAutograph;
	}

	/**
	* 设置字段AllowAutograph的值，该字段的<br>
	* 字段名称 :AllowAutograph<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowAutograph(String allowAutograph) {
		this.AllowAutograph = allowAutograph;
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
	* 获取字段AllowEditUser的值，该字段的<br>
	* 字段名称 :AllowEditUser<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowEditUser() {
		return AllowEditUser;
	}

	/**
	* 设置字段AllowEditUser的值，该字段的<br>
	* 字段名称 :AllowEditUser<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowEditUser(String allowEditUser) {
		this.AllowEditUser = allowEditUser;
    }

	/**
	* 获取字段AllowForbidUser的值，该字段的<br>
	* 字段名称 :AllowForbidUser<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowForbidUser() {
		return AllowForbidUser;
	}

	/**
	* 设置字段AllowForbidUser的值，该字段的<br>
	* 字段名称 :AllowForbidUser<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowForbidUser(String allowForbidUser) {
		this.AllowForbidUser = allowForbidUser;
    }

	/**
	* 获取字段AllowEditForum的值，该字段的<br>
	* 字段名称 :AllowEditForum<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowEditForum() {
		return AllowEditForum;
	}

	/**
	* 设置字段AllowEditForum的值，该字段的<br>
	* 字段名称 :AllowEditForum<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowEditForum(String allowEditForum) {
		this.AllowEditForum = allowEditForum;
    }

	/**
	* 获取字段AllowVerfyPost的值，该字段的<br>
	* 字段名称 :AllowVerfyPost<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowVerfyPost() {
		return AllowVerfyPost;
	}

	/**
	* 设置字段AllowVerfyPost的值，该字段的<br>
	* 字段名称 :AllowVerfyPost<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowVerfyPost(String allowVerfyPost) {
		this.AllowVerfyPost = allowVerfyPost;
    }

	/**
	* 获取字段AllowDel的值，该字段的<br>
	* 字段名称 :AllowDel<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowDel() {
		return AllowDel;
	}

	/**
	* 设置字段AllowDel的值，该字段的<br>
	* 字段名称 :AllowDel<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowDel(String allowDel) {
		this.AllowDel = allowDel;
    }

	/**
	* 获取字段AllowEdit的值，该字段的<br>
	* 字段名称 :AllowEdit<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowEdit() {
		return AllowEdit;
	}

	/**
	* 设置字段AllowEdit的值，该字段的<br>
	* 字段名称 :AllowEdit<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowEdit(String allowEdit) {
		this.AllowEdit = allowEdit;
    }

	/**
	* 获取字段Hide的值，该字段的<br>
	* 字段名称 :Hide<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getHide() {
		return Hide;
	}

	/**
	* 设置字段Hide的值，该字段的<br>
	* 字段名称 :Hide<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setHide(String hide) {
		this.Hide = hide;
    }

	/**
	* 获取字段RemoveTheme的值，该字段的<br>
	* 字段名称 :RemoveTheme<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRemoveTheme() {
		return RemoveTheme;
	}

	/**
	* 设置字段RemoveTheme的值，该字段的<br>
	* 字段名称 :RemoveTheme<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRemoveTheme(String removeTheme) {
		this.RemoveTheme = removeTheme;
    }

	/**
	* 获取字段MoveTheme的值，该字段的<br>
	* 字段名称 :MoveTheme<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMoveTheme() {
		return MoveTheme;
	}

	/**
	* 设置字段MoveTheme的值，该字段的<br>
	* 字段名称 :MoveTheme<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMoveTheme(String moveTheme) {
		this.MoveTheme = moveTheme;
    }

	/**
	* 获取字段TopTheme的值，该字段的<br>
	* 字段名称 :TopTheme<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTopTheme() {
		return TopTheme;
	}

	/**
	* 设置字段TopTheme的值，该字段的<br>
	* 字段名称 :TopTheme<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTopTheme(String topTheme) {
		this.TopTheme = topTheme;
    }

	/**
	* 获取字段BrightTheme的值，该字段的<br>
	* 字段名称 :BrightTheme<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBrightTheme() {
		return BrightTheme;
	}

	/**
	* 设置字段BrightTheme的值，该字段的<br>
	* 字段名称 :BrightTheme<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBrightTheme(String brightTheme) {
		this.BrightTheme = brightTheme;
    }

	/**
	* 获取字段UpOrDownTheme的值，该字段的<br>
	* 字段名称 :UpOrDownTheme<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getUpOrDownTheme() {
		return UpOrDownTheme;
	}

	/**
	* 设置字段UpOrDownTheme的值，该字段的<br>
	* 字段名称 :UpOrDownTheme<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUpOrDownTheme(String upOrDownTheme) {
		this.UpOrDownTheme = upOrDownTheme;
    }

	/**
	* 获取字段BestTheme的值，该字段的<br>
	* 字段名称 :BestTheme<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBestTheme() {
		return BestTheme;
	}

	/**
	* 设置字段BestTheme的值，该字段的<br>
	* 字段名称 :BestTheme<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBestTheme(String bestTheme) {
		this.BestTheme = bestTheme;
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

	/**
	* 获取字段OrderFlag的值，该字段的<br>
	* 字段名称 :OrderFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
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
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :OrderFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(String orderFlag) {
		if (orderFlag == null){
			this.OrderFlag = null;
			return;
		}
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 获取字段BackupNo的值，该字段的<br>
	* 字段名称 :备份编号<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getBackupNo() {
		return BackupNo;
	}

	/**
	* 设置字段BackupNo的值，该字段的<br>
	* 字段名称 :备份编号<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setBackupNo(String backupNo) {
		this.BackupNo = backupNo;
    }

	/**
	* 获取字段BackupOperator的值，该字段的<br>
	* 字段名称 :备份人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getBackupOperator() {
		return BackupOperator;
	}

	/**
	* 设置字段BackupOperator的值，该字段的<br>
	* 字段名称 :备份人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBackupOperator(String backupOperator) {
		this.BackupOperator = backupOperator;
    }

	/**
	* 获取字段BackupTime的值，该字段的<br>
	* 字段名称 :备份时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getBackupTime() {
		return BackupTime;
	}

	/**
	* 设置字段BackupTime的值，该字段的<br>
	* 字段名称 :备份时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBackupTime(Date backupTime) {
		this.BackupTime = backupTime;
    }

	/**
	* 获取字段BackupMemo的值，该字段的<br>
	* 字段名称 :备份备注<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBackupMemo() {
		return BackupMemo;
	}

	/**
	* 设置字段BackupMemo的值，该字段的<br>
	* 字段名称 :备份备注<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBackupMemo(String backupMemo) {
		this.BackupMemo = backupMemo;
    }

}