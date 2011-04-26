package com.xdarkness.cms.dataservice;

import java.util.Date;

import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCQuestionGroupSchema;
import com.xdarkness.schema.ZCQuestionGroupSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.TreeAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.Mapx;

public class QuestionTeam extends Page {
	public static void treeDataBind(TreeAction ta) {
		DataTable dt = new QueryBuilder(
				" select InnerCode,ParentInnerCode,TreeLevel,Name from ZCQuestionGroup")
				.executeDataTable();
		ta.setRootText("问题分类列表");
		ta.setParentIdentifierColumnName("ParentInnerCode");
		ta.setIdentifierColumnName("InnerCode");
		ta.setBranchIcon("Icons/treeicon12.gif");
		ta.setLeafIcon("Icons/treeicon12.gif");
		ta.bindData(dt);
	}

	public static Mapx initDialog(Mapx params) {
		String InnerCode = params.getString("InnerCode");
		String ParentInnerCode = params.getString("ParentInnerCode");
		if (XString.isNotEmpty(InnerCode)) {
			ZCQuestionGroupSchema group = new ZCQuestionGroupSchema();
			group.setInnerCode(InnerCode);
			if (!group.fill()) {
				return params;
			}
			Mapx map = group.toMapx();
			ParentInnerCode = map.getString("ParentInnerCode");
			if ((XString.isNotEmpty(ParentInnerCode))
					&& (!"0".equals(ParentInnerCode))) {
				String ParentName = new QueryBuilder(
						"select name from zcquestionGroup where InnerCode=?",
						ParentInnerCode).executeString();
				map.put("ParentName", ParentName);
				map.put("ParentInnerCode", ParentInnerCode);
			}
			return map;
		}
		String ParentName = new QueryBuilder(
				"select name as ParentName from zcquestiongroup where InnerCode=?",
				ParentInnerCode).executeString();
		params.put("ParentInnerCode", ParentInnerCode);
		params.put("ParentName", ParentName);
		return params;
	}

	public boolean verifyName(String Name) {
		int count = new QueryBuilder(
				"select count(*) from ZCQuestionGroup where Name=?", Name
						.trim()).executeInt();

		return (count > 0) && (XString.isNotEmpty(Name));
	}

	public void add() {
		String ParentInnerCode = $V("ParentInnerCode");
		ZCQuestionGroupSchema group = new ZCQuestionGroupSchema();
		Transaction trans = new Transaction();
		group.setValue(this.request);
		if (verifyName(group.getName())) {
			this.response.setLogInfo(0, "操作失败,分组名已经存在!");
			return;
		}
		if (XString.isNotEmpty(ParentInnerCode)) {
			group.setInnerCode(NoUtil.getMaxNo("GroupInnerCode",
					ParentInnerCode, 4));
			group.setParentInnerCode(ParentInnerCode);
			int parentTreelevel = new QueryBuilder(
					"select TreeLevel from ZCQuestionGroup where InnerCode=?",
					ParentInnerCode).executeInt();
			group.setTreeLevel(parentTreelevel + 1);
			trans.add(new QueryBuilder(
					"update ZCQuestionGroup set IsLeaf='Y' where InnerCode=?",
					ParentInnerCode));
		} else {
			group.setInnerCode(NoUtil.getMaxNo("GroupInnerCode", 4));
			group.setParentInnerCode("0");
			group.setTreeLevel(1);
		}
		group.setOrderFlag(0L);
		group.setAddUser(User.getUserName());
		Date currentDate = new Date();
		group.setAddTime(currentDate);
		trans.add(group, OperateType.INSERT);
		if (trans.commit())
			this.response.setLogInfo(1, "操作成功!");
		else
			this.response.setLogInfo(0, "操作失败!");
	}

	public void edit() {
		ZCQuestionGroupSchema group = new ZCQuestionGroupSchema();
		String innerCode = $V("InnerCode");
		group.setInnerCode(innerCode);
		group.fill();
		group.setName($V("Name"));
		group.setModifyTime(new Date());
		group.setModifyUser(User.getUserName());
		if (group.update())
			this.response.setLogInfo(1, "操作成功!");
		else
			this.response.setLogInfo(0, "操作失败!");
	}

	public void del() {
		String InnerCode = $V("InnerCode");
		if (XString.isEmpty(InnerCode)) {
			this.response.setStatus(0);
			this.response.setMessage("传入InnerCode时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCQuestionGroupSchema group = new ZCQuestionGroupSchema();
		ZCQuestionGroupSet questionset = group.query(new QueryBuilder(
				"where InnerCode = ?", InnerCode));
		trans.add(questionset, OperateType.DELETE_AND_BACKUP);
		if (trans.commit())
			this.response.setLogInfo(1, "操作成功!");
		else
			this.response.setLogInfo(0, "操作失败!");
	}
}

/*
 * com.xdarkness.cms.dataservice.QuestionTeam JD-Core Version: 0.6.0
 */