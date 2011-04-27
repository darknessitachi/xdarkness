package com.xdarkness.cms.pub;

import java.io.File;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import com.xdarkness.cms.template.TagParser;
import com.xdarkness.schema.ZCSiteSchema;
import com.xdarkness.framework.extend.ExtendManager;
import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.util.Mapx;

public class PublicInclude {
	private static Mapx timeMap = new Mapx(3000);
	private static Mapx fileMap = new Mapx(3000);
	private static Object mutex = new Object();

	public static String getHtml(String alias, String path) {
		long current = System.currentTimeMillis();
		Long lastTime = (Long) timeMap.get(path);
		String html = null;
		if (lastTime == null)
			synchronized (mutex) {
				lastTime = new Long(current);
				timeMap.put(path, lastTime);
				if (new File(path).exists()) {
					html = FileUtil.readText(path, Constant.GlobalCharset);
					html = dealResource(alias, html);
				} else {
					html = "Include file is not exists!";
				}
				fileMap.put(path, html);
			}
		if (lastTime.longValue() < current - 3000L) {
			if (new File(path).lastModified() > lastTime.longValue()) {
				synchronized (mutex) {
					lastTime = new Long(current);
					timeMap.put(path, lastTime);
					if (new File(path).exists()) {
						html = FileUtil.readText(path, Constant.GlobalCharset);
						html = dealResource(alias, html);
					} else {
						html = "Include file is not exists!";
					}
					fileMap.put(path, html);
				}
			}
			synchronized (mutex) {
				lastTime = new Long(current);
				html = fileMap.getString(path);
				timeMap.put(path, lastTime);
			}
		}

		html = fileMap.getString(path);

		return html;
	}

	public static void getMenu(HttpServletRequest request, JspWriter out)
			throws Exception {
		String cur = request.getParameter("cur");
		if (XString.isEmpty(cur)) {
			cur = "Menu_MI";
		}
		String siteID = getSiteID(request);
		if (XString.isEmpty(siteID)) {
			out.println("URL中必须带有SiteID参数!");
			return;
		}
		StringBuffer sb = new StringBuffer();
		if ("Y".equals(CMSCache.getSite(siteID).getAllowContribute())) {
			sb.append("<ul class='sidemenu'>");
			sb.append("<li id='Menu_WA'><a href='" + Config.getContextPath()
					+ "Member/WriteArticle.jsp?cur=Menu_WA&SiteID=" + siteID
					+ "'>网站文章投稿</a></li>");
			sb.append("<li id='Menu_MA'><a href='" + Config.getContextPath()
					+ "Member/MemberArticles.jsp?cur=Menu_MA&SiteID=" + siteID
					+ "'>我投稿的文章</a></li>");
			sb.append("</ul>");
		}
		sb.append("<ul class='sidemenu'>");
		ExtendManager.executeAll("Member.Menu", new Object[] { sb, siteID,
				request });
		sb.append("<li id='Menu_MI'><a href='" + Config.getContextPath()
				+ "Member/MemberInfo.jsp?cur=Menu_MI&SiteID=" + siteID
				+ "'>编辑个人资料</a></li>");
		sb.append("<li id='Menu_PW'><a href='" + Config.getContextPath()
				+ "Member/Password.jsp?cur=Menu_PW&SiteID=" + siteID
				+ "'>修改密码</a></li>");
		sb.append("</ul>");
		sb.append("<hr class='shadowline'/>");
		sb.append("<ul class='sidemenu'>");
		sb
				.append("<li id='Menu_Logout'><a href='javascript:void(0)' onclick='doLogout();'>退出</a></li>");
		sb.append("</ul>");
		sb.append("<script type='text/javascript'>");
		sb.append("document.getElementById('" + cur + "').className='current'");
		sb.append("</script>");
		out.write(sb.toString());
	}

	public static String getSiteID(HttpServletRequest request) {
		String siteID = request.getParameter("SiteID");
		if (XString.isEmpty(siteID)) {
			siteID = request.getParameter("site");
			if (XString.isEmpty(siteID)) {
				siteID = (String) User.getValue("SiteID");
				if (XString.isEmpty(siteID)) {
					siteID = (String) Current.getVariable("SiteID");
				}
			}
		}
		return siteID;
	}

	public static void getHead(HttpServletRequest request, JspWriter out)
			throws Exception {
		String siteID = getSiteID(request);
		if (XString.isEmpty(siteID)) {
			out.println("URL中必须带有SiteID参数!");
			return;
		}
		String path = SiteUtil.getAbsolutePath(siteID);
		ZCSiteSchema site = SiteUtil.getSchema(siteID);
		if (site == null) {
			out.println("Site=" + siteID + "的站点未找到!");
			return;
		}
		String template = site.getHeaderTemplate();
		if (XString.isEmpty(template)) {
			return;
		}
		if (template.startsWith("/")) {
			template = template.substring(1);
		}
		template = template.substring(template.indexOf('/') + 1);

		path = path + "/include/" + template;
		String html = getHtml(site.getAlias(), path);
		out.write(html);
	}

