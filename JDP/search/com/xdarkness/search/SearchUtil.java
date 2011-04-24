package com.xdarkness.search;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xdarkness.framework.jaf.controls.HtmlElement;
import com.xdarkness.framework.util.CaseIgnoreMapx;
import com.xdarkness.framework.util.Mapx;

/**
 * 
 * @author Darkness
 * Create on Nov 19, 2010 1:34:29 PM
 * @version 1.0
 */
public class SearchUtil {
	public static final Pattern emailPattern = Pattern
			.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

	public static final Pattern resourcePattern1 = Pattern.compile(
			"\\s(href|file|virtual|src)\\s*=\\s*([^\\\"\\'\\s>]+)(\\s|>|(/>))",
			34);

	public static final Pattern resourcePattern2 = Pattern
			.compile(
					"\\s(href|file|virtual|src)\\s*?=\\s*?(\\\"|\\')([^\\\"\\'\\s>]+)\\2",
					34);

	public static final Pattern redirectPattern1 = Pattern
			.compile(
					"<meta\\s*?http\\-equiv.*?refresh.*?content\\s*?=(.*?)url\\s*?=\\s*?(\\\"|\\')([^\\\"\\'\\s]+?)\\2.*?>",
					34);

	public static final Pattern redirectPattern4 = Pattern
			.compile(
					"<meta\\s*?http\\-equiv.*?refresh.*?content\\s*?=.*?url\\s*?=\\s*?([^\\\"\\'\\s]+?)(\\\"|\\'|\\s|>|(/>))",
					34);
	public static final Pattern redirectPattern2;
	public static final Pattern redirectPattern3 = Pattern
			.compile(
					"<script.*?[\\s|>](location|document\\.location|window\\.location)\\.replace\\(\\s*?(\\\"|\\')([^\\\"\\'\\s]+?)\\2\\)[\\s|;|<].*?\\/script>",
					34);
	public static Pattern urlEncodedPattern;
	public static final Pattern pagePattern1;
	public static final Pattern pagePattern2;
	public static final Pattern pagePattern3;
	public static final Pattern isPagePattern1;
	public static final Pattern isPagePattern2;
	public static final Pattern isPagePattern3;
	static SimpleDateFormat lastModifedFormat1;
	static SimpleDateFormat lastModifedFormat2;
	static SimpleDateFormat lastModifedFormat3;
	static SimpleDateFormat lastModifedFormat4;
	public static final Pattern inputPattern;

