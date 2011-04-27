 package com.zving.cmcore.template;
 
 import com.zving.framework.utility.Mapx;
 
 public class TemplateFragment
 {
   public static final int FRAGMENT_HTML = 1;
   public static final int FRAGMENT_TAG = 2;
   public static final int FRAGMENT_HOLDER = 3;
   public static final int FRAGMENT_SCRIPT = 4;
   public int Type;
   public String TagPrefix;
   public String TagName;
   public Mapx Attributes;
   public String FragmentText;
   public int StartLineNo;
   public int StartCharIndex;
   public int EndCharIndex;
 
   public String toString()
   {
     StringBuffer sb = new StringBuffer();
     if (this.Type == 1) {
       sb.append("HTML");
     }
     if (this.Type == 2) {
       sb.append("TAG");
     }
     if (this.Type == 4) {
       sb.append("SCRIPT");
     }
     if (this.Type == 3) {
       sb.append("HOLDER");
     }
     sb.append(":");
     if (this.Type == 2) {
       sb.append("<" + this.TagPrefix + ":" + this.TagName);
     }
     if (this.Type == 3) {
       sb.append("${");
     }
     if (this.FragmentText != null) {
       String str = this.FragmentText.replaceAll("[\\n\\r]+", "\\\\n");
       str = this.FragmentText.replaceAll("\\s+", " ");
       if (str.length() > 100) {
         str = str.substring(0, 100);
       }
       sb.append(str);
     }
     if (this.Type == 3) {
       sb.append("}");
     }
     return sb.toString();
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cmcore.template.TemplateFragment
 * JD-Core Version:    0.5.4
 */