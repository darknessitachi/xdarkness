 package com.zving.bbs.admin;
 
 import com.zving.bbs.ForumUtil;
 import com.zving.framework.Ajax;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.User;
 import com.zving.framework.cache.CacheManager;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.HtmlUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.schema.ZCForumConfigSchema;
 import com.zving.schema.ZCForumConfigSet;
 import java.util.Date;
 
 public class ForumSetting extends Ajax
 {
   public static Mapx init(Mapx params)
   {
     long siteID = ForumUtil.getCurrentBBSSiteID();
     ZCForumConfigSet set = new ZCForumConfigSchema().query(new QueryBuilder("where SiteID = ?", siteID));
     if (set.size() == 1) {
       params = set.get(0).toMapx();
     }
     DataTable dt = new QueryBuilder("select TempCloseFlag from ZCForumConfig where SiteID = ?", siteID).executeDataTable();
     Mapx map = new Mapx();
     map.put("Y", "是");
     map.put("N", "否");
     params.put("TempCloseFlag", HtmlUtil.mapxToRadios("TempCloseFlag", map, (dt.getRowCount() > 0) ? dt.getString(0, "TempCloseFlag") : ""));
     return params;
   }
 
   public void add() {
     Transaction trans = new Transaction();
 
     ZCForumConfigSchema config = new ZCForumConfigSchema();
     config.setID($V("ID"));
     config.fill();
     config.setSiteID(ForumUtil.getCurrentBBSSiteID());
     config.setValue(this.Request);
     config.setAddTime(new Date());
     config.setAddUser(User.getUserName());
     trans.add(config, 6);
 
     if (trans.commit()) {
       CacheManager.set("Forum", "Config", config.getID(), config);
       this.Response.setLogInfo(1, "操作成功!");
     } else {
       this.Response.setLogInfo(0, "操作失败!");
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.bbs.admin.ForumSetting
 * JD-Core Version:    0.5.4
 */