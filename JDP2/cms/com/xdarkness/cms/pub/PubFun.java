package com.xdarkness.cms.pub;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xdarkness.cms.template.HtmlNameParser;
import com.xdarkness.cms.template.HtmlNameRule;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.PlatformCache;
import com.xdarkness.schema.ZCArticleSchema;
import com.xdarkness.schema.ZCCatalogSchema;
import com.xdarkness.schema.ZCImagePlayerSchema;
import com.xdarkness.schema.ZCImagePlayerSet;
import com.xdarkness.schema.ZCSiteSchema;
import com.xdarkness.schema.ZDBranchSchema;
import com.xdarkness.schema.ZDDistrictSchema;
import com.xdarkness.schema.ZDRoleSchema;
import com.xdarkness.schema.ZDUserSchema;
import com.xdarkness.framework.Config;
import com.xdarkness.framework.User;
import com.xdarkness.framework.cache.CacheManager;
import com.xdarkness.framework.orm.data.DataRow;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Filter;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.XString;

public class PubFun {
	public static final String INDENT = "　";
	public static final String SEPARATE = "|";
	

	

	private static Object mutex = new Object();

	



	public static ZDBranchSchema getBranch(String innerCode) {
		return PlatformCache.getBranch(innerCode);
	}

	public static String getBranchName(String innerCode) {
		ZDBranchSchema branch = PlatformCache.getBranch(innerCode);
		if (branch == null) {
			branch = PlatformCache.getBranch("0001");
		}
		if (branch != null) {
			return branch.getName();
		}
		return null;
	}

	

	

	public static String getCurrentPage(long catalogID, int level, String name,
			String separator, String target) {
		String levelString = level == 0 ? "./" : "";
		for (int i = 0; i < level; i++) {
			levelString = levelString + "../";
		}
		ZCCatalogSchema catalog = CMSCache.getCatalog(catalogID);
		long parentID = catalog.getParentID();

		String linkText = catalog.getName();
		String href = CatalogUtil.getLink(catalogID, levelString);
		String text = "";
		if (!"N".equals(catalog.getPublishFlag())) {
			text = " " + separator + " <a href='" + href + "' target='"
					+ target + "'>" + linkText + "</a>";
		}
		if (parentID == 0L)
			text = "<a href='" + levelString + "' target='" + target
					+ "'>首页</a>" + text;
		else {
			text = getCurrentPage(parentID, level, name, separator, target)
					+ text;
		}
		return text;
	}

	public static String getArticleURL(ZCArticleSchema article) {
		ZCCatalogSchema catalog = CatalogUtil.getSchema(article.getCatalogID());
		ZCSiteSchema site = SiteUtil.getSchema(article.getSiteID());
		return getArticleURL(site, catalog, article);
	}

	public static String getDocURL(DataRow doc) {
		ZCCatalogSchema catalog = CatalogUtil.getSchema(doc
				.getLong("CatalogID"));
		ZCSiteSchema site = SiteUtil.getSchema(doc.getLong("SiteID"));
		HtmlNameParser nameParser = new HtmlNameParser(site.toDataRow(),
				catalog.toDataRow(), doc, catalog.getDetailNameRule());
		HtmlNameRule h = nameParser.getNameRule();
		String url = h.getFullPath();
		return url;
	}

	public static String getArticleURL(ZCSiteSchema site,
			ZCCatalogSchema catalog, ZCArticleSchema article) {
		HtmlNameParser nameParser = new HtmlNameParser(site.toDataRow(),
				catalog.toDataRow(), article.toDataRow(), catalog
						.getDetailNameRule());
		HtmlNameRule h = nameParser.getNameRule();
		String url = h.getFullPath();
		return url;
	}

	public static String getGoodsURL(String ID) {
		DataTable dt = new QueryBuilder(
				"select catalogID from zsgoods where ID=?", ID)
				.executeDataTable();
		long catalogID = Long.parseLong(dt.get(0, "catalogID").toString());
		String url = CatalogUtil.getPath(catalogID) + ID + ".shtml";
		return url;
	}

