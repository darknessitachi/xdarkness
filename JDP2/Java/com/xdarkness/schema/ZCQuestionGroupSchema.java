package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCQuestionGroup<br>
 * 表代码：ZCQuestionGroup<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：InnerCode<br>
 */
public class ZCQuestionGroupSchema extends Schema {
	private String InnerCode;

	private String ParentInnerCode;

	private String Name;

	private Integer TreeLevel;

	private String IsLeaf;

	private Long OrderFlag;

	private String Memo;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private Date AddTime;

	private String AddUser;

	private Date ModifyTime;

	private String ModifyUser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("InnerCode", DataColumn.STRING, 0, 100 , 0 , true , true),
		new SchemaColumn("ParentInnerCode", DataColumn.STRING, 1, 100 , 0 , true , false),
		new SchemaColumn("Name", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("TreeLevel", DataColumn.INTEGER, 3, 11 , 0 , true , false),
		new SchemaColumn("IsLeaf", DataColumn.STRING, 4, 2 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 5, 20 , 0 , true , false),
		new SchemaColumn("Memo", DataColumn.STRING, 6, 100 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 8, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 10, 100 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 11, 0 , 0 , true , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 12, 100 , 0 , true , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 13, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 14, 100 , 0 , false , false)
	};

	public static final String _TableCode = "ZCQuestionGroup";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into ZCQuestionGroup values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCQuestionGroup set InnerCode=?,ParentInnerCode=?,Name=?,TreeLevel=?,IsLeaf=?,OrderFlag=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddTime=?,AddUser=?,ModifyTime=?,ModifyUser=? where InnerCode=?";

	protected static final String _DeleteSQL = "delete from ZCQuestionGroup  where InnerCode=?";

	protected static final String _FillAllSQL = "select * from ZCQuestionGroup  where InnerCode=?";

	public ZCQuestionGroupSchema(){
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
		return new ZCQuestionGroupSchema();
	}

	protected SchemaSet newSet(){
		return new ZCQuestionGroupSet();
	}

	public ZCQuestionGroupSet query() {
		return query(null, -1, -1);
	}

	public ZCQuestionGroupSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCQuestionGroupSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCQuestionGroupSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCQuestionGroupSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){InnerCode = (String)v;return;}
		if (i == 1){ParentInnerCode = (String)v;return;}
		if (i == 2){Name = (String)v;return;}
		if (i == 3){if(v==null){TreeLevel = null;}else{TreeLevel = new Integer(v.toString());}return;}
		if (i == 4){IsLeaf = (String)v;return;}
		if (i == 5){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 6){Memo = (String)v;return;}
		if (i == 7){Prop1 = (String)v;return;}
		if (i == 8){Prop2 = (String)v;return;}
		if (i == 9){Prop3 = (String)v;return;}
		if (i == 10){Prop4 = (String)v;return;}
		if (i == 11){AddTime = (Date)v;return;}
		if (i == 12){AddUser = (String)v;return;}
		if (i == 13){ModifyTime = (Date)v;return;}
		if (i == 14){ModifyUser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return InnerCode;}
		if (i == 1){return ParentInnerCode;}
		if (i == 2){return Name;}
		if (i == 3){return TreeLevel;}
		if (i == 4){return IsLeaf;}
		if (i == 5){return OrderFlag;}
		if (i == 6){return Memo;}
		if (i == 7){return Prop1;}
		if (i == 8){return Prop2;}
		if (i == 9){return Prop3;}
		if (i == 10){return Prop4;}
		if (i == 11){return AddTime;}
		if (i == 12){return AddUser;}
		if (i == 13){return ModifyTime;}
		if (i == 14){return ModifyUser;}
		return null;
	}

	/**
	* 获取字段InnerCode的值，该字段的<br>
	* 字段名称 :InnerCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getInnerCode() {
		return InnerCode;
	}

	/**
	* 设置字段InnerCode的值，该字段的<br>
	* 字段名称 :InnerCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setInnerCode(String innerCode) {
		this.InnerCode = innerCode;
    }

	/**
	* 获取字段ParentInnerCode的值，该字段的<br>
	* 字段名称 :ParentInnerCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getParentInnerCode() {
		return ParentInnerCode;
	}

	/**
	* 设置字段ParentInnerCode的值，该字段的<br>
	* 字段名称 :ParentInnerCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setParentInnerCode(String parentInnerCode) {
		this.ParentInnerCode = parentInnerCode;
    }

	/**
	* 获取字段Name的值，该字段的<br>
	* 字段名称 :Name<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* 设置字段Name的值，该字段的<br>
	* 字段名称 :Name<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* 获取字段TreeLevel的值，该字段的<br>
	* 字段名称 :TreeLevel<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getTreeLevel() {
		if(TreeLevel==null){return 0;}
		return TreeLevel.intValue();
	}

	/**
	* 设置字段TreeLevel的值，该字段的<br>
	* 字段名称 :TreeLevel<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTreeLevel(int treeLevel) {
		this.TreeLevel = new Integer(treeLevel);
    }

	/**
	* 设置字段TreeLevel的值，该字段的<br>
	* 字段名称 :TreeLevel<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTreeLevel(String treeLevel) {
		if (treeLevel == null){
			this.TreeLevel = null;
			return;
		}
		this.TreeLevel = new Integer(treeLevel);
    }

	/**
	* 获取字段IsLeaf的值，该字段的<br>
	* 字段名称 :IsLeaf<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIsLeaf() {
		return IsLeaf;
	}

	/**
	* 设置字段IsLeaf的值，该字段的<br>
	* 字段名称 :IsLeaf<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsLeaf(String isLeaf) {
		this.IsLeaf = isLeaf;
    }

	/**
	* 获取字段OrderFlag的值，该字段的<br>
	* 字段名称 :OrderFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
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
	* 是否必填 :true<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :OrderFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setOrderFlag(String orderFlag) {
		if (orderFlag == null){
			this.OrderFlag = null;
			return;
		}
		this.OrderFlag = new Long(orderFlag);
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