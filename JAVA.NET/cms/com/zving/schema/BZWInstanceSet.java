 package com.zving.schema;
 
 import com.zving.framework.orm.SchemaSet;
 
 public class BZWInstanceSet extends SchemaSet
 {
   public BZWInstanceSet()
   {
     this(10, 0);
   }
 
   public BZWInstanceSet(int initialCapacity) {
     this(initialCapacity, 0);
   }
 
   public BZWInstanceSet(int initialCapacity, int capacityIncrement) {
     super(initialCapacity, capacityIncrement);
     this.TableCode = "BZWInstance";
     this.Columns = BZWInstanceSchema._Columns;
     this.NameSpace = "com.zving.schema";
     this.InsertAllSQL = "insert into BZWInstance values(?,?,?,?,?,?,?,?,?,?,?,?)";
     this.UpdateAllSQL = "update BZWInstance set ID=?,WorkflowID=?,Name=?,DataID=?,State=?,Memo=?,AddTime=?,AddUser=?,BackupNo=?,BackupOperator=?,BackupTime=?,BackupMemo=? where ID=? and BackupNo=?";
     this.FillAllSQL = "select * from BZWInstance  where ID=? and BackupNo=?";
     this.DeleteSQL = "delete from BZWInstance  where ID=? and BackupNo=?";
   }
 
   protected SchemaSet newInstance() {
     return new BZWInstanceSet();
   }
 
   public boolean add(BZWInstanceSchema aSchema) {
     return super.add(aSchema);
   }
 
   public boolean add(BZWInstanceSet aSet) {
     return super.add(aSet);
   }
 
   public boolean remove(BZWInstanceSchema aSchema) {
     return super.remove(aSchema);
   }
 
   public BZWInstanceSchema get(int index) {
     BZWInstanceSchema tSchema = (BZWInstanceSchema)super.getObject(index);
     return tSchema;
   }
 
   public boolean set(int index, BZWInstanceSchema aSchema) {
     return super.set(index, aSchema);
   }
 
   public boolean set(BZWInstanceSet aSet) {
     return super.set(aSet);
   }
 }

/* Location:           E:\zcms\zcms_1.3_final_utf8\WEB-INF\classes\
 * Qualified Name:     com.zving.schema.BZWInstanceSet
 * JD-Core Version:    0.5.4
 */