	static {
		String[] arr = { "location", "location.href", "window.location",
				"window.location.href", "document.location",
				"document.location.href" };
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i != 0) {
				sb.append("|");
			}
			sb.append(arr[i].replaceAll("\\.", "\\."));
		}
		redirectPattern2 = Pattern
				.compile(
						"<script.*?[\\s|>]("
								+ sb
								+ ")\\s*?=\\s*?(\\\"|\\')([^\\\"\\'\\s\\+]+?)\\2[\\s|;|<].*?\\/script>",
						34);

		urlEncodedPattern = Pattern.compile("\\%[0-9A-F]{2}");

		pagePattern1 = Pattern.compile(
				"\\shref\\s*=([^\\\"\\'\\s>]+)[^>]*>(.*?)<\\s*\\/\\s*a\\s*>",
				34);

		pagePattern2 = Pattern
				.compile(
						"\\shref\\s*?=\\s*?\\'([^\\'\\s>]+)\\'.*?>(.*?)<\\s*\\/\\s*a\\s*>",
						34);

		pagePattern3 = Pattern
				.compile(
						"\\shref\\s*?=\\s*?\\\"([^\\\"\\s>]+)\\\".*?>(.*?)<\\s*\\/\\s*a\\s*>",
						34);

		isPagePattern1 = Pattern.compile("<.*?>", 34);

		isPagePattern2 = Pattern.compile("\\&\\w*?\\;", 34);

		isPagePattern3 = Pattern.compile(
				"[<\\{\\[\\|\\\\\\/]?\\d{1,4}[>\\}\\]\\|\\\\\\/]?", 34);

		lastModifedFormat1 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss",
				Locale.ENGLISH);

		lastModifedFormat2 = new SimpleDateFormat(
				"z EEE, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);

		lastModifedFormat3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",
				Locale.ENGLISH);

		lastModifedFormat4 = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z",
				Locale.ENGLISH);

		inputPattern = Pattern.compile("<input (.*?)>", 34);
	}

	public static String normalizeUrl(String url, String base) {
		if ((url.startsWith("http://")) || (url.startsWith("https://"))) {
			return url;
		}
String domain = "http://x";// x表示域名
		int domainLength = domain.length();
		int index = base.lastIndexOf('/');
		if (index > domainLength) {
			base = base.substring(0, index);
		}
		if (url.startsWith("/")) {
			index = base.indexOf('/', domainLength);
			if (index > 0) {
				base = base.substring(0, index);
			}
			return base + url;
		}
		if (!url.startsWith("../")) {
			if (!url.startsWith("./"))
				;
		} else
			while ((url.startsWith("../")) || (url.startsWith("./"))) {
				if (url.startsWith("./")) {
					url = url.substring(2);
				} else {
					index = base.lastIndexOf('/');
					if (index > 8) {
						base = base.substring(0, index);
					}
					url = url.substring(3);
				}
			}
/*
if (url.startsWith("../") || url.startsWith("./")){
				while ((url.startsWith("../")) || (url.startsWith("./"))) {
					if (url.startsWith("./")) {
						url = url.substring(2);
					} else {
						index = base.lastIndexOf('/');
						if (index > domainLength) {
							base = base.substring(0, index);
						}
						url = url.substring(3);
					}
				}
		}
*/

		return base + "/" + url;
	}

	public static boolean isRightUrl(String url) {
		if ((url.equals("")) || (url.indexOf(' ') > 0)
				|| (url.toLowerCase().startsWith("res:"))
				|| (url.toLowerCase().startsWith("file:"))
				|| (url.toLowerCase().startsWith("ftp:"))
				|| (url.toLowerCase().startsWith("javascript:"))
				|| (url.toLowerCase().startsWith("mailto:"))
				|| (url.startsWith("#")) || (url.startsWith("&"))
				|| (url.startsWith("<"))) {
			return false;
		}

		if (url.indexOf("@") > 0) {
			return false;
		}

		return (url.indexOf("&gt;") <= 0) && (url.indexOf("&lt;") <= 0)
				&& (url.indexOf("&nbsp;") <= 0) && (url.indexOf("&quot;") <= 0);
	}

	public static boolean isRightUrl2(String url) {
		if ((url.equals("")) || (url.indexOf(' ') > 0)
				|| (url.toLowerCase().startsWith("res:"))
				|| (url.toLowerCase().startsWith("file:"))
				|| (url.toLowerCase().startsWith("ftp:"))
				|| (url.toLowerCase().startsWith("javascript:"))
				|| (url.toLowerCase().startsWith("mailto:"))
				|| (url.startsWith("#")) || (url.startsWith("&"))
				|| (url.startsWith("<"))) {
			return false;
		}

		return url.indexOf("@") <= 0;
	}

	public static String[] getUrlFromHtml(String content) {
		Matcher m = resourcePattern1.matcher(content);
			ArrayList<String> arr = new ArrayList<String>();
		int lastEndIndex = 0;

		while (m.find(lastEndIndex)) {
			String url = m.group(2);
			if (isRightUrl(url)) {
				arr.add(url);
			}
			lastEndIndex = m.end();
		}

		m = resourcePattern2.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String url = m.group(3);
			if (isRightUrl2(url)) {
				arr.add(XString.replaceEx(url, "&amp;", "&"));
			}
			lastEndIndex = m.end();
		}
		//String[] urls = new String[arr.size()];
	//	for (int i = 0; i < arr.size(); i++) {
	//		urls[i] = ((String) arr.get(i));
	//	}
	//	return urls;
