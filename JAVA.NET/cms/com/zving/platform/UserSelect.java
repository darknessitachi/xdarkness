 package com.zving.platform;
 
 import com.zving.framework.Page;
 import com.zving.framework.SessionListener;
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.data.DataRow;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.utility.Filter;
 import com.zving.framework.utility.HtmlUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import java.util.Arrays;
 import java.util.List;
 
 public class UserSelect extends Page
 {
   private static final String Show_Branch = "branch";
   private static final String Show_Role = "role";
   private static final String Show_OnLine = "online";
   private static final String Show_Duty = "duty";
   private static Mapx Show_Map = new Mapx();
 
   static {
     Show_Map.put("branch", "机构");
     Show_Map.put("role", "角色");
   }
 
   public static Mapx init(Mapx params)
   {
     params.put("ShowType", HtmlUtil.mapxToOptions(Show_Map));
     params.put("SelectedUser", params.getString("SelectedUser"));
     return params;
   }
 
   public static void dg1DataBind(DataGridAction dga) {
     String showType = dga.getParam("ShowType");
     String selectedUser = dga.getParam("SelectedUser");
 
     String[] userNames = (String[])null;
     if ((StringUtil.isNotEmpty(selectedUser)) && (StringUtil.isNotEmpty(selectedUser.substring(0, selectedUser.indexOf("|"))))) {
       String userName = selectedUser.substring(0, selectedUser.indexOf("|"));
       if (userName.lastIndexOf(",") == userName.length()) {
         userName = userName.substring(0, userName.length() - 1);
       }
 
       userNames = StringUtil.splitEx(userName, ",");
     }
 
     String type = dga.getParam("Type");
     if (StringUtil.isEmpty(type)) {
       type = "checkbox";
     }
 
     if (StringUtil.isEmpty(showType)) {
       showType = "branch";
     }
 
     List list = SessionListener.getUserNames("online");
     String usernames = StringUtil.join(list.toArray(), "','");
 
     DataTable dt = new DataTable();
     if ("role".equalsIgnoreCase(showType))
       dt = new QueryBuilder("select RoleCode as ID,RoleCode,RoleName as name from zdrole order by id asc").executeDataTable();
     else if (!"duty".equalsIgnoreCase(showType))
     {
       if ("online".equalsIgnoreCase(showType))
         dt = new QueryBuilder("select branchinnercode,branchinnercode as ID,name,treelevel,type from zdbranch where type='0' order by orderflag").executeDataTable();
       else {
         dt = new QueryBuilder("select branchinnercode,branchinnercode as ID,name,treelevel,type from zdbranch where type='0' order by orderflag").executeDataTable();
       }
     }
     String userName = "";
     dt.insertColumns(new String[] { "LevelStr", "UserName" });
     int level = 0;
     DataTable userDT = null;
 
     for (int i = 0; (dt != null) && (i < dt.getRowCount()); ++i) {
       if ("role".equalsIgnoreCase(showType)) {
         dt.set(i, "LevelStr", "");
 
         userDT = new QueryBuilder("select realname,username from zduser where exists (select * from zduserrole where rolecode = ? and username = zduser.username) order by addTime", 
           dt.getString(i, "RoleCode")).executeDataTable();
         filterUser(dga.getParams(), userDT);
         if ("checkbox".equalsIgnoreCase(type))
           userName = dealDataTable("UserList_" + dt.getString(i, "branchinnercode"), userDT, userNames, type);
         else {
           userName = dealDataTable("UserList", userDT, userNames, type);
         }
 
         dt.set(i, "UserName", userName);
       } else if ("online".equalsIgnoreCase(showType)) {
         String levelStr = "";
         level = Integer.parseInt(dt.getString(i, "TreeLevel"));
         for (int j = 0; j < level; ++j) {
           levelStr = levelStr + "&nbsp;&nbsp;";
         }
         dt.set(i, "LevelStr", levelStr);
 
         userDT = new QueryBuilder("select realname,username from zduser where branchinnercode = ? and username in ('" + 
           usernames + "') order by addTime", 
           dt.getString(i, "branchinnercode")).executeDataTable();
         filterUser(dga.getParams(), userDT);
 
         if ("checkbox".equalsIgnoreCase(type))
           userName = dealDataTable("UserList_" + dt.getString(i, "branchinnercode"), userDT, userNames, type);
         else {
           userName = dealDataTable("UserList", userDT, userNames, type);
         }
 
         dt.set(i, "UserName", userName); } else {
         if ("duty".equalsIgnoreCase(showType)) {
           continue;
         }
         String levelStr = "";
         level = Integer.parseInt(dt.getString(i, "TreeLevel"));
         for (int j = 0; j < level; ++j) {
           levelStr = levelStr + "&nbsp;&nbsp;";
         }
         dt.set(i, "LevelStr", levelStr);
 
         userDT = new QueryBuilder("select realname,username from zduser where branchinnercode = ?  order by addTime", 
           dt.getString(i, "branchinnercode")).executeDataTable();
 
         if ("checkbox".equalsIgnoreCase(type))
           userName = dealDataTable("UserList_" + dt.getString(i, "branchinnercode"), userDT, userNames, type);
         else {
           userName = dealDataTable("UserList", userDT, userNames, type);
         }
 
         dt.set(i, "UserName", userName);
       }
     }
 
     dga.bindData(dt);
   }
 
   private static String dealDataTable(String name, DataTable dt, String[] checkedArray, String Type) {
     StringBuffer sb = new StringBuffer();
     for (int k = 0; k < dt.getRowCount(); ++k)
     {
       String value = dt.getString(k, "UserName") + "," + dt.getString(k, "RealName");
       sb.append("<input type='" + Type + "' name='" + name);
       sb.append("' id='" + name + "_" + k + "' value='");
       sb.append(value);
       boolean flag = false;
       if (checkedArray != null) {
         for (int j = 0; j < checkedArray.length; ++j) {
           if (dt.getString(k, "UserName").equals(checkedArray[j])) {
             sb.append("' checked >");
             flag = true;
             break;
           }
         }
       }
 
       if (!flag) {
         sb.append("' >");
       }
       sb.append("<label for='" + name + "_" + k + "'>");
 
       sb.append(dt.getString(k, "RealName"));
 
       sb.append("</label>&nbsp;");
     }
     return sb.toString();
   }
 
   private static void filterUser(Mapx params, DataTable dt) {
     if (params.containsKey("FilterUsers")) {
       String filterUsers = params.getString("FilterUsers");
       List list = Arrays.asList(StringUtil.splitEx(filterUsers, ","));
       dt.filter(new Filter(list) {
         public boolean filter(Object obj) {
           DataRow dr = (DataRow)obj;
           return UserSelect.this.contains(dr.getString(1));
         }
       });
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.platform.UserSelect
 * JD-Core Version:    0.5.4
 */