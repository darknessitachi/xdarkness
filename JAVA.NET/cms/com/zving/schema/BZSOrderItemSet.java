 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class BZSOrderItemSet extends SchemaSet
 {
   public BZSOrderItemSet()
   {
     this(10, 0);
   }
 
   public BZSOrderItemSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public BZSOrderItemSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "BZSOrderItem";
     this.Columns = BZSOrderItemSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into BZSOrderItem values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZSOrderItem set OrderID=?,GoodsID=?,SiteID=?,UserName=?,SN=?,Name=?,Price=?,Discount=?,DiscountPrice=?,Count=?,Amount=?,Score=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where OrderID=? and GoodsID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZSOrderItem  where OrderID=? and GoodsID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZSOrderItem  where OrderID=? and GoodsID=? and BackupNo=?";
   }
 
   protected SchemaSet newInstance() {
     return new BZSOrderItemSet();
   }
 
   public boolean add(BZSOrderItemSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(BZSOrderItemSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(BZSOrderItemSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public BZSOrderItemSchema get(int index) {
     BZSOrderItemSchema tSchema = (BZSOrderItemSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, BZSOrderItemSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(BZSOrderItemSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZSOrderItemSet
 * JD-Core Version:    0.5.4
 */