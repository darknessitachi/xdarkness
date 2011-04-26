package com.xdarkness.search.crawl;

import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.search.DocumentList;
import com.xdarkness.search.SearchUtil;
import com.xdarkness.search.WebDocument;

/**
 * 从文档列表当前层级的上一层级文档内容中提取出符合规则的URL，将其加入文档列表中
 * @author Darkness
 *
 */
public class UrlExtracterThread extends Thread {
	private DocumentList list;
	private int level;
	private boolean isBusy;
	private UrlExtracter extracter;
	private int threadIndex;
	private WebDocument doc;

	public void run() {
		this.doc = this.list.next();
	Crawler crawler = this.list.getCrawler();
		int maxPageCount = this.list.getCrawler().getConfig().getMaxPageCount();//最大采集数
		int maxListCount = this.list.getCrawler().getConfig().getMaxListCount();
		int maxUrlLevel = this.list.getCrawler().getConfig().getUrlLevels().length;
		while (this.doc != null) {
if ((maxPageCount > 0) && (this.list.size() >= maxPageCount)) {
				break;
			}
			if (!this.list.getCrawler().task.checkStop()) {
				updateIsBusy();

	/**
				 * 从上一层级doc的内容中提取出URL
				 */
				if ((this.doc.getLevel() == this.level - 1)
						&& (this.doc.isTextContent())) {
crawler.task.setCurrentInfo("正在提取" + this.doc.getUrl() + "中的URL");
					// 获取内容中的所有URL
					String[] urls = SearchUtil.getUrlFromHtml(this.doc
							.getContentText());
					for (int k = 0; k < urls.length; k++) {
						if ((maxPageCount > 0)
								&& (maxUrlLevel == this.doc.getLevel() + 2)
								&& (this.extracter.extractOutCount >= maxPageCount)) {
							break;
						}
						if ((maxListCount > 0)
								&& (maxUrlLevel == this.doc.getLevel() + 3)
								&& (this.extracter.extractOutCount >= maxListCount)) {
							break;
						}
						String url2 = urls[k];
						url2 = SearchUtil.normalizeUrl(url2, this.doc.getUrl());
						url2 = SearchUtil.escapeUrl(url2);

// 不符合当前层级URL抓取规则或该URL已经在文档列表里
						if ((!this.extracter.isMatchedUrl(url2)) || (this.list.containsKey(url2)))
							continue;
// 已达到最大抓取文档上线
						if ((maxPageCount > 0) && (this.list.size() >= maxPageCount)) {
							break;
						}

						if (this.extracter
								.isMatchedUrl(url2, this.doc.getUrl())) {
							if (!this.list.containsKey(url2)) {
								WebDocument doc2 = new WebDocument();
								doc2.setUrl(url2);
								doc2.setLevel(this.level);
								doc2.setRefUrl(this.doc.getUrl());
								this.list.put(doc2);
								CrawlScriptUtil util = new CrawlScriptUtil();
								util.doc = doc2;
								util.list = this.list;
								this.list.getCrawler().executeScript("before",
										util);

								this.extracter.extractOutCount += 1;//this.extracter.extractedCount += 1;
//crawler.executeScript("before", new CrawlScriptUtil(webDocument,this.list));
							} else if (this.list.get(url2).getContent() != null) {
								this.extracter.extractOutCount += 1;
							}
						}
					}
	//logPercent();
					this.extracter.extractedCount += 1;
					long percent = Math.round(this.extracter.extractedCount
							* 10000.0D / this.extracter.size);
					long p = percent % 100L;
					String pStr = p+"";
					if (p < 10L) {
						pStr = "0" + p;
					}
					LogUtil.getLogger().info(
							"Extracting,Thread " + this.threadIndex + "\tL"
									+ this.doc.getLevel() + "\t" + percent
									/ 100L + "." + pStr + "%");
				} else if ((this.doc.getLevel() == this.level)
						&& (!this.extracter.isMatchedUrl(this.doc.getUrl(),
								this.doc.getRefUrl()))) {
					this.list.remove(this.doc.getUrl());
				}

				this.doc = this.list.next();
			} else {
				if (this.isBusy) {
					synchronized (this.extracter) {
						this.extracter.busyThreadCount -= 1;
					}
				}
				this.doc = null;
			}
		}
		synchronized (this.extracter) {
			this.isBusy = false;
			this.extracter.busyThreadCount -= 1;
			this.extracter.aliveThreadCount -= 1;
		}
	}
	/**
	 * 更新为非忙碌状态
	 */
	private void updateNotBusy() {
		if (this.isBusy) {
			synchronized (UrlExtracter.class) {
				this.extracter.busyThreadCount -= 1;
				this.isBusy = false;
			}
		}
	}

	/**
	 * 更新为忙碌状态
	 */
	private void updateIsBusy() {
		if (!this.isBusy) {
			synchronized (UrlExtracter.class) {
				this.extracter.busyThreadCount += 1;
				this.isBusy = true;
			}
		}
		
	}

	/**
	 * 记录当前抓取状态
	 */
	private void logPercent() {
		LogUtil.getLogger().info(
				"Extracting,Thread " + this.threadIndex + "\tL"
						+ this.doc.getLevel() + "\t" + this.extracter.getExtracterPercent());
	}
	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public DocumentList getList() {
		return this.list;
	}

	public void setList(DocumentList list) {
		this.list = list;
	}

	public UrlExtracter getExtracter() {
		return this.extracter;
	}

	public void setExtracter(UrlExtracter extracter) {
		this.extracter = extracter;
	}

	public int getThreadIndex() {
		return this.threadIndex;
	}

	public void setThreadIndex(int threadIndex) {
		this.threadIndex = threadIndex;
	}
}
