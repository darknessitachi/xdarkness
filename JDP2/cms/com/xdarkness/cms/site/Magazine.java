package com.xdarkness.cms.site;

import java.util.Date;
import java.util.List;

import com.xdarkness.cms.datachannel.Publisher;
import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.schema.ZCCatalogSchema;
import com.xdarkness.schema.ZCMagazineCatalogRelaSchema;
import com.xdarkness.schema.ZCMagazineIssueSchema;
import com.xdarkness.schema.ZCMagazineSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.TreeAction;
import com.xdarkness.framework.jaf.controls.TreeItem;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;

public class Magazine extends Page {
	public static void treeDataBind(TreeAction ta) {
		String siteID = ApplicationPage.getCurrentSiteID()+"";
		int catalogType = 3;
		String parentLevel = (String) ta.getParams().get("ParentLevel");
		String parentID = (String) ta.getParams().get("ParentID");
		DataTable dt = null;
		if (ta.isLazyLoad()) {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel>? and innerCode like ? order by orderflag,innercode");

			qb.add(catalogType);
			qb.add(siteID);
			qb.add(parentLevel);
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			dt = qb.executeDataTable();
		} else {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel-1 <=? order by orderflag,innercode");

			qb.add(catalogType);
			qb.add(siteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
		}

		String siteName = "期刊库";

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
			if (item.getLevel() == 1) {
				item.setIcon("Icons/icon008a19.gif");
			}
			if (item.getLevel() == 2)
				item.setIcon("Icons/icon_magazaine.gif");
		}
	}

	public static Mapx initDialog(Mapx params) {
		Object o1 = params.get("MagazineID");
		if (o1 != null) {
			long ID = Long.parseLong(o1.toString());
			ZCMagazineSchema magazine = new ZCMagazineSchema();
			magazine.setID(ID);
			if (magazine.fill()) {
				Mapx map = magazine.toMapx();
				String imagePath = magazine.getCoverImage();
				if (imagePath == null)
					imagePath = "../Images/nopicture.jpg";
				else {
					imagePath = Config.getContextPath()
							+ Config.getValue("UploadDir") + "/"
							+ SiteUtil.getAlias(magazine.getSiteID()) + "/"
							+ imagePath;
				}

				map.put("PicSrc", imagePath);
				return map;
			}
			return null;
		}
		params.put("SiteID", ApplicationPage.getCurrentSiteID());
		params.put("PicSrc", "../Images/nocover.jpg");
		params.put("CoverTemplate", "/template/magazine.html");
		return params;
	}

	public static Mapx initIssue(Mapx params) {
		String sql = "select concat(year,'年',periodNum,'期') as Name,ID from zcmagazineissue where MagazineID=(select min(id) from zcmagazine where siteid="
				+ ApplicationPage.getCurrentSiteID() + ") order by id desc";
		DataTable dt1 = new QueryBuilder(sql).executeDataTable();
		params.put("optionIssue", HtmlUtil.dataTableToOptions(dt1));
		return params;
	}

	public static Mapx init(Mapx params) {
		Object o1 = params.get("MagazineID");
		if (o1 != null) {
			long ID = Long.parseLong(o1.toString());
			ZCMagazineSchema magazine = new ZCMagazineSchema();
			magazine.setID(ID);
			magazine.fill();

			Mapx map = magazine.toMapx();
			return map;
		}
		return params;
	}

	public void add() {
		Transaction trans = new Transaction();
		Catalog catalog = new Catalog();
		ZCCatalogSchema scheme = catalog.add(this.request, trans);
		long catalogID = scheme.getID();

		this.request.put("IndexTemplate", $V("CoverTemplate"));
		ZCMagazineSchema magazine = new ZCMagazineSchema();
		magazine.setID(catalogID);
		magazine.setValue(this.request);
		String imageid = $V("CoverImage");
		DataTable imagedt = new QueryBuilder(
				"select path,filename from zcimage where id=?", imageid)
				.executeDataTable();
		if (imagedt.getRowCount() > 0) {
			magazine.setCoverImage(imagedt.getString(0, "path") + "1_"
					+ imagedt.getString(0, "filename"));
		}
		magazine.setAddTime(new Date());
		magazine.setAddUser(User.getUserName());

		trans.add(magazine, OperateType.INSERT);
		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("插入数据发生错误!");
		}
	}

	public void edit() {
		Transaction trans = new Transaction();
		ZCMagazineSchema magazine = new ZCMagazineSchema();
		magazine.setID(Long.parseLong($V("MagazineID")));
		if (!magazine.fill()) {
			this.response.setStatus(0);
			this.response.setMessage("修改数据发生错误!");
			return;
		}
		magazine.setValue(this.request);
		String imageid = $V("CoverImage");
		DataTable imagedt = new QueryBuilder(
				"select path,filename from zcimage where id=?", imageid)
				.executeDataTable();
		if (imagedt.getRowCount() > 0) {
			magazine.setCoverImage(imagedt.getString(0, "path") + "1_"
					+ imagedt.getString(0, "filename"));
		}
		magazine.setModifyTime(new Date());
		magazine.setModifyUser(User.getUserName());
		trans.add(magazine, OperateType.UPDATE);

		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(Long.parseLong($V("MagazineID")));
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

		ZCMagazineSchema magazine = new ZCMagazineSchema();
		magazine.setID(Long.parseLong(ID));
		magazine.fill();

		trans.add(magazine, OperateType.DELETE_AND_BACKUP);

		trans.add(new ZCMagazineIssueSchema().query(new QueryBuilder(
				" where magazineID =?", ID)), OperateType.DELETE_AND_BACKUP);

		trans.add(new ZCMagazineCatalogRelaSchema().query(new QueryBuilder(
				" where magazineID =?", ID)), OperateType.DELETE_AND_BACKUP);

		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("删除期刊失败");
		}
	}

	public void publish() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
			return;
		}
		if ((ids.indexOf("\"") >= 0) || (ids.indexOf("'") >= 0)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}

		String[] arr = ids.split(",");

		for (int i = 0; i < arr.length; i++) {
			String temp = arr[i];
			long catalogID = Long.parseLong(temp);
			long id = publishTask(catalogID, true, true);
			this.response.setStatus(1);
			$S("TaskID", id);
		}
	}

	private long publishTask(final long catalogID, final boolean child, final boolean detail) {
		LongTimeTask ltt = new LongTimeTask() {

			public void execute() {
				Publisher p = new Publisher();
				p.publishCatalog(catalogID, child,
						detail, this);
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}
}
