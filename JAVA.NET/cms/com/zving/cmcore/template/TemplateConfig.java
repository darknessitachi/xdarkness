 package com.zving.cmcore.template;
 
 public class TemplateConfig
 {
   private String Type;
   private String Name;
   private String Author;
   private String Version;
   private String Description;
   private String ScriptStart;
   private String ScriptEnd;
 
   public TemplateConfig(String type, String name, String author, String version, String description, String scriptStart, String scriptEnd)
   {
     this.Type = type;
     this.Name = name;
     this.Author = author;
     this.Version = version;
     this.Description = description;
     this.ScriptEnd = scriptEnd;
     this.ScriptStart = scriptStart;
   }
 
   public String getType()
   {
     return this.Type;
   }
 
   public String getName()
   {
     return this.Name;
   }
 
   public String getAuthor()
   {
     return this.Author;
   }
 
   public String getVersion()
   {
     return this.Version;
   }
 
   public String getDescription()
   {
     return this.Description;
   }
 
   public String getScriptStart()
   {
     return this.ScriptStart;
   }
 
   public String getScriptEnd()
   {
     return this.ScriptEnd;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cmcore.template.TemplateConfig
 * JD-Core Version:    0.5.4
 */