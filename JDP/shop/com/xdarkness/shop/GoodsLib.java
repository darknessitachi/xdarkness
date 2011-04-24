package com.xdarkness.shop;

import java.sql.SQLException;
import java.util.Date;

import com.xdarkness.cms.datachannel.Publisher;
import com.xdarkness.cms.dataservice.ColumnUtil;
import com.xdarkness.cms.document.Article;
import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.cms.site.Catalog;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.page.UserLogPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.schema.ZCCatalogSchema;
import com.xdarkness.schema.ZCCatalogSet;
import com.xdarkness.schema.ZCImageSchema;
import com.xdarkness.schema.ZCSiteSchema;
import com.xdarkness.schema.ZSGoodsSchema;
import com.xdarkness.schema.ZSGoodsSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.TreeAction;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Errorx;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;

public class GoodsLib extends Page {
	public static Mapx initDialog(Mapx params) {
		params.put("goodsParentID", HtmlUtil.dataTableToOptions(CatalogUtil
				.getCatalogOptions(9L)));

		String catalogID = params.getString("CatalogID");
		params.put("CustomColumn", ColumnUtil.getHtml("1", catalogID));
		return params;
	}

	public static Mapx initEditDialog(Mapx params) {
		ZSGoodsSchema goods = new ZSGoodsSchema();
		goods.setID(params.getString("ID"));
		goods.fill();
		Mapx map = goods.toMapx();
		String catalogID = goods.getCatalogID()+"";

		map.put("CustomColumn", ColumnUtil.getHtml("1", catalogID, "2", goods
				.getID()+""));
		return map;
	}

	public static Mapx initGoodsInfo(Mapx params) {
		ZSGoodsSchema goods = new ZSGoodsSchema();
		goods.setID(params.getString("ID"));
		goods.fill();
		Mapx map = goods.toMapx();
		map.put("imgpath",
				(Config.getContextPath() + Config.getValue("UploadDir") + "/"
						+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID())
						+ "/" + goods.getImage0()).replaceAll("//", "/"));

		String catalogID = goods.getCatalogID()+"";
		String sql = null;

		sql = "select name from zccatalog where id=?";
		String catalogName = new QueryBuilder(sql, goods.getCatalogID())
				.executeString();
		map.put("CatalogName", catalogName == null ? "没有所属分类" : catalogName);

		sql = "select name from zccatalog where exists (select 1 from zcdocrela where docid=? and catalogType=? and catalogid=zccatalog.id)";
		String brandName = new QueryBuilder(sql, goods.getID(), 10L)
				.executeString();
		map.put("BrandCatalogName", brandName == null ? "没有所属品牌" : brandName);

