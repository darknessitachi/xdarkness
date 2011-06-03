package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：UseOff<br>
 * 表代码：UseOff<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：id<br>
 */
public class UseOffSchema extends Schema {
	private Integer id;

	private Double money;

	private String useFor;

	private String moneyType;

	private String description;

	private Date createTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.INTEGER, 0, 11 , 0 , true , true),
		new SchemaColumn("money", DataColumn.DOUBLE, 1, 0 , 0 , true , false),
		new SchemaColumn("useFor", DataColumn.STRING, 2, 100 , 0 , false , false),
		new SchemaColumn("moneyType", DataColumn.STRING, 3, 5 , 0 , false , false),
		new SchemaColumn("description", DataColumn.STRING, 4, 200 , 0 , false , false),
		new SchemaColumn("createTime", DataColumn.DATETIME, 5, 0 , 0 , false , false)
	};

	public static final String _TableCode = "UseOff";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into UseOff values(?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update UseOff set id=?,money=?,useFor=?,moneyType=?,description=?,createTime=? where id=?";

	protected static final String _DeleteSQL = "delete from UseOff  where id=?";

	protected static final String _FillAllSQL = "select * from UseOff  where id=?";

	public UseOffSchema(){
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
		return new UseOffSchema();
	}

	protected SchemaSet newSet(){
		return new UseOffSet();
	}

	public UseOffSet query() {
		return query(null, -1, -1);
	}

	public UseOffSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public UseOffSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public UseOffSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (UseOffSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){id = null;}else{id = new Integer(v.toString());}return;}
		if (i == 1){if(v==null){money = null;}else{money = new Double(v.toString());}return;}
		if (i == 2){useFor = (String)v;return;}
		if (i == 3){moneyType = (String)v;return;}
		if (i == 4){description = (String)v;return;}
		if (i == 5){createTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return money;}
		if (i == 2){return useFor;}
		if (i == 3){return moneyType;}
		if (i == 4){return description;}
		if (i == 5){return createTime;}
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

}