package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZDIP备份<br>
 * 表代码：BZDIP<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：IP1, IP2, BackupNo<br>
 */
public class BZDIPSchema extends Schema {
	private Long IP1;

	private Long IP2;

	private String IP3;

	private String IP4;

	private String Address;

	private String Memo;

	private String DistrictCode;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("IP1", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("IP2", DataColumn.LONG, 1, 20 , 0 , true , true),
		new SchemaColumn("IP3", DataColumn.STRING, 2, 30 , 0 , true , false),
		new SchemaColumn("IP4", DataColumn.STRING, 3, 30 , 0 , false , false),
		new SchemaColumn("Address", DataColumn.STRING, 4, 200 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 5, 200 , 0 , false , false),
		new SchemaColumn("DistrictCode", DataColumn.STRING, 6, 10 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 7, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 8, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 9, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 10, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZDIP";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into BZDIP values(?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZDIP set IP1=?,IP2=?,IP3=?,IP4=?,Address=?,Memo=?,DistrictCode=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where IP1=? and IP2=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZDIP  where IP1=? and IP2=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZDIP  where IP1=? and IP2=? and BackupNo=?";

	public BZDIPSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[11];
	}

	protected Schema newInstance(){
		return new BZDIPSchema();
	}

	protected SchemaSet newSet(){
		return new BZDIPSet();
	}

	public BZDIPSet query() {
		return query(null, -1, -1);
	}

	public BZDIPSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZDIPSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZDIPSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZDIPSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){IP1 = null;}else{IP1 = new Long(v.toString());}return;}
		if (i == 1){if(v==null){IP2 = null;}else{IP2 = new Long(v.toString());}return;}
		if (i == 2){IP3 = (String)v;return;}
		if (i == 3){IP4 = (String)v;return;}
		if (i == 4){Address = (String)v;return;}
		if (i == 5){Memo = (String)v;return;}
		if (i == 6){DistrictCode = (String)v;return;}
		if (i == 7){BackupNo = (String)v;return;}
		if (i == 8){BackupOperator = (String)v;return;}
		if (i == 9){BackupTime = (Date)v;return;}
		if (i == 10){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return IP1;}
		if (i == 1){return IP2;}
		if (i == 2){return IP3;}
		if (i == 3){return IP4;}
		if (i == 4){return Address;}
		if (i == 5){return Memo;}
		if (i == 6){return DistrictCode;}
		if (i == 7){return BackupNo;}
		if (i == 8){return BackupOperator;}
		if (i == 9){return BackupTime;}
		if (i == 10){return BackupMemo;}
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