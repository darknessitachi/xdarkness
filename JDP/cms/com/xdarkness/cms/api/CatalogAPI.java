package com.xdarkness.cms.api;

import java.util.Date;
import java.util.Iterator;

import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCArticleSchema;
import com.xdarkness.schema.ZCAttachmentSchema;
import com.xdarkness.schema.ZCAudioSchema;
import com.xdarkness.schema.ZCCatalogSchema;
import com.xdarkness.schema.ZCCatalogSet;
import com.xdarkness.schema.ZCImageSchema;
import com.xdarkness.schema.ZCPageBlockItemSchema;
import com.xdarkness.schema.ZCPageBlockItemSet;
import com.xdarkness.schema.ZCPageBlockSchema;
import com.xdarkness.schema.ZCPageBlockSet;
import com.xdarkness.schema.ZCSiteSchema;
import com.xdarkness.schema.ZCVideoSchema;
import com.xdarkness.schema.ZDColumnRelaSchema;
import com.xdarkness.schema.ZDColumnRelaSet;
import com.xdarkness.schema.ZDColumnValueSchema;
import com.xdarkness.schema.ZDColumnValueSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Errorx;
import com.xdarkness.framework.util.Mapx;

public class CatalogAPI implements APIInterface {
	private Mapx params;

	public Mapx getParams() {
		return this.params;
	}

	public void setParams(Mapx params) {
		convertParams(params);
		this.params = params;
	}

	public long insert() {
		return insert(new Transaction());
	}

	public long insert(Transaction trans) {
		String parentID = this.params.getString("ParentID");
		String name = this.params.getString("Name");
		String type = this.params.getString("Type");
		String alias = this.params.getString("Alias");
		String siteID = this.params.getString("SiteID");
		String url = this.params.getString("URL");

		DataTable dt = new QueryBuilder(
				"select id from ZCCatalog where parentID='" + parentID
						+ "' and name='" + name + "' and siteID='" + siteID
						+ "'").executeDataTable();
		if (dt.getRowCount() > 0) {
			Errorx.addError(name + "栏目已经存在!");
			return -1L;
		}

		int aliasCount = new QueryBuilder(
				"select count(1) from zccatalog where alias=? and siteid=?",
				alias, siteID).executeInt();
		if (aliasCount > 0) {
			Errorx.addError(alias + "别名已经存在!");
			return -1L;
		}

		ZCCatalogSchema pCatalog = new ZCCatalogSchema();
		ZCCatalogSchema catalog = new ZCCatalogSchema();

		if (XString.isEmpty(type)) {
			type = "1";
		}

		if (XString.isNotEmpty(parentID)) {
			pCatalog.setID(Long.parseLong(parentID));
			if (!pCatalog.fill()) {
				return -1L;
			}
			catalog.setParentID(pCatalog.getID());
			catalog.setSiteID(pCatalog.getSiteID());

			catalog.setTreeLevel(pCatalog.getTreeLevel() + 1L);
			pCatalog.setChildCount(pCatalog.getChildCount() + 1L);
			pCatalog.setIsLeaf(0L);
			trans.add(pCatalog, OperateType.UPDATE);
		} else {
			if (XString.isEmpty(siteID)) {
				return -1L;
			}

			ZCSiteSchema site = new ZCSiteSchema();
			site.setID(siteID);
			if (!site.fill()) {
				return -1L;
			}
			catalog.setParentID(0L);
			catalog.setSiteID(site.getID());
			catalog.setTreeLevel(1L);
			parentID = "0";

			if ("1".equals(type)) {
				site.setChannelCount(site.getChannelCount() + 1L);
			} else if ("2".equals(type)) {
				site.setSpecialCount(site.getSpecialCount() + 1L);
			} else if ("3".equals(type)) {
				site.setMagzineCount(site.getMagzineCount() + 1L);
			}
			trans.add(site, OperateType.UPDATE);
		}

		if (XString.isEmpty(url)) {
			url = "";
			if (XString.isNotEmpty(parentID)) {
				url = url + CatalogUtil.getPath(parentID);
			}
			url = url + alias + "/";
		}

		long catalogID = NoUtil.getMaxID("CatalogID");
		catalog.setID(catalogID);

		String innerCode = CatalogUtil.createCatalogInnerCode(pCatalog
				.getInnerCode());
		catalog.setInnerCode(innerCode);

		catalog.setName(name.trim());
		catalog.setAlias(alias);
		catalog.setURL(url);
		catalog.setType(Integer.parseInt(type));

		catalog.setListTemplate(this.params.getString("Template"));
		catalog.setListNameRule(this.params.getString("ListNameRule"));
		catalog.setDetailTemplate(this.params.getString("DetailTemplate"));
		catalog.setDetailNameRule(this.params.getString("DetailNameRule"));
		catalog.setChildCount(0L);
		catalog.setIsLeaf(1L);
		catalog.setTotal(0L);

		String orderFlag = getCatalogOrderFlag(parentID, type);
		catalog.setOrderFlag(Integer.parseInt(orderFlag) + 1);
		catalog.setLogo(this.params.getString("Logo"));
		catalog.setListPageSize(20L);

		if (XString.isNotEmpty(this.params.getString("PublishFlag"))) {
			catalog.setPublishFlag("N");
		} else {
			catalog.setPublishFlag("Y");
		}

		if ("Y".equals(this.params.getString("SingleFlag"))) {
			catalog.setSingleFlag("Y");
		} else {
			catalog.setSingleFlag("N");
		}

		catalog.setImagePath(this.params.getString("Imagepath"));
		catalog.setHitCount(0L);
		catalog.setMeta_Keywords(this.params.getString("MetaKeyWords"));
		catalog.setMeta_Description(this.params.getString("MetaDescription"));
		catalog.setOrderColumn(this.params.getString("OrderColumn"));
		catalog.setProp1(this.params.getString("Prop1"));
		catalog.setProp2(this.params.getString("Prop2"));
		catalog.setProp3(this.params.getString("Prop3"));
		catalog.setProp4(this.params.getString("Prop4"));
		catalog.setAddUser("wsdl");
		catalog.setAddTime(new Date());

		trans.add(catalog, OperateType.INSERT);
		trans.add(new QueryBuilder(
				"update zccatalog set orderflag=orderflag+1 where orderflag>"
						+ orderFlag + " and type=" + type));

		if (trans.commit()) {
			CatalogUtil.update(catalog.getID());
			return 1L;
		}
		return -1L;
	}

