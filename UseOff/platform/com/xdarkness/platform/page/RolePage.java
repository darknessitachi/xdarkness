 package com.xdarkness.platform.page;
 
 import com.abigdreamer.java.net.User;
import com.abigdreamer.java.net.jaf.Page;
import com.abigdreamer.java.net.jaf.controls.TreeAction;
import com.abigdreamer.java.net.orm.data.DataTable;
import com.abigdreamer.java.net.sql.QueryBuilder;
 
 public class RolePage extends Page
 {
   public static final String EVERYONE = "everyone";
 
   public static void treeDataBind(TreeAction ta)
   {
     DataTable dt = new QueryBuilder("select RoleCode,'' as ParentID,'1' as TreeLevel,RoleName from ZDRole Where BranchInnerCode like ?", User.getBranchInnerCode() + "%").executeDataTable();
     ta.setRootText("角色列表");
     ta.setIdentifierColumnName("RoleCode");
     ta.setBranchIcon("Icons/icon025a1.gif");
     ta.setLeafIcon("Icons/icon025a1.gif");
     ta.bindData(dt);
   }
 }

          
/*    com.xdarkness.platform.Role
 * JD-Core Version:    0.6.0
 */