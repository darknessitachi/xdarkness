package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：CategoryValue<br>
 * 表代码：CategoryValue<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：Id<br>
 */
public class CategoryValueSchema extends Schema {
	private String Id;

	private Integer adder;

	private Date addtime;

	private Integer moder;

	private Date modtime;

	private Integer delstatus;

	private String refid;

	private String categoryid;

	private String chinaname;

	private Integer extint1;

	private Integer extint2;

	private Integer extint3;

	private String extchar1;

	private String extchar2;

	private String extchar3;

	private String extchar4;

	private String parentid;

	private Integer indexid;

	private String sortcode;

	private String remark;

	private Integer isleaf;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("Id", DataColumn.STRING, 0, 40 , 0 , true , true),
		new SchemaColumn("adder", DataColumn.INTEGER, 1, 11 , 0 , true , false),
		new SchemaColumn("addtime", DataColumn.DATETIME, 2, 0 , 0 , true , false),
		new SchemaColumn("moder", DataColumn.INTEGER, 3, 11 , 0 , true , false),
		new SchemaColumn("modtime", DataColumn.DATETIME, 4, 0 , 0 , true , false),
		new SchemaColumn("delstatus", DataColumn.INTEGER, 5, 4 , 0 , true , false),
		new SchemaColumn("refid", DataColumn.STRING, 6, 30 , 0 , true , false),
		new SchemaColumn("categoryid", DataColumn.STRING, 7, 40 , 0 , true , false),
		new SchemaColumn("chinaname", DataColumn.STRING, 8, 255 , 0 , true , false),
		new SchemaColumn("extint1", DataColumn.INTEGER, 9, 11 , 0 , true , false),
		new SchemaColumn("extint2", DataColumn.INTEGER, 10, 11 , 0 , true , false),
		new SchemaColumn("extint3", DataColumn.INTEGER, 11, 11 , 0 , true , false),
		new SchemaColumn("extchar1", DataColumn.STRING, 12, 255 , 0 , true , false),
		new SchemaColumn("extchar2", DataColumn.STRING, 13, 255 , 0 , true , false),
		new SchemaColumn("extchar3", DataColumn.STRING, 14, 255 , 0 , true , false),
		new SchemaColumn("extchar4", DataColumn.STRING, 15, 255 , 0 , true , false),
		new SchemaColumn("parentid", DataColumn.STRING, 16, 40 , 0 , true , false),
		new SchemaColumn("indexid", DataColumn.INTEGER, 17, 11 , 0 , true , false),
		new SchemaColumn("sortcode", DataColumn.STRING, 18, 100 , 0 , true , false),
		new SchemaColumn("remark", DataColumn.STRING, 19, 1000 , 0 , true , false),
		new SchemaColumn("isleaf", DataColumn.INTEGER, 20, 4 , 0 , true , false)
	};

	public static final String _TableCode = "CategoryValue";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into CategoryValue values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update CategoryValue set Id=?,adder=?,addtime=?,moder=?,modtime=?,delstatus=?,refid=?,categoryid=?,chinaname=?,extint1=?,extint2=?,extint3=?,extchar1=?,extchar2=?,extchar3=?,extchar4=?,parentid=?,indexid=?,sortcode=?,remark=?,isleaf=? where Id=?";

	protected static final String _DeleteSQL = "delete from CategoryValue  where Id=?";

	protected static final String _FillAllSQL = "select * from CategoryValue  where Id=?";

	public CategoryValueSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[21];
	}

	protected Schema newInstance(){
		return new CategoryValueSchema();
	}

	protected SchemaSet newSet(){
		return new CategoryValueSet();
	}

	public CategoryValueSet query() {
		return query(null, -1, -1);
	}

	public CategoryValueSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public CategoryValueSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public CategoryValueSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (CategoryValueSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){Id = (String)v;return;}
		if (i == 1){if(v==null){adder = null;}else{adder = new Integer(v.toString());}return;}
		if (i == 2){addtime = (Date)v;return;}
		if (i == 3){if(v==null){moder = null;}else{moder = new Integer(v.toString());}return;}
		if (i == 4){modtime = (Date)v;return;}
		if (i == 5){if(v==null){delstatus = null;}else{delstatus = new Integer(v.toString());}return;}
		if (i == 6){refid = (String)v;return;}
		if (i == 7){categoryid = (String)v;return;}
		if (i == 8){chinaname = (String)v;return;}
		if (i == 9){if(v==null){extint1 = null;}else{extint1 = new Integer(v.toString());}return;}
		if (i == 10){if(v==null){extint2 = null;}else{extint2 = new Integer(v.toString());}return;}
		if (i == 11){if(v==null){extint3 = null;}else{extint3 = new Integer(v.toString());}return;}
		if (i == 12){extchar1 = (String)v;return;}
		if (i == 13){extchar2 = (String)v;return;}
		if (i == 14){extchar3 = (String)v;return;}
		if (i == 15){extchar4 = (String)v;return;}
		if (i == 16){parentid = (String)v;return;}
		if (i == 17){if(v==null){indexid = null;}else{indexid = new Integer(v.toString());}return;}
		if (i == 18){sortcode = (String)v;return;}
		if (i == 19){remark = (String)v;return;}
		if (i == 20){if(v==null){isleaf = null;}else{isleaf = new Integer(v.toString());}return;}
	}

	public Object getV(int i) {
		if (i == 0){return Id;}
		if (i == 1){return adder;}
		if (i == 2){return addtime;}
		if (i == 3){return moder;}
		if (i == 4){return modtime;}
		if (i == 5){return delstatus;}
		if (i == 6){return refid;}
		if (i == 7){return categoryid;}
		if (i == 8){return chinaname;}
		if (i == 9){return extint1;}
		if (i == 10){return extint2;}
		if (i == 11){return extint3;}
		if (i == 12){return extchar1;}
		if (i == 13){return extchar2;}
		if (i == 14){return extchar3;}
		if (i == 15){return extchar4;}
		if (i == 16){return parentid;}
		if (i == 17){return indexid;}
		if (i == 18){return sortcode;}
		if (i == 19){return remark;}
		if (i == 20){return isleaf;}
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
	* 获取字段refid的值，该字段的<br>
	* 字段名称 :refid<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getRefid() {
		return refid;
	}

	/**
	* 设置字段refid的值，该字段的<br>
	* 字段名称 :refid<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setRefid(String refid) {
		this.refid = refid;
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
	* 获取字段extint1的值，该字段的<br>
	* 字段名称 :extint1<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getExtint1() {
		if(extint1==null){return 0;}
		return extint1.intValue();
	}

	/**
	* 设置字段extint1的值，该字段的<br>
	* 字段名称 :extint1<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setExtint1(int extint1) {
		this.extint1 = new Integer(extint1);
    }

	/**
	* 设置字段extint1的值，该字段的<br>
	* 字段名称 :extint1<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setExtint1(String extint1) {
		if (extint1 == null){
			this.extint1 = null;
			return;
		}
		this.extint1 = new Integer(extint1);
    }

	/**
	* 获取字段extint2的值，该字段的<br>
	* 字段名称 :extint2<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getExtint2() {
		if(extint2==null){return 0;}
		return extint2.intValue();
	}

	/**
	* 设置字段extint2的值，该字段的<br>
	* 字段名称 :extint2<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setExtint2(int extint2) {
		this.extint2 = new Integer(extint2);
    }

	/**
	* 设置字段extint2的值，该字段的<br>
	* 字段名称 :extint2<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setExtint2(String extint2) {
		if (extint2 == null){
			this.extint2 = null;
			return;
		}
		this.extint2 = new Integer(extint2);
    }

	/**
	* 获取字段extint3的值，该字段的<br>
	* 字段名称 :extint3<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getExtint3() {
		if(extint3==null){return 0;}
		return extint3.intValue();
	}

	/**
	* 设置字段extint3的值，该字段的<br>
	* 字段名称 :extint3<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setExtint3(int extint3) {
		this.extint3 = new Integer(extint3);
    }

	/**
	* 设置字段extint3的值，该字段的<br>
	* 字段名称 :extint3<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setExtint3(String extint3) {
		if (extint3 == null){
			this.extint3 = null;
			return;
		}
		this.extint3 = new Integer(extint3);
    }

	/**
	* 获取字段extchar1的值，该字段的<br>
	* 字段名称 :extchar1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getExtchar1() {
		return extchar1;
	}

	/**
	* 设置字段extchar1的值，该字段的<br>
	* 字段名称 :extchar1<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setExtchar1(String extchar1) {
		this.extchar1 = extchar1;
    }

	/**
	* 获取字段extchar2的值，该字段的<br>
	* 字段名称 :extchar2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getExtchar2() {
		return extchar2;
	}

	/**
	* 设置字段extchar2的值，该字段的<br>
	* 字段名称 :extchar2<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setExtchar2(String extchar2) {
		this.extchar2 = extchar2;
    }

	/**
	* 获取字段extchar3的值，该字段的<br>
	* 字段名称 :extchar3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getExtchar3() {
		return extchar3;
	}

	/**
	* 设置字段extchar3的值，该字段的<br>
	* 字段名称 :extchar3<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setExtchar3(String extchar3) {
		this.extchar3 = extchar3;
    }

	/**
	* 获取字段extchar4的值，该字段的<br>
	* 字段名称 :extchar4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getExtchar4() {
		return extchar4;
	}

	/**
	* 设置字段extchar4的值，该字段的<br>
	* 字段名称 :extchar4<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setExtchar4(String extchar4) {
		this.extchar4 = extchar4;
    }

	/**
	* 获取字段parentid的值，该字段的<br>
	* 字段名称 :parentid<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getParentid() {
		return parentid;
	}

	/**
	* 设置字段parentid的值，该字段的<br>
	* 字段名称 :parentid<br>
	* 数据类型 :varchar(40)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setParentid(String parentid) {
		this.parentid = parentid;
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
	* 获取字段sortcode的值，该字段的<br>
	* 字段名称 :sortcode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getSortcode() {
		return sortcode;
	}

	/**
	* 设置字段sortcode的值，该字段的<br>
	* 字段名称 :sortcode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSortcode(String sortcode) {
		this.sortcode = sortcode;
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
	* 获取字段isleaf的值，该字段的<br>
	* 字段名称 :isleaf<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getIsleaf() {
		if(isleaf==null){return 0;}
		return isleaf.intValue();
	}

	/**
	* 设置字段isleaf的值，该字段的<br>
	* 字段名称 :isleaf<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setIsleaf(int isleaf) {
		this.isleaf = new Integer(isleaf);
    }

	/**
	* 设置字段isleaf的值，该字段的<br>
	* 字段名称 :isleaf<br>
	* 数据类型 :tinyint(4)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setIsleaf(String isleaf) {
		if (isleaf == null){
			this.isleaf = null;
			return;
		}
		this.isleaf = new Integer(isleaf);
    }

}