 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class ZSOrderItemSet extends SchemaSet
 {
   public ZSOrderItemSet()
   {
     this(10, 0);
   }
 
   public ZSOrderItemSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public ZSOrderItemSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "ZSOrderItem";
     this.Columns = ZSOrderItemSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into ZSOrderItem values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZSOrderItem set OrderID=?,GoodsID=?,SiteID=?,UserName=?,SN=?,Name=?,Price=?,Discount=?,DiscountPrice=?,Count=?,Amount=?,Score=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where OrderID=? and GoodsID=?";
     this.FillAllSQL = "select * from ZSOrderItem  where OrderID=? and GoodsID=?";
     this.DeleteSQL = "delete from ZSOrderItem  where OrderID=? and GoodsID=?";
   }
 
   protected SchemaSet newInstance() {
     return new ZSOrderItemSet();
   }
 
   public boolean add(ZSOrderItemSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(ZSOrderItemSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(ZSOrderItemSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public ZSOrderItemSchema get(int index) {
     ZSOrderItemSchema tSchema = (ZSOrderItemSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, ZSOrderItemSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(ZSOrderItemSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZSOrderItemSet
 * JD-Core Version:    0.5.4
 */