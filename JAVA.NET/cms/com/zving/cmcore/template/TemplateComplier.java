 package com.zving.cmcore.template;
 
 import com.zving.framework.utility.Errorx;
 import com.zving.framework.utility.FileUtil;
 import com.zving.framework.utility.StringUtil;
 import com.zving.framework.utility.Treex;
 import java.io.PrintStream;
 import java.io.UnsupportedEncodingException;
 import org.apache.commons.lang.ArrayUtils;
 
 public class TemplateComplier
 {
   public void complieFile(String templateFileName)
   {
     byte[] bs = FileUtil.readByte(templateFileName);
     String text = null;
     if (StringUtil.isUTF8(bs)) {
       if (StringUtil.hexEncode(ArrayUtils.subarray(bs, 0, 3)).equals("efbbbf"))
         bs = ArrayUtils.subarray(bs, 3, bs.length);
       try
       {
         text = new String(bs, "UTF-8");
       } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
       }
     } else {
       try {
         text = new String(bs, "GBK");
       } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
       }
     }
     complieText(text);
   }
 
   public void complieText(String text) {
     TemplateParser parser = new TemplateParser(text);
     parser.parse();
     if (Errorx.hasError()) {
       System.out.println(StringUtil.join(Errorx.getMessages(), "\n"));
       return;
     }
 
     Treex tree = parser.getTree();
     TemplateConfig config = parser.getConfig();
 
     System.out.println(tree);
 
     String[] messages = Errorx.getMessages();
     if (messages.length > 0)
       System.out.println(StringUtil.join(messages, "\n"));
   }
 
   public static void main(String[] args)
   {
     TemplateComplier tc = new TemplateComplier();
     tc.complieFile("F:/Workspace_Product/ZCMS/UI/wwwroot/ZCMSDemo/template/kjindex.html");
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cmcore.template.TemplateComplier
 * JD-Core Version:    0.5.4
 */