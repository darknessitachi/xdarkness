// package com.xdarkness.platform.pub;
// 
// import java.awt.Dimension;
//import java.awt.image.BufferedImage;
//import java.util.ArrayList;
//
//import com.abigdreamer.java.net.Config;
//import com.abigdreamer.java.net.io.FileUtil;
//import com.abigdreamer.java.net.util.ImageJDKUtil;
//import com.abigdreamer.java.net.util.ImageUtil;
//import com.abigdreamer.java.net.util.LogUtil;
//import com.abigdreamer.java.net.util.Mapx;
//import com.abigdreamer.java.net.util.gif.BmpUtil;
//import com.abigdreamer.schema.ZCImageSchema;
//import com.xdarkness.platform.page.ApplicationPage;
// 
// public class ImageUtilEx
// {
//   public static ArrayList afterUploadImage(ZCImageSchema image, String absolutePath)
//     throws Throwable
//   {
//     return afterUploadImage(image, absolutePath, null);
//   }
// 
//   public static ArrayList afterUploadImage(ZCImageSchema image, String absolutePath, Mapx fields) throws Throwable {
//     long t = System.currentTimeMillis();
//     ArrayList imageList = new ArrayList();
//     String imageFile = absolutePath + image.getSrcFileName();
//     Dimension dim = null;
//     try {
//       dim = ImageUtil.getDimension(imageFile);
//     } catch (Throwable ex) {
//       throw ex;
//     }
//     image.setWidth((int)dim.getWidth());
//     image.setHeight((int)dim.getHeight());
// 
//     String destFile = image.getFileName();
//     destFile = destFile.substring(0, destFile.lastIndexOf(".")) + ".jpg";
//     image.setFileName(destFile);
// 
//     Mapx configFields = new Mapx();
//     configFields.putAll(ConfigImageLib.getImageLibConfig(image.getSiteID()));
//     if (fields != null) {
//       configFields.putAll(fields);
//     }
// 
//     int count = Integer.parseInt(configFields.get("Count").toString());
// 
//     for (int i = 1; i <= count; i++) {
//       if ((configFields == null) || ("1".equals(configFields.get("HasAbbrImage" + i)))) {
//         String SizeType = (String)configFields.get("SizeType" + i);
//         int Width = Integer.parseInt((String)configFields.get("Width" + i));
//         int Height = Integer.parseInt((String)configFields.get("Height" + i));
//         if ("1".equals(SizeType))
//           Height = 0;
//         else if ("2".equals(SizeType)) {
//           Width = 0;
//         }
//         String abbrImage = absolutePath + i + "_" + image.getFileName();
// 
//         if ("3".equals(SizeType))
//           ImageUtil.scaleFixedImageFile(imageFile, abbrImage, Width, Height);
//         else {
//           ImageUtil.scaleRateImageFile(imageFile, abbrImage, Width, Height);
//         }
//         image.setCount(count);
// 
//         if (i == 1)
//         {
//           String thumbFileName = absolutePath + "s_" + image.getFileName();
//           ImageUtil.scaleRateImageFile(absolutePath + "1_" + image.getFileName(), thumbFileName, 120, 120);
//           imageList.add(thumbFileName);
//         }
// 
//         if ("1".equals(configFields.get("HasWaterMark" + i))) {
//           if ("Image".equals(configFields.get("WaterMarkType" + i)))
//             ImageUtil.pressImage(abbrImage, Config.getContextRealPath() + Config.getValue("UploadDir") + 
//               "/" + SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/" + 
//               configFields.get(new StringBuffer("Image").append(i).toString()), Integer.parseInt(configFields.get("Position" + i)
//               .toString()));
//           else {
//             ImageUtil.pressText(abbrImage, (String)configFields.get("Text" + i), 
//               Integer.parseInt(configFields.get("FontColor" + i).toString()), Integer.parseInt(configFields
//               .get("FontSize" + i).toString()), Integer.parseInt(configFields.get("Position" + i)
//               .toString()));
//           }
//         }
// 
//         imageList.add(abbrImage);
//       }
// 
//     }
// 
//     if (image.getCount() == 0L) {
//       if (image.getFileName().toLowerCase().endsWith(".bmp")) {
//         BufferedImage img = BmpUtil.read(imageFile);
//         ImageJDKUtil.writeImageFile(absolutePath + "1_" + image.getFileName(), img);
//       } else {
//         FileUtil.copy(imageFile, absolutePath + "1_" + image.getFileName());
//       }
//       image.setCount(1L);
//       imageList.add(absolutePath + "1_" + image.getFileName());
// 
//       String thumbFileName = absolutePath + "s_" + image.getFileName();
//       ImageUtil.scaleRateImageFile(absolutePath + "1_" + image.getFileName(), thumbFileName, 120, 120);
//       imageList.add(thumbFileName);
//     }
// 
//     if ((configFields == null) || ("1".equals(configFields.get("HasWaterMark")))) {
//       if ("Image".equals(configFields.get("WaterMarkType")))
//         ImageUtil.pressImage(absolutePath + image.getSrcFileName(), Config.getContextRealPath() + 
//           Config.getValue("UploadDir") + "/" + SiteUtil.getAlias(ApplicationPage.getCurrentSiteID()) + "/" + 
//           configFields.get("Image"), Integer.parseInt(configFields.get("Position").toString()));
//       else {
//         ImageUtil.pressText(absolutePath + image.getSrcFileName(), (String)configFields.get("Text"), 
//           Integer.parseInt(configFields.get("FontColor").toString()), Integer.parseInt(configFields.get(
//           "FontSize").toString()), Integer.parseInt(configFields.get("Position").toString()));
//       }
// 
//     }
// 
//     LogUtil.info("上传图片处理花费：" + (System.currentTimeMillis() - t) + "毫秒");
//     return imageList;
//   }
// }
//
//          
///*    com.xdarkness.platform.pub.ImageUtilEx
// * JD-Core Version:    0.6.0
// */