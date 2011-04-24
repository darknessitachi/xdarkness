package com.xdarkness.search;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.xdarkness.cms.api.ArticleAPI;
import com.xdarkness.cms.dataservice.ColumnUtil;
import com.xdarkness.schema.ZCFullTextSchema;
import com.xdarkness.search.index.Indexer;
import com.xdarkness.framework.orm.data.DataRow;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.NumberUtil;

public class ResourceIndexer extends Indexer {
	private long id;
	private int PageSize = 200;
	private String type;
	private int catalogType;
	private ZCFullTextSchema ft;

	public ResourceIndexer(long fullTextID) {
		this.id = fullTextID;
		setPath(Config.getContextRealPath() + "WEB-INF/data/index/" + this.id
				+ "/");
		this.ft = new ZCFullTextSchema();
		this.ft.setID(this.id);
		if (!this.ft.fill()) {
			return;
		}
		this.type = this.ft.getType();
		this.catalogType = 0;
		if ("Image".equalsIgnoreCase(this.type)) {
			this.catalogType = 4;
		}
		if ("Video".equalsIgnoreCase(this.type)) {
			this.catalogType = 5;
		}
		if ("Audio".equalsIgnoreCase(this.type)) {
			this.catalogType = 6;
		}
		if ("Attachment".equalsIgnoreCase(this.type)) {
			this.catalogType = 7;
		}
		if ("true".equals(Config.getValue("App.MinimalMemory")))
			this.PageSize = 50;
	}

