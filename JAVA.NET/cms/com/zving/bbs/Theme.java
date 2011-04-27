 package com.zving.bbs;
 
 import com.zving.bbs.admin.ForumScore;
 import com.zving.framework.Ajax;
 import com.zving.framework.RequestImpl;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.cache.CacheManager;
 import com.zving.framework.controls.DataListAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.DateUtil;
 import com.zving.framework.utility.HtmlUtil;
 import com.zving.framework.utility.LogUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.UserLog;
 import com.zving.schema.ZCForumMemberSchema;
 import com.zving.schema.ZCForumMemberSet;
 import com.zving.schema.ZCForumSchema;
 import com.zving.schema.ZCPostSchema;
 import com.zving.schema.ZCPostSet;
 import com.zving.schema.ZCThemeSchema;
 import com.zving.schema.ZCThemeSet;
 import java.util.Date;
 
 public class Theme extends Ajax
 {
   public static void getList(DataListAction dla)
   {
     String postType = dla.getParam("postType");
     String addTime = dla.getParam("addtime");
     String orderBy = dla.getParam("orderby");
     String ascdesc = dla.getParam("ascdesc");
     String ForumID = dla.getParam("ForumID");
     String SiteID = dla.getParam("SiteID");
 
     QueryBuilder qb = new QueryBuilder("select * from ZCTheme where Status='Y' and VerifyFlag='Y' and ForumID=?", 
       ForumID);
 
     if ((!StringUtil.isEmpty(addTime)) && (!"0".equals(addTime))) {
       Date date = new Date(System.currentTimeMillis() - Long.parseLong(addTime));
       qb.append(" and addTime >?", date);
     }
 
     if ((!StringUtil.isEmpty(postType)) && 
       (postType.equals("best"))) {
       qb.append(" and Best='Y'");
     }
 
     if (!StringUtil.isEmpty(orderBy)) {
       if (!StringUtil.checkID(orderBy)) {
         LogUtil.warn("可能的SQL注入,Theme.getList:" + orderBy);
         return;
       }
       qb.append(" order by TopTheme desc," + orderBy);
       if ((!StringUtil.isEmpty(ascdesc)) && 
         ("DESC".equals(ascdesc)))
         qb.append(" desc ");
     }
     else {
       qb.append(" order by TopTheme desc,OrderTime desc");
     }
     DataTable dt = qb.executePagedDataTable(dla.getPageSize(), dla.getPageIndex());
     String[] types = { "原创", "投票", "活动", "商品" };
     for (int i = 0; i < dt.getRowCount(); ++i) {
       if (dt.get(i, "TopTheme").equals("Y"))
         dt.set(i, "TopTheme", "<img src='Images/top.gif' />");
       else {
         dt.set(i, "TopTheme", "");
       }
       if (dt.get(i, "Best").equals("Y"))
         dt.set(i, "Best", "<img src='Images/best.gif' />");
       else {
         dt.set(i, "Best", "");
       }
       dt.set(i, "type", types[java.lang.Integer.parseInt(dt.getString(i, "Type"))]);
     }
     dt.insertColumn("Box");
     if (ForumUtil.operateThemeButton(ForumID, SiteID).length() == 0) {
       for (int i = 0; i < dt.getRowCount(); ++i) {
         dt.set(i, "Box", "none");
       }
     }
     dla.setTotal(qb);
     dla.bindData(dt);
   }
 
   public static Mapx init(Mapx params) {
     String SiteID = params.getString("SiteID");
     String ForumID = params.getString("ForumID");
     ZCForumSchema forum = ForumCache.getForum(ForumID);
     Mapx map = forum.toMapx();
     map.put("SiteID", SiteID);
     map.put("AddUser", User.getUserName());
     map.put("Operate", ForumUtil.operateThemeButton(ForumID, SiteID));
     map.put("Priv", ForumUtil.initPriv(ForumID, SiteID));
 
     if (ForumUtil.isSendTheme(SiteID, ForumID)) {
       map.put("NewThemeButton", "<a href='ThemeAdd.jsp?ForumID=" + ForumID + "&SiteID=" + SiteID + "'>发表新话题</a>");
     }
     if (ForumUtil.operateThemeButton(ForumID, SiteID).length() == 0) {
       map.put("box", "none");
     }
     map.put("BBSName", ForumUtil.getBBSName(SiteID));
     return map;
   }
 
   public void del() {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       LogUtil.warn("可能的SQL注入,Theme.del:" + ids);
       return;
     }
     ForumScore forumScore = new ForumScore($V("SiteID"));
     ZCThemeSet set = new ZCThemeSchema().query(new QueryBuilder("where ID in (" + ids + ")"));
     ZCForumMemberSet userSet = new ZCForumMemberSet();
     ZCForumSchema forum = ForumCache.getForum(set.get(0).getForumID());
     Transaction trans = new Transaction();
 
     StringBuffer themeLog = new StringBuffer("删除主题：");
     for (int i = 0; i < set.size(); ++i) {
       ZCForumMemberSchema user = new ZCForumMemberSchema();
       user.setUserName(set.get(i).getAddUser());
       int index = ForumUtil.getValueOfMemberSet(userSet, user);
       set.get(i).setStatus("N");
       if (index == -1) {
         user.fill();
         user.setForumScore(user.getForumScore() + forumScore.DeleteTheme);
         userSet.add(user);
       } else {
         userSet.get(index).setForumScore(userSet.get(index).getForumScore() + forumScore.DeleteTheme);
       }
       themeLog.append(set.get(i).getSubject() + ",");
     }
     forum.setThemeCount(forum.getThemeCount() - set.size());
     forum.setPostCount(forum.getPostCount() - set.size());
     ForumUtil.changeLastTheme(forum, ids);
 
     ZCPostSet postSet = new ZCPostSchema().query(new QueryBuilder("where ThemeID in (" + ids + ")"));
     for (int i = 0; i < postSet.size(); ++i) {
       postSet.get(i).setInvisible("N");
     }
 
     ForumUtil.userGroupChange(userSet);
     trans.add(forum, 2);
     trans.add(userSet, 2);
     trans.add(set, 2);
     trans.add(postSet, 2);
     if (trans.commit()) {
       CacheManager.set("Forum", "Forum", forum.getID(), forum);
       UserLog.log("Forum", "DelTheme", themeLog + "成功", this.Request.getClientIP());
       this.Response.setLogInfo(1, "删除成功");
     } else {
       UserLog.log("UserLog.FORUM", "DelTheme", themeLog + "失败", this.Request.getClientIP());
       this.Response.setLogInfo(0, "操作失败");
     }
   }
 
   public static Mapx initMoveDialog(Mapx params) {
     String ForumID = params.getString("ForumID");
     QueryBuilder qb = new QueryBuilder("select Name,ID from ZCForum where ParentID<>0 and SiteID=?", params
       .getString("SiteID"));
     DataTable dt = qb.executeDataTable();
     params.put("Forum", HtmlUtil.dataTableToOptions(dt, ForumID));
     return params;
   }
 
   public void move()
   {
     Transaction trans = new Transaction();
     ZCForumSchema originalForum = new ZCForumSchema();
     ZCForumSchema targetForum = new ZCForumSchema();
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       LogUtil.warn("可能的SQL注入,Theme.move:" + ids);
       return;
     }
     ZCThemeSet themeSet = new ZCThemeSchema().query(new QueryBuilder("where ID in (" + ids + ")"));
     ZCPostSet postSet = new ZCPostSchema().query(new QueryBuilder("where ThemeID in(" + ids + ")"));
     originalForum = ForumCache.getForum(themeSet.get(0).getForumID());
 
     StringBuffer themeLog = new StringBuffer("移动主题：");
     for (int i = 0; i < themeSet.size(); ++i) {
       themeSet.get(i).setForumID($V("ForumID"));
       themeLog.append(themeSet.get(i).getSubject() + ",");
     }
     for (int i = 0; i < postSet.size(); ++i) {
       postSet.get(i).setForumID(themeSet.get(i).getForumID());
     }
     targetForum = ForumCache.getForum($V("ForumID"));
     ForumUtil.changeLastTheme(originalForum, targetForum, ids);
     originalForum.setThemeCount(originalForum.getThemeCount() - themeSet.size());
     originalForum.setPostCount(originalForum.getPostCount() - themeSet.size());
     targetForum.setThemeCount(targetForum.getThemeCount() + themeSet.size());
     targetForum.setPostCount(targetForum.getPostCount() + themeSet.size());
     trans.add(originalForum, 2);
     trans.add(targetForum, 2);
     trans.add(postSet, 2);
     trans.add(themeSet, 2);
     if (trans.commit()) {
       CacheManager.set("Forum", "Forum", originalForum.getID(), originalForum);
       CacheManager.set("Forum", "Forum", targetForum.getID(), targetForum);
       UserLog.log("Forum", "MoveTheme", themeLog + "成功", this.Request.getClientIP());
       this.Response.setLogInfo(1, "移动成功");
     } else {
       UserLog.log("Forum", "MoveTheme", themeLog + "失败", this.Request.getClientIP());
       this.Response.setLogInfo(0, "操作失败");
     }
   }
 
   public static Mapx initBrightDialog(Mapx params)
   {
     Mapx map = new Mapx();
     map.put("blue", "蓝色");
     map.put("yellow", "黄色");
     map.put("green", "绿色");
     map.put("red", "红色");
     params.put("Color", HtmlUtil.mapxToRadios("Color", map));
     return params;
   }
 
   public void brightSave()
   {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       LogUtil.warn("可能的SQL注入,Theme.brightSave:" + ids);
       return;
     }
     ForumScore forumScore = new ForumScore($V("SiteID"));
     ZCThemeSet set = new ZCThemeSchema().query(new QueryBuilder("where ID in (" + ids + ")"));
     ZCForumMemberSet userSet = new ZCForumMemberSet();
     Transaction trans = new Transaction();
 
     StringBuffer themeLog = new StringBuffer("设置或取消高亮显示主题：");
     for (int i = 0; i < set.size(); ++i) {
       ZCForumMemberSchema user = new ZCForumMemberSchema();
       user.setUserName(set.get(i).getAddUser());
 
       int index = ForumUtil.getValueOfMemberSet(userSet, user);
       if (index == -1) {
         user.fill();
         if ((StringUtil.isEmpty(set.get(i).getBright())) && (StringUtil.isNotEmpty($V("Color")))) {
           user.setForumScore(user.getForumScore() + forumScore.Bright);
         }
         userSet.add(user);
       }
       else if ((StringUtil.isEmpty(set.get(i).getBright())) && (StringUtil.isNotEmpty($V("Color")))) {
         userSet.get(index).setForumScore(userSet.get(index).getForumScore() + forumScore.Bright);
       }
 
       set.get(i).setBright($V("Color"));
       themeLog.append(set.get(i).getSubject() + ",");
     }
     ForumUtil.userGroupChange(userSet);
     trans.add(userSet, 2);
     trans.add(set, 2);
     if (trans.commit()) {
       UserLog.log("Forum", "BrightTheme", themeLog + "成功", this.Request.getClientIP(), trans);
       this.Response.setLogInfo(1, "操作成功");
     } else {
       UserLog.log("Forum", "BrightTheme", themeLog + "失败", this.Request.getClientIP(), trans);
       this.Response.setLogInfo(0, "操作失败");
     }
   }
 
   public static Mapx initUpOrDownDialog(Mapx params)
   {
     Mapx map = new Mapx();
     map.put("up", "提升");
     map.put("down", "下沉");
     params.put("UpOrDown", HtmlUtil.mapxToRadios("UpOrDown", map));
     return params;
   }
 
   public void upOrDownSave()
   {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       LogUtil.warn("可能的SQL注入,Theme.upOrDownSave:" + ids);
       return;
     }
     ForumScore forumScore = new ForumScore($V("SiteID"));
     ZCThemeSet set = new ZCThemeSchema().query(new QueryBuilder("where ID in (" + ids + ")"));
     ZCForumMemberSet userSet = new ZCForumMemberSet();
     Transaction trans = new Transaction();
 
     StringBuffer themeLog = new StringBuffer();
     for (int i = 0; i < set.size(); ++i) {
       if (StringUtil.isEmpty($V("UpOrDown"))) {
         this.Response.setLogInfo(0, "有未选择的选项");
         return;
       }
 
       ZCForumMemberSchema user = new ZCForumMemberSchema();
       user.setUserName(set.get(i).getAddUser());
       int index = ForumUtil.getValueOfMemberSet(userSet, user);
       if ($V("UpOrDown").equals("up")) {
         set.get(i).setOrderTime(new Date());
         if (index == -1) {
           user.fill();
           QueryBuilder qb = new QueryBuilder(
             "select ID from ZCTheme where ForumID=? order by OrderTime desc", set.get(i).getForumID());
           DataTable dt = qb.executePagedDataTable(1, 0);
           if (dt.getInt(0, 0) != set.get(i).getID()) {
             user.setForumScore(user.getForumScore() + forumScore.UpTheme);
           }
 
           userSet.add(user);
         } else {
           userSet.get(index).setForumScore(userSet.get(index).getForumScore() + forumScore.UpTheme);
         }
       }
       else if ($V("UpOrDown").equals("down")) {
         QueryBuilder qb = new QueryBuilder("select OrderTime from ZCTheme where ForumID=? order by OrderTime", 
           set.get(i).getForumID());
         DataTable dt = qb.executePagedDataTable(1, 0);
         long time = DateUtil.parseDateTime(dt.getString(0, "OrderTime")).getTime() - 1000L;
         set.get(i).setOrderTime(new Date(time));
 
         if (index == -1) {
           user.fill();
           user.setForumScore(user.getForumScore() + forumScore.DownTheme);
           userSet.add(user);
         } else {
           userSet.get(index).setForumScore(userSet.get(index).getForumScore() + forumScore.DownTheme);
         }
       }
       themeLog.append(set.get(i).getSubject() + ",");
     }
 
     ForumUtil.userGroupChange(userSet);
     trans.add(userSet, 2);
     trans.add(set, 2);
     if (trans.commit()) {
       if ($V("UpOrDown").equals("up"))
         UserLog.log("Forum", "UpTheme", "提升主题：" + themeLog + "成功", this.Request.getClientIP());
       else {
         UserLog.log("Forum", "DownTheme", "下沉主题：" + themeLog + "成功", this.Request.getClientIP());
       }
       this.Response.setLogInfo(1, "操作成功");
     } else {
       if ($V("UpOrDown").equals("down"))
         UserLog.log("Forum", "UpTheme", "提升主题：" + themeLog + "失败", this.Request.getClientIP());
       else {
         UserLog.log("Forum", "DownTheme", "下沉主题：" + themeLog + "失败", this.Request.getClientIP());
       }
       this.Response.setLogInfo(0, "操作失败");
     }
   }
 
   public static Mapx initTopThemeDialog(Mapx params) {
     Mapx map = new Mapx();
     map.put("top", "置顶");
     map.put("back", "解除置顶");
     params.put("TopTheme", HtmlUtil.mapxToRadios("TopTheme", map));
     return params;
   }
 
   public void topTheme()
   {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       LogUtil.warn("可能的SQL注入,Theme.topTheme:" + ids);
       return;
     }
     ForumScore forumScore = new ForumScore($V("SiteID"));
     Transaction trans = new Transaction();
     ZCThemeSet set = new ZCThemeSchema().query(new QueryBuilder("where ID in (" + ids + ")"));
     ZCForumMemberSet userSet = new ZCForumMemberSet();
 
     StringBuffer themeLog = new StringBuffer();
     for (int i = 0; i < set.size(); ++i) {
       if (StringUtil.isEmpty($V("TopTheme"))) {
         this.Response.setLogInfo(0, "有未选择的选项");
         return;
       }
 
       ZCForumMemberSchema user = new ZCForumMemberSchema();
       user.setUserName(set.get(i).getAddUser());
       int index = ForumUtil.getValueOfMemberSet(userSet, user);
       if ($V("TopTheme").equals("top")) {
         if (index == -1) {
           user.fill();
           if (set.get(i).getTopTheme().equals("Y")) {
             continue;
           }
           user.setForumScore(user.getForumScore() + forumScore.TopTheme);
           userSet.add(user);
         } else {
           userSet.get(index).setForumScore(userSet.get(index).getForumScore() + forumScore.TopTheme);
         }
         set.get(i).setTopTheme("Y");
       } else if ($V("TopTheme").equals("back")) {
         if (index == -1) {
           user.fill();
           if (set.get(i).getTopTheme().equals("N")) {
             continue;
           }
           user.setForumScore(user.getForumScore() + forumScore.CancelTop);
           userSet.add(user);
         } else {
           userSet.get(index).setForumScore(userSet.get(index).getForumScore() + forumScore.CancelTop);
         }
         set.get(i).setTopTheme("N");
       }
       themeLog.append(set.get(i).getSubject() + ",");
     }
     ForumUtil.userGroupChange(userSet);
     trans.add(userSet, 2);
     trans.add(set, 2);
     if (trans.commit()) {
       if ($V("TopTheme").equals("top"))
         UserLog.log("Forum", "TopTheme", "置顶主题：" + themeLog + "成功", this.Request.getClientIP());
       else {
         UserLog.log("Forum", "CancelTop", "取消置顶：" + themeLog + "成功", this.Request.getClientIP());
       }
       this.Response.setLogInfo(1, "操作成功");
     } else {
       if ($V("TopTheme").equals("back"))
         UserLog.log("Forum", "TopTheme", "置顶主题：" + themeLog + "失败", this.Request.getClientIP());
       else {
         UserLog.log("Forum", "TopCancel", "取消置顶：" + themeLog + "失败", this.Request.getClientIP());
       }
       this.Response.setLogInfo(0, "操作失败");
     }
   }
 
   public static Mapx initBestThemeDialog(Mapx params) {
     Mapx map = new Mapx();
     map.put("best", "设为精华");
     map.put("back", "解除精华");
     params.put("BestTheme", HtmlUtil.mapxToRadios("BestTheme", map));
     return params;
   }
 
   public void bestTheme() {
     String ids = $V("IDs");
     if (!StringUtil.checkID(ids)) {
       LogUtil.warn("可能的SQL注入,Theme.bestTheme:" + ids);
       return;
     }
     ForumScore forumScore = new ForumScore($V("SiteID"));
     ZCThemeSet set = new ZCThemeSchema().query(new QueryBuilder("where ID in (" + ids + ")"));
     ZCForumMemberSet userSet = new ZCForumMemberSet();
     Transaction trans = new Transaction();
 
     StringBuffer themeLog = new StringBuffer();
     for (int i = 0; i < set.size(); ++i) {
       if (StringUtil.isEmpty($V("BestTheme"))) {
         this.Response.setLogInfo(0, "有未选择的选项");
         return;
       }
 
       ZCForumMemberSchema user = new ZCForumMemberSchema();
       user.setUserName(set.get(i).getAddUser());
       int index = ForumUtil.getValueOfMemberSet(userSet, user);
       if ($V("BestTheme").equals("best")) {
         if (index == -1) {
           user.fill();
           if (set.get(i).getBest().equals("Y")) {
             continue;
           }
           user.setForumScore(user.getForumScore() + forumScore.Best);
           userSet.add(user);
         } else {
           userSet.get(index).setForumScore(userSet.get(index).getForumScore() + forumScore.Best);
         }
         set.get(i).setBest("Y");
       } else if ($V("BestTheme").equals("back")) {
         if (index == -1) {
           user.fill();
           if (set.get(i).getBest().equals("N")) {
             continue;
           }
           user.setForumScore(user.getForumScore() + forumScore.CancelBest);
           userSet.add(user);
         } else {
           userSet.get(index).setForumScore(userSet.get(index).getForumScore() + forumScore.CancelBest);
         }
         set.get(i).setBest("N");
       }
       themeLog.append(set.get(i).getSubject() + ",");
     }
 
     ForumUtil.userGroupChange(userSet);
     trans.add(userSet, 2);
     trans.add(set, 2);
     if (trans.commit()) {
       if ($V("BestTheme").equals("best"))
         UserLog.log("Forum", "BestTheme", "设为精华：" + themeLog + "成功", this.Request.getClientIP());
       else {
         UserLog.log("Forum", "CancelBest", "取消精华：" + themeLog + "成功", this.Request.getClientIP());
       }
       this.Response.setLogInfo(1, "操作成功");
     } else {
       if ($V("BestTheme").equals("back"))
         UserLog.log("Forum", "BestTheme", "设为精华：" + themeLog + "失败", this.Request.getClientIP());
       else {
         UserLog.log("Forum", "BestCancel", "取消精华：" + themeLog + "失败", this.Request.getClientIP());
       }
       this.Response.setLogInfo(0, "操作失败");
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.bbs.Theme
 * JD-Core Version:    0.5.4
 */