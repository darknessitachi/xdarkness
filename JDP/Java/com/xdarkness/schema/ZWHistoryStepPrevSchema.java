package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;

/**
 * 表名称：ZWHistoryStepPrev<br>
 * 表代码：ZWHistoryStepPrev<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID, PreviousID<br>
 */
public class ZWHistoryStepPrevSchema extends Schema {
	private Long ID;

	private Long PreviousID;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("PreviousID", DataColumn.LONG, 1, 20 , 0 , true , true)
	};

	public static final String _TableCode = "ZWHistoryStepPrev";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into ZWHistoryStepPrev values(?,?)";

	protected static final String _UpdateAllSQL = "update ZWHistoryStepPrev set ID=?,PreviousID=? where ID=? and PreviousID=?";

	protected static final String _DeleteSQL = "delete from ZWHistoryStepPrev  where ID=? and PreviousID=?";

	protected static final String _FillAllSQL = "select * from ZWHistoryStepPrev  where ID=? and PreviousID=?";

	public ZWHistoryStepPrevSchema(){
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
		return new ZWHistoryStepPrevSchema();
	}

	protected SchemaSet newSet(){
		return new ZWHistoryStepPrevSet();
	}

	public ZWHistoryStepPrevSet query() {
		return query(null, -1, -1);
	}

	public ZWHistoryStepPrevSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZWHistoryStepPrevSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZWHistoryStepPrevSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZWHistoryStepPrevSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){PreviousID = null;}else{PreviousID = new Long(v.toString());}return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return PreviousID;}
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
	* 获取字段PreviousID的值，该字段的<br>
	* 字段名称 :PreviousID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getPreviousID() {
		if(PreviousID==null){return 0;}
		return PreviousID.longValue();
	}

	/**
	* 设置字段PreviousID的值，该字段的<br>
	* 字段名称 :PreviousID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setPreviousID(long previousID) {
		this.PreviousID = new Long(previousID);
    }

	/**
	* 设置字段PreviousID的值，该字段的<br>
	* 字段名称 :PreviousID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setPreviousID(String previousID) {
		if (previousID == null){
			this.PreviousID = null;
			return;
		}
		this.PreviousID = new Long(previousID);
    }

}