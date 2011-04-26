package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZDUserLog<br>
 * 表代码：ZDUserLog<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：UserName, LogID<br>
 */
public class ZDUserLogSchema extends Schema {
	private String UserName;

	private Long LogID;

	private String IP;

	private String LogType;

	private String SubType;

	private String LogMessage;

	private String Memo;

	private Date AddTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("UserName", DataColumn.STRING, 0, 200 , 0 , true , true),
		new SchemaColumn("LogID", DataColumn.LONG, 1, 20 , 0 , true , true),
		new SchemaColumn("IP", DataColumn.STRING, 2, 40 , 0 , false , false),
		new SchemaColumn("LogType", DataColumn.STRING, 3, 20 , 0 , true , false),
		new SchemaColumn("SubType", DataColumn.STRING, 4, 20 , 0 , false , false),
		new SchemaColumn("LogMessage", DataColumn.STRING, 5, 400 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 6, 40 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 7, 0 , 0 , true , false)
	};

	public static final String _TableCode = "ZDUserLog";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into ZDUserLog values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDUserLog set UserName=?,LogID=?,IP=?,LogType=?,SubType=?,LogMessage=?,Memo=?,AddTime=? where UserName=? and LogID=?";

	protected static final String _DeleteSQL = "delete from ZDUserLog  where UserName=? and LogID=?";

	protected static final String _FillAllSQL = "select * from ZDUserLog  where UserName=? and LogID=?";

	public ZDUserLogSchema(){
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
		return new ZDUserLogSchema();
	}

	protected SchemaSet newSet(){
		return new ZDUserLogSet();
	}

	public ZDUserLogSet query() {
		return query(null, -1, -1);
	}

	public ZDUserLogSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDUserLogSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDUserLogSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDUserLogSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){UserName = (String)v;return;}
		if (i == 1){if(v==null){LogID = null;}else{LogID = new Long(v.toString());}return;}
		if (i == 2){IP = (String)v;return;}
		if (i == 3){LogType = (String)v;return;}
		if (i == 4){SubType = (String)v;return;}
		if (i == 5){LogMessage = (String)v;return;}
		if (i == 6){Memo = (String)v;return;}
		if (i == 7){AddTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return UserName;}
		if (i == 1){return LogID;}
		if (i == 2){return IP;}
		if (i == 3){return LogType;}
		if (i == 4){return SubType;}
		if (i == 5){return LogMessage;}
		if (i == 6){return Memo;}
		if (i == 7){return AddTime;}
		return null;
	}

	/**
	* 获取字段UserName的值，该字段的<br>
	* 字段名称 :UserName<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getUserName() {
		return UserName;
	}

	/**
	* 设置字段UserName的值，该字段的<br>
	* 字段名称 :UserName<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setUserName(String userName) {
		this.UserName = userName;
    }

	/**
	* 获取字段LogID的值，该字段的<br>
	* 字段名称 :LogID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getLogID() {
		if(LogID==null){return 0;}
		return LogID.longValue();
	}

	/**
	* 设置字段LogID的值，该字段的<br>
	* 字段名称 :LogID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setLogID(long logID) {
		this.LogID = new Long(logID);
    }

	/**
	* 设置字段LogID的值，该字段的<br>
	* 字段名称 :LogID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setLogID(String logID) {
		if (logID == null){
			this.LogID = null;
			return;
		}
		this.LogID = new Long(logID);
    }

	/**
	* 获取字段IP的值，该字段的<br>
	* 字段名称 :IP<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIP() {
		return IP;
	}

	/**
	* 设置字段IP的值，该字段的<br>
	* 字段名称 :IP<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIP(String iP) {
		this.IP = iP;
    }

	/**
	* 获取字段LogType的值，该字段的<br>
	* 字段名称 :LogType<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getLogType() {
		return LogType;
	}

	/**
	* 设置字段LogType的值，该字段的<br>
	* 字段名称 :LogType<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setLogType(String logType) {
		this.LogType = logType;
    }

	/**
	* 获取字段SubType的值，该字段的<br>
	* 字段名称 :SubType<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSubType() {
		return SubType;
	}

	/**
	* 设置字段SubType的值，该字段的<br>
	* 字段名称 :SubType<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSubType(String subType) {
		this.SubType = subType;
    }

	/**
	* 获取字段LogMessage的值，该字段的<br>
	* 字段名称 :LogMessage<br>
	* 数据类型 :varchar(400)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLogMessage() {
		return LogMessage;
	}

	/**
	* 设置字段LogMessage的值，该字段的<br>
	* 字段名称 :LogMessage<br>
	* 数据类型 :varchar(400)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLogMessage(String logMessage) {
		this.LogMessage = logMessage;
    }

	/**
	* 获取字段Memo的值，该字段的<br>
	* 字段名称 :Memo<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* 设置字段Memo的值，该字段的<br>
	* 字段名称 :Memo<br>
	* 数据类型 :varchar(40)<br>
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

}