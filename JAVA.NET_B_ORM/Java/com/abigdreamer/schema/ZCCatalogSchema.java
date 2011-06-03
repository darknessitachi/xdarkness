package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCCatalog<br>
 * 表代码：ZCCatalog<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID<br>
 */
public class ZCCatalogSchema extends Schema {
	private Long ID;

	private Long ParentID;

	private Long SiteID;

	private String Name;

	private String InnerCode;

	private String BranchInnerCode;

	private String Alias;

	private String URL;

	private String ImagePath;

	private Long Type;

	private String IndexTemplate;

	private String ListTemplate;

	private String ListNameRule;

	private String DetailTemplate;

	private String DetailNameRule;

	private String RssTemplate;

	private String RssNameRule;

	private String Workflow;

	private Long TreeLevel;

	private Long ChildCount;

	private Long IsLeaf;

	private Long IsDirty;

	private Long Total;

	private Long OrderFlag;

	private String Logo;

	private Long ListPageSize;

	private Long ListPage;

	private String PublishFlag;

	private String SingleFlag;

	private Long HitCount;

	private String Meta_Keywords;

	private String Meta_Description;

	private String OrderColumn;

	private Long Integral;

	private String KeywordFlag;

	private String KeywordSetting;

	private String AllowContribute;

	private String ClusterSourceID;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("ParentID", DataColumn.LONG, 1, 20 , 0 , true , false),
		new SchemaColumn("SiteID", DataColumn.LONG, 2, 20 , 0 , true , false),
		new SchemaColumn("Name", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("InnerCode", DataColumn.STRING, 4, 100 , 0 , true , false),
		new SchemaColumn("BranchInnerCode", DataColumn.STRING, 5, 100 , 0 , false , false),
		new SchemaColumn("Alias", DataColumn.STRING, 6, 100 , 0 , true , false),
		new SchemaColumn("URL", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("ImagePath", DataColumn.STRING, 8, 50 , 0 , false , false),
		new SchemaColumn("Type", DataColumn.LONG, 9, 20 , 0 , true , false),
		new SchemaColumn("IndexTemplate", DataColumn.STRING, 10, 200 , 0 , false , false),
		new SchemaColumn("ListTemplate", DataColumn.STRING, 11, 200 , 0 , false , false),
		new SchemaColumn("ListNameRule", DataColumn.STRING, 12, 200 , 0 , false , false),
		new SchemaColumn("DetailTemplate", DataColumn.STRING, 13, 200 , 0 , false , false),
		new SchemaColumn("DetailNameRule", DataColumn.STRING, 14, 200 , 0 , false , false),
		new SchemaColumn("RssTemplate", DataColumn.STRING, 15, 200 , 0 , false , false),
		new SchemaColumn("RssNameRule", DataColumn.STRING, 16, 200 , 0 , false , false),
		new SchemaColumn("Workflow", DataColumn.STRING, 17, 100 , 0 , false , false),
		new SchemaColumn("TreeLevel", DataColumn.LONG, 18, 20 , 0 , true , false),
		new SchemaColumn("ChildCount", DataColumn.LONG, 19, 20 , 0 , true , false),
		new SchemaColumn("IsLeaf", DataColumn.LONG, 20, 20 , 0 , true , false),
		new SchemaColumn("IsDirty", DataColumn.LONG, 21, 20 , 0 , false , false),
		new SchemaColumn("Total", DataColumn.LONG, 22, 20 , 0 , true , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 23, 20 , 0 , true , false),
		new SchemaColumn("Logo", DataColumn.STRING, 24, 100 , 0 , false , false),
		new SchemaColumn("ListPageSize", DataColumn.LONG, 25, 20 , 0 , false , false),
		new SchemaColumn("ListPage", DataColumn.LONG, 26, 20 , 0 , false , false),
		new SchemaColumn("PublishFlag", DataColumn.STRING, 27, 2 , 0 , true , false),
		new SchemaColumn("SingleFlag", DataColumn.STRING, 28, 2 , 0 , false , false),
		new SchemaColumn("HitCount", DataColumn.LONG, 29, 20 , 0 , false , false),
		new SchemaColumn("Meta_Keywords", DataColumn.STRING, 30, 200 , 0 , false , false),
		new SchemaColumn("Meta_Description", DataColumn.STRING, 31, 200 , 0 , false , false),
		new SchemaColumn("OrderColumn", DataColumn.STRING, 32, 20 , 0 , false , false),
		new SchemaColumn("Integral", DataColumn.LONG, 33, 20 , 0 , false , false),
		new SchemaColumn("KeywordFlag", DataColumn.STRING, 34, 2 , 0 , false , false),
		new SchemaColumn("KeywordSetting", DataColumn.STRING, 35, 50 , 0 , false , false),
		new SchemaColumn("AllowContribute", DataColumn.STRING, 36, 2 , 0 , false , false),
		new SchemaColumn("ClusterSourceID", DataColumn.STRING, 37, 50 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 38, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 39, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 40, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 41, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 42, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 43, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 44, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 45, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCCatalog";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into ZCCatalog values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCCatalog set ID=?,ParentID=?,SiteID=?,Name=?,InnerCode=?,BranchInnerCode=?,Alias=?,URL=?,ImagePath=?,Type=?,IndexTemplate=?,ListTemplate=?,ListNameRule=?,DetailTemplate=?,DetailNameRule=?,RssTemplate=?,RssNameRule=?,Workflow=?,TreeLevel=?,ChildCount=?,IsLeaf=?,IsDirty=?,Total=?,OrderFlag=?,Logo=?,ListPageSize=?,ListPage=?,PublishFlag=?,SingleFlag=?,HitCount=?,Meta_Keywords=?,Meta_Description=?,OrderColumn=?,Integral=?,KeywordFlag=?,KeywordSetting=?,AllowContribute=?,ClusterSourceID=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCCatalog  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCCatalog  where ID=?";

	public ZCCatalogSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[46];
	}

	protected Schema newInstance(){
		return new ZCCatalogSchema();
	}

	protected SchemaSet newSet(){
		return new ZCCatalogSet();
	}

	public ZCCatalogSet query() {
		return query(null, -1, -1);
	}

	public ZCCatalogSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCCatalogSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCCatalogSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCCatalogSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){ParentID = null;}else{ParentID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 3){Name = (String)v;return;}
		if (i == 4){InnerCode = (String)v;return;}
		if (i == 5){BranchInnerCode = (String)v;return;}
		if (i == 6){Alias = (String)v;return;}
		if (i == 7){URL = (String)v;return;}
		if (i == 8){ImagePath = (String)v;return;}
		if (i == 9){if(v==null){Type = null;}else{Type = new Long(v.toString());}return;}
		if (i == 10){IndexTemplate = (String)v;return;}
		if (i == 11){ListTemplate = (String)v;return;}
		if (i == 12){ListNameRule = (String)v;return;}
		if (i == 13){DetailTemplate = (String)v;return;}
		if (i == 14){DetailNameRule = (String)v;return;}
		if (i == 15){RssTemplate = (String)v;return;}
		if (i == 16){RssNameRule = (String)v;return;}
		if (i == 17){Workflow = (String)v;return;}
		if (i == 18){if(v==null){TreeLevel = null;}else{TreeLevel = new Long(v.toString());}return;}
		if (i == 19){if(v==null){ChildCount = null;}else{ChildCount = new Long(v.toString());}return;}
		if (i == 20){if(v==null){IsLeaf = null;}else{IsLeaf = new Long(v.toString());}return;}
		if (i == 21){if(v==null){IsDirty = null;}else{IsDirty = new Long(v.toString());}return;}
		if (i == 22){if(v==null){Total = null;}else{Total = new Long(v.toString());}return;}
		if (i == 23){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 24){Logo = (String)v;return;}
		if (i == 25){if(v==null){ListPageSize = null;}else{ListPageSize = new Long(v.toString());}return;}
		if (i == 26){if(v==null){ListPage = null;}else{ListPage = new Long(v.toString());}return;}
		if (i == 27){PublishFlag = (String)v;return;}
		if (i == 28){SingleFlag = (String)v;return;}
		if (i == 29){if(v==null){HitCount = null;}else{HitCount = new Long(v.toString());}return;}
		if (i == 30){Meta_Keywords = (String)v;return;}
		if (i == 31){Meta_Description = (String)v;return;}
		if (i == 32){OrderColumn = (String)v;return;}
		if (i == 33){if(v==null){Integral = null;}else{Integral = new Long(v.toString());}return;}
		if (i == 34){KeywordFlag = (String)v;return;}
		if (i == 35){KeywordSetting = (String)v;return;}
		if (i == 36){AllowContribute = (String)v;return;}
		if (i == 37){ClusterSourceID = (String)v;return;}
		if (i == 38){Prop1 = (String)v;return;}
		if (i == 39){Prop2 = (String)v;return;}
		if (i == 40){Prop3 = (String)v;return;}
		if (i == 41){Prop4 = (String)v;return;}
		if (i == 42){AddUser = (String)v;return;}
		if (i == 43){AddTime = (Date)v;return;}
		if (i == 44){ModifyUser = (String)v;return;}
		if (i == 45){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return ParentID;}
		if (i == 2){return SiteID;}
		if (i == 3){return Name;}
		if (i == 4){return InnerCode;}
		if (i == 5){return BranchInnerCode;}
		if (i == 6){return Alias;}
		if (i == 7){return URL;}
		if (i == 8){return ImagePath;}
		if (i == 9){return Type;}
		if (i == 10){return IndexTemplate;}
		if (i == 11){return ListTemplate;}
		if (i == 12){return ListNameRule;}
		if (i == 13){return DetailTemplate;}
		if (i == 14){return DetailNameRule;}
		if (i == 15){return RssTemplate;}
		if (i == 16){return RssNameRule;}
		if (i == 17){return Workflow;}
		if (i == 18){return TreeLevel;}
		if (i == 19){return ChildCount;}
		if (i == 20){return IsLeaf;}
		if (i == 21){return IsDirty;}
		if (i == 22){return Total;}
		if (i == 23){return OrderFlag;}
		if (i == 24){return Logo;}
		if (i == 25){return ListPageSize;}
		if (i == 26){return ListPage;}
		if (i == 27){return PublishFlag;}
		if (i == 28){return SingleFlag;}
		if (i == 29){return HitCount;}
		if (i == 30){return Meta_Keywords;}
		if (i == 31){return Meta_Description;}
		if (i == 32){return OrderColumn;}
		if (i == 33){return Integral;}
		if (i == 34){return KeywordFlag;}
		if (i == 35){return KeywordSetting;}
		if (i == 36){return AllowContribute;}
		if (i == 37){return ClusterSourceID;}
		if (i == 38){return Prop1;}
		if (i == 39){return Prop2;}
		if (i == 40){return Prop3;}
		if (i == 41){return Prop4;}
		if (i == 42){return AddUser;}
		if (i == 43){return AddTime;}
		if (i == 44){return ModifyUser;}
		if (i == 45){return ModifyTime;}
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
	* 获取字段ParentID的值，该字段的<br>
	* 字段名称 :ParentID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getParentID() {
		if(ParentID==null){return 0;}
		return ParentID.longValue();
	}

	/**
	* 设置字段ParentID的值，该字段的<br>
	* 字段名称 :ParentID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setParentID(long parentID) {
		this.ParentID = new Long(parentID);
    }

	/**
	* 设置字段ParentID的值，该字段的<br>
	* 字段名称 :ParentID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setParentID(String parentID) {
		if (parentID == null){
			this.ParentID = null;
			return;
		}
		this.ParentID = new Long(parentID);
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
	* 获取字段InnerCode的值，该字段的<br>
	* 字段名称 :InnerCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getInnerCode() {
		return InnerCode;
	}

	/**
	* 设置字段InnerCode的值，该字段的<br>
	* 字段名称 :InnerCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setInnerCode(String innerCode) {
		this.InnerCode = innerCode;
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
	* 获取字段Type的值，该字段的<br>
	* 字段名称 :Type<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getType() {
		if(Type==null){return 0;}
		return Type.longValue();
	}

	/**
	* 设置字段Type的值，该字段的<br>
	* 字段名称 :Type<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setType(long type) {
		this.Type = new Long(type);
    }

	/**
	* 设置字段Type的值，该字段的<br>
	* 字段名称 :Type<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setType(String type) {
		if (type == null){
			this.Type = null;
			return;
		}
		this.Type = new Long(type);
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
	* 获取字段TreeLevel的值，该字段的<br>
	* 字段名称 :TreeLevel<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getTreeLevel() {
		if(TreeLevel==null){return 0;}
		return TreeLevel.longValue();
	}

	/**
	* 设置字段TreeLevel的值，该字段的<br>
	* 字段名称 :TreeLevel<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTreeLevel(long treeLevel) {
		this.TreeLevel = new Long(treeLevel);
    }

	/**
	* 设置字段TreeLevel的值，该字段的<br>
	* 字段名称 :TreeLevel<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTreeLevel(String treeLevel) {
		if (treeLevel == null){
			this.TreeLevel = null;
			return;
		}
		this.TreeLevel = new Long(treeLevel);
    }

	/**
	* 获取字段ChildCount的值，该字段的<br>
	* 字段名称 :ChildCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getChildCount() {
		if(ChildCount==null){return 0;}
		return ChildCount.longValue();
	}

	/**
	* 设置字段ChildCount的值，该字段的<br>
	* 字段名称 :ChildCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setChildCount(long childCount) {
		this.ChildCount = new Long(childCount);
    }

	/**
	* 设置字段ChildCount的值，该字段的<br>
	* 字段名称 :ChildCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setChildCount(String childCount) {
		if (childCount == null){
			this.ChildCount = null;
			return;
		}
		this.ChildCount = new Long(childCount);
    }

	/**
	* 获取字段IsLeaf的值，该字段的<br>
	* 字段名称 :IsLeaf<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getIsLeaf() {
		if(IsLeaf==null){return 0;}
		return IsLeaf.longValue();
	}

	/**
	* 设置字段IsLeaf的值，该字段的<br>
	* 字段名称 :IsLeaf<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setIsLeaf(long isLeaf) {
		this.IsLeaf = new Long(isLeaf);
    }

	/**
	* 设置字段IsLeaf的值，该字段的<br>
	* 字段名称 :IsLeaf<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setIsLeaf(String isLeaf) {
		if (isLeaf == null){
			this.IsLeaf = null;
			return;
		}
		this.IsLeaf = new Long(isLeaf);
    }

	/**
	* 获取字段IsDirty的值，该字段的<br>
	* 字段名称 :IsDirty<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getIsDirty() {
		if(IsDirty==null){return 0;}
		return IsDirty.longValue();
	}

	/**
	* 设置字段IsDirty的值，该字段的<br>
	* 字段名称 :IsDirty<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsDirty(long isDirty) {
		this.IsDirty = new Long(isDirty);
    }

	/**
	* 设置字段IsDirty的值，该字段的<br>
	* 字段名称 :IsDirty<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsDirty(String isDirty) {
		if (isDirty == null){
			this.IsDirty = null;
			return;
		}
		this.IsDirty = new Long(isDirty);
    }

	/**
	* 获取字段Total的值，该字段的<br>
	* 字段名称 :Total<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getTotal() {
		if(Total==null){return 0;}
		return Total.longValue();
	}

	/**
	* 设置字段Total的值，该字段的<br>
	* 字段名称 :Total<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTotal(long total) {
		this.Total = new Long(total);
    }

	/**
	* 设置字段Total的值，该字段的<br>
	* 字段名称 :Total<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTotal(String total) {
		if (total == null){
			this.Total = null;
			return;
		}
		this.Total = new Long(total);
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
	* 获取字段OrderColumn的值，该字段的<br>
	* 字段名称 :OrderColumn<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOrderColumn() {
		return OrderColumn;
	}

	/**
	* 设置字段OrderColumn的值，该字段的<br>
	* 字段名称 :OrderColumn<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderColumn(String orderColumn) {
		this.OrderColumn = orderColumn;
    }

	/**
	* 获取字段Integral的值，该字段的<br>
	* 字段名称 :Integral<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getIntegral() {
		if(Integral==null){return 0;}
		return Integral.longValue();
	}

	/**
	* 设置字段Integral的值，该字段的<br>
	* 字段名称 :Integral<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIntegral(long integral) {
		this.Integral = new Long(integral);
    }

	/**
	* 设置字段Integral的值，该字段的<br>
	* 字段名称 :Integral<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIntegral(String integral) {
		if (integral == null){
			this.Integral = null;
			return;
		}
		this.Integral = new Long(integral);
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
	* 获取字段ClusterSourceID的值，该字段的<br>
	* 字段名称 :ClusterSourceID<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getClusterSourceID() {
		return ClusterSourceID;
	}

	/**
	* 设置字段ClusterSourceID的值，该字段的<br>
	* 字段名称 :ClusterSourceID<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setClusterSourceID(String clusterSourceID) {
		this.ClusterSourceID = clusterSourceID;
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

}