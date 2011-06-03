package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZWHistoryStep备份<br>
 * 表代码：BZWHistoryStep<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZWHistoryStepSchema extends Schema {
	private Long ID;

	private Long EntryID;

	private Integer StepID;

	private Integer ActionID;

	private String Owner;

	private Date StartDate;

	private Date FinishDate;

	private Date DueDate;

	private String Status;

	private String Caller;

	private String Memo;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("EntryID", DataColumn.LONG, 1, 20 , 0 , false , false),
		new SchemaColumn("StepID", DataColumn.INTEGER, 2, 11 , 0 , false , false),
		new SchemaColumn("ActionID", DataColumn.INTEGER, 3, 11 , 0 , false , false),
		new SchemaColumn("Owner", DataColumn.STRING, 4, 200 , 0 , false , false),
		new SchemaColumn("StartDate", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("FinishDate", DataColumn.DATETIME, 6, 0 , 0 , false , false),
		new SchemaColumn("DueDate", DataColumn.DATETIME, 7, 0 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 8, 40 , 0 , false , false),
		new SchemaColumn("Caller", DataColumn.STRING, 9, 200 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 10, 100 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 11, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 12, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 13, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 14, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZWHistoryStep";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into BZWHistoryStep values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZWHistoryStep set ID=?,EntryID=?,StepID=?,ActionID=?,Owner=?,StartDate=?,FinishDate=?,DueDate=?,Status=?,Caller=?,Memo=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZWHistoryStep  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZWHistoryStep  where ID=? and BackupNo=?";

	public BZWHistoryStepSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[15];
	}

	protected Schema newInstance(){
		return new BZWHistoryStepSchema();
	}

	protected SchemaSet newSet(){
		return new BZWHistoryStepSet();
	}

	public BZWHistoryStepSet query() {
		return query(null, -1, -1);
	}

	public BZWHistoryStepSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZWHistoryStepSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZWHistoryStepSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZWHistoryStepSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){EntryID = null;}else{EntryID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){StepID = null;}else{StepID = new Integer(v.toString());}return;}
		if (i == 3){if(v==null){ActionID = null;}else{ActionID = new Integer(v.toString());}return;}
		if (i == 4){Owner = (String)v;return;}
		if (i == 5){StartDate = (Date)v;return;}
		if (i == 6){FinishDate = (Date)v;return;}
		if (i == 7){DueDate = (Date)v;return;}
		if (i == 8){Status = (String)v;return;}
		if (i == 9){Caller = (String)v;return;}
		if (i == 10){Memo = (String)v;return;}
		if (i == 11){BackupNo = (String)v;return;}
		if (i == 12){BackupOperator = (String)v;return;}
		if (i == 13){BackupTime = (Date)v;return;}
		if (i == 14){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return EntryID;}
		if (i == 2){return StepID;}
		if (i == 3){return ActionID;}
		if (i == 4){return Owner;}
		if (i == 5){return StartDate;}
		if (i == 6){return FinishDate;}
		if (i == 7){return DueDate;}
		if (i == 8){return Status;}
		if (i == 9){return Caller;}
		if (i == 10){return Memo;}
		if (i == 11){return BackupNo;}
		if (i == 12){return BackupOperator;}
		if (i == 13){return BackupTime;}
		if (i == 14){return BackupMemo;}
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
	* 获取字段EntryID的值，该字段的<br>
	* 字段名称 :EntryID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getEntryID() {
		if(EntryID==null){return 0;}
		return EntryID.longValue();
	}

	/**
	* 设置字段EntryID的值，该字段的<br>
	* 字段名称 :EntryID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEntryID(long entryID) {
		this.EntryID = new Long(entryID);
    }

	/**
	* 设置字段EntryID的值，该字段的<br>
	* 字段名称 :EntryID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEntryID(String entryID) {
		if (entryID == null){
			this.EntryID = null;
			return;
		}
		this.EntryID = new Long(entryID);
    }

	/**
	* 获取字段StepID的值，该字段的<br>
	* 字段名称 :StepID<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getStepID() {
		if(StepID==null){return 0;}
		return StepID.intValue();
	}

	/**
	* 设置字段StepID的值，该字段的<br>
	* 字段名称 :StepID<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStepID(int stepID) {
		this.StepID = new Integer(stepID);
    }

	/**
	* 设置字段StepID的值，该字段的<br>
	* 字段名称 :StepID<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStepID(String stepID) {
		if (stepID == null){
			this.StepID = null;
			return;
		}
		this.StepID = new Integer(stepID);
    }

	/**
	* 获取字段ActionID的值，该字段的<br>
	* 字段名称 :ActionID<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getActionID() {
		if(ActionID==null){return 0;}
		return ActionID.intValue();
	}

	/**
	* 设置字段ActionID的值，该字段的<br>
	* 字段名称 :ActionID<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setActionID(int actionID) {
		this.ActionID = new Integer(actionID);
    }

	/**
	* 设置字段ActionID的值，该字段的<br>
	* 字段名称 :ActionID<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setActionID(String actionID) {
		if (actionID == null){
			this.ActionID = null;
			return;
		}
		this.ActionID = new Integer(actionID);
    }

	/**
	* 获取字段Owner的值，该字段的<br>
	* 字段名称 :Owner<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOwner() {
		return Owner;
	}

	/**
	* 设置字段Owner的值，该字段的<br>
	* 字段名称 :Owner<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOwner(String owner) {
		this.Owner = owner;
    }

	/**
	* 获取字段StartDate的值，该字段的<br>
	* 字段名称 :StartDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getStartDate() {
		return StartDate;
	}

	/**
	* 设置字段StartDate的值，该字段的<br>
	* 字段名称 :StartDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStartDate(Date startDate) {
		this.StartDate = startDate;
    }

	/**
	* 获取字段FinishDate的值，该字段的<br>
	* 字段名称 :FinishDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getFinishDate() {
		return FinishDate;
	}

	/**
	* 设置字段FinishDate的值，该字段的<br>
	* 字段名称 :FinishDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFinishDate(Date finishDate) {
		this.FinishDate = finishDate;
    }

	/**
	* 获取字段DueDate的值，该字段的<br>
	* 字段名称 :DueDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getDueDate() {
		return DueDate;
	}

	/**
	* 设置字段DueDate的值，该字段的<br>
	* 字段名称 :DueDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDueDate(Date dueDate) {
		this.DueDate = dueDate;
    }

	/**
	* 获取字段Status的值，该字段的<br>
	* 字段名称 :Status<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* 设置字段Status的值，该字段的<br>
	* 字段名称 :Status<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
    }

	/**
	* 获取字段Caller的值，该字段的<br>
	* 字段名称 :Caller<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCaller() {
		return Caller;
	}

	/**
	* 设置字段Caller的值，该字段的<br>
	* 字段名称 :Caller<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCaller(String caller) {
		this.Caller = caller;
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