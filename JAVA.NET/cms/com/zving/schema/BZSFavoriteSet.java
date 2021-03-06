 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class BZSFavoriteSet extends SchemaSet
 {
   public BZSFavoriteSet()
   {
     this(10, 0);
   }
 
   public BZSFavoriteSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public BZSFavoriteSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "BZSFavorite";
     this.Columns = BZSFavoriteSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into BZSFavorite values(?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZSFavorite set UserName=?,GoodsID=?,SiteID=?,PriceNoteFlag=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where UserName=? and GoodsID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZSFavorite  where UserName=? and GoodsID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZSFavorite  where UserName=? and GoodsID=? and BackupNo=?";
   }
 
   protected SchemaSet newInstance() {
     return new BZSFavoriteSet();
   }
 
   public boolean add(BZSFavoriteSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(BZSFavoriteSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(BZSFavoriteSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public BZSFavoriteSchema get(int index) {
     BZSFavoriteSchema tSchema = (BZSFavoriteSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, BZSFavoriteSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(BZSFavoriteSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZSFavoriteSet
 * JD-Core Version:    0.5.4
 */