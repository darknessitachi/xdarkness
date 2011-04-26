package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCSite备份<br>
 * 表代码：BZCSite<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZCSiteSchema extends Schema {
	private Long ID;

	private String Name;

	private String Alias;

	private String Info;

	private String BranchInnerCode;

	private String URL;

	private String RootPath;

	private String IndexTemplate;

	private String ListTemplate;

	private String DetailTemplate;

	private String EditorCss;

	private String Workflow;

	private Long OrderFlag;

	private String LogoFileName;

	private String MessageBoardFlag;

	private String CommentAuditFlag;

	private Long ChannelCount;

	private Long MagzineCount;

	private Long SpecialCount;

	private Long ImageLibCount;

	private Long VideoLibCount;

	private Long AudioLibCount;

	private Long AttachmentLibCount;

	private Long ArticleCount;

	private Long HitCount;

	private String ConfigXML;

	private String AutoIndexFlag;

	private String AutoStatFlag;

	private String HeaderTemplate;

	private String TopTemplate;

	private String BottomTemplate;

	private String AllowContribute;

	private String BBSEnableFlag;

	private String ShopEnableFlag;

	private String Meta_Keywords;

	private String Meta_Description;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String Prop5;

	private String Prop6;

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
		new SchemaColumn("Name", DataColumn.STRING, 1, 100 , 0 , true , false),
		new SchemaColumn("Alias", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("Info", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("BranchInnerCode", DataColumn.STRING, 4, 100 , 0 , true , false),
		new SchemaColumn("URL", DataColumn.STRING, 5, 100 , 0 , true , false),
		new SchemaColumn("RootPath", DataColumn.STRING, 6, 100 , 0 , false , false),
		new SchemaColumn("IndexTemplate", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("ListTemplate", DataColumn.STRING, 8, 100 , 0 , false , false),
		new SchemaColumn("DetailTemplate", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("EditorCss", DataColumn.STRING, 10, 100 , 0 , false , false),
		new SchemaColumn("Workflow", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 12, 20 , 0 , false , false),
		new SchemaColumn("LogoFileName", DataColumn.STRING, 13, 100 , 0 , false , false),
		new SchemaColumn("MessageBoardFlag", DataColumn.STRING, 14, 2 , 0 , false , false),
		new SchemaColumn("CommentAuditFlag", DataColumn.STRING, 15, 1 , 0 , false , false),
		new SchemaColumn("ChannelCount", DataColumn.LONG, 16, 20 , 0 , true , false),
		new SchemaColumn("MagzineCount", DataColumn.LONG, 17, 20 , 0 , true , false),
		new SchemaColumn("SpecialCount", DataColumn.LONG, 18, 20 , 0 , true , false),
		new SchemaColumn("ImageLibCount", DataColumn.LONG, 19, 20 , 0 , true , false),
		new SchemaColumn("VideoLibCount", DataColumn.LONG, 20, 20 , 0 , true , false),
		new SchemaColumn("AudioLibCount", DataColumn.LONG, 21, 20 , 0 , true , false),
		new SchemaColumn("AttachmentLibCount", DataColumn.LONG, 22, 20 , 0 , true , false),
		new SchemaColumn("ArticleCount", DataColumn.LONG, 23, 20 , 0 , true , false),
		new SchemaColumn("HitCount", DataColumn.LONG, 24, 20 , 0 , true , false),
		new SchemaColumn("ConfigXML", DataColumn.CLOB, 25, 0 , 0 , false , false),
		new SchemaColumn("AutoIndexFlag", DataColumn.STRING, 26, 2 , 0 , false , false),
		new SchemaColumn("AutoStatFlag", DataColumn.STRING, 27, 2 , 0 , false , false),
		new SchemaColumn("HeaderTemplate", DataColumn.STRING, 28, 100 , 0 , false , false),
		new SchemaColumn("TopTemplate", DataColumn.STRING, 29, 100 , 0 , false , false),
		new SchemaColumn("BottomTemplate", DataColumn.STRING, 30, 100 , 0 , false , false),
		new SchemaColumn("AllowContribute", DataColumn.STRING, 31, 2 , 0 , false , false),
		new SchemaColumn("BBSEnableFlag", DataColumn.STRING, 32, 2 , 0 , false , false),
		new SchemaColumn("ShopEnableFlag", DataColumn.STRING, 33, 2 , 0 , false , false),
		new SchemaColumn("Meta_Keywords", DataColumn.STRING, 34, 200 , 0 , false , false),
		new SchemaColumn("Meta_Description", DataColumn.STRING, 35, 400 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 36, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 37, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 38, 100 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 39, 100 , 0 , false , false),
		new SchemaColumn("Prop5", DataColumn.STRING, 40, 100 , 0 , false , false),
		new SchemaColumn("Prop6", DataColumn.STRING, 41, 100 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 42, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 43, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 44, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 45, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 46, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 47, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 48, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 49, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCSite";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into BZCSite values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCSite set ID=?,Name=?,Alias=?,Info=?,BranchInnerCode=?,URL=?,RootPath=?,IndexTemplate=?,ListTemplate=?,DetailTemplate=?,EditorCss=?,Workflow=?,OrderFlag=?,LogoFileName=?,MessageBoardFlag=?,CommentAuditFlag=?,ChannelCount=?,MagzineCount=?,SpecialCount=?,ImageLibCount=?,VideoLibCount=?,AudioLibCount=?,AttachmentLibCount=?,ArticleCount=?,HitCount=?,ConfigXML=?,AutoIndexFlag=?,AutoStatFlag=?,HeaderTemplate=?,TopTemplate=?,BottomTemplate=?,AllowContribute=?,BBSEnableFlag=?,ShopEnableFlag=?,Meta_Keywords=?,Meta_Description=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,Prop5=?,Prop6=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCSite  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCSite  where ID=? and BackupNo=?";

	public BZCSiteSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[50];
	}

	protected Schema newInstance(){
		return new BZCSiteSchema();
	}

	protected SchemaSet newSet(){
		return new BZCSiteSet();
	}

	public BZCSiteSet query() {
		return query(null, -1, -1);
	}

	public BZCSiteSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCSiteSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCSiteSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCSiteSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){Name = (String)v;return;}
		if (i == 2){Alias = (String)v;return;}
		if (i == 3){Info = (String)v;return;}
		if (i == 4){BranchInnerCode = (String)v;return;}
		if (i == 5){URL = (String)v;return;}
		if (i == 6){RootPath = (String)v;return;}
		if (i == 7){IndexTemplate = (String)v;return;}
		if (i == 8){ListTemplate = (String)v;return;}
		if (i == 9){DetailTemplate = (String)v;return;}
		if (i == 10){EditorCss = (String)v;return;}
		if (i == 11){Workflow = (String)v;return;}
		if (i == 12){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 13){LogoFileName = (String)v;return;}
		if (i == 14){MessageBoardFlag = (String)v;return;}
		if (i == 15){CommentAuditFlag = (String)v;return;}
		if (i == 16){if(v==null){ChannelCount = null;}else{ChannelCount = new Long(v.toString());}return;}
		if (i == 17){if(v==null){MagzineCount = null;}else{MagzineCount = new Long(v.toString());}return;}
		if (i == 18){if(v==null){SpecialCount = null;}else{SpecialCount = new Long(v.toString());}return;}
		if (i == 19){if(v==null){ImageLibCount = null;}else{ImageLibCount = new Long(v.toString());}return;}
		if (i == 20){if(v==null){VideoLibCount = null;}else{VideoLibCount = new Long(v.toString());}return;}
		if (i == 21){if(v==null){AudioLibCount = null;}else{AudioLibCount = new Long(v.toString());}return;}
		if (i == 22){if(v==null){AttachmentLibCount = null;}else{AttachmentLibCount = new Long(v.toString());}return;}
		if (i == 23){if(v==null){ArticleCount = null;}else{ArticleCount = new Long(v.toString());}return;}
		if (i == 24){if(v==null){HitCount = null;}else{HitCount = new Long(v.toString());}return;}
		if (i == 25){ConfigXML = (String)v;return;}
		if (i == 26){AutoIndexFlag = (String)v;return;}
		if (i == 27){AutoStatFlag = (String)v;return;}
		if (i == 28){HeaderTemplate = (String)v;return;}
		if (i == 29){TopTemplate = (String)v;return;}
		if (i == 30){BottomTemplate = (String)v;return;}
		if (i == 31){AllowContribute = (String)v;return;}
		if (i == 32){BBSEnableFlag = (String)v;return;}
		if (i == 33){ShopEnableFlag = (String)v;return;}
		if (i == 34){Meta_Keywords = (String)v;return;}
		if (i == 35){Meta_Description = (String)v;return;}
		if (i == 36){Prop1 = (String)v;return;}
		if (i == 37){Prop2 = (String)v;return;}
		if (i == 38){Prop3 = (String)v;return;}
		if (i == 39){Prop4 = (String)v;return;}
		if (i == 40){Prop5 = (String)v;return;}
		if (i == 41){Prop6 = (String)v;return;}
		if (i == 42){AddUser = (String)v;return;}
		if (i == 43){AddTime = (Date)v;return;}
		if (i == 44){ModifyUser = (String)v;return;}
		if (i == 45){ModifyTime = (Date)v;return;}
		if (i == 46){BackupNo = (String)v;return;}
		if (i == 47){BackupOperator = (String)v;return;}
		if (i == 48){BackupTime = (Date)v;return;}
		if (i == 49){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return Name;}
		if (i == 2){return Alias;}
		if (i == 3){return Info;}
		if (i == 4){return BranchInnerCode;}
		if (i == 5){return URL;}
		if (i == 6){return RootPath;}
		if (i == 7){return IndexTemplate;}
		if (i == 8){return ListTemplate;}
		if (i == 9){return DetailTemplate;}
		if (i == 10){return EditorCss;}
		if (i == 11){return Workflow;}
		if (i == 12){return OrderFlag;}
		if (i == 13){return LogoFileName;}
		if (i == 14){return MessageBoardFlag;}
		if (i == 15){return CommentAuditFlag;}
		if (i == 16){return ChannelCount;}
		if (i == 17){return MagzineCount;}
		if (i == 18){return SpecialCount;}
		if (i == 19){return ImageLibCount;}
		if (i == 20){return VideoLibCount;}
		if (i == 21){return AudioLibCount;}
		if (i == 22){return AttachmentLibCount;}
		if (i == 23){return ArticleCount;}
		if (i == 24){return HitCount;}
		if (i == 25){return ConfigXML;}
		if (i == 26){return AutoIndexFlag;}
		if (i == 27){return AutoStatFlag;}
		if (i == 28){return HeaderTemplate;}
		if (i == 29){return TopTemplate;}
		if (i == 30){return BottomTemplate;}
		if (i == 31){return AllowContribute;}
		if (i == 32){return BBSEnableFlag;}
		if (i == 33){return ShopEnableFlag;}
		if (i == 34){return Meta_Keywords;}
		if (i == 35){return Meta_Description;}
		if (i == 36){return Prop1;}
		if (i == 37){return Prop2;}
		if (i == 38){return Prop3;}
		if (i == 39){return Prop4;}
		if (i == 40){return Prop5;}
		if (i == 41){return Prop6;}
		if (i == 42){return AddUser;}
		if (i == 43){return AddTime;}
		if (i == 44){return ModifyUser;}
		if (i == 45){return ModifyTime;}
		if (i == 46){return BackupNo;}
		if (i == 47){return BackupOperator;}
		if (i == 48){return BackupTime;}
		if (i == 49){return BackupMemo;}
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
	* 获取字段Alias的值，该字段的<br>
	* 字段名称 :Alias<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAlias() {
		return Alias;
	}

	/**
	* 设置字段Alias的值，该字段的<br>
	* 字段名称 :Alias<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAlias(String alias) {
		this.Alias = alias;
    }

	/**
	* 获取字段Info的值，该字段的<br>
	* 字段名称 :Info<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInfo() {
		return Info;
	}

	/**
	* 设置字段Info的值，该字段的<br>
	* 字段名称 :Info<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInfo(String info) {
		this.Info = info;
    }

	/**
	* 获取字段BranchInnerCode的值，该字段的<br>
	* 字段名称 :BranchInnerCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getBranchInnerCode() {
		return BranchInnerCode;
	}

	/**
	* 设置字段BranchInnerCode的值，该字段的<br>
	* 字段名称 :BranchInnerCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBranchInnerCode(String branchInnerCode) {
		this.BranchInnerCode = branchInnerCode;
    }

	/**
	* 获取字段URL的值，该字段的<br>
	* 字段名称 :URL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getURL() {
		return URL;
	}

	/**
	* 设置字段URL的值，该字段的<br>
	* 字段名称 :URL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setURL(String uRL) {
		this.URL = uRL;
    }

	/**
	* 获取字段RootPath的值，该字段的<br>
	* 字段名称 :RootPath<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRootPath() {
		return RootPath;
	}

	/**
	* 设置字段RootPath的值，该字段的<br>
	* 字段名称 :RootPath<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRootPath(String rootPath) {
		this.RootPath = rootPath;
    }

	/**
	* 获取字段IndexTemplate的值，该字段的<br>
	* 字段名称 :IndexTemplate<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIndexTemplate() {
		return IndexTemplate;
	}

	/**
	* 设置字段IndexTemplate的值，该字段的<br>
	* 字段名称 :IndexTemplate<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIndexTemplate(String indexTemplate) {
		this.IndexTemplate = indexTemplate;
    }

	/**
	* 获取字段ListTemplate的值，该字段的<br>
	* 字段名称 :ListTemplate<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getListTemplate() {
		return ListTemplate;
	}

	/**
	* 设置字段ListTemplate的值，该字段的<br>
	* 字段名称 :ListTemplate<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setListTemplate(String listTemplate) {
		this.ListTemplate = listTemplate;
    }

	/**
	* 获取字段DetailTemplate的值，该字段的<br>
	* 字段名称 :DetailTemplate<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDetailTemplate() {
		return DetailTemplate;
	}

	/**
	* 设置字段DetailTemplate的值，该字段的<br>
	* 字段名称 :DetailTemplate<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDetailTemplate(String detailTemplate) {
		this.DetailTemplate = detailTemplate;
    }

	/**
	* 获取字段EditorCss的值，该字段的<br>
	* 字段名称 :EditorCss<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getEditorCss() {
		return EditorCss;
	}

	/**
	* 设置字段EditorCss的值，该字段的<br>
	* 字段名称 :EditorCss<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEditorCss(String editorCss) {
		this.EditorCss = editorCss;
    }

	/**
	* 获取字段Workflow的值，该字段的<br>
	* 字段名称 :Workflow<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getWorkflow() {
		return Workflow;
	}

	/**
	* 设置字段Workflow的值，该字段的<br>
	* 字段名称 :Workflow<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setWorkflow(String workflow) {
		this.Workflow = workflow;
    }

	/**
	* 获取字段OrderFlag的值，该字段的<br>
	* 字段名称 :OrderFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
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
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :OrderFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(String orderFlag) {
		if (orderFlag == null){
			this.OrderFlag = null;
			return;
		}
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 获取字段LogoFileName的值，该字段的<br>
	* 字段名称 :LogoFileName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLogoFileName() {
		return LogoFileName;
	}

	/**
	* 设置字段LogoFileName的值，该字段的<br>
	* 字段名称 :LogoFileName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLogoFileName(String logoFileName) {
		this.LogoFileName = logoFileName;
    }

	/**
	* 获取字段MessageBoardFlag的值，该字段的<br>
	* 字段名称 :MessageBoardFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMessageBoardFlag() {
		return MessageBoardFlag;
	}

	/**
	* 设置字段MessageBoardFlag的值，该字段的<br>
	* 字段名称 :MessageBoardFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMessageBoardFlag(String messageBoardFlag) {
		this.MessageBoardFlag = messageBoardFlag;
    }

	/**
	* 获取字段CommentAuditFlag的值，该字段的<br>
	* 字段名称 :CommentAuditFlag<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCommentAuditFlag() {
		return CommentAuditFlag;
	}

	/**
	* 设置字段CommentAuditFlag的值，该字段的<br>
	* 字段名称 :CommentAuditFlag<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCommentAuditFlag(String commentAuditFlag) {
		this.CommentAuditFlag = commentAuditFlag;
    }

	/**
	* 获取字段ChannelCount的值，该字段的<br>
	* 字段名称 :ChannelCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getChannelCount() {
		if(ChannelCount==null){return 0;}
		return ChannelCount.longValue();
	}

	/**
	* 设置字段ChannelCount的值，该字段的<br>
	* 字段名称 :ChannelCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setChannelCount(long channelCount) {
		this.ChannelCount = new Long(channelCount);
    }

	/**
	* 设置字段ChannelCount的值，该字段的<br>
	* 字段名称 :ChannelCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setChannelCount(String channelCount) {
		if (channelCount == null){
			this.ChannelCount = null;
			return;
		}
		this.ChannelCount = new Long(channelCount);
    }

	/**
	* 获取字段MagzineCount的值，该字段的<br>
	* 字段名称 :MagzineCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getMagzineCount() {
		if(MagzineCount==null){return 0;}
		return MagzineCount.longValue();
	}

	/**
	* 设置字段MagzineCount的值，该字段的<br>
	* 字段名称 :MagzineCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMagzineCount(long magzineCount) {
		this.MagzineCount = new Long(magzineCount);
    }

	/**
	* 设置字段MagzineCount的值，该字段的<br>
	* 字段名称 :MagzineCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMagzineCount(String magzineCount) {
		if (magzineCount == null){
			this.MagzineCount = null;
			return;
		}
		this.MagzineCount = new Long(magzineCount);
    }

	/**
	* 获取字段SpecialCount的值，该字段的<br>
	* 字段名称 :SpecialCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getSpecialCount() {
		if(SpecialCount==null){return 0;}
		return SpecialCount.longValue();
	}

	/**
	* 设置字段SpecialCount的值，该字段的<br>
	* 字段名称 :SpecialCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSpecialCount(long specialCount) {
		this.SpecialCount = new Long(specialCount);
    }

	/**
	* 设置字段SpecialCount的值，该字段的<br>
	* 字段名称 :SpecialCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSpecialCount(String specialCount) {
		if (specialCount == null){
			this.SpecialCount = null;
			return;
		}
		this.SpecialCount = new Long(specialCount);
    }

	/**
	* 获取字段ImageLibCount的值，该字段的<br>
	* 字段名称 :ImageLibCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getImageLibCount() {
		if(ImageLibCount==null){return 0;}
		return ImageLibCount.longValue();
	}

	/**
	* 设置字段ImageLibCount的值，该字段的<br>
	* 字段名称 :ImageLibCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setImageLibCount(long imageLibCount) {
		this.ImageLibCount = new Long(imageLibCount);
    }

	/**
	* 设置字段ImageLibCount的值，该字段的<br>
	* 字段名称 :ImageLibCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setImageLibCount(String imageLibCount) {
		if (imageLibCount == null){
			this.ImageLibCount = null;
			return;
		}
		this.ImageLibCount = new Long(imageLibCount);
    }

	/**
	* 获取字段VideoLibCount的值，该字段的<br>
	* 字段名称 :VideoLibCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getVideoLibCount() {
		if(VideoLibCount==null){return 0;}
		return VideoLibCount.longValue();
	}

	/**
	* 设置字段VideoLibCount的值，该字段的<br>
	* 字段名称 :VideoLibCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setVideoLibCount(long videoLibCount) {
		this.VideoLibCount = new Long(videoLibCount);
    }

	/**
	* 设置字段VideoLibCount的值，该字段的<br>
	* 字段名称 :VideoLibCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setVideoLibCount(String videoLibCount) {
		if (videoLibCount == null){
			this.VideoLibCount = null;
			return;
		}
		this.VideoLibCount = new Long(videoLibCount);
    }

	/**
	* 获取字段AudioLibCount的值，该字段的<br>
	* 字段名称 :AudioLibCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getAudioLibCount() {
		if(AudioLibCount==null){return 0;}
		return AudioLibCount.longValue();
	}

	/**
	* 设置字段AudioLibCount的值，该字段的<br>
	* 字段名称 :AudioLibCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAudioLibCount(long audioLibCount) {
		this.AudioLibCount = new Long(audioLibCount);
    }

	/**
	* 设置字段AudioLibCount的值，该字段的<br>
	* 字段名称 :AudioLibCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAudioLibCount(String audioLibCount) {
		if (audioLibCount == null){
			this.AudioLibCount = null;
			return;
		}
		this.AudioLibCount = new Long(audioLibCount);
    }

	/**
	* 获取字段AttachmentLibCount的值，该字段的<br>
	* 字段名称 :AttachmentLibCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getAttachmentLibCount() {
		if(AttachmentLibCount==null){return 0;}
		return AttachmentLibCount.longValue();
	}

	/**
	* 设置字段AttachmentLibCount的值，该字段的<br>
	* 字段名称 :AttachmentLibCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAttachmentLibCount(long attachmentLibCount) {
		this.AttachmentLibCount = new Long(attachmentLibCount);
    }

	/**
	* 设置字段AttachmentLibCount的值，该字段的<br>
	* 字段名称 :AttachmentLibCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAttachmentLibCount(String attachmentLibCount) {
		if (attachmentLibCount == null){
			this.AttachmentLibCount = null;
			return;
		}
		this.AttachmentLibCount = new Long(attachmentLibCount);
    }

	/**
	* 获取字段ArticleCount的值，该字段的<br>
	* 字段名称 :ArticleCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getArticleCount() {
		if(ArticleCount==null){return 0;}
		return ArticleCount.longValue();
	}

	/**
	* 设置字段ArticleCount的值，该字段的<br>
	* 字段名称 :ArticleCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setArticleCount(long articleCount) {
		this.ArticleCount = new Long(articleCount);
    }

	/**
	* 设置字段ArticleCount的值，该字段的<br>
	* 字段名称 :ArticleCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setArticleCount(String articleCount) {
		if (articleCount == null){
			this.ArticleCount = null;
			return;
		}
		this.ArticleCount = new Long(articleCount);
    }

	/**
	* 获取字段HitCount的值，该字段的<br>
	* 字段名称 :HitCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getHitCount() {
		if(HitCount==null){return 0;}
		return HitCount.longValue();
	}

	/**
	* 设置字段HitCount的值，该字段的<br>
	* 字段名称 :HitCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setHitCount(long hitCount) {
		this.HitCount = new Long(hitCount);
    }

	/**
	* 设置字段HitCount的值，该字段的<br>
	* 字段名称 :HitCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setHitCount(String hitCount) {
		if (hitCount == null){
			this.HitCount = null;
			return;
		}
		this.HitCount = new Long(hitCount);
    }

	/**
	* 获取字段ConfigXML的值，该字段的<br>
	* 字段名称 :ConfigXML<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getConfigXML() {
		return ConfigXML;
	}

	/**
	* 设置字段ConfigXML的值，该字段的<br>
	* 字段名称 :ConfigXML<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setConfigXML(String configXML) {
		this.ConfigXML = configXML;
    }

	/**
	* 获取字段AutoIndexFlag的值，该字段的<br>
	* 字段名称 :AutoIndexFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAutoIndexFlag() {
		return AutoIndexFlag;
	}

	/**
	* 设置字段AutoIndexFlag的值，该字段的<br>
	* 字段名称 :AutoIndexFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAutoIndexFlag(String autoIndexFlag) {
		this.AutoIndexFlag = autoIndexFlag;
    }

	/**
	* 获取字段AutoStatFlag的值，该字段的<br>
	* 字段名称 :AutoStatFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAutoStatFlag() {
		return AutoStatFlag;
	}

	/**
	* 设置字段AutoStatFlag的值，该字段的<br>
	* 字段名称 :AutoStatFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAutoStatFlag(String autoStatFlag) {
		this.AutoStatFlag = autoStatFlag;
    }

	/**
	* 获取字段HeaderTemplate的值，该字段的<br>
	* 字段名称 :HeaderTemplate<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getHeaderTemplate() {
		return HeaderTemplate;
	}

	/**
	* 设置字段HeaderTemplate的值，该字段的<br>
	* 字段名称 :HeaderTemplate<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setHeaderTemplate(String headerTemplate) {
		this.HeaderTemplate = headerTemplate;
    }

	/**
	* 获取字段TopTemplate的值，该字段的<br>
	* 字段名称 :TopTemplate<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTopTemplate() {
		return TopTemplate;
	}

	/**
	* 设置字段TopTemplate的值，该字段的<br>
	* 字段名称 :TopTemplate<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTopTemplate(String topTemplate) {
		this.TopTemplate = topTemplate;
    }

	/**
	* 获取字段BottomTemplate的值，该字段的<br>
	* 字段名称 :BottomTemplate<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBottomTemplate() {
		return BottomTemplate;
	}

	/**
	* 设置字段BottomTemplate的值，该字段的<br>
	* 字段名称 :BottomTemplate<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBottomTemplate(String bottomTemplate) {
		this.BottomTemplate = bottomTemplate;
    }

	/**
	* 获取字段AllowContribute的值，该字段的<br>
	* 字段名称 :AllowContribute<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAllowContribute() {
		return AllowContribute;
	}

	/**
	* 设置字段AllowContribute的值，该字段的<br>
	* 字段名称 :AllowContribute<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAllowContribute(String allowContribute) {
		this.AllowContribute = allowContribute;
    }

	/**
	* 获取字段BBSEnableFlag的值，该字段的<br>
	* 字段名称 :BBSEnableFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBBSEnableFlag() {
		return BBSEnableFlag;
	}

	/**
	* 设置字段BBSEnableFlag的值，该字段的<br>
	* 字段名称 :BBSEnableFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBBSEnableFlag(String bBSEnableFlag) {
		this.BBSEnableFlag = bBSEnableFlag;
    }

	/**
	* 获取字段ShopEnableFlag的值，该字段的<br>
	* 字段名称 :ShopEnableFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getShopEnableFlag() {
		return ShopEnableFlag;
	}

	/**
	* 设置字段ShopEnableFlag的值，该字段的<br>
	* 字段名称 :ShopEnableFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setShopEnableFlag(String shopEnableFlag) {
		this.ShopEnableFlag = shopEnableFlag;
    }

	/**
	* 获取字段Meta_Keywords的值，该字段的<br>
	* 字段名称 :Meta_Keywords<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMeta_Keywords() {
		return Meta_Keywords;
	}

	/**
	* 设置字段Meta_Keywords的值，该字段的<br>
	* 字段名称 :Meta_Keywords<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMeta_Keywords(String meta_Keywords) {
		this.Meta_Keywords = meta_Keywords;
    }

	/**
	* 获取字段Meta_Description的值，该字段的<br>
	* 字段名称 :Meta_Description<br>
	* 数据类型 :varchar(400)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMeta_Description() {
		return Meta_Description;
	}

	/**
	* 设置字段Meta_Description的值，该字段的<br>
	* 字段名称 :Meta_Description<br>
	* 数据类型 :varchar(400)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMeta_Description(String meta_Description) {
		this.Meta_Description = meta_Description;
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
	* 获取字段Prop5的值，该字段的<br>
	* 字段名称 :Prop5<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp5() {
		return Prop5;
	}

	/**
	* 设置字段Prop5的值，该字段的<br>
	* 字段名称 :Prop5<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp5(String prop5) {
		this.Prop5 = prop5;
    }

	/**
	* 获取字段Prop6的值，该字段的<br>
	* 字段名称 :Prop6<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp6() {
		return Prop6;
	}

	/**
	* 设置字段Prop6的值，该字段的<br>
	* 字段名称 :Prop6<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp6(String prop6) {
		this.Prop6 = prop6;
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