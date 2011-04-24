package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：Url备份<br>
 * 表代码：BUrl<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：id, BackupNo<br>
 */
public class BUrlSchema extends Schema {
	private Integer id;

	private String url;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.INTEGER, 0, 11 , 0 , true , true),
		new SchemaColumn("url", DataColumn.STRING, 1, 2000 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 2, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 3, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 4, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 5, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BUrl";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into BUrl values(?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BUrl set id=?,url=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where id=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BUrl  where id=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BUrl  where id=? and BackupNo=?";

	public BUrlSchema(){
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
		return new BUrlSchema();
	}

	protected SchemaSet newSet(){
		return new BUrlSet();
	}

	public BUrlSet query() {
		return query(null, -1, -1);
	}

	public BUrlSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BUrlSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BUrlSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BUrlSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){id = null;}else{id = new Integer(v.toString());}return;}
		if (i == 1){url = (String)v;return;}
		if (i == 2){BackupNo = (String)v;return;}
		if (i == 3){BackupOperator = (String)v;return;}
		if (i == 4){BackupTime = (Date)v;return;}
		if (i == 5){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return url;}
		if (i == 2){return BackupNo;}
		if (i == 3){return BackupOperator;}
		if (i == 4){return BackupTime;}
		if (i == 5){return BackupMemo;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public int getId() {
		if(id==null){return 0;}
		return id.intValue();
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(int id) {
		this.id = new Integer(id);
    }

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		if (id == null){
			this.id = null;
			return;
		}
		this.id = new Integer(id);
    }

	/**
	* 获取字段url的值，该字段的<br>
	* 字段名称 :url<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getUrl() {
		return url;
	}

	/**
	* 设置字段url的值，该字段的<br>
	* 字段名称 :url<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUrl(String url) {
		this.url = url;
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