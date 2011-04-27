 package com.zving.bbs.extend;
 
 import com.zving.bbs.ForumUtil;
 import com.zving.cms.pub.SiteUtil;
 import com.zving.framework.extend.IExtendAction;
 import com.zving.member.Member;
 
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

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.bbs.extend.BBSLoginExtend
 * JD-Core Version:    0.5.4
 */