	public void create() {
		try {
			String rela = this.ft.getRelaText();
			if (rela.indexOf("-1") >= 0) {
				QueryBuilder qb = new QueryBuilder(
						"select distinct ID from zccatalog where SiteID=? and Type=?",
						this.ft.getSiteID(), this.catalogType);
				DataTable catalogs = qb.executeDataTable();
				for (int j = 0; j < catalogs.getRowCount(); j++) {
					qb = new QueryBuilder(
							"select (select Name from zccatalog where id=zc"
									+ this.type
									+ ".catalogid) as catalogname,zc"
									+ this.type + ".* from zc" + this.type
									+ " where siteid=" + this.ft.getSiteID()
									+ " and status=" + 30 + " and catalogid =?",
							catalogs.getString(j, "ID"));
					int total = qb.getCount();
					for (int i = 0; i * this.PageSize < total; i++) {
						DataTable dt = qb.executePagedDataTable(this.PageSize,
								i);
						extendArticleData(dt);
						ColumnUtil.extendDocColumnData(dt, catalogs.getString(
								j, "ID"));
						add(dt, this.writer);
					}
				}
			} else {
				String[] catalogs = rela.split(",");
				for (int j = 0; j < catalogs.length; j++) {
					QueryBuilder qb = new QueryBuilder(
							"select (select Name from zccatalog where id=zc"
									+ this.type
									+ ".catalogid) as catalogname,zc"
									+ this.type + ".* from zc" + this.type
									+ " where siteid=" + this.ft.getSiteID()
									+ " and status=" + 30 + " and catalogid =?",
							catalogs[j]);
					int total = qb.getCount();
					for (int i = 0; i * this.PageSize < total; i++) {
						DataTable dt = qb.executePagedDataTable(this.PageSize,
								i);
						extendArticleData(dt);
						ColumnUtil.extendDocColumnData(dt, catalogs[j]);
						add(dt, this.writer);
					}
				}
			}
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void extendArticleData(DataTable dt) {
		String ids = XString.join(dt.getColumnValues("ID"));
		if (XString.isEmpty(ids)) {
			ids = "0";
		}
		String condition = "";
		if (this.type.equalsIgnoreCase("Image")) {
			condition = " and RelaType='ArticleImage'";
		}
		QueryBuilder qb = new QueryBuilder(
				"select a.Title,a.Keyword,b.RelaID from ZCArticle a,ZC"
						+ this.type + "Rela b where b.RelaID=a.ID " + condition
						+ " and b.RelaID in (" + ids
						+ ") order by b.RelaID desc");
		DataTable articles = qb.executeDataTable();
		dt.insertColumn("Title");
		dt.insertColumn("Content");
		dt.insertColumn("ArticleID");
		for (int i = 0; i < dt.getRowCount(); i++) {
			boolean firstFlag = true;
			for (int j = 0; j < articles.getRowCount(); j++)
				if (articles.getString(j, "RelaID").equals(
						dt.getString(i, "ID"))) {
					if (firstFlag) {
						dt.set(i, "Title", articles.getString(j, "Title"));
						dt.set(i, "ArticleID", articles.getString(j, "RelaID"));
						firstFlag = false;
					}
					dt.set(i, "Content", dt.getString(i, "Content") + ";"
							+ articles.getString(j, "Title") + ";"
							+ articles.getString(j, "Keyword"));
				}
		}
	}

	private void index(DataTable dt, IndexWriter writer, boolean updateFlag)
			throws CorruptIndexException, IOException {
		for (int i = 0; i < dt.getRowCount(); i++) {
			Document doc = new Document();
			DataRow dr = dt.getDataRow(i);

			Date d1 = dr.getDate("AddTime");
			Date d2 = dr.getDate("ModifyTime");
			if ((d1 != null) && (d1.getTime() > this.nextLastDate.getTime())) {
				this.nextLastDate = d1;
			}
			if ((d2 != null) && (d2.getTime() > this.nextLastDate.getTime())) {
				this.nextLastDate = d2;
			}
			StringBuffer sb = new StringBuffer();
			sb.append(dr.getString("Title"));
			sb.append(" ");
			sb.append(dr.getString("Content"));
			sb.append(" ");
			sb.append(dr.getString("OldName"));
			if (dr.getString("CatalogName").startsWith("默认")) {
				sb.append(" ");
				sb.append(dr.getString("CatalogName"));
			}
			try {
				String url = dr.getString("Path") + dr.getString("FileName");
				if (XString.isNotEmpty(dr.getString("ArticleID"))) {
					url = ArticleAPI.getPublishedURL(Long.parseLong(dr
							.getString("ArticleID")));
				}
				for (int n = 0; n < dt.getColCount(); n++) {
					String columnName = dt.getDataColumn(n).getColumnName();
					if (columnName.equalsIgnoreCase("URL"))
						doc.add(new Field("URL", url, Field.Store.YES,
								Field.Index.NO));
					else if (columnName.equalsIgnoreCase("TITLE"))
						doc.add(new Field("TITLE", dr.getString(n),
								Field.Store.YES, Field.Index.NOT_ANALYZED));
					else {
						doc.add(new Field(columnName.toUpperCase(), dr
								.getString(n), Field.Store.YES,
								Field.Index.NOT_ANALYZED));
					}
				}

				doc.add(new Field("_KEYWORD", sb.toString(), Field.Store.NO,
						Field.Index.ANALYZED));

				if ((updateFlag)
						&& (XString
								.isNotEmpty(dt.getString(i, "ModifyTime"))))
					writer.updateDocument(
							new Term("ID", dt.getString(i, "ID")), doc);
				else
					writer.addDocument(doc);
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	private void add(DataTable dt, IndexWriter writer)
			throws CorruptIndexException, IOException {
		index(dt, writer, false);
	}

	private void update(DataTable dt, IndexWriter writer)
			throws CorruptIndexException, IOException {
		index(dt, writer, true);
	}

	public void update() {
		try {
			String rela = this.ft.getRelaText();
			if (rela.indexOf("-1") >= 0) {
				DataTable catalogs = new QueryBuilder(
						"select distinct CatalogID from zc" + this.type
								+ " where siteid=" + this.ft.getSiteID()
								+ " and (addtime>=?"
								+ " or modifytime>=?) and status=" + 30,
						this.lastDate, this.lastDate).executeDataTable();
				for (int j = 0; j < catalogs.getRowCount(); j++) {
					QueryBuilder qb = new QueryBuilder(
							"select (select Name from zccatalog where id=zc"
									+ this.type
									+ " .catalogid) as catalogname,zc"
									+ this.type
									+ " .* from zc"
									+ this.type
									+ "  where siteid="
									+ this.ft.getSiteID()
									+ " and CatalogID=? and (addtime>=? or modifytime>=?) and status="
									+ 30, catalogs.getString(j, "CatalogID"),
							this.lastDate);
					qb.add(this.lastDate);
					int total = qb.getCount();
					for (int i = 0; i * this.PageSize < total; i++) {
						DataTable dt = qb.executePagedDataTable(this.PageSize,
								i);
						extendArticleData(dt);
						ColumnUtil.extendDocColumnData(dt, catalogs.getString(
								j, "ID"));
						update(dt, this.writer);
					}
				}
				DataTable dt = new QueryBuilder("select id from bzc"
						+ this.type + "  where status=" + 30
						+ " and siteid=? and backuptime>?",
						this.ft.getSiteID(), this.lastDate).executeDataTable();
				for (int i = 0; i < dt.getRowCount(); i++) {
					this.writer.deleteDocuments(new Term("ID", dt.getString(i,
							0)));
				}
				dt = new QueryBuilder("select id from zc" + this.type
						+ "  where status=" + 40
						+ " and siteid=? and modifytime>?",
						this.ft.getSiteID(), this.lastDate).executeDataTable();
				for (int i = 0; i < dt.getRowCount(); i++)
					this.writer.deleteDocuments(new Term("ID", dt.getString(i,
							0)));
			} else {
				String[] catalogs = rela.split(",");
				for (int j = 0; j < catalogs.length; j++) {
					QueryBuilder qb = new QueryBuilder(
							"select (select Name from zccatalog where id=zc"
									+ this.type
									+ " .catalogid) as catalogname,zc"
									+ this.type
									+ " .* from zc"
									+ this.type
									+ "  where status="
									+ 30
									+ " and catalogid=? and (addtime>=? or modifytime>=?)",
							catalogs[j], this.lastDate);
					qb.add(this.lastDate);
					int total = qb.getCount();
					for (int i = 0; i * this.PageSize < total; i++) {
						DataTable dt = qb.executePagedDataTable(this.PageSize,
								i);
						extendArticleData(dt);
						ColumnUtil.extendDocColumnData(dt, catalogs[j]);
						add(dt, this.writer);
					}
				}
				DataTable dt = new QueryBuilder("select id from bzc"
						+ this.type + "  where status=" + 30
						+ " and siteid=? and catalogid in (" + rela
						+ ") and backuptime>?", this.ft.getSiteID(),
						this.lastDate).executeDataTable();
				for (int i = 0; i < dt.getRowCount(); i++) {
					this.writer.deleteDocuments(new Term("ID", dt.getString(i,
							0)));
				}
				dt = new QueryBuilder("select id from zc" + this.type
						+ "  where status=" + 40
						+ " and siteid=? and modifytime>?",
						this.ft.getSiteID(), this.lastDate).executeDataTable();
				for (int i = 0; i < dt.getRowCount(); i++)
					this.writer.deleteDocuments(new Term("ID", dt.getString(i,
							0)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SearchResult search(SearchParameters sps) {
		try {
			long t = System.currentTimeMillis();
			IndexSearcher searcher = new IndexSearcher(FSDirectory
					.open(new File(Config.getContextRealPath()
							+ "WEB-INF/data/index/" + sps.getIndexID())), true);

			if (sps.getBooleanQuery().clauses().size() == 0) {
				MatchAllDocsQuery maq = new MatchAllDocsQuery();
				sps.addQuery(maq);
			}

			SimpleHTMLFormatter formatter = new SimpleHTMLFormatter(
					"<font color=red>", "</font>");
			Highlighter highlighter = new Highlighter(formatter,
					new QueryScorer(sps.getBooleanQuery()));
			int abstractLength = 150;
			highlighter.setTextFragmenter(new SimpleFragmenter(abstractLength));
			DataTable dt = new DataTable();

			int start = sps.getPageIndex() * sps.getPageSize();
			TopDocs docs = null;
			if (sps.getSort() != null)
				docs = searcher.search(sps.getBooleanQuery(), sps
						.getDateRangeFilter(), start + sps.getPageSize(), sps
						.getSort());
			else {
				docs = searcher.search(sps.getBooleanQuery(), sps
						.getDateRangeFilter(), start + sps.getPageSize());
			}

			for (int i = start; (i < start + sps.getPageSize())
					&& (i < docs.scoreDocs.length); i++) {
				Document doc = searcher.doc(docs.scoreDocs[i].doc);
				if (i == start) {
					Object[] fields = doc.getFields().toArray();
					for (int j = 0; j < fields.length; j++) {
						String name = ((Field) fields[j]).name();
						dt.insertColumn(name);
					}
				}
				String title = doc.get("TITLE");
				TokenStream tokenStream = new IKAnalyzer().tokenStream("TITLE",
						new StringReader(title));

				String tmp = highlighter.getBestFragment(tokenStream, title);
				if (XString.isNotEmpty(tmp)) {
					title = tmp;
				}
				String content = doc.get("CONTENT");
				tokenStream = new IKAnalyzer().tokenStream("CONTENT",
						new StringReader(content));
				tmp = highlighter.getBestFragment(tokenStream, content);
				if (XString.isNotEmpty(tmp)) {
					content = tmp.trim();
				} else if (content.length() > abstractLength) {
					content = content.substring(0, abstractLength);
				}

				dt.insertRow(new String[dt.getColCount()]);
				for (int j = 0; j < dt.getColCount(); j++) {
					dt.set(i - start, j, doc.get(dt.getDataColumn(j)
							.getColumnName()));
				}
				dt.set(i - start, "TITLE", title);
				dt.set(i - start, "CONTENT", content);
			}
			searcher.close();

			SearchResult r = new SearchResult();
			r.Data = dt;
			r.Total = docs.totalHits;
			r.UsedTime = NumberUtil.round(
					(System.currentTimeMillis() - t) * 1.0D / 1000.0D, 3);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

/*
 * com.xdarkness.search.ResourceIndexer JD-Core Version: 0.6.0
 */