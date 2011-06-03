package com.abigdreamer.schema;

import com.abigdreamer.java.net.orm.Schema;
import com.abigdreamer.java.net.orm.SchemaColumn;
import com.abigdreamer.java.net.orm.SchemaSet;
import com.abigdreamer.java.net.orm.data.DataColumn;
import com.abigdreamer.java.net.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCForumScore备份<br>
 * 表代码：BZCForumScore<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID, BackupNo<br>
 */
public class BZCForumScoreSchema extends Schema {
	private Long ID;

	private Long SiteID;

	private Long InitScore;

	private Long PublishTheme;

	private Long DeleteTheme;

	private Long PublishPost;

	private Long DeletePost;

	private Long Best;

	private Long CancelBest;

	private Long Bright;

	private Long CancelBright;

	private Long TopTheme;

	private Long CancelTop;

	private Long UpTheme;

	private Long DownTheme;

	private Long Upload;

	private Long Download;

	private Long Search;

	private Long Vote;

	private String prop1;

	private String prop2;

	private String prop3;

	private String prop4;

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
		new SchemaColumn("SiteID", DataColumn.LONG, 1, 20 , 0 , false , false),
		new SchemaColumn("InitScore", DataColumn.LONG, 2, 20 , 0 , false , false),
		new SchemaColumn("PublishTheme", DataColumn.LONG, 3, 20 , 0 , false , false),
		new SchemaColumn("DeleteTheme", DataColumn.LONG, 4, 20 , 0 , false , false),
		new SchemaColumn("PublishPost", DataColumn.LONG, 5, 20 , 0 , false , false),
		new SchemaColumn("DeletePost", DataColumn.LONG, 6, 20 , 0 , false , false),
		new SchemaColumn("Best", DataColumn.LONG, 7, 20 , 0 , false , false),
		new SchemaColumn("CancelBest", DataColumn.LONG, 8, 20 , 0 , false , false),
		new SchemaColumn("Bright", DataColumn.LONG, 9, 20 , 0 , false , false),
		new SchemaColumn("CancelBright", DataColumn.LONG, 10, 20 , 0 , false , false),
		new SchemaColumn("TopTheme", DataColumn.LONG, 11, 20 , 0 , false , false),
		new SchemaColumn("CancelTop", DataColumn.LONG, 12, 20 , 0 , false , false),
		new SchemaColumn("UpTheme", DataColumn.LONG, 13, 20 , 0 , false , false),
		new SchemaColumn("DownTheme", DataColumn.LONG, 14, 20 , 0 , false , false),
		new SchemaColumn("Upload", DataColumn.LONG, 15, 20 , 0 , false , false),
		new SchemaColumn("Download", DataColumn.LONG, 16, 20 , 0 , false , false),
		new SchemaColumn("Search", DataColumn.LONG, 17, 20 , 0 , false , false),
		new SchemaColumn("Vote", DataColumn.LONG, 18, 20 , 0 , false , false),
		new SchemaColumn("prop1", DataColumn.STRING, 19, 50 , 0 , false , false),
		new SchemaColumn("prop2", DataColumn.STRING, 20, 50 , 0 , false , false),
		new SchemaColumn("prop3", DataColumn.STRING, 21, 50 , 0 , false , false),
		new SchemaColumn("prop4", DataColumn.STRING, 22, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 23, 100 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 24, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 25, 100 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 26, 0 , 0 , false , false),
		new SchemaColumn("BackupNo", DataColumn.STRING, 27, 15 , 0 , true , true),
		new SchemaColumn("BackupOperator", DataColumn.STRING, 28, 200 , 0 , true , false),
		new SchemaColumn("BackupTime", DataColumn.DATETIME, 29, 0 , 0 , true , false),
		new SchemaColumn("BackupMemo", DataColumn.STRING, 30, 50 , 0 , false , false)
	};

	public static final String _TableCode = "BZCForumScore";

	public static final String _NameSpace = "com.abigdreamer.schema";

	protected static final String _InsertAllSQL = "insert into BZCForumScore values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update BZCForumScore set ID=?,SiteID=?,InitScore=?,PublishTheme=?,DeleteTheme=?,PublishPost=?,DeletePost=?,Best=?,CancelBest=?,Bright=?,CancelBright=?,TopTheme=?,CancelTop=?,UpTheme=?,DownTheme=?,Upload=?,Download=?,Search=?,Vote=?,prop1=?,prop2=?,prop3=?,prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";

	protected static final String _DeleteSQL = "delete from BZCForumScore  where ID=? and BackupNo=?";

	protected static final String _FillAllSQL = "select * from BZCForumScore  where ID=? and BackupNo=?";

	public BZCForumScoreSchema(){
		TableCode = _TableCode;
		NameSpace = _NameSpace;
		Columns = _Columns;
		InsertAllSQL = _InsertAllSQL;
		UpdateAllSQL = _UpdateAllSQL;
		DeleteSQL = _DeleteSQL;
		FillAllSQL = _FillAllSQL;
		HasSetFlag = new boolean[31];
	}

	protected Schema newInstance(){
		return new BZCForumScoreSchema();
	}

	protected SchemaSet newSet(){
		return new BZCForumScoreSet();
	}

	public BZCForumScoreSet query() {
		return query(null, -1, -1);
	}

	public BZCForumScoreSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public BZCForumScoreSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public BZCForumScoreSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (BZCForumScoreSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){InitScore = null;}else{InitScore = new Long(v.toString());}return;}
		if (i == 3){if(v==null){PublishTheme = null;}else{PublishTheme = new Long(v.toString());}return;}
		if (i == 4){if(v==null){DeleteTheme = null;}else{DeleteTheme = new Long(v.toString());}return;}
		if (i == 5){if(v==null){PublishPost = null;}else{PublishPost = new Long(v.toString());}return;}
		if (i == 6){if(v==null){DeletePost = null;}else{DeletePost = new Long(v.toString());}return;}
		if (i == 7){if(v==null){Best = null;}else{Best = new Long(v.toString());}return;}
		if (i == 8){if(v==null){CancelBest = null;}else{CancelBest = new Long(v.toString());}return;}
		if (i == 9){if(v==null){Bright = null;}else{Bright = new Long(v.toString());}return;}
		if (i == 10){if(v==null){CancelBright = null;}else{CancelBright = new Long(v.toString());}return;}
		if (i == 11){if(v==null){TopTheme = null;}else{TopTheme = new Long(v.toString());}return;}
		if (i == 12){if(v==null){CancelTop = null;}else{CancelTop = new Long(v.toString());}return;}
		if (i == 13){if(v==null){UpTheme = null;}else{UpTheme = new Long(v.toString());}return;}
		if (i == 14){if(v==null){DownTheme = null;}else{DownTheme = new Long(v.toString());}return;}
		if (i == 15){if(v==null){Upload = null;}else{Upload = new Long(v.toString());}return;}
		if (i == 16){if(v==null){Download = null;}else{Download = new Long(v.toString());}return;}
		if (i == 17){if(v==null){Search = null;}else{Search = new Long(v.toString());}return;}
		if (i == 18){if(v==null){Vote = null;}else{Vote = new Long(v.toString());}return;}
		if (i == 19){prop1 = (String)v;return;}
		if (i == 20){prop2 = (String)v;return;}
		if (i == 21){prop3 = (String)v;return;}
		if (i == 22){prop4 = (String)v;return;}
		if (i == 23){AddUser = (String)v;return;}
		if (i == 24){AddTime = (Date)v;return;}
		if (i == 25){ModifyUser = (String)v;return;}
		if (i == 26){ModifyTime = (Date)v;return;}
		if (i == 27){BackupNo = (String)v;return;}
		if (i == 28){BackupOperator = (String)v;return;}
		if (i == 29){BackupTime = (Date)v;return;}
		if (i == 30){BackupMemo = (String)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return SiteID;}
		if (i == 2){return InitScore;}
		if (i == 3){return PublishTheme;}
		if (i == 4){return DeleteTheme;}
		if (i == 5){return PublishPost;}
		if (i == 6){return DeletePost;}
		if (i == 7){return Best;}
		if (i == 8){return CancelBest;}
		if (i == 9){return Bright;}
		if (i == 10){return CancelBright;}
		if (i == 11){return TopTheme;}
		if (i == 12){return CancelTop;}
		if (i == 13){return UpTheme;}
		if (i == 14){return DownTheme;}
		if (i == 15){return Upload;}
		if (i == 16){return Download;}
		if (i == 17){return Search;}
		if (i == 18){return Vote;}
		if (i == 19){return prop1;}
		if (i == 20){return prop2;}
		if (i == 21){return prop3;}
		if (i == 22){return prop4;}
		if (i == 23){return AddUser;}
		if (i == 24){return AddTime;}
		if (i == 25){return ModifyUser;}
		if (i == 26){return ModifyTime;}
		if (i == 27){return BackupNo;}
		if (i == 28){return BackupOperator;}
		if (i == 29){return BackupTime;}
		if (i == 30){return BackupMemo;}
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
	* 获取字段SiteID的值，该字段的<br>
	* 字段名称 :SiteID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getSiteID() {
		if(SiteID==null){return 0;}
		return SiteID.longValue();
	}

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :SiteID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :SiteID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSiteID(String siteID) {
		if (siteID == null){
			this.SiteID = null;
			return;
		}
		this.SiteID = new Long(siteID);
    }

	/**
	* 获取字段InitScore的值，该字段的<br>
	* 字段名称 :InitScore<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getInitScore() {
		if(InitScore==null){return 0;}
		return InitScore.longValue();
	}

	/**
	* 设置字段InitScore的值，该字段的<br>
	* 字段名称 :InitScore<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInitScore(long initScore) {
		this.InitScore = new Long(initScore);
    }

	/**
	* 设置字段InitScore的值，该字段的<br>
	* 字段名称 :InitScore<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setInitScore(String initScore) {
		if (initScore == null){
			this.InitScore = null;
			return;
		}
		this.InitScore = new Long(initScore);
    }

	/**
	* 获取字段PublishTheme的值，该字段的<br>
	* 字段名称 :PublishTheme<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getPublishTheme() {
		if(PublishTheme==null){return 0;}
		return PublishTheme.longValue();
	}

	/**
	* 设置字段PublishTheme的值，该字段的<br>
	* 字段名称 :PublishTheme<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPublishTheme(long publishTheme) {
		this.PublishTheme = new Long(publishTheme);
    }

	/**
	* 设置字段PublishTheme的值，该字段的<br>
	* 字段名称 :PublishTheme<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPublishTheme(String publishTheme) {
		if (publishTheme == null){
			this.PublishTheme = null;
			return;
		}
		this.PublishTheme = new Long(publishTheme);
    }

	/**
	* 获取字段DeleteTheme的值，该字段的<br>
	* 字段名称 :DeleteTheme<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getDeleteTheme() {
		if(DeleteTheme==null){return 0;}
		return DeleteTheme.longValue();
	}

	/**
	* 设置字段DeleteTheme的值，该字段的<br>
	* 字段名称 :DeleteTheme<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDeleteTheme(long deleteTheme) {
		this.DeleteTheme = new Long(deleteTheme);
    }

	/**
	* 设置字段DeleteTheme的值，该字段的<br>
	* 字段名称 :DeleteTheme<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDeleteTheme(String deleteTheme) {
		if (deleteTheme == null){
			this.DeleteTheme = null;
			return;
		}
		this.DeleteTheme = new Long(deleteTheme);
    }

	/**
	* 获取字段PublishPost的值，该字段的<br>
	* 字段名称 :PublishPost<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getPublishPost() {
		if(PublishPost==null){return 0;}
		return PublishPost.longValue();
	}

	/**
	* 设置字段PublishPost的值，该字段的<br>
	* 字段名称 :PublishPost<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPublishPost(long publishPost) {
		this.PublishPost = new Long(publishPost);
    }

	/**
	* 设置字段PublishPost的值，该字段的<br>
	* 字段名称 :PublishPost<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPublishPost(String publishPost) {
		if (publishPost == null){
			this.PublishPost = null;
			return;
		}
		this.PublishPost = new Long(publishPost);
    }

	/**
	* 获取字段DeletePost的值，该字段的<br>
	* 字段名称 :DeletePost<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getDeletePost() {
		if(DeletePost==null){return 0;}
		return DeletePost.longValue();
	}

	/**
	* 设置字段DeletePost的值，该字段的<br>
	* 字段名称 :DeletePost<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDeletePost(long deletePost) {
		this.DeletePost = new Long(deletePost);
    }

	/**
	* 设置字段DeletePost的值，该字段的<br>
	* 字段名称 :DeletePost<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDeletePost(String deletePost) {
		if (deletePost == null){
			this.DeletePost = null;
			return;
		}
		this.DeletePost = new Long(deletePost);
    }

	/**
	* 获取字段Best的值，该字段的<br>
	* 字段名称 :Best<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getBest() {
		if(Best==null){return 0;}
		return Best.longValue();
	}

	/**
	* 设置字段Best的值，该字段的<br>
	* 字段名称 :Best<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBest(long best) {
		this.Best = new Long(best);
    }

	/**
	* 设置字段Best的值，该字段的<br>
	* 字段名称 :Best<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBest(String best) {
		if (best == null){
			this.Best = null;
			return;
		}
		this.Best = new Long(best);
    }

	/**
	* 获取字段CancelBest的值，该字段的<br>
	* 字段名称 :CancelBest<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getCancelBest() {
		if(CancelBest==null){return 0;}
		return CancelBest.longValue();
	}

	/**
	* 设置字段CancelBest的值，该字段的<br>
	* 字段名称 :CancelBest<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCancelBest(long cancelBest) {
		this.CancelBest = new Long(cancelBest);
    }

	/**
	* 设置字段CancelBest的值，该字段的<br>
	* 字段名称 :CancelBest<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCancelBest(String cancelBest) {
		if (cancelBest == null){
			this.CancelBest = null;
			return;
		}
		this.CancelBest = new Long(cancelBest);
    }

	/**
	* 获取字段Bright的值，该字段的<br>
	* 字段名称 :Bright<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getBright() {
		if(Bright==null){return 0;}
		return Bright.longValue();
	}

	/**
	* 设置字段Bright的值，该字段的<br>
	* 字段名称 :Bright<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBright(long bright) {
		this.Bright = new Long(bright);
    }

	/**
	* 设置字段Bright的值，该字段的<br>
	* 字段名称 :Bright<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setBright(String bright) {
		if (bright == null){
			this.Bright = null;
			return;
		}
		this.Bright = new Long(bright);
    }

	/**
	* 获取字段CancelBright的值，该字段的<br>
	* 字段名称 :CancelBright<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getCancelBright() {
		if(CancelBright==null){return 0;}
		return CancelBright.longValue();
	}

	/**
	* 设置字段CancelBright的值，该字段的<br>
	* 字段名称 :CancelBright<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCancelBright(long cancelBright) {
		this.CancelBright = new Long(cancelBright);
    }

	/**
	* 设置字段CancelBright的值，该字段的<br>
	* 字段名称 :CancelBright<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCancelBright(String cancelBright) {
		if (cancelBright == null){
			this.CancelBright = null;
			return;
		}
		this.CancelBright = new Long(cancelBright);
    }

	/**
	* 获取字段TopTheme的值，该字段的<br>
	* 字段名称 :TopTheme<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getTopTheme() {
		if(TopTheme==null){return 0;}
		return TopTheme.longValue();
	}

	/**
	* 设置字段TopTheme的值，该字段的<br>
	* 字段名称 :TopTheme<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTopTheme(long topTheme) {
		this.TopTheme = new Long(topTheme);
    }

	/**
	* 设置字段TopTheme的值，该字段的<br>
	* 字段名称 :TopTheme<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTopTheme(String topTheme) {
		if (topTheme == null){
			this.TopTheme = null;
			return;
		}
		this.TopTheme = new Long(topTheme);
    }

	/**
	* 获取字段CancelTop的值，该字段的<br>
	* 字段名称 :CancelTop<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getCancelTop() {
		if(CancelTop==null){return 0;}
		return CancelTop.longValue();
	}

	/**
	* 设置字段CancelTop的值，该字段的<br>
	* 字段名称 :CancelTop<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCancelTop(long cancelTop) {
		this.CancelTop = new Long(cancelTop);
    }

	/**
	* 设置字段CancelTop的值，该字段的<br>
	* 字段名称 :CancelTop<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setCancelTop(String cancelTop) {
		if (cancelTop == null){
			this.CancelTop = null;
			return;
		}
		this.CancelTop = new Long(cancelTop);
    }

	/**
	* 获取字段UpTheme的值，该字段的<br>
	* 字段名称 :UpTheme<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getUpTheme() {
		if(UpTheme==null){return 0;}
		return UpTheme.longValue();
	}

	/**
	* 设置字段UpTheme的值，该字段的<br>
	* 字段名称 :UpTheme<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUpTheme(long upTheme) {
		this.UpTheme = new Long(upTheme);
    }

	/**
	* 设置字段UpTheme的值，该字段的<br>
	* 字段名称 :UpTheme<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUpTheme(String upTheme) {
		if (upTheme == null){
			this.UpTheme = null;
			return;
		}
		this.UpTheme = new Long(upTheme);
    }

	/**
	* 获取字段DownTheme的值，该字段的<br>
	* 字段名称 :DownTheme<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getDownTheme() {
		if(DownTheme==null){return 0;}
		return DownTheme.longValue();
	}

	/**
	* 设置字段DownTheme的值，该字段的<br>
	* 字段名称 :DownTheme<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDownTheme(long downTheme) {
		this.DownTheme = new Long(downTheme);
    }

	/**
	* 设置字段DownTheme的值，该字段的<br>
	* 字段名称 :DownTheme<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDownTheme(String downTheme) {
		if (downTheme == null){
			this.DownTheme = null;
			return;
		}
		this.DownTheme = new Long(downTheme);
    }

	/**
	* 获取字段Upload的值，该字段的<br>
	* 字段名称 :Upload<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getUpload() {
		if(Upload==null){return 0;}
		return Upload.longValue();
	}

	/**
	* 设置字段Upload的值，该字段的<br>
	* 字段名称 :Upload<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUpload(long upload) {
		this.Upload = new Long(upload);
    }

	/**
	* 设置字段Upload的值，该字段的<br>
	* 字段名称 :Upload<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUpload(String upload) {
		if (upload == null){
			this.Upload = null;
			return;
		}
		this.Upload = new Long(upload);
    }

	/**
	* 获取字段Download的值，该字段的<br>
	* 字段名称 :Download<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getDownload() {
		if(Download==null){return 0;}
		return Download.longValue();
	}

	/**
	* 设置字段Download的值，该字段的<br>
	* 字段名称 :Download<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDownload(long download) {
		this.Download = new Long(download);
    }

	/**
	* 设置字段Download的值，该字段的<br>
	* 字段名称 :Download<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setDownload(String download) {
		if (download == null){
			this.Download = null;
			return;
		}
		this.Download = new Long(download);
    }

	/**
	* 获取字段Search的值，该字段的<br>
	* 字段名称 :Search<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getSearch() {
		if(Search==null){return 0;}
		return Search.longValue();
	}

	/**
	* 设置字段Search的值，该字段的<br>
	* 字段名称 :Search<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSearch(long search) {
		this.Search = new Long(search);
    }

	/**
	* 设置字段Search的值，该字段的<br>
	* 字段名称 :Search<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSearch(String search) {
		if (search == null){
			this.Search = null;
			return;
		}
		this.Search = new Long(search);
    }

	/**
	* 获取字段Vote的值，该字段的<br>
	* 字段名称 :Vote<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getVote() {
		if(Vote==null){return 0;}
		return Vote.longValue();
	}

	/**
	* 设置字段Vote的值，该字段的<br>
	* 字段名称 :Vote<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setVote(long vote) {
		this.Vote = new Long(vote);
    }

	/**
	* 设置字段Vote的值，该字段的<br>
	* 字段名称 :Vote<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setVote(String vote) {
		if (vote == null){
			this.Vote = null;
			return;
		}
		this.Vote = new Long(vote);
    }

	/**
	* 获取字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp1() {
		return prop1;
	}

	/**
	* 设置字段prop1的值，该字段的<br>
	* 字段名称 :prop1<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp1(String prop1) {
		this.prop1 = prop1;
    }

	/**
	* 获取字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp2() {
		return prop2;
	}

	/**
	* 设置字段prop2的值，该字段的<br>
	* 字段名称 :prop2<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp2(String prop2) {
		this.prop2 = prop2;
    }

	/**
	* 获取字段prop3的值，该字段的<br>
	* 字段名称 :prop3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp3() {
		return prop3;
	}

	/**
	* 设置字段prop3的值，该字段的<br>
	* 字段名称 :prop3<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp3(String prop3) {
		this.prop3 = prop3;
    }

	/**
	* 获取字段prop4的值，该字段的<br>
	* 字段名称 :prop4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getProp4() {
		return prop4;
	}

	/**
	* 设置字段prop4的值，该字段的<br>
	* 字段名称 :prop4<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setProp4(String prop4) {
		this.prop4 = prop4;
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