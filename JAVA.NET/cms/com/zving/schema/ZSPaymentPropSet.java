 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class ZSPaymentPropSet extends SchemaSet
 {
   public ZSPaymentPropSet()
   {
     this(10, 0);
   }
 
   public ZSPaymentPropSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public ZSPaymentPropSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "ZSPaymentProp";
     this.Columns = ZSPaymentPropSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into ZSPaymentProp values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZSPaymentProp set ID=?,PaymentID=?,PropName=?,PropValue=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.FillAllSQL = "select * from ZSPaymentProp  where ID=?";
     this.DeleteSQL = "delete from ZSPaymentProp  where ID=?";
   }
 
   protected SchemaSet newInstance() {
     return new ZSPaymentPropSet();
   }
 
   public boolean add(ZSPaymentPropSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(ZSPaymentPropSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(ZSPaymentPropSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public ZSPaymentPropSchema get(int index) {
     ZSPaymentPropSchema tSchema = (ZSPaymentPropSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, ZSPaymentPropSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(ZSPaymentPropSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZSPaymentPropSet
 * JD-Core Version:    0.5.4
 */