	public static String getImagePath(String imageID, String imageIndex) {
		String imagePath = null;
		if (XString.isEmpty(imageIndex)) {
			imageIndex = "src";
		}
		if (XString.isEmpty(imageID)) {
			imageID = "0";
		}
		String imageSql = "select id,path,catalogID,filename,SrcFileName from zcimage where id=?";
		DataTable dtImage = new QueryBuilder(imageSql, imageID)
				.executeDataTable();
		if ((dtImage != null) && (dtImage.getRowCount() > 0)) {
			if ("src".equals(imageIndex))
				imagePath = dtImage.getString(0, "path")
						+ dtImage.getString(0, "SrcFileName");
			else {
				imagePath = dtImage.getString(0, "path") + imageIndex + "_"
						+ dtImage.getString(0, "fileName");
			}
		}
		return imagePath;
	}

	public static String getImagePath(long imageID, String imageIndex) {
		return getImagePath(imageID, imageIndex);
	}

	public static String getImagePath(long imageID) {
		return getImagePath(imageID, null);
	}



	public static String getImagePath(String imageID) {
		return getImagePath(imageID, null);
	}

	public static String getImagePlayer(String ImagePlayCode) {
		ZCImagePlayerSchema imagePlayer = new ZCImagePlayerSchema();
		imagePlayer.setCode(ImagePlayCode);
		ZCImagePlayerSet set = imagePlayer.query();
		if (set.size() > 0)
			imagePlayer = set.get(0);
		else {
			System.out.println("没有" + ImagePlayCode + "对应的图片播放器，请检查"
					+ ImagePlayCode + "是否正确。");
		}
		return getImagePlayer(imagePlayer);
	}