return arr.toArray(new String[0]);
	}

	public static String getRedirectURL(WebDocument doc) {
		if (!doc.isTextContent()) {
			return null;
		}
		String content = doc.getContentText();
		Matcher m = redirectPattern1.matcher(content);
		if (m.find()) {
			return m.group(3);
		}
		m = redirectPattern2.matcher(content);
		if (m.find()) {
			return m.group(3);
		}
		m = redirectPattern3.matcher(content);
		if (m.find()) {
			return m.group(3);
		}
		m = redirectPattern4.matcher(content);
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}

	private static String encodeUrlEx(String url, String charset) {
		StringBuffer sb = new StringBuffer();
		boolean regexFlag = false;
		for (int i = 0; i < url.length(); i++) {
			String c = url.substring(i, i + 1);
			if ((c.equals("%"))
					&& (i < url.length() - 2)
					&& (urlEncodedPattern.matcher(url.substring(i, i + 3))
							.find())) {
				sb.append(url.substring(i, i + 3));
				i += 2;
			} else {
				if ((i < url.length() - 2) && (c.equals("$"))
						&& (url.substring(i + 1, i + 2).equals("{"))) {
					regexFlag = true;
				}
				if (regexFlag) {
					sb.append(c);
					if (c.equals("}")) {
						if ((i < url.length() - 1)
								&& (url.substring(i + 1, i + 2).equals("}"))) {
							sb.append("}");
							i++;
						}
						regexFlag = false;
					}
				} else {
					sb.append(XString.urlEncode(c, charset));
				}
			}
		}
		return sb.toString();
	}

	public static String escapeUrl(String url) {
		int index = url.indexOf('?');
		String head = null;
		if (index < 0)
			head = url;
		else {
			head = url.substring(0, index);
		}
		StringBuffer sb = new StringBuffer();
		index = head.indexOf('/') + 3;
		index = head.indexOf('/', index);
		if (index < 0) {
			return url;
		}
		sb.append(head.substring(0, index));
		head = head.substring(index);
		String[] pairs = head.split("\\/");
		boolean isEncoded = false;
		for (int i = 0; i < pairs.length; i++) {
			if (XString.isEmpty(pairs[i])) {
				sb.append("/");
			} else {
				if (!isEncoded) {
					if (!urlEncodedPattern.matcher(pairs[i]).find())
						pairs[i] = encodeUrlEx(pairs[i], "UTF-8");
					else {
						isEncoded = true;
					}
				}
				sb.append(pairs[i]);
				if (i != pairs.length - 1)
					sb.append("/");
			}
		}
		index = url.indexOf('?');
		if (index < 0) {
			return sb.toString();
		}
		sb.append("?");
		String queryString = url.substring(index + 1);
		String tail = "";
		index = queryString.indexOf('#');
		if (index > 0) {
			tail = queryString.substring(index);
			queryString = queryString.substring(0, index);
		}
		pairs = queryString.split("\\&");
		for (int i = 0; i < pairs.length; i++) {
			if (XString.isEmpty(pairs[i])) {
				continue;
			}
			if (i != 0) {
				sb.append("&");
			}
			String[] arr = pairs[i].split("\\=");
			sb.append(encodeUrlEx(arr[0], "GBK"));
			if ((arr.length > 1) && (XString.isNotEmpty(arr[1]))) {
				sb.append("=");
				sb.append(encodeUrlEx(arr[1], "GBK"));
			}
		}
		sb.append(tail);
		return sb.toString();
	}

	public static String[] getPageURL(WebDocument doc) {
		String content = doc.getContentText();
//		 System.out.println("===========start====================");
//		 System.out.println(content);
//		 System.out.println("===========end====================");
		Matcher m = pagePattern1.matcher(content);
		ArrayList arr = new ArrayList();
		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String url = m.group(1);
			String text = m.group(2);
			if (((isRightUrl(url)) || ((url.startsWith("javascript")) && (url
					.indexOf("__doPostBack(") > 0)))
					&& (isPageURL(url, doc.getUrl(), text))) {
				arr.add(url);
			}

			lastEndIndex = m.end();
		}
		m = pagePattern2.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String url = m.group(1);
			String text = m.group(2);
			if ((isRightUrl2(url))
					|| ((url.startsWith("javascript")) && (url
							.indexOf("__doPostBack(") > 0))) {
				url = XString.replaceEx(url, "&amp;", "&");
				if (isPageURL(url, doc.getUrl(), text)) {
					arr.add(url);
				}
			}
			lastEndIndex = m.end();
		}
		m = pagePattern3.matcher(content);
		lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String url = m.group(1);
			String text = m.group(2);
			if ((isRightUrl2(url))
					|| ((url.startsWith("javascript")) && (url
							.indexOf("__doPostBack(") > 0))) {
				url = XString.replaceEx(url, "&amp;", "&");
				if (isPageURL(url, doc.getUrl(), text)) {
					arr.add(url);
				}
			}
			lastEndIndex = m.end();
		}

		String[] urls = new String[arr.size()];
		for (int i = 0; i < arr.size(); i++) {
			urls[i] = ((String) arr.get(i));
		}
		return urls;
	}

	private static boolean isPageURL(String url, String refurl, String text) {
		if ((url.startsWith("http://")) && (url.indexOf('/', 8) > 0)) {
			String prefix = url.substring(0, url.indexOf('/', 8));
			if (!refurl.startsWith(prefix)) {
				return false;
			}

		}

		if ((text.indexOf("下一页") >= 0) || (text.indexOf("后一页") >= 0)
				|| (text.indexOf("下页") >= 0) || (text.indexOf("尾页") >= 0)
				|| (text.indexOf("末页") >= 0) || (text.indexOf("后页") >= 0)
				|| (text.toLowerCase().indexOf("next") >= 0)
				|| (text.toLowerCase().indexOf("last") >= 0)) {
			return true;
		}
		text = isPagePattern1.matcher(text).replaceAll("");
		text = isPagePattern2.matcher(text).replaceAll("");
		text = text.trim();
		if (isPagePattern3.matcher(text).matches()) {
			return true;
		}
		if (url.indexOf("?") >= 0) {
			url = url.toLowerCase();
			Mapx map = XString.splitToMapx(url
					.substring(url.indexOf("?") + 1), "&", "=");
			try {
				if ((map.containsKey("page"))
						&& (Integer.parseInt(map.getString("page")) > 1)
						&& (Integer.parseInt(map.getString("page")) < 200)) {
					return true;
				}
				if ((map.containsKey("pageindex"))
						&& (Integer.parseInt(map.getString("pageindex")) > 1)
						&& (Integer.parseInt(map.getString("pageindex")) < 200)) {
					return true;
				}
				if ((map.containsKey("pageno"))
						&& (Integer.parseInt(map.getString("pageno")) > 1)
						&& (Integer.parseInt(map.getString("pageno")) < 200)) {
					return true;
				}
				if ((map.containsKey("pagenumber"))
						&& (Integer.parseInt(map.getString("pagenumber")) > 1)
						&& (Integer.parseInt(map.getString("pagenumber")) < 200)) {
					return true;
				}
				if ((map.containsKey("pagenum"))
						&& (Integer.parseInt(map.getString("pagenum")) > 1)
						&& (Integer.parseInt(map.getString("pagenum")) < 200))
					return true;
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	public static Date parseLastModifedDate(String str) {
		try {
			return lastModifedFormat1.parse(str);
		} catch (Exception localException3) {
			try {
				return lastModifedFormat2.parse(str);
			} catch (Exception exceptionx) {
				try {
					return lastModifedFormat3.parse(str);
				} catch (Exception exception) {
					try {
						return lastModifedFormat4.parse(str);
					} catch (Exception exception2) {
					}
				}
			}
		}
		return null;
	}

	public static Mapx getDotNetForm(String html, String js) {
		String[] arr = js.split("\\'");
		String target = arr[1];
		String args = arr[3];

		Matcher m = inputPattern.matcher(html);
		Mapx form = new Mapx();

		int lastEndIndex = 0;
		while (m.find(lastEndIndex)) {
			String attr = m.group(1);
			CaseIgnoreMapx map = new CaseIgnoreMapx(HtmlElement.parseAttr(attr));
			if ((map.containsKey("name")) && (map.containsKey("value"))) {
				form.put(map.get("name"), map.get("value"));
			}
			lastEndIndex = m.end();
		}
		form.put("__EVENTTARGET", target);
		form.put("__EVENTARGUMENT", args);
		return form;
	}
}
