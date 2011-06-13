package com.xdarkness.platform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.abigdreamer.java.net.User;
import com.abigdreamer.java.net.cache.CacheManager;
import com.abigdreamer.java.net.jaf.WebConfig;
import com.abigdreamer.java.net.orm.data.DataRow;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.sql.QueryBuilder;
import com.abigdreamer.java.net.util.Mapx;
import com.abigdreamer.java.net.util.XString;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.page.MenuPage;
import com.xdarkness.platform.page.RoleTabCatalogPage;

/**
 * 
 * @author Darkness 
 * create on 2010 2010-12-3 下午03:06:56
 * @version 1.0
 * @since JDP 1.0
 */
public class Priv {
	public static final String MENU = "menu";
	public static final String MENU_BROWSE = "menu_browse";
	public static final Mapx MENU_MAP = new Mapx();
	public static final String SITE = "site";
	public static final String SITE_BROWSE = "site_browse";
	public static final String SITE_MANAGE = "site_manage";
	public static final Mapx SITE_MAP;
	public static final String ARTICLE = "article";
	public static final String ARTICLE_BROWSE = "article_browse";
	public static final String ARTICLE_MANAGE = "article_manage";
	public static final String ARTICLE_MODIFY = "article_modify";
	public static final String ARTICLE_AUDIT = "article_audit";
	public static final Mapx ARTICLE_MAP;
	public static final String IMAGE = "image";
	public static final String IMAGE_BROWSE = "image_browse";
	public static final String IMAGE_MANAGE = "image_manage";
	public static final String IMAGE_MODIFY = "image_modify";
	public static final Mapx IMAGE_MAP;
	public static final String VIDEO = "video";
	public static final String VIDEO_BROWSE = "video_browse";
	public static final String VIDEO_MANAGE = "video_manage";
	public static final String VIDEO_MODIFY = "video_modify";
	public static final Mapx VIDEO_MAP;
	public static final String AUDIO = "audio";
	public static final String AUDIO_BROWSE = "audio_browse";
	public static final String AUDIO_MANAGE = "audio_manage";
	public static final String AUDIO_MODIFY = "audio_modify";
	public static final Mapx AUDIO_MAP;
	public static final String ATTACH = "attach";
	public static final String ATTACH_BROWSE = "attach_browse";
	public static final String ATTACH_MANAGE = "attach_manage";
	public static final String ATTACH_MODIFY = "attach_modify";
	public static final Mapx ATTACH_MAP;
	public static final Mapx PRIV_MAP;
	public static final String OWNERTYPE_USER = "U";
	private static Map UserPrivMap;

	static {
		MENU_MAP.put("menu_browse", "菜单浏览");

		SITE_MAP = new Mapx();

		SITE_MAP.put("site_browse", "站点浏览");
		SITE_MAP.put("site_manage", "站点管理");

		ARTICLE_MAP = new Mapx();

		ARTICLE_MAP.put("article_browse", "文章栏目浏览");
		ARTICLE_MAP.put("article_manage", "文章栏目管理");
		ARTICLE_MAP.put("article_modify", "文章管理");
		ARTICLE_MAP.put("article_audit", "文章审核");

		IMAGE_MAP = new Mapx();

		IMAGE_MAP.put("image_browse", "图片栏目浏览");
		IMAGE_MAP.put("image_manage", "图片栏目管理");
		IMAGE_MAP.put("image_modify", "图片管理");

		VIDEO_MAP = new Mapx();

		VIDEO_MAP.put("video_browse", "视频栏目浏览");
		VIDEO_MAP.put("video_manage", "视频栏目管理");
		VIDEO_MAP.put("video_modify", "视频管理");

		AUDIO_MAP = new Mapx();

		AUDIO_MAP.put("audio_browse", "音频栏目浏览");
		AUDIO_MAP.put("audio_manage", "音频栏目管理");
		AUDIO_MAP.put("audio_modify", "音频管理");

		ATTACH_MAP = new Mapx();

		ATTACH_MAP.put("attach_browse", "附件栏目浏览");
		ATTACH_MAP.put("attach_manage", "附件栏目管理");
		ATTACH_MAP.put("attach_modify", "附件管理");

		PRIV_MAP = new Mapx();

		PRIV_MAP.put("menu", MENU_MAP);
		PRIV_MAP.put("site", SITE_MAP);
		PRIV_MAP.put("article", ARTICLE_MAP);
		PRIV_MAP.put("image", IMAGE_MAP);
		PRIV_MAP.put("video", VIDEO_MAP);
		PRIV_MAP.put("audio", AUDIO_MAP);
		PRIV_MAP.put("attach", ATTACH_MAP);

		UserPrivMap = new Hashtable();
	}

