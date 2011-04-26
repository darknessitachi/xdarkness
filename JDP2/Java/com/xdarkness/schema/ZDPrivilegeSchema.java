package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;

/**
 * 表名称：ZDPrivilege<br>
 * 表代码：ZDPrivilege<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：OwnerType, Owner, PrivType, ID, Code<br>
 */
public class ZDPrivilegeSchema extends Schema {
	private String OwnerType;

	private String Owner;

	private String PrivType;

	private String ID;

	private String Code;

	private String Value;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("OwnerType", DataColumn.STRING, 0, 1 , 0 , true , true),
		new SchemaColumn("Owner", DataColumn.STRING, 1, 100 , 0 , true , true),
		new SchemaColumn("PrivType", DataColumn.STRING, 2, 40 , 0 , true , true),
		new SchemaColumn("ID", DataColumn.STRING, 3, 100 , 0 , true , true),
		new SchemaColumn("Code", DataColumn.STRING, 4, 40 , 0 , true , true),
		new SchemaColumn("Value", DataColumn.STRING, 5, 2 , 0 , true , false)
	};

	public static final String _TableCode = "ZDPrivilege";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into ZDPrivilege values(?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDPrivilege set OwnerType=?,Owner=?,PrivType=?,ID=?,Code=?,Value=? where OwnerType=? and Owner=? and PrivType=? and ID=? and Code=?";

	protected static final String _DeleteSQL = "delete from ZDPrivilege  where OwnerType=? and Owner=? and PrivType=? and ID=? and Code=?";

	protected static final String _FillAllSQL = "select * from ZDPrivilege  where OwnerType=? and Owner=? and PrivType=? and ID=? and Code=?";

	public ZDPrivilegeSchema(){
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
		return new ZDPrivilegeSchema();
	}

	protected SchemaSet newSet(){
		return new ZDPrivilegeSet();
	}

	public ZDPrivilegeSet query() {
		return query(null, -1, -1);
	}

	public ZDPrivilegeSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDPrivilegeSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDPrivilegeSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDPrivilegeSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){OwnerType = (String)v;return;}
		if (i == 1){Owner = (String)v;return;}
		if (i == 2){PrivType = (String)v;return;}
		if (i == 3){ID = (String)v;return;}
		if (i == 4){Code = (String)v;return;}
		if (i == 5){Value = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return OwnerType;}
		if (i == 1){return Owner;}
		if (i == 2){return PrivType;}
		if (i == 3){return ID;}
		if (i == 4){return Code;}
		if (i == 5){return Value;}
		return null;
	}

	/**
	* 获取字段OwnerType的值，该字段的<br>
	* 字段名称 :OwnerType<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getOwnerType() {
		return OwnerType;
	}

	/**
	* 设置字段OwnerType的值，该字段的<br>
	* 字段名称 :OwnerType<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setOwnerType(String ownerType) {
		this.OwnerType = ownerType;
    }

	/**
	* 获取字段Owner的值，该字段的<br>
	* 字段名称 :Owner<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getOwner() {
		return Owner;
	}

	/**
	* 设置字段Owner的值，该字段的<br>
	* 字段名称 :Owner<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setOwner(String owner) {
		this.Owner = owner;
    }

	/**
	* 获取字段PrivType的值，该字段的<br>
	* 字段名称 :PrivType<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getPrivType() {
		return PrivType;
	}

	/**
	* 设置字段PrivType的值，该字段的<br>
	* 字段名称 :PrivType<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setPrivType(String privType) {
		this.PrivType = privType;
    }

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
    }

	/**
	* 获取字段Code的值，该字段的<br>
	* 字段名称 :Code<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getCode() {
		return Code;
	}

	/**
	* 设置字段Code的值，该字段的<br>
	* 字段名称 :Code<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setCode(String code) {
		this.Code = code;
    }

	/**
	* 获取字段Value的值，该字段的<br>
	* 字段名称 :Value<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getValue() {
		return Value;
	}

	/**
	* 设置字段Value的值，该字段的<br>
	* 字段名称 :Value<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setValue(String value) {
		this.Value = value;
    }

}