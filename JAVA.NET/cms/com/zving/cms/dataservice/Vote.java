 package com.zving.cms.dataservice;
 
 import com.zving.cms.datachannel.Deploy;
 import com.zving.cms.pub.CatalogUtil;
 import com.zving.cms.pub.SiteUtil;
 import com.zving.framework.Config;
 import com.zving.framework.Page;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.controls.TreeAction;
 import com.zving.framework.controls.TreeItem;
 import com.zving.framework.data.BlockingTransaction;
 import com.zving.framework.data.DataRow;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.DateUtil;
 import com.zving.framework.utility.FileUtil;
 import com.zving.framework.utility.Filter;
 import com.zving.framework.utility.HtmlUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.platform.Priv;
 import com.zving.platform.pub.NoUtil;
 import com.zving.platform.pub.OrderUtil;
 import com.zving.schema.ZCVoteItemSchema;
 import com.zving.schema.ZCVoteItemSet;
 import com.zving.schema.ZCVoteSchema;
 import com.zving.schema.ZCVoteSet;
 import com.zving.schema.ZCVoteSubjectSchema;
 import com.zving.schema.ZCVoteSubjectSet;
 import java.io.File;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 
 public class Vote extends Page
 {
   public static Mapx initDialog(Mapx params)
   {
     String ID = params.get("ID").toString();
     if (StringUtil.isEmpty(ID))
     {
       Date date = new Date();
       params.put("StartDate", DateUtil.toString(date));
       params.put("StartTime", DateUtil.toTimeString(date));
       params.put("IPLimit", HtmlUtil.codeToRadios("IPLimit", "YesOrNo", "N", false));
       params.put("Prop4", HtmlUtil.codeToRadios("Prop4", "YesOrNo", "Y", false));
       params.put("VerifyFlag", HtmlUtil.codeToRadios("VerifyFlag", "VerifyFlag", "N"));
       params.put("Width", "350");
       params.put("VoteType_0", "Checked");
     }
     else {
       ZCVoteSchema vote = new ZCVoteSchema();
       vote.setID(ID);
       vote.fill();
       params = vote.toMapx();
       params.put("IPLimit", HtmlUtil.codeToRadios("IPLimit", "YesOrNo", vote.getIPLimit(), false));
       params.put("Prop4", HtmlUtil.codeToRadios("Prop4", "YesOrNo", vote.getProp4(), false));
       params.put("VerifyFlag", HtmlUtil.codeToRadios("VerifyFlag", "VerifyFlag", vote.getVerifyFlag()));
       params.put("StartDate", DateUtil.toString(vote.getStartTime()));
       params.put("StartTime", DateUtil.toTimeString(vote.getStartTime()));
       params.put("EndDate", DateUtil.toString(vote.getEndTime()));
       params.put("EndTime", DateUtil.toTimeString(vote.getEndTime()));
       if (vote.getVoteCatalogID() == 0L) {
         params.put("VoteType_0", "Checked");
       } else {
         params.put("VoteType_1", "Checked");
         params.put("VoteCatalogName", CatalogUtil.getName(vote.getVoteCatalogID()));
       }
     }
     return params;
   }
 
   public static void treeDataBind(TreeAction ta) {
     Object obj = ta.getParams().get("SiteID");
     String siteID = Application.getCurrentSiteID();
     Object typeObj = ta.getParams().get("CatalogType");
     int catalogType = (typeObj != null) ? Integer.parseInt(typeObj.toString()) : 1;
     String parentTreeLevel = ta.getParams().getString("ParentLevel");
     String parentID = ta.getParams().getString("ParentID");
     DataTable dt = null;
     if (ta.isLazyLoad()) {
       QueryBuilder qb = new QueryBuilder(
         "select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel>? and innerCode like ? and exists (select 1 from ZCVote where ZCCatalog.id=RelaCatalogID) order by orderflag,innercode");
 
       qb.add(catalogType);
       qb.add(siteID);
       qb.add(parentTreeLevel);
       qb.add(CatalogUtil.getInnerCode(parentID) + "%");
       dt = qb.executeDataTable();
     } else {
       QueryBuilder qb = new QueryBuilder(
         "select ID,ParentID,TreeLevel,Name,SingleFlag,InnerCode,OrderFlag from ZCCatalog Where Type = ? and SiteID = ? and TreeLevel-1 <=? and exists (select 1 from ZCVote where ZCCatalog.id=RelaCatalogID) order by orderflag,innercode");
 
       qb.add(catalogType);
       qb.add(siteID);
       qb.add(ta.getLevel());
       dt = qb.executeDataTable();
     }
 
     String siteName = "文档库";
     dt = dt.filter(new Filter() {
       public boolean filter(Object obj) {
         DataRow dr = (DataRow)obj;
         return Priv.getPriv(User.getUserName(), "article", dr.getString("InnerCode"), "article_browse");
       }
     });
     ta.setRootText(siteName);
     ta.bindData(dt);
     List items = ta.getItemList();
     for (int i = 1; i < items.size(); ++i) {
       TreeItem item = (TreeItem)items.get(i);
       if ("Y".equals(item.getData().getString("SingleFlag")))
         item.setIcon("Icons/treeicon11.gif");
     }
   }
 
   public static Mapx JSCodeDialog(Mapx params)
   {
     String id = (String)params.get("ID");
     ZCVoteSchema vote = new ZCVoteSchema();
     vote.setID(id);
     vote.fill();
     String JSCode = "";
     JSCode = JSCode + "<div>调查：" + vote.getTitle() + "\n";
     JSCode = JSCode + "<!--" + vote.getTitle() + "-->\n";
     JSCode = JSCode + "<script language='javascript' src='" + 
       new StringBuffer(String.valueOf(Config.getContextPath())).append(Config.getValue("Statical.TargetDir")).append("/")
       .append(Application.getCurrentSiteAlias()).append("/js/vote_").append(vote.getID()).toString()
       .replaceAll("/+", "/") + 
       ".js'></script>";
     JSCode = JSCode + "\n</div>";
     params.put("Title", vote.getTitle());
     params.put("JSCode", JSCode);
     return params;
   }
 
   public void getJSCode() {
     Mapx map = JSCodeDialog(this.Request);
     $S("JSCode", map.getString("JSCode"));
   }
 
   public static void dg1DataBind(DataGridAction dga) {
     String RelaCatalogID = dga.getParam("CatalogID");
     if ((StringUtil.isEmpty(RelaCatalogID)) || (RelaCatalogID == null) || (RelaCatalogID.equalsIgnoreCase("null"))) {
       RelaCatalogID = "0";
     }
     QueryBuilder qb = new QueryBuilder(
       "select ZCVote.*,(SELECT Name from ZCCatalog where ZCCatalog.ID = ZCVote.RelaCatalogID) as CatalogName from ZCVote where SiteID = ? ", 
       Application.getCurrentSiteID());
     if (!RelaCatalogID.equals("0"))
       qb.append(" and RelaCatalogID = ?  order by ID desc", RelaCatalogID);
     else {
       qb.append("  order by RelaCatalogID asc,ID desc");
     }
     DataTable dt = qb.executeDataTable();
     dt = dt.filter(new Filter() {
       public boolean filter(Object obj) {
         if (Priv.getPriv(User.getUserName(), "site", Application.getCurrentSiteID(), "site_manage")) {
           return true;
         }
         DataRow dr = (DataRow)obj;
         String RelaCatalogID = dr.getString("RelaCatalogID");
         if ("0".equals(RelaCatalogID)) {
           return Priv.getPriv(User.getUserName(), "site", Application.getCurrentSiteID(), 
             "article_manage");
         }
         return Priv.getPriv(User.getUserName(), "article", CatalogUtil.getInnerCode(RelaCatalogID), 
           "article_modify");
       }
     });
     DataTable newdt = new DataTable(dt.getDataColumns(), null);
     for (int i = dga.getPageIndex() * dga.getPageSize(); (i < dt.getRowCount()) && 
       (i < (dga.getPageIndex() + 1) * dga.getPageSize()); )
     {
       newdt.insertRow(dt.getDataRow(i));
 
       ++i;
     }
 
     dga.setTotal(dt.getRowCount());
     newdt.decodeColumn("IPLimit", HtmlUtil.codeToMapx("YesOrNo"));
     for (int i = 0; i < newdt.getRowCount(); ++i) {
       if (StringUtil.isEmpty(newdt.getString(i, "CatalogName"))) {
         newdt.set(i, "CatalogName", "文档库");
       }
     }
     dga.bindData(newdt);
   }
 
   public static void dg2DataBind(DataGridAction dga) {
     QueryBuilder qb = new QueryBuilder("select * from ZCVotelog where VoteID = ? order by ID desc", dga
       .getParam("ID"));
     dga.setTotal(qb);
     DataTable dt = qb.executePagedDataTable(dga.getPageSize(), dga.getPageIndex());
     dt.insertColumn("OtherContents");
     for (int i = 0; i < dt.getRowCount(); ++i) {
       String[] resArr = StringUtil.splitEx(dt.getString(i, "Result"), "$|");
       StringBuffer sb = new StringBuffer();
       for (int j = 0; j < resArr.length - 1; ++j) {
         String[] strArr = StringUtil.splitEx(resArr[j], "$&");
         if (strArr.length > 1) {
           sb.append(strArr[1]);
           sb.append("，");
         }
       }
       if (StringUtil.splitEx(sb.toString(), "，").length > 1) {
         dt.set(i, "OtherContents", sb.substring(0, sb.length() - 1));
       }
       if (StringUtil.isEmpty(dt.getString(i, "AddUser"))) {
         dt.set(i, "AddUser", "匿名");
       }
     }
     dga.bindData(dt);
   }
 
   public void add() {
     String Code = $V("Code");
     QueryBuilder qb = new QueryBuilder("select count(*) from ZCVote where Code = ?", Code);
     if (qb.executeInt() > 0) {
       this.Response.setLogInfo(0, "数据库中已经有相同的调用代码，麻烦您填写另外的调用代码，以免出错！");
       return;
     }
     ZCVoteSchema vote = new ZCVoteSchema();
     vote.setID(NoUtil.getMaxID("VoteID"));
     vote.setTitle($V("Title"));
     vote.setCode(Code);
     vote.setTotal(0L);
     vote.setIPLimit($V("IPLimit"));
     vote.setVerifyFlag($V("VerifyFlag"));
     vote.setWidth($V("Width"));
     vote.setProp4($V("Prop4"));
     if ((StringUtil.isEmpty($V("StartDate"))) || (StringUtil.isEmpty($V("StartTime"))))
       vote.setStartTime(new Date());
     else {
       vote.setStartTime(DateUtil.parse($V("StartDate") + " " + $V("StartTime"), "yyyy-MM-dd HH:mm:ss"));
     }
     if ((StringUtil.isNotEmpty($V("EndDate"))) && (StringUtil.isNotEmpty($V("EndTime")))) {
       vote.setEndTime(DateUtil.parse($V("EndDate") + " " + $V("EndTime"), "yyyy-MM-dd HH:mm:ss"));
     }
     vote.setRelaCatalogID($V("RelaCatalogID"));
     if (StringUtil.isNotEmpty($V("VoteCatalogID"))) {
       vote.setVoteCatalogID($V("VoteCatalogID"));
     }
     if (vote.getVoteCatalogID() != 0L) {
       vote.setRelaCatalogID(vote.getVoteCatalogID());
     }
     vote.setSiteID(Application.getCurrentSiteID());
     vote.setAddTime(new Date());
     vote.setAddUser(User.getUserName());
     Transaction trans = new BlockingTransaction();
     trans.add(vote, 1);
     if ("1".equals($V("VoteType")))
     {
       ZCVoteSubjectSchema subject = new ZCVoteSubjectSchema();
       subject.setID(NoUtil.getMaxID("VoteSubjectID"));
       subject.setVoteID(vote.getID());
       subject.setType("Y");
       subject.setSubject(CatalogUtil.getName(vote.getVoteCatalogID()));
       subject.setOrderFlag(OrderUtil.getDefaultOrder());
       subject.setVoteCatalogID(vote.getVoteCatalogID());
       trans.add(subject, 1);
     }
     dealArticle(vote, trans);
     if (trans.commit())
       this.Response.setLogInfo(1, "新建调查成功！");
     else
       this.Response.setLogInfo(0, "新建调查失败！");
   }
 
   private static void dealArticle(ZCVoteSchema vote, Transaction tran)
   {
     DataTable subject = new QueryBuilder("select * from zcvotesubject where voteid=?", vote.getID())
       .executeDataTable();
     if (subject.getRowCount() > 0) {
       DataTable dt = new QueryBuilder("select ID,Title from ZCArticle where CatalogID=? and Status=?", vote
         .getVoteCatalogID(), 30L).executeDataTable();
       ZCVoteItemSchema item = new ZCVoteItemSchema();
       item.setVoteID(vote.getID());
       ZCVoteItemSet set = item.query();
       ZCVoteItemSet insertSet = new ZCVoteItemSet();
       for (int j = 0; j < dt.getRowCount(); ++j) {
         boolean flag = false;
         for (int i = 0; i < set.size(); ++i) {
           item = set.get(i);
           if (item.getVoteDocID() == dt.getInt(j, "ID")) {
             item.setItem(dt.getString(j, "Title"));
             flag = true;
             break;
           }
         }
         if (!flag) {
           item = new ZCVoteItemSchema();
           item.setID(NoUtil.getMaxID("VoteItemID"));
           item.setVoteID(subject.getString(0, "VoteID"));
           item.setSubjectID(subject.getString(0, "ID"));
           item.setItem(dt.getString(j, "Title"));
           item.setScore(0L);
           item.setItemType("0");
           item.setVoteDocID(dt.getInt(j, "ID"));
           item.setOrderFlag(OrderUtil.getDefaultOrder());
           insertSet.add(item);
         }
       }
       tran.add(set, 2);
       tran.add(insertSet, 1);
     }
   }
 
   public void edit() {
     String Code = $V("Code");
     ZCVoteSchema vote = new ZCVoteSchema();
     vote.setID($V("ID"));
     vote.fill();
     vote.setTitle($V("Title"));
     vote.setCode(Code);
     vote.setIPLimit($V("IPLimit"));
     vote.setVerifyFlag($V("VerifyFlag"));
     vote.setWidth($V("Width"));
     vote.setProp4($V("Prop4"));
     if ((StringUtil.isEmpty($V("StartDate"))) || (StringUtil.isEmpty($V("StartTime"))))
       vote.setStartTime(new Date());
     else {
       vote.setStartTime(DateUtil.parse($V("StartDate") + " " + $V("StartTime"), "yyyy-MM-dd HH:mm:ss"));
     }
     if ((StringUtil.isNotEmpty($V("EndDate"))) && (StringUtil.isNotEmpty($V("EndTime")))) {
       vote.setEndTime(DateUtil.parse($V("EndDate") + " " + $V("EndTime"), "yyyy-MM-dd HH:mm:ss"));
     }
     Transaction trans = new Transaction();
     vote.setSiteID(Application.getCurrentSiteID());
     if ((StringUtil.isNotEmpty($V("VoteCatalogID"))) && 
       (Long.parseLong($V("VoteCatalogID")) != vote.getVoteCatalogID())) {
       trans.add(new QueryBuilder("delete from zcvoteitem where VoteID=?", vote.getID()));
     }
     vote.setRelaCatalogID($V("RelaCatalogID"));
     if (StringUtil.isNotEmpty($V("VoteCatalogID"))) {
       vote.setVoteCatalogID($V("VoteCatalogID"));
     }
     if (vote.getVoteCatalogID() != 0L) {
       vote.setRelaCatalogID(vote.getVoteCatalogID());
     }
     trans.add(vote, 2);
     dealArticle(vote, trans);
     if (trans.commit()) {
       this.Response.setLogInfo(1, "修改调查成功！");
       generateJS(vote.getID());
     } else {
       this.Response.setLogInfo(0, "修改调查失败！");
     }
   }
 
   public static boolean generateJS(long ID) {
     return generateJS(ID);
   }
 
   public static boolean generateJS(String ID) {
     ZCVoteSchema vote = new ZCVoteSchema();
     vote.setID(ID);
     vote.fill();
     StringBuffer sb = new StringBuffer();
 
     Date now = new Date();
     boolean flag = true;
     if (now.before(vote.getStartTime())) {
       sb.append("document.write('对不起，此调查还没有开始！开始时间为：" + vote.getStartTime() + "，请您到时候再来投票');");
       flag = false;
     }
     if ((vote.getEndTime() != null) && (now.after(vote.getEndTime()))) {
       sb.append("document.write('对不起，此调查已经结束，不再接受投票！');");
       flag = false;
     }
     if (flag) {
       sb.append("document.write(\"<div id='vote_" + ID + 
         "' class='votecontainer' style='text-align:left' >\");\n");
       String serviceUrl = Config.getValue("ServicesContext");
       sb.append("document.write(\" <form id='voteForm_" + ID + "' name='voteForm_" + ID + "' action='" + 
         serviceUrl + Config.getValue("Vote.ActionURL") + "' method='post' target='_blank'>\");\n");
       sb.append("document.write(\" <input type='hidden' id='ID' name='ID' value='" + ID + "'>\");\n");
       sb.append("document.write(\" <input type='hidden' id='VoteFlag' name='VoteFlag' value='Y'>\");\n");
       sb.append("document.write(\" <dl>\");\n");
       ZCVoteSubjectSchema subject = new ZCVoteSubjectSchema();
       ZCVoteSubjectSet subjectSet = subject.query(new QueryBuilder(" where voteID =? order by OrderFlag,ID", vote.getID()));
       for (int i = 0; i < subjectSet.size(); ++i) {
         subject = subjectSet.get(i);
         String type = "radio";
         if ("D".equals(subject.getType())) {
           type = "checkbox";
         }
         sb.append("document.write(\"  <dt id='" + subject.getID() + "'>" + (i + 1) + "." + subject.getSubject() + 
           "</dt>\");\n");
         ZCVoteItemSchema item = new ZCVoteItemSchema();
         ZCVoteItemSet itemSet = item.query(
           new QueryBuilder("where voteID = ? and subjectID = ? order by OrderFlag,ID", 
           vote.getID(), subject.getID()));
         for (int j = 0; j < itemSet.size(); ++j) {
           item = itemSet.get(j);
           if ("0".equals(item.getItemType()))
             sb.append("document.write(\"<dd><label><input name='Subject_" + subject.getID() + "' type='" + 
               type + "' value='" + item.getID() + "' />" + item.getItem() + "</label></dd>\");\n");
           else if ("1".equals(item.getItemType())) {
             if ("W".equals(subject.getType()))
               sb.append("document.write(\"<dd><input id='Subject_" + subject.getID() + "' name='Subject_" + 
                 subject.getID() + "' type='text' value=''/></dd>\");\n");
             else
               sb.append("document.write(\"<dd><label><input name='Subject_" + subject.getID() + 
                 "' type='" + type + "' value='" + item.getID() + "' id='Subject_" + 
                 subject.getID() + "_Item_" + item.getID() + "_Button'/>" + item.getItem() + 
                 "</label><input id='Subject_" + subject.getID() + "_Item_" + item.getID() + 
                 "' name='Subject_" + subject.getID() + "_Item_" + item.getID() + 
                 "' type='text' value='' onClick=\\\"clickInput('Subject_" + subject.getID() + 
                 "_Item_" + item.getID() + "');\\\"/></dd>\");\n");
           }
           else if ("2".equals(item.getItemType())) {
             if ("W".equals(subject.getType()))
               sb
                 .append("document.write(\"<dd><textarea style='height:60px;width:500px;vertical-align:top;' id='Subject_" + 
                 subject.getID() + 
                 "' name='Subject_" + 
                 subject.getID() + 
                 "'></textarea></dd>\");\n");
             else {
               sb
                 .append("document.write(\"<dd><label><input name='Subject_" + 
                 subject.getID() + 
                 "' type='" + 
                 type + 
                 "' value='" + 
                 item.getID() + 
                 "' id='Subject_" + 
                 subject.getID() + 
                 "_Item_" + 
                 item.getID() + 
                 "_Button'/>" + 
                 item.getItem() + 
                 "</label><textarea style='height:60px;width:500px;vertical-align:top;' id='Subject_" + 
                 subject.getID() + "_Item_" + item.getID() + "' name='Subject_" + 
                 subject.getID() + "_Item_" + item.getID() + 
                 "'  onClick=\\\"clickInput('Subject_" + subject.getID() + "_Item_" + 
                 item.getID() + "');\\\"></textarea></dd>\");\n");
             }
           }
         }
       }
       if ("Y".equals(vote.getVerifyFlag())) {
         sb.append("document.write(\" </dl>\");\n");
         sb.append("document.write(\" <dl>\");\n");
         sb
           .append("document.write(\" <dd><img src='" + 
           Config.getContextPath() + 
           "AuthCode.jsp' alt='点击刷新验证码' height='16' align='absmiddle' style='cursor:pointer;' onClick=\\\"this.src='" + 
           Config.getContextPath() + 
           "AuthCode.jsp'\\\" />&nbsp; <input\tname='VerifyCode' type='text' style='width:60px' id='VerifyCode' class='inputText' onfocus='this.select();'/></dd>\");\n");
         sb.append("document.write(\" </dl>\");\n");
       }
       sb.append("document.write(\" <dl>\");\n");
       sb.append("document.write(\" <dd><input type='submit' value='提交' onclick='return checkVote(" + ID + 
         ");'>&nbsp;&nbsp" + "</dd>\");\n");
       sb.append("document.write(\" </dl>\");\n");
       sb.append("document.write(\" </form>\");\n");
       sb.append("document.write(\"</div>\");\n");
     }
 
     String file = new StringBuffer(String.valueOf(Config.getContextRealPath())).append(Config.getValue("Statical.TargetDir")).append("/")
       .append(SiteUtil.getAlias(vote.getSiteID())).append("/js/").toString()
       .replaceAll("//", "/") + 
       "vote_" + ID + ".js";
     String path = file.substring(0, file.lastIndexOf("/"));
     File pathDir = new File(path);
     if (!pathDir.exists()) {
       pathDir.mkdirs();
     }
     FileUtil.writeText(file, sb.toString());
 
     ArrayList deployList = new ArrayList();
     deployList.add(file);
     Deploy d = new Deploy();
     d.addJobs(vote.getSiteID(), deployList);
     return true;
   }
 
   public void del() {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       this.Response.setStatus(0);
       this.Response.setMessage("传入ID时发生错误!");
       return;
     }
     ZCVoteSet set = new ZCVoteSchema().query(new QueryBuilder("where id in (" + ids + ")"));
     ZCVoteSubjectSet subjectset = new ZCVoteSubjectSchema()
       .query(new QueryBuilder("where voteid in (" + ids + ")"));
     ZCVoteItemSet itemset = new ZCVoteItemSchema().query(new QueryBuilder("where voteid in (" + ids + ")"));
     Transaction trans = new Transaction();
     trans.add(set, 5);
     trans.add(subjectset, 5);
     trans.add(itemset, 5);
     trans.add(new QueryBuilder("delete from zcvotelog where voteid in (" + ids + ")"));
     if (trans.commit()) {
       this.Response.setMessage("删除成功!");
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("删除失败!");
     }
   }
 
   public void handStop() {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       this.Response.setStatus(0);
       this.Response.setMessage("传入ID时发生错误!");
       return;
     }
 
     Transaction trans = new Transaction();
     trans.add(new QueryBuilder("update zcvote set EndTime = ? where id in (" + ids + ") ", new Date()));
     if (trans.commit()) {
       this.Response.setMessage("手工终止成功!");
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("手工终止失败!");
     }
   }
 
   public static DataTable getVoteSubjects(Mapx params, DataRow parentDR) {
     String voteID = params.getString("ID");
     DataTable dt = new QueryBuilder("select * from ZCVoteSubject where voteID =? order by ID", voteID)
       .executeDataTable();
 
     return dt;
   }
 
   public static DataTable getVoteItems(Mapx params, DataRow parentDR) {
     String voteID = params.getString("ID");
     DataTable dt = new QueryBuilder("select * from ZCVoteItem where voteID =? and subjectID = ?", voteID, parentDR
       .getString("ID")).executeDataTable();
     dt.insertColumn("html");
     for (int i = 0; i < dt.getRowCount(); ++i) {
       String inputType = "";
       if ("D".equals(parentDR.getString("Type")))
         inputType = "checkbox";
       else {
         inputType = "radio";
       }
       String html = "";
       if ("0".equals(dt.getString(i, "ItemType")))
         html = "<label><input name='Subject_" + dt.getString(i, "SubjectID") + "' type='" + inputType + 
           "' value='" + dt.getString(i, "id") + "' />" + dt.getString(i, "item") + "</label>\n";
       else if ("1".equals(dt.getString(i, "ItemType"))) {
         if ("W".equals(parentDR.getString("Type")))
           html = "<input id='Subject_" + dt.getString(i, "SubjectID") + "' name='Subject_" + 
             dt.getString(i, "SubjectID") + "' type='text' value=''/>\n";
         else
           html = "<lable><input name='Subject_" + dt.getString(i, "SubjectID") + "' type='" + inputType + 
             "' value='" + dt.getString(i, "id") + "' id='Subject_" + dt.getString(i, "SubjectID") + 
             "_Item_" + dt.getString(i, "id") + "_Button'/>" + dt.getString(i, "item") + 
             "</lable><input id='Subject_" + dt.getString(i, "SubjectID") + "_Item_" + 
             dt.getString(i, "id") + "' name='Subject_" + dt.getString(i, "SubjectID") + "_Item_" + 
             dt.getString(i, "id") + "' type='text' value='' onClick=\"clickInput('Subject_" + 
             dt.getString(i, "SubjectID") + "_Item_" + dt.getString(i, "id") + "');\"/>\n";
       }
       else if ("2".equals(dt.getString(i, "ItemType"))) {
         if ("W".equals(parentDR.getString("Type")))
           html = "<textarea style=\"height:60px;width:400px;vertical-align:top;\" id='Subject_" + 
             dt.getString(i, "SubjectID") + "' name='Subject_" + dt.getString(i, "SubjectID") + 
             "'/></textarea>\n";
         else {
           html = "<lable><input name='Subject_" + dt.getString(i, "SubjectID") + "' type='" + inputType + 
             "' value='" + dt.getString(i, "id") + "' id='Subject_" + dt.getString(i, "SubjectID") + 
             "_Item_" + dt.getString(i, "id") + "_Button'/>" + dt.getString(i, "item") + 
             "</lable><textarea style=\"height:60px;width:400px;vertical-align:top;\" id='Subject_" + 
             dt.getString(i, "SubjectID") + "_Item_" + dt.getString(i, "id") + "' name='Subject_" + 
             dt.getString(i, "SubjectID") + "_Item_" + dt.getString(i, "id") + 
             "'  onClick=\"clickInput('Subject_" + dt.getString(i, "SubjectID") + "_Item_" + 
             dt.getString(i, "id") + "');\"/></textarea>\n";
         }
       }
       dt.set(i, "html", html);
     }
     return dt;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.dataservice.Vote
 * JD-Core Version:    0.5.4
 */