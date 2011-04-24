package com.xdarkness.jaf.controls;


import java.lang.reflect.Method;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.xdarkness.common.util.Mapx;
import com.xdarkness.common.util.ServletUtil;
import com.xdarkness.common.util.StringUtil;

public class InitTag extends BodyTagSupport {

	public InitTag() {
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int doAfterBody() throws JspException {
		String content = getBodyContent().getString();
		try {
			HttpServletRequest request = (HttpServletRequest) pageContext
					.getRequest();
			Mapx map = null;
			if (method == null || method.equals("")) {
				map = ServletUtil.getParameterMap(request);
			} else {
				int index = method.lastIndexOf('.');
				String className = method.substring(0, index);
				method = method.substring(index + 1);
				Class c = Class.forName(className);
				Method m = c.getMethod(method,
						new Class[] { Mapx.class });
				Mapx params = ServletUtil.getParameterMap(request);
				Object o = m.invoke(null, new Object[] { params });
				if (o == null)
					o = new Mapx();
				if (!(o instanceof Mapx))
					throw new RuntimeException("调用z:init指定的method时发现返回类型不是Mapx");
				map = (Mapx) o;
			}
			content = HtmlUtil.replacePlaceHolder(content, map, true);
			Matcher matcher = Constant.PatternSpeicalField.matcher(content);
			StringBuffer sb = new StringBuffer();
			int lastEndIndex;
			for (lastEndIndex = 0; matcher.find(lastEndIndex); lastEndIndex = matcher
					.end()) {
				sb.append(content.substring(lastEndIndex, matcher.start()));
				Object v = map.get(matcher.group(1));
				if (v != null && !v.equals("")) {
					if (matcher.group().indexOf('#') > 0)
						sb.append(StringUtil.javaEncode(v.toString()));
					else
						sb.append(v.toString());
				} else {
					sb.append(content.substring(matcher.start(), matcher
									.end()));
				}
			}

			sb.append(content.substring(lastEndIndex));
			content = sb.toString();
			getPreviousOut().print(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 6;
	}

	private static final long serialVersionUID = 1L;
	private String method;
}
