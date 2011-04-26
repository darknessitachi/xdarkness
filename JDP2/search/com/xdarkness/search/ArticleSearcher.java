package com.xdarkness.search;

import java.util.Date;

import com.xdarkness.cms.api.SearchAPI;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.util.DateUtil;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.XString;

public class ArticleSearcher {
	public static SearchResult search(Mapx map) {
		SearchParameters sps = new SearchParameters();
		String site = map.getString("site");
		String id = map.getString("id");
		String catalog = map.getString("catalog");
		if (XString.isEmpty(catalog)) {
			catalog = map.getString("Catalog");
		}
		String order = map.getString("order");
		String time = map.getString("time");
		String keyword = map.getString("keyword");
		String query = map.getString("query");
		if (XString.isEmpty(keyword)) {
			keyword = query;
		}
		String page = map.getString("page");
		String size = map.getString("size");

		if (XString.isEmpty(id)) {
			id = SearchAPI.getIndexIDBySiteID(site);
		}

		if (XString.isNotEmpty(keyword)) {
			sps.addFulltextField("Title", keyword, false);
			sps.addFulltextField("Content", keyword, false);
			sps.addFulltextField("_Keyword", keyword, true);
		}

		if ("time".equalsIgnoreCase(order)) {
			sps.setSortField("PublishDate", 3, true);
		}

		if (XString.isNotEmpty(time)) {
			Date today = new Date();
			String StartDate = DateUtil
					.toString(DateUtil.addDay(today, -36500));
			if (time.equals("week"))
				StartDate = DateUtil.toString(DateUtil.addDay(today, -7));
			else if (time.equals("month"))
				StartDate = DateUtil.toString(DateUtil.addDay(today, -30));
			else if (time.equals("quarter")) {
				StartDate = DateUtil.toString(DateUtil.addDay(today, -90));
			}
			String EndDate = "2999-01-01";
			sps.setDateRange("PublishDate", StartDate, EndDate);
		}
		if (XString.isNotEmpty(catalog)) {
			sps.addLeftLikeField("CatalogInnerCode", catalog);
		}
		if (XString.isNotEmpty(page)) {
			sps.setPageIndex(Integer.parseInt(page) - 1);
		}
		if (XString.isNotEmpty(size)) {
			sps.setPageSize(Integer.parseInt(size));
		}
		if (XString.isEmpty(id)) {
			SearchResult sr = new SearchResult();
			sr.Data = new DataTable();
			return sr;
		}
		sps.setIndexID(Long.parseLong(id));
		return ArticleIndexer.search(sps);
	}

	public static SearchResult tagSearch(Mapx map) {
		SearchParameters sps = new SearchParameters();
		String site = map.getString("site");
		String order = map.getString("order");
		String keyword = map.getString("keyword");
		String query = map.getString("query");
		if (XString.isEmpty(keyword)) {
			keyword = query;
		}
		String page = map.getString("page");
		String size = map.getString("size");

		if (XString.isNotEmpty(keyword)) {
			sps.addLikeField("Tag", keyword, false);
		}

		if ("time".equalsIgnoreCase(order)) {
			sps.setSortField("PublishDate", 3, true);
		}
		if (XString.isNotEmpty(page)) {
			sps.setPageIndex(Integer.parseInt(page) - 1);
		}
		if (XString.isNotEmpty(size)) {
			sps.setPageSize(Integer.parseInt(size));
		}
		String id = SearchAPI.getIndexIDBySiteID(site);
		sps.setIndexID(Long.parseLong(id));
		return ArticleIndexer.search(sps);
	}

	public static SearchResult advanceSearch(Mapx map) {
		SearchParameters sps = new SearchParameters();

		String site = map.getString("site");
		String id = map.getString("id");
		String startDate = map.getString("startdate");
		String endDate = map.getString("enddate");
		String catalog = map.getString("catalog");
		String author = map.getString("author");
		String title = map.getString("title");
		String content = map.getString("content");
		String keyword = map.getString("keyword");
		String query = map.getString("query");
		if (XString.isEmpty(keyword)) {
			keyword = query;
		}
		String orderField = map.getString("orderfield");
		String descFlag = map.getString("descflag");
		String page = map.getString("page");
		String size = map.getString("size");

		if (XString.isEmpty(id)) {
			id = SearchAPI.getIndexIDBySiteID(site);
		}

		if ((XString.isNotEmpty(startDate)) && (XString.isEmpty(endDate))) {
			endDate = "2099-01-01";
		}
		if ((XString.isNotEmpty(endDate)) && (XString.isEmpty(startDate))) {
			startDate = "1900-01-01";
		}
		if (XString.isNotEmpty(startDate)) {
			sps.setDateRange("PublishDate", startDate, endDate);
		}
		if (XString.isNotEmpty(catalog)) {
			sps.addLeftLikeField("CatalogInnerCode", catalog, true);
		}
		if (XString.isNotEmpty(title)) {
			sps.addFulltextField("Title", title);
		}
		if (XString.isNotEmpty(content)) {
			sps.addFulltextField("Content", content);
		}
		if (XString.isNotEmpty(keyword)) {
			sps.addFulltextField("Title", keyword, false);
			sps.addFulltextField("Content", keyword, false);
			sps.addFulltextField("_Keyword", keyword, true);
		}
		if (XString.isNotEmpty(orderField)) {
			boolean isDesc = "true".equals(descFlag);
			sps.setSortField(orderField, 3, isDesc);
		}
		if (XString.isNotEmpty(author)) {
			sps.addEqualField("Author", author);
		}
		if (XString.isNotEmpty(page)) {
			sps.setPageIndex(Integer.parseInt(page) - 1);
		}
		if (XString.isNotEmpty(size)) {
			sps.setPageSize(Integer.parseInt(size));
		}
		if (XString.isEmpty(id)) {
			SearchResult sr = new SearchResult();
			sr.Data = new DataTable();
			return sr;
		}

		sps.setIndexID(Long.parseLong(id));
		return ArticleIndexer.search(sps);
	}

	public static void main(String[] args) {
		SearchParameters sps = new SearchParameters();
		sps.setIndexID(66L);

		sps.addFulltextField("Title", "科技");
		SearchResult sr = ArticleIndexer.search(sps);
		System.out.println(sr.Data);
	}
}

/*
 * com.xdarkness.search.ArticleSearcher JD-Core Version: 0.6.0
 */