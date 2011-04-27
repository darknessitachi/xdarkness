 package com.abigdreamer.java.net.ssi;
 
 import java.io.ByteArrayOutputStream;

import javax.servlet.ServletOutputStream;
 
 public class ByteArrayServletOutputStream extends ServletOutputStream
 {
   protected ByteArrayOutputStream buf = null;
 
   public ByteArrayServletOutputStream()
   {
     this.buf = new ByteArrayOutputStream();
   }
 
   public byte[] toByteArray()
   {
     return this.buf.toByteArray();
   }
 
   public void write(int b)
   {
     this.buf.write(b);
   }
 }

          
/*    com.xdarkness.framework.ssi.ByteArrayServletOutputStream
 * JD-Core Version:    0.6.0
 */