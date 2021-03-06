package com.xdarkness.cms.template;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.schema.ZCPageBlockSchema;
import com.xdarkness.schema.ZCPageBlockSet;
import com.xdarkness.schema.ZCTemplateBlockRelaSchema;
import com.xdarkness.schema.ZCTemplateBlockRelaSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Big5Convert;
import com.xdarkness.framework.util.Errorx;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;

public class TagParser {
	private static final Pattern cmsForm = Pattern.compile(
			"<cms:form\\s(.*?)>(.*?)</cms:form>", 34);

	private static final Pattern cmsList = Pattern.compile(
			"<cms:list\\s(.*?)>(.*?)</cms:List>", 34);

	private static final Pattern cmsVar = Pattern.compile(
			"<cms:var\\s(.*?)>(.*?)</cms:var>", 34);

	private static final Pattern cmsSubList = Pattern.compile(
			"<cms:sublist\\s(.*?)>(.*?)</cms:subList>", 34);

	private static final Pattern cmsImagePlayer = Pattern.compile(
			"<cms:imageplayer\\s(.*?)(/>|>(.*?)</cms:ImagePlayer>)", 34);

	private static final Pattern cmsAD = Pattern.compile(
			"<cms:ad\\s(.*?)(/>|>(.*?)</cms:ad>)", 34);

	private static final Pattern cmsVote = Pattern.compile(
			"<cms:vote\\s(.*?)>(.*?)</cms:vote>", 34);

	private static final Pattern cmsComment = Pattern.compile(
			"<cms:comment\\s(.*?)((/>)|(>.*?</cms:comment>))", 34);

	private static final Pattern cmsLink = Pattern.compile(
			"<cms:link\\s(.*?)((/>)|(>.*?</cms:link>))", 34);

	private static final Pattern cmsPage = Pattern.compile(
			"<cms:page\\s(.*?)((/>)|(>.*?</cms:page>))", 34);

	private static final Pattern cmsTree = Pattern.compile(
			"<cms:tree\\s(.*?)>(.*?)</cms:tree>", 34);

	private static final Pattern cmsInclude = Pattern.compile(
			"<cms:include\\s(.*?)(/>|(>.*?</cms:include>))", 34);

	public static final Pattern pAttr1 = Pattern.compile(
			"\\s*?(\\w+?)\\s*?=\\s*?(\\\"|\\')(.*?)\\2", 34);

	public static final Pattern pAttr2 = Pattern.compile(
			"\\s*?(\\w+?)\\s*?=\\s*?([^\\'\\\"\\s]+)", 34);

	private static final Pattern pField = Pattern
			.compile("\\$\\{(\\w+?)\\.(\\w+?)(\\|(.*?))??\\}");

	private static final Pattern pageBar = Pattern.compile("\\$\\{PageBar\\}",
			34);

	private static final Pattern pageBreakBar = Pattern.compile(
			"\\$\\{PageBreakBar\\}", 34);

	private static final Pattern levelPattern = Pattern.compile(
			"\\$\\{Level\\}", 34);

	public static final Pattern resourcePattern1 = Pattern
			.compile(
					"\\s(src|href|background|file|virtual|action|name=\"movie\" value)\\s*?=\\s*?([^\\\"\\'\\s]+?)(\\s|>|/>)",
					34);

	public static final Pattern resourcePattern2 = Pattern
			.compile(
					"\\s(src|href|background|file|virtual|action|name=\"movie\" value)\\s*?=\\s*?(\\\"|\\')(.*?)\\2",
					34);

	public static final Pattern resourcePatternCss = Pattern.compile(
			"(:\\s*?url)\\s*?\\(\\s*?([^\\\"\\'\\s]+)(\\))", 34);

	public static final Pattern resourcePatternCss2 = Pattern.compile(
			"(:\\s*?url)\\s*?\\(\\s*?(\\\"|\\')(.*?)\\2(\\))", 34);
	private String templateFileName;
	private String content;
	private String staticDir;
	private String templateDir;
	private long siteID;
	private int dirLevel;
	private boolean isPageBlock;
	private boolean isPreview;
	private Errorx error = new Errorx();

	private Transaction trans = new Transaction();

	private ZCPageBlockSet blockSet = new ZCPageBlockSet();

	private Mapx pageListPrams = new Mapx();

	private ArrayList fileList = new ArrayList();

	public TagParser() {
		String contextRealPath = Config.getContextRealPath();
		this.templateDir = (contextRealPath + Config.getValue(
				"Statical.TemplateDir").replace('\\', '/'));
		this.staticDir = (contextRealPath + Config.getValue(
				"Statical.TargetDir").replace('\\', '/'));
		this.staticDir = this.staticDir.replaceAll("/+", "/");
	}

	private void processPath() {
		String levelString = getLevelStr();
		this.content = dealResource(this.content, levelString);
	}

