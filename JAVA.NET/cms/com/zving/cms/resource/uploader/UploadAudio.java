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
 import com.zving.platform.Application;
 import com.zving.platform.pub.NoUtil;
 import com.zving.platform.pub.OrderUtil;
 import com.zving.schema.ZCAudioSchema;
 import com.zving.schema.ZCCatalogSchema;
 import java.io.File;
 import java.io.PrintStream;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.Iterator;
 import java.util.Set;
 import org.apache.commons.fileupload.FileItem;
 import org.apache.commons.io.FileUtils;
 
 public class UploadAudio
 {
   public static void repeatUpload(Mapx files, Mapx fields)
     throws Exception
   {
     ZCAudioSchema audio = new ZCAudioSchema();
     audio.setID(fields.getString("RepeatID"));
     if (!audio.fill()) {
       UploadStatus.setTask(fields.getString("taskID"), fields.getString("RepeatID"), fields.getString("FileType"), "empty", "上传失败，未找到文件,完成");
       return;
     }
     Iterator it = files.keySet().iterator();
     ArrayList fileList = new ArrayList();
     Transaction trans = new Transaction();
     while (it.hasNext()) {
       FileItem uplFile = (FileItem)files.get(it.next());
       UploadStatus.setTask(fields.getString("taskID"), fields.getString("RepeatID"), fields.getString("FileType"), fields.getString("PathData"), "处理中");
       String AbsolutePath = fields.getString("AbsolutePath");
       uplFile.write(new File(AbsolutePath + audio.getSrcFileName()));
       audio.setFileSize(FileUtils.byteCountToDisplaySize(uplFile.getSize()));
       fileList.add(AbsolutePath + audio.getSrcFileName());
     }
     audio.setModifyTime(new Date());
     audio.setModifyUser(User.getUserName());
     audio.setStatus(60L);
     trans.add(audio, 2);
 
     Deploy d = new Deploy();
     trans.add(d.getJobs(Application.getCurrentSiteID(), fileList, "copy"), 1);
     trans.commit();
     UploadStatus.setTask(fields.getString("taskID"), fields.getString("RepeatID"), fields.getString("FileType"), fields.getString("PathData") + audio.getSrcFileName(), "文件上传完成");
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
 
       long audioID = NoUtil.getMaxID("DocID");
       UploadStatus.setTask(fields.getString("taskID"), audioID, fields.getString("FileType"), fields.getString("PathData"), "处理中");
       int random = NumberUtil.getRandomInt(10000);
       String srcFileName = audioID + random + "." + ext;
       uplFile.write(new File(AbsolutePath, srcFileName));
 
       ZCAudioSchema audio = new ZCAudioSchema();
       audio.setID(audioID);
       audio.setName(oldName.substring(0, oldName.lastIndexOf(".")));
       audio.setOldName(oldName.substring(0, oldName.lastIndexOf(".")));
       audio.setSiteID(fields.getString("SiteID"));
       audio.setCatalogID(CatalogID);
       audio.setCatalogInnerCode(catalog.getInnerCode());
       audio.setBranchInnerCode(User.getBranchInnerCode());
       audio.setPath(fields.getString("PathData"));
       audio.setFileName(srcFileName);
       audio.setSrcFileName(srcFileName);
       audio.setSuffix(ext);
 
       audio.setTag(fields.getString("FileTag"));
       audio.setIsOriginal("Y");
       audio.setFileSize(FileUtils.byteCountToDisplaySize(uplFile.getSize()));
       audio.setSystem("CMS");
       audio.setOrderFlag(OrderUtil.getDefaultOrder());
       audio.setAddTime(new Date());
       audio.setAddUser("admin");
       audio.setModifyTime(new Date());
       audio.setModifyUser(User.getUserName());
       audio.setStatus(20L);
       trans.add(audio, 1);
       trans.add(ColumnUtil.getValueFromRequest(Long.parseLong(CatalogID), audio.getID(), dc), 1);
       fileList.add(AbsolutePath + srcFileName);
       UploadStatus.setTask(fields.getString("taskID"), audioID, fields.getString("FileType"), fields.getString("PathData") + srcFileName, "上传完成");
     }
     trans.add(new QueryBuilder("update zccatalog set isdirty = 1,total = (select count(*) from zcaudio where catalogID =?) where ID =?", catalog.getID(), catalog.getID()));
 
     Deploy d = new Deploy();
     trans.add(d.getJobs(Application.getCurrentSiteID(), fileList, "copy"), 1);
 
     trans.commit();
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.resource.uploader.UploadAudio
 * JD-Core Version:    0.5.4
 */