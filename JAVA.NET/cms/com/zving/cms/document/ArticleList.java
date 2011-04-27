 package com.zving.cms.document;
 
 import com.zving.cms.datachannel.Publisher;
 import com.zving.cms.pub.CMSCache;
 import com.zving.cms.pub.CatalogUtil;
 import com.zving.cms.pub.PubFun;
 import com.zving.cms.site.Catalog;
 import com.zving.framework.Page;
 import com.zving.framework.RequestImpl;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.controls.TreeAction;
 import com.zving.framework.data.DataColumn;
 import com.zving.framework.data.DataRow;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.messages.LongTimeTask;
 import com.zving.framework.orm.SchemaUtil;
 import com.zving.framework.utility.DateUtil;
 import com.zving.framework.utility.HtmlUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringFormat;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.platform.Priv;
 import com.zving.platform.UserLog;
 import com.zving.platform.pub.NoUtil;
 import com.zving.platform.pub.OrderUtil;
 import com.zving.schema.BZCArticleSchema;
 import com.zving.schema.BZCArticleSet;
 import com.zving.schema.ZCArticleLogSchema;
 import com.zving.schema.ZCArticleLogSet;
 import com.zving.schema.ZCArticleSchema;
 import com.zving.schema.ZCArticleSet;
 import com.zving.schema.ZCAttachmentRelaSchema;
 import com.zving.schema.ZCAttachmentRelaSet;
 import com.zving.schema.ZCAudioRelaSchema;
 import com.zving.schema.ZCAudioRelaSet;
 import com.zving.schema.ZCCatalogConfigSchema;
 import com.zving.schema.ZCCommentSchema;
 import com.zving.schema.ZCCommentSet;
 import com.zving.schema.ZCImageRelaSchema;
 import com.zving.schema.ZCImageRelaSet;
 import com.zving.schema.ZCVideoRelaSchema;
 import com.zving.schema.ZCVideoRelaSet;
 import com.zving.schema.ZCVoteItemSchema;
 import com.zving.schema.ZDColumnValueSchema;
 import com.zving.schema.ZDColumnValueSet;
 import com.zving.workflow.Workflow;
 import com.zving.workflow.Workflow.Node;
 import com.zving.workflow.WorkflowAction;
 import com.zving.workflow.WorkflowUtil;
 import java.util.Date;
 
 public class ArticleList extends Page
 {
   public static void magazineListDataBind(DataGridAction dga)
   {
     String catalogID = (String)dga.getParams().get("CatalogID");
     if (StringUtil.isEmpty(catalogID)) {
       catalogID = dga.getParams().getString("Cookie.DocList.LastMagazineCatalog");
       if ((StringUtil.isEmpty(catalogID)) || ("null".equals(catalogID))) {
         catalogID = "0";
       }
       dga.getParams().put("CatalogID", catalogID);
     }
     dg1DataBind(dga);
   }
 
   public static void dg1DataBind(DataGridAction dga) {
     String catalogID = (String)dga.getParams().get("CatalogID");
     if (StringUtil.isEmpty(catalogID)) {
       catalogID = dga.getParams().getString("Cookie.DocList.LastCatalog");
       if ((StringUtil.isEmpty(catalogID)) || ("null".equals(catalogID))) {
         catalogID = "0";
       }
       dga.getParams().put("CatalogID", catalogID);
     }
 
     if ((!catalogID.equals("0")) && 
       (!Application.getCurrentSiteID().equals(CatalogUtil.getSiteID(catalogID)))) {
       catalogID = "0";
       dga.getParams().put("CatalogID", catalogID);
     }
 
     if (!Priv.getPriv(User.getUserName(), "article", CatalogUtil.getInnerCode(catalogID), "article_browse")) {
       dga.bindData(new DataTable());
       return;
     }
 
     String keyword = (String)dga.getParams().get("Keyword");
     String startDate = (String)dga.getParams().get("StartDate");
     String endDate = (String)dga.getParams().get("EndDate");
     String listType = (String)dga.getParams().get("Type");
     if (StringUtil.isEmpty(listType)) {
       listType = "ALL";
     }
     String Table = "";
     if ("ARCHIVE".equals(listType))
       Table = "BZCArticle";
     else {
       Table = "ZCArticle";
     }
     QueryBuilder qb = new QueryBuilder(
       "select ID,Attribute,Title,AddUser,PublishDate,Addtime,Status,WorkFlowID,Type,TopFlag,OrderFlag,TitleStyle,TopDate,ReferTarget,ReferType,ReferSourceID from " + 
       Table + " where CatalogID=?");
     qb.add(catalogID);
     if (StringUtil.isNotEmpty(keyword)) {
       qb.append(" and title like ? ", "%" + keyword.trim() + "%");
     }
     if (StringUtil.isNotEmpty(startDate)) {
       startDate = startDate + " 00:00:00";
       qb.append(" and publishdate >= ? ", startDate);
     }
     if (StringUtil.isNotEmpty(endDate)) {
       endDate = endDate + " 23:59:59";
       qb.append(" and publishdate <= ? ", endDate);
     }
 
     if ("ADD".equals(listType))
       qb.append(" and adduser=?", User.getUserName());
     else if ("WORKFLOW".equals(listType))
       qb.append(" and status=?", 10);
     else if ("TOPUBLISH".equals(listType))
       qb.append(" and status=?", 20);
     else if ("PUBLISHED".equals(listType))
       qb.append(" and status=?", 30);
     else if ("OFFLINE".equals(listType))
       qb.append(" and status=?", 40);
     else if ("ARCHIVE".equals(listType)) {
       qb.append(" and BackUpMemo='Archive'");
     }
     qb.append(dga.getSortString());
 
     if (StringUtil.isNotEmpty(dga.getSortString()))
       qb.append(" ,orderflag desc");
     else {
       qb.append(" order by topflag desc,orderflag desc");
     }
     dga.setTotal(qb);
     DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
     if ((dt != null) && (dt.getRowCount() > 0)) {
       dt.decodeColumn("Status", Article.STATUS_MAP);
       dt.getDataColumn("PublishDate").setDateFormat("yy-MM-dd HH:mm");
     }
     setDetailWorkflowStatus(dt);
 
     Mapx attributemap = HtmlUtil.codeToMapx("ArticleAttribute");
     dt.insertColumn("Icon");
     if (dt.getRowCount() > 0) {
       for (int i = 0; i < dt.getRowCount(); ++i) {
         if (StringUtil.isNotEmpty(dt.getString(i, "Attribute"))) {
           String[] array = dt.getString(i, "Attribute").split(",");
           String attributeName = "";
           for (int j = 0; j < array.length; ++j) {
             if (j != array.length - 1)
               attributeName = attributeName + attributemap.getString(array[j]) + ",";
             else {
               attributeName = attributeName + attributemap.getString(array[j]);
             }
           }
           dt.set(i, "Title", StringUtil.htmlEncode(dt.getString(i, "Title")) + " <font class='lightred'> [" + 
             attributeName + "]</font>");
         }
 
         StringBuffer icons = new StringBuffer();
 
         String topFlag = dt.getString(i, "TopFlag");
         if ("1".equals(topFlag)) {
           String topdate = "永久置顶";
           if (StringUtil.isNotEmpty(dt.getString(i, "TopDate"))) {
             topdate = DateUtil.toString((Date)dt.get(i, "TopDate"), "yyyy-MM-dd HH:mm:ss");
           }
           icons.append("<img src='../Icons/icon13_stick.gif' title='有效期限: " + topdate + "'/>");
         }
 
         if (StringUtil.isNotEmpty(dt.getString(i, "ReferSourceID"))) {
           int referType = dt.getInt(i, "ReferType");
           if (referType == 1)
             icons.append("<img src='../Icons/icon13_copy.gif' title='复制'/>");
           else if (referType == 2) {
             icons.append("<img src='../Icons/icon13_refer.gif' title='引用'/>");
           }
         }
 
         if (StringUtil.isNotEmpty(dt.getString(i, "ReferTarget"))) {
           icons.append("<img src='../Icons/icon13_copyout.gif' title='复制源'/>");
         }
 
         dt.set(i, "Icon", icons.toString());
       }
     }
     dga.bindData(dt);
   }
 
   private static void setDetailWorkflowStatus(DataTable dt) {
     Mapx instanceIDMap = new Mapx();
     for (int i = 0; i < dt.getRowCount(); ++i) {
       if (10 == dt.getInt(i, "Status")) {
         instanceIDMap.put(dt.getString(i, "WorkflowID"), "1");
       }
     }
     String ids = StringUtil.join(instanceIDMap.keyArray());
     if ((!StringUtil.checkID(ids)) || (instanceIDMap.size() == 0)) {
       return;
     }
     QueryBuilder qb = new QueryBuilder(
       "select WorkflowID,NodeID,InstanceID,ActionID,State from ZWStep where (State=? or State=?) and InstanceID in (" + 
       ids + ") order by ID asc");
     qb.add("Unread");
     qb.add("Underway");
     DataTable stepTable = qb.executeDataTable();
     Mapx instanceNodeMap = new Mapx();
     Mapx actionMap = new Mapx();
     Mapx stateMap = new Mapx();
 
     for (int i = 0; i < stepTable.getRowCount(); ++i) {
       int flowID = stepTable.getInt(i, "WorkflowID");
       int nodeID = stepTable.getInt(i, "NodeID");
       Workflow.Node node = WorkflowUtil.findWorkflow(flowID).findNode(nodeID);
       instanceNodeMap.put(stepTable.getString(i, "InstanceID"), node);
 
       actionMap.put(stepTable.getString(i, "InstanceID"), stepTable.getString(i, "ActionID"));
       stateMap.put(stepTable.getString(i, "InstanceID"), stepTable.getString(i, "State"));
     }
 
     for (int i = 0; i < dt.getRowCount(); ++i)
       if (10 == dt.getInt(i, "Status")) {
         String instanceID = dt.getString(i, "WorkflowID");
         if (instanceNodeMap.containsKey(instanceID)) {
           Workflow.Node node = (Workflow.Node)instanceNodeMap.get(instanceID);
           String nodeName = node.getName();
           String nodeType = node.getType();
           dt.set(i, "StatusName", nodeName);
           if ("StartNode".equals(nodeType)) {
             WorkflowAction action = WorkflowUtil.findAction(node.getWorkflow().getID(), actionMap
               .getInt(instanceID));
             if (action != null)
               dt.set(i, "StatusName", action.getName());
           }
           else if ("Unread".equals(stateMap.getString(instanceID))) {
             dt.set(i, "StatusName", nodeName + "-未读");
           } else if ("Underway".equals(stateMap.getString(instanceID))) {
             dt.set(i, "StatusName", nodeName + "-处理中");
           }
         }
       }
   }
 
   public static void dialogDg1DataBind(DataGridAction dga)
   {
     String catalogID = (String)dga.getParams().get("CatalogID");
     if (StringUtil.isEmpty(catalogID)) {
       catalogID = "0";
     }
     String keyword = (String)dga.getParams().get("Keyword");
     QueryBuilder qb = new QueryBuilder("select ID,Title,author,publishDate,Addtime,catalogID,topflag,SiteID from ZCArticle where catalogid=?", 
       catalogID);
     if (StringUtil.isNotEmpty(keyword)) {
       qb.append(" and title like ? ", "%" + keyword.trim() + "%");
     }
     qb.append(dga.getSortString());
 
     dga.setTotal(qb);
     DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
     int size = dt.getRowCount();
     String[] columnValue = new String[dt.getRowCount()];
     for (int i = 0; i < size; ++i) {
       columnValue[i] = PubFun.getDocURL(dt.get(i));
     }
 
     dt.insertColumn("Link", columnValue);
     dga.bindData(dt);
   }
 
   public static void treeDataBind(TreeAction ta)
   {
     Catalog.treeDataBind(ta);
   }
 
   public static Mapx init(Mapx params) {
     String catalogID = (String)params.get("CatalogID");
     if (catalogID == null) {
       return params;
     }
     DataTable dtCatalog = new QueryBuilder("select siteid from zccatalog where id=?", catalogID).executeDataTable();
     long siteID = ((Long)dtCatalog.get(0, "siteid")).longValue();
     params.put("SiteID", siteID);
     params.put("ListType", (String)params.get("Type"));
     return params;
   }
 
   public void add() {
   }
 
   public void up() {
     String ids = $V("ArticleIDs");
     if (!StringUtil.checkID(ids)) {
       UserLog.log("Article", "UpArticle", "文章上线失败", this.Request.getClientIP());
       this.Response.setStatus(0);
       this.Response.setMessage("错误的参数!");
       return;
     }
     Transaction trans = new Transaction();
     dealArticleHistory(ids, trans, "UP", "上线处理");
     trans.add(
       new QueryBuilder("update zcarticle set Status =30,PublishDate = ?,DownlineDate = '2999-12-31 23:59:59' where status = 40 and id in(" + 
       ids + ")", new Date()));
     DataTable dt = new QueryBuilder("select Title from ZCArticle where status = 40 and id in (" + 
       ids + ")").executeDataTable();
     if (trans.commit()) {
       upTask(ids);
       StringBuffer logs = new StringBuffer("文章:");
       if (dt.getRowCount() > 0) {
         logs.append(dt.get(0, "Title"));
         if (dt.getRowCount() > 1) {
           logs.append(" 等，共" + dt.getRowCount() + "篇");
         }
       }
       UserLog.log("Article", "UpArticle", logs + "上线成功", this.Request.getClientIP());
       this.Response.setStatus(1);
     }
     else {
       dt = new QueryBuilder("select Title from ZCArticle where id in (" + ids + ")").executeDataTable();
       StringBuffer logs = new StringBuffer("文章:");
       if (dt.getRowCount() > 0) {
         logs.append(dt.get(0, "Title"));
         if (dt.getRowCount() > 1) {
           logs.append(" 等，共" + dt.getRowCount() + "篇");
         }
       }
       UserLog.log("Article", "UpArticle", logs + "上线失败", this.Request.getClientIP());
 
       this.Response.setStatus(0);
     }
   }
 
   private long upTask(String ids) {
     LongTimeTask ltt = new LongTimeTask(ids) { private final String val$ids;
 
       public void execute() { Publisher p = new Publisher();
         ZCArticleSchema site = new ZCArticleSchema();
         ZCArticleSet set = site.query(
           new QueryBuilder("where status = 30 and id in (" + 
           this.val$ids + ")"));
         if ((set != null) && (set.size() > 0)) {
           p.publishArticle(set, false, this);
           p.publishCatalog(set.get(0).getCatalogID(), false, false);
           setPercent(100);
         } }
 
     };
     ltt.setUser(User.getCurrent());
     ltt.start();
     return ltt.getTaskID();
   }
 
   public static void dealArticleHistory(String ids, Transaction trans, String dealName, String dealDetail) {
     ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
     String[] idarr = ids.split(",");
     for (int i = 0; i < idarr.length; ++i) {
       articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
       articleLog.setArticleID(idarr[i]);
       articleLog.setAction(dealName);
       articleLog.setActionDetail(dealDetail);
       articleLog.setAddUser(User.getUserName());
       articleLog.setAddTime(new Date());
       trans.add((ZCArticleLogSchema)articleLog.clone(), 1);
     }
   }
 
   public void down() {
     String ids = $V("ArticleIDs");
     if (!StringUtil.checkID(ids)) {
       UserLog.log("Article", "DownArticle", "文章下线失败", this.Request.getClientIP());
       this.Response.setStatus(0);
       this.Response.setMessage("错误的参数!");
       return;
     }
     Date now = new Date();
 
     ZCArticleSchema article = new ZCArticleSchema();
     ZCArticleSet set = article.query(new QueryBuilder("where id in(" + ids + ")"));
     for (int i = 0; i < set.size(); ++i) {
       article = set.get(i);
 
       ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(article.getCatalogID());
       if (("Y".equals(config.getBranchManageFlag())) && (!"admin".equals(User.getUserName()))) {
         String branchInnerCode = article.getBranchInnerCode();
         if ((StringUtil.isNotEmpty(branchInnerCode)) && (!User.getBranchInnerCode().equals(branchInnerCode))) {
           this.Response.setStatus(0);
           this.Response.setMessage("发生错误：您没有操作文档“" + article.getTitle() + "”的权限！");
           return;
         }
       }
     }
 
     Transaction trans = new Transaction();
     dealArticleHistory(ids, trans, "DOWN", "下线处理");
     trans.add(
       new QueryBuilder("update zcarticle set Status = 40,TopFlag='0',DownlineDate = ?,modifyTime=? where status = 30 and id in(" + 
       ids + ")", now, now));
     if (trans.commit()) {
       DataTable dt = new QueryBuilder("select Title from ZCArticle where id in (" + ids + ")").executeDataTable();
       StringBuffer logs = new StringBuffer("文章:");
       if (dt.getRowCount() > 0) {
         logs.append(dt.get(0, "Title"));
         if (dt.getRowCount() > 1) {
           logs.append(" 等，共" + dt.getRowCount() + "篇");
         }
       }
       UserLog.log("Article", "DownArticle", logs + "下线成功", this.Request.getClientIP());
 
       ZCArticleSchema site = new ZCArticleSchema();
       set = site.query(new QueryBuilder("where status = 40 and id in (" + ids + ")"));
       downTask(set);
 
       this.Response.setStatus(1);
     }
     else {
       DataTable dt = new QueryBuilder("select Title from ZCArticle where id in (" + ids + ")").executeDataTable();
       StringBuffer logs = new StringBuffer("文章:");
       if (dt.getRowCount() > 0) {
         logs.append(dt.get(0, "Title"));
         if (dt.getRowCount() > 1) {
           logs.append(" 等，共" + dt.getRowCount() + "篇");
         }
       }
       UserLog.log("Article", "DownArticle", logs + "下线失败", this.Request.getClientIP());
 
       this.Response.setStatus(0);
     }
   }
 
   private long downTask(ZCArticleSet set) {
     LongTimeTask ltt = new LongTimeTask(set) { private final ZCArticleSet val$set;
 
       public void execute() { Publisher p = new Publisher();
         if ((this.val$set != null) && (this.val$set.size() > 0)) {
           p.deletePubishedFile(this.val$set);
 
           Mapx catalogMap = new Mapx();
           for (int k = 0; k < this.val$set.size(); ++k) {
             catalogMap.put(this.val$set.get(k).getCatalogID(), this.val$set.get(k).getCatalogID());
             String pid = CatalogUtil.getParentID(this.val$set.get(k).getCatalogID());
             while ((StringUtil.isNotEmpty(pid)) && (!"null".equals(pid)) && (!"0".equals(pid))) {
               catalogMap.put(pid, pid);
               pid = CatalogUtil.getParentID(pid);
             }
 
           }
 
           Object[] vs = catalogMap.valueArray();
           for (int j = 0; j < catalogMap.size(); ++j) {
             String listpage = CatalogUtil.getData(vs[j].toString()).getString("ListPage");
             if ((StringUtil.isEmpty(listpage)) || ("0".equals(listpage)) || ("-1".equals(listpage))) {
               listpage = "20";
             }
             p.publishCatalog(Long.parseLong(vs[j].toString()), false, false, Integer.parseInt(listpage));
             setPercent(getPercent() + 5);
             setCurrentInfo("发布栏目页面");
           }
         }
         setPercent(100); }
 
     };
     ltt.setUser(User.getCurrent());
     ltt.start();
     return ltt.getTaskID();
   }
 
   private boolean checkArticleStatus(ZCArticleSet set, String allowArticleStatus)
   {
     DataTable dt = set.toDataTable();
     dt.insertColumn("ActionID");
     Mapx map = new Mapx();
     for (int i = 0; i < dt.getRowCount(); ++i) {
       if (10 == dt.getInt(i, "Status")) {
         map.put(dt.getString(i, "WorkflowID"), "1");
       }
     }
 
     String ids = StringUtil.join(map.keyArray());
     if ((!StringUtil.checkID(ids)) || (map.size() == 0)) {
       for (int i = 0; i < dt.getRowCount(); ++i)
         dt.set(i, "ActionID", "0");
     }
     else {
       QueryBuilder qb = new QueryBuilder("select InstanceID,ActionID from ZWStep where InstanceID in (" + ids + 
         ") order by ID asc");
       DataTable stepTable = qb.executeDataTable();
       Mapx stepMap = stepTable.toMapx(0, 1);
       for (int i = 0; i < dt.getRowCount(); ++i) {
         if (10 == dt.getInt(i, "Status")) {
           String id = dt.getString(i, "WorkflowID");
           if (stepMap.containsKey(id))
             dt.set(i, "ActionID", stepMap.get(id));
           else
             dt.set(i, "ActionID", "0");
         }
         else {
           dt.set(i, "ActionID", "0");
         }
       }
     }
 
     if (!allowArticleStatus.startsWith(",")) {
       allowArticleStatus = "," + allowArticleStatus;
     }
 
     if (!allowArticleStatus.endsWith(",")) {
       allowArticleStatus = allowArticleStatus + ",";
     }
 
     for (int i = 0; (dt != null) && (i < dt.getRowCount()); ++i) {
       if (!checkArticleStatus(dt.get(i), allowArticleStatus)) {
         return false;
       }
     }
     return true;
   }
 
   private boolean checkArticleStatus(DataRow dr, String notDeleteArticleStatus) {
     if ((StringUtil.isNotEmpty(notDeleteArticleStatus)) && 
       (notDeleteArticleStatus.indexOf("," + dr.getString("Status") + ",") != -1))
     {
       return (dr.getInt("Status") == 10) && 
         ("0".equals(dr.getString("ActionID")));
     }
 
     return true;
   }
 
   public void del()
   {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       UserLog.log("Article", "DelArticle", "删除文章失败", this.Request.getClientIP());
       this.Response.setStatus(0);
       this.Response.setMessage("错误的参数!");
       return;
     }
 
     Transaction trans = new Transaction();
     ZCArticleSchema article = new ZCArticleSchema();
     ZCArticleSet set = article.query(
       new QueryBuilder("where id in (" + ids + 
       ") or id in (select id from zcarticle where refersourceid in (" + ids + ") )"));
 
     String notDeleteArticleStatus = "30,10,20";
 
     if (!checkArticleStatus(set, notDeleteArticleStatus)) {
       UserLog.log("Article", "DelArticle", "已发布的文档或流转中的文档不能删除,请下线后再删除", this.Request.getClientIP());
       this.Response.setStatus(0);
       this.Response.setMessage("已发布的文档或流转中的文档不能删除,请下线后再删除!");
       return;
     }
 
     trans.add(set, 5);
     Mapx catalogMap = new Mapx();
     StringBuffer logs = new StringBuffer("删除文章:");
 
     for (int i = 0; i < set.size(); ++i) {
       article = set.get(i);
 
       ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(article.getCatalogID());
       if (("Y".equals(config.getBranchManageFlag())) && (!"admin".equals(User.getUserName()))) {
         String branchInnerCode = article.getBranchInnerCode();
         if ((StringUtil.isNotEmpty(branchInnerCode)) && (!User.getBranchInnerCode().equals(branchInnerCode))) {
           this.Response.setStatus(0);
           this.Response.setMessage("发生错误：您没有操作文档“" + article.getTitle() + "”的权限！");
           return;
         }
       }
 
       String sqlArticleCount = "update zccatalog set total = total-1,isdirty=1 where innercode in(" + 
         CatalogUtil.getParentCatalogCode(article.getCatalogInnerCode()) + ")";
       trans.add(new QueryBuilder(sqlArticleCount));
 
       StringFormat sf = new StringFormat("标题为 ? 的文档己被删除");
       sf.add("<font class='red'>" + article.getTitle() + "</font>");
       String subject = sf.toString();
 
       sf = new StringFormat("您创建的标题为 ? 的文档，己于 ? 由 ? 删除。");
       sf.add("<font class='red'>" + article.getTitle() + "</font>");
       sf.add("<font class='red'>" + DateUtil.getCurrentDateTime() + "</font>");
       sf.add("<font class='red'>" + User.getUserName() + "</font>");
 
       MessageCache.addMessage(trans, subject, sf.toString(), new String[] { article.getAddUser() }, "SYSTEM", 
         false);
 
       ZDColumnValueSchema colValue = new ZDColumnValueSchema();
       colValue.setRelaID(article.getID());
       colValue.setRelaType("2");
       ZDColumnValueSet colValueSet = colValue.query();
       trans.add(colValueSet, 5);
 
       ZCImageRelaSchema imageRela = new ZCImageRelaSchema();
       imageRela.setRelaID(article.getID());
       imageRela.setRelaType("ArticleImage");
       ZCImageRelaSet imageRelaSet = imageRela.query();
       trans.add(imageRelaSet, 5);
 
       ZCVideoRelaSchema videoRela = new ZCVideoRelaSchema();
       videoRela.setRelaID(article.getID());
       videoRela.setRelaType("ArticleVideo");
       ZCVideoRelaSet videoRelaSet = videoRela.query();
       trans.add(videoRelaSet, 5);
 
       ZCAttachmentRelaSchema attachmentRela = new ZCAttachmentRelaSchema();
       attachmentRela.setRelaID(article.getID());
       attachmentRela.setRelaType("ArticleAttach");
       ZCAttachmentRelaSet attachmentRelaSet = attachmentRela.query();
       trans.add(attachmentRelaSet, 5);
 
       ZCAudioRelaSchema audioRela = new ZCAudioRelaSchema();
       audioRela.setRelaID(article.getID());
       ZCAudioRelaSet audioRelaSet = audioRela.query();
       trans.add(audioRelaSet, 5);
 
       ZCCommentSchema comment = new ZCCommentSchema();
       comment.setRelaID(article.getID());
       ZCCommentSet commentSet = comment.query();
       trans.add(commentSet, 5);
 
       ZCVoteItemSchema voteitem = new ZCVoteItemSchema();
       voteitem.setVoteDocID(article.getID());
       trans.add(voteitem.query(), 5);
 
       ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
       articleLog.setArticleID(article.getID());
       ZCArticleLogSet artilceLogSet = articleLog.query();
 
       articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
       articleLog.setAction("DELETE");
       articleLog.setActionDetail("删除。删除原因：" + $V("DeleteReason"));
       articleLog.setAddUser(User.getUserName());
       articleLog.setAddTime(new Date());
       artilceLogSet.add(articleLog);
       trans.add(artilceLogSet, 5);
 
       catalogMap.put(article.getCatalogID(), article.getCatalogInnerCode());
 
       if (article.getWorkFlowID() != 0L) {
         WorkflowUtil.deleteInstance(trans, article.getWorkFlowID());
       }
     }
 
     if (set.size() > 0) {
       logs.append(set.get(0).getTitle());
       if (set.size() > 1) {
         logs.append(" 等，共" + set.size() + "篇");
       }
     }
     if (trans.commit()) {
       downTask(set);
       UserLog.log("Article", "DelArticle", logs + "成功", this.Request.getClientIP());
       this.Response.setStatus(1);
     } else {
       UserLog.log("Article", "DelArticle", logs + "失败", this.Request.getClientIP());
       this.Response.setStatus(0);
       this.Response.setMessage("操作数据库时发生错误!");
     }
   }
 
   public void topublish() {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       UserLog.log("Article", "ToPublishArticle", "转为待发布操作失败,ids:" + ids, this.Request
         .getClientIP());
       this.Response.setStatus(0);
       this.Response.setMessage("传入IDs参数错误!");
       return;
     }
     ZCArticleSchema article = new ZCArticleSchema();
     ZCArticleSet set = article.query(
       new QueryBuilder("where id in(" + ids + 
       ") or id in(select id from zcarticle where refersourceid in (" + ids + ") )"));
     String log = "转为待发布操作成功";
     ZCArticleSet updateset = new ZCArticleSet();
     for (int i = 0; i < set.size(); ++i) {
       article = set.get(i);
       if ((((article.getStatus() == 0L) || (article.getStatus() == 60L))) && 
         (article.getWorkFlowID() == 0L)) {
         article.setStatus(20L);
         updateset.add(article);
       } else if (article.getWorkFlowID() != 0L) {
         log = "此文档在工作流转中，不能转为待发布";
       } else {
         log = "只有‘初稿’和‘重新编辑’的文章转为待发布状态了";
       }
     }
     updateset.update();
     UserLog.log("Article", "ToPublishArticle", "转为待发布操作成功,ids:" + ids, this.Request.getClientIP());
     this.Response.setLogInfo(1, log);
   }
 
   public void publish() {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       UserLog.log("Article", "PublishArticle", "文章发布失败", this.Request.getClientIP());
       this.Response.setStatus(0);
       this.Response.setMessage("传入IDs参数错误!");
       return;
     }
     ZCArticleSchema article = new ZCArticleSchema();
     ZCArticleSet set = article.query(new QueryBuilder(" where id in (" + ids + ")"));
 
     ZCArticleSet referset = article.query(new QueryBuilder(" where refersourceid in (" + ids + ")"));
     if (referset.size() > 0) {
       for (int i = 0; i < referset.size(); ++i) {
         String catalogInnerCode = referset.get(i).getCatalogInnerCode();
         boolean hasPriv = Priv.getPriv(User.getUserName(), "article", catalogInnerCode, "article_manage");
         String workflow = CatalogUtil.getWorkflow(referset.get(i).getCatalogID());
 
         if ((hasPriv) && (StringUtil.isEmpty(workflow))) {
           set.add(referset.get(i));
         }
       }
     }
     StringBuffer logs = new StringBuffer("发布文章: ");
     if (set.size() > 0) {
       logs.append(set.get(0).getTitle());
       if (set.size() > 1) {
         logs.append(" 等，共" + set.size() + "篇");
       }
     }
     UserLog.log("Article", "PublishArticle", logs + "成功", this.Request.getClientIP());
 
     this.Response.setStatus(1);
     long id = publishSetTask(set);
     $S("TaskID", id);
   }
 
   public void changeToPublish() {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       this.Response.setStatus(0);
       this.Response.setMessage("传入IDs参数错误!");
       return;
     }
     BZCArticleSchema barticle = new BZCArticleSchema();
     BZCArticleSet bset = barticle.query(new QueryBuilder(" where id in (" + ids + ") and backupmemo='Archive' "));
     Transaction trans = new Transaction();
     for (int i = 0; i < bset.size(); ++i) {
       barticle = bset.get(i);
       ZCArticleSchema article = new ZCArticleSchema();
       SchemaUtil.copyFieldValue(barticle, article);
       article.setStatus(30L);
       article.setArchiveDate(CatalogUtil.getArchiveTime(barticle.getCatalogID()));
       trans.add(article, 1);
       trans.add(barticle, 3);
     }
     if (trans.commit()) {
       StringBuffer logs = new StringBuffer("从归档文章转为已发布文章: ");
       if (bset.size() > 0) {
         logs.append(bset.get(0).getTitle());
         if (bset.size() > 1) {
           logs.append(" 等，共" + bset.size() + "篇");
         }
       }
       UserLog.log("Article", "PublishArticle", logs + "成功", this.Request.getClientIP());
       this.Response.setLogInfo(1, "转为已发布成功");
     } else {
       this.Response.setLogInfo(0, "转为已发布失败");
     }
   }
 
   private long publishSetTask(ZCArticleSet set) {
     LongTimeTask ltt = new LongTimeTask(set) { private final ZCArticleSet val$set;
 
       public void execute() { Publisher p = new Publisher();
         setPercent(5);
         p.publishArticle(this.val$set, this);
         setPercent(100); }
 
     };
     ltt.setUser(User.getCurrent());
     ltt.start();
     return ltt.getTaskID();
   }
 
   private long publishTask(ZCArticleSet set) {
     LongTimeTask ltt = new LongTimeTask(set) { private final ZCArticleSet val$set;
 
       public void execute() { Publisher p = new Publisher();
         if ((this.val$set != null) && (this.val$set.size() > 0)) {
           p.publishCatalog(this.val$set.get(0).getCatalogID(), false, true);
           setPercent(100);
         } }
 
     };
     ltt.setUser(User.getCurrent());
     ltt.start();
     return ltt.getTaskID();
   }
 
   public void move()
   {
     String articleIDs = $V("ArticleIDs");
     if (!StringUtil.checkID(articleIDs)) {
       this.Response.setError("操作数据库时发生错误!");
       return;
     }
 
     String catalogID = $V("CatalogID");
     if (!StringUtil.checkID(catalogID)) {
       this.Response.setError("传入CatalogID时发生错误!");
       return;
     }
 
     Transaction trans = new Transaction();
     ZCArticleSchema srcArticle = new ZCArticleSchema();
     ZCArticleSet set = srcArticle.query(new QueryBuilder("where id in (" + articleIDs + ")"));
     long srcCatalogID = 0L;
 
     String[] srcArticleIDs = (String[])null;
     if (set.size() > 0) {
       srcArticleIDs = new String[set.size()];
       for (int i = 0; i < set.size(); ++i) {
         srcArticleIDs[i] = String.valueOf(set.get(i).getID());
       }
     }
     StringBuffer logs = new StringBuffer("转移文章:");
     for (int i = 0; i < set.size(); ++i) {
       ZCArticleSchema article = set.get(i);
 
       ZCCatalogConfigSchema config = CMSCache.getCatalogConfig(article.getCatalogID());
       if (("Y".equals(config.getBranchManageFlag())) && (!"admin".equals(User.getUserName()))) {
         String branchInnerCode = article.getBranchInnerCode();
         if ((StringUtil.isNotEmpty(branchInnerCode)) && (!User.getBranchInnerCode().equals(branchInnerCode))) {
           this.Response.setStatus(0);
           this.Response.setMessage("发生错误：您没有操作文档“" + article.getTitle() + "”的权限！");
           return;
         }
       }
       srcCatalogID = article.getCatalogID();
       String destCatalogID = catalogID;
       if ((article.getStatus() == 10L) && 
         (!"admin".equals(User.getUserName()))) {
         this.Response.setStatus(0);
         this.Response.setMessage("文档处于流转中，不能进行转移操作！");
         return;
       }
       String ReferTarget = article.getReferTarget();
       if (StringUtil.isNotEmpty(ReferTarget)) {
         ReferTarget = "," + ReferTarget + ",";
         ReferTarget = StringUtil.replaceEx(ReferTarget, "," + catalogID + ",", ",");
         ReferTarget = ReferTarget.substring(0, ReferTarget.length() - 1);
         article.setReferTarget(ReferTarget);
       }
       article.setClusterTarget(null);
 
       trans.add(new QueryBuilder("update zccatalog set total = total+1 where id=?", destCatalogID));
       trans.add(new QueryBuilder("update zccatalog set total = total-1 where id=?", srcCatalogID));
       article.setCatalogInnerCode(CatalogUtil.getInnerCode(catalogID));
       article.setCatalogID(catalogID);
       article.setOrderFlag(OrderUtil.getDefaultOrder());
       String workflowID = CatalogUtil.getWorkflow(destCatalogID);
       if (StringUtil.isNotEmpty(workflowID)) {
         article.setWorkFlowID(null);
         trans.add(
           new QueryBuilder("delete from zwstep where exists (select * from zwinstance where dataid=? and id=zwstep.instanceID)", 
           article.getID()));
         trans.add(new QueryBuilder("delete from zwinstance where dataid=?", article.getID()));
       }
       article.setStatus(0L);
 
       trans.add(article, 2);
 
       ZCArticleLogSchema articleLog = new ZCArticleLogSchema();
       articleLog.setArticleID(article.getID());
       articleLog.setID(NoUtil.getMaxID("ArticleLogID"));
       articleLog.setAction("MOVE");
       articleLog.setActionDetail("转移。从" + CatalogUtil.getName(srcCatalogID) + "转移到" + 
         CatalogUtil.getName(destCatalogID) + "。");
       articleLog.setAddUser(User.getUserName());
       articleLog.setAddTime(new Date());
       trans.add(articleLog, 1);
     }
     if (set.size() > 0) {
       logs.append(set.get(0).getTitle());
       if (set.size() > 1) {
         logs.append(" 等，共" + set.size() + "篇");
       }
     }
     if (trans.commit()) {
       Publisher p = new Publisher();
 
       p.deletePubishedFile(set);
 
       UserLog.log("Article", "MoveArticle", logs + "成功", this.Request.getClientIP());
       this.Response.setMessage("转移成功");
     } else {
       UserLog.log("Article", "MoveArticle", logs + "失败", this.Request.getClientIP());
       this.Response.setError("操作数据库时发生错误!");
     }
   }
 
   public void copy()
   {
     String articleIDs = $V("ArticleIDs");
     if (!StringUtil.checkID(articleIDs)) {
       UserLog.log("Article", "CopyArticle", "复制文章失败", this.Request.getClientIP());
       this.Response.setStatus(0);
       this.Response.setMessage("传入ArticleID时发生错误!");
       return;
     }
     String catalogIDs = $V("CatalogIDs");
     if (!StringUtil.checkID(catalogIDs)) {
       UserLog.log("Article", "CopyArticle", "复制文章失败", this.Request.getClientIP());
       this.Response.setStatus(0);
       this.Response.setMessage("传入CatalogID时发生错误!");
       return;
     }
     if ((catalogIDs.indexOf("\"") >= 0) || (catalogIDs.indexOf("'") >= 0)) {
       UserLog.log("Article", "CopyArticle", "复制文章失败", this.Request.getClientIP());
       this.Response.setStatus(0);
       this.Response.setMessage("传入CatalogID时发生错误!");
       return;
     }
     Transaction trans = new Transaction();
     ZCArticleSchema article = new ZCArticleSchema();
     ZCArticleSet set = article.query(new QueryBuilder("where id in (" + articleIDs + ")"));
 
     for (int i = 0; i < set.size(); ++i) {
       article = set.get(i);
       if ((article.getStatus() == 10L) && 
         (!"admin".equals(User.getUserName()))) {
         this.Response.setStatus(0);
         this.Response.setMessage("文档处于流转中，不能进行复制操作！");
         return;
       }
 
       DataTable customData = new QueryBuilder(
         "select ColumnCode,TextValue from zdcolumnvalue where relaid = ?", article.getID()).executeDataTable();
       for (int j = 0; j < customData.getRowCount(); ++j) {
         this.Request.put("_C_" + customData.getString(j, "ColumnCode"), customData.getString(j, "TextValue"));
       }
       this.Request.put("ReferTarget", catalogIDs);
       Article.copy(this.Request, trans, article);
 
       article.setReferTarget(catalogIDs);
       article.setReferType($V("ReferType"));
     }
 
     StringBuffer logs = new StringBuffer("复制文章:");
     if (set.size() > 0) {
       logs.append(set.get(0).getTitle());
       if (set.size() > 1) {
         logs.append(" 等，共" + set.size() + "篇");
       }
 
       trans.add(set, 2);
     }
     if (trans.commit()) {
       UserLog.log("Article", "CopyArticle", logs + "成功", this.Request.getClientIP());
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       UserLog.log("Article", "CopyArticle", logs + "失败", this.Request.getClientIP());
       this.Response.setMessage("操作数据库时发生错误!");
     }
   }
 
   public void sortArticle()
   {
     String target = $V("Target");
     String orders = $V("Orders");
     String type = $V("Type");
     String catalogID = $V("CatalogID");
     boolean topFlag = "true".equals($V("TopFlag"));
     if ((!StringUtil.checkID(target)) && (!StringUtil.checkID(orders))) {
       return;
     }
     Transaction tran = new Transaction();
     if (topFlag) {
       QueryBuilder qb = new QueryBuilder("update ZCArticle set TopFlag='1' where OrderFlag in (" + orders + ")");
       tran.add(qb);
     } else {
       QueryBuilder qb = new QueryBuilder("update ZCArticle set TopFlag='0' where OrderFlag in (" + orders + ")");
       tran.add(qb);
     }
     OrderUtil.updateOrder("ZCArticle", "OrderFlag", type, target, orders, null, tran);
     if (tran.commit()) {
       String id = catalogID;
       LongTimeTask ltt = new LongTimeTask(id) { private final String val$id;
 
         public void execute() { Publisher p = new Publisher();
           String listpage = CatalogUtil.getData(this.val$id).getString("ListPage");
           if ((StringUtil.isEmpty(listpage)) || ("0".equals(listpage)) || ("-1".equals(listpage))) {
             listpage = "20";
           }
           p.publishCatalog(Long.parseLong(this.val$id), false, false, Integer.parseInt(listpage));
           setPercent(100); }
 
       };
       ltt.setUser(User.getCurrent());
       ltt.start();
 
       this.Response.setMessage("操作成功");
     } else {
       this.Response.setError("操作失败");
     }
   }
 
   public void setTop() {
     String ids = $V("IDs");
     String topDate = $V("TopDate");
     if (!StringUtil.checkID(ids)) {
       this.Response.setStatus(0);
       this.Response.setMessage("错误的参数!");
       return;
     }
 
     QueryBuilder qb = new QueryBuilder("update ZCArticle set TopFlag='1' where id in (" + ids + ")");
     if (StringUtil.isNotEmpty(topDate)) {
       if (new Date().compareTo(DateUtil.parseDateTime(topDate + " " + $V("TopTime"))) >= 0) {
         this.Response.setLogInfo(0, "置顶有效期限应大于当前时间!");
         return;
       }
       qb = new QueryBuilder("update ZCArticle set TopFlag='1',TopDate='" + topDate + " " + $V("TopTime") + 
         "' where id in (" + ids + ")");
     }
     qb.executeNoQuery();
     Transaction trans = new Transaction();
     dealArticleHistory(ids, trans, "SETTOP", "置顶处理");
     if (!trans.commit()) {
       this.Response.setMessage("置顶操作时记录操作历史信息出错！");
     }
     DataTable dt = new QueryBuilder("select Title from ZCArticle where id in (" + ids + ")").executeDataTable();
     StringBuffer logs = new StringBuffer("置顶文章:");
     if (dt.getRowCount() > 0) {
       logs.append(dt.get(0, "Title"));
       if (dt.getRowCount() > 1) {
         logs.append(" 等，共" + dt.getRowCount() + "篇");
       }
     }
     UserLog.log("Article", "TopArticle", logs + "成功", this.Request.getClientIP());
     this.Response.setLogInfo(1, "置顶成功");
 
     ZCArticleSchema article = new ZCArticleSchema();
     ZCArticleSet set = article.query(new QueryBuilder(" where id in (" + ids + ")"));
     LongTimeTask ltt = new LongTimeTask(set) { private final ZCArticleSet val$set;
 
       public void execute() { Publisher p = new Publisher();
         p.publishCatalog(this.val$set.get(0).getCatalogID(), false, false);
         setPercent(100); }
 
     };
     ltt.setUser(User.getCurrent());
     ltt.start();
   }
 
   public void setNotTop() {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       this.Response.setStatus(0);
       this.Response.setMessage("错误的参数!");
       return;
     }
     QueryBuilder qb = new QueryBuilder("update ZCArticle set TopFlag='0' where id in (" + ids + ")");
     qb.executeNoQuery();
     Transaction trans = new Transaction();
     dealArticleHistory(ids, trans, "SETNOTTOP", "取消置顶");
     if (!trans.commit()) {
       this.Response.setMessage("取消置顶操作时记录操作历史信息出错！");
     }
     DataTable dt = new QueryBuilder("select Title from ZCArticle where id in (" + ids + ")").executeDataTable();
     StringBuffer logs = new StringBuffer("取消置顶文章:");
     if (dt.getRowCount() > 0) {
       logs.append(dt.get(0, "Title"));
       if (dt.getRowCount() > 1) {
         logs.append(" 等，共" + dt.getRowCount() + "篇");
       }
     }
     UserLog.log("Article", "NotTopArticle", logs + "成功", this.Request.getClientIP());
     this.Response.setLogInfo(1, "取消置顶成功");
 
     ZCArticleSchema article = new ZCArticleSchema();
     ZCArticleSet set = article.query(new QueryBuilder(" where id in (" + ids + ")"));
     LongTimeTask ltt = new LongTimeTask(set) { private final ZCArticleSet val$set;
 
       public void execute() { Publisher p = new Publisher();
         p.publishCatalog(this.val$set.get(0).getCatalogID(), false, false);
         setPercent(100); }
 
     };
     ltt.setUser(User.getCurrent());
     ltt.start();
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.document.ArticleList
 * JD-Core Version:    0.5.4
 */