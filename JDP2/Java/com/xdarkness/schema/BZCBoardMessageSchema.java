package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCBoardMessage备份<br>
 * 表代码：BZCBoardMessage<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZCBoardMessageSchema extends Schema {
	private Long ID;

	private Long BoardID;

	private String Title;

	private String Content;

	private String PublishFlag;

	private String ReplyFlag;

	private String ReplyContent;

	private String EMail;

	private String QQ;

	private String Prop1;

	private String Prop2;

	private String Prop4;

	private String Prop3;

	private String IP;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	private String BackupNo;

	private String BackupOperator;

	private Date BackupTime;

	private String BackupMemo;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("BoardID", DataColumn.LONG, 1, 20 , 0 , true , false),
		new SchemaColumn("Title", DataColumn.STRING, 2, 100 , 0 , true , false),
		new SchemaColumn("Content", DataColumn.CLOB, 3, 0 , 0 , true , false),
		new SchemaColumn("PublishFlag", DataColumn.STRING, 4, 2 , 0 , true , false),
		new SchemaColumn("ReplyFlag", DataColumn.STRING, 5, 2 , 0 , true , false),
		new SchemaColumn("ReplyContent", DataColumn.STRING, 6, 1000 , 0 , false , false),
		new SchemaColumn("EMail", DataColumn.STRING, 7, 100 , 0 , false , false),
		new SchemaColumn("QQ", DataColumn.STRING, 8, 20 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 9, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 10, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 11, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 12, 50 , 0 , false , false),
		new SchemaColumn("IP", DataColumn.STRING, 13, 20 , 0 , true , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 14, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 15, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 16, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 17, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 18, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 19, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 20, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 21, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCBoardMessage";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into BZCBoardMessage values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCBoardMessage set ID=?,BoardID=?,Title=?,Content=?,PublishFlag=?,ReplyFlag=?,ReplyContent=?,EMail=?,QQ=?,Prop1=?,Prop2=?,Prop4=?,Prop3=?,IP=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCBoardMessage  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCBoardMessage  where ID=? and BackupNo=?";

	public BZCBoardMessageSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[22];
	}

	protected Schema newInstance(){
		return new BZCBoardMessageSchema();
	}

	protected SchemaSet newSet(){
		return new BZCBoardMessageSet();
	}

	public BZCBoardMessageSet query() {
		return query(null, -1, -1);
	}

	public BZCBoardMessageSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCBoardMessageSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCBoardMessageSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCBoardMessageSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){BoardID = null;}else{BoardID = new Long(v.toString());}return;}
		if (i == 2){Title = (String)v;return;}
		if (i == 3){Content = (String)v;return;}
		if (i == 4){PublishFlag = (String)v;return;}
		if (i == 5){ReplyFlag = (String)v;return;}
		if (i == 6){ReplyContent = (String)v;return;}
		if (i == 7){EMail = (String)v;return;}
		if (i == 8){QQ = (String)v;return;}
		if (i == 9){Prop1 = (String)v;return;}
		if (i == 10){Prop2 = (String)v;return;}
		if (i == 11){Prop4 = (String)v;return;}
		if (i == 12){Prop3 = (String)v;return;}
		if (i == 13){IP = (String)v;return;}
		if (i == 14){AddUser = (String)v;return;}
		if (i == 15){AddTime = (Date)v;return;}
		if (i == 16){ModifyUser = (String)v;return;}
		if (i == 17){ModifyTime = (Date)v;return;}
		if (i == 18){BackupNo = (String)v;return;}
		if (i == 19){BackupOperator = (String)v;return;}
		if (i == 20){BackupTime = (Date)v;return;}
		if (i == 21){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return BoardID;}
		if (i == 2){return Title;}
		if (i == 3){return Content;}
		if (i == 4){return PublishFlag;}
		if (i == 5){return ReplyFlag;}
		if (i == 6){return ReplyContent;}
		if (i == 7){return EMail;}
		if (i == 8){return QQ;}
		if (i == 9){return Prop1;}
		if (i == 10){return Prop2;}
		if (i == 11){return Prop4;}
		if (i == 12){return Prop3;}
		if (i == 13){return IP;}
		if (i == 14){return AddUser;}
		if (i == 15){return AddTime;}
		if (i == 16){return ModifyUser;}
		if (i == 17){return ModifyTime;}
		if (i == 18){return BackupNo;}
		if (i == 19){return BackupOperator;}
		if (i == 20){return BackupTime;}
		if (i == 21){return BackupMemo;}
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
	* 获取字段BoardID的值，该字段的<br>
	* 字段名称 :BoardID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getBoardID() {
		if(BoardID==null){return 0;}
		return BoardID.longValue();
	}

	/**
	* 设置字段BoardID的值，该字段的<br>
	* 字段名称 :BoardID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBoardID(long boardID) {
		this.BoardID = new Long(boardID);
    }

	/**
	* 设置字段BoardID的值，该字段的<br>
	* 字段名称 :BoardID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setBoardID(String boardID) {
		if (boardID == null){
			this.BoardID = null;
			return;
		}
		this.BoardID = new Long(boardID);
    }

	/**
	* 获取字段Title的值，该字段的<br>
	* 字段名称 :Title<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getTitle() {
		return Title;
	}

	/**
	* 设置字段Title的值，该字段的<br>
	* 字段名称 :Title<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setTitle(String title) {
		this.Title = title;
    }

	/**
	* 获取字段Content的值，该字段的<br>
	* 字段名称 :Content<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getContent() {
		return Content;
	}

	/**
	* 设置字段Content的值，该字段的<br>
	* 字段名称 :Content<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setContent(String content) {
		this.Content = content;
    }

	/**
	* 获取字段PublishFlag的值，该字段的<br>
	* 字段名称 :PublishFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getPublishFlag() {
		return PublishFlag;
	}

	/**
	* 设置字段PublishFlag的值，该字段的<br>
	* 字段名称 :PublishFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setPublishFlag(String publishFlag) {
		this.PublishFlag = publishFlag;
    }

	/**
	* 获取字段ReplyFlag的值，该字段的<br>
	* 字段名称 :ReplyFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getReplyFlag() {
		return ReplyFlag;
	}

	/**
	* 设置字段ReplyFlag的值，该字段的<br>
	* 字段名称 :ReplyFlag<br>
	* 数据类型 :varchar(2)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setReplyFlag(String replyFlag) {
		this.ReplyFlag = replyFlag;
    }

	/**
	* 获取字段ReplyContent的值，该字段的<br>
	* 字段名称 :ReplyContent<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getReplyContent() {
		return ReplyContent;
	}

	/**
	* 设置字段ReplyContent的值，该字段的<br>
	* 字段名称 :ReplyContent<br>
	* 数据类型 :varchar(1000)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReplyContent(String replyContent) {
		this.ReplyContent = replyContent;
    }

	/**
	* 获取字段EMail的值，该字段的<br>
	* 字段名称 :EMail<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getEMail() {
		return EMail;
	}

	/**
	* 设置字段EMail的值，该字段的<br>
	* 字段名称 :EMail<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEMail(String eMail) {
		this.EMail = eMail;
    }

	/**
	* 获取字段QQ的值，该字段的<br>
	* 字段名称 :QQ<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getQQ() {
		return QQ;
	}

	/**
	* 设置字段QQ的值，该字段的<br>
	* 字段名称 :QQ<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setQQ(String qQ) {
		this.QQ = qQ;
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
	* 获取字段IP的值，该字段的<br>
	* 字段名称 :IP<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getIP() {
		return IP;
	}

	/**
	* 设置字段IP的值，该字段的<br>
	* 字段名称 :IP<br>
	* 数据类型 :varchar(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setIP(String iP) {
		this.IP = iP;
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