package com.xdarkness.cms.pub;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xdarkness.cms.dataservice.ColumnUtil;
import com.xdarkness.cms.site.CatalogConfig;
import com.xdarkness.cms.template.HtmlNameParser;
import com.xdarkness.cms.template.HtmlNameRule;
import com.xdarkness.platform.Priv;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCCatalogConfigSchema;
import com.xdarkness.schema.ZCCatalogConfigSet;
import com.xdarkness.schema.ZCCatalogSchema;
import com.xdarkness.schema.ZCSiteSchema;
import com.xdarkness.framework.orm.data.DataRow;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.ServletUtil;

public class CatalogUtil {
	static {
		CatalogConfig.initCatalogConfig();
	}

	public static ZCCatalogSchema getSchema(String catalogID) {
		return CMSCache.getCatalog(catalogID);
	}

	public static String getName(String catalogID) {
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog == null) {
			return null;
		}
		return catalog.getName();
	}

	public static String getFullName(String catalogID, String separete) {
		StringBuffer sb = new StringBuffer();
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog == null) {
			return null;
		}

		if (catalog.getParentID() != 0L) {
			sb.append(getFullName(catalog.getParentID(), separete));
		}
		if (sb.toString().length() > 0) {
			sb.append(separete);
		}

		sb.append(catalog.getName());

		return sb.toString();
	}

	public static String getFullName(long catalogID, String separete) {
		return getFullName(catalogID+"", separete);
	}

	public static String getFullName(long catalogID) {
		return getFullName(catalogID, "/");
	}

	public static String getNameByInnerCode(String catalogInnerCode) {
		ZCCatalogSchema catalog = CMSCache
				.getCatalogByInnerCode(catalogInnerCode);
		if (catalog == null) {
			return null;
		}
		return catalog.getName();
	}

	public static String getIDByNames(String names) {
		return getIDByNames(ApplicationPage.getCurrentSiteID(), names);
	}

	public static String getIDByNames(long siteID, String names) {
		return getIDByNames(siteID+"", names);
	}

	public static String getIDByNames(String siteID, String names) {
		if (XString.isEmpty(names)) {
			return null;
		}
		if (names.startsWith("/")) {
			names = names.substring(1);
		}
		if (names.endsWith("/")) {
			names = names.substring(0, names.length() - 1);
		}
		String[] catalogNames = names.split("/");
		int catalogLenth = catalogNames.length;
		String id = "";
		if (catalogLenth > 0) {
			if (catalogLenth > 1) {
				String catalogStr = XString.join(catalogNames, "_");
				id = getCatalogIDByNames(siteID, catalogStr);
			} else {
				id = getCatalogIDByNames(siteID, catalogNames[0]);
			}
		}
		return id;
	}

	private static String getCatalogIDByNames(String siteID, String names) {
		if (XString.isEmpty(names)) {
			return null;
		}
		if (names.startsWith("_")) {
			names = names.substring(1);
		}
		if (names.endsWith("_")) {
			names = names.substring(0, names.length() - 1);
		}
		long catalogID = 0L;
		String[] catalogNames = names.split("_");
		if (catalogNames.length <= 0)
			return null;
		if (catalogNames.length == 1) {
			ZCCatalogSchema catalog = null;
			if (XString.isDigit(catalogNames[0])) {
				catalog = CMSCache.getCatalog(Long.parseLong(catalogNames[0]));
			}

			if (catalog == null) {
				catalog = CMSCache.getCatalog(siteID, catalogNames[0]);
			}

			if (catalog == null) {
				return null;
			}
			catalogID = catalog.getID();
		} else if (catalogNames.length > 1) {
			for (int i = 0; i < catalogNames.length; i++) {
				if (i == 0) {
					ZCCatalogSchema catalog = null;
					if (XString.isDigit(catalogNames[i])) {
						catalog = CMSCache.getCatalog(Long
								.parseLong(catalogNames[i]));
					}

					if (catalog == null) {
						catalog = CMSCache.getCatalog(siteID, catalogNames[i]);
					}

					if (catalog == null) {
						return null;
					}
					catalogID = catalog.getID();
				} else {
					ZCCatalogSchema catalog = null;
					if (XString.isDigit(catalogNames[i])) {
						catalog = CMSCache.getCatalog(Long
								.parseLong(catalogNames[i]));
					}

					if (catalog == null) {
						catalog = CMSCache.getCatalog(siteID, catalogID,
								catalogNames[i]);
					}

					if (catalog == null) {
						return null;
					}
					catalogID = catalog.getID();
				}
			}
		}
		return String.valueOf(catalogID);
	}

	public static String getIDsByName(String names) {
		return getIDsByName(ApplicationPage.getCurrentSiteID(), names);
	}

	public static String getIDsByName(long siteID, String names) {
		return getIDsByName(siteID+"", names);
	}

	public static String getIDsByName(String siteID, String names) {
		if (XString.isEmpty(names)) {
			return null;
		}
		if (names.startsWith(",")) {
			names = names.substring(1);
		}
		if (names.endsWith(",")) {
			names = names.substring(0, names.length() - 1);
		}
		String[] catalogNames = names.split(",");
		int catalogLenth = catalogNames.length;
		StringBuffer sb = new StringBuffer(40);
		if (catalogLenth > 0) {
			for (int i = 0; i < catalogLenth; i++) {
				String catalogID = getIDByNames(siteID, catalogNames[i]);
				if (XString.isEmpty(catalogID)) {
					continue;
				}
				if (i != 0) {
					sb.append(",");
				}
				sb.append(catalogID);
			}
		}
		return sb.toString();
	}

	public static String getIDByName(String catalogName) {
		return getIDByName(ApplicationPage.getCurrentSiteID(), catalogName);
	}

	public static String getIDByName(long siteID, String catalogName) {
		return getIDByName(siteID+"", catalogName);
	}

	public static String getIDByName(String siteID, String catalogName) {
		ZCCatalogSchema catalog = null;
		if (XString.isDigit(catalogName)) {
			catalog = CMSCache.getCatalog(Long.parseLong(catalogName));
		}
		if (catalog == null) {
			catalog = CMSCache.getCatalog(siteID, catalogName);
		}
		if (catalog == null) {
			return null;
		}
		return String.valueOf(catalog.getID());
	}

	public static String getIDByName(long siteID, long parentID,
			String catalogName) {
		return getIDByName(siteID+"", parentID+"", catalogName);
	}

	public static String getIDByName(long siteID, String parentID,
			String catalogName) {
		return getIDByName(siteID+"", parentID, catalogName);
	}

	public static String getIDByName(String siteID, long parentID,
			String catalogName) {
		return getIDByName(siteID, parentID+"", catalogName);
	}

	public static String getIDByName(String siteID, String parentID,
			String catalogName) {
		ZCCatalogSchema catalog = null;
		if (XString.isDigit(catalogName)) {
			catalog = CMSCache.getCatalog(Long.parseLong(catalogName));
		}
		if (catalog == null) {
			catalog = CMSCache.getCatalog(siteID, Long.parseLong(parentID),
					catalogName);
		}
		if (catalog == null) {
			return null;
		}
		return String.valueOf(catalog.getID());
	}

	public static String getIDByInnerCode(String catalogInnerCode) {
		ZCCatalogSchema catalog = CMSCache
				.getCatalogByInnerCode(catalogInnerCode);
		if (catalog != null) {
			return catalog.getID() + "";
		}
		return null;
	}

	public static String getPath(long catalogID) {
		return getPath(catalogID+"");
	}

	public static String getPath(String catalogID) {
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		ZCSiteSchema site = SiteUtil.getSchema(catalog.getSiteID() + "");
		String fullPath = "";
		String url = catalog.getURL();
		if (XString.isNotEmpty(url)) {
			if ((url.startsWith("http://")) || (url.startsWith("https://"))) {
				fullPath = getFullPath(catalogID);
			} else if (url.startsWith("#")) {
				fullPath = getFullPath(catalogID);
			} else if ((url.startsWith("/"))
					&& ((url.endsWith(".html")) || (url.endsWith(".htm"))
							|| (url.endsWith(".shtml"))
							|| (url.endsWith(".jsp")) || (url.endsWith(".do")))) {
				fullPath = getFullPath(catalogID);
			} else {
				HtmlNameParser h = new HtmlNameParser(site.toDataRow(), catalog
						.toDataRow(), null, url);
				HtmlNameRule rule = h.getNameRule();
				fullPath = rule.getFullPath() + "/";
				fullPath = fullPath.replaceAll("/+", "/");
			}
		} else
			fullPath = getFullPath(catalogID);

		return fullPath;
	}

	public static String getLink(long catalogID, String levelStr) {
		return getLink(catalogID+"", levelStr);
	}

	public static String getLink(String catalogID, String levelStr) {
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		ZCSiteSchema site = SiteUtil.getSchema(catalog.getSiteID());
		String ext = ServletUtil.getUrlExtension(getSchema(catalogID)
				.getListTemplate());
		String indexPage = "";
		if (ext.equals(".jsp"))
			indexPage = indexPage + "index" + ext;
		else {
			indexPage = indexPage + "index.shtml";
		}
		String linkUrl = "";
		String url = catalog.getURL();
		if (XString.isNotEmpty(url)) {
			if ((url.startsWith("http://")) || (url.startsWith("https://"))) {
				linkUrl = url;
			} else if (url.startsWith("#")) {
				linkUrl = url;
			} else if ((url.startsWith("/"))
					&& ((url.endsWith(".html")) || (url.endsWith(".htm"))
							|| (url.endsWith(".shtml"))
							|| (url.endsWith(".jsp")) || (url.endsWith(".do")))) {
				url = url.substring(1);
				linkUrl = levelStr + url;
				linkUrl.replaceAll("/+", "/");
			} else {
				HtmlNameParser h = new HtmlNameParser(site.toDataRow(), catalog
						.toDataRow(), null, url);
				HtmlNameRule rule = h.getNameRule();
				linkUrl = levelStr + rule.getFullPath() + "/" + indexPage;
				linkUrl = linkUrl.replaceAll("/+", "/");
			}
		} else
			linkUrl = levelStr + getFullPath(catalogID) + indexPage;

		return linkUrl;
	}

	public static String getCatalogIDPath(long catalogID) {
		return getCatalogIDPath(catalogID+"");
	}

	public static String getCatalogIDPath(String catalogID) {
		return getIDPath(catalogID+"");
	}

	public static String getInnerCode(long catalogID) {
		return getInnerCode(catalogID+"");
	}

	public static String getInnerCode(String catalogID) {
		if ((XString.isEmpty(catalogID)) || (catalogID.equals("0"))) {
			return "";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return catalog.getInnerCode();
		}
		return null;
	}

	public static long getCatalogType(long catalogID) {
		return getCatalogType(catalogID+"");
	}

	public static long getCatalogType(String catalogID) {
		if ((XString.isEmpty(catalogID)) || (catalogID.equals("0"))) {
			return 0L;
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return catalog.getType();
		}
		return 0L;
	}

	public static String getSiteID(long catalogID) {
		return getSiteID(catalogID+"");
	}

	public static String getSiteID(String catalogID) {
		if ((XString.isEmpty(catalogID)) || (catalogID.equals("0"))) {
			return "";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return catalog.getSiteID() + "";
		}
		return null;
	}

	public static String getSiteIDByInnerCode(String catalogInnerCode) {
		ZCCatalogSchema catalog = CMSCache
				.getCatalogByInnerCode(catalogInnerCode);
		if (catalog != null) {
			return catalog.getSiteID() + "";
		}
		return null;
	}

	public static String getAbsolutePath(long catalogID) {
		return getAbsolutePath(catalogID+"");
	}

	public static String getAbsolutePath(String catalogID) {
		return SiteUtil.getAbsolutePath(getSiteID(catalogID))
				+ getPath(catalogID);
	}

	public static String getAbsoluteIDPath(long catalogID) {
		return getAbsoluteIDPath(catalogID+"");
	}

	public static String getAbsoluteIDPath(String catalogID) {
		return SiteUtil.getAbsolutePath(getSiteID(catalogID))
				+ getCatalogIDPath(catalogID);
	}

	public static String getAlias(long catalogID) {
		return getAlias(catalogID+"");
	}

	public static String getAlias(String catalogID) {
		if ((XString.isEmpty(catalogID)) || (catalogID.equals("0"))) {
			return "";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return catalog.getAlias().toLowerCase();
		}
		return null;
	}

	public static String getGoodsTypeID(long catalogID) {
		return getGoodsTypeID(catalogID+"");
	}

	public static String getGoodsTypeID(String catalogID) {
		if ((XString.isEmpty(catalogID)) || (catalogID.equals("0"))) {
			return "";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return catalog.getProp4();
		}
		return null;
	}

	public static String getParentID(long catalogID) {
		return getParentID(catalogID+"");
	}

	public static String getParentID(String catalogID) {
		if ((XString.isEmpty(catalogID)) || (catalogID.equals("0"))) {
			return "0";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return catalog.getParentID() + "";
		}
		return null;
	}

	public static String getChildCount(long catalogID) {
		if (catalogID == 0L) {
			return "";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return catalog.getChildCount() + "";
		}
		return null;
	}

	public static String getChildCount(String catalogID) {
		return getChildCount(Long.parseLong(catalogID));
	}

	public static DataTable getCatalogOptions(long type) {
		DataTable dt = new QueryBuilder(
				"select Name,ID,TreeLevel,ParentID from ZCCatalog where SiteID = ? and Type = ? order by ID",
				ApplicationPage.getCurrentSiteID(), type).executeDataTable();
		PubFun.indentDataTable(dt, 0, 2, 0);
		return dt;
	}

	public static DataTable getList(int type) {
		return getList(type, 1);
	}

	public static DataTable getList(int type, int firstLevel) {
		String sql = "select Name,ID ,Level from ZCCatalog where Type=? and siteID =? order by InnerCode";
		DataTable dt = new QueryBuilder(sql, type, ApplicationPage
				.getCurrentSiteID()).executeDataTable();
		PubFun.indentDataTable(dt, 0, 2, firstLevel);
		return dt;
	}

	public static String getWorkflow(long catalogID) {
		return getWorkflow(catalogID+"");
	}

	public static String getWorkflow(String catalogID) {
		if ((XString.isEmpty(catalogID)) || (catalogID.equals("0"))) {
			return "";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return catalog.getWorkflow();
		}
		return null;
	}

	public static String getAttachDownFlag(long catalogID) {
		return getAttachDownFlag(catalogID+"");
	}

	public static String getAttachDownFlag(String catalogID) {
		ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(catalogID);
		if (config != null) {
			return config.getAttachDownFlag();
		}
		return null;
	}

	public static Date getArchiveTime(long catalogID) {
		return getArchiveTime(catalogID+"");
	}

	public static Date getArchiveTime(String catalogID) {
		if ((XString.isEmpty(catalogID)) || (catalogID.equals("0"))) {
			return null;
		}
		ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(catalogID);
		if (config != null) {
			String archive = config.getArchiveTime();
			if (archive.equals("0")) {
				return null;
			}
			return DateUtil.addMonth(new Date(), Integer.parseInt(archive));
		}

		return null;
	}

	public static String getSingleFlag(long catalogID) {
		return getSingleFlag(catalogID+"");
	}

	public static String getSingleFlag(String catalogID) {
		if ((XString.isEmpty(catalogID)) || (catalogID.equals("0"))) {
			return "";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			return catalog.getSingleFlag();
		}
		return null;
	}

	public static String getParentCatalogCode(String innerCode) {
		if (innerCode == null) {
			return "";
		}
		String[] arr = new String[innerCode.length() / 6];
		int i = 0;
		while (innerCode.length() >= 6) {
			arr[(i++)] = innerCode;
			innerCode = innerCode.substring(0, innerCode.length() - 6);
		}
		return "'" + XString.join(arr, "','") + "'";
	}

	public static int getLevel(long catalogID) {
		return getLevel(catalogID+"");
	}

	public static int getLevel(String catalogID) {
		if ((XString.isEmpty(catalogID)) || (catalogID.equals("0"))) {
			return 0;
		}
		int level = 0;
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			String url = catalog.getURL();
			if (XString.isNotEmpty(url)) {
				url = url + "/";
				Pattern p = Pattern.compile("\\$\\{CatalogPath\\}",
						2);
				Matcher matcher = p.matcher(url);

				if (matcher.find()) {
					level = (int) catalog.getTreeLevel();
				}

				url = matcher.replaceAll("").replaceAll("/+", "/");
				if (url.startsWith("/")) {
					url = url.substring(1);
				}
				level += XString.count(url, "/");
			} else {
				level = (int) catalog.getTreeLevel();
			}
		}
		return level;
	}

	public static int getDetailLevel(long catalogID) {
		return getDetailLevel(catalogID+"");
	}

	public static int getDetailLevel(String catalogID) {
		int level = 0;
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		String detailTemplateNameRule = catalog.getDetailNameRule();
		if (XString.isNotEmpty(detailTemplateNameRule)) {
			Pattern p = Pattern.compile("\\$\\{CatalogPath\\}",
					2);
			Matcher matcher = p.matcher(detailTemplateNameRule);
			if (matcher.find()) {
				level = getLevel(catalogID);
			}

			detailTemplateNameRule = matcher.replaceAll("");
			detailTemplateNameRule = detailTemplateNameRule.replaceAll("/+",
					"/");
			if (detailTemplateNameRule.startsWith("/")) {
				detailTemplateNameRule = detailTemplateNameRule.substring(1);
			}

			level += XString.count(detailTemplateNameRule, "/");
		} else {
			level = getLevel(catalogID);
		}
		return level;
	}

	public static String getFullPath(String catalogID) {
		if ((XString.isEmpty(catalogID)) || (catalogID.equals("0"))) {
			return "";
		}
		String path = "";
		String parentID = getParentID(catalogID);
		if (XString.isEmpty(parentID)) {
			return "";
		}
		path = getAlias(new StringBuffer(String.valueOf(catalogID)).toString())
				.toLowerCase()
				+ "/";
		if (!"0".equals(parentID)) {
			path = getFullPath(parentID) + path;
		}

		return path;
	}

	private static String getIDPath(String catalogID) {
		if ((XString.isEmpty(catalogID)) || (catalogID.equals("0"))) {
			return "";
		}
		String path = "";
		String parentID = getParentID(catalogID);
		if (XString.isEmpty(parentID)) {
			return "";
		}
		path = catalogID + "/";
		if (!"0".equals(parentID)) {
			path = getIDPath(parentID) + path;
		}

		return path;
	}

	public static DataRow getData(String catalogID) {
		if ((XString.isEmpty(catalogID)) || (catalogID.equals("0"))) {
			return null;
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		if (catalog != null) {
			DataRow dr = catalog.toDataRow();
			ColumnUtil.extendCatalogColumnData(dr, catalog.getSiteID(), "");
			return dr;
		}
		return null;
	}

	public static String getHotWordType(String catalogID) {
		if ((XString.isEmpty(catalogID)) || (catalogID.equals("0"))) {
			return "";
		}
		ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(catalogID);
		if (config != null) {
			return config.getHotWordType() + "";
		}
		return null;
	}

	public static void update(long catalogID) {
		update(catalogID+"");
	}

	public static void update(String catalogID) {
		if ((XString.isEmpty(catalogID)) || (catalogID.equals("0"))) {
			return;
		}
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(catalogID);

		if (catalog.fill()) {
			ZCCatalogConfigSchema config = new ZCCatalogConfigSchema();
			ZCCatalogConfigSet configSet = config.query(new QueryBuilder(
					"where CatalogID=?", catalog.getID()));
			CacheManager.set("CMS", "Catalog", catalog.getID(), catalog);
			if ((configSet != null) && (configSet.size() > 0)) {
				config = configSet.get(0);
				if (config.getCatalogID() == 0L)
					CacheManager.set("CMS", "CatalogConfig", config.getSiteID()
							+ ",0", config);
				else
					CacheManager.set("CMS", "CatalogConfig", config
							.getCatalogID(), config);
			}
		} else {
			CacheManager.remove("CMS", "Catalog", catalogID);
		}
	}

	public static String createCatalogInnerCode(String parentCode) {
		if (XString.isNotEmpty(parentCode)) {
			return NoUtil.getMaxNo("CatalogInnerCode", parentCode, 6);
		}
		return NoUtil.getMaxNo("CatalogInnerCode", 6);
	}

	public static String getPrivCatalog(int type, String properties) {
		DataTable dt = new QueryBuilder(
				"select * from zccatalog where siteid =? and type = ?",
				ApplicationPage.getCurrentSiteID(), type).executeDataTable();
		ArrayList list = new ArrayList();
		String PrivType = "article";
		if (type == 1)
			PrivType = "article";
		else if (type == 4)
			PrivType = "image";
		else if (type == 5)
			PrivType = "video";
		else if (type == 7) {
			PrivType = "attach";
		}
		for (int i = 0; i < dt.getRowCount(); i++) {
			if (Priv.getPriv(User.getUserName(), PrivType, dt.getString(i,
					"InnerCode"), "article_browse")) {
				if (properties.equals("ID"))
					list.add(dt.getString(i, "ID"));
				else if (properties.equals("InnerCode")) {
					list.add(dt.getString(i, "InnerCode"));
				}
			}
		}
		if (!list.isEmpty()) {
			return XString.join(list);
		}
		return "0";
	}

	public static void addCatalogName(DataTable dt, String catalogIDColumn) {
		dt.insertColumn("CatalogName");
		for (int i = 0; i < dt.getRowCount(); i++) {
			String name = getName(dt.getString(i, catalogIDColumn));
			dt.set(i, "CatalogName", name);
		}
	}

	public static void main(String[] args) {
		System.out.println(getFullName(10742L));
	}

	public static ZCCatalogSchema getSchema(long catalogID) {
		return getSchema(catalogID + "");
	}

	public static String getName(long catalogID) {
		return getName(catalogID + "");
	}
}
