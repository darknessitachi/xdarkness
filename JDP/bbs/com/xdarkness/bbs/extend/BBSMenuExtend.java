 package com.xdarkness.bbs.extend;
 
 import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.framework.extend.IExtendAction;
 
 public class BBSMenuExtend
   implements IExtendAction
 {
   public void execute(Object[] args)
   {
     StringBuffer sb = (StringBuffer)args[0];
     String SiteID = (String)args[1];
     if (!SiteUtil.isBBSEnable(SiteID)) {
       return;
     }
     sb.append("<ul class='sidemenu'>");
     sb.append("<li id='Menu_MT'><a href='" + Config.getContextPath() + "BBS/MyThemes.jsp?cur=Menu_MT&SiteID=" + 
       SiteID + "'>我发表的帖子</a></li>");
     sb.append("<li id='Menu_MS'><a href='" + Config.getContextPath() + "BBS/MyScore.jsp?cur=Menu_MS&SiteID=" + 
       SiteID + "'>我的论坛积分</a></li>");
     sb.append("</ul>");
     sb.append("<hr class='shadowline'/>");
   }
 
   public String getTarget() {
     return "Member.Menu";
   }
 
   public String getName() {
     return "BBS会员中心菜单扩展";
   }
 }

          
/*    com.xdarkness.bbs.extend.BBSMenuExtend
 * JD-Core Version:    0.6.0
 */