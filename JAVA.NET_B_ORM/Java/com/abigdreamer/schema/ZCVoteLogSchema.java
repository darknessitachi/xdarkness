package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCVoteLog<br>
 * 表代码：ZCVoteLog<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID<br>
 */
public class ZCVoteLogSchema extends Schema {
	private Long ID;

	private Long VoteID;

	private String IP;

	private String Result;

	private String Prop1;

	private String Prop2;

	private String addUser;

	private Date addTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("VoteID", DataColumn.LONG, 1, 20 , 0 , true , false),
		new SchemaColumn("IP", DataColumn.STRING, 2, 20 , 0 , true , false),
		new SchemaColumn("Result", DataColumn.STRING, 3, 2000 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("addUser", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("addTime", DataColumn.DATETIME, 7, 0 , 0 , true , false)
	};

	public static final String _TableCode = "ZCVoteLog";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into ZCVoteLog values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCVoteLog set ID=?,VoteID=?,IP=?,Result=?,Prop1=?,Prop2=?,addUser=?,addTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCVoteLog  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCVoteLog  where ID=?";

	public ZCVoteLogSchema(){
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
		return new ZCVoteLogSchema();
	}

	protected SchemaSet newSet(){
		return new ZCVoteLogSet();
	}

	public ZCVoteLogSet query() {
		return query(null, -1, -1);
	}

	public ZCVoteLogSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCVoteLogSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCVoteLogSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCVoteLogSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){VoteID = null;}else{VoteID = new Long(v.toString());}return;}
		if (i == 2){IP = (String)v;return;}
		if (i == 3){Result = (String)v;return;}
		if (i == 4){Prop1 = (String)v;return;}
		if (i == 5){Prop2 = (String)v;return;}
		if (i == 6){addUser = (String)v;return;}
		if (i == 7){addTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return VoteID;}
		if (i == 2){return IP;}
		if (i == 3){return Result;}
		if (i == 4){return Prop1;}
		if (i == 5){return Prop2;}
		if (i == 6){return addUser;}
		if (i == 7){return addTime;}
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
	* 获取字段VoteID的值，该字段的<br>
	* 字段名称 :VoteID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getVoteID() {
		if(VoteID==null){return 0;}
		return VoteID.longValue();
	}

	/**
	* 设置字段VoteID的值，该字段的<br>
	* 字段名称 :VoteID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setVoteID(long voteID) {
		this.VoteID = new Long(voteID);
    }

	/**
	* 设置字段VoteID的值，该字段的<br>
	* 字段名称 :VoteID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setVoteID(String voteID) {
		if (voteID == null){
			this.VoteID = null;
			return;
		}
		this.VoteID = new Long(voteID);
    }

	/**
	* 获取字段IP的值，该字段的<br>
	* 字段名称 :IP<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getIP() {
		return IP;
	}

	/**
	* 设置字段IP的值，该字段的<br>
	* 字段名称 :IP<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setIP(String iP) {
		this.IP = iP;
    }

	/**
	* 获取字段Result的值，该字段的<br>
	* 字段名称 :Result<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getResult() {
		return Result;
	}

	/**
	* 设置字段Result的值，该字段的<br>
	* 字段名称 :Result<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setResult(String result) {
		this.Result = result;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段addUser的值，该字段的<br>
	* 字段名称 :addUser<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddUser() {
		return addUser;
	}

	/**
	* 设置字段addUser的值，该字段的<br>
	* 字段名称 :addUser<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddUser(String addUser) {
		this.addUser = addUser;
    }

	/**
	* 获取字段addTime的值，该字段的<br>
	* 字段名称 :addTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getAddTime() {
		return addTime;
	}

	/**
	* 设置字段addTime的值，该字段的<br>
	* 字段名称 :addTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
    }

}