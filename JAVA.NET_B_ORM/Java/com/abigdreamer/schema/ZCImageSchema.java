package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCImage<br>
 * 表代码：ZCImage<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID<br>
 */
public class ZCImageSchema extends Schema {
	private Long ID;

	private String Name;

	private String OldName;

	private String Tag;

	private Long SiteID;

	private Long CatalogID;

	private String CatalogInnerCode;

	private String BranchInnerCode;

	private String Path;

	private String FileName;

	private String SrcFileName;

	private String Suffix;

	private Long Width;

	private Long Height;

	private Long Count;

	private String Info;

	private String IsOriginal;

	private String FileSize;

	private String System;

	private String LinkURL;

	private String LinkText;

	private Date ProduceTime;

	private String Author;

	private Date PublishDate;

	private Long Integral;

	private Long OrderFlag;

	private Long HitCount;

	private Long StickTime;

	private String SourceURL;

	private Long Status;

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
		new SchemaColumn("Name", DataColumn.STRING, 1, 100 , 0 , true , false),
		new SchemaColumn("OldName", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("Tag", DataColumn.STRING, 3, 100 , 0 , false , false),
		new SchemaColumn("SiteID", DataColumn.LONG, 4, 20 , 0 , true , false),
		new SchemaColumn("CatalogID", DataColumn.LONG, 5, 20 , 0 , true , false),
		new SchemaColumn("CatalogInnerCode", DataColumn.STRING, 6, 100 , 0 , true , false),
		new SchemaColumn("BranchInnerCode", DataColumn.STRING, 7, 50 , 0 , false , false),
		new SchemaColumn("Path", DataColumn.STRING, 8, 100 , 0 , true , false),
		new SchemaColumn("FileName", DataColumn.STRING, 9, 100 , 0 , true , false),
		new SchemaColumn("SrcFileName", DataColumn.STRING, 10, 100 , 0 , true , false),
		new SchemaColumn("Suffix", DataColumn.STRING, 11, 10 , 0 , true , false),
		new SchemaColumn("Width", DataColumn.LONG, 12, 20 , 0 , true , false),
		new SchemaColumn("Height", DataColumn.LONG, 13, 20 , 0 , true , false),
		new SchemaColumn("Count", DataColumn.LONG, 14, 20 , 0 , true , false),
		new SchemaColumn("Info", DataColumn.STRING, 15, 500 , 0 , false , false),
		new SchemaColumn("IsOriginal", DataColumn.STRING, 16, 1 , 0 , false , false),
		new SchemaColumn("FileSize", DataColumn.STRING, 17, 20 , 0 , false , false),
		new SchemaColumn("System", DataColumn.STRING, 18, 20 , 0 , false , false),
		new SchemaColumn("LinkURL", DataColumn.STRING, 19, 100 , 0 , false , false),
		new SchemaColumn("LinkText", DataColumn.STRING, 20, 100 , 0 , false , false),
		new SchemaColumn("ProduceTime", DataColumn.DATETIME, 21, 0 , 0 , false , false),
		new SchemaColumn("Author", DataColumn.STRING, 22, 50 , 0 , false , false),
		new SchemaColumn("PublishDate", DataColumn.DATETIME, 23, 0 , 0 , false , false),
		new SchemaColumn("Integral", DataColumn.LONG, 24, 20 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 25, 20 , 0 , true , false),
		new SchemaColumn("HitCount", DataColumn.LONG, 26, 20 , 0 , true , false),
		new SchemaColumn("StickTime", DataColumn.LONG, 27, 20 , 0 , true , false),
		new SchemaColumn("SourceURL", DataColumn.STRING, 28, 500 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.LONG, 29, 20 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 30, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 31, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 32, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 33, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 34, 50 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 35, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 36, 50 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 37, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCImage";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into ZCImage values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCImage set ID=?,Name=?,OldName=?,Tag=?,SiteID=?,CatalogID=?,CatalogInnerCode=?,BranchInnerCode=?,Path=?,FileName=?,SrcFileName=?,Suffix=?,Width=?,Height=?,Count=?,Info=?,IsOriginal=?,FileSize=?,System=?,LinkURL=?,LinkText=?,ProduceTime=?,Author=?,PublishDate=?,Integral=?,OrderFlag=?,HitCount=?,StickTime=?,SourceURL=?,Status=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCImage  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCImage  where ID=?";

	public ZCImageSchema(){
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
		return new ZCImageSchema();
	}

	protected SchemaSet newSet(){
		return new ZCImageSet();
	}

	public ZCImageSet query() {
		return query(null, -1, -1);
	}

	public ZCImageSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCImageSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCImageSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCImageSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){Name = (String)v;return;}
		if (i == 2){OldName = (String)v;return;}
		if (i == 3){Tag = (String)v;return;}
		if (i == 4){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 5){if(v==null){CatalogID = null;}else{CatalogID = new Long(v.toString());}return;}
		if (i == 6){CatalogInnerCode = (String)v;return;}
		if (i == 7){BranchInnerCode = (String)v;return;}
		if (i == 8){Path = (String)v;return;}
		if (i == 9){FileName = (String)v;return;}
		if (i == 10){SrcFileName = (String)v;return;}
		if (i == 11){Suffix = (String)v;return;}
		if (i == 12){if(v==null){Width = null;}else{Width = new Long(v.toString());}return;}
		if (i == 13){if(v==null){Height = null;}else{Height = new Long(v.toString());}return;}
		if (i == 14){if(v==null){Count = null;}else{Count = new Long(v.toString());}return;}
		if (i == 15){Info = (String)v;return;}
		if (i == 16){IsOriginal = (String)v;return;}
		if (i == 17){FileSize = (String)v;return;}
		if (i == 18){System = (String)v;return;}
		if (i == 19){LinkURL = (String)v;return;}
		if (i == 20){LinkText = (String)v;return;}
		if (i == 21){ProduceTime = (Date)v;return;}
		if (i == 22){Author = (String)v;return;}
		if (i == 23){PublishDate = (Date)v;return;}
		if (i == 24){if(v==null){Integral = null;}else{Integral = new Long(v.toString());}return;}
		if (i == 25){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 26){if(v==null){HitCount = null;}else{HitCount = new Long(v.toString());}return;}
		if (i == 27){if(v==null){StickTime = null;}else{StickTime = new Long(v.toString());}return;}
		if (i == 28){SourceURL = (String)v;return;}
		if (i == 29){if(v==null){Status = null;}else{Status = new Long(v.toString());}return;}
		if (i == 30){Prop1 = (String)v;return;}
		if (i == 31){Prop2 = (String)v;return;}
		if (i == 32){Prop3 = (String)v;return;}
		if (i == 33){Prop4 = (String)v;return;}
		if (i == 34){AddUser = (String)v;return;}
		if (i == 35){AddTime = (Date)v;return;}
		if (i == 36){ModifyUser = (String)v;return;}
		if (i == 37){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return Name;}
		if (i == 2){return OldName;}
		if (i == 3){return Tag;}
		if (i == 4){return SiteID;}
		if (i == 5){return CatalogID;}
		if (i == 6){return CatalogInnerCode;}
		if (i == 7){return BranchInnerCode;}
		if (i == 8){return Path;}
		if (i == 9){return FileName;}
		if (i == 10){return SrcFileName;}
		if (i == 11){return Suffix;}
		if (i == 12){return Width;}
		if (i == 13){return Height;}
		if (i == 14){return Count;}
		if (i == 15){return Info;}
		if (i == 16){return IsOriginal;}
		if (i == 17){return FileSize;}
		if (i == 18){return System;}
		if (i == 19){return LinkURL;}
		if (i == 20){return LinkText;}
		if (i == 21){return ProduceTime;}
		if (i == 22){return Author;}
		if (i == 23){return PublishDate;}
		if (i == 24){return Integral;}
		if (i == 25){return OrderFlag;}
		if (i == 26){return HitCount;}
		if (i == 27){return StickTime;}
		if (i == 28){return SourceURL;}
		if (i == 29){return Status;}
		if (i == 30){return Prop1;}
		if (i == 31){return Prop2;}
		if (i == 32){return Prop3;}
		if (i == 33){return Prop4;}
		if (i == 34){return AddUser;}
		if (i == 35){return AddTime;}
		if (i == 36){return ModifyUser;}
		if (i == 37){return ModifyTime;}
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
	* 获取字段OldName的值，该字段的<br>
	* 字段名称 :OldName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getOldName() {
		return OldName;
	}

	/**
	* 设置字段OldName的值，该字段的<br>
	* 字段名称 :OldName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setOldName(String oldName) {
		this.OldName = oldName;
    }

	/**
	* 获取字段Tag的值，该字段的<br>
	* 字段名称 :Tag<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTag() {
		return Tag;
	}

	/**
	* 设置字段Tag的值，该字段的<br>
	* 字段名称 :Tag<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTag(String tag) {
		this.Tag = tag;
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
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getBranchInnerCode() {
		return BranchInnerCode;
	}

	/**
	* 设置字段BranchInnerCode的值，该字段的<br>
	* 字段名称 :BranchInnerCode<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBranchInnerCode(String branchInnerCode) {
		this.BranchInnerCode = branchInnerCode;
    }

	/**
	* 获取字段Path的值，该字段的<br>
	* 字段名称 :Path<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getPath() {
		return Path;
	}

	/**
	* 设置字段Path的值，该字段的<br>
	* 字段名称 :Path<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setPath(String path) {
		this.Path = path;
    }

	/**
	* 获取字段FileName的值，该字段的<br>
	* 字段名称 :FileName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getFileName() {
		return FileName;
	}

	/**
	* 设置字段FileName的值，该字段的<br>
	* 字段名称 :FileName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setFileName(String fileName) {
		this.FileName = fileName;
    }

	/**
	* 获取字段SrcFileName的值，该字段的<br>
	* 字段名称 :SrcFileName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getSrcFileName() {
		return SrcFileName;
	}

	/**
	* 设置字段SrcFileName的值，该字段的<br>
	* 字段名称 :SrcFileName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSrcFileName(String srcFileName) {
		this.SrcFileName = srcFileName;
    }

	/**
	* 获取字段Suffix的值，该字段的<br>
	* 字段名称 :Suffix<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getSuffix() {
		return Suffix;
	}

	/**
	* 设置字段Suffix的值，该字段的<br>
	* 字段名称 :Suffix<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSuffix(String suffix) {
		this.Suffix = suffix;
    }

	/**
	* 获取字段Width的值，该字段的<br>
	* 字段名称 :Width<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getWidth() {
		if(Width==null){return 0;}
		return Width.longValue();
	}

	/**
	* 设置字段Width的值，该字段的<br>
	* 字段名称 :Width<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setWidth(long width) {
		this.Width = new Long(width);
    }

	/**
	* 设置字段Width的值，该字段的<br>
	* 字段名称 :Width<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setWidth(String width) {
		if (width == null){
			this.Width = null;
			return;
		}
		this.Width = new Long(width);
    }

	/**
	* 获取字段Height的值，该字段的<br>
	* 字段名称 :Height<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getHeight() {
		if(Height==null){return 0;}
		return Height.longValue();
	}

	/**
	* 设置字段Height的值，该字段的<br>
	* 字段名称 :Height<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setHeight(long height) {
		this.Height = new Long(height);
    }

	/**
	* 设置字段Height的值，该字段的<br>
	* 字段名称 :Height<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setHeight(String height) {
		if (height == null){
			this.Height = null;
			return;
		}
		this.Height = new Long(height);
    }

	/**
	* 获取字段Count的值，该字段的<br>
	* 字段名称 :Count<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getCount() {
		if(Count==null){return 0;}
		return Count.longValue();
	}

	/**
	* 设置字段Count的值，该字段的<br>
	* 字段名称 :Count<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCount(long count) {
		this.Count = new Long(count);
    }

	/**
	* 设置字段Count的值，该字段的<br>
	* 字段名称 :Count<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCount(String count) {
		if (count == null){
			this.Count = null;
			return;
		}
		this.Count = new Long(count);
    }

	/**
	* 获取字段Info的值，该字段的<br>
	* 字段名称 :Info<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getInfo() {
		return Info;
	}

	/**
	* 设置字段Info的值，该字段的<br>
	* 字段名称 :Info<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInfo(String info) {
		this.Info = info;
    }

	/**
	* 获取字段IsOriginal的值，该字段的<br>
	* 字段名称 :IsOriginal<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIsOriginal() {
		return IsOriginal;
	}

	/**
	* 设置字段IsOriginal的值，该字段的<br>
	* 字段名称 :IsOriginal<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsOriginal(String isOriginal) {
		this.IsOriginal = isOriginal;
    }

	/**
	* 获取字段FileSize的值，该字段的<br>
	* 字段名称 :FileSize<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getFileSize() {
		return FileSize;
	}

	/**
	* 设置字段FileSize的值，该字段的<br>
	* 字段名称 :FileSize<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setFileSize(String fileSize) {
		this.FileSize = fileSize;
    }

	/**
	* 获取字段System的值，该字段的<br>
	* 字段名称 :System<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSystem() {
		return System;
	}

	/**
	* 设置字段System的值，该字段的<br>
	* 字段名称 :System<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSystem(String system) {
		this.System = system;
    }

	/**
	* 获取字段LinkURL的值，该字段的<br>
	* 字段名称 :LinkURL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLinkURL() {
		return LinkURL;
	}

	/**
	* 设置字段LinkURL的值，该字段的<br>
	* 字段名称 :LinkURL<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLinkURL(String linkURL) {
		this.LinkURL = linkURL;
    }

	/**
	* 获取字段LinkText的值，该字段的<br>
	* 字段名称 :LinkText<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getLinkText() {
		return LinkText;
	}

	/**
	* 设置字段LinkText的值，该字段的<br>
	* 字段名称 :LinkText<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setLinkText(String linkText) {
		this.LinkText = linkText;
    }

	/**
	* 获取字段ProduceTime的值，该字段的<br>
	* 字段名称 :ProduceTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getProduceTime() {
		return ProduceTime;
	}

	/**
	* 设置字段ProduceTime的值，该字段的<br>
	* 字段名称 :ProduceTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProduceTime(Date produceTime) {
		this.ProduceTime = produceTime;
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
	* 获取字段SourceURL的值，该字段的<br>
	* 字段名称 :SourceURL<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSourceURL() {
		return SourceURL;
	}

	/**
	* 设置字段SourceURL的值，该字段的<br>
	* 字段名称 :SourceURL<br>
	* 数据类型 :varchar(500)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSourceURL(String sourceURL) {
		this.SourceURL = sourceURL;
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

}