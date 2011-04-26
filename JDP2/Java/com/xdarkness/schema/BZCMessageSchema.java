package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCMessage备份<br>
 * 表代码：BZCMessage<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZCMessageSchema extends Schema {
	private Long ID;

	private String FromUser;

	private String ToUser;

	private String Box;

	private Long ReadFlag;

	private Long PopFlag;

	private String Subject;

	private String Content;

	private Date AddTime;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("FromUser", DataColumn.STRING, 1, 50 , 0 , false , false),
		new SchemaColumn("ToUser", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("Box", DataColumn.STRING, 3, 10 , 0 , false , false),
		new SchemaColumn("ReadFlag", DataColumn.LONG, 4, 20 , 0 , false , false),
		new SchemaColumn("PopFlag", DataColumn.LONG, 5, 20 , 0 , false , false),
		new SchemaColumn("Subject", DataColumn.STRING, 6, 500 , 0 , false , false),
		new SchemaColumn("Content", DataColumn.CLOB, 7, 0 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 9, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 10, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 11, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 12, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCMessage";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into BZCMessage values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCMessage set ID=?,FromUser=?,ToUser=?,Box=?,ReadFlag=?,PopFlag=?,Subject=?,Content=?,AddTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCMessage  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCMessage  where ID=? and BackupNo=?";

	public BZCMessageSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[13];
	}

	protected Schema newInstance(){
		return new BZCMessageSchema();
	}

	protected SchemaSet newSet(){
		return new BZCMessageSet();
	}

	public BZCMessageSet query() {
		return query(null, -1, -1);
	}

	public BZCMessageSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCMessageSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCMessageSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCMessageSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){FromUser = (String)v;return;}
		if (i == 2){ToUser = (String)v;return;}
		if (i == 3){Box = (String)v;return;}
		if (i == 4){if(v==null){ReadFlag = null;}else{ReadFlag = new Long(v.toString());}return;}
		if (i == 5){if(v==null){PopFlag = null;}else{PopFlag = new Long(v.toString());}return;}
		if (i == 6){Subject = (String)v;return;}
		if (i == 7){Content = (String)v;return;}
		if (i == 8){AddTime = (Date)v;return;}
		if (i == 9){BackupNo = (String)v;return;}
		if (i == 10){BackupOperator = (String)v;return;}
		if (i == 11){BackupTime = (Date)v;return;}
		if (i == 12){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return FromUser;}
		if (i == 2){return ToUser;}
		if (i == 3){return Box;}
		if (i == 4){return ReadFlag;}
		if (i == 5){return PopFlag;}
		if (i == 6){return Subject;}
		if (i == 7){return Content;}
		if (i == 8){return AddTime;}
		if (i == 9){return BackupNo;}
		if (i == 10){return BackupOperator;}
		if (i == 11){return BackupTime;}
		if (i == 12){return BackupMemo;}
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
	* 获取字段FromUser的值，该字段的<br>
	* 字段名称 :FromUser<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFromUser() {
		return FromUser;
	}

	/**
	* 设置字段FromUser的值，该字段的<br>
	* 字段名称 :FromUser<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFromUser(String fromUser) {
		this.FromUser = fromUser;
    }

	/**
	* 获取字段ToUser的值，该字段的<br>
	* 字段名称 :ToUser<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getToUser() {
		return ToUser;
	}

	/**
	* 设置字段ToUser的值，该字段的<br>
	* 字段名称 :ToUser<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setToUser(String toUser) {
		this.ToUser = toUser;
    }

	/**
	* 获取字段Box的值，该字段的<br>
	* 字段名称 :Box<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBox() {
		return Box;
	}

	/**
	* 设置字段Box的值，该字段的<br>
	* 字段名称 :Box<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBox(String box) {
		this.Box = box;
    }

	/**
	* 获取字段ReadFlag的值，该字段的<br>
	* 字段名称 :ReadFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getReadFlag() {
		if(ReadFlag==null){return 0;}
		return ReadFlag.longValue();
	}

	/**
	* 设置字段ReadFlag的值，该字段的<br>
	* 字段名称 :ReadFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReadFlag(long readFlag) {
		this.ReadFlag = new Long(readFlag);
    }

	/**
	* 设置字段ReadFlag的值，该字段的<br>
	* 字段名称 :ReadFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReadFlag(String readFlag) {
		if (readFlag == null){
			this.ReadFlag = null;
			return;
		}
		this.ReadFlag = new Long(readFlag);
    }

	/**
	* 获取字段PopFlag的值，该字段的<br>
	* 字段名称 :PopFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getPopFlag() {
		if(PopFlag==null){return 0;}
		return PopFlag.longValue();
	}

	/**
	* 设置字段PopFlag的值，该字段的<br>
	* 字段名称 :PopFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPopFlag(long popFlag) {
		this.PopFlag = new Long(popFlag);
    }

	/**
	* 设置字段PopFlag的值，该字段的<br>
	* 字段名称 :PopFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPopFlag(String popFlag) {
		if (popFlag == null){
			this.PopFlag = null;
			return;
		}
		this.PopFlag = new Long(popFlag);
    }

	/**
	* 获取字段Subject的值，该字段的<br>
	* 字段名称 :Subject<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSubject() {
		return Subject;
	}

	/**
	* 设置字段Subject的值，该字段的<br>
	* 字段名称 :Subject<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSubject(String subject) {
		this.Subject = subject;
    }

	/**
	* 获取字段Content的值，该字段的<br>
	* 字段名称 :Content<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getContent() {
		return Content;
	}

	/**
	* 设置字段Content的值，该字段的<br>
	* 字段名称 :Content<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setContent(String content) {
		this.Content = content;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
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