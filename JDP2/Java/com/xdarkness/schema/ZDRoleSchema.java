package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZDRole<br>
 * 表代码：ZDRole<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：RoleCode<br>
 */
public class ZDRoleSchema extends Schema {
	private String RoleCode;

	private String RoleName;

	private String BranchInnerCode;

	private String Prop1;

	private String Prop2;

	private String Memo;

	private Date AddTime;

	private String AddUser;

	private Date ModifyTime;

	private String ModifyUser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("RoleCode", DataColumn.STRING, 0, 200 , 0 , true , true),
		new SchemaColumn("RoleName", DataColumn.STRING, 1, 100 , 0 , true , false),
		new SchemaColumn("BranchInnerCode", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 6, 0 , 0 , true , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 7, 200 , 0 , true , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 8, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 9, 200 , 0 , false , false)
	};

	public static final String _TableCode = "ZDRole";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into ZDRole values(?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDRole set RoleCode=?,RoleName=?,BranchInnerCode=?,Prop1=?,Prop2=?,Memo=?,AddTime=?,AddUser=?,ModifyTime=?,ModifyUser=? where RoleCode=?";

	protected static final String _DeleteSQL = "delete from ZDRole  where RoleCode=?";

	protected static final String _FillAllSQL = "select * from ZDRole  where RoleCode=?";

	public ZDRoleSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[10];
	}

	protected Schema newInstance(){
		return new ZDRoleSchema();
	}

	protected SchemaSet newSet(){
		return new ZDRoleSet();
	}

	public ZDRoleSet query() {
		return query(null, -1, -1);
	}

	public ZDRoleSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDRoleSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDRoleSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDRoleSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){RoleCode = (String)v;return;}
		if (i == 1){RoleName = (String)v;return;}
		if (i == 2){BranchInnerCode = (String)v;return;}
		if (i == 3){Prop1 = (String)v;return;}
		if (i == 4){Prop2 = (String)v;return;}
		if (i == 5){Memo = (String)v;return;}
		if (i == 6){AddTime = (Date)v;return;}
		if (i == 7){AddUser = (String)v;return;}
		if (i == 8){ModifyTime = (Date)v;return;}
		if (i == 9){ModifyUser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return RoleCode;}
		if (i == 1){return RoleName;}
		if (i == 2){return BranchInnerCode;}
		if (i == 3){return Prop1;}
		if (i == 4){return Prop2;}
		if (i == 5){return Memo;}
		if (i == 6){return AddTime;}
		if (i == 7){return AddUser;}
		if (i == 8){return ModifyTime;}
		if (i == 9){return ModifyUser;}
		return null;
	}

	/**
	* 获取字段RoleCode的值，该字段的<br>
	* 字段名称 :RoleCode<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getRoleCode() {
		return RoleCode;
	}

	/**
	* 设置字段RoleCode的值，该字段的<br>
	* 字段名称 :RoleCode<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setRoleCode(String roleCode) {
		this.RoleCode = roleCode;
    }

	/**
	* 获取字段RoleName的值，该字段的<br>
	* 字段名称 :RoleName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getRoleName() {
		return RoleName;
	}

	/**
	* 设置字段RoleName的值，该字段的<br>
	* 字段名称 :RoleName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setRoleName(String roleName) {
		this.RoleName = roleName;
    }

	/**
	* 获取字段BranchInnerCode的值，该字段的<br>
	* 字段名称 :BranchInnerCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getBranchInnerCode() {
		return BranchInnerCode;
	}

	/**
	* 设置字段BranchInnerCode的值，该字段的<br>
	* 字段名称 :BranchInnerCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBranchInnerCode(String branchInnerCode) {
		this.BranchInnerCode = branchInnerCode;
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
	* 获取字段Memo的值，该字段的<br>
	* 字段名称 :Memo<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* 设置字段Memo的值，该字段的<br>
	* 字段名称 :Memo<br>
	* 数据类型 :varchar(100)<br>
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