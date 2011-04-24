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
import com.xdarkness.schema.ZCSiteSchema;
import com.xdarkness.schema.ZCVideoRelaSchema;
import com.xdarkness.schema.ZCVideoRelaSet;
import com.xdarkness.schema.ZCVideoSchema;
import com.xdarkness.schema.ZCVideoSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.DataListAction;
import com.xdarkness.framework.jaf.controls.TreeAction;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataRow;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.ChineseSpelling;
import com.xdarkness.framework.util.Mapx;

public class VideoLib extends Page {
	public static Mapx initEditDialog(Mapx params) {
		long ID = Long.parseLong(params.get("ID").toString());
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(ID);
		catalog.fill();
		return catalog.toMapx();
	}

	public void setVideoCover() {
		String ID = this.request.get("ID").toString();
		String imagePath = "";
		DataTable dt = new QueryBuilder(
				"select path,filename from ZCVideo where id=?", ID)
				.executeDataTable();
		if (dt.getRowCount() > 0) {
			imagePath = dt.get(0, "path").toString()
					+ dt.get(0, "filename").toString();
		}
		ZCVideoSchema video = new ZCVideoSchema();
		video.setID(Long.parseLong(ID));
		video.fill();
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(video.getCatalogID());
		catalog.fill();
		catalog.setImagePath(imagePath);
		if (catalog.update())
			this.response.setLogInfo(1, "设置专辑封面成功！");
		else
			this.response.setLogInfo(0, "设置专辑封面失败！");
	}

	public void setTopper() {
		String ID = this.request.get("ID").toString();
		ZCVideoSchema video = new ZCVideoSchema();
		video.setID(Long.parseLong(ID));
		video.fill();
		QueryBuilder qb = new QueryBuilder(
				"select max(OrderFlag) from ZCVideo where CatalogID = ?", video
						.getCatalogID());
		video.setOrderFlag(qb.executeInt() + 1);
		if (video.update())
			this.response.setLogInfo(1, "置顶成功！");
		else
			this.response.setLogInfo(0, "置顶失败！");
	}

	public void VideoLibEdit() {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setValue(this.request);
		catalog.fill();
		catalog.setValue(this.request);
		catalog.setAlias(XString.getChineseFirstAlpha(catalog.getName()));
		if (catalog.update()) {
			CatalogUtil.update(catalog.getID());
			this.response.setLogInfo(1, "修改视频分类成功！");
		} else {
			this.response.setLogInfo(0, "修改视频分类失败！");
		}
	}

