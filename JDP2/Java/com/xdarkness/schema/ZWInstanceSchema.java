package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZWInstance<br>
 * 表代码：ZWInstance<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID<br>
 */
public class ZWInstanceSchema extends Schema {
	private Long ID;

	private Long WorkflowID;

	private String Name;

	private String DataID;

	private String State;

	private String Memo;

	private Date AddTime;

	private String AddUser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("WorkflowID", DataColumn.LONG, 1, 20 , 0 , true , false),
		new SchemaColumn("Name", DataColumn.STRING, 2, 100 , 0 , false , false),
		new SchemaColumn("DataID", DataColumn.STRING, 3, 30 , 0 , false , false),
		new SchemaColumn("State", DataColumn.STRING, 4, 10 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 6, 0 , 0 , true , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 7, 50 , 0 , true , false)
	};

	public static final String _TableCode = "ZWInstance";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into ZWInstance values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZWInstance set ID=?,WorkflowID=?,Name=?,DataID=?,State=?,Memo=?,AddTime=?,AddUser=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZWInstance  where ID=?";

	protected static final String _FillAllSQL = "select * from ZWInstance  where ID=?";

	public ZWInstanceSchema(){
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
		return new ZWInstanceSchema();
	}

	protected SchemaSet newSet(){
		return new ZWInstanceSet();
	}

	public ZWInstanceSet query() {
		return query(null, -1, -1);
	}

	public ZWInstanceSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZWInstanceSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZWInstanceSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZWInstanceSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){WorkflowID = null;}else{WorkflowID = new Long(v.toString());}return;}
		if (i == 2){Name = (String)v;return;}
		if (i == 3){DataID = (String)v;return;}
		if (i == 4){State = (String)v;return;}
		if (i == 5){Memo = (String)v;return;}
		if (i == 6){AddTime = (Date)v;return;}
		if (i == 7){AddUser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return WorkflowID;}
		if (i == 2){return Name;}
		if (i == 3){return DataID;}
		if (i == 4){return State;}
		if (i == 5){return Memo;}
		if (i == 6){return AddTime;}
		if (i == 7){return AddUser;}
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
	* 获取字段WorkflowID的值，该字段的<br>
	* 字段名称 :WorkflowID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getWorkflowID() {
		if(WorkflowID==null){return 0;}
		return WorkflowID.longValue();
	}

	/**
	* 设置字段WorkflowID的值，该字段的<br>
	* 字段名称 :WorkflowID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setWorkflowID(long workflowID) {
		this.WorkflowID = new Long(workflowID);
    }

	/**
	* 设置字段WorkflowID的值，该字段的<br>
	* 字段名称 :WorkflowID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setWorkflowID(String workflowID) {
		if (workflowID == null){
			this.WorkflowID = null;
			return;
		}
		this.WorkflowID = new Long(workflowID);
    }

	/**
	* 获取字段Name的值，该字段的<br>
	* 字段名称 :Name<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* 设置字段Name的值，该字段的<br>
	* 字段名称 :Name<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* 获取字段DataID的值，该字段的<br>
	* 字段名称 :DataID<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDataID() {
		return DataID;
	}

	/**
	* 设置字段DataID的值，该字段的<br>
	* 字段名称 :DataID<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDataID(String dataID) {
		this.DataID = dataID;
    }

	/**
	* 获取字段State的值，该字段的<br>
	* 字段名称 :State<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getState() {
		return State;
	}

	/**
	* 设置字段State的值，该字段的<br>
	* 字段名称 :State<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setState(String state) {
		this.State = state;
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
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

}