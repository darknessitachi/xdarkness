 package com.zving.cms.stat.impl;
 
 import com.zving.cms.pub.CatalogUtil;
 import com.zving.cms.stat.AbstractStat;
 import com.zving.cms.stat.Visit;
 import com.zving.cms.stat.VisitCount;
 import com.zving.framework.data.QueryBuilder;
 import com.zving.framework.data.Transaction;
 import com.zving.framework.utility.StringUtil;
 import com.zving.schema.ZCStatItemSchema;
 import com.zving.schema.ZCStatItemSet;
 import java.util.ArrayList;
 
 public class CatalogStat extends AbstractStat
 {
   private static final String[] avgSubTypes = { "StickTime" };
   private static final String Type = "Catalog";
 
   public String getStatType()
   {
     return "Catalog";
   }
 
   public String[] getAverageSubTypes() {
     return avgSubTypes;
   }
 
   public void deal(Visit v) {
     if ((StringUtil.isEmpty(v.Type)) || ("AD".equals(v.Type)) || ("Other".equals(v.Type))) {
       return;
     }
     String code = v.CatalogInnerCode;
     if ((code == null) || (code.length() % 6 != 0)) {
       code = "";
     }
     if ((StringUtil.isNotEmpty(code)) && (CatalogUtil.getSiteIDByInnerCode(v.CatalogInnerCode) == null)) {
       return;
     }
     if (v.LeafID == 0L) {
       if ("Unload".equals(v.Event))
         VisitCount.getInstance().addAverage(v.SiteID, "Catalog", "StickTime", code + "Index", v.StickTime);
       else {
         VisitCount.getInstance().add(v.SiteID, "Catalog", "PV", code + "Index");
       }
     }
     String[] codes = new String[code.length() / 6];
     for (int i = 0; i < codes.length; ++i) {
       codes[i] = code.substring(0, i * 6 + 6);
     }
     for (int i = 0; i < codes.length; ++i) {
       code = codes[i];
       if ("Unload".equals(v.Event))
         VisitCount.getInstance().addAverage(v.SiteID, "Catalog", "StickTime", code, v.StickTime);
       else {
         VisitCount.getInstance().add(v.SiteID, "Catalog", "PV", code);
       }
       code = code.substring(0, code.length() - 6);
     }
   }
 
   public static void updateInnerCode(Transaction tran, long siteID, String oldInnerCode, String newInnerCode)
   {
     QueryBuilder qb = new QueryBuilder("where SiteID=? and type=? and item like ?");
     qb.add(siteID);
     qb.add("Catalog");
     qb.add(oldInnerCode + "%");
     ZCStatItemSet statSet = new ZCStatItemSchema().query(qb);
     ZCStatItemSet childSet = new ZCStatItemSet();
     for (int i = 0; i < statSet.size(); ++i) {
       String item = statSet.get(i).getItem();
       if (item.equals(oldInnerCode)) {
         childSet.add(statSet.get(i));
       }
       item = StringUtil.replaceEx(item, oldInnerCode, newInnerCode);
       statSet.get(i).setItem(item);
     }
 
     ArrayList parentList = new ArrayList();
     ZCStatItemSet parentSet = null;
     String code = oldInnerCode;
     while (code.length() > 6) {
       code = code.substring(0, code.length() - 6);
       parentList.add(code);
     }
     if (parentList.size() > 0) {
       qb = new QueryBuilder("where SiteID=? and type=? and item in (" + StringUtil.join(parentList) + ")");
       qb.add(siteID);
       qb.add("Catalog");
       parentSet = new ZCStatItemSchema().query(qb);
     }
 
     parentList = new ArrayList();
     ZCStatItemSet newParentSet = null;
     code = newInnerCode;
     while (code.length() > 6) {
       code = code.substring(0, code.length() - 6);
       parentList.add(code);
     }
     if (parentList.size() > 0) {
       qb = new QueryBuilder("where SiteID=? and type=? and item in (" + StringUtil.join(parentList) + ")");
       qb.add(siteID);
       qb.add("Catalog");
       newParentSet = new ZCStatItemSchema().query(qb);
     }
 
     if (parentSet != null) {
       for (int i = 0; i < parentSet.size(); ++i) {
         ZCStatItemSchema parent = parentSet.get(i);
         for (int j = 0; j < childSet.size(); ++j) {
           ZCStatItemSchema child = childSet.get(j);
           if (child.getItem().endsWith("Index")) {
             continue;
           }
           if ((child.getPeriod().equals(parent.getPeriod())) && (child.getSubType().equals(parent.getSubType()))) {
             for (int k = 5; k < child.getColumnCount(); ++k) {
               Long n1 = (Long)child.getV(k);
               Long n2 = (Long)parent.getV(k);
               long v = n2.longValue() - n1.longValue();
               if (v < 0L) {
                 v = 0L;
               }
               parent.setV(k, new Long(v));
             }
           }
         }
       }
     }
 
     ZCStatItemSet noExistsParentSet = new ZCStatItemSet();
     if (newParentSet != null) {
       for (int j = 0; j < childSet.size(); ++j) {
         ZCStatItemSchema child = childSet.get(j);
         if (child.getItem().endsWith("Index")) {
           continue;
         }
         ArrayList list = (ArrayList)parentList.clone();
         for (int i = 0; i < newParentSet.size(); ++i) {
           ZCStatItemSchema parent = newParentSet.get(i);
           if ((child.getPeriod().equals(parent.getPeriod())) && (child.getSubType().equals(parent.getSubType()))) {
             for (int k = 5; k < child.getColumnCount(); ++k) {
               Long n1 = (Long)child.getV(k);
               Long n2 = (Long)parent.getV(k);
               parent.setV(k, new Long(n2.longValue() + n1.longValue()));
             }
             String item = parent.getItem();
             list.remove(item);
           }
         }
         for (int i = 0; i < list.size(); ++i) {
           code = (String)list.get(i);
           ZCStatItemSchema si = (ZCStatItemSchema)child.clone();
           si.setItem(code);
           noExistsParentSet.add(si);
         }
       }
     }
     tran.add(noExistsParentSet, 1);
     tran.add(parentSet, 2);
     tran.add(newParentSet, 2);
     tran.add(statSet, 2);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.cms.stat.impl.CatalogStat
 * JD-Core Version:    0.5.4
 */