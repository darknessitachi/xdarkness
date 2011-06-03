package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZDMemberField<br>
 * 表代码：ZDMemberField<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：SiteID, Code<br>
 */
public class ZDMemberFieldSchema extends Schema {
	private Long SiteID;

	private String Name;

	private String Code;

	private String RealField;

	private String VerifyType;

	private Integer MaxLength;

	private String InputType;

	private String DefaultValue;

	private String ListOption;

	private String HTML;

	private String IsMandatory;

	private Long OrderFlag;

	private Integer RowSize;

	private Integer ColSize;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("SiteID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("Name", DataColumn.STRING, 1, 50 , 0 , false , false),
		new SchemaColumn("Code", DataColumn.STRING, 2, 50 , 0 , true , true),
		new SchemaColumn("RealField", DataColumn.STRING, 3, 20 , 0 , false , false),
		new SchemaColumn("VerifyType", DataColumn.STRING, 4, 2 , 0 , true , false),
		new SchemaColumn("MaxLength", DataColumn.INTEGER, 5, 11 , 0 , false , false),
		new SchemaColumn("InputType", DataColumn.STRING, 6, 20 , 0 , true , false),
		new SchemaColumn("DefaultValue", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("ListOption", DataColumn.STRING, 8, 1000 , 0 , false , false),
		new SchemaColumn("HTML", DataColumn.CLOB, 9, 0 , 0 , false , false),
		new SchemaColumn("IsMandatory", DataColumn.STRING, 10, 2 , 0 , true , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 11, 20 , 0 , false , false),
		new SchemaColumn("RowSize", DataColumn.INTEGER, 12, 11 , 0 , false , false),
		new SchemaColumn("ColSize", DataColumn.INTEGER, 13, 11 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 14, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 15, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 16, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 17, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZDMemberField";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into ZDMemberField values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDMemberField set SiteID=?,Name=?,Code=?,RealField=?,VerifyType=?,MaxLength=?,InputType=?,DefaultValue=?,ListOption=?,HTML=?,IsMandatory=?,OrderFlag=?,RowSize=?,ColSize=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where SiteID=? and Code=?";

	protected static final String _DeleteSQL = "delete from ZDMemberField  where SiteID=? and Code=?";

	protected static final String _FillAllSQL = "select * from ZDMemberField  where SiteID=? and Code=?";

	public ZDMemberFieldSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[18];
	}

	protected Schema newInstance(){
		return new ZDMemberFieldSchema();
	}

	protected SchemaSet newSet(){
		return new ZDMemberFieldSet();
	}

	public ZDMemberFieldSet query() {
		return query(null, -1, -1);
	}

	public ZDMemberFieldSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDMemberFieldSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDMemberFieldSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDMemberFieldSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 1){Name = (String)v;return;}
		if (i == 2){Code = (String)v;return;}
		if (i == 3){RealField = (String)v;return;}
		if (i == 4){VerifyType = (String)v;return;}
		if (i == 5){if(v==null){MaxLength = null;}else{MaxLength = new Integer(v.toString());}return;}
		if (i == 6){InputType = (String)v;return;}
		if (i == 7){DefaultValue = (String)v;return;}
		if (i == 8){ListOption = (String)v;return;}
		if (i == 9){HTML = (String)v;return;}
		if (i == 10){IsMandatory = (String)v;return;}
		if (i == 11){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 12){if(v==null){RowSize = null;}else{RowSize = new Integer(v.toString());}return;}
		if (i == 13){if(v==null){ColSize = null;}else{ColSize = new Integer(v.toString());}return;}
		if (i == 14){AddUser = (String)v;return;}
		if (i == 15){AddTime = (Date)v;return;}
		if (i == 16){ModifyUser = (String)v;return;}
		if (i == 17){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return SiteID;}
		if (i == 1){return Name;}
		if (i == 2){return Code;}
		if (i == 3){return RealField;}
		if (i == 4){return VerifyType;}
		if (i == 5){return MaxLength;}
		if (i == 6){return InputType;}
		if (i == 7){return DefaultValue;}
		if (i == 8){return ListOption;}
		if (i == 9){return HTML;}
		if (i == 10){return IsMandatory;}
		if (i == 11){return OrderFlag;}
		if (i == 12){return RowSize;}
		if (i == 13){return ColSize;}
		if (i == 14){return AddUser;}
		if (i == 15){return AddTime;}
		if (i == 16){return ModifyUser;}
		if (i == 17){return ModifyTime;}
		return null;
	}

