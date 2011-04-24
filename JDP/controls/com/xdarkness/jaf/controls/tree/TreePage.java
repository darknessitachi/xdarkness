// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TreePage.java

package com.xdarkness.jaf.controls.tree;


import java.lang.reflect.Method;

import com.xdarkness.common.util.StringUtil;
import com.xdarkness.jaf.Page;
import com.xdarkness.jaf.controls.html.HtmlP;

//            TreeAction, HtmlP

public class TreePage extends Page {

	public TreePage() {
	}

	public void doWork() {
		try {
			TreeAction ta = new TreeAction();
			ta.setTagBody(StringUtil.htmlDecode($V("_SKY_TAGBODY")));
			String method = $V("_SKY_METHOD");
			ta.setMethod(method);
			if ("true".equals($V("_SKY_TREE_LAZY"))) {
				if (!"false".equals($V("_SKY_TREE_EXPAND")))
					ta.setExpand(true);
				ta.setLazy(true);
			}
			if ($V("ParentLevel") != null && !"".equals($V("ParentLevel"))) {
				ta.setParentLevel(Integer.parseInt($V("ParentLevel")));
				ta.setLazyLoad(true);
			}
			ta.setID($V("_SKY_ID"));
			ta.setParams(Request);
			String levelStr = $V("_SKY_TREE_LEVEL");
			String style = $V("_SKY_TREE_STYLE");
			int level = Integer.parseInt(levelStr);
			if (level <= 0)
				level = 999;
			ta.setLevel(level);
			ta.setStyle(style);
			HtmlP p = new HtmlP();
			p.parseHtml(ta.getTagBody());
			ta.setTemplate(p);
			int index = method.lastIndexOf('.');
			String className = method.substring(0, index);
			method = method.substring(index + 1);
			Class c = Class.forName(className);
			Method m = c
					.getMethod(
							method,
							new Class[] { com.xdarkness.jaf.controls.tree.TreeAction.class });
			m.invoke(null, new Object[] { ta });
			$S("HTML", ta.getHtml());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
