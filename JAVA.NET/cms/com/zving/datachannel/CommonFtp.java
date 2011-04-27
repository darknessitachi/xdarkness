 package com.zving.datachannel;
 
 import com.zving.framework.utility.LogUtil;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.PrintStream;
 import org.apache.commons.net.ftp.FTPClient;
 import org.apache.commons.net.ftp.FTPReply;
 
 public class CommonFtp
 {
   private static boolean DEBUG = false;
   FTPClient ftp;
 
   public synchronized void connect(String host)
     throws IOException
   {
     connect(host, 21);
   }
 
   public synchronized void connect(String host, int port) throws IOException {
     connect(host, port, "anonymous", "anonymous@a.com");
   }
 
   public synchronized void connect(String host, int port, String user, String pass) throws IOException {
     try {
       this.ftp = new FTPClient();
 
       this.ftp.connect(host);
       System.out.println("Connected to " + host + ".");
       int reply = this.ftp.getReplyCode();
       if (!FTPReply.isPositiveCompletion(reply)) {
         this.ftp.disconnect();
         System.err.println("FTP server refused connection.");
         throw new IOException("FTP server refused connection.");
       }
 
       if (!this.ftp.login(user, pass)) {
         this.ftp.logout();
         throw new IOException("FTP server refused connection.");
       }
     }
     catch (IOException ex) {
       throw new IOException("Connect to " + host + ":" + port + " Error:  " + ex);
     }
   }
 
   public synchronized void disconnect() {
     if (this.ftp == null) {
       return;
     }
 
     if (!this.ftp.isConnected()) return;
     try {
       this.ftp.disconnect();
     }
     catch (IOException localIOException)
     {
     }
   }
 
   public synchronized boolean mkdir(String path) throws Exception {
     boolean flag = true;
     try {
       if (!this.ftp.changeWorkingDirectory(path))
         if (!this.ftp.makeDirectory(path)) {
           String parentPath = path.substring(0, path.lastIndexOf("/"));
           mkdir(parentPath);
           this.ftp.makeDirectory(path);
           LogUtil.info("FTP创建文件夹：" + path);
         } else {
           LogUtil.info("FTP创建文件夹：" + path);
         }
     }
     catch (Exception localException)
     {
     }
     return flag;
   }
 
   public synchronized boolean upload(String srcFile, String tarFile) throws Exception {
     boolean retval = true;
     String path = tarFile.substring(0, tarFile.lastIndexOf("/"));
     mkdir(path);
     this.ftp.changeWorkingDirectory(path);
     try {
       File file_in = new File(srcFile);
       if (file_in.isDirectory())
         throw new IOException("FTP cannot upload a directory.");
       this.ftp.setFileType(2);
       this.ftp.enterLocalPassiveMode();
 
       InputStream input = new FileInputStream(srcFile);
       this.ftp.storeFile(tarFile, input);
       LogUtil.info("上传文件:" + srcFile + " to " + tarFile);
       input.close();
     }
     catch (Exception ex) {
       retval = false;
       throw new Exception("上传文件失败：" + srcFile + " to " + tarFile + " Failure! " + ex);
     }
     return retval;
   }
 
   public synchronized boolean delete(String filePath)
     throws Exception
   {
     this.ftp.setFileType(2);
     this.ftp.enterLocalPassiveMode();
     return this.ftp.deleteFile(filePath);
   }
 
   public static void main(String[] args) {
     CommonFtp ftp = new CommonFtp();
     try {
       ftp.connect("192.168.1.100", 21, "test", "zving10301");
       ftp.upload("F:/Xuzhe/ZCMS/UI/wwwroot/ZCMSDemo/upload/", "/");
     }
     catch (IOException e) {
       e.printStackTrace();
     }
     catch (Exception e) {
       e.printStackTrace();
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.datachannel.CommonFtp
 * JD-Core Version:    0.5.4
 */