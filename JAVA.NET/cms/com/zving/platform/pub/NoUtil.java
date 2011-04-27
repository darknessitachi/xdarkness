 package com.zving.platform.pub;
 
 import com.zving.framework.Config;
 import com.zving.framework.data.DBConn;
 import com.zving.framework.data.DBConnPool;
 import com.zving.framework.data.DataAccess;
 import com.zving.framework.data.DataTable;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.utility.StringUtil;
 import com.zving.schema.ZDMaxNoSchema;
 import com.zving.schema.ZDMaxNoSet;
 import java.sql.SQLException;
 
 public class NoUtil
 {
   private static ZDMaxNoSet MaxNoSet;
   private static Object mutex = new Object();
 
   public static String getMaxNo(String noType) {
     return getMaxNo(noType, "SN");
   }
 
   public static String getMaxNoLoal(String noType) {
     return getMaxNoLocal(noType, "SN");
   }
 
   public static long getMaxID(String noType, String subType)
   {
     return getMaxIDUseLock(noType, subType);
   }
 
   public static synchronized long getMaxIDUseLock(String noType, String subType)
   {
     DBConn conn = DBConnPool.getConnection("Default", false, false);
     DataAccess da = new DataAccess(conn);
     try {
       da.setAutoCommit(false);
       QueryBuilder qb = new QueryBuilder("select MaxValue from ZDMaxNo where NoType=? and NoSubType=?", noType, 
         subType);
       if (Config.isOracle()) {
         qb.append(" for update");
       }
       Object maxValue = da.executeOneValue(qb);
       if (maxValue != null) {
         long t = Long.parseLong(maxValue.toString()) + 1L;
         qb = new QueryBuilder("update ZDMaxNo set MaxValue=? where NoType=? and NoSubType=?", t, noType);
         qb.add(subType);
         da.executeNoQuery(qb);
         da.commit();
         long l1 = t;
         return l1;
       }
       Object maxValue;
       QueryBuilder qb;
       ZDMaxNoSchema maxno = new ZDMaxNoSchema();
       maxno.setNoType(noType);
       maxno.setNoSubType(subType);
       maxno.setMaxValue(1L);
       maxno.setLength(10L);
       maxno.setDataAccess(da);
       if (maxno.insert()) {
         da.commit();
         try
         {
           da.setAutoCommit(true);
           da.close();
         } catch (SQLException e) {
           e.printStackTrace();
         }
       }
       ZDMaxNoSchema maxno;
       Object maxValue;
       QueryBuilder qb;
       throw new RuntimeException("获取最大号时发生错误!");
     }
     catch (Exception e)
     {
       throw new RuntimeException("获取最大号时发生错误:" + e.getMessage());
     } finally {
       try {
         da.setAutoCommit(true);
         da.close();
       } catch (SQLException e) {
         e.printStackTrace();
       }
     }
   }
 
   public static String getMaxNo(String noType, int length)
   {
     long t = getMaxID(noType, "SN");
     String no = String.valueOf(t);
     if (no.length() > length) {
       return no.substring(0, length);
     }
     return StringUtil.leftPad(no, '0', length);
   }
 
   public static String getMaxNo(String noType, String prefix, int length)
   {
     long t = getMaxID(noType, prefix);
     String no = String.valueOf(t);
     if (no.length() > length) {
       return no.substring(0, length);
     }
     return prefix + StringUtil.leftPad(no, '0', length);
   }
 
   public static synchronized String getMaxNoUseLock(String noType, String subType) {
     DataAccess da = new DataAccess();
     try {
       da.setAutoCommit(false);
       QueryBuilder qb = new QueryBuilder("select MaxValue,Length from ZDMaxNo where NoType=? and NoSubType=?", 
         noType, subType);
       if (Config.isOracle()) {
         qb.append(" for update");
       }
       DataTable dt = qb.executeDataTable();
       if (dt.getRowCount() > 0) {
         long t = Long.parseLong(dt.getString(0, "MaxValue")) + 1L;
         int length = Integer.parseInt(dt.getString(0, "Length"));
         String no = String.valueOf(t);
         if (length > 0) {
           no = StringUtil.leftPad(no, '0', length);
         }
         qb = new QueryBuilder("update ZDMaxNo set MaxValue=? where NoType=? and NoSubType=?", t, noType);
         qb.add(subType);
         da.executeNoQuery(qb);
         da.commit();
         String str1 = no;
         return str1;
       }
       DataTable dt;
       QueryBuilder qb;
       ZDMaxNoSchema maxno = new ZDMaxNoSchema();
       maxno.setNoType(noType);
       maxno.setNoSubType(subType);
       maxno.setMaxValue(1L);
       maxno.setLength(10L);
       maxno.setDataAccess(da);
       if (maxno.insert()) {
         da.commit();
         try
         {
           da.setAutoCommit(true);
           da.close();
         } catch (SQLException e) {
           e.printStackTrace();
         }
       }
       ZDMaxNoSchema maxno;
       DataTable dt;
       QueryBuilder qb;
       throw new RuntimeException("获取最大号时发生错误!");
     }
     catch (Exception e)
     {
       throw new RuntimeException("获取最大号时发生错误:" + e.getMessage());
     } finally {
       try {
         da.setAutoCommit(true);
         da.close();
       } catch (SQLException e) {
         e.printStackTrace();
       }
     }
   }
 
   public static synchronized long getMaxIDLocal(String noType, String subType) {
     if (MaxNoSet == null) {
       init();
     }
     ZDMaxNoSchema maxno = null;
     if (MaxNoSet != null) {
       for (int i = 0; i < MaxNoSet.size(); ++i) {
         maxno = MaxNoSet.get(i);
         if ((maxno.getNoType().equals(noType)) && (maxno.getNoSubType().equals(subType)))
           synchronized (mutex) {
             maxno.setMaxValue(maxno.getMaxValue() + 1L);
             if (!maxno.update()) {
               throw new RuntimeException("生成最大号错误,MaxNoType=" + noType + ",MaxSubType=" + subType);
             }
             return maxno.getMaxValue();
           }
       }
     }
     else {
       synchronized (mutex) {
         MaxNoSet = new ZDMaxNoSet();
         maxno = new ZDMaxNoSchema();
         maxno.setNoType(noType);
         maxno.setNoSubType(subType);
         maxno.setLength(0L);
         maxno.setMaxValue(1L);
         maxno.insert();
         MaxNoSet.add(maxno);
         return 1L;
       }
     }
 
     synchronized (mutex) {
       maxno = new ZDMaxNoSchema();
       maxno.setNoType(noType);
       maxno.setNoSubType(subType);
       maxno.setLength(10L);
       maxno.setMaxValue(1L);
       maxno.insert();
       MaxNoSet.add(maxno);
       return 1L;
     }
   }
 
   public static long getMaxID(String noType) {
     return getMaxID(noType, "ID");
   }
 
   public static long getMaxIDLocal(String noType) {
     return getMaxIDLocal(noType, "ID");
   }
 
   public static String getMaxNo(String noType, String subType) {
     if (Config.isDebugMode()) {
       return getMaxNoUseLock(noType, subType);
     }
     return getMaxNoLocal(noType, subType);
   }
 
   public static synchronized String getMaxNoLocal(String noType, String subType) {
     if (MaxNoSet == null) {
       init();
     }
     ZDMaxNoSchema maxno = null;
     if (MaxNoSet != null) {
       for (int i = 0; i < MaxNoSet.size(); ++i) {
         maxno = MaxNoSet.get(i);
         if ((maxno.getNoType().equals(noType)) && (maxno.getNoSubType().equals(subType)))
           synchronized (mutex) {
             maxno.setMaxValue(maxno.getMaxValue() + 1L);
             if (!maxno.update()) {
               throw new RuntimeException("生成最大号错误,NoType=" + noType + ",MaxSubType=" + subType);
             }
             if (maxno.getLength() <= 0L) {
               return String.valueOf(maxno.getMaxValue());
             }
             return StringUtil.leftPad(String.valueOf(maxno.getMaxValue()), '0', (int)maxno.getLength());
           }
       }
     }
     else {
       synchronized (mutex) {
         MaxNoSet = new ZDMaxNoSet();
         maxno = new ZDMaxNoSchema();
         maxno.setNoType(noType);
         maxno.setNoSubType(subType);
         maxno.setLength(10L);
         maxno.setMaxValue(1L);
         maxno.insert();
         MaxNoSet.add(maxno);
         return "0000000001";
       }
     }
 
     synchronized (mutex) {
       maxno = new ZDMaxNoSchema();
       maxno.setNoType(noType);
       maxno.setNoSubType(subType);
       maxno.setLength(10L);
       maxno.setMaxValue(1L);
       maxno.insert();
       MaxNoSet.add(maxno);
       return "0000000001";
     }
   }
 
   private static synchronized void init() {
     if (MaxNoSet != null) {
       return;
     }
     ZDMaxNoSchema maxno = new ZDMaxNoSchema();
     MaxNoSet = maxno.query();
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.platform.pub.NoUtil
 * JD-Core Version:    0.5.4
 */