package com.xdarkness.cms.dataservice;

import java.util.Date;

import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.ZCQuestionSchema;
import com.xdarkness.schema.ZCQuestionSet;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.jaf.controls.grid.DataGridAction;
import com.xdarkness.framework.jaf.Page;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mapx;

public class Question extends Page {
	public static void dg1DataBind(DataGridAction dga) {
		String InnerCode = dga.getParam("QuestionInnerCode");
		QueryBuilder qb = new QueryBuilder(
				"select *  from zcquestion where 1=1 ");
		if (XString.isEmpty(InnerCode)) {
			LogUtil.info("没有得到InnerCode的值,历史InnerCode："
					+ dga.getParams().getString("Cookie.Ask.InnerCode"));
			qb.append(" and QuestionInnerCode = ?");
			InnerCode = dga.getParam("Cookie.Ask.InnerCode");
		} else {
			qb.append(" and QuestionInnerCode = ?");
		}
		qb.append(" order by AddTime desc");
		qb.add(InnerCode.trim());
		dga.bindData(qb);
	}

	public static Mapx initDialog(Mapx params) {
		String ID = params.getString("ID");
		String questionInnerCode = params.getString("QuestionInnerCode");
		if (XString.isNotEmpty(ID)) {
			ZCQuestionSchema question = new ZCQuestionSchema();
			question.setID(ID);
			if (!question.fill()) {
				return params;
			}
			Mapx map = question.toMapx();
			String questionName = new QueryBuilder(
					"select name from zcquestiongroup where InnerCode=?",
					questionInnerCode).executeString();
			map.put("QuestionInnerCode", questionInnerCode);
			map.put("QuestionName", questionName);
			return map;
		}
		String questionName = new QueryBuilder(
				"select name from zcquestiongroup where InnerCode=?",
				questionInnerCode).executeString();
		params.put("QuestionInnerCode", questionInnerCode);
		params.put("QuestionName", questionName);
		return params;
	}

	public void add() {
		String questionInnerCode = $V("QuestionInnerCode");
		if (XString.isEmpty(questionInnerCode)) {
			this.response.setLogInfo(0, "操作失败，请选择分类!");
			return;
		}
		ZCQuestionSchema question = new ZCQuestionSchema();
		question.setID(NoUtil.getMaxID("QuestionID"));
		question.setValue(this.request);
		question.setQuestionInnerCode(questionInnerCode);
		question.setAddUser(User.getUserName());
		Date currentDate = new Date();
		question.setAddTime(currentDate);
		if (question.insert())
			this.response.setLogInfo(1, "操作成功!");
		else
			this.response.setLogInfo(0, "操作失败!");
	}

	public void edit() {
		ZCQuestionSchema question = new ZCQuestionSchema();
		String id = $V("ID");
		question.setID(id);
		question.fill();
		question.setValue(this.request);
		question.setModifyTime(new Date());
		question.setModifyUser(User.getUserName());
		if (question.update())
			this.response.setLogInfo(1, "操作成功!");
		else
			this.response.setLogInfo(0, "操作失败!");
	}

	public void del() {
		String ids = $V("IDs");
		if (!XString.checkID(ids)) {
			this.response.setStatus(0);
			this.response.setMessage("传入ID时发生错误!");
			return;
		}
		Transaction trans = new Transaction();
		ZCQuestionSchema question = new ZCQuestionSchema();
		ZCQuestionSet questionset = question.query(new QueryBuilder(
				"where ID in (" + ids + ")"));
		trans.add(questionset, OperateType.DELETE_AND_BACKUP);
		if (trans.commit())
			this.response.setLogInfo(1, "操作成功!");
		else
			this.response.setLogInfo(0, "操作失败!");
	}
}

/*
 * com.xdarkness.cms.dataservice.Question JD-Core Version: 0.6.0
 */