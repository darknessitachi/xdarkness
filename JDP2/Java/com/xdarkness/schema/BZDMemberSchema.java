package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZDMember备份<br>
 * 表代码：BZDMember<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：UserName, BackupNo<br>
 */
public class BZDMemberSchema extends Schema {
	private String UserName;

	private String Password;

	private String Name;

	private String Email;

	private String Gender;

	private String Type;

	private Long SiteID;

	private String Logo;

	private String Status;

	private String Score;

	private String Rank;

	private String MemberLevel;

	private String PWQuestion;

	private String PWAnswer;

	private String LastLoginIP;

	private Date LastLoginTime;

	private Date RegTime;

	private String RegIP;

	private String LoginMD5;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String Prop5;

	private String Prop6;

	private String Prop7;

	private String Prop8;

	private String Prop9;

	private String Prop10;

	private String Prop11;

	private String Prop12;

	private String Prop13;

	private String Prop14;

	private String Prop15;

	private String Prop16;

	private String Prop17;

	private String Prop18;

	private String Prop19;

	private String Prop20;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("UserName", DataColumn.STRING, 0, 50 , 0 , true , true),
		new SchemaColumn("Password", DataColumn.STRING, 1, 32 , 0 , true , false),
		new SchemaColumn("Name", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("Email", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("Gender", DataColumn.STRING, 4, 1 , 0 , false , false),
		new SchemaColumn("Type", DataColumn.STRING, 5, 10 , 0 , false , false),
		new SchemaColumn("SiteID", DataColumn.LONG, 6, 20 , 0 , false , false),
		new SchemaColumn("Logo", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 8, 1 , 0 , true , false),
		new SchemaColumn("Score", DataColumn.STRING, 9, 20 , 0 , false , false),
		new SchemaColumn("Rank", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("MemberLevel", DataColumn.STRING, 11, 10 , 0 , false , false),
		new SchemaColumn("PWQuestion", DataColumn.STRING, 12, 100 , 0 , false , false),
		new SchemaColumn("PWAnswer", DataColumn.STRING, 13, 100 , 0 , false , false),
		new SchemaColumn("LastLoginIP", DataColumn.STRING, 14, 16 , 0 , false , false),
		new SchemaColumn("LastLoginTime", DataColumn.DATETIME, 15, 0 , 0 , false , false),
		new SchemaColumn("RegTime", DataColumn.DATETIME, 16, 0 , 0 , false , false),
		new SchemaColumn("RegIP", DataColumn.STRING, 17, 16 , 0 , false , false),
		new SchemaColumn("LoginMD5", DataColumn.STRING, 18, 32 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 19, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 20, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 21, 100 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 22, 100 , 0 , false , false),
		new SchemaColumn("Prop5", DataColumn.STRING, 23, 100 , 0 , false , false),
		new SchemaColumn("Prop6", DataColumn.STRING, 24, 100 , 0 , false , false),
		new SchemaColumn("Prop7", DataColumn.STRING, 25, 100 , 0 , false , false),
		new SchemaColumn("Prop8", DataColumn.STRING, 26, 100 , 0 , false , false),
		new SchemaColumn("Prop9", DataColumn.STRING, 27, 100 , 0 , false , false),
		new SchemaColumn("Prop10", DataColumn.STRING, 28, 100 , 0 , false , false),
		new SchemaColumn("Prop11", DataColumn.STRING, 29, 100 , 0 , false , false),
		new SchemaColumn("Prop12", DataColumn.STRING, 30, 100 , 0 , false , false),
		new SchemaColumn("Prop13", DataColumn.STRING, 31, 100 , 0 , false , false),
		new SchemaColumn("Prop14", DataColumn.STRING, 32, 100 , 0 , false , false),
		new SchemaColumn("Prop15", DataColumn.STRING, 33, 100 , 0 , false , false),
		new SchemaColumn("Prop16", DataColumn.STRING, 34, 100 , 0 , false , false),
		new SchemaColumn("Prop17", DataColumn.STRING, 35, 100 , 0 , false , false),
		new SchemaColumn("Prop18", DataColumn.STRING, 36, 100 , 0 , false , false),
		new SchemaColumn("Prop19", DataColumn.STRING, 37, 100 , 0 , false , false),
		new SchemaColumn("Prop20", DataColumn.STRING, 38, 100 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 39, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 40, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 41, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 42, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZDMember";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into BZDMember values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZDMember set UserName=?,Password=?,Name=?,Email=?,Gender=?,Type=?,SiteID=?,Logo=?,Status=?,Score=?,Rank=?,MemberLevel=?,PWQuestion=?,PWAnswer=?,LastLoginIP=?,LastLoginTime=?,RegTime=?,RegIP=?,LoginMD5=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Prop5=?,Prop6=?,Prop7=?,Prop8=?,Prop9=?,Prop10=?,Prop11=?,Prop12=?,Prop13=?,Prop14=?,Prop15=?,Prop16=?,Prop17=?,Prop18=?,Prop19=?,Prop20=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where UserName=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZDMember  where UserName=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZDMember  where UserName=? and BackupNo=?";

	public BZDMemberSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[43];
	}

	protected Schema newInstance(){
		return new BZDMemberSchema();
	}

	protected SchemaSet newSet(){
		return new BZDMemberSet();
	}

	public BZDMemberSet query() {
		return query(null, -1, -1);
	}

	public BZDMemberSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZDMemberSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZDMemberSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZDMemberSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){UserName = (String)v;return;}
		if (i == 1){Password = (String)v;return;}
		if (i == 2){Name = (String)v;return;}
		if (i == 3){Email = (String)v;return;}
		if (i == 4){Gender = (String)v;return;}
		if (i == 5){Type = (String)v;return;}
		if (i == 6){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 7){Logo = (String)v;return;}
		if (i == 8){Status = (String)v;return;}
		if (i == 9){Score = (String)v;return;}
		if (i == 10){Rank = (String)v;return;}
		if (i == 11){MemberLevel = (String)v;return;}
		if (i == 12){PWQuestion = (String)v;return;}
		if (i == 13){PWAnswer = (String)v;return;}
		if (i == 14){LastLoginIP = (String)v;return;}
		if (i == 15){LastLoginTime = (Date)v;return;}
		if (i == 16){RegTime = (Date)v;return;}
		if (i == 17){RegIP = (String)v;return;}
		if (i == 18){LoginMD5 = (String)v;return;}
		if (i == 19){Prop1 = (String)v;return;}
		if (i == 20){Prop2 = (String)v;return;}
		if (i == 21){Prop3 = (String)v;return;}
		if (i == 22){Prop4 = (String)v;return;}
		if (i == 23){Prop5 = (String)v;return;}
		if (i == 24){Prop6 = (String)v;return;}
		if (i == 25){Prop7 = (String)v;return;}
		if (i == 26){Prop8 = (String)v;return;}
		if (i == 27){Prop9 = (String)v;return;}
		if (i == 28){Prop10 = (String)v;return;}
		if (i == 29){Prop11 = (String)v;return;}
		if (i == 30){Prop12 = (String)v;return;}
		if (i == 31){Prop13 = (String)v;return;}
		if (i == 32){Prop14 = (String)v;return;}
		if (i == 33){Prop15 = (String)v;return;}
		if (i == 34){Prop16 = (String)v;return;}
		if (i == 35){Prop17 = (String)v;return;}
		if (i == 36){Prop18 = (String)v;return;}
		if (i == 37){Prop19 = (String)v;return;}
		if (i == 38){Prop20 = (String)v;return;}
		if (i == 39){BackupNo = (String)v;return;}
		if (i == 40){BackupOperator = (String)v;return;}
		if (i == 41){BackupTime = (Date)v;return;}
		if (i == 42){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return UserName;}
		if (i == 1){return Password;}
		if (i == 2){return Name;}
		if (i == 3){return Email;}
		if (i == 4){return Gender;}
		if (i == 5){return Type;}
		if (i == 6){return SiteID;}
		if (i == 7){return Logo;}
		if (i == 8){return Status;}
		if (i == 9){return Score;}
		if (i == 10){return Rank;}
		if (i == 11){return MemberLevel;}
		if (i == 12){return PWQuestion;}
		if (i == 13){return PWAnswer;}
		if (i == 14){return LastLoginIP;}
		if (i == 15){return LastLoginTime;}
		if (i == 16){return RegTime;}
		if (i == 17){return RegIP;}
		if (i == 18){return LoginMD5;}
		if (i == 19){return Prop1;}
		if (i == 20){return Prop2;}
		if (i == 21){return Prop3;}
		if (i == 22){return Prop4;}
		if (i == 23){return Prop5;}
		if (i == 24){return Prop6;}
		if (i == 25){return Prop7;}
		if (i == 26){return Prop8;}
		if (i == 27){return Prop9;}
		if (i == 28){return Prop10;}
		if (i == 29){return Prop11;}
		if (i == 30){return Prop12;}
		if (i == 31){return Prop13;}
		if (i == 32){return Prop14;}
		if (i == 33){return Prop15;}
		if (i == 34){return Prop16;}
		if (i == 35){return Prop17;}
		if (i == 36){return Prop18;}
		if (i == 37){return Prop19;}
		if (i == 38){return Prop20;}
		if (i == 39){return BackupNo;}
		if (i == 40){return BackupOperator;}
		if (i == 41){return BackupTime;}
		if (i == 42){return BackupMemo;}
		return null;
	}

	/**
	* 获取字段UserName的值，该字段的<br>
	* 字段名称 :UserName<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getUserName() {
		return UserName;
	}

	/**
	* 设置字段UserName的值，该字段的<br>
	* 字段名称 :UserName<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setUserName(String userName) {
		this.UserName = userName;
    }

	/**
	* 获取字段Password的值，该字段的<br>
	* 字段名称 :Password<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getPassword() {
		return Password;
	}

	/**
	* 设置字段Password的值，该字段的<br>
	* 字段名称 :Password<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setPassword(String password) {
		this.Password = password;
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
	* 获取字段Email的值，该字段的<br>
	* 字段名称 :Email<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getEmail() {
		return Email;
	}

	/**
	* 设置字段Email的值，该字段的<br>
	* 字段名称 :Email<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setEmail(String email) {
		this.Email = email;
    }

	/**
	* 获取字段Gender的值，该字段的<br>
	* 字段名称 :Gender<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getGender() {
		return Gender;
	}

	/**
	* 设置字段Gender的值，该字段的<br>
	* 字段名称 :Gender<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setGender(String gender) {
		this.Gender = gender;
    }

	/**
	* 获取字段Type的值，该字段的<br>
	* 字段名称 :Type<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* 设置字段Type的值，该字段的<br>
	* 字段名称 :Type<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setType(String type) {
		this.Type = type;
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
	* 获取字段Logo的值，该字段的<br>
	* 字段名称 :Logo<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLogo() {
		return Logo;
	}

	/**
	* 设置字段Logo的值，该字段的<br>
	* 字段名称 :Logo<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLogo(String logo) {
		this.Logo = logo;
    }

	/**
	* 获取字段Status的值，该字段的<br>
	* 字段名称 :Status<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* 设置字段Status的值，该字段的<br>
	* 字段名称 :Status<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
    }

	/**
	* 获取字段Score的值，该字段的<br>
	* 字段名称 :Score<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getScore() {
		return Score;
	}

	/**
	* 设置字段Score的值，该字段的<br>
	* 字段名称 :Score<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setScore(String score) {
		this.Score = score;
    }

	/**
	* 获取字段Rank的值，该字段的<br>
	* 字段名称 :Rank<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRank() {
		return Rank;
	}

	/**
	* 设置字段Rank的值，该字段的<br>
	* 字段名称 :Rank<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRank(String rank) {
		this.Rank = rank;
    }

	/**
	* 获取字段MemberLevel的值，该字段的<br>
	* 字段名称 :MemberLevel<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemberLevel() {
		return MemberLevel;
	}

	/**
	* 设置字段MemberLevel的值，该字段的<br>
	* 字段名称 :MemberLevel<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemberLevel(String memberLevel) {
		this.MemberLevel = memberLevel;
    }

	/**
	* 获取字段PWQuestion的值，该字段的<br>
	* 字段名称 :PWQuestion<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPWQuestion() {
		return PWQuestion;
	}

	/**
	* 设置字段PWQuestion的值，该字段的<br>
	* 字段名称 :PWQuestion<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPWQuestion(String pWQuestion) {
		this.PWQuestion = pWQuestion;
    }

	/**
	* 获取字段PWAnswer的值，该字段的<br>
	* 字段名称 :PWAnswer<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPWAnswer() {
		return PWAnswer;
	}

	/**
	* 设置字段PWAnswer的值，该字段的<br>
	* 字段名称 :PWAnswer<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPWAnswer(String pWAnswer) {
		this.PWAnswer = pWAnswer;
    }

	/**
	* 获取字段LastLoginIP的值，该字段的<br>
	* 字段名称 :LastLoginIP<br>
	* 数据类型 :varchar(16)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLastLoginIP() {
		return LastLoginIP;
	}

	/**
	* 设置字段LastLoginIP的值，该字段的<br>
	* 字段名称 :LastLoginIP<br>
	* 数据类型 :varchar(16)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLastLoginIP(String lastLoginIP) {
		this.LastLoginIP = lastLoginIP;
    }

	/**
	* 获取字段LastLoginTime的值，该字段的<br>
	* 字段名称 :LastLoginTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getLastLoginTime() {
		return LastLoginTime;
	}

	/**
	* 设置字段LastLoginTime的值，该字段的<br>
	* 字段名称 :LastLoginTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLastLoginTime(Date lastLoginTime) {
		this.LastLoginTime = lastLoginTime;
    }

	/**
	* 获取字段RegTime的值，该字段的<br>
	* 字段名称 :RegTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getRegTime() {
		return RegTime;
	}

	/**
	* 设置字段RegTime的值，该字段的<br>
	* 字段名称 :RegTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRegTime(Date regTime) {
		this.RegTime = regTime;
    }

	/**
	* 获取字段RegIP的值，该字段的<br>
	* 字段名称 :RegIP<br>
	* 数据类型 :varchar(16)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRegIP() {
		return RegIP;
	}

	/**
	* 设置字段RegIP的值，该字段的<br>
	* 字段名称 :RegIP<br>
	* 数据类型 :varchar(16)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRegIP(String regIP) {
		this.RegIP = regIP;
    }

	/**
	* 获取字段LoginMD5的值，该字段的<br>
	* 字段名称 :LoginMD5<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLoginMD5() {
		return LoginMD5;
	}

	/**
	* 设置字段LoginMD5的值，该字段的<br>
	* 字段名称 :LoginMD5<br>
	* 数据类型 :varchar(32)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLoginMD5(String loginMD5) {
		this.LoginMD5 = loginMD5;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* 获取字段Prop5的值，该字段的<br>
	* 字段名称 :Prop5<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp5() {
		return Prop5;
	}

	/**
	* 设置字段Prop5的值，该字段的<br>
	* 字段名称 :Prop5<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp5(String prop5) {
		this.Prop5 = prop5;
    }

	/**
	* 获取字段Prop6的值，该字段的<br>
	* 字段名称 :Prop6<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp6() {
		return Prop6;
	}

	/**
	* 设置字段Prop6的值，该字段的<br>
	* 字段名称 :Prop6<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp6(String prop6) {
		this.Prop6 = prop6;
    }

	/**
	* 获取字段Prop7的值，该字段的<br>
	* 字段名称 :Prop7<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp7() {
		return Prop7;
	}

	/**
	* 设置字段Prop7的值，该字段的<br>
	* 字段名称 :Prop7<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp7(String prop7) {
		this.Prop7 = prop7;
    }

	/**
	* 获取字段Prop8的值，该字段的<br>
	* 字段名称 :Prop8<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp8() {
		return Prop8;
	}

	/**
	* 设置字段Prop8的值，该字段的<br>
	* 字段名称 :Prop8<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp8(String prop8) {
		this.Prop8 = prop8;
    }

	/**
	* 获取字段Prop9的值，该字段的<br>
	* 字段名称 :Prop9<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp9() {
		return Prop9;
	}

	/**
	* 设置字段Prop9的值，该字段的<br>
	* 字段名称 :Prop9<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp9(String prop9) {
		this.Prop9 = prop9;
    }

	/**
	* 获取字段Prop10的值，该字段的<br>
	* 字段名称 :Prop10<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp10() {
		return Prop10;
	}

	/**
	* 设置字段Prop10的值，该字段的<br>
	* 字段名称 :Prop10<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp10(String prop10) {
		this.Prop10 = prop10;
    }

	/**
	* 获取字段Prop11的值，该字段的<br>
	* 字段名称 :Prop11<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp11() {
		return Prop11;
	}

	/**
	* 设置字段Prop11的值，该字段的<br>
	* 字段名称 :Prop11<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp11(String prop11) {
		this.Prop11 = prop11;
    }

	/**
	* 获取字段Prop12的值，该字段的<br>
	* 字段名称 :Prop12<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp12() {
		return Prop12;
	}

	/**
	* 设置字段Prop12的值，该字段的<br>
	* 字段名称 :Prop12<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp12(String prop12) {
		this.Prop12 = prop12;
    }

	/**
	* 获取字段Prop13的值，该字段的<br>
	* 字段名称 :Prop13<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp13() {
		return Prop13;
	}

	/**
	* 设置字段Prop13的值，该字段的<br>
	* 字段名称 :Prop13<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp13(String prop13) {
		this.Prop13 = prop13;
    }

	/**
	* 获取字段Prop14的值，该字段的<br>
	* 字段名称 :Prop14<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp14() {
		return Prop14;
	}

	/**
	* 设置字段Prop14的值，该字段的<br>
	* 字段名称 :Prop14<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp14(String prop14) {
		this.Prop14 = prop14;
    }

	/**
	* 获取字段Prop15的值，该字段的<br>
	* 字段名称 :Prop15<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp15() {
		return Prop15;
	}

	/**
	* 设置字段Prop15的值，该字段的<br>
	* 字段名称 :Prop15<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp15(String prop15) {
		this.Prop15 = prop15;
    }

	/**
	* 获取字段Prop16的值，该字段的<br>
	* 字段名称 :Prop16<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp16() {
		return Prop16;
	}

	/**
	* 设置字段Prop16的值，该字段的<br>
	* 字段名称 :Prop16<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp16(String prop16) {
		this.Prop16 = prop16;
    }

	/**
	* 获取字段Prop17的值，该字段的<br>
	* 字段名称 :Prop17<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp17() {
		return Prop17;
	}

	/**
	* 设置字段Prop17的值，该字段的<br>
	* 字段名称 :Prop17<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp17(String prop17) {
		this.Prop17 = prop17;
    }

	/**
	* 获取字段Prop18的值，该字段的<br>
	* 字段名称 :Prop18<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp18() {
		return Prop18;
	}

	/**
	* 设置字段Prop18的值，该字段的<br>
	* 字段名称 :Prop18<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp18(String prop18) {
		this.Prop18 = prop18;
    }

	/**
	* 获取字段Prop19的值，该字段的<br>
	* 字段名称 :Prop19<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp19() {
		return Prop19;
	}

	/**
	* 设置字段Prop19的值，该字段的<br>
	* 字段名称 :Prop19<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp19(String prop19) {
		this.Prop19 = prop19;
    }

	/**
	* 获取字段Prop20的值，该字段的<br>
	* 字段名称 :Prop20<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp20() {
		return Prop20;
	}

	/**
	* 设置字段Prop20的值，该字段的<br>
	* 字段名称 :Prop20<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp20(String prop20) {
		this.Prop20 = prop20;
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