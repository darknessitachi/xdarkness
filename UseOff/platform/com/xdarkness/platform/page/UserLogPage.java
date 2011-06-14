package com.xdarkness.platform.page;

import java.sql.SQLException;
import java.util.Date;

import com.abigdreamer.java.net.User;
import com.abigdreamer.java.net.data.Transaction;
import com.abigdreamer.java.net.jaf.Page;
import com.abigdreamer.java.net.jaf.controls.HtmlUtil;
import com.abigdreamer.java.net.jaf.controls.grid.DataGridAction;
import com.abigdreamer.java.net.orm.OperateType;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.orm.data.DataTableMap;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.DateUtil;
import com.abigdreamer.java.net.util.Mapx;
import com.abigdreamer.java.net.util.XString;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.platform.pub.PlatformCache;
import com.zving.schema.ZDUserLogSchema;
import com.zving.schema.ZDUserLogSet;
import com.zving.schema.ZDUserSchema;

public class UserLogPage extends Page {
	public static final String LOGIN = "Login";
	public static final String LOGOUT = "Logout";
	public static final String LOG = "Log";
	public static final DataTableMap USERLOG_LOGTYPE_MAP = new DataTableMap();
	public static final Mapx USERLOG_FORUM_MAP;
	public static final String FORUM = "Forum";
	public static final String FORUM_TOPTHEME = "TopTheme";
	public static final String FORUM_TOPCANCEL = "TopCancel";
	public static final String FORUM_DELTHEME = "DelTheme";
	public static final String FORUM_BESTTHEME = "BestTheme";
	public static final String FORUM_BESTCANCEL = "BestCancel";
	public static final String FORUM_BRIGHTTHEME = "BrightTheme";
	public static final String FORUM_UPTHEME = "UpTheme";
	public static final String FORUM_DOWNTHEME = "DownTheme";
	public static final String FORUM_MOVETHEME = "MoveTheme";
	public static final String FORUM_EDITPOST = "EditPost";
	public static final String FORUM_DELPOST = "DelPost";
	public static final String FORUM_HIDEPOST = "HidePost";
	public static final Mapx USERLOG_SITE_MAP;
	public static final String SITE = "Site";
	public static final String SITE_ADDSITE = "AddSite";
	public static final String SITE_DELSITE = "DelSite";
	public static final String SITE_UPDATESITE = "UpdateSite";
	public static final Mapx USERLOG_CATALOG_MAP;
	public static final String CATALOG = "Catalog";
	public static final String CATALOG_ADDCATALOG = "AddCataLog";
	public static final String CATALOG_DELCATALOG = "DelCataLog";
	public static final String CATALOG_UPDATECATALOG = "UpdateCataLog";
	public static final Mapx USERLOG_ARTICLE_MAP;
	public static final String ARTICLE = "Article";
	public static final String ARTICLE_SAVEARTICLE = "AddArticle";
	public static final String ARTICLE_DELARTICLE = "DelArticle";
	public static final String ARTICLE_PUBLISHARTICLE = "PublishArticle";
	public static final String ARTICLE_TOPUBLISHARTICLE = "ToPublishArticle";
	public static final String ARTICLE_MOVEARTICLE = "MoveArticle";
	public static final String ARTICLE_TOPARTICLE = "TopArticle";
	public static final String ARTICLE_NOTTOPARTICLE = "NotTopArticle";
	public static final String ARTICLE_UPARTICLE = "UpArticle";
	public static final String ARTICLE_DOWNARTICLE = "DownArticle";
	public static final String ARTICLE_COPYARTICLE = "CopyArticle";
	public static final Mapx USERLOG_RESOURCE_MAP;
	public static final String RESOURCE = "Resource";
	public static final String RESOURCE_ADDTYPEIMAGE = "AddTypeImage";
	public static final String RESOURCE_ADDIMAGE = "AddImage";
	public static final String RESOURCE_EDITIMAGE = "EditImage";
	public static final String RESOURCE_PUBLISTHIMAGE = "PublishImage";
	public static final String RESOURCE_COPYIMAGE = "CopyImage";
	public static final String RESOURCE_MOVEIMAGE = "MoveImage";
	public static final String RESOURCE_DELIMAGE = "DelImage";
	public static final String RESOURCE_ADDTYPEVIDEO = "AddTypeVideo";
	public static final String RESOURCE_ADDVIDEO = "AddVideo";
	public static final String RESOURCE_EDITVIDEO = "EditVideo";
	public static final String RESOURCE_PUBLISTHVIDEO = "PublishVideo";
	public static final String RESOURCE_COPYVIDEO = "CopyVideo";
	public static final String RESOURCE_MOVEVIDEO = "MoveVideo";
	public static final String RESOURCE_DELVIDEO = "DelVideo";
	public static final String RESOURCE_ADDTYPEAUDIO = "AddTypeAudio";
	public static final String RESOURCE_ADDAUDIO = "AddAudio";
	public static final String RESOURCE_EDITAUDIO = "EditAudio";
	public static final String RESOURCE_PUBLISTHAUDIO = "PublishAudio";
	public static final String RESOURCE_COPYAUDIO = "CopyAudio";
	public static final String RESOURCE_MOVEAUDIO = "MoveAudio";
	public static final String RESOURCE_DELAUDIO = "DelAudio";
	public static final String RESOURCE_ADDTYPEATTACHMENT = "AddTypeAttachment";
	public static final String RESOURCE_ADDATTACHMENT = "AddAttachment";
	public static final String RESOURCE_EDITATTACHMENT = "EditAttachment";
	public static final String RESOURCE_PUBLISTHATTACHMENT = "PublishAttachment";
	public static final String RESOURCE_COPYATTACHMENT = "CopyAttachment";
	public static final String RESOURCE_MOVEATTACHMENT = "MoveAttachment";
	public static final String RESOURCE_DELATTACHMENT = "DelAttachment";
	public static final Mapx USERLOG_USER_MAP;
	public static final String USER = "User";
	public static final String USER_DELUSER = "DelUser";
	public static final String USER_DELROLE = "DelROLE";
	public static final String USER_EDITPASSWORD = "EditPassword";
	public static final Mapx USERLOG_SYSTEM_MAP;
	public static final String SYSTEM = "System";
	public static final String SYSTEM_DELBRANCH = "DelBranch";
	public static final String SYSTEM_DELCODE = "DelCode";
	public static final String SYSTEM_DELCONFIG = "DelConfig";
	public static final String SYSTEM_DELSCHEDULE = "DelSchedule";
	public static final String SYSTEM_DELMENU = "DelMenu";
	public static final DataTableMap USERLOG_MAP;
	public static final Mapx USERLOG_SELECT_MAP;

