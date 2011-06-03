package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCMagazineCatalogRela备份<br>
 * 表代码：BZCMagazineCatalogRela<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：MagazineID, CatalogID, BackupNo<br>
 */
public class BZCMagazineCatalogRelaSchema extends Schema {
	private Long MagazineID;

	private Long CatalogID;

	private Long IssueID;

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
		new SchemaColumn("MagazineID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("CatalogID", DataColumn.LONG, 1, 20 , 0 , true , true),
		new SchemaColumn("IssueID", DataColumn.LONG, 2, 20 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 3, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 7, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 8, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 9, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 10, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 11, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 12, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 13, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 14, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCMagazineCatalogRela";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into BZCMagazineCatalogRela values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCMagazineCatalogRela set MagazineID=?,CatalogID=?,IssueID=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where MagazineID=? and CatalogID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCMagazineCatalogRela  where MagazineID=? and CatalogID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCMagazineCatalogRela  where MagazineID=? and CatalogID=? and BackupNo=?";

	public BZCMagazineCatalogRelaSchema(){
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
		return new BZCMagazineCatalogRelaSchema();
	}

	protected SchemaSet newSet(){
		return new BZCMagazineCatalogRelaSet();
	}

	public BZCMagazineCatalogRelaSet query() {
		return query(null, -1, -1);
	}

	public BZCMagazineCatalogRelaSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCMagazineCatalogRelaSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCMagazineCatalogRelaSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCMagazineCatalogRelaSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){MagazineID = null;}else{MagazineID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){CatalogID = null;}else{CatalogID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){IssueID = null;}else{IssueID = new Long(v.toString());}return;}
		if (i == 3){Prop1 = (String)v;return;}
		if (i == 4){Prop2 = (String)v;return;}
		if (i == 5){Prop3 = (String)v;return;}
		if (i == 6){Prop4 = (String)v;return;}
		if (i == 7){AddUser = (String)v;return;}
		if (i == 8){AddTime = (Date)v;return;}
		if (i == 9){ModifyUser = (String)v;return;}
		if (i == 10){ModifyTime = (Date)v;return;}
		if (i == 11){BackupNo = (String)v;return;}
		if (i == 12){BackupOperator = (String)v;return;}
		if (i == 13){BackupTime = (Date)v;return;}
		if (i == 14){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return MagazineID;}
		if (i == 1){return CatalogID;}
		if (i == 2){return IssueID;}
		if (i == 3){return Prop1;}
		if (i == 4){return Prop2;}
		if (i == 5){return Prop3;}
		if (i == 6){return Prop4;}
		if (i == 7){return AddUser;}
		if (i == 8){return AddTime;}
		if (i == 9){return ModifyUser;}
		if (i == 10){return ModifyTime;}
		if (i == 11){return BackupNo;}
		if (i == 12){return BackupOperator;}
		if (i == 13){return BackupTime;}
		if (i == 14){return BackupMemo;}
		return null;
	}

	/**
	* 获取字段MagazineID的值，该字段的<br>
	* 字段名称 :MagazineID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getMagazineID() {
		if(MagazineID==null){return 0;}
		return MagazineID.longValue();
	}

	/**
	* 设置字段MagazineID的值，该字段的<br>
	* 字段名称 :MagazineID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setMagazineID(long magazineID) {
		this.MagazineID = new Long(magazineID);
    }

	/**
	* 设置字段MagazineID的值，该字段的<br>
	* 字段名称 :MagazineID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setMagazineID(String magazineID) {
		if (magazineID == null){
			this.MagazineID = null;
			return;
		}
		this.MagazineID = new Long(magazineID);
    }

	/**
	* 获取字段CatalogID的值，该字段的<br>
	* 字段名称 :CatalogID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getCatalogID() {
		if(CatalogID==null){return 0;}
		return CatalogID.longValue();
	}

	/**
	* 设置字段CatalogID的值，该字段的<br>
	* 字段名称 :CatalogID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setCatalogID(long catalogID) {
		this.CatalogID = new Long(catalogID);
    }

	/**
	* 设置字段CatalogID的值，该字段的<br>
	* 字段名称 :CatalogID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setCatalogID(String catalogID) {
		if (catalogID == null){
			this.CatalogID = null;
			return;
		}
		this.CatalogID = new Long(catalogID);
    }

	/**
	* 获取字段IssueID的值，该字段的<br>
	* 字段名称 :IssueID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getIssueID() {
		if(IssueID==null){return 0;}
		return IssueID.longValue();
	}

	/**
	* 设置字段IssueID的值，该字段的<br>
	* 字段名称 :IssueID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIssueID(long issueID) {
		this.IssueID = new Long(issueID);
    }

	/**
	* 设置字段IssueID的值，该字段的<br>
	* 字段名称 :IssueID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIssueID(String issueID) {
		if (issueID == null){
			this.IssueID = null;
			return;
		}
		this.IssueID = new Long(issueID);
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
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(50)<br>
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