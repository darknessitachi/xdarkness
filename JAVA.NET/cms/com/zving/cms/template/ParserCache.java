 package com.zving.cms.template;
 
 import com.zving.framework.Config;
 import com.zving.framework.script.EvalException;
 import com.zving.framework.utility.FileUtil;
 import com.zving.framework.utility.LogUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.statical.TemplateParser;
 import java.io.File;
 import java.util.ArrayList;
 
 public class ParserCache
 {
   private static Mapx parserMap;
   private static Mapx lastModifyMap;
 
   public static TemplateParser get(long siteID, String template, int level, boolean isPageBlock, ArrayList list)
   {
     template = template.trim();
     if (parserMap == null) {
       parserMap = new Mapx();
       lastModifyMap = new Mapx();
     }
 
     File templateFile = new File(template);
     template = templateFile.getPath().replace('\\', '/');
 
     String key = template + level;
 
     TemplateParser tp = null;
 
     if (parserMap.get(key) == null) {
       if ((!templateFile.exists()) || (templateFile.isDirectory())) {
         return null;
       }
       update(siteID, templateFile, level, isPageBlock, list);
       tp = (TemplateParser)parserMap.get(template + level);
     } else {
       if ((!templateFile.exists()) || (templateFile.isDirectory())) {
         return null;
       }
       long lastModifyTime = ((Long)lastModifyMap.get(template + level)).longValue();
       if (lastModifyTime != templateFile.lastModified()) {
         update(siteID, templateFile, level, isPageBlock, list);
       }
       tp = (TemplateParser)parserMap.get(template + level);
     }
 
     Mapx map = getConfig(siteID);
     tp.define("system", map);
 
     return tp;
   }
 
   public static Mapx getConfig(long siteID)
   {
     Mapx map = new Mapx();
     String serviceUrl = Config.getValue("ServicesContext");
     String context = serviceUrl;
     if (serviceUrl.endsWith("/")) {
       context = serviceUrl.substring(0, serviceUrl.length() - 1);
     }
     int index = context.lastIndexOf('/');
     if (index != -1) {
       context = context.substring(0, index);
     }
 
     map.put("servicescontext", serviceUrl);
     map.put("cmscontext", context);
     map.put("searchaction", context + "/Search/Result.jsp");
     map.put("voteaction", serviceUrl + Config.getValue("Vote.ActionURL"));
     map.put("voteresult", serviceUrl + Config.getValue("Vote.ResultURL"));
     map.put("commentaction", serviceUrl + Config.getValue("CommentActionURL"));
     map.put("totalhitcount", "\n<script src=\"" + serviceUrl + "/Counter.jsp?Type=Total&ID=" + siteID + 
       "\" type=\"text/javascript\"></script>\n");
     map.put("todayhitcount", "\n<script src=\"" + serviceUrl + "/Counter.jsp?Type=Today&ID=" + siteID + 
       "\" type=\"text/javascript\"></script>\n");
     return map;
   }
 
   public static void update(long siteID, File templateFile, int level, boolean isPageBlock, ArrayList list) {
     String content = FileUtil.readText(templateFile);
     String templatePath = templateFile.getPath().replace('\\', '/');
 
     LogUtil.info("模板更新" + templatePath);
 
     PreParser p = new PreParser();
     p.setSiteID(siteID);
     p.setTemplateFileName(templatePath);
     p.parse();
 
     TagParser tagParser = new TagParser();
     tagParser.setSiteID(siteID);
     tagParser.setTemplateFileName(templatePath);
     tagParser.setPageBlock(isPageBlock);
     tagParser.setContent(content);
     tagParser.setDirLevel(level);
     tagParser.parse();
 
     TemplateParser tp = new TemplateParser();
     tp.setFileName(templateFile.getPath());
     tp.addClass("com.zving.cms.pub.CatalogUtil");
     tp.addClass("com.zving.cms.pub.SiteUtil");
     tp.addClass("com.zving.cms.pub.PubFun");
     tp.addClass("com.zving.cms.template.TemplateUtil");
     tp.setPageListPrams(tagParser.getPageListPrams());
     tp.setTemplate(tagParser.getContent());
     try
     {
       tp.parse();
 
       parserMap.put(templatePath + level, tp);
       lastModifyMap.put(templatePath + level, new Long(templateFile.lastModified()));
 
       list.addAll(tagParser.getFileList());
     } catch (EvalException e) {
       e.printStackTrace();
     }
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.template.ParserCache
 * JD-Core Version:    0.5.4
 */