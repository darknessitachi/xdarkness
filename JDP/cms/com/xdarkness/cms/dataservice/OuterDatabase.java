package com.xdarkness.cms.dataservice;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCDatabaseSchema;
import com.xdarkness.schema.ZCDatabaseSet;
import com.xdarkness.framework.connection.DBTypes;
import com.xdarkness.framework.connection.XConnectionConfig;
import com.xdarkness.framework.connection.XConnectionPool;
import com.xdarkness.framework.connection.XConnectionPoolManager;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.DataAccess;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class OuterDatabase extends Page {
	private static boolean initFlag = true;
	private static Object mutex = new Object();

	public static void init() {
		ZCDatabaseSet set = new ZCDatabaseSchema().query();
		for (int i = 0; i < set.size(); i++) {
			ZCDatabaseSchema db = set.get(i);
			addConnPool(db);
		}
	}

	public static void addConnPool(ZCDatabaseSchema db) {
		XConnectionConfig dcc = new XConnectionConfig();
		dcc.DBName = db.getDBName();
		dcc.DBPassword = db.getPassword();
		dcc.DBPort = (int) db.getPort();
		dcc.DBServerAddress = db.getAddress();
		dcc.DBType = DBTypes.getDBType(db.getServerType());
		dcc.DBUserName = db.getUserName();
		dcc.PoolName = ("_OuterDatabase_" + db.getID());
		dcc.TestTable = db.getTestTable();
		dcc.isLatin1Charset = "Y".equals(db.getLatin1Flag());
		if (!XConnectionPoolManager.getPoolMap().containsKey(dcc.PoolName + "."))
			new XConnectionPool(dcc);
	}

	public static XConnection getConnection(long id) {
		if (initFlag) {
			synchronized (mutex) {
				if (initFlag) {
					init();
					initFlag = false;
				}
			}
		}
		return XConnectionPoolManager.getConnection("_OuterDatabase_" + id);
	}

	public static void removeConnPool(long id) {
		Object o = XConnectionPoolManager.getPoolMap().get("_OuterDatabase_" + id + ".");
		if (o == null) {
			return;
		}
		XConnectionPool pool = (XConnectionPool) o;
		pool.clear();
		XConnectionPoolManager.getPoolMap().remove("_OuterDatabase_" + id + ".");
	}

	public static XConnectionConfig getDBConnConfig(long id) {
		if (initFlag) {
			synchronized (mutex) {
				if (initFlag) {
					init();
					initFlag = false;
				}
			}
		}
		return XConnectionPoolManager.getDBConnConfig("_OuterDatabase_" + id);
	}

	public static void dg1DataBind(DataGridAction dga) {
		DataTable dt = new QueryBuilder(
				"select * from ZCDatabase where SiteID=?", ApplicationPage
						.getCurrentSiteID()).executeDataTable();
		Mapx map = new Mapx();
		map.put("ORACLE", "Oracle");
		map.put("DB2", "DB2");
		map.put("MSSQL2000", "SQL Server 2000");
		map.put("MSSQL", "SQL Server 2005");
		map.put("MYSQL", "Mysql");
		dt.decodeColumn("ServerType", map);
		dga.bindData(dt);
	}

	public void save() {
		ZCDatabaseSchema db = new ZCDatabaseSchema();
		if (XString.isEmpty($V("ID"))) {
			db.setValue(this.request);
			db.setID(NoUtil.getMaxID("DatabaseID"));
			db.setAddTime(new Date());
			db.setAddUser(User.getUserName());
			db.setSiteID(ApplicationPage.getCurrentSiteID());
			if (db.insert())
				this.response.setMessage("添加数据库连接成功");
			else
				this.response.setError("发生错误,添加数据库连接失败");
		} else {
			db.setID(Long.parseLong($V("ID")));
			db.fill();
			db.setValue(this.request);
			db.setModifyTime(new Date());
			db.setModifyUser(User.getUserName());
			if (db.update())
				this.response.setMessage("修改数据库连接成功");
			else {
				this.response.setError("发生错误,修改数据库连接失败");
			}
		}
		removeConnPool(db.getID());
		addConnPool(db);
	}

	public void del() {
		String ids = $V("IDs");
		String[] arr = ids.split("\\,");
		Transaction tran = new Transaction();
		for (int i = 0; i < arr.length; i++) {
			tran.add(new QueryBuilder(
					"delete from ZCDatabase where SiteID=? and ID=?",
					ApplicationPage.getCurrentSiteID(), arr[i]));
		}
		if (tran.commit())
			this.response.setMessage("删除数据库连接成功");
		else
			this.response.setError("发生错误,删除数据库连接失败");
	}

	public void connTest() {
		XConnectionConfig dcc = new XConnectionConfig();
		dcc.DBName = $V("DBName");
		dcc.DBPassword = $V("Password");
		dcc.DBPort = Integer.parseInt($V("Port"));
		dcc.DBServerAddress = $V("Address");
		dcc.DBType = DBTypes.getDBType($V("ServerType"));
		dcc.DBUserName = $V("UserName");
		dcc.isLatin1Charset = "Y".equals($V("Latin1Flag"));
		XConnection conn = null;
		try {
			conn = XConnectionPool.createConnection(dcc, false);
			String msg = "测试连接成功";
			DataAccess da = new DataAccess(conn);
			try {
				new QueryBuilder("select 1 from "
						+ $V("TestTable") + " where 1=2").executeOneValue();
			} catch (Exception e) {
				e.printStackTrace();
				msg = msg + "，但表" + $V("TestTable") + "不存在!";
			} finally {
				da.close();
			}
			this.response.setMessage(msg);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			this.response.setError("连接到数据库时发生错误:" + e.getMessage());
			return;
		} finally {
			if (conn != null)
				try {
					conn.closeReally();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	public static DataTable getDatabases(Mapx map) {
		return new QueryBuilder(
				"select id,name from ZCDatabase where SiteID=?", ApplicationPage
						.getCurrentSiteID()).executeDataTable();
	}

	public void getTables() {
		if (XString.isEmpty($V("DatabaseID"))) {
			this.response.setError("未传入DatabaseID");
			return;
		}
		long id = Long.parseLong($V("DatabaseID"));
		Connection conn = null;
		try {
			conn = getConnection(id);
			DatabaseMetaData dbm = conn.getMetaData();
			String currentCatalog = conn.getCatalog();
			ResultSet rs = dbm.getTables(currentCatalog, null, null, null);
			ArrayList al = new ArrayList();
			while (rs.next()) {
				if (rs.getObject(2) != null)
					al.add(rs.getObject(2) + "." + rs.getObject(3));
				else {
					al.add(rs.getObject(3));
				}
			}
			String[] arr = new String[al.size()];
			for (int i = 0; i < arr.length; i++) {
				arr[i] = al.get(i).toString();
			}
			this.response.put("Tables", arr);
			this.response.setMessage("获取表信息成功");
			return;
		} catch (Exception e) {
			e.printStackTrace();
			this.response.setError("连接到数据库时发生错误:" + e.getMessage());
			return;
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
}

/*
 * com.xdarkness.cms.dataservice.OuterDatabase JD-Core Version: 0.6.0
 */