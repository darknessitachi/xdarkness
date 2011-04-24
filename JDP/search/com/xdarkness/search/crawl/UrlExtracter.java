package com.xdarkness.search.crawl;

import java.util.ArrayList;
import java.util.List;

import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.RegexParser;
import com.xdarkness.framework.util.ServletUtil;
import com.xdarkness.framework.util.XString;
import com.xdarkness.search.DocumentList;
import com.xdarkness.search.SearchUtil;
/**
 * 抓取文档列表中的当前层级URL
 * 从文档列表当前层级的上一层级文档内容中提取出符合规则的URL，将其加入文档列表中
 * @author Darkness
 *
 */
public class UrlExtracter extends Thread {
	private int threadCount = 1;//4

	protected int aliveThreadCount = 0;

	protected int busyThreadCount = 0;
	protected FileDownloader fileDownloader;
// 允许的资源后缀
	private String allowExtension;
	// 不允许的资源后缀
	private String denyExtension;
	protected int size;
	protected int extractOutCount;
	protected int extractedCount;
	// 当前抓取URL列表
	List<String> urlArr = new ArrayList<String>();
	// 当前抓取URL通配列表
	List<RegexParser> rpArr = new ArrayList<RegexParser>();
	// 当前抓取URL通配过滤列表
	List<RegexParser> rpFilterArr = new ArrayList<RegexParser>();
	// 抓取配置规则
	CrawlConfig cc;
/**
	 * 获取当前链接的抓取百分比
	 * @return
	 */public String getExtracterPercent() {
		long percent = Math.round(this.extractedCount * 10000.0D / this.size);
		long p = percent % 100L;
		String pStr = "" + p;
		if (p < 10L) {
			pStr = "0" + p;
		}
		return percent / 100L + "." + pStr + "%";
	}
/**
	 * 根据CrawlConfig配置当前层级URL抓取规则列表
	 * @param crawlConfig
	 * @param level
	 * @param allowExtension
	 * @param denyExtension
	 */

	//protected void init(DocumentList list, int level,
			//FileDownloader fileDownloader) {
protected void init(CrawlConfig crawlConfig, int level,
			String allowExtension, String denyExtension) {
this.allowExtension = allowExtension;
		this.denyExtension = denyExtension;
		
		this.fileDownloader = fileDownloader;
		this.extractedCount = 0;
		//this.cc = list.getCrawler().getConfig();
	this.cc = crawlConfig;
		String[] arr = this.cc.getUrlLevels()[level].trim().split("\n");
		this.urlArr.clear();
		this.rpArr.clear();
		for (int i = 0; i < arr.length; i++) {
			String url = arr[i].trim();
			if (XString.isEmpty(url)) {
				continue;
			}
			url = url.trim();
			url = SearchUtil.escapeUrl(url);
			RegexParser rp = new RegexParser(url);//, true);
			this.urlArr.add(url);
			this.rpArr.add(rp);
		}
		if (this.cc.isFilterFlag()) {
			arr = this.cc.getFilterExpr().trim().split("\n");
			for (int i = 0; i < arr.length; i++) {
				String url = arr[i].trim();
				if (XString.isEmpty(url)) {
					continue;
				}
				url = url.trim();
				RegexParser rp = new RegexParser(url);//, true);
				this.rpFilterArr.add(rp);
			}
		}
	}

	/**
	 * 抓取文档列表中的当前层级URL
	 * @param list
	 * @param level
	 * @param allowExtension
	 * @param denyExtension
	 */
	public void extract(DocumentList list, int level, String allowExtension, String denyExtension) {
	
	//public void extract(DocumentList list, int level,
	//		FileDownloader fileDownloader) {
		//init(list, level, fileDownloader);
init(list.getCrawler().getConfig(), level, allowExtension, denyExtension);
		
		this.aliveThreadCount = this.threadCount;
		this.size = list.size();
		list.moveFirst();
		for (int i = 0; i < this.threadCount; i++) {
			UrlExtracterThread edt = new UrlExtracterThread();
			edt.setExtracter(this);
			edt.setList(list);
			edt.setLevel(level);
			edt.setThreadIndex(i);
			edt.start();
		}
		try {
			while (this.aliveThreadCount != 0)
	LogUtil.getLogger().debug("当前存活线程数："+this.aliveThreadCount);
				Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean isMatchedUrl(String url2){
		return isMatchedUrl(url2,"");
	}
/**
	 * 判断是否是符合抓取规则的URL
	 * @param url2
	 * @return
	 */
	public boolean isMatchedUrl(String url2, String refUrl){
		if (url2.startsWith("http://mso.allyes.com")) {
			System.out.println(1);
		}

/**
		 * 判断URL后缀是否在抓取列表中
		 */
		String ext = ServletUtil.getUrlExtension(url2);
		if ((XString.isNotEmpty(ext))
				&& ((XString
						.isEmpty(this.fileDownloader.getAllowExtension())) || (this.fileDownloader
						.getAllowExtension().indexOf(ext) < 0))
				&& (this.fileDownloader.getDenyExtension().indexOf(ext) >= 0)) {
			return false;
		}
if ((XString.isNotEmpty(ext))
				&& (((XString.isEmpty(this.allowExtension)) || (this
						.allowExtension.indexOf(ext) < 0)))
				&& (this.denyExtension.indexOf(ext) >= 0)) {
			return false;
		}
	/**
		 * 判断是否匹配过滤URL规则
		 */
		boolean matchedFlag = false;
		for (int i = 0; i < this.rpFilterArr.size(); i++) {
			RegexParser rp = (RegexParser) this.rpFilterArr.get(i);

			synchronized (rp) {
				rp.setText(url2);
				if (rp.match()) {
					return false;
				}
			}
/*
Mapx[] maps = rp.getMatchedMaps(url2);
			if (maps.length != 0) {
				return false;
			}
*/
		}

	/**
		 * 判断是否匹配抓取URL规则
		 */
		for (int i = 0; i < this.rpArr.size(); i++) {
			String url = (String) this.urlArr.get(i);
			RegexParser rp = (RegexParser) this.rpArr.get(i);

			if (url.indexOf('/', 8) > 0) {
				String prefix = url.substring(0, url.indexOf('/', 8));
				if ((prefix.indexOf('$') < 0) && (!url2.startsWith(prefix))) {
					continue;
				}

			}

			synchronized (rp) {
				rp.setText(url2);
				if (rp.match()) {
					matchedFlag = true;
				}
			}
/*
Mapx[] maps = rp.getMatchedMaps(url2);
			if (maps.length == 0) {
				continue;
			}
			matchedFlag = true;
*/
		}
		return matchedFlag;
	}
}
