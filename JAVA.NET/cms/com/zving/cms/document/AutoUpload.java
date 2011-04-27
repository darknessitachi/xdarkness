 package com.zving.cms.document;
 
 import com.zving.cms.datachannel.Deploy;
 import com.zving.cms.pub.CatalogUtil;
 import com.zving.framework.Config;
 import com.zving.framework.User;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.NumberUtil;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.platform.pub.ImageUtilEx;
 import com.zving.platform.pub.NoUtil;
 import com.zving.platform.pub.OrderUtil;
 import com.zving.schema.ZCCatalogSchema;
 import com.zving.schema.ZCImageSchema;
 import java.io.File;
 import java.util.ArrayList;
 import java.util.Date;
 
 public class AutoUpload
 {
   public static ZCImageSchema dealImage(String path, String filename, Transaction trans)
   {
     String autoSaveLib = Config.getValue("AutoSaveImageLib");
     if (StringUtil.isEmpty(autoSaveLib)) {
       autoSaveLib = "默认图片";
     }
     String catalogID = new QueryBuilder("select ID from ZCCatalog where type='4' and Name =?  and siteid=?", 
       autoSaveLib, Application.getCurrentSiteID()).executeString();
 
     if (StringUtil.isEmpty(catalogID)) {
       catalogID = new QueryBuilder("select ID from ZCCatalog where type='4' and siteid=?", 
         Application.getCurrentSiteID()).executeString();
     }
 
     boolean uploadFlag = true;
     String absolutePath = path + "defaultTemp/" + filename;
     String ext = filename.substring(filename.lastIndexOf(".") + 1);
     filename = filename.substring(0, filename.lastIndexOf("."));
     long imageID = NoUtil.getMaxID("DocID");
     int random = NumberUtil.getRandomInt(10000);
     String newFileName = imageID + random + "." + ext;
     new File(absolutePath).renameTo(new File(path + CatalogUtil.getPath(catalogID) + newFileName));
 
     ZCCatalogSchema catalog = CatalogUtil.getSchema(catalogID);
 
     ZCImageSchema image = new ZCImageSchema();
     image.setID(imageID);
     image.setCatalogID(catalogID);
     image.setCatalogInnerCode(catalog.getInnerCode());
     image.setName(filename);
     image.setOldName(filename);
     image.setSiteID(Application.getCurrentSiteID());
     image.setPath("upload/Image/" + CatalogUtil.getPath(catalogID));
     image.setFileName(newFileName);
     image.setSrcFileName(newFileName);
     image.setSuffix(ext);
     image.setCount(0L);
     image.setWidth(0L);
     image.setHeight(0L);
     image.setHitCount(0L);
     image.setStickTime(0L);
     image.setAuthor("articleEditor");
     image.setSystem("CMS");
     image.setAddTime(new Date());
     image.setAddUser(User.getUserName());
     image.setOrderFlag(OrderUtil.getDefaultOrder());
     image.setStatus(20L);
     try {
       ImageUtilEx.afterUploadImage(image, path + CatalogUtil.getPath(catalogID));
       ArrayList imageList = ImageUtilEx.afterUploadImage(image, path + CatalogUtil.getPath(catalogID));
       Deploy d = new Deploy();
       d.addJobs(Application.getCurrentSiteID(), imageList, "copy");
       uploadFlag = true;
     } catch (Throwable e) {
       e.printStackTrace();
       uploadFlag = false;
     }
     if (uploadFlag) {
       trans.add(image, 1);
       return image;
     }
     return new ZCImageSchema();
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.document.AutoUpload
 * JD-Core Version:    0.5.4
 */