package com.xdarkness.cms.webservice;

import java.sql.SQLException;

import com.xdarkness.cms.api.ArticleAPI;
import com.xdarkness.cms.api.CatalogAPI;
import com.xdarkness.cms.api.MemberAPI;
import com.xdarkness.cms.api.UserAPI;
import com.xdarkness.cms.datachannel.Publisher;
import com.xdarkness.schema.ZCArticleSchema;
import com.xdarkness.schema.ZCArticleSet;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class CmsServiceImpl implements CmsService {
	public long addArticle(long catalogID, String title, String author,
			String content, String publishDate) {
		ArticleAPI p = new ArticleAPI();
		ZCArticleSchema article = new ZCArticleSchema();
		article.setCatalogID(catalogID);
		article.setTitle(title);
		article.setAuthor(author);
		article.setContent(content);
		article.setPublishDate(DateUtil.parse(publishDate));
		p.setSchema(article);
		if (p.insert() > 0L) {
			return article.getID();
		}
		return -1L;
	}

	public long addCatalog(long parentID, String name, int type, String alias) {
		Mapx params = new Mapx();
		params.put("ParentID", parentID);
		params.put("Name", name);
		params.put("Type", type);
		params.put("Alias", alias);
		CatalogAPI c = new CatalogAPI();
		c.setParams(params);

		if (c.insert() > 0L)
			return 1L;
		return -1L;
	}

	public long addUser(String userName, String realName, String password,
			String departID, String email) {
		Mapx params = new Mapx();
		params.put("Username", userName);
		params.put("RealName", realName);
		params.put("Password", password);
		params.put("Email", email);
		params.put("BranchInnerCode", departID);
		if (XString.isEmpty(departID)) {
			params.put("BranchInnerCode", "0001");
		}
		UserAPI u = new UserAPI();
		u.setParams(params);
		return u.insert();
	}

	public boolean deleteArticle(long articleID) {
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(articleID);
		if (article.fill()) {
			ArticleAPI p = new ArticleAPI();
			p.setSchema(article);
			return p.delete();
		}
		return false;
	}

	public boolean deleteCatalog(long ID) {
		Mapx params = new Mapx();
		params.put("CatalogID", ID);
		CatalogAPI c = new CatalogAPI();
		c.setParams(params);
		return c.delete();
	}

	public boolean deleteUser(String userName) {
		Mapx params = new Mapx();
		params.put("Username", userName);
		UserAPI u = new UserAPI();
		u.setParams(params);
		return u.delete();
	}

	public boolean editArticle(long articleID, String title, String author,
			String content, String publishDate) {
		Mapx params = new Mapx();
		params.put("DocID", articleID);
		params.put("Title", title);
		params.put("Author", author);
		params.put("Cotent", content);
		params.put("PublishDate", publishDate);
		ArticleAPI a = new ArticleAPI();
		a.setParams(params);
		return a.update();
	}

	public boolean editCatalog(long ID, String name, String alias) {
		Mapx params = new Mapx();
		params.put("CatalogID", ID);
		params.put("Name", name);
		params.put("Alias", alias);
		CatalogAPI c = new CatalogAPI();
		c.setParams(params);
		return c.update();
	}

	public boolean editUser(String userName, String realName, String password,
			String departID, String email) {
		Mapx params = new Mapx();
		params.put("Username", userName);
		params.put("RealName", realName);
		params.put("Password", password);
		params.put("BranchInnerCode", departID);
		if (XString.isEmpty(departID)) {
			params.put("BranchInnerCode", "0001");
		}
		params.put("Email", email);
		UserAPI u = new UserAPI();
		u.setParams(params);
		return u.update();
	}

	public boolean publishArticle(long articleID) {
		ZCArticleSchema article = new ZCArticleSchema();
		ZCArticleSet set = article
				.query(new QueryBuilder(
						"where id =? or id in(select id from zcarticle where refersourceid=? )",
						articleID, articleID));
		if (set.size() > 0) {
			Publisher p = new Publisher();
			try {
				return p.publishArticle(set);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean publishCatalog(final long ID) {
		try {
			LongTimeTask ltt = new LongTimeTask() {
				public void execute() {
					Publisher p = new Publisher();
					p.publishCatalog(ID, true, true, this);
					setPercent(100);
				}
			};
			ltt.start();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public long addMember(String UserName, String RealName, String PassWord,
			String Email) {
		Mapx params = new Mapx();
		params.put("UserName", UserName);
		params.put("PassWord", PassWord);
		params.put("RealName", RealName);
		params.put("Email", Email);
		MemberAPI m = new MemberAPI();
		m.setParams(params);
		return m.insert();
	}

	public boolean deleteMember(String UserName) {
		Mapx params = new Mapx();
		params.put("UserName", UserName);
		MemberAPI m = new MemberAPI();
		m.setParams(params);
		return m.delete();
	}

	public boolean editMember(String UserName, String RealName,
			String PassWord, String Email) {
		Mapx params = new Mapx();
		params.put("UserName", UserName);
		params.put("RealName", RealName);
		params.put("PassWord", PassWord);
		params.put("Email", Email);
		MemberAPI m = new MemberAPI();
		m.setParams(params);
		return m.update();
	}
}

/*
 * com.xdarkness.cms.webservice.CmsServiceImpl JD-Core Version: 0.6.0
 */