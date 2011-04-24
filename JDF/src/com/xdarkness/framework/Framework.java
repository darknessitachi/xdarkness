package com.xdarkness.framework;

import java.io.InputStream;
import java.lang.reflect.Method;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import com.xdarkness.framework.jaf.JafRequest;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataCollection;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.util.IOUtil;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;

public class Framework extends Page {
	public void getCodeData() {
		String CodeType = this.request.getString("_SKY_CODETYPE");
		String className = Config.getValue("App.CodeSource");
		String methodName = className.substring(className.lastIndexOf(".") + 1);
		className = className.substring(0, className.lastIndexOf("."));
		try {
			Class c = Class.forName(className);
			Method m = c.getMethod(methodName, new Class[] { String.class,
					DataCollection.class });
			Object d = m.invoke(null, new Object[] { CodeType, this.request });
			if (d != null)
				this.response.put("CodeData", (DataTable) d);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Mapx callRemoteMethod(String url, String method,
			JafRequest request) {
		SimpleHttpConnectionManager cm = new SimpleHttpConnectionManager();
		HttpConnectionManagerParams hcmp = new HttpConnectionManagerParams();
		hcmp.setDefaultMaxConnectionsPerHost(1);
		hcmp.setConnectionTimeout(3000);
		hcmp.setSoTimeout(3000);
		cm.setParams(hcmp);
		HttpClient httpClient = new HttpClient(cm);
		if (!url.startsWith("/")) {
			url = url + "/";
		}
		for (int i = 0; i < 3;) {
			try {
				PostMethod pm = new PostMethod(url + "MainServlet.jsp");
				pm.addParameter("_SKY_METHOD", method);
				pm.addParameter("_SKY_DATA", request.toXML());
				pm.addParameter("_SKY_URL", url);
				httpClient.executeMethod(pm);
				if (pm.getStatusCode() != 200) {
					LogUtil.info("Framework.callRemoteMethod()状态代码异常:"
							+ pm.getStatusCode() + ";URL=" + url);
				} else {
					InputStream is = pm.getResponseBodyAsStream();
					byte[] body = IOUtil.getBytesFromStream(is);
					String result = new String(body, "UTF-8");
					DataCollection dc = new DataCollection();
					dc.parseXML(result);
					return dc;
				}
			} catch (Exception e) {
				LogUtil.info("Framework.callRemoteMethod()发生异常:"
						+ e.getMessage() + ";URL=" + url);

				i++;
			}

		}

		return null;
	}
}

/*
 * com.xdarkness.framework.Framework JD-Core Version: 0.6.0
 */