	public String dealResource(String content, String levelStr) {
		if (content == null) {
			return "";
		}
		Matcher m = resourcePattern1.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;

		while (m.find(lastEndIndex)) {
			String dealPath = processText(m.group(1), m.group(2), levelStr);
			sb.append(content.substring(lastEndIndex, m.start()));
			sb.append(" " + m.group(1) + "=" + dealPath + m.group(3));
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		content = sb.toString();
		sb = new StringBuffer();
		m = resourcePattern2.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String dealPath = processText(m.group(1), m.group(3), levelStr);
			sb.append(content.substring(lastEndIndex, m.start()));
			String separator = m.group(2);
			sb
					.append(" " + m.group(1) + "=" + separator + dealPath
							+ separator);
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		content = sb.toString();
		sb = new StringBuffer();
		m = resourcePatternCss.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String dealPath = processText(m.group(1), m.group(2), levelStr);
			sb.append(content.substring(lastEndIndex, m.start()));
			sb.append(m.group(1) + "(" + dealPath + ")");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		content = sb.toString();
		sb = new StringBuffer();
		m = resourcePatternCss2.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String dealPath = processText(m.group(1), m.group(3), levelStr);
			sb.append(content.substring(lastEndIndex, m.start()));
			sb.append(m.group(1) + "(" + dealPath + ")");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		if (!this.isPageBlock) {
			content = sb.toString();
			sb = new StringBuffer();
			lastEndIndex = 0;
			m = levelPattern.matcher(content);
			while (m.find(lastEndIndex)) {
				sb.append(content.substring(lastEndIndex, m.start()));
				sb.append(levelStr);
				lastEndIndex = m.end();
			}
			sb.append(content.substring(lastEndIndex));
		}

		return sb.toString();
	}

	private String processText(String srcType, String resourcePath,
			String levelStr) {
		String strPath = "";
		resourcePath = resourcePath.trim();

		if (resourcePath.startsWith("#")) {
			strPath = resourcePath;
		} else if ((resourcePath.toLowerCase().startsWith("/"))
				|| (resourcePath.toLowerCase().indexOf("${") != -1)
				|| (resourcePath.toLowerCase().indexOf("<%") != -1)
				|| (resourcePath.toLowerCase().indexOf("+") != -1)
				|| (resourcePath.toLowerCase().startsWith("<cms:"))
				|| (resourcePath.toLowerCase().startsWith("http"))
				|| (resourcePath.toLowerCase().startsWith("https"))
				|| (resourcePath.toLowerCase().startsWith("ftp"))
				|| (resourcePath.toLowerCase().startsWith("javascript"))
				|| (resourcePath.toLowerCase().startsWith("mailto"))) {
			strPath = resourcePath;
		} else {
			int upLevelIndex = resourcePath.lastIndexOf("../");
			if (upLevelIndex != -1) {
				resourcePath = resourcePath.substring(upLevelIndex + 3);
			}
			if (resourcePath.indexOf("//") == -1) {
				String fileName = resourcePath.indexOf("/") == -1 ? resourcePath
						: resourcePath
								.substring(resourcePath.lastIndexOf("/") + 1);

				if (resourcePath.length() - 1 == resourcePath.lastIndexOf("/")) {
					fileName = resourcePath;
				}

				String ext = resourcePath.indexOf(".") == -1 ? ""
						: resourcePath
								.substring(resourcePath.lastIndexOf(".") + 1);
				ext = ext.toLowerCase().trim();

				if (("file".equalsIgnoreCase(srcType))
						|| ("virtual".equalsIgnoreCase(srcType)))
					strPath = levelStr + resourcePath;
				else if (("url".equalsIgnoreCase(srcType))
						&& ("#default#homepage".equalsIgnoreCase(fileName)))
					strPath = resourcePath;
				else if ("".equalsIgnoreCase(ext))
					strPath = levelStr + resourcePath;
				else if ("window.external.AddFavorite(location.href,document.title);"
						.equals(fileName))
					strPath = resourcePath;
				else
					strPath = levelStr + resourcePath;
			} else if ((resourcePath.indexOf("//") > 0)
					&& (resourcePath.indexOf("#default#homepage") != -1)) {
				strPath = resourcePath;
			}
		}

		return strPath;
	}

	private void addHeader() {
		this.content = ("<z:config language=\"java\" prefix=\"%\"/>" + this.content);
	}

	private void parseList() {
		Matcher m = cmsList.matcher(this.content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		int varIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(this.content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String listContent = m.group(2);

			String item = (String) map.get("item");
			if (XString.isEmpty(item)) {
				sb.append("模板错误：cms:list未设置item属性");
				varIndex++;
			} else {
				item = item.toLowerCase();

				String type = (String) map.get("type");
				String page = (String) map.get("page");
				String countStr = (String) map.get("count");
				String begin = (String) map.get("begin");
				String pagesizeStr = (String) map.get("pagesize");
				String condition = (String) map.get("condition");
				String displayLevel = (String) map.get("level");
				String var = (String) map.get("var");
				String attribute = (String) map.get("hasattribute");
				if (XString.isNotEmpty(displayLevel))
					displayLevel = displayLevel.toLowerCase();
				else {
					displayLevel = "current";
				}
				map.put("level", displayLevel);

				if (XString.isEmpty(var)) {
					var = item + "_" + varIndex;
					Pattern p = Pattern.compile("(" + item + ")\\.", 2);
					Matcher matcher = p.matcher(listContent);
					listContent = matcher.replaceAll(item + "_" + varIndex
							+ ".");
				}

				listContent = parseSubList(listContent, System
						.currentTimeMillis(), item, var);

				int count = 0;
				if ((XString.isNotEmpty(countStr))
						&& (XString.isDigit(countStr)))
					count = Integer.parseInt(countStr);
				else {
					count = 50;
				}
				map.put("count", count);

				if ((XString.isEmpty(pagesizeStr))
						|| (!XString.isDigit(pagesizeStr))) {
					pagesizeStr = "0";
				}
				map.put("pagesize", pagesizeStr);

				String beginIndex = null;
				if ((XString.isNotEmpty(begin))
						&& (XString.isDigit(begin))) {
					beginIndex = begin;
					count += Integer.parseInt(begin);
				} else {
					beginIndex = "0";
				}

				if ((page != null) && ("true".equalsIgnoreCase(page))) {
					this.pageListPrams = map;
					String jspContent = "<%DataTable dt_" + varIndex
							+ " = TemplateData.getListTable();" + "for(int i="
							+ beginIndex + ";i<dt_" + varIndex
							+ ".getRowCount();i++){\n" + "DataRow " + var
							+ "  = dt_" + varIndex + ".getDataRow(i);\n"
							+ "%>\n";
					jspContent = jspContent + listContent;
					jspContent = jspContent + "<%}%>";
					sb.append(jspContent);
				} else if (("article".equalsIgnoreCase(item))
						|| ("image".equalsIgnoreCase(item))
						|| ("video".equalsIgnoreCase(item))
						|| ("audio".equalsIgnoreCase(item))
						|| ("attachment".equalsIgnoreCase(item))
						|| ("goods".equalsIgnoreCase(item))) {
					String catalog = (String) map.get("name");
					String id = (String) map.get("id");
					if (XString.isNotEmpty(id)) {
						catalog = id;
					}
					String parent = (String) map.get("parent");

					if ((XString.isEmpty(catalog))
							&& (XString.isNotEmpty(parent))) {
						catalog = parent;
					}

					String catalogStr = null;
					if ((XString.isNotEmpty(catalog))
							&& (catalog.indexOf("${") != -1))
						catalogStr = parsePlaceHolderStr(catalog);
					else {
						catalogStr = "\"" + catalog + "\"";
					}

					String conditionStr = null;
					if ((XString.isNotEmpty(condition))
							&& (condition.indexOf("${") != -1))
						conditionStr = parsePlaceHolderStr(condition);
					else {
						conditionStr = "\"" + condition + "\"";
					}

					String jspContent = "<!--循环：" + item + " count：" + count
							+ "-->\n" + "<% DataTable dt_" + varIndex
							+ " = TemplateData.getDocList(\"" + item + "\","
							+ catalogStr + ",\"" + displayLevel + "\",\""
							+ attribute + "\",\"" + type + "\",\"" + count
							+ "\"," + conditionStr + ");\n" + "if(dt_"
							+ varIndex + "==null){write(\"没有找到栏目。parent："
							+ map.get("parent") + "  catalog："
							+ (String) map.get("name") + "\");}else{\n"
							+ "for(int i=" + beginIndex + ";i<dt_" + varIndex
							+ ".getRowCount();i++){\n" + "DataRow " + var
							+ "  = dt_" + varIndex + ".getDataRow(i);\n"
							+ "%>\n";

					jspContent = jspContent + listContent;
					jspContent = jspContent + "<%}}%>";

					if ((!this.isPageBlock) && (!this.isPreview)) {
						if ((catalog == null)
								|| ("relate".equalsIgnoreCase(type))) {
							sb.append(jspContent);
						} else
							sb.append(getListBlock(item, count, type, catalog,
									parent, jspContent));
					} else {
						sb.append(jspContent);
					}
				} else if ("friendlink".equalsIgnoreCase(item)) {
					String group = map.getString("group");
					if (group == null) {
						group = map.getString("name");
					}

					if (group != null) {
						group = group.toLowerCase();
					}

					String jspContent = "<!--循环：" + item + " count：" + count
							+ "-->\n" + "<% DataTable dt_" + varIndex
							+ " = TemplateData.getFriendLinkList(\"" + item
							+ "\",\"" + group + "\",\"" + count + "\",\""
							+ condition + "\");\n" + "if(dt_" + varIndex
							+ "==null){write(\"没有找到友情链接："
							+ (String) map.get("name") + "\");}else{\n"
							+ "for(int i=" + beginIndex + ";i<dt_" + varIndex
							+ ".getRowCount();i++){\n" + "DataRow " + var
							+ "  = dt_" + varIndex + ".getDataRow(i);\n"
							+ "%>\n";

					jspContent = jspContent + listContent;
					jspContent = jspContent + "<%}}%>";

					if ((!this.isPageBlock) && (this.dirLevel == 0)
							&& (!this.isPreview)) {
						String targetFileName = getLinkBlock(item, group,
								count, jspContent);
						sb.append("<!--#include virtual=\"" + getLevelStr()
								+ targetFileName + "\"-->");
					} else {
						sb.append(jspContent);
					}
				} else if ("block".equalsIgnoreCase(item)) {
					String jspContent = "<!--循环：" + item + " count：" + count
							+ "-->\n" + "<% DataTable dt_" + varIndex
							+ " = TemplateData.getBlockList(\"" + item
							+ "\",\"" + count + "\",\"" + condition + "\");\n"
							+ "for(int i=" + beginIndex + ";i<dt_" + varIndex
							+ ".getRowCount();i++){" + "DataRow " + var
							+ "  = dt_" + varIndex + ".getDataRow(i);" + "%>";

					jspContent = jspContent + listContent;
					jspContent = jspContent + "<%}%>";
					sb.append(jspContent);
				} else if ("catalog".equalsIgnoreCase(item)) {
					String parent = (String) map.get("parent");
					String parentID = (String) map.get("id");
					String catalog = (String) map.get("name");
					if (XString.isNotEmpty(parentID)) {
						parent = parentID;
					}

					if ((XString.isEmpty(catalog))
							&& (XString.isNotEmpty(parent))) {
						catalog = parent;
					}

					String catalogStr = null;
					if ((XString.isNotEmpty(catalog))
							&& (catalog.indexOf("${") != -1))
						catalogStr = parsePlaceHolder(catalog);
					else {
						catalogStr = "\"" + catalog + "\"";
					}

					if (XString.isEmpty(displayLevel))
						displayLevel = "\"child\"";
					else {
						displayLevel = "\"" + displayLevel + "\"";
					}

					String jspContent = "<!--循环：" + item + " count：" + count
							+ "-->\n" + "<% DataTable dt_" + varIndex
							+ " = TemplateData.getCatalogList(\"" + item
							+ "\",\"" + type + "\"," + catalogStr + ","
							+ displayLevel + ",\"" + count + "\",\""
							+ condition + "\");\n" + "if(dt_" + varIndex
							+ "==null){write(\"没有找到栏目列表。\");}else{\n"
							+ "for(int i=" + beginIndex + ";i<dt_" + varIndex
							+ ".getRowCount();i++){" + "DataRow " + var
							+ "  = dt_" + varIndex + ".getDataRow(i);\n" + "%>";
					jspContent = jspContent + listContent;
					jspContent = jspContent + "<%}}%>";

					sb.append(jspContent);
				} else if ("customtable".equalsIgnoreCase(item)) {
					String tableName = (String) map.get("name");
					String jspContent = "<!--循环：" + item + " count：" + count
							+ "-->\n" + "<% DataTable dt_" + varIndex
							+ " = TemplateData.getCustomData(\"" + tableName
							+ "\",\"" + count + "\",\"" + condition + "\");\n"
							+ "if(dt_" + varIndex + "==null){write(\"没有找到自定义数据"
							+ tableName + "。\");}else{\n" + "for(int i="
							+ beginIndex + ";i<dt_" + varIndex
							+ ".getRowCount();i++){" + "DataRow " + var
							+ "  = dt_" + varIndex + ".getDataRow(i);\n" + "%>";
					jspContent = jspContent + listContent;
					jspContent = jspContent + "<%}}%>";

					sb.append(jspContent);
				} else if ("keyword".equalsIgnoreCase(item)) {
					String jspContent = "<!--循环：" + item + " count：" + count
							+ "-->\n" + "<% DataTable dt_" + varIndex
							+ " = TemplateData.getKeywords(\"" + type + "\",\""
							+ count + "\"" + ");\n" + "if(dt_" + varIndex
							+ "==null){write(\"" + "\");}else{\n"
							+ "for(int i=" + beginIndex + ";i<dt_" + varIndex
							+ ".getRowCount();i++){" + "DataRow " + var
							+ "  = dt_" + varIndex + ".getDataRow(i);\n" + "%>";
					jspContent = jspContent + listContent;
					jspContent = jspContent + "<%}}%>";

					sb.append(jspContent);
				} else if ("magazine".equalsIgnoreCase(item)) {
					String name = (String) map.get("name");
					if (XString.isEmpty(displayLevel))
						displayLevel = "\"child\"";
					else {
						displayLevel = "\"" + displayLevel + "\"";
					}

					String jspContent = "<!--循环：" + item + " count：" + count
							+ "-->\n" + "<% DataTable dt_" + varIndex
							+ " = TemplateData.getMagazineList(\"" + item
							+ "\",\"" + name + "\",\"" + count + "\",\""
							+ condition + "\");\n" + "if(dt_" + varIndex
							+ "==null){write(\"没有找到期刊列表。\");}else{\n"
							+ "for(int i=" + beginIndex + ";i<dt_" + varIndex
							+ ".getRowCount();i++){" + "DataRow " + var
							+ "  = dt_" + varIndex + ".getDataRow(i);\n" + "%>";
					jspContent = jspContent + listContent;
					jspContent = jspContent + "<%}}%>";

					sb.append(jspContent);
				} else if ("magazineissue".equalsIgnoreCase(item)) {
					String name = (String) map.get("name");
					if (XString.isEmpty(displayLevel))
						displayLevel = "\"child\"";
					else {
						displayLevel = "\"" + displayLevel + "\"";
					}

					String jspContent = "<!--循环：" + item + " count：" + count
							+ "-->\n" + "<% DataTable dt_" + varIndex
							+ " = TemplateData.getMagazineIssueList(\"" + item
							+ "\",\"" + name + "\",\"" + type + "\",\"" + count
							+ "\",\"" + condition + "\");\n" + "if(dt_"
							+ varIndex
							+ "==null){write(\"没有找到期刊期数列表。\");}else{\n"
							+ "for(int i=" + beginIndex + ";i<dt_" + varIndex
							+ ".getRowCount();i++){" + "DataRow " + var
							+ "  = dt_" + varIndex + ".getDataRow(i);\n" + "%>";
					jspContent = jspContent + listContent;
					jspContent = jspContent + "<%}}%>";

					sb.append(jspContent);
				} else if ("page".equalsIgnoreCase(item)) {
					String jspContent = "<!--循环："
							+ item
							+ " count："
							+ count
							+ "-->\n"
							+ "<% DataTable dt_"
							+ varIndex
							+ " = TemplateData.getArticlePages();\n"
							+ "if(dt_"
							+ varIndex
							+ "==null){write(\"<cms:list item='page'>本标签只能在文章详细页中使用。"
							+ "。\");}else{\n" + "for(int i=" + beginIndex
							+ ";i<dt_" + varIndex + ".getRowCount();i++){"
							+ "DataRow " + var + "  = dt_" + varIndex
							+ ".getDataRow(i);\n" + "%>";
					jspContent = jspContent + listContent;
					jspContent = jspContent + "<%}}%>";

					sb.append(jspContent);
				}

				varIndex++;
			}
		}
		sb.append(this.content.substring(lastEndIndex));
		this.content = sb.toString();

		if ("true".equalsIgnoreCase(this.pageListPrams.getString("page")))
			parsePageBar();
	}

	private String getLinkBlock(String item, String group, int count,
			String jspContent) {
		String catalogID = "0";

		String cacheDir = this.templateDir + "/"
				+ SiteUtil.getAlias(this.siteID);
		cacheDir = cacheDir.replace('\\', '/').replaceAll("//", "/");

		File cacheFileDir = new File(cacheDir + "/cache/template");
		if (!cacheFileDir.exists()) {
			cacheFileDir.mkdirs();
		}

		String groupAlias = XString.getChineseFirstAlpha(group);
		String code = catalogID + "_" + item + "_" + count + "_" + groupAlias
				+ "_" + this.dirLevel;
		String cacheFileName = "/cache/template/block_" + code + ".html";
		String targetFileName = "cache/page/block_" + code + ".html";

		FileUtil.writeText(cacheDir + cacheFileName, jspContent);

		ZCPageBlockSchema block = new ZCPageBlockSchema();
		long blockID = NoUtil.getMaxID("PageBlockID");
		block.setID(blockID);
		block.setSiteID(this.siteID);

		block.setName(code);
		block.setCode(code);
		block.setFileName(targetFileName);
		block.setTemplate(cacheFileName);
		block.setSortField("PublishDate");

		block.setType(4L);
		block.setContent("");
		block.setAddTime(new Date());
		if (User.getCurrent() != null)
			block.setAddUser(User.getUserName());
		else {
			block.setAddUser("admin");
		}

		this.blockSet.add(block);
		return targetFileName;
	}

	private String getListBlock(String item, int count, String type,
			String catalog, String parent, String jspContent) {
		String pageString = null;
		String key = null;
		String catalogID = null;
		if (XString.isNotEmpty(catalog)) {
			if (XString.isDigit(catalog)) {
				catalogID = catalog;
			} else if (XString.isNotEmpty(parent))
				catalogID = CatalogUtil.getIDByName(this.siteID, parent,
						catalog);
			else {
				try {
					if (catalog.indexOf(",") != -1)
						catalogID = CatalogUtil.getIDsByName(this.siteID,
								catalog);
					else if (catalog.indexOf("/") != -1)
						catalogID = CatalogUtil.getIDByNames(this.siteID,
								catalog);
					else
						catalogID = CatalogUtil.getIDByName(this.siteID,
								catalog);
				} catch (Exception e) {
					LogUtil.warn(e.getMessage());
				}
			}

		}

		if (catalogID != null) {
			String cacheDir = this.templateDir + "/"
					+ SiteUtil.getAlias(this.siteID);
			cacheDir = cacheDir.replace('\\', '/').replaceAll("//", "/");

			File cacheFileDir = new File(cacheDir + "/cache/template");
			if (!cacheFileDir.exists()) {
				cacheFileDir.mkdirs();
			}
			if ((XString.isEmpty(type)) || ("null".equals(type))) {
				type = "default";
			}
			key = this.siteID + "_" + catalogID;
			String hex = XString.md5Hex(jspContent).substring(0, 8);
			String code = catalogID + "_" + item + "_" + type + "_" + count
					+ "_" + hex + "_" + this.dirLevel;
			String cacheFileName = "/cache/template/block_" + code + ".html";
			String targetFileName = "cache/page/block_" + code + ".html";

			FileUtil.writeText(cacheDir + cacheFileName, jspContent);

			if (catalogID.indexOf(",") != -1) {
				String[] catalogIDs = catalogID.split(",");
				for (int m = 0; m < catalogIDs.length; m++) {
					ZCPageBlockSchema block = new ZCPageBlockSchema();
					long blockID = NoUtil.getMaxID("PageBlockID");
					block.setID(blockID);
					block.setSiteID(this.siteID);
					block.setCatalogID(catalogIDs[m]);
					block.setName(key);
					block.setCode(code);
					block.setFileName(targetFileName);
					block.setTemplate(cacheFileName);
					block.setSortField("PublishDate");

					block.setType(4L);
					block.setContent("");
					block.setAddTime(new Date());
					if (User.getCurrent() != null)
						block.setAddUser(User.getUserName());
					else {
						block.setAddUser("admin");
					}
					this.blockSet.add(block);
				}
			} else {
				key = this.siteID + "_" + catalog;
				ZCPageBlockSchema block = new ZCPageBlockSchema();
				long blockID = NoUtil.getMaxID("PageBlockID");
				block.setID(blockID);
				block.setSiteID(this.siteID);
				block.setCatalogID(catalogID);
				block.setName(key);
				block.setCode(code);
				block.setFileName(targetFileName);
				block.setTemplate(cacheFileName);
				block.setSortField("PublishDate");

				block.setType(4L);
				block.setContent("");
				block.setAddTime(new Date());
				if (User.getCurrent() != null)
					block.setAddUser(User.getUserName());
				else {
					block.setAddUser("admin");
				}
				this.blockSet.add(block);
			}
			pageString = "<!--#include virtual=\"" + getLevelStr()
					+ targetFileName + "\"-->";
		} else {
			pageString = "没有找到栏目。 parent：" + parent + "  catalog：" + catalog;
		}
		return pageString;
	}

	private void parsePageBar() {
		Matcher m = pageBar.matcher(this.content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		int count = 0;
		while (m.find(lastEndIndex)) {
			sb.append(this.content.substring(lastEndIndex, m.start()));
			String jspContent = "<%write(TemplateData.getPageBar(" + count
					+ "));%>";
			sb.append(jspContent);
			lastEndIndex = m.end();
			count++;
		}
		sb.append(this.content.substring(lastEndIndex));
		this.content = sb.toString();
	}

	private void parsePageBreakBar() {
		Matcher m = pageBreakBar.matcher(this.content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		int count = 0;
		while (m.find(lastEndIndex)) {
			sb.append(this.content.substring(lastEndIndex, m.start()));
			String jspContent = "<%write(TemplateData.getPageBreakBar(" + count
					+ "));%>";
			sb.append(jspContent);
			lastEndIndex = m.end();
			count++;
		}
		sb.append(this.content.substring(lastEndIndex));
		this.content = sb.toString();
	}

	public String parsePlaceHolder(String content) {
		StringBuffer sb = new StringBuffer();
		Matcher m = pField.matcher(content);
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			String table = m.group(1);
			String field = m.group(2);

			if (table != null) {
				table = table.toLowerCase();
			}
			if (field != null) {
				field = field.toLowerCase();
			}

			sb.append(table + ".getString(\"" + field + "\")");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));
		return sb.toString();
	}

	public String parsePlaceHolderStr(String content) {
		StringBuffer sb = new StringBuffer();
		sb.append("\"");
		Matcher m = pField.matcher(content);
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			String table = m.group(1);
			String field = m.group(2);

			if (table != null) {
				table = table.toLowerCase();
			}
			if (field != null) {
				field = field.toLowerCase();
			}

			sb.append("\"+" + table + ".getString(\"" + field + "\")+\"");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex) + "\"");

		content = sb.toString();
		sb = new StringBuffer();
		m = Constant.PatternField.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(content.substring(lastEndIndex, m.start()));
			String field = m.group(1);

			sb.append("\"+" + field + "+\"");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		return sb.toString();
	}

