 package com.zving.bbs.admin;
 
 import com.zving.framework.Page;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.HtmlUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.schema.ZCAdminGroupSchema;
 import com.zving.schema.ZCForumGroupSchema;
 
 public class ForumManageGroupOption extends Page
 {
   public static Mapx init(Mapx params)
   {
     String ID = params.getString("ID");
     ZCForumGroupSchema group = new ZCForumGroupSchema();
     group.setID(ID);
     group.fill();
     Mapx map = new Mapx();
     map.put("Y", "允许");
     map.put("N", "不允许");
     if ((StringUtil.isEmpty(group.getSystemName())) || (!group.getSystemName().equals("游客"))) {
       params.put("AllowEditUser", HtmlUtil.mapxToRadios("AllowEditUser", map, group.getAllowEditUser()));
       params.put("AllowForbidUser", HtmlUtil.mapxToRadios("AllowForbidUser", map, group.getAllowForbidUser()));
       params.put("AllowEditForum", HtmlUtil.mapxToRadios("AllowEditForum", map, group.getAllowEditForum()));
       params.put("AllowVerfyPost", HtmlUtil.mapxToRadios("AllowVerfyPost", map, group.getAllowVerfyPost()));
       params.put("AllowEdit", HtmlUtil.mapxToRadios("AllowEdit", map, group.getAllowEdit()));
       params.put("AllowDel", HtmlUtil.mapxToRadios("AllowDel", map, group.getAllowDel()));
       params.put("Hide", HtmlUtil.mapxToRadios("Hide", map, group.getHide()));
     } else {
       params.put("AllowEditUser", "不允许");
       params.put("AllowForbidUser", "不允许");
       params.put("AllowEditForum", "不允许");
       params.put("AllowVerfyPost", "不允许");
       params.put("AllowEdit", "不允许");
       params.put("AllowDel", "不允许");
       params.put("Hide", "不允许");
     }
 
     return params;
   }
 
   public static Mapx initThemeOption(Mapx params) {
     ZCForumGroupSchema group = new ZCForumGroupSchema();
     group.setID(params.getString("ID"));
     group.fill();
     Mapx map = new Mapx();
     map.put("Y", "允许");
     map.put("N", "不允许");
     if ((StringUtil.isEmpty(group.getSystemName())) || (!group.getSystemName().equals("游客"))) {
       params.put("RemoveTheme", HtmlUtil.mapxToRadios("RemoveTheme", map, group.getRemoveTheme()));
       params.put("MoveTheme", HtmlUtil.mapxToRadios("MoveTheme", map, group.getMoveTheme()));
       params.put("TopTheme", HtmlUtil.mapxToRadios("TopTheme", map, group.getTopTheme()));
       params.put("BrightTheme", HtmlUtil.mapxToRadios("BrightTheme", map, group.getBrightTheme()));
       params.put("UpOrDownTheme", HtmlUtil.mapxToRadios("UpOrDownTheme", map, group.getUpOrDownTheme()));
       params.put("BestTheme", HtmlUtil.mapxToRadios("BestTheme", map, group.getBestTheme()));
     } else {
       params.put("RemoveTheme", "不允许");
       params.put("MoveTheme", "不允许");
       params.put("TopTheme", "不允许");
       params.put("BrightTheme", "不允许");
       params.put("UpOrDownTheme", "不允许");
       params.put("BestTheme", "不允许");
     }
     return params;
   }
 
   public void editSave() {
     Transaction trans = new Transaction();
     ZCAdminGroupSchema manageGroup = new ZCAdminGroupSchema();
     manageGroup.setGroupID($V("GroupID"));
     manageGroup.fill();
     manageGroup.setValue(this.Request);
     manageGroup.setAllowEditUser($V("AllowEditUser"));
 
     trans.add(manageGroup, 2);
     if (trans.commit())
       this.Response.setLogInfo(1, "设置成功!");
     else
       this.Response.setLogInfo(0, "操作失败！");
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.bbs.admin.ForumManageGroupOption
 * JD-Core Version:    0.5.4
 */