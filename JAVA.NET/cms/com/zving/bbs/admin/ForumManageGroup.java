 package com.zving.bbs.admin;
 
 import com.zving.bbs.ForumUtil;
 import com.zving.framework.Page;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.cache.CacheManager;
 import com.zving.framework.controls.DataListAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.platform.pub.NoUtil;
 import com.zving.schema.ZCForumGroupSchema;
 import java.util.Date;
 
 public class ForumManageGroup extends Page
 {
   public static void getListManageGroup(DataListAction dla)
   {
     long SiteID = ForumUtil.getCurrentBBSSiteID();
     QueryBuilder qb = new QueryBuilder("select f1.Name, f2.SystemName, f1.Type, a.* from ZCForumGroup f1, ZCForumGroup f2, ZCAdminGroup a where f1.SiteID=?  and f1.ID=a.GroupID and f1.RadminID=f2.ID", 
       SiteID);
     DataTable dt = qb.executeDataTable();
     for (int i = 0; i < dt.getRowCount(); ++i) {
       if (dt.get(i, "Type").equals("2"))
         dt.set(i, "Type", "内置");
       else {
         dt.set(i, "Type", "自定义");
       }
     }
     dla.setTotal(qb);
     dla.bindData(dt);
   }
 
   public void addManageGroup() {
     Transaction trans = new Transaction();
     ZCForumGroupSchema userGroup = new ZCForumGroupSchema();
     userGroup.setID(NoUtil.getMaxID("ForumGroupID"));
     userGroup.setType("2");
     userGroup.setValue(this.Request);
     userGroup.setAddUser(User.getUserName());
     userGroup.setAddTime(new Date());
     trans.add(userGroup, 1);
     if (trans.commit()) {
       CacheManager.set("Forum", "Group", userGroup.getID(), userGroup);
       this.Response.setLogInfo(1, "添加成功");
     } else {
       this.Response.setLogInfo(0, "操作失败");
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.bbs.admin.ForumManageGroup
 * JD-Core Version:    0.5.4
 */