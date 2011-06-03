package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZDFavorite<br>
 * 表代码：ZDFavorite<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：UserName, DocID<br>
 */
public class ZDFavoriteSchema extends Schema {
	private String UserName;

	private Long DocID;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("UserName", DataColumn.STRING, 0, 200 , 0 , true , true),
		new SchemaColumn("DocID", DataColumn.LONG, 1, 20 , 0 , true , true),
		new SchemaColumn("AddUser", DataColumn.STRING, 2, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 3, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 4, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 5, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZDFavorite";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into ZDFavorite values(?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDFavorite set UserName=?,DocID=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where UserName=? and DocID=?";

	protected static final String _DeleteSQL = "delete from ZDFavorite  where UserName=? and DocID=?";

	protected static final String _FillAllSQL = "select * from ZDFavorite  where UserName=? and DocID=?";

	public ZDFavoriteSchema(){
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
		return new ZDFavoriteSchema();
	}

	protected SchemaSet newSet(){
		return new ZDFavoriteSet();
	}

	public ZDFavoriteSet query() {
		return query(null, -1, -1);
	}

	public ZDFavoriteSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDFavoriteSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDFavoriteSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDFavoriteSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){UserName = (String)v;return;}
		if (i == 1){if(v==null){DocID = null;}else{DocID = new Long(v.toString());}return;}
		if (i == 2){AddUser = (String)v;return;}
		if (i == 3){AddTime = (Date)v;return;}
		if (i == 4){ModifyUser = (String)v;return;}
		if (i == 5){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return UserName;}
		if (i == 1){return DocID;}
		if (i == 2){return AddUser;}
		if (i == 3){return AddTime;}
		if (i == 4){return ModifyUser;}
		if (i == 5){return ModifyTime;}
		return null;
	}

	/**
	* 获取字段UserName的值，该字段的<br>
	* 字段名称 :UserName<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getUserName() {
		return UserName;
	}

	/**
	* 设置字段UserName的值，该字段的<br>
	* 字段名称 :UserName<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setUserName(String userName) {
		this.UserName = userName;
    }

	/**
	* 获取字段DocID的值，该字段的<br>
	* 字段名称 :DocID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getDocID() {
		if(DocID==null){return 0;}
		return DocID.longValue();
	}

	/**
	* 设置字段DocID的值，该字段的<br>
	* 字段名称 :DocID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setDocID(long docID) {
		this.DocID = new Long(docID);
    }

	/**
	* 设置字段DocID的值，该字段的<br>
	* 字段名称 :DocID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setDocID(String docID) {
		if (docID == null){
			this.DocID = null;
			return;
		}
		this.DocID = new Long(docID);
    }

	/**
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :ModifyUser<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :ModifyUser<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

	/**
	* 获取字段ModifyTime的值，该字段的<br>
	* 字段名称 :ModifyTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModifyTime() {
		return ModifyTime;
	}

	/**
	* 设置字段ModifyTime的值，该字段的<br>
	* 字段名称 :ModifyTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyTime(Date modifyTime) {
		this.ModifyTime = modifyTime;
    }

}