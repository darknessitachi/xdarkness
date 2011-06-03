package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：TablesRemark<br>
 * 表代码：TablesRemark<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：tablename<br>
 */
public class TablesRemarkSchema extends Schema {
	private String tablename;

	private Date addtime;

	private Integer moder;

	private Date modtime;

	private String alias;

	private String remark;

	private String primaryEx1;

	private String primaryEx2;

	private String ordercol;

	private Integer BoolUseCache;

	private Integer tabletype;

	private Integer categoryid;

	private String summarytable;

	private String summarycol;

	private Integer indexid;

	private Integer tablechanged;

	private Integer colchanged;

	private Integer tracelog;

	private String baseTitle;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("tablename", DataColumn.STRING, 0, 50 , 0 , true , true),
		new SchemaColumn("addtime", DataColumn.DATETIME, 1, 0 , 0 , false , false),
		new SchemaColumn("moder", DataColumn.INTEGER, 2, 11 , 0 , true , false),
		new SchemaColumn("modtime", DataColumn.DATETIME, 3, 0 , 0 , false , false),
		new SchemaColumn("alias", DataColumn.STRING, 4, 255 , 0 , true , false),
		new SchemaColumn("remark", DataColumn.CLOB, 5, 0 , 0 , true , false),
		new SchemaColumn("primaryEx1", DataColumn.STRING, 6, 100 , 0 , true , false),
		new SchemaColumn("primaryEx2", DataColumn.STRING, 7, 100 , 0 , true , false),
		new SchemaColumn("ordercol", DataColumn.STRING, 8, 100 , 0 , true , false),
		new SchemaColumn("BoolUseCache", DataColumn.INTEGER, 9, 4 , 0 , true , false),
		new SchemaColumn("tabletype", DataColumn.INTEGER, 10, 4 , 0 , true , false),
		new SchemaColumn("categoryid", DataColumn.INTEGER, 11, 11 , 0 , true , false),
		new SchemaColumn("summarytable", DataColumn.STRING, 12, 50 , 0 , true , false),
		new SchemaColumn("summarycol", DataColumn.STRING, 13, 50 , 0 , true , false),
		new SchemaColumn("indexid", DataColumn.INTEGER, 14, 11 , 0 , true , false),
		new SchemaColumn("tablechanged", DataColumn.INTEGER, 15, 4 , 0 , true , false),
		new SchemaColumn("colchanged", DataColumn.INTEGER, 16, 4 , 0 , true , false),
		new SchemaColumn("tracelog", DataColumn.INTEGER, 17, 4 , 0 , true , false),
		new SchemaColumn("baseTitle", DataColumn.STRING, 18, 100 , 0 , true , false)
	};

	public static final String _TableCode = "TablesRemark";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into TablesRemark values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update TablesRemark set tablename=?,addtime=?,moder=?,modtime=?,alias=?,remark=?,primaryEx1=?,primaryEx2=?,ordercol=?,BoolUseCache=?,tabletype=?,categoryid=?,summarytable=?,summarycol=?,indexid=?,tablechanged=?,colchanged=?,tracelog=?,baseTitle=? where tablename=?";

	protected static final String _DeleteSQL = "delete from TablesRemark  where tablename=?";

	protected static final String _FillAllSQL = "select * from TablesRemark  where tablename=?";

	public TablesRemarkSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[19];
	}

	protected Schema newInstance(){
		return new TablesRemarkSchema();
	}

	protected SchemaSet newSet(){
		return new TablesRemarkSet();
	}

	public TablesRemarkSet query() {
		return query(null, -1, -1);
	}

	public TablesRemarkSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public TablesRemarkSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public TablesRemarkSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (TablesRemarkSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){tablename = (String)v;return;}
		if (i == 1){addtime = (Date)v;return;}
		if (i == 2){if(v==null){moder = null;}else{moder = new Integer(v.toString());}return;}
		if (i == 3){modtime = (Date)v;return;}
		if (i == 4){alias = (String)v;return;}
		if (i == 5){remark = (String)v;return;}
		if (i == 6){primaryEx1 = (String)v;return;}
		if (i == 7){primaryEx2 = (String)v;return;}
		if (i == 8){ordercol = (String)v;return;}
		if (i == 9){if(v==null){BoolUseCache = null;}else{BoolUseCache = new Integer(v.toString());}return;}
		if (i == 10){if(v==null){tabletype = null;}else{tabletype = new Integer(v.toString());}return;}
		if (i == 11){if(v==null){categoryid = null;}else{categoryid = new Integer(v.toString());}return;}
		if (i == 12){summarytable = (String)v;return;}
		if (i == 13){summarycol = (String)v;return;}
		if (i == 14){if(v==null){indexid = null;}else{indexid = new Integer(v.toString());}return;}
		if (i == 15){if(v==null){tablechanged = null;}else{tablechanged = new Integer(v.toString());}return;}
		if (i == 16){if(v==null){colchanged = null;}else{colchanged = new Integer(v.toString());}return;}
		if (i == 17){if(v==null){tracelog = null;}else{tracelog = new Integer(v.toString());}return;}
		if (i == 18){baseTitle = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return tablename;}
		if (i == 1){return addtime;}
		if (i == 2){return moder;}
		if (i == 3){return modtime;}
		if (i == 4){return alias;}
		if (i == 5){return remark;}
		if (i == 6){return primaryEx1;}
		if (i == 7){return primaryEx2;}
		if (i == 8){return ordercol;}
		if (i == 9){return BoolUseCache;}
		if (i == 10){return tabletype;}
		if (i == 11){return categoryid;}
		if (i == 12){return summarytable;}
		if (i == 13){return summarycol;}
		if (i == 14){return indexid;}
		if (i == 15){return tablechanged;}
		if (i == 16){return colchanged;}
		if (i == 17){return tracelog;}
		if (i == 18){return baseTitle;}
		return null;
	}

	/**
	* 获取字段tablename的值，该字段的<br>
	* 字段名称 :tablename<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getTablename() {
		return tablename;
	}

	/**
	* 设置字段tablename的值，该字段的<br>
	* 字段名称 :tablename<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setTablename(String tablename) {
		this.tablename = tablename;
    }

	/**
	* 获取字段addtime的值，该字段的<br>
	* 字段名称 :addtime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getAddtime() {
		return addtime;
	}

	/**
	* 设置字段addtime的值，该字段的<br>
	* 字段名称 :addtime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
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
	* 获取字段alias的值，该字段的<br>
	* 字段名称 :alias<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAlias() {
		return alias;
	}

	/**
	* 设置字段alias的值，该字段的<br>
	* 字段名称 :alias<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAlias(String alias) {
		this.alias = alias;
    }

	/**
	* 获取字段remark的值，该字段的<br>
	* 字段名称 :remark<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getRemark() {
		return remark;
	}

	/**
	* 设置字段remark的值，该字段的<br>
	* 字段名称 :remark<br>
	* 数据类型 :text<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setRemark(String remark) {
		this.remark = remark;
    }

	/**
	* 获取字段primaryEx1的值，该字段的<br>
	* 字段名称 :primaryEx1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getPrimaryEx1() {
		return primaryEx1;
	}

	/**
	* 设置字段primaryEx1的值，该字段的<br>
	* 字段名称 :primaryEx1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setPrimaryEx1(String primaryEx1) {
		this.primaryEx1 = primaryEx1;
    }

	/**
	* 获取字段primaryEx2的值，该字段的<br>
	* 字段名称 :primaryEx2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getPrimaryEx2() {
		return primaryEx2;
	}

	/**
	* 设置字段primaryEx2的值，该字段的<br>
	* 字段名称 :primaryEx2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setPrimaryEx2(String primaryEx2) {
		this.primaryEx2 = primaryEx2;
    }

	/**
	* 获取字段ordercol的值，该字段的<br>
	* 字段名称 :ordercol<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getOrdercol() {
		return ordercol;
	}

	/**
	* 设置字段ordercol的值，该字段的<br>
	* 字段名称 :ordercol<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setOrdercol(String ordercol) {
		this.ordercol = ordercol;
    }

	/**
	* 获取字段BoolUseCache的值，该字段的<br>
	* 字段名称 :BoolUseCache<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getBoolUseCache() {
		if(BoolUseCache==null){return 0;}
		return BoolUseCache.intValue();
	}

	/**
	* 设置字段BoolUseCache的值，该字段的<br>
	* 字段名称 :BoolUseCache<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBoolUseCache(int boolUseCache) {
		this.BoolUseCache = new Integer(boolUseCache);
    }

	/**
	* 设置字段BoolUseCache的值，该字段的<br>
	* 字段名称 :BoolUseCache<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBoolUseCache(String boolUseCache) {
		if (boolUseCache == null){
			this.BoolUseCache = null;
			return;
		}
		this.BoolUseCache = new Integer(boolUseCache);
    }

	/**
	* 获取字段tabletype的值，该字段的<br>
	* 字段名称 :tabletype<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getTabletype() {
		if(tabletype==null){return 0;}
		return tabletype.intValue();
	}

	/**
	* 设置字段tabletype的值，该字段的<br>
	* 字段名称 :tabletype<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTabletype(int tabletype) {
		this.tabletype = new Integer(tabletype);
    }

	/**
	* 设置字段tabletype的值，该字段的<br>
	* 字段名称 :tabletype<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTabletype(String tabletype) {
		if (tabletype == null){
			this.tabletype = null;
			return;
		}
		this.tabletype = new Integer(tabletype);
    }

	/**
	* 获取字段categoryid的值，该字段的<br>
	* 字段名称 :categoryid<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	ҵ<br>
	*/
	public int getCategoryid() {
		if(categoryid==null){return 0;}
		return categoryid.intValue();
	}

	/**
	* 设置字段categoryid的值，该字段的<br>
	* 字段名称 :categoryid<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	ҵ<br>
	*/
	public void setCategoryid(int categoryid) {
		this.categoryid = new Integer(categoryid);
    }

	/**
	* 设置字段categoryid的值，该字段的<br>
	* 字段名称 :categoryid<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	ҵ<br>
	*/
	public void setCategoryid(String categoryid) {
		if (categoryid == null){
			this.categoryid = null;
			return;
		}
		this.categoryid = new Integer(categoryid);
    }

	/**
	* 获取字段summarytable的值，该字段的<br>
	* 字段名称 :summarytable<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	summarytable<br>
	*/
	public String getSummarytable() {
		return summarytable;
	}

	/**
	* 设置字段summarytable的值，该字段的<br>
	* 字段名称 :summarytable<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	summarytable<br>
	*/
	public void setSummarytable(String summarytable) {
		this.summarytable = summarytable;
    }

	/**
	* 获取字段summarycol的值，该字段的<br>
	* 字段名称 :summarycol<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getSummarycol() {
		return summarycol;
	}

	/**
	* 设置字段summarycol的值，该字段的<br>
	* 字段名称 :summarycol<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSummarycol(String summarycol) {
		this.summarycol = summarycol;
    }

	/**
	* 获取字段indexid的值，该字段的<br>
	* 字段名称 :indexid<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getIndexid() {
		if(indexid==null){return 0;}
		return indexid.intValue();
	}

	/**
	* 设置字段indexid的值，该字段的<br>
	* 字段名称 :indexid<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setIndexid(int indexid) {
		this.indexid = new Integer(indexid);
    }

	/**
	* 设置字段indexid的值，该字段的<br>
	* 字段名称 :indexid<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setIndexid(String indexid) {
		if (indexid == null){
			this.indexid = null;
			return;
		}
		this.indexid = new Integer(indexid);
    }

	/**
	* 获取字段tablechanged的值，该字段的<br>
	* 字段名称 :tablechanged<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getTablechanged() {
		if(tablechanged==null){return 0;}
		return tablechanged.intValue();
	}

	/**
	* 设置字段tablechanged的值，该字段的<br>
	* 字段名称 :tablechanged<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTablechanged(int tablechanged) {
		this.tablechanged = new Integer(tablechanged);
    }

	/**
	* 设置字段tablechanged的值，该字段的<br>
	* 字段名称 :tablechanged<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTablechanged(String tablechanged) {
		if (tablechanged == null){
			this.tablechanged = null;
			return;
		}
		this.tablechanged = new Integer(tablechanged);
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
	* 获取字段baseTitle的值，该字段的<br>
	* 字段名称 :baseTitle<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	չʾ<br>
	*/
	public String getBaseTitle() {
		return baseTitle;
	}

	/**
	* 设置字段baseTitle的值，该字段的<br>
	* 字段名称 :baseTitle<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	* 备注信息 :<br>
	չʾ<br>
	*/
	public void setBaseTitle(String baseTitle) {
		this.baseTitle = baseTitle;
    }

}