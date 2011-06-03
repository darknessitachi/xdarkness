package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;

/**
 * 表名称：ZDMemberCompanyInfo<br>
 * 表代码：ZDMemberCompanyInfo<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：UserName<br>
 */
public class ZDMemberCompanyInfoSchema extends Schema {
	private String UserName;

	private String CompanyName;

	private String Scale;

	private String BusinessType;

	private String Products;

	private String CompanySite;

	private String Tel;

	private String Fax;

	private String LinkMan;

	private String Mobile;

	private String Email;

	private String Address;

	private String ZipCode;

	private String Pic;

	private String Intro;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("UserName", DataColumn.STRING, 0, 50 , 0 , true , true),
		new SchemaColumn("CompanyName", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("Scale", DataColumn.STRING, 2, 10 , 0 , false , false),
		new SchemaColumn("BusinessType", DataColumn.STRING, 3, 10 , 0 , false , false),
		new SchemaColumn("Products", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("CompanySite", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("Tel", DataColumn.STRING, 6, 20 , 0 , false , false),
		new SchemaColumn("Fax", DataColumn.STRING, 7, 20 , 0 , false , false),
		new SchemaColumn("LinkMan", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("Mobile", DataColumn.STRING, 9, 20 , 0 , false , false),
		new SchemaColumn("Email", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("Address", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("ZipCode", DataColumn.STRING, 12, 10 , 0 , false , false),
		new SchemaColumn("Pic", DataColumn.STRING, 13, 100 , 0 , false , false),
		new SchemaColumn("Intro", DataColumn.CLOB, 14, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZDMemberCompanyInfo";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into ZDMemberCompanyInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDMemberCompanyInfo set UserName=?,CompanyName=?,Scale=?,BusinessType=?,Products=?,CompanySite=?,Tel=?,Fax=?,LinkMan=?,Mobile=?,Email=?,Address=?,ZipCode=?,Pic=?,Intro=? where UserName=?";

	protected static final String _DeleteSQL = "delete from ZDMemberCompanyInfo  where UserName=?";

	protected static final String _FillAllSQL = "select * from ZDMemberCompanyInfo  where UserName=?";

	public ZDMemberCompanyInfoSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[15];
	}

	protected Schema newInstance(){
		return new ZDMemberCompanyInfoSchema();
	}

	protected SchemaSet newSet(){
		return new ZDMemberCompanyInfoSet();
	}

	public ZDMemberCompanyInfoSet query() {
		return query(null, -1, -1);
	}

	public ZDMemberCompanyInfoSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDMemberCompanyInfoSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDMemberCompanyInfoSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDMemberCompanyInfoSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){UserName = (String)v;return;}
		if (i == 1){CompanyName = (String)v;return;}
		if (i == 2){Scale = (String)v;return;}
		if (i == 3){BusinessType = (String)v;return;}
		if (i == 4){Products = (String)v;return;}
		if (i == 5){CompanySite = (String)v;return;}
		if (i == 6){Tel = (String)v;return;}
		if (i == 7){Fax = (String)v;return;}
		if (i == 8){LinkMan = (String)v;return;}
		if (i == 9){Mobile = (String)v;return;}
		if (i == 10){Email = (String)v;return;}
		if (i == 11){Address = (String)v;return;}
		if (i == 12){ZipCode = (String)v;return;}
		if (i == 13){Pic = (String)v;return;}
		if (i == 14){Intro = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return UserName;}
		if (i == 1){return CompanyName;}
		if (i == 2){return Scale;}
		if (i == 3){return BusinessType;}
		if (i == 4){return Products;}
		if (i == 5){return CompanySite;}
		if (i == 6){return Tel;}
		if (i == 7){return Fax;}
		if (i == 8){return LinkMan;}
		if (i == 9){return Mobile;}
		if (i == 10){return Email;}
		if (i == 11){return Address;}
		if (i == 12){return ZipCode;}
		if (i == 13){return Pic;}
		if (i == 14){return Intro;}
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
	* 获取字段CompanyName的值，该字段的<br>
	* 字段名称 :CompanyName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCompanyName() {
		return CompanyName;
	}

	/**
	* 设置字段CompanyName的值，该字段的<br>
	* 字段名称 :CompanyName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCompanyName(String companyName) {
		this.CompanyName = companyName;
    }

	/**
	* 获取字段Scale的值，该字段的<br>
	* 字段名称 :Scale<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getScale() {
		return Scale;
	}

	/**
	* 设置字段Scale的值，该字段的<br>
	* 字段名称 :Scale<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setScale(String scale) {
		this.Scale = scale;
    }

	/**
	* 获取字段BusinessType的值，该字段的<br>
	* 字段名称 :BusinessType<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBusinessType() {
		return BusinessType;
	}

	/**
	* 设置字段BusinessType的值，该字段的<br>
	* 字段名称 :BusinessType<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBusinessType(String businessType) {
		this.BusinessType = businessType;
    }

	/**
	* 获取字段Products的值，该字段的<br>
	* 字段名称 :Products<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProducts() {
		return Products;
	}

	/**
	* 设置字段Products的值，该字段的<br>
	* 字段名称 :Products<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProducts(String products) {
		this.Products = products;
    }

	/**
	* 获取字段CompanySite的值，该字段的<br>
	* 字段名称 :CompanySite<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCompanySite() {
		return CompanySite;
	}

	/**
	* 设置字段CompanySite的值，该字段的<br>
	* 字段名称 :CompanySite<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCompanySite(String companySite) {
		this.CompanySite = companySite;
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
	* 获取字段Fax的值，该字段的<br>
	* 字段名称 :Fax<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFax() {
		return Fax;
	}

	/**
	* 设置字段Fax的值，该字段的<br>
	* 字段名称 :Fax<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFax(String fax) {
		this.Fax = fax;
    }

	/**
	* 获取字段LinkMan的值，该字段的<br>
	* 字段名称 :LinkMan<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLinkMan() {
		return LinkMan;
	}

	/**
	* 设置字段LinkMan的值，该字段的<br>
	* 字段名称 :LinkMan<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLinkMan(String linkMan) {
		this.LinkMan = linkMan;
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
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getEmail() {
		return Email;
	}

	/**
	* 设置字段Email的值，该字段的<br>
	* 字段名称 :Email<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEmail(String email) {
		this.Email = email;
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

	/**
	* 获取字段Pic的值，该字段的<br>
	* 字段名称 :Pic<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPic() {
		return Pic;
	}

	/**
	* 设置字段Pic的值，该字段的<br>
	* 字段名称 :Pic<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPic(String pic) {
		this.Pic = pic;
    }

	/**
	* 获取字段Intro的值，该字段的<br>
	* 字段名称 :Intro<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIntro() {
		return Intro;
	}

	/**
	* 设置字段Intro的值，该字段的<br>
	* 字段名称 :Intro<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIntro(String intro) {
		this.Intro = intro;
    }

}