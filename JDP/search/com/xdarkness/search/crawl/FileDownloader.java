package com.xdarkness.search.crawl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.CircularRedirectException;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NTCredentials;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.IOUtil;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.ServletUtil;
import com.xdarkness.framework.util.XString;
import com.xdarkness.framework.util.ZipUtil;
import com.xdarkness.search.DocumentList;
import com.xdarkness.search.SearchUtil;
import com.xdarkness.search.WebDocument;

public class FileDownloader {
	private String allowExtension;
	private String denyExtension;
	private HttpClient client;
	private int threadCount = 10;

	protected int aliveThreadCount = 0;

	protected int busyThreadCount = 0;
	private boolean proxyFlag;
	private String proxyHost;
	private int proxyPort;
	private String proxyUserName;
	private String proxyPassword;
	private int timeout;
	private int currentLevel;
	protected int downloadCount;
	protected int levelDownloadCount;
	protected int levelAddedCount;
	private UrlExtracter extracter;
	public static final Pattern charsetPattern = Pattern
			.compile(
					"<meta\\s*?http\\-equiv.*?content-type.*?content\\s*?=.*?charset\\s*?=\\s*?([^\\\"\\'\\s]+?)(\\\"|\\'|\\s|>|(/>))",
					Pattern.CASE_INSENSITIVE);

