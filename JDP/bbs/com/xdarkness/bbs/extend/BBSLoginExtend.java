 package com.xdarkness.bbs.extend;
 
 import com.xdarkness.bbs.ForumUtil;
import com.xdarkness.cms.pub.SiteUtil;
import com.xdarkness.member.Member;
import com.xdarkness.framework.extend.IExtendAction;
 
 public class BBSLoginExtend
   implements IExtendAction
 {
   public String getTarget()
   {
     return "Member.Login";
   }
 
   public void execute(Object[] args) {
     Member member = (Member)args[0];
     String siteID = String.valueOf(member.getSiteID());
     if (SiteUtil.isBBSEnable(siteID))
       ForumUtil.allowVisit(siteID);
   }
 
   public String getName()
   {
     return "BBS会员登录逻辑扩展";
   }
 }

          
/*    com.xdarkness.bbs.extend.BBSLoginExtend
 * JD-Core Version:    0.6.0
 */