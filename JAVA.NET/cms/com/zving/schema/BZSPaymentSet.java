 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class BZSPaymentSet extends SchemaSet
 {
   public BZSPaymentSet()
   {
     this(10, 0);
   }
 
   public BZSPaymentSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public BZSPaymentSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "BZSPayment";
     this.Columns = BZSPaymentSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into BZSPayment values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZSPayment set ID=?,SiteID=?,Name=?,Info=?,IsVisible=?,ImagePath=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZSPayment  where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZSPayment  where ID=? and BackupNo=?";
   }
 
   protected SchemaSet newInstance() {
     return new BZSPaymentSet();
   }
 
   public boolean add(BZSPaymentSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(BZSPaymentSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(BZSPaymentSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public BZSPaymentSchema get(int index) {
     BZSPaymentSchema tSchema = (BZSPaymentSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, BZSPaymentSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(BZSPaymentSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZSPaymentSet
 * JD-Core Version:    0.5.4
 */