package com.abigdreamer.java.net.util;


public class ServletUtil {
//	public static String getHomeURL(HttpServletRequest request) {
//		return request.getScheme() + "://" + request.getServerName() + ":"
//				+ request.getServerPort();
//	}
//
//	public static Mapx getParameterMap(HttpServletRequest request) {
//		return getParameterMap(request, true);
//	}
//
//	public static Mapx<Object, Object> getParameterMap(HttpServletRequest request,
//			boolean otherFlag) {
//		Mapx<Object, Object> map = new Mapx<Object, Object>();
//		Map<?,?> tmap = request.getParameterMap();
//		Object[] keys = tmap.keySet().toArray();
//		for (int i = 0; i < tmap.size(); i++) {
//			Object key = keys[i];
//			Object value = tmap.get(key);
//			if (value == null) {
//				map.put(key, null);
//			}
//			if (!value.getClass().isArray()) {
//				map.put(key, value);
//			} else {
//				String[] arr = (String[]) value;
//				if (arr.length == 1) {
//					map.put(key, arr[0]);
//				} else {
//					StringBuffer sb = new StringBuffer();
//					for (int k = 0; k < arr.length; k++) {
//						if (k != 0) {
//							sb.append(",");
//						}
//						sb.append(arr[k]);
//					}
//					map.put(key, sb.toString());
//				}
//			}
//		}
//
//		if (otherFlag) {
//			Cookies cookie = new Cookies(request);
//			Cookies.CookieObject[] ks = cookie.getArray();
//			for (int i = 0; i < ks.length; i++) {
//				map.put("Cookie." + ks[i].name, ks[i].value);
//			}
//
//			map.put("Header.UserAgent", request.getHeader("User-Agent"));
//			map.put("Header.Host", request.getHeader("Host"));
//			map.put("Header.Protocol", request.getProtocol());
//		}
//		return map;
//	}
//
//	public static Mapx getMapFromQueryString(String url) {
//		Mapx map = new Mapx();
//		int index = url.indexOf('?');
//		if (index < 0) {
//			return map;
//		}
//		String str = url.substring(url.indexOf('?') + 1);
//		int index2 = str.lastIndexOf('#');
//		if (index2 != -1) {
//			str = str.substring(0, index2);
//		}
//		String[] arr = str.split("\\&");
//		for (int i = 0; i < arr.length; i++) {
//			String[] arr2 = arr[i].split("\\=");
//			if (arr2.length == 2)
//				map.put(arr2[0], arr2[1]);
//			else {
//				map.put(arr2[0], "");
//			}
//		}
//		return map;
//	}
//
//	public static String getQueryStringFromMap(Mapx map) {
//		return getQueryStringFromMap(map, false);
//	}
//
//	public static String getQueryStringFromMap(Mapx map, boolean encodeFlag) {
//		StringBuffer sb = new StringBuffer();
//		sb.append("?");
//		Object[] ks = map.keyArray();
//		Object[] vs = map.valueArray();
//		int i = 0;
//		for (int j = 0; i < map.size(); i++) {
//			if (vs[i] == null) {
//				continue;
//			}
//			if (j != 0) {
//				sb.append("&");
//			}
//			sb.append(ks[i] + "=");
//			if (encodeFlag)
//				sb.append(XString.urlEncode(vs[i].toString()));
//			else {
//				sb.append(vs[i].toString());
//			}
//			j = 1;
//		}
//		return sb.toString();
//	}
//
//	public static String getURLContent(String url) throws Exception {
//		return getURLContent(url, null);
//	}
//
//	public static String getURLContent(String url, String encoding)
//			throws Exception {
//		StringBuffer sb = new StringBuffer();
//		InputStreamReader isr = null;
//		if (XString.isNotEmpty(encoding))
//			isr = new InputStreamReader(new URL(url).openStream(), encoding);
//		else {
//			isr = new InputStreamReader(new URL(url).openStream());
//		}
//		BufferedReader br = new BufferedReader(isr);
//		String s = null;
//		while ((s = br.readLine()) != null) {
//			sb.append(s);
//			sb.append("\n");
//		}
//		br.close();
//		return sb.toString();
//	}
//
//	public static String getCookieValue(HttpServletRequest request,
//			String cookieName) {
//		return getCookieValue(request, cookieName, "");
//	}
//
//	public static String getCookieValue(HttpServletRequest request,
//			String cookieName, String defaultValue) {
//		Cookie[] cookies = request.getCookies();
//		for (int i = 0; (cookies != null) && (i < cookies.length); i++) {
//			Cookie cookie = cookies[i];
//			if (!cookieName.equalsIgnoreCase(cookie.getName()))
//				continue;
//			try {
//				return URLDecoder.decode(cookie.getValue(), "UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//				return defaultValue;
//			}
//		}
//		return defaultValue;
//	}
//
//	public static void setCookieValue(HttpServletRequest request,
//			HttpServletResponse response, String cookieName, String cValue) {
//		setCookieValue(request, response, cookieName, 2678400, cValue);
//	}
//
//	public static void setCookieValue(HttpServletRequest request,
//			HttpServletResponse response, String cookieName, int maxAge,
//			String cValue) {
//		Cookie[] cookies = request.getCookies();
//		boolean cookieexistflag = false;
//		String contextPath = Config.getContextPath();
//		contextPath = contextPath.substring(0, contextPath.length() - 1);
//		try {
//			cValue = URLEncoder.encode(cValue, "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		for (int i = 0; (cookies != null) && (i < cookies.length); i++) {
//			Cookie cookie = cookies[i];
//			if (cookieName.equalsIgnoreCase(cookie.getName())) {
//				cookieexistflag = true;
//				cookie.setValue(cValue);
//				cookie.setPath(contextPath);
//				cookie.setMaxAge(maxAge);
//				response.addCookie(cookie);
//			}
//		}
//		if (!cookieexistflag) {
//			Cookie cookie = new Cookie(cookieName, cValue);
//			cookie.setPath(contextPath);
//			cookie.setMaxAge(maxAge);
//			response.addCookie(cookie);
//		}
//	}
//
//	public static String getUrlExtension(String url) {
//		if (XString.isEmpty(url)) {
//			return "";
//		}
//		int index = url.indexOf('?');
//		if (index > 0) {
//			url = url.substring(0, index);
//		}
//		int i1 = url.lastIndexOf('/');
//		int i2 = url.lastIndexOf('.');
//		if (i1 >= i2) {
//			return "";
//		}
//		return url.substring(i2).toLowerCase();
//	}
//
//	public static String getFileName(String url) {
//		int index1 = url.indexOf('?');
//		int index2 = url.indexOf('#');
//		int index3 = url.lastIndexOf('/');
//		if (index2 > 0) {
//			url = url.substring(0, index2);
//		}
//		if (index1 > 0) {
//			url = url.substring(0, index1);
//		}
//		if (index3 < 8) {
//			return "";
//		}
//		return url.substring(index3 + 1);
//	}
//
//	public static String getHost(String url) {
//		if ((!url.startsWith("http://")) && (!url.startsWith("https://"))) {
//			LogUtil.error("错误的URL：" + url);
//			return null;
//		}
//		int index = url.indexOf("//") + 2;
//		int index2 = url.indexOf("/", index);
//		if (index2 <= 0) {
//			index2 = url.length();
//		}
//		return url.substring(index, index2);
//	}
}
