package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;

/**
 * 表名称：ZDMemberPersonInfo<br>
 * 表代码：ZDMemberPersonInfo<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：UserName<br>
 */
public class ZDMemberPersonInfoSchema extends Schema {
	private String UserName;

	private String NickName;

	private String Birthday;

	private String QQ;

	private String MSN;

	private String Tel;

	private String Mobile;

	private String Address;

	private String ZipCode;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("UserName", DataColumn.STRING, 0, 50 , 0 , true , true),
		new SchemaColumn("NickName", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("Birthday", DataColumn.STRING, 2, 20 , 0 , false , false),
		new SchemaColumn("QQ", DataColumn.STRING, 3, 10 , 0 , false , false),
		new SchemaColumn("MSN", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("Tel", DataColumn.STRING, 5, 20 , 0 , false , false),
		new SchemaColumn("Mobile", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("Address", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("ZipCode", DataColumn.STRING, 8, 10 , 0 , false , false)
	};

	public static final String _TableCode = "ZDMemberPersonInfo";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into ZDMemberPersonInfo values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDMemberPersonInfo set UserName=?,NickName=?,Birthday=?,QQ=?,MSN=?,Tel=?,Mobile=?,Address=?,ZipCode=? where UserName=?";

	protected static final String _DeleteSQL = "delete from ZDMemberPersonInfo  where UserName=?";

	protected static final String _FillAllSQL = "select * from ZDMemberPersonInfo  where UserName=?";

	public ZDMemberPersonInfoSchema(){
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
		return new ZDMemberPersonInfoSchema();
	}

	protected SchemaSet newSet(){
		return new ZDMemberPersonInfoSet();
	}

	public ZDMemberPersonInfoSet query() {
		return query(null, -1, -1);
	}

	public ZDMemberPersonInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDMemberPersonInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDMemberPersonInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDMemberPersonInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){UserName = (String)v;return;}
		if (i == 1){NickName = (String)v;return;}
		if (i == 2){Birthday = (String)v;return;}
		if (i == 3){QQ = (String)v;return;}
		if (i == 4){MSN = (String)v;return;}
		if (i == 5){Tel = (String)v;return;}
		if (i == 6){Mobile = (String)v;return;}
		if (i == 7){Address = (String)v;return;}
		if (i == 8){ZipCode = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return UserName;}
		if (i == 1){return NickName;}
		if (i == 2){return Birthday;}
		if (i == 3){return QQ;}
		if (i == 4){return MSN;}
		if (i == 5){return Tel;}
		if (i == 6){return Mobile;}
		if (i == 7){return Address;}
		if (i == 8){return ZipCode;}
		return null;
	}

	/**
	* 获取字段UserName的值，该字段的<br>
	* 字段名称 :UserName<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getUserName() {
		return UserName;
	}

	/**
	* 设置字段UserName的值，该字段的<br>
	* 字段名称 :UserName<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setUserName(String userName) {
		this.UserName = userName;
    }

	/**
	* 获取字段NickName的值，该字段的<br>
	* 字段名称 :NickName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getNickName() {
		return NickName;
	}

	/**
	* 设置字段NickName的值，该字段的<br>
	* 字段名称 :NickName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setNickName(String nickName) {
		this.NickName = nickName;
    }

	/**
	* 获取字段Birthday的值，该字段的<br>
	* 字段名称 :Birthday<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBirthday() {
		return Birthday;
	}

	/**
	* 设置字段Birthday的值，该字段的<br>
	* 字段名称 :Birthday<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBirthday(String birthday) {
		this.Birthday = birthday;
    }

	/**
	* 获取字段QQ的值，该字段的<br>
	* 字段名称 :QQ<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getQQ() {
		return QQ;
	}

	/**
	* 设置字段QQ的值，该字段的<br>
	* 字段名称 :QQ<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setQQ(String qQ) {
		this.QQ = qQ;
    }

	/**
	* 获取字段MSN的值，该字段的<br>
	* 字段名称 :MSN<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMSN() {
		return MSN;
	}

	/**
	* 设置字段MSN的值，该字段的<br>
	* 字段名称 :MSN<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMSN(String mSN) {
		this.MSN = mSN;
    }

	/**
	* 获取字段Tel的值，该字段的<br>
	* 字段名称 :Tel<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTel() {
		return Tel;
	}

	/**
	* 设置字段Tel的值，该字段的<br>
	* 字段名称 :Tel<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTel(String tel) {
		this.Tel = tel;
    }

	/**
	* 获取字段Mobile的值，该字段的<br>
	* 字段名称 :Mobile<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMobile() {
		return Mobile;
	}

	/**
	* 设置字段Mobile的值，该字段的<br>
	* 字段名称 :Mobile<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMobile(String mobile) {
		this.Mobile = mobile;
    }

	/**
	* 获取字段Address的值，该字段的<br>
	* 字段名称 :Address<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddress() {
		return Address;
	}

	/**
	* 设置字段Address的值，该字段的<br>
	* 字段名称 :Address<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddress(String address) {
		this.Address = address;
    }

	/**
	* 获取字段ZipCode的值，该字段的<br>
	* 字段名称 :ZipCode<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getZipCode() {
		return ZipCode;
	}

	/**
	* 设置字段ZipCode的值，该字段的<br>
	* 字段名称 :ZipCode<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setZipCode(String zipCode) {
		this.ZipCode = zipCode;
    }

}