package com.xdarkness.schema;

import com.xdarkness.framework.orm.Schema;
import com.xdarkness.framework.orm.SchemaColumn;
import com.xdarkness.framework.orm.SchemaSet;
import com.xdarkness.framework.orm.data.DataColumn;
import com.xdarkness.framework.sql.QueryBuilder;
import java.util.Date;

/**
 * 表名称：ZCDeployJob<br>
 * 表代码：ZCDeployJob<br>
 * 表备注：<br>
InnoDB free: 38912 kBInnoDB free: 38912 kB<br>
<br>
 * 表主键：ID<br>
 */
public class ZCDeployJobSchema extends Schema {
	private Long ID;

	private Long ConfigID;

	private Long SiteID;

	private String Source;

	private String Target;

	private String Method;

	private String Operation;

	private String Host;

	private Long Port;

	private String UserName;

	private String Password;

	private Long Status;

	private Long RetryCount;

	private String Prop1;

	private String Prop2;

	private String Prop3;

	private String Prop4;

	private String AddUser;

	private Date AddTime;

	private String ModifyUser;

	private Date ModifyTime;

	public static final SchemaColumn[] _Columns = new SchemaColumn[] {
		new SchemaColumn("ID", DataColumn.LONG, 0, 20 , 0 , true , true),
		new SchemaColumn("ConfigID", DataColumn.LONG, 1, 20 , 0 , true , false),
		new SchemaColumn("SiteID", DataColumn.LONG, 2, 20 , 0 , true , false),
		new SchemaColumn("Source", DataColumn.STRING, 3, 255 , 0 , false , false),
		new SchemaColumn("Target", DataColumn.STRING, 4, 255 , 0 , false , false),
		new SchemaColumn("Method", DataColumn.STRING, 5, 50 , 0 , false , false),
		new SchemaColumn("Operation", DataColumn.STRING, 6, 100 , 0 , false , false),
		new SchemaColumn("Host", DataColumn.STRING, 7, 255 , 0 , false , false),
		new SchemaColumn("Port", DataColumn.LONG, 8, 20 , 0 , false , false),
		new SchemaColumn("UserName", DataColumn.STRING, 9, 100 , 0 , false , false),
		new SchemaColumn("Password", DataColumn.STRING, 10, 100 , 0 , false , false),
		new SchemaColumn("Status", DataColumn.LONG, 11, 20 , 0 , false , false),
		new SchemaColumn("RetryCount", DataColumn.LONG, 12, 20 , 0 , false , false),
		new SchemaColumn("Prop1", DataColumn.STRING, 13, 50 , 0 , false , false),
		new SchemaColumn("Prop2", DataColumn.STRING, 14, 50 , 0 , false , false),
		new SchemaColumn("Prop3", DataColumn.STRING, 15, 50 , 0 , false , false),
		new SchemaColumn("Prop4", DataColumn.STRING, 16, 50 , 0 , false , false),
		new SchemaColumn("AddUser", DataColumn.STRING, 17, 200 , 0 , true , false),
		new SchemaColumn("AddTime", DataColumn.DATETIME, 18, 0 , 0 , true , false),
		new SchemaColumn("ModifyUser", DataColumn.STRING, 19, 200 , 0 , false , false),
		new SchemaColumn("ModifyTime", DataColumn.DATETIME, 20, 0 , 0 , false , false)
	};

	public static final String _TableCode = "ZCDeployJob";

	public static final String _NameSpace = "com.xdarkness.schema";

	protected static final String _InsertAllSQL = "insert into ZCDeployJob values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	protected static final String _UpdateAllSQL = "update ZCDeployJob set ID=?,ConfigID=?,SiteID=?,Source=?,Target=?,Method=?,Operation=?,Host=?,Port=?,UserName=?,Password=?,Status=?,RetryCount=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";

	protected static final String _DeleteSQL = "delete from ZCDeployJob  where ID=?";

