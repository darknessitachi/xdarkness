 package com.zving.cms.site;
 
 import com.zving.cms.datachannel.Deploy;
 import com.zving.cms.pub.PubFun;
 import com.zving.cms.template.PreParser;
 import com.zving.framework.Config;
 import com.zving.framework.Page;
 import com.zving.framework.ResponseImpl;
 import com.zving.framework.controls.TreeAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.utility.FileUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.framework.utility.ZipUtil;
 import com.zving.platform.Application;
 import java.io.File;
 import java.util.ArrayList;
 import org.apache.commons.io.filefilter.FileFilterUtils;
 
 public class Template extends Page
 {
   public static void treeDataBind(TreeAction ta)
   {
     Object obj = ta.getParams().get("SiteID");
     String siteID = Application.getCurrentSiteID();
     DataTable dt = new QueryBuilder("select ID,ParentID,Level,Name from ZCCatalog Where SiteID=?", siteID).executeDataTable();
     String siteName = new QueryBuilder("select name from ZCSite where id=?", siteID).executeString();
     ta.setRootText(siteName);
     ta.bindData(dt);
   }
 
   public boolean unzipFile(String zipFileName, String upzipPath, String siteCode)
   {
     String copyToPath = Config.getContextRealPath() + Config.getValue("Statical.TemplateDir") + "/" + siteCode;
     copyToPath = copyToPath.replace('\\', '/').replaceAll("/+", "/");
     upzipPath = upzipPath.replace('\\', '/').replaceAll("/+", "/");
     if (ZipUtil.unzip(zipFileName, upzipPath, true)) {
       FileUtil.delete(zipFileName);
     }
 
     ArrayList deployList = new ArrayList();
     File unzipFile = new File(upzipPath);
     ArrayList fileList = FileList.getAllFiles(upzipPath);
     for (int i = 0; i < fileList.size(); ++i) {
       String fileName = (String)fileList.get(i);
       fileName = fileName.replace('\\', '/').replaceAll("/+", "/");
       String ext = (fileName.lastIndexOf(".") == -1) ? "" : fileName.substring(fileName.lastIndexOf(".") + 1);
       String destPath = fileName.replaceAll(upzipPath, copyToPath);
       if (!PubFun.isAllowExt(ext)) {
         this.Response.setStatus(0);
         this.Response.setMessage("导入失败,不允许创建" + ext + "文件！");
         return false;
       }
       String destDirs = destPath.substring(0, destPath.lastIndexOf("/"));
       File dirs = new File(destDirs);
       if (!dirs.exists()) {
         dirs.mkdirs();
       }
 
       FileUtil.copy(fileName, destPath);
       deployList.add(destPath);
     }
 
     FileUtil.delete(unzipFile);
 
     Deploy d = new Deploy();
     d.addJobs(Application.getCurrentSiteID(), deployList);
 
     return true;
   }
 
   public boolean processFile(String fileFullName, String siteCode)
   {
     ArrayList deployList = new ArrayList();
     File file = new File(fileFullName);
     if (!file.exists()) {
       return false;
     }
     String fileName = file.getName();
 
     String copyToPath = Config.getContextRealPath() + Config.getValue("Statical.TemplateDir") + "/" + siteCode;
 
     String ext = (fileName.lastIndexOf(".") == -1) ? "" : fileName.substring(fileName.lastIndexOf(".") + 1);
     if (("html".equalsIgnoreCase(ext)) || ("htm".equalsIgnoreCase(ext)) || ("jsp".equalsIgnoreCase(ext)) || 
       ("js".equalsIgnoreCase(ext)) || ("php".equalsIgnoreCase(ext)) || ("jsp".equalsIgnoreCase(ext)) || 
       ("asp".equalsIgnoreCase(ext))) {
       String fileText = FileUtil.readText(file);
       String tplPath = copyToPath + "/template/";
       FileUtil.mkdir(tplPath);
       FileUtil.writeText(tplPath + fileName, fileText);
       deployList.add(tplPath + fileName);
     } else {
       FileUtil.mkdir(copyToPath + "/images/");
       FileUtil.copy(file, copyToPath + "/images/" + fileName);
       deployList.add(copyToPath + "/images/" + fileName);
     }
 
     FileUtil.delete(file);
 
     Deploy d = new Deploy();
     d.addJobs(Application.getCurrentSiteID(), deployList);
 
     return true;
   }
 
   public void preParse() {
     String path = $V("Path");
     if (StringUtil.isEmpty(path)) {
       this.Response.setStatus(0);
       this.Response.setMessage("模板路径为空!");
       return;
     }
 
     File file = new File(path);
     boolean flag = true;
     if (file.exists()) {
       PreParser p = new PreParser();
       p.setSiteID(Application.getCurrentSiteID());
       File[] templates = file.listFiles(FileFilterUtils.makeSVNAware(FileFilterUtils.trueFileFilter()));
       for (int i = 0; i < templates.length; ++i) {
         p.setTemplateFileName(templates[i].getPath());
         if (!p.parse())
           flag = true;
       }
     }
     else {
       this.Response.setStatus(0);
       this.Response.setMessage("文件不存在!");
       return;
     }
 
     if (flag) {
       this.Response.setStatus(0);
       this.Response.setMessage("处理成功!");
     } else {
       this.Response.setStatus(1);
       this.Response.setMessage("处理失败!");
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.site.Template
 * JD-Core Version:    0.5.4
 */