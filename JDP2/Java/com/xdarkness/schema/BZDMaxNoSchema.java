package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZDMaxNo备份<br>
 * 表代码：BZDMaxNo<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：NoType, NoSubType, BackupNo<br>
 */
public class BZDMaxNoSchema extends Schema {
	private String NoType;

	private String NoSubType;

	private Long MaxValue;

	private Long Length;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("NoType", DataColumn.STRING, 0, 20 , 0 , true , true),
		new SchemaColumn("NoSubType", DataColumn.STRING, 1, 255 , 0 , true , true),
		new SchemaColumn("MaxValue", DataColumn.LONG, 2, 20 , 0 , true , false),
		new SchemaColumn("Length", DataColumn.LONG, 3, 20 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 4, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 5, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 6, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 7, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZDMaxNo";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into BZDMaxNo values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZDMaxNo set NoType=?,NoSubType=?,MaxValue=?,Length=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where NoType=? and NoSubType=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZDMaxNo  where NoType=? and NoSubType=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZDMaxNo  where NoType=? and NoSubType=? and BackupNo=?";

	public BZDMaxNoSchema(){
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
		return new BZDMaxNoSchema();
	}

	protected SchemaSet newSet(){
		return new BZDMaxNoSet();
	}

	public BZDMaxNoSet query() {
		return query(null, -1, -1);
	}

	public BZDMaxNoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZDMaxNoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZDMaxNoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZDMaxNoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){NoType = (String)v;return;}
		if (i == 1){NoSubType = (String)v;return;}
		if (i == 2){if(v==null){MaxValue = null;}else{MaxValue = new Long(v.toString());}return;}
		if (i == 3){if(v==null){Length = null;}else{Length = new Long(v.toString());}return;}
		if (i == 4){BackupNo = (String)v;return;}
		if (i == 5){BackupOperator = (String)v;return;}
		if (i == 6){BackupTime = (Date)v;return;}
		if (i == 7){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return NoType;}
		if (i == 1){return NoSubType;}
		if (i == 2){return MaxValue;}
		if (i == 3){return Length;}
		if (i == 4){return BackupNo;}
		if (i == 5){return BackupOperator;}
		if (i == 6){return BackupTime;}
		if (i == 7){return BackupMemo;}
		return null;
	}

	/**
	* 获取字段NoType的值，该字段的<br>
	* 字段名称 :NoType<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getNoType() {
		return NoType;
	}

	/**
	* 设置字段NoType的值，该字段的<br>
	* 字段名称 :NoType<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setNoType(String noType) {
		this.NoType = noType;
    }

	/**
	* 获取字段NoSubType的值，该字段的<br>
	* 字段名称 :NoSubType<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getNoSubType() {
		return NoSubType;
	}

	/**
	* 设置字段NoSubType的值，该字段的<br>
	* 字段名称 :NoSubType<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setNoSubType(String noSubType) {
		this.NoSubType = noSubType;
    }

	/**
	* 获取字段MaxValue的值，该字段的<br>
	* 字段名称 :MaxValue<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getMaxValue() {
		if(MaxValue==null){return 0;}
		return MaxValue.longValue();
	}

	/**
	* 设置字段MaxValue的值，该字段的<br>
	* 字段名称 :MaxValue<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMaxValue(long maxValue) {
		this.MaxValue = new Long(maxValue);
    }

	/**
	* 设置字段MaxValue的值，该字段的<br>
	* 字段名称 :MaxValue<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMaxValue(String maxValue) {
		if (maxValue == null){
			this.MaxValue = null;
			return;
		}
		this.MaxValue = new Long(maxValue);
    }

	/**
	* 获取字段Length的值，该字段的<br>
	* 字段名称 :Length<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getLength() {
		if(Length==null){return 0;}
		return Length.longValue();
	}

	/**
	* 设置字段Length的值，该字段的<br>
	* 字段名称 :Length<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLength(long length) {
		this.Length = new Long(length);
    }

	/**
	* 设置字段Length的值，该字段的<br>
	* 字段名称 :Length<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLength(String length) {
		if (length == null){
			this.Length = null;
			return;
		}
		this.Length = new Long(length);
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