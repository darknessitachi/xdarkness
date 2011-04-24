package com.xdarkness.framework.extend;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import com.xdarkness.framework.jaf.Ajax;
import com.xdarkness.framework.jaf.Cookies;
import com.xdarkness.framework.jaf.Current;
import com.xdarkness.framework.jaf.JafRequest;

public abstract class JSPExtendAction extends Ajax implements IExtendAction {
	public void execute(Object[] args) {
		PageContext pageContext = (PageContext) args[0];
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		HttpServletResponse response = (HttpServletResponse) pageContext
				.getResponse();
		Current.init(request, response, getClass().getName() + ".execute");
		String html = execute(Current.getRequest(), Current.getPage().getCookie());
		try {
			pageContext.getOut().print(html);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract String execute(JafRequest paramRequestImpl,
			Cookies paramCookieImpl);
}
