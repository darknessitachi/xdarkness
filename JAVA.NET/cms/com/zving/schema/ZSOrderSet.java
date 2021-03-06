 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class ZSOrderSet extends SchemaSet
 {
   public ZSOrderSet()
   {
     this(10, 0);
   }
 
   public ZSOrderSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public ZSOrderSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "ZSOrder";
     this.Columns = ZSOrderSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into ZSOrder values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update ZSOrder set ID=?,SiteID=?,UserName=?,IsValid=?,Status=?,Amount=?,SendFee=?,OrderAmount=?,Score=?,Name=?,Province=?,City=?,District=?,Address=?,ZipCode=?,Tel=?,Mobile=?,HasInvoice=?,InvoiceTitle=?,SendBeginDate=?,SendEndDate=?,SendTimeSlice=?,SendInfo=?,SendType=?,PaymentType=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=? where ID=?";
     this.FillAllSQL = "select * from ZSOrder  where ID=?";
     this.DeleteSQL = "delete from ZSOrder  where ID=?";
   }
 
   protected SchemaSet newInstance() {
     return new ZSOrderSet();
   }
 
   public boolean add(ZSOrderSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(ZSOrderSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(ZSOrderSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public ZSOrderSchema get(int index) {
     ZSOrderSchema tSchema = (ZSOrderSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, ZSOrderSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(ZSOrderSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.ZSOrderSet
 * JD-Core Version:    0.5.4
 */