	protected static final String _FillAllSQL = "select * from ZCDeployJob  where ID=?";

	public ZCDeployJobSchema(){
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
		return new ZCDeployJobSchema();
	}

	protected SchemaSet newSet(){
		return new ZCDeployJobSet();
	}

	public ZCDeployJobSet query() {
		return query(null, -1, -1);
	}

	public ZCDeployJobSet query(QueryBuilder qb) {
		return query(qb, -1, -1);
	}

	public ZCDeployJobSet query(int pageSize, int pageIndex) {
		return query(null, pageSize, pageIndex);
	}

	public ZCDeployJobSet query(QueryBuilder qb , int pageSize, int pageIndex){
		return (ZCDeployJobSet)querySet(qb , pageSize , pageIndex);
	}

	public void setV(int i, Object v) {
		if (i == 0){if(v==null){ID = null;}else{ID = new Long(v.toString());}return;}
		if (i == 1){if(v==null){ConfigID = null;}else{ConfigID = new Long(v.toString());}return;}
		if (i == 2){if(v==null){SiteID = null;}else{SiteID = new Long(v.toString());}return;}
		if (i == 3){Source = (String)v;return;}
		if (i == 4){Target = (String)v;return;}
		if (i == 5){Method = (String)v;return;}
		if (i == 6){Operation = (String)v;return;}
		if (i == 7){Host = (String)v;return;}
		if (i == 8){if(v==null){Port = null;}else{Port = new Long(v.toString());}return;}
		if (i == 9){UserName = (String)v;return;}
		if (i == 10){Password = (String)v;return;}
		if (i == 11){if(v==null){Status = null;}else{Status = new Long(v.toString());}return;}
		if (i == 12){if(v==null){RetryCount = null;}else{RetryCount = new Long(v.toString());}return;}
		if (i == 13){Prop1 = (String)v;return;}
		if (i == 14){Prop2 = (String)v;return;}
		if (i == 15){Prop3 = (String)v;return;}
		if (i == 16){Prop4 = (String)v;return;}
		if (i == 17){AddUser = (String)v;return;}
		if (i == 18){AddTime = (Date)v;return;}
		if (i == 19){ModifyUser = (String)v;return;}
		if (i == 20){ModifyTime = (Date)v;return;}
	}

