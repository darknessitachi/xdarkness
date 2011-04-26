package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;

/**
 * 表名称：ZDColumnValue<br>
 * 表代码：ZDColumnValue<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID<br>
 */
public class ZDColumnValueSchema extends Schema {
	private Long ID;

	private Long ColumnID;

	private String ColumnCode;

	private String TextValue;

	private String RelaType;

	private String RelaID;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("ColumnID", DataColumn.LONG, 1, 20 , 0 , true , false),
		new SchemaColumn("ColumnCode", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("TextValue", DataColumn.CLOB, 3, 0 , 0 , false , false),
		new SchemaColumn("RelaType", DataColumn.STRING, 4, 2 , 0 , false , false),
		new SchemaColumn("RelaID", DataColumn.STRING, 5, 100 , 0 , false , false)
	};

	public static final String _TableCode = "ZDColumnValue";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into ZDColumnValue values(?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDColumnValue set ID=?,ColumnID=?,ColumnCode=?,TextValue=?,RelaType=?,RelaID=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZDColumnValue  where ID=?";

	protected static final String _FillAllSQL = "select * from ZDColumnValue  where ID=?";

	public ZDColumnValueSchema(){
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
		return new ZDColumnValueSchema();
	}

	protected SchemaSet newSet(){
		return new ZDColumnValueSet();
	}

	public ZDColumnValueSet query() {
		return query(null, -1, -1);
	}

	public ZDColumnValueSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDColumnValueSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDColumnValueSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDColumnValueSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){ColumnID = null;}else{ColumnID = new Long(v.toString());}return;}
		if (i == 2){ColumnCode = (String)v;return;}
		if (i == 3){TextValue = (String)v;return;}
		if (i == 4){RelaType = (String)v;return;}
		if (i == 5){RelaID = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return ColumnID;}
		if (i == 2){return ColumnCode;}
		if (i == 3){return TextValue;}
		if (i == 4){return RelaType;}
		if (i == 5){return RelaID;}
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
	* 获取字段ColumnID的值，该字段的<br>
	* 字段名称 :ColumnID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getColumnID() {
		if(ColumnID==null){return 0;}
		return ColumnID.longValue();
	}

	/**
	* 设置字段ColumnID的值，该字段的<br>
	* 字段名称 :ColumnID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setColumnID(long columnID) {
		this.ColumnID = new Long(columnID);
    }

	/**
	* 设置字段ColumnID的值，该字段的<br>
	* 字段名称 :ColumnID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setColumnID(String columnID) {
		if (columnID == null){
			this.ColumnID = null;
			return;
		}
		this.ColumnID = new Long(columnID);
    }

	/**
	* 获取字段ColumnCode的值，该字段的<br>
	* 字段名称 :ColumnCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getColumnCode() {
		return ColumnCode;
	}

	/**
	* 设置字段ColumnCode的值，该字段的<br>
	* 字段名称 :ColumnCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setColumnCode(String columnCode) {
		this.ColumnCode = columnCode;
    }

	/**
	* 获取字段TextValue的值，该字段的<br>
	* 字段名称 :TextValue<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTextValue() {
		return TextValue;
	}

	/**
	* 设置字段TextValue的值，该字段的<br>
	* 字段名称 :TextValue<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTextValue(String textValue) {
		this.TextValue = textValue;
    }

	/**
	* 获取字段RelaType的值，该字段的<br>
	* 字段名称 :RelaType<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRelaType() {
		return RelaType;
	}

	/**
	* 设置字段RelaType的值，该字段的<br>
	* 字段名称 :RelaType<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRelaType(String relaType) {
		this.RelaType = relaType;
    }

	/**
	* 获取字段RelaID的值，该字段的<br>
	* 字段名称 :RelaID<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRelaID() {
		return RelaID;
	}

	/**
	* 设置字段RelaID的值，该字段的<br>
	* 字段名称 :RelaID<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRelaID(String relaID) {
		this.RelaID = relaID;
    }

}