	public static String getImagePlayer(ZCImagePlayerSchema imagePlayer) {
		String id = "flashcontent";
		String Alias = Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/";
		Alias = Alias.replaceAll("///", "/");
		Alias = Alias.replaceAll("//", "/");
		StringBuffer pics = new StringBuffer();
		StringBuffer links = new StringBuffer();
		StringBuffer texts = new StringBuffer();
		pics.append("'");
		links.append("'");
		texts.append("'");
		DataTable dt = null;
		int shortTextLen = Integer
				.parseInt((imagePlayer.getWidth() / 10L - imagePlayer
						.getWidth() / 60L * 2L)
						+ "");

		int imageCount = (int) imagePlayer.getDisplayCount();
		if (imageCount == 0) {
			imageCount = 6;
		}
		if ("1".equals(imagePlayer.getImageSource())) {
			String catalogStr = " and cataloginnercode like '%'";
			if ((XString.isNotEmpty(imagePlayer.getRelaCatalogInnerCode()))
					&& (!"null".equalsIgnoreCase(imagePlayer
							.getRelaCatalogInnerCode()))) {
				catalogStr = " and cataloginnercode like '"
						+ imagePlayer.getRelaCatalogInnerCode() + "%'";
			}

			String attributeSql = " and attribute like '%image%'";
			String typeStr = " order by publishdate desc,orderflag desc, id desc";
			dt = new QueryBuilder("select * from zcarticle where siteID=?"
					+ catalogStr + " and status in(" + 20 + "," + 30
					+ ") and (publishdate<=? or publishdate is null) "
					+ attributeSql + typeStr, imagePlayer.getSiteID(),
					new Date()).executePagedDataTable(imageCount, 0);
			dt.insertColumns(new String[] { "FirstImagePath" });
			dealArticleMedia(dt, null, "image");

			for (int i = 0; i < dt.getRowCount(); i++) {
				if (i != 0) {
					pics.append("|");
					links.append("|");
					texts.append("|");
				}
				String imagePath = "";
				if (XString.isNotEmpty(dt.getString(i, "FirstImagePath"))) {
					imagePath = dt.getString(i, "FirstImagePath").substring(
							dt.getString(i, "FirstImagePath")
									.indexOf("upload/"));
				}
				pics.append(".." + Alias + imagePath);
				String siteUrl = SiteUtil.getURL(dt.getString(i, "SiteID"));
				if (siteUrl.endsWith("shtml")) {
					siteUrl = siteUrl.substring(0, siteUrl.lastIndexOf("/"));
				}

				if (!siteUrl.endsWith("/")) {
					siteUrl = siteUrl + "/";
				}

				links.append(siteUrl + getDocURL(dt.getDataRow(i)));
				String text = dt.getString(i, "ShortTitle");
				if (XString.isEmpty(text)) {
					text = dt.getString(i, "Title");
				}
				if (text.length() > shortTextLen) {
					text = text.substring(0, shortTextLen) + "...";
				}
				texts.append(text);
			}
		} else {
			String sql = "select b.* from ZCImageRela a,zcimage b where a.id = b.id  and a.RelaID="
					+ imagePlayer.getID()
					+ " and a.RelaType='"
					+ "ImagePlayerImage"
					+ "' order by a.orderflag desc, a.addtime desc";
			dt = new QueryBuilder(sql).executePagedDataTable(imageCount, 0);

			for (int i = 0; i < dt.getRowCount(); i++) {
				if (i != 0) {
					pics.append("|");
					links.append("|");
					texts.append("|");
				}
				pics.append(".." + Alias + dt.getString(i, "path") + "1_"
						+ dt.getString(i, "FileName"));
				if (XString.isNotEmpty(Alias + dt.getString(i, "LinkURL"))) {
					links.append(dt.getString(i, "LinkURL"));
				}
				String text = dt.getString(i, "LinkText");
				if (XString.isNotEmpty(text)) {
					if (text.length() > shortTextLen) {
						text = text.substring(0, shortTextLen) + "...";
					}
					texts.append(text);
				}
			}
		}
		pics.append("'");
		links.append("'");
		texts.append("'");

		StringBuffer sb = new StringBuffer();
		sb.append("<script type=\"text/javascript\" src=\""
				+ Config.getContextPath() + "Tools/Swfobject.js\"></script>\n");
		sb.append("<div id='" + id + "'>\n");
		sb.append("  This text is replaced by the Flash movie.\n");
		sb.append("</div>\n");
		sb.append("<script type='text/javascript'>\n");
		sb
				.append("var so=new SWFObject('" + Config.getContextPath()
						+ "Tools/ImagePlayer.swf','ImagePlayer1',"
						+ imagePlayer.getWidth() + ","
						+ (imagePlayer.getHeight() + 22L)
						+ ",'7','#FFFFFF','high');\n");
		sb.append("so.addVariable('wmode','transparent');\n");
		sb.append("so.addVariable('pics'," + pics.toString() + ");\n");
		sb.append("so.addVariable('links'," + links.toString() + ");\n");
		sb.append("so.addVariable('texts'," + texts.toString() + ");\n");
		sb.append("so.addVariable('borderwidth'," + imagePlayer.getWidth()
				+ ");\n");
		sb.append("so.addVariable('borderheight'," + imagePlayer.getHeight()
				+ ");\n");
		if ("Y".equals(imagePlayer.getIsShowText())) {
			sb.append("so.addVariable('textheight',2OperateType.UPDATE);\n");
			sb.append("so.addVariable('show_text','1');\n");
		} else {
			sb.append("so.addVariable('show_text','0');\n");
		}

		sb.append("so.addVariable('txtcolor','FFFF00');\n");
		sb.append("so.addVariable('overtxtcolor','FFFF00');\n");
		sb.append("so.addVariable('overtxtcolor','FFFF00');\n");

		sb.append("so.write('" + id + "');\n");
		sb.append("</script>");

		return sb.toString();
	}

	public static void dealArticleMedia(DataTable dt) {
		dealArticleMedia(dt, null, "image,attchment,video");
	}

	public static void dealArticleMedia(DataTable dt, String levelString,
			String hasAttribute) {
		for (int i = 0; i < dt.getRowCount(); i++)
			dealArticleMedia(dt.getDataRow(i), levelString, hasAttribute);
	}

