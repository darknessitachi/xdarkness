package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCArticle备份<br>
 * 表代码：BZCArticle<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZCArticleSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private Long CatalogID;

	private String CatalogInnerCode;

	private String BranchInnerCode;

	private String Title;

	private String SubTitle;

	private String ShortTitle;

	private String TitleStyle;

	private String ShortTitleStyle;

	private String Author;

	private String Type;

	private String Attribute;

	private String URL;

	private String RedirectURL;

	private Long Status;

	private String Summary;

	private String Content;

	private String TopFlag;

	private Date TopDate;

	private String TemplateFlag;

	private String Template;

	private String CommentFlag;

	private String CopyImageFlag;

	private Long OrderFlag;

	private String ReferName;

	private String ReferURL;

	private String Keyword;

	private String Tag;

	private String RelativeArticle;

	private String RecommendArticle;

	private Long ReferType;

	private Long ReferSourceID;

	private Long HitCount;

	private Long StickTime;

	private String PublishFlag;

	private String Priority;

	private String LockUser;

	private Date PublishDate;

	private Date DownlineDate;

	private Date ArchiveDate;

	private Long WorkFlowID;

	private Long IssueID;

	private String Logo;

	private String PageTitle;

	private String ClusterSource;

	private String ClusterTarget;

	private String ReferTarget;

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
		new SchemaColumn("CatalogID", DataColumn.LONG, 2, 20 , 0 , true , false),
		new SchemaColumn("CatalogInnerCode", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("BranchInnerCode", DataColumn.STRING, 4, 100 , 0 , false , false),
		new SchemaColumn("Title", DataColumn.STRING, 5, 200 , 0 , true , false),
		new SchemaColumn("SubTitle", DataColumn.STRING, 6, 200 , 0 , false , false),
		new SchemaColumn("ShortTitle", DataColumn.STRING, 7, 200 , 0 , false , false),
		new SchemaColumn("TitleStyle", DataColumn.STRING, 8, 100 , 0 , false , false),
		new SchemaColumn("ShortTitleStyle", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("Author", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("Type", DataColumn.STRING, 11, 2 , 0 , true , false),
		new SchemaColumn("Attribute", DataColumn.STRING, 12, 100 , 0 , false , false),
		new SchemaColumn("URL", DataColumn.STRING, 13, 200 , 0 , false , false),
		new SchemaColumn("RedirectURL", DataColumn.STRING, 14, 200 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.LONG, 15, 20 , 0 , false , false),
		new SchemaColumn("Summary", DataColumn.STRING, 16, 2000 , 0 , false , false),
		new SchemaColumn("Content", DataColumn.CLOB, 17, 0 , 0 , false , false),
		new SchemaColumn("TopFlag", DataColumn.STRING, 18, 2 , 0 , true , false),
		new SchemaColumn("TopDate", DataColumn.DATETIME, 19, 0 , 0 , false , false),
		new SchemaColumn("TemplateFlag", DataColumn.STRING, 20, 2 , 0 , true , false),
		new SchemaColumn("Template", DataColumn.STRING, 21, 100 , 0 , false , false),
		new SchemaColumn("CommentFlag", DataColumn.STRING, 22, 2 , 0 , true , false),
		new SchemaColumn("CopyImageFlag", DataColumn.STRING, 23, 2 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 24, 20 , 0 , true , false),
		new SchemaColumn("ReferName", DataColumn.STRING, 25, 100 , 0 , false , false),
		new SchemaColumn("ReferURL", DataColumn.STRING, 26, 200 , 0 , false , false),
		new SchemaColumn("Keyword", DataColumn.STRING, 27, 100 , 0 , false , false),
		new SchemaColumn("Tag", DataColumn.STRING, 28, 1000 , 0 , false , false),
		new SchemaColumn("RelativeArticle", DataColumn.STRING, 29, 200 , 0 , false , false),
		new SchemaColumn("RecommendArticle", DataColumn.STRING, 30, 200 , 0 , false , false),
		new SchemaColumn("ReferType", DataColumn.LONG, 31, 20 , 0 , false , false),
		new SchemaColumn("ReferSourceID", DataColumn.LONG, 32, 20 , 0 , false , false),
		new SchemaColumn("HitCount", DataColumn.LONG, 33, 20 , 0 , true , false),
		new SchemaColumn("StickTime", DataColumn.LONG, 34, 20 , 0 , true , false),
		new SchemaColumn("PublishFlag", DataColumn.STRING, 35, 2 , 0 , true , false),
		new SchemaColumn("Priority", DataColumn.STRING, 36, 2 , 0 , false , false),
		new SchemaColumn("LockUser", DataColumn.STRING, 37, 50 , 0 , false , false),
		new SchemaColumn("PublishDate", DataColumn.DATETIME, 38, 0 , 0 , false , false),
		new SchemaColumn("DownlineDate", DataColumn.DATETIME, 39, 0 , 0 , false , false),
		new SchemaColumn("ArchiveDate", DataColumn.DATETIME, 40, 0 , 0 , false , false),
		new SchemaColumn("WorkFlowID", DataColumn.LONG, 41, 20 , 0 , false , false),
		new SchemaColumn("IssueID", DataColumn.LONG, 42, 20 , 0 , false , false),
		new SchemaColumn("Logo", DataColumn.STRING, 43, 100 , 0 , false , false),
		new SchemaColumn("PageTitle", DataColumn.STRING, 44, 200 , 0 , false , false),
		new SchemaColumn("ClusterSource", DataColumn.STRING, 45, 200 , 0 , false , false),
		new SchemaColumn("ClusterTarget", DataColumn.STRING, 46, 1000 , 0 , false , false),
		new SchemaColumn("ReferTarget", DataColumn.STRING, 47, 1000 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 48, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 49, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 50, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 51, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 52, 50 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 53, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 54, 50 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 55, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 56, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 57, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 58, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 59, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCArticle";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into BZCArticle values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCArticle set ID=?,SiteID=?,CatalogID=?,CatalogInnerCode=?,BranchInnerCode=?,Title=?,SubTitle=?,ShortTitle=?,TitleStyle=?,ShortTitleStyle=?,Author=?,Type=?,Attribute=?,URL=?,RedirectURL=?,Status=?,Summary=?,Content=?,TopFlag=?,TopDate=?,TemplateFlag=?,Template=?,CommentFlag=?,CopyImageFlag=?,OrderFlag=?,ReferName=?,ReferURL=?,Keyword=?,Tag=?,RelativeArticle=?,RecommendArticle=?,ReferType=?,ReferSourceID=?,HitCount=?,StickTime=?,PublishFlag=?,Priority=?,LockUser=?,PublishDate=?,DownlineDate=?,ArchiveDate=?,WorkFlowID=?,IssueID=?,Logo=?,PageTitle=?,ClusterSource=?,ClusterTarget=?,ReferTarget=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCArticle  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCArticle  where ID=? and BackupNo=?";

	public BZCArticleSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[60];
	}

	protected Schema newInstance(){
		return new BZCArticleSchema();
	}

	protected SchemaSet newSet(){
		return new BZCArticleSet();
	}

	public BZCArticleSet query() {
		return query(null, -1, -1);
	}

	public BZCArticleSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCArticleSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCArticleSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCArticleSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){CatalogID = null;}else{CatalogID = new Long(v.toString());}return;}
		if (i == 3){CatalogInnerCode = (String)v;return;}
		if (i == 4){BranchInnerCode = (String)v;return;}
		if (i == 5){Title = (String)v;return;}
		if (i == 6){SubTitle = (String)v;return;}
		if (i == 7){ShortTitle = (String)v;return;}
		if (i == 8){TitleStyle = (String)v;return;}
		if (i == 9){ShortTitleStyle = (String)v;return;}
		if (i == 10){Author = (String)v;return;}
		if (i == 11){Type = (String)v;return;}
		if (i == 12){Attribute = (String)v;return;}
		if (i == 13){URL = (String)v;return;}
		if (i == 14){RedirectURL = (String)v;return;}
		if (i == 15){if(v==null){Status = null;}else{Status = new Long(v.toString());}return;}
		if (i == 16){Summary = (String)v;return;}
		if (i == 17){Content = (String)v;return;}
		if (i == 18){TopFlag = (String)v;return;}
		if (i == 19){TopDate = (Date)v;return;}
		if (i == 20){TemplateFlag = (String)v;return;}
		if (i == 21){Template = (String)v;return;}
		if (i == 22){CommentFlag = (String)v;return;}
		if (i == 23){CopyImageFlag = (String)v;return;}
		if (i == 24){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 25){ReferName = (String)v;return;}
		if (i == 26){ReferURL = (String)v;return;}
		if (i == 27){Keyword = (String)v;return;}
		if (i == 28){Tag = (String)v;return;}
		if (i == 29){RelativeArticle = (String)v;return;}
		if (i == 30){RecommendArticle = (String)v;return;}
		if (i == 31){if(v==null){ReferType = null;}else{ReferType = new Long(v.toString());}return;}
		if (i == 32){if(v==null){ReferSourceID = null;}else{ReferSourceID = new Long(v.toString());}return;}
		if (i == 33){if(v==null){HitCount = null;}else{HitCount = new Long(v.toString());}return;}
		if (i == 34){if(v==null){StickTime = null;}else{StickTime = new Long(v.toString());}return;}
		if (i == 35){PublishFlag = (String)v;return;}
		if (i == 36){Priority = (String)v;return;}
		if (i == 37){LockUser = (String)v;return;}
		if (i == 38){PublishDate = (Date)v;return;}
		if (i == 39){DownlineDate = (Date)v;return;}
		if (i == 40){ArchiveDate = (Date)v;return;}
		if (i == 41){if(v==null){WorkFlowID = null;}else{WorkFlowID = new Long(v.toString());}return;}
		if (i == 42){if(v==null){IssueID = null;}else{IssueID = new Long(v.toString());}return;}
		if (i == 43){Logo = (String)v;return;}
		if (i == 44){PageTitle = (String)v;return;}
		if (i == 45){ClusterSource = (String)v;return;}
		if (i == 46){ClusterTarget = (String)v;return;}
		if (i == 47){ReferTarget = (String)v;return;}
		if (i == 48){Prop1 = (String)v;return;}
		if (i == 49){Prop2 = (String)v;return;}
		if (i == 50){Prop3 = (String)v;return;}
		if (i == 51){Prop4 = (String)v;return;}
		if (i == 52){AddUser = (String)v;return;}
		if (i == 53){AddTime = (Date)v;return;}
		if (i == 54){ModifyUser = (String)v;return;}
		if (i == 55){ModifyTime = (Date)v;return;}
		if (i == 56){BackupNo = (String)v;return;}
		if (i == 57){BackupOperator = (String)v;return;}
		if (i == 58){BackupTime = (Date)v;return;}
		if (i == 59){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return CatalogID;}
		if (i == 3){return CatalogInnerCode;}
		if (i == 4){return BranchInnerCode;}
		if (i == 5){return Title;}
		if (i == 6){return SubTitle;}
		if (i == 7){return ShortTitle;}
		if (i == 8){return TitleStyle;}
		if (i == 9){return ShortTitleStyle;}
		if (i == 10){return Author;}
		if (i == 11){return Type;}
		if (i == 12){return Attribute;}
		if (i == 13){return URL;}
		if (i == 14){return RedirectURL;}
		if (i == 15){return Status;}
		if (i == 16){return Summary;}
		if (i == 17){return Content;}
		if (i == 18){return TopFlag;}
		if (i == 19){return TopDate;}
		if (i == 20){return TemplateFlag;}
		if (i == 21){return Template;}
		if (i == 22){return CommentFlag;}
		if (i == 23){return CopyImageFlag;}
		if (i == 24){return OrderFlag;}
		if (i == 25){return ReferName;}
		if (i == 26){return ReferURL;}
		if (i == 27){return Keyword;}
		if (i == 28){return Tag;}
		if (i == 29){return RelativeArticle;}
		if (i == 30){return RecommendArticle;}
		if (i == 31){return ReferType;}
		if (i == 32){return ReferSourceID;}
		if (i == 33){return HitCount;}
		if (i == 34){return StickTime;}
		if (i == 35){return PublishFlag;}
		if (i == 36){return Priority;}
		if (i == 37){return LockUser;}
		if (i == 38){return PublishDate;}
		if (i == 39){return DownlineDate;}
		if (i == 40){return ArchiveDate;}
		if (i == 41){return WorkFlowID;}
		if (i == 42){return IssueID;}
		if (i == 43){return Logo;}
		if (i == 44){return PageTitle;}
		if (i == 45){return ClusterSource;}
		if (i == 46){return ClusterTarget;}
		if (i == 47){return ReferTarget;}
		if (i == 48){return Prop1;}
		if (i == 49){return Prop2;}
		if (i == 50){return Prop3;}
		if (i == 51){return Prop4;}
		if (i == 52){return AddUser;}
		if (i == 53){return AddTime;}
		if (i == 54){return ModifyUser;}
		if (i == 55){return ModifyTime;}
		if (i == 56){return BackupNo;}
		if (i == 57){return BackupOperator;}
		if (i == 58){return BackupTime;}
		if (i == 59){return BackupMemo;}
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
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCatalogID(long catalogID) {
		this.CatalogID = new Long(catalogID);
    }

	/**
	* 设置字段CatalogID的值，该字段的<br>
	* 字段名称 :CatalogID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
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
	* 获取字段CatalogInnerCode的值，该字段的<br>
	* 字段名称 :CatalogInnerCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCatalogInnerCode() {
		return CatalogInnerCode;
	}

	/**
	* 设置字段CatalogInnerCode的值，该字段的<br>
	* 字段名称 :CatalogInnerCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCatalogInnerCode(String catalogInnerCode) {
		this.CatalogInnerCode = catalogInnerCode;
    }

	/**
	* 获取字段BranchInnerCode的值，该字段的<br>
	* 字段名称 :BranchInnerCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBranchInnerCode() {
		return BranchInnerCode;
	}

	/**
	* 设置字段BranchInnerCode的值，该字段的<br>
	* 字段名称 :BranchInnerCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBranchInnerCode(String branchInnerCode) {
		this.BranchInnerCode = branchInnerCode;
    }

	/**
	* 获取字段Title的值，该字段的<br>
	* 字段名称 :Title<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getTitle() {
		return Title;
	}

	/**
	* 设置字段Title的值，该字段的<br>
	* 字段名称 :Title<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTitle(String title) {
		this.Title = title;
    }

	/**
	* 获取字段SubTitle的值，该字段的<br>
	* 字段名称 :SubTitle<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSubTitle() {
		return SubTitle;
	}

	/**
	* 设置字段SubTitle的值，该字段的<br>
	* 字段名称 :SubTitle<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSubTitle(String subTitle) {
		this.SubTitle = subTitle;
    }

	/**
	* 获取字段ShortTitle的值，该字段的<br>
	* 字段名称 :ShortTitle<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getShortTitle() {
		return ShortTitle;
	}

	/**
	* 设置字段ShortTitle的值，该字段的<br>
	* 字段名称 :ShortTitle<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setShortTitle(String shortTitle) {
		this.ShortTitle = shortTitle;
    }

	/**
	* 获取字段TitleStyle的值，该字段的<br>
	* 字段名称 :TitleStyle<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTitleStyle() {
		return TitleStyle;
	}

	/**
	* 设置字段TitleStyle的值，该字段的<br>
	* 字段名称 :TitleStyle<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTitleStyle(String titleStyle) {
		this.TitleStyle = titleStyle;
    }

	/**
	* 获取字段ShortTitleStyle的值，该字段的<br>
	* 字段名称 :ShortTitleStyle<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getShortTitleStyle() {
		return ShortTitleStyle;
	}

	/**
	* 设置字段ShortTitleStyle的值，该字段的<br>
	* 字段名称 :ShortTitleStyle<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setShortTitleStyle(String shortTitleStyle) {
		this.ShortTitleStyle = shortTitleStyle;
    }

	/**
	* 获取字段Author的值，该字段的<br>
	* 字段名称 :Author<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAuthor() {
		return Author;
	}

	/**
	* 设置字段Author的值，该字段的<br>
	* 字段名称 :Author<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAuthor(String author) {
		this.Author = author;
    }

	/**
	* 获取字段Type的值，该字段的<br>
	* 字段名称 :Type<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* 设置字段Type的值，该字段的<br>
	* 字段名称 :Type<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* 获取字段Attribute的值，该字段的<br>
	* 字段名称 :Attribute<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAttribute() {
		return Attribute;
	}

	/**
	* 设置字段Attribute的值，该字段的<br>
	* 字段名称 :Attribute<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAttribute(String attribute) {
		this.Attribute = attribute;
    }

	/**
	* 获取字段URL的值，该字段的<br>
	* 字段名称 :URL<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getURL() {
		return URL;
	}

	/**
	* 设置字段URL的值，该字段的<br>
	* 字段名称 :URL<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setURL(String uRL) {
		this.URL = uRL;
    }

	/**
	* 获取字段RedirectURL的值，该字段的<br>
	* 字段名称 :RedirectURL<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRedirectURL() {
		return RedirectURL;
	}

	/**
	* 设置字段RedirectURL的值，该字段的<br>
	* 字段名称 :RedirectURL<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRedirectURL(String redirectURL) {
		this.RedirectURL = redirectURL;
    }

	/**
	* 获取字段Status的值，该字段的<br>
	* 字段名称 :Status<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getStatus() {
		if(Status==null){return 0;}
		return Status.longValue();
	}

	/**
	* 设置字段Status的值，该字段的<br>
	* 字段名称 :Status<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStatus(long status) {
		this.Status = new Long(status);
    }

	/**
	* 设置字段Status的值，该字段的<br>
	* 字段名称 :Status<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStatus(String status) {
		if (status == null){
			this.Status = null;
			return;
		}
		this.Status = new Long(status);
    }

	/**
	* 获取字段Summary的值，该字段的<br>
	* 字段名称 :Summary<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSummary() {
		return Summary;
	}

	/**
	* 设置字段Summary的值，该字段的<br>
	* 字段名称 :Summary<br>
	* 数据类型 :varchar(2000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSummary(String summary) {
		this.Summary = summary;
    }

	/**
	* 获取字段Content的值，该字段的<br>
	* 字段名称 :Content<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getContent() {
		return Content;
	}

	/**
	* 设置字段Content的值，该字段的<br>
	* 字段名称 :Content<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setContent(String content) {
		this.Content = content;
    }

	/**
	* 获取字段TopFlag的值，该字段的<br>
	* 字段名称 :TopFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getTopFlag() {
		return TopFlag;
	}

	/**
	* 设置字段TopFlag的值，该字段的<br>
	* 字段名称 :TopFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTopFlag(String topFlag) {
		this.TopFlag = topFlag;
    }

	/**
	* 获取字段TopDate的值，该字段的<br>
	* 字段名称 :TopDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getTopDate() {
		return TopDate;
	}

	/**
	* 设置字段TopDate的值，该字段的<br>
	* 字段名称 :TopDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTopDate(Date topDate) {
		this.TopDate = topDate;
    }

	/**
	* 获取字段TemplateFlag的值，该字段的<br>
	* 字段名称 :TemplateFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getTemplateFlag() {
		return TemplateFlag;
	}

	/**
	* 设置字段TemplateFlag的值，该字段的<br>
	* 字段名称 :TemplateFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTemplateFlag(String templateFlag) {
		this.TemplateFlag = templateFlag;
    }

	/**
	* 获取字段Template的值，该字段的<br>
	* 字段名称 :Template<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTemplate() {
		return Template;
	}

	/**
	* 设置字段Template的值，该字段的<br>
	* 字段名称 :Template<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTemplate(String template) {
		this.Template = template;
    }

	/**
	* 获取字段CommentFlag的值，该字段的<br>
	* 字段名称 :CommentFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCommentFlag() {
		return CommentFlag;
	}

	/**
	* 设置字段CommentFlag的值，该字段的<br>
	* 字段名称 :CommentFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCommentFlag(String commentFlag) {
		this.CommentFlag = commentFlag;
    }

	/**
	* 获取字段CopyImageFlag的值，该字段的<br>
	* 字段名称 :CopyImageFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getCopyImageFlag() {
		return CopyImageFlag;
	}

	/**
	* 设置字段CopyImageFlag的值，该字段的<br>
	* 字段名称 :CopyImageFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCopyImageFlag(String copyImageFlag) {
		this.CopyImageFlag = copyImageFlag;
    }

	/**
	* 获取字段OrderFlag的值，该字段的<br>
	* 字段名称 :OrderFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
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
	* 是否必填 :true<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :OrderFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setOrderFlag(String orderFlag) {
		if (orderFlag == null){
			this.OrderFlag = null;
			return;
		}
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 获取字段ReferName的值，该字段的<br>
	* 字段名称 :ReferName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getReferName() {
		return ReferName;
	}

	/**
	* 设置字段ReferName的值，该字段的<br>
	* 字段名称 :ReferName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReferName(String referName) {
		this.ReferName = referName;
    }

	/**
	* 获取字段ReferURL的值，该字段的<br>
	* 字段名称 :ReferURL<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getReferURL() {
		return ReferURL;
	}

	/**
	* 设置字段ReferURL的值，该字段的<br>
	* 字段名称 :ReferURL<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReferURL(String referURL) {
		this.ReferURL = referURL;
    }

	/**
	* 获取字段Keyword的值，该字段的<br>
	* 字段名称 :Keyword<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getKeyword() {
		return Keyword;
	}

	/**
	* 设置字段Keyword的值，该字段的<br>
	* 字段名称 :Keyword<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setKeyword(String keyword) {
		this.Keyword = keyword;
    }

	/**
	* 获取字段Tag的值，该字段的<br>
	* 字段名称 :Tag<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTag() {
		return Tag;
	}

	/**
	* 设置字段Tag的值，该字段的<br>
	* 字段名称 :Tag<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTag(String tag) {
		this.Tag = tag;
    }

	/**
	* 获取字段RelativeArticle的值，该字段的<br>
	* 字段名称 :RelativeArticle<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRelativeArticle() {
		return RelativeArticle;
	}

	/**
	* 设置字段RelativeArticle的值，该字段的<br>
	* 字段名称 :RelativeArticle<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRelativeArticle(String relativeArticle) {
		this.RelativeArticle = relativeArticle;
    }

	/**
	* 获取字段RecommendArticle的值，该字段的<br>
	* 字段名称 :RecommendArticle<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRecommendArticle() {
		return RecommendArticle;
	}

	/**
	* 设置字段RecommendArticle的值，该字段的<br>
	* 字段名称 :RecommendArticle<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRecommendArticle(String recommendArticle) {
		this.RecommendArticle = recommendArticle;
    }

	/**
	* 获取字段ReferType的值，该字段的<br>
	* 字段名称 :ReferType<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getReferType() {
		if(ReferType==null){return 0;}
		return ReferType.longValue();
	}

	/**
	* 设置字段ReferType的值，该字段的<br>
	* 字段名称 :ReferType<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReferType(long referType) {
		this.ReferType = new Long(referType);
    }

	/**
	* 设置字段ReferType的值，该字段的<br>
	* 字段名称 :ReferType<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReferType(String referType) {
		if (referType == null){
			this.ReferType = null;
			return;
		}
		this.ReferType = new Long(referType);
    }

	/**
	* 获取字段ReferSourceID的值，该字段的<br>
	* 字段名称 :ReferSourceID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getReferSourceID() {
		if(ReferSourceID==null){return 0;}
		return ReferSourceID.longValue();
	}

	/**
	* 设置字段ReferSourceID的值，该字段的<br>
	* 字段名称 :ReferSourceID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReferSourceID(long referSourceID) {
		this.ReferSourceID = new Long(referSourceID);
    }

	/**
	* 设置字段ReferSourceID的值，该字段的<br>
	* 字段名称 :ReferSourceID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReferSourceID(String referSourceID) {
		if (referSourceID == null){
			this.ReferSourceID = null;
			return;
		}
		this.ReferSourceID = new Long(referSourceID);
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
	* 获取字段StickTime的值，该字段的<br>
	* 字段名称 :StickTime<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getStickTime() {
		if(StickTime==null){return 0;}
		return StickTime.longValue();
	}

	/**
	* 设置字段StickTime的值，该字段的<br>
	* 字段名称 :StickTime<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setStickTime(long stickTime) {
		this.StickTime = new Long(stickTime);
    }

	/**
	* 设置字段StickTime的值，该字段的<br>
	* 字段名称 :StickTime<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setStickTime(String stickTime) {
		if (stickTime == null){
			this.StickTime = null;
			return;
		}
		this.StickTime = new Long(stickTime);
    }

	/**
	* 获取字段PublishFlag的值，该字段的<br>
	* 字段名称 :PublishFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getPublishFlag() {
		return PublishFlag;
	}

	/**
	* 设置字段PublishFlag的值，该字段的<br>
	* 字段名称 :PublishFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setPublishFlag(String publishFlag) {
		this.PublishFlag = publishFlag;
    }

	/**
	* 获取字段Priority的值，该字段的<br>
	* 字段名称 :Priority<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPriority() {
		return Priority;
	}

	/**
	* 设置字段Priority的值，该字段的<br>
	* 字段名称 :Priority<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPriority(String priority) {
		this.Priority = priority;
    }

	/**
	* 获取字段LockUser的值，该字段的<br>
	* 字段名称 :LockUser<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLockUser() {
		return LockUser;
	}

	/**
	* 设置字段LockUser的值，该字段的<br>
	* 字段名称 :LockUser<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLockUser(String lockUser) {
		this.LockUser = lockUser;
    }

	/**
	* 获取字段PublishDate的值，该字段的<br>
	* 字段名称 :PublishDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getPublishDate() {
		return PublishDate;
	}

	/**
	* 设置字段PublishDate的值，该字段的<br>
	* 字段名称 :PublishDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPublishDate(Date publishDate) {
		this.PublishDate = publishDate;
    }

	/**
	* 获取字段DownlineDate的值，该字段的<br>
	* 字段名称 :DownlineDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getDownlineDate() {
		return DownlineDate;
	}

	/**
	* 设置字段DownlineDate的值，该字段的<br>
	* 字段名称 :DownlineDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDownlineDate(Date downlineDate) {
		this.DownlineDate = downlineDate;
    }

	/**
	* 获取字段ArchiveDate的值，该字段的<br>
	* 字段名称 :ArchiveDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getArchiveDate() {
		return ArchiveDate;
	}

	/**
	* 设置字段ArchiveDate的值，该字段的<br>
	* 字段名称 :ArchiveDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setArchiveDate(Date archiveDate) {
		this.ArchiveDate = archiveDate;
    }

	/**
	* 获取字段WorkFlowID的值，该字段的<br>
	* 字段名称 :WorkFlowID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getWorkFlowID() {
		if(WorkFlowID==null){return 0;}
		return WorkFlowID.longValue();
	}

	/**
	* 设置字段WorkFlowID的值，该字段的<br>
	* 字段名称 :WorkFlowID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setWorkFlowID(long workFlowID) {
		this.WorkFlowID = new Long(workFlowID);
    }

	/**
	* 设置字段WorkFlowID的值，该字段的<br>
	* 字段名称 :WorkFlowID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setWorkFlowID(String workFlowID) {
		if (workFlowID == null){
			this.WorkFlowID = null;
			return;
		}
		this.WorkFlowID = new Long(workFlowID);
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
	* 获取字段Logo的值，该字段的<br>
	* 字段名称 :Logo<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLogo() {
		return Logo;
	}

	/**
	* 设置字段Logo的值，该字段的<br>
	* 字段名称 :Logo<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLogo(String logo) {
		this.Logo = logo;
    }

	/**
	* 获取字段PageTitle的值，该字段的<br>
	* 字段名称 :PageTitle<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPageTitle() {
		return PageTitle;
	}

	/**
	* 设置字段PageTitle的值，该字段的<br>
	* 字段名称 :PageTitle<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPageTitle(String pageTitle) {
		this.PageTitle = pageTitle;
    }

	/**
	* 获取字段ClusterSource的值，该字段的<br>
	* 字段名称 :ClusterSource<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getClusterSource() {
		return ClusterSource;
	}

	/**
	* 设置字段ClusterSource的值，该字段的<br>
	* 字段名称 :ClusterSource<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setClusterSource(String clusterSource) {
		this.ClusterSource = clusterSource;
    }

	/**
	* 获取字段ClusterTarget的值，该字段的<br>
	* 字段名称 :ClusterTarget<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getClusterTarget() {
		return ClusterTarget;
	}

	/**
	* 设置字段ClusterTarget的值，该字段的<br>
	* 字段名称 :ClusterTarget<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setClusterTarget(String clusterTarget) {
		this.ClusterTarget = clusterTarget;
    }

	/**
	* 获取字段ReferTarget的值，该字段的<br>
	* 字段名称 :ReferTarget<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getReferTarget() {
		return ReferTarget;
	}

	/**
	* 设置字段ReferTarget的值，该字段的<br>
	* 字段名称 :ReferTarget<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReferTarget(String referTarget) {
		this.ReferTarget = referTarget;
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
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(50)<br>
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
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :ModifyUser<br>
	* 数据类型 :varchar(50)<br>
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