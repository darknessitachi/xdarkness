package com.xdarkness.cms.site;

import java.util.Date;
import java.util.List;

import com.xdarkness.platform.page.ApplicationPage;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCKeywordSchema;
import com.xdarkness.schema.ZCKeywordSet;
import com.xdarkness.schema.ZCKeywordTypeSchema;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.TreeAction;
import com.xdarkness.framework.jaf.controls.TreeItem;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.orm.data.DataTableUtil;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class Keyword extends Page {
	public static Mapx Type_KeyWordMap = new Mapx(1000);

	public static ZCKeywordSet getKeyWordSet(String typeID) {
		ZCKeywordSet set = (ZCKeywordSet) Type_KeyWordMap.get(typeID);
		if (set == null) {
			updateCache(typeID);
			set = (ZCKeywordSet) Type_KeyWordMap.get(typeID);
		}
		return set;
	}

	private static void updateCache(String typeID) {
		ZCKeywordSchema keyword = new ZCKeywordSchema();
		QueryBuilder qb = new QueryBuilder();
		if (Config.isSQLServer())
			qb.setSQL("where KeyWordType like ? order by len(KeyWord) desc");
		else {
			qb.setSQL("where KeyWordType like ? order by length(KeyWord) desc");
		}
		qb.add("%" + typeID + "%");
		ZCKeywordSet set = keyword.query(qb);
		if (set.size() == 0)
			Type_KeyWordMap.remove(typeID);
		else
			Type_KeyWordMap.put(typeID, set);
	}

	public static void dg1DataBind(DataGridAction dga) {
		String keywordTypeID = dga.getParam("id");
		String word = dga.getParam("Word");
		String siteID = ApplicationPage.getCurrentSiteID()+"";
		QueryBuilder qb = new QueryBuilder(
				"select ID,Keyword,LinkURL,LinkAlt,LinkTarget,addTime from ZCKeyword where 1=1 ");
		if (XString.isNotEmpty(word)) {
			qb.append(" and Keyword like ? ", "%" + word.trim() + "%");
		}
		qb.append(" and SiteId = ? ");
		qb.add(siteID);
		if ((XString.isNotEmpty(keywordTypeID))
				&& (!keywordTypeID.trim().equals("null"))) {
			qb.append(" and KeywordType like ? ");
			qb.add("%," + keywordTypeID.trim() + ",%");
		}
		if (XString.isNotEmpty(dga.getSortString()))
			qb.append(dga.getSortString());
		else {
			qb.append(" order by Keyword asc");
		}
		dga.bindData(qb);
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) this.request.get("DT");
		ZCKeywordSet set = new ZCKeywordSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZCKeywordSchema keyword = new ZCKeywordSchema();
			keyword.setID(Integer.parseInt(dt.getString(i, "ID")));
			keyword.fill();
			keyword.setValue(dt.getDataRow(i));
			keyword.setModifyTime(new Date());
			keyword.setModifyUser(User.getUserName());

			set.add(keyword);
		}
		if (set.update()) {
			Type_KeyWordMap.clear();
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("发生错误!");
		}
	}

	public static Mapx init(Mapx params) {
		return null;
	}

	public static Mapx initDialog(Mapx params) {
		String ID = params.getString("ID");
		ZCKeywordSchema keyword = new ZCKeywordSchema();
		keyword.setID(ID);
		if ((XString.isNotEmpty(ID)) && (keyword.fill())) {
			params.putAll(keyword.toMapx());
		} else {
			params.put("LinkUrl", "http://");
			params.put("LinkTarget", "_blank");
		}
		return params;
	}

	public void add() {
		long siteID = ApplicationPage.getCurrentSiteID();
		String KeyWord = $V("Keyword").trim();
		int flag = new QueryBuilder(
				"select count(*) from ZCKeyWord where SiteID=? and Keyword=?",
				siteID, KeyWord).executeInt();
		ZCKeywordSchema keyword = new ZCKeywordSchema();
		if (flag == 0) {
			keyword.setID(NoUtil.getMaxID("KeywordID"));
			keyword.setKeyword(KeyWord);
			keyword.setSiteId(siteID);
			keyword.setLinkUrl($V("LinkURL"));
			keyword.setLinkTarget($V("LinkTarget"));
			keyword.setKeywordType("," + $V("selectdTypes") + ",");
			keyword.setLinkAlt($V("LinkAlt"));
			keyword.setAddTime(new Date());
			keyword.setAddUser(User.getUserName());
			if (keyword.insert()) {
				Type_KeyWordMap.clear();
				this.response.setStatus(1);
				this.response.setMessage("新增成功！");
			} else {
				this.response.setStatus(0);
				this.response.setMessage("发生错误!");
			}
		} else {
			keyword = keyword.query(
					new QueryBuilder("where Keyword = ? and siteID = ?",
							KeyWord, siteID)).get(0);
			if ((XString.isNotEmpty($V("LinkURL")))
					&& (!$V("LinkURL").equals("http://"))) {
				keyword.setLinkUrl($V("LinkURL"));
			}
			if (XString.isNotEmpty($V("LinkAlt"))) {
				keyword.setLinkAlt($V("LinkAlt"));
			}
			keyword.setLinkTarget($V("LinkTarget"));
			String[] temp = $V("selectdTypes").split(",");
			for (int i = 0; i < temp.length; i++) {
				if (keyword.getKeywordType().indexOf("," + temp[i] + ",") < 0) {
					keyword.setKeywordType(keyword.getKeywordType() + temp[i]
							+ ",");
				}
			}

			if (keyword.update()) {
				if (!XString.checkID(keyword.getKeywordType())) {
					this.response.setStatus(0);
					this.response.setMessage("传入ID时发生错误!");
					return;
				}
				DataTable keywordTypeDT = new QueryBuilder(
						"select * from ZCKeyWordType where SiteID=? and ID in ("
								+ keyword.getKeywordType().substring(1,
										keyword.getKeywordType().length() - 1)
								+ ")", siteID).executeDataTable();
				StringBuffer message = new StringBuffer();
				for (int i = 0; i < keywordTypeDT.getRowCount(); i++) {
					message.append(keywordTypeDT.get(i, "TypeName") + " ");
				}
				this.response.setStatus(1);
				this.response
						.setMessage("新增成功！此热点词同时存在于以下分类中：<br/><font class='red'>"
								+ message + "</font>");
			} else {
				this.response.setStatus(0);
				this.response.setMessage("发生错误!");
			}
		}
	}

	public void edit() {
		String KeyWord = $V("Keyword").trim();
		String ID = $V("ID");
		QueryBuilder qb = new QueryBuilder(
				"select count(*) from ZCKeyWord where ID != ? and SiteID=? and Keyword=?");
		qb.add(ID);
		qb.add(ApplicationPage.getCurrentSiteID());
		qb.add(KeyWord);
		if (qb.executeInt() == 0) {
			ZCKeywordSchema keyword = new ZCKeywordSchema();
			keyword.setID(ID);
			keyword.fill();
			keyword.setKeyword(KeyWord);
			keyword.setSiteId(ApplicationPage.getCurrentSiteID());
			keyword.setLinkUrl($V("LinkURL"));
			keyword.setLinkTarget($V("LinkTarget"));
			keyword.setKeywordType("," + $V("selectdTypes") + ",");
			keyword.setLinkAlt($V("LinkAlt"));
			keyword.setModifyTime(new Date());
			keyword.setModifyUser(User.getUserName());
			if (keyword.update()) {
				Type_KeyWordMap.clear();
				this.response.setStatus(1);
				this.response.setMessage("修改成功！");
			} else {
				this.response.setStatus(0);
				this.response.setMessage("发生错误!");
			}
		} else {
			this.response.setStatus(0);
			this.response.setMessage("已经存在的关键词!");
		}
	}

	public void importWords() {
		String FilePath = $V("FilePath");
		long SiteID = ApplicationPage.getCurrentSiteID();
		Transaction trans = new Transaction();
		String Words = $V("KeyWords");
		String selectedCID = $V("selectedCID");
		if (XString.isEmpty(selectedCID)) {
			selectedCID = "";
		}
		String wordsText = "";
		String[] keyWords = (String[]) null;
		DataTable dt = null;
		if (XString.isEmpty(FilePath)) {
			keyWords = Words.split("\n");
		} else if (FilePath.indexOf("txt") >= 0) {
			FilePath = FilePath.replaceAll("//", "/");
			wordsText = FileUtil.readText(FilePath);
			keyWords = wordsText.split("\n");
		} else if (FilePath.indexOf("xls") >= 0) {
			try {
				dt = DataTableUtil.xlsToDataTable(FilePath);
			} catch (Exception e) {
				e.printStackTrace();
			}
			keyWords = new String[dt.getRowCount()];
			for (int i = 0; i < dt.getRowCount(); i++) {
				keyWords[i] = XString.join(dt.getDataRow(i).toMapx()
						.valueArray());
			}
		}

		String temp = "";
		for (int i = 0; i < keyWords.length; i++) {
			if ((!keyWords[i].equals("\r"))
					&& (!XString.isEmpty(keyWords[i]))) {
				ZCKeywordSchema keyword = new ZCKeywordSchema();
				temp = keyWords[i];
				temp = temp.trim().replaceAll("\\s+", ",");
				temp = temp.replaceAll("，", ",");
				String[] word = XString.splitEx(temp, ",");
				if ((word.length != 5) || (XString.isEmpty(word[0]))) {
					continue;
				}
				boolean flag = false;

				if (new QueryBuilder(
						"select count(*) from ZCKeyWord where KeyWord = ? and SiteID=? ",
						word[0], SiteID).executeInt() > 0)
					flag = true;
				else {
					flag = false;
				}
				if (flag) {
					String WordID = new QueryBuilder(
							"select ID from ZCKeyWord where KeyWord = ? and SiteID=? ",
							word[0].trim(), SiteID).executeOneValue()
							.toString();
					keyword.setID(WordID);
					keyword.fill();
				} else {
					keyword.setID(NoUtil.getMaxID("KeywordID"));
				}
				keyword.setKeyword(word[0]);
				keyword.setSiteId(SiteID);
				if (XString.isEmpty(word[1]))
					keyword.setLinkUrl("http://");
				else {
					keyword.setLinkUrl(word[1].trim());
				}
				if (XString.isNotEmpty(word[2])) {
					keyword.setLinkAlt(word[2].trim());
				}
				if ((XString.isDigit(word[3]))
						&& (Integer.parseInt(word[3]) > 0)
						&& (Integer.parseInt(word[3].trim()) < 4)) {
					if (word[3].equals("1"))
						keyword.setLinkTarget("_self");
					else if (word[3].equals("2"))
						keyword.setLinkTarget("_blank");
					else
						keyword.setLinkTarget("_parent");
				} else {
					keyword.setLinkTarget("_blank");
				}

				String[] typeNames = word[4].split("/");
				for (int j = 0; j < typeNames.length; j++) {
					if (new QueryBuilder(
							"select count(*) from ZCKeyWordType where TypeName = ?",
							typeNames[j]).executeInt() == 0) {
						ZCKeywordTypeSchema type = new ZCKeywordTypeSchema();
						type.setID(NoUtil.getMaxID("KeyWordTypeID"));
						type.setTypeName(typeNames[j].trim());
						type.setSiteID(ApplicationPage.getCurrentSiteID());
						type.setAddTime(new Date());
						type.setAddUser(User.getUserName());
						type.insert();
					}
					typeNames[j] = ("'" + typeNames[j].trim() + "'");
				}
				DataTable typeDT = new QueryBuilder(
						"select ID from ZCKeywordType where siteID = ? and TypeName in ("
								+ XString.join(typeNames) + ")", SiteID)
						.executeDataTable();
				for (int j = 0; j < typeDT.getRowCount(); j++) {
					if (keyword.getKeywordType() == null)
						keyword.setKeywordType("," + typeDT.getString(j, 0));
					else if (keyword.getKeywordType().indexOf(
							typeDT.getString(j, 0)) < 0) {
						keyword.setKeywordType(keyword.getKeywordType() + ","
								+ typeDT.getString(j, 0));
					}
				}
				keyword.setKeywordType(keyword.getKeywordType() + ",");
				if (flag) {
					keyword.setModifyTime(new Date());
					keyword.setModifyUser(User.getUserName());
					trans.add(keyword, OperateType.UPDATE);
				} else {
					keyword.setAddTime(new Date());
					keyword.setAddUser(User.getUserName());
					trans.add(keyword, OperateType.INSERT);
				}
			}

		}

		if (trans.commit()) {
			Type_KeyWordMap.clear();
			this.response.setLogInfo(1, "导入成功");
		} else {
			this.response.setLogInfo(0, "导入失败");
		}
	}

	public void del() {
		String ids = $V("IDs");
		String selectedCID = $V("selectedCID");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		long SiteID = ApplicationPage.getCurrentSiteID();
		ZCKeywordSchema keyword = new ZCKeywordSchema();
		ZCKeywordSet set = keyword.query(new QueryBuilder(
				"where SiteID=? and id in (" + ids + ")", SiteID));
		if (XString.isEmpty(selectedCID)) {
			trans.add(set, OperateType.DELETE_AND_BACKUP);
		} else {
			for (int i = 0; i < set.size(); i++) {
				keyword = set.get(i);
				String keywordType = keyword.getKeywordType();
				if (keywordType.indexOf("," + selectedCID + ",") >= 0) {
					keyword.setKeywordType(keywordType.replaceAll(","
							+ selectedCID + ",", ","));
				} else {
					ZCKeywordSchema keywordTemp = keyword;
					trans.add(keywordTemp, OperateType.DELETE_AND_BACKUP);
				}
			}
			trans.add(set, OperateType.UPDATE);
		}
		if (trans.commit()) {
			Type_KeyWordMap.clear();
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
		}
	}

	public static void treeDataBind(TreeAction ta) {
		Object obj = ta.getParams().get("SiteID");
		String siteID = ApplicationPage.getCurrentSiteID()+"";
		DataTable dt = null;
		QueryBuilder qb = new QueryBuilder(
				"select ID,TypeName from ZCKeywordType Where SiteID = ? order by ID",
				siteID);
		dt = qb.executeDataTable();
		ta.setRootText("热点词汇库");
		ta.bindData(dt);
		List items = ta.getItemList();
		for (int i = 1; i < items.size(); i++) {
			TreeItem item = (TreeItem) items.get(i);
			if ("Y".equals(item.getData().getString("SingleFlag")))
				item.setIcon("Icons/treeicon11.gif");
		}
	}

	public void move() {
		String keywordIDs = $V("KeywordIDs");
		String tarTypeID = $V("TypeID");
		if (!XString.checkID(keywordIDs)) {
			this.response.setError("操作数据库时发生错误!");
			return;
		}
		if (!XString.checkID(tarTypeID)) {
			this.response.setError("传入TypeID时发生错误!");
			return;
		}

		Transaction trans = new Transaction();
		ZCKeywordSchema srcKeyword = new ZCKeywordSchema();
		ZCKeywordSet set = srcKeyword.query(new QueryBuilder("where id in ("
				+ keywordIDs + ")"));

		for (int i = 0; i < set.size(); i++) {
			ZCKeywordSchema keyword = set.get(i);
			String keywordType = keyword.getKeywordType();
			if (keywordType.indexOf(tarTypeID) >= 0) {
				this.response.setMessage("该分类下已经存在热点词\"" + keyword.getKeyword()
						+ "\"");
				return;
			}
			keywordType = keywordType + "," + tarTypeID;
			keyword.setKeywordType(keywordType);

			trans.add(keyword, OperateType.UPDATE);
		}

		if (trans.commit())
			this.response.setMessage("复制成功");
		else
			this.response.setError("操作数据库时发生错误!");
	}
}