	public Object getV(int i) {
		if (i == 0){return ID;}
		if (i == 1){return ConfigID;}
		if (i == 2){return SiteID;}
		if (i == 3){return Source;}
		if (i == 4){return Target;}
		if (i == 5){return Method;}
		if (i == 6){return Operation;}
		if (i == 7){return Host;}
		if (i == 8){return Port;}
		if (i == 9){return UserName;}
		if (i == 10){return Password;}
		if (i == 11){return Status;}
		if (i == 12){return RetryCount;}
		if (i == 13){return Prop1;}
		if (i == 14){return Prop2;}
		if (i == 15){return Prop3;}
		if (i == 16){return Prop4;}
		if (i == 17){return AddUser;}
		if (i == 18){return AddTime;}
		if (i == 19){return ModifyUser;}
		if (i == 20){return ModifyTime;}
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
	* 获取字段ConfigID的值，该字段的<br>
	* 字段名称 :ConfigID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public long getConfigID() {
		if(ConfigID==null){return 0;}
		return ConfigID.longValue();
	}

	/**
	* 设置字段ConfigID的值，该字段的<br>
	* 字段名称 :ConfigID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setConfigID(long configID) {
		this.ConfigID = new Long(configID);
    }

	/**
	* 设置字段ConfigID的值，该字段的<br>
	* 字段名称 :ConfigID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setConfigID(String configID) {
		if (configID == null){
			this.ConfigID = null;
			return;
		}
		this.ConfigID = new Long(configID);
    }

	/**
	* 获取字段SiteID的值，该字段的<br>
	* 字段名称 :SiteID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
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
	* 是否必填 :true<br>
	*/
	public void setSiteID(long siteID) {
		this.SiteID = new Long(siteID);
    }

	/**
	* 设置字段SiteID的值，该字段的<br>
	* 字段名称 :SiteID<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :true<br>
	*/
	public void setSiteID(String siteID) {
		if (siteID == null){
			this.SiteID = null;
			return;
		}
		this.SiteID = new Long(siteID);
    }

	/**
	* 获取字段Source的值，该字段的<br>
	* 字段名称 :Source<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getSource() {
		return Source;
	}

	/**
	* 设置字段Source的值，该字段的<br>
	* 字段名称 :Source<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setSource(String source) {
		this.Source = source;
    }

	/**
	* 获取字段Target的值，该字段的<br>
	* 字段名称 :Target<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getTarget() {
		return Target;
	}

	/**
	* 设置字段Target的值，该字段的<br>
	* 字段名称 :Target<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setTarget(String target) {
		this.Target = target;
    }

	/**
	* 获取字段Method的值，该字段的<br>
	* 字段名称 :Method<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getMethod() {
		return Method;
	}

	/**
	* 设置字段Method的值，该字段的<br>
	* 字段名称 :Method<br>
	* 数据类型 :varchar(50)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setMethod(String method) {
		this.Method = method;
    }

	/**
	* 获取字段Operation的值，该字段的<br>
	* 字段名称 :Operation<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getOperation() {
		return Operation;
	}

	/**
	* 设置字段Operation的值，该字段的<br>
	* 字段名称 :Operation<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setOperation(String operation) {
		this.Operation = operation;
    }

	/**
	* 获取字段Host的值，该字段的<br>
	* 字段名称 :Host<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getHost() {
		return Host;
	}

	/**
	* 设置字段Host的值，该字段的<br>
	* 字段名称 :Host<br>
	* 数据类型 :varchar(255)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setHost(String host) {
		this.Host = host;
    }

	/**
	* 获取字段Port的值，该字段的<br>
	* 字段名称 :Port<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getPort() {
		if(Port==null){return 0;}
		return Port.longValue();
	}

	/**
	* 设置字段Port的值，该字段的<br>
	* 字段名称 :Port<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPort(long port) {
		this.Port = new Long(port);
    }

	/**
	* 设置字段Port的值，该字段的<br>
	* 字段名称 :Port<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPort(String port) {
		if (port == null){
			this.Port = null;
			return;
		}
		this.Port = new Long(port);
    }

	/**
	* 获取字段UserName的值，该字段的<br>
	* 字段名称 :UserName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getUserName() {
		return UserName;
	}

	/**
	* 设置字段UserName的值，该字段的<br>
	* 字段名称 :UserName<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setUserName(String userName) {
		this.UserName = userName;
    }

	/**
	* 获取字段Password的值，该字段的<br>
	* 字段名称 :Password<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public String getPassword() {
		return Password;
	}

	/**
	* 设置字段Password的值，该字段的<br>
	* 字段名称 :Password<br>
	* 数据类型 :varchar(100)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setPassword(String password) {
		this.Password = password;
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
	* 获取字段RetryCount的值，该字段的<br>
	* 字段名称 :RetryCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public long getRetryCount() {
		if(RetryCount==null){return 0;}
		return RetryCount.longValue();
	}

	/**
	* 设置字段RetryCount的值，该字段的<br>
	* 字段名称 :RetryCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRetryCount(long retryCount) {
		this.RetryCount = new Long(retryCount);
    }

	/**
	* 设置字段RetryCount的值，该字段的<br>
	* 字段名称 :RetryCount<br>
	* 数据类型 :bigint(20)<br>
	* 是否主键 :false<br>
	* 是否必填 :false<br>
	*/
	public void setRetryCount(String retryCount) {
		if (retryCount == null){
			this.RetryCount = null;
			return;
		}
		this.RetryCount = new Long(retryCount);
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

}