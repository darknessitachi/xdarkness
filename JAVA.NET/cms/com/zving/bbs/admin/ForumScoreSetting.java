 package com.zving.bbs.admin;
 
 import com.zving.bbs.ForumUtil;
 import com.zving.framework.Page;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.cache.CacheManager;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.pub.NoUtil;
 import com.zving.schema.ZCForumScoreSchema;
 import com.zving.schema.ZCForumScoreSet;
 import java.util.Date;
 
 public class ForumScoreSetting extends Page
 {
   public static Mapx init(Mapx params)
   {
     ZCForumScoreSet set = new ZCForumScoreSchema().query(new QueryBuilder("where SiteID=?", ForumUtil.getCurrentBBSSiteID()));
     if (set.size() > 0) {
       params = set.get(0).toMapx();
     }
     return params;
   }
 
   public void save() {
     Transaction trans = new Transaction();
     ZCForumScoreSchema forumScore = new ZCForumScoreSchema();
     if (StringUtil.isNotEmpty($V("ID"))) {
       forumScore.setID($V("ID"));
       forumScore.fill();
       forumScore.setValue(this.Request);
       trans.add(forumScore, 2);
     } else {
       forumScore.setID(NoUtil.getMaxID("ForumScoreID"));
       forumScore.setValue(this.Request);
       forumScore.setAddUser(User.getUserName());
       forumScore.setAddTime(new Date());
       trans.add(forumScore, 1);
     }
     if (trans.commit()) {
       CacheManager.set("Forum", "Score", forumScore.getID(), forumScore);
       this.Response.setLogInfo(1, "操作成功！");
     } else {
       this.Response.setLogInfo(0, "操作失败");
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.bbs.admin.ForumScoreSetting
 * JD-Core Version:    0.5.4
 */