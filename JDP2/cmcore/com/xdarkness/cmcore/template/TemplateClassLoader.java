 package com.xdarkness.cmcore.template;
 
 import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
 
 public class TemplateClassLoader extends ClassLoader
 {
   private String dir;
 
   public TemplateClassLoader(ClassLoader parent, String dir)
   {
     super(parent);
     this.dir = dir;
   }
 
   protected Class findClass(String name) throws ClassNotFoundException {
     byte[] bytes = loadClassBytes(name);
     Class theClass = defineClass(name, bytes, 0, bytes.length);
     if (theClass == null) {
       throw new ClassFormatError();
     }
     return theClass;
   }
 
   private byte[] loadClassBytes(String className) throws ClassNotFoundException {
     try {
       String classFile = getClassFile(className);
       FileInputStream fis = new FileInputStream(classFile);
       FileChannel fileC = fis.getChannel();
       ByteArrayOutputStream baos = new ByteArrayOutputStream();
       WritableByteChannel outC = Channels.newChannel(baos);
       ByteBuffer buffer = ByteBuffer.allocate(1024);
       while (true) {
         int i = fileC.read(buffer);
         if ((i == 0) || (i == -1)) {
           break;
         }
         buffer.flip();
         outC.write(buffer);
         buffer.clear();
       }
       fis.close();
       return baos.toByteArray(); } catch (IOException fnfe) {
     }
     throw new ClassNotFoundException(className);
   }
 
   private String getClassFile(String name)
   {
     StringBuffer sb = new StringBuffer(this.dir);
     name = name.replace('.', File.separatorChar) + ".class";
     sb.append(File.separator + name);
     return sb.toString();
   }
 
   protected URL findResource(String name) {
     try {
       URL url = super.findResource(name);
       if (url != null) {
         return url;
       }
       url = new URL("file:///" + converName(name));
       return url; } catch (MalformedURLException mue) {
     }
     return null;
   }
 
   private String converName(String name)
   {
     StringBuffer sb = new StringBuffer(this.dir);
     name = name.replace('.', File.separatorChar);
     sb.append(File.separator + name);
     return sb.toString();
   }
 }

          
/*    com.xdarkness.cmcore.template.TemplateClassLoader
 * JD-Core Version:    0.6.0
 */