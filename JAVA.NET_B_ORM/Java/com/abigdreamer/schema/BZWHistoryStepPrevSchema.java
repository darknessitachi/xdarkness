package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZWHistoryStepPrev备份<br>
 * 表代码：BZWHistoryStepPrev<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID, PreviousID, BackupNo<br>
 */
public class BZWHistoryStepPrevSchema extends Schema {
	private Long ID;

	private Long PreviousID;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("PreviousID", DataColumn.LONG, 1, 20 , 0 , true , true),
		new SchemaColumn("BackupNo", DataColumn.STRING, 2, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 3, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 4, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 5, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZWHistoryStepPrev";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into BZWHistoryStepPrev values(?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZWHistoryStepPrev set ID=?,PreviousID=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and PreviousID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZWHistoryStepPrev  where ID=? and PreviousID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZWHistoryStepPrev  where ID=? and PreviousID=? and BackupNo=?";

	public BZWHistoryStepPrevSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[6];
	}

	protected Schema newInstance(){
		return new BZWHistoryStepPrevSchema();
	}

	protected SchemaSet newSet(){
		return new BZWHistoryStepPrevSet();
	}

	public BZWHistoryStepPrevSet query() {
		return query(null, -1, -1);
	}

	public BZWHistoryStepPrevSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZWHistoryStepPrevSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZWHistoryStepPrevSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZWHistoryStepPrevSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){PreviousID = null;}else{PreviousID = new Long(v.toString());}return;}
		if (i == 2){BackupNo = (String)v;return;}
		if (i == 3){BackupOperator = (String)v;return;}
		if (i == 4){BackupTime = (Date)v;return;}
		if (i == 5){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return PreviousID;}
		if (i == 2){return BackupNo;}
		if (i == 3){return BackupOperator;}
		if (i == 4){return BackupTime;}
		if (i == 5){return BackupMemo;}
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
	* 获取字段PreviousID的值，该字段的<br>
	* 字段名称 :PreviousID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getPreviousID() {
		if(PreviousID==null){return 0;}
		return PreviousID.longValue();
	}

	/**
	* 设置字段PreviousID的值，该字段的<br>
	* 字段名称 :PreviousID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setPreviousID(long previousID) {
		this.PreviousID = new Long(previousID);
    }

	/**
	* 设置字段PreviousID的值，该字段的<br>
	* 字段名称 :PreviousID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setPreviousID(String previousID) {
		if (previousID == null){
			this.PreviousID = null;
			return;
		}
		this.PreviousID = new Long(previousID);
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