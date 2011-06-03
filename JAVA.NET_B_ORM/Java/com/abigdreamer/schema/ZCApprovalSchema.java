package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCApproval<br>
 * 表代码：ZCApproval<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID<br>
 */
public class ZCApprovalSchema extends Schema {
	private Long ID;

	private String ApproveUser;

	private Long ArticleID;

	private String Memo;

	private Long Status;

	private Date ApprovalDate;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("ApproveUser", DataColumn.STRING, 1, 200 , 0 , true , false),
		new SchemaColumn("ArticleID", DataColumn.LONG, 2, 20 , 0 , true , false),
		new SchemaColumn("Memo", DataColumn.STRING, 3, 200 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.LONG, 4, 20 , 0 , false , false),
		new SchemaColumn("ApprovalDate", DataColumn.DATETIME, 5, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCApproval";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into ZCApproval values(?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCApproval set ID=?,ApproveUser=?,ArticleID=?,Memo=?,Status=?,ApprovalDate=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCApproval  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCApproval  where ID=?";

	public ZCApprovalSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[6];
	}

	protected Schema newInstance(){
		return new ZCApprovalSchema();
	}

	protected SchemaSet newSet(){
		return new ZCApprovalSet();
	}

	public ZCApprovalSet query() {
		return query(null, -1, -1);
	}

	public ZCApprovalSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCApprovalSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCApprovalSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCApprovalSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){ApproveUser = (String)v;return;}
		if (i == 2){if(v==null){ArticleID = null;}else{ArticleID = new Long(v.toString());}return;}
		if (i == 3){Memo = (String)v;return;}
		if (i == 4){if(v==null){Status = null;}else{Status = new Long(v.toString());}return;}
		if (i == 5){ApprovalDate = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return ApproveUser;}
		if (i == 2){return ArticleID;}
		if (i == 3){return Memo;}
		if (i == 4){return Status;}
		if (i == 5){return ApprovalDate;}
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
	* 获取字段ApproveUser的值，该字段的<br>
	* 字段名称 :ApproveUser<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public String getApproveUser() {
		return ApproveUser;
	}

	/**
	* 设置字段ApproveUser的值，该字段的<br>
	* 字段名称 :ApproveUser<br>
	* 数据类型 :varchar(200)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setApproveUser(String approveUser) {
		this.ApproveUser = approveUser;
    }

	/**
	* 获取字段ArticleID的值，该字段的<br>
	* 字段名称 :ArticleID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getArticleID() {
		if(ArticleID==null){return 0;}
		return ArticleID.longValue();
	}

	/**
	* 设置字段ArticleID的值，该字段的<br>
	* 字段名称 :ArticleID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setArticleID(long articleID) {
		this.ArticleID = new Long(articleID);
    }

	/**
	* 设置字段ArticleID的值，该字段的<br>
	* 字段名称 :ArticleID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setArticleID(String articleID) {
		if (articleID == null){
			this.ArticleID = null;
			return;
		}
		this.ArticleID = new Long(articleID);
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
	* 获取字段Status的值，该字段的<br>
	* 字段名称 :Status<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getStatus() {
		if(Status==null){return 0;}
		return Status.longValue();
	}

	/**
	* 设置字段Status的值，该字段的<br>
	* 字段名称 :Status<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStatus(long status) {
		this.Status = new Long(status);
    }

	/**
	* 设置字段Status的值，该字段的<br>
	* 字段名称 :Status<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setStatus(String status) {
		if (status == null){
			this.Status = null;
			return;
		}
		this.Status = new Long(status);
    }

	/**
	* 获取字段ApprovalDate的值，该字段的<br>
	* 字段名称 :ApprovalDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public Date getApprovalDate() {
		return ApprovalDate;
	}

	/**
	* 设置字段ApprovalDate的值，该字段的<br>
	* 字段名称 :ApprovalDate<br>
	* 数据类型 :datetime<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setApprovalDate(Date approvalDate) {
		this.ApprovalDate = approvalDate;
    }

}