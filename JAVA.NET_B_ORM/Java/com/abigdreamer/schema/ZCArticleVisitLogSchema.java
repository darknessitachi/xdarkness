package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCArticleVisitLog<br>
 * 表代码：ZCArticleVisitLog<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID<br>
 */
public class ZCArticleVisitLogSchema extends Schema {
	private Long ID;

	private Long ArticleID;

	private String ArticleTitle;

	private String UserName;

	private String RealName;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String IP;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("ArticleID", DataColumn.LONG, 1, 20 , 0 , true , false),
		new SchemaColumn("ArticleTitle", DataColumn.STRING, 2, 200 , 0 , false , false),
		new SchemaColumn("UserName", DataColumn.STRING, 3, 200 , 0 , true , false),
		new SchemaColumn("RealName", DataColumn.STRING, 4, 200 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("IP", DataColumn.STRING, 8, 50 , 0 , true , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 9, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 10, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 11, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 12, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCArticleVisitLog";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into ZCArticleVisitLog values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCArticleVisitLog set ID=?,ArticleID=?,ArticleTitle=?,UserName=?,RealName=?,Prop1=?,Prop2=?,Prop3=?,IP=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCArticleVisitLog  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCArticleVisitLog  where ID=?";

	public ZCArticleVisitLogSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[13];
	}

	protected Schema newInstance(){
		return new ZCArticleVisitLogSchema();
	}

	protected SchemaSet newSet(){
		return new ZCArticleVisitLogSet();
	}

	public ZCArticleVisitLogSet query() {
		return query(null, -1, -1);
	}

	public ZCArticleVisitLogSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCArticleVisitLogSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCArticleVisitLogSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCArticleVisitLogSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){ArticleID = null;}else{ArticleID = new Long(v.toString());}return;}
		if (i == 2){ArticleTitle = (String)v;return;}
		if (i == 3){UserName = (String)v;return;}
		if (i == 4){RealName = (String)v;return;}
		if (i == 5){Prop1 = (String)v;return;}
		if (i == 6){Prop2 = (String)v;return;}
		if (i == 7){Prop3 = (String)v;return;}
		if (i == 8){IP = (String)v;return;}
		if (i == 9){AddUser = (String)v;return;}
		if (i == 10){AddTime = (Date)v;return;}
		if (i == 11){ModifyUser = (String)v;return;}
		if (i == 12){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return ArticleID;}
		if (i == 2){return ArticleTitle;}
		if (i == 3){return UserName;}
		if (i == 4){return RealName;}
		if (i == 5){return Prop1;}
		if (i == 6){return Prop2;}
		if (i == 7){return Prop3;}
		if (i == 8){return IP;}
		if (i == 9){return AddUser;}
		if (i == 10){return AddTime;}
		if (i == 11){return ModifyUser;}
		if (i == 12){return ModifyTime;}
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
	* 获取字段ArticleID的值，该字段的<br>
	* 字段名称 :ArticleID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getArticleID() {
		if(ArticleID==null){return 0;}
		return ArticleID.longValue();
	}

	/**
	* 设置字段ArticleID的值，该字段的<br>
	* 字段名称 :ArticleID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setArticleID(long articleID) {
		this.ArticleID = new Long(articleID);
    }

	/**
	* 设置字段ArticleID的值，该字段的<br>
	* 字段名称 :ArticleID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setArticleID(String articleID) {
		if (articleID == null){
			this.ArticleID = null;
			return;
		}
		this.ArticleID = new Long(articleID);
    }

	/**
	* 获取字段ArticleTitle的值，该字段的<br>
	* 字段名称 :ArticleTitle<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getArticleTitle() {
		return ArticleTitle;
	}

	/**
	* 设置字段ArticleTitle的值，该字段的<br>
	* 字段名称 :ArticleTitle<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setArticleTitle(String articleTitle) {
		this.ArticleTitle = articleTitle;
    }

	/**
	* 获取字段UserName的值，该字段的<br>
	* 字段名称 :UserName<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getUserName() {
		return UserName;
	}

	/**
	* 设置字段UserName的值，该字段的<br>
	* 字段名称 :UserName<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setUserName(String userName) {
		this.UserName = userName;
    }

	/**
	* 获取字段RealName的值，该字段的<br>
	* 字段名称 :RealName<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRealName() {
		return RealName;
	}

	/**
	* 设置字段RealName的值，该字段的<br>
	* 字段名称 :RealName<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRealName(String realName) {
		this.RealName = realName;
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
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段IP的值，该字段的<br>
	* 字段名称 :IP<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getIP() {
		return IP;
	}

	/**
	* 设置字段IP的值，该字段的<br>
	* 字段名称 :IP<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setIP(String iP) {
		this.IP = iP;
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