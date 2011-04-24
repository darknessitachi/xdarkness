package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;

/**
 * 表名称：ZDIPRange<br>
 * 表代码：ZDIPRange<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：DistrictCode<br>
 */
public class ZDIPRangeSchema extends Schema {
	private String DistrictCode;

	private String IPRanges;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("DistrictCode", DataColumn.STRING, 0, 10 , 0 , true , true),
		new SchemaColumn("IPRanges", DataColumn.CLOB, 1, 0 , 0 , true , false)
	};

	public static final String _TableCode = "ZDIPRange";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into ZDIPRange values(?,?)";

	protected static final String _UpdateAllSQL = "update ZDIPRange set DistrictCode=?,IPRanges=? where DistrictCode=?";

	protected static final String _DeleteSQL = "delete from ZDIPRange  where DistrictCode=?";

	protected static final String _FillAllSQL = "select * from ZDIPRange  where DistrictCode=?";

	public ZDIPRangeSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[2];
	}

	protected Schema newInstance(){
		return new ZDIPRangeSchema();
	}

	protected SchemaSet newSet(){
		return new ZDIPRangeSet();
	}

	public ZDIPRangeSet query() {
		return query(null, -1, -1);
	}

	public ZDIPRangeSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDIPRangeSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDIPRangeSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDIPRangeSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){DistrictCode = (String)v;return;}
		if (i == 1){IPRanges = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return DistrictCode;}
		if (i == 1){return IPRanges;}
		return null;
	}

	/**
	* 获取字段DistrictCode的值，该字段的<br>
	* 字段名称 :DistrictCode<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getDistrictCode() {
		return DistrictCode;
	}

	/**
	* 设置字段DistrictCode的值，该字段的<br>
	* 字段名称 :DistrictCode<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setDistrictCode(String districtCode) {
		this.DistrictCode = districtCode;
    }

	/**
	* 获取字段IPRanges的值，该字段的<br>
	* 字段名称 :IPRanges<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getIPRanges() {
		return IPRanges;
	}

	/**
	* 设置字段IPRanges的值，该字段的<br>
	* 字段名称 :IPRanges<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setIPRanges(String iPRanges) {
		this.IPRanges = iPRanges;
    }

}