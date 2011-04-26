package com.xdarkness.platform.pub;

//import java.io.IOException;
//import java.io.OutputStream;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.tools.ant.filters.StringInputStream;
//
//import com.xdarkness.cms.api.SearchAPI;
//import com.xdarkness.cms.template.PageGenerator;
//import com.xdarkness.framework.Config;
//import com.xdarkness.framework.Constant;
//import com.xdarkness.framework.RequestImpl;
//import com.xdarkness.framework.User;
//import com.xdarkness.framework.connection.DBTypes;
//import com.xdarkness.framework.connection.XConnection;
//import com.xdarkness.framework.connection.XConnectionConfig;
//import com.xdarkness.framework.connection.XConnectionPool;
//import com.xdarkness.framework.connection.XConnectionPoolManager;
//import com.xdarkness.framework.jaf.Ajax;
//import com.xdarkness.framework.messages.LongTimeTask;
//import com.xdarkness.framework.orm.DBImport;
//import com.xdarkness.framework.orm.data.DataTable;
//import com.xdarkness.framework.schedule.CronManager;
//import com.xdarkness.framework.security.EncryptUtil;
//import com.xdarkness.framework.sql.DataAccess;
//import com.xdarkness.framework.sql.QueryBuilder;
//import com.xdarkness.framework.util.FileUtil;
//import com.xdarkness.framework.util.LogUtil;
//import com.xdarkness.framework.util.StringUtil;
//import com.xdarkness.framework.util.ZipUtil;
//import com.xdarkness.platform.Application;
//import com.xdarkness.schema.ZCFullTextSchema;
//import com.xdarkness.schema.ZCSiteSchema;
//
//public class Install extends Ajax {
//	public static String[] IndexArray = { "zcarticle(CatalogID,Status)",
//			"zcarticle(Orderflag)", "zcarticle(publishDate)",
//			"zcarticle(addtime)", "zcarticle(modifytime)",
//			"zcarticle(DownlineDate)", "zcarticle(CatalogInnercode)",
//			"zcarticle(SiteID)", "zcarticle(refersourceid)",
//			"zcarticle(keyword)", "zcarticle(ArchiveDate)",
//			"bzcarticle(BackupMemo)", "bzcarticle(CatalogID)",
//			"zcimage(CatalogID)", "zcimage(CatalogInnercode)",
//			"zcimage(SiteID)", "zcimage(OrderFlag)", "zcimage(publishDate)",
//			"zcimage(addtime)", "zcimage(modifytime)", "zcvideo(CatalogID)",
//			"zcvideo(CatalogInnercode)", "zcvideo(SiteID)",
//			"zcvideo(OrderFlag)", "zcvideo(publishDate)", "zcvideo(addtime)",
//			"zcvideo(modifytime)", "zcaudio(CatalogID)",
//			"zcaudio(CatalogInnercode)", "zcaudio(SiteID)",
//			"zcaudio(OrderFlag)", "zcaudio(publishDate)", "zcaudio(addtime)",
//			"zcaudio(modifytime)", "zcattachment(CatalogID)",
//			"zcattachment(CatalogInnercode)", "zcattachment(SiteID)",
//			"zcattachment(OrderFlag)", "zcattachment(publishDate)",
//			"zcattachment(addtime)", "zcattachment(modifytime)",
//			"zcarticlelog(articleID)", "zcarticlevisitlog(articleID)",
//			"zccomment(relaid)", "zccomment(catalogid)",
//			"zccatalog(SiteID,Type)", "zccatalog(InnerCode)",
//			"zcpageblock(SiteID)", "zcpageblock(CatalogID)",
//			"zdprivilege(owner)", "zdcolumnrela(relaid)",
//			"zdcolumnvalue(relaid)", "zwinstance(workflowid)",
//			"zwstep(InstanceID)", "zwstep(NodeID)", "zwstep(owner)",
//			"zctag(tag,siteid)", "zcvisitlog(siteid)" };
//
//	public void execute() {
//     if (Config.isInstalled) {
//       this.response.setError("已经为" + Config.getAppCode() + "初始数据库完毕，不能再次初始化!");
//       return; 
//     }
// 
//     XConnectionConfig dcc = new XConnectionConfig();
//     dcc.isJNDIPool = "1".equals($V("isJNDIPool"));
//     dcc.isLatin1Charset = "1".equals($V("isLatin1Charset"));
//     dcc.JNDIName = $V("JNDIName");
//     dcc.DBName = $V("DBName");
//     dcc.DBPassword = $V("Password");
//     try {
//       dcc.DBPort = Integer.parseInt($V("Port"));
//     }
//     catch (NumberFormatException localNumberFormatException) {
//     }
//     dcc.DBServerAddress = $V("Address");
//     dcc.DBType = DBTypes.getDBType($V("ServerType"));
//     dcc.DBUserName = $V("UserName");
// 
//     if ((Config.isJboss()) && 
//       (dcc.JNDIName.toLowerCase().startsWith("jdbc/"))) {
//       dcc.JNDIName = dcc.JNDIName.substring(5);
//     }
// 
//     XConnection conn = null;
//     try {
//       if (dcc.isMysql())
//         try {
//           conn = XConnectionPool.createConnection(dcc, false);
//         } catch (Exception e) {
//           if (conn != null) {
//             conn.close();
//           }
//           dcc.DBName = "mysql";
//           try {
//             conn = XConnectionPool.createConnection(dcc, false);
// 
//             DataAccess da = new DataAccess(conn);
//             DataTable dt = new QueryBuilder("show variables like 'lower_case_table_names'").executeDataTable(
//               );
//             if ((dt.getRowCount() == 0) || (dt.getInt(0, 1) == 0)) {
//               this.response.setError("检查到mysql数据库区分表名大小写，请修改my.cnf或my.ini:<br><font color=red>在[mysqld]段加上一行配置lower_case_table_names=1!</font>");
// 
//               if (conn != null)
//                 try {
//                   conn.closeReally();
//                 } catch (SQLException se) {
//                   se.printStackTrace();
//                 }
//               return;
//             }
//              dt = new QueryBuilder("show variables like 'character_set_database'").executeDataTable();
//             String charset = Constant.GlobalCharset.replaceAll("\\-", "");
//             if (!charset.equalsIgnoreCase(dt.getString(0, 1))) {
//               this.response.setError("检查到mysql的字符集为" + dt.getString(0, 1) + "，但程序要求的字符集为" + 
//                 charset.toLowerCase() + "，请修改my.cnf或my.ini:<br><font color=red>" + 
//                 "凡以default-character-set开头的行，都修改为default-character-set=" + charset.toLowerCase() + 
//                 "</font>");
// 
//               if (conn != null)
//                 try {
//                   conn.closeReally();
//                 } catch (SQLException se) {
//                   se.printStackTrace();
//                 }
//               return;
//             }
//              dt = new QueryBuilder("show databases like ?", $V("DBName")).executeDataTable();
//             if (dt.getRowCount() != 0) break label753; LogUtil.info("安装目标数据库不存在，将自动创建目标数据库!");
//             new QueryBuilder("create schema " + $V("DBName")).executeNoQuery();
//             dcc.DBName = $V("DBName");
//             conn.close();
//             conn = XConnectionPool.createConnection(dcc, false);
//           }
//           catch (Exception e2)
//           {
//             throw e;
//           }
//         }
//       else if (dcc.isSQLServer())
//         try {
//           conn = XConnectionPool.createConnection(dcc, false);
//         } catch (Exception e) {
//           if (conn != null) {
//             conn.close();
//           }
//           dcc.DBName = "master";
//           try {
//             conn = XConnectionPool.createConnection(dcc, false);
//             DataAccess da = new DataAccess(conn);
// 
//             DataTable dt = da.executeDataTable(
//               new QueryBuilder("select * from sysDatabases where name=?", 
//               $V("DBName")));
//             if (dt.getRowCount() == 0) {
//               LogUtil.info("安装目标数据库不存在，将自动创建目标数据库!");
//               da.executeNoQuery(new QueryBuilder("create database " + $V("DBName")));
//               dcc.DBName = $V("DBName");
//               conn.close();
//               conn = DBConnPoolImpl.createConnection(dcc, false); break label753;
//             }
//             this.response.setError("用户" + dcc.DBUserName + "没有访问数据库" + $V("DBName") + "的权限！");
// 
//             if (conn != null)
//               try {
//                 conn.closeReally();
//               } catch (SQLException se) {
//                 se.printStackTrace();
//               }
//             return;
//           }
//           catch (Exception e2)
//           {
//             Exception e;
//             throw e;
//           }
//         }
//       else {
//         conn = XConnectionPool.createConnection(dcc, false);
//       }
//       label753: boolean importData = "1".equals($V("ImportData"));
//       DBConn conn2 = conn;
//       boolean autoCreate = "1".equals($V("AutoCreate"));
//       RequestImpl r = this.request;
//       RequestImpl r;
//       DBConn conn2;
//       if (importData) {
//         LongTimeTask ltt = LongTimeTask.getInstanceByType("Install");
//         if (ltt != null) {
//           this.response.setError("相关任务正在运行中，请先中止！");
//           return;
//         }
//         boolean autoCreate;
//         boolean importData;
//         LongTimeTask ltt = new LongTimeTask(conn2, autoCreate, r, dcc) { private final DBConn val$conn2;
//           private final boolean val$autoCreate;
//           private final RequestImpl val$r;
//           private final DBConnConfig val$dcc;
// 
//           public void execute() { try { DBImport di = new DBImport();
//               di.setTask(this);
//               Config.setValue("App.DebugMode", "true");
//               if (di.importDB(Config.getContextRealPath() + "WEB-INF/data/installer/Install.dat", this.val$conn2, 
//                 this.val$autoCreate)) {
//                 if (this.val$autoCreate) {
//                   setCurrentInfo("正在建立索引");
//                   Install.createIndexes(this.val$conn2, null, this.val$conn2.getDBConfig().DBType);
//                 }
//                 setCurrentInfo("正在初始化系统配置");
//                 Install.init(this.val$conn2, this.val$r);
//                 setPercent(33);
//                 Install.generateFrameworkConfig(this.val$dcc);
//                 Config.loadConfig();
//                 Config.isInstalled = false;
//                 Config.loadDBConfig();
// 
//                 User.UserData user = new User.UserData();
//                 user.setLogin(true);
//                 user.setManager(true);
//                 user.setBranchInnerCode("0001");
//                 user.setUserName("admin");
//                 User.setCurrent(user);
//                 Application.setCurrentSiteID(new QueryBuilder("select ID from ZCSite").executeString());
// 
//                 setCurrentInfo("正在建立全文检索索引文件，需耗时约1至3分钟，请稍等");
//                 ZCFullTextSchema ft = new ZCFullTextSchema();
//                 ft = ft.query().get(0);
//                 SearchAPI.update(ft.getID());
// 
//                 setCurrentInfo("正在生成全站静态文件，需耗时约2-5分钟，请稍等");
//                 PageGenerator pg = new PageGenerator(this);
//                 ZCSiteSchema site = new ZCSiteSchema();
//                 site = site.query().get(0);
//                 pg.staticSite(site.getID());
// 
//                 setCurrentInfo("安装完成，将重定向到登录页面!");
//                 Config.isInstalled = true;
// 
//                 CronManager.getInstance().init();
//               } else {
//                 addError("<font color=red>导入失败，请查看服务器日志! 确认问题后请按F5刷新页面重新导入。</font>");
//               }
//             } finally {
//               if (this.val$conn2 != null)
//                 try {
//                   this.val$conn2.closeReally();
//                 } catch (SQLException e) {
//                   e.printStackTrace();
//                 }
//             }
//           }
//         };
//         ltt.setType("Install");
//         ltt.setUser(User.getCurrent());
//         ltt.start();
//         $S("TaskID", ltt.getTaskID());
//         this.response.setStatus(1);
//       } else {
//         init(conn2, r);
//         generateFrameworkConfig(dcc);
//         Config.loadConfig();
//         CronManager.getInstance().init();
//         this.response.setError(Config.getAppCode() + "初始化完毕!");
//       }
//     } catch (Exception e) {
//       this.response.setError("连接到数据库时发生错误:" + e.getMessage());
// 
//       if (conn != null)
//         try {
//           conn.closeReally();
//         } catch (SQLException se) {
//           se.printStackTrace();
//         }
//     }
//     finally
//     {
//       if (conn != null)
//         try {
//           conn.closeReally();
//         } catch (SQLException se) {
//           se.printStackTrace();
//         }
//     }
//   }
//
//	public static void generateFrameworkConfig(DBConnConfig dcc) {
//		StringBuffer sb = new StringBuffer();
//		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
//		sb.append("<framework>\n");
//		sb.append("\t<application>\n");
//		sb.append("\t\t<config name=\"DebugMode\">false</config>\n");
//		sb.append("\t\t<config name=\"LogLevel\">Debug</config>\n");
//		sb
//				.append("\t\t<config name=\"LoginClass\">com.xdarkness.platform.Login</config>\n");
//		sb.append("\t\t<config name=\"LoginPage\">Login.jsp</config>\n");
//		sb.append("\t\t<config name=\"MinimalMemory\">true</config>\n");
//		sb
//				.append("\t\t<config name=\"CodeSource\">com.xdarkness.platform.pub.PlatformCodeSource</config>\n");
//		sb
//				.append("\t\t<config name=\"WorkflowAdapter\">com.xdarkness.cms.workflow.CMSWorkflowAdapter</config>\n");
//		sb.append("\t\t<config name=\"ExistPlatformDB\">true</config>\n");
//		sb.append("\t\t<config name=\"PDM\">Platform,ZCMS,WorkFlow</config>\n");
//		sb.append("\t</application>\n");
//
//		sb.append("\t<cache>\n");
//		sb.append("\t\t<provider class=\"com.xdarkness.bbs.ForumCache\" />\n");
//		sb
//				.append("\t\t<provider class=\"com.xdarkness.platform.pub.PlatformCache\" />\n");
//		sb.append("\t\t<provider class=\"com.xdarkness.cms.pub.CMSCache\" />\n");
//		sb
//				.append("\t\t<provider class=\"com.xdarkness.cms.document.MessageCache\" />\n");
//		sb.append("\t</cache>\n");
//
//		sb.append("\t<extend>\n");
//		sb
//				.append("\t\t<action class=\"com.xdarkness.shop.extend.ShopMenuExtend\" />\n");
//		sb
//				.append("\t\t<action class=\"com.xdarkness.shop.extend.ShopLoginExtend\" />\n");
//		sb
//				.append("\t\t<action class=\"com.xdarkness.bbs.extend.BBSMenuExtend\" />\n");
//		sb
//				.append("\t\t<action class=\"com.xdarkness.bbs.extend.BBSLoginExtend\" />\n");
//		sb.append("\t</extend>\n");
//
//		sb.append("\t<cron>\n");
//		sb.append("\t\t<config name=\"RefreshInterval\">30000</config>\n");
//		sb
//				.append("\t\t<taskManager class=\"com.xdarkness.datachannel.WebCrawlTaskManager\" />\n");
//		sb
//				.append("\t\t<taskManager class=\"com.xdarkness.cms.dataservice.FullTextTaskManager\" />\n");
//		sb
//				.append("\t\t<taskManager class=\"com.xdarkness.cms.datachannel.PublishTaskManager\" />\n");
//		sb
//				.append("\t\t<task class=\"com.xdarkness.framework.FrameworkTask\" time=\"30 10,16 * * *\" />\n");
//		sb
//				.append("\t\t<task class=\"com.xdarkness.cms.dataservice.ADUpdating\" time=\"*/30 * * * *\" />\n");
//		sb
//				.append("\t\t<task class=\"com.xdarkness.cms.datachannel.DeployTask\" time=\"* * * * *\" />\n");
//		sb
//				.append("\t\t<task class=\"com.xdarkness.cms.datachannel.PublishTask\" time=\"* * * * *\" />\n");
//		sb
//				.append("\t\t<task class=\"com.xdarkness.cms.stat.StatUpdateTask\" time=\"*/5 * * * *\" />\n");
//		sb
//				.append("\t\t<task class=\"com.xdarkness.cms.datachannel.ArticleArchiveTask\" time=\"0 0 1 * *\" />\n");
//		sb
//				.append("\t\t<task class=\"com.xdarkness.cms.datachannel.ArticleCancelTopTask\" time=\"*/5 * * * *\" />\n");
//		sb
//				.append("\t\t<task class=\"com.xdarkness.cms.document.ArticleRelaTask\" time=\"*/2 * * * *\" />\n");
//		sb
//				.append("\t\t<task class=\"com.xdarkness.cms.site.TagUpdateTask\" time=\"0 * * * *\" />\n");
//		sb
//				.append("\t\t<task class=\"com.xdarkness.datachannel.InnerSyncTask\" time=\"* * * * *\" />\n");
//		sb
//				.append("\t\t<task class=\"com.xdarkness.datachannel.DBSyncTask\" time=\"* * * * *\" />\n");
//		sb.append("\t</cron>\n");
//
//		sb.append("\t<databases>\n");
//		sb.append("\t\t<database name=\"Default\">\n");
//		sb.append("\t\t\t<config name=\"Type\">" + dcc.DBType + "</config>\n");
//		if (dcc.isJNDIPool) {
//			sb.append("\t\t\t<config name=\"JNDIName\">" + dcc.JNDIName
//					+ "</config>\n");
//		} else {
//			sb.append("\t\t\t<config name=\"ServerAddress\">"
//					+ dcc.DBServerAddress + "</config>\n");
//			sb.append("\t\t\t<config name=\"Port\">" + dcc.DBPort
//					+ "</config>\n");
//			sb.append("\t\t\t<config name=\"Name\">" + dcc.DBName
//					+ "</config>\n");
//			sb.append("\t\t\t<config name=\"UserName\">" + dcc.DBUserName
//					+ "</config>\n");
//			sb.append("\t\t\t<config name=\"Password\">$KEY"
//					+ EncryptUtil.encrypt3DES(dcc.DBPassword,
//							"27jrWz3sxrVbR+pnyg6j") + "</config>\n");
//			sb.append("\t\t\t<config name=\"MaxConnCount\">1000</config>\n");
//			sb.append("\t\t\t<config name=\"InitConnCount\">0</config>\n");
//			sb.append("\t\t\t<config name=\"TestTable\">ZDMaxNo</config>\n");
//			if (dcc.isLatin1Charset) {
//				sb
//						.append("\t\t\t<config name=\"isLatin1Charset\">true</config>\n");
//			}
//		}
//		sb.append("\t\t</database>\n");
//		sb.append("\t</databases>\n");
//		sb.append("\t<allowUploadExt>\n");
//		sb
//				.append("\t\t<config name=\"AllowAttachExt\">doc,docx,xls,xlsx,ppt,pptx,pdf,swf,rar,zip,txt,xml,html,htm,css,js,db,dat</config>\n");
//		sb.append("\t\t<config name=\"AllowAudioExt\">mp3</config>\n");
//		sb
//				.append("\t\t<config name=\"AllowImageExt\">jpg,gif,jpeg,png,bmp,tif,zip</config>\n");
//		sb
//				.append("\t\t<config name=\"AllowVideoExt\">asx,flv,avi,wmv,mp4,mov,asf,mpg,rm,rmvb</config>\n");
//		sb.append("\t\t</allowUploadExt>\n");
//		sb.append("\t</framework>\n");
//		FileUtil.writeText(Config.getContextRealPath()
//				+ "WEB-INF/classes/framework.xml", sb.toString(), "UTF-8");
//	}
//
//	public static void generateSQL(HttpServletRequest request,
//			HttpServletResponse response) {
//		String dbtype = request.getParameter("Type");
//		String sql = new DBImport().getSQL(Config.getContextRealPath()
//				+ "WEB-INF/data/installer/Install.dat", dbtype);
//
//		StringBuffer sb = new StringBuffer(sql);
//		createIndexes(null, sb, dbtype);
//		try {
//			request.setCharacterEncoding(Constant.GlobalCharset);
//			response.reset();
//			response.setContentType("application/octet-stream");
//			response.setHeader("Content-Disposition", "attachment; filename="
//					+ dbtype + ".zip");
//
//			OutputStream os = response.getOutputStream();
//			ZipUtil.zipStream(new StringInputStream(sb.toString()), response
//					.getOutputStream(), dbtype + ".sql");
//			os.flush();
//			os.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void main(String[] args) {
//		createIndexes(DBConnPool.getConnection(), new StringBuffer(), "MYSQL");
//	}
//
//	public static String createIndex(String indexInfo, int i, String dbType) {
//		int p1 = indexInfo.indexOf("(");
//		if (p1 < 0) {
//			return null;
//		}
//		String table = indexInfo.substring(0, p1);
//		if (!indexInfo.endsWith(")")) {
//			return null;
//		}
//		indexInfo = indexInfo.substring(p1 + 1, indexInfo.length() - 1);
//		String[] cs = indexInfo.split(",");
//		StringBuffer sb = new StringBuffer();
//		String name = "idx" + i + "_" + table.substring(OperateType.UPDATE);
//		if ((dbType.equals("ORACLE")) && (name.length() > 15)) {
//			name = name.substring(0, 15);
//		}
//
//		sb.append("create index " + name + " on " + table + " (");
//		boolean first = true;
//		for (int j = 0; j < cs.length; j++) {
//			if (StringUtil.isEmpty(cs[j])) {
//				continue;
//			}
//			if (!first) {
//				sb.append(",");
//			}
//			sb.append(cs[j]);
//			first = false;
//		}
//		sb.append(")");
//		return sb.toString();
//	}
//
//	public static ArrayList getIndexSQLForTable(String tableName, String dbType) {
//		ArrayList list = new ArrayList();
//		for (int i = 0; i < IndexArray.length; i++) {
//			String indexInfo = IndexArray[i];
//			if (indexInfo.toLowerCase().startsWith(
//					tableName.toLowerCase() + "(")) {
//				String sql = createIndex(indexInfo, i, dbType);
//				if (StringUtil.isNotEmpty(sql)) {
//					list.add(sql);
//				}
//			}
//		}
//		return list;
//	}
//
//	public static void createIndexes(DBConn conn, StringBuffer sbAll,
//			String dbtype) {
//		DataAccess da = null;
//		if (conn == null)
//			da = new DataAccess();
//		else {
//			da = new DataAccess(conn);
//		}
//		for (int i = 0; i < IndexArray.length; i++) {
//			String indexInfo = IndexArray[i];
//			int p1 = indexInfo.indexOf("(");
//			if (p1 < 0) {
//				continue;
//			}
//			String table = indexInfo.substring(0, p1);
//			if (!indexInfo.endsWith(")")) {
//				continue;
//			}
//			String name = "idx" + i + "_" + table.substring(OperateType.UPDATE);
//			String sql = createIndex(indexInfo, i, dbtype);
//			if (conn != null) {
//				try {
//					if (dbtype.equals("MYSQL"))
//						da.executeNoQuery(new QueryBuilder("alter table "
//								+ table + " drop index " + name));
//					else if (dbtype.equals("MSSQL"))
//						da.executeNoQuery(new QueryBuilder("drop index " + name
//								+ " on " + table));
//					else
//						da
//								.executeNoQuery(new QueryBuilder("drop index "
//										+ name));
//				} catch (SQLException localSQLException1) {
//				}
//				try {
//					da.executeNoQuery(new QueryBuilder(sql));
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			} else {
//				sbAll.append(sql);
//				if (dbtype.equals("MSSQL")) {
//					sbAll.append("\n");
//					sbAll.append("go\n");
//				} else {
//					sbAll.append(";\n");
//				}
//			}
//		}
//	}
//
//	public static void init(DBConn conn, RequestImpl r) {
//		DataAccess da = new DataAccess(conn);
//		try {
//			if (StringUtil.isNotEmpty(Config.getContextPath())) {
//				String path = Config.getContextPath() + "Services";
//				String prefix = r.getScheme() + "://" + r.getServerName();
//				if ((r.getScheme().equalsIgnoreCase("http"))
//						&& (r.getPort() != 80)) {
//					prefix = prefix + ":" + r.getPort();
//				}
//				if ((r.getScheme().equalsIgnoreCase("https"))
//						&& (r.getPort() != 443)) {
//					prefix = ":" + r.getPort();
//				}
//				path = prefix + path;
//				da
//						.executeNoQuery(new QueryBuilder(
//								"update ZDConfig set Value=? where Type='ServicesContext'",
//								path));
//				Config.update();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//}
