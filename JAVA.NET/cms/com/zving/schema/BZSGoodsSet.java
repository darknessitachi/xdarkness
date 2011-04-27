 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class BZSGoodsSet extends SchemaSet
 {
   public BZSGoodsSet()
   {
     this(10, 0);
   }
 
   public BZSGoodsSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public BZSGoodsSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "BZSGoods";
     this.Columns = BZSGoodsSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into BZSGoods values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZSGoods set ID=?,SiteID=?,CatalogID=?,CatalogInnerCode=?,BrandID=?,BranchInnerCode=?,Type=?,SN=?,Name=?,Alias=?,BarCode=?,WorkFlowID=?,Status=?,Factory=?,OrderFlag=?,MarketPrice=?,Price=?,Discount=?,OfferPrice=?,MemberPrice=?,VIPPrice=?,EffectDate=?,Store=?,Standard=?,Unit=?,Score=?,CommentCount=?,SaleCount=?,HitCount=?,Image0=?,Image1=?,Image2=?,Image3=?,RelativeGoods=?,Keyword=?,TopDate=?,TopFlag=?,Content=?,Summary=?,RedirectURL=?,Attribute=?,RecommendGoods=?,StickTime=?,PublishDate=?,DownlineDate=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZSGoods  where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZSGoods  where ID=? and BackupNo=?";
   }
 
   protected SchemaSet newInstance() {
     return new BZSGoodsSet();
   }
 
   public boolean add(BZSGoodsSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(BZSGoodsSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(BZSGoodsSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public BZSGoodsSchema get(int index) {
     BZSGoodsSchema tSchema = (BZSGoodsSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, BZSGoodsSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(BZSGoodsSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZSGoodsSet
 * JD-Core Version:    0.5.4
 */