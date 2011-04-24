 package com.xdarkness.search;
 
 import java.util.ArrayList;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeFilter;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.WildcardQuery;
import org.wltea.analyzer.lucene.IKQueryParser;

import com.xdarkness.framework.util.LogUtil;
 
 public class SearchParameters
 {
   private BooleanQuery booleanQuery = new BooleanQuery();
 
   private TermRangeFilter filter = null;
 
   private int pageSize = 10;
 
   private int pageIndex = 0;
   private long indexID;
   private ArrayList sortFieldList = new ArrayList(4);
 
   public void addFulltextField(String field, String query)
   {
     addFulltextField(field, query, true);
   }
 
   public void addFulltextField(String field, String query, boolean isMust)
   {
     try
     {
       Query q = IKQueryParser.parse(field.toUpperCase(), query);
       if (q == null) {
         LogUtil.info("不符合条件的关键字:" + query);
         return;
       }
       if (field.equalsIgnoreCase("Title")) {
         q.setBoost(10000.0F);
       }
       this.booleanQuery.add(q, isMust ? BooleanClause.Occur.MUST : BooleanClause.Occur.SHOULD);
     } catch (Exception e) {
       e.printStackTrace();
     }
   }
 
   public void addNotEqualField(String field, String query) {
     TermQuery q = new TermQuery(new Term(field.toUpperCase(), query));
     this.booleanQuery.add(q, BooleanClause.Occur.MUST_NOT);
   }
 
   public void addRightLikeField(String field, String query)
   {
     addRightLikeField(field, query, true);
   }
 
   public void addRightLikeField(String field, String query, boolean isMust)
   {
     WildcardQuery q = new WildcardQuery(new Term(field.toUpperCase(), "*" + query));
     this.booleanQuery.add(q, isMust ? BooleanClause.Occur.MUST : BooleanClause.Occur.SHOULD);
   }
 
   public void addLeftLikeField(String field, String query)
   {
     addLeftLikeField(field, query, true);
   }
 
   public void addLeftLikeField(String field, String query, boolean isMust)
   {
     PrefixQuery q = new PrefixQuery(new Term(field.toUpperCase(), query));
     this.booleanQuery.add(q, isMust ? BooleanClause.Occur.MUST : BooleanClause.Occur.SHOULD);
   }
 
   public void addLikeField(String field, String query)
   {
     addLikeField(field, query, true);
   }
 
   public void addLikeField(String field, String query, boolean isMust)
   {
     WildcardQuery q = new WildcardQuery(new Term(field.toUpperCase(), "*" + query + "*"));
     this.booleanQuery.add(q, isMust ? BooleanClause.Occur.MUST : BooleanClause.Occur.SHOULD);
   }
 
   public void addEqualField(String field, String query)
   {
     addEqualField(field, query, true);
   }
 
   public void addEqualField(String field, String query, boolean isMust)
   {
     TermQuery q = new TermQuery(new Term(field.toUpperCase(), query));
     this.booleanQuery.add(q, isMust ? BooleanClause.Occur.MUST : BooleanClause.Occur.SHOULD);
   }
 
   public void addRangeField(String field, String valueBegin, String valueEnd)
   {
     addRangeField(field, valueBegin, valueEnd, true);
   }
 
   public void addRangeField(String field, String valueBegin, String valueEnd, boolean isMust)
   {
     TermRangeQuery q = new TermRangeQuery(field, valueBegin, valueEnd, true, true);
     this.booleanQuery.add(q, isMust ? BooleanClause.Occur.MUST : BooleanClause.Occur.SHOULD);
   }
 
   public void addQuery(Query q)
   {
     addQuery(q, true);
   }
 
   public void addQuery(Query q, boolean isMust)
   {
     this.booleanQuery.add(q, isMust ? BooleanClause.Occur.MUST : BooleanClause.Occur.SHOULD);
   }
 

   public void setSortField(String field, int dataType, boolean descFlag)
   {
     this.sortFieldList.clear();
     this.sortFieldList.add(new SortField(field.toUpperCase(), dataType, descFlag));
   }
 
   public void addSortField(String field, int dataType, boolean descFlag)
   {
     this.sortFieldList.add(new SortField(field.toUpperCase(), dataType, descFlag));
   }
 
   public void setDateRange(String field, String startDate, String endDate)
   {
     this.filter = new TermRangeFilter(field.toUpperCase(), startDate, endDate, true, true);
   }
 
   public BooleanQuery getBooleanQuery() {
     return this.booleanQuery;
   }
 
   public TermRangeFilter getDateRangeFilter() {
     return this.filter;
   }
 
   public Sort getSort() {
     if (this.sortFieldList.size() == 0) {
       return null;
     }
     Sort sort = new Sort();
     SortField[] sfs = new SortField[this.sortFieldList.size()];
     for (int i = 0; i < this.sortFieldList.size(); i++) {
       sfs[i] = ((SortField)this.sortFieldList.get(i));
     }
     sort.setSort(sfs);
     return sort;
   }
 
   public int getPageSize() {
     return this.pageSize;
   }
 
   public void setPageSize(int pageSize) {
     this.pageSize = pageSize;
   }
 
   public int getPageIndex() {
     return this.pageIndex;
   }
 
   public void setPageIndex(int pageIndex) {
     if (pageIndex < 0) {
       pageIndex = 0;
     }
     this.pageIndex = pageIndex;
   }
 
   public long getIndexID() {
     return this.indexID;
   }
 
   public void setIndexID(long indexID) {
     this.indexID = indexID;
   }
 }

          
/*    com.xdarkness.search.SearchParameters
 * JD-Core Version:    0.6.0
 */