	public static void treeDataBind(TreeAction ta) {
		String SiteID = ApplicationPage.getCurrentSiteID()+"";
		DataTable dt = null;
		Mapx params = ta.getParams();
		String parentLevel = params.getString("ParentLevel");
		String parentID = params.getString("ParentID");

		String IDs = ta.getParam("IDs");
		if (XString.isEmpty(IDs)) {
			IDs = ta.getParam("Cookie.Resource.LastVideoLib");
		}
		String[] codes = Catalog.getSelectedCatalogList(IDs, CatalogShowConfig
				.getVideoLibShowLevel());

		if (ta.isLazyLoad()) {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel>? and innerCode like ?");
			qb.add(5);
			qb.add(SiteID);
			qb.add(parentLevel);
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			if (!CatalogShowConfig.isVideoLibLoadAllChild()) {
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
			ta.setLevel(CatalogShowConfig.getVideoLibShowLevel());
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel-1 <=? order by orderflag,innercode");
			qb.add(5);
			qb.add(SiteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
			Catalog.prepareSelectedCatalogData(dt, codes, 5, SiteID, ta
					.getLevel());
		}
		ta.setRootText("视频库");
		dt = dt.filter(new Filter() {
			public boolean filter(Object obj) {
				DataRow dr = (DataRow) obj;
				return Priv.getPriv(User.getUserName(), "video", dr
						.getString("InnerCode"), "video_browse");
			}
		});
		ta.bindData(dt);
		Catalog.addSelectedBranches(ta, codes);
	}

	public static void dg1DataList(DataListAction dla) {
		String Alias = Config.getContextPath() + "/"
				+ Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/";
		Alias = Alias.replaceAll("///", "/");
		Alias = Alias.replaceAll("//", "/");
		String CatalogID = dla.getParams().getString("CatalogID");
		if (XString.isEmpty(CatalogID)) {
			dla.bindData(new DataTable());
			return;
		}
		String Name = dla.getParams().getString("Name");
		String StartDate = dla.getParam("StartDate");
		String EndDate = dla.getParam("EndDate");
		if ((XString.isEmpty(CatalogID)) && (XString.isEmpty(Name))) {
			dla.setTotal(0);
			dla.bindData(new DataTable());
			return;
		}
		QueryBuilder qb = new QueryBuilder("select * from ZCVideo where 1=1");
		if (XString.isNotEmpty(CatalogID)) {
			qb.append(" and CatalogID = ?", CatalogID);
		} else {
			dla.setTotal(0);
			dla.bindData(new DataTable());
			return;
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
		DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla
				.getPageIndex());
		dt.insertColumn("Alias", Alias);
		dla.bindData(dt);
	}

	public static void dg1DataBindBrowse(DataGridAction dga) {
		String Search = dga.getParams().getString("Search");
		if ((Search == null) || ("".equals(Search))) {
			dga.bindData(new DataTable());
			return;
		}

		String CatalogID = dga.getParams().getString("_CatalogID");
		String Name = dga.getParams().getString("Name");
		String Info = dga.getParams().getString("Info");
		QueryBuilder qb = new QueryBuilder("select * from ZCVideo");
		if (XString.isEmpty(CatalogID))
			qb.append(" where SiteID =?", ApplicationPage.getCurrentSiteID());
		else {
			qb.append(" where CatalogID =?", CatalogID);
		}
		if (XString.isNotEmpty(Name)) {
			qb.append(" and Name like ?", "%" + Name.trim() + "%");
		}
		if (XString.isNotEmpty(Info)) {
			qb.append(" and Info like ?", "%" + Info.trim() + "%");
		}
		qb.append(" order by ID desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dga.bindData(dt);
	}

	public static void dg1DataListBrowse(DataGridAction dga) {
		String Search = dga.getParams().getString("Search");
		if ((Search == null) || ("".equals(Search))) {
			dga.bindData(new DataTable());
			return;
		}

		String CatalogID = dga.getParams().getString("_CatalogID");
		String Name = dga.getParams().getString("Name");
		String Info = dga.getParams().getString("Info");
		QueryBuilder qb = new QueryBuilder("select * from ZCVideo");
		if (XString.isEmpty(CatalogID))
			qb.append(" where SiteID =?", ApplicationPage.getCurrentSiteID());
		else {
			qb.append(" where CatalogID =?", CatalogID);
		}
		if (XString.isNotEmpty(Name)) {
			qb.append(" and Name like ?", "%" + Name.trim() + "%");
		}
		if (XString.isNotEmpty(Info)) {
			qb.append(" and Info like ?", "%" + Info.trim() + "%");
		}
		qb.append(" order by ID desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dga.bindData(dt);
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) this.request.get("DT");
		ZCVideoSet set = new ZCVideoSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZCVideoSchema video = new ZCVideoSchema();
			video.setValue(dt.getDataRow(i));
			video.setModifyTime(new Date());
			video.setModifyUser(User.getUserName());
			set.add(video);
		}
		if (set.update())
			this.response.setLogInfo(1, "保存成功!");
		else
			this.response.setLogInfo(0, "保存失败!");
	}

	public void edit() {
		ZCVideoSchema Video = new ZCVideoSchema();
		Video.setValue(this.request);
		Video.fill();
		Video.setValue(this.request);
		if (Video.update())
			this.response.setLogInfo(1, "修改视频成功");
		else
			this.response.setLogInfo(0, "修改视频失败");
	}

	public void add() {
		String name = $V("Name");
		String parentID = $V("ParentID");
		String IT = $V("IndexTemplate");
		String DT = $V("DetailTemplate");
		String LT = $V("ListTemplate");
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
		catalog.setURL(" ");
		String alias = ChineseSpelling.getFirstAlpha(name).toLowerCase();
		String existsName = Catalog.checkAliasExists(alias, catalog
				.getParentID());
		if (XString.isNotEmpty(existsName)) {
			alias = alias + NoUtil.getMaxID("AliasNo");
		}
		catalog.setAlias(alias);
		catalog.setType(5L);
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
		catalog.setPublishFlag("Y");
		catalog.setHitCount(0L);
		catalog.setMeta_Keywords("");
		catalog.setMeta_Description("");
		catalog.setOrderColumn("");
		catalog.setListPage(-1L);
		catalog.setAddUser(User.getUserName());
		catalog.setAddTime(new Date());

		trans.add(catalog, OperateType.INSERT);

		Catalog.initCatalogConfig(catalog, trans);

		if (trans.commit()) {
			CatalogUtil.update(catalog.getID());
			UserLogPage.log("Resource", "AddTypeVideo", "新建视频分类:"
					+ catalog.getName() + " 成功", this.request.getClientIP());
			this.response.setLogInfo(1, "新建视频分类成功!");
		} else {
			UserLogPage.log("Resource", "AddTypeVideo", "新建视频分类:"
					+ catalog.getName() + " 失败", this.request.getClientIP());
			this.response.setLogInfo(0, "新建视频分类失败!");
		}
	}

	public void delLib() {
		String catalogID = $V("catalogID");
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(Long.parseLong(catalogID));
		if (!catalog.fill()) {
			this.response.setLogInfo(0, "没有视频分类！");
			return;
		}

		if ("N".equals(catalog.getProp4())) {
			this.response.setLogInfo(0, "不能删除该视频分类！");
			return;
		}

		ZCCatalogSet catalogSet = new ZCCatalogSchema().query(new QueryBuilder(
				"where InnerCode like ?", catalog.getInnerCode() + "%"));
		Transaction trans = new Transaction();
		ZCVideoSet videoSet = new ZCVideoSchema()
				.query(new QueryBuilder(" where CatalogInnerCode like ?",
						catalog.getInnerCode() + "%"));
		for (int i = 0; i < videoSet.size(); i++) {
			FileUtil
					.delete(Config.getContextRealPath()
							+ videoSet.get(i).getPath()
							+ videoSet.get(i).getFileName());
			ZCVideoRelaSet VideoRelaSet = new ZCVideoRelaSchema()
					.query(new QueryBuilder(" where id =?", videoSet.get(i)
							.getID()));
			trans.add(VideoRelaSet, OperateType.DELETE_AND_BACKUP);
		}

		File file = new File(Config.getContextRealPath()
				+ Config.getValue("UploadDir") + "/"
				+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID())
				+ "/upload/Video/"
				+ CatalogUtil.getPath(Long.parseLong(catalogID)));
		FileUtil.delete(file);

		trans.add(videoSet, OperateType.DELETE_AND_BACKUP);
		trans.add(catalogSet, OperateType.DELETE_AND_BACKUP);
		if (trans.commit()) {
			CMSCache.removeCatalogSet(catalogSet);
			this.response.setLogInfo(1, "删除视频分类成功！");

			Publisher p = new Publisher();
			p.deleteFileTask(videoSet);
		} else {
			this.response.setLogInfo(0, "删除视频频分类失败！");
		}
	}

	public void publish() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
			return;
		}
		ZCVideoSchema video = new ZCVideoSchema();
		ZCVideoSet set = video.query(new QueryBuilder("where id in(" + ids
				+ ")"));

		this.response.setStatus(1);
		Publisher p = new Publisher();
		long id = p.publishSetTask("Video", set);
		$S("TaskID", id);
	}
}

/*
 * com.xdarkness.cms.resource.VideoLib JD-Core Version: 0.6.0
 */