 package com.zving.search.index;
 
 public class IndexerThread extends Thread
 {
   private Indexer indexer;
 
   public void run()
   {
     try
     {
       if (this.indexer.isUpdateFlag())
         this.indexer.update();
       else
         this.indexer.create();
     }
     finally {
       synchronized (this.indexer) {
         this.indexer.aliveThreadCount -= 1;
       }
     }
   }
 
   public Indexer getIndexer() {
     return this.indexer;
   }
 
   public void setIndexer(Indexer indexer) {
     this.indexer = indexer;
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.search.index.IndexerThread
 * JD-Core Version:    0.5.4
 */