	/**
	 * 配置HttpClient
	 * @return
	 */
	private HttpClient configHttpClient() {
		//if (this.client == null) {
			MultiThreadedHttpConnectionManager cm = new MultiThreadedHttpConnectionManager();
			HttpConnectionManagerParams hcmp = new HttpConnectionManagerParams();
			hcmp.setDefaultMaxConnectionsPerHost(this.threadCount);
			hcmp.setConnectionTimeout(this.timeout);
			hcmp.setSoTimeout(this.timeout);
			cm.setParams(hcmp);
			this.client = new HttpClient(cm);
		//}
		return this.client;
	}
		/**
	 * 配置下载线程数
	 * @param list
	 */
	private void configFileDownloadThread(DocumentList list) {
		for (int i = 0; i < this.threadCount; ++i) {
			FileDownloadThread fdt = new FileDownloadThread();
			fdt.setDownloader(this);
			fdt.setList(list);
			fdt.setThreadIndex(i);
			fdt.setHttpClient(this.client);
			fdt.start();
		}
		for (int i = 0; i < this.threadCount; i++) {
				FileDownloadThread fdt = new FileDownloadThread();
				fdt.setDownloader(this);
				fdt.setList(list);
				fdt.setThreadIndex(i);
				fdt.setHttpClient(this.client);
				fdt.start();
			}
	}
	private void configProxy() {
		if (this.proxyFlag) {
//			NTCredentials creds = new NTCredentials(this.proxyUserName, this.proxyPassword, this.proxyHost, "*");
//			this.client.getState().setProxyCredentials(AuthScope.ANY, creds);
			this.client.getHostConfiguration().setProxy(this.proxyHost, this.proxyPort);
		}

if (this.proxyFlag) {// NEW
				NTCredentials creds = new NTCredentials(this.proxyUserName,
						this.proxyPassword, this.proxyHost, "*");
				this.client.getState()
						.setProxyCredentials(AuthScope.ANY, creds);
				this.client.getHostConfiguration().setProxy(this.proxyHost,
						this.proxyPort);
			}
	}
/**
	 * 下载当前层级文档
	 * @param list
	 * @param level
	 */
	public void downloadList(DocumentList list, int level) {
		this.currentLevel = level;
		this.downloadCount = 0;
		this.levelDownloadCount = 0;
		this.levelAddedCount = 0;
		LogUtil.getLogger().info(
				"===Level Download Start====\t Total " + list.size() + " URL");
		try {
			configHttpClient();

			configProxy();
			list.moveFirst();
			this.aliveThreadCount = this.threadCount;
			this.extracter = new UrlExtracter();
			//this.extracter.init(list, level, this);
this.extracter.init(list.getCrawler().getConfig(), level, this.allowExtension, this.denyExtension);

	configFileDownloadThread(list);
		
			try {
				while (this.aliveThreadCount != 0) {
					LogUtil.getLogger().debug("当前存活线程数："+this.aliveThreadCount);
					Thread.sleep(1000L);
				}
				checkEmpty(list);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int downloadOne(WebDocument doc) {
		return downloadOne(null, doc, false, -1);
	}

	public static int downloadOne(HttpClient httpClient, WebDocument doc) {
		return downloadOne(httpClient, doc, false, -1);
	}

	public static int downloadOne(HttpClient httpClient, WebDocument doc,
			int threadIndex) {
		return downloadOne(httpClient, doc, false, threadIndex);
	}
	private static HttpClient getHttpClient() {
		SimpleHttpConnectionManager cm = new SimpleHttpConnectionManager();
		HttpConnectionManagerParams hcmp = new HttpConnectionManagerParams();
		hcmp.setDefaultMaxConnectionsPerHost(1);
		hcmp.setConnectionTimeout(30000);
		hcmp.setSoTimeout(30000);
		cm.setParams(hcmp);
		return new HttpClient(cm);
	}
	private static void dealCircularRedirect(HttpClient httpClient, HttpMethodBase method) {
//		Header locationHeader = method.getResponseHeader("location");
		//httpClient.getParams().setParameter("http.socket.timeout", new Integer(1000));
//		Header hostHeader = method.getRequestHeader("host");
//		method = new GetMethod("http://" + hostHeader.getValue() + locationHeader.getValue());
//		method.setFollowRedirects(false);
	}
/**
	 * 下载文档
	 */
	public static int downloadOne(HttpClient httpClient, WebDocument doc,
			boolean second, int threadIndex) {
			if (httpClient == null) {
			httpClient = getHttpClient();
		}
		String url = doc.getUrl();		
		String sql = "SELECT COUNT(URL) FROM Url WHERE url=?";
		int count = new QueryBuilder(sql, url).executeInt();

		//https://99.saobi.org.ru/viewthread.php?tid=85021&extra=page%3D1
        if(url.indexOf("viewthread.php?tid=") != -1){// 是文章
            if(url.indexOf("page%3D1") == -1 && url.indexOf("page=1") == -1) {// 非第一页，为回复，不下载
                return -200;
            }
            if(count > 0) {     
                return 800+count;// 已经下载过，不再重复下载
            }
        }
        
		String ext = ServletUtil.getUrlExtension(url);
		if (".gif.jpg.jpeg.bmp.png".indexOf(ext) >= 0) {// 是图片
			if(count > 0) {		
				return 800+count;// 已经下载过，不再重复下载
			}
		}
		
		try {
			if(count < 1) {
				sql = "INSERT INTO Url(url) VALUES(?)";
				new QueryBuilder(sql, url).executeNoQuery();
			}
		} catch (Exception ex) {
			// ingore
		}
		int statusCode = 200;
		HttpMethodBase gm = null;
		String charset = null;
		try {
			String host = ServletUtil.getHost(url);
			if (doc.getForm() != null) {
				Mapx form = doc.getForm();
				PostMethod pm = new PostMethod(url);
				Object[] ks = form.keyArray();
				Object[] vs = form.valueArray();
				for (int i = 0; i < form.size(); i++) {
					pm.addParameter(ks[i].toString(), vs[i].toString());
				}
				gm = pm;
			} else {
				gm = new GetMethod(url);
			}
dealCircularRedirect(httpClient, gm);
configRequestHeader(gm, host);
		
			httpClient.executeMethod(gm);

			statusCode = gm.getStatusCode();
			InputStream is = gm.getResponseBodyAsStream();
			byte[] body = IOUtil.getBytesFromStream(is);
//byte[] body = ByteUtil.getBytesFromStream(is);

			if (is != null) {
				is.close();
			}
			String contentType = null;
			if (gm.getResponseHeader("Content-Type") != null)
				contentType = gm.getResponseHeader("Content-Type").getValue()
						.toLowerCase();
			else {
				contentType = "text/html";
			}
			if (gm.getResponseHeader("Content-Encoding") != null) {
				String contentEncoding = gm.getResponseHeader(
						"Content-Encoding").getValue().toLowerCase();
				if (contentEncoding.equals("gzip"))
					body = ZipUtil.ungzip(body);
				else if (contentEncoding.equals("zip")) {
					body = ZipUtil.unzip(body);
				}
			}
			Header h = gm.getResponseHeader("Last-Modified");
			if (h == null) {
				h = gm.getResponseHeader("Date");
			}
			Date lastModified = null;
			if (h != null) {
				lastModified = SearchUtil.parseLastModifedDate(h.getValue());
				if (lastModified == null)
					LogUtil.getLogger().info(
							"Error on Get lastModified：" + h.getValue() + "\t"
									+ url + "\t RefURL：" + doc.getRefUrl());
			} else {
				lastModified = new Date();
			}
			doc.setLastDownloadTime(System.currentTimeMillis());
			doc.setContent(body);
			doc.setLastmodifiedDate(lastModified);
			doc.setErrorMessage(null);
			if ((contentType.indexOf("text") < 0)
					&& (contentType.indexOf("html") < 0)) {
				doc.setTextContent(false);
			} else {
				doc.setTextContent(true);
				charset = gm.getResponseCharSet();
				try {
					Charset.forName(charset);
				} catch (Exception e) {
					charset = "utf-8";
				}
				if (((charset = getCharSet(body, charset)) == null)
						&& ((charset = getCharSet(body, "utf-8")) == null)
						&& ((charset = getCharSet(body, "unicode")) == null)) {
					charset = getCharSet(body, "ISO-8859-1");
				}

				try {
					Charset.forName(charset);
				} catch (Exception e) {
					charset = "utf-8";
				}
				if ((charset.equalsIgnoreCase("utf-8"))
						&& (!XString.isUTF8(body))) {
					charset = "gbk";
				}
				if (charset.equalsIgnoreCase("gb2312")) {
					charset = "gbk";
				}
				doc.setCharset(charset);
			}
		} catch (UnsupportedEncodingException e) {
			doc.setErrorMessage("无法处理的文件编码:" + charset);
			LogUtil.getLogger().info(
					"Error Charset,URL not been Downloaded：" + charset + "\t"
							+ url + "\t RefURL：" + doc.getRefUrl());
		} catch (IllegalArgumentException e) {
			if (second) {
				e.printStackTrace();
				doc.setErrorMessage("URL语法不正确");
				LogUtil.getLogger().info(
						"URL Syntax Error,URL not been Downloaded：\t" + url
								+ "\t RefURL：" + doc.getRefUrl());
			} else {
				statusCode = downloadOne(httpClient, doc, true, threadIndex);
			}
		} catch (CircularRedirectException e) {
			doc.setErrorMessage("URL循环重定向");
			LogUtil.getLogger().info(
					gm.getStatusCode()
							+ ":Circular Redirect,URL not been Downloaded：\t"
							+ url + "\t RefURL：" + doc.getRefUrl());
		} catch (HttpException e) {
			if (second) {
				e.printStackTrace();
				doc.setErrorMessage("HttpException,第二次重试失败.");
				LogUtil.getLogger().info(
						"HttpException,Failed With 2 Times Retry：\t" + url
								+ "\t RefURL：" + doc.getRefUrl());
			} else {
				statusCode = downloadOne(httpClient, doc, true, threadIndex);
			}
		} catch (IOException e) {
			if (second) {
				doc.setErrorMessage("IOException,两次重试皆失败");
				LogUtil.getLogger().info(
						"IOException,Failed With 2 Times Retry：\t" + url
								+ "\t RefURL：" + doc.getRefUrl());
			} else {
				statusCode = downloadOne(httpClient, doc, true, threadIndex);
			}
		} finally {
			if (gm != null) {
				gm.releaseConnection();
			}
		}
		return statusCode;
	}
	/**
	 * 配置Http方法头参数
	 */
	private static void configRequestHeader(HttpMethodBase gm, String host) {
		gm.setRequestHeader("Host", host);
		gm.setRequestHeader(
						"User-Agent",
						"Mozilla/5.0 (Windows; U; Windows NT 5.2; zh-CN; rv:1.8.1.11) Gecko/20071127 Firefox/3.0.1 QQDownload");
		gm.setRequestHeader("Accept",
						"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	gm.setRequestHeader("Host", host);
			gm
					.setRequestHeader(
							"User-Agent",
							"Mozilla/5.0 (Windows; U; Windows NT 5.2; zh-CN; rv:1.8.1.11) Gecko/20071127 Firefox/3.0.1 QQDownload");
			gm
					.setRequestHeader("Accept",
							"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	}
	public static String getCharSet(byte[] body, String charset) {
		String tmp = null;
		try {
			tmp = new String(body, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Matcher m = charsetPattern.matcher(tmp);
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}


	public void checkEmpty(DocumentList list) {
		LogUtil.getLogger()
				.info(
						"===Check Content Empty URL====\tTotal " + list.size()
								+ " URL");
		int t = this.threadCount / 3;
		if (t == 0) {
			t = 1;
		}
		this.aliveThreadCount = t;
		for (int i = 0; i < t; i++) {
			FileDownloadThread fdt = new FileDownloadThread();
			fdt.setDownloader(this);
			fdt.setList(list);
			fdt.setThreadIndex(i);
			fdt.setHttpClient(this.client);
			fdt.setCheckEmpty(true);
			fdt.start();
		}
		try {
			while (this.aliveThreadCount != 0) {
				LogUtil.getLogger().debug("当前存活线程数："+this.aliveThreadCount);
				Thread.sleep(1000L);
			}
			return;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public boolean isMatchedUrl(String url) {
		return this.extracter.isMatchedUrl(url);
	}


	public int getDownloadCount() {
		return this.downloadCount;
	}

	public void setDownloadCount(int downloadCount) {
		this.downloadCount = downloadCount;
	}
	
	public void addDownloadCount() {
		this.downloadCount++;
	}
	public String getDenyExtension() {
		return this.denyExtension;
	}

	public void setDenyExtension(String denyExtension) {
		this.denyExtension = denyExtension;
	}

	public String getAllowExtension() {
		return this.allowExtension;
	}

	public void setAllowExtension(String allowExtension) {
		this.allowExtension = allowExtension;
	}

	public int getThreadCount() {
		return this.threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public String getProxyHost() {
		return this.proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public String getProxyPassword() {
		return this.proxyPassword;
	}

	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}

	public int getProxyPort() {
		return this.proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyUserName() {
		return this.proxyUserName;
	}

	public void setProxyUserName(String proxyUserName) {
		this.proxyUserName = proxyUserName;
	}

	public int getCurrentLevel() {
		return this.currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	public boolean isProxyFlag() {
		return this.proxyFlag;
	}

	public void setProxyFlag(boolean proxyFlag) {
		this.proxyFlag = proxyFlag;
	}
	public int getTimeout() {
		return this.timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}

