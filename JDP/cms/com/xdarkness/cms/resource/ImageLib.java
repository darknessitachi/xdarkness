package com.xdarkness.cms.resource;

import java.io.File;
import java.util.Date;

import com.xdarkness.cms.datachannel.Publisher;
import com.xdarkness.cms.pub.CMSCache;
import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.cms.site.Catalog;
import com.xdarkness.cms.site.CatalogShowConfig;
import com.xdarkness.platform.Priv;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.page.UserLogPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCCatalogSchema;
import com.xdarkness.schema.ZCCatalogSet;
import com.xdarkness.schema.ZCImageRelaSchema;
import com.xdarkness.schema.ZCImageRelaSet;
import com.xdarkness.schema.ZCImageSchema;
import com.xdarkness.schema.ZCImageSet;
import com.xdarkness.schema.ZCSiteSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.DataListAction;
import com.xdarkness.framework.jaf.controls.TreeAction;
import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataRow;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.ChineseSpelling;
import com.xdarkness.framework.util.Mapx;

public class ImageLib extends Page {
	public static Mapx initEditDialog(Mapx params) {
		String ID = params.getString("ID");
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(ID);
		catalog.fill();
		return catalog.toMapx();
	}

	public void setImageCover() {
		String ID = $V("ID");
		String imagePath = "";
		DataTable dt = new QueryBuilder(
				"select path,filename from zcimage where id=?", ID)
				.executeDataTable();
		if (dt.getRowCount() > 0) {
			imagePath = dt.get(0, "path").toString()
					+ dt.get(0, "filename").toString();
		}
		ZCImageSchema image = new ZCImageSchema();
		image.setID(Long.parseLong(ID));
		image.fill();
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(image.getCatalogID());
		catalog.fill();
		catalog.setImagePath(imagePath);
		if (catalog.update())
			this.response.setLogInfo(1, "设置专辑封面成功！");
		else
			this.response.setLogInfo(0, "设置专辑封面失败！");
	}

	public void setTopper() {
		String ID = $V("ID");
		ZCImageSchema image = new ZCImageSchema();
		image.setID(Long.parseLong(ID));
		image.fill();
		QueryBuilder qb = new QueryBuilder(
				"select max(OrderFlag) from ZCImage where CatalogID = ?", image
						.getCatalogID());
		image.setOrderFlag(qb.executeLong() + 1L);
		if (image.update())
			this.response.setLogInfo(1, "置顶成功！");
		else
			this.response.setLogInfo(0, "置顶失败！");
	}

	public void ImageLibEdit() {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setValue(this.request);
		catalog.fill();
		catalog.setValue(this.request);
		catalog.setAlias(XString.getChineseFirstAlpha(catalog.getName()));
		if (catalog.update()) {
			CatalogUtil.update(catalog.getID());
			this.response.setLogInfo(1, "修改图片分类成功！");
		} else {
			this.response.setLogInfo(0, "修改图片分类失败！");
		}
	}

