package com.xdarkness.framework.jaf;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xdarkness.framework.Config;
import com.xdarkness.framework.SessionCheck;
import com.xdarkness.framework.User;
import com.xdarkness.framework.extend.ExtendManager;
import com.xdarkness.framework.orm.data.DataCollection;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.XString;

/**
 * 
 * @author Darkness Create on 2010-5-29 上午10:53:43
 * @version 1.0
 * @since JDF1.0
 */
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = -524995966806258384L;
	public static int ServletMajorVersion = Config.ServletMajorVersion;
	public static int ServletMinorVersion = Config.ServletMinorVersion;

	public MainServlet() {
	}

	public static String CONTEXT_PATH = "/";

	/**
	 * 执行Page类中的方法，并输出xml文件流
	 */
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		CONTEXT_PATH = request.getContextPath() + "/";

		try {

			response.setHeader("Pragma", "No-Cache");
			response.setHeader("Cache-Control", "No-Cache");
			response.setDateHeader("Expires", 0L);

			response.setContentType("text/xml");

			System.out.println(request.getParameter("componentTypeId"));
			if ((ServletMajorVersion == 2) && (ServletMinorVersion == 3))
				response.setContentType("text/xml;charset=utf-8");
			else {
				response.setCharacterEncoding("UTF-8");
			}
			request.setCharacterEncoding("UTF-8");

			String method = request.getParameter("_SKY_METHOD");
			String url = request.getParameter("_SKY_URL");
			if (("".equals(url)) || ("/".equals(url))) {
				url = "/Index.jsp";
			}

			// 测试网站，禁用删除功能
			if (("www.sky.com".equalsIgnoreCase(request.getServerName()))
					&& ("/demo".equalsIgnoreCase(request.getContextPath()))
					&& (!"admin".equalsIgnoreCase(User.getUserName()))
					&& (getServletConfig().getInitParameter(method) != null)) {
				LogUtil
						.getLogger()
						.warn(
								"method:"
										+ method
										+ ",操作："
										+ getServletConfig().getInitParameter(
												method)
										+ "此操作被拒绝!<br>系统提示：为保证泽元软件Demo站的稳定运行，Demo站中部分删除功能已被屏蔽.");
				DataCollection dcResponse = new DataCollection();
				dcResponse.put("_SKY_STATUS", "0");
				dcResponse
						.put(
								"_SKY_MESSAGE",
								"此操作被拒绝!<br>系统提示：为保证泽元软件Demo站的稳定运行，Demo站中部分删除功能已被屏蔽.如需要可下载安装程序到本地来试用.<br>下载地址：<a href='http://www.sky.com/download/program/index.shtml' target='_blank'>下载ZCMS</a>");
				response.getWriter().write(dcResponse.toXML());
				return;
			}

			Current.init(request, response, method);
			if (XString.isEmpty(method)) {
				LogUtil.warn("错误的Server.sendRequest()调用，URL=" + url
						+ "，Referer=" + request.getHeader("referer"));
				return;
			}

			/**
			 * 未登陆，转到登陆页面
			 */
			String className = method.substring(0, method.lastIndexOf("."));
			Class<?> c = Class.forName(className);

			String LoginClass = Config.getValue("App.LoginClass");

			if ((!Ajax.class.isAssignableFrom(c))
					&& (!className.equals("com.xdarkness.framework.Framework"))
					&& (!className.equals(LoginClass)) && (!User.isLogin())) {
				DataCollection dcResponse = new DataCollection();
				dcResponse.put("_SKY_SCRIPT", "window.top.location='"
						+ Config.getContextPath() + Config.getLoginPage()
						+ "';");
				response.getWriter().write(dcResponse.toXML());
				return;
			}

			if ((!className.equals(LoginClass)) && (!SessionCheck.check(c))) {
				DataCollection dcResponse = new DataCollection();
				dcResponse.put("_SKY_MESSAGE", "不允许越权访问!");
				response.getWriter().write(dcResponse.toXML());
				return;
			}

			if (ExtendManager.hasAction("BeforePageMethodInvoke")) {
				ExtendManager.executeAll("BeforePageMethodInvoke",
						new Object[] { method });
			}

			Current.invokeMethod(method);

			if (ExtendManager.hasAction("AfterPageMethodInvoke")) {
				ExtendManager.executeAll("AfterPageMethodInvoke",
						new Object[] { method });
			}

			response.getWriter().write(Current.getResponse().toXML());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
