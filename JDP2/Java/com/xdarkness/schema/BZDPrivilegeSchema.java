package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZDPrivilege备份<br>
 * 表代码：BZDPrivilege<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：OwnerType, Owner, PrivType, ID, Code, BackupNo<br>
 */
public class BZDPrivilegeSchema extends Schema {
	private String OwnerType;

	private String Owner;

	private String PrivType;

	private String ID;

	private String Code;

	private String Value;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("OwnerType", DataColumn.STRING, 0, 1 , 0 , true , true),
		new SchemaColumn("Owner", DataColumn.STRING, 1, 100 , 0 , true , true),
		new SchemaColumn("PrivType", DataColumn.STRING, 2, 40 , 0 , true , true),
		new SchemaColumn("ID", DataColumn.STRING, 3, 100 , 0 , true , true),
		new SchemaColumn("Code", DataColumn.STRING, 4, 40 , 0 , true , true),
		new SchemaColumn("Value", DataColumn.STRING, 5, 2 , 0 , true , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 6, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 7, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 8, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 9, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZDPrivilege";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into BZDPrivilege values(?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZDPrivilege set OwnerType=?,Owner=?,PrivType=?,ID=?,Code=?,Value=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where OwnerType=? and Owner=? and PrivType=? and ID=? and Code=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZDPrivilege  where OwnerType=? and Owner=? and PrivType=? and ID=? and Code=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZDPrivilege  where OwnerType=? and Owner=? and PrivType=? and ID=? and Code=? and BackupNo=?";

	public BZDPrivilegeSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[10];
	}

	protected Schema newInstance(){
		return new BZDPrivilegeSchema();
	}

	protected SchemaSet newSet(){
		return new BZDPrivilegeSet();
	}

	public BZDPrivilegeSet query() {
		return query(null, -1, -1);
	}

	public BZDPrivilegeSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZDPrivilegeSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZDPrivilegeSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZDPrivilegeSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){OwnerType = (String)v;return;}
		if (i == 1){Owner = (String)v;return;}
		if (i == 2){PrivType = (String)v;return;}
		if (i == 3){ID = (String)v;return;}
		if (i == 4){Code = (String)v;return;}
		if (i == 5){Value = (String)v;return;}
		if (i == 6){BackupNo = (String)v;return;}
		if (i == 7){BackupOperator = (String)v;return;}
		if (i == 8){BackupTime = (Date)v;return;}
		if (i == 9){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return OwnerType;}
		if (i == 1){return Owner;}
		if (i == 2){return PrivType;}
		if (i == 3){return ID;}
		if (i == 4){return Code;}
		if (i == 5){return Value;}
		if (i == 6){return BackupNo;}
		if (i == 7){return BackupOperator;}
		if (i == 8){return BackupTime;}
		if (i == 9){return BackupMemo;}
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