	private String parseSubList(String subContent, long varIndex,
			String parentItem, String parentVar) {
		Matcher m = cmsSubList.matcher(subContent);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(subContent.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String listContent = m.group(2);

			String item = ((String) map.get("item")).toLowerCase();
			String type = (String) map.get("type");
			String countStr = (String) map.get("count");
			String condition = (String) map.get("condition");
			String begin = (String) map.get("begin");
			String attribute = (String) map.get("hasattribute");

			String relaColumn = (String) map.get("RelaColumn");
			String subNewVar = "";
			if (parentItem.equals(item)) {
				parentItem = parentVar;
				subNewVar = parentItem + "_" + varIndex;
			} else {
				subNewVar = item + "_" + varIndex;
			}

			Pattern p = Pattern.compile("(" + parentItem + ")\\.", 2);
			Matcher matcher = p.matcher(listContent);
			listContent = matcher.replaceAll(parentItem + "_" + varIndex + ".");

			p = Pattern.compile("(" + item + ")\\.", 2);
			matcher = p.matcher(listContent);
			listContent = matcher.replaceAll(item + "_" + varIndex + ".");

			int count = 0;
			if ((XString.isNotEmpty(countStr))
					&& (XString.isDigit(countStr)))
				count = Integer.parseInt(countStr);
			else {
				count = 20;
			}

			String beginIndex = null;
			if ((XString.isNotEmpty(begin)) && (XString.isDigit(begin))) {
				beginIndex = begin;
				count += Integer.parseInt(begin);
			} else {
				beginIndex = "0";
			}

			if (("article".equalsIgnoreCase(item))
					|| ("image".equalsIgnoreCase(item))
					|| ("video".equalsIgnoreCase(item))
					|| ("audio".equalsIgnoreCase(item))
					|| ("attachment".equalsIgnoreCase(item))) {
				String catalog = (String) map.get("catalog");
				String level = (String) map.get("level");
				if ((XString.isNotEmpty(catalog))
						&& (catalog.indexOf("${") != -1))
					catalog = parsePlaceHolder(catalog);
				else {
					catalog = "\"" + catalog + "\"";
				}

				String catalogID = null;
				if (relaColumn != null)
					catalogID = parentVar + ".getString(\"" + relaColumn
							+ "\")";
				else {
					catalogID = parentVar + ".getString(\"ID\")";
				}

				if (XString.isEmpty(level))
					level = "\"all\"";
				else {
					level = "\"" + level + "\"";
				}

				String jspContent = "<!--循环：" + item + " count：" + count
						+ "-->\n" + "<% DataTable dt_" + varIndex
						+ " = TemplateData.getDocList(\"" + item + "\","
						+ catalogID + "," + level + ",\"" + attribute + "\",\""
						+ type + "\",\"" + count + "\",\"" + condition
						+ "\");\n" + "if(dt_" + varIndex
						+ "==null){write(\"没有找到栏目\");}else{\n" + "for(int i="
						+ beginIndex + ";i<dt_" + varIndex
						+ ".getRowCount();i++){\n" + "DataRow " + subNewVar
						+ "  = dt_" + varIndex + ".getDataRow(i);\n" + "%>\n";

				jspContent = jspContent + listContent;
				jspContent = jspContent + "<%}}%>";
				sb.append(jspContent);
			} else if ("voteitem".equalsIgnoreCase(item)) {
				String parentID = parentVar + ".getString(\"ID\")";
				String inputType = parentVar + ".getString(\"type\")";
				String jspContent = "<!--循环：" + item + " count：" + count
						+ "-->\n" + "<% DataTable dt_" + varIndex
						+ " = TemplateData.getVoteItems(" + parentID + ","
						+ inputType + "," + count + ");\n"
						+ "for(int j=0;j<dt_" + varIndex
						+ ".getRowCount();j++){" + "DataRow " + subNewVar
						+ "  = dt_" + varIndex + ".getDataRow(j);\n" + "%>";
				jspContent = jspContent + listContent;
				jspContent = jspContent + "<%}%>";

				sb.append(jspContent);
			} else {
				String parentID = "\"0\"";
				if (relaColumn != null)
					parentID = parentVar + ".getString(\"" + relaColumn + "\")";
				else {
					parentID = parentVar + ".getString(\"ID\")";
				}

				String jspContent = "<!--循环：" + item + " count：" + count
						+ "-->\n" + "<% DataTable dt_" + varIndex
						+ " = TemplateData.getCatalogList(\"" + item + "\",\""
						+ type + "\"," + parentID + ",\"child\"," + "\""
						+ count + "\"," + "null);\n" + "for(int j=0;j<dt_"
						+ varIndex + ".getRowCount();j++){" + "DataRow "
						+ subNewVar + "  = dt_" + varIndex
						+ ".getDataRow(j);\n" + "%>";
				jspContent = jspContent + listContent;
				jspContent = jspContent + "<%}%>";

				sb.append(jspContent);
			}

			varIndex += 1L;
		}
		sb.append(subContent.substring(lastEndIndex));
		return sb.toString();
	}

