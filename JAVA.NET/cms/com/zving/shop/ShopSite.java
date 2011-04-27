 package com.zving.shop;
 
 import com.zving.cms.datachannel.Publisher;
 import com.zving.framework.Page;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.utility.Mapx;
 import com.zving.platform.Application;
 import com.zving.schema.ZCSiteSchema;
 
 public class ShopSite extends Page
 {
   public static Mapx init(Mapx params)
   {
     ZCSiteSchema site = new ZCSiteSchema();
     site.setID(Application.getCurrentSiteID());
     site.fill();
     return site.toMapx();
   }
 
   public void publishIndex()
   {
     Publisher p = new Publisher();
     if (p.publishIndex(Application.getCurrentSiteID())) {
       this.Response.setStatus(1);
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("保存数据发生错误!");
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.shop.ShopSite
 * JD-Core Version:    0.5.4
 */