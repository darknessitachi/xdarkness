 package com.zving.cmcore.tag;
 
 import com.zving.cmcore.template.TemplateBase;
 import com.zving.cmcore.template.TemplateContext;
 import com.zving.cmcore.template.TemplateManager;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 
 public class ZIncludeTag extends SimpleTag
 {
   public String[] getAllAttributeNames()
   {
     return new String[] { "file", "type" };
   }
 
   public String[] getMandantoryAttributeNames() {
     return new String[] { "file" };
   }
 
   public int onTagStart() {
     String path = this.template.getTemplateFilePath();
     String file = this.attributes.getString("file");
 
     if (path.endsWith("/")) {
       path = path.substring(0, path.length() - 1);
     }
     while (file.startsWith(".")) {
       if (file.startsWith("./")) {
         file = file.substring(2); } else {
         if (!file.startsWith("../")) break;
         file = file.substring(3);
         if (path.lastIndexOf("/") < 0) {
           this.context.addError("文件包含路径不正确:" + this.attributes.getString("file"));
         }
         path = path.substring(0, path.lastIndexOf("/"));
       }
 
     }
 
     file = path + "/" + file;
     String type = this.attributes.getString("type");
     if (this.context.isSSIContext()) {
       int level = 0;
       if ("template".equals(type))
       {
         TemplateBase tpl = TemplateManager.findTemplate(file);
         TemplateContext ctx = new TemplateContext();
         tpl.setContext(ctx);
         try {
           tpl.execute();
         } catch (Exception e) {
           e.printStackTrace();
         }
       }
       this.context.output(TemplateManager.getFileText(file));
       checkIncludeFileExists(file, 0);
       file = this.attributes.getString("file");
       file = file.substring(0, file.lastIndexOf(".")) + "_" + level + "." + 
         file.substring(file.lastIndexOf(".") + 1);
       this.context.output("<!--#include virtual=\"" + file + "\"-->");
     }
     else if ("template".equals(type)) {
       TemplateBase tpl = TemplateManager.findTemplate(file);
       tpl.setContext(this.context);
       try {
         tpl.execute();
       } catch (Exception e) {
         e.printStackTrace();
       }
     } else {
       this.context.output(TemplateManager.getFileText(file));
     }
 
     return 2;
   }
 
   private static void checkIncludeFileExists(String file, int level)
   {
   }
 
   public String getPrefix()
   {
     return "z";
   }
 
   public String getTagName() {
     return "include";
   }
 
   public boolean checkAttribute() {
     if (!super.checkAttribute()) {
       return false;
     }
     String type = this.attributes.getString("type");
     if (StringUtil.isNotEmpty(type)) {
       if ((type.equalsIgnoreCase("html")) || (type.equalsIgnoreCase("template"))) {
         return true;
       }
       this.context.addError("z:include的type属性值不正确:" + type);
     }
 
     return true;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cmcore.tag.ZIncludeTag
 * JD-Core Version:    0.5.4
 */