		map.put("CustomColumn", ColumnUtil.getText("1", catalogID, "2", goods
				.getID()+""));
		return map;
	}

	public static Mapx initEditLibDialog(Mapx params) {
		long ID = Long.parseLong(params.get("ID").toString());
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(ID);
		catalog.fill();
		return catalog.toMapx();
	}

	public static void dg1DataBind(DataGridAction dga) {
		String CatalogID = dga.getParam("CatalogID");
		String SearchContent = dga.getParam("SearchContent");

		QueryBuilder qb = new QueryBuilder(
				"select zsgoods.*,(select name from zccatalog c where c.id=zsgoods.catalogid) as CatalogIDName from zsgoods  where ");
		if (XString.isEmpty(CatalogID))
			qb.append(" SiteID = ?", ApplicationPage.getCurrentSiteID());
		else {
			qb.append(" CatalogID = ?", CatalogID);
		}
		if (XString.isNotEmpty(SearchContent)) {
			qb.append(" and Name like ?", "%" + SearchContent.trim() + "%");
		}
		qb.append(" order by OrderFlag desc");
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dt.decodeColumn("Status", Article.STATUS_MAP);
		if ((dt != null) && (dt.getRowCount() > 0)) {
			dt.getDataColumn("PublishDate").setDateFormat("yyyy-MM-dd");
		}
		ColumnUtil.extendCatalogColumnData(dt, CatalogID);
		dga.bindData(dt);
	}

	public void setImageCover() {
		ZCImageSchema image = new ZCImageSchema();
		image.setID($V("ID"));
		image.fill();
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(image.getCatalogID());
		catalog.fill();

		if (catalog.update())
			this.response.setLogInfo(1, "设置专辑封面成功！");
		else
			this.response.setLogInfo(0, "设置专辑封面失败！");
	}

	public void GoodsLibEdit() {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setValue(this.request);
		catalog.fill();
		catalog.setValue(this.request);
		if (catalog.update())
			this.response.setLogInfo(1, "修改分类成功！");
		else
			this.response.setLogInfo(0, "修改分类失败！");
	}

	public void addLib() {
		String name = $V("Name");
		String parentID = $V("ParentID");
		String DT = $V("DetailTemplate");
		String LT = $V("ListTemplate");
		Transaction trans = new Transaction();
		ZCCatalogSchema catalog = new ZCCatalogSchema();

		catalog.setID(NoUtil.getMaxID("CatalogID"));
		catalog.setSiteID(ApplicationPage.getCurrentSiteID());

		if (XString.isEmpty(parentID)) {
			catalog.setParentID(0L);
			catalog.setInnerCode(NoUtil.getMaxNo("InnerCode", 4));
			catalog.setTreeLevel(1L);
			ZCSiteSchema site = new ZCSiteSchema();
			site.setID(catalog.getSiteID());
			site.fill();
			site.setImageLibCount(site.getImageLibCount() + 1L);
			trans.add(site, OperateType.UPDATE);
		} else {
			catalog.setParentID(Long.parseLong(parentID));
			ZCCatalogSchema pCatalog = new ZCCatalogSchema();
			pCatalog.setID(catalog.getParentID());
			pCatalog.fill();

			catalog.setInnerCode(NoUtil.getMaxNo("InnerCode", pCatalog
					.getInnerCode(), 4));
			catalog.setTreeLevel(pCatalog.getTreeLevel() + 1L);

			pCatalog.setChildCount(pCatalog.getChildCount() + 1L);
			trans.add(pCatalog, OperateType.UPDATE);
		}

		catalog.setName(name);
		catalog.setURL(" ");
		catalog.setAlias(XString.getChineseFirstAlpha(name));
		catalog.setType(9L);
		catalog.setListTemplate(LT);
		catalog.setListNameRule("");
		catalog.setDetailTemplate(DT);
		catalog.setDetailNameRule("");
		catalog.setChildCount(0L);
		catalog.setIsLeaf(1L);
		catalog.setTotal(0L);
		catalog.setOrderFlag(OrderUtil.getDefaultOrder());
		catalog.setLogo("");
		catalog.setListPageSize(10L);
		catalog.setListPage(10L);
		catalog.setPublishFlag("Y");
		catalog.setHitCount(0L);
		catalog.setMeta_Keywords("");
		catalog.setMeta_Description("");
		catalog.setOrderColumn("");
		catalog.setAddUser(User.getUserName());
		catalog.setAddTime(new Date());

		trans.add(catalog, OperateType.INSERT);
		if (trans.commit())
			this.response.setLogInfo(1, "新建分类成功!!!");
		else
			this.response.setLogInfo(0, "新建分类失败!!!");
	}

	public void delLib() {
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		String catalogId = $V("catalogID");
		catalog.setID(catalogId);
		if (!catalog.fill()) {
			this.response.setLogInfo(0, "没有这个分类！");
			return;
		}

		ZSGoodsSet goodsSet = new ZSGoodsSchema()
				.query(new QueryBuilder("where CatalogInnerCode like '"
						+ catalog.getInnerCode() + "%'"));
		System.err.println(goodsSet.size());
		if (goodsSet.size() != 0) {
			this.response.setLogInfo(1, "该分类下面有商品，请先删除商品！");
			return;
		}
		ZCCatalogSet catalogSet = new ZCCatalogSchema().query(new QueryBuilder(
				"where InnerCode like '" + catalog.getInnerCode() + "%'"));
		Transaction trans = new Transaction();
		trans.add(catalogSet, OperateType.DELETE);
		if (trans.commit())
			this.response.setLogInfo(1, "删除该分类成功！");
		else
			this.response.setLogInfo(0, "删除该分类失败，请重试!");
	}

	public void transfer() {
		String IDs = $V("IDs");
		if (!XString.checkID(IDs)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		long catalogID = Long.parseLong($V("CatalogID"));
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(catalogID);
		if (!catalog.fill()) {
			this.response.setLogInfo(0,
					"The Catalog you selected is not existsed!");
			return;
		}
		Transaction trans = new Transaction();
		QueryBuilder qb = new QueryBuilder(
				"update zsgoods set CatalogID = ?,CatalogInnerCode = ?,BranchCode = ?,BranchInnerCode = ?,SiteID = ? where ID in ("
						+ IDs + ")");
		qb.add(catalogID);
		qb.add(catalog.getInnerCode());

		qb.add(catalog.getBranchInnerCode());
		qb.add(ApplicationPage.getCurrentSiteID());
		trans.add(qb);
		if (trans.commit())
			this.response.setLogInfo(1, "转移成功");
		else
			this.response.setLogInfo(0, "转移失败");
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
		String[] codes = Catalog.getSelectedCatalogList(IDs, ta.getLevel());
		if (ta.isLazyLoad()) {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,prop1 from ZCCatalog Where Type =? and SiteID =? and TreeLevel>? and innerCode like ? order by orderflag,innercode");
			qb.add(9);
			qb.add(SiteID);
			qb.add(parentLevel);
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			dt = qb.executeDataTable();
		} else {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,prop1 from ZCCatalog Where Type = ? and SiteID =? and TreeLevel-1 <=?  order by orderflag,innercode");
			qb.add(9);
			qb.add(SiteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
			Catalog.prepareSelectedCatalogData(dt, codes, 9, SiteID, ta
					.getLevel());
		}
		ta.setRootText("商品库");
		ta.bindData(dt);
		Catalog.addSelectedBranches(ta, codes);
	}

	public void publish() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			UserLogPage.log("Goods", "Publish_Goods", "商品发布失败", this.request
					.getClientIP());
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
			return;
		}
		ZSGoodsSchema goods = new ZSGoodsSchema();
		ZSGoodsSet set = goods.query(new QueryBuilder("where id in(" + ids
				+ ")"));
		StringBuffer logs = new StringBuffer("发布商品:");
		for (int i = 0; i < set.size(); i++) {
			logs.append(set.get(i).getName() + ",");
		}
		UserLogPage.log("Goods", "Publish_Goods", logs + "成功", this.request
				.getClientIP());

		this.response.setStatus(1);
		long id = publishSetTask(set);
		$S("TaskID", id);
	}

	private long publishSetTask(final ZSGoodsSet set) {
		LongTimeTask ltt = new LongTimeTask() {

			public void execute() {
				Publisher p = new Publisher();
				try {
					p.publishGoods(set, this);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	public void down() {
		String ids = $V("GoodsIDs");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("错误的参数!");
			return;
		}
		Date now = new Date();
		Transaction trans = new Transaction();

		trans
				.add(new QueryBuilder(
						"update zsgoods set Status = 40,TopFlag='0',DownlineDate = ?,modifyTime=? where id in("
								+ ids + ")", now, now));
		if (trans.commit()) {
			ZSGoodsSchema site = new ZSGoodsSchema();
			ZSGoodsSet set = site.query(new QueryBuilder("where id in (" + ids
					+ ")"));
			downTask(set);
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
		}
	}

	private long downTask(final ZSGoodsSet set) {
		LongTimeTask ltt = new LongTimeTask() {

			public void execute() {
				Publisher p = new Publisher();
				if ((set != null) && (set.size() > 0)) {
					p.deletePubishedFile(set);

					Mapx catalogMap = new Mapx();
					for (int k = 0; k < set.size(); k++) {
						catalogMap.put(set.get(k).getCatalogID(),
								set.get(k).getCatalogID());
						String pid = CatalogUtil.getParentID(set
								.get(k).getCatalogID());
						while ((XString.isNotEmpty(pid))
								&& (!"null".equals(pid)) && (!"0".equals(pid))) {
							catalogMap.put(pid, pid);
							pid = CatalogUtil.getParentID(pid);
						}

					}

					Object[] vs = catalogMap.valueArray();
					for (int j = 0; j < catalogMap.size(); j++) {
						String listpage = CatalogUtil.getData(vs[j].toString())
								.getString("ListPage");
						if ((XString.isEmpty(listpage))
								|| ("0".equals(listpage))
								|| ("-1".equals(listpage))) {
							listpage = "20";
						}
						p.publishCatalog(Long.parseLong(vs[j].toString()),
								false, false, Integer.parseInt(listpage));
						setPercent(getPercent() + 5);
						setCurrentInfo("发布栏目页面");
					}
				}
				setPercent(100);
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	public void up() {
		String ids = $V("GoodsIDs");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("错误的参数!");
			return;
		}
		Date now = new Date();
		Transaction trans = new Transaction();

		trans
				.add(new QueryBuilder(
						"update zsgoods set Status =30,PublishDate = ?,DownlineDate = '2999-12-31 23:59:59' where id in("
								+ ids + ")", now));
		if (trans.commit()) {
			upTask(ids);
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
		}
	}

	private long upTask(final String ids) {
		LongTimeTask ltt = new LongTimeTask() {

			public void execute() {
				Publisher p = new Publisher();
				ZSGoodsSchema site = new ZSGoodsSchema();
				ZSGoodsSet set = site.query(new QueryBuilder("where id in ("
						+ ids + ")"));
				if ((set != null) && (set.size() > 0)) {
					try {
						p.publishGoods(set, false, this);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					p.publishCatalog(set.get(0).getCatalogID(), false, false);
					setPercent(100);
				}
			}
		};
		ltt.setUser(User.getCurrent());
		ltt.start();
		return ltt.getTaskID();
	}

	public static void treeDiagDataBind(TreeAction ta) {
		Object obj = ta.getParams().get("SiteID");
		String siteID = ApplicationPage.getCurrentSiteID()+"";
		Object typeObj = ta.getParams().get("CatalogType");
		int catalogType = typeObj != null ? Integer
				.parseInt(typeObj.toString()) : 9;
		String parentTreeLevel = ta.getParams().getString("ParentLevel");
		String parentID = ta.getParams().getString("ParentID");

		String IDs = ta.getParam("IDs");
		if (XString.isEmpty(IDs)) {
			IDs = ta.getParam("Cookie.Resource.LastVideoLib");
		}
		String[] codes = Catalog.getSelectedCatalogList(IDs, ta.getLevel());

		DataTable dt = null;
		if (ta.isLazyLoad()) {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel>? and innerCode like ? order by orderflag,innercode");

			qb.add(catalogType);
			qb.add(siteID);
			qb.add(parentTreeLevel);
			qb.add(CatalogUtil.getInnerCode(parentID) + "%");
			dt = qb.executeDataTable();
		} else {
			QueryBuilder qb = new QueryBuilder(
					"select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel-1 <=? order by orderflag,innercode");

			qb.add(catalogType);
			qb.add(siteID);
			qb.add(ta.getLevel());
			dt = qb.executeDataTable();
			Catalog.prepareSelectedCatalogData(dt, codes, 9, siteID, ta
					.getLevel());
		}
		ta.setRootText("商品库");

		String siteName = "文档库";
		if (catalogType == 9) {
			siteName = "商品库";
		}
		if (catalogType == 10) {
			siteName = "商品品牌库";
		}
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
		Catalog.addSelectedBranches(ta, codes);
	}

	public void move() {
		String goodsIDs = $V("GoodsIDs");
		if (!XString.checkID(goodsIDs)) {
			this.response.setError("操作数据库时发生错误!");
			return;
		}

		String catalogID = $V("CatalogID");
		if (!XString.checkID(catalogID)) {
			this.response.setError("传入CatalogID时发生错误!");
			return;
		}

		Transaction trans = new Transaction();
		ZSGoodsSchema srcGoods = new ZSGoodsSchema();
		ZSGoodsSet set = srcGoods.query(new QueryBuilder("where id in ("
				+ goodsIDs + ")"));
		long srcCatalogID = 0L;

		String[] srcGoodsIDs = (String[]) null;
		if (set.size() > 0) {
			srcGoodsIDs = new String[set.size()];
			for (int i = 0; i < set.size(); i++) {
				srcGoodsIDs[i] = String.valueOf(set.get(i).getID());
			}
		}
		for (int i = 0; i < set.size(); i++) {
			ZSGoodsSchema goods = set.get(i);
			srcCatalogID = goods.getCatalogID();
			String destCatalogID = catalogID;
			trans.add(new QueryBuilder(
					"update zccatalog set total = total+1 where id=?",
					destCatalogID));
			trans.add(new QueryBuilder(
					"update zccatalog set total = total-1 where id=?",
					srcCatalogID));
			goods.setCatalogInnerCode(CatalogUtil.getInnerCode(catalogID));
			goods.setCatalogID(catalogID);
			goods.setOrderFlag(OrderUtil.getDefaultOrder());
			trans.add(goods, OperateType.UPDATE);
		}

		if (trans.commit()) {
			Publisher p = new Publisher();

			p.deletePubishedFile(set);

			try {
				if ((p.publishGoods(set, true, null))
						&& (p.publishCatalog(srcCatalogID, false, false)))
					this.response.setMessage("转移成功");
				else
					this.response.setError("转移成功,发布失败。" + Errorx.printString());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			this.response.setError("操作数据库时发生错误!");
		}
	}

	public void copy() {
		String catalogIDs = $V("CatalogIDs");
		ZCCatalogSet catalogs = new ZCCatalogSchema().query(new QueryBuilder(
				"where ID in (" + catalogIDs + ")"));
		for (int i = 0; i < catalogs.size(); i++) {
			String IDs = $V("GoodsIDs");
			if (!XString.checkID(IDs)) {
				this.response.setStatus(0);
				this.response.setMessage("传入ID时发生错误!");
				return;
			}
			ZSGoodsSet goodsSet = new ZSGoodsSchema().query(new QueryBuilder(
					" where ID in (" + IDs + ")"));
			for (int j = 0; j < goodsSet.size(); j++) {
				ZSGoodsSchema goods = goodsSet.get(j);
				goods.setID(NoUtil.getMaxID("ID"));
				goods.setCatalogID(catalogs.get(i).getID());
				goods.setCatalogInnerCode(catalogs.get(i).getInnerCode());
				goods.setBranchInnerCode(catalogs.get(i).getBranchInnerCode());
				goods.setSiteID(ApplicationPage.getCurrentSiteID());
				goods.setAddTime(new Date());
				goods.setAddUser(User.getUserName());
			}
			if (goodsSet.insert())
				this.response.setLogInfo(1, "复制成功");
			else
				this.response.setLogInfo(0, "复制失败");
		}
	}

	public static void dg2DataBind(DataGridAction dga) {
		String catalogID = (String) dga.getParams().get("CatalogID");
		if (XString.isEmpty(catalogID)) {
			catalogID = "0";
			dga.getParams().put("CatalogID", catalogID);
		}

		QueryBuilder qb = new QueryBuilder(
				"select * from ZSGoods where CatalogID=?", catalogID);

		qb.append(dga.getSortString());
		if (XString.isNotEmpty(dga.getSortString()))
			qb.append(" ,orderflag desc");
		else {
			qb.append(" order by topflag desc,orderflag desc");
		}
		dga.setTotal(qb);

		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		if ((dt != null) && (dt.getRowCount() > 0)) {
			dt.decodeColumn("Status", Article.STATUS_MAP);
			dt.getDataColumn("PublishDate").setDateFormat("yy-MM-dd");
		}

		dga.bindData(dt);
	}

	public void sortGoods() {
		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		String catalogID = $V("CatalogID");
		if ((!XString.checkID(target)) && (!XString.checkID(orders))) {
			return;
		}
		Transaction tran = new Transaction();

		OrderUtil.updateOrder("ZSGoods", "OrderFlag", type, target, orders,
				null, tran);
		if (tran.commit()) {
			final String id = catalogID;
			LongTimeTask ltt = new LongTimeTask() {

				public void execute() {
					Publisher p = new Publisher();
					String listpage = CatalogUtil.getData(id)
							.getString("ListPage");
					if ((XString.isEmpty(listpage))
							|| ("0".equals(listpage))
							|| ("-1".equals(listpage))) {
						listpage = "20";
					}
					p.publishCatalog(Long.parseLong(id), false, false,
							Integer.parseInt(listpage));
					setPercent(100);
				}
			};
			ltt.setUser(User.getCurrent());
			ltt.start();

			this.response.setMessage("操作成功");
		} else {
			this.response.setError("操作失败");
		}
	}
}

/*
 * com.xdarkness.shop.GoodsLib JD-Core Version: 0.6.0
 */