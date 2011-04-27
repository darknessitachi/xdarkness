package com.xdarkness.cms.site;

import java.util.Date;
import java.util.List;

import com.xdarkness.cms.pub.PubFun;
import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.schema.ZCCatalogSchema;
import com.xdarkness.schema.ZCPaperIssueSchema;
import com.xdarkness.schema.ZCPaperSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.TreeAction;
import com.xdarkness.framework.jaf.controls.TreeItem;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;

public class Newspaper extends Page {
	public static void treeDataBind(TreeAction ta) {
		Object obj = ta.getParams().get("SiteID");
		String siteID = ApplicationPage.getCurrentSiteID()+"";
		int catalogType = 8;
		DataTable dt = null;

		QueryBuilder qb = new QueryBuilder(
				"select ID,ParentID,TreeLevel,Name from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel<3",
				catalogType, siteID);
		dt = qb.executeDataTable();

		String siteName = "报纸库";
		String inputType = (String) ta.getParams().get("Type");
		if ("3".equals(inputType))
			ta
					.setRootText("<input type='radio' name=CatalogID id='_site' value='"
							+ siteID
							+ "'><label for='_site'>"
							+ siteName
							+ "</label>");
		else if ("2".equals(inputType))
			ta
					.setRootText("<input type='CheckBox' name=CatalogID id='_site' value='"
							+ siteID
							+ "' onclick='selectAll()'><label for='_site'>"
							+ siteName + "</label>");
		else {
			ta.setRootText(siteName);
		}

		ta.bindData(dt);
		List items = ta.getItemList();
		for (int i = 0; i < items.size(); i++) {
			TreeItem item = (TreeItem) items.get(i);
			if (item.getLevel() == 1)
				item.setIcon("Icons/icon008a2.gif");
		}
	}

	public static void docTreeDataBind(TreeAction ta) {
		String siteID = ApplicationPage.getCurrentSiteID()+"";
		int catalogType = 8;
		DataTable dt = null;

		dt = new QueryBuilder(
				"select ID,ParentID,TreeLevel,Name from ZCCatalog Where Type = ? and SiteID = ?",
				catalogType, siteID).executeDataTable();
		String siteName = "报纸库";

		String inputType = (String) ta.getParams().get("Type");
		if ("3".equals(inputType))
			ta
					.setRootText("<input type='radio' name=CatalogID id='_site' value='"
							+ siteID
							+ "'><label for='_site'>"
							+ siteName
							+ "</label>");
		else if ("2".equals(inputType))
			ta
					.setRootText("<input type='CheckBox' name=CatalogID id='_site' value='"
							+ siteID
							+ "' onclick='selectAll()'><label for='_site'>"
							+ siteName + "</label>");
		else {
			ta.setRootText(siteName);
		}

		ta.bindData(dt);
		List items = ta.getItemList();
		for (int i = 0; i < items.size(); i++) {
			TreeItem item = (TreeItem) items.get(i);
			if (item.getLevel() == 1)
				item.setIcon("Icons/icon008a1.gif");
			else if (item.getLevel() == 2)
				item.setIcon("Icons/icon018a11.gif");
			else if (item.getLevel() == 3)
				item.setIcon("Icons/icon5.gif");
		}
	}

	public static Mapx initDialog(Mapx params) {
		String sql = "select CodeName,CodeValue from ZDCode where ParentCode !='System' and CodeType ='Period' Order by CodeOrder";
		DataTable dt1 = new QueryBuilder(sql).executeDataTable();

		Object o1 = params.get("NewspaperID");
		if (o1 != null) {
			long ID = Long.parseLong(o1.toString());
			ZCPaperSchema paper = new ZCPaperSchema();
			paper.setID(ID);
			if (paper.fill()) {
				Mapx map = paper.toMapx();
				String imagePath = PubFun.getImagePath((String) map
						.get("CoverImage"));
				if (imagePath == null)
					imagePath = "../Images/nopicture.gif";
				else {
					imagePath = Config.getContextPath()
							+ Config.getValue("UploadDir")
							+ SiteUtil.getAlias(paper.getSiteID()) + "/"
							+ imagePath;
				}

				map.put("PicSrc", imagePath);
				map.put("optionPeriod", HtmlUtil.dataTableToOptions(dt1));
				return map;
			}
			return null;
		}
		params.put("SiteID", ApplicationPage.getCurrentSiteID());
		params.put("PicSrc", "../Images/nopicture.gif");
		params.put("CoverTemplate", "/template/Paper.html");
		params.put("optionPeriod", HtmlUtil.dataTableToOptions(dt1));
		return params;
	}

	public static Mapx initIssue(Mapx params) {
		String sql = "select concat(year,'年',periodNum,'期') as Name,ID from zcPaperissue where PaperID=(select min(id) from zcPaper where siteid="
				+ ApplicationPage.getCurrentSiteID() + ") order by id desc";
		DataTable dt1 = new QueryBuilder(sql).executeDataTable();
		params.put("optionIssue", HtmlUtil.dataTableToOptions(dt1));
		return params;
	}

	public static Mapx init(Mapx params) {
		Object o1 = params.get("NewspaperID");
		System.out.println(o1);
		if (o1 != null) {
			long ID = Long.parseLong(o1.toString());
			ZCPaperSchema Paper = new ZCPaperSchema();
			Paper.setID(ID);
			if (Paper.fill()) {
				Mapx map = Paper.toMapx();
				return map;
			}
			return params;
		}
		return params;
	}

	public void add() {
		Transaction trans = new Transaction();
		Catalog catalog = new Catalog();
		long catalogID = catalog.add(this.request, trans).getID();

		ZCPaperSchema Paper = new ZCPaperSchema();
		Paper.setID(catalogID);
		Paper.setValue(this.request);
		Paper.setAddTime(new Date());
		Paper.setAddUser(User.getUserName());
		trans.add(Paper, OperateType.INSERT);
		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("插入数据发生错误!");
		}
	}

	public void edit() {
		Transaction trans = new Transaction();
		ZCPaperSchema Paper = new ZCPaperSchema();
		Paper.setID(Long.parseLong($V("NewspaperID")));
		if (!Paper.fill()) {
			this.response.setStatus(0);
			this.response.setMessage("修改数据发生错误!");
			return;
		}
		Paper.setValue(this.request);
		Paper.setModifyTime(new Date());
		Paper.setModifyUser(User.getUserName());
		trans.add(Paper, OperateType.UPDATE);

		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(Long.parseLong($V("NewspaperID")));
		catalog.fill();
		catalog.setValue(this.request);
		catalog.setIndexTemplate($V("CoverTemplate"));
		catalog.setModifyUser(User.getUserName());
		catalog.setModifyTime(new Date());
		trans.add(catalog, OperateType.UPDATE);

		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("修改数据发生错误!");
		}
	}

	public void del() {
		Transaction trans = new Transaction();
		String ID = $V("CatalogID");

		Catalog.deleteCatalog(trans, Long.parseLong(ID));

		ZCPaperSchema Paper = new ZCPaperSchema();
		Paper.setID(Long.parseLong(ID));
		Paper.fill();

		trans.add(Paper, OperateType.DELETE_AND_BACKUP);

		trans.add(new ZCPaperIssueSchema().query(new QueryBuilder(
				" where PaperID =?", ID)), OperateType.DELETE_AND_BACKUP);

		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("删除期刊失败");
		}
	}
}

/*
 * com.xdarkness.cms.site.Newspaper JD-Core Version: 0.6.0
 */