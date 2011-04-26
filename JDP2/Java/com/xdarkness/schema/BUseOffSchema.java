package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：UseOff备份<br>
 * 表代码：BUseOff<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：id, BackupNo<br>
 */
public class BUseOffSchema extends Schema {
	private Integer id;

	private Double money;

	private String useFor;

	private String moneyType;

	private String description;

	private Date createTime;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.INTEGER, 0, 11 , 0 , true , true),
		new SchemaColumn("money", DataColumn.DOUBLE, 1, 0 , 0 , true , false),
		new SchemaColumn("useFor", DataColumn.STRING, 2, 100 , 0 , false , false),
		new SchemaColumn("moneyType", DataColumn.STRING, 3, 5 , 0 , false , false),
		new SchemaColumn("description", DataColumn.STRING, 4, 200 , 0 , false , false),
		new SchemaColumn("createTime", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 6, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 7, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 8, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 9, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BUseOff";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into BUseOff values(?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BUseOff set id=?,money=?,useFor=?,moneyType=?,description=?,createTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where id=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BUseOff  where id=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BUseOff  where id=? and BackupNo=?";

	public BUseOffSchema(){
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
		return new BUseOffSchema();
	}

	protected SchemaSet newSet(){
		return new BUseOffSet();
	}

	public BUseOffSet query() {
		return query(null, -1, -1);
	}

	public BUseOffSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BUseOffSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BUseOffSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BUseOffSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){id = null;}else{id = new Integer(v.toString());}return;}
		if (i == 1){if(v==null){money = null;}else{money = new Double(v.toString());}return;}
		if (i == 2){useFor = (String)v;return;}
		if (i == 3){moneyType = (String)v;return;}
		if (i == 4){description = (String)v;return;}
		if (i == 5){createTime = (Date)v;return;}
		if (i == 6){BackupNo = (String)v;return;}
		if (i == 7){BackupOperator = (String)v;return;}
		if (i == 8){BackupTime = (Date)v;return;}
		if (i == 9){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return money;}
		if (i == 2){return useFor;}
		if (i == 3){return moneyType;}
		if (i == 4){return description;}
		if (i == 5){return createTime;}
		if (i == 6){return BackupNo;}
		if (i == 7){return BackupOperator;}
		if (i == 8){return BackupTime;}
		if (i == 9){return BackupMemo;}
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
	* 获取字段money的值，该字段的<br>
	* 字段名称 :money<br>
	* 数据类型 :double<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public double getMoney() {
		if(money==null){return 0;}
		return money.doubleValue();
	}

	/**
	* 设置字段money的值，该字段的<br>
	* 字段名称 :money<br>
	* 数据类型 :double<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMoney(double money) {
		this.money = new Double(money);
    }

	/**
	* 设置字段money的值，该字段的<br>
	* 字段名称 :money<br>
	* 数据类型 :double<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMoney(String money) {
		if (money == null){
			this.money = null;
			return;
		}
		this.money = new Double(money);
    }

	/**
	* 获取字段useFor的值，该字段的<br>
	* 字段名称 :useFor<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getUseFor() {
		return useFor;
	}

	/**
	* 设置字段useFor的值，该字段的<br>
	* 字段名称 :useFor<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUseFor(String useFor) {
		this.useFor = useFor;
    }

	/**
	* 获取字段moneyType的值，该字段的<br>
	* 字段名称 :moneyType<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMoneyType() {
		return moneyType;
	}

	/**
	* 设置字段moneyType的值，该字段的<br>
	* 字段名称 :moneyType<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
    }

	/**
	* 获取字段description的值，该字段的<br>
	* 字段名称 :description<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDescription() {
		return description;
	}

	/**
	* 设置字段description的值，该字段的<br>
	* 字段名称 :description<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDescription(String description) {
		this.description = description;
    }

	/**
	* 获取字段createTime的值，该字段的<br>
	* 字段名称 :createTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getCreateTime() {
		return createTime;
	}

	/**
	* 设置字段createTime的值，该字段的<br>
	* 字段名称 :createTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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