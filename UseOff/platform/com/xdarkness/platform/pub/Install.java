package com.xdarkness.platform.pub;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.ant.filters.StringInputStream;

import com.abigdreamer.java.net.Config;
import com.abigdreamer.java.net.connection.DBTypes;
import com.abigdreamer.java.net.connection.XConnection;
import com.abigdreamer.java.net.connection.XConnectionConfig;
import com.abigdreamer.java.net.connection.XConnectionPoolManager;
import com.abigdreamer.java.net.io.FileUtil;
import com.abigdreamer.java.net.jaf.Ajax;
import com.abigdreamer.java.net.jaf.Constant;
import com.abigdreamer.java.net.message.LongTimeTask;
import com.abigdreamer.java.net.orm.DBImport;
import com.abigdreamer.java.net.orm.OperateType;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.LogUtil;
import com.abigdreamer.java.net.util.XString;
import com.abigdreamer.java.net.util.ZipUtil;

public class Install extends Ajax {

	// START IndexArray
	public static String[] IndexArray = { "zcarticle(CatalogID,Status)",
			"zcarticle(Orderflag)", "zcarticle(publishDate)",
			"zcarticle(addtime)", "zcarticle(modifytime)",
			"zcarticle(DownlineDate)", "zcarticle(CatalogInnercode)",
			"zcarticle(SiteID)", "zcarticle(refersourceid)",
			"zcarticle(keyword)", "zcarticle(ArchiveDate)",
			"bzcarticle(BackupMemo)", "bzcarticle(CatalogID)",
			"zcimage(CatalogID)", "zcimage(CatalogInnercode)",
			"zcimage(SiteID)", "zcimage(OrderFlag)", "zcimage(publishDate)",
			"zcimage(addtime)", "zcimage(modifytime)", "zcvideo(CatalogID)",
			"zcvideo(CatalogInnercode)", "zcvideo(SiteID)",
			"zcvideo(OrderFlag)", "zcvideo(publishDate)", "zcvideo(addtime)",
			"zcvideo(modifytime)", "zcaudio(CatalogID)",
			"zcaudio(CatalogInnercode)", "zcaudio(SiteID)",
			"zcaudio(OrderFlag)", "zcaudio(publishDate)", "zcaudio(addtime)",
			"zcaudio(modifytime)", "zcattachment(CatalogID)",
			"zcattachment(CatalogInnercode)", "zcattachment(SiteID)",
			"zcattachment(OrderFlag)", "zcattachment(publishDate)",
			"zcattachment(addtime)", "zcattachment(modifytime)",
			"zcarticlelog(articleID)", "zcarticlevisitlog(articleID)",
			"zccomment(relaid)", "zccomment(catalogid)",
			"zccatalog(SiteID,Type)", "zccatalog(InnerCode)",
			"zcpageblock(SiteID)", "zcpageblock(CatalogID)",
			"zdprivilege(owner)", "zdcolumnrela(relaid)",
			"zdcolumnvalue(relaid)", "zwinstance(workflowid)",
			"zwstep(InstanceID)", "zwstep(NodeID)", "zwstep(owner)",
			"zctag(tag,siteid)", "zcvisitlog(siteid)" };// END

	// START generate the file framework.xml
	public void generateFrameworkConfig() {

		XConnectionConfig dcc = new XConnectionConfig();
		dcc.isJNDIPool = "1".equals($V("isJNDIPool"));
		dcc.isLatin1Charset = "1".equals($V("isLatin1Charset"));
		dcc.JNDIName = $V("JNDIName");
		dcc.DBName = $V("DBName");
		dcc.DBPassword = $V("Password");

		try {
			dcc.DBPort = Integer.parseInt($V("Port"));
		} catch (NumberFormatException localNumberFormatException) {
		}

		dcc.DBServerAddress = $V("Address");
		dcc.DBType = DBTypes.getDBType($V("ServerType"));
		dcc.DBUserName = $V("UserName");

		final String JDBC = "jdbc/";
		if (Config.isJboss() && (dcc.JNDIName.toLowerCase().startsWith(JDBC))) {
			dcc.JNDIName = dcc.JNDIName.substring(JDBC.length());
		}

		String template = FileUtil.readText(Config.getContextRealPath()
				+ "WEB-INF/classes/framework.template");
		// template.replace("{password}", EncryptUtil.encrypt3DES("password",
		// "27jrWz3sxrVbR+pnyg6j"));

		FileUtil.writeText(Config.getContextRealPath()
				+ "WEB-INF/classes/framework.xml", template, "UTF-8");
	}// END

