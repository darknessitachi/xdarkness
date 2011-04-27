package com.xdarkness.cms.workflow;

import java.util.Date;

import com.xdarkness.cms.dataservice.ColumnUtil;
import com.xdarkness.cms.document.Article;
import com.xdarkness.cms.document.MessageCache;
import com.xdarkness.platform.pub.NoUtil;
import com.xdarkness.schema.BZCArticleSchema;
import com.xdarkness.schema.ZCArticleLogSchema;
import com.xdarkness.schema.ZCArticleSchema;
import com.xdarkness.workflow.Context;
import com.xdarkness.workflow.Workflow;
import com.xdarkness.workflow.WorkflowAction;
import com.xdarkness.workflow.WorkflowAdapter;
import com.xdarkness.workflow.WorkflowUtil;
import com.xdarkness.framework.OperateType;
import com.xdarkness.framework.orm.SchemaUtil;
import com.xdarkness.framework.orm.data.DataTable;
import com.xdarkness.framework.sql.QueryBuilder;
import com.xdarkness.framework.util.LogUtil;
import com.xdarkness.framework.util.Mail;
import com.xdarkness.framework.util.Mapx;
import com.xdarkness.framework.util.StringFormat;

public class CMSWorkflowAdapter extends WorkflowAdapter {
	public void onActionExecute(Context context, WorkflowAction action) {
		ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
		articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
		articleLog.setArticleID(context.getInstance().getDataID());
		articleLog.setAction("WORKFLOW");
		articleLog.setActionDetail(action.getName());
		articleLog.setAddUser(User.getUserName());
		articleLog.setAddTime(new Date());
		context.getTransaction().add(articleLog, OperateType.INSERT);
	}

	public void onStepCreate(Context context) {
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(context.getInstance().getDataID());
		if (!article.fill()) {
			return;
		}
		BZCArticleSchema barticle = new BZCArticleSchema();
		for (int i = 0; i < article.getColumnCount(); i++) {
			barticle.setV(i, article.getV(i));
		}
		barticle.setBackupMemo("流程备份");
		barticle.setBackupNo(SchemaUtil.getBackupNo());
		barticle.setBackupOperator(User.getUserName());
		barticle.setBackupTime(new Date());

		Workflow wf = WorkflowUtil.findWorkflow(context.getStep()
				.getWorkflowID());
		String nodeName = wf.findNode(context.getStep().getNodeID()).getName();
		String actionName = WorkflowUtil.getActionNodeName(wf.getID(), context
				.getStep().getActionID());

		ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
		articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
		articleLog.setArticleID(context.getInstance().getDataID());
		articleLog.setAction("WORKFLOW");
		articleLog.setActionDetail("文档流转到步骤:" + nodeName);
		articleLog.setAddUser(User.getUserName());
		articleLog.setAddTime(new Date());
		context.getTransaction().add(articleLog, OperateType.INSERT);

		StringFormat sf = new StringFormat("标题为 ? 的文档己流转到步骤 ? 。");
		sf.add("<font class='lightred'>" + barticle.getTitle() + "</font>");
		sf.add("<font class='deepred'>" + nodeName + "</font>");
		String subject = sf.toString();

		sf = new StringFormat("您创建的标题为 ? 的文档，己于 ? 由 ? 执行 ?，现流转到步骤  ? 。");
		sf.add("<font class='lightred'>" + barticle.getTitle() + "</font>");
		sf.add("<font class='black'>" + DateUtil.getCurrentDateTime()
				+ "</font>");
		sf.add("<font class='deepred'>" + context.getStep().getAddUser()
				+ "</font>");
		sf.add("<font class='deepgreen'>" + actionName + "</font>");
		sf.add("<font class='deepred'>" + nodeName + "</font>");

		MessageCache.addMessage(context.getTransaction(), subject, sf
				.toString(), article.getAddUser(), "SYSTEM");

		context.getStep().setDataVersionID(barticle.getBackupNo());

		context.getTransaction().add(barticle, OperateType.INSERT);
	}

