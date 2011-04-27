 package com.zving.search;
 
 import java.io.Reader;
 import org.apache.lucene.analysis.Analyzer;
 import org.apache.lucene.analysis.TokenStream;
 
 public class ZvingAnalyzer extends Analyzer
 {
   public TokenStream tokenStream(String fieldName, Reader reader)
   {
     return new ZvingTokenizer(fieldName, reader);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.search.ZvingAnalyzer
 * JD-Core Version:    0.5.4
 */