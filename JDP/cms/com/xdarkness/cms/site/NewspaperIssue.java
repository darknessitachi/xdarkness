package com.xdarkness.cms.site;

import java.util.Date;

import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.schema.ZCCatalogSchema;
import com.xdarkness.schema.ZCCatalogSet;
import com.xdarkness.schema.ZCPaperIssueSchema;
import com.xdarkness.schema.ZCPaperIssueSet;
import com.xdarkness.schema.ZCPaperSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class NewspaperIssue extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		long PaperID = Long.parseLong(dga.getParam("NewspaperID"));
		QueryBuilder qb = new QueryBuilder(
				"select ID,PaperID,year,PeriodNum,CoverImage,Status,Memo,publishDate as pubDate,addtime from ZCPaperIssue where PaperID=? order by ID desc",
				PaperID);
		dga.bindData(qb);
	}

	public static Mapx init(Mapx params) {
		return params;
	}

	public void add() {
		long paperID = Long.parseLong($V("NewspaperID"));
		Transaction trans = new Transaction();
		Catalog catalog = new Catalog();
		this.request.put("Name", $V("PublishDate") + "(" + $V("Year") + "年"
				+ $V("PeriodNum") + "期)");
		this.request.put("ParentID", paperID);
		this.request.put("Alias", $V("Year") + $V("PeriodNum"));
		this.request.put("Type", "8");

		long catalogID = catalog.add(this.request, trans).getID();

		ZCPaperIssueSchema issue = new ZCPaperIssueSchema();
		issue.setPaperID(paperID);
		issue.setID(catalogID);
		issue.setValue(this.request);
		issue.setAddTime(new Date());
		issue.setAddUser(User.getUserName());
		issue.setStatus(1L);
		trans.add(issue, OperateType.INSERT);

		ZCPaperSchema paper = new ZCPaperSchema();
		paper.setID(paperID);
		if (paper.fill()) {
			paper.setTotal(paper.getTotal() + 1L);
			paper.setCurrentYear($V("Year"));
			paper.setCurrentPeriodNum($V("PeriodNum"));
			paper.setCoverImage($V("CoverImage"));
			paper.setModifyTime(new Date());
			paper.setModifyUser(User.getUserName());
			trans.add(paper, OperateType.UPDATE);
		}
		System.out.println($V("CoverImage"));
		trans.add(new QueryBuilder(
				"update zccatalog set imagePath=? where id=?",
				$V("CoverImage"), paperID));

		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("发生错误!");
		}
	}

	public void edit() {
		long paperID = Long.parseLong($V("PaperID"));
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
		Paper.setID(paperID);
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
				$V("CoverImage"), paperID));

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

		ZCCatalogSchema catalog = new ZCCatalogSchema();
		ZCCatalogSet catalogSet = catalog.query(new QueryBuilder(
				"where id in (" + ids + ") or parentid in (" + ids + ")"));
		trans.add(catalogSet, OperateType.DELETE_AND_BACKUP);

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
 * com.xdarkness.cms.site.NewspaperIssue JD-Core Version: 0.6.0
 */