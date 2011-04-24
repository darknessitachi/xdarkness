package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCQuestion<br>
 * 表代码：ZCQuestion<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID<br>
 */
public class ZCQuestionSchema extends Schema {
	private Long ID;

	private String QuestionInnerCode;

	private String Title;

	private String Content;

	private Integer ReplyCount;

	private String Status;

	private String IsCommend;

	private Date EndTime;

	private String Memo;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private Date AddTime;

	private String AddUser;

	private Date ModifyTime;

	private String ModifyUser;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("QuestionInnerCode", DataColumn.STRING, 1, 100 , 0 , false , false),
		new SchemaColumn("Title", DataColumn.STRING, 2, 250 , 0 , false , false),
		new SchemaColumn("Content", DataColumn.CLOB, 3, 0 , 0 , false , false),
		new SchemaColumn("ReplyCount", DataColumn.INTEGER, 4, 11 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.STRING, 5, 1 , 0 , false , false),
		new SchemaColumn("IsCommend", DataColumn.STRING, 6, 1 , 0 , false , false),
		new SchemaColumn("EndTime", DataColumn.DATETIME, 7, 0 , 0 , false , false),
		new SchemaColumn("Memo", DataColumn.STRING, 8, 100 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 10, 100 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 11, 100 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 12, 100 , 0 , false , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 13, 0 , 0 , true , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 14, 100 , 0 , true , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 15, 0 , 0 , false , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 16, 100 , 0 , false , false)
	};

	public static final String _TableCode = "ZCQuestion";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into ZCQuestion values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCQuestion set ID=?,QuestionInnerCode=?,Title=?,Content=?,ReplyCount=?,Status=?,IsCommend=?,EndTime=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddTime=?,AddUser=?,ModifyTime=?,ModifyUser=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCQuestion  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCQuestion  where ID=?";

	public ZCQuestionSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[17];
	}

	protected Schema newInstance(){
		return new ZCQuestionSchema();
	}

	protected SchemaSet newSet(){
		return new ZCQuestionSet();
	}

	public ZCQuestionSet query() {
		return query(null, -1, -1);
	}

	public ZCQuestionSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCQuestionSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCQuestionSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCQuestionSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){QuestionInnerCode = (String)v;return;}
		if (i == 2){Title = (String)v;return;}
		if (i == 3){Content = (String)v;return;}
		if (i == 4){if(v==null){ReplyCount = null;}else{ReplyCount = new Integer(v.toString());}return;}
		if (i == 5){Status = (String)v;return;}
		if (i == 6){IsCommend = (String)v;return;}
		if (i == 7){EndTime = (Date)v;return;}
		if (i == 8){Memo = (String)v;return;}
		if (i == 9){Prop1 = (String)v;return;}
		if (i == 10){Prop2 = (String)v;return;}
		if (i == 11){Prop3 = (String)v;return;}
		if (i == 12){Prop4 = (String)v;return;}
		if (i == 13){AddTime = (Date)v;return;}
		if (i == 14){AddUser = (String)v;return;}
		if (i == 15){ModifyTime = (Date)v;return;}
		if (i == 16){ModifyUser = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return QuestionInnerCode;}
		if (i == 2){return Title;}
		if (i == 3){return Content;}
		if (i == 4){return ReplyCount;}
		if (i == 5){return Status;}
		if (i == 6){return IsCommend;}
		if (i == 7){return EndTime;}
		if (i == 8){return Memo;}
		if (i == 9){return Prop1;}
		if (i == 10){return Prop2;}
		if (i == 11){return Prop3;}
		if (i == 12){return Prop4;}
		if (i == 13){return AddTime;}
		if (i == 14){return AddUser;}
		if (i == 15){return ModifyTime;}
		if (i == 16){return ModifyUser;}
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
	* 获取字段QuestionInnerCode的值，该字段的<br>
	* 字段名称 :QuestionInnerCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getQuestionInnerCode() {
		return QuestionInnerCode;
	}

	/**
	* 设置字段QuestionInnerCode的值，该字段的<br>
	* 字段名称 :QuestionInnerCode<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setQuestionInnerCode(String questionInnerCode) {
		this.QuestionInnerCode = questionInnerCode;
    }

	/**
	* 获取字段Title的值，该字段的<br>
	* 字段名称 :Title<br>
	* 数据类型 :varchar(250)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTitle() {
		return Title;
	}

	/**
	* 设置字段Title的值，该字段的<br>
	* 字段名称 :Title<br>
	* 数据类型 :varchar(250)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTitle(String title) {
		this.Title = title;
    }

	/**
	* 获取字段Content的值，该字段的<br>
	* 字段名称 :Content<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getContent() {
		return Content;
	}

	/**
	* 设置字段Content的值，该字段的<br>
	* 字段名称 :Content<br>
	* 数据类型 :mediumtext<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setContent(String content) {
		this.Content = content;
    }

	/**
	* 获取字段ReplyCount的值，该字段的<br>
	* 字段名称 :ReplyCount<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public int getReplyCount() {
		if(ReplyCount==null){return 0;}
		return ReplyCount.intValue();
	}

	/**
	* 设置字段ReplyCount的值，该字段的<br>
	* 字段名称 :ReplyCount<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReplyCount(int replyCount) {
		this.ReplyCount = new Integer(replyCount);
    }

	/**
	* 设置字段ReplyCount的值，该字段的<br>
	* 字段名称 :ReplyCount<br>
	* 数据类型 :int(11)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setReplyCount(String replyCount) {
		if (replyCount == null){
			this.ReplyCount = null;
			return;
		}
		this.ReplyCount = new Integer(replyCount);
    }

	/**
	* 获取字段Status的值，该字段的<br>
	* 字段名称 :Status<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getStatus() {
		return Status;
	}

	/**
	* 设置字段Status的值，该字段的<br>
	* 字段名称 :Status<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStatus(String status) {
		this.Status = status;
    }

	/**
	* 获取字段IsCommend的值，该字段的<br>
	* 字段名称 :IsCommend<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getIsCommend() {
		return IsCommend;
	}

	/**
	* 设置字段IsCommend的值，该字段的<br>
	* 字段名称 :IsCommend<br>
	* 数据类型 :varchar(1)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setIsCommend(String isCommend) {
		this.IsCommend = isCommend;
    }

	/**
	* 获取字段EndTime的值，该字段的<br>
	* 字段名称 :EndTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getEndTime() {
		return EndTime;
	}

	/**
	* 设置字段EndTime的值，该字段的<br>
	* 字段名称 :EndTime<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setEndTime(Date endTime) {
		this.EndTime = endTime;
    }

	/**
	* 获取字段Memo的值，该字段的<br>
	* 字段名称 :Memo<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMemo() {
		return Memo;
	}

	/**
	* 设置字段Memo的值，该字段的<br>
	* 字段名称 :Memo<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMemo(String memo) {
		this.Memo = memo;
    }

	/**
	* 获取字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return Prop1;
	}

	/**
	* 设置字段Prop1的值，该字段的<br>
	* 字段名称 :Prop1<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.Prop1 = prop1;
    }

	/**
	* 获取字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return Prop2;
	}

	/**
	* 设置字段Prop2的值，该字段的<br>
	* 字段名称 :Prop2<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.Prop2 = prop2;
    }

	/**
	* 获取字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return Prop3;
	}

	/**
	* 设置字段Prop3的值，该字段的<br>
	* 字段名称 :Prop3<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.Prop3 = prop3;
    }

	/**
	* 获取字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return Prop4;
	}

	/**
	* 设置字段Prop4的值，该字段的<br>
	* 字段名称 :Prop4<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.Prop4 = prop4;
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
	* 获取字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getAddUser() {
		return AddUser;
	}

	/**
	* 设置字段AddUser的值，该字段的<br>
	* 字段名称 :AddUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setAddUser(String addUser) {
		this.AddUser = addUser;
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
	* 获取字段ModifyUser的值，该字段的<br>
	* 字段名称 :ModifyUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getModifyUser() {
		return ModifyUser;
	}

	/**
	* 设置字段ModifyUser的值，该字段的<br>
	* 字段名称 :ModifyUser<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setModifyUser(String modifyUser) {
		this.ModifyUser = modifyUser;
    }

}