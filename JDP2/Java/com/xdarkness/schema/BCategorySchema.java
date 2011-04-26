package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：Category备份<br>
 * 表代码：BCategory<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：Id, BackupNo<br>
 */
public class BCategorySchema extends Schema {
	private String Id;

	private Integer adder;

	private Date addtime;

	private Integer moder;

	private Date modtime;

	private Integer delstatus;

	private String constname;

	private String chinaname;

	private String optionname;

	private Integer maxdepth;

	private String remark;

	private Integer diclevel;

	private Integer folderid;

	private String extchar1name;

	private String extchar2name;

	private String extchar3name;

	private String extchar4name;

	private String extint1name;

	private String extint2name;

	private String extint3name;

	private String extint1categoryid;

	private String extint2categoryid;

	private String extint3categoryid;

	private Integer refidOrder;

	private Integer refidInt;

	private Integer leafuse;

	private String extTablename;

	private Integer sortBy;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 40 , 0 , true , true),
		new SchemaColumn("adder", DataColumn.INTEGER, 1, 11 , 0 , true , false),
		new SchemaColumn("addtime", DataColumn.DATETIME, 2, 0 , 0 , true , false),
		new SchemaColumn("moder", DataColumn.INTEGER, 3, 11 , 0 , true , false),
		new SchemaColumn("modtime", DataColumn.DATETIME, 4, 0 , 0 , true , false),
		new SchemaColumn("delstatus", DataColumn.INTEGER, 5, 4 , 0 , true , false),
		new SchemaColumn("constname", DataColumn.STRING, 6, 50 , 0 , true , false),
		new SchemaColumn("chinaname", DataColumn.STRING, 7, 255 , 0 , true , false),
		new SchemaColumn("optionname", DataColumn.STRING, 8, 50 , 0 , true , false),
		new SchemaColumn("maxdepth", DataColumn.INTEGER, 9, 4 , 0 , true , false),
		new SchemaColumn("remark", DataColumn.STRING, 10, 1000 , 0 , true , false),
		new SchemaColumn("diclevel", DataColumn.INTEGER, 11, 4 , 0 , true , false),
		new SchemaColumn("folderid", DataColumn.INTEGER, 12, 11 , 0 , true , false),
		new SchemaColumn("extchar1name", DataColumn.STRING, 13, 50 , 0 , true , false),
		new SchemaColumn("extchar2name", DataColumn.STRING, 14, 50 , 0 , true , false),
		new SchemaColumn("extchar3name", DataColumn.STRING, 15, 50 , 0 , true , false),
		new SchemaColumn("extchar4name", DataColumn.STRING, 16, 50 , 0 , true , false),
		new SchemaColumn("extint1name", DataColumn.STRING, 17, 50 , 0 , true , false),
		new SchemaColumn("extint2name", DataColumn.STRING, 18, 50 , 0 , true , false),
		new SchemaColumn("extint3name", DataColumn.STRING, 19, 50 , 0 , true , false),
		new SchemaColumn("extint1categoryid", DataColumn.STRING, 20, 40 , 0 , true , false),
		new SchemaColumn("extint2categoryid", DataColumn.STRING, 21, 40 , 0 , true , false),
		new SchemaColumn("extint3categoryid", DataColumn.STRING, 22, 40 , 0 , true , false),
		new SchemaColumn("refidOrder", DataColumn.INTEGER, 23, 4 , 0 , true , false),
		new SchemaColumn("refidInt", DataColumn.INTEGER, 24, 4 , 0 , true , false),
		new SchemaColumn("leafuse", DataColumn.INTEGER, 25, 4 , 0 , true , false),
		new SchemaColumn("extTablename", DataColumn.STRING, 26, 50 , 0 , true , false),
		new SchemaColumn("sortBy", DataColumn.INTEGER, 27, 4 , 0 , true , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 28, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 29, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 30, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 31, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BCategory";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into BCategory values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BCategory set Id=?,adder=?,addtime=?,moder=?,modtime=?,delstatus=?,constname=?,chinaname=?,optionname=?,maxdepth=?,remark=?,diclevel=?,folderid=?,extchar1name=?,extchar2name=?,extchar3name=?,extchar4name=?,extint1name=?,extint2name=?,extint3name=?,extint1categoryid=?,extint2categoryid=?,extint3categoryid=?,refidOrder=?,refidInt=?,leafuse=?,extTablename=?,sortBy=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where Id=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BCategory  where Id=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BCategory  where Id=? and BackupNo=?";

	public BCategorySchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[32];
	}

	protected Schema newInstance(){
		return new BCategorySchema();
	}

	protected SchemaSet newSet(){
		return new BCategorySet();
	}

	public BCategorySet query() {
		return query(null, -1, -1);
	}

	public BCategorySet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BCategorySet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BCategorySet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BCategorySet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){if(v==null){adder = null;}else{adder = new Integer(v.toString());}return;}
		if (i == 2){addtime = (Date)v;return;}
		if (i == 3){if(v==null){moder = null;}else{moder = new Integer(v.toString());}return;}
		if (i == 4){modtime = (Date)v;return;}
		if (i == 5){if(v==null){delstatus = null;}else{delstatus = new Integer(v.toString());}return;}
		if (i == 6){constname = (String)v;return;}
		if (i == 7){chinaname = (String)v;return;}
		if (i == 8){optionname = (String)v;return;}
		if (i == 9){if(v==null){maxdepth = null;}else{maxdepth = new Integer(v.toString());}return;}
		if (i == 10){remark = (String)v;return;}
		if (i == 11){if(v==null){diclevel = null;}else{diclevel = new Integer(v.toString());}return;}
		if (i == 12){if(v==null){folderid = null;}else{folderid = new Integer(v.toString());}return;}
		if (i == 13){extchar1name = (String)v;return;}
		if (i == 14){extchar2name = (String)v;return;}
		if (i == 15){extchar3name = (String)v;return;}
		if (i == 16){extchar4name = (String)v;return;}
		if (i == 17){extint1name = (String)v;return;}
		if (i == 18){extint2name = (String)v;return;}
		if (i == 19){extint3name = (String)v;return;}
		if (i == 20){extint1categoryid = (String)v;return;}
		if (i == 21){extint2categoryid = (String)v;return;}
		if (i == 22){extint3categoryid = (String)v;return;}
		if (i == 23){if(v==null){refidOrder = null;}else{refidOrder = new Integer(v.toString());}return;}
		if (i == 24){if(v==null){refidInt = null;}else{refidInt = new Integer(v.toString());}return;}
		if (i == 25){if(v==null){leafuse = null;}else{leafuse = new Integer(v.toString());}return;}
		if (i == 26){extTablename = (String)v;return;}
		if (i == 27){if(v==null){sortBy = null;}else{sortBy = new Integer(v.toString());}return;}
		if (i == 28){BackupNo = (String)v;return;}
		if (i == 29){BackupOperator = (String)v;return;}
		if (i == 30){BackupTime = (Date)v;return;}
		if (i == 31){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return adder;}
		if (i == 2){return addtime;}
		if (i == 3){return moder;}
		if (i == 4){return modtime;}
		if (i == 5){return delstatus;}
		if (i == 6){return constname;}
		if (i == 7){return chinaname;}
		if (i == 8){return optionname;}
		if (i == 9){return maxdepth;}
		if (i == 10){return remark;}
		if (i == 11){return diclevel;}
		if (i == 12){return folderid;}
		if (i == 13){return extchar1name;}
		if (i == 14){return extchar2name;}
		if (i == 15){return extchar3name;}
		if (i == 16){return extchar4name;}
		if (i == 17){return extint1name;}
		if (i == 18){return extint2name;}
		if (i == 19){return extint3name;}
		if (i == 20){return extint1categoryid;}
		if (i == 21){return extint2categoryid;}
		if (i == 22){return extint3categoryid;}
		if (i == 23){return refidOrder;}
		if (i == 24){return refidInt;}
		if (i == 25){return leafuse;}
		if (i == 26){return extTablename;}
		if (i == 27){return sortBy;}
		if (i == 28){return BackupNo;}
		if (i == 29){return BackupOperator;}
		if (i == 30){return BackupTime;}
		if (i == 31){return BackupMemo;}
		return null;
	}

	/**
	* 获取字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getId() {
		return Id;
	}

	/**
	* 设置字段Id的值，该字段的<br>
	* 字段名称 :Id<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setId(String id) {
		this.Id = id;
    }

	/**
	* 获取字段adder的值，该字段的<br>
	* 字段名称 :adder<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getAdder() {
		if(adder==null){return 0;}
		return adder.intValue();
	}

	/**
	* 设置字段adder的值，该字段的<br>
	* 字段名称 :adder<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAdder(int adder) {
		this.adder = new Integer(adder);
    }

	/**
	* 设置字段adder的值，该字段的<br>
	* 字段名称 :adder<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAdder(String adder) {
		if (adder == null){
			this.adder = null;
			return;
		}
		this.adder = new Integer(adder);
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
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getModtime() {
		return modtime;
	}

	/**
	* 设置字段modtime的值，该字段的<br>
	* 字段名称 :modtime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setModtime(Date modtime) {
		this.modtime = modtime;
    }

	/**
	* 获取字段delstatus的值，该字段的<br>
	* 字段名称 :delstatus<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getDelstatus() {
		if(delstatus==null){return 0;}
		return delstatus.intValue();
	}

	/**
	* 设置字段delstatus的值，该字段的<br>
	* 字段名称 :delstatus<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setDelstatus(int delstatus) {
		this.delstatus = new Integer(delstatus);
    }

	/**
	* 设置字段delstatus的值，该字段的<br>
	* 字段名称 :delstatus<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setDelstatus(String delstatus) {
		if (delstatus == null){
			this.delstatus = null;
			return;
		}
		this.delstatus = new Integer(delstatus);
    }

	/**
	* 获取字段constname的值，该字段的<br>
	* 字段名称 :constname<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getConstname() {
		return constname;
	}

	/**
	* 设置字段constname的值，该字段的<br>
	* 字段名称 :constname<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setConstname(String constname) {
		this.constname = constname;
    }

	/**
	* 获取字段chinaname的值，该字段的<br>
	* 字段名称 :chinaname<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getChinaname() {
		return chinaname;
	}

	/**
	* 设置字段chinaname的值，该字段的<br>
	* 字段名称 :chinaname<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setChinaname(String chinaname) {
		this.chinaname = chinaname;
    }

	/**
	* 获取字段optionname的值，该字段的<br>
	* 字段名称 :optionname<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getOptionname() {
		return optionname;
	}

	/**
	* 设置字段optionname的值，该字段的<br>
	* 字段名称 :optionname<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setOptionname(String optionname) {
		this.optionname = optionname;
    }

	/**
	* 获取字段maxdepth的值，该字段的<br>
	* 字段名称 :maxdepth<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getMaxdepth() {
		if(maxdepth==null){return 0;}
		return maxdepth.intValue();
	}

	/**
	* 设置字段maxdepth的值，该字段的<br>
	* 字段名称 :maxdepth<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMaxdepth(int maxdepth) {
		this.maxdepth = new Integer(maxdepth);
    }

	/**
	* 设置字段maxdepth的值，该字段的<br>
	* 字段名称 :maxdepth<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setMaxdepth(String maxdepth) {
		if (maxdepth == null){
			this.maxdepth = null;
			return;
		}
		this.maxdepth = new Integer(maxdepth);
    }

	/**
	* 获取字段remark的值，该字段的<br>
	* 字段名称 :remark<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getRemark() {
		return remark;
	}

	/**
	* 设置字段remark的值，该字段的<br>
	* 字段名称 :remark<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setRemark(String remark) {
		this.remark = remark;
    }

	/**
	* 获取字段diclevel的值，该字段的<br>
	* 字段名称 :diclevel<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getDiclevel() {
		if(diclevel==null){return 0;}
		return diclevel.intValue();
	}

	/**
	* 设置字段diclevel的值，该字段的<br>
	* 字段名称 :diclevel<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setDiclevel(int diclevel) {
		this.diclevel = new Integer(diclevel);
    }

	/**
	* 设置字段diclevel的值，该字段的<br>
	* 字段名称 :diclevel<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setDiclevel(String diclevel) {
		if (diclevel == null){
			this.diclevel = null;
			return;
		}
		this.diclevel = new Integer(diclevel);
    }

	/**
	* 获取字段folderid的值，该字段的<br>
	* 字段名称 :folderid<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getFolderid() {
		if(folderid==null){return 0;}
		return folderid.intValue();
	}

	/**
	* 设置字段folderid的值，该字段的<br>
	* 字段名称 :folderid<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setFolderid(int folderid) {
		this.folderid = new Integer(folderid);
    }

	/**
	* 设置字段folderid的值，该字段的<br>
	* 字段名称 :folderid<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setFolderid(String folderid) {
		if (folderid == null){
			this.folderid = null;
			return;
		}
		this.folderid = new Integer(folderid);
    }

	/**
	* 获取字段extchar1name的值，该字段的<br>
	* 字段名称 :extchar1name<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getExtchar1name() {
		return extchar1name;
	}

	/**
	* 设置字段extchar1name的值，该字段的<br>
	* 字段名称 :extchar1name<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setExtchar1name(String extchar1name) {
		this.extchar1name = extchar1name;
    }

	/**
	* 获取字段extchar2name的值，该字段的<br>
	* 字段名称 :extchar2name<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getExtchar2name() {
		return extchar2name;
	}

	/**
	* 设置字段extchar2name的值，该字段的<br>
	* 字段名称 :extchar2name<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setExtchar2name(String extchar2name) {
		this.extchar2name = extchar2name;
    }

	/**
	* 获取字段extchar3name的值，该字段的<br>
	* 字段名称 :extchar3name<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getExtchar3name() {
		return extchar3name;
	}

	/**
	* 设置字段extchar3name的值，该字段的<br>
	* 字段名称 :extchar3name<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setExtchar3name(String extchar3name) {
		this.extchar3name = extchar3name;
    }

	/**
	* 获取字段extchar4name的值，该字段的<br>
	* 字段名称 :extchar4name<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getExtchar4name() {
		return extchar4name;
	}

	/**
	* 设置字段extchar4name的值，该字段的<br>
	* 字段名称 :extchar4name<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setExtchar4name(String extchar4name) {
		this.extchar4name = extchar4name;
    }

	/**
	* 获取字段extint1name的值，该字段的<br>
	* 字段名称 :extint1name<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getExtint1name() {
		return extint1name;
	}

	/**
	* 设置字段extint1name的值，该字段的<br>
	* 字段名称 :extint1name<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setExtint1name(String extint1name) {
		this.extint1name = extint1name;
    }

	/**
	* 获取字段extint2name的值，该字段的<br>
	* 字段名称 :extint2name<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getExtint2name() {
		return extint2name;
	}

	/**
	* 设置字段extint2name的值，该字段的<br>
	* 字段名称 :extint2name<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setExtint2name(String extint2name) {
		this.extint2name = extint2name;
    }

	/**
	* 获取字段extint3name的值，该字段的<br>
	* 字段名称 :extint3name<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getExtint3name() {
		return extint3name;
	}

	/**
	* 设置字段extint3name的值，该字段的<br>
	* 字段名称 :extint3name<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setExtint3name(String extint3name) {
		this.extint3name = extint3name;
    }

	/**
	* 获取字段extint1categoryid的值，该字段的<br>
	* 字段名称 :extint1categoryid<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getExtint1categoryid() {
		return extint1categoryid;
	}

	/**
	* 设置字段extint1categoryid的值，该字段的<br>
	* 字段名称 :extint1categoryid<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setExtint1categoryid(String extint1categoryid) {
		this.extint1categoryid = extint1categoryid;
    }

	/**
	* 获取字段extint2categoryid的值，该字段的<br>
	* 字段名称 :extint2categoryid<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getExtint2categoryid() {
		return extint2categoryid;
	}

	/**
	* 设置字段extint2categoryid的值，该字段的<br>
	* 字段名称 :extint2categoryid<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setExtint2categoryid(String extint2categoryid) {
		this.extint2categoryid = extint2categoryid;
    }

	/**
	* 获取字段extint3categoryid的值，该字段的<br>
	* 字段名称 :extint3categoryid<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getExtint3categoryid() {
		return extint3categoryid;
	}

	/**
	* 设置字段extint3categoryid的值，该字段的<br>
	* 字段名称 :extint3categoryid<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setExtint3categoryid(String extint3categoryid) {
		this.extint3categoryid = extint3categoryid;
    }

	/**
	* 获取字段refidOrder的值，该字段的<br>
	* 字段名称 :refidOrder<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getRefidOrder() {
		if(refidOrder==null){return 0;}
		return refidOrder.intValue();
	}

	/**
	* 设置字段refidOrder的值，该字段的<br>
	* 字段名称 :refidOrder<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setRefidOrder(int refidOrder) {
		this.refidOrder = new Integer(refidOrder);
    }

	/**
	* 设置字段refidOrder的值，该字段的<br>
	* 字段名称 :refidOrder<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setRefidOrder(String refidOrder) {
		if (refidOrder == null){
			this.refidOrder = null;
			return;
		}
		this.refidOrder = new Integer(refidOrder);
    }

	/**
	* 获取字段refidInt的值，该字段的<br>
	* 字段名称 :refidInt<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getRefidInt() {
		if(refidInt==null){return 0;}
		return refidInt.intValue();
	}

	/**
	* 设置字段refidInt的值，该字段的<br>
	* 字段名称 :refidInt<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setRefidInt(int refidInt) {
		this.refidInt = new Integer(refidInt);
    }

	/**
	* 设置字段refidInt的值，该字段的<br>
	* 字段名称 :refidInt<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setRefidInt(String refidInt) {
		if (refidInt == null){
			this.refidInt = null;
			return;
		}
		this.refidInt = new Integer(refidInt);
    }

	/**
	* 获取字段leafuse的值，该字段的<br>
	* 字段名称 :leafuse<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getLeafuse() {
		if(leafuse==null){return 0;}
		return leafuse.intValue();
	}

	/**
	* 设置字段leafuse的值，该字段的<br>
	* 字段名称 :leafuse<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setLeafuse(int leafuse) {
		this.leafuse = new Integer(leafuse);
    }

	/**
	* 设置字段leafuse的值，该字段的<br>
	* 字段名称 :leafuse<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setLeafuse(String leafuse) {
		if (leafuse == null){
			this.leafuse = null;
			return;
		}
		this.leafuse = new Integer(leafuse);
    }

	/**
	* 获取字段extTablename的值，该字段的<br>
	* 字段名称 :extTablename<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getExtTablename() {
		return extTablename;
	}

	/**
	* 设置字段extTablename的值，该字段的<br>
	* 字段名称 :extTablename<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setExtTablename(String extTablename) {
		this.extTablename = extTablename;
    }

	/**
	* 获取字段sortBy的值，该字段的<br>
	* 字段名称 :sortBy<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getSortBy() {
		if(sortBy==null){return 0;}
		return sortBy.intValue();
	}

	/**
	* 设置字段sortBy的值，该字段的<br>
	* 字段名称 :sortBy<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSortBy(int sortBy) {
		this.sortBy = new Integer(sortBy);
    }

	/**
	* 设置字段sortBy的值，该字段的<br>
	* 字段名称 :sortBy<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSortBy(String sortBy) {
		if (sortBy == null){
			this.sortBy = null;
			return;
		}
		this.sortBy = new Integer(sortBy);
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