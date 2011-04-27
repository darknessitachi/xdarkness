package com.xdarkness.cms.site;

import java.util.Date;

import com.xdarkness.cms.datachannel.Deploy;
import com.xdarkness.cms.pub.CatalogUtil;
import com.xdarkness.cms.template.PageGenerator;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCPageBlockItemSchema;
import com.xdarkness.schema.ZCPageBlockItemSet;
import com.xdarkness.schema.ZCPageBlockSchema;
import com.xdarkness.schema.ZCPageBlockSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Errorx;
import com.xdarkness.framework.util.Mapx;

public class PageBlock extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder("select a.* from ZCPageBlock a");
		String catalogID = dga.getParam("CatalogID");
		if (XString.isNotEmpty(catalogID))
			qb
					.append(
							",zccatalog b where a.catalogid=b.id and b.innercode like ?",
							CatalogUtil.getInnerCode(catalogID) + "%");
		else {
			qb.append(" where a.siteid=?", ApplicationPage.getCurrentSiteID());
		}
		if (!Config.isDebugMode()) {
			qb.append(" and a.type<>4");
		}
		qb.append(" order by a.type asc");
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dt.insertColumn("CatalogIDName");
		dt.insertColumn("CatalogPath");
		for (int i = 0; i < dt.getRowCount(); i++) {
			if ((XString.isNotEmpty(dt.getString(i, "CatalogID")))
					&& (!"0".equals(dt.getString(i, "CatalogID")))) {
				dt.set(i, "CatalogIDName", CatalogUtil.getName(dt.getString(i,
						"CatalogID")));
				String str = "/";
				String id = dt.getString(i, "CatalogID");
				String parentid = "";
				do {
					if (XString.isNotEmpty(parentid)) {
						id = parentid;
					}
					parentid = CatalogUtil.getParentID(id);
					str = "/" + CatalogUtil.getName(id) + str;
				} while (!parentid.equals("0"));
				dt.set(i, "CatalogPath", str);
			} else {
				dt.set(i, "CatalogIDName", "全站");
				dt.set(i, "CatalogPath", "");
			}
		}
		dga.setTotal(qb);
		dga.bindData(dt);
	}

	public static void dialogDataBind(DataGridAction dga) {
		String id = (String) dga.getParams().get("ID");

		DataTable dt = null;
		if (XString.isNotEmpty(id))
			dt = new QueryBuilder(
					"select title,URL from zcpageblockItem where blockid=?", id)
					.executeDataTable();
		else {
			dt = new QueryBuilder(
					"select '' as title,'' as URL from zcpageblockItem")
					.executePagedDataTable(1, 0);
		}
		dga.bindData(dt);
	}

	public static void blockItemDataBind(DataGridAction dga) {
		String id = dga.getParam("ID");
		DataTable dt = null;
		if (XString.isNotEmpty(id)) {
			dt = new QueryBuilder(
					"select id,title,URL from zcpageblockItem where blockid=?",
					id).executeDataTable();
		}
		if ((dt == null) || (dt.getRowCount() == 0)) {
			String sql = "select 0 as id,'' as title,'' as URL from zcpageblockItem";
			dt = new QueryBuilder(sql).executePagedDataTable(1, 0);
		}
		dga.setTotal(dt.getRowCount());
		dga.bindData(dt);
	}

	public void edit() {
		Transaction trans = new Transaction();

		ZCPageBlockSchema block = new ZCPageBlockSchema();
		long blockID = Long.parseLong($V("ID"));
		block.setID(blockID);
		if (!block.fill()) {
			this.response.setStatus(0);
			this.response.setMessage("没有找到对应的区块!");
		}
		block.setName($V("Name"));
		block.setCode($V("Code"));
		block.setFileName($V("FileName"));

		block.setTemplate($V("Template"));
		block.setSortField($V("SortField"));

		block.setType(Integer.parseInt($V("Type")));
		block.setContent($V("Content"));
		block.setModifyTime(new Date());
		block.setModifyUser(User.getUserName());

		trans.add(block, OperateType.UPDATE);

		if (Integer.parseInt($V("Type")) == 2) {
			trans.add(new QueryBuilder(
					"delete from ZCPageBlockItem where blockid=?", blockID));
			String[] title = $V("ItemTitle").split("\\^");
			String[] url = $V("ItemURL").split("\\^");
			for (int i = 0; i < title.length; i++) {
				ZCPageBlockItemSchema item = new ZCPageBlockItemSchema();
				item.setID(NoUtil.getMaxID("PageBlockID"));
				item.setBlockID(blockID);
				item.setTitle(title[i]);
				item.setURL(url[i]);
				item.setImage($V("Image"));
				item.setAddTime(new Date());
				item.setAddUser(User.getUserName());
				trans.add(item, OperateType.INSERT);
			}
		}

		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("发生错误!");
		}
	}

	public static Mapx init(Mapx params) {
		if ((params.get("ID") != null) && (!"".equals(params.get("ID")))) {
			long ID = Long.parseLong(params.get("ID").toString());
			ZCPageBlockSchema block = new ZCPageBlockSchema();
			block.setID(ID);
			block.fill();
			Mapx mapx = block.toMapx();
			mapx.put("BlockType", block.getType());
			return mapx;
		}
		return params;
	}

	public static Mapx initParam(Mapx params) {
		return params;
	}

	public void add() {
		Transaction trans = new Transaction();

		ZCPageBlockSchema block = new ZCPageBlockSchema();
		long blockID = NoUtil.getMaxID("PageBlockID");
		block.setID(blockID);
		block.setSiteID(ApplicationPage.getCurrentSiteID());
		String obj = $V("CatalogID");
		if (XString.isNotEmpty(obj)) {
			block.setCatalogID(Long.parseLong(obj.toString()));
		}

		block.setName($V("Name"));
		block.setCode($V("Code"));
		block.setFileName($V("FileName"));
		block.setTemplate($V("Template"));
		block.setSortField($V("SortField"));

		block.setType(Integer.parseInt($V("Type")));
		block.setContent($V("Content"));
		block.setAddTime(new Date());
		block.setAddUser(User.getUserName());

		trans.add(block, OperateType.INSERT);

		if (Integer.parseInt($V("Type")) == 2) {
			String[] title = $V("ItemTitle").split("\\^");
			String[] url = $V("ItemURL").split("\\^");
			for (int i = 0; i < title.length; i++) {
				ZCPageBlockItemSchema item = new ZCPageBlockItemSchema();
				item.setID(NoUtil.getMaxID("PageBlockID"));
				item.setBlockID(blockID);
				item.setTitle(title[i]);
				item.setURL(url[i]);
				item.setImage($V("Image"));
				item.setAddTime(new Date());
				item.setAddUser(User.getUserName());
				trans.add(item, OperateType.INSERT);
			}
		}

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
		ZCPageBlockSchema block = new ZCPageBlockSchema();
		ZCPageBlockSet set = block.query(new QueryBuilder("where id in (" + ids
				+ ")"));
		trans.add(set, OperateType.DELETE_AND_BACKUP);

		ZCPageBlockItemSchema blockItem = new ZCPageBlockItemSchema();
		ZCPageBlockItemSet itemSet = blockItem.query(new QueryBuilder(
				"where blockID in (" + ids + ")"));
		trans.add(itemSet, OperateType.DELETE_AND_BACKUP);

		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
		}
	}

	public void generate() {
		PageGenerator p = new PageGenerator();
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		ZCPageBlockSchema block = new ZCPageBlockSchema();
		ZCPageBlockSet set = block.query(new QueryBuilder("where id in (" + ids
				+ ")"));
		if (p.staticPageBlock(set)) {
			Deploy d = new Deploy();
			d.addJobs(ApplicationPage.getCurrentSiteID(), p.getFileList());
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("生成区块失败。" + Errorx.printString());
		}
	}

	public void copy() {
		String BlockID = $V("ID");
		if (!XString.checkID(BlockID)) {
			this.response.setStatus(0);
			this.response.setMessage("传入BlockID时发生错误!");
			return;
		}
		String catalogIDs = $V("CatalogIDs");
		if (!XString.checkID(catalogIDs)) {
			this.response.setStatus(0);
			this.response.setMessage("传入CatalogID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCPageBlockSchema site = new ZCPageBlockSchema();
		ZCPageBlockSet set = site.query(new QueryBuilder("where id in ("
				+ BlockID + ")"));
		String copySiteID = "";

		String[] catalogArr = catalogIDs.split(",");
		for (int i = 0; i < set.size(); i++) {
			ZCPageBlockSchema block = set.get(i);
			for (int j = 0; j < catalogArr.length; j++) {
				ZCPageBlockSchema blockClone = (ZCPageBlockSchema) block
						.clone();
				blockClone.setID(NoUtil.getMaxID("PageBlockID"));
				blockClone.setCatalogID(catalogArr[j]);
				if ("".equals(copySiteID)) {
					copySiteID = CatalogUtil.getSiteID(catalogArr[j]);
				}
				blockClone.setSiteID(copySiteID);
				trans.add(blockClone, OperateType.INSERT);

				String sqlPageBlockCount = "update zccatalog set total = total+1 where id=?";
				trans.add(new QueryBuilder(sqlPageBlockCount, catalogArr[j]));
			}
		}
		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
		}
	}
}

/*
 * com.xdarkness.cms.site.PageBlock JD-Core Version: 0.6.0
 */