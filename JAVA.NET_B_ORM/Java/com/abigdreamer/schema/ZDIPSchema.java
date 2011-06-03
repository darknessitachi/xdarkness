package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;

/**
 * 表名称：ZDIP<br>
 * 表代码：ZDIP<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：IP1, IP2<br>
 */
public class ZDIPSchema extends Schema {
	private Long IP1;

	private Long IP2;

	private String IP3;

	private String IP4;

	private String Address;

	private String Memo;

	private String DistrictCode;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("IP1", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("IP2", DataColumn.LONG, 1, 20 , 0 , true , true),
		new SchemaColumn("IP3", DataColumn.STRING, 2, 30 , 0 , true , false),
		new SchemaColumn("IP4", DataColumn.STRING, 3, 30 , 0 , false , false),
		new SchemaColumn("Address", DataColumn.STRING, 4, 200 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 5, 200 , 0 , false , false),
		new SchemaColumn("DistrictCode", DataColumn.STRING, 6, 10 , 0 , false , false)
	};

	public static final String _TableCode = "ZDIP";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into ZDIP values(?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZDIP set IP1=?,IP2=?,IP3=?,IP4=?,Address=?,Memo=?,DistrictCode=? where IP1=? and IP2=?";

	protected static final String _DeleteSQL = "delete from ZDIP  where IP1=? and IP2=?";

	protected static final String _FillAllSQL = "select * from ZDIP  where IP1=? and IP2=?";

	public ZDIPSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[7];
	}

	protected Schema newInstance(){
		return new ZDIPSchema();
	}

	protected SchemaSet newSet(){
		return new ZDIPSet();
	}

	public ZDIPSet query() {
		return query(null, -1, -1);
	}

	public ZDIPSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZDIPSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZDIPSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZDIPSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){IP1 = null;}else{IP1 = new Long(v.toString());}return;}
		if (i == 1){if(v==null){IP2 = null;}else{IP2 = new Long(v.toString());}return;}
		if (i == 2){IP3 = (String)v;return;}
		if (i == 3){IP4 = (String)v;return;}
		if (i == 4){Address = (String)v;return;}
		if (i == 5){Memo = (String)v;return;}
		if (i == 6){DistrictCode = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return IP1;}
		if (i == 1){return IP2;}
		if (i == 2){return IP3;}
		if (i == 3){return IP4;}
		if (i == 4){return Address;}
		if (i == 5){return Memo;}
		if (i == 6){return DistrictCode;}
		return null;
	}

	/**
	* 获取字段IP1的值，该字段的<br>
	* 字段名称 :IP1<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getIP1() {
		if(IP1==null){return 0;}
		return IP1.longValue();
	}

	/**
	* 设置字段IP1的值，该字段的<br>
	* 字段名称 :IP1<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setIP1(long iP1) {
		this.IP1 = new Long(iP1);
    }

	/**
	* 设置字段IP1的值，该字段的<br>
	* 字段名称 :IP1<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setIP1(String iP1) {
		if (iP1 == null){
			this.IP1 = null;
			return;
		}
		this.IP1 = new Long(iP1);
    }

	/**
	* 获取字段IP2的值，该字段的<br>
	* 字段名称 :IP2<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public long getIP2() {
		if(IP2==null){return 0;}
		return IP2.longValue();
	}

	/**
	* 设置字段IP2的值，该字段的<br>
	* 字段名称 :IP2<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setIP2(long iP2) {
		this.IP2 = new Long(iP2);
    }

	/**
	* 设置字段IP2的值，该字段的<br>
	* 字段名称 :IP2<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :true<br>
	* 是否必填 :true<br>
	*/
	public void setIP2(String iP2) {
		if (iP2 == null){
			this.IP2 = null;
			return;
		}
		this.IP2 = new Long(iP2);
    }

	/**
	* 获取字段IP3的值，该字段的<br>
	* 字段名称 :IP3<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getIP3() {
		return IP3;
	}

	/**
	* 设置字段IP3的值，该字段的<br>
	* 字段名称 :IP3<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setIP3(String iP3) {
		this.IP3 = iP3;
    }

	/**
	* 获取字段IP4的值，该字段的<br>
	* 字段名称 :IP4<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIP4() {
		return IP4;
	}

	/**
	* 设置字段IP4的值，该字段的<br>
	* 字段名称 :IP4<br>
	* 数据类型 :varchar(30)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIP4(String iP4) {
		this.IP4 = iP4;
    }

	/**
	* 获取字段Address的值，该字段的<br>
	* 字段名称 :Address<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getAddress() {
		return Address;
	}

	/**
	* 设置字段Address的值，该字段的<br>
	* 字段名称 :Address<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setAddress(String address) {
		this.Address = address;
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
	* 获取字段DistrictCode的值，该字段的<br>
	* 字段名称 :DistrictCode<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getDistrictCode() {
		return DistrictCode;
	}

	/**
	* 设置字段DistrictCode的值，该字段的<br>
	* 字段名称 :DistrictCode<br>
	* 数据类型 :varchar(10)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDistrictCode(String districtCode) {
		this.DistrictCode = districtCode;
    }

}