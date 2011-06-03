package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCDeployLog<br>
 * 表代码：ZCDeployLog<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID<br>
 */
public class ZCDeployLogSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private Long JobID;

	private String Message;

	private String Memo;

	private Date BeginTime;

	private Date EndTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 20 , 0 , true , false),
		new SchemaColumn("JobID", DataColumn.LONG, 2, 20 , 0 , true , false),
		new SchemaColumn("Message", DataColumn.STRING, 3, 500 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 4, 200 , 0 , false , false),
		new SchemaColumn("BeginTime", DataColumn.DATETIME, 5, 0 , 0 , false , false),
		new SchemaColumn("EndTime", DataColumn.DATETIME, 6, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCDeployLog";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into ZCDeployLog values(?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCDeployLog set ID=?,SiteID=?,JobID=?,Message=?,Memo=?,BeginTime=?,EndTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCDeployLog  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCDeployLog  where ID=?";

	public ZCDeployLogSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[7];
	}

	protected Schema newInstance(){
		return new ZCDeployLogSchema();
	}

	protected SchemaSet newSet(){
		return new ZCDeployLogSet();
	}

	public ZCDeployLogSet query() {
		return query(null, -1, -1);
	}

	public ZCDeployLogSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCDeployLogSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCDeployLogSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCDeployLogSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){JobID = null;}else{JobID = new Long(v.toString());}return;}
		if (i == 3){Message = (String)v;return;}
		if (i == 4){Memo = (String)v;return;}
		if (i == 5){BeginTime = (Date)v;return;}
		if (i == 6){EndTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return JobID;}
		if (i == 3){return Message;}
		if (i == 4){return Memo;}
		if (i == 5){return BeginTime;}
		if (i == 6){return EndTime;}
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

}