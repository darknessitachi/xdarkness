package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;

/**
 * 表名称：ZWWorkflowEntry<br>
 * 表代码：ZWWorkflowEntry<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID<br>
 */
public class ZWWorkflowEntrySchema extends Schema {
	private Long ID;

	private Long WorkflowDefID;

	private Integer State;

	private String Memo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("WorkflowDefID", DataColumn.LONG, 1, 20 , 0 , true , false),
		new SchemaColumn("State", DataColumn.INTEGER, 2, 11 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 3, 100 , 0 , false , false)
	};

	public static final String _TableCode = "ZWWorkflowEntry";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into ZWWorkflowEntry values(?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZWWorkflowEntry set ID=?,WorkflowDefID=?,State=?,Memo=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZWWorkflowEntry  where ID=?";

	protected static final String _FillAllSQL = "select * from ZWWorkflowEntry  where ID=?";

	public ZWWorkflowEntrySchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[4];
	}

	protected Schema newInstance(){
		return new ZWWorkflowEntrySchema();
	}

	protected SchemaSet newSet(){
		return new ZWWorkflowEntrySet();
	}

	public ZWWorkflowEntrySet query() {
		return query(null, -1, -1);
	}

	public ZWWorkflowEntrySet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZWWorkflowEntrySet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZWWorkflowEntrySet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZWWorkflowEntrySet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){WorkflowDefID = null;}else{WorkflowDefID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){State = null;}else{State = new Integer(v.toString());}return;}
		if (i == 3){Memo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return WorkflowDefID;}
		if (i == 2){return State;}
		if (i == 3){return Memo;}
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

}