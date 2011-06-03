package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZSBrand备份<br>
 * 表代码：BZSBrand<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZSBrandSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private String Name;

	private String BranchInnerCode;

	private String Alias;

	private String URL;

	private String ImagePath;

	private String Info;

	private String IndexTemplate;

	private String ListTemplate;

	private String ListNameRule;

	private String DetailTemplate;

	private String DetailNameRule;

	private String RssTemplate;

	private String RssNameRule;

	private Long OrderFlag;

	private Long ListPageSize;

	private Long ListPage;

	private String PublishFlag;

	private String SingleFlag;

	private Long HitCount;

	private String Meta_Keywords;

	private String Meta_Description;

	private String KeywordFlag;

	private String KeywordSetting;

	private String Memo;

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
		new SchemaColumn("Name", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("BranchInnerCode", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("Alias", DataColumn.STRING, 4, 100 , 0 , true , false),
		new SchemaColumn("URL", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("ImagePath", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("Info", DataColumn.STRING, 7, 1024 , 0 , false , false),
		new SchemaColumn("IndexTemplate", DataColumn.STRING, 8, 200 , 0 , false , false),
		new SchemaColumn("ListTemplate", DataColumn.STRING, 9, 200 , 0 , false , false),
		new SchemaColumn("ListNameRule", DataColumn.STRING, 10, 200 , 0 , false , false),
		new SchemaColumn("DetailTemplate", DataColumn.STRING, 11, 200 , 0 , false , false),
		new SchemaColumn("DetailNameRule", DataColumn.STRING, 12, 200 , 0 , false , false),
		new SchemaColumn("RssTemplate", DataColumn.STRING, 13, 200 , 0 , false , false),
		new SchemaColumn("RssNameRule", DataColumn.STRING, 14, 200 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 15, 20 , 0 , true , false),
		new SchemaColumn("ListPageSize", DataColumn.LONG, 16, 20 , 0 , false , false),
		new SchemaColumn("ListPage", DataColumn.LONG, 17, 20 , 0 , false , false),
		new SchemaColumn("PublishFlag", DataColumn.STRING, 18, 2 , 0 , true , false),
		new SchemaColumn("SingleFlag", DataColumn.STRING, 19, 2 , 0 , false , false),
		new SchemaColumn("HitCount", DataColumn.LONG, 20, 20 , 0 , false , false),
		new SchemaColumn("Meta_Keywords", DataColumn.STRING, 21, 200 , 0 , false , false),
		new SchemaColumn("Meta_Description", DataColumn.STRING, 22, 200 , 0 , false , false),
		new SchemaColumn("KeywordFlag", DataColumn.STRING, 23, 2 , 0 , false , false),
		new SchemaColumn("KeywordSetting", DataColumn.STRING, 24, 50 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 25, 200 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 26, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 27, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 28, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 29, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 30, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 31, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 32, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 33, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 34, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 35, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 36, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 37, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZSBrand";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into BZSBrand values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZSBrand set ID=?,SiteID=?,Name=?,BranchInnerCode=?,Alias=?,URL=?,ImagePath=?,Info=?,IndexTemplate=?,ListTemplate=?,ListNameRule=?,DetailTemplate=?,DetailNameRule=?,RssTemplate=?,RssNameRule=?,OrderFlag=?,ListPageSize=?,ListPage=?,PublishFlag=?,SingleFlag=?,HitCount=?,Meta_Keywords=?,Meta_Description=?,KeywordFlag=?,KeywordSetting=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZSBrand  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZSBrand  where ID=? and BackupNo=?";

	public BZSBrandSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[38];
	}

	protected Schema newInstance(){
		return new BZSBrandSchema();
	}

	protected SchemaSet newSet(){
		return new BZSBrandSet();
	}

	public BZSBrandSet query() {
		return query(null, -1, -1);
	}

	public BZSBrandSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZSBrandSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZSBrandSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZSBrandSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){Name = (String)v;return;}
		if (i == 3){BranchInnerCode = (String)v;return;}
		if (i == 4){Alias = (String)v;return;}
		if (i == 5){URL = (String)v;return;}
		if (i == 6){ImagePath = (String)v;return;}
		if (i == 7){Info = (String)v;return;}
		if (i == 8){IndexTemplate = (String)v;return;}
		if (i == 9){ListTemplate = (String)v;return;}
		if (i == 10){ListNameRule = (String)v;return;}
		if (i == 11){DetailTemplate = (String)v;return;}
		if (i == 12){DetailNameRule = (String)v;return;}
		if (i == 13){RssTemplate = (String)v;return;}
		if (i == 14){RssNameRule = (String)v;return;}
		if (i == 15){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 16){if(v==null){ListPageSize = null;}else{ListPageSize = new Long(v.toString());}return;}
		if (i == 17){if(v==null){ListPage = null;}else{ListPage = new Long(v.toString());}return;}
		if (i == 18){PublishFlag = (String)v;return;}
		if (i == 19){SingleFlag = (String)v;return;}
		if (i == 20){if(v==null){HitCount = null;}else{HitCount = new Long(v.toString());}return;}
		if (i == 21){Meta_Keywords = (String)v;return;}
		if (i == 22){Meta_Description = (String)v;return;}
		if (i == 23){KeywordFlag = (String)v;return;}
		if (i == 24){KeywordSetting = (String)v;return;}
		if (i == 25){Memo = (String)v;return;}
		if (i == 26){Prop1 = (String)v;return;}
		if (i == 27){Prop2 = (String)v;return;}
		if (i == 28){Prop3 = (String)v;return;}
		if (i == 29){Prop4 = (String)v;return;}
		if (i == 30){AddUser = (String)v;return;}
		if (i == 31){AddTime = (Date)v;return;}
		if (i == 32){ModifyUser = (String)v;return;}
		if (i == 33){ModifyTime = (Date)v;return;}
		if (i == 34){BackupNo = (String)v;return;}
		if (i == 35){BackupOperator = (String)v;return;}
		if (i == 36){BackupTime = (Date)v;return;}
		if (i == 37){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return Name;}
		if (i == 3){return BranchInnerCode;}
		if (i == 4){return Alias;}
		if (i == 5){return URL;}
		if (i == 6){return ImagePath;}
		if (i == 7){return Info;}
		if (i == 8){return IndexTemplate;}
		if (i == 9){return ListTemplate;}
		if (i == 10){return ListNameRule;}
		if (i == 11){return DetailTemplate;}
		if (i == 12){return DetailNameRule;}
		if (i == 13){return RssTemplate;}
		if (i == 14){return RssNameRule;}
		if (i == 15){return OrderFlag;}
		if (i == 16){return ListPageSize;}
		if (i == 17){return ListPage;}
		if (i == 18){return PublishFlag;}
		if (i == 19){return SingleFlag;}
		if (i == 20){return HitCount;}
		if (i == 21){return Meta_Keywords;}
		if (i == 22){return Meta_Description;}
		if (i == 23){return KeywordFlag;}
		if (i == 24){return KeywordSetting;}
		if (i == 25){return Memo;}
		if (i == 26){return Prop1;}
		if (i == 27){return Prop2;}
		if (i == 28){return Prop3;}
		if (i == 29){return Prop4;}
		if (i == 30){return AddUser;}
		if (i == 31){return AddTime;}
		if (i == 32){return ModifyUser;}
		if (i == 33){return ModifyTime;}
		if (i == 34){return BackupNo;}
		if (i == 35){return BackupOperator;}
		if (i == 36){return BackupTime;}
		if (i == 37){return BackupMemo;}
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
	* 获取字段URL的值，该字段的<br>
	* 字段名称 :URL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getURL() {
		return URL;
	}

	/**
	* 设置字段URL的值，该字段的<br>
	* 字段名称 :URL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setURL(String uRL) {
		this.URL = uRL;
    }

	/**
	* 获取字段ImagePath的值，该字段的<br>
	* 字段名称 :ImagePath<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getImagePath() {
		return ImagePath;
	}

	/**
	* 设置字段ImagePath的值，该字段的<br>
	* 字段名称 :ImagePath<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setImagePath(String imagePath) {
		this.ImagePath = imagePath;
    }

	/**
	* 获取字段Info的值，该字段的<br>
	* 字段名称 :Info<br>
	* 数据类型 :varchar(1024)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInfo() {
		return Info;
	}

	/**
	* 设置字段Info的值，该字段的<br>
	* 字段名称 :Info<br>
	* 数据类型 :varchar(1024)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInfo(String info) {
		this.Info = info;
    }

	/**
	* 获取字段IndexTemplate的值，该字段的<br>
	* 字段名称 :IndexTemplate<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIndexTemplate() {
		return IndexTemplate;
	}

	/**
	* 设置字段IndexTemplate的值，该字段的<br>
	* 字段名称 :IndexTemplate<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIndexTemplate(String indexTemplate) {
		this.IndexTemplate = indexTemplate;
    }

	/**
	* 获取字段ListTemplate的值，该字段的<br>
	* 字段名称 :ListTemplate<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getListTemplate() {
		return ListTemplate;
	}

	/**
	* 设置字段ListTemplate的值，该字段的<br>
	* 字段名称 :ListTemplate<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setListTemplate(String listTemplate) {
		this.ListTemplate = listTemplate;
    }

	/**
	* 获取字段ListNameRule的值，该字段的<br>
	* 字段名称 :ListNameRule<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getListNameRule() {
		return ListNameRule;
	}

	/**
	* 设置字段ListNameRule的值，该字段的<br>
	* 字段名称 :ListNameRule<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setListNameRule(String listNameRule) {
		this.ListNameRule = listNameRule;
    }

	/**
	* 获取字段DetailTemplate的值，该字段的<br>
	* 字段名称 :DetailTemplate<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDetailTemplate() {
		return DetailTemplate;
	}

	/**
	* 设置字段DetailTemplate的值，该字段的<br>
	* 字段名称 :DetailTemplate<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDetailTemplate(String detailTemplate) {
		this.DetailTemplate = detailTemplate;
    }

	/**
	* 获取字段DetailNameRule的值，该字段的<br>
	* 字段名称 :DetailNameRule<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDetailNameRule() {
		return DetailNameRule;
	}

	/**
	* 设置字段DetailNameRule的值，该字段的<br>
	* 字段名称 :DetailNameRule<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDetailNameRule(String detailNameRule) {
		this.DetailNameRule = detailNameRule;
    }

	/**
	* 获取字段RssTemplate的值，该字段的<br>
	* 字段名称 :RssTemplate<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRssTemplate() {
		return RssTemplate;
	}

	/**
	* 设置字段RssTemplate的值，该字段的<br>
	* 字段名称 :RssTemplate<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRssTemplate(String rssTemplate) {
		this.RssTemplate = rssTemplate;
    }

	/**
	* 获取字段RssNameRule的值，该字段的<br>
	* 字段名称 :RssNameRule<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRssNameRule() {
		return RssNameRule;
	}

	/**
	* 设置字段RssNameRule的值，该字段的<br>
	* 字段名称 :RssNameRule<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRssNameRule(String rssNameRule) {
		this.RssNameRule = rssNameRule;
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
	* 获取字段ListPageSize的值，该字段的<br>
	* 字段名称 :ListPageSize<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getListPageSize() {
		if(ListPageSize==null){return 0;}
		return ListPageSize.longValue();
	}

	/**
	* 设置字段ListPageSize的值，该字段的<br>
	* 字段名称 :ListPageSize<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setListPageSize(long listPageSize) {
		this.ListPageSize = new Long(listPageSize);
    }

	/**
	* 设置字段ListPageSize的值，该字段的<br>
	* 字段名称 :ListPageSize<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setListPageSize(String listPageSize) {
		if (listPageSize == null){
			this.ListPageSize = null;
			return;
		}
		this.ListPageSize = new Long(listPageSize);
    }

	/**
	* 获取字段ListPage的值，该字段的<br>
	* 字段名称 :ListPage<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getListPage() {
		if(ListPage==null){return 0;}
		return ListPage.longValue();
	}

	/**
	* 设置字段ListPage的值，该字段的<br>
	* 字段名称 :ListPage<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setListPage(long listPage) {
		this.ListPage = new Long(listPage);
    }

	/**
	* 设置字段ListPage的值，该字段的<br>
	* 字段名称 :ListPage<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setListPage(String listPage) {
		if (listPage == null){
			this.ListPage = null;
			return;
		}
		this.ListPage = new Long(listPage);
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
	* 获取字段SingleFlag的值，该字段的<br>
	* 字段名称 :SingleFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSingleFlag() {
		return SingleFlag;
	}

	/**
	* 设置字段SingleFlag的值，该字段的<br>
	* 字段名称 :SingleFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSingleFlag(String singleFlag) {
		this.SingleFlag = singleFlag;
    }

	/**
	* 获取字段HitCount的值，该字段的<br>
	* 字段名称 :HitCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
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
	* 是否必填 :false<br>
	*/
	public void setHitCount(long hitCount) {
		this.HitCount = new Long(hitCount);
    }

	/**
	* 设置字段HitCount的值，该字段的<br>
	* 字段名称 :HitCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setHitCount(String hitCount) {
		if (hitCount == null){
			this.HitCount = null;
			return;
		}
		this.HitCount = new Long(hitCount);
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
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMeta_Description() {
		return Meta_Description;
	}

	/**
	* 设置字段Meta_Description的值，该字段的<br>
	* 字段名称 :Meta_Description<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMeta_Description(String meta_Description) {
		this.Meta_Description = meta_Description;
    }

	/**
	* 获取字段KeywordFlag的值，该字段的<br>
	* 字段名称 :KeywordFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getKeywordFlag() {
		return KeywordFlag;
	}

	/**
	* 设置字段KeywordFlag的值，该字段的<br>
	* 字段名称 :KeywordFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setKeywordFlag(String keywordFlag) {
		this.KeywordFlag = keywordFlag;
    }

	/**
	* 获取字段KeywordSetting的值，该字段的<br>
	* 字段名称 :KeywordSetting<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getKeywordSetting() {
		return KeywordSetting;
	}

	/**
	* 设置字段KeywordSetting的值，该字段的<br>
	* 字段名称 :KeywordSetting<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setKeywordSetting(String keywordSetting) {
		this.KeywordSetting = keywordSetting;
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