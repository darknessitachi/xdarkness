package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;

/**
 * 表名称：Url<br>
 * 表代码：Url<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：id<br>
 */
public class UrlSchema extends Schema {
	private Integer id;

	private String url;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.INTEGER, 0, 11 , 0 , true , true),
		new SchemaColumn("url", DataColumn.STRING, 1, 2000 , 0 , false , false)
	};

	public static final String _TableCode = "Url";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into Url values(?,?)";

	protected static final String _UpdateAllSQL = "update Url set id=?,url=? where id=?";

	protected static final String _DeleteSQL = "delete from Url  where id=?";

	protected static final String _FillAllSQL = "select * from Url  where id=?";

	public UrlSchema(){
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
		return new UrlSchema();
	}

	protected SchemaSet newSet(){
		return new UrlSet();
	}

	public UrlSet query() {
		return query(null, -1, -1);
	}

	public UrlSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public UrlSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public UrlSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (UrlSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){id = null;}else{id = new Integer(v.toString());}return;}
		if (i == 1){url = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return url;}
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

}