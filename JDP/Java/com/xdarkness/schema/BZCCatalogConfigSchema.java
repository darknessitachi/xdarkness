package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCCatalogConfig备份<br>
 * 表代码：BZCCatalogConfig<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZCCatalogConfigSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private Long CatalogID;

	private String CatalogInnerCode;

	private String CronExpression;

	private String PlanType;

	private Date StartTime;

	private String IsUsing;

	private Long HotWordType;

	private String AllowStatus;

	private String AfterEditStatus;

	private String Encoding;

	private String ArchiveTime;

	private String AttachDownFlag;

	private String BranchManageFlag;

	private String AllowInnerDeploy;

	private String InnerDeployPassword;

	private String SyncCatalogInsert;

	private String SyncCatalogModify;

	private String SyncArticleModify;

	private Long AfterSyncStatus;

	private Long AfterModifyStatus;

	private String AllowInnerGather;

	private String InnerGatherPassword;

	private String AllowComment;

	private String CommentAnonymous;

	private String CommentVerify;

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
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 20 , 0 , true , false),
		new SchemaColumn("CatalogID", DataColumn.LONG, 2, 20 , 0 , false , false),
		new SchemaColumn("CatalogInnerCode", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("CronExpression", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("PlanType", DataColumn.STRING, 5, 10 , 0 , false , false),
		new SchemaColumn("StartTime", DataColumn.DATETIME, 6, 0 , 0 , false , false),
		new SchemaColumn("IsUsing", DataColumn.STRING, 7, 2 , 0 , true , false),
		new SchemaColumn("HotWordType", DataColumn.LONG, 8, 20 , 0 , false , false),
		new SchemaColumn("AllowStatus", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("AfterEditStatus", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("Encoding", DataColumn.STRING, 11, 20 , 0 , false , false),
		new SchemaColumn("ArchiveTime", DataColumn.STRING, 12, 10 , 0 , false , false),
		new SchemaColumn("AttachDownFlag", DataColumn.STRING, 13, 2 , 0 , false , false),
		new SchemaColumn("BranchManageFlag", DataColumn.STRING, 14, 2 , 0 , false , false),
		new SchemaColumn("AllowInnerDeploy", DataColumn.STRING, 15, 2 , 0 , false , false),
		new SchemaColumn("InnerDeployPassword", DataColumn.STRING, 16, 255 , 0 , false , false),
		new SchemaColumn("SyncCatalogInsert", DataColumn.STRING, 17, 2 , 0 , false , false),
		new SchemaColumn("SyncCatalogModify", DataColumn.STRING, 18, 2 , 0 , false , false),
		new SchemaColumn("SyncArticleModify", DataColumn.STRING, 19, 2 , 0 , false , false),
		new SchemaColumn("AfterSyncStatus", DataColumn.LONG, 20, 20 , 0 , false , false),
		new SchemaColumn("AfterModifyStatus", DataColumn.LONG, 21, 20 , 0 , false , false),
		new SchemaColumn("AllowInnerGather", DataColumn.STRING, 22, 2 , 0 , false , false),
		new SchemaColumn("InnerGatherPassword", DataColumn.STRING, 23, 255 , 0 , false , false),
		new SchemaColumn("AllowComment", DataColumn.STRING, 24, 2 , 0 , false , false),
		new SchemaColumn("CommentAnonymous", DataColumn.STRING, 25, 2 , 0 , false , false),
		new SchemaColumn("CommentVerify", DataColumn.STRING, 26, 2 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 27, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 28, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 29, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 30, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 31, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 32, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 33, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 34, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 35, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 36, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 37, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 38, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCCatalogConfig";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into BZCCatalogConfig values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCCatalogConfig set ID=?,SiteID=?,CatalogID=?,CatalogInnerCode=?,CronExpression=?,PlanType=?,StartTime=?,IsUsing=?,HotWordType=?,AllowStatus=?,AfterEditStatus=?,Encoding=?,ArchiveTime=?,AttachDownFlag=?,BranchManageFlag=?,AllowInnerDeploy=?,InnerDeployPassword=?,SyncCatalogInsert=?,SyncCatalogModify=?,SyncArticleModify=?,AfterSyncStatus=?,AfterModifyStatus=?,AllowInnerGather=?,InnerGatherPassword=?,AllowComment=?,CommentAnonymous=?,CommentVerify=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCCatalogConfig  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCCatalogConfig  where ID=? and BackupNo=?";

	public BZCCatalogConfigSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[39];
	}

	protected Schema newInstance(){
		return new BZCCatalogConfigSchema();
	}

	protected SchemaSet newSet(){
		return new BZCCatalogConfigSet();
	}

	public BZCCatalogConfigSet query() {
		return query(null, -1, -1);
	}

	public BZCCatalogConfigSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCCatalogConfigSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCCatalogConfigSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCCatalogConfigSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){CatalogID = null;}else{CatalogID = new Long(v.toString());}return;}
		if (i == 3){CatalogInnerCode = (String)v;return;}
		if (i == 4){CronExpression = (String)v;return;}
		if (i == 5){PlanType = (String)v;return;}
		if (i == 6){StartTime = (Date)v;return;}
		if (i == 7){IsUsing = (String)v;return;}
		if (i == 8){if(v==null){HotWordType = null;}else{HotWordType = new Long(v.toString());}return;}
		if (i == 9){AllowStatus = (String)v;return;}
		if (i == 10){AfterEditStatus = (String)v;return;}
		if (i == 11){Encoding = (String)v;return;}
		if (i == 12){ArchiveTime = (String)v;return;}
		if (i == 13){AttachDownFlag = (String)v;return;}
		if (i == 14){BranchManageFlag = (String)v;return;}
		if (i == 15){AllowInnerDeploy = (String)v;return;}
		if (i == 16){InnerDeployPassword = (String)v;return;}
		if (i == 17){SyncCatalogInsert = (String)v;return;}
		if (i == 18){SyncCatalogModify = (String)v;return;}
		if (i == 19){SyncArticleModify = (String)v;return;}
		if (i == 20){if(v==null){AfterSyncStatus = null;}else{AfterSyncStatus = new Long(v.toString());}return;}
		if (i == 21){if(v==null){AfterModifyStatus = null;}else{AfterModifyStatus = new Long(v.toString());}return;}
		if (i == 22){AllowInnerGather = (String)v;return;}
		if (i == 23){InnerGatherPassword = (String)v;return;}
		if (i == 24){AllowComment = (String)v;return;}
		if (i == 25){CommentAnonymous = (String)v;return;}
		if (i == 26){CommentVerify = (String)v;return;}
		if (i == 27){Prop1 = (String)v;return;}
		if (i == 28){Prop2 = (String)v;return;}
		if (i == 29){Prop3 = (String)v;return;}
		if (i == 30){Prop4 = (String)v;return;}
		if (i == 31){AddUser = (String)v;return;}
		if (i == 32){AddTime = (Date)v;return;}
		if (i == 33){ModifyUser = (String)v;return;}
		if (i == 34){ModifyTime = (Date)v;return;}
		if (i == 35){BackupNo = (String)v;return;}
		if (i == 36){BackupOperator = (String)v;return;}
		if (i == 37){BackupTime = (Date)v;return;}
		if (i == 38){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return CatalogID;}
		if (i == 3){return CatalogInnerCode;}
		if (i == 4){return CronExpression;}
		if (i == 5){return PlanType;}
		if (i == 6){return StartTime;}
		if (i == 7){return IsUsing;}
		if (i == 8){return HotWordType;}
		if (i == 9){return AllowStatus;}
		if (i == 10){return AfterEditStatus;}
		if (i == 11){return Encoding;}
		if (i == 12){return ArchiveTime;}
		if (i == 13){return AttachDownFlag;}
		if (i == 14){return BranchManageFlag;}
		if (i == 15){return AllowInnerDeploy;}
		if (i == 16){return InnerDeployPassword;}
		if (i == 17){return SyncCatalogInsert;}
		if (i == 18){return SyncCatalogModify;}
		if (i == 19){return SyncArticleModify;}
		if (i == 20){return AfterSyncStatus;}
		if (i == 21){return AfterModifyStatus;}
		if (i == 22){return AllowInnerGather;}
		if (i == 23){return InnerGatherPassword;}
		if (i == 24){return AllowComment;}
		if (i == 25){return CommentAnonymous;}
		if (i == 26){return CommentVerify;}
		if (i == 27){return Prop1;}
		if (i == 28){return Prop2;}
		if (i == 29){return Prop3;}
		if (i == 30){return Prop4;}
		if (i == 31){return AddUser;}
		if (i == 32){return AddTime;}
		if (i == 33){return ModifyUser;}
		if (i == 34){return ModifyTime;}
		if (i == 35){return BackupNo;}
		if (i == 36){return BackupOperator;}
		if (i == 37){return BackupTime;}
		if (i == 38){return BackupMemo;}
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
	* 获取字段CatalogID的值，该字段的<br>
	* 字段名称 :CatalogID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getCatalogID() {
		if(CatalogID==null){return 0;}
		return CatalogID.longValue();
	}

	/**
	* 设置字段CatalogID的值，该字段的<br>
	* 字段名称 :CatalogID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCatalogID(long catalogID) {
		this.CatalogID = new Long(catalogID);
    }

	/**
	* 设置字段CatalogID的值，该字段的<br>
	* 字段名称 :CatalogID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCatalogID(String catalogID) {
		if (catalogID == null){
			this.CatalogID = null;
			return;
		}
		this.CatalogID = new Long(catalogID);
    }

	/**
	* 获取字段CatalogInnerCode的值，该字段的<br>
	* 字段名称 :CatalogInnerCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCatalogInnerCode() {
		return CatalogInnerCode;
	}

	/**
	* 设置字段CatalogInnerCode的值，该字段的<br>
	* 字段名称 :CatalogInnerCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCatalogInnerCode(String catalogInnerCode) {
		this.CatalogInnerCode = catalogInnerCode;
    }

	/**
	* 获取字段CronExpression的值，该字段的<br>
	* 字段名称 :CronExpression<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCronExpression() {
		return CronExpression;
	}

	/**
	* 设置字段CronExpression的值，该字段的<br>
	* 字段名称 :CronExpression<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCronExpression(String cronExpression) {
		this.CronExpression = cronExpression;
    }

	/**
	* 获取字段PlanType的值，该字段的<br>
	* 字段名称 :PlanType<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPlanType() {
		return PlanType;
	}

	/**
	* 设置字段PlanType的值，该字段的<br>
	* 字段名称 :PlanType<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPlanType(String planType) {
		this.PlanType = planType;
    }

	/**
	* 获取字段StartTime的值，该字段的<br>
	* 字段名称 :StartTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getStartTime() {
		return StartTime;
	}

	/**
	* 设置字段StartTime的值，该字段的<br>
	* 字段名称 :StartTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStartTime(Date startTime) {
		this.StartTime = startTime;
    }

	/**
	* 获取字段IsUsing的值，该字段的<br>
	* 字段名称 :IsUsing<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getIsUsing() {
		return IsUsing;
	}

	/**
	* 设置字段IsUsing的值，该字段的<br>
	* 字段名称 :IsUsing<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setIsUsing(String isUsing) {
		this.IsUsing = isUsing;
    }

	/**
	* 获取字段HotWordType的值，该字段的<br>
	* 字段名称 :HotWordType<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getHotWordType() {
		if(HotWordType==null){return 0;}
		return HotWordType.longValue();
	}

	/**
	* 设置字段HotWordType的值，该字段的<br>
	* 字段名称 :HotWordType<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setHotWordType(long hotWordType) {
		this.HotWordType = new Long(hotWordType);
    }

	/**
	* 设置字段HotWordType的值，该字段的<br>
	* 字段名称 :HotWordType<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setHotWordType(String hotWordType) {
		if (hotWordType == null){
			this.HotWordType = null;
			return;
		}
		this.HotWordType = new Long(hotWordType);
    }

	/**
	* 获取字段AllowStatus的值，该字段的<br>
	* 字段名称 :AllowStatus<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowStatus() {
		return AllowStatus;
	}

	/**
	* 设置字段AllowStatus的值，该字段的<br>
	* 字段名称 :AllowStatus<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowStatus(String allowStatus) {
		this.AllowStatus = allowStatus;
    }

	/**
	* 获取字段AfterEditStatus的值，该字段的<br>
	* 字段名称 :AfterEditStatus<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAfterEditStatus() {
		return AfterEditStatus;
	}

	/**
	* 设置字段AfterEditStatus的值，该字段的<br>
	* 字段名称 :AfterEditStatus<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAfterEditStatus(String afterEditStatus) {
		this.AfterEditStatus = afterEditStatus;
    }

	/**
	* 获取字段Encoding的值，该字段的<br>
	* 字段名称 :Encoding<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getEncoding() {
		return Encoding;
	}

	/**
	* 设置字段Encoding的值，该字段的<br>
	* 字段名称 :Encoding<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEncoding(String encoding) {
		this.Encoding = encoding;
    }

	/**
	* 获取字段ArchiveTime的值，该字段的<br>
	* 字段名称 :ArchiveTime<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getArchiveTime() {
		return ArchiveTime;
	}

	/**
	* 设置字段ArchiveTime的值，该字段的<br>
	* 字段名称 :ArchiveTime<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setArchiveTime(String archiveTime) {
		this.ArchiveTime = archiveTime;
    }

	/**
	* 获取字段AttachDownFlag的值，该字段的<br>
	* 字段名称 :AttachDownFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAttachDownFlag() {
		return AttachDownFlag;
	}

	/**
	* 设置字段AttachDownFlag的值，该字段的<br>
	* 字段名称 :AttachDownFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAttachDownFlag(String attachDownFlag) {
		this.AttachDownFlag = attachDownFlag;
    }

	/**
	* 获取字段BranchManageFlag的值，该字段的<br>
	* 字段名称 :BranchManageFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBranchManageFlag() {
		return BranchManageFlag;
	}

	/**
	* 设置字段BranchManageFlag的值，该字段的<br>
	* 字段名称 :BranchManageFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBranchManageFlag(String branchManageFlag) {
		this.BranchManageFlag = branchManageFlag;
    }

	/**
	* 获取字段AllowInnerDeploy的值，该字段的<br>
	* 字段名称 :AllowInnerDeploy<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowInnerDeploy() {
		return AllowInnerDeploy;
	}

	/**
	* 设置字段AllowInnerDeploy的值，该字段的<br>
	* 字段名称 :AllowInnerDeploy<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowInnerDeploy(String allowInnerDeploy) {
		this.AllowInnerDeploy = allowInnerDeploy;
    }

	/**
	* 获取字段InnerDeployPassword的值，该字段的<br>
	* 字段名称 :InnerDeployPassword<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInnerDeployPassword() {
		return InnerDeployPassword;
	}

	/**
	* 设置字段InnerDeployPassword的值，该字段的<br>
	* 字段名称 :InnerDeployPassword<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInnerDeployPassword(String innerDeployPassword) {
		this.InnerDeployPassword = innerDeployPassword;
    }

	/**
	* 获取字段SyncCatalogInsert的值，该字段的<br>
	* 字段名称 :SyncCatalogInsert<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSyncCatalogInsert() {
		return SyncCatalogInsert;
	}

	/**
	* 设置字段SyncCatalogInsert的值，该字段的<br>
	* 字段名称 :SyncCatalogInsert<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSyncCatalogInsert(String syncCatalogInsert) {
		this.SyncCatalogInsert = syncCatalogInsert;
    }

	/**
	* 获取字段SyncCatalogModify的值，该字段的<br>
	* 字段名称 :SyncCatalogModify<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSyncCatalogModify() {
		return SyncCatalogModify;
	}

	/**
	* 设置字段SyncCatalogModify的值，该字段的<br>
	* 字段名称 :SyncCatalogModify<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSyncCatalogModify(String syncCatalogModify) {
		this.SyncCatalogModify = syncCatalogModify;
    }

	/**
	* 获取字段SyncArticleModify的值，该字段的<br>
	* 字段名称 :SyncArticleModify<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSyncArticleModify() {
		return SyncArticleModify;
	}

	/**
	* 设置字段SyncArticleModify的值，该字段的<br>
	* 字段名称 :SyncArticleModify<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSyncArticleModify(String syncArticleModify) {
		this.SyncArticleModify = syncArticleModify;
    }

	/**
	* 获取字段AfterSyncStatus的值，该字段的<br>
	* 字段名称 :AfterSyncStatus<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getAfterSyncStatus() {
		if(AfterSyncStatus==null){return 0;}
		return AfterSyncStatus.longValue();
	}

	/**
	* 设置字段AfterSyncStatus的值，该字段的<br>
	* 字段名称 :AfterSyncStatus<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAfterSyncStatus(long afterSyncStatus) {
		this.AfterSyncStatus = new Long(afterSyncStatus);
    }

	/**
	* 设置字段AfterSyncStatus的值，该字段的<br>
	* 字段名称 :AfterSyncStatus<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAfterSyncStatus(String afterSyncStatus) {
		if (afterSyncStatus == null){
			this.AfterSyncStatus = null;
			return;
		}
		this.AfterSyncStatus = new Long(afterSyncStatus);
    }

	/**
	* 获取字段AfterModifyStatus的值，该字段的<br>
	* 字段名称 :AfterModifyStatus<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getAfterModifyStatus() {
		if(AfterModifyStatus==null){return 0;}
		return AfterModifyStatus.longValue();
	}

	/**
	* 设置字段AfterModifyStatus的值，该字段的<br>
	* 字段名称 :AfterModifyStatus<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAfterModifyStatus(long afterModifyStatus) {
		this.AfterModifyStatus = new Long(afterModifyStatus);
    }

	/**
	* 设置字段AfterModifyStatus的值，该字段的<br>
	* 字段名称 :AfterModifyStatus<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAfterModifyStatus(String afterModifyStatus) {
		if (afterModifyStatus == null){
			this.AfterModifyStatus = null;
			return;
		}
		this.AfterModifyStatus = new Long(afterModifyStatus);
    }

	/**
	* 获取字段AllowInnerGather的值，该字段的<br>
	* 字段名称 :AllowInnerGather<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowInnerGather() {
		return AllowInnerGather;
	}

	/**
	* 设置字段AllowInnerGather的值，该字段的<br>
	* 字段名称 :AllowInnerGather<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowInnerGather(String allowInnerGather) {
		this.AllowInnerGather = allowInnerGather;
    }

	/**
	* 获取字段InnerGatherPassword的值，该字段的<br>
	* 字段名称 :InnerGatherPassword<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInnerGatherPassword() {
		return InnerGatherPassword;
	}

	/**
	* 设置字段InnerGatherPassword的值，该字段的<br>
	* 字段名称 :InnerGatherPassword<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInnerGatherPassword(String innerGatherPassword) {
		this.InnerGatherPassword = innerGatherPassword;
    }

	/**
	* 获取字段AllowComment的值，该字段的<br>
	* 字段名称 :AllowComment<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowComment() {
		return AllowComment;
	}

	/**
	* 设置字段AllowComment的值，该字段的<br>
	* 字段名称 :AllowComment<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowComment(String allowComment) {
		this.AllowComment = allowComment;
    }

	/**
	* 获取字段CommentAnonymous的值，该字段的<br>
	* 字段名称 :CommentAnonymous<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCommentAnonymous() {
		return CommentAnonymous;
	}

	/**
	* 设置字段CommentAnonymous的值，该字段的<br>
	* 字段名称 :CommentAnonymous<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCommentAnonymous(String commentAnonymous) {
		this.CommentAnonymous = commentAnonymous;
    }

	/**
	* 获取字段CommentVerify的值，该字段的<br>
	* 字段名称 :CommentVerify<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCommentVerify() {
		return CommentVerify;
	}

	/**
	* 设置字段CommentVerify的值，该字段的<br>
	* 字段名称 :CommentVerify<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCommentVerify(String commentVerify) {
		this.CommentVerify = commentVerify;
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