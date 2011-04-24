 package com.xdarkness.search;
 
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

          
/*    com.xdarkness.search.ZvingAnalyzer
 * JD-Core Version:    0.6.0
 */