package com.xdarkness.framework.database;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.abigdreamer.java.net.connection.XConnection;
import com.abigdreamer.java.net.connection.XConnectionConfig;
import com.abigdreamer.java.net.connection.XConnectionPoolManager;
import com.abigdreamer.java.net.jaf.Page;
import com.abigdreamer.java.net.jaf.controls.TreeAction;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.sql.QueryBuilder;

/**
 * 
 * @author Darkness 
 * create on 2010 2010-12-10 上午08:52:56
 * @version 1.0
 * @since JDP 1.0
 */
public class Database extends Page {

	public Database() {
	}

	public static void treeDataBind(TreeAction ta) {
		DataTable dt = new QueryBuilder(
				"SELECT t.tablename,CONCAT(CONCAT(CONCAT(t.alias,'('),t.tablename),')') AS alias,t.categoryid AS parentTable FROM TablesRemark t")
				.executeDataTable();
		ta.setRootText("数据库列表");
		ta.setIdentifierColumnName("tableName");
		ta.setParentIdentifierColumnName("parentTable");
		ta.setBranchIcon("Icons/icon025a1.gif");
		ta.setLeafIcon("Icons/icon025a1.gif");

		XConnectionConfig dcc = XConnectionPoolManager.getDBConnConfig();
		dt.insertRow(new Object[] { "数据库类型", dcc.DBType, "-1" });
		dt.insertRow(new Object[] { "数据库名称", /*dcc.DBName*/"XPlatform", "数据库类型" });

		// 所有表挂在“全部物理表”节点下
		dt.insertRow(new Object[] { "AllTables", "全部物理表", "数据库名称" });
		dt.insertRow(new Object[] { "AllNoDefinedTables", "未定义表", "数据库名称" });

		dt.add(new QueryBuilder(
						"select cv.refid as tableName,cv.chinaname as alias,'数据库名称' as parentTable from categoryValue cv join category c on cv.categoryid=c.id and c.constname = 'TABLE_CATEGORYID'")
						.executeDataTable());
		ta.bindData(dt);

	}

	public void addTestData() throws SQLException {
		XConnection conn = XConnectionPoolManager.getConnection();
		Statement stmt = conn.createStatement();
		conn.setAutoCommit(false);
		stmt
				.addBatch("insert into category values('{260EA55B-69F4-48CE-A75A-0733A1C11D49}',1,'2006-02-20 13:22:43.030',1,'2007-07-31 16:02:24.873',0,'TABLE_CATEGORYID','表所处目录','表所处目录名称',1,'依据表所处目录（结合表的类型），可维护出所有的业务表视图，此字典中第一个节点项（系统核心表）是不允许用户维护的',2,0,'','','','','','','','','','',1,1,0,'',0)");
		stmt
				.addBatch("insert into categoryValue values('{36B840AD-687C-4F6F-B9AF-E4678B54CD42}',1,'2006-09-11 16:58:49.000',1,'2007-07-31 16:02:20.513',0,8,'{260EA55B-69F4-48CE-A75A-0733A1C11D49}','项目数据表',0,0,0,'','','','','',450,'00002','',1)");
		stmt
				.addBatch("insert into categoryValue values('{4186D692-4DAA-4335-86D3-5E8A4B37B3E4}',1,'2006-08-10 11:36:37.000',1,'2006-08-10 11:37:01.000',0,1,'{260EA55B-69F4-48CE-A75A-0733A1C11D49}','所有模块公用表',0,0,0,'','','','','',100,'00001','',1)");
		stmt
				.addBatch("insert into categoryValue values('{860B8013-E5CA-40DF-B650-3116C2EBC58A}',0,'2006-02-21 16:26:17.170',1,'2006-03-20 08:38:20.000',0,0,'{260EA55B-69F4-48CE-A75A-0733A1C11D49}','系统核心表',0,0,0,'','','','','',50,'00000','所有位于此目录中的表不允许最终用户维护',1)");

		// pstmt.addBatch(sql)
		stmt.executeBatch();
		conn.commit();
		conn.setAutoCommit(true);
	}

	public void init() throws SQLException {
		
		try {
			Date startTime = new Date();
			XConnection conn = XConnectionPoolManager.getConnection();
			CallableStatement cs = conn.prepareCall("{ call pt_init }");
			cs.execute();
			System.out.println("=======execute time:"+(new Date().getTime()-startTime.getTime())/1000);
		} catch (SQLException e) {
			// ....
			throw e;
		}
	}

	public static void main(String[] args) {
		try {
			Database database = new Database();
			database.addTestData();
			database.init();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}