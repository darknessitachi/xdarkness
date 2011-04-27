package com.xdarkness.url;

import java.io.IOException;
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

import com.abigdreamer.java.net.io.FileUtil;

public class UrlCrawer {
	// http://ssbb.net.ru/viewthread.php?tid=66309&extra=page%3D1
	static String articleUrl = "viewthread.php?extra=page";
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

	public void getArticelUrl(String url) {
		matchUrl(getContent(url));
	}

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

	private static Set<String> urls = new HashSet<String>();

	public String getContent(String url) {
		// System.out.println("正在提取url："+url);
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

	public void crawerUrl() {
		long startTime = new Date().getTime();

		String loginUrl = "http://ssbb.net.ru/logging.php?action=login&loginsubmit=yes&inajax=1&username=darkness&password=depravedAngel";
		login("darkness", "depravedAngel", loginUrl);

		int page = 70;
		String url = "http://ssbb.net.ru/forumdisplay.php?fid=12&page=";
		for (int i = 0; i < page; i++) {
			System.out.println("========" + i);
			getArticelUrl(url + i);
		}
		System.out.println("提取文章url完毕！");
		System.out.println("共耗时：" + (new Date().getTime() - startTime));

		FileUtil.writeText("D:\\ssbb\\yz\\urls.txt", urls.toString());
	}
}