	public static void dealArticleMedia(DataRow dr) {
		dealArticleMedia(dr, null, "image,attchment,video");
	}

	public static void dealArticleMedia(DataRow dr, String hasAttribute) {
		dealArticleMedia(dr, null, hasAttribute);
	}

	public static void dealArticleMedia(DataRow dr, String levelString,
			String hasAttribute) {
		String attribute = dr.getString("Attribute");

		if ((levelString == null) || ("null".equalsIgnoreCase(levelString))) {
			levelString = getLevelStr(CatalogUtil.getDetailLevel(dr
					.getString("CatalogID")));
		}

		if ((XString.isNotEmpty(attribute))
				&& (XString.isNotEmpty(hasAttribute))) {
			if (hasAttribute.indexOf("image") != -1) {
				if (attribute.indexOf("image") != -1) {
					DataTable imageRelaDT = new QueryBuilder(
							"select b.path,b.filename from zcimagerela a,zcimage b where a.relaID=? and a.relatype=? and a.ID=b.ID order by a.orderflag",
							dr.getString("ID"), "ArticleImage")
							.executeDataTable();
					if ((imageRelaDT != null)
							&& (imageRelaDT.getRowCount() > 0))
						dr.set("FirstImagePath", levelString
								+ imageRelaDT.getString(0, "path") + "1_"
								+ imageRelaDT.getString(0, "filename"));
					else
						dr.set("FirstImagePath", levelString
								+ "upload/Image/nopicture.jpg");
				} else {
					dr.set("FirstImagePath", levelString
							+ "upload/Image/nopicture.jpg");
				}

			}

			if (hasAttribute.indexOf("video") != -1)
				if (attribute.indexOf("video") != -1) {
					DataTable videoRelaDT = new QueryBuilder(
							"select ID from zcvideorela where relaID=? and relatype=?",
							dr.getString("ID"), "ArticleVideo")
							.executeDataTable();
					if ((videoRelaDT != null)
							&& (videoRelaDT.getRowCount() > 0)) {
						DataTable videoDT = new QueryBuilder(
								"select id,name,suffix,path,filename,srcfilename,imageName from zcvideo where id=?",
								videoRelaDT.getString(0, "ID"))
								.executeDataTable();
						if ((videoDT != null) && (videoDT.getRowCount() > 0)) {
							dr.set("FirstVideoImage", levelString
									+ videoDT.getString(0, "path")
									+ videoDT.getString(0, "imageName"));
							dr.set("FirstVideoHtml", getVideoHtml(videoDT
									.getString(0, "ID"), levelString));
						}
					}
				} else {
					dr.set("FirstVideoImage", "");
					dr.set("FirstVideoHtml", "");
				}
		} else {
			dr.set("FirstImagePath", "");

			dr.set("FirstVideoImage", "");
			dr.set("FirstVideoHtml", "");
		}
	}

	public static String getVideoHtml(long videoID) {
		return getVideoHtml(videoID, null);
	}

	public static String getVideoHtml(long videoID, String levelString) {
		return getVideoHtml(videoID, levelString);
	}

	public static String getVideoHtml(String videoID, String levelString) {
		DataTable videoDT = new QueryBuilder(
				"select id,catalogID,name,suffix,path,filename,srcfilename,imageName from zcvideo where id=?",
				videoID).executeDataTable();
		if ((videoDT != null) && (videoDT.getRowCount() > 0)) {
			return getVideoHtml(videoDT.getDataRow(0), levelString);
		}
		return "";
	}

	public static String getVideoHtml(DataRow dr) {
		return getVideoHtml(dr, null);
	}

