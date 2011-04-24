package com.xdarkness.cms.site;

import java.util.Date;

import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.cms.template.PageGenerator;
import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.schema.ZCLinkSchema;
import com.xdarkness.schema.ZCLinkSet;
import com.xdarkness.schema.ZCPageBlockSchema;
import com.xdarkness.schema.ZCPageBlockSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class Link extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		String groupID = dga.getParam("LinkGroupID");
		String sql = "select ZCLink.*,(select name from zclinkgroup where id=zclink.linkgroupID) as LinkGroupName from ZCLink ";
		QueryBuilder qb = new QueryBuilder(sql);
		if (XString.isNotEmpty(groupID)) {
			qb.append(" where LinkGroupID=?", groupID);
		} else {
			qb
					.append(" where exists (select '' from ZCLinkGroup where ID=ZCLink.LinkGroupID and SiteID=?)");
			qb.add(ApplicationPage.getCurrentSiteID());
		}
		qb.append(" order by OrderFlag desc,id desc");
		dga.bindData(qb);
	}

	public static Mapx initDialog(Mapx params) {
		String ID = params.getString("ID");
		String type = params.getString("Type");
		if (XString.isNotEmpty(ID)) {
			ZCLinkSchema link = new ZCLinkSchema();
			link.setID(ID);
			link.fill();
			Mapx map = link.toMapx();
			map.put("Type", type);
			map.put("ImageSrc",
					(Config.getContextPath() + Config.getValue("UploadDir")
							+ "/"
							+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID())
							+ "/" + link.getImagePath()).replaceAll("//", "/"));
			return map;
		}
		params.put("LinkGroupID", params.getString("LinkGroupID"));
		params.put("Type", type);
		params.put("URL", "http://");
		params
				.put(
						"ImageSrc",
						(Config.getContextPath()
								+ Config.getValue("UploadDir")
								+ "/"
								+ SiteUtil.getAlias(ApplicationPage
										.getCurrentSiteID()) + "/upload/Image/nopicture.jpg")
								.replaceAll("//", "/"));
		return params;
	}

	public void save() {
		DataTable dt = (DataTable) this.request.get("DT");
		Transaction trans = new Transaction();
		for (int i = 0; i < dt.getRowCount(); i++) {
			QueryBuilder qb = new QueryBuilder(
					"update ZCLink set Name=?,URL=?,ModifyUser=?,ModifyTime=? where ID=?");
			qb.add(dt.getString(i, "Name"));
			qb.add(dt.getString(i, "URL"));
			qb.add(User.getUserName());
			qb.add(new Date());
			qb.add(dt.getString(i, "ID"));
			trans.add(qb);
		}
		if (trans.commit()) {
			updateStatLink();
			this.response.setLogInfo(1, "修改成功!");
		} else {
			this.response.setLogInfo(0, "修改失败!");
		}
	}

	public void edit() {
		Transaction trans = new Transaction();
		String ID = $V("ID");
		if ((XString.isEmpty(ID)) || (!XString.isDigit(ID))) {
			this.response.setLogInfo(0, "传入ID错误");
			return;
		}

		ZCLinkSchema link = new ZCLinkSchema();
		link.setID(ID);
		link.fill();
		link.setValue(this.request);
		String ImageID = $V("ImageID");
		if (XString.isNotEmpty(ImageID)) {
			DataTable dt = new QueryBuilder(
					"select path,srcfilename from zcimage where id=?", ImageID)
					.executeDataTable();
			link.setImagePath((dt.get(0, "path").toString() + dt.get(0,
					"srcfilename")).replaceAll("//", "/").toString());
		}
		link.setModifyUser(User.getUserName());
		link.setModifyTime(new Date());
		trans.add(link, OperateType.UPDATE);
		if (trans.commit()) {
			updateStatLink();
			this.response.setLogInfo(1, "修改成功!");
		} else {
			this.response.setLogInfo(0, "修改失败!");
		}
	}

	public void add() {
		ZCLinkSchema link = new ZCLinkSchema();
		link.setID(NoUtil.getMaxID("LinkID"));
		link.setValue(this.request);
		String ImageID = $V("ImageID");
		if (XString.isNotEmpty(ImageID)) {
			DataTable dt = new QueryBuilder(
					"select path,srcfilename from zcimage where id=?", ImageID)
					.executeDataTable();
			link.setImagePath((dt.get(0, "path").toString() + dt.get(0,
					"srcfilename")).replaceAll("//", "/").toString());
		} else {
			link.setImagePath("upload/Image/nopicture.jpg");
		}
		link.setOrderFlag(OrderUtil.getDefaultOrder());
		link.setSiteID(ApplicationPage.getCurrentSiteID());
		link.setAddUser(User.getUserName());
		link.setAddTime(new Date());
		if (link.insert())
			this.response.setLogInfo(1, "新增成功!");
		else
			this.response.setLogInfo(0, "新增失败!");
	}

	public void del() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setLogInfo(0, "传入ID时发生错误！");
			return;
		}
		Transaction trans = new Transaction();
		ZCLinkSchema link = new ZCLinkSchema();
		ZCLinkSet set = link
				.query(new QueryBuilder("where id in (" + ids + ")"));
		trans.add(set, OperateType.DELETE);

		if (trans.commit())
			this.response.setLogInfo(1, "删除成功！");
		else
			this.response.setLogInfo(0, "删除数据时发生错误!");
	}

	public void getPicSrc() {
		String ID = $V("PicID");
		DataTable dt = new QueryBuilder(
				"select path,srcfilename from zcimage where id=?", ID)
				.executeDataTable();
		if (dt.getRowCount() > 0)
			this.response.put("picSrc", (Config.getContextPath()
					+ Config.getValue("UploadDir") + "/"
					+ SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/"
					+ dt.get(0, "path").toString() + dt.get(0, "srcfilename"))
					.replaceAll("//", "/").toString());
	}

	public void sortColumn() {
		String target = $V("Target");
		String orders = $V("Orders");
		String type = $V("Type");
		String linkGroupID = $V("LinkGroupID");
		if ((!XString.checkID(target)) || (!XString.checkID(orders))) {
			return;
		}
		if (OrderUtil.updateOrder("ZCLink", type, target, orders,
				" LinkGroupID = " + linkGroupID))
			this.response.setMessage("排序成功");
		else
			this.response.setError("排序失败");
	}

	public static void updateStatLink() {
		ZCPageBlockSet set = new ZCPageBlockSchema().query(new QueryBuilder(
				" where SiteID = ? and code like '%friendlink%'", ApplicationPage
						.getCurrentSiteID()));
		PageGenerator p = new PageGenerator();
		p.staticPageBlock(set);
	}
}

/*
 * com.xdarkness.cms.site.Link JD-Core Version: 0.6.0
 */