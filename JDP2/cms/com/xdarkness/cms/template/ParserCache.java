package com.xdarkness.cms.template;

import java.io.File;
import java.util.ArrayList;

import com.xdarkness.statical.TemplateParser;
import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;

public class ParserCache {
	private static Mapx parserMap;
	private static Mapx lastModifyMap;

	public static TemplateParser get(long siteID, String template, int level,
			boolean isPageBlock, ArrayList list) {
		template = template.trim();
		if (parserMap == null) {
			parserMap = new Mapx();
			lastModifyMap = new Mapx();
		}

		File templateFile = new File(template);
		template = templateFile.getPath().replace('\\', '/');

		String key = template + level;

		TemplateParser tp = null;

		if (parserMap.get(key) == null) {
			if ((!templateFile.exists()) || (templateFile.isDirectory())) {
				return null;
			}
			update(siteID, templateFile, level, isPageBlock, list);
			tp = (TemplateParser) parserMap.get(template + level);
		} else {
			if ((!templateFile.exists()) || (templateFile.isDirectory())) {
				return null;
			}
			long lastModifyTime = ((Long) lastModifyMap.get(template + level))
					.longValue();
			if (lastModifyTime != templateFile.lastModified()) {
				update(siteID, templateFile, level, isPageBlock, list);
			}
			tp = (TemplateParser) parserMap.get(template + level);
		}

		Mapx map = getConfig(siteID);
		tp.define("system", map);

		return tp;
	}

	public static Mapx getConfig(long siteID) {
		Mapx map = new Mapx();
		String serviceUrl = Config.getValue("ServicesContext");
		String context = serviceUrl;
		if (serviceUrl.endsWith("/")) {
			context = serviceUrl.substring(0, serviceUrl.length() - 1);
		}
		int index = context.lastIndexOf('/');
		if (index != -1) {
			context = context.substring(0, index);
		}

		map.put("servicescontext", serviceUrl);
		map.put("cmscontext", context);
		map.put("searchaction", context + "/Search/Result.jsp");
		map.put("voteaction", serviceUrl + Config.getValue("Vote.ActionURL"));
		map.put("voteresult", serviceUrl + Config.getValue("Vote.ResultURL"));
		map.put("commentaction", serviceUrl
				+ Config.getValue("CommentActionURL"));
		map.put("totalhitcount", "\n<script src=\"" + serviceUrl
				+ "/Counter.jsp?Type=Total&ID=" + siteID
				+ "\" type=\"text/javascript\"></script>\n");
		map.put("todayhitcount", "\n<script src=\"" + serviceUrl
				+ "/Counter.jsp?Type=Today&ID=" + siteID
				+ "\" type=\"text/javascript\"></script>\n");
		return map;
	}

	public static void update(long siteID, File templateFile, int level,
			boolean isPageBlock, ArrayList list) {
		String content = FileUtil.readText(templateFile);
		String templatePath = templateFile.getPath().replace('\\', '/');

		LogUtil.info("模板更新" + templatePath);

		PreParser p = new PreParser();
		p.setSiteID(siteID);
		p.setTemplateFileName(templatePath);
		p.parse();

		TagParser tagParser = new TagParser();
		tagParser.setSiteID(siteID);
		tagParser.setTemplateFileName(templatePath);
		tagParser.setPageBlock(isPageBlock);
		tagParser.setContent(content);
		tagParser.setDirLevel(level);
		tagParser.parse();

		TemplateParser tp = new TemplateParser();
		tp.setFileName(templateFile.getPath());
		tp.addClass("com.xdarkness.cms.pub.CatalogUtil");
		tp.addClass("com.xdarkness.cms.pub.SiteUtil");
		tp.addClass("com.xdarkness.cms.pub.PubFun");
		tp.addClass("com.xdarkness.cms.template.TemplateUtil");
		tp.setPageListPrams(tagParser.getPageListPrams());
		tp.setTemplate(tagParser.getContent());
		try {
			tp.parse();

			parserMap.put(templatePath + level, tp);
			lastModifyMap.put(templatePath + level, new Long(templateFile
					.lastModified()));

			list.addAll(tagParser.getFileList());
		} catch (EvalException e) {
			e.printStackTrace();
		}
	}
}

/*
 * com.xdarkness.cms.template.ParserCache JD-Core Version: 0.6.0
 */