	private void parseImagePlayer() {
		Matcher m = cmsImagePlayer.matcher(this.content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(this.content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));

			String name = (String) map.get("name");
			if (XString.isEmpty(name)) {
				name = (String) map.get("code");
			}
			String width = (String) map.get("width");
			String height = (String) map.get("height");
			String count = (String) map.get("count");
			String cw = (String) map.get("charwidth");

			String jspContent = "<%write(TemplateData.getImagePlayer(\"" + name
					+ "\",\"" + width + "\",\"" + height + "\",\"" + count
					+ "\",\"" + cw + "\"));%>";
			sb.append(jspContent);
		}
		sb.append(this.content.substring(lastEndIndex));
		this.content = sb.toString();
	}

	private void parseTree() {
		Matcher m = cmsTree.matcher(this.content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(this.content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String id = (String) map.get("id");
			String method = (String) map.get("method");
			String style = (String) map.get("style");
			String levelStr = (String) map.get("level");
			String tagBody = m.group(2);
			tagBody = XString.javaEncode(tagBody);

			String jspContent = "<%write(TemplateData.getTree(\"" + id
					+ "\",\"" + method + "\",\"" + tagBody + "\", \"" + style
					+ "\",\"" + levelStr + "\"));%>";
			sb.append(jspContent);
		}
		sb.append(this.content.substring(lastEndIndex));
		this.content = sb.toString();
	}

	private void parseAD() {
		Matcher m = cmsAD.matcher(this.content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(this.content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String name = (String) map.get("name");
			String type = (String) map.get("type");
			String jspContent = "<%write(TemplateData.getAD(\"" + name
					+ "\",\"" + type + "\"));%>";
			sb.append(jspContent);
		}
		sb.append(this.content.substring(lastEndIndex));
		this.content = sb.toString();
	}

	private void parseForm() {
		Matcher m = cmsForm.matcher(this.content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(this.content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String code = (String) map.get("code");
			String jspContent = "<%write(TemplateData.getForm(\"" + code
					+ "\"));%>";
			sb.append(jspContent);
		}
		sb.append(this.content.substring(lastEndIndex));
		this.content = sb.toString();
	}

	private void parseVote() {
		Matcher m = cmsVote.matcher(this.content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(this.content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String name = (String) map.get("name");
			String id = (String) map.get("id");
			String voteBodyContent = m.group(2);

			if (voteBodyContent.indexOf("cms:list") > 0) {
				String voteVar = "vote_" + lastEndIndex;
				String jspContent = "<%DataRow " + voteVar
						+ " = TemplateData.getVoteData(\"" + name + "\",\""
						+ id + "\");\n" + "if(" + voteVar
						+ "==null){\n write(\"没有找到对应的投票主题:" + name
						+ "\");\n}else{\n%>";
				sb.append(jspContent);

				voteBodyContent = "<div id='vote_${vote.id}' class='votecontainer' style='text-align:left'><form id='voteForm_${vote.id}' name='voteForm_${vote.id}' action='"
						+ Config.getValue("ServicesContext")
						+ "/VoteResult.jsp' method='post' target='_blank'><input type='hidden' id='ID' name='ID' value='${vote.id}'><input type='hidden' id='VoteFlag' name='VoteFlag' value='Y'>"
						+ voteBodyContent
						+ "<input type='submit' value='提交' onclick='return checkVote(${vote.id});'>&nbsp;&nbsp<input type='button' value='查看' onclick='javascript:window.open(\""
						+ Config.getValue("ServicesContext")
						+ "/VoteResult.jsp?ID=${vote.id}\",\"VoteResult\")'></form></div>";

				String item = "vote";
				Pattern p = Pattern.compile("(" + item + ")\\.", 2);
				Matcher matcher = p.matcher(voteBodyContent);
				voteBodyContent = matcher.replaceAll(voteVar + ".");
				sb.append(parseVoteList(voteBodyContent, voteVar));
				sb.append("<%}%>");
			} else {
				String jspContent = "<%write(TemplateData.getVote(\"" + name
						+ "\",\"" + id + "\"));%>";
				sb.append(jspContent);
			}
		}

		sb.append(this.content.substring(lastEndIndex));
		this.content = sb.toString();
	}

	private String parseVoteList(String parseContent, String voteVar) {
		Matcher m = cmsList.matcher(parseContent);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		int varIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(parseContent.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String listContent = m.group(2);

			String item = ((String) map.get("item")).toLowerCase();
			String countStr = (String) map.get("count");
			String begin = (String) map.get("begin");
			String var = (String) map.get("var");

			if (XString.isEmpty(var)) {
				var = item + "_" + varIndex;
				Pattern p = Pattern.compile("(" + item + ")\\.", 2);
				Matcher matcher = p.matcher(listContent);
				listContent = matcher.replaceAll(item + "_" + varIndex + ".");
			}

			listContent = parseSubList(listContent, System.currentTimeMillis(),
					item, var);

			int count = 0;
			if ((XString.isNotEmpty(countStr))
					&& (XString.isDigit(countStr)))
				count = Integer.parseInt(countStr);
			else {
				count = 20;
			}

			String beginIndex = null;
			if ((XString.isNotEmpty(begin)) && (XString.isDigit(begin))) {
				beginIndex = begin;
				count += Integer.parseInt(begin);
			} else {
				beginIndex = "0";
			}

			if ("voteSubject".equalsIgnoreCase(item)) {
				String jspContent = "<!--循环：" + item + " count：" + count
						+ "-->\n" + "<% DataTable dt_" + varIndex
						+ " = TemplateData.getVoteSubjects(" + voteVar
						+ ".getString(\"id\")," + count + ");\n" + "if(dt_"
						+ varIndex + "==null){write(\"没有找到投票列表。\");}else{\n"
						+ "for(int i=" + beginIndex + ";i<dt_" + varIndex
						+ ".getRowCount();i++){" + "DataRow " + var + "  = dt_"
						+ varIndex + ".getDataRow(i);\n" + "%>";
				jspContent = jspContent + listContent;
				jspContent = jspContent + "<%}}%>";

				sb.append(jspContent);
			}
			varIndex++;
		}
		sb.append(parseContent.substring(lastEndIndex));
		return sb.toString();
	}

	private void parseComment() {
		Matcher m = cmsComment.matcher(this.content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(this.content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String count = (String) map.get("count");
			String jspContent = "<% if(\"1\".equals(TemplateData.getDoc().getString(\"CommentFlag\"))){\nwrite(TemplateData.getComment(\""
					+ count + "\"));\n}%>";
			sb.append(jspContent);
		}
		sb.append(this.content.substring(lastEndIndex));
		this.content = sb.toString();
	}

	private void parseInclude() {
		Matcher m = cmsInclude.matcher(this.content);
		StringBuffer sb = new StringBuffer();
		String siteAlias = SiteUtil.getAlias(this.siteID);

		String sitePath = this.staticDir + "/" + siteAlias + "/";
		String templateFilePath = sitePath + "template/";
		if (this.isPreview) {
			sitePath = Config.getContextRealPath();
		}

		int lastEndIndex = 0;

		while (m.find(lastEndIndex)) {
			sb.append(this.content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String file = (String) map.get("file");
			file = file.replaceAll("\\.\\./", "").replaceAll("/+", "/");
			if ((this.isPreview) && (file.startsWith(Config.getContextPath()))) {
				file = XString.replaceEx(file, Config.getContextPath(), "/");
			}
			String includeDir = sitePath + file;
			String includePath = file.substring(0, file.lastIndexOf("/") + 1);

			includeDir = includeDir.substring(0,
					includeDir.lastIndexOf("/") + 1);

			String fileName = file.substring(file.lastIndexOf("/") + 1);
			if (XString.isEmpty(fileName)) {
				Errorx.addError("cms:include没有配置file属性，请检查。");
			} else {
				String includeContent = null;
				File includeFile = new File(sitePath + file);
				if (!includeFile.exists()) {
					includeFile = new File(templateFilePath + fileName);
					if (!includeFile.exists()) {
						Errorx.addError("文件" + sitePath + file + "不存在，请检查。");
					} else {
						File includeFileDir = new File(includeDir);
						if (!includeFileDir.exists()) {
							includeFileDir.mkdirs();
						}
						includeContent = FileUtil.readText(includeFile);
						if (includeContent.indexOf("${") != -1)
							includeContent = "请先发布附带页面片段："
									+ (String) map.get("file");
					}
				} else {
					includeContent = FileUtil.readText(includeFile);
				}
				String levelStr = getLevelStr();
				String fileLevelName = fileName.substring(0, fileName
						.lastIndexOf('.'))
						+ "_"
						+ this.dirLevel
						+ fileName.substring(fileName.lastIndexOf('.'));

				this.isPageBlock = false;
				includeContent = dealResource(includeContent, levelStr);

				if (this.isPreview) {
					sb.append(includeContent);
				} else {
					String includeFileName = includeDir + fileLevelName;
					FileUtil.writeText(includeFileName, includeContent);
					this.fileList.add(includeFileName);

					if ("Y".equals(Config.getValue("Big5ConvertFlag"))) {
						String big5Html = Big5Convert
								.toTradition(includeContent);
						big5Html = big5Html.replaceAll("cn/", "big5/");

						String big5FileName = includeFileName.substring(0,
								includeFileName.lastIndexOf('.'))
								+ "_big5"
								+ includeFileName.substring(includeFileName
										.lastIndexOf('.'));
						FileUtil.writeText(big5FileName, big5Html);
						LogUtil.info(big5FileName);
						this.fileList.add(big5FileName);
					}

					file = levelStr + includePath + fileLevelName;
					String jspContent = "<!--#include virtual=\"" + file
							+ "\"-->";
					sb.append(jspContent);
				}
			}
		}
		sb.append(this.content.substring(lastEndIndex));
		this.content = sb.toString();
	}

	private String getLevelStr() {
		String levelString = "";
		if (this.isPreview) {
			levelString = Config.getContextPath()
					+ Config.getValue("Statical.TargetDir") + "/"
					+ SiteUtil.getAlias(this.siteID) + "/";
			levelString = levelString.replace('\\', '/').replaceAll("/+", "/");
		} else {
			for (int i = 0; i < this.dirLevel; i++) {
				levelString = levelString + "../";
			}
		}

		return levelString;
	}

	private void parseLinkURL() {
		Matcher m = cmsLink.matcher(this.content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(this.content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String type = (String) map.get("type");
			String code = (String) map.get("code");
			String name = (String) map.get("name");
			String homePageName = (String) map.get("homePageName");
			if ((XString.isNotEmpty(homePageName))
					&& (XString.isEmpty(name))) {
				name = homePageName;
			}
			String spliter = (String) map.get("spliter");

			String jspContent = "<%write(TemplateData.getLinkURL(\"" + type
					+ "\",\"" + code + "\",\"" + name + "\",\"" + spliter
					+ "\"));%>";
			sb.append(jspContent);
		}
		sb.append(this.content.substring(lastEndIndex));
		this.content = sb.toString();
	}

	private void parsePage() {
		Matcher m = cmsPage.matcher(this.content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(this.content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String type = (String) map.get("type");
			String value = (String) map.get("value");
			String name = (String) map.get("name");
			String target = (String) map.get("target");

			String jspContent = "<%write(TemplateData.getPage(\"" + type
					+ "\",\"" + value + "\",\"" + name + "\",\"" + target
					+ "\"));%>";
			sb.append(jspContent);
		}
		sb.append(this.content.substring(lastEndIndex));
		this.content = sb.toString();
	}

	private void parseVar() {
		Matcher m = cmsVar.matcher(this.content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			sb.append(this.content.substring(lastEndIndex, m.start()));
			lastEndIndex = m.end();
			Mapx map = getAttrMap(m.group(1));
			String type = (String) map.get("type");
			if (XString.isNotEmpty(type)) {
				type = type.toLowerCase();
			}
			String name = (String) map.get("name");
			String title = (String) map.get("title");

			if (XString.isEmpty(name)) {
				name = title;
			}

			if (XString.isEmpty(name)) {
				sb.append("cms:var标签中的name属性不能为空。");
			} else {
				String listContent = m.group(2);
				long index = OrderUtil.getDefaultOrder();
				String newVar = type + "_" + index;
				Pattern p = Pattern.compile("(" + type + ")\\.", 2);
				Matcher matcher = p.matcher(listContent);
				listContent = matcher.replaceAll(type + "_" + index + ".");

				String jspContent = null;
				if (("article".equalsIgnoreCase(type))
						|| ("image".equalsIgnoreCase(type))
						|| ("video".equalsIgnoreCase(type))
						|| ("audio".equalsIgnoreCase(type))
						|| ("attachment".equalsIgnoreCase(type)))
					jspContent = "<% DataRow " + newVar
							+ "=TemplateData.getDocument(\"" + type + "\",\""
							+ name + "\"); if(" + newVar
							+ "==null){write(\"没有找到对象" + name + "+\");}else{%>";
				else if ("catalog".equalsIgnoreCase(type)) {
					jspContent = "<% DataRow " + newVar
							+ "=TemplateData.getCatalog(\"" + type + "\",\""
							+ name + "\"); if(" + newVar
							+ "==null){write(\"没有找到对象" + name + "+\");}else{%>";
				}

				sb.append(jspContent);
				sb.append(listContent);
				sb.append("<%}%>");
			}
		}
		sb.append(this.content.substring(lastEndIndex));
		this.content = sb.toString();
	}

	public boolean parse() {
		if (this.content == null) {
			return false;
		}
		StringBuffer initContent = new StringBuffer();
		initContent.append(this.content);
		this.content = initContent.toString();

		processPath();
		parseInclude();
		parseImagePlayer();
		parseAD();
		parseForm();
		parseVote();
		parseComment();
		parseLinkURL();
		parsePage();
		parseTree();
		parseVar();
		parseList();
		parsePageBreakBar();

		addHeader();
		dealBlock();

		return true;
	}

	private void dealBlock() {
		if ((this.blockSet != null) && (this.blockSet.size() > 0)) {
			int index = this.templateFileName.indexOf(this.staticDir);
			this.templateFileName = this.templateFileName.substring(index
					+ this.staticDir.length());
			this.templateFileName = (this.templateFileName + "_" + this.dirLevel);
			this.trans
					.add(new QueryBuilder(
							"delete from zcpageblock where exists (select blockid from  ZCTemplateBlockRela where filename=? and blockid=zcpageblock.id)",
							this.templateFileName));
			this.trans.add(new QueryBuilder(
					"delete  from  ZCTemplateBlockRela where filename=?",
					this.templateFileName));
			this.trans.add(this.blockSet, OperateType.INSERT);

			ZCTemplateBlockRelaSet relaSet = new ZCTemplateBlockRelaSet();
			for (int i = 0; i < this.blockSet.size(); i++) {
				ZCTemplateBlockRelaSchema rela = new ZCTemplateBlockRelaSchema();
				rela.setSiteID(this.siteID);
				rela.setFileName(this.templateFileName);
				rela.setBlockID(this.blockSet.get(i).getID());
				rela.setAddTime(new Date());
				if ((User.getCurrent() != null) && (User.getUserName() != null))
					rela.setAddUser(User.getUserName());
				else {
					rela.setAddUser("SYSTEM");
				}

				relaSet.add(rela);
			}
			this.trans.add(relaSet, OperateType.INSERT);

			this.trans.commit();
		}
	}

	private static Mapx getAttrMap(String str) {
		Mapx map = new Mapx();
		Matcher m = pAttr1.matcher(str);
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String value = m.group(3);
			if (value != null) {
				value = value.trim();
			}
			map.put(m.group(1).toLowerCase(), value);
			lastEndIndex = m.end();
		}

		m = pAttr2.matcher(str);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String value = m.group(2);
			if (value != null) {
				value = value.trim();
			}
			map.put(m.group(1).toLowerCase(), value);
			lastEndIndex = m.end();
		}
		return map;
	}

	public String getTemplateFileName() {
		return this.templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public Errorx getError() {
		return this.error;
	}

	public void setError(Errorx error) {
		this.error = error;
	}

	public int getDirLevel() {
		return this.dirLevel;
	}

	public void setDirLevel(int dirLevel) {
		this.dirLevel = dirLevel;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isPagelock() {
		return this.isPageBlock;
	}

	public void setPageBlock(boolean isPageBlock) {
		this.isPageBlock = isPageBlock;
	}

	public ZCPageBlockSet getBlockSet() {
		return this.blockSet;
	}

	public void setBlockSet(ZCPageBlockSet blockSet) {
		this.blockSet = blockSet;
	}

	public long getSiteID() {
		return this.siteID;
	}

	public void setSiteID(long siteID) {
		this.siteID = siteID;
	}

	public ArrayList getFileList() {
		return this.fileList;
	}

	public Mapx getPageListPrams() {
		return this.pageListPrams;
	}

	public void setPageListPrams(Mapx pageListPrams) {
		this.pageListPrams = pageListPrams;
	}

	public boolean isPreview() {
		return this.isPreview;
	}

	public void setPreview(boolean isPreview) {
		this.isPreview = isPreview;
	}
}

/*
 * com.xdarkness.cms.template.TagParser JD-Core Version: 0.6.0
 */