package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZWHistoryStep<br>
 * 表代码：ZWHistoryStep<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID<br>
 */
public class ZWHistoryStepSchema extends Schema {
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
		new SchemaColumn("Memo", DataColumn.STRING, 10, 100 , 0 , false , false)
	};

	public static final String _TableCode = "ZWHistoryStep";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into ZWHistoryStep values(?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZWHistoryStep set ID=?,EntryID=?,StepID=?,ActionID=?,Owner=?,StartDate=?,FinishDate=?,DueDate=?,Status=?,Caller=?,Memo=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZWHistoryStep  where ID=?";

	protected static final String _FillAllSQL = "select * from ZWHistoryStep  where ID=?";

	public ZWHistoryStepSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[11];
	}

	protected Schema newInstance(){
		return new ZWHistoryStepSchema();
	}

	protected SchemaSet newSet(){
		return new ZWHistoryStepSet();
	}

	public ZWHistoryStepSet query() {
		return query(null, -1, -1);
	}

	public ZWHistoryStepSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZWHistoryStepSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZWHistoryStepSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZWHistoryStepSet)querySet(qb , pageSize , pageIndex);
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

}