	static {
		USERLOG_LOGTYPE_MAP.put("0", "");
		USERLOG_LOGTYPE_MAP.put("Login", "登陆");
		USERLOG_LOGTYPE_MAP.put("Logout", "退出");

		USERLOG_FORUM_MAP = new Mapx();

		USERLOG_FORUM_MAP.put("0", "");
		USERLOG_FORUM_MAP.put("TopTheme", "置顶主题");
		USERLOG_FORUM_MAP.put("TopCancel", "取消置顶");
		USERLOG_FORUM_MAP.put("DelTheme", "删除主题");
		USERLOG_FORUM_MAP.put("BestTheme", "设置精华");
		USERLOG_FORUM_MAP.put("BestCancel", "取消精华");
		USERLOG_FORUM_MAP.put("UpTheme", "提升主题");
		USERLOG_FORUM_MAP.put("DownTheme", "下沉主题");
		USERLOG_FORUM_MAP.put("MoveTheme", "移动主题");
		USERLOG_FORUM_MAP.put("EditPost", "编辑帖子");
		USERLOG_FORUM_MAP.put("DelPost", "删除帖子");
		USERLOG_FORUM_MAP.put("HidePost", "屏蔽帖子");

		USERLOG_SITE_MAP = new Mapx();

		USERLOG_SITE_MAP.put("0", "");
		USERLOG_SITE_MAP.put("AddSite", "增加站点");
		USERLOG_SITE_MAP.put("DelSite", "删除站点");
		USERLOG_SITE_MAP.put("UpdateSite", "修改站点");

		USERLOG_CATALOG_MAP = new Mapx();

		USERLOG_CATALOG_MAP.put("0", "");
		USERLOG_CATALOG_MAP.put("AddCataLog", "增加栏目");
		USERLOG_CATALOG_MAP.put("DelCataLog", "删除栏目");
		USERLOG_CATALOG_MAP.put("UpdateCataLog", "修改栏目");

		USERLOG_ARTICLE_MAP = new Mapx();

		USERLOG_ARTICLE_MAP.put("0", "");
		USERLOG_ARTICLE_MAP.put("AddArticle", "增加文章");
		USERLOG_ARTICLE_MAP.put("DelArticle", "删除文章");
		USERLOG_ARTICLE_MAP.put("PublishArticle", "发布文章");
		USERLOG_ARTICLE_MAP.put("ToPublishArticle", "转为待发布文章");
		USERLOG_ARTICLE_MAP.put("MoveArticle", "转移文章");
		USERLOG_ARTICLE_MAP.put("TopArticle", "置顶文章");
		USERLOG_ARTICLE_MAP.put("NotTopArticle", "取消置顶文章");
		USERLOG_ARTICLE_MAP.put("UpArticle", "文章上线");
		USERLOG_ARTICLE_MAP.put("DownArticle", "文章下线");
		USERLOG_ARTICLE_MAP.put("CopyArticle", "复制文章");

		USERLOG_RESOURCE_MAP = new Mapx();

		USERLOG_RESOURCE_MAP.put("0", "");
		USERLOG_RESOURCE_MAP.put("AddTypeImage", "新增图片分类");
		USERLOG_RESOURCE_MAP.put("AddImage", "增加图片");
		USERLOG_RESOURCE_MAP.put("EditImage", "编辑图片");
		USERLOG_RESOURCE_MAP.put("PublishImage", "发布图片");
		USERLOG_RESOURCE_MAP.put("CopyImage", "复制图片");
		USERLOG_RESOURCE_MAP.put("MoveImage", "移动图片");
		USERLOG_RESOURCE_MAP.put("DelImage", "删除图片");
		USERLOG_RESOURCE_MAP.put("AddTypeVideo", "新增视频分类");
		USERLOG_RESOURCE_MAP.put("AddVideo", "增加视频");
		USERLOG_RESOURCE_MAP.put("EditVideo", "编辑视频");
		USERLOG_RESOURCE_MAP.put("PublishVideo", "发布视频");
		USERLOG_RESOURCE_MAP.put("CopyVideo", "复制视频");
		USERLOG_RESOURCE_MAP.put("MoveVideo", "移动视频");
		USERLOG_RESOURCE_MAP.put("DelVideo", "删除视频");
		USERLOG_RESOURCE_MAP.put("AddTypeAudio", "新增音频分类");
		USERLOG_RESOURCE_MAP.put("AddAudio", "增加音频");
		USERLOG_RESOURCE_MAP.put("EditAudio", "编辑音频");
		USERLOG_RESOURCE_MAP.put("PublishAudio", "发布音频");
		USERLOG_RESOURCE_MAP.put("CopyAudio", "复制音频");
		USERLOG_RESOURCE_MAP.put("MoveAudio", "移动音频");
		USERLOG_RESOURCE_MAP.put("DelAudio", "删除音频");
		USERLOG_RESOURCE_MAP.put("AddTypeAttachment", "新增附件分类");
		USERLOG_RESOURCE_MAP.put("AddAttachment", "增加附件");
		USERLOG_RESOURCE_MAP.put("EditAttachment", "编辑附件");
		USERLOG_RESOURCE_MAP.put("PublishAttachment", "发布附件");
		USERLOG_RESOURCE_MAP.put("CopyAttachment", "复制附件");
		USERLOG_RESOURCE_MAP.put("MoveAttachment", "移动附件");
		USERLOG_RESOURCE_MAP.put("DelAttachment", "删除附件");

		USERLOG_USER_MAP = new Mapx();

		USERLOG_USER_MAP.put("0", "");
		USERLOG_USER_MAP.put("DelUser", "删除用户");
		USERLOG_USER_MAP.put("DelROLE", "删除角色");
		USERLOG_USER_MAP.put("EditPassword", "修改密码");

		USERLOG_SYSTEM_MAP = new Mapx();

		USERLOG_SYSTEM_MAP.put("0", "");
		USERLOG_SYSTEM_MAP.put("DelBranch", "删除机构");
		USERLOG_SYSTEM_MAP.put("DelCode", "删除代码");
		USERLOG_SYSTEM_MAP.put("DelConfig", "删除配置项");
		USERLOG_SYSTEM_MAP.put("DelSchedule", "删除定时任务");
		USERLOG_SYSTEM_MAP.put("DelMenu", "删除菜单");

		USERLOG_MAP = new DataTableMap();

		USERLOG_MAP.put("Log", USERLOG_LOGTYPE_MAP);
		USERLOG_MAP.put("Forum", USERLOG_FORUM_MAP);
		USERLOG_MAP.put("Site", USERLOG_SITE_MAP);
		USERLOG_MAP.put("Catalog", USERLOG_CATALOG_MAP);
		USERLOG_MAP.put("Article", USERLOG_ARTICLE_MAP);
		USERLOG_MAP.put("User", USERLOG_USER_MAP);
		USERLOG_MAP.put("System", USERLOG_SYSTEM_MAP);
		USERLOG_MAP.put("Resource", USERLOG_RESOURCE_MAP);

		USERLOG_SELECT_MAP = new Mapx();

		USERLOG_SELECT_MAP.put("Log", "登录状态");
		USERLOG_SELECT_MAP.put("Forum", "论坛操作");
		USERLOG_SELECT_MAP.put("Site", "站点操作");
		USERLOG_SELECT_MAP.put("Catalog", "栏目操作");
		USERLOG_SELECT_MAP.put("Article", "文章操作");
		USERLOG_SELECT_MAP.put("User", "用户角色操作");
		USERLOG_SELECT_MAP.put("System", "系统管理");
		USERLOG_SELECT_MAP.put("Resource", "媒体库操作");
	}