	public boolean delete() {
		String catalogID = this.params.getString("CatalogID");
		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(catalogID);
		if (!catalog.fill()) {
			return false;
		}

		Transaction trans = new Transaction();

		ZCCatalogSchema pCatalog = new ZCCatalogSchema();
		pCatalog.setID(catalog.getParentID());
		pCatalog.fill();
		pCatalog.setChildCount(pCatalog.getChildCount() - 1L);
		if (pCatalog.getChildCount() == 0L) {
			pCatalog.setIsLeaf(1L);
		} else {
			pCatalog.setIsLeaf(0L);
		}
		trans.add(pCatalog, OperateType.UPDATE);

		ZCCatalogSet catalogSet = catalog.query(new QueryBuilder(
				" where InnerCode like ?", catalog.getInnerCode() + "%"));
		trans.add(catalogSet, OperateType.DELETE);
		trans.add(new ZCArticleSchema()
				.query(new QueryBuilder(" where CatalogInnerCode like ?",
						catalog.getInnerCode() + "%")), OperateType.DELETE);
		trans.add(new ZCImageSchema()
				.query(new QueryBuilder(" where CatalogInnerCode like ?",
						catalog.getInnerCode() + "%")), OperateType.DELETE);
		trans.add(new ZCVideoSchema()
				.query(new QueryBuilder(" where CatalogInnerCode like ?",
						catalog.getInnerCode() + "%")), OperateType.DELETE);
		trans.add(new ZCAudioSchema()
				.query(new QueryBuilder(" where CatalogInnerCode like ?",
						catalog.getInnerCode() + "%")), OperateType.DELETE);
		trans.add(new ZCAttachmentSchema()
				.query(new QueryBuilder(" where CatalogInnerCode like ?",
						catalog.getInnerCode() + "%")), OperateType.DELETE);

		String ids = "";
		for (int i = 0; i < catalogSet.size(); i++) {
			ids = ids + catalogSet.get(i).getID();
			if (i != catalogSet.size() - 1) {
				ids = ids + ",";
			}

			FileUtil.delete(CatalogUtil.getAbsolutePath(catalogSet.get(i)
					.getID()));
		}
		ZCPageBlockSet blockSet = new ZCPageBlockSchema()
				.query(new QueryBuilder(" where catalogid in (" + ids + ")"));
		for (int i = 0; i < blockSet.size(); i++) {
			ZCPageBlockItemSet itemSet = new ZCPageBlockItemSchema()
					.query(new QueryBuilder(" where blockID=?", blockSet.get(i)
							.getID()));
			trans.add(itemSet, OperateType.DELETE);
		}
		trans.add(blockSet, OperateType.DELETE);

		String idsStr = "'" + ids.replaceAll(",", "','") + "'";

		ZDColumnRelaSet columnRelaSet = new ZDColumnRelaSchema()
				.query(new QueryBuilder(" where RelaID in(" + idsStr + ")"));
		trans.add(columnRelaSet, OperateType.DELETE);

		ZDColumnValueSet columnValueSet1 = new ZDColumnValueSchema()
				.query(new QueryBuilder(" where RelaID in(" + idsStr + ")"));
		trans.add(columnValueSet1, OperateType.DELETE);

		String wherepart = " where exists (select ID from zcarticle where cataloginnercode like '"
				+ catalog.getInnerCode() + "%' and ID=zdcolumnvalue.relaID )";
		ZDColumnValueSet columnValueSet2 = new ZDColumnValueSchema()
				.query(new QueryBuilder(wherepart));
		trans.add(columnValueSet2, OperateType.DELETE);

		if (trans.commit()) {
			CatalogUtil.update(catalog.getID());
			return true;
		}
		return false;
	}