	public void delLib() {
		String catalogID = $V("catalogID");
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(Long.parseLong(catalogID));
		if (!catalog.fill()) {
			this.response.setLogInfo(0, "没有图片分类！");
			return;
		}

		if ("N".equals(catalog.getProp4())) {
			this.response.setLogInfo(0, "不能删除该图片分类！");
			return;
		}

		ZCCatalogSet catalogSet = new ZCCatalogSchema().query(new QueryBuilder(
				"where InnerCode like ?", catalog.getInnerCode() + "%"));
		Transaction trans = new Transaction();
		ZCImageSet imageSet = new ZCImageSchema().query(new QueryBuilder(
				"where CatalogInnerCode like ?", catalog.getInnerCode() + "%"));
		for (int i = 0; i < imageSet.size(); i++) {
			ZCImageRelaSet ImageRelaSet = new ZCImageRelaSchema()
					.query(new QueryBuilder(" where id = ?", imageSet.get(i)
							.getID()));
			trans.add(ImageRelaSet, OperateType.DELETE_AND_BACKUP);
		}

		File file = new File(Config.getContextRealPath()
				+ Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID())
				+ "/upload/Image/" + CatalogUtil.getPath(catalog.getID()));
		FileUtil.delete(file);
		trans.add(imageSet, OperateType.DELETE_AND_BACKUP);
		trans.add(catalogSet, OperateType.DELETE_AND_BACKUP);
		if (trans.commit()) {
			CMSCache.removeCatalogSet(catalogSet);
			this.response.setLogInfo(1, "删除图片分类成功！");

			Publisher p = new Publisher();
			p.deleteFileTask(imageSet);
		} else {
			this.response.setLogInfo(0, "删除图片分类失败！");
		}
	}

	public static void treeDataBind(TreeAction ta) {
		String SiteID = ApplicationPage.getCurrentSiteID()+"";
		DataTable dt = null;
		Mapx params = ta.getParams();
		String parentLevel = (String) params.get("ParentLevel");
		String parentID = (String) params.get("ParentID");
		if (XString.isEmpty(parentID)) {
			parentID = "0";
		}

		String IDs = ta.getParam("IDs");
		if (XString.isEmpty(IDs)) {
			IDs = ta.getParam("Cookie.Resource.LastImageLib");
		}
		String[] codes = Catalog.getSelectedCatalogList(IDs, CatalogShowConfig
				.getImageLibShowLevel());

		if (ta.isLazyLoad()) {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode from ZCCatalog Where Type =? and SiteID =? and TreeLevel>? and innerCode like ?");
			qb.add(4);
			qb.add(SiteID);
			qb.add(parentLevel);
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			if (!CatalogShowConfig.isImageLibLoadAllChild()) {
				qb
						.append(" and TreeLevel<?", Integer
								.parseInt(parentLevel) + 3);
				ta.setExpand(false);
			} else {
				ta.setExpand(true);
			}
			qb.append(" order by orderflag,innercode");
			dt = qb.executeDataTable();
		} else {
			ta.setLevel(CatalogShowConfig.getImageLibShowLevel());
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type=? and SiteID=? and TreeLevel-1<=? order by orderflag,innercode");
			qb.add(4);
			qb.add(SiteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
			Catalog.prepareSelectedCatalogData(dt, codes, 4, SiteID, ta
					.getLevel());
		}
		ta.setRootText("图片库");
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return Priv.getPriv(User.getUserName(), "image", dr
						.getString("InnerCode"), "image_browse");
			}
		});
		ta.bindData(dt);
		Catalog.addSelectedBranches(ta, codes);
	}

	public static void dg1DataList(DataListAction dla) {
		String CatalogID = dla.getParam("CatalogID");
		if (XString.isEmpty(CatalogID)) {
			dla.bindData(new DataTable());
			return;
		}
		String Name = dla.getParam("Name");
		String StartDate = dla.getParam("StartDate");
		String EndDate = dla.getParam("EndDate");
		QueryBuilder qb = new QueryBuilder("select * from ZCImage where 1 = 1");
		if ("0".equals(CatalogID))
			qb.append(" and SiteID = ?", ApplicationPage.getCurrentSiteID());
		else {
			qb.append(" and CatalogID = ?", CatalogID);
		}
		if (XString.isNotEmpty(Name)) {
			qb.append(" and Name like ?", "%" + Name.trim() + "%");
		}
		if (XString.isNotEmpty(StartDate)) {
			qb.append(" and addtime >= ? ", StartDate);
		}
		if (XString.isNotEmpty(EndDate)) {
			qb.append(" and addtime <= ? ", EndDate);
		}
		qb.append(" order by OrderFlag desc,ID desc");
		dla.setTotal(qb);
		String alias = Config.getContextPath() + Config.getValue("UploadDir")
				+ "/" + SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/";
		alias = alias.replaceAll("///", "/");
		alias = alias.replaceAll("//", "/");
		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla
				.getPageIndex());
		dt.insertColumn("Alias", alias);
		dla.bindData(dt);
	}

	public static void dg1DataListBrowse(DataListAction dla) {
		String Search = dla.getParam("Search");
		if ((Search == null) || ("".equals(Search))) {
			dla.bindData(new DataTable());
			return;
		}
		String alias = Config.getContextPath() + Config.getValue("UploadDir")
				+ "/" + SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/";
		alias = alias.replaceAll("///", "/");
		alias = alias.replaceAll("//", "/");
		String CatalogID = dla.getParam("_CatalogID");
		String Name = dla.getParam("Name");
		String StartDate = dla.getParam("StartDate");
		String EndDate = dla.getParam("EndDate");
		String Info = dla.getParam("Info");
		QueryBuilder qb = new QueryBuilder("select * from ZCImage");
		if ((XString.isNotEmpty(CatalogID)) && (!"null".equals(CatalogID)))
			qb.append(" where CatalogID = ?", CatalogID);
		else {
			qb.append(" where SiteID = ?", ApplicationPage.getCurrentSiteID());
		}
		if (XString.isNotEmpty(Name)) {
			qb.append(" and Name like ?", "%" + Name.trim() + "%");
		}
		if (XString.isNotEmpty(Info)) {
			qb.append(" and Info like ?", "%" + Info.trim() + "%");
		}
		if (XString.isNotEmpty(StartDate)) {
			qb.append(" and addtime >= ? ", StartDate);
		}
		if (XString.isNotEmpty(EndDate)) {
			qb.append(" and addtime <= ? ", EndDate);
		}
		qb.append(" order by ID desc");
		dla.setTotal(qb);
		DataTable dt = qb.executeDataTable();
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				if (Priv.getPriv(User.getUserName(), "site", ApplicationPage
						.getCurrentSiteID()+"", "site_manage")) {
					return true;
				}
				DataRow dr = (DataRow) obj;
				String CatalogID = dr.getString("CatalogID");
				return Priv.getPriv(User.getUserName(), "image", CatalogUtil
						.getInnerCode(CatalogID), "image_browse");
			}
		});
		DataTable newdt = new DataTable(dt.getDataColumns(), null);
		for (int i = dla.getPageIndex() * dla.getPageSize(); (i < dt
				.getRowCount())
				&& (i < (dla.getPageIndex() + 1) * dla.getPageSize());) {
			newdt.insertRow(dt.getDataRow(i));

			i++;
		}

		String SelectType = dla.getParam("SelectType");
		if ((SelectType == null) || ("".equals(SelectType))) {
			SelectType = "checkbox";
		}
		newdt.insertColumn("alias", alias);
		newdt.insertColumn("selecttype", SelectType);
		dla.bindData(newdt);
	}

	public void add() {
		String name = $V("Name");
		String parentID = $V("ParentID");
		String DT = $V("DetailTemplate");
		String LT = $V("ListTemplate");
		String IT = $V("IndexTemplate");
		Transaction trans = new Transaction();
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		ZCCatalogSchema pCatalog = new ZCCatalogSchema();

		catalog.setID(NoUtil.getMaxID("CatalogID"));
		catalog.setSiteID(ApplicationPage.getCurrentSiteID());

		if ((parentID.equals("0")) || (XString.isEmpty(parentID))) {
			catalog.setParentID(0L);
			catalog.setTreeLevel(1L);
			ZCSiteSchema site = new ZCSiteSchema();
			site.setID(catalog.getSiteID());
			site.fill();
			site.setImageLibCount(site.getImageLibCount() + 1L);
			trans.add(site, OperateType.UPDATE);
		} else {
			catalog.setParentID(Long.parseLong(parentID));
			pCatalog.setID(catalog.getParentID());
			pCatalog.fill();
			catalog.setTreeLevel(pCatalog.getTreeLevel() + 1L);

			pCatalog.setChildCount(pCatalog.getChildCount() + 1L);
			trans.add(pCatalog, OperateType.UPDATE);
		}

		String innerCode = CatalogUtil.createCatalogInnerCode(pCatalog
				.getInnerCode());
		catalog.setInnerCode(innerCode);

		catalog.setName(name);
		catalog.setURL("");
		String alias = ChineseSpelling.getFirstAlpha(name).toLowerCase();
		String existsName = Catalog.checkAliasExists(alias, catalog
				.getParentID());
		if (XString.isNotEmpty(existsName)) {
			alias = alias + NoUtil.getMaxID("AliasNo");
		}
		catalog.setAlias(alias);
		catalog.setType(4L);
		catalog.setIndexTemplate(IT);
		catalog.setListTemplate(LT);
		catalog.setListNameRule("");
		catalog.setDetailTemplate(DT);
		catalog.setDetailNameRule("");
		catalog.setChildCount(0L);
		catalog.setIsLeaf(1L);
		catalog.setTotal(0L);
		catalog.setOrderFlag(Catalog.getCatalogOrderFlag(parentID, catalog
				.getType()));
		catalog.setLogo("");
		catalog.setListPageSize(10L);
		catalog.setListPage(-1L);
		catalog.setPublishFlag("Y");
		catalog.setHitCount(0L);
		catalog.setMeta_Keywords("");
		catalog.setMeta_Description("");
		catalog.setOrderColumn("");
		catalog.setAddUser(User.getUserName());
		catalog.setAddTime(new Date());

		trans.add(catalog, OperateType.INSERT);

		Catalog.initCatalogConfig(catalog, trans);

		if (trans.commit()) {
			UserLogPage.log("Resource", "AddTypeImage", "新建图片分类:"
					+ catalog.getName() + "成功", this.request.getClientIP());
			this.response.setLogInfo(1, "新建图片分类成功!");
			CatalogUtil.update(catalog.getID());
		} else {
			UserLogPage.log("Resource", "AddTypeImage", "新建图片分类:"
					+ catalog.getName() + "失败", this.request.getClientIP());
			this.response.setLogInfo(0, "新建图片分类失败!");
		}
	}

	public void publish() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			UserLogPage.log("Resource", "AddTypeImage", "发布图片失败", this.request
					.getClientIP());
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
			return;
		}
		ZCImageSchema image = new ZCImageSchema();
		ZCImageSet set = image.query(new QueryBuilder("where id in(" + ids
				+ ")"));

		this.response.setStatus(1);
		Publisher p = new Publisher();
		long id = p.publishSetTask("Image", set);
		$S("TaskID", id);
	}
}

/*
 * com.xdarkness.cms.resource.ImageLib JD-Core Version: 0.6.0
 */