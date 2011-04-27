package com.abigdreamer.java.net;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abigdreamer.java.net.data.BlockingTransaction;
import com.abigdreamer.java.net.extend.ExtendManager;
import com.abigdreamer.java.net.jaf.Current;
import com.abigdreamer.java.net.util.Errorx;
import com.abigdreamer.java.net.util.LogUtil;
import com.abigdreamer.java.net.util.XString;

public class MainFilter implements Filter {
	private long uptime = 0L;
	private static String[] NoFilterPaths;

	public void init(FilterConfig config) throws ServletException {
		LogUtil.info("----" + Config.getAppCode() + "(" + Config.getAppName()
				+ "): MainFilter Initialized----");
		ServletContext sc = config.getServletContext();
		Config.configMap.put("System.ContainerInfo", sc.getServerInfo());
		Config.getJBossInfo();
		Config.ServletMajorVersion = sc.getMajorVersion();
		Config.ServletMinorVersion = sc.getMinorVersion();

		this.uptime = System.currentTimeMillis();
		Config.setValue("App.Uptime", this.uptime+"");

		String paths = Config.getValue("App.NoFilterPath");
		if (XString.isNotEmpty(paths)) {
			String[] arr = paths.split(",");
			for (int i = 0; i < arr.length; i++) {
				String path = arr[i];
				if (!path.startsWith("/")) {
					path = "/" + path;
				}
				if (!path.endsWith("/"))
					path = path + "/";
			}
		}
	}

	public boolean isNoFilterPath(String url) {
		if (NoFilterPaths == null) {
			return false;
		}
		url = url + "/";
		for (int i = 0; i < NoFilterPaths.length; i++) {
			if (url.indexOf(NoFilterPaths[i]) >= 0) {
				return true;
			}
		}
		return false;
	}

	public void doFilter(ServletRequest req, ServletResponse rep,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) rep;
		String url = request.getServletPath();
		if (isNoFilterPath(url)) {
			chain.doFilter(request, response);
			return;
		}

		request.setCharacterEncoding(Constant.GlobalCharset);
		if ((Config.ServletMajorVersion == 2)
				&& (Config.ServletMinorVersion == 3))
			response.setContentType("text/html;charset="
					+ Constant.GlobalCharset);
		else {
			response.setCharacterEncoding(Constant.GlobalCharset);
		}

		Current.clear();

		HttpSession session = request.getSession();
		User.UserData u = (User.UserData) session.getAttribute("_SKY_USER");
		if (u == null) {
			boolean flag = true;
			if (Config.isDebugMode()) {
				Cookie[] cs = request.getCookies();
				if (cs != null) {
					for (int i = 0; i < cs.length; i++) {
						if (cs[i].getName().equals("JSESSIONID")) {
							u = User.getCachedUser(cs[i].getValue());
							if (u != null) {
								flag = false;
								break;
							}
						}
					}
				}
			}
			if (flag) {
				u = new User.UserData();
			}
			u.setSessionID(session.getId());
		}
		User.setCurrent(u);

		String contextPath = request.getContextPath();
		if (!contextPath.endsWith("/")) {
			contextPath = contextPath + "/";
		}
		if (Config.isComplexDepolyMode()) {
			User.setValue("App.ContextPath", contextPath);
		}
		Config.setValue("App.ContextPath", contextPath);

		if ((!Config.isInstalled) && (url.indexOf("Install.jsp") < 0)
				&& (url.indexOf("MainServlet.jsp") < 0)) {
			RequestDispatcher rd = request.getRequestDispatcher("/Install.jsp");
			rd.forward(req, rep);
			return;
		}

		if ((Config.isInstalled)
				&& ((Config.isNeedCheckPatch) || (Config.isPatching))) {
			RequestDispatcher rd = request
					.getRequestDispatcher("/Patching.jsp");
			rd.forward(req, rep);
			return;
		}

		Errorx.init();

		if ((url != null) && (url.indexOf("/MainServlet.jsp") > 0)
				&& (!url.equals("/MainServlet.jsp"))) {
			RequestDispatcher rd = request
					.getRequestDispatcher("/MainServlet.jsp");
			rd.forward(req, rep);
			return;
		}
		SessionCheck.check(request, response);

		if (!Errorx.hasDealed()) {
			LogUtil.getLogger().warn("严重，发现未处理的错误！");
			Errorx.printString();
		}

		if (ExtendManager.hasAction("AfterMainFilter")) {
			ExtendManager.executeAll("AfterMainFilter", new Object[] { request,
					response, chain });
		}
		session.setAttribute("_SKY_USER", User.getCurrent());
		try {
			chain.doFilter(request, response);
		} finally {
			BlockingTransaction.clearTransactionBinding();
		}
		if (Current.getPage() != null)
			Current.getPage().getCookie().writeToResponse(request, response);
	}

	public void destroy() {
	}
}

/*
 * com.xdarkness.framework.MainFilter JD-Core Version: 0.6.0
 */