 package com.zving.cms.site;
 
 import com.zving.cms.datachannel.Deploy;
 import com.zving.cms.pub.PubFun;
 import com.zving.framework.Config;
 import com.zving.framework.Page;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.FileUtil;
 import com.zving.framework.utility.LogUtil;
 import com.zving.framework.utility.StringUtil;
 import com.zving.framework.utility.ZipUtil;
 import com.zving.platform.Application;
 import com.zving.schema.ZCDeployJobSet;
 import java.io.File;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.OutputStream;
 import java.io.PrintStream;
 import java.util.ArrayList;
 import org.apache.commons.io.filefilter.FileFilterUtils;
 import org.apache.commons.io.filefilter.IOFileFilter;
 import org.apache.commons.io.filefilter.SuffixFileFilter;
 import org.apache.commons.logging.Log;
 
 public class FileList extends Page
 {
   public void add()
   {
     String baseDir = $V("baseDir");
     String currentPath = $V("currentPath");
     baseDir = baseDir.replace('\\', '/');
     currentPath = currentPath.replace('\\', '/');
 
     String fileName = $V("fileName");
     String directoryName = $V("directoryName");
     String mess = "文件";
     String filestr = "";
     if ((fileName != null) && (!"".equals(fileName))) {
       filestr = baseDir + "/" + currentPath + "/" + fileName;
       mess = "文件";
     } else if ((directoryName != null) && (!"".equals(directoryName))) {
       filestr = baseDir + "/" + currentPath + "/" + directoryName;
       mess = "目录";
     }
     if (fileName.indexOf(".") != 0) {
       String ext = fileName.substring(fileName.indexOf(".") + 1);
       if ((mess.equals("文件")) && (!PubFun.isAllowExt(ext))) {
         this.Response.setStatus(0);
         this.Response.setMessage("创建" + mess + fileName + "失败,不允许创建" + ext + "文件！");
         return;
       }
     }
     File file = new File(filestr);
     if (!file.exists()) {
       try {
         if (mess.equals("文件")) {
           if (file.createNewFile()) {
             this.Response.setStatus(1);
             this.Response.setMessage("创建" + mess + fileName + "成功！");
           } else {
             this.Response.setStatus(0);
             this.Response.setMessage("创建" + mess + fileName + "失败！");
           }
         }
         else if (file.mkdir()) {
           this.Response.setStatus(1);
           this.Response.setMessage("创建" + mess + fileName + "成功！");
         } else {
           this.Response.setStatus(0);
           this.Response.setMessage("创建" + mess + fileName + "失败！");
         }
       }
       catch (IOException e)
       {
         e.printStackTrace();
         this.Response.setStatus(0);
         this.Response.setMessage("创建" + mess + fileName + "失败！");
       }
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage(mess + fileName + "已经存在了！");
     }
   }
 
   public void edit() {
     String fileName = $V("FileName");
     String content = $V("Content");
     content = StringUtil.htmlDecode(content);
     System.out.println(content);
     if (FileUtil.writeText(fileName, content)) {
       this.Response.setMessage("保存成功");
       this.Response.setStatus(1);
     } else {
       this.Response.setMessage("保存失败");
       this.Response.setStatus(0);
     }
   }
 
   public void paste() {
     String baseDir = $V("baseDir");
     String currentPath = $V("currentPath");
     String copyFile = $V("copyFile");
     String cutFile = $V("cutFile");
 
     baseDir = baseDir.replace('\\', '/');
     currentPath = currentPath.replace('\\', '/');
     copyFile = copyFile.replace('\\', '/');
     cutFile = cutFile.replace('\\', '/');
 
     String oldPath = "";
     String fileName = "";
     if (!"".equals(copyFile)) {
       oldPath = baseDir + "/" + copyFile;
       fileName = copyFile.substring(copyFile.lastIndexOf("/") + 1);
     } else {
       oldPath = baseDir + "/" + cutFile;
       fileName = cutFile.substring(cutFile.lastIndexOf("/") + 1);
     }
     File oldFile = new File(oldPath);
     if (!oldFile.exists()) {
       this.Response.setMessage(copyFile + "文件不存在");
       this.Response.setStatus(0);
       return;
     }
     String newPath = baseDir + "/" + currentPath + "/" + fileName;
     File newFile = new File(newPath);
     if (newFile.exists()) {
       newPath = baseDir + "/" + currentPath + "/" + "复件 " + fileName;
     }
     if (!"".equals(copyFile))
       FileUtil.copy(oldFile, newPath);
     else {
       FileUtil.move(oldFile, newPath);
     }
 
     this.Response.setMessage("粘贴成功");
     this.Response.setStatus(1);
   }
 
   public void del() {
     String baseDir = $V("baseDir");
     String currentPath = $V("currentPath");
     String delFile = $V("delFile");
 
     baseDir = baseDir.replace('\\', '/');
     currentPath = currentPath.replace('\\', '/');
     String path = baseDir + "/" + currentPath + "/" + delFile;
     File file = new File(path);
     if (!file.exists()) {
       this.Response.setMessage(delFile + "文件不存在");
       this.Response.setStatus(0);
       return;
     }
 
     if (FileUtil.delete(file)) {
       this.Response.setMessage("删除成功");
       this.Response.setStatus(1);
     } else {
       this.Response.setMessage("删除失败");
       this.Response.setStatus(0);
     }
   }
 
   public void rename() {
     String baseDir = $V("baseDir");
     String currentPath = $V("currentPath");
     String oldFileName = $V("oldFileName");
     String newFileName = $V("newFileName");
     baseDir = baseDir.replace('\\', '/');
     currentPath = currentPath.replace('\\', '/');
 
     File file = new File(baseDir + "/" + currentPath + "/" + oldFileName);
     if (file.exists()) {
       String ext = newFileName.substring(newFileName.lastIndexOf(".") + 1);
       if (!PubFun.isAllowExt(ext)) {
         this.Response.setStatus(0);
         this.Response.setMessage("重命名失败,不允许命名为" + ext + "文件！");
         return;
       }
 
       if ((file.isFile()) && ((baseDir + "/" + currentPath + "/" + oldFileName).indexOf("template") != -1))
       {
         if (newFileName.lastIndexOf(".") == -1) {
           newFileName = newFileName + ".html";
         }
         if (newFileName.endsWith(".jsp")) {
           this.Response.setStatus(0);
           this.Response.setMessage("重命名文件" + oldFileName + "失败,不能重命名为jsp文件！");
           return;
         }
       }
 
       if (file.renameTo(new File(baseDir + "/" + currentPath + "/" + newFileName))) {
         this.Response.setStatus(1);
         this.Response.setMessage("重命名保存成功！");
       } else {
         this.Response.setStatus(0);
         this.Response.setMessage("重命名文件" + oldFileName + "失败！");
       }
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage(oldFileName + "不存在！");
     }
   }
 
   public void export()
   {
     String baseDir = $V("baseDir");
     String currentPath = $V("currentPath");
     String fileName = $V("filename");
     if (fileName == null) {
       fileName = "";
     }
 
     baseDir = baseDir.replace('\\', '/');
 
     currentPath = currentPath.replace('\\', '/');
 
     String srcPath = "";
     String destPath = "";
 
     String tmpPath = Config.getContextRealPath() + "/Temp";
     tmpPath = tmpPath.replaceAll("/+", "/");
     File tmpFolder = new File(tmpPath);
     if (!tmpFolder.exists()) {
       tmpFolder.mkdirs();
     }
 
     if (StringUtil.isNotEmpty(currentPath))
       srcPath = (baseDir + "/" + currentPath + "/" + fileName).replaceAll("/+", "/");
     else {
       srcPath = (baseDir + "/" + fileName).replaceAll("/+", "/");
     }
     if (StringUtil.isEmpty(fileName)) {
       fileName = "all_export_" + System.currentTimeMillis();
     }
     destPath = (tmpPath + "/" + fileName + ".zip").replaceAll("/+", "/");
 
     if (StringUtil.isNotEmpty(srcPath));
     try {
       File file = new File(srcPath);
       OutputStream output = new FileOutputStream(destPath);
       ZipUtil.zip(file, output);
       String path = Config.getContextPath() + destPath.substring(Config.getContextRealPath().length());
       path = path.replaceAll("/+", "/");
       this.Response.put("path", path);
       this.Response.setStatus(1);
       return;
     } catch (Exception e) {
       e.printStackTrace();
 
       this.Response.setStatus(0);
     }
   }
 
   public void deploy() {
     String baseDir = $V("baseDir");
     String currentPath = $V("currentPath");
     String FileName = $V("filename");
     baseDir = baseDir.replace('\\', '/');
     currentPath = currentPath.replace('\\', '/');
     String srcPath = "";
     if (StringUtil.isNotEmpty(currentPath))
       srcPath = "/" + currentPath + "/" + FileName;
     else {
       srcPath = "/" + FileName;
     }
 
     Deploy d = new Deploy();
     ArrayList list = new ArrayList();
     list.add(srcPath);
     ZCDeployJobSet jobs = d.getJobs(Application.getCurrentSiteID(), list, "copy");
     if (jobs.size() > 0) {
       Transaction trans = new Transaction();
       trans.add(jobs, 1);
       if (trans.commit()) {
         this.Response.setStatus(1);
       } else {
         this.Response.setStatus(0);
         this.Response.setMessage("添加任务时操作数据库失败。");
       }
     } else {
       this.Response.setStatus(0);
       this.Response.setMessage("没有分发任务添加到队列中，请在＂采集与分发->分发到目录＂配置分发任务。");
       LogUtil.getLogger().info("没有添加分发任务");
     }
   }
 
   public static ArrayList getAllFiles(String filePath)
   {
     ArrayList list = new ArrayList();
     File file = new File(filePath);
 
     if (!file.exists()) {
       return list;
     }
 
     if (file.isFile()) {
       list.add(file.getPath());
       return list;
     }
 
     IOFileFilter dirFilter = FileFilterUtils.directoryFileFilter();
     IOFileFilter suffixFilter = FileFilterUtils.notFileFilter(new SuffixFileFilter("db"));
     IOFileFilter allFilter = FileFilterUtils.orFileFilter(dirFilter, suffixFilter);
     File[] files = file.listFiles(FileFilterUtils.makeSVNAware(allFilter));
 
     if (files != null) {
       for (int i = 0; i < files.length; ++i) {
         if (files[i].isDirectory()) {
           ArrayList subList = getAllFiles(files[i].getPath());
           for (int j = 0; j < subList.size(); ++j)
             list.add(subList.get(j));
         }
         else {
           list.add(files[i].getPath());
         }
       }
     }
 
     return list;
   }
 
   public static ArrayList getAllDirs(String filePath) {
     ArrayList list = new ArrayList();
     File file = new File(filePath);
 
     if ((!file.exists()) || (file.isFile())) {
       return list;
     }
 
     IOFileFilter dirFilter = FileFilterUtils.directoryFileFilter();
     File[] files = file.listFiles(FileFilterUtils.makeSVNAware(dirFilter));
 
     if (files != null) {
       for (int i = 0; i < files.length; ++i) {
         if (files[i].isDirectory()) {
           list.add(files[i].getPath());
           ArrayList subList = getAllDirs(files[i].getPath());
           for (int j = 0; j < subList.size(); ++j) {
             list.add(subList.get(j));
           }
         }
       }
     }
 
     return list;
   }
 
   public static ArrayList getFiles(String filePath)
   {
     ArrayList list = new ArrayList();
     File file = new File(filePath);
 
     if (!file.exists()) {
       return list;
     }
 
     if (file.isFile()) {
       list.add(file.getPath());
       return list;
     }
 
     IOFileFilter dirFilter = FileFilterUtils.directoryFileFilter();
     IOFileFilter suffixFilter = FileFilterUtils.notFileFilter(new SuffixFileFilter("db"));
     IOFileFilter allFilter = FileFilterUtils.orFileFilter(dirFilter, suffixFilter);
     File[] files = file.listFiles(FileFilterUtils.makeSVNAware(allFilter));
 
     if (files != null) {
       for (int i = 0; i < files.length; ++i) {
         if (files[i].isFile()) {
           list.add(files[i].getPath());
         }
       }
     }
 
     return list;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.site.FileList
 * JD-Core Version:    0.5.4
 */