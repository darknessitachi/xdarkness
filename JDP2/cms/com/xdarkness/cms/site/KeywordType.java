package com.xdarkness.cms.site;

import java.util.Date;

import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCKeywordSchema;
import com.xdarkness.schema.ZCKeywordTypeSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.TreeAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class KeywordType extends Page {
	public void add() {
		String typeName = $V("TypeName").trim();
		if (new QueryBuilder(
				"select count(*) from ZCKeyWordType where SiteID=? and TypeName=?",
				ApplicationPage.getCurrentSiteID(), typeName).executeInt() == 0) {
			ZCKeywordTypeSchema keywordType = new ZCKeywordTypeSchema();
			keywordType.setID(NoUtil.getMaxID("KeyWordTypeID"));
			keywordType.setTypeName(typeName);
			keywordType.setSiteID(ApplicationPage.getCurrentSiteID());
			keywordType.setAddTime(new Date());
			keywordType.setAddUser(User.getUserName());
			if (keywordType.insert()) {
				this.response.setStatus(1);
				this.response.setMessage("新增成功！");
			} else {
				this.response.setStatus(0);
				this.response.setMessage("发生错误!");
			}
		} else {
			this.response.setStatus(0);
			this.response.setMessage("已经存在的分类!");
		}
	}

	public static DataTable loadType(Mapx params) {
		DataTable dt = new DataTable();
		dt = new QueryBuilder(
				"select ID,TypeName from ZCKeywordType where SiteID = ?",
				ApplicationPage.getCurrentSiteID()).executeDataTable();

		return dt;
	}

	public static void loadTypeTree(TreeAction ta) {
		String ID = ta.getParam("ID");
		String selectedCID = ta.getParam("selectedCID");
		if (XString.isEmpty(selectedCID)) {
			selectedCID = null;
		}
		ZCKeywordSchema keyword = new ZCKeywordSchema();
		keyword.setID(ID);
		DataTable dt = null;
		QueryBuilder qb = null;
		if ((XString.isNotEmpty(ID)) && (keyword.fill())) {
			qb = new QueryBuilder(
					"select ID,TypeName,(select 'Checked' from ZCKeyword k where k.ID = ? and ZCKeywordType.ID in ("
							+ keyword.getKeywordType().substring(1,
									keyword.getKeywordType().length() - 1)
							+ ")) as Checked "
							+ "from ZCKeywordType where SiteID = ? ");
			qb.add(ID);
			qb.add(ApplicationPage.getCurrentSiteID());
		} else {
			qb = new QueryBuilder(
					"select ID,TypeName,(select 'Checked' from ZCKeywordType k where k.ID=ZCKeywordType.ID and k.ID = ?) as Checked from ZCKeywordType where SiteID = ?",
					selectedCID, ApplicationPage.getCurrentSiteID());
		}
		dt = qb.executeDataTable();
		ta.setRootText("请选择类别");
		ta.bindData(dt);
	}

	public void del() {
		Transaction trans = new Transaction();
		String ID = $V("ID");
		ZCKeywordTypeSchema keyWordType = new ZCKeywordTypeSchema();
		keyWordType.setID(ID);
		trans.add(keyWordType, OperateType.DELETE);
		trans.add(new QueryBuilder(
				"update ZCKeyword set KeywordType = replace(KeywordType,',"
						+ ID + ",',',') where KeywordType like ?", "%," + ID
						+ ",%"));
		trans.add(new QueryBuilder(
				"delete from ZCKeyword where KeywordType = ?", ID));

		if (trans.commit()) {
			this.response.setStatus(1);
			this.response.setMessage("删除成功!");
		} else {
			this.response.setStatus(0);
			this.response.setMessage("发生错误!");
		}
	}

	public void edit() {
		String ID = $V("ID");
		ZCKeywordTypeSchema keywordType = new ZCKeywordTypeSchema();
		keywordType.setID(ID);
		String typeName = $V("TypeName").trim();
		if (keywordType.fill()) {
			if (new QueryBuilder(
					"select count(*) from ZCKeyWordType where SiteID=? and TypeName=?",
					ApplicationPage.getCurrentSiteID(), typeName).executeInt() == 0) {
				keywordType.setTypeName(typeName);
				keywordType.setModifyTime(new Date());
				keywordType.setModifyUser(User.getUserName());
				if (keywordType.update()) {
					this.response.setStatus(1);
					this.response.setMessage("修改成功！");
					return;
				}
				this.response.setStatus(0);
				this.response.setMessage("发生错误!");
				return;
			}
		}
		this.response.setStatus(0);
		this.response.setMessage("已经存在的分类!");
	}

	public static Mapx initEditDialog(Mapx params) {
		String ID = params.getString("id");
		params.put("ID", ID);
		ZCKeywordTypeSchema keywordType = new ZCKeywordTypeSchema();
		if (XString.isNotEmpty(ID)) {
			keywordType.setID(ID);
			keywordType.fill();
			params.put("TypeName", keywordType.getTypeName());
		}

		return params;
	}

	public static DataTable loadKeywordType(Mapx params) {
		DataTable dt = new DataTable();
		dt.insertRow(new String[] { "", "" });
		dt.union(new QueryBuilder(
				"select ID, TypeName from ZCKeywordType where siteID = ?",
				ApplicationPage.getCurrentSiteID()).executeDataTable());

		return dt;
	}
}

/*
 * com.xdarkness.cms.site.KeywordType JD-Core Version: 0.6.0
 */