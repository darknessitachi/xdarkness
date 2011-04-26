package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZSFavorite备份<br>
 * 表代码：BZSFavorite<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：UserName, GoodsID, BackupNo<br>
 */
public class BZSFavoriteSchema extends Schema {
	private String UserName;

	private Long GoodsID;

	private Long SiteID;

	private String PriceNoteFlag;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("UserName", DataColumn.STRING, 0, 200 , 0 , true , true),
		new SchemaColumn("GoodsID", DataColumn.LONG, 1, 20 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 2, 20 , 0 , false , false),
		new SchemaColumn("PriceNoteFlag", DataColumn.STRING, 3, 2 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 4, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 5, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 6, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 7, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 8, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 9, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 10, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 11, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZSFavorite";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into BZSFavorite values(?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZSFavorite set UserName=?,GoodsID=?,SiteID=?,PriceNoteFlag=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where UserName=? and GoodsID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZSFavorite  where UserName=? and GoodsID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZSFavorite  where UserName=? and GoodsID=? and BackupNo=?";

	public BZSFavoriteSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[12];
	}

	protected Schema newInstance(){
		return new BZSFavoriteSchema();
	}

	protected SchemaSet newSet(){
		return new BZSFavoriteSet();
	}

	public BZSFavoriteSet query() {
		return query(null, -1, -1);
	}

	public BZSFavoriteSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZSFavoriteSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZSFavoriteSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZSFavoriteSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){UserName = (String)v;return;}
		if (i == 1){if(v==null){GoodsID = null;}else{GoodsID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 3){PriceNoteFlag = (String)v;return;}
		if (i == 4){AddUser = (String)v;return;}
		if (i == 5){AddTime = (Date)v;return;}
		if (i == 6){ModifyUser = (String)v;return;}
		if (i == 7){ModifyTime = (Date)v;return;}
		if (i == 8){BackupNo = (String)v;return;}
		if (i == 9){BackupOperator = (String)v;return;}
		if (i == 10){BackupTime = (Date)v;return;}
		if (i == 11){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return UserName;}
		if (i == 1){return GoodsID;}
		if (i == 2){return SiteID;}
		if (i == 3){return PriceNoteFlag;}
		if (i == 4){return AddUser;}
		if (i == 5){return AddTime;}
		if (i == 6){return ModifyUser;}
		if (i == 7){return ModifyTime;}
		if (i == 8){return BackupNo;}
		if (i == 9){return BackupOperator;}
		if (i == 10){return BackupTime;}
		if (i == 11){return BackupMemo;}
		return null;
	}

	/**
	* 获取字段UserName的值，该字段的<br>
	* 字段名称 :UserName<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getUserName() {
		return UserName;
	}

	/**
	* 设置字段UserName的值，该字段的<br>
	* 字段名称 :UserName<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setUserName(String userName) {
		this.UserName = userName;
    }

	/**
	* 获取字段GoodsID的值，该字段的<br>
	* 字段名称 :GoodsID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getGoodsID() {
		if(GoodsID==null){return 0;}
		return GoodsID.longValue();
	}

	/**
	* 设置字段GoodsID的值，该字段的<br>
	* 字段名称 :GoodsID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setGoodsID(long goodsID) {
		this.GoodsID = new Long(goodsID);
    }

	/**
	* 设置字段GoodsID的值，该字段的<br>
	* 字段名称 :GoodsID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setGoodsID(String goodsID) {
		if (goodsID == null){
			this.GoodsID = null;
			return;
		}
		this.GoodsID = new Long(goodsID);
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
	* 获取字段PriceNoteFlag的值，该字段的<br>
	* 字段名称 :PriceNoteFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPriceNoteFlag() {
		return PriceNoteFlag;
	}

	/**
	* 设置字段PriceNoteFlag的值，该字段的<br>
	* 字段名称 :PriceNoteFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPriceNoteFlag(String priceNoteFlag) {
		this.PriceNoteFlag = priceNoteFlag;
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