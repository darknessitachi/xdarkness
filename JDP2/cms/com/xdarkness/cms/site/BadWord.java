package com.xdarkness.cms.site;

import java.util.Date;
import java.util.Iterator;

import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCBadWordSchema;
import com.xdarkness.schema.ZCBadWordSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.io.FileUtil;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class BadWord extends Page {
	public static final String TREELEVEL_1 = "1";
	public static final String TREELEVEL_2 = "2";
	public static final String TREELEVEL_3 = "3";
	public static final Mapx TREELEVEL_MAP = new Mapx();
	private static Mapx BadWordMap;

	static {
		TREELEVEL_MAP.put("1", "一般");
		TREELEVEL_MAP.put("2", "比较敏感");
		TREELEVEL_MAP.put("3", "非常敏感");

		BadWordMap = new Mapx();

		BadWordMap.put("1", new Mapx());
		BadWordMap.put("2", new Mapx());
		BadWordMap.put("3", new Mapx());
		updateCache();
	}

	private static void updateCache() {
		((Mapx) BadWordMap.get("1")).clear();
		((Mapx) BadWordMap.get("2")).clear();
		((Mapx) BadWordMap.get("3")).clear();
		DataTable dt = new QueryBuilder(
				"select TreeLevel,Word,ReplaceWord from ZCBadWord order by ID desc")
				.executeDataTable();
		for (int i = 0; i < dt.getRowCount(); i++) {
			String TreeLevel = dt.getString(i, "TreeLevel");
			if ("3".compareTo(TreeLevel) <= 0) {
				((Mapx) BadWordMap.get("2")).put(dt.getString(i, "Word"), dt
						.getString(i, "ReplaceWord"));
				((Mapx) BadWordMap.get("3")).put(dt.getString(i, "Word"), dt
						.getString(i, "ReplaceWord"));
			} else if ("2".compareTo(TreeLevel) <= 0) {
				((Mapx) BadWordMap.get("2")).put(dt.getString(i, "Word"), dt
						.getString(i, "ReplaceWord"));
			}
			((Mapx) BadWordMap.get("1")).put(dt.getString(i, "Word"), dt
					.getString(i, "ReplaceWord"));
		}
	}

	public static String checkBadWord(String content) {
		return checkBadWord("1");
	}

	public static String checkBadWord(String content, String priority) {
		Mapx map = (Mapx) BadWordMap.get(priority);
		String badwords = "";
		for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
			String word = (String) iter.next();
			if ((XString.isNotEmpty(content)) && (!"null".equals(content))
					&& (content.indexOf(word) != -1)) {
				badwords = badwords + " " + word;
			}
		}
		return badwords;
	}

	public static String filterBadWord(String content) {
		return filterBadWord(content, "1");
	}

	public static String filterBadWord(String content, String priority) {
		Mapx map = (Mapx) BadWordMap.get(priority);
		String word = null;
		String replaceWord = null;
		for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
			word = null;
			replaceWord = null;
			word = (String) iter.next();
			replaceWord = (String) map.get(word);
			if (XString.isNotEmpty(word)) {
				content = XString.replaceAllIgnoreCase(content, word,
						replaceWord);
			}
		}
		return content;
	}

	public static void dg1DataBind(DataGridAction dga) {
		String word = dga.getParam("Word");
		QueryBuilder qb = new QueryBuilder(
				"select ID,Word,TreeLevel,ReplaceWord,AddUser,AddTime from ZCBadWord ");
		if (XString.isNotEmpty(word)) {
			qb.append("where word like ?", "%" + word.trim() + "%");
		}
		if (XString.isNotEmpty(dga.getSortString()))
			qb.append(dga.getSortString());
		else {
			qb.append(" order by ID desc");
		}
		dga.setTotal(qb);
		DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga
				.getPageIndex());
		dt.decodeColumn("TreeLevel", TREELEVEL_MAP);
		dga.bindData(dt);
	}

	public void dg1Edit() {
		DataTable dt = (DataTable) this.request.get("DT");
		ZCBadWordSet set = new ZCBadWordSet();
		for (int i = 0; i < dt.getRowCount(); i++) {
			ZCBadWordSchema badWord = new ZCBadWordSchema();
			badWord.setID(Integer.parseInt(dt.getString(i, "ID")));
			badWord.fill();

			badWord.setTreeLevel(dt.getString(i, "TreeLevel"));
			badWord.setWord(dt.getString(i, "Word"));
			badWord.setReplaceWord(dt.getString(i, "ReplaceWord"));
			badWord.setModifyTime(new Date());
			badWord.setModifyUser(User.getUserName());

			set.add(badWord);
		}
		if (set.update()) {
			updateCache();
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("发生错误!");
		}
	}

	public static Mapx init(Mapx params) {
		return null;
	}

	public void add() {
		String Word = $V("BadWord").trim();
		if (new QueryBuilder("select count(*) from ZCBadWord where Word = ?",
				Word).executeInt() == 0) {
			ZCBadWordSchema badWord = new ZCBadWordSchema();
			badWord.setID(NoUtil.getMaxID("BadWordID"));
			badWord.setWord(Word);
			badWord.setTreeLevel(Integer.parseInt($V("Level")));
			badWord.setReplaceWord($V("ReplaceWord"));
			badWord.setAddTime(new Date());
			badWord.setAddUser(User.getUserName());
			if (badWord.insert()) {
				updateCache();
				this.response.setStatus(1);
				this.response.setMessage("新增成功!");
			} else {
				this.response.setStatus(0);
				this.response.setMessage("发生错误!");
			}
		} else {
			this.response.setStatus(0);
			this.response.setMessage("已经存在的敏感词!");
		}
	}

	public void importWords() {
		String FilePath = $V("FilePath");
		String Words = $V("BadWords");
		String wordsText = "";
		if (XString.isNotEmpty(FilePath)) {
			FilePath = FilePath.replaceAll("//", "/");
			wordsText = FileUtil.readText(FilePath);
		} else {
			wordsText = Words;
		}
		String[] badWords = wordsText.split("\n");
		String temp = "";
		Transaction trans = new Transaction();
		ZCBadWordSchema badword = new ZCBadWordSchema();
		for (int i = 0; i < badWords.length; i++) {
			if ((!badWords[i].equals("\r"))
					&& (!XString.isEmpty(badWords[i]))) {
				temp = badWords[i];
				temp = temp.trim().replaceAll("\\s+", ",");
				temp = temp.replaceAll("，", ",");
				String[] word = XString.splitEx(temp, ",");
				if ((word.length == 0) || (word.length > 3)
						|| (XString.isEmpty(word[0]))) {
					continue;
				}
				boolean flag = false;
				if (new QueryBuilder(
						"select count(*) from ZCBadWord where Word = ?",
						word[0]).executeInt() > 0)
					flag = true;
				else {
					flag = false;
				}
				badword = new ZCBadWordSchema();
				if (flag) {
					String WordID = new QueryBuilder(
							"select ID from ZCBadWord where Word = ?", word[0])
							.executeOneValue().toString();
					badword.setID(WordID);
					badword.fill();
					badword.setWord(word[0]);
					if (word.length == 1) {
						badword.setReplaceWord("");
						badword.setTreeLevel(1L);
					} else if (word.length == 2) {
						badword.setReplaceWord(word[1]);
						badword.setTreeLevel(1L);
					} else if (word.length == 3) {
						badword.setReplaceWord(word[1]);
						if ((XString.isDigit(word[2]))
								&& (Integer.parseInt(word[2]) > 0)
								&& (Integer.parseInt(word[2]) < 4))
							badword.setTreeLevel(word[2]);
						else
							badword.setTreeLevel(1L);
					}
				} else {
					badword.setID(NoUtil.getMaxID("BadWordID"));
					badword.setWord(word[0]);
					if (word.length == 1) {
						badword.setReplaceWord("");
						badword.setTreeLevel(1L);
					} else if (word.length == 2) {
						badword.setReplaceWord(word[1]);
						badword.setTreeLevel(1L);
					} else if (word.length == 3) {
						badword.setReplaceWord(word[1]);
						if ((XString.isDigit(word[2]))
								&& (Integer.parseInt(word[2]) > 0)
								&& (Integer.parseInt(word[2]) < 4))
							badword.setTreeLevel(word[2]);
						else {
							badword.setTreeLevel(1L);
						}
					}
				}
				if (flag) {
					badword.setModifyTime(new Date());
					badword.setModifyUser(User.getUserName());
					trans.add(badword, OperateType.UPDATE);
				} else {
					badword.setAddTime(new Date());
					badword.setAddUser(User.getUserName());
					trans.add(badword, OperateType.INSERT);
				}
			}
		}

		if (trans.commit())
			this.response.setLogInfo(1, "导入成功");
		else
			this.response.setLogInfo(0, "导入失败");
	}

	public void del() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCBadWordSchema badWord = new ZCBadWordSchema();
		ZCBadWordSet set = badWord.query(new QueryBuilder("where id in (" + ids
				+ ")"));
		trans.add(set, OperateType.DELETE_AND_BACKUP);

		if (trans.commit()) {
			updateCache();
			this.response.setStatus(1);
		} else {
			this.response.setStatus(0);
			this.response.setMessage("操作数据库时发生错误!");
		}
	}
}

/*
 * com.xdarkness.cms.site.BadWord JD-Core Version: 0.6.0
 */