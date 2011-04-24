package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCArticleLog备份<br>
 * 表代码：BZCArticleLog<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZCArticleLogSchema extends Schema {
	private Long ID;

	private Long ArticleID;

	private String Action;

	private String ActionDetail;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String AddUser;

	private Date AddTime;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("ArticleID", DataColumn.LONG, 1, 20 , 0 , true , false),
		new SchemaColumn("Action", DataColumn.STRING, 2, 200 , 0 , true , false),
		new SchemaColumn("ActionDetail", DataColumn.STRING, 3, 200 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 8, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 9, 0 , 0 , true , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 10, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 11, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 12, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 13, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCArticleLog";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into BZCArticleLog values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCArticleLog set ID=?,ArticleID=?,Action=?,ActionDetail=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCArticleLog  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCArticleLog  where ID=? and BackupNo=?";

	public BZCArticleLogSchema(){
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
		return new BZCArticleLogSchema();
	}

	protected SchemaSet newSet(){
		return new BZCArticleLogSet();
	}

	public BZCArticleLogSet query() {
		return query(null, -1, -1);
	}

	public BZCArticleLogSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCArticleLogSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCArticleLogSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCArticleLogSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){ArticleID = null;}else{ArticleID = new Long(v.toString());}return;}
		if (i == 2){Action = (String)v;return;}
		if (i == 3){ActionDetail = (String)v;return;}
		if (i == 4){Prop1 = (String)v;return;}
		if (i == 5){Prop2 = (String)v;return;}
		if (i == 6){Prop3 = (String)v;return;}
		if (i == 7){Prop4 = (String)v;return;}
		if (i == 8){AddUser = (String)v;return;}
		if (i == 9){AddTime = (Date)v;return;}
		if (i == 10){BackupNo = (String)v;return;}
		if (i == 11){BackupOperator = (String)v;return;}
		if (i == 12){BackupTime = (Date)v;return;}
		if (i == 13){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return ArticleID;}
		if (i == 2){return Action;}
		if (i == 3){return ActionDetail;}
		if (i == 4){return Prop1;}
		if (i == 5){return Prop2;}
		if (i == 6){return Prop3;}
		if (i == 7){return Prop4;}
		if (i == 8){return AddUser;}
		if (i == 9){return AddTime;}
		if (i == 10){return BackupNo;}
		if (i == 11){return BackupOperator;}
		if (i == 12){return BackupTime;}
		if (i == 13){return BackupMemo;}
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
	* 获取字段ArticleID的值，该字段的<br>
	* 字段名称 :ArticleID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getArticleID() {
		if(ArticleID==null){return 0;}
		return ArticleID.longValue();
	}

	/**
	* 设置字段ArticleID的值，该字段的<br>
	* 字段名称 :ArticleID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setArticleID(long articleID) {
		this.ArticleID = new Long(articleID);
    }

	/**
	* 设置字段ArticleID的值，该字段的<br>
	* 字段名称 :ArticleID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setArticleID(String articleID) {
		if (articleID == null){
			this.ArticleID = null;
			return;
		}
		this.ArticleID = new Long(articleID);
    }

	/**
	* 获取字段Action的值，该字段的<br>
	* 字段名称 :Action<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAction() {
		return Action;
	}

	/**
	* 设置字段Action的值，该字段的<br>
	* 字段名称 :Action<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAction(String action) {
		this.Action = action;
    }

	/**
	* 获取字段ActionDetail的值，该字段的<br>
	* 字段名称 :ActionDetail<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getActionDetail() {
		return ActionDetail;
	}

	/**
	* 设置字段ActionDetail的值，该字段的<br>
	* 字段名称 :ActionDetail<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setActionDetail(String actionDetail) {
		this.ActionDetail = actionDetail;
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