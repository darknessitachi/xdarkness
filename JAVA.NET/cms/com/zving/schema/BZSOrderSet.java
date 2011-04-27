 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class BZSOrderSet extends SchemaSet
 {
   public BZSOrderSet()
   {
     this(10, 0);
   }
 
   public BZSOrderSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public BZSOrderSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "BZSOrder";
     this.Columns = BZSOrderSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into BZSOrder values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZSOrder set ID=?,SiteID=?,UserName=?,IsValid=?,Status=?,Amount=?,SendFee=?,OrderAmount=?,Score=?,Name=?,Province=?,City=?,District=?,Address=?,ZipCode=?,Tel=?,Mobile=?,HasInvoice=?,InvoiceTitle=?,SendBeginDate=?,SendEndDate=?,SendTimeSlice=?,SendInfo=?,SendType=?,PaymentType=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZSOrder  where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZSOrder  where ID=? and BackupNo=?";
   }
 
   protected SchemaSet newInstance() {
     return new BZSOrderSet();
   }
 
   public boolean add(BZSOrderSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(BZSOrderSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(BZSOrderSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public BZSOrderSchema get(int index) {
     BZSOrderSchema tSchema = (BZSOrderSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, BZSOrderSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(BZSOrderSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZSOrderSet
 * JD-Core Version:    0.5.4
 */