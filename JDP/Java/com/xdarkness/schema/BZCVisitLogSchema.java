package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCVisitLog备份<br>
 * 表代码：BZCVisitLog<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID, AddTime, BackupNo<br>
 */
public class BZCVisitLogSchema extends Schema {
	private String ID;

	private Long SiteID;

	private String URL;

	private String IP;

	private String OS;

	private String Browser;

	private String Screen;

	private String Referer;

	private String ColorDepth;

	private String CookieEnabled;

	private String FlashEnabled;

	private String FlashVersion;

	private String JavaEnabled;

	private String Language;

	private String District;

	private Date AddTime;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 50 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 20 , 0 , false , false),
		new SchemaColumn("URL", DataColumn.STRING, 2, 500 , 0 , true , false),
		new SchemaColumn("IP", DataColumn.STRING, 3, 30 , 0 , false , false),
		new SchemaColumn("OS", DataColumn.STRING, 4, 50 , 0 , false , false),
		new SchemaColumn("Browser", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("Screen", DataColumn.STRING, 6, 10 , 0 , false , false),
		new SchemaColumn("Referer", DataColumn.STRING, 7, 500 , 0 , false , false),
		new SchemaColumn("ColorDepth", DataColumn.STRING, 8, 10 , 0 , false , false),
		new SchemaColumn("CookieEnabled", DataColumn.STRING, 9, 10 , 0 , false , false),
		new SchemaColumn("FlashEnabled", DataColumn.STRING, 10, 10 , 0 , false , false),
		new SchemaColumn("FlashVersion", DataColumn.STRING, 11, 30 , 0 , false , false),
		new SchemaColumn("JavaEnabled", DataColumn.STRING, 12, 10 , 0 , false , false),
		new SchemaColumn("Language", DataColumn.STRING, 13, 30 , 0 , false , false),
		new SchemaColumn("District", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 15, 0 , 0 , true , true),
		new SchemaColumn("BackupNo", DataColumn.STRING, 16, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 17, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 18, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 19, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCVisitLog";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into BZCVisitLog values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCVisitLog set ID=?,SiteID=?,URL=?,IP=?,OS=?,Browser=?,Screen=?,Referer=?,ColorDepth=?,CookieEnabled=?,FlashEnabled=?,FlashVersion=?,JavaEnabled=?,Language=?,District=?,AddTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and AddTime=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCVisitLog  where ID=? and AddTime=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCVisitLog  where ID=? and AddTime=? and BackupNo=?";

	public BZCVisitLogSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[20];
	}

	protected Schema newInstance(){
		return new BZCVisitLogSchema();
	}

	protected SchemaSet newSet(){
		return new BZCVisitLogSet();
	}

	public BZCVisitLogSet query() {
		return query(null, -1, -1);
	}

	public BZCVisitLogSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCVisitLogSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCVisitLogSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCVisitLogSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){URL = (String)v;return;}
		if (i == 3){IP = (String)v;return;}
		if (i == 4){OS = (String)v;return;}
		if (i == 5){Browser = (String)v;return;}
		if (i == 6){Screen = (String)v;return;}
		if (i == 7){Referer = (String)v;return;}
		if (i == 8){ColorDepth = (String)v;return;}
		if (i == 9){CookieEnabled = (String)v;return;}
		if (i == 10){FlashEnabled = (String)v;return;}
		if (i == 11){FlashVersion = (String)v;return;}
		if (i == 12){JavaEnabled = (String)v;return;}
		if (i == 13){Language = (String)v;return;}
		if (i == 14){District = (String)v;return;}
		if (i == 15){AddTime = (Date)v;return;}
		if (i == 16){BackupNo = (String)v;return;}
		if (i == 17){BackupOperator = (String)v;return;}
		if (i == 18){BackupTime = (Date)v;return;}
		if (i == 19){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return URL;}
		if (i == 3){return IP;}
		if (i == 4){return OS;}
		if (i == 5){return Browser;}
		if (i == 6){return Screen;}
		if (i == 7){return Referer;}
		if (i == 8){return ColorDepth;}
		if (i == 9){return CookieEnabled;}
		if (i == 10){return FlashEnabled;}
		if (i == 11){return FlashVersion;}
		if (i == 12){return JavaEnabled;}
		if (i == 13){return Language;}
		if (i == 14){return District;}
		if (i == 15){return AddTime;}
		if (i == 16){return BackupNo;}
		if (i == 17){return BackupOperator;}
		if (i == 18){return BackupTime;}
		if (i == 19){return BackupMemo;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
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
	* 获取字段URL的值，该字段的<br>
	* 字段名称 :URL<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getURL() {
		return URL;
	}

	/**
	* 设置字段URL的值，该字段的<br>
	* 字段名称 :URL<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setURL(String uRL) {
		this.URL = uRL;
    }

	/**
	* 获取字段IP的值，该字段的<br>
	* 字段名称 :IP<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIP() {
		return IP;
	}

	/**
	* 设置字段IP的值，该字段的<br>
	* 字段名称 :IP<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIP(String iP) {
		this.IP = iP;
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
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getScreen() {
		return Screen;
	}

	/**
	* 设置字段Screen的值，该字段的<br>
	* 字段名称 :Screen<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setScreen(String screen) {
		this.Screen = screen;
    }

	/**
	* 获取字段Referer的值，该字段的<br>
	* 字段名称 :Referer<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getReferer() {
		return Referer;
	}

	/**
	* 设置字段Referer的值，该字段的<br>
	* 字段名称 :Referer<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReferer(String referer) {
		this.Referer = referer;
    }

	/**
	* 获取字段ColorDepth的值，该字段的<br>
	* 字段名称 :ColorDepth<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getColorDepth() {
		return ColorDepth;
	}

	/**
	* 设置字段ColorDepth的值，该字段的<br>
	* 字段名称 :ColorDepth<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setColorDepth(String colorDepth) {
		this.ColorDepth = colorDepth;
    }

	/**
	* 获取字段CookieEnabled的值，该字段的<br>
	* 字段名称 :CookieEnabled<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCookieEnabled() {
		return CookieEnabled;
	}

	/**
	* 设置字段CookieEnabled的值，该字段的<br>
	* 字段名称 :CookieEnabled<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCookieEnabled(String cookieEnabled) {
		this.CookieEnabled = cookieEnabled;
    }

	/**
	* 获取字段FlashEnabled的值，该字段的<br>
	* 字段名称 :FlashEnabled<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFlashEnabled() {
		return FlashEnabled;
	}

	/**
	* 设置字段FlashEnabled的值，该字段的<br>
	* 字段名称 :FlashEnabled<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFlashEnabled(String flashEnabled) {
		this.FlashEnabled = flashEnabled;
    }

	/**
	* 获取字段FlashVersion的值，该字段的<br>
	* 字段名称 :FlashVersion<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFlashVersion() {
		return FlashVersion;
	}

	/**
	* 设置字段FlashVersion的值，该字段的<br>
	* 字段名称 :FlashVersion<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFlashVersion(String flashVersion) {
		this.FlashVersion = flashVersion;
    }

	/**
	* 获取字段JavaEnabled的值，该字段的<br>
	* 字段名称 :JavaEnabled<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getJavaEnabled() {
		return JavaEnabled;
	}

	/**
	* 设置字段JavaEnabled的值，该字段的<br>
	* 字段名称 :JavaEnabled<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setJavaEnabled(String javaEnabled) {
		this.JavaEnabled = javaEnabled;
    }

	/**
	* 获取字段Language的值，该字段的<br>
	* 字段名称 :Language<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLanguage() {
		return Language;
	}

	/**
	* 设置字段Language的值，该字段的<br>
	* 字段名称 :Language<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLanguage(String language) {
		this.Language = language;
    }

	/**
	* 获取字段District的值，该字段的<br>
	* 字段名称 :District<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDistrict() {
		return District;
	}

	/**
	* 设置字段District的值，该字段的<br>
	* 字段名称 :District<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDistrict(String district) {
		this.District = district;
    }

	/**
	* 获取字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public Date getAddTime() {
		return AddTime;
	}

	/**
	* 设置字段AddTime的值，该字段的<br>
	* 字段名称 :AddTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :true<br>
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