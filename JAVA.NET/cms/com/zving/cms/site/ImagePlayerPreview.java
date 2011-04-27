 package com.zving.cms.site;
 
 import com.zving.cms.pub.PubFun;
 import com.zving.framework.Page;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.schema.ZCImagePlayerSchema;
 
 public class ImagePlayerPreview extends Page
 {
   public static Mapx init(Mapx params)
   {
     String s = (String)params.get("ImagePlayerID");
     if (StringUtil.isEmpty(s)) {
       return null;
     }
     long ImagePlayerID = Long.parseLong(params.get("ImagePlayerID").toString());
     ZCImagePlayerSchema imagePlayer = new ZCImagePlayerSchema();
     imagePlayer.setID(ImagePlayerID);
     imagePlayer.fill();
 
     params.put("_SWFObject", PubFun.getImagePlayer(imagePlayer));
     return params;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.site.ImagePlayerPreview
 * JD-Core Version:    0.5.4
 */