	public void execute() {
		if (Config.isInstalled) {
			this.response.setError("已经为" + Config.getAppCode()
					+ "初始数据库完毕，不能再次初始化!");
			return;
		}

		generateFrameworkConfig();

		// START MYSQL
		if (XConnectionPoolManager.isMysql()) {

			DataTable dt = new QueryBuilder(
					"show variables like 'lower_case_table_names'")
					.executeDataTable();
			if ((dt.getRowCount() == 0) || (dt.getInt(0, 1) == 0)) {
				this.response
						.setError("检查到mysql数据库区分表名大小写，请修改my.cnf或my.ini:<br><font color=red>在[mysqld]段加上一行配置lower_case_table_names=1!</font>");
				return;
			}

			dt = new QueryBuilder(
					"show variables like 'character_set_database'")
					.executeDataTable();
			String charset = Constant.GlobalCharset.replaceAll("\\-", "");
			if (!charset.equalsIgnoreCase(dt.getString(0, 1))) {
				this.response
						.setError("检查到mysql的字符集为"
								+ dt.getString(0, 1)
								+ "，但程序要求的字符集为"
								+ charset.toLowerCase()
								+ "，请修改my.cnf或my.ini:<br><font color=red>"
								+ "凡以default-character-set开头的行，都修改为default-character-set="
								+ charset.toLowerCase() + "</font>");
				return;
			}
			dt = new QueryBuilder("show databases like ?", $V("DBName"))
					.executeDataTable();
			if (dt.getRowCount() != 0)
				LogUtil.info("安装目标数据库不存在，将自动创建目标数据库!");

			try {
				new QueryBuilder("create schema " + $V("DBName"))
						.executeNoQuery();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}// END
		// START SQLServer
		else if (XConnectionPoolManager.isSQLServer()) {
			// dcc.DBName = "master";

			DataTable dt = new QueryBuilder(
					"select * from sysDatabases where name=?", $V("DBName"))
					.executeDataTable();

			if (dt.getRowCount() == 0) {
				LogUtil.info("安装目标数据库不存在，将自动创建目标数据库!");
				try {
					new QueryBuilder("create database " + $V("DBName"))
							.executeNoQuery();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			String user = "";// dcc.DBUserName
			this.response.setError("用户" + user + "没有访问数据库" + $V("DBName")
					+ "的权限！");
		}// END

		boolean importData = "1".equals($V("ImportData"));
		boolean autoCreate = "1".equals($V("AutoCreate"));
		
		// START 导入数据
		if (importData) {
			LongTimeTask ltt = LongTimeTask.getInstanceByType("Install");
			if (ltt != null) {
				this.response.setError("相关任务正在运行中，请先中止！");
				return;
			}
			ltt = new LongTimeTask() {

				public void execute() {
					DBImport di = new DBImport();
					di.setTask(this);
					Config.setValue("App.DebugMode", "true");
					if (di.importDB(Config.getContextRealPath()
							+ "WEB-INF/data/installer/Install.dat")) {

						setCurrentInfo("正在初始化系统配置");
						// Install.init(this.val$conn2, this.val$r);
//						setPercent(33);

						Config.loadConfig();
						Config.isInstalled = false;
						Config.loadDBConfig();

						// START 建立索引、全文检索索引、生成全站静态文件,暂不考虑
						// if (this.val$autoCreate) {
						// setCurrentInfo("正在建立索引");
						// Install.createIndexes(this.val$conn2, null,
						// this.val$conn2.getDBConfig().DBType);
						// }

						// User.UserData user = new User.UserData();
						// user.setLogin(true);
						// user.setManager(true);
						// user.setBranchInnerCode("0001");
						// user.setUserName("admin");
						// User.setCurrent(user);
						// Application.setCurrentSiteID(new
						// QueryBuilder("select ID from ZCSite").executeString());

						// setCurrentInfo("正在建立全文检索索引文件，需耗时约1至3分钟，请稍等");
						// ZCFullTextSchema ft = new ZCFullTextSchema();
						// ft = ft.query().get(0);
						// SearchAPI.update(ft.getID());

						// setCurrentInfo("正在生成全站静态文件，需耗时约2-5分钟，请稍等");
						// PageGenerator pg = new PageGenerator(this);
						// ZCSiteSchema site = new ZCSiteSchema();
						// site = site.query().get(0);
						// pg.staticSite(site.getID());
						// END

						setCurrentInfo("安装完成，将重定向到登录页面!");
						Config.isInstalled = true;

						// CronManager.getInstance().init();
					} else {
						addError("<font color=red>导入失败，请查看服务器日志! 确认问题后请按F5刷新页面重新导入。</font>");
					}
				}
			};
			ltt.setType("Install");
			// ltt.setUser(User.getCurrent());
			ltt.start();
			$S("TaskID", ltt.getTaskID());
			this.response.setStatus(1);
		}// END 
		// START 不需导入数据
		else {
			// init(conn2, r);
			Config.loadConfig();
			// CronManager.getInstance().init();
			this.response.setError(Config.getAppCode() + "初始化完毕!");
		}// END
	}

	// START 提取SQL脚本
	public static void generateSQL(HttpServletRequest request,
			HttpServletResponse response) {
		String dbtype = request.getParameter("Type");
		String sql = new DBImport().getSQL(Config.getContextRealPath()
				+ "WEB-INF/data/installer/Install.dat", dbtype);

		StringBuffer sb = new StringBuffer(sql);
		createIndexes(null, sb, dbtype);
		try {
			request.setCharacterEncoding(Constant.GlobalCharset);
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ dbtype + ".zip");

			OutputStream os = response.getOutputStream();
			ZipUtil.zipStream(new StringInputStream(sb.toString()),
					response.getOutputStream(), dbtype + ".sql");
			os.flush();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// END

	public static void main(String[] args) {
		// createIndexes(DBConnPool.getConnection(), new StringBuffer(),
		// "MYSQL");
	}

	// START 创建索引，先不考虑
	public static String createIndex(String indexInfo, int i, String dbType) {
		int p1 = indexInfo.indexOf("(");
		if (p1 < 0) {
			return null;
		}
		String table = indexInfo.substring(0, p1);
		if (!indexInfo.endsWith(")")) {
			return null;
		}
		indexInfo = indexInfo.substring(p1 + 1, indexInfo.length() - 1);
		String[] cs = indexInfo.split(",");
		StringBuffer sb = new StringBuffer();
		String name = "idx" + i + "_"
				+ table.substring(OperateType.UPDATE.value());
		if ((dbType.equals("ORACLE")) && (name.length() > 15)) {
			name = name.substring(0, 15);
		}

		sb.append("create index " + name + " on " + table + " (");
		boolean first = true;
		for (int j = 0; j < cs.length; j++) {
			if (XString.isEmpty(cs[j])) {
				continue;
			}
			if (!first) {
				sb.append(",");
			}
			sb.append(cs[j]);
			first = false;
		}
		sb.append(")");
		return sb.toString();
	}

	public static ArrayList getIndexSQLForTable(String tableName, String dbType) {
		ArrayList list = new ArrayList();
		for (int i = 0; i < IndexArray.length; i++) {
			String indexInfo = IndexArray[i];
			if (indexInfo.toLowerCase().startsWith(
					tableName.toLowerCase() + "(")) {
				String sql = createIndex(indexInfo, i, dbType);
				if (XString.isNotEmpty(sql)) {
					list.add(sql);
				}
			}
		}
		return list;
	}

	public static void createIndexes(XConnection conn, StringBuffer sbAll,
			String dbtype) {
		// DataAccess da = null;
		// if (conn == null)
		// da = new DataAccess();
		// else {
		// da = new DataAccess(conn);
		// }
		// for (int i = 0; i < IndexArray.length; i++) {
		// String indexInfo = IndexArray[i];
		// int p1 = indexInfo.indexOf("(");
		// if (p1 < 0) {
		// continue;
		// }
		// String table = indexInfo.substring(0, p1);
		// if (!indexInfo.endsWith(")")) {
		// continue;
		// }
		// String name = "idx" + i + "_" + table.substring(OperateType.UPDATE);
		// String sql = createIndex(indexInfo, i, dbtype);
		// if (conn != null) {
		// try {
		// if (dbtype.equals("MYSQL"))
		// da.executeNoQuery(new QueryBuilder("alter table "
		// + table + " drop index " + name));
		// else if (dbtype.equals("MSSQL"))
		// da.executeNoQuery(new QueryBuilder("drop index " + name
		// + " on " + table));
		// else
		// da.executeNoQuery(new QueryBuilder("drop index " + name));
		// } catch (SQLException localSQLException1) {
		// }
		// try {
		// da.executeNoQuery(new QueryBuilder(sql));
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// } else {
		// sbAll.append(sql);
		// if (dbtype.equals("MSSQL")) {
		// sbAll.append("\n");
		// sbAll.append("go\n");
		// } else {
		// sbAll.append(";\n");
		// }
		// }
		// }
	}// END

	// START INIT 作用未知
	// public static void init(DBConn conn, RequestImpl r) {
	// DataAccess da = new DataAccess(conn);
	// try {
	// if (XString.isNotEmpty(Config.getContextPath())) {
	// String path = Config.getContextPath() + "Services";
	// String prefix = r.getScheme() + "://" + r.getServerName();
	// if ((r.getScheme().equalsIgnoreCase("http"))
	// && (r.getPort() != 80)) {
	// prefix = prefix + ":" + r.getPort();
	// }
	// if ((r.getScheme().equalsIgnoreCase("https"))
	// && (r.getPort() != 443)) {
	// prefix = ":" + r.getPort();
	// }
	// path = prefix + path;
	// da.executeNoQuery(new QueryBuilder(
	// "update ZDConfig set Value=? where Type='ServicesContext'",
	// path));
	// Config.update();
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }
	// END
}
