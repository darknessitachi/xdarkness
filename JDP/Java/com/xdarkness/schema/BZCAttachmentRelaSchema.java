package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCAttachmentRela备份<br>
 * 表代码：BZCAttachmentRela<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID, RelaID, RelaType, BackupNo<br>
 */
public class BZCAttachmentRelaSchema extends Schema {
	private Long ID;

	private Long RelaID;

	private String RelaType;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("RelaID", DataColumn.LONG, 1, 20 , 0 , true , true),
		new SchemaColumn("RelaType", DataColumn.STRING, 2, 20 , 0 , true , true),
		new SchemaColumn("BackupNo", DataColumn.STRING, 3, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 4, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 5, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 6, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCAttachmentRela";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into BZCAttachmentRela values(?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCAttachmentRela set ID=?,RelaID=?,RelaType=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and RelaID=? and RelaType=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCAttachmentRela  where ID=? and RelaID=? and RelaType=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCAttachmentRela  where ID=? and RelaID=? and RelaType=? and BackupNo=?";

	public BZCAttachmentRelaSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[7];
	}

	protected Schema newInstance(){
		return new BZCAttachmentRelaSchema();
	}

	protected SchemaSet newSet(){
		return new BZCAttachmentRelaSet();
	}

	public BZCAttachmentRelaSet query() {
		return query(null, -1, -1);
	}

	public BZCAttachmentRelaSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCAttachmentRelaSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCAttachmentRelaSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCAttachmentRelaSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){RelaID = null;}else{RelaID = new Long(v.toString());}return;}
		if (i == 2){RelaType = (String)v;return;}
		if (i == 3){BackupNo = (String)v;return;}
		if (i == 4){BackupOperator = (String)v;return;}
		if (i == 5){BackupTime = (Date)v;return;}
		if (i == 6){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return RelaID;}
		if (i == 2){return RelaType;}
		if (i == 3){return BackupNo;}
		if (i == 4){return BackupOperator;}
		if (i == 5){return BackupTime;}
		if (i == 6){return BackupMemo;}
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
	* 获取字段RelaID的值，该字段的<br>
	* 字段名称 :RelaID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getRelaID() {
		if(RelaID==null){return 0;}
		return RelaID.longValue();
	}

	/**
	* 设置字段RelaID的值，该字段的<br>
	* 字段名称 :RelaID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setRelaID(long relaID) {
		this.RelaID = new Long(relaID);
    }

	/**
	* 设置字段RelaID的值，该字段的<br>
	* 字段名称 :RelaID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setRelaID(String relaID) {
		if (relaID == null){
			this.RelaID = null;
			return;
		}
		this.RelaID = new Long(relaID);
    }

	/**
	* 获取字段RelaType的值，该字段的<br>
	* 字段名称 :RelaType<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getRelaType() {
		return RelaType;
	}

	/**
	* 设置字段RelaType的值，该字段的<br>
	* 字段名称 :RelaType<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setRelaType(String relaType) {
		this.RelaType = relaType;
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