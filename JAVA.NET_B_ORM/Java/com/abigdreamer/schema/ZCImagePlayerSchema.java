package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCImagePlayer<br>
 * 表代码：ZCImagePlayer<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID<br>
 */
public class ZCImagePlayerSchema extends Schema {
	private Long ID;

	private String Name;

	private String Code;

	private Long SiteID;

	private String DisplayType;

	private String ImageSource;

	private String RelaCatalogInnerCode;

	private Long Height;

	private Long Width;

	private Long DisplayCount;

	private String IsShowText;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("Name", DataColumn.STRING, 1, 100 , 0 , true , false),
		new SchemaColumn("Code", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("SiteID", DataColumn.LONG, 3, 20 , 0 , true , false),
		new SchemaColumn("DisplayType", DataColumn.STRING, 4, 2 , 0 , true , false),
		new SchemaColumn("ImageSource", DataColumn.STRING, 5, 2 , 0 , true , false),
		new SchemaColumn("RelaCatalogInnerCode", DataColumn.STRING, 6, 100 , 0 , false , false),
		new SchemaColumn("Height", DataColumn.LONG, 7, 20 , 0 , true , false),
		new SchemaColumn("Width", DataColumn.LONG, 8, 20 , 0 , true , false),
		new SchemaColumn("DisplayCount", DataColumn.LONG, 9, 20 , 0 , false , false),
		new SchemaColumn("IsShowText", DataColumn.STRING, 10, 2 , 0 , true , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 11, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 12, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 13, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 14, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCImagePlayer";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into ZCImagePlayer values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCImagePlayer set ID=?,Name=?,Code=?,SiteID=?,DisplayType=?,ImageSource=?,RelaCatalogInnerCode=?,Height=?,Width=?,DisplayCount=?,IsShowText=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCImagePlayer  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCImagePlayer  where ID=?";

	public ZCImagePlayerSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[15];
	}

	protected Schema newInstance(){
		return new ZCImagePlayerSchema();
	}

	protected SchemaSet newSet(){
		return new ZCImagePlayerSet();
	}

	public ZCImagePlayerSet query() {
		return query(null, -1, -1);
	}

	public ZCImagePlayerSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCImagePlayerSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCImagePlayerSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCImagePlayerSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){Name = (String)v;return;}
		if (i == 2){Code = (String)v;return;}
		if (i == 3){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 4){DisplayType = (String)v;return;}
		if (i == 5){ImageSource = (String)v;return;}
		if (i == 6){RelaCatalogInnerCode = (String)v;return;}
		if (i == 7){if(v==null){Height = null;}else{Height = new Long(v.toString());}return;}
		if (i == 8){if(v==null){Width = null;}else{Width = new Long(v.toString());}return;}
		if (i == 9){if(v==null){DisplayCount = null;}else{DisplayCount = new Long(v.toString());}return;}
		if (i == 10){IsShowText = (String)v;return;}
		if (i == 11){AddUser = (String)v;return;}
		if (i == 12){AddTime = (Date)v;return;}
		if (i == 13){ModifyUser = (String)v;return;}
		if (i == 14){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return Name;}
		if (i == 2){return Code;}
		if (i == 3){return SiteID;}
		if (i == 4){return DisplayType;}
		if (i == 5){return ImageSource;}
		if (i == 6){return RelaCatalogInnerCode;}
		if (i == 7){return Height;}
		if (i == 8){return Width;}
		if (i == 9){return DisplayCount;}
		if (i == 10){return IsShowText;}
		if (i == 11){return AddUser;}
		if (i == 12){return AddTime;}
		if (i == 13){return ModifyUser;}
		if (i == 14){return ModifyTime;}
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
	* 获取字段Code的值，该字段的<br>
	* 字段名称 :Code<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCode() {
		return Code;
	}

	/**
	* 设置字段Code的值，该字段的<br>
	* 字段名称 :Code<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCode(String code) {
		this.Code = code;
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
	* 获取字段DisplayType的值，该字段的<br>
	* 字段名称 :DisplayType<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getDisplayType() {
		return DisplayType;
	}

	/**
	* 设置字段DisplayType的值，该字段的<br>
	* 字段名称 :DisplayType<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setDisplayType(String displayType) {
		this.DisplayType = displayType;
    }

	/**
	* 获取字段ImageSource的值，该字段的<br>
	* 字段名称 :ImageSource<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getImageSource() {
		return ImageSource;
	}

	/**
	* 设置字段ImageSource的值，该字段的<br>
	* 字段名称 :ImageSource<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setImageSource(String imageSource) {
		this.ImageSource = imageSource;
    }

	/**
	* 获取字段RelaCatalogInnerCode的值，该字段的<br>
	* 字段名称 :RelaCatalogInnerCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getRelaCatalogInnerCode() {
		return RelaCatalogInnerCode;
	}

	/**
	* 设置字段RelaCatalogInnerCode的值，该字段的<br>
	* 字段名称 :RelaCatalogInnerCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRelaCatalogInnerCode(String relaCatalogInnerCode) {
		this.RelaCatalogInnerCode = relaCatalogInnerCode;
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
	* 获取字段DisplayCount的值，该字段的<br>
	* 字段名称 :DisplayCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getDisplayCount() {
		if(DisplayCount==null){return 0;}
		return DisplayCount.longValue();
	}

	/**
	* 设置字段DisplayCount的值，该字段的<br>
	* 字段名称 :DisplayCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDisplayCount(long displayCount) {
		this.DisplayCount = new Long(displayCount);
    }

	/**
	* 设置字段DisplayCount的值，该字段的<br>
	* 字段名称 :DisplayCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDisplayCount(String displayCount) {
		if (displayCount == null){
			this.DisplayCount = null;
			return;
		}
		this.DisplayCount = new Long(displayCount);
    }

	/**
	* 获取字段IsShowText的值，该字段的<br>
	* 字段名称 :IsShowText<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getIsShowText() {
		return IsShowText;
	}

	/**
	* 设置字段IsShowText的值，该字段的<br>
	* 字段名称 :IsShowText<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setIsShowText(String isShowText) {
		this.IsShowText = isShowText;
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