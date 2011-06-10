package com.abigdreamer.java.net.jaf;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abigdreamer.java.net.orm.data.DataCollection;
import com.abigdreamer.java.net.util.LogUtil;
import com.abigdreamer.java.net.util.Mapx;
import com.abigdreamer.java.net.util.XString;

/**
 * 
 * @author Darkness Create on 2010-5-29 上午10:53:55
 * @version 1.0
 * @since JDF1.0
 */
public class Current {
	private static ThreadLocal<Mapx<String, Object>> current = new ThreadLocal<Mapx<String, Object>>();
	private static final String PageKey = "_SKY_PAGE_KEY";

	public Current() {
	}

	public static void clear() {
		if (current.get() != null)
			current.set(null);
	}

	public static void setVariable(String key, Object value) {
		Mapx<String, Object> map = current.get();
		if (map == null) {
			map = new Mapx<String, Object>();
			current.set(map);
		}
		if ((value instanceof Mapx)) {
			Mapx vmap = (Mapx) value;
			Object[] ks = vmap.keyArray();
			Object[] vs = vmap.valueArray();
			for (int i = 0; i < ks.length; i++) {
				map.put(key + "." + ks[i], vs[i]);
			}
		}
		map.put(key, value);
	}

	public static Object getVariable(String key) {
		Mapx<String, Object> map = current.get();
		if (map == null) {
			return null;
		}
		return map.get(key);
	}

	public static int getInt(String key) {
		Mapx<String, Object> map = current.get();
		if (map == null) {
			return 0;
		}
		return map.getInt(key);
	}

	public static long getLong(String key) {
		Mapx<String, Object> map = current.get();
		if (map == null) {
			return 0L;
		}
		return map.getLong(key);
	}

	public static String getString(String key) {
		Mapx<String, Object> map = current.get();
		if (map == null) {
			return null;
		}
		return map.getString(key);
	}

	/**
	 * 生成当前method指定Page类实例，并将request数据提取放入Page实例的Request中
	 * 
	 * @param request
	 * @param response
	 * @param method
	 */
	public static void init(HttpServletRequest request,
			HttpServletResponse response, String method) {
		if (XString.isEmpty(method))
			return;
		if (current.get() != null) {
			current.remove();
		}

		try {
			int index = method.lastIndexOf('.');
			String className = method.substring(0, index);
			Class<?> c = Class.forName(className);
			Object o = c.newInstance();
			IPage page = (IPage) o;
			String data = request.getParameter("_SKY_DATA");
			JafRequest dc = new JafRequest();
			if (XString.isNotEmpty(data)) {
				data = XString.htmlDecode(data);
				dc.setURL(request.getParameter("_SKY_URL"));
				dc.putAll(ServletUtil.getParameterMap(request));
				dc.remove("_SKY_DATA");
				dc.remove("_SKY_URL");
				dc.remove("_SKY_METHOD");
				dc.parseXML(data);
			} else {
				// dc.setURL(request.getPathInfo());
				dc.setURL(request.getRequestURL() + "?"
						+ request.getQueryString());
				dc.putAll(ServletUtil.getParameterMap(request));
			}
			dc.setClientIP(request.getRemoteAddr());
			dc.setClassName(className);
			dc.setServerName(request.getServerName());
			dc.setPort(request.getServerPort());
			dc.setScheme(request.getScheme());

			Cookies cookie = new Cookies(request);
			page.setCookie(cookie);

			Cookies.CookieObject ks[] = page.getCookie().getArray();
			for (int i = 0; i < ks.length; i++)
				dc.put("Cookie." + ks[i].name, ks[i].value);

			dc.put("Header.UserAgent", request.getHeader("User-Agent"));
			dc.put("Header.Host", request.getHeader("Host"));
			dc.put("Header.Protocol", request.getProtocol());

			page.setRequest(dc);
			page.setResponse(new JafResponse());

			Mapx<String, Object> map = current.get();
			if (map == null) {
				map = new Mapx<String, Object>();
				current.set(map);
			}
			map.put(PageKey, page);

		} catch (Exception e) {
			DataCollection dcResponse = new DataCollection();
			dcResponse.put("_SKY_STATUS", 0);
			String msg = "系统发生内部错误，操作失败:" + method;
			LogUtil.getLogger().warn(msg);
			e.printStackTrace();
			dcResponse.put("_SKY_MESSAGE", msg);
			try {
				response.getWriter().write(dcResponse.toXML());
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}

	public static Object invokeMethod(String method, Object... args) {
		try {
			int index = method.lastIndexOf('.');
			String className = method.substring(0, index);
			method = method.substring(index + 1);

			IPage p = getPage();
			if (!(p.getClass().getName().equals(className))) {
				Class<?> c = Class.forName(className);
				IPage p2 = (IPage) c.newInstance();
				p2.setRequest(p.getRequest());
				p2.setResponse(p.getResponse());
				p2.setCookie(p.getCookie());
				p = p2;
			}
			// if (!SessionCheck.check(p.getClass())) {
			// throw new RuntimeException("非法访问，前台用户不能访问Page类!" + className
			// + "#" + method);
			// }
			/**
			 * 寻找签名相同的方法
			 */
			Method[] ms = p.getClass().getMethods();
			Method m = null;
			boolean flag = false;
			for (int i = 0; i < ms.length; ++i) {
				m = ms[i];
				if (m.getName().equals(method)) {// 方法名相同

					// 寻找参数签名相同的方法
					Class<?>[] cs = m.getParameterTypes();
					if ((args != null) && (args.length == cs.length)) {
						for (int j = 0; j < cs.length; ++j) {
							if (!(cs[j].isInstance(args[j]))) {
								break;
							}
						}
						flag = true;
						break;
					}

					if ((args == null) && (((cs == null) || (cs.length == 0)))) {
						flag = true;
						break;
					}
				}
			}
			if (!(flag)) {
				throw new RuntimeException("没有找到合适的方法，请检查参数是否正确!" + className
						+ "#" + method);
			}

			// 非静态方法
			if (!(Modifier.isStatic(m.getModifiers()))) {
				return m.invoke(p, args);
			}

			// 静态方法，无需传入实例
			return m.invoke(null, args);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public static IPage getPage() {

		Mapx map = (Mapx) current.get();
		if (map == null) {
			return null;
		}
		return (IPage) map.get(PageKey);
	}

	public static JafRequest getRequest() {
		IPage p = getPage();
		if (p == null)
			return null;
		else
			return p.getRequest();
	}

	public static JafResponse getResponse() {
		IPage p = getPage();
		if (p == null)
			return null;
		return p.getResponse();
	}

}