	public void menuVisit() {
		String id = this.request.valueArray()[0].toString();
		if (!XString.verify(id, "Int")) {
			return;
		}
		DataTable dt = new QueryBuilder(
				"select Name,(select Name from ZDMenu where id=a.ParentID) from ZDMenu a where id=?",
				id).executeDataTable();
		String menu = dt.getString(0, 1) + "->" + dt.getString(0, 0);
		ZDUserLogSchema userlog = new ZDUserLogSchema();
		userlog.setUserName(User.getUserName());
		userlog.setIP(this.request.getClientIP());
		userlog.setAddTime(new Date());
		userlog.setLogID(NoUtil.getMaxID("LogID"));
		userlog.setLogType("Menu");
		userlog.setLogMessage("访问菜单：" + menu);
		userlog.insert();
		this.response.setStatus(1);
	}

	public void logout() {
		if (log("Menu", "", "退出系统", this.request.getClientIP()))
			this.response.setStatus(1);
		else
			this.response.setStatus(0);
	}

	public static void dg1DataBind(DataGridAction dga) {
		String searchUser = dga.getParams().getString("SearchUser");
		String ip = dga.getParams().getString("IP");
		String logMessage = dga.getParams().getString("LogMessage");
		String startDate = dga.getParams().getString("StartDate");
		String endDate = dga.getParams().getString("EndDate");
		String logType = dga.getParams().getString("LogType");
		String subType = dga.getParams().getString("SubType");

		QueryBuilder qb = new QueryBuilder(
				"select a.*,(select b.RealName from zduser b where b.UserName = a.UserName) as RealName from ZDUserLog a where 1=1");
		if (XString.isNotEmpty(searchUser)) {
			qb.append(" and UserName like ? ", "%" + searchUser + "%");
		}
		if (XString.isNotEmpty(ip)) {
			qb.append(" and IP like ? ", "%" + ip + "%");
		}
		if (XString.isNotEmpty(logType)) {
			qb.append(" and LogType like ? ", "%" + logType + "%");
		}
		if ((XString.isNotEmpty(subType)) && (!"0".equals(subType))) {
			qb.append(" and SubType like ? ", "%" + subType + "%");
		}
		if (XString.isNotEmpty(logMessage)) {
			qb.append(" and LogMessage like ? ", "%" + logMessage + "%");
		}
		if ((XString.isNotEmpty(startDate))
				&& (XString.isNotEmpty(endDate))) {
			qb.append(" and AddTime >=?", startDate);
			qb.append(" and AddTime<=?", endDate);
		}
		qb.append(" order by addtime desc");
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dt.decodeColumn("LogType", USERLOG_SELECT_MAP);
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	public static Mapx init(Mapx params) {
		Date date = new Date();
		String str = DateUtil.toString(date);
		params.put("Time", str);
		params.put("LogType", HtmlUtil.mapxToOptions(USERLOG_SELECT_MAP, true));
		return params;
	}

	public static Mapx initDialog(Mapx params) {
		ZDUserLogSchema userLog = new ZDUserLogSchema();
		userLog.setUserName(params.getString("UserName"));
		userLog.setLogID(params.getString("LogID"));
		userLog.fill();
		params = userLog.toMapx();
		params.put("LogType", USERLOG_SELECT_MAP
				.getString(userLog.getLogType()));
		params.put("RealName", getUserRealName(userLog.getUserName()));
		return params;
	}
	public static String getUserRealName(String userName) {
		ZDUserSchema user = PlatformCache.getUser(userName);
		if (user == null) {
			return "";
		}
		return user.getRealName();
	}
	public void del() {
		String IDs = $V("ids");
		if (!XString.checkID(IDs)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		ZDUserLogSet set = new ZDUserLogSchema().query(new QueryBuilder(
				"where LogID in (" + IDs + ")"));
		if (set.deleteAndBackup())
			this.response.setLogInfo(1, "删除成功");
		else
			this.response.setLogInfo(0, "删除失败");
	}

	public void delAll() throws SQLException {
		QueryBuilder qb = new QueryBuilder("delete from ZDUserLog");
		if (qb.executeNoQuery() > 0)
			this.response.setLogInfo(1, "日志清空成功");
		else
			this.response.setLogInfo(0, "操作失败");
	}

	public void delByTime() throws SQLException {
		int month = Integer.parseInt($V("month"));
		long timeNow = new Date().getTime();
		long time = timeNow - 2592000000L * month;
		Date date = new Date(time);
		String sql = "delete from zdUserlog where addtime < ?";
		System.out.println(sql);
		QueryBuilder qb = new QueryBuilder(sql, date);
		if (qb.executeNoQuery() >= 0)
			this.response.setLogInfo(1, "删除成功");
		else
			this.response.setLogInfo(0, "操作失败");
	}

	public static boolean log(String logType, String subType,
			String logMessage, String ip) {
		return log(logType, subType, logMessage, ip, User.getUserName(), null);
	}

	public static boolean log(String logType, String subType,
			String logMessage, String ip, Transaction trans) {
		return log(logType, subType, logMessage, ip, User.getUserName(), trans);
	}

	public static boolean log(String logType, String subType,
			String logMessage, String ip, String userName) {
		return log(logType, subType, logMessage, ip, userName, null);
	}

	public static boolean log(String logType, String subType,
			String logMessage, String ip, String userName, Transaction trans) {
		ZDUserLogSchema userlog = new ZDUserLogSchema();
		userlog.setUserName(userName);
		userlog.setIP(ip);
		userlog.setAddTime(new Date());
		userlog.setLogID(NoUtil.getMaxID("LogID"));
		userlog.setLogType(logType);
		userlog.setSubType(subType);
		userlog.setLogMessage(logMessage);
		if (trans == null) {
			return userlog.insert();
		}
		trans.add(userlog, OperateType.INSERT);

		return true;
	}

	public static DataTable getSubType(Mapx params) {
		String logType = params.getString("LogType");
		if ((XString.isEmpty(logType)) || ("0".equals(logType))) {
			return null;
		}
		DataTableMap map = (DataTableMap) USERLOG_MAP.get(logType);
		return map.toDataTable();
	}
}
