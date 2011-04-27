 package com.zving.cms.resource.uploader;
 
 import com.zving.cms.datachannel.Deploy;
 import com.zving.cms.dataservice.ColumnUtil;
 import com.zving.framework.User;
 import com.zving.framework.controls.UploadStatus;
 import com.zving.framework.data.DataCollection;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.NumberUtil;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.platform.pub.NoUtil;
 import com.zving.platform.pub.OrderUtil;
 import com.zving.schema.ZCAttachmentSchema;
 import com.zving.schema.ZCCatalogSchema;
 import java.io.File;
 import java.io.PrintStream;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.Iterator;
 import java.util.Set;
 import org.apache.commons.fileupload.FileItem;
 import org.apache.commons.io.FileUtils;
 
 public class UploadAttachment
 {
   public static void repeatUpload(Mapx files, Mapx fields)
     throws Exception
   {
     ZCAttachmentSchema attach = new ZCAttachmentSchema();
     attach.setID(fields.getString("RepeatID"));
     if (!attach.fill()) {
       UploadStatus.setTask(fields.getString("taskID"), fields.getString("RepeatID"), 
         fields.getString("FileType"), "empty", "上传失败，未找到文件,完成");
       return;
     }
     ArrayList fileList = new ArrayList();
     Transaction trans = new Transaction();
     Iterator it = files.keySet().iterator();
     while (it.hasNext()) {
       FileItem uplFile = (FileItem)files.get(it.next());
       UploadStatus.setTask(fields.getString("taskID"), fields.getString("RepeatID"), 
         fields.getString("FileType"), fields.getString("PathData"), "处理中");
       String AbsolutePath = fields.getString("AbsolutePath");
       uplFile.write(new File(AbsolutePath + attach.getSrcFileName()));
       attach.setFileSize(FileUtils.byteCountToDisplaySize(uplFile.getSize()));
     }
     attach.setModifyTime(new Date());
     attach.setModifyUser(User.getUserName());
     attach.setStatus(60L);
     trans.add(attach, 2);
 
     Deploy d = new Deploy();
     trans.add(d.getJobs(Application.getCurrentSiteID(), fileList, "copy"), 1);
     trans.commit();
 
     UploadStatus.setTask(fields.getString("taskID"), fields.getString("RepeatID"), fields.getString("FileType"), 
       fields.getString("PathData") + attach.getSrcFileName(), "文件上传完成");
   }
 
   public static void upload(Mapx files, Mapx fields) throws Exception {
     String CatalogID = fields.getString("CatalogID");
     String AbsolutePath = fields.getString("AbsolutePath");
 
     ZCCatalogSchema catalog = new ZCCatalogSchema();
     catalog.setID(CatalogID);
     if (!catalog.fill()) {
       System.out.println("没有找到上传库");
     }
 
     ArrayList fileList = new ArrayList();
     Transaction trans = new Transaction();
     DataCollection dc = new DataCollection();
     dc.putAll(fields);
 
     Iterator it = files.keySet().iterator();
     while (it.hasNext()) {
       FileItem uplFile = (FileItem)files.get(it.next());
       String fileNameLong = uplFile.getName();
       fileNameLong = fileNameLong.replaceAll("\\\\", "/");
       String oldName = fileNameLong.substring(fileNameLong.lastIndexOf("/") + 1);
       String ext = ZUploaderServlet.getExtension(oldName);
 
       long attachID = NoUtil.getMaxID("DocID");
       UploadStatus.setTask(fields.getString("taskID"), attachID, fields.getString("FileType"), fields
         .getString("PathData"), "处理中");
       int random = NumberUtil.getRandomInt(10000);
       String srcFileName = attachID + random + "." + ext;
       uplFile.write(new File(AbsolutePath, srcFileName));
 
       fileList.add(AbsolutePath + srcFileName);
       if (StringUtil.isEmpty(fields.getString("FileName"))) {
         fields.put("FileName", oldName.substring(0, oldName.lastIndexOf(".")));
       }
 
       ZCAttachmentSchema attachment = new ZCAttachmentSchema();
       attachment.setID(attachID);
       attachment.setName(fields.getString("FileName"));
       attachment.setOldName(oldName.substring(0, oldName.lastIndexOf(".")));
       attachment.setSiteID(fields.getString("SiteID"));
       attachment.setInfo(fields.getString("Info"));
       attachment.setCatalogID(Long.parseLong(CatalogID));
       attachment.setCatalogInnerCode(catalog.getInnerCode());
       attachment.setBranchInnerCode(User.getBranchInnerCode());
       attachment.setPath(fields.getString("PathData"));
       attachment.setImagePath(fields.getString("ImagePath"));
       attachment.setFileName(srcFileName);
       attachment.setSrcFileName(srcFileName);
       attachment.setSuffix(ext);
       attachment.setIsLocked(fields.getString(new StringBuffer(String.valueOf(uplFile.getFieldName())).append("Locked").toString()));
       if ("Y".equals(attachment.getIsLocked())) {
         attachment.setPassword(StringUtil.md5Hex(fields.getString(uplFile.getFieldName() + "Password")));
       }
       attachment.setSystem("CMS");
       attachment.setFileSize(FileUtils.byteCountToDisplaySize(uplFile.getSize()));
       attachment.setAddTime(new Date());
       attachment.setAddUser("admin");
       attachment.setOrderFlag(OrderUtil.getDefaultOrder());
       attachment.setModifyTime(new Date());
       attachment.setModifyUser(User.getUserName());
       attachment.setProp1("0");
       attachment.setStatus(20L);
       trans.add(attachment, 1);
       trans.add(ColumnUtil.getValueFromRequest(Long.parseLong(CatalogID), attachment.getID(), dc), 1);
       UploadStatus.setTask(fields.getString("taskID"), attachID, fields.getString("FileType"), fields
         .getString("PathData") + 
         srcFileName, "文件上传完成");
     }
     trans
       .add(new QueryBuilder(
       "update zccatalog set isdirty = 1,total = (select count(*) from zcattachment where catalogID =?) where ID =?", 
       catalog.getID(), catalog.getID()));
 
     Deploy d = new Deploy();
     trans.add(d.getJobs(Application.getCurrentSiteID(), fileList, "copy"), 1);
 
     trans.commit();
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.resource.uploader.UploadAttachment
 * JD-Core Version:    0.5.4
 */