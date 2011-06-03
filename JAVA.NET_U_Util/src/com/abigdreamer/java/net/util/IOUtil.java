 package com.abigdreamer.java.net.util;
 
 import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
 
 public class IOUtil
 {
   public static byte[] getBytesFromStream(InputStream is)
     throws IOException
   {
     byte[] buffer = new byte[1024];
     int read = -1;
     ByteArrayOutputStream bos = new ByteArrayOutputStream();
     byte[] data = (byte[])null;
     try {
       while ((read = is.read(buffer)) != -1) {
         if (read > 0) {
           byte[] chunk = (byte[])null;
           if (read == 1024) {
             chunk = buffer;
           } else {
             chunk = new byte[read];
             System.arraycopy(buffer, 0, chunk, 0, read);
           }
           bos.write(chunk);
         }
       }
       data = bos.toByteArray();
     } finally {
       if (bos != null) {
         bos.close();
         bos = null;
       }
     }
     return data;
   }
 }

          
/*    com.xdarkness.framework.utility.IOUtil
 * JD-Core Version:    0.6.0
 */