	public static void getTop(HttpServletRequest request, JspWriter out)
			throws Exception {
		String siteID = getSiteID(request);
		if (XString.isEmpty(siteID)) {
			out.println("URL中必须带有SiteID参数!");
			return;
		}
		String path = SiteUtil.getAbsolutePath(siteID);
		ZCSiteSchema site = SiteUtil.getSchema(siteID);
		if (site == null) {
			out.println("Site=" + siteID + "的站点未找到!");
			return;
		}
		String template = site.getTopTemplate();
		if (XString.isEmpty(template)) {
			template = "head.html";
		} else {
			if (template.startsWith("/")) {
				template = template.substring(1);
			}
			template = template.substring(template.indexOf('/') + 1);
		}
		path = path + "/include/" + template;
		String html = getHtml(site.getAlias(), path);
		out.write(html);
	}

	public static void getBlock(HttpServletRequest request, String fileName,
			JspWriter out) throws Exception {
		String siteID = getSiteID(request);
		if (XString.isEmpty(siteID)) {
			out.println("URL中必须带有SiteID参数!");
			return;
		}
		String path = SiteUtil.getAbsolutePath(siteID);
		ZCSiteSchema site = SiteUtil.getSchema(siteID);
		if (site == null) {
			out.println("Site=" + siteID + "的站点未找到!");
			return;
		}
		String html = getHtml(site.getAlias(), path + fileName);
		out.write(html);
	}

	public static void getBottom(HttpServletRequest request, JspWriter out)
			throws Exception {
		String siteID = getSiteID(request);
		if (XString.isEmpty(siteID)) {
			out.println("URL中必须带有SiteID参数!");
			return;
		}
		String path = SiteUtil.getAbsolutePath(siteID);
		ZCSiteSchema site = SiteUtil.getSchema(siteID);
		if (site == null) {
			out.println("Site=" + siteID + "的站点未找到!");
			return;
		}
		String template = site.getBottomTemplate();
		if (XString.isEmpty(template)) {
			template = "footer.html";
		} else {
			if (template.startsWith("/")) {
				template = template.substring(1);
			}
			template = template.substring(template.indexOf('/') + 1);
		}
		path = path + "/include/" + template;
		String html = getHtml(site.getAlias(), path);
		out.write(html);
	}

	public static String dealResource(String alias, String content) {
		if (content == null) {
			return "";
		}
		content = XString
				.replaceAllIgnoreCase(content, "\\$\\{level\\}", "");
		Matcher m = TagParser.resourcePattern1.matcher(content);
		StringBuffer sb = new StringBuffer();
		int lastEndIndex = 0;

		while (m.find(lastEndIndex)) {
			String dealPath = dealURL(alias, m.group(2));
			sb.append(content.substring(lastEndIndex, m.start()));
			sb.append(" " + m.group(1) + "=" + dealPath + m.group(3));
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		content = sb.toString();
		sb = new StringBuffer();
		m = TagParser.resourcePattern2.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String dealPath = dealURL(alias, m.group(3));
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
		m = TagParser.resourcePatternCss.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String dealPath = dealURL(alias, m.group(2));
			sb.append(content.substring(lastEndIndex, m.start()));
			sb.append(m.group(1) + "(" + dealPath + ")");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));

		content = sb.toString();
		sb = new StringBuffer();
		m = TagParser.resourcePatternCss2.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String dealPath = dealURL(alias, m.group(3));
			sb.append(content.substring(lastEndIndex, m.start()));
			sb.append(m.group(1) + "(" + dealPath + ")");
			lastEndIndex = m.end();
		}
		sb.append(content.substring(lastEndIndex));
		return sb.toString();
	}

	private static String dealURL(String alias, String url) {
		String prefix = (Config.getContextPath()
				+ Config.getValue("Statical.TargetDir") + "/" + alias + "/")
				.replaceAll("/+", "/");
		if (url.startsWith("../")) {
			return prefix + url.substring(3);
		}
		if ((url.indexOf(':') > 0) || (url.indexOf(')') > 0)
				|| (url.indexOf('<') > 0) || (url.indexOf('#') > 0)) {
			return url;
		}
		return prefix + url;
	}
}

/*
 * com.xdarkness.cms.pub.PublicInclude JD-Core Version: 0.6.0
 */