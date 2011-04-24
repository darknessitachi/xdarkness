package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCTheme备份<br>
 * 表代码：BZCTheme<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZCThemeSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private Long ForumID;

	private String Subject;

	private String IPAddress;

	private String Type;

	private String Status;

	private String LastPost;

	private String LastPoster;

	private Integer ViewCount;

	private Integer ReplyCount;

	private Long OrderFlag;

	private String VerifyFlag;

	private String TopTheme;

	private String Best;

	private String Bright;

	private String Applydel;

	private Date LastPostTime;

	private Date OrderTime;

	private String prop1;

	private String prop2;

	private String prop3;

	private String prop4;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 20 , 0 , false , false),
		new SchemaColumn("ForumID", DataColumn.LONG, 2, 20 , 0 , true , false),
		new SchemaColumn("Subject", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("IPAddress", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("Type", DataColumn.STRING, 5, 2 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 6, 2 , 0 , false , false),
		new SchemaColumn("LastPost", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("LastPoster", DataColumn.STRING, 8, 100 , 0 , false , false),
		new SchemaColumn("ViewCount", DataColumn.INTEGER, 9, 11 , 0 , false , false),
		new SchemaColumn("ReplyCount", DataColumn.INTEGER, 10, 11 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 11, 20 , 0 , true , false),
		new SchemaColumn("VerifyFlag", DataColumn.STRING, 12, 2 , 0 , false , false),
		new SchemaColumn("TopTheme", DataColumn.STRING, 13, 2 , 0 , false , false),
		new SchemaColumn("Best", DataColumn.STRING, 14, 2 , 0 , false , false),
		new SchemaColumn("Bright", DataColumn.STRING, 15, 100 , 0 , false , false),
		new SchemaColumn("Applydel", DataColumn.STRING, 16, 2 , 0 , false , false),
		new SchemaColumn("LastPostTime", DataColumn.DATETIME, 17, 0 , 0 , false , false),
		new SchemaColumn("OrderTime", DataColumn.DATETIME, 18, 0 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 19, 50 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 20, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 21, 50 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 22, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 23, 100 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 24, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 25, 100 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 26, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 27, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 28, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 29, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 30, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCTheme";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into BZCTheme values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCTheme set ID=?,SiteID=?,ForumID=?,Subject=?,IPAddress=?,Type=?,Status=?,LastPost=?,LastPoster=?,ViewCount=?,ReplyCount=?,OrderFlag=?,VerifyFlag=?,TopTheme=?,Best=?,Bright=?,Applydel=?,LastPostTime=?,OrderTime=?,prop1=?,prop2=?,prop3=?,prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCTheme  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCTheme  where ID=? and BackupNo=?";

	public BZCThemeSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[31];
	}

	protected Schema newInstance(){
		return new BZCThemeSchema();
	}

	protected SchemaSet newSet(){
		return new BZCThemeSet();
	}

	public BZCThemeSet query() {
		return query(null, -1, -1);
	}

	public BZCThemeSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCThemeSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCThemeSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCThemeSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){ForumID = null;}else{ForumID = new Long(v.toString());}return;}
		if (i == 3){Subject = (String)v;return;}
		if (i == 4){IPAddress = (String)v;return;}
		if (i == 5){Type = (String)v;return;}
		if (i == 6){Status = (String)v;return;}
		if (i == 7){LastPost = (String)v;return;}
		if (i == 8){LastPoster = (String)v;return;}
		if (i == 9){if(v==null){ViewCount = null;}else{ViewCount = new Integer(v.toString());}return;}
		if (i == 10){if(v==null){ReplyCount = null;}else{ReplyCount = new Integer(v.toString());}return;}
		if (i == 11){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 12){VerifyFlag = (String)v;return;}
		if (i == 13){TopTheme = (String)v;return;}
		if (i == 14){Best = (String)v;return;}
		if (i == 15){Bright = (String)v;return;}
		if (i == 16){Applydel = (String)v;return;}
		if (i == 17){LastPostTime = (Date)v;return;}
		if (i == 18){OrderTime = (Date)v;return;}
		if (i == 19){prop1 = (String)v;return;}
		if (i == 20){prop2 = (String)v;return;}
		if (i == 21){prop3 = (String)v;return;}
		if (i == 22){prop4 = (String)v;return;}
		if (i == 23){AddUser = (String)v;return;}
		if (i == 24){AddTime = (Date)v;return;}
		if (i == 25){ModifyUser = (String)v;return;}
		if (i == 26){ModifyTime = (Date)v;return;}
		if (i == 27){BackupNo = (String)v;return;}
		if (i == 28){BackupOperator = (String)v;return;}
		if (i == 29){BackupTime = (Date)v;return;}
		if (i == 30){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return ForumID;}
		if (i == 3){return Subject;}
		if (i == 4){return IPAddress;}
		if (i == 5){return Type;}
		if (i == 6){return Status;}
		if (i == 7){return LastPost;}
		if (i == 8){return LastPoster;}
		if (i == 9){return ViewCount;}
		if (i == 10){return ReplyCount;}
		if (i == 11){return OrderFlag;}
		if (i == 12){return VerifyFlag;}
		if (i == 13){return TopTheme;}
		if (i == 14){return Best;}
		if (i == 15){return Bright;}
		if (i == 16){return Applydel;}
		if (i == 17){return LastPostTime;}
		if (i == 18){return OrderTime;}
		if (i == 19){return prop1;}
		if (i == 20){return prop2;}
		if (i == 21){return prop3;}
		if (i == 22){return prop4;}
		if (i == 23){return AddUser;}
		if (i == 24){return AddTime;}
		if (i == 25){return ModifyUser;}
		if (i == 26){return ModifyTime;}
		if (i == 27){return BackupNo;}
		if (i == 28){return BackupOperator;}
		if (i == 29){return BackupTime;}
		if (i == 30){return BackupMemo;}
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
	* 获取字段ForumID的值，该字段的<br>
	* 字段名称 :ForumID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getForumID() {
		if(ForumID==null){return 0;}
		return ForumID.longValue();
	}

	/**
	* 设置字段ForumID的值，该字段的<br>
	* 字段名称 :ForumID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setForumID(long forumID) {
		this.ForumID = new Long(forumID);
    }

	/**
	* 设置字段ForumID的值，该字段的<br>
	* 字段名称 :ForumID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setForumID(String forumID) {
		if (forumID == null){
			this.ForumID = null;
			return;
		}
		this.ForumID = new Long(forumID);
    }

	/**
	* 获取字段Subject的值，该字段的<br>
	* 字段名称 :Subject<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSubject() {
		return Subject;
	}

	/**
	* 设置字段Subject的值，该字段的<br>
	* 字段名称 :Subject<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSubject(String subject) {
		this.Subject = subject;
    }

	/**
	* 获取字段IPAddress的值，该字段的<br>
	* 字段名称 :IPAddress<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIPAddress() {
		return IPAddress;
	}

	/**
	* 设置字段IPAddress的值，该字段的<br>
	* 字段名称 :IPAddress<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIPAddress(String iPAddress) {
		this.IPAddress = iPAddress;
    }

	/**
	* 获取字段Type的值，该字段的<br>
	* 字段名称 :Type<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* 设置字段Type的值，该字段的<br>
	* 字段名称 :Type<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* 获取字段Status的值，该字段的<br>
	* 字段名称 :Status<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* 设置字段Status的值，该字段的<br>
	* 字段名称 :Status<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
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
	* 获取字段ViewCount的值，该字段的<br>
	* 字段名称 :ViewCount<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getViewCount() {
		if(ViewCount==null){return 0;}
		return ViewCount.intValue();
	}

	/**
	* 设置字段ViewCount的值，该字段的<br>
	* 字段名称 :ViewCount<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setViewCount(int viewCount) {
		this.ViewCount = new Integer(viewCount);
    }

	/**
	* 设置字段ViewCount的值，该字段的<br>
	* 字段名称 :ViewCount<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setViewCount(String viewCount) {
		if (viewCount == null){
			this.ViewCount = null;
			return;
		}
		this.ViewCount = new Integer(viewCount);
    }

	/**
	* 获取字段ReplyCount的值，该字段的<br>
	* 字段名称 :ReplyCount<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getReplyCount() {
		if(ReplyCount==null){return 0;}
		return ReplyCount.intValue();
	}

	/**
	* 设置字段ReplyCount的值，该字段的<br>
	* 字段名称 :ReplyCount<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReplyCount(int replyCount) {
		this.ReplyCount = new Integer(replyCount);
    }

	/**
	* 设置字段ReplyCount的值，该字段的<br>
	* 字段名称 :ReplyCount<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReplyCount(String replyCount) {
		if (replyCount == null){
			this.ReplyCount = null;
			return;
		}
		this.ReplyCount = new Integer(replyCount);
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
	* 获取字段VerifyFlag的值，该字段的<br>
	* 字段名称 :VerifyFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getVerifyFlag() {
		return VerifyFlag;
	}

	/**
	* 设置字段VerifyFlag的值，该字段的<br>
	* 字段名称 :VerifyFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setVerifyFlag(String verifyFlag) {
		this.VerifyFlag = verifyFlag;
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
	* 获取字段Best的值，该字段的<br>
	* 字段名称 :Best<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBest() {
		return Best;
	}

	/**
	* 设置字段Best的值，该字段的<br>
	* 字段名称 :Best<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBest(String best) {
		this.Best = best;
    }

	/**
	* 获取字段Bright的值，该字段的<br>
	* 字段名称 :Bright<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBright() {
		return Bright;
	}

	/**
	* 设置字段Bright的值，该字段的<br>
	* 字段名称 :Bright<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBright(String bright) {
		this.Bright = bright;
    }

	/**
	* 获取字段Applydel的值，该字段的<br>
	* 字段名称 :Applydel<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getApplydel() {
		return Applydel;
	}

	/**
	* 设置字段Applydel的值，该字段的<br>
	* 字段名称 :Applydel<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setApplydel(String applydel) {
		this.Applydel = applydel;
    }

	/**
	* 获取字段LastPostTime的值，该字段的<br>
	* 字段名称 :LastPostTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getLastPostTime() {
		return LastPostTime;
	}

	/**
	* 设置字段LastPostTime的值，该字段的<br>
	* 字段名称 :LastPostTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLastPostTime(Date lastPostTime) {
		this.LastPostTime = lastPostTime;
    }

	/**
	* 获取字段OrderTime的值，该字段的<br>
	* 字段名称 :OrderTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getOrderTime() {
		return OrderTime;
	}

	/**
	* 设置字段OrderTime的值，该字段的<br>
	* 字段名称 :OrderTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderTime(Date orderTime) {
		this.OrderTime = orderTime;
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