package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCAdVisitLog备份<br>
 * 表代码：BZCAdVisitLog<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZCAdVisitLogSchema extends Schema {
	private Long ID;

	private Long AdID;

	private Date ServerTime;

	private Date ClientTime;

	private String IP;

	private String Address;

	private String OS;

	private String Browser;

	private String Screen;

	private String Depth;

	private String Referer;

	private String CurrentPage;

	private String Visitor;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("AdID", DataColumn.LONG, 1, 20 , 0 , true , false),
		new SchemaColumn("ServerTime", DataColumn.DATETIME, 2, 0 , 0 , false , false),
		new SchemaColumn("ClientTime", DataColumn.DATETIME, 3, 0 , 0 , false , false),
		new SchemaColumn("IP", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("Address", DataColumn.STRING, 5, 200 , 0 , false , false),
		new SchemaColumn("OS", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("Browser", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("Screen", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("Depth", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("Referer", DataColumn.STRING, 10, 250 , 0 , false , false),
		new SchemaColumn("CurrentPage", DataColumn.STRING, 11, 250 , 0 , false , false),
		new SchemaColumn("Visitor", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 13, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 14, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 15, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 16, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCAdVisitLog";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into BZCAdVisitLog values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCAdVisitLog set ID=?,AdID=?,ServerTime=?,ClientTime=?,IP=?,Address=?,OS=?,Browser=?,Screen=?,Depth=?,Referer=?,CurrentPage=?,Visitor=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCAdVisitLog  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCAdVisitLog  where ID=? and BackupNo=?";

	public BZCAdVisitLogSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[17];
	}

	protected Schema newInstance(){
		return new BZCAdVisitLogSchema();
	}

	protected SchemaSet newSet(){
		return new BZCAdVisitLogSet();
	}

	public BZCAdVisitLogSet query() {
		return query(null, -1, -1);
	}

	public BZCAdVisitLogSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCAdVisitLogSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCAdVisitLogSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCAdVisitLogSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){AdID = null;}else{AdID = new Long(v.toString());}return;}
		if (i == 2){ServerTime = (Date)v;return;}
		if (i == 3){ClientTime = (Date)v;return;}
		if (i == 4){IP = (String)v;return;}
		if (i == 5){Address = (String)v;return;}
		if (i == 6){OS = (String)v;return;}
		if (i == 7){Browser = (String)v;return;}
		if (i == 8){Screen = (String)v;return;}
		if (i == 9){Depth = (String)v;return;}
		if (i == 10){Referer = (String)v;return;}
		if (i == 11){CurrentPage = (String)v;return;}
		if (i == 12){Visitor = (String)v;return;}
		if (i == 13){BackupNo = (String)v;return;}
		if (i == 14){BackupOperator = (String)v;return;}
		if (i == 15){BackupTime = (Date)v;return;}
		if (i == 16){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return AdID;}
		if (i == 2){return ServerTime;}
		if (i == 3){return ClientTime;}
		if (i == 4){return IP;}
		if (i == 5){return Address;}
		if (i == 6){return OS;}
		if (i == 7){return Browser;}
		if (i == 8){return Screen;}
		if (i == 9){return Depth;}
		if (i == 10){return Referer;}
		if (i == 11){return CurrentPage;}
		if (i == 12){return Visitor;}
		if (i == 13){return BackupNo;}
		if (i == 14){return BackupOperator;}
		if (i == 15){return BackupTime;}
		if (i == 16){return BackupMemo;}
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
	* 获取字段AdID的值，该字段的<br>
	* 字段名称 :AdID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getAdID() {
		if(AdID==null){return 0;}
		return AdID.longValue();
	}

	/**
	* 设置字段AdID的值，该字段的<br>
	* 字段名称 :AdID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAdID(long adID) {
		this.AdID = new Long(adID);
    }

	/**
	* 设置字段AdID的值，该字段的<br>
	* 字段名称 :AdID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAdID(String adID) {
		if (adID == null){
			this.AdID = null;
			return;
		}
		this.AdID = new Long(adID);
    }

	/**
	* 获取字段ServerTime的值，该字段的<br>
	* 字段名称 :ServerTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getServerTime() {
		return ServerTime;
	}

	/**
	* 设置字段ServerTime的值，该字段的<br>
	* 字段名称 :ServerTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setServerTime(Date serverTime) {
		this.ServerTime = serverTime;
    }

	/**
	* 获取字段ClientTime的值，该字段的<br>
	* 字段名称 :ClientTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getClientTime() {
		return ClientTime;
	}

	/**
	* 设置字段ClientTime的值，该字段的<br>
	* 字段名称 :ClientTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setClientTime(Date clientTime) {
		this.ClientTime = clientTime;
    }

	/**
	* 获取字段IP的值，该字段的<br>
	* 字段名称 :IP<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIP() {
		return IP;
	}

	/**
	* 设置字段IP的值，该字段的<br>
	* 字段名称 :IP<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIP(String iP) {
		this.IP = iP;
    }

	/**
	* 获取字段Address的值，该字段的<br>
	* 字段名称 :Address<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddress() {
		return Address;
	}

	/**
	* 设置字段Address的值，该字段的<br>
	* 字段名称 :Address<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddress(String address) {
		this.Address = address;
    }

	/**
	* 获取字段OS的值，该字段的<br>
	* 字段名称 :OS<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOS() {
		return OS;
	}

	/**
	* 设置字段OS的值，该字段的<br>
	* 字段名称 :OS<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOS(String oS) {
		this.OS = oS;
    }

	/**
	* 获取字段Browser的值，该字段的<br>
	* 字段名称 :Browser<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBrowser() {
		return Browser;
	}

	/**
	* 设置字段Browser的值，该字段的<br>
	* 字段名称 :Browser<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBrowser(String browser) {
		this.Browser = browser;
    }

	/**
	* 获取字段Screen的值，该字段的<br>
	* 字段名称 :Screen<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getScreen() {
		return Screen;
	}

	/**
	* 设置字段Screen的值，该字段的<br>
	* 字段名称 :Screen<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setScreen(String screen) {
		this.Screen = screen;
    }

	/**
	* 获取字段Depth的值，该字段的<br>
	* 字段名称 :Depth<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDepth() {
		return Depth;
	}

	/**
	* 设置字段Depth的值，该字段的<br>
	* 字段名称 :Depth<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDepth(String depth) {
		this.Depth = depth;
    }

	/**
	* 获取字段Referer的值，该字段的<br>
	* 字段名称 :Referer<br>
	* 数据类型 :varchar(250)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getReferer() {
		return Referer;
	}

	/**
	* 设置字段Referer的值，该字段的<br>
	* 字段名称 :Referer<br>
	* 数据类型 :varchar(250)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReferer(String referer) {
		this.Referer = referer;
    }

	/**
	* 获取字段CurrentPage的值，该字段的<br>
	* 字段名称 :CurrentPage<br>
	* 数据类型 :varchar(250)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCurrentPage() {
		return CurrentPage;
	}

	/**
	* 设置字段CurrentPage的值，该字段的<br>
	* 字段名称 :CurrentPage<br>
	* 数据类型 :varchar(250)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCurrentPage(String currentPage) {
		this.CurrentPage = currentPage;
    }

	/**
	* 获取字段Visitor的值，该字段的<br>
	* 字段名称 :Visitor<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getVisitor() {
		return Visitor;
	}

	/**
	* 设置字段Visitor的值，该字段的<br>
	* 字段名称 :Visitor<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setVisitor(String visitor) {
		this.Visitor = visitor;
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