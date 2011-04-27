 package com.zving.cms.workflow;
 
 import com.zving.cms.dataservice.ColumnUtil;
 import com.zving.cms.document.Article;
 import com.zving.cms.document.MessageCache;
 import com.zving.framework.Config;
 import com.zving.framework.User;
 import com.zving.framework.data.DataRow;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.orm.SchemaUtil;
 import com.zving.framework.utility.DateUtil;
 import com.zving.framework.utility.LogUtil;
 import com.zving.framework.utility.Mail;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringFormat;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.pub.NoUtil;
 import com.zving.schema.BZCArticleSchema;
 import com.zving.schema.ZCArticleLogSchema;
 import com.zving.schema.ZCArticleSchema;
 import com.zving.schema.ZWStepSchema;
 import com.zving.workflow.Context;
 import com.zving.workflow.Workflow;
 import com.zving.workflow.Workflow.Node;
 import com.zving.workflow.WorkflowAction;
 import com.zving.workflow.WorkflowAdapter;
 import com.zving.workflow.WorkflowInstance;
 import com.zving.workflow.WorkflowUtil;
 import java.util.Date;
 
 public class CMSWorkflowAdapter extends WorkflowAdapter
 {
   public void onActionExecute(Context context, WorkflowAction action)
   {
     ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
     articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
     articleLog.setArticleID(context.getInstance().getDataID());
     articleLog.setAction("WORKFLOW");
     articleLog.setActionDetail(action.getName());
     articleLog.setAddUser(User.getUserName());
     articleLog.setAddTime(new Date());
     context.getTransaction().add(articleLog, 1);
   }
 
   public void onStepCreate(Context context)
   {
     ZCArticleSchema article = new ZCArticleSchema();
     article.setID(context.getInstance().getDataID());
     if (!article.fill()) {
       return;
     }
     BZCArticleSchema barticle = new BZCArticleSchema();
     for (int i = 0; i < article.getColumnCount(); ++i) {
       barticle.setV(i, article.getV(i));
     }
     barticle.setBackupMemo("流程备份");
     barticle.setBackupNo(SchemaUtil.getBackupNo());
     barticle.setBackupOperator(User.getUserName());
     barticle.setBackupTime(new Date());
 
     Workflow wf = WorkflowUtil.findWorkflow(context.getStep().getWorkflowID());
     String nodeName = wf.findNode(context.getStep().getNodeID()).getName();
     String actionName = WorkflowUtil.getActionNodeName(wf.getID(), context.getStep().getActionID());
 
     ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
     articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
     articleLog.setArticleID(context.getInstance().getDataID());
     articleLog.setAction("WORKFLOW");
     articleLog.setActionDetail("文档流转到步骤:" + nodeName);
     articleLog.setAddUser(User.getUserName());
     articleLog.setAddTime(new Date());
     context.getTransaction().add(articleLog, 1);
 
     StringFormat sf = new StringFormat("标题为 ? 的文档己流转到步骤 ? 。");
     sf.add("<font class='lightred'>" + barticle.getTitle() + "</font>");
     sf.add("<font class='deepred'>" + nodeName + "</font>");
     String subject = sf.toString();
 
     sf = new StringFormat("您创建的标题为 ? 的文档，己于 ? 由 ? 执行 ?，现流转到步骤  ? 。");
     sf.add("<font class='lightred'>" + barticle.getTitle() + "</font>");
     sf.add("<font class='black'>" + DateUtil.getCurrentDateTime() + "</font>");
     sf.add("<font class='deepred'>" + context.getStep().getAddUser() + "</font>");
     sf.add("<font class='deepgreen'>" + actionName + "</font>");
     sf.add("<font class='deepred'>" + nodeName + "</font>");
 
     MessageCache.addMessage(context.getTransaction(), subject, sf.toString(), article.getAddUser(), "SYSTEM");
 
     context.getStep().setDataVersionID(barticle.getBackupNo());
 
     context.getTransaction().add(barticle, 1);
   }
 
   public void notifyNextStep(Context context, String[] users) {
     Workflow wf = WorkflowUtil.findWorkflow(context.getStep().getWorkflowID());
     String stepName = wf.findNode(context.getStep().getNodeID()).getName();
     StringFormat sf = new StringFormat("有流转到步骤 ?的文档待处理。");
     sf.add("<font class='lightred'>" + context.getValue("Title") + "</font>");
     sf.add("<font class='deepred'>" + stepName + "</font>");
     String subject = sf.toString();
 
     sf = new StringFormat("标题为 ? 的文档，己于 ? 由 ? 执行 ?，现流转到步骤  ? ,您可以开始处理。");
     sf.add("<font class='lightred'>" + context.getValue("Title") + "</font>");
     sf.add("<font class='black'>" + DateUtil.getCurrentDateTime() + "</font>");
     sf.add("<font class='deepred'>" + context.getStep().getAddUser() + "</font>");
     String actionName = WorkflowUtil.getActionNodeName(wf.getID(), context.getStep().getActionID());
     sf.add("<font class='deepgreen'>" + actionName + "</font>");
     sf.add("<font class='deepred'>" + stepName + "</font>");
 
     StringFormat mailSubjectSF = new StringFormat("文档?流转到?,请您处理");
     mailSubjectSF.add(context.getValue("Title"));
     mailSubjectSF.add(stepName);
     String subjectSubject = mailSubjectSF.toString();
 
     for (int i = 0; i < users.length; ++i) {
       MessageCache.addMessage(context.getTransaction(), subject, sf.toString(), users[i], "SYSTEM");
       Mapx mailMap = new Mapx();
 
       if ("Y".equals(Config.getValue("Workflow.SendMail"))) {
         String userName = users[i];
         DataTable dt = new QueryBuilder("select Email,RealName from ZDUser where UserName=?", userName)
           .executeDataTable();
         if (dt.getRowCount() > 0) {
           String email = dt.getString(0, "email");
           if (StringUtil.isNotEmpty(email)) {
             mailMap.put("ToUser", email);
             mailMap.put("Subject", subjectSubject);
             mailMap.put("Content", sf.toString());
             String flag = Mail.sendSimpleEmail(mailMap);
             if ("success".equals(flag))
               LogUtil.info("发送邮件成功：" + subjectSubject);
             else
               LogUtil.info("发送邮件失败：" + subjectSubject + " " + flag);
           }
         }
       }
     }
   }
 
   public void onWorkflowDelete(Transaction tran, long workflowID)
   {
     tran.add(
       new QueryBuilder("update ZCArticle set Status=? where exists (select 1 from ZWInstance where WorkflowID=? and ID=ZCArticle.WorkflowID)", 
       20L, workflowID));
 
     tran.add(new QueryBuilder("update ZCCatalog set Workflow=? where Workflow=?", null, workflowID));
   }
 
   public Mapx getVariables(String dataID, String dataVersionID)
   {
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
 
   public boolean saveVariables(Context context)
   {
     ZCArticleSchema article = new ZCArticleSchema();
     article.setValue(context.getVariables());
     article.fill();
     Article.saveCustomColumn(context.getTransaction(), context.getVariables(), article.getCatalogID(), article
       .getID(), false);
     context.getTransaction().add(article, 2);
     return true;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.workflow.CMSWorkflowAdapter
 * JD-Core Version:    0.5.4
 */