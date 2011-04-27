 package com.zving.cms.api;
 
 import com.zving.framework.controls.DataGridAction;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.utility.LogUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.ServletUtil;
 import com.zving.framework.utility.StringUtil;
 import com.zving.schema.ZCFullTextSchema;
 import com.zving.schema.ZCFullTextSet;
 import com.zving.search.ArticleIndexer;
 import com.zving.search.ArticleSearcher;
 import com.zving.search.ResourceIndexer;
 import com.zving.search.SearchParameters;
 import com.zving.search.SearchResult;
 import java.io.UnsupportedEncodingException;
 import javax.servlet.http.HttpServletRequest;
 import org.apache.commons.logging.Log;
 
 public class SearchAPI
 {
   private static Mapx siteMap = new Mapx();
 
   public static String getIndexIDBySiteID(String site) {
     if ((StringUtil.isEmpty(site)) || (!StringUtil.isDigit(site))) {
       return null;
     }
     String id = siteMap.getString(site);
     if (id == null) {
       ZCFullTextSchema ft = new ZCFullTextSchema();
       ft.setSiteID(site);
       ft.setProp1("AutoIndex");
       ZCFullTextSet set = ft.query();
       if (set.size() > 0) {
         id = set.get(0).getID();
         siteMap.put(site, id);
       } else {
         return null;
       }
     }
     return id;
   }
 
   public static void update(long id)
   {
     ZCFullTextSchema ft = new ZCFullTextSchema();
     ft.setID(id);
     if (ft.fill()) {
       if (ft.getType().equals("Article"))
         updateArticleIndex(id);
       else
         updateResourceIndex(id);
     }
     else
       LogUtil.getLogger().warn("没有ID=" + id + "的全文检索!");
   }
 
   public static void updateArticleIndex(long id)
   {
     ArticleIndexer ai = new ArticleIndexer(id);
     ai.start();
   }
 
   public static void updateResourceIndex(long id)
   {
     ResourceIndexer ri = new ResourceIndexer(id);
     ri.start();
   }
 
   public static SearchResult searchArticle(long id, String keyword, int pageSize, int pageIndex) {
     SearchParameters sps = new SearchParameters();
     sps.addFulltextField("_KeyWord", keyword);
     sps.setPageIndex(pageIndex);
     sps.setPageSize(pageSize);
     sps.setIndexID(id);
     return ArticleIndexer.search(sps);
   }
 
   public static SearchResult searchArticle(String keyword, int pageSize, int pageIndex, Mapx map) {
     map.put("keyword", keyword);
     map.put("page", pageIndex);
     map.put("size", pageSize);
     return ArticleSearcher.search(map);
   }
 
   public static SearchResult searchArticle(long id, String keyword, int pageSize, int pageIndex, String catalog) {
     Mapx map = new Mapx();
     map.put("catalog", catalog);
     map.put("keyword", keyword);
     map.put("page", pageIndex);
     map.put("size", pageSize);
     return ArticleSearcher.search(map);
   }
 
   public static SearchResult search(long id, String keyword, int pageSize, int pageIndex, Mapx map) {
     map.put("keyword", keyword);
     map.put("page", pageIndex);
     map.put("size", pageSize);
     return ArticleSearcher.search(map);
   }
 
   public static String getPageURL(Mapx map, int pageNo) {
     map.put("page", pageNo);
     return ServletUtil.getQueryStringFromMap(map, true);
   }
 
   public static String getURL(Mapx map, String text, String type, String value) {
     Mapx map2 = (Mapx)map.clone();
     if ((value != null) && (value.equals(map2.get(type)))) {
       return " ·" + text;
     }
     map2.put(type, value);
     return " ·<a href='" + ServletUtil.getQueryStringFromMap(map2, true) + "'>" + text + "</a>";
   }
 
   public static String getParameter(HttpServletRequest request, String key)
   {
     String queryString = request.getQueryString();
     return getParameter(queryString, key);
   }
 
   public static String getParameter(String url, String key) {
     if (url.indexOf('?') > 0) {
       url = url.substring(url.indexOf('?') + 1);
     }
     Mapx map = StringUtil.splitToMapx(url, "&", "=");
     String keyword = map.getString(key);
     if (StringUtil.isNotEmpty(keyword)) {
       if (keyword.indexOf('%') < 0) {
         return keyword;
       }
       if (keyword.startsWith("%00")) {
         keyword = keyword.substring(3);
         keyword = StringUtil.urlDecode(keyword, "UTF-8");
         return keyword;
       }
       byte[] bs;
       if (keyword.indexOf("%") >= 0) {
         keyword = StringUtil.replaceEx(keyword, "?", "");
         keyword = StringUtil.replaceEx(keyword, "+", "%20");
         keyword = StringUtil.replaceEx(keyword, " ", "%20");
         bs = StringUtil.hexDecode(StringUtil.replaceEx(keyword, "%", ""));
       }try {
         String result = null;
         if ((bs.length >= 3) && (StringUtil.isUTF8(bs))) {
           result = new String(bs, "UTF-8");
           String test = new String(result.getBytes());
           if (test.indexOf("?") >= 0)
             result = new String(bs, "GBK");
         }
         else {
           result = new String(bs, "GBK");
           String test = new String(result.getBytes());
           if (test.indexOf("?") >= 0) {
             result = new String(bs, "UTF-8");
           }
         }
         return result;
       } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
 
         return keyword;
       }
     }
     return null;
   }
 
   public static String showCatalogList(String siteID, String catalogInnerCode) {
     if (StringUtil.isEmpty(siteID)) {
       return "";
     }
     DataTable dt = new QueryBuilder(
       "select ID,Name,ParentID,TreeLevel,InnerCode from ZCCatalog where SiteID=? and Type=? order by orderflag,innercode", 
       siteID, 1).executeDataTable();
     dt = DataGridAction.sortTreeDataTable(dt, "ID", "ParentID");
     StringBuffer sb = new StringBuffer();
     for (int i = 0; i < dt.getRowCount(); ++i) {
       String prefix = "";
       for (int j = 1; j < dt.getInt(i, "TreeLevel"); ++j) {
         prefix = prefix + "　";
       }
       String name = prefix + dt.getString(i, "Name");
       String innerCode = dt.getString(i, "InnerCode");
       String checked = "";
       if (innerCode.equals(catalogInnerCode)) {
         checked = "selected";
       }
       sb.append("<option value=\"" + innerCode + "\" " + checked + ">" + name + "</option>");
     }
     return sb.toString();
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.api.SearchAPI
 * JD-Core Version:    0.5.4
 */