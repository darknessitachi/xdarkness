package com.xdarkness.framework.jaf;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xdarkness.framework.Config;

/**
 * 
 * @author Darkness Create on Jun 3, 2010 11:17:35 AM
 * @version 1.0
 * @since JDF1.0
 */
public class Cookies {
	public static class CookieObject {

		public String name;
		public String value;
		public String comment;
		public String domain;
		public int maxAge = -1;
		public String path;
		public boolean secure;
		public int version = 0;
		public boolean changed = false;
	}

	public ArrayList list = new ArrayList();
	public static final String CookieCharset = "UTF-8";

	public Cookies(ServletRequest request) {
		Cookie cookies[] = ((HttpServletRequest) request).getCookies();
		if (cookies == null)
			return;
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			CookieObject c = new CookieObject();
			c.name = cookie.getName();
			try {
				c.value = URLDecoder.decode(cookie.getValue(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			c.domain = cookie.getDomain();
			c.maxAge = cookie.getMaxAge();
			c.path = cookie.getPath();
			c.path = normalizePath(c.path);
			c.secure = cookie.getSecure();
			c.comment = cookie.getComment();
			c.version = cookie.getVersion();
			list.add(c);
		}

	}

	public void setCookie(String name, String value, String domain, int maxAge,
			String path, boolean secure, String comment) {
		path = normalizePath(path);
		for (int i = 0; i < list.size(); i++) {
			CookieObject c = (CookieObject) list.get(i);
			if (c.name.equals(name)
					&& (c.domain == null || c.domain.equals(domain))
					&& (c.path == null || c.path.equals(path))
					&& c.secure == secure && c.value != null
					&& !c.value.equals(value)) {
				c.changed = true;
				c.value = value;
				c.comment = comment;
				return;
			}
		}

		CookieObject c = new CookieObject();
		c.name = name;
		c.value = value;
		c.comment = comment;
		c.domain = domain;
		c.maxAge = maxAge;
		c.path = path;
		c.secure = secure;
		c.version = 0;
		c.changed = true;
		list.add(c);
	}

	public CookieObject[] getArray() {
		CookieObject cos[] = new CookieObject[list.size()];
		for (int i = 0; i < cos.length; i++)
			cos[i] = (CookieObject) list.get(i);

		return cos;
	}

	public void setCookie(String name, String value, int maxAge) {
		setCookie(name, value, null, maxAge, getDefaultPath(), false, null);
	}

	public void setCookie(String name, String value) {
		setCookie(name, value, null, -1141367296, getDefaultPath(), false, null);
	}

	public void setCookie(String name, String value, String path) {
		setCookie(name, value, path, -1141367296, getDefaultPath(), false, null);
	}

	public void setCookie(String name, String value, String path, int maxAge) {
		setCookie(name, value, path, maxAge, getDefaultPath(), false, null);
	}

	public String getCookie(String name) {
		return getCookie(name, null);
	}

	private static String getDefaultPath() {
		String path = Config.getContextPath();
		return normalizePath(path);
	}

	private static String normalizePath(String path) {
		if (path == null)
			path = "/";
		if (path.endsWith("/") && !path.equals("/"))
			path = path.substring(0, path.length() - 1);
		return path;
	}

	public String getCookie(String name, String path) {
		path = normalizePath(path);
		ArrayList arr = new ArrayList();
		for (int i = 0; i < this.list.size(); i++) {
			CookieObject c = (CookieObject) this.list.get(i);
			if (c.name.equals(name))
				if (path != null) {
					if (c.path.equals(path))
						arr.add(c.value);
				} else {
					arr.add(c.value);
				}
		}

		if (arr.size() == 0)
			return null;
		if (arr.size() == 1)
			return arr.get(0) != null ? String.valueOf(arr.get(0)) : null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.size(); i++) {
			if (i != 0)
				sb.append(",");
			if (arr.get(i) == null)
				sb.append("");
			else
				sb.append(arr.get(i));
		}

		return sb.toString();
	}

	public void writeToResponse(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			for (int j = 0; j < list.size(); j++) {
				CookieObject co = (CookieObject) list.get(j);
				if (!co.changed) {
					continue;
				}
				Cookie cs[] = request.getCookies();
				boolean flag = true;
				for (int i = 0; i < cs.length; i++) {
					String path = normalizePath(cs[i].getPath());
					if (!cs[i].getName().equals(co.name) || path != null
							&& !path.equals(co.path))
						continue;
					cs[i].setValue(URLEncoder.encode(co.value, "UTF-8"));
					cs[i].setMaxAge(co.maxAge);
					if (co.domain != null)
						cs[i].setDomain(co.domain);
					response.addCookie(cs[i]);
					flag = false;
					break;
				}

				if (flag) {
					Cookie cookie = new Cookie(co.name, URLEncoder.encode(
							co.value, "UTF-8"));
					cookie.setMaxAge(co.maxAge);
					cookie.setPath(co.path);
					if (co.domain != null)
						cookie.setDomain(co.domain);
					cookie.setSecure(co.secure);
					response.addCookie(cookie);
				}

			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
