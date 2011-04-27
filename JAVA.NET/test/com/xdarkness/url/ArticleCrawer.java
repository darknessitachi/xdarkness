package com.xdarkness.url;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.abigdreamer.java.net.io.FileUtil;
import com.abigdreamer.java.net.util.LogUtil;

public class ArticleCrawer {

	String[] urlList;
	int currentIndex = 0;
	boolean finished = false;
	static String host = "http://ssbb.net.ru/";
	long start = 0;
	java.util.List<ArticleDownloader> threads = new ArrayList<ArticleDownloader>();
	int count = 15;

	public void getArticles(String[] urls) {
		this.urlList = urls;
		LogUtil.info("==========开始抓取文章=============");

		for (int i = 0; i < count; i++) {
			ArticleDownloader thread = new ArticleDownloader();
			threads.add(thread);
		}

		for (ArticleDownloader articleDownloader : threads) {
			articleDownloader.start();
		}
	}

	public void checkFinish() {
		boolean jobFinished = true;
		for (ArticleDownloader downloadThread : threads) {
			jobFinished &= downloadThread.jobFinished;
		}
		if (jobFinished) {
			LogUtil.info("==========文章抓取完毕，总文章数：" + urlList.length + "，共耗时："
					+ (new Date().getTime() - start));
			System.exit(0);
		}
	}

	public synchronized String getNextUrl() {
		String url = "";

		checkFinish();

		if (start == 0) {
			start = new Date().getTime();
		}
		if (!finished) {
			url = urlList[currentIndex++];
			if (currentIndex == urlList.length) {
				finished = true;
			}
		}

		return url.trim();
	}
	
	static int downloadArticleCount = 0;// 已下载文件个数
	
	synchronized int getDownloadArticleCount(){
		return downloadArticleCount++;
	}

	class ArticleDownloader extends Thread {
		boolean busy;
		boolean jobFinished;

		private HttpClient client;
		String loginUrl = "http://ssbb.net.ru/logging.php?action=login&loginsubmit=yes&inajax=1&username=darkness&password=depravedAngel";

		public PostMethod login() {
			System.out.println("login success");
			client = new HttpClient();
			client.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "gbk");
			PostMethod pMethod = new PostMethod(loginUrl);
			NameValuePair[] postData = { new NameValuePair("username", ""),
					new NameValuePair("userpwd", "") };
			pMethod.setRequestBody(postData);
			try {
				client.executeMethod(pMethod);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return pMethod;
		}

		public String getContent(String url) {
			currentCount = 1;
			return getContent(url, "正在提取url：" + url);
		}

		private int retryCount = 5;
		private int currentCount = 1;

		public String getContent(String url, String msg) {
			LogUtil.info(msg);
			// 解决获取网页信息的乱码问题
			client.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "gbk");// 设置编码格式
			GetMethod gMethod = new GetMethod(url);
			try {
				client.executeMethod(gMethod);
				return gMethod.getResponseBodyAsString();
			} catch (Exception e) {
				if (currentCount++ > retryCount) {
					LogUtil.error(e.getMessage());
				} else {
					getContent(url, "重新抓取url：" + url);
				}
			}
			return "";
		}

		@Override
		public void run() {

			while (true) {
				checkFinish();

				if (this.client == null) {
					login();
				}

				if (busy) {
					return;
				}
				busy = true;
				String url = getNextUrl();
				if ("".equals(url)) {
					jobFinished = true;
					return;
				}
				LogUtil.info("已完成"
						+ ((currentIndex + 0.0d) / urlList.length * 100)
						+ "%，正在提取总" + urlList.length + "中的第" + currentIndex
						+ "个文章......");
				int articleCount = getDownloadArticleCount();
				FileUtil.writeText("D:\\ssbb\\yz\\article\\" + (articleCount/512) + "\\" + getArticleId(url) + ".txt",
						getContent(host + url));
				busy = false;
			}

		}

	}

	java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("tid=(\\d*)");
	public String getArticleId(String url){
		
		Matcher match = pattern.matcher(url);
		if(match.find()){
			return match.group(1);
		}
		return "";
	}
	public static void main(String[] args) {

		ArticleCrawer httpClientTest = new ArticleCrawer();
		String[] urls = FileUtil.readText("D:\\ssbb\\yz\\urls.txt").replace(
				"[", "").replace("]", "").split(",");
		httpClientTest.getArticles(urls);

	}
}
