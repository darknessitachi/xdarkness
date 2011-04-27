 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class ZSFavoriteSet extends SchemaSet
 {
   public ZSFavoriteSet()
   {
     this(10, 0);
   }
 
   public ZSFavoriteSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public ZSFavoriteSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "ZSFavorite";
     this.Columns = ZSFavoriteSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into ZSFavorite values(?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZSFavorite set UserName=?,GoodsID=?,SiteID=?,PriceNoteFlag=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where UserName=? and GoodsID=?";
     this.FillAllSQL = "select * from ZSFavorite  where UserName=? and GoodsID=?";
     this.DeleteSQL = "delete from ZSFavorite  where UserName=? and GoodsID=?";
   }
 
   protected SchemaSet newInstance() {
     return new ZSFavoriteSet();
   }
 
   public boolean add(ZSFavoriteSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(ZSFavoriteSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(ZSFavoriteSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public ZSFavoriteSchema get(int index) {
     ZSFavoriteSchema tSchema = (ZSFavoriteSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, ZSFavoriteSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(ZSFavoriteSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZSFavoriteSet
 * JD-Core Version:    0.5.4
 */