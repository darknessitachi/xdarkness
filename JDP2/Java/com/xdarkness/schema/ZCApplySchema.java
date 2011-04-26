package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCApply<br>
 * 表代码：ZCApply<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID<br>
 */
public class ZCApplySchema extends Schema {
	private Long ID;

	private Long SiteID;

	private String Name;

	private String Gender;

	private Date BirthDate;

	private String Picture;

	private String Ethnicity;

	private String NativePlace;

	private String Political;

	private String CertNumber;

	private String Phone;

	private String Mobile;

	private String Address;

	private String Postcode;

	private String Email;

	private String ForeignLanguage;

	private String LanguageLevel;

	private String Authentification;

	private String PersonIntro;

	private String Honour;

	private String PracticeExperience;

	private String RegisteredPlace;

	private String EduLevel;

	private String University;

	private String Speacility;

	private String WillPosition;

	private String AuditUser;

	private String AuditStatus;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private Date AddTime;

	private String AddUser;

	private Date ModifyTime;

	private String ModifyUser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 20 , 0 , false , false),
		new SchemaColumn("Name", DataColumn.STRING, 2, 25 , 0 , false , false),
		new SchemaColumn("Gender", DataColumn.STRING, 3, 1 , 0 , false , false),
		new SchemaColumn("BirthDate", DataColumn.DATETIME, 4, 0 , 0 , false , false),
		new SchemaColumn("Picture", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("Ethnicity", DataColumn.STRING, 6, 3 , 0 , false , false),
		new SchemaColumn("NativePlace", DataColumn.STRING, 7, 10 , 0 , false , false),
		new SchemaColumn("Political", DataColumn.STRING, 8, 3 , 0 , false , false),
		new SchemaColumn("CertNumber", DataColumn.STRING, 9, 20 , 0 , false , false),
		new SchemaColumn("Phone", DataColumn.STRING, 10, 20 , 0 , false , false),
		new SchemaColumn("Mobile", DataColumn.STRING, 11, 20 , 0 , false , false),
		new SchemaColumn("Address", DataColumn.STRING, 12, 200 , 0 , false , false),
		new SchemaColumn("Postcode", DataColumn.STRING, 13, 10 , 0 , false , false),
		new SchemaColumn("Email", DataColumn.STRING, 14, 100 , 0 , false , false),
		new SchemaColumn("ForeignLanguage", DataColumn.STRING, 15, 50 , 0 , false , false),
		new SchemaColumn("LanguageLevel", DataColumn.STRING, 16, 50 , 0 , false , false),
		new SchemaColumn("Authentification", DataColumn.STRING, 17, 200 , 0 , false , false),
		new SchemaColumn("PersonIntro", DataColumn.STRING, 18, 1500 , 0 , false , false),
		new SchemaColumn("Honour", DataColumn.STRING, 19, 1500 , 0 , false , false),
		new SchemaColumn("PracticeExperience", DataColumn.STRING, 20, 2000 , 0 , false , false),
		new SchemaColumn("RegisteredPlace", DataColumn.STRING, 21, 10 , 0 , false , false),
		new SchemaColumn("EduLevel", DataColumn.STRING, 22, 3 , 0 , false , false),
		new SchemaColumn("University", DataColumn.STRING, 23, 40 , 0 , false , false),
		new SchemaColumn("Speacility", DataColumn.STRING, 24, 100 , 0 , false , false),
		new SchemaColumn("WillPosition", DataColumn.STRING, 25, 50 , 0 , false , false),
		new SchemaColumn("AuditUser", DataColumn.STRING, 26, 50 , 0 , false , false),
		new SchemaColumn("AuditStatus", DataColumn.STRING, 27, 5 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 28, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 29, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 30, 100 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 31, 100 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 32, 0 , 0 , true , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 33, 100 , 0 , true , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 34, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 35, 100 , 0 , false , false)
	};

	public static final String _TableCode = "ZCApply";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into ZCApply values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCApply set ID=?,SiteID=?,Name=?,Gender=?,BirthDate=?,Picture=?,Ethnicity=?,NativePlace=?,Political=?,CertNumber=?,Phone=?,Mobile=?,Address=?,Postcode=?,Email=?,ForeignLanguage=?,LanguageLevel=?,Authentification=?,PersonIntro=?,Honour=?,PracticeExperience=?,RegisteredPlace=?,EduLevel=?,University=?,Speacility=?,WillPosition=?,AuditUser=?,AuditStatus=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddTime=?,AddUser=?,ModifyTime=?,ModifyUser=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCApply  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCApply  where ID=?";

	public ZCApplySchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[36];
	}

	protected Schema newInstance(){
		return new ZCApplySchema();
	}

	protected SchemaSet newSet(){
		return new ZCApplySet();
	}

	public ZCApplySet query() {
		return query(null, -1, -1);
	}

	public ZCApplySet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCApplySet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCApplySet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCApplySet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){Name = (String)v;return;}
		if (i == 3){Gender = (String)v;return;}
		if (i == 4){BirthDate = (Date)v;return;}
		if (i == 5){Picture = (String)v;return;}
		if (i == 6){Ethnicity = (String)v;return;}
		if (i == 7){NativePlace = (String)v;return;}
		if (i == 8){Political = (String)v;return;}
		if (i == 9){CertNumber = (String)v;return;}
		if (i == 10){Phone = (String)v;return;}
		if (i == 11){Mobile = (String)v;return;}
		if (i == 12){Address = (String)v;return;}
		if (i == 13){Postcode = (String)v;return;}
		if (i == 14){Email = (String)v;return;}
		if (i == 15){ForeignLanguage = (String)v;return;}
		if (i == 16){LanguageLevel = (String)v;return;}
		if (i == 17){Authentification = (String)v;return;}
		if (i == 18){PersonIntro = (String)v;return;}
		if (i == 19){Honour = (String)v;return;}
		if (i == 20){PracticeExperience = (String)v;return;}
		if (i == 21){RegisteredPlace = (String)v;return;}
		if (i == 22){EduLevel = (String)v;return;}
		if (i == 23){University = (String)v;return;}
		if (i == 24){Speacility = (String)v;return;}
		if (i == 25){WillPosition = (String)v;return;}
		if (i == 26){AuditUser = (String)v;return;}
		if (i == 27){AuditStatus = (String)v;return;}
		if (i == 28){Prop1 = (String)v;return;}
		if (i == 29){Prop2 = (String)v;return;}
		if (i == 30){Prop3 = (String)v;return;}
		if (i == 31){Prop4 = (String)v;return;}
		if (i == 32){AddTime = (Date)v;return;}
		if (i == 33){AddUser = (String)v;return;}
		if (i == 34){ModifyTime = (Date)v;return;}
		if (i == 35){ModifyUser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return Name;}
		if (i == 3){return Gender;}
		if (i == 4){return BirthDate;}
		if (i == 5){return Picture;}
		if (i == 6){return Ethnicity;}
		if (i == 7){return NativePlace;}
		if (i == 8){return Political;}
		if (i == 9){return CertNumber;}
		if (i == 10){return Phone;}
		if (i == 11){return Mobile;}
		if (i == 12){return Address;}
		if (i == 13){return Postcode;}
		if (i == 14){return Email;}
		if (i == 15){return ForeignLanguage;}
		if (i == 16){return LanguageLevel;}
		if (i == 17){return Authentification;}
		if (i == 18){return PersonIntro;}
		if (i == 19){return Honour;}
		if (i == 20){return PracticeExperience;}
		if (i == 21){return RegisteredPlace;}
		if (i == 22){return EduLevel;}
		if (i == 23){return University;}
		if (i == 24){return Speacility;}
		if (i == 25){return WillPosition;}
		if (i == 26){return AuditUser;}
		if (i == 27){return AuditStatus;}
		if (i == 28){return Prop1;}
		if (i == 29){return Prop2;}
		if (i == 30){return Prop3;}
		if (i == 31){return Prop4;}
		if (i == 32){return AddTime;}
		if (i == 33){return AddUser;}
		if (i == 34){return ModifyTime;}
		if (i == 35){return ModifyUser;}
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
	* 获取字段SiteID的值，该字段的<br>
	* 字段名称 :SiteID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getSiteID() {
		if(SiteID==null){return 0;}
		return SiteID.longValue();
	}

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :SiteID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :SiteID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSiteID(String siteID) {
		if (siteID == null){
			this.SiteID = null;
			return;
		}
		this.SiteID = new Long(siteID);
    }

	/**
	* 获取字段Name的值，该字段的<br>
	* 字段名称 :Name<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* 设置字段Name的值，该字段的<br>
	* 字段名称 :Name<br>
	* 数据类型 :varchar(25)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* 获取字段Gender的值，该字段的<br>
	* 字段名称 :Gender<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getGender() {
		return Gender;
	}

	/**
	* 设置字段Gender的值，该字段的<br>
	* 字段名称 :Gender<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setGender(String gender) {
		this.Gender = gender;
    }

	/**
	* 获取字段BirthDate的值，该字段的<br>
	* 字段名称 :BirthDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getBirthDate() {
		return BirthDate;
	}

	/**
	* 设置字段BirthDate的值，该字段的<br>
	* 字段名称 :BirthDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBirthDate(Date birthDate) {
		this.BirthDate = birthDate;
    }

	/**
	* 获取字段Picture的值，该字段的<br>
	* 字段名称 :Picture<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPicture() {
		return Picture;
	}

	/**
	* 设置字段Picture的值，该字段的<br>
	* 字段名称 :Picture<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPicture(String picture) {
		this.Picture = picture;
    }

	/**
	* 获取字段Ethnicity的值，该字段的<br>
	* 字段名称 :Ethnicity<br>
	* 数据类型 :varchar(3)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getEthnicity() {
		return Ethnicity;
	}

	/**
	* 设置字段Ethnicity的值，该字段的<br>
	* 字段名称 :Ethnicity<br>
	* 数据类型 :varchar(3)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEthnicity(String ethnicity) {
		this.Ethnicity = ethnicity;
    }

	/**
	* 获取字段NativePlace的值，该字段的<br>
	* 字段名称 :NativePlace<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getNativePlace() {
		return NativePlace;
	}

	/**
	* 设置字段NativePlace的值，该字段的<br>
	* 字段名称 :NativePlace<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setNativePlace(String nativePlace) {
		this.NativePlace = nativePlace;
    }

	/**
	* 获取字段Political的值，该字段的<br>
	* 字段名称 :Political<br>
	* 数据类型 :varchar(3)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPolitical() {
		return Political;
	}

	/**
	* 设置字段Political的值，该字段的<br>
	* 字段名称 :Political<br>
	* 数据类型 :varchar(3)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPolitical(String political) {
		this.Political = political;
    }

	/**
	* 获取字段CertNumber的值，该字段的<br>
	* 字段名称 :CertNumber<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCertNumber() {
		return CertNumber;
	}

	/**
	* 设置字段CertNumber的值，该字段的<br>
	* 字段名称 :CertNumber<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCertNumber(String certNumber) {
		this.CertNumber = certNumber;
    }

	/**
	* 获取字段Phone的值，该字段的<br>
	* 字段名称 :Phone<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPhone() {
		return Phone;
	}

	/**
	* 设置字段Phone的值，该字段的<br>
	* 字段名称 :Phone<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPhone(String phone) {
		this.Phone = phone;
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
	* 获取字段Postcode的值，该字段的<br>
	* 字段名称 :Postcode<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPostcode() {
		return Postcode;
	}

	/**
	* 设置字段Postcode的值，该字段的<br>
	* 字段名称 :Postcode<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPostcode(String postcode) {
		this.Postcode = postcode;
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
	* 获取字段ForeignLanguage的值，该字段的<br>
	* 字段名称 :ForeignLanguage<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getForeignLanguage() {
		return ForeignLanguage;
	}

	/**
	* 设置字段ForeignLanguage的值，该字段的<br>
	* 字段名称 :ForeignLanguage<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setForeignLanguage(String foreignLanguage) {
		this.ForeignLanguage = foreignLanguage;
    }

	/**
	* 获取字段LanguageLevel的值，该字段的<br>
	* 字段名称 :LanguageLevel<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLanguageLevel() {
		return LanguageLevel;
	}

	/**
	* 设置字段LanguageLevel的值，该字段的<br>
	* 字段名称 :LanguageLevel<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLanguageLevel(String languageLevel) {
		this.LanguageLevel = languageLevel;
    }

	/**
	* 获取字段Authentification的值，该字段的<br>
	* 字段名称 :Authentification<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAuthentification() {
		return Authentification;
	}

	/**
	* 设置字段Authentification的值，该字段的<br>
	* 字段名称 :Authentification<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAuthentification(String authentification) {
		this.Authentification = authentification;
    }

	/**
	* 获取字段PersonIntro的值，该字段的<br>
	* 字段名称 :PersonIntro<br>
	* 数据类型 :varchar(1500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPersonIntro() {
		return PersonIntro;
	}

	/**
	* 设置字段PersonIntro的值，该字段的<br>
	* 字段名称 :PersonIntro<br>
	* 数据类型 :varchar(1500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPersonIntro(String personIntro) {
		this.PersonIntro = personIntro;
    }

	/**
	* 获取字段Honour的值，该字段的<br>
	* 字段名称 :Honour<br>
	* 数据类型 :varchar(1500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getHonour() {
		return Honour;
	}

	/**
	* 设置字段Honour的值，该字段的<br>
	* 字段名称 :Honour<br>
	* 数据类型 :varchar(1500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setHonour(String honour) {
		this.Honour = honour;
    }

	/**
	* 获取字段PracticeExperience的值，该字段的<br>
	* 字段名称 :PracticeExperience<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPracticeExperience() {
		return PracticeExperience;
	}

	/**
	* 设置字段PracticeExperience的值，该字段的<br>
	* 字段名称 :PracticeExperience<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPracticeExperience(String practiceExperience) {
		this.PracticeExperience = practiceExperience;
    }

	/**
	* 获取字段RegisteredPlace的值，该字段的<br>
	* 字段名称 :RegisteredPlace<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRegisteredPlace() {
		return RegisteredPlace;
	}

	/**
	* 设置字段RegisteredPlace的值，该字段的<br>
	* 字段名称 :RegisteredPlace<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRegisteredPlace(String registeredPlace) {
		this.RegisteredPlace = registeredPlace;
    }

	/**
	* 获取字段EduLevel的值，该字段的<br>
	* 字段名称 :EduLevel<br>
	* 数据类型 :varchar(3)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getEduLevel() {
		return EduLevel;
	}

	/**
	* 设置字段EduLevel的值，该字段的<br>
	* 字段名称 :EduLevel<br>
	* 数据类型 :varchar(3)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEduLevel(String eduLevel) {
		this.EduLevel = eduLevel;
    }

	/**
	* 获取字段University的值，该字段的<br>
	* 字段名称 :University<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getUniversity() {
		return University;
	}

	/**
	* 设置字段University的值，该字段的<br>
	* 字段名称 :University<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUniversity(String university) {
		this.University = university;
    }

	/**
	* 获取字段Speacility的值，该字段的<br>
	* 字段名称 :Speacility<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSpeacility() {
		return Speacility;
	}

	/**
	* 设置字段Speacility的值，该字段的<br>
	* 字段名称 :Speacility<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSpeacility(String speacility) {
		this.Speacility = speacility;
    }

	/**
	* 获取字段WillPosition的值，该字段的<br>
	* 字段名称 :WillPosition<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getWillPosition() {
		return WillPosition;
	}

	/**
	* 设置字段WillPosition的值，该字段的<br>
	* 字段名称 :WillPosition<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setWillPosition(String willPosition) {
		this.WillPosition = willPosition;
    }

	/**
	* 获取字段AuditUser的值，该字段的<br>
	* 字段名称 :AuditUser<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAuditUser() {
		return AuditUser;
	}

	/**
	* 设置字段AuditUser的值，该字段的<br>
	* 字段名称 :AuditUser<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAuditUser(String auditUser) {
		this.AuditUser = auditUser;
    }

	/**
	* 获取字段AuditStatus的值，该字段的<br>
	* 字段名称 :AuditStatus<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAuditStatus() {
		return AuditStatus;
	}

	/**
	* 设置字段AuditStatus的值，该字段的<br>
	* 字段名称 :AuditStatus<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAuditStatus(String auditStatus) {
		this.AuditStatus = auditStatus;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
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
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
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

	/**
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :ModifyUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :ModifyUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

}