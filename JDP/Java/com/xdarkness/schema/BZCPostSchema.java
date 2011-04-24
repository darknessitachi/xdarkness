package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCPost备份<br>
 * 表代码：BZCPost<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZCPostSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private Long ForumID;

	private Long ThemeID;

	private String First;

	private String Subject;

	private String Message;

	private String IP;

	private String Status;

	private String IsHidden;

	private String Invisible;

	private String VerifyFlag;

	private Long Layer;

	private String ApplyDel;

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
		new SchemaColumn("ThemeID", DataColumn.LONG, 3, 20 , 0 , true , false),
		new SchemaColumn("First", DataColumn.STRING, 4, 2 , 0 , false , false),
		new SchemaColumn("Subject", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("Message", DataColumn.CLOB, 6, 0 , 0 , false , false),
		new SchemaColumn("IP", DataColumn.STRING, 7, 20 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 8, 2 , 0 , false , false),
		new SchemaColumn("IsHidden", DataColumn.STRING, 9, 2 , 0 , false , false),
		new SchemaColumn("Invisible", DataColumn.STRING, 10, 2 , 0 , false , false),
		new SchemaColumn("VerifyFlag", DataColumn.STRING, 11, 2 , 0 , false , false),
		new SchemaColumn("Layer", DataColumn.LONG, 12, 20 , 0 , false , false),
		new SchemaColumn("ApplyDel", DataColumn.STRING, 13, 2 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 15, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 16, 50 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 17, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 18, 100 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 19, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 20, 100 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 21, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 22, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 23, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 24, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 25, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCPost";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into BZCPost values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCPost set ID=?,SiteID=?,ForumID=?,ThemeID=?,First=?,Subject=?,Message=?,IP=?,Status=?,IsHidden=?,Invisible=?,VerifyFlag=?,Layer=?,ApplyDel=?,prop1=?,prop2=?,prop3=?,prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCPost  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCPost  where ID=? and BackupNo=?";

	public BZCPostSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[26];
	}

	protected Schema newInstance(){
		return new BZCPostSchema();
	}

	protected SchemaSet newSet(){
		return new BZCPostSet();
	}

	public BZCPostSet query() {
		return query(null, -1, -1);
	}

	public BZCPostSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCPostSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCPostSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCPostSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){ForumID = null;}else{ForumID = new Long(v.toString());}return;}
		if (i == 3){if(v==null){ThemeID = null;}else{ThemeID = new Long(v.toString());}return;}
		if (i == 4){First = (String)v;return;}
		if (i == 5){Subject = (String)v;return;}
		if (i == 6){Message = (String)v;return;}
		if (i == 7){IP = (String)v;return;}
		if (i == 8){Status = (String)v;return;}
		if (i == 9){IsHidden = (String)v;return;}
		if (i == 10){Invisible = (String)v;return;}
		if (i == 11){VerifyFlag = (String)v;return;}
		if (i == 12){if(v==null){Layer = null;}else{Layer = new Long(v.toString());}return;}
		if (i == 13){ApplyDel = (String)v;return;}
		if (i == 14){prop1 = (String)v;return;}
		if (i == 15){prop2 = (String)v;return;}
		if (i == 16){prop3 = (String)v;return;}
		if (i == 17){prop4 = (String)v;return;}
		if (i == 18){AddUser = (String)v;return;}
		if (i == 19){AddTime = (Date)v;return;}
		if (i == 20){ModifyUser = (String)v;return;}
		if (i == 21){ModifyTime = (Date)v;return;}
		if (i == 22){BackupNo = (String)v;return;}
		if (i == 23){BackupOperator = (String)v;return;}
		if (i == 24){BackupTime = (Date)v;return;}
		if (i == 25){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return ForumID;}
		if (i == 3){return ThemeID;}
		if (i == 4){return First;}
		if (i == 5){return Subject;}
		if (i == 6){return Message;}
		if (i == 7){return IP;}
		if (i == 8){return Status;}
		if (i == 9){return IsHidden;}
		if (i == 10){return Invisible;}
		if (i == 11){return VerifyFlag;}
		if (i == 12){return Layer;}
		if (i == 13){return ApplyDel;}
		if (i == 14){return prop1;}
		if (i == 15){return prop2;}
		if (i == 16){return prop3;}
		if (i == 17){return prop4;}
		if (i == 18){return AddUser;}
		if (i == 19){return AddTime;}
		if (i == 20){return ModifyUser;}
		if (i == 21){return ModifyTime;}
		if (i == 22){return BackupNo;}
		if (i == 23){return BackupOperator;}
		if (i == 24){return BackupTime;}
		if (i == 25){return BackupMemo;}
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
	* 获取字段ThemeID的值，该字段的<br>
	* 字段名称 :ThemeID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getThemeID() {
		if(ThemeID==null){return 0;}
		return ThemeID.longValue();
	}

	/**
	* 设置字段ThemeID的值，该字段的<br>
	* 字段名称 :ThemeID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setThemeID(long themeID) {
		this.ThemeID = new Long(themeID);
    }

	/**
	* 设置字段ThemeID的值，该字段的<br>
	* 字段名称 :ThemeID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setThemeID(String themeID) {
		if (themeID == null){
			this.ThemeID = null;
			return;
		}
		this.ThemeID = new Long(themeID);
    }

	/**
	* 获取字段First的值，该字段的<br>
	* 字段名称 :First<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFirst() {
		return First;
	}

	/**
	* 设置字段First的值，该字段的<br>
	* 字段名称 :First<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFirst(String first) {
		this.First = first;
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
	* 获取字段Message的值，该字段的<br>
	* 字段名称 :Message<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMessage() {
		return Message;
	}

	/**
	* 设置字段Message的值，该字段的<br>
	* 字段名称 :Message<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMessage(String message) {
		this.Message = message;
    }

	/**
	* 获取字段IP的值，该字段的<br>
	* 字段名称 :IP<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIP() {
		return IP;
	}

	/**
	* 设置字段IP的值，该字段的<br>
	* 字段名称 :IP<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIP(String iP) {
		this.IP = iP;
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
	* 获取字段IsHidden的值，该字段的<br>
	* 字段名称 :IsHidden<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIsHidden() {
		return IsHidden;
	}

	/**
	* 设置字段IsHidden的值，该字段的<br>
	* 字段名称 :IsHidden<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsHidden(String isHidden) {
		this.IsHidden = isHidden;
    }

	/**
	* 获取字段Invisible的值，该字段的<br>
	* 字段名称 :Invisible<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInvisible() {
		return Invisible;
	}

	/**
	* 设置字段Invisible的值，该字段的<br>
	* 字段名称 :Invisible<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInvisible(String invisible) {
		this.Invisible = invisible;
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
	* 获取字段Layer的值，该字段的<br>
	* 字段名称 :Layer<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getLayer() {
		if(Layer==null){return 0;}
		return Layer.longValue();
	}

	/**
	* 设置字段Layer的值，该字段的<br>
	* 字段名称 :Layer<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLayer(long layer) {
		this.Layer = new Long(layer);
    }

	/**
	* 设置字段Layer的值，该字段的<br>
	* 字段名称 :Layer<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLayer(String layer) {
		if (layer == null){
			this.Layer = null;
			return;
		}
		this.Layer = new Long(layer);
    }

	/**
	* 获取字段ApplyDel的值，该字段的<br>
	* 字段名称 :ApplyDel<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getApplyDel() {
		return ApplyDel;
	}

	/**
	* 设置字段ApplyDel的值，该字段的<br>
	* 字段名称 :ApplyDel<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setApplyDel(String applyDel) {
		this.ApplyDel = applyDel;
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