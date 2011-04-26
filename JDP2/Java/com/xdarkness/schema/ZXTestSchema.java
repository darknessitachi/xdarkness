package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZXTest<br>
 * 表代码：ZXTest<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID<br>
 */
public class ZXTestSchema extends Schema {
	private String ID;

	private String Name;

	private String Gender;

	private String Type;

	private String CertNo;

	private Integer Score;

	private String Memo;

	private Date AddDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.STRING, 0, 50 , 0 , true , true),
		new SchemaColumn("Name", DataColumn.STRING, 1, 50 , 0 , true , false),
		new SchemaColumn("Gender", DataColumn.STRING, 2, 10 , 0 , true , false),
		new SchemaColumn("Type", DataColumn.STRING, 3, 20 , 0 , true , false),
		new SchemaColumn("CertNo", DataColumn.STRING, 4, 50 , 0 , true , false),
		new SchemaColumn("Score", DataColumn.INTEGER, 5, 11 , 0 , true , false),
		new SchemaColumn("Memo", DataColumn.STRING, 6, 200 , 0 , false , false),
		new SchemaColumn("AddDate", DataColumn.DATETIME, 7, 0 , 0 , true , false)
	};

	public static final String _TableCode = "ZXTest";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into ZXTest values(?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZXTest set ID=?,Name=?,Gender=?,Type=?,CertNo=?,Score=?,Memo=?,AddDate=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZXTest  where ID=?";

	protected static final String _FillAllSQL = "select * from ZXTest  where ID=?";

	public ZXTestSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[8];
	}

	protected Schema newInstance(){
		return new ZXTestSchema();
	}

	protected SchemaSet newSet(){
		return new ZXTestSet();
	}

	public ZXTestSet query() {
		return query(null, -1, -1);
	}

	public ZXTestSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZXTestSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZXTestSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZXTestSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){ID = (String)v;return;}
		if (i == 1){Name = (String)v;return;}
		if (i == 2){Gender = (String)v;return;}
		if (i == 3){Type = (String)v;return;}
		if (i == 4){CertNo = (String)v;return;}
		if (i == 5){if(v==null){Score = null;}else{Score = new Integer(v.toString());}return;}
		if (i == 6){Memo = (String)v;return;}
		if (i == 7){AddDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return Name;}
		if (i == 2){return Gender;}
		if (i == 3){return Type;}
		if (i == 4){return CertNo;}
		if (i == 5){return Score;}
		if (i == 6){return Memo;}
		if (i == 7){return AddDate;}
		return null;
	}

	/**
	* 获取字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public String getID() {
		return ID;
	}

	/**
	* 设置字段ID的值，该字段的<br>
	* 字段名称 :ID<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setID(String iD) {
		this.ID = iD;
    }

	/**
	* 获取字段Name的值，该字段的<br>
	* 字段名称 :Name<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getName() {
		return Name;
	}

	/**
	* 设置字段Name的值，该字段的<br>
	* 字段名称 :Name<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setName(String name) {
		this.Name = name;
    }

	/**
	* 获取字段Gender的值，该字段的<br>
	* 字段名称 :Gender<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getGender() {
		return Gender;
	}

	/**
	* 设置字段Gender的值，该字段的<br>
	* 字段名称 :Gender<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setGender(String gender) {
		this.Gender = gender;
    }

	/**
	* 获取字段Type的值，该字段的<br>
	* 字段名称 :Type<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getType() {
		return Type;
	}

	/**
	* 设置字段Type的值，该字段的<br>
	* 字段名称 :Type<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setType(String type) {
		this.Type = type;
    }

	/**
	* 获取字段CertNo的值，该字段的<br>
	* 字段名称 :CertNo<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getCertNo() {
		return CertNo;
	}

	/**
	* 设置字段CertNo的值，该字段的<br>
	* 字段名称 :CertNo<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setCertNo(String certNo) {
		this.CertNo = certNo;
    }

	/**
	* 获取字段Score的值，该字段的<br>
	* 字段名称 :Score<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public int getScore() {
		if(Score==null){return 0;}
		return Score.intValue();
	}

	/**
	* 设置字段Score的值，该字段的<br>
	* 字段名称 :Score<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setScore(int score) {
		this.Score = new Integer(score);
    }

	/**
	* 设置字段Score的值，该字段的<br>
	* 字段名称 :Score<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setScore(String score) {
		if (score == null){
			this.Score = null;
			return;
		}
		this.Score = new Integer(score);
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
	* 获取字段AddDate的值，该字段的<br>
	* 字段名称 :AddDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public Date getAddDate() {
		return AddDate;
	}

	/**
	* 设置字段AddDate的值，该字段的<br>
	* 字段名称 :AddDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddDate(Date addDate) {
		this.AddDate = addDate;
    }

}