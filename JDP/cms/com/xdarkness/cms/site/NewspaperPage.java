package com.xdarkness.cms.site;

import java.util.Date;

import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.schema.ZCPaperIssueSchema;
import com.xdarkness.schema.ZCPaperIssueSet;
import com.xdarkness.schema.ZCPaperPageSchema;
import com.xdarkness.schema.ZCPaperSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class NewspaperPage extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		long issueID = Long.parseLong(dga.getParam("CatalogID"));
		QueryBuilder qb = new QueryBuilder(
				"select a.ID,a.IssueID,a.pageNo,a.Title,a.PaperImage,(select concat(path,srcfilename) from zcimage where id=a.PaperImage) as Image,a.PDFFile,(select concat(path,srcfilename) from zcattachment where id=a.pdffile) as File,a.Memo,b.listtemplate,b.detailtemplate from ZCPaperPage a,zccatalog b where a.issueid=? and a.id = b.id order by a.pageno",
				issueID);
		dga.bindData(qb);
	}

	public static Mapx init(Mapx params) {
		return params;
	}

	public void add() {
		long issueID = Long.parseLong($V("IssueID"));
		Transaction trans = new Transaction();

		Catalog catalog = new Catalog();
		this.request.put("Name", "第" + $V("PageNo") + "版 " + $V("Title"));
		this.request.put("ParentID", issueID);
		this.request.put("Alias", $V("PageNo"));
		this.request.put("Type", "8");

		long catalogID = catalog.add(this.request, trans).getID();

		ZCPaperPageSchema page = new ZCPaperPageSchema();
		page.setID(catalogID);
		page.setValue(this.request);
		page.setAddTime(new Date());
		page.setAddUser(User.getUserName());
		trans.add(page, OperateType.INSERT);

		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("发生错误!");
		}
	}

	public void edit() {
		long PaperID = Long.parseLong($V("PaperID"));
		Transaction trans = new Transaction();

		ZCPaperIssueSchema issue = new ZCPaperIssueSchema();
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

		ZCPaperSchema Paper = new ZCPaperSchema();
		Paper.setID(PaperID);
		if (Paper.fill()) {
			Paper.setTotal(Paper.getTotal() + 1L);
			Paper.setCurrentYear($V("Year"));
			Paper.setCurrentPeriodNum($V("PeriodNum"));
			Paper.setCoverImage($V("CoverImage"));
			Paper.setModifyTime(new Date());
			Paper.setModifyUser(User.getUserName());
			trans.add(Paper, OperateType.UPDATE);
		}
		trans.add(new QueryBuilder("update zccatalog set imageID=? where id=?",
				$V("CoverImage"), PaperID));

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
		Transaction trans = new Transaction();
		ZCPaperIssueSchema paperIssue = new ZCPaperIssueSchema();
		ZCPaperIssueSet set = paperIssue.query(new QueryBuilder("where id in ("
				+ ids + ")"));
		trans.add(set, OperateType.DELETE_AND_BACKUP);

		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
		}
	}

	public void getPicSrc() {
		String ID = $V("PicID");
		DataTable dt = new QueryBuilder(
				"select path,filename from zcimage where id=?", ID)
				.executeDataTable();

		if (dt.getRowCount() > 0)
			this.response.put("picSrc", Config.getContextPath()
					+ Config.getValue("UploadDir")
					+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/"
					+ dt.get(0, "path").toString() + "s_"
					+ dt.get(0, "filename").toString());
		else
			this.response.put("picSrc", "../Images/nopicture.gif");
	}
}

/*
 * com.xdarkness.cms.site.NewspaperPage JD-Core Version: 0.6.0
 */