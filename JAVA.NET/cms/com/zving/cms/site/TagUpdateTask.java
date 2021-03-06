 package com.zving.cms.site;
 
 import com.zving.framework.Config;
 import com.zving.framework.data.DBUtil;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.schedule.GeneralTask;
 import com.zving.framework.utility.FileUtil;
 import com.zving.framework.utility.Mapx;
 import com.zving.framework.utility.StringUtil;
 import java.io.File;
 
 public class TagUpdateTask extends GeneralTask
 {
   public void execute()
   {
     new Thread()
     {
       public void run() {
         Object[] sites = new QueryBuilder("select ID from ZCSite").executeDataTable().getColumnValues(0);
         for (int m = 0; m < sites.length; ++m) {
           Mapx countMap = new Mapx();
           QueryBuilder qb = new QueryBuilder("select Tag from ZCArticle where Status=? and SiteID=?", 
             30, sites[m]);
           int total = DBUtil.getCount(qb);
           int pageSize = 20000;
 
           for (int i = 0; i * pageSize < total; ++i) {
             DataTable dt = qb.executePagedDataTable(pageSize, i);
             TagUpdateTask.this.cache(dt, i);
             for (int j = 0; j < dt.getRowCount(); ++j) {
               String tag = dt.getString(j, "Tag");
               String[] arr = tag.split("\\s");
               for (int k = 0; k < arr.length; ++k) {
                 if (StringUtil.isNotEmpty(arr[k])) {
                   countMap.put(arr[k], countMap.getInt(arr[k]) + 1);
                 }
               }
             }
           }
           Object[] tags = countMap.keyArray();
 
           QueryBuilder updater = new QueryBuilder("Update ZCTag set UsedCount=? where SiteID=? and Tag=?");
           updater.setBatchMode(true);
           for (int i = 0; i < tags.length; ++i) {
             updater.add(countMap.getInt(tags[i]));
             updater.add(sites[m]);
             updater.add(tags[i]);
             updater.addBatch();
           }
           updater.executeNoQuery();
 
           int TagPageSize = 100;
           Mapx[] relas = new Mapx[TagPageSize];
           for (int p = 0; p < tags.length; p += TagPageSize) {
             for (int i = 0; i * pageSize < total; ++i) {
               DataTable dt = TagUpdateTask.this.getCache(i);
               for (int j = 0; j < dt.getRowCount(); ++j) {
                 String articleTag = " " + dt.getString(j, "Tag") + " ";
                 for (int k = p; (k < tags.length) && (k < p + TagPageSize); ++k) {
                   Mapx map = relas[(k - p)];
                   if (map == null)
                   {
                      tmp379_376 = new Mapx(1000); map = tmp379_376; relas[(k - p)] = tmp379_376;
                   }
                   if (articleTag.indexOf(" " + tags[k] + " ") >= 0) {
                     String[] arr = articleTag.split("\\s");
                     for (int q = 0; q < arr.length; ++q) {
                       if ((StringUtil.isNotEmpty(arr[q])) && (!arr[q].equals(tags[k]))) {
                         map.put(arr[q], map.getInt(arr[q]) + 1);
                       }
                     }
                   }
                 }
               }
             }
 
             updater = new QueryBuilder("Update ZCTag set RelaTag=? where SiteID=? and Tag=?");
             updater.setBatchMode(true);
             for (int i = 0; i < relas.length; ++i) {
               Mapx map = relas[i];
               if (map == null) {
                 break;
               }
               DataTable dt = map.toDataTable();
               dt.sort("Value", "desc");
               StringBuffer sb = new StringBuffer();
               for (int j = 0; (j < 30) && (j < dt.getRowCount()); ++j) {
                 if (j != 0) {
                   sb.append(" ");
                 }
                 sb.append(dt.getString(j, "Key"));
               }
               updater.add(sb.toString());
               updater.add(sites[m]);
               updater.add(tags[i]);
               updater.addBatch();
             }
             updater.executeNoQuery();
           }
           for (int i = 0; i * pageSize < total; ++i)
             FileUtil.deleteEx(Config.getContextRealPath() + "WEB-INF/cache/TagCache" + i + ".dat");
         }
       }
     }
     .start();
   }
 
   private void cache(DataTable dt, int i) {
     FileUtil.serialize(dt, Config.getContextRealPath() + "WEB-INF/cache/TagCache" + i + ".dat");
   }
 
   private DataTable getCache(int i) {
     File f = new File(Config.getContextRealPath() + "WEB-INF/cache/TagCache" + i + ".dat");
     if (f.exists()) {
       return (DataTable)FileUtil.unserialize(f.getAbsolutePath());
     }
     return null;
   }
 
   public long getID()
   {
     return 201005151955L;
   }
 
   public String getName()
   {
     return "Tag热度/关联性计算";
   }
 
   public static void main(String[] args) {
     TagUpdateTask tut = new TagUpdateTask();
     tut.execute();
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.site.TagUpdateTask
 * JD-Core Version:    0.5.4
 */