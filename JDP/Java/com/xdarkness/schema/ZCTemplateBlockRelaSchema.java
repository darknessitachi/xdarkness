package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCTemplateBlockRela<br>
 * 表代码：ZCTemplateBlockRela<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：SiteID, FileName, BlockID<br>
 */
public class ZCTemplateBlockRelaSchema extends Schema {
	private Long SiteID;

	private String FileName;

	private Long BlockID;

	private String Prop1;

	private String Prop2;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("SiteID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("FileName", DataColumn.STRING, 1, 100 , 0 , true , true),
		new SchemaColumn("BlockID", DataColumn.LONG, 2, 20 , 0 , true , true),
		new SchemaColumn("Prop1", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 5, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 6, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 7, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 8, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCTemplateBlockRela";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into ZCTemplateBlockRela values(?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCTemplateBlockRela set SiteID=?,FileName=?,BlockID=?,Prop1=?,Prop2=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where SiteID=? and FileName=? and BlockID=?";

	protected static final String _DeleteSQL = "delete from ZCTemplateBlockRela  where SiteID=? and FileName=? and BlockID=?";

	protected static final String _FillAllSQL = "select * from ZCTemplateBlockRela  where SiteID=? and FileName=? and BlockID=?";

	public ZCTemplateBlockRelaSchema(){
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
		return new ZCTemplateBlockRelaSchema();
	}

	protected SchemaSet newSet(){
		return new ZCTemplateBlockRelaSet();
	}

	public ZCTemplateBlockRelaSet query() {
		return query(null, -1, -1);
	}

	public ZCTemplateBlockRelaSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCTemplateBlockRelaSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCTemplateBlockRelaSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCTemplateBlockRelaSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 1){FileName = (String)v;return;}
		if (i == 2){if(v==null){BlockID = null;}else{BlockID = new Long(v.toString());}return;}
		if (i == 3){Prop1 = (String)v;return;}
		if (i == 4){Prop2 = (String)v;return;}
		if (i == 5){AddUser = (String)v;return;}
		if (i == 6){AddTime = (Date)v;return;}
		if (i == 7){ModifyUser = (String)v;return;}
		if (i == 8){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return SiteID;}
		if (i == 1){return FileName;}
		if (i == 2){return BlockID;}
		if (i == 3){return Prop1;}
		if (i == 4){return Prop2;}
		if (i == 5){return AddUser;}
		if (i == 6){return AddTime;}
		if (i == 7){return ModifyUser;}
		if (i == 8){return ModifyTime;}
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
	* 获取字段FileName的值，该字段的<br>
	* 字段名称 :FileName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getFileName() {
		return FileName;
	}

	/**
	* 设置字段FileName的值，该字段的<br>
	* 字段名称 :FileName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setFileName(String fileName) {
		this.FileName = fileName;
    }

	/**
	* 获取字段BlockID的值，该字段的<br>
	* 字段名称 :BlockID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getBlockID() {
		if(BlockID==null){return 0;}
		return BlockID.longValue();
	}

	/**
	* 设置字段BlockID的值，该字段的<br>
	* 字段名称 :BlockID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setBlockID(long blockID) {
		this.BlockID = new Long(blockID);
    }

	/**
	* 设置字段BlockID的值，该字段的<br>
	* 字段名称 :BlockID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setBlockID(String blockID) {
		if (blockID == null){
			this.BlockID = null;
			return;
		}
		this.BlockID = new Long(blockID);
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