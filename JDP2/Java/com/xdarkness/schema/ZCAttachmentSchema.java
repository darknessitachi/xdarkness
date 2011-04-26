package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCAttachment<br>
 * 表代码：ZCAttachment<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID<br>
 */
public class ZCAttachmentSchema extends Schema {
	private Long ID;

	private String Name;

	private String OldName;

	private Long SiteID;

	private Long CatalogID;

	private String CatalogInnerCode;

	private String BranchInnerCode;

	private String Path;

	private String FileName;

	private String SrcFileName;

	private String Suffix;

	private String Info;

	private String FileSize;

	private String System;

	private Date PublishDate;

	private Long Integral;

	private String IsLocked;

	private String Password;

	private String SourceURL;

	private Long Status;

	private Long OrderFlag;

	private String ImagePath;

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
		new SchemaColumn("SiteID", DataColumn.LONG, 3, 20 , 0 , true , false),
		new SchemaColumn("CatalogID", DataColumn.LONG, 4, 20 , 0 , true , false),
		new SchemaColumn("CatalogInnerCode", DataColumn.STRING, 5, 100 , 0 , true , false),
		new SchemaColumn("BranchInnerCode", DataColumn.STRING, 6, 50 , 0 , false , false),
		new SchemaColumn("Path", DataColumn.STRING, 7, 100 , 0 , true , false),
		new SchemaColumn("FileName", DataColumn.STRING, 8, 100 , 0 , true , false),
		new SchemaColumn("SrcFileName", DataColumn.STRING, 9, 100 , 0 , true , false),
		new SchemaColumn("Suffix", DataColumn.STRING, 10, 10 , 0 , true , false),
		new SchemaColumn("Info", DataColumn.STRING, 11, 500 , 0 , false , false),
		new SchemaColumn("FileSize", DataColumn.STRING, 12, 20 , 0 , false , false),
		new SchemaColumn("System", DataColumn.STRING, 13, 20 , 0 , false , false),
		new SchemaColumn("PublishDate", DataColumn.DATETIME, 14, 0 , 0 , false , false),
		new SchemaColumn("Integral", DataColumn.LONG, 15, 20 , 0 , false , false),
		new SchemaColumn("IsLocked", DataColumn.STRING, 16, 5 , 0 , true , false),
		new SchemaColumn("Password", DataColumn.STRING, 17, 50 , 0 , false , false),
		new SchemaColumn("SourceURL", DataColumn.STRING, 18, 200 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.LONG, 19, 20 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 20, 20 , 0 , true , false),
		new SchemaColumn("ImagePath", DataColumn.STRING, 21, 100 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 22, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 23, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 24, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 25, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 26, 50 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 27, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 28, 50 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 29, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCAttachment";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into ZCAttachment values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCAttachment set ID=?,Name=?,OldName=?,SiteID=?,CatalogID=?,CatalogInnerCode=?,BranchInnerCode=?,Path=?,FileName=?,SrcFileName=?,Suffix=?,Info=?,FileSize=?,System=?,PublishDate=?,Integral=?,IsLocked=?,Password=?,SourceURL=?,Status=?,OrderFlag=?,ImagePath=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCAttachment  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCAttachment  where ID=?";

	public ZCAttachmentSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[30];
	}

	protected Schema newInstance(){
		return new ZCAttachmentSchema();
	}

	protected SchemaSet newSet(){
		return new ZCAttachmentSet();
	}

	public ZCAttachmentSet query() {
		return query(null, -1, -1);
	}

	public ZCAttachmentSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCAttachmentSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCAttachmentSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCAttachmentSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){Name = (String)v;return;}
		if (i == 2){OldName = (String)v;return;}
		if (i == 3){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 4){if(v==null){CatalogID = null;}else{CatalogID = new Long(v.toString());}return;}
		if (i == 5){CatalogInnerCode = (String)v;return;}
		if (i == 6){BranchInnerCode = (String)v;return;}
		if (i == 7){Path = (String)v;return;}
		if (i == 8){FileName = (String)v;return;}
		if (i == 9){SrcFileName = (String)v;return;}
		if (i == 10){Suffix = (String)v;return;}
		if (i == 11){Info = (String)v;return;}
		if (i == 12){FileSize = (String)v;return;}
		if (i == 13){System = (String)v;return;}
		if (i == 14){PublishDate = (Date)v;return;}
		if (i == 15){if(v==null){Integral = null;}else{Integral = new Long(v.toString());}return;}
		if (i == 16){IsLocked = (String)v;return;}
		if (i == 17){Password = (String)v;return;}
		if (i == 18){SourceURL = (String)v;return;}
		if (i == 19){if(v==null){Status = null;}else{Status = new Long(v.toString());}return;}
		if (i == 20){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 21){ImagePath = (String)v;return;}
		if (i == 22){Prop1 = (String)v;return;}
		if (i == 23){Prop2 = (String)v;return;}
		if (i == 24){Prop3 = (String)v;return;}
		if (i == 25){Prop4 = (String)v;return;}
		if (i == 26){AddUser = (String)v;return;}
		if (i == 27){AddTime = (Date)v;return;}
		if (i == 28){ModifyUser = (String)v;return;}
		if (i == 29){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return Name;}
		if (i == 2){return OldName;}
		if (i == 3){return SiteID;}
		if (i == 4){return CatalogID;}
		if (i == 5){return CatalogInnerCode;}
		if (i == 6){return BranchInnerCode;}
		if (i == 7){return Path;}
		if (i == 8){return FileName;}
		if (i == 9){return SrcFileName;}
		if (i == 10){return Suffix;}
		if (i == 11){return Info;}
		if (i == 12){return FileSize;}
		if (i == 13){return System;}
		if (i == 14){return PublishDate;}
		if (i == 15){return Integral;}
		if (i == 16){return IsLocked;}
		if (i == 17){return Password;}
		if (i == 18){return SourceURL;}
		if (i == 19){return Status;}
		if (i == 20){return OrderFlag;}
		if (i == 21){return ImagePath;}
		if (i == 22){return Prop1;}
		if (i == 23){return Prop2;}
		if (i == 24){return Prop3;}
		if (i == 25){return Prop4;}
		if (i == 26){return AddUser;}
		if (i == 27){return AddTime;}
		if (i == 28){return ModifyUser;}
		if (i == 29){return ModifyTime;}
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
	* 获取字段IsLocked的值，该字段的<br>
	* 字段名称 :IsLocked<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getIsLocked() {
		return IsLocked;
	}

	/**
	* 设置字段IsLocked的值，该字段的<br>
	* 字段名称 :IsLocked<br>
	* 数据类型 :varchar(5)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setIsLocked(String isLocked) {
		this.IsLocked = isLocked;
    }

	/**
	* 获取字段Password的值，该字段的<br>
	* 字段名称 :Password<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPassword() {
		return Password;
	}

	/**
	* 设置字段Password的值，该字段的<br>
	* 字段名称 :Password<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPassword(String password) {
		this.Password = password;
    }

	/**
	* 获取字段SourceURL的值，该字段的<br>
	* 字段名称 :SourceURL<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSourceURL() {
		return SourceURL;
	}

	/**
	* 设置字段SourceURL的值，该字段的<br>
	* 字段名称 :SourceURL<br>
	* 数据类型 :varchar(200)<br>
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
	* 获取字段ImagePath的值，该字段的<br>
	* 字段名称 :ImagePath<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getImagePath() {
		return ImagePath;
	}

	/**
	* 设置字段ImagePath的值，该字段的<br>
	* 字段名称 :ImagePath<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setImagePath(String imagePath) {
		this.ImagePath = imagePath;
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