	public static void updateAllPriv(String UserName) {
		Object obj = new QueryBuilder(
				"select UserName from ZDUser where UserName=?", UserName)
				.executeOneValue();
		if (obj == null) {
			UserPrivMap.remove(UserName);
			return;
		}
		Object[] ks = PRIV_MAP.keyArray();
		for (int i = 0; i < PRIV_MAP.size(); i++)
			updatePriv(UserName, (String) ks[i]);
	}

	public static void updatePriv(String UserName, String PrivType) {
		String sql = "select ID,Code,Value from ZDPrivilege where OwnerType=? and Owner=? and PrivType=?";
		QueryBuilder qb = new QueryBuilder(sql);
		qb.add("U");
		qb.add(UserName);
		qb.add(PrivType);
		DataTable dt = qb.executeDataTable();
		Map PrivTypeMap = getPrivTypeMap(UserName, PrivType);
		RolePriv.getMapFromDataTable(PrivTypeMap, dt);
	}

	private static Map getPrivTypeMap(String UserName, String PrivType) {
		Map UserNamePrivMap = (Map) UserPrivMap.get(UserName);
		if (UserNamePrivMap == null) {
			UserNamePrivMap = new Hashtable();
			UserPrivMap.put(UserName, UserNamePrivMap);
			updateAllPriv(UserName);
		}
		Map PrivTypeMap = (Map) UserNamePrivMap.get(PrivType);
		if (PrivTypeMap == null) {
			PrivTypeMap = new HashMap();
			UserNamePrivMap.put(PrivType, PrivTypeMap);
		}
		return PrivTypeMap;
	}

	public static boolean getPriv(String PrivType, String ID, String Code) {
		return getPriv(User.getUserName(), PrivType, ID, Code);
	}

	public static boolean getPriv(String UserName, String PrivType, String ID,
			String Code) {
		if ("admin".equalsIgnoreCase(UserName)) {
			return true;
		}
		String value = getUserPriv(UserName, PrivType, ID, Code);
		if ("1".equals(value))
			return true;
		if ("-1".equals(value)) {
			return false;
		}
		List roleCodeList = getRoleCodesByUserName(UserName);
		if ((roleCodeList != null) && (roleCodeList.size() != 0)) {
			return RolePriv.getRolePriv((String[]) roleCodeList
					.toArray(new String[roleCodeList.size()]), PrivType, ID,
					Code);
		}
		return false;
	}

	public static List getRoleCodesByUserName(String userName) {
		String roles = (String) CacheManager.get("Platform", "UserRole",
				userName);
		if (roles == null) {
			return null;
		}
		String[] arr = roles.split(",");
		ArrayList list = new ArrayList();
		for (int i = 0; i < arr.length; i++) {
			if (XString.isNotEmpty(arr[i])) {
				list.add(arr[i]);
			}
		}
		return list;
	}
	
	private static String getUserPriv(String UserName, String PrivType,
			String ID, String Code) {
		if ("menu".equals(PrivType)) {
			Map map = getPrivTypeMap(UserName, PrivType);
			map = (Map) map.get(ID);
			if (map != null) {
				return (String) map.get(Code);
			}
			return null;
		}
		if ("site".equals(PrivType)) {
			Map map = getPrivTypeMap(UserName, PrivType);
			map = (Map) map.get(ID);
			if (map != null) {
				return (String) map.get(Code);
			}
			return null;
		}

		Map map = getPrivTypeMap(UserName, PrivType);
		map = (Map) map.get(ID);
		if (map != null) {
			return (String) map.get(Code);
		}
		return null;
	}

	public static DataTable getCatalogPrivDT(String userName, String siteID,
			String PrivType) {
		return getCatalogPrivDT(userName, siteID, PrivType, false);
	}

