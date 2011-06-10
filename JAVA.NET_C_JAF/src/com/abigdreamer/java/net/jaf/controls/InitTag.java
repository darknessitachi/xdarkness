package com.abigdreamer.java.net.jaf.controls;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.abigdreamer.java.net.jaf.Constant;
import com.abigdreamer.java.net.jaf.Current;
import com.abigdreamer.java.net.jaf.ServletUtil;
import com.abigdreamer.java.net.util.Mapx;
import com.abigdreamer.java.net.util.XString;

public class InitTag extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	private String method;
	private Mapx map;

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int doStartTag() throws JspException {
		try {
			HttpServletRequest request = (HttpServletRequest) this.pageContext
					.getRequest();
			if ((this.method == null) || (this.method.equals(""))) {
				this.map = ServletUtil.getParameterMap(request);
			} else {
				int index = this.method.lastIndexOf('.');
				String className = this.method.substring(0, index);
				this.method = this.method.substring(index + 1);
				Class<?> c = Class.forName(className);
				Method m = c.getMethod(this.method, new Class[] { Mapx.class });
				Mapx params = ServletUtil.getParameterMap(request);
				Object o = m.invoke(c.newInstance(), new Object[] { params });
				if (o == null) { 
					o = new Mapx();
				} 
				if (!(o instanceof Mapx)) {
					throw new RuntimeException("调用sky:init指定的method时发现返回类型不是Mapx");
				}
				this.map = ((Mapx) o);
				Current.setVariable("InitParams", this.map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 6;
	}

	public int doAfterBody() throws JspException {
		String content = getBodyContent().getString();
		try {
			content = HtmlUtil.replacePlaceHolder(content, this.map, true);

			Matcher matcher = Constant.PatternSpeicalField.matcher(content);
			StringBuffer sb = new StringBuffer();
			int lastEndIndex = 0;
			while (matcher.find(lastEndIndex)) {
				sb.append(content.substring(lastEndIndex, matcher.start()));
				Object v = this.map.get(matcher.group(1));
				if ((v != null) && (!v.equals(""))) {
					if (matcher.group().indexOf('#') > 0)
						sb.append(XString.javaEncode(v.toString()));
					else
						sb.append(v.toString());
				} else {
					sb
							.append(content.substring(matcher.start(), matcher
									.end()));
				}
				lastEndIndex = matcher.end();
			}
			sb.append(content.substring(lastEndIndex));
			content = sb.toString();
			getPreviousOut().print(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 6;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException {
		String className = "com.xdarkness.platform.page.BranchPage";
		String method = "initDialog";
		Class<?> c = Class.forName(className);
		Method m = c.getMethod(method, new Class[] { Mapx.class });
		Mapx params = new Mapx();
		Object o = m.invoke(c.newInstance(), new Object[] { params });
	}
}