	public static String getVideoHtml(DataRow dr, String levelString) {
		if ((XString.isEmpty(levelString))
				|| ("null".equalsIgnoreCase(levelString))) {
			levelString = getLevelStr(CatalogUtil.getDetailLevel(dr
					.getString("CatalogID")));
		}

		String files = "../" + dr.getString("path") + dr.getString("filename");
		String relaVideoHtml = "<script type='text/javascript' src='"
				+ levelString
				+ "images/Swfobject.js'></script><script type='text/javascript'>"
				+ "var s1 = new SWFObject('" + levelString
				+ "images/player.swf','player','270','225','9','#FFFFFF');"
				+ "s1.addParam('allowfullscreen','true');"
				+ "s1.addParam('allowscriptaccess','always');"
				+ "s1.addParam('flashvars','file=" + files + "');"
				+ "s1.write('container');</script>";
		return relaVideoHtml;
	}

	
	public static Mapx getDistrictMap() {
		if (DistrictMap == null) {
			synchronized (mutex) {
				if (DistrictMap == null) {
					initDistrict();
				}
			}
		}
		return DistrictMap;
	}

	public static DataTable getProvince() {
		if (District == null) {
			initDistrict();
		}
		return District.filter(new Filter() {
			public boolean filter(Object o) {
				DataRow dr = (DataRow) o;

				return "1".equals(dr.get("TreeLevel"));
			}
		});
	}

	public static DataTable getCity(String Province) {
		if (District == null) {
			initDistrict();
		}
		if (XString.isNotEmpty(Province)) {
			ZDDistrictSchema district = new ZDDistrictSchema();
			district.setCode(Province);
			district.fill();
			if ("0".equals(district.getType())) {
				return District.filter(new Filter(Province) {
					public boolean filter(Object o) {
						DataRow dr = (DataRow) o;

						return ((String) this.Param).equals(dr
								.getString("Code"));
					}

				});
			}

			return District.filter(new Filter(Province) {
				public boolean filter(Object o) {
					DataRow dr = (DataRow) o;

					return ("2".equals(dr.getString("TreeLevel")))
							&& (dr.getString("Code").substring(0, 2)
									.equals(((String) this.Param).substring(0,
											2)));
				}

			});
		}

		return new DataTable();
	}

	public static DataTable getDistrict(String City) {
		if (District == null) {
			initDistrict();
		}
		if (XString.isNotEmpty(City)) {
			ZDDistrictSchema district = new ZDDistrictSchema();
			district.setCode(City);
			district.fill();
			if ("0".equalsIgnoreCase(district.getType())) {
				return District.filter(new Filter(City) {
					public boolean filter(Object o) {
						DataRow dr = (DataRow) o;

						return ("3".equals(dr.get("TreeLevel").toString()))
								&& (dr.get("Code").toString().substring(0, 3)
										.equals(((String) this.Param)
												.substring(0, 3)));
					}

				});
			}

			return District.filter(new Filter(City) {
				public boolean filter(Object o) {
					DataRow dr = (DataRow) o;

					return ("3".equals(dr.getString("TreeLevel")))
							&& (dr.get("Code").toString().substring(0, 4)
									.equals(((String) this.Param).substring(0,
											4)));
				}

			});
		}

		return new DataTable();
	}

	public static boolean isAllowExt(String ext) {
		return isAllowExt(ext, "All");
	}

	public static boolean isAllowExt(String ext, String fileType) {
		boolean allowed = false;
		if (XString.isNotEmpty(ext)) {
			String[] extArr = (String[]) null;
			ArrayList allowList = new ArrayList();
			String[] typeArr = new String[1];
			if (fileType.equalsIgnoreCase("All"))
				typeArr = "Attach,Image,Video,Audio".split(",");
			else {
				typeArr[0] = fileType;
			}
			for (int i = 0; i < typeArr.length; i++) {
				String allowExt = Config.getValue("Allow" + typeArr[i] + "Ext");
				if (XString.isNotEmpty(allowExt)) {
					extArr = allowExt.split(",");
					for (int j = 0; j < extArr.length; j++) {
						allowList.add(extArr[j].toLowerCase());
					}
				}
			}
			allowed = allowList.contains(ext.toLowerCase());
		}
		return allowed;
	}

	public static String getLevelStr(long level) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < level; i++) {
			sb.append("../");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		String s = getCurrentPage(11001L, 2, "", ">", "_self");
		System.out.println(s);
	}
}

/*
 * com.xdarkness.cms.pub.PubFun JD-Core Version: 0.6.0
 */