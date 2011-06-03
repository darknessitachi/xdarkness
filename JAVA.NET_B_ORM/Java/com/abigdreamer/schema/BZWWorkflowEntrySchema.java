package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZWWorkflowEntry备份<br>
 * 表代码：BZWWorkflowEntry<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZWWorkflowEntrySchema extends Schema {
	private Long ID;

	private Long WorkflowDefID;

	private Integer State;

	private String Memo;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("WorkflowDefID", DataColumn.LONG, 1, 20 , 0 , true , false),
		new SchemaColumn("State", DataColumn.INTEGER, 2, 11 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 4, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 5, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 6, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 7, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZWWorkflowEntry";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into BZWWorkflowEntry values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZWWorkflowEntry set ID=?,WorkflowDefID=?,State=?,Memo=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZWWorkflowEntry  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZWWorkflowEntry  where ID=? and BackupNo=?";

	public BZWWorkflowEntrySchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[8];
	}

	protected Schema newInstance(){
		return new BZWWorkflowEntrySchema();
	}

	protected SchemaSet newSet(){
		return new BZWWorkflowEntrySet();
	}

	public BZWWorkflowEntrySet query() {
		return query(null, -1, -1);
	}

	public BZWWorkflowEntrySet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZWWorkflowEntrySet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZWWorkflowEntrySet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZWWorkflowEntrySet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){WorkflowDefID = null;}else{WorkflowDefID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){State = null;}else{State = new Integer(v.toString());}return;}
		if (i == 3){Memo = (String)v;return;}
		if (i == 4){BackupNo = (String)v;return;}
		if (i == 5){BackupOperator = (String)v;return;}
		if (i == 6){BackupTime = (Date)v;return;}
		if (i == 7){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return WorkflowDefID;}
		if (i == 2){return State;}
		if (i == 3){return Memo;}
		if (i == 4){return BackupNo;}
		if (i == 5){return BackupOperator;}
		if (i == 6){return BackupTime;}
		if (i == 7){return BackupMemo;}
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
	* 获取字段WorkflowDefID的值，该字段的<br>
	* 字段名称 :WorkflowDefID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getWorkflowDefID() {
		if(WorkflowDefID==null){return 0;}
		return WorkflowDefID.longValue();
	}

	/**
	* 设置字段WorkflowDefID的值，该字段的<br>
	* 字段名称 :WorkflowDefID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setWorkflowDefID(long workflowDefID) {
		this.WorkflowDefID = new Long(workflowDefID);
    }

	/**
	* 设置字段WorkflowDefID的值，该字段的<br>
	* 字段名称 :WorkflowDefID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setWorkflowDefID(String workflowDefID) {
		if (workflowDefID == null){
			this.WorkflowDefID = null;
			return;
		}
		this.WorkflowDefID = new Long(workflowDefID);
    }

	/**
	* 获取字段State的值，该字段的<br>
	* 字段名称 :State<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getState() {
		if(State==null){return 0;}
		return State.intValue();
	}

	/**
	* 设置字段State的值，该字段的<br>
	* 字段名称 :State<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setState(int state) {
		this.State = new Integer(state);
    }

	/**
	* 设置字段State的值，该字段的<br>
	* 字段名称 :State<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setState(String state) {
		if (state == null){
			this.State = null;
			return;
		}
		this.State = new Integer(state);
    }

	/**
	* 获取字段Memo的值，该字段的<br>
	* 字段名称 :Memo<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* 设置字段Memo的值，该字段的<br>
	* 字段名称 :Memo<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemo(String memo) {
		this.Memo = memo;
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