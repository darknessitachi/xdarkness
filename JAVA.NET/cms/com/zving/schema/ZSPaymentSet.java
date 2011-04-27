 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class ZSPaymentSet extends SchemaSet
 {
   public ZSPaymentSet()
   {
     this(10, 0);
   }
 
   public ZSPaymentSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public ZSPaymentSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "ZSPayment";
     this.Columns = ZSPaymentSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into ZSPayment values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZSPayment set ID=?,SiteID=?,Name=?,Info=?,IsVisible=?,ImagePath=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.FillAllSQL = "select * from ZSPayment  where ID=?";
     this.DeleteSQL = "delete from ZSPayment  where ID=?";
   }
 
   protected SchemaSet newInstance() {
     return new ZSPaymentSet();
   }
 
   public boolean add(ZSPaymentSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(ZSPaymentSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(ZSPaymentSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public ZSPaymentSchema get(int index) {
     ZSPaymentSchema tSchema = (ZSPaymentSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, ZSPaymentSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(ZSPaymentSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZSPaymentSet
 * JD-Core Version:    0.5.4
 */