	public static DataTable getCatalogPrivDT(String userName, String siteID,
			String PrivType, boolean isWebMode) {
		StringBuffer sb = new StringBuffer();
		sb.append(",'" + userName + "' as UserName");
		Object[] ks = ((Mapx) PRIV_MAP.get(PrivType)).keyArray();
		for (int i = 0; i < ((Mapx) PRIV_MAP.get(PrivType)).size(); i++) {
			sb.append(",'' as " + ks[i]);
		}

		String sql = "select ID,Name,0 as TreeLevel ,'site' as PrivType"
				+ sb.toString().replaceAll("''", "''")
				+ ",'' as ParentInnerCode from ZCSite a where a.ID = ?";
		DataTable siteDT = new QueryBuilder(sql, siteID).executeDataTable();
		if (siteDT.getRowCount() == 0) {
			return new DataTable();
		}

		String catalogType = RoleTabCatalogPage.CatalogTypeMap.getString(PrivType);
		sql = "select InnerCode as ID,Name,TreeLevel ,'"
				+ PrivType
				+ "' as PrivType"
				+ sb.toString()
				+ ", (select b.InnerCode from ZCCatalog b where a.parentid=b.id) as ParentInnerCode from ZCCatalog a where Type ="
				+ catalogType
				+ " and a.SiteID = ? order by orderflag,innercode ";
		DataTable catalogDT = new QueryBuilder(sql, siteID).executeDataTable();

		DataRow dr = null;
		String value = "1";
		if (isWebMode) {
			value = "√";
		}
		for (int i = 0; i < siteDT.getRowCount(); i++) {
			dr = siteDT.getDataRow(i);
			for (int j = 0; j < dr.getColumnCount(); j++) {
				String columnName = dr.getDataColumn(j).getColumnName()
						.toLowerCase();
				if (columnName.indexOf("_") > 0) {
					dr.set(j, getPriv(userName, "site", dr.getString("ID"),
							columnName) ? value : "");
				}
			}
		}
		for (int i = 0; i < catalogDT.getRowCount(); i++) {
			dr = catalogDT.getDataRow(i);
			for (int j = 0; j < dr.getColumnCount(); j++) {
				String columnName = dr.getDataColumn(j).getColumnName()
						.toLowerCase();
				if (columnName.indexOf("_") > 0) {
					dr.set(j, getPriv(userName, PrivType, dr.getString("ID"),
							columnName) ? value : "");
				}
			}
		}
		catalogDT.insertRow(siteDT.getDataRow(0), 0);
		return catalogDT;
	}

	public static DataTable getSitePrivDT(String userName, String siteID,
			String PrivType) {
		String s = "";
		StringBuffer sb = new StringBuffer();
		sb.append(",'" + userName + "' as UserName");
		Object[] ks = SITE_MAP.keyArray();
		for (int i = 0; i < SITE_MAP.size(); i++) {
			sb.append(",'" + s + "' as " + ks[i].toString());
		}
		String sql = "select ID,Name,0 as TreeLevel ,'site' as PrivType "
				+ sb.toString()
				+ " from ZCSite a where id = ? order by orderflag ,id";
		DataTable siteDT = new QueryBuilder(sql, siteID).executeDataTable();
		DataRow dr = null;
		for (int i = 0; i < siteDT.getRowCount(); i++) {
			dr = siteDT.getDataRow(i);
			for (int j = 0; j < dr.getColumnCount(); j++) {
				String columnName = dr.getDataColumn(j).getColumnName()
						.toLowerCase().toLowerCase();
				if (columnName.indexOf("_") > 0) {
					dr.set(j, getPriv(userName, PrivType, dr.getString("ID"),
							columnName) ? "1" : "");
				}
			}
		}
		return siteDT;
	}

	public static boolean isValidURL(String URL) {
		if (XString.isNotEmpty(URL)) {
			URL = URL.replaceAll("/+", "/");
			if (URL.startsWith("/")) {
				URL = URL.substring(1);
			}
		}
		if (!WebConfig.isInstalled) {
			return true;
		}
		String menuID = MenuPage.MenuCacheMap.getString(URL);

		return (!XString.isNotEmpty(menuID))
				|| (getPriv(User.getUserName(), "menu", ApplicationPage
						.getCurrentSiteID()
						+ "-" + menuID, "menu_browse"));
	}
}
