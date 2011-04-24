package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ColsRemark<br>
 * 表代码：ColsRemark<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：id<br>
 */
public class ColsRemarkSchema extends Schema {
	private String id;

	private Date addtime;

	private Integer moder;

	private Date modtime;

	private String tablename;

	private String colname;

	private String alias;

	private String categoryid;

	private String minvalue;

	private String maxvalue;

	private Integer multilines;

	private Integer rigor;

	private Integer Fmttypetime;

	private Integer openout;

	private Integer colchanged;

	private Integer Required;

	private String ValidExp;

	private Integer MultiSelected;

	private String devinfo;

	private Integer inputtype;

	private Integer colorder;

	private Integer tracelog;

	private String usedRefid;

	private Integer usedRefidRoot;

	private Integer dynamicbind;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("id", DataColumn.STRING, 0, 125 , 0 , true , true),
		new SchemaColumn("addtime", DataColumn.DATETIME, 1, 0 , 0 , true , false),
		new SchemaColumn("moder", DataColumn.INTEGER, 2, 11 , 0 , true , false),
		new SchemaColumn("modtime", DataColumn.DATETIME, 3, 0 , 0 , false , false),
		new SchemaColumn("tablename", DataColumn.STRING, 4, 50 , 0 , true , false),
		new SchemaColumn("colname", DataColumn.STRING, 5, 50 , 0 , true , false),
		new SchemaColumn("alias", DataColumn.STRING, 6, 100 , 0 , true , false),
		new SchemaColumn("categoryid", DataColumn.STRING, 7, 40 , 0 , true , false),
		new SchemaColumn("minvalue", DataColumn.STRING, 8, 20 , 0 , true , false),
		new SchemaColumn("maxvalue", DataColumn.STRING, 9, 20 , 0 , true , false),
		new SchemaColumn("multilines", DataColumn.INTEGER, 10, 4 , 0 , true , false),
		new SchemaColumn("rigor", DataColumn.INTEGER, 11, 4 , 0 , true , false),
		new SchemaColumn("Fmttypetime", DataColumn.INTEGER, 12, 4 , 0 , true , false),
		new SchemaColumn("openout", DataColumn.INTEGER, 13, 4 , 0 , true , false),
		new SchemaColumn("colchanged", DataColumn.INTEGER, 14, 4 , 0 , true , false),
		new SchemaColumn("Required", DataColumn.INTEGER, 15, 4 , 0 , true , false),
		new SchemaColumn("ValidExp", DataColumn.STRING, 16, 255 , 0 , true , false),
		new SchemaColumn("MultiSelected", DataColumn.INTEGER, 17, 4 , 0 , true , false),
		new SchemaColumn("devinfo", DataColumn.STRING, 18, 1000 , 0 , true , false),
		new SchemaColumn("inputtype", DataColumn.INTEGER, 19, 4 , 0 , true , false),
		new SchemaColumn("colorder", DataColumn.INTEGER, 20, 11 , 0 , true , false),
		new SchemaColumn("tracelog", DataColumn.INTEGER, 21, 4 , 0 , true , false),
		new SchemaColumn("usedRefid", DataColumn.STRING, 22, 20 , 0 , true , false),
		new SchemaColumn("usedRefidRoot", DataColumn.INTEGER, 23, 4 , 0 , true , false),
		new SchemaColumn("dynamicbind", DataColumn.INTEGER, 24, 4 , 0 , true , false)
	};

	public static final String _TableCode = "ColsRemark";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into ColsRemark values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ColsRemark set id=?,addtime=?,moder=?,modtime=?,tablename=?,colname=?,alias=?,categoryid=?,minvalue=?,maxvalue=?,multilines=?,rigor=?,Fmttypetime=?,openout=?,colchanged=?,Required=?,ValidExp=?,MultiSelected=?,devinfo=?,inputtype=?,colorder=?,tracelog=?,usedRefid=?,usedRefidRoot=?,dynamicbind=? where id=?";

	protected static final String _DeleteSQL = "delete from ColsRemark  where id=?";

	protected static final String _FillAllSQL = "select * from ColsRemark  where id=?";

	public ColsRemarkSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[25];
	}

	protected Schema newInstance(){
		return new ColsRemarkSchema();
	}

	protected SchemaSet newSet(){
		return new ColsRemarkSet();
	}

	public ColsRemarkSet query() {
		return query(null, -1, -1);
	}

	public ColsRemarkSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ColsRemarkSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ColsRemarkSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ColsRemarkSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){id = (String)v;return;}
		if (i == 1){addtime = (Date)v;return;}
		if (i == 2){if(v==null){moder = null;}else{moder = new Integer(v.toString());}return;}
		if (i == 3){modtime = (Date)v;return;}
		if (i == 4){tablename = (String)v;return;}
		if (i == 5){colname = (String)v;return;}
		if (i == 6){alias = (String)v;return;}
		if (i == 7){categoryid = (String)v;return;}
		if (i == 8){minvalue = (String)v;return;}
		if (i == 9){maxvalue = (String)v;return;}
		if (i == 10){if(v==null){multilines = null;}else{multilines = new Integer(v.toString());}return;}
		if (i == 11){if(v==null){rigor = null;}else{rigor = new Integer(v.toString());}return;}
		if (i == 12){if(v==null){Fmttypetime = null;}else{Fmttypetime = new Integer(v.toString());}return;}
		if (i == 13){if(v==null){openout = null;}else{openout = new Integer(v.toString());}return;}
		if (i == 14){if(v==null){colchanged = null;}else{colchanged = new Integer(v.toString());}return;}
		if (i == 15){if(v==null){Required = null;}else{Required = new Integer(v.toString());}return;}
		if (i == 16){ValidExp = (String)v;return;}
		if (i == 17){if(v==null){MultiSelected = null;}else{MultiSelected = new Integer(v.toString());}return;}
		if (i == 18){devinfo = (String)v;return;}
		if (i == 19){if(v==null){inputtype = null;}else{inputtype = new Integer(v.toString());}return;}
		if (i == 20){if(v==null){colorder = null;}else{colorder = new Integer(v.toString());}return;}
		if (i == 21){if(v==null){tracelog = null;}else{tracelog = new Integer(v.toString());}return;}
		if (i == 22){usedRefid = (String)v;return;}
		if (i == 23){if(v==null){usedRefidRoot = null;}else{usedRefidRoot = new Integer(v.toString());}return;}
		if (i == 24){if(v==null){dynamicbind = null;}else{dynamicbind = new Integer(v.toString());}return;}
	}

	public Object getV(int i) {
		if (i == 0){return id;}
		if (i == 1){return addtime;}
		if (i == 2){return moder;}
		if (i == 3){return modtime;}
		if (i == 4){return tablename;}
		if (i == 5){return colname;}
		if (i == 6){return alias;}
		if (i == 7){return categoryid;}
		if (i == 8){return minvalue;}
		if (i == 9){return maxvalue;}
		if (i == 10){return multilines;}
		if (i == 11){return rigor;}
		if (i == 12){return Fmttypetime;}
		if (i == 13){return openout;}
		if (i == 14){return colchanged;}
		if (i == 15){return Required;}
		if (i == 16){return ValidExp;}
		if (i == 17){return MultiSelected;}
		if (i == 18){return devinfo;}
		if (i == 19){return inputtype;}
		if (i == 20){return colorder;}
		if (i == 21){return tracelog;}
		if (i == 22){return usedRefid;}
		if (i == 23){return usedRefidRoot;}
		if (i == 24){return dynamicbind;}
		return null;
	}

	/**
	* 获取字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(125)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return id;
	}

	/**
	* 设置字段id的值，该字段的<br>
	* 字段名称 :id<br>
	* 数据类型 :varchar(125)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.id = id;
    }

	/**
	* 获取字段addtime的值，该字段的<br>
	* 字段名称 :addtime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getAddtime() {
		return addtime;
	}

	/**
	* 设置字段addtime的值，该字段的<br>
	* 字段名称 :addtime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
    }

	/**
	* 获取字段moder的值，该字段的<br>
	* 字段名称 :moder<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getModer() {
		if(moder==null){return 0;}
		return moder.intValue();
	}

	/**
	* 设置字段moder的值，该字段的<br>
	* 字段名称 :moder<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setModer(int moder) {
		this.moder = new Integer(moder);
    }

	/**
	* 设置字段moder的值，该字段的<br>
	* 字段名称 :moder<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setModer(String moder) {
		if (moder == null){
			this.moder = null;
			return;
		}
		this.moder = new Integer(moder);
    }

	/**
	* 获取字段modtime的值，该字段的<br>
	* 字段名称 :modtime<br>
	* 数据类型 :timestamp<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getModtime() {
		return modtime;
	}

	/**
	* 设置字段modtime的值，该字段的<br>
	* 字段名称 :modtime<br>
	* 数据类型 :timestamp<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModtime(Date modtime) {
		this.modtime = modtime;
    }

	/**
	* 获取字段tablename的值，该字段的<br>
	* 字段名称 :tablename<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getTablename() {
		return tablename;
	}

	/**
	* 设置字段tablename的值，该字段的<br>
	* 字段名称 :tablename<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTablename(String tablename) {
		this.tablename = tablename;
    }

	/**
	* 获取字段colname的值，该字段的<br>
	* 字段名称 :colname<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getColname() {
		return colname;
	}

	/**
	* 设置字段colname的值，该字段的<br>
	* 字段名称 :colname<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setColname(String colname) {
		this.colname = colname;
    }

	/**
	* 获取字段alias的值，该字段的<br>
	* 字段名称 :alias<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAlias() {
		return alias;
	}

	/**
	* 设置字段alias的值，该字段的<br>
	* 字段名称 :alias<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAlias(String alias) {
		this.alias = alias;
    }

	/**
	* 获取字段categoryid的值，该字段的<br>
	* 字段名称 :categoryid<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCategoryid() {
		return categoryid;
	}

	/**
	* 设置字段categoryid的值，该字段的<br>
	* 字段名称 :categoryid<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
    }

	/**
	* 获取字段minvalue的值，该字段的<br>
	* 字段名称 :minvalue<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getMinvalue() {
		return minvalue;
	}

	/**
	* 设置字段minvalue的值，该字段的<br>
	* 字段名称 :minvalue<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMinvalue(String minvalue) {
		this.minvalue = minvalue;
    }

	/**
	* 获取字段maxvalue的值，该字段的<br>
	* 字段名称 :maxvalue<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getMaxvalue() {
		return maxvalue;
	}

	/**
	* 设置字段maxvalue的值，该字段的<br>
	* 字段名称 :maxvalue<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMaxvalue(String maxvalue) {
		this.maxvalue = maxvalue;
    }

	/**
	* 获取字段multilines的值，该字段的<br>
	* 字段名称 :multilines<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getMultilines() {
		if(multilines==null){return 0;}
		return multilines.intValue();
	}

	/**
	* 设置字段multilines的值，该字段的<br>
	* 字段名称 :multilines<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMultilines(int multilines) {
		this.multilines = new Integer(multilines);
    }

	/**
	* 设置字段multilines的值，该字段的<br>
	* 字段名称 :multilines<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMultilines(String multilines) {
		if (multilines == null){
			this.multilines = null;
			return;
		}
		this.multilines = new Integer(multilines);
    }

	/**
	* 获取字段rigor的值，该字段的<br>
	* 字段名称 :rigor<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getRigor() {
		if(rigor==null){return 0;}
		return rigor.intValue();
	}

	/**
	* 设置字段rigor的值，该字段的<br>
	* 字段名称 :rigor<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setRigor(int rigor) {
		this.rigor = new Integer(rigor);
    }

	/**
	* 设置字段rigor的值，该字段的<br>
	* 字段名称 :rigor<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setRigor(String rigor) {
		if (rigor == null){
			this.rigor = null;
			return;
		}
		this.rigor = new Integer(rigor);
    }

	/**
	* 获取字段Fmttypetime的值，该字段的<br>
	* 字段名称 :Fmttypetime<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getFmttypetime() {
		if(Fmttypetime==null){return 0;}
		return Fmttypetime.intValue();
	}

	/**
	* 设置字段Fmttypetime的值，该字段的<br>
	* 字段名称 :Fmttypetime<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setFmttypetime(int fmttypetime) {
		this.Fmttypetime = new Integer(fmttypetime);
    }

	/**
	* 设置字段Fmttypetime的值，该字段的<br>
	* 字段名称 :Fmttypetime<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setFmttypetime(String fmttypetime) {
		if (fmttypetime == null){
			this.Fmttypetime = null;
			return;
		}
		this.Fmttypetime = new Integer(fmttypetime);
    }

	/**
	* 获取字段openout的值，该字段的<br>
	* 字段名称 :openout<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getOpenout() {
		if(openout==null){return 0;}
		return openout.intValue();
	}

	/**
	* 设置字段openout的值，该字段的<br>
	* 字段名称 :openout<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setOpenout(int openout) {
		this.openout = new Integer(openout);
    }

	/**
	* 设置字段openout的值，该字段的<br>
	* 字段名称 :openout<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setOpenout(String openout) {
		if (openout == null){
			this.openout = null;
			return;
		}
		this.openout = new Integer(openout);
    }

	/**
	* 获取字段colchanged的值，该字段的<br>
	* 字段名称 :colchanged<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getColchanged() {
		if(colchanged==null){return 0;}
		return colchanged.intValue();
	}

	/**
	* 设置字段colchanged的值，该字段的<br>
	* 字段名称 :colchanged<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setColchanged(int colchanged) {
		this.colchanged = new Integer(colchanged);
    }

	/**
	* 设置字段colchanged的值，该字段的<br>
	* 字段名称 :colchanged<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setColchanged(String colchanged) {
		if (colchanged == null){
			this.colchanged = null;
			return;
		}
		this.colchanged = new Integer(colchanged);
    }

	/**
	* 获取字段Required的值，该字段的<br>
	* 字段名称 :Required<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	varchar<br>
	*/
	public int getRequired() {
		if(Required==null){return 0;}
		return Required.intValue();
	}

	/**
	* 设置字段Required的值，该字段的<br>
	* 字段名称 :Required<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	varchar<br>
	*/
	public void setRequired(int required) {
		this.Required = new Integer(required);
    }

	/**
	* 设置字段Required的值，该字段的<br>
	* 字段名称 :Required<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	varchar<br>
	*/
	public void setRequired(String required) {
		if (required == null){
			this.Required = null;
			return;
		}
		this.Required = new Integer(required);
    }

	/**
	* 获取字段ValidExp的值，该字段的<br>
	* 字段名称 :ValidExp<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	У<br>
	*/
	public String getValidExp() {
		return ValidExp;
	}

	/**
	* 设置字段ValidExp的值，该字段的<br>
	* 字段名称 :ValidExp<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	У<br>
	*/
	public void setValidExp(String validExp) {
		this.ValidExp = validExp;
    }

	/**
	* 获取字段MultiSelected的值，该字段的<br>
	* 字段名称 :MultiSelected<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getMultiSelected() {
		if(MultiSelected==null){return 0;}
		return MultiSelected.intValue();
	}

	/**
	* 设置字段MultiSelected的值，该字段的<br>
	* 字段名称 :MultiSelected<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMultiSelected(int multiSelected) {
		this.MultiSelected = new Integer(multiSelected);
    }

	/**
	* 设置字段MultiSelected的值，该字段的<br>
	* 字段名称 :MultiSelected<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMultiSelected(String multiSelected) {
		if (multiSelected == null){
			this.MultiSelected = null;
			return;
		}
		this.MultiSelected = new Integer(multiSelected);
    }

	/**
	* 获取字段devinfo的值，该字段的<br>
	* 字段名称 :devinfo<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getDevinfo() {
		return devinfo;
	}

	/**
	* 设置字段devinfo的值，该字段的<br>
	* 字段名称 :devinfo<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setDevinfo(String devinfo) {
		this.devinfo = devinfo;
    }

	/**
	* 获取字段inputtype的值，该字段的<br>
	* 字段名称 :inputtype<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	(ҵ<br>
	*/
	public int getInputtype() {
		if(inputtype==null){return 0;}
		return inputtype.intValue();
	}

	/**
	* 设置字段inputtype的值，该字段的<br>
	* 字段名称 :inputtype<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	(ҵ<br>
	*/
	public void setInputtype(int inputtype) {
		this.inputtype = new Integer(inputtype);
    }

	/**
	* 设置字段inputtype的值，该字段的<br>
	* 字段名称 :inputtype<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	(ҵ<br>
	*/
	public void setInputtype(String inputtype) {
		if (inputtype == null){
			this.inputtype = null;
			return;
		}
		this.inputtype = new Integer(inputtype);
    }

	/**
	* 获取字段colorder的值，该字段的<br>
	* 字段名称 :colorder<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	ͬһ<br>
	*/
	public int getColorder() {
		if(colorder==null){return 0;}
		return colorder.intValue();
	}

	/**
	* 设置字段colorder的值，该字段的<br>
	* 字段名称 :colorder<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	ͬһ<br>
	*/
	public void setColorder(int colorder) {
		this.colorder = new Integer(colorder);
    }

	/**
	* 设置字段colorder的值，该字段的<br>
	* 字段名称 :colorder<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	ͬһ<br>
	*/
	public void setColorder(String colorder) {
		if (colorder == null){
			this.colorder = null;
			return;
		}
		this.colorder = new Integer(colorder);
    }

	/**
	* 获取字段tracelog的值，该字段的<br>
	* 字段名称 :tracelog<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getTracelog() {
		if(tracelog==null){return 0;}
		return tracelog.intValue();
	}

	/**
	* 设置字段tracelog的值，该字段的<br>
	* 字段名称 :tracelog<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTracelog(int tracelog) {
		this.tracelog = new Integer(tracelog);
    }

	/**
	* 设置字段tracelog的值，该字段的<br>
	* 字段名称 :tracelog<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTracelog(String tracelog) {
		if (tracelog == null){
			this.tracelog = null;
			return;
		}
		this.tracelog = new Integer(tracelog);
    }

	/**
	* 获取字段usedRefid的值，该字段的<br>
	* 字段名称 :usedRefid<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	ʹ<br>
	*/
	public String getUsedRefid() {
		return usedRefid;
	}

	/**
	* 设置字段usedRefid的值，该字段的<br>
	* 字段名称 :usedRefid<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	ʹ<br>
	*/
	public void setUsedRefid(String usedRefid) {
		this.usedRefid = usedRefid;
    }

	/**
	* 获取字段usedRefidRoot的值，该字段的<br>
	* 字段名称 :usedRefidRoot<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getUsedRefidRoot() {
		if(usedRefidRoot==null){return 0;}
		return usedRefidRoot.intValue();
	}

	/**
	* 设置字段usedRefidRoot的值，该字段的<br>
	* 字段名称 :usedRefidRoot<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setUsedRefidRoot(int usedRefidRoot) {
		this.usedRefidRoot = new Integer(usedRefidRoot);
    }

	/**
	* 设置字段usedRefidRoot的值，该字段的<br>
	* 字段名称 :usedRefidRoot<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setUsedRefidRoot(String usedRefidRoot) {
		if (usedRefidRoot == null){
			this.usedRefidRoot = null;
			return;
		}
		this.usedRefidRoot = new Integer(usedRefidRoot);
    }

	/**
	* 获取字段dynamicbind的值，该字段的<br>
	* 字段名称 :dynamicbind<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getDynamicbind() {
		if(dynamicbind==null){return 0;}
		return dynamicbind.intValue();
	}

	/**
	* 设置字段dynamicbind的值，该字段的<br>
	* 字段名称 :dynamicbind<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setDynamicbind(int dynamicbind) {
		this.dynamicbind = new Integer(dynamicbind);
    }

	/**
	* 设置字段dynamicbind的值，该字段的<br>
	* 字段名称 :dynamicbind<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setDynamicbind(String dynamicbind) {
		if (dynamicbind == null){
			this.dynamicbind = null;
			return;
		}
		this.dynamicbind = new Integer(dynamicbind);
    }

}