	public void notifyNextStep(Context context, String[] users) {
		Workflow wf = WorkflowUtil.findWorkflow(context.getStep()
				.getWorkflowID());
		String stepName = wf.findNode(context.getStep().getNodeID()).getName();
		StringFormat sf = new StringFormat("有流转到步骤 ?的文档待处理。");
		sf.add("<font class='lightred'>" + context.getValue("Title")
				+ "</font>");
		sf.add("<font class='deepred'>" + stepName + "</font>");
		String subject = sf.toString();

		sf = new StringFormat("标题为 ? 的文档，己于 ? 由 ? 执行 ?，现流转到步骤  ? ,您可以开始处理。");
		sf.add("<font class='lightred'>" + context.getValue("Title")
				+ "</font>");
		sf.add("<font class='black'>" + DateUtil.getCurrentDateTime()
				+ "</font>");
		sf.add("<font class='deepred'>" + context.getStep().getAddUser()
				+ "</font>");
		String actionName = WorkflowUtil.getActionNodeName(wf.getID(), context
				.getStep().getActionID());
		sf.add("<font class='deepgreen'>" + actionName + "</font>");
		sf.add("<font class='deepred'>" + stepName + "</font>");

		StringFormat mailSubjectSF = new StringFormat("文档?流转到?,请您处理");
		mailSubjectSF.add(context.getValue("Title"));
		mailSubjectSF.add(stepName);
		String subjectSubject = mailSubjectSF.toString();

		for (int i = 0; i < users.length; i++) {
			MessageCache.addMessage(context.getTransaction(), subject, sf
					.toString(), users[i], "SYSTEM");
			Mapx mailMap = new Mapx();

			if ("Y".equals(Config.getValue("Workflow.SendMail"))) {
				String userName = users[i];
				DataTable dt = new QueryBuilder(
						"select Email,RealName from ZDUser where UserName=?",
						userName).executeDataTable();
				if (dt.getRowCount() > 0) {
					String email = dt.getString(0, "email");
					if (XString.isNotEmpty(email)) {
						mailMap.put("ToUser", email);
						mailMap.put("Subject", subjectSubject);
						mailMap.put("Content", sf.toString());
						String flag = Mail.sendSimpleEmail(mailMap);
						if ("success".equals(flag))
							LogUtil.info("发送邮件成功：" + subjectSubject);
						else
							LogUtil.info("发送邮件失败：" + subjectSubject + " "
									+ flag);
					}
				}
			}
		}
	}

	public void onWorkflowDelete(Transaction tran, long workflowID) {
		tran
				.add(new QueryBuilder(
						"update ZCArticle set Status=? where exists (select 1 from ZWInstance where WorkflowID=? and ID=ZCArticle.WorkflowID)",
						20L, workflowID));

		tran.add(new QueryBuilder(
				"update ZCCatalog set Workflow=? where Workflow=?", null,
				workflowID));
	}

	public Mapx getVariables(String dataID, String dataVersionID) {
		ZCArticleSchema article = new ZCArticleSchema();
		article.setID(dataID);
		article.fill();
		Mapx map = article.toCaseIgnoreMapx();
		DataTable dt = ColumnUtil.getColumnValue("2", dataID);
		if ((dt != null) && (dt.getRowCount() > 0)) {
			map.putAll(dt.get(0).toCaseIgnoreMapx());
		}
		return map;
	}

	public boolean saveVariables(Context context) {
		ZCArticleSchema article = new ZCArticleSchema();
		article.setValue(context.getVariables());
		article.fill();
		Article
				.saveCustomColumn(context.getTransaction(), context
						.getVariables(), article.getCatalogID(), article
						.getID(), false);
		context.getTransaction().add(article, OperateType.UPDATE);
		return true;
	}
}

/*
 * com.xdarkness.cms.workflow.CMSWorkflowAdapter JD-Core Version: 0.6.0
 */