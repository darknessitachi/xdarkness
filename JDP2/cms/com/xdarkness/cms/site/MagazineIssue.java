package com.xdarkness.cms.site;

import java.util.Date;

import com.xdarkness.cms.document.Article;
import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.schema.ZCCatalogSchema;
import com.xdarkness.schema.ZCCatalogSet;
import com.xdarkness.schema.ZCMagazineIssueSchema;
import com.xdarkness.schema.ZCMagazineIssueSet;
import com.xdarkness.schema.ZCMagazineSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class MagazineIssue extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		long magazineID = Long.parseLong(dga.getParam("MagazineID"));
		QueryBuilder qb = new QueryBuilder(
				"select ID,MagazineID,year,PeriodNum,CoverImage,Status,Memo,publishDate as pubDate,addtime from ZCMagazineIssue where magazineID=? order by ID desc",
				magazineID);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		if ((dt != null) && (dt.getRowCount() > 0)) {
			dt.decodeColumn("Status", Article.STATUS_MAP);
			dt.getDataColumn("pubDate").setDateFormat("yy-MM-dd");
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	public static Mapx init(Mapx params) {
		return params;
	}

	public static Mapx initDialog(Mapx params) {
		String magazineIssueID = params.getString("ID");
		String magazineID = params.getString("MagazineID");
		String coverImage = "upload/Image/nopicture.jpg";
		if (XString.isNotEmpty(magazineIssueID)) {
			ZCMagazineIssueSchema magazineIssue = new ZCMagazineIssueSchema();
			magazineIssue.setID(magazineIssueID);
			magazineIssue.fill();
			params = magazineIssue.toMapx();
			coverImage = magazineIssue.getCoverImage();
			params.put("PicSrc", Config.getContextPath()
					+ Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/"
					+ coverImage);
		} else {
			params.put("SiteID", ApplicationPage.getCurrentSiteID());
			params.put("CoverImage", coverImage);
			params.put("PicSrc", Config.getContextPath()
					+ Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/"
					+ coverImage);
			if (XString.isNotEmpty(magazineID)) {
				DataTable catalogDt = new QueryBuilder(
						"select * from zccatalog where parentid=(select max(id) from zccatalog where parentid=?)",
						magazineID).executeDataTable();
				if (catalogDt != null) {
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < catalogDt.getRowCount(); i++) {
						sb
								.append("<input type=\"checkbox\" name=\"catalog\" value=\""
										+ catalogDt.getString(i, "id")
										+ "\" checked>"
										+ catalogDt.getString(i, "name"));
						if (i == 3) {
							sb.append("<br>");
						}
					}
					params.put("LastCatalog", sb.toString());
				}
			}
		}
		return params;
	}

	public void add() {
		long magazineID = Long.parseLong($V("MagazineID"));
		Transaction trans = new Transaction();

		Catalog catalog = new Catalog();
		this.request.put("Name", $V("Year") + "年第" + $V("PeriodNum") + "期");
		this.request.put("ParentID", magazineID);
		this.request.put("Alias", $V("Year") + $V("PeriodNum"));
		this.request.put("Type", "3");
		this.request.put("ImagePath", $V("CoverImage"));

		ZCCatalogSchema catalogSchema = catalog.add(this.request, trans);

		ZCMagazineIssueSchema issue = new ZCMagazineIssueSchema();
		issue.setID(catalogSchema.getID());
		issue.setValue(this.request);
		issue.setAddTime(new Date());
		issue.setAddUser(User.getUserName());
		issue.setStatus(1L);
		trans.add(issue, OperateType.INSERT);

		QueryBuilder qb = new QueryBuilder(
				"select DetailTemplate,ListTemplate from ZCCatalog where Type=? and ParentID=? order by ID desc",
				3L, issue.getMagazineID());
		DataTable dt = qb.executePagedDataTable(1, 0);
		if (dt.getRowCount() > 0) {
			if (XString.isNotEmpty(dt.getString(0, "ListTemplate")))
				catalogSchema.setListTemplate(dt.getString(0, "ListTemplate"));
			else {
				catalogSchema.setListTemplate("/template/list.html");
			}
			if (XString.isNotEmpty(dt.getString(0, "DetailTemplate")))
				catalogSchema.setDetailTemplate(dt.getString(0,
						"DetailTemplate"));
			else
				catalogSchema.setDetailTemplate("/template/detail.html");
		} else {
			catalogSchema.setListTemplate("/template/list.html");
			catalogSchema.setDetailTemplate("/template/detail.html");
		}
		ZCMagazineSchema magazine = new ZCMagazineSchema();
		magazine.setID(magazineID);
		if (magazine.fill()) {
			long totalissue = magazine.getTotal();
			String currentyear = "";
			String periodnum = "";
			String coverimage = "";
			if ((XString.isEmpty(magazine.getCurrentYear()))
					|| (Long.parseLong($V("Year")) > Long.parseLong(magazine
							.getCurrentYear()))) {
				currentyear = $V("Year");
				periodnum = $V("PeriodNum");
				coverimage = $V("CoverImage");
			} else {
				currentyear = magazine.getCurrentYear();
				coverimage = magazine.getCoverImage();
				periodnum = Long.parseLong($V("PeriodNum")) > Long
						.parseLong(magazine.getCurrentPeriodNum()) ? $V("PeriodNum")
						: magazine.getCurrentPeriodNum();
			}
			magazine.setTotal(totalissue + 1L);
			magazine.setCurrentYear(currentyear);
			magazine.setCurrentPeriodNum(periodnum);
			magazine.setCoverImage(coverimage);
			magazine.setModifyTime(new Date());
			magazine.setModifyUser(User.getUserName());
			trans.add(magazine, OperateType.UPDATE);
		}

		trans.add(new QueryBuilder(
				"update zccatalog set ImagePath=? where id=?",
				$V("CoverImage"), magazineID));

		if (trans.commit()) {
			CatalogUtil.update(catalogSchema.getID());
			if (XString.isNotEmpty($V("CatalogIDs"))) {
				if (!XString.checkID($V("CatalogIDs"))) {
					this.response.setStatus(0);
					this.response.setMessage("发生错误!");
					return;
				}
				ZCCatalogSchema catalogLastIssue = new ZCCatalogSchema();
				if (!XString.checkID($V("CatalogIDs"))) {
					return;
				}
				ZCCatalogSet set = catalogLastIssue.query(new QueryBuilder(
						"where id in(" + $V("CatalogIDs") + ")"));
				for (int i = 0; i < set.size(); i++) {
					catalog.add(catalogSchema, set.get(i), trans);
				}
				trans.commit();
			}
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("发生错误!");
		}
	}

	public void edit() {
		long magazineID = Long.parseLong($V("MagazineID"));
		Transaction trans = new Transaction();

		ZCMagazineIssueSchema issue = new ZCMagazineIssueSchema();
		issue.setID(Long.parseLong($V("ID")));
		if (!issue.fill()) {
			this.response.setStatus(0);
			this.response.setMessage("没有找到期号!");
			return;
		}
		issue.setValue(this.request);
		issue.setModifyTime(new Date());
		issue.setModifyUser(User.getUserName());
		issue.setStatus(1L);
		trans.add(issue, OperateType.UPDATE);

		ZCMagazineSchema magazine = new ZCMagazineSchema();
		magazine.setID(magazineID);
		if (magazine.fill()) {
			magazine.setTotal(magazine.getTotal() + 1L);
			magazine.setCurrentYear($V("Year"));
			magazine.setCurrentPeriodNum($V("PeriodNum"));
			magazine.setCoverImage($V("CoverImage"));
			magazine.setModifyTime(new Date());
			magazine.setModifyUser(User.getUserName());
			trans.add(magazine, OperateType.UPDATE);
		}
		trans.add(new QueryBuilder(
				"update zccatalog set ImagePath=? where id=?",
				$V("CoverImage"), magazineID));
		trans.add(new QueryBuilder(
				"update zccatalog set ImagePath=? where id=?",
				$V("CoverImage"), issue.getID()));

		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("发生错误!");
		}
	}

	public void del() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		if ((ids.indexOf("\"") >= 0) || (ids.indexOf("'") >= 0)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCMagazineIssueSchema MagazineIssue = new ZCMagazineIssueSchema();
		ZCMagazineIssueSet set = MagazineIssue.query(new QueryBuilder(
				"where id in (" + ids + ")"));
		trans.add(set, OperateType.DELETE_AND_BACKUP);

		String[] idArray = ids.split("\\,");
		for (int i = 0; i < idArray.length; i++) {
			Catalog.deleteCatalog(trans, Long.parseLong(idArray[i]));
		}

		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
		}
	}

	public void getPicSrc() {
		String ID = $V("PicID");
		String id = $V("ID");
		DataTable dt = new QueryBuilder(
				"select path,filename from zcimage where id=?", ID)
				.executeDataTable();
		if (dt.getRowCount() > 0) {
			this.response.put("picSrc", Config.getContextPath()
					+ Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/"
					+ dt.get(0, "path").toString() + "s_"
					+ dt.get(0, "filename").toString());
			this.response.put("CoverImage", dt.get(0, "path").toString() + "1_"
					+ dt.get(0, "filename").toString());
		}
		Transaction trans = new Transaction();
		ZCMagazineIssueSchema magazineIssue = new ZCMagazineIssueSchema();
		if (XString.isNotEmpty(id)) {
			magazineIssue.setID(id);
			magazineIssue.fill();
			magazineIssue.setValue(this.request);
			magazineIssue.setCoverImage((String) this.response
					.get("CoverImage"));
			trans.add(magazineIssue, OperateType.UPDATE);
			trans.commit();
		} else {
			return;
		}
	}
}

/*
 * com.xdarkness.cms.site.MagazineIssue JD-Core Version: 0.6.0
 */