 package com.zving.shop.extend;
 
 import com.zving.cms.pub.SiteUtil;
 import com.zving.framework.extend.IExtendAction;
 import com.zving.member.Member;
 
 public class ShopLoginExtend
   implements IExtendAction
 {
   public String getTarget()
   {
     return "Member.Login";
   }
 
   public void execute(Object[] args) {
     Member member = (Member)args[0];
     String siteID = String.valueOf(member.getSiteID());
     SiteUtil.isShopEnable(siteID);
   }
 
   public String getName()
   {
     return "Shop会员登录逻辑扩展";
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.shop.extend.ShopLoginExtend
 * JD-Core Version:    0.5.4
 */