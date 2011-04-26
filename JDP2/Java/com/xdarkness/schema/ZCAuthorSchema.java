package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCAuthor<br>
 * 表代码：ZCAuthor<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID<br>
 */
public class ZCAuthorSchema extends Schema {
	private Long ID;

	private String Author;

	private String RealName;

	private String Sex;

	private String Email;

	private String Tel;

	private String Mobile;

	private String Address;

	private String Zipcode;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("Author", DataColumn.STRING, 1, 100 , 0 , true , false),
		new SchemaColumn("RealName", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("Sex", DataColumn.STRING, 3, 10 , 0 , false , false),
		new SchemaColumn("Email", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("Tel", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("Mobile", DataColumn.STRING, 6, 100 , 0 , false , false),
		new SchemaColumn("Address", DataColumn.STRING, 7, 200 , 0 , false , false),
		new SchemaColumn("Zipcode", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 13, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 14, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 15, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 16, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCAuthor";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into ZCAuthor values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCAuthor set ID=?,Author=?,RealName=?,Sex=?,Email=?,Tel=?,Mobile=?,Address=?,Zipcode=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCAuthor  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCAuthor  where ID=?";

	public ZCAuthorSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[17];
	}

	protected Schema newInstance(){
		return new ZCAuthorSchema();
	}

	protected SchemaSet newSet(){
		return new ZCAuthorSet();
	}

	public ZCAuthorSet query() {
		return query(null, -1, -1);
	}

	public ZCAuthorSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCAuthorSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCAuthorSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCAuthorSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){Author = (String)v;return;}
		if (i == 2){RealName = (String)v;return;}
		if (i == 3){Sex = (String)v;return;}
		if (i == 4){Email = (String)v;return;}
		if (i == 5){Tel = (String)v;return;}
		if (i == 6){Mobile = (String)v;return;}
		if (i == 7){Address = (String)v;return;}
		if (i == 8){Zipcode = (String)v;return;}
		if (i == 9){Prop1 = (String)v;return;}
		if (i == 10){Prop2 = (String)v;return;}
		if (i == 11){Prop3 = (String)v;return;}
		if (i == 12){Prop4 = (String)v;return;}
		if (i == 13){AddUser = (String)v;return;}
		if (i == 14){AddTime = (Date)v;return;}
		if (i == 15){ModifyUser = (String)v;return;}
		if (i == 16){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return Author;}
		if (i == 2){return RealName;}
		if (i == 3){return Sex;}
		if (i == 4){return Email;}
		if (i == 5){return Tel;}
		if (i == 6){return Mobile;}
		if (i == 7){return Address;}
		if (i == 8){return Zipcode;}
		if (i == 9){return Prop1;}
		if (i == 10){return Prop2;}
		if (i == 11){return Prop3;}
		if (i == 12){return Prop4;}
		if (i == 13){return AddUser;}
		if (i == 14){return AddTime;}
		if (i == 15){return ModifyUser;}
		if (i == 16){return ModifyTime;}
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
	* 获取字段Author的值，该字段的<br>
	* 字段名称 :Author<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAuthor() {
		return Author;
	}

	/**
	* 设置字段Author的值，该字段的<br>
	* 字段名称 :Author<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAuthor(String author) {
		this.Author = author;
    }

	/**
	* 获取字段RealName的值，该字段的<br>
	* 字段名称 :RealName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getRealName() {
		return RealName;
	}

	/**
	* 设置字段RealName的值，该字段的<br>
	* 字段名称 :RealName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setRealName(String realName) {
		this.RealName = realName;
    }

	/**
	* 获取字段Sex的值，该字段的<br>
	* 字段名称 :Sex<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSex() {
		return Sex;
	}

	/**
	* 设置字段Sex的值，该字段的<br>
	* 字段名称 :Sex<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSex(String sex) {
		this.Sex = sex;
    }

	/**
	* 获取字段Email的值，该字段的<br>
	* 字段名称 :Email<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getEmail() {
		return Email;
	}

	/**
	* 设置字段Email的值，该字段的<br>
	* 字段名称 :Email<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEmail(String email) {
		this.Email = email;
    }

	/**
	* 获取字段Tel的值，该字段的<br>
	* 字段名称 :Tel<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTel() {
		return Tel;
	}

	/**
	* 设置字段Tel的值，该字段的<br>
	* 字段名称 :Tel<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTel(String tel) {
		this.Tel = tel;
    }

	/**
	* 获取字段Mobile的值，该字段的<br>
	* 字段名称 :Mobile<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMobile() {
		return Mobile;
	}

	/**
	* 设置字段Mobile的值，该字段的<br>
	* 字段名称 :Mobile<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMobile(String mobile) {
		this.Mobile = mobile;
    }

	/**
	* 获取字段Address的值，该字段的<br>
	* 字段名称 :Address<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddress() {
		return Address;
	}

	/**
	* 设置字段Address的值，该字段的<br>
	* 字段名称 :Address<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddress(String address) {
		this.Address = address;
    }

	/**
	* 获取字段Zipcode的值，该字段的<br>
	* 字段名称 :Zipcode<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getZipcode() {
		return Zipcode;
	}

	/**
	* 设置字段Zipcode的值，该字段的<br>
	* 字段名称 :Zipcode<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setZipcode(String zipcode) {
		this.Zipcode = zipcode;
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
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
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