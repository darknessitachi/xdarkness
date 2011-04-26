 package com.xdarkness.datachannel;
 
 import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.xdarkness.framework.util.LogUtil;
 
 public class SFtp
 {
   ChannelSftp sftp = null;
 
   public void connect(String host, int port, String username, String password)
     throws JSchException
   {
     JSch jsch = new JSch();
     jsch.getSession(username, host, port);
     Session sshSession = jsch.getSession(username, host, port);
     sshSession.setPassword(password);
     Properties sshConfig = new Properties();
     sshConfig.put("StrictHostKeyChecking", "no");
     sshSession.setConfig(sshConfig);
     LogUtil.info("SFTP：" + host + " 开始连接...");
     sshSession.connect();
     Channel channel = sshSession.openChannel("sftp");
     channel.connect();
     this.sftp = ((ChannelSftp)channel);
     LogUtil.info("SFTP：" + host + " 连接建立...");
   }
 
   public void disconnect() {
     if (this.sftp == null) {
       return;
     }
 
     if (this.sftp.isConnected())
       try {
         this.sftp.getSession().disconnect();
         this.sftp.disconnect();
       } catch (JSchException e) {
         e.printStackTrace();
       }
   }
 
   public boolean upload(String srcFile, String tarFile)
     throws Exception
   {
     System.out.println("upload file " + srcFile + " to " + tarFile);
     boolean flag = true;
     String path = tarFile.substring(0, tarFile.lastIndexOf("/"));
     path = path.replaceAll("///", "/");
     path = path.replaceAll("//", "/");
 
     mkdir(path);
 
     this.sftp.cd(path);
     File file = new File(srcFile);
     FileInputStream fis = new FileInputStream(file);
     this.sftp.put(fis, tarFile);
     fis.close();
 
     System.out.println("文件SFTP上传成功:" + srcFile);
     return flag;
   }
 
   public boolean mkdir(String path)
   {
     boolean flag = true;
     try {
       this.sftp.cd(path);
     } catch (SftpException e) {
       try {
         this.sftp.mkdir(path);
       } catch (SftpException e1) {
         String parentPath = path.substring(0, path.lastIndexOf("/"));
         mkdir(parentPath);
         try {
           this.sftp.mkdir(path);
         } catch (SftpException e2) {
           e2.printStackTrace();
         }
       }
     }
     return flag;
   }
 
   public void download(String directory, String downloadFile, String saveFile)
   {
     try
     {
       this.sftp.cd(directory);
       File file = new File(saveFile);
       this.sftp.get(downloadFile, new FileOutputStream(file));
     } catch (Exception e) {
       e.printStackTrace();
     }
   }
 
   public boolean delete(String deleteFile)
   {
     boolean flag = true;
     try {
       this.sftp.rm(deleteFile);
     } catch (Exception e) {
       e.printStackTrace();
       flag = false;
     }
     return flag;
   }
 
   public Vector listFiles(String directory, ChannelSftp sftp)
     throws SftpException
   {
     return sftp.ls(directory);
   }
 
   public static void main(String[] args) {
     SFtp sf = new SFtp();
     String host = "10.192.18.195";
     int port = 22;
     String username = "";
     String password = "";
     String targetFile = "/app/was6/sftp/wwwroot/Image/gahdzpzs/5542583.gif";
     String uploadFile = "F:/workspace_ibm/cms/UI/wwwroot/cpic/upload/Image/gahdzpzs/5542583.gif";
     try {
       sf.connect(host, port, username, password);
     }
     catch (JSchException e) {
       e.printStackTrace();
     }
     try {
       sf.upload(uploadFile, targetFile);
     } catch (Exception e) {
       e.printStackTrace();
     }
 
     sf.disconnect();
   }
 }

          
/*    com.xdarkness.datachannel.SFtp
 * JD-Core Version:    0.6.0
 */