package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZSOrder备份<br>
 * 表代码：BZSOrder<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZSOrderSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private String UserName;

	private String IsValid;

	private String Status;

	private Float Amount;

	private Float SendFee;

	private Float OrderAmount;

	private Long Score;

	private String Name;

	private String Province;

	private String City;

	private String District;

	private String Address;

	private String ZipCode;

	private String Tel;

	private String Mobile;

	private String HasInvoice;

	private String InvoiceTitle;

	private Date SendBeginDate;

	private Date SendEndDate;

	private String SendTimeSlice;

	private String SendInfo;

	private String SendType;

	private String PaymentType;

	private String Memo;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 20 , 0 , true , false),
		new SchemaColumn("UserName", DataColumn.STRING, 2, 200 , 0 , false , false),
		new SchemaColumn("IsValid", DataColumn.STRING, 3, 1 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 4, 40 , 0 , false , false),
		new SchemaColumn("Amount", DataColumn.FLOAT, 5, 12 , 2 , true , false),
		new SchemaColumn("SendFee", DataColumn.FLOAT, 6, 12 , 2 , false , false),
		new SchemaColumn("OrderAmount", DataColumn.FLOAT, 7, 12 , 2 , false , false),
		new SchemaColumn("Score", DataColumn.LONG, 8, 20 , 0 , true , false),
		new SchemaColumn("Name", DataColumn.STRING, 9, 30 , 0 , false , false),
		new SchemaColumn("Province", DataColumn.STRING, 10, 6 , 0 , false , false),
		new SchemaColumn("City", DataColumn.STRING, 11, 6 , 0 , false , false),
		new SchemaColumn("District", DataColumn.STRING, 12, 6 , 0 , false , false),
		new SchemaColumn("Address", DataColumn.STRING, 13, 255 , 0 , false , false),
		new SchemaColumn("ZipCode", DataColumn.STRING, 14, 10 , 0 , false , false),
		new SchemaColumn("Tel", DataColumn.STRING, 15, 20 , 0 , false , false),
		new SchemaColumn("Mobile", DataColumn.STRING, 16, 20 , 0 , false , false),
		new SchemaColumn("HasInvoice", DataColumn.STRING, 17, 1 , 0 , true , false),
		new SchemaColumn("InvoiceTitle", DataColumn.STRING, 18, 100 , 0 , false , false),
		new SchemaColumn("SendBeginDate", DataColumn.DATETIME, 19, 0 , 0 , false , false),
		new SchemaColumn("SendEndDate", DataColumn.DATETIME, 20, 0 , 0 , false , false),
		new SchemaColumn("SendTimeSlice", DataColumn.STRING, 21, 40 , 0 , false , false),
		new SchemaColumn("SendInfo", DataColumn.STRING, 22, 200 , 0 , false , false),
		new SchemaColumn("SendType", DataColumn.STRING, 23, 40 , 0 , false , false),
		new SchemaColumn("PaymentType", DataColumn.STRING, 24, 40 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 25, 200 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 26, 200 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 27, 200 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 28, 200 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 29, 200 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 30, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 31, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 32, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 33, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 34, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 35, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 36, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 37, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZSOrder";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into BZSOrder values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZSOrder set ID=?,SiteID=?,UserName=?,IsValid=?,Status=?,Amount=?,SendFee=?,OrderAmount=?,Score=?,Name=?,Province=?,City=?,District=?,Address=?,ZipCode=?,Tel=?,Mobile=?,HasInvoice=?,InvoiceTitle=?,SendBeginDate=?,SendEndDate=?,SendTimeSlice=?,SendInfo=?,SendType=?,PaymentType=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZSOrder  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZSOrder  where ID=? and BackupNo=?";

	public BZSOrderSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[38];
	}

	protected Schema newInstance(){
		return new BZSOrderSchema();
	}

	protected SchemaSet newSet(){
		return new BZSOrderSet();
	}

	public BZSOrderSet query() {
		return query(null, -1, -1);
	}

	public BZSOrderSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZSOrderSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZSOrderSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZSOrderSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){UserName = (String)v;return;}
		if (i == 3){IsValid = (String)v;return;}
		if (i == 4){Status = (String)v;return;}
		if (i == 5){if(v==null){Amount = null;}else{Amount = new Float(v.toString());}return;}
		if (i == 6){if(v==null){SendFee = null;}else{SendFee = new Float(v.toString());}return;}
		if (i == 7){if(v==null){OrderAmount = null;}else{OrderAmount = new Float(v.toString());}return;}
		if (i == 8){if(v==null){Score = null;}else{Score = new Long(v.toString());}return;}
		if (i == 9){Name = (String)v;return;}
		if (i == 10){Province = (String)v;return;}
		if (i == 11){City = (String)v;return;}
		if (i == 12){District = (String)v;return;}
		if (i == 13){Address = (String)v;return;}
		if (i == 14){ZipCode = (String)v;return;}
		if (i == 15){Tel = (String)v;return;}
		if (i == 16){Mobile = (String)v;return;}
		if (i == 17){HasInvoice = (String)v;return;}
		if (i == 18){InvoiceTitle = (String)v;return;}
		if (i == 19){SendBeginDate = (Date)v;return;}
		if (i == 20){SendEndDate = (Date)v;return;}
		if (i == 21){SendTimeSlice = (String)v;return;}
		if (i == 22){SendInfo = (String)v;return;}
		if (i == 23){SendType = (String)v;return;}
		if (i == 24){PaymentType = (String)v;return;}
		if (i == 25){Memo = (String)v;return;}
		if (i == 26){Prop1 = (String)v;return;}
		if (i == 27){Prop2 = (String)v;return;}
		if (i == 28){Prop3 = (String)v;return;}
		if (i == 29){Prop4 = (String)v;return;}
		if (i == 30){AddUser = (String)v;return;}
		if (i == 31){AddTime = (Date)v;return;}
		if (i == 32){ModifyUser = (String)v;return;}
		if (i == 33){ModifyTime = (Date)v;return;}
		if (i == 34){BackupNo = (String)v;return;}
		if (i == 35){BackupOperator = (String)v;return;}
		if (i == 36){BackupTime = (Date)v;return;}
		if (i == 37){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return UserName;}
		if (i == 3){return IsValid;}
		if (i == 4){return Status;}
		if (i == 5){return Amount;}
		if (i == 6){return SendFee;}
		if (i == 7){return OrderAmount;}
		if (i == 8){return Score;}
		if (i == 9){return Name;}
		if (i == 10){return Province;}
		if (i == 11){return City;}
		if (i == 12){return District;}
		if (i == 13){return Address;}
		if (i == 14){return ZipCode;}
		if (i == 15){return Tel;}
		if (i == 16){return Mobile;}
		if (i == 17){return HasInvoice;}
		if (i == 18){return InvoiceTitle;}
		if (i == 19){return SendBeginDate;}
		if (i == 20){return SendEndDate;}
		if (i == 21){return SendTimeSlice;}
		if (i == 22){return SendInfo;}
		if (i == 23){return SendType;}
		if (i == 24){return PaymentType;}
		if (i == 25){return Memo;}
		if (i == 26){return Prop1;}
		if (i == 27){return Prop2;}
		if (i == 28){return Prop3;}
		if (i == 29){return Prop4;}
		if (i == 30){return AddUser;}
		if (i == 31){return AddTime;}
		if (i == 32){return ModifyUser;}
		if (i == 33){return ModifyTime;}
		if (i == 34){return BackupNo;}
		if (i == 35){return BackupOperator;}
		if (i == 36){return BackupTime;}
		if (i == 37){return BackupMemo;}
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
	* 是否必填 :true<br>
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
	* 是否必填 :true<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :SiteID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSiteID(String siteID) {
		if (siteID == null){
			this.SiteID = null;
			return;
		}
		this.SiteID = new Long(siteID);
    }

	/**
	* 获取字段UserName的值，该字段的<br>
	* 字段名称 :UserName<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getUserName() {
		return UserName;
	}

	/**
	* 设置字段UserName的值，该字段的<br>
	* 字段名称 :UserName<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUserName(String userName) {
		this.UserName = userName;
    }

	/**
	* 获取字段IsValid的值，该字段的<br>
	* 字段名称 :IsValid<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIsValid() {
		return IsValid;
	}

	/**
	* 设置字段IsValid的值，该字段的<br>
	* 字段名称 :IsValid<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsValid(String isValid) {
		this.IsValid = isValid;
    }

	/**
	* 获取字段Status的值，该字段的<br>
	* 字段名称 :Status<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* 设置字段Status的值，该字段的<br>
	* 字段名称 :Status<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
    }

	/**
	* 获取字段Amount的值，该字段的<br>
	* 字段名称 :Amount<br>
	* 数据类型 :float(12,2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public float getAmount() {
		if(Amount==null){return 0;}
		return Amount.floatValue();
	}

	/**
	* 设置字段Amount的值，该字段的<br>
	* 字段名称 :Amount<br>
	* 数据类型 :float(12,2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAmount(float amount) {
		this.Amount = new Float(amount);
    }

	/**
	* 设置字段Amount的值，该字段的<br>
	* 字段名称 :Amount<br>
	* 数据类型 :float(12,2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAmount(String amount) {
		if (amount == null){
			this.Amount = null;
			return;
		}
		this.Amount = new Float(amount);
    }

	/**
	* 获取字段SendFee的值，该字段的<br>
	* 字段名称 :SendFee<br>
	* 数据类型 :float(12,2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public float getSendFee() {
		if(SendFee==null){return 0;}
		return SendFee.floatValue();
	}

	/**
	* 设置字段SendFee的值，该字段的<br>
	* 字段名称 :SendFee<br>
	* 数据类型 :float(12,2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSendFee(float sendFee) {
		this.SendFee = new Float(sendFee);
    }

	/**
	* 设置字段SendFee的值，该字段的<br>
	* 字段名称 :SendFee<br>
	* 数据类型 :float(12,2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSendFee(String sendFee) {
		if (sendFee == null){
			this.SendFee = null;
			return;
		}
		this.SendFee = new Float(sendFee);
    }

	/**
	* 获取字段OrderAmount的值，该字段的<br>
	* 字段名称 :OrderAmount<br>
	* 数据类型 :float(12,2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public float getOrderAmount() {
		if(OrderAmount==null){return 0;}
		return OrderAmount.floatValue();
	}

	/**
	* 设置字段OrderAmount的值，该字段的<br>
	* 字段名称 :OrderAmount<br>
	* 数据类型 :float(12,2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderAmount(float orderAmount) {
		this.OrderAmount = new Float(orderAmount);
    }

	/**
	* 设置字段OrderAmount的值，该字段的<br>
	* 字段名称 :OrderAmount<br>
	* 数据类型 :float(12,2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderAmount(String orderAmount) {
		if (orderAmount == null){
			this.OrderAmount = null;
			return;
		}
		this.OrderAmount = new Float(orderAmount);
    }

	/**
	* 获取字段Score的值，该字段的<br>
	* 字段名称 :Score<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getScore() {
		if(Score==null){return 0;}
		return Score.longValue();
	}

	/**
	* 设置字段Score的值，该字段的<br>
	* 字段名称 :Score<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setScore(long score) {
		this.Score = new Long(score);
    }

	/**
	* 设置字段Score的值，该字段的<br>
	* 字段名称 :Score<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setScore(String score) {
		if (score == null){
			this.Score = null;
			return;
		}
		this.Score = new Long(score);
    }

	/**
	* 获取字段Name的值，该字段的<br>
	* 字段名称 :Name<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* 设置字段Name的值，该字段的<br>
	* 字段名称 :Name<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setName(String name) {
		this.Name = name;
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
	* 获取字段HasInvoice的值，该字段的<br>
	* 字段名称 :HasInvoice<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getHasInvoice() {
		return HasInvoice;
	}

	/**
	* 设置字段HasInvoice的值，该字段的<br>
	* 字段名称 :HasInvoice<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setHasInvoice(String hasInvoice) {
		this.HasInvoice = hasInvoice;
    }

	/**
	* 获取字段InvoiceTitle的值，该字段的<br>
	* 字段名称 :InvoiceTitle<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInvoiceTitle() {
		return InvoiceTitle;
	}

	/**
	* 设置字段InvoiceTitle的值，该字段的<br>
	* 字段名称 :InvoiceTitle<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInvoiceTitle(String invoiceTitle) {
		this.InvoiceTitle = invoiceTitle;
    }

	/**
	* 获取字段SendBeginDate的值，该字段的<br>
	* 字段名称 :SendBeginDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getSendBeginDate() {
		return SendBeginDate;
	}

	/**
	* 设置字段SendBeginDate的值，该字段的<br>
	* 字段名称 :SendBeginDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSendBeginDate(Date sendBeginDate) {
		this.SendBeginDate = sendBeginDate;
    }

	/**
	* 获取字段SendEndDate的值，该字段的<br>
	* 字段名称 :SendEndDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getSendEndDate() {
		return SendEndDate;
	}

	/**
	* 设置字段SendEndDate的值，该字段的<br>
	* 字段名称 :SendEndDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSendEndDate(Date sendEndDate) {
		this.SendEndDate = sendEndDate;
    }

	/**
	* 获取字段SendTimeSlice的值，该字段的<br>
	* 字段名称 :SendTimeSlice<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSendTimeSlice() {
		return SendTimeSlice;
	}

	/**
	* 设置字段SendTimeSlice的值，该字段的<br>
	* 字段名称 :SendTimeSlice<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSendTimeSlice(String sendTimeSlice) {
		this.SendTimeSlice = sendTimeSlice;
    }

	/**
	* 获取字段SendInfo的值，该字段的<br>
	* 字段名称 :SendInfo<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSendInfo() {
		return SendInfo;
	}

	/**
	* 设置字段SendInfo的值，该字段的<br>
	* 字段名称 :SendInfo<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSendInfo(String sendInfo) {
		this.SendInfo = sendInfo;
    }

	/**
	* 获取字段SendType的值，该字段的<br>
	* 字段名称 :SendType<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSendType() {
		return SendType;
	}

	/**
	* 设置字段SendType的值，该字段的<br>
	* 字段名称 :SendType<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSendType(String sendType) {
		this.SendType = sendType;
    }

	/**
	* 获取字段PaymentType的值，该字段的<br>
	* 字段名称 :PaymentType<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPaymentType() {
		return PaymentType;
	}

	/**
	* 设置字段PaymentType的值，该字段的<br>
	* 字段名称 :PaymentType<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPaymentType(String paymentType) {
		this.PaymentType = paymentType;
    }

	/**
	* 获取字段Memo的值，该字段的<br>
	* 字段名称 :Memo<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* 设置字段Memo的值，该字段的<br>
	* 字段名称 :Memo<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemo(String memo) {
		this.Memo = memo;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(200)<br>
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

	/**
	* 获取字段BackupNo的值，该字段的<br>
	* 字段名称 :备份编号<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getBackupNo() {
		return BackupNo;
	}

	/**
	* 设置字段BackupNo的值，该字段的<br>
	* 字段名称 :备份编号<br>
	* 数据类型 :varchar(15)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setBackupNo(String backupNo) {
		this.BackupNo = backupNo;
    }

	/**
	* 获取字段BackupOperator的值，该字段的<br>
	* 字段名称 :备份人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getBackupOperator() {
		return BackupOperator;
	}

	/**
	* 设置字段BackupOperator的值，该字段的<br>
	* 字段名称 :备份人<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBackupOperator(String backupOperator) {
		this.BackupOperator = backupOperator;
    }

	/**
	* 获取字段BackupTime的值，该字段的<br>
	* 字段名称 :备份时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getBackupTime() {
		return BackupTime;
	}

	/**
	* 设置字段BackupTime的值，该字段的<br>
	* 字段名称 :备份时间<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBackupTime(Date backupTime) {
		this.BackupTime = backupTime;
    }

	/**
	* 获取字段BackupMemo的值，该字段的<br>
	* 字段名称 :备份备注<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBackupMemo() {
		return BackupMemo;
	}

	/**
	* 设置字段BackupMemo的值，该字段的<br>
	* 字段名称 :备份备注<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBackupMemo(String backupMemo) {
		this.BackupMemo = backupMemo;
    }

}