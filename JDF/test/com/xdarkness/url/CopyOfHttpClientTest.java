package com.xdarkness.url;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
import com.xdarkness.framework.io.FileUtil;

public class CopyOfHttpClientTest {

	private static Set<String> urls = new HashSet<String>();

	// 匹配链接内容
	public static void matchUrl(String str) {
		String regEx = "<a.*?href=\"(.*?)\"";
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(str);
		while (mat.find()) {
			String url = mat.group(1);
			if (articleUrl.equals(url.replaceAll("tid=\\d{0,9}&amp;", "")
					.replaceAll("%3D\\d{0,9}", ""))) {
				urls.add(url);
			}
		}
	}
	
	public void getArticelUrl(String url){
		matchUrl(getContent(url));
	}

	public String getContent(String url) {
		System.out.println("正在提取url："+url);
		// 解决获取网页信息的乱码问题
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				"gbk");// 设置编码格式
		GetMethod gMethod = new GetMethod(url);
		try {
			client.executeMethod(gMethod);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return gMethod.getResponseBodyAsString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	private HttpClient client;

	public PostMethod login(String username, String password, String url) {
		client = new HttpClient();
		String loginUrl = url;
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,
				"gbk");
		PostMethod pMethod = new PostMethod(loginUrl);
		NameValuePair[] postData = { new NameValuePair("username", username),
				new NameValuePair("userpwd", password) };
		pMethod.setRequestBody(postData);
		try {
			client.executeMethod(pMethod);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pMethod;
	}

	// http://ssbb.net.ru/viewthread.php?tid=66309&extra=page%3D1
	static String articleUrl = "viewthread.php?extra=page";

	String[] urlList;
	int currentIndex = 0;
	boolean finished = false;
	static String host = "http://ssbb.net.ru/";
	public void getArticles() {
		System.out.println("==========开始抓取文章=============");
		urlList = urls.toArray(new String[]{});
		int count = 10;
		for (int i=0; i<count; i++) {
			DownloadThread thread = new DownloadThread();
			threads.add(thread);
			thread.start();
		}
		
	}
	long start = 0;
	
	java.util.List<DownloadThread> threads = new ArrayList<DownloadThread>();
	
	public void checkFinish(){
		boolean jobFinished = false;
		for (DownloadThread downloadThread : threads) {
			jobFinished &= downloadThread.jobFinished;
		}
		if (jobFinished) {
			System.out.println("==========文章抓取完毕，总文章数："+urlList.length+"，共耗时：" + (new Date().getTime()-start));
			System.exit(0);
		}
	}
	public synchronized String getNextUrl(){
		String url = "";
		
		checkFinish();
		
		if(start == 0) {
			start = new Date().getTime();
		}
		if(!finished) {
			url = urlList[currentIndex++];
			if(currentIndex == urlList.length) {
				finished = true;
			}
		}
		return url;
	}
	
	class DownloadThread extends Thread {

		boolean busy;
		boolean jobFinished;
		@Override
		public void run() {
			if(busy) {
				return;
			}
			if(!finished) {
				busy = true;
				String url = getNextUrl();
				System.out.println("已完成"+((currentIndex+0.0d)/urlList.length)+"%，正在提取总"+urlList.length+"中的第"+currentIndex + "个文章......");
				FileUtil.writeText("D:\\ssbb\\yz\\" + currentIndex + ".txt", getContent(host+url));
				busy = false;
			}
			jobFinished = true;
		}
		
	}

	public static void main(String[] args) {
		long startTime = new Date().getTime();

		String loginUrl = "http://ssbb.net.ru/logging.php?action=login&loginsubmit=yes&inajax=1&username=darkness&password=depravedAngel";
		CopyOfHttpClientTest httpClientTest = new CopyOfHttpClientTest();
		httpClientTest.login("darkness", "depravedAngel", loginUrl);

		int page = 70;
		String url = "http://ssbb.net.ru/forumdisplay.php?fid=12&page=";
		for (int i = 0; i < page; i++) {
			System.out.println("========" + i);
			httpClientTest.getArticelUrl(url + i);
		}
		System.out.println("提取文章url完毕！");
		System.out.println("共耗时：" + (new Date().getTime() - startTime));
		
		FileUtil.writeText("D:\\ssbb\\yz\\urls.txt", httpClientTest.urls.toString());
		httpClientTest.getArticles();
		
		// FileUtil.writeText(fileName, content)
		// System.out.println("viewthread.php?tid=66309&extra=page%3D1".replaceAll("tid=\\d{5}&",
		// ""));
		// System.out.println(articleUrl.equals("viewthread.php?tid=66309&extra=page%3D1".replaceAll("tid=\\d{5}&",
		// "")));
	}
}
