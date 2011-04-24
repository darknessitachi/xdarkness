package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;

/**
 * 表名称：ZDDistrict<br>
 * 表代码：ZDDistrict<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：Code<br>
 */
public class ZDDistrictSchema extends Schema {
	private String Code;

	private String Name;

	private String CodeOrder;

	private Integer TreeLevel;

	private String Type;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Code", DataColumn.STRING, 0, 6 , 0 , true , true),
		new SchemaColumn("Name", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("CodeOrder", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("TreeLevel", DataColumn.INTEGER, 3, 11 , 0 , false , false),
		new SchemaColumn("Type", DataColumn.STRING, 4, 2 , 0 , false , false)
	};

	public static final String _TableCode = "ZDDistrict";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into ZDDistrict values(?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDDistrict set Code=?,Name=?,CodeOrder=?,TreeLevel=?,Type=? where Code=?";

	protected static final String _DeleteSQL = "delete from ZDDistrict  where Code=?";

	protected static final String _FillAllSQL = "select * from ZDDistrict  where Code=?";

	public ZDDistrictSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[5];
	}

	protected Schema newInstance(){
		return new ZDDistrictSchema();
	}

	protected SchemaSet newSet(){
		return new ZDDistrictSet();
	}

	public ZDDistrictSet query() {
		return query(null, -1, -1);
	}

	public ZDDistrictSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDDistrictSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDDistrictSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDDistrictSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Code = (String)v;return;}
		if (i == 1){Name = (String)v;return;}
		if (i == 2){CodeOrder = (String)v;return;}
		if (i == 3){if(v==null){TreeLevel = null;}else{TreeLevel = new Integer(v.toString());}return;}
		if (i == 4){Type = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Code;}
		if (i == 1){return Name;}
		if (i == 2){return CodeOrder;}
		if (i == 3){return TreeLevel;}
		if (i == 4){return Type;}
		return null;
	}

	/**
	* 获取字段Code的值，该字段的<br>
	* 字段名称 :Code<br>
	* 数据类型 :varchar(6)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getCode() {
		return Code;
	}

	/**
	* 设置字段Code的值，该字段的<br>
	* 字段名称 :Code<br>
	* 数据类型 :varchar(6)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setCode(String code) {
		this.Code = code;
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
	* 获取字段CodeOrder的值，该字段的<br>
	* 字段名称 :CodeOrder<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCodeOrder() {
		return CodeOrder;
	}

	/**
	* 设置字段CodeOrder的值，该字段的<br>
	* 字段名称 :CodeOrder<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCodeOrder(String codeOrder) {
		this.CodeOrder = codeOrder;
    }

	/**
	* 获取字段TreeLevel的值，该字段的<br>
	* 字段名称 :TreeLevel<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getTreeLevel() {
		if(TreeLevel==null){return 0;}
		return TreeLevel.intValue();
	}

	/**
	* 设置字段TreeLevel的值，该字段的<br>
	* 字段名称 :TreeLevel<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTreeLevel(int treeLevel) {
		this.TreeLevel = new Integer(treeLevel);
    }

	/**
	* 设置字段TreeLevel的值，该字段的<br>
	* 字段名称 :TreeLevel<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTreeLevel(String treeLevel) {
		if (treeLevel == null){
			this.TreeLevel = null;
			return;
		}
		this.TreeLevel = new Integer(treeLevel);
    }

	/**
	* 获取字段Type的值，该字段的<br>
	* 字段名称 :Type<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* 设置字段Type的值，该字段的<br>
	* 字段名称 :Type<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

}