	public boolean setSchema(Schema schema) {
		return false;
	}

	public boolean update() {
		String ID = this.params.getString("CatalogID");

		String extend = this.params.getString("Extend");

		ZCCatalogSchema catalog = new ZCCatalogSchema();
		catalog.setID(ID);
		if (!catalog.fill()) {
			return false;
		}

		catalog.setName(this.params.getString("Name"));
		catalog.setAlias(this.params.getString("Alias"));
		catalog.setModifyUser("wsdl");
		catalog.setModifyTime(new Date());

		Transaction trans = new Transaction();
		trans.add(catalog, OperateType.UPDATE);

		if ((XString.isNotEmpty(extend)) && (!"1".equals(extend))) {
			if ("2".equals(extend)) {
				String IndexTemplate = this.params.getString("IndexTemplate");
				String ListTemplate = this.params.getString("ListTemplate");
				String DetailTemplate = this.params.getString("DetailTemplate");
				String RssTemplate = this.params.getString("RssTemplate");

				if ((XString.isNotEmpty(IndexTemplate))
						&& (XString.isNotEmpty(ListTemplate))
						&& (XString.isNotEmpty(DetailTemplate))
						&& (XString.isNotEmpty(RssTemplate))) {
					String innerCode = catalog.getInnerCode();
					QueryBuilder qb = new QueryBuilder(
							"update zccatalog set IndexTemplate=?,ListTemplate=?,DetailTemplate=?,rssTemplate=? where innercode like ? and TreeLevel>?");

					qb.add(IndexTemplate);
					qb.add(ListTemplate);
					qb.add(DetailTemplate);
					qb.add(RssTemplate);
					qb.add(innerCode + "%");
					qb.add(catalog.getTreeLevel());
					trans.add(qb);
				}

			} else if ("3".equals(extend)) {
				String IndexTemplate = this.params.getString("IndexTemplate");
				String ListTemplate = this.params.getString("ListTemplate");
				String DetailTemplate = this.params.getString("DetailTemplate");
				String RssTemplate = this.params.getString("RssTemplate");
				if ((XString.isNotEmpty(IndexTemplate))
						&& (XString.isNotEmpty(ListTemplate))
						&& (XString.isNotEmpty(DetailTemplate))
						&& (XString.isNotEmpty(RssTemplate))) {
					QueryBuilder qb = new QueryBuilder(
							"update zccatalog set IndexTemplate=?,ListTemplate=?,DetailTemplate=? ,rssTemplate=? where siteID=? and Type=?");

					qb.add(IndexTemplate);
					qb.add(ListTemplate);
					qb.add(DetailTemplate);
					qb.add(RssTemplate);
					qb.add(catalog.getSiteID());
					qb.add(catalog.getType());
					trans.add(qb);
				}
			} else if ("4".equals(extend)) {
				String IndexTemplate = this.params.getString("IndexTemplate");
				String ListTemplate = this.params.getString("ListTemplate");
				String DetailTemplate = this.params.getString("DetailTemplate");
				String RssTemplate = this.params.getString("RssTemplate");
				if ((XString.isNotEmpty(IndexTemplate))
						&& (XString.isNotEmpty(ListTemplate))
						&& (XString.isNotEmpty(DetailTemplate))
						&& (XString.isNotEmpty(RssTemplate))) {
					String part = "";
					String innerCode = catalog.getInnerCode();
					if ((XString.isNotEmpty(innerCode))
							&& (innerCode.length() > 4)) {
						part = " and innercode like '"
								+ innerCode
										.substring(0, innerCode.length() - 4)
								+ "%'";
					}
					QueryBuilder qb = new QueryBuilder(
							"update zccatalog set IndexTemplate=?,ListTemplate=?,DetailTemplate=?,rssTemplate=? where siteID=? and Type=? and TreeLevel=?"
									+ part);
					qb.add(IndexTemplate);
					qb.add(ListTemplate);
					qb.add(DetailTemplate);
					qb.add(RssTemplate);
					qb.add(catalog.getSiteID());
					qb.add(catalog.getType());
					qb.add(catalog.getTreeLevel());
					trans.add(qb);
				}
			}

		}

		String wfExtend = this.params.getString("WorkFlowExtend");
		if ((XString.isNotEmpty(wfExtend)) && (!"1".equals(wfExtend))) {
			if ("2".equals(wfExtend)) {
				trans
						.add(new QueryBuilder(
								"update zccatalog set workflow =? where innercode like ?",
								catalog.getWorkflow(), catalog.getInnerCode()
										+ "%"));
			} else if ("3".equals(wfExtend)) {
				trans.add(new QueryBuilder(
						"update zccatalog set workflow =? where siteID ="
								+ ApplicationPage.getCurrentSiteID()
								+ " and Type=? ", catalog.getWorkflow(),
						catalog.getType()));
			} else if ("4".equals(wfExtend)) {
				trans.add(new QueryBuilder(
						"update zccatalog set workflow =? where siteID ="
								+ ApplicationPage.getCurrentSiteID()
								+ " and Type=? and TreeLevel="
								+ catalog.getTreeLevel(),
						catalog.getWorkflow(), catalog.getType()));
			}
		}

		if (trans.commit()) {
			CatalogUtil.update(catalog.getID());
			return true;
		}
		return false;
	}

