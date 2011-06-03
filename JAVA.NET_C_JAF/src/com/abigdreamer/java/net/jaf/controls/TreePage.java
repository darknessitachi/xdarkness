package com.abigdreamer.java.net.jaf.controls;

import java.lang.reflect.Method;

import com.abigdreamer.java.net.jaf.Ajax;
import com.abigdreamer.java.net.jaf.html.element.HtmlP;
import com.abigdreamer.java.net.util.XString;

public class TreePage extends Ajax {
	public void doWork() {
		try {
			TreeAction ta = new TreeAction();

			ta.setTagBody(XString.htmlDecode($V("_SKY_TAGBODY")));
			String method = $V("_SKY_METHOD");
			ta.setMethod(method);

			if ("true".equals($V("_SKY_TREE_LAZY"))) {
				if (!"false".equals($V("_SKY_TREE_EXPAND"))) {
					ta.setExpand(true);
				}
				ta.setLazy(true);
			}

			if (($V("ParentLevel") != null) && (!"".equals($V("ParentLevel")))) {
				ta.setParentLevel(Integer.parseInt($V("ParentLevel")));
				ta.setLazyLoad(true);
			}

			ta.setID($V("_SKY_ID"));
			ta.setParams(this.request);

			String levelStr = $V("_SKY_TREE_LEVEL");
			String style = $V("_SKY_TREE_STYLE");

			int level = Integer.parseInt(levelStr);
			if (level <= 0) {
				level = 999;
			}
			ta.setLevel(level);
			ta.setStyle(style);

			HtmlP p = new HtmlP();
			p.parseHtml(ta.getTagBody());
			ta.setTemplate(p);

			int index = method.lastIndexOf('.');
			String className = method.substring(0, index);
			method = method.substring(index + 1);
			Class c = Class.forName(className);
			Method m = c.getMethod(method, new Class[] { TreeAction.class });
			m.invoke(null, new Object[] { ta });

			$S("HTML", ta.getHtml());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

/*
 * com.xdarkness.framework.controls.TreePage JD-Core Version: 0.6.0
 */