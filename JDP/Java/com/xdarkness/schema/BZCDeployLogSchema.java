package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCDeployLog备份<br>
 * 表代码：BZCDeployLog<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZCDeployLogSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private Long JobID;

	private String Message;

	private String Memo;

	private Date BeginTime;

	private Date EndTime;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 20 , 0 , true , false),
		new SchemaColumn("JobID", DataColumn.LONG, 2, 20 , 0 , true , false),
		new SchemaColumn("Message", DataColumn.STRING, 3, 500 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 4, 200 , 0 , false , false),
		new SchemaColumn("BeginTime", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("EndTime", DataColumn.DATETIME, 6, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 7, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 8, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 9, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 10, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCDeployLog";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into BZCDeployLog values(?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCDeployLog set ID=?,SiteID=?,JobID=?,Message=?,Memo=?,BeginTime=?,EndTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCDeployLog  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCDeployLog  where ID=? and BackupNo=?";

	public BZCDeployLogSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[11];
	}

	protected Schema newInstance(){
		return new BZCDeployLogSchema();
	}

	protected SchemaSet newSet(){
		return new BZCDeployLogSet();
	}

	public BZCDeployLogSet query() {
		return query(null, -1, -1);
	}

	public BZCDeployLogSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCDeployLogSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCDeployLogSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCDeployLogSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){JobID = null;}else{JobID = new Long(v.toString());}return;}
		if (i == 3){Message = (String)v;return;}
		if (i == 4){Memo = (String)v;return;}
		if (i == 5){BeginTime = (Date)v;return;}
		if (i == 6){EndTime = (Date)v;return;}
		if (i == 7){BackupNo = (String)v;return;}
		if (i == 8){BackupOperator = (String)v;return;}
		if (i == 9){BackupTime = (Date)v;return;}
		if (i == 10){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return JobID;}
		if (i == 3){return Message;}
		if (i == 4){return Memo;}
		if (i == 5){return BeginTime;}
		if (i == 6){return EndTime;}
		if (i == 7){return BackupNo;}
		if (i == 8){return BackupOperator;}
		if (i == 9){return BackupTime;}
		if (i == 10){return BackupMemo;}
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
	* 获取字段JobID的值，该字段的<br>
	* 字段名称 :JobID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getJobID() {
		if(JobID==null){return 0;}
		return JobID.longValue();
	}

	/**
	* 设置字段JobID的值，该字段的<br>
	* 字段名称 :JobID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setJobID(long jobID) {
		this.JobID = new Long(jobID);
    }

	/**
	* 设置字段JobID的值，该字段的<br>
	* 字段名称 :JobID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setJobID(String jobID) {
		if (jobID == null){
			this.JobID = null;
			return;
		}
		this.JobID = new Long(jobID);
    }

	/**
	* 获取字段Message的值，该字段的<br>
	* 字段名称 :Message<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMessage() {
		return Message;
	}

	/**
	* 设置字段Message的值，该字段的<br>
	* 字段名称 :Message<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMessage(String message) {
		this.Message = message;
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
	* 获取字段BeginTime的值，该字段的<br>
	* 字段名称 :BeginTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getBeginTime() {
		return BeginTime;
	}

	/**
	* 设置字段BeginTime的值，该字段的<br>
	* 字段名称 :BeginTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBeginTime(Date beginTime) {
		this.BeginTime = beginTime;
    }

	/**
	* 获取字段EndTime的值，该字段的<br>
	* 字段名称 :EndTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getEndTime() {
		return EndTime;
	}

	/**
	* 设置字段EndTime的值，该字段的<br>
	* 字段名称 :EndTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEndTime(Date endTime) {
		this.EndTime = endTime;
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