	public static String getCatalogOrderFlag(String ParentID, String CatalogType) {
		DataTable parentDT = null;

		if ((XString.isEmpty(ParentID)) || ("0".equals(ParentID))) {
			parentDT = new QueryBuilder(
					"select * from zccatalog where siteID = "
							+ ApplicationPage.getCurrentSiteID() + " and type = "
							+ CatalogType + " order by orderflag")
					.executeDataTable();
		} else {
			String innercode = CatalogUtil.getInnerCode(ParentID);
			parentDT = new QueryBuilder(
					"select * from zccatalog where siteID = "
							+ CatalogUtil.getSiteID(ParentID) + " and type = "
							+ CatalogType + " and innercode like '" + innercode
							+ "%' order by orderflag").executeDataTable();
		}
		if ((parentDT != null) && (parentDT.getRowCount() > 0)) {
			return parentDT.getString(parentDT.getRowCount() - 1, "OrderFlag");
		}

		return "0";
	}

	public void convertParams(Mapx params) {
		Iterator iter = params.keySet().iterator();
		while (iter.hasNext()) {
			Object key = iter.next();
			String value = params.getString(key);
			if ((XString.isEmpty(value)) || ("null".equalsIgnoreCase(value)))
				params.put(key, "");
		}
	}
}

/*
 * com.xdarkness.cms.api.CatalogAPI JD-Core Version: 0.6.0
 */