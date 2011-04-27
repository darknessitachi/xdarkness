 package com.zving.bbs;
 
 import com.zving.framework.User;
 import com.zving.framework.data.DataColumn;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.utility.CaseIgnoreMapx;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.schema.ZCForumGroupSchema;
 import com.zving.schema.ZCForumGroupSet;
 import com.zving.schema.ZCForumMemberSchema;
 
 public class ForumPriv
 {
   public static final String NO = "N";
   public static final String YES = "Y";
   Mapx map = new CaseIgnoreMapx();
 
   public ForumPriv(long SiteID) {
     this(User.getUserName(), SiteID, null);
   }
 
   public ForumPriv(String SiteID) {
     this(User.getUserName(), SiteID, null);
   }
 
   public ForumPriv(String SiteID, String ForumID) {
     this(User.getUserName(), SiteID, ForumID);
   }
 
   public ForumPriv(String UserName, String SiteID, String ForumID) {
     cleanPriv();
     initPriv(UserName, SiteID, ForumID);
   }
 
   public ForumPriv(String UserName, long SiteID, String ForumID) {
     cleanPriv();
     initPriv(UserName, SiteID, ForumID);
   }
 
   public boolean hasPriv(String PrivType)
   {
     return "Y".equals(this.map.get(PrivType));
   }
 
   private void initPriv(String UserName, String SiteID, String ForumID) {
     initPriv(UserName, Long.parseLong(SiteID), ForumID);
   }
 
   private void cleanPriv() {
     this.map.clear();
   }
 
   private void initPriv(String UserName, long SiteID, String ForumID) {
     if (!ForumUtil.isInitDB(SiteID)) {
       cleanPriv();
       return;
     }
     ZCForumGroupSet set = new ZCForumGroupSet();
     if (StringUtil.isNotEmpty(UserName)) {
       ZCForumMemberSchema member = new ZCForumMemberSchema();
       member.setUserName(UserName);
       member.fill();
       if (StringUtil.isNotEmpty(ForumID)) {
         String systemName = ForumCache.getGroup(member.getAdminID()).getSystemName();
         if ((StringUtil.isNotEmpty(systemName)) && (systemName.equals("版主"))) {
           String[] forumAdmins = ForumUtil.getAdmins(ForumID);
           for (int i = 0; i < forumAdmins.length; ++i) {
             if (forumAdmins[i].equals(User.getUserName())) {
               break;
             }
             if (i == forumAdmins.length - 1) {
               member.setAdminID(0L);
             }
           }
         }
       }
       if (member.getUserName().equals("admin")) {
         long groupID = ForumCache.getGroupBySystemName(SiteID, "系统管理员").getID();
         member.setAdminID(groupID);
       }
       long id = ForumCache.getGroupBySystemName(SiteID, "禁止访问").getID();
       if (member.getAdminID() == id) {
         member.setUserGroupID(0L);
         member.setDefinedID(0L);
       }
       String ids = member.getUserGroupID() + "," + member.getAdminID() + "," + member.getDefinedID();
       String[] arr = ids.split(",");
       for (int i = 0; i < arr.length; ++i)
         if ((StringUtil.isNotEmpty(arr[i].trim())) && (!arr[i].trim().equals("0")))
           set.add(ForumCache.getGroup(arr[i].trim()));
     }
     else
     {
       set.add(ForumCache.getGroupBySystemName(String.valueOf(SiteID), "游客"));
     }
     DataTable dt = set.toDataTable();
     for (int i = 0; i < dt.getRowCount(); ++i) {
       for (int j = 0; j < dt.getColCount(); ++j) {
         String k = dt.getDataColumn(j).getColumnName();
         if ((!hasPriv(k)) && ("Y".equals(dt.getString(i, j)))) {
           this.map.put(k, "Y");
         }
       }
     }
     isAdmin();
     if ((StringUtil.isNotEmpty(User.getUserName())) && (User.getUserName().equals("admin")))
       this.map.put("Admin", "N");
   }
 
   private void isAdmin()
   {
     if (("Y".equals(this.map.get("AllowEditUser"))) || ("Y".equals(this.map.get("AllowForbidUser"))) || 
       ("Y".equals(this.map.get("AllowEditForum"))) || ("Y".equals(this.map.get("AllowVerfyPost"))))
       this.map.put("Admin", "Y");
     else
       this.map.put("Admin", "N");
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.bbs.ForumPriv
 * JD-Core Version:    0.5.4
 */