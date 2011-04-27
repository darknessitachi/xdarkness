package com.xdarkness.cms.site;

import java.util.ArrayList;
import java.util.Date;

import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.platform.pub.OrderUtil;
import com.xdarkness.schema.ZCLinkGroupSchema;
import com.xdarkness.schema.ZCLinkGroupSet;
import com.xdarkness.schema.ZCLinkSchema;
import com.xdarkness.schema.ZCLinkSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.HtmlUtil;
import com.xdarkness.framework.util.Mapx;

public class LinkGroup extends Page {
	public static final String TYPE_TEXT = "text";
	public static final String TYPE_IMAGE = "image";
	public static final Mapx TYPE_MAP = new Mapx();

	static {
		TYPE_MAP.put("text", "文字链接");
		TYPE_MAP.put("image", "图片链接");
	}

	public static void dg1DataBind(DataGridAction dga) {
		QueryBuilder qb = new QueryBuilder(
				"select * from ZCLinkGroup where SiteID=? order by OrderFlag asc,id desc",
				ApplicationPage.getCurrentSiteID());
		DataTable dt = qb.executeDataTable();
		dt.decodeColumn("Type", TYPE_MAP);
		dga.bindData(dt);
	}

	public static Mapx initDialog(Mapx params) {
		params.put("Type", HtmlUtil.mapxToRadios("Type", TYPE_MAP, "text"));
		return params;
	}

	public void save() {
		DataTable dt = (DataTable) this.request.get("DT");
		Transaction trans = new Transaction();
		String logMessage = "保存成功！";
		ArrayList list = new ArrayList();
		for (int i = 0; i < dt.getRowCount(); i++) {
			DataTable checkdt = new QueryBuilder(
					"select * from zclinkgroup where name=?", dt.getString(i,
							"Name")).executeDataTable();
			if (checkdt.getRowCount() > 0) {
				list.add(dt.getString(i, "Name"));
			} else {
				QueryBuilder qb = new QueryBuilder(
						"update ZCLinkGroup set Name=?,ModifyUser=?,ModifyTime=? where ID=?");
				qb.add(dt.getString(i, "Name"));
				qb.add(User.getUserName());
				qb.add(new Date());
				qb.add(dt.getString(i, "ID"));
				trans.add(qb);
			}
		}
		if (list.size() > 0) {
			logMessage = logMessage + "链接分类名称";
			logMessage = logMessage + XString.join(list.toArray(), "、");
			logMessage = logMessage + "已存在,请更换...";
		}
		if (trans.commit())
			this.response.setLogInfo(1, logMessage);
		else
			this.response.setLogInfo(0, "保存失败！");
	}

	public void add() {
		String name = $V("Name");
		DataTable dt = new QueryBuilder(
				"select * from zclinkgroup where name=?", name)
				.executeDataTable();
		if (dt.getRowCount() > 0) {
			this.response.setLogInfo(0, "该链接分类名称已存在，请更换...");
			return;
		}
		ZCLinkGroupSchema linkGroup = new ZCLinkGroupSchema();
		linkGroup.setValue(this.request);
		linkGroup.setID(NoUtil.getMaxID("LinkGroupID"));
		linkGroup.setOrderFlag(OrderUtil.getDefaultOrder());
		linkGroup.setSiteID(ApplicationPage.getCurrentSiteID());
		linkGroup.setAddTime(new Date());
		linkGroup.setAddUser(User.getUserName());
		if (linkGroup.insert())
			this.response.setLogInfo(1, "新增成功");
		else
			this.response.setLogInfo(0, "新增" + linkGroup.getName() + "失败!");
	}

	public void del() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setLogInfo(0, "传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCLinkGroupSchema linkGroup = new ZCLinkGroupSchema();
		ZCLinkGroupSet set = linkGroup.query(new QueryBuilder("where id in ("
				+ ids + ")"));
		trans.add(set, OperateType.DELETE);

		for (int i = 0; i < set.size(); i++) {
			linkGroup = set.get(i);
			ZCLinkSchema link = new ZCLinkSchema();
			link.setLinkGroupID(linkGroup.getID());
			ZCLinkSet LinkSet = link.query();
			trans.add(LinkSet, OperateType.DELETE);
		}
		if (trans.commit())
			this.response.setLogInfo(1, "删除成功");
		else
			this.response.setLogInfo(0, "删除失败");
	}
}

/*
 * com.xdarkness.cms.site.LinkGroup JD-Core Version: 0.6.0
 */