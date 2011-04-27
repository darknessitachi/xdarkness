 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class ZSGoodsSet extends SchemaSet
 {
   public ZSGoodsSet()
   {
     this(10, 0);
   }
 
   public ZSGoodsSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public ZSGoodsSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "ZSGoods";
     this.Columns = ZSGoodsSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into ZSGoods values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZSGoods set ID=?,SiteID=?,CatalogID=?,CatalogInnerCode=?,BrandID=?,BranchInnerCode=?,Type=?,SN=?,Name=?,Alias=?,BarCode=?,WorkFlowID=?,Status=?,Factory=?,OrderFlag=?,MarketPrice=?,Price=?,Discount=?,OfferPrice=?,MemberPrice=?,VIPPrice=?,EffectDate=?,Store=?,Standard=?,Unit=?,Score=?,CommentCount=?,SaleCount=?,HitCount=?,Image0=?,Image1=?,Image2=?,Image3=?,RelativeGoods=?,Keyword=?,TopDate=?,TopFlag=?,Content=?,Summary=?,RedirectURL=?,Attribute=?,RecommendGoods=?,StickTime=?,PublishDate=?,DownlineDate=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.FillAllSQL = "select * from ZSGoods  where ID=?";
     this.DeleteSQL = "delete from ZSGoods  where ID=?";
   }
 
   protected SchemaSet newInstance() {
     return new ZSGoodsSet();
   }
 
   public boolean add(ZSGoodsSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(ZSGoodsSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(ZSGoodsSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public ZSGoodsSchema get(int index) {
     ZSGoodsSchema tSchema = (ZSGoodsSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, ZSGoodsSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(ZSGoodsSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZSGoodsSet
 * JD-Core Version:    0.5.4
 */