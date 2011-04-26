package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZDMemberAddr<br>
 * 表代码：ZDMemberAddr<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID<br>
 */
public class ZDMemberAddrSchema extends Schema {
	private Long ID;

	private String UserName;

	private String RealName;

	private String Country;

	private String Province;

	private String City;

	private String District;

	private String Address;

	private String ZipCode;

	private String Tel;

	private String Mobile;

	private String Email;

	private String IsDefault;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("UserName", DataColumn.STRING, 1, 200 , 0 , true , false),
		new SchemaColumn("RealName", DataColumn.STRING, 2, 100 , 0 , false , false),
		new SchemaColumn("Country", DataColumn.STRING, 3, 30 , 0 , false , false),
		new SchemaColumn("Province", DataColumn.STRING, 4, 6 , 0 , false , false),
		new SchemaColumn("City", DataColumn.STRING, 5, 6 , 0 , false , false),
		new SchemaColumn("District", DataColumn.STRING, 6, 6 , 0 , false , false),
		new SchemaColumn("Address", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("ZipCode", DataColumn.STRING, 8, 10 , 0 , false , false),
		new SchemaColumn("Tel", DataColumn.STRING, 9, 20 , 0 , false , false),
		new SchemaColumn("Mobile", DataColumn.STRING, 10, 20 , 0 , false , false),
		new SchemaColumn("Email", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("IsDefault", DataColumn.STRING, 12, 2 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 13, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 14, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 15, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 16, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZDMemberAddr";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into ZDMemberAddr values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDMemberAddr set ID=?,UserName=?,RealName=?,Country=?,Province=?,City=?,District=?,Address=?,ZipCode=?,Tel=?,Mobile=?,Email=?,IsDefault=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZDMemberAddr  where ID=?";

	protected static final String _FillAllSQL = "select * from ZDMemberAddr  where ID=?";

	public ZDMemberAddrSchema(){
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
		return new ZDMemberAddrSchema();
	}

	protected SchemaSet newSet(){
		return new ZDMemberAddrSet();
	}

	public ZDMemberAddrSet query() {
		return query(null, -1, -1);
	}

	public ZDMemberAddrSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDMemberAddrSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDMemberAddrSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDMemberAddrSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){UserName = (String)v;return;}
		if (i == 2){RealName = (String)v;return;}
		if (i == 3){Country = (String)v;return;}
		if (i == 4){Province = (String)v;return;}
		if (i == 5){City = (String)v;return;}
		if (i == 6){District = (String)v;return;}
		if (i == 7){Address = (String)v;return;}
		if (i == 8){ZipCode = (String)v;return;}
		if (i == 9){Tel = (String)v;return;}
		if (i == 10){Mobile = (String)v;return;}
		if (i == 11){Email = (String)v;return;}
		if (i == 12){IsDefault = (String)v;return;}
		if (i == 13){AddUser = (String)v;return;}
		if (i == 14){AddTime = (Date)v;return;}
		if (i == 15){ModifyUser = (String)v;return;}
		if (i == 16){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return UserName;}
		if (i == 2){return RealName;}
		if (i == 3){return Country;}
		if (i == 4){return Province;}
		if (i == 5){return City;}
		if (i == 6){return District;}
		if (i == 7){return Address;}
		if (i == 8){return ZipCode;}
		if (i == 9){return Tel;}
		if (i == 10){return Mobile;}
		if (i == 11){return Email;}
		if (i == 12){return IsDefault;}
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
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRealName() {
		return RealName;
	}

	/**
	* 设置字段RealName的值，该字段的<br>
	* 字段名称 :RealName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRealName(String realName) {
		this.RealName = realName;
    }

	/**
	* 获取字段Country的值，该字段的<br>
	* 字段名称 :Country<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCountry() {
		return Country;
	}

	/**
	* 设置字段Country的值，该字段的<br>
	* 字段名称 :Country<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCountry(String country) {
		this.Country = country;
    }

	/**
	* 获取字段Province的值，该字段的<br>
	* 字段名称 :Province<br>
	* 数据类型 :varchar(6)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProvince() {
		return Province;
	}

	/**
	* 设置字段Province的值，该字段的<br>
	* 字段名称 :Province<br>
	* 数据类型 :varchar(6)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProvince(String province) {
		this.Province = province;
    }

	/**
	* 获取字段City的值，该字段的<br>
	* 字段名称 :City<br>
	* 数据类型 :varchar(6)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCity() {
		return City;
	}

	/**
	* 设置字段City的值，该字段的<br>
	* 字段名称 :City<br>
	* 数据类型 :varchar(6)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCity(String city) {
		this.City = city;
    }

	/**
	* 获取字段District的值，该字段的<br>
	* 字段名称 :District<br>
	* 数据类型 :varchar(6)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDistrict() {
		return District;
	}

	/**
	* 设置字段District的值，该字段的<br>
	* 字段名称 :District<br>
	* 数据类型 :varchar(6)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDistrict(String district) {
		this.District = district;
    }

	/**
	* 获取字段Address的值，该字段的<br>
	* 字段名称 :Address<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddress() {
		return Address;
	}

	/**
	* 设置字段Address的值，该字段的<br>
	* 字段名称 :Address<br>
	* 数据类型 :varchar(255)<br>
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
	* 获取字段IsDefault的值，该字段的<br>
	* 字段名称 :IsDefault<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIsDefault() {
		return IsDefault;
	}

	/**
	* 设置字段IsDefault的值，该字段的<br>
	* 字段名称 :IsDefault<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsDefault(String isDefault) {
		this.IsDefault = isDefault;
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