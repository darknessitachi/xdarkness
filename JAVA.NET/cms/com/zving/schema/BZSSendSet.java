 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class BZSSendSet extends SchemaSet
 {
   public BZSSendSet()
   {
     this(10, 0);
   }
 
   public BZSSendSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public BZSSendSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "BZSSend";
     this.Columns = BZSSendSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into BZSSend values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZSSend set ID=?,SiteID=?,Name=?,SendInfo=?,ArriveInfo=?,Info=?,Price=?,Memo=?,Prop1=?,Prop2=?,Prop3=?,Prop4=?,AddUser=?,AddTime=?,ModifyUser=?,ModifyTime=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZSSend  where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZSSend  where ID=? and BackupNo=?";
   }
 
   protected SchemaSet newInstance() {
     return new BZSSendSet();
   }
 
   public boolean add(BZSSendSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(BZSSendSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(BZSSendSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public BZSSendSchema get(int index) {
     BZSSendSchema tSchema = (BZSSendSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, BZSSendSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(BZSSendSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZSSendSet
 * JD-Core Version:    0.5.4
 */