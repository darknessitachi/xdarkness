 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class BZSPaymentPropSet extends SchemaSet
 {
   public BZSPaymentPropSet()
   {
     this(10, 0);
   }
 
   public BZSPaymentPropSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public BZSPaymentPropSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "BZSPaymentProp";
     this.Columns = BZSPaymentPropSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into BZSPaymentProp values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZSPaymentProp set ID=?,PaymentID=?,PropName=?,PropValue=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZSPaymentProp  where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZSPaymentProp  where ID=? and BackupNo=?";
   }
 
   protected SchemaSet newInstance() {
     return new BZSPaymentPropSet();
   }
 
   public boolean add(BZSPaymentPropSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(BZSPaymentPropSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(BZSPaymentPropSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public BZSPaymentPropSchema get(int index) {
     BZSPaymentPropSchema tSchema = (BZSPaymentPropSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, BZSPaymentPropSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(BZSPaymentPropSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZSPaymentPropSet
 * JD-Core Version:    0.5.4
 */