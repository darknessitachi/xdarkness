 package com.zving.cms.datachannel;
 
 import com.jcraft.jsch.JSchException;
 import com.zving.cms.pub.SiteUtil;
 import com.zving.cms.site.FileList;
 import com.zving.datachannel.CommonFtp;
 import com.zving.datachannel.SFtp;
 import com.zving.framework.Config;
 import com.zving.framework.User;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.messages.LongTimeTask;
 import com.zving.framework.utility.Errorx;
 import com.zving.framework.utility.FileUtil;
 import com.zving.framework.utility.LogUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import com.zving.platform.Application;
 import com.zving.platform.pub.NoUtil;
 import com.zving.schema.ZCDeployConfigSchema;
 import com.zving.schema.ZCDeployConfigSet;
 import com.zving.schema.ZCDeployJobSchema;
 import com.zving.schema.ZCDeployJobSet;
 import com.zving.schema.ZCDeployLogSchema;
 import java.io.File;
 import java.io.IOException;
 import java.util.ArrayList;
 import java.util.Date;
 import org.apache.commons.logging.Log;
 
 public class Deploy
 {
   public static final int READY = 0;
   public static final int EXECUTION = 1;
   public static final int SUCCESS = 2;
   public static final int FAIL = 3;
   public static final String TemplateDIR = "template";
   public static final String OPERATION_COPY = "copy";
   public static final String OPERATION_DELETE = "delete";
   public static final Mapx depolyStatus = new Mapx();
 
   static {
     depolyStatus.put("0", "就绪");
     depolyStatus.put("1", "执行中");
     depolyStatus.put("2", "成功");
     depolyStatus.put("3", "失败");
   }
 
   public boolean addOneJob(long configID, boolean immediate)
   {
     ZCDeployJobSchema job = new ZCDeployJobSchema();
     ZCDeployConfigSchema config = new ZCDeployConfigSchema();
     config.setID(configID);
     if (!config.fill()) {
       return false;
     }
 
     String staticDir = Config.getContextRealPath() + Config.getValue("Statical.TargetDir").replace('\\', '/');
     String sourcePath = staticDir + "/" + Application.getCurrentSiteAlias() + config.getSourceDir();
     job.setID(NoUtil.getMaxID("DeployJobID"));
     job.setConfigID(config.getID());
     job.setSource(sourcePath);
     job.setMethod(config.getMethod());
 
     String targetDir = config.getTargetDir();
     if (StringUtil.isEmpty(targetDir)) {
       targetDir = "/";
     }
     else if (!targetDir.endsWith("/")) {
       targetDir = targetDir + "/";
     }
 
     job.setTarget(targetDir);
     job.setSiteID(config.getSiteID());
     job.setHost(config.getHost());
     job.setPort(config.getPort());
     job.setUserName(config.getUserName());
     job.setPassword(config.getPassword());
     job.setStatus(0L);
     job.setAddTime(new Date());
     job.setAddUser(User.getUserName());
 
     Transaction trans = new Transaction();
     trans.add(job, 1);
     if (trans.commit()) {
       if (immediate) {
         executeJob(config, job);
       }
       return true;
     }
     LogUtil.getLogger().info("添加部署任务时，数据库操作失败");
     return false;
   }
 
   public boolean addJobs(long siteID, ArrayList list)
   {
     return addJobs(siteID, list, "copy");
   }
 
   public boolean addJobs(long siteID, ArrayList list, String operation) {
     ZCDeployJobSet set = getJobs(siteID, list, operation);
     Transaction trans = new Transaction();
     trans.add(set, 1);
     if (trans.commit())
     {
       return true;
     }
     LogUtil.getLogger().info("添加部署任务时，数据库操作失败");
     return false;
   }
 
   public long copyOuterDir()
   {
     LongTimeTask ltt = new LongTimeTask() {
       public void execute() {
         ZCDeployConfigSet configSet = new ZCDeployConfigSchema().query();
         if (configSet.size() > 0)
           for (int i = 0; i < configSet.size(); ++i) {
             ZCDeployJobSchema job = new ZCDeployJobSchema();
             ZCDeployJobSet jobs = job.query(
               new QueryBuilder("where RetryCount=0 and status<>? and configID=? order by id", 
               2L, configSet.get(i).getID()));
             if ((jobs != null) && (jobs.size() > 0)) {
               LogUtil.getLogger().info("执行分发任务 任务数：" + jobs.size());
               Deploy.this.executeBatchJob(configSet.get(i), jobs);
             }
           }
       }
     };
     ltt.setUser(User.getCurrent());
     ltt.start();
     return ltt.getTaskID();
   }
 
   public ZCDeployJobSet getJobs(long siteID, ArrayList list, String operation) {
     ZCDeployJobSet jobSet = new ZCDeployJobSet();
     for (int j = 0; j < list.size(); ++j) {
       String srcPath = (String)list.get(j);
       if (StringUtil.isEmpty(srcPath)) {
         continue;
       }
       srcPath = srcPath.replace('\\', '/').replaceAll("///", "/").replaceAll("//", "/");
 
       String baseDir = Config.getContextRealPath() + Config.getValue("Statical.TargetDir").replace('\\', '/');
       baseDir = baseDir + "/" + SiteUtil.getAlias(siteID);
 
       baseDir = baseDir.replaceAll("///", "/");
       baseDir = baseDir.replaceAll("//", "/");
       srcPath = srcPath.replaceAll(baseDir, "");
 
       ZCDeployConfigSchema config = new ZCDeployConfigSchema();
 
       QueryBuilder qb = new QueryBuilder(" where UseFlag =1 and siteid=? and ? like concat(sourcedir,'%')", siteID, srcPath);
       if (Config.isSQLServer()) {
         qb.setSQL(" where siteid=? and charindex(sourcedir,?)=0");
       }
       if (Config.isDB2()) {
         qb.setSQL(" where siteid=? and locate(sourcedir,'" + srcPath + "')=0");
         qb.getParams().remove(qb.getParams().size() - 1);
       }
 
       ZCDeployConfigSet set = config.query(qb);
 
       for (int i = 0; i < set.size(); ++i) {
         config = set.get(i);
         String target = config.getTargetDir();
         if (StringUtil.isEmpty(target)) {
           target = "/";
         }
         else if (!target.endsWith("/")) {
           target = target + "/";
         }
 
         String filePath = srcPath;
         if (!config.getSourceDir().equals("/")) {
           filePath = srcPath.replaceAll(config.getSourceDir(), "");
         }
         target = dealFileName(target, filePath);
 
         ZCDeployJobSchema job = new ZCDeployJobSchema();
         job.setID(NoUtil.getMaxID("DeployJobID"));
         job.setConfigID(config.getID());
         job.setSource(baseDir + srcPath);
         job.setMethod(config.getMethod());
         job.setTarget(target);
         job.setSiteID(config.getSiteID());
         job.setHost(config.getHost());
         job.setPort(config.getPort());
         job.setUserName(config.getUserName());
         job.setPassword(config.getPassword());
         job.setRetryCount(0L);
         job.setStatus(0L);
         job.setOperation(operation);
         job.setAddTime(new Date());
         if (User.getCurrent() != null)
           job.setAddUser(User.getUserName());
         else {
           job.setAddUser("SYS");
         }
 
         jobSet.add(job);
       }
     }
 
     return jobSet;
   }
 
   private String dealFileName(String part1, String part2) {
     if ((part1.equals("")) || (part2.equals("")))
       return part1 + part2;
     if ((!part1.endsWith("/")) && (!part2.startsWith("/")))
       return part1 + "/" + part2;
     if ((part1.endsWith("/")) && (part2.startsWith("/"))) {
       return part1 + part2.substring(1);
     }
     return part1 + part2;
   }
 
   public boolean executeJob(ZCDeployJobSchema job) {
     ZCDeployConfigSchema config = new ZCDeployConfigSchema();
     config.setID(job.getConfigID());
     if (!config.fill()) {
       return false;
     }
 
     return executeJob(config, job);
   }
 
   public boolean executeJob(long jobID)
   {
     ZCDeployJobSchema job = new ZCDeployJobSchema();
     job.setID(jobID);
     if (!job.fill()) {
       return false;
     }
 
     ZCDeployConfigSchema config = new ZCDeployConfigSchema();
     config.setID(job.getConfigID());
     if (!config.fill()) {
       return false;
     }
 
     return executeJob(config, job);
   }
 
   public boolean executeJob(ZCDeployConfigSchema config, ZCDeployJobSchema job) {
     ZCDeployJobSet set = new ZCDeployJobSet();
     set.add(job);
     return executeBatchJob(config, set);
   }
 
   public boolean executeBatchJob(ZCDeployConfigSchema config, ZCDeployJobSet jobs)
   {
     if ((config == null) || (jobs.size() < 1)) {
       return false;
     }
 
     Transaction trans = new Transaction();
     String message = "";
     boolean connectFlag = true;
 
     String deployMethod = config.getMethod();
     if ("DIR".equals(deployMethod)) {
       for (int i = 0; i < jobs.size(); ++i) {
         ZCDeployJobSchema job = jobs.get(i);
 
         ZCDeployLogSchema jobLog = new ZCDeployLogSchema();
         jobLog.setID(NoUtil.getMaxID("DeployLogID"));
         jobLog.setSiteID(job.getSiteID());
         jobLog.setJobID(job.getID());
         jobLog.setBeginTime(new Date());
 
         if (job.getStatus() == 3L) {
           job.setRetryCount(job.getRetryCount() + 1L);
         }
 
         String sourceFile = job.getSource();
         if (sourceFile.indexOf("template") != -1) {
           LogUtil.getLogger().info("模板文件" + sourceFile + "不复制，跳过");
           return true;
         }
 
         String target = job.getTarget();
         target = target.replace('\\', '/');
         String targetDir = target.substring(0, target.lastIndexOf("/"));
         File dir = new File(targetDir);
         if (!dir.exists()) {
           dir.mkdirs();
         }
         if (!targetDir.endsWith("/template")) {
           if ("delete".equalsIgnoreCase(job.getOperation())) {
             if (FileUtil.delete(target)) {
               message = "成功删除文件" + target;
               LogUtil.getLogger().info(message);
               job.setStatus(2L);
             } else {
               message = "失败：删除文件" + target;
               LogUtil.getLogger().info(message);
               job.setStatus(3L);
               Errorx.addError(message);
             }
           }
           else if (FileUtil.copy(sourceFile, target)) {
             message = "成功复制文件" + sourceFile + "到" + target;
             LogUtil.getLogger().info(message);
             job.setStatus(2L);
           } else {
             message = "失败：复制文件" + sourceFile + "到" + target;
             LogUtil.getLogger().info(message);
             job.setStatus(3L);
             Errorx.addError(message);
           }
 
         }
 
         jobLog.setMessage(message);
         jobLog.setEndTime(new Date());
         LogUtil.getLogger().info(message);
 
         trans.add(jobLog, 1);
         trans.add(job, 2);
       }
     } else if ("FTP".equals(deployMethod)) {
       CommonFtp ftp = new CommonFtp();
       try {
         ftp.connect(config.getHost(), (int)config.getPort(), config.getUserName(), config.getPassword());
         connectFlag = true;
       } catch (IOException e1) {
         e1.printStackTrace();
 
         ZCDeployLogSchema jobLog = new ZCDeployLogSchema();
         jobLog.setID(NoUtil.getMaxID("DeployLogID"));
         jobLog.setSiteID(config.getSiteID());
         jobLog.setJobID(jobs.get(0).getID());
         jobLog.setBeginTime(new Date());
         jobLog.setEndTime(new Date());
         jobLog.setMessage(e1.getMessage());
         trans.add(jobLog, 1);
         connectFlag = false;
       }
       if (connectFlag) {
         for (int i = 0; i < jobs.size(); ++i) {
           ZCDeployJobSchema job = jobs.get(i);
 
           ZCDeployLogSchema jobLog = new ZCDeployLogSchema();
           jobLog.setID(NoUtil.getMaxID("DeployLogID"));
           jobLog.setSiteID(job.getSiteID());
           jobLog.setJobID(job.getID());
           jobLog.setBeginTime(new Date());
 
           if (job.getStatus() == 3L) {
             job.setRetryCount(job.getRetryCount() + 1L);
           }
 
           String target = job.getTarget();
           target = target.replace('\\', '/');
           if ("delete".equalsIgnoreCase(job.getOperation()))
             try {
               target = target.replaceAll("///", "/");
               if (ftp.delete(target)) {
                 message = "FTP删除文件成功";
                 job.setStatus(2L);
               } else {
                 message = "FTP删除文件失败";
                 job.setStatus(3L);
               }
             } catch (Exception e) {
               job.setStatus(3L);
               message = e.getMessage();
               Errorx.addError(message);
             }
           else {
             try {
               String srcFile = job.getSource();
               srcFile = srcFile.replaceAll("///", "/");
               srcFile = srcFile.replaceAll("//", "/");
               String path = srcFile;
               ArrayList list = FileList.getAllFiles(path);
               if (list.size() == 0) {
                 job.setStatus(3L);
                 message = "文件不存在：" + path;
                 Errorx.addError(message);
               } else {
                 for (int j = 0; j < list.size(); ++j) {
                   String name = (String)list.get(j);
                   if (name.indexOf("template") != -1) {
                     continue;
                   }
                   name = name.replace('\\', '/');
                   String targetName = name.replaceAll(path, "");
                   ftp.upload(name, target + targetName);
                 }
                 job.setStatus(2L);
                 message = "FTP上传成功";
               }
             } catch (Exception e) {
               job.setStatus(3L);
               message = e.getMessage();
               Errorx.addError(message);
             }
           }
           jobLog.setMessage(message);
           jobLog.setEndTime(new Date());
           LogUtil.getLogger().info(message);
 
           trans.add(jobLog, 1);
           trans.add(job, 2);
         }
       }
       ftp.disconnect();
     } else if ("SFTP".equals(deployMethod)) {
       SFtp sftp = new SFtp();
       try {
         sftp.connect(config.getHost(), (int)config.getPort(), config.getUserName(), config.getPassword());
         connectFlag = true;
       } catch (JSchException e1) {
         e1.printStackTrace();
 
         ZCDeployLogSchema jobLog = new ZCDeployLogSchema();
         jobLog.setID(NoUtil.getMaxID("DeployLogID"));
         jobLog.setSiteID(config.getSiteID());
         jobLog.setJobID(jobs.get(0).getID());
         jobLog.setBeginTime(new Date());
         jobLog.setEndTime(new Date());
         jobLog.setMessage(e1.getMessage());
         trans.add(jobLog, 1);
         connectFlag = false;
       }
       if (connectFlag) {
         for (int i = 0; i < jobs.size(); ++i) {
           ZCDeployJobSchema job = jobs.get(i);
 
           ZCDeployLogSchema jobLog = new ZCDeployLogSchema();
           jobLog.setID(NoUtil.getMaxID("DeployLogID"));
           jobLog.setSiteID(job.getSiteID());
           jobLog.setJobID(job.getID());
           jobLog.setBeginTime(new Date());
 
           if (job.getStatus() == 3L) {
             job.setRetryCount(job.getRetryCount() + 1L);
           }
 
           String target = job.getTarget();
           target = target.replace('\\', '/');
           if ("delete".equalsIgnoreCase(job.getOperation()))
             try {
               target = target.replaceAll("///", "/");
               if (sftp.delete(target)) {
                 message = "SFTP删除文件成功";
                 job.setStatus(2L);
               } else {
                 message = "SFTP删除文件失败";
                 job.setStatus(3L);
               }
             } catch (Exception e) {
               job.setStatus(3L);
               message = e.getMessage();
               Errorx.addError(message);
             }
           else {
             try {
               String srcFile = job.getSource();
               srcFile = srcFile.replaceAll("///", "/");
               srcFile = srcFile.replaceAll("//", "/");
               String path = srcFile;
               ArrayList list = FileList.getAllFiles(path);
               if (list.size() == 0) {
                 job.setStatus(3L);
                 message = "文件不存在" + srcFile;
               } else {
                 for (int j = 0; j < list.size(); ++j) {
                   String name = (String)list.get(j);
                   if (name.indexOf("template") != -1) {
                     continue;
                   }
                   name = name.replace('\\', '/');
                   String targetName = name.replaceAll(path, "");
                   sftp.upload(name, target + targetName);
                 }
                 job.setStatus(2L);
                 message = "SFTP上传成功";
               }
             } catch (Exception e) {
               job.setStatus(3L);
               message = e.getMessage();
               Errorx.addError(message);
             }
           }
           jobLog.setMessage(message);
           jobLog.setEndTime(new Date());
           LogUtil.getLogger().info(message);
 
           trans.add(jobLog, 1);
           trans.add(job, 2);
         }
       }
       sftp.disconnect();
     }
 
     if (trans.commit()) {
       return true;
     }
     LogUtil.getLogger().info("添加部署任务时，数据库操作失败");
     Errorx.addError(message);
     return false;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.datachannel.Deploy
 * JD-Core Version:    0.5.4
 */