package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZDCode<br>
 * 表代码：ZDCode<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：CodeType, ParentCode, CodeValue<br>
 */
public class ZDCodeSchema extends Schema {
	private String CodeType;

	private String ParentCode;

	private String CodeValue;

	private String CodeName;

	private Long CodeOrder;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String Memo;

	private Date AddTime;

	private String AddUser;

	private Date ModifyTime;

	private String ModifyUser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("CodeType", DataColumn.STRING, 0, 40 , 0 , true , true),
		new SchemaColumn("ParentCode", DataColumn.STRING, 1, 40 , 0 , true , true),
		new SchemaColumn("CodeValue", DataColumn.STRING, 2, 40 , 0 , true , true),
		new SchemaColumn("CodeName", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("CodeOrder", DataColumn.LONG, 4, 20 , 0 , true , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 5, 40 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 6, 40 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 7, 40 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 8, 40 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 9, 400 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 10, 0 , 0 , true , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 11, 200 , 0 , true , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 12, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 13, 200 , 0 , false , false)
	};

	public static final String _TableCode = "ZDCode";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into ZDCode values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDCode set CodeType=?,ParentCode=?,CodeValue=?,CodeName=?,CodeOrder=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Memo=?,AddTime=?,AddUser=?,ModifyTime=?,ModifyUser=? where CodeType=? and ParentCode=? and CodeValue=?";

	protected static final String _DeleteSQL = "delete from ZDCode  where CodeType=? and ParentCode=? and CodeValue=?";

	protected static final String _FillAllSQL = "select * from ZDCode  where CodeType=? and ParentCode=? and CodeValue=?";

	public ZDCodeSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[14];
	}

	protected Schema newInstance(){
		return new ZDCodeSchema();
	}

	protected SchemaSet newSet(){
		return new ZDCodeSet();
	}

	public ZDCodeSet query() {
		return query(null, -1, -1);
	}

	public ZDCodeSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDCodeSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDCodeSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDCodeSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){CodeType = (String)v;return;}
		if (i == 1){ParentCode = (String)v;return;}
		if (i == 2){CodeValue = (String)v;return;}
		if (i == 3){CodeName = (String)v;return;}
		if (i == 4){if(v==null){CodeOrder = null;}else{CodeOrder = new Long(v.toString());}return;}
		if (i == 5){Prop1 = (String)v;return;}
		if (i == 6){Prop2 = (String)v;return;}
		if (i == 7){Prop3 = (String)v;return;}
		if (i == 8){Prop4 = (String)v;return;}
		if (i == 9){Memo = (String)v;return;}
		if (i == 10){AddTime = (Date)v;return;}
		if (i == 11){AddUser = (String)v;return;}
		if (i == 12){ModifyTime = (Date)v;return;}
		if (i == 13){ModifyUser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return CodeType;}
		if (i == 1){return ParentCode;}
		if (i == 2){return CodeValue;}
		if (i == 3){return CodeName;}
		if (i == 4){return CodeOrder;}
		if (i == 5){return Prop1;}
		if (i == 6){return Prop2;}
		if (i == 7){return Prop3;}
		if (i == 8){return Prop4;}
		if (i == 9){return Memo;}
		if (i == 10){return AddTime;}
		if (i == 11){return AddUser;}
		if (i == 12){return ModifyTime;}
		if (i == 13){return ModifyUser;}
		return null;
	}

	/**
	* 获取字段CodeType的值，该字段的<br>
	* 字段名称 :CodeType<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getCodeType() {
		return CodeType;
	}

	/**
	* 设置字段CodeType的值，该字段的<br>
	* 字段名称 :CodeType<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setCodeType(String codeType) {
		this.CodeType = codeType;
    }

	/**
	* 获取字段ParentCode的值，该字段的<br>
	* 字段名称 :ParentCode<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getParentCode() {
		return ParentCode;
	}

	/**
	* 设置字段ParentCode的值，该字段的<br>
	* 字段名称 :ParentCode<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setParentCode(String parentCode) {
		this.ParentCode = parentCode;
    }

	/**
	* 获取字段CodeValue的值，该字段的<br>
	* 字段名称 :CodeValue<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getCodeValue() {
		return CodeValue;
	}

	/**
	* 设置字段CodeValue的值，该字段的<br>
	* 字段名称 :CodeValue<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setCodeValue(String codeValue) {
		this.CodeValue = codeValue;
    }

	/**
	* 获取字段CodeName的值，该字段的<br>
	* 字段名称 :CodeName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCodeName() {
		return CodeName;
	}

	/**
	* 设置字段CodeName的值，该字段的<br>
	* 字段名称 :CodeName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCodeName(String codeName) {
		this.CodeName = codeName;
    }

	/**
	* 获取字段CodeOrder的值，该字段的<br>
	* 字段名称 :CodeOrder<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getCodeOrder() {
		if(CodeOrder==null){return 0;}
		return CodeOrder.longValue();
	}

	/**
	* 设置字段CodeOrder的值，该字段的<br>
	* 字段名称 :CodeOrder<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCodeOrder(long codeOrder) {
		this.CodeOrder = new Long(codeOrder);
    }

	/**
	* 设置字段CodeOrder的值，该字段的<br>
	* 字段名称 :CodeOrder<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCodeOrder(String codeOrder) {
		if (codeOrder == null){
			this.CodeOrder = null;
			return;
		}
		this.CodeOrder = new Long(codeOrder);
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
    }

	/**
	* 获取字段Memo的值，该字段的<br>
	* 字段名称 :Memo<br>
	* 数据类型 :varchar(400)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* 设置字段Memo的值，该字段的<br>
	* 字段名称 :Memo<br>
	* 数据类型 :varchar(400)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemo(String memo) {
		this.Memo = memo;
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

}