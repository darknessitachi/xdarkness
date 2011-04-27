package com.abigdreamer.java.net.jaf.controls;

import java.lang.reflect.Method;

import com.abigdreamer.java.net.jaf.Ajax;
import com.abigdreamer.java.net.util.XString;

public class DataListPage extends Ajax {
	public void doWork() {
		try {
			DataListAction dla = new DataListAction();

			dla.setTagBody(XString.htmlDecode($V("_SKY_TAGBODY")));
			dla.setPage("true".equalsIgnoreCase($V("_SKY_PAGE")));
			String method = $V("_SKY_METHOD");
			dla.setMethod(method);

			dla.setID($V("_SKY_ID"));
			dla.setParams(this.request);
			dla.setPageSize(Integer.parseInt($V("_SKY_SIZE")));
			if (dla.isPage()) {
				dla.setPageIndex(0);
				if ((this.request.get("_SKY_PAGEINDEX") != null)
						&& (!this.request.get("_SKY_PAGEINDEX").equals(""))) {
					dla.setPageIndex(Integer.parseInt(this.request.get(
							"_SKY_PAGEINDEX").toString()));
				}
				if (dla.getPageIndex() < 0) {
					dla.setPageIndex(0);
				}
				if ((this.request.get("_SKY_PAGETOTAL") != null)
						&& (!this.request.get("_SKY_PAGETOTAL").equals(""))) {
					dla.setTotal(Integer.parseInt(this.request.get(
							"_SKY_PAGETOTAL").toString()));
					if (dla.getPageIndex() > Math.ceil(dla.getTotal() * 1.0D
							/ dla.getPageSize())) {
						dla.setPageIndex(new Double(Math.floor(dla.getTotal()
								* 1.0D / dla.getPageSize())).intValue());
					}
				}
			}
			int index = method.lastIndexOf('.');
			String className = method.substring(0, index);
			method = method.substring(index + 1);
			Class c = Class.forName(className);
			Method m = c
					.getMethod(method, new Class[] { DataListAction.class });
			m.invoke(null, new Object[] { dla });

			$S("HTML", dla.getHtml());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

/*
 * com.xdarkness.framework.controls.DataListPage JD-Core Version: 0.6.0
 */