	/**
	* 获取字段SiteID的值，该字段的<br>
	* 字段名称 :SiteID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
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
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :SiteID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
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
	* 获取字段Name的值，该字段的<br>
	* 字段名称 :Name<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* 设置字段Name的值，该字段的<br>
	* 字段名称 :Name<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* 获取字段Code的值，该字段的<br>
	* 字段名称 :Code<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getCode() {
		return Code;
	}

	/**
	* 设置字段Code的值，该字段的<br>
	* 字段名称 :Code<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setCode(String code) {
		this.Code = code;
    }

	/**
	* 获取字段RealField的值，该字段的<br>
	* 字段名称 :RealField<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRealField() {
		return RealField;
	}

	/**
	* 设置字段RealField的值，该字段的<br>
	* 字段名称 :RealField<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRealField(String realField) {
		this.RealField = realField;
    }

	/**
	* 获取字段VerifyType的值，该字段的<br>
	* 字段名称 :VerifyType<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getVerifyType() {
		return VerifyType;
	}

	/**
	* 设置字段VerifyType的值，该字段的<br>
	* 字段名称 :VerifyType<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setVerifyType(String verifyType) {
		this.VerifyType = verifyType;
    }

	/**
	* 获取字段MaxLength的值，该字段的<br>
	* 字段名称 :MaxLength<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getMaxLength() {
		if(MaxLength==null){return 0;}
		return MaxLength.intValue();
	}

	/**
	* 设置字段MaxLength的值，该字段的<br>
	* 字段名称 :MaxLength<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMaxLength(int maxLength) {
		this.MaxLength = new Integer(maxLength);
    }

	/**
	* 设置字段MaxLength的值，该字段的<br>
	* 字段名称 :MaxLength<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMaxLength(String maxLength) {
		if (maxLength == null){
			this.MaxLength = null;
			return;
		}
		this.MaxLength = new Integer(maxLength);
    }

	/**
	* 获取字段InputType的值，该字段的<br>
	* 字段名称 :InputType<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getInputType() {
		return InputType;
	}

	/**
	* 设置字段InputType的值，该字段的<br>
	* 字段名称 :InputType<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setInputType(String inputType) {
		this.InputType = inputType;
    }

	/**
	* 获取字段DefaultValue的值，该字段的<br>
	* 字段名称 :DefaultValue<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDefaultValue() {
		return DefaultValue;
	}

	/**
	* 设置字段DefaultValue的值，该字段的<br>
	* 字段名称 :DefaultValue<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDefaultValue(String defaultValue) {
		this.DefaultValue = defaultValue;
    }

	/**
	* 获取字段ListOption的值，该字段的<br>
	* 字段名称 :ListOption<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getListOption() {
		return ListOption;
	}

	/**
	* 设置字段ListOption的值，该字段的<br>
	* 字段名称 :ListOption<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setListOption(String listOption) {
		this.ListOption = listOption;
    }

	/**
	* 获取字段HTML的值，该字段的<br>
	* 字段名称 :HTML<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getHTML() {
		return HTML;
	}

	/**
	* 设置字段HTML的值，该字段的<br>
	* 字段名称 :HTML<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setHTML(String hTML) {
		this.HTML = hTML;
    }

	/**
	* 获取字段IsMandatory的值，该字段的<br>
	* 字段名称 :IsMandatory<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getIsMandatory() {
		return IsMandatory;
	}

	/**
	* 设置字段IsMandatory的值，该字段的<br>
	* 字段名称 :IsMandatory<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setIsMandatory(String isMandatory) {
		this.IsMandatory = isMandatory;
    }

	/**
	* 获取字段OrderFlag的值，该字段的<br>
	* 字段名称 :OrderFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getOrderFlag() {
		if(OrderFlag==null){return 0;}
		return OrderFlag.longValue();
	}

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :OrderFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :OrderFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(String orderFlag) {
		if (orderFlag == null){
			this.OrderFlag = null;
			return;
		}
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 获取字段RowSize的值，该字段的<br>
	* 字段名称 :RowSize<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getRowSize() {
		if(RowSize==null){return 0;}
		return RowSize.intValue();
	}

	/**
	* 设置字段RowSize的值，该字段的<br>
	* 字段名称 :RowSize<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRowSize(int rowSize) {
		this.RowSize = new Integer(rowSize);
    }

	/**
	* 设置字段RowSize的值，该字段的<br>
	* 字段名称 :RowSize<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRowSize(String rowSize) {
		if (rowSize == null){
			this.RowSize = null;
			return;
		}
		this.RowSize = new Integer(rowSize);
    }

	/**
	* 获取字段ColSize的值，该字段的<br>
	* 字段名称 :ColSize<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getColSize() {
		if(ColSize==null){return 0;}
		return ColSize.intValue();
	}

	/**
	* 设置字段ColSize的值，该字段的<br>
	* 字段名称 :ColSize<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setColSize(int colSize) {
		this.ColSize = new Integer(colSize);
    }

	/**
	* 设置字段ColSize的值，该字段的<br>
	* 字段名称 :ColSize<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setColSize(String colSize) {
		if (colSize == null){
			this.ColSize = null;
			return;
		}
		this.ColSize = new Integer(colSize);
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