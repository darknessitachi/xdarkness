package com.xdarkness.search.crawl;

import java.io.StringReader;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.RegexParser;
import com.xdarkness.framework.util.XString;
import com.xdarkness.schema.ZCWebGatherSchema;

public class CrawlConfig {
	public static final int TYPE_DOCUMENT = 1;
	public static final int TYPE_CUSTOMTABLE = 1;
	private int type;
	private int language;
	private String name;
	private long ID;
	private boolean proxyFlag;
	private String proxyHost;
	private int proxyPort;
	private String proxyUserName;
	private String proxyPassword;
	private boolean copyImageFlag;// 下载远程图片
	private boolean cleanLinkFlag;// 去掉内容中的超链接
	private long catalogID;// 采集到此栏目
	private String script;
	private String[] urlLevels;// URL层级
	private RegexParser[] filterBlocks;// 过滤块
	private Mapx templateMap;// 匹配块
	private int maxPageCount = 2147483647;// 最大采集数

	private int maxListCount = 2147483647;
	private int threadCount;// 采集线程数
	private int maxLevel = 2147483647;
	private int timeout;// 超时等待时间
	private int retryTimes;// 发生错误时重试次数
	private boolean filterFlag;// 过滤URL
	private String filterExpr;// 过滤表达式
	private String publishDateFormat;// 发布日期格式

	/**
	 * get the web gather dir
	 * 
	 * @return
	 */
	public static String getWebGatherDir() {
		return "WEB-INF/data";
	}

	public void parse(ZCWebGatherSchema wg) {
		this.type = ("D".equals(wg.getType()) ? 1 : 1);
		this.name = wg.getName();
		this.ID = wg.getID();
		this.proxyFlag = ("1".equals(wg.getProxyFlag()));
		this.proxyHost = wg.getProxyHost();
		this.proxyPassword = wg.getProxyPassword();
		this.proxyPort = (int) wg.getProxyPort();
		this.proxyUserName = wg.getProxyUserName();
		parseXML(wg.getConfigXML());
	}

	public void parseXML(String xml) {
		SAXReader reader = new SAXReader(false);
		try {
			Document doc = reader.read(new StringReader(xml));
			Element root = doc.getRootElement();
			List configs = root.elements("config");
			for (int i = 0; i < configs.size(); i++) {
				Element conf = (Element) configs.get(i);
				String key = conf.attributeValue("key");
				String value = conf.attributeValue("value");
				if ((key.equals("CopyImage")) && ("1".equals(value))) {
					this.copyImageFlag = true;
				}
				if ((key.equals("CleanLink")) && ("1".equals(value))) {
					this.cleanLinkFlag = true;
				}
				if (key.equals("PublishDateFormat")) {
					this.publishDateFormat = value;
				}
				if ((key.equals("FilterFlag")) && ("1".equals(value))) {
					this.filterFlag = true;
				}
				if ((key.equals("CatalogID")) && (XString.isNotEmpty(value))) {
					this.catalogID = Long.parseLong(value);
				}
				if ((key.equals("MaxPageCount"))
						&& (XString.isNotEmpty(value))) {
					this.maxPageCount = Integer.parseInt(value);
				}
				if ((key.equals("MaxListCount"))
						&& (XString.isNotEmpty(value))) {
					this.maxListCount = Integer.parseInt(value);
				}
				if ((key.equals("ThreadCount"))
						&& (XString.isNotEmpty(value))) {
					this.threadCount = Integer.parseInt(value);
				}
				if ((key.equals("TimeOut")) && (XString.isNotEmpty(value))) {
					this.timeout = Integer.parseInt(value);
				}
				if ((key.equals("RetryTimes"))
						&& (XString.isNotEmpty(value))) {
					this.retryTimes = Integer.parseInt(value);
				}
			}
			Element escript = root.element("script");
			this.language = ("java".equals(escript.attribute("language")) ? 1
					: 0);
			this.script = escript.getText();

			Element eFilterExpr = root.element("filterExpr");
			this.filterExpr = eFilterExpr.getText();

			List urls = root.elements("urls");
			this.urlLevels = new String[urls.size()];
			for (int i = 0; i < urls.size(); i++) {
				Element url = (Element) urls.get(i);
				String level = url.attributeValue("level");
				String content = url.getText();
				this.urlLevels[(Integer.parseInt(level) - 1)] = content;
			}

			List blocks = root.elements("filterBlock");
			if (blocks != null) {
				this.filterBlocks = new RegexParser[blocks.size()];
				for (int i = 0; i < blocks.size(); i++) {
					Element block = (Element) blocks.get(i);
					String content = block.getText();
					this.filterBlocks[i] = new RegexParser(content);
				}
			}

			List templates = root.elements("template");
			this.templateMap = new Mapx();
			for (int i = 0; i < templates.size(); i++) {
				Element template = (Element) templates.get(i);
				String code = template.attributeValue("code");
				String content = template.getText();
				this.templateMap.put(code, new RegexParser(content));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RegexParser getTemplate(String code) {
		return (RegexParser) this.templateMap.get(code);
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isCopyImageFlag() {
		return this.copyImageFlag;
	}

	public int getLanguage() {
		return this.language;
	}

	public String getName() {
		return this.name;
	}

	public String getProxyHost() {
		return this.proxyHost;
	}

	public String getProxyPassword() {
		return this.proxyPassword;
	}

	public int getProxyPort() {
		return this.proxyPort;
	}

	public String getProxyUserName() {
		return this.proxyUserName;
	}

	public String getScript() {
		return this.script;
	}

	public String[] getUrlLevels() {
		return this.urlLevels;
	}

	public boolean isProxyFlag() {
		return this.proxyFlag;
	}

	public void setProxyFlag(boolean proxyFlag) {
		this.proxyFlag = proxyFlag;
	}

	public long getCatalogID() {
		return this.catalogID;
	}

	public boolean isCleanLinkFlag() {
		return this.cleanLinkFlag;
	}

	public int getMaxPageCount() {
		return this.maxPageCount;
	}

	public void setMaxPageCount(int count) {
		this.maxPageCount = count;
	}

	public int getThreadCount() {
		return this.threadCount;
	}

	public int getTimeout() {
		return this.timeout;
	}

	public long getID() {
		return this.ID;
	}

	public int getRetryTimes() {
		return this.retryTimes;
	}

	public String getFilterExpr() {
		return this.filterExpr;
	}

	public void setFilterExpr(String filterExpr) {
		this.filterExpr = filterExpr;
	}

	public boolean isFilterFlag() {
		return this.filterFlag;
	}

	public void setFilterFlag(boolean filterFlag) {
		this.filterFlag = filterFlag;
	}

	public int getMaxLevel() {
		return this.maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	public void setUrlLevels(String[] urlLevels) {
		this.urlLevels = urlLevels;
	}

	public RegexParser[] getFilterBlocks() {
		return this.filterBlocks;
	}

	public String getPublishDateFormat() {
		return this.publishDateFormat;
	}

	public int getMaxListCount() {
		return this.maxListCount;
	}
}
