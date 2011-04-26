package com.xdarkness.search.crawl;

import com.xdarkness.search.DocumentList;
import com.xdarkness.search.WebDocument;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.RegexParser;

public class CrawlScriptUtil {
	protected DocumentList list;
	protected WebDocument doc;
	protected Mapx map;

	public String getField(String content, String regex, String key) {
		RegexParser rp = new RegexParser(regex);
		rp.setText(content);
		if (rp.match()) {
			return this.map.getString(key);
		}
		return null;
		/*
		 * Mapx[] maps = rp.getMatchedMaps(content); if (maps.length == 0) {
		 * return null; } return maps[0].getString(key);
		 */
	}

	public String getCurrentUrlField(String field) {
		return this.map.getString(field);
	}

	public String getCurrentUrl() {
		return this.doc.getUrl();
	}

	public int getCurrentLevel() {
		return this.doc.getLevel();
	}

	public void removeUrl(String url) {
		this.list.remove(url);
	}

	public void removeCurrentUrl() {
		this.list.remove(this.doc.getUrl());
	}

	public void addUrl(String url) {
		addUrl(url, this.doc.getLevel());
	}

	public String getCurrentContent() {
		return this.doc.getContentText();
	}

	public String getContent(String url) {
		WebDocument wd = this.list.get(url);
		if (wd != null) {
			return wd.getContentText();
		}
		return null;
	}

	public void addUrl(String url, int level) {
		WebDocument wd = new WebDocument();
		wd.setUrl(url);
		wd.setLevel(level);
		wd.setRefUrl(this.doc.getUrl());
		this.list.put(wd);
	}

	public void addDocument(String url, String content) {
	}
}
