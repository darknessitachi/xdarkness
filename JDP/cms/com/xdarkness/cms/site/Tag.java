package com.xdarkness.cms.site;

import java.util.Date;

import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCTagSchema;
import com.xdarkness.schema.ZCTagSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class Tag extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		String TagWord = dga.getParam("TagWord");
		QueryBuilder qb = new QueryBuilder("select * from ZCTag where siteID=?");
		qb.add(ApplicationPage.getCurrentSiteID());
		if (XString.isNotEmpty(TagWord)) {
			qb.append(" and Tag like ?", "%" + TagWord.trim() + "%");
		}
		if (XString.isNotEmpty(dga.getSortString()))
			qb.append(dga.getSortString());
		else {
			qb.append(" order by ID desc");
		}
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dga.bindData(dt);
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) this.request.get("DT");
		ZCTagSet set = new ZCTagSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZCTagSchema tag = new ZCTagSchema();
			tag.setID(Integer.parseInt(dt.getString(i, "ID")));
			tag.fill();
			tag.setValue(dt.getDataRow(i));
			tag.setModifyTime(new Date());
			tag.setModifyUser(User.getUserName());
			if (!checkTagWord(tag.getID(), tag.getTag())) {
				this.response.setStatus(0);
				this.response.setMessage("更改Tag内容不允许和其他数据的Tag内容重复!");
				return;
			}
			set.add(tag);
		}
		if (set.update()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("发生错误!");
		}
	}

	public static Mapx init(Mapx params) {
		return null;
	}

	public static boolean checkTagWord(long SiteID, String TagWord) {
		int count = new QueryBuilder(
				"select count(1) from ZCTag where Tag=? and SiteID=?", TagWord,
				SiteID).executeInt();
		return count == 0;
	}

	public void add() {
		ZCTagSchema tag = new ZCTagSchema();
		String TagWord = $V("Tag").trim();
		tag.setID(NoUtil.getMaxID("TagID"));
		tag.setValue(this.request);
		tag.setSiteID(ApplicationPage.getCurrentSiteID());
		tag.setAddTime(new Date());
		tag.setAddUser(User.getUserName());
		tag.setUsedCount(0L);
		if (checkTagWord(ApplicationPage.getCurrentSiteID(), TagWord)) {
			if (tag.insert())
				this.response.setLogInfo(1, "新增成功");
			else
				this.response.setLogInfo(0, "发生错误！");
		} else {
			this.response.setStatus(0);
			this.response.setMessage("已经存在的Tag内容!");
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
		ZCTagSchema Tag = new ZCTagSchema();
		QueryBuilder qb = new QueryBuilder("where id in (" + ids + ")");
		ZCTagSet set = Tag.query(qb);
		trans.add(set, OperateType.DELETE_AND_BACKUP);
		if (trans.commit()) {
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
		}
	}
}

/*
 * com.xdarkness.cms.site.Tag JD-Core Version: 0.6.0
 */