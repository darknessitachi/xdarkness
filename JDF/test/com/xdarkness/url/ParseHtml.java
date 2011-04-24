package com.xdarkness.url;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibm.db2.jcc.b.i;
import com.sun.swing.internal.plaf.synth.resources.synth;
import com.xdarkness.framework.io.FileUtil;

public class ParseHtml {

	private int allCount = 0;
	private int successCount = 0;
	private int failureCount = 0;

	int currentIndex =0;
	List<File> files;
	public synchronized File getFile(){
		if(currentIndex>=files.size())
			return null;
		return files.get(currentIndex++);
	}
	public synchronized void addSuccessCount(){
		successCount++;
	}
	public synchronized void addFailureCount(){
		failureCount++;
	}
	public void parse() {
		File file = new File("D:\\ssbb");
		files = getAllFiles(file);
		allCount = files.size();
		
		for (int i = 0; i < 15; i++) {
			new Thread(){
				@Override
				public void run() {
					while(true){
						System.out.println("正在处理第"+(currentIndex)+"个文件...");
//						System.out.println("【"+(currentIndex/allCount*100)+"%】正在处理第"+(currentIndex)+"个文件，成功："+successCount+"个，失败："+(failureCount)+"个...");
						File file2 = getFile();
						if(file2==null)
							return;
						
						String content = FileUtil.readText(file2);
						Article article = getData(content);
						if (article != null) {
							content = article.toString();
							FileUtil.writeText(file2.getAbsolutePath(), content);
//							addSuccessCount();
						} else {
//							addFailureCount();
						}
					}
				}
			}.start();
		}
		
		
		System.out.println("all:"+allCount + ", success:"+successCount + ", failure:"+(failureCount)) ;
	}

	Pattern pattern = Pattern
			.compile("<div id=\"threadtitle\">([\\s\\S]*)<h1>([\\s\\S]*)</h1>([\\s\\S]*)</div>([\\s\\S]*)<div class=\"t_msgfontfix\">([\\s\\S]*)<font color=\"Blue\">([\\s\\S]*)</div>([\\s\\S]*)<div class=\"useraction\">");

	public Article getData(String content) {
		Matcher match = pattern.matcher(content);
		if (match.find()) {
			return new Article(match.group(2), match.group(6));
		}
		return null;
	}

	class Article {
		String title;
		String content;

		public Article(String t, String c) {
			this.title = t;
			this.content = c;
		}

		@Override
		public String toString() {
			return "<tilte>" + this.title + "</title><content>" + content
					+ "</content>";
		}
	}

	public List<File> getAllFiles(File folder) {
		List<File> fileList = new ArrayList<File>();

		File[] files = folder.listFiles();
		for (File file2 : files) {
			if (!file2.isDirectory()) {
				fileList.add(file2);
			} else {
				fileList.addAll(getAllFiles(file2));
			}
		}
		return fileList;
	}

	public static void main(String[] args) {
		String content = "<div id=\"threadtitle\">\r\n"
				+ "<h1>[国产/无码] 北京工大贾楠在希尔顿酒店开房完整版[MP4/101MB]</h1>\r\n"
				+ "</div><div class=\"t_msgfontfix\">\r\n"
				+ "<table cellspacing=\"0\" cellpadding=\"0\"><tbody><tr><td id=\"postmessage_331370\" class=\"t_msgfont\"><font size=\"4\"><font color=\"Blue\">[国产/无码] 北京工大贾楠在希尔顿酒店开房完整版[MP4/101MB]<br>\r\n"
				+ "<br>\r\n"
				+ "[影片名称]: 国产无修正「北京工大贾楠在希尔顿酒店(看枕巾上的标识)开房完整版.<br>\r\n"
				+ "<br>\r\n"
				+ "<br>\r\n"
				+ "<br>\r\n"
				+ "穿情趣内衣被男的外射，好漂亮的PP啊，磨的那男的估计喷出2米远，接着还喷洒了好<br>\r\n"
				+ "<br>\r\n"
				+ "<br>\r\n"
				+ "<br>\r\n"
				+ "几下」<br>\r\n"
				+ "<br>\r\n"
				+ "[影片大小]: 101.62 MB<br>\r\n"
				+ "<br>\r\n"
				+ "[影片时间]: 时间未统计<br>\r\n"
				+ "<br>\r\n"
				+ "[影片格式]：MP4<br>\r\n"
				+ "<br>\r\n"
				+ "[播放软件]：暴风影音，其它软件未测试<br>\r\n"
				+ "<br>\r\n"
				+ "[有码无码]: 无<br>\r\n"
				+ "<br>\r\n"
				+ "[特 征 码]: c14a7fc07daa61649ff3a6327f2d9c3231b4ca4c 分块大小:128KB<br>\r\n"
				+ "<br>\r\n"
				+ "[种子期限]：大水管接入 超速上传，请自觉留种1小时，做个有种的男人<br>\r\n"
				+ "<br>\r\n"
				+ "[影图预览]: 种子里有图片<br>\r\n"
				+ "<br>\r\n"
				+ "<br>\r\n"
				+ "<img height=\"225\" width=\"300\" alt=\"\" onload=\"thumbImg(this)\" src=\"http://flare.me/images/kbywnrs0.jpg\"><br>\r\n"
				+ "<br>\r\n"
				+ "<br>\r\n"
				+ "<br>\r\n"
				+ "<img height=\"225\" width=\"300\" alt=\"\" onload=\"thumbImg(this)\" src=\"http://flare.me/images/ca6f3b2g.jpg\"><br>\r\n"
				+ "<br>\r\n"
				+ "<br>\r\n"
				+ "<br>\r\n"
				+ "<img height=\"225\" width=\"300\" alt=\"\" onload=\"thumbImg(this)\" src=\"http://flare.me/images/a73cijxf.jpg\"><br>\r\n"
				+ "<br>\r\n"
				+ "<br>\r\n"
				+ "<br>\r\n"
				+ "<img height=\"225\" width=\"300\" alt=\"\" onload=\"thumbImg(this)\" src=\"http://flare.me/images/z9q35nv9.jpg\"><br>\r\n"
				+ "<br>\r\n"
				+ "<br>\r\n"
				+ "<br>\r\n"
				+ "<img height=\"225\" width=\"300\" alt=\"\" onload=\"thumbImg(this)\" src=\"http://flare.me/images/ilfy235y.jpg\"><br>\r\n"
				+ "<br>\r\n"
				+ "<br>\r\n"
				+ "<br>\r\n"
				+ "<br>\r\n"
				+ "[下载地址]：<br>\r\n"
				+ "<a target=\"_blank\" href=\"http://umxx.com/free_web_file_host/1524.shtml\">http://umxx.com/free_web_file_host/1524.shtml</a><br>\r\n"
				+ "<br>\r\n"
				+ "<br>\r\n"
				+ "[下载地址]：<br>\r\n"
				+ "<a target=\"_blank\" href=\"http://zzupload.com/upload/12909360951053.shtml\">http://zzupload.com/upload/12909360951053.shtml</a></font></font></td></tr></tbody></table>\r\n"
				+ "</div><div id=\"post_rate_div_331370\"></div><div class=\"useraction\">\r\n"
				+ "<a onclick=\"showDialog($('favoritewin').innerHTML, 'info', '收藏/关注')\" href=\"javascript:;\">收藏</a>\r\n"
				+ "<a onclick=\"showDialog($('sharewin').innerHTML, 'info', '分享')\" id=\"share\" href=\"javascript:;\">分享</a>\r\n"
				+ "</div>";
		new ParseHtml().parse();
	}
}
