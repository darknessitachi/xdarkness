package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCVoteItem备份<br>
 * 表代码：BZCVoteItem<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZCVoteItemSchema extends Schema {
	private Long ID;

	private Long SubjectID;

	private Long VoteID;

	private String Item;

	private Long Score;

	private String ItemType;

	private Long VoteDocID;

	private Long OrderFlag;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("SubjectID", DataColumn.LONG, 1, 20 , 0 , true , false),
		new SchemaColumn("VoteID", DataColumn.LONG, 2, 20 , 0 , true , false),
		new SchemaColumn("Item", DataColumn.STRING, 3, 100 , 0 , true , false),
		new SchemaColumn("Score", DataColumn.LONG, 4, 20 , 0 , true , false),
		new SchemaColumn("ItemType", DataColumn.STRING, 5, 1 , 0 , true , false),
		new SchemaColumn("VoteDocID", DataColumn.LONG, 6, 20 , 0 , false , false),
		new SchemaColumn("OrderFlag", DataColumn.LONG, 7, 20 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 8, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 9, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 10, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 11, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCVoteItem";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into BZCVoteItem values(?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCVoteItem set ID=?,SubjectID=?,VoteID=?,Item=?,Score=?,ItemType=?,VoteDocID=?,OrderFlag=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCVoteItem  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCVoteItem  where ID=? and BackupNo=?";

	public BZCVoteItemSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[12];
	}

	protected Schema newInstance(){
		return new BZCVoteItemSchema();
	}

	protected SchemaSet newSet(){
		return new BZCVoteItemSet();
	}

	public BZCVoteItemSet query() {
		return query(null, -1, -1);
	}

	public BZCVoteItemSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCVoteItemSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCVoteItemSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCVoteItemSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SubjectID = null;}else{SubjectID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){VoteID = null;}else{VoteID = new Long(v.toString());}return;}
		if (i == 3){Item = (String)v;return;}
		if (i == 4){if(v==null){Score = null;}else{Score = new Long(v.toString());}return;}
		if (i == 5){ItemType = (String)v;return;}
		if (i == 6){if(v==null){VoteDocID = null;}else{VoteDocID = new Long(v.toString());}return;}
		if (i == 7){if(v==null){OrderFlag = null;}else{OrderFlag = new Long(v.toString());}return;}
		if (i == 8){BackupNo = (String)v;return;}
		if (i == 9){BackupOperator = (String)v;return;}
		if (i == 10){BackupTime = (Date)v;return;}
		if (i == 11){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SubjectID;}
		if (i == 2){return VoteID;}
		if (i == 3){return Item;}
		if (i == 4){return Score;}
		if (i == 5){return ItemType;}
		if (i == 6){return VoteDocID;}
		if (i == 7){return OrderFlag;}
		if (i == 8){return BackupNo;}
		if (i == 9){return BackupOperator;}
		if (i == 10){return BackupTime;}
		if (i == 11){return BackupMemo;}
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
	* 获取字段SubjectID的值，该字段的<br>
	* 字段名称 :SubjectID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getSubjectID() {
		if(SubjectID==null){return 0;}
		return SubjectID.longValue();
	}

	/**
	* 设置字段SubjectID的值，该字段的<br>
	* 字段名称 :SubjectID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSubjectID(long subjectID) {
		this.SubjectID = new Long(subjectID);
    }

	/**
	* 设置字段SubjectID的值，该字段的<br>
	* 字段名称 :SubjectID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSubjectID(String subjectID) {
		if (subjectID == null){
			this.SubjectID = null;
			return;
		}
		this.SubjectID = new Long(subjectID);
    }

	/**
	* 获取字段VoteID的值，该字段的<br>
	* 字段名称 :VoteID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getVoteID() {
		if(VoteID==null){return 0;}
		return VoteID.longValue();
	}

	/**
	* 设置字段VoteID的值，该字段的<br>
	* 字段名称 :VoteID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setVoteID(long voteID) {
		this.VoteID = new Long(voteID);
    }

	/**
	* 设置字段VoteID的值，该字段的<br>
	* 字段名称 :VoteID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setVoteID(String voteID) {
		if (voteID == null){
			this.VoteID = null;
			return;
		}
		this.VoteID = new Long(voteID);
    }

	/**
	* 获取字段Item的值，该字段的<br>
	* 字段名称 :Item<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getItem() {
		return Item;
	}

	/**
	* 设置字段Item的值，该字段的<br>
	* 字段名称 :Item<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setItem(String item) {
		this.Item = item;
    }

	/**
	* 获取字段Score的值，该字段的<br>
	* 字段名称 :Score<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getScore() {
		if(Score==null){return 0;}
		return Score.longValue();
	}

	/**
	* 设置字段Score的值，该字段的<br>
	* 字段名称 :Score<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setScore(long score) {
		this.Score = new Long(score);
    }

	/**
	* 设置字段Score的值，该字段的<br>
	* 字段名称 :Score<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setScore(String score) {
		if (score == null){
			this.Score = null;
			return;
		}
		this.Score = new Long(score);
    }

	/**
	* 获取字段ItemType的值，该字段的<br>
	* 字段名称 :ItemType<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getItemType() {
		return ItemType;
	}

	/**
	* 设置字段ItemType的值，该字段的<br>
	* 字段名称 :ItemType<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setItemType(String itemType) {
		this.ItemType = itemType;
    }

	/**
	* 获取字段VoteDocID的值，该字段的<br>
	* 字段名称 :VoteDocID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getVoteDocID() {
		if(VoteDocID==null){return 0;}
		return VoteDocID.longValue();
	}

	/**
	* 设置字段VoteDocID的值，该字段的<br>
	* 字段名称 :VoteDocID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setVoteDocID(long voteDocID) {
		this.VoteDocID = new Long(voteDocID);
    }

	/**
	* 设置字段VoteDocID的值，该字段的<br>
	* 字段名称 :VoteDocID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setVoteDocID(String voteDocID) {
		if (voteDocID == null){
			this.VoteDocID = null;
			return;
		}
		this.VoteDocID = new Long(voteDocID);
    }

	/**
	* 获取字段OrderFlag的值，该字段的<br>
	* 字段名称 :OrderFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
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
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(long orderFlag) {
		this.OrderFlag = new Long(orderFlag);
    }

	/**
	* 设置字段OrderFlag的值，该字段的<br>
	* 字段名称 :OrderFlag<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOrderFlag(String orderFlag) {
		if (orderFlag == null){
			this.OrderFlag = null;
			return;
		}
		this.OrderFlag = new Long(orderFlag);
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