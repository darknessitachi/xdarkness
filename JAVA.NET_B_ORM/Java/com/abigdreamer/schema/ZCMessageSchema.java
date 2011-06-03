package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCMessage<br>
 * 表代码：ZCMessage<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID<br>
 */
public class ZCMessageSchema extends Schema {
	private Long ID;

	private String FromUser;

	private String ToUser;

	private String Box;

	private Long ReadFlag;

	private Long PopFlag;

	private String Subject;

	private String Content;

	private Date AddTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("FromUser", DataColumn.STRING, 1, 50 , 0 , false , false),
		new SchemaColumn("ToUser", DataColumn.STRING, 2, 50 , 0 , false , false),
		new SchemaColumn("Box", DataColumn.STRING, 3, 10 , 0 , false , false),
		new SchemaColumn("ReadFlag", DataColumn.LONG, 4, 20 , 0 , false , false),
		new SchemaColumn("PopFlag", DataColumn.LONG, 5, 20 , 0 , false , false),
		new SchemaColumn("Subject", DataColumn.STRING, 6, 500 , 0 , false , false),
		new SchemaColumn("Content", DataColumn.CLOB, 7, 0 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 8, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCMessage";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into ZCMessage values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCMessage set ID=?,FromUser=?,ToUser=?,Box=?,ReadFlag=?,PopFlag=?,Subject=?,Content=?,AddTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCMessage  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCMessage  where ID=?";

	public ZCMessageSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[9];
	}

	protected Schema newInstance(){
		return new ZCMessageSchema();
	}

	protected SchemaSet newSet(){
		return new ZCMessageSet();
	}

	public ZCMessageSet query() {
		return query(null, -1, -1);
	}

	public ZCMessageSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCMessageSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCMessageSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCMessageSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){FromUser = (String)v;return;}
		if (i == 2){ToUser = (String)v;return;}
		if (i == 3){Box = (String)v;return;}
		if (i == 4){if(v==null){ReadFlag = null;}else{ReadFlag = new Long(v.toString());}return;}
		if (i == 5){if(v==null){PopFlag = null;}else{PopFlag = new Long(v.toString());}return;}
		if (i == 6){Subject = (String)v;return;}
		if (i == 7){Content = (String)v;return;}
		if (i == 8){AddTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return FromUser;}
		if (i == 2){return ToUser;}
		if (i == 3){return Box;}
		if (i == 4){return ReadFlag;}
		if (i == 5){return PopFlag;}
		if (i == 6){return Subject;}
		if (i == 7){return Content;}
		if (i == 8){return AddTime;}
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
	* 获取字段FromUser的值，该字段的<br>
	* 字段名称 :FromUser<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFromUser() {
		return FromUser;
	}

	/**
	* 设置字段FromUser的值，该字段的<br>
	* 字段名称 :FromUser<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFromUser(String fromUser) {
		this.FromUser = fromUser;
    }

	/**
	* 获取字段ToUser的值，该字段的<br>
	* 字段名称 :ToUser<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getToUser() {
		return ToUser;
	}

	/**
	* 设置字段ToUser的值，该字段的<br>
	* 字段名称 :ToUser<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setToUser(String toUser) {
		this.ToUser = toUser;
    }

	/**
	* 获取字段Box的值，该字段的<br>
	* 字段名称 :Box<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBox() {
		return Box;
	}

	/**
	* 设置字段Box的值，该字段的<br>
	* 字段名称 :Box<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBox(String box) {
		this.Box = box;
    }

	/**
	* 获取字段ReadFlag的值，该字段的<br>
	* 字段名称 :ReadFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getReadFlag() {
		if(ReadFlag==null){return 0;}
		return ReadFlag.longValue();
	}

	/**
	* 设置字段ReadFlag的值，该字段的<br>
	* 字段名称 :ReadFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReadFlag(long readFlag) {
		this.ReadFlag = new Long(readFlag);
    }

	/**
	* 设置字段ReadFlag的值，该字段的<br>
	* 字段名称 :ReadFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReadFlag(String readFlag) {
		if (readFlag == null){
			this.ReadFlag = null;
			return;
		}
		this.ReadFlag = new Long(readFlag);
    }

	/**
	* 获取字段PopFlag的值，该字段的<br>
	* 字段名称 :PopFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getPopFlag() {
		if(PopFlag==null){return 0;}
		return PopFlag.longValue();
	}

	/**
	* 设置字段PopFlag的值，该字段的<br>
	* 字段名称 :PopFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPopFlag(long popFlag) {
		this.PopFlag = new Long(popFlag);
    }

	/**
	* 设置字段PopFlag的值，该字段的<br>
	* 字段名称 :PopFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPopFlag(String popFlag) {
		if (popFlag == null){
			this.PopFlag = null;
			return;
		}
		this.PopFlag = new Long(popFlag);
    }

	/**
	* 获取字段Subject的值，该字段的<br>
	* 字段名称 :Subject<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSubject() {
		return Subject;
	}

	/**
	* 设置字段Subject的值，该字段的<br>
	* 字段名称 :Subject<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSubject(String subject) {
		this.Subject = subject;
    }

	/**
	* 获取字段Content的值，该字段的<br>
	* 字段名称 :Content<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getContent() {
		return Content;
	}

	/**
	* 设置字段Content的值，该字段的<br>
	* 字段名称 :Content<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setContent(String content) {
		this.Content = content;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddTime(Date addTime